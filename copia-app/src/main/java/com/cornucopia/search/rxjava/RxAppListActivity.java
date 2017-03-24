package com.cornucopia.search.rxjava;

import android.app.ListActivity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cornucopia.R;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class RxAppListActivity extends ListActivity {
    
    private static final String TAG = "RxAppListActivity";
    
    private List<RxAppEntry> listApp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_applist);
        
        onAppLoading();
    }
    
    void onAppLoading() {

        loadAppObservable()
            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<List<RxAppEntry>>() {

                @Override
                public void onCompleted() {
                    AppListAdapter adapter = new AppListAdapter(listApp);
                    setListAdapter(adapter);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError", e);
                }

                @Override
                public void onNext(List<RxAppEntry> t) {
                    listApp = t;
                }
                
            }); 
    }
    
    private Observable<List<RxAppEntry>> loadAppObservable() {
        return Observable.defer(new Func0<Observable<List<RxAppEntry>>>() {

            @Override
            public Observable<List<RxAppEntry>> call() {
                List<RxAppEntry> applist = loadAppList();
                return Observable.just(applist);
            }
        });
    }

    private List<RxAppEntry> loadAppList() {
        // Retrieve all known applications.
        List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(
                PackageManager.GET_UNINSTALLED_PACKAGES);
//                PackageManager.GET_DISABLED_COMPONENTS);
        if (apps == null) {
            apps = new ArrayList<ApplicationInfo>();
        }

        final Context context = this;

        // Create corresponding array of entries and load their labels.
        List<RxAppEntry> entries = new ArrayList<RxAppEntry>(apps.size());
        for (int i=0; i<apps.size(); i++) {
            RxAppEntry entry = new RxAppEntry(context, apps.get(i));
            entry.loadLabel(context);
            entries.add(entry);
        }

        // Sort the list.
        Collections.sort(entries, ALPHA_COMPARATOR);

        // Done!
        return entries;
    }
    
    public static final Comparator<RxAppEntry> ALPHA_COMPARATOR = new Comparator<RxAppEntry>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(RxAppEntry object1, RxAppEntry object2) {
            return sCollator.compare(object1.getLabel(), object2.getLabel());
        }
    };

    class AppListAdapter extends BaseAdapter {
        
        private List<RxAppEntry> listApp;
        
        private HolderView holderView;
        
        AppListAdapter(List<RxAppEntry> listApp) {
            this.listApp = listApp;
        }

        @Override
        public int getCount() {
            if (null == listApp) {
                return 0;
            } else {
                return listApp.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return listApp.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = getLayoutInflater().inflate(android.R.layout.activity_list_item, null);
                holderView = new HolderView();
                holderView.tvAppTitle = (TextView) convertView.findViewById(android.R.id.text1);
                holderView.ivAppIcon = (ImageView) convertView.findViewById(android.R.id.icon);
                
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            
            RxAppEntry appEntry = listApp.get(position);
            holderView.ivAppIcon.setImageDrawable(appEntry.getIcon());
            holderView.tvAppTitle.setText(appEntry.getLabel());
            
            return convertView;
        }
        
        class HolderView {
            TextView tvAppTitle;
            ImageView ivAppIcon;
        }
        
    }
}

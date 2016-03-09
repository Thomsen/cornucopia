package com.cornucopia.search.loader;

import java.util.List;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cornucopia.R;

public class AppListActivity extends ListActivity implements LoaderCallbacks<List<AppEntry>> {
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_applist);
        
//        AppListLoader alLoader = new AppListLoader(this);
//        List<AppEntry> la = alLoader.loadInBackground();
//        AppListAdapter adapter = new AppListAdapter(la);
//        setListAdapter(adapter);
        
        LoaderManager manager = getLoaderManager();
        manager.initLoader(1000, null, this);
    }
    

    @Override
    public Loader<List<AppEntry>> onCreateLoader(int id, Bundle args) {
        return new AppListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<AppEntry>> loader,
            List<AppEntry> data) {
        AppListAdapter adapter = new AppListAdapter(data);
        setListAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<AppEntry>> loader) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
    
    class AppListAdapter extends BaseAdapter {
        
        private List<AppEntry> listApp;
        
        private HolderView holderView;
        
        AppListAdapter(List<AppEntry> listApp) {
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
            
            AppEntry appEntry = listApp.get(position);
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

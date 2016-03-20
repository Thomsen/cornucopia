package com.cornucopia.event.rxjava;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import com.cornucopia.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class RxjavaActivity extends Activity implements OnClickListener {
    
    private static final String TAG = "RxjavaActivity";
    
    private Handler backgroundHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_rxjava);
        
        Button btnRx = (Button) findViewById(R.id.btn_rxjava);
        btnRx.setOnClickListener(this);
        
        Observable<OnClickEvent> clicksObservable = ViewObservable.clicks(btnRx);
        
        BackgroundThread bgThread = new BackgroundThread();
        bgThread.start();
        backgroundHandler = new Handler(bgThread.getLooper());
        
    }

    private void basicOb() {
        Observable<String> myObservable = Observable.just("Hello", "World"); // 被观察者(观察得到的)，事件源
        
        final Observer<String> myObserver = new Observer<String>() {  // 观察者

            @Override
            public void onCompleted() {
                Toast.makeText(RxjavaActivity.this, "observer completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxjavaActivity.this, "observer error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String t) {
                Toast.makeText(RxjavaActivity.this, "observer next " + t, Toast.LENGTH_SHORT).show();            
            }
            
        };
        
        Subscription mySubscription = myObservable.subscribe(myObserver);  // 订阅者
        
        mySubscription.unsubscribe();
        
        Action1<String> myAction1 = new Action1<String>() {

            @Override
            public void call(String t) {
                Toast.makeText(RxjavaActivity.this, "my action " + t, Toast.LENGTH_SHORT).show();
            }
            
        };
        
        Subscription mySubscription1 = myObservable.subscribe(myAction1);
        
        mySubscription1.unsubscribe();
        
        Observable.just("1", "2", "3", "4", "5")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(myObserver);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                final Handler handler = new Handler();
                Observable.just("6", "7", "8", "9", "10")
                    .subscribeOn(Schedulers.newThread())
                    // 如果是AndroidSchedulers.mainThread()，则1~10；HandlerScheduler.from(handler)，6~10 1~5 parallel
                    .observeOn(HandlerScheduler.from(handler))
                    .subscribe(myObserver);
                Looper.loop();
            }
        }, "custom-thread").start();
        
    }

    @Override
    public void onClick(View v) {
        if (R.id.btn_rxjava == v.getId()) {
            Toast.makeText(this, "rxjava", Toast.LENGTH_SHORT).show();
            new SleepTask().execute(); 
           
            basicOb();
           
            onRunSchedulerExampleButtonClicked();
            
        }
    }
    
    private class SleepTask extends AsyncTask<String, String, Void> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RxjavaActivity.this, "sleep start...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... params) {
            
            try {
                new Thread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            return null;
        }
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(RxjavaActivity.this, "sleep end", Toast.LENGTH_SHORT).show();
        }
    }
    
    void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
            // Run on a background thread
            .subscribeOn(HandlerScheduler.from(backgroundHandler))
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "onCompleted()");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError()", e);
                }

                @Override
                public void onNext(String string) {
                    Log.d(TAG, "onNext(" + string + ")");
                }
            });
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    throw OnErrorThrowable.from(e);
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }
    
    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("rxjava background", THREAD_PRIORITY_BACKGROUND);
        }
    }

}

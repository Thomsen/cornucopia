/**
 *  Copyright (c) 2012 The Anyuaning Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cornucopia.ui.list;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cornucopia.R;

/**
 * TODO
 * @author Thomsen
 * @version 1.0
 * @since Jan 22, 2013 9:11:46 PM
 */
public class PullPushRefreshDemo extends Activity {

    private PullPushListView mPullPushListView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        
        setContentView(R.layout.activity_refresh);
        
        mPullPushListView = (PullPushListView) findViewById(R.id.pull_push_listview);

        mPullPushListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 25;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Object getItem(int i) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public long getItemId(int i) {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                TextView tv = null;
                if (view == null) {
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.list_content, null);
                    tv = (TextView) view.findViewById(R.id.content);
                    view.setTag(tv);
                } else {
                    tv = (TextView) view.getTag();
                }
                tv.setText("view test");

                return view;
            }
        });
//        pullPushListView.setOnRefreshListener(new PullPushListView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Toast.makeText(getApplicationContext(), "heh", Toast.LENGTH_SHORT).show();
//            }
//        });

        mPullPushListView.setOnPageRefreshListener(new PullPushListView.OnPageRefreshListener() {
            @Override
            public void pullRefresh() {
                Toast.makeText(getApplicationContext(), "pull", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void pushRefresh() {
                Toast.makeText(getApplicationContext(), "push", Toast.LENGTH_SHORT).show();
            }
        });

        mPullPushListView.setOnRefreshListener(new PullPushListView.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		});
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                ;
            }
            
            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        	mPullPushListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}

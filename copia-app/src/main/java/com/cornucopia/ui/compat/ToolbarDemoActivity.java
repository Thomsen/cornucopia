package com.cornucopia.ui.compat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cornucopia.R;

public class ToolbarDemoActivity extends ActionBarActivity {


    private Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toolbar_demo);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("Toolbar");

        setSupportActionBar(mToolbar);

        // must behind setSupportActionBar
        mToolbar.setNavigationIcon(android.R.drawable.ic_media_previous);
        mToolbar.setOnMenuItemClickListener(onMenuItemClick);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_take_picture) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.menu_take_picture) {
                Toast.makeText(ToolbarDemoActivity.this, "take camera", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    };

}

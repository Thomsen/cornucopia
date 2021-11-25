package com.cornucopia.spi;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cornucopia.R;

public class DisplayActivity extends AppCompatActivity {

    private TextView mTextView;

    private Button mButton;

    StringBuffer mDisplayBuffer = new StringBuffer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);

        mTextView = findViewById(R.id.textView2);
        mButton = findViewById(R.id.button3);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadModule();
            }
        });
    }

    private void loadModule() {

        mButton.setClickable(false);

        while (DisplayFactory.getSingleton().hasNextDisplay()) {
            mDisplayBuffer.append(DisplayFactory.getSingleton().getDisplay().message());
            mDisplayBuffer.append("\n");
        }

        mTextView.setText(mDisplayBuffer.toString());
    }
}

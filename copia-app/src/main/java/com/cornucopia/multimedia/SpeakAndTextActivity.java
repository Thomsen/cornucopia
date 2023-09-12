package com.cornucopia.multimedia;

import java.util.List;
import java.util.Locale;

import com.cornucopia.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SpeakAndTextActivity extends Activity implements OnClickListener {

	EditText mEditText;
	Button mButtonSTT;
	Button mButtonTTS;

	TextToSpeech mTextToSpeech;

	private static final int STT_RECOGNITION = 0x00000000;
	private static final int TTS_RECOGNITION = 0x00000010;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.multimedia_stts);

		setupViews();

		initTTSRecognition();

	}

	private void setupViews() {
		mEditText = (EditText) findViewById(R.id.multimedia_content);

		mButtonSTT = (Button) findViewById(R.id.multimedia_stt);
		mButtonTTS = (Button) findViewById(R.id.multimedia_tts);

		mButtonSTT.setOnClickListener(this);
		mButtonTTS.setOnClickListener(this);

		PackageManager pm = getPackageManager();
		List activityList = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

		if (activityList.size() != 0) {
			mButtonSTT.setEnabled(true);
		} else {
			mButtonSTT.setEnabled(false);
		}

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.multimedia_stt) {
				startSTTRecognition();
			}
		if (v.getId() == R.id.multimedia_tts) {
			startTTSRecognition();
		}

	}

	private void startTTSRecognition() {

		mTextToSpeech.speak(mEditText.getText().toString(),
				TextToSpeech.QUEUE_ADD, null);

	}

	private void startSTTRecognition() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		startActivityForResult(intent, STT_RECOGNITION);

		mTextToSpeech.setLanguage(Locale.US);

	}

	private void initTTSRecognition() {
		Intent intent = new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(intent, TTS_RECOGNITION);

		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == STT_RECOGNITION) {

			switch (resultCode) {
			case RESULT_OK: {
				// TODO use adapter to select
				mEditText.setText(data.getStringArrayListExtra(
						RecognizerIntent.EXTRA_RESULTS).toString());
				break;
			}
			}

		} else if (requestCode == TTS_RECOGNITION) {
			switch (resultCode) {
			case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS: {
				mTextToSpeech = new TextToSpeech(this, new OnInitListener() {

					@Override
					public void onInit(int status) {
						if (status == TextToSpeech.SUCCESS) {
							// replace Locale.US
							int result = mTextToSpeech.setLanguage(Locale.US);
							if (result == TextToSpeech.LANG_MISSING_DATA
									|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
								mButtonTTS.setEnabled(false);
								
								// TODO setting language
								mTextToSpeech.setLanguage(Locale.US);
							} else {
								mButtonTTS.setEnabled(true);
							}
						}
					}

				});
				break;
			}
			}
		}
	}
}

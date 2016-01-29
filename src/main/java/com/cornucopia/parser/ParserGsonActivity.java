package com.cornucopia.parser;

import com.cornucopia.R;
import com.cornucopia.parser.entry.Cat;
import com.cornucopia.parser.entry.Dog;
import com.cornucopia.parser.entry.IAnimal;
import com.cornucopia.parser.gson.GsonBuilderUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParserGsonActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "ParserDemoActivity";
	
	private IAnimal[] mAnimal;
	
	private String[] mAnimalStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_parser);
		
		
		Button btnGsonSe = (Button) findViewById(R.id.btn_gson_serializer);
		Button btnGsonDe = (Button) findViewById(R.id.btn_gson_deserializer);
		
		btnGsonSe.setOnClickListener(this);
		btnGsonDe.setOnClickListener(this);
		
		mAnimal = new IAnimal[] {
				new Cat("Kitty"),
				new Dog("Brutus", 5)
		};
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_gson_serializer) {
			gsonSerializer();
		}
		if (v.getId() == R.id.btn_gson_deserializer) {
			gsonDeserializer();
		}
	}

	private void gsonSerializer() {
		
		if (null != mAnimal) {
			int len = mAnimal.length;
			mAnimalStr  = new String[len];
			String aniStr = null;
			for (int i=0; i<len; i++) {
//				aniStr = GsonBuilderUtil.createAminalGson().toJson(mAnimal[i], IAnimal.class);
//				// serialized: {"CLASSNAME":"com.cornucopia.parser.entry.Cat","INSTANCE":{"name":"Kitty"}}
//				// serialized: {"CLASSNAME":"com.cornucopia.parser.entry.Dog","INSTANCE":{"name":"Brutus","ferocity":5}}
				
				aniStr = GsonBuilderUtil.createAnimalHierarchyGson().toJson(mAnimal[i]);
				// serialized: "Kitty"
				// serialized: "Brutus"	
				
				mAnimalStr[i] = aniStr;
				Log.i(TAG, "serialized: " + aniStr);
			}
		}
		
	}

	private void gsonDeserializer() {
		if (null != mAnimalStr) {
			IAnimal ani;
			for (String aniStr : mAnimalStr) {
//				ani = GsonBuilderUtil.createAminalGson().fromJson(aniStr, IAnimal.class);
//				// deserialized: Kitty:"meaow"
//				// deserialized: Brutus:"brak" { ferocity level:  }
				
				ani = GsonBuilderUtil.createAnimalHierarchyGson().fromJson(aniStr, IAnimal.class);
				
				Log.i(TAG, "deserialized: " + ani.sound());
			}
		}
	}
	
	

}

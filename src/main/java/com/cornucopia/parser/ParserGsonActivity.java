package com.cornucopia.parser;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.cornucopia.R;
import com.cornucopia.parser.entry.Cat;
import com.cornucopia.parser.entry.Dog;
import com.cornucopia.parser.entry.IAnimal;
import com.cornucopia.parser.gson.GsonBuilderUtil;
import com.cornucopia.parser.jaskson.JasksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	
	private String mJsonStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_parser);
		
		
		Button btnGsonSe = (Button) findViewById(R.id.btn_gson_serializer);
		Button btnGsonDe = (Button) findViewById(R.id.btn_gson_deserializer);
		
		btnGsonSe.setOnClickListener(this);
		btnGsonDe.setOnClickListener(this);
		
		Button btnJasksonSe = (Button) findViewById(R.id.btn_jaskson_serializer);
		Button btnJasksonDe = (Button) findViewById(R.id.btn_jaskson_deserializer);
		
		btnJasksonSe.setOnClickListener(this);
		btnJasksonDe.setOnClickListener(this);
		
		Button btnFastjsonSe = (Button) findViewById(R.id.btn_fastjson_serializer);
		Button btnFastjsonDe = (Button) findViewById(R.id.btn_fastjson_deserializer);
		
		btnFastjsonSe.setOnClickListener(this);
		btnFastjsonDe.setOnClickListener(this);
		
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
		if (v.getId() == R.id.btn_jaskson_serializer) {
			jasksonSerializer();
		}
		if (v.getId() == R.id.btn_jaskson_deserializer) {
			jasksonDeserializer();
		}
		if (v.getId() == R.id.btn_fastjson_serializer) {
			fastjsonSerializer();
		}
		if (v.getId() == R.id.btn_fastjson_deserializer) {
			fastjsonDeserializer();
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
				
				aniStr = GsonBuilderUtil.createAnimalHierarchyGson().toJson(mAnimal[i], IAnimal.class);
				// serialized: "Kitty"
				// serialized: "Brutus"  // no IAnimal.class
				
				// serialized: {"CLASSNAME":"com.cornucopia.parser.entry.Cat","INSTANCE":"Kitty"}
				// serialized: {"CLASSNAME":"com.cornucopia.parser.entry.Dog","INSTANCE":{"name":"Brutus","ferocity":5}}
				
				mAnimalStr[i] = aniStr;
				Log.i(TAG, "gson serialized: " + aniStr);
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
				
				// deserialized: Kitty:"meaow"
				// deserialized: Brutus:"brak" { ferocity level: 5 }
				
				Log.i(TAG, "gson deserialized: " + ani.sound());
			}
		}
	}
	

	private void jasksonSerializer() {
		Dog dog = new Dog("Brutus", 5);
		try {
			mJsonStr = JasksonUtil.getMapper().writeValueAsString(dog);
			Log.i(TAG, "jaskson serialized: " + mJsonStr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	private void jasksonDeserializer() {
		if (null != mJsonStr) {
			try {
				Dog dog = JasksonUtil.getMapper().readValue(mJsonStr, Dog.class);
				Log.i(TAG, "jaskson deserialized: " + dog.sound());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void fastjsonSerializer() {
		Cat cat = new Cat("Kitty");
		mJsonStr = JSON.toJSONString(cat);
		Log.i(TAG, "fastJson serialized: " + mJsonStr);
	}

	private void fastjsonDeserializer() {
		if (null != mJsonStr) {
			Cat cat = JSON.parseObject(mJsonStr, Cat.class);
			Log.i(TAG, "fastjson deserialized: " + cat.sound());
		}
	}

}

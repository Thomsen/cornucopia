package com.cornucopia.storage.contacts;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Contacts.People;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.widget.Toast;

public class AddContacts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Toast.makeText(this, "Add Contacts", Toast.LENGTH_SHORT).show();
		
		// 存储数据为了Content Resolver
		ContentValues values = new ContentValues();
		
		// 2.1以上
		Uri uri = getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(uri);
		
		values.clear();
        values.put(Data.RAW_CONTACT_ID, rawContactId);
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
        values.put(StructuredName.GIVEN_NAME, "Thomsen Wang");
        // 添加后手动删除不掉
        getContentResolver().insert(
                android.provider.ContactsContract.Data.CONTENT_URI, values);
        // 下面的添加不成功
//		getContentResolver().insert(ContactsContract.Contacts.CONTENT_URI, values);
		
		// 2.1以下
//		values.put(People.NAME, "Thomsen Wang");
//		
//		Uri uri = getContentResolver().insert(People.CONTENT_URI, values);
		
	}
	

}

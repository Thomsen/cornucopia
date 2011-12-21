package contentprovider;

import com.csit.cornucopia.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

public class FetchContacts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		ContentResolver contentResolver = getContentResolver();
		
		// andriod 2.0以下
//		Cursor cursor = contentResolver.query(People.CONTENT_URI, null, null, null, null);
		
		// android 2.0以上
		Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				
//				String id = cursor.getString(cursor.getColumnIndex(People._ID));
//				String name = cursor.getString(cursor.getColumnIndex(People.NAME));
				
				String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				Toast.makeText(this, "id: " + id + "\n" + "name: " + name, Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
		}
	}

}

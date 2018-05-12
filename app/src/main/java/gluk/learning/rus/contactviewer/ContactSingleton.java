package gluk.learning.rus.contactviewer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ContactSingleton {
    private static ContactSingleton sContactSingleton;
    Cursor mCursor;

    public static ContactSingleton get(Context context) {
        if (sContactSingleton == null) {
            sContactSingleton = new ContactSingleton(context);
        }
        return sContactSingleton;
    }

    public ContactSingleton(Context context) {
        ContentResolver contentResolver = context.
                getApplicationContext().getContentResolver();
        mCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
    }

    public Cursor getCursor() {
        return mCursor;
    }

}

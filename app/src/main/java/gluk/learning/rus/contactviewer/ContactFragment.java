package gluk.learning.rus.contactviewer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactFragment extends Fragment {
    private TextView mContactName;
    private TextView mContactNumber;
    private TextView mContactMail;
    int mIDContact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIDContact = (int) getActivity().getIntent().getSerializableExtra(MainActivity.EXTRA_CONTACT_ID);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ContentResolver contentResolver = getActivity().getApplicationContext().
                getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
        if (!cursor.moveToPosition(mIDContact)) { // mCursor maybe null?
            return inflater.inflate(R.layout.fragment_not_found_contact, container, false);
        }
        View v = inflater.inflate(R.layout.fragment_fullcontact, container, false);
        mContactName = v.findViewById(R.id.viewContactName);
        mContactNumber = v.findViewById(R.id.viewContactNumber);
        mContactMail = v.findViewById(R.id.viewContactMail);

        mContactName.setText(
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        );
        mContactNumber.setText(
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        );

        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor cur1 = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);

        if (cur1.moveToFirst()) {
            mContactMail.setText(
                    cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
        } else {
            mContactMail.setText("Emprty " + cur1.getCount());
        }
        cur1.close();
        cursor.close();
        return v;
    }
}

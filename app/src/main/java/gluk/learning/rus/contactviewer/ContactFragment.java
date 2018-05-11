package gluk.learning.rus.contactviewer;

import android.content.ContentResolver;
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
    private Contact mContact;
    private TextView mContactName;
    private TextView mContactNumber;
    private TextView mContactMail;
    private Cursor mCursor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContact = new Contact();
        ContentResolver contentResolver = getActivity().getApplicationContext().
                getApplicationContext().getContentResolver();
        mCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fullcontact, container, false);
        mContactName = v.findViewById(R.id.viewContactName);
        mContactNumber = v.findViewById(R.id.viewContactNumber);
        mContactMail = v.findViewById(R.id.viewContactMail);

        if (mCursor.isBeforeFirst()) {
            if (!mCursor.moveToNext()) {
                return inflater.inflate(R.layout.fragment_not_found_contact, container, false);
            }
        }

        mContactName.setText(
                mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        );
        mContactNumber.setText(
                mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        );

        ContentResolver contentResolver = getActivity().getApplicationContext().
                getApplicationContext().getContentResolver();
        String id = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
        Cursor cur1 = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);

        if (cur1 != null) if (cur1.moveToNext()) {
            mContactMail.setText(
                    cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
            cur1.close();
        }

        return v;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void setCursor(Cursor cursor) {
        mCursor = cursor;
    }
}

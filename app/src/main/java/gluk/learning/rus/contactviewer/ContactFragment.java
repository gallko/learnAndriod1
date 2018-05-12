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
    private TextView mContactName;
    private TextView mContactNumber;
    private TextView mContactMail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Cursor mCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        if (mCursor.isBeforeFirst()) { // mCursor maybe null?
            int x = 1; //savedInstanceState.getInt(MainActivity.KEY_INDEX);
            if (!mCursor.move(x)) {
                mCursor.close();
                return inflater.inflate(R.layout.fragment_not_found_contact, container, false);
            }
        }

        View v = inflater.inflate(R.layout.fragment_fullcontact, container, false);
        mContactName = v.findViewById(R.id.viewContactName);
        mContactNumber = v.findViewById(R.id.viewContactNumber);
        mContactMail = v.findViewById(R.id.viewContactMail);

        mContactName.setText(
                mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
        );
        mContactNumber.setText(
                mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
        );

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
        mCursor.close();
        return v;
    }
}

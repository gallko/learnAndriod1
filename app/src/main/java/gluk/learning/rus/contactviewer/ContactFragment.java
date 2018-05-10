package gluk.learning.rus.contactviewer;

import android.os.Bundle;
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContact = new Contact();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fullcontact, container, false);

        mContactName = v.findViewById(R.id.viewContactName);
        mContactName.setText(mContact.getName());

        mContactNumber = v.findViewById(R.id.viewContactNumber);
        mContactNumber.setText(mContact.getNumber());

        mContactMail = v.findViewById(R.id.viewContactMail);
        mContactMail.setText(mContact.getMail());


        return v;
    }
}

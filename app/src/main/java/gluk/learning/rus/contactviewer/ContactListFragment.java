package gluk.learning.rus.contactviewer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.security.auth.callback.Callback;

public class ContactListFragment extends Fragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mAdapter;
    private Callbacks mCallback;

    public interface Callbacks {
        void onContactSelected(int ID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ContactSingleton contactSingleton = ContactSingleton.get(getActivity());
        Cursor cursor = contactSingleton.getCursor();
        if (cursor.getCount() == 0) {
            return inflater.inflate(R.layout.fragment_not_found_contact, container, false);
        }
        View view = inflater.inflate(R.layout.fragment_contact_list, container,
                false);
        mContactRecyclerView = view.findViewById(R.id.contact_recycler_view);
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    private void updateUI() {
        ContactSingleton contactSingleton = ContactSingleton.get(getActivity());
        Cursor cursor = contactSingleton.getCursor();
        mAdapter = new ContactAdapter(cursor);
        mContactRecyclerView.setAdapter(mAdapter);
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mNumberTextView;
        private int mIDContact;

        public ContactHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_contact, parent, false));
            mNameTextView = itemView.findViewById(R.id.contact_name);
            mNumberTextView = itemView.findViewById(R.id.contact_number);
            itemView.setOnClickListener(this);
        }

        public void bind(Cursor cursor) {
            mIDContact = cursor.getPosition();
            mNameTextView.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            mNumberTextView.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        @Override
        public void onClick(View v) {
//            Intent intent = MainActivity.newIntent(getActivity(), mIDContact);
//            startActivity(intent);
            mCallback.onContactSelected(mIDContact);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {
        Cursor mCursor;

        public ContactAdapter(Cursor cursor) {
            mCursor = cursor;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ContactHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            mCursor.moveToPosition(position);
            holder.bind(mCursor);
        }

        @Override
        public int getItemCount() {
            return mCursor.getCount();
        }
    }


}

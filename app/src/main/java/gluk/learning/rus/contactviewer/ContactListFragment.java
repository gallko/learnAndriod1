package gluk.learning.rus.contactviewer;

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

public class ContactListFragment extends Fragment {
    private RecyclerView mContactRecyclerView;
    private ContactAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

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

    private class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mNumberTextView;
        private Cursor mCursor;

        public ContactHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_contact, parent, false));
            mNameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            mNumberTextView = (TextView) itemView.findViewById(R.id.contact_number);
        }

        public void bind(Cursor cursor) {
            mCursor = cursor;
            mNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            mNumberTextView.setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
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

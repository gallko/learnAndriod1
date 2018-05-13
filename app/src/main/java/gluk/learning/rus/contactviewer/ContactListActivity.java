package gluk.learning.rus.contactviewer;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class ContactListActivity
        extends SingleFragmentActivity
        implements ContactListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onContactSelected(int ID) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = MainActivity.newIntent(this, ID);
            startActivity(intent);
        } else {
            Fragment newDetail = ContactFragment.newInstance(ID);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }
}

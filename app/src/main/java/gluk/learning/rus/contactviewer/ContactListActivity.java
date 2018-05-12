package gluk.learning.rus.contactviewer;

import android.support.v4.app.Fragment;

public class ContactListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }
}

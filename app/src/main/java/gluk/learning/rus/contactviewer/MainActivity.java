package gluk.learning.rus.contactviewer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {
    public static final String EXTRA_CONTACT_ID =
            "gluk.learning.rus.contactviewer.contact_id";

    @Override
    protected Fragment createFragment() {
        return new ContactFragment();
    }

    public static Intent newIntent(Context packageContext, int contactID) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactID);
        return intent;
    }
}

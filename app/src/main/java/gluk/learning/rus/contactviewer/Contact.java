package gluk.learning.rus.contactviewer;

import android.net.Uri;

import java.util.UUID;

public class Contact {
    private UUID mId;
    private String mName;
    private String mNumber;
    private String mMail;

    public Contact() {
        mId = UUID.randomUUID();
        mName = "Rus";
        mNumber = "89991893661";
        mMail = "thgall@mail.ru";
        Uri uri = Uri.parse("content://contacts");
        String s = "sdddd";
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getMail() {
        return mMail;
    }
}

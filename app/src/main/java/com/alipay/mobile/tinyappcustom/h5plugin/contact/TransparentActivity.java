package com.alipay.mobile.tinyappcustom.h5plugin.contact;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import com.alipay.mobile.a.a.a.b;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcustom.h5plugin.ContactAccount;

public class TransparentActivity extends BaseActivity {
    public static final int ContactsRequestCode = 3;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT != 26) {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
        setContentView(b.transparent_layout);
        H5Log.d("TransparentActivity", "onCreate: ");
        a();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void a() {
        try {
            startActivityForResult(new Intent("android.intent.action.PICK", Phone.CONTENT_URI), 3);
        } catch (Exception e) {
            H5Log.e("TransparentActivity", "goToContactListActivity", e);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
        H5Log.d("TransparentActivity", "onActivityResult");
        if (resultCode != -1) {
            ContactService.setResultAccount(null);
        } else if (requestCode == 3) {
            try {
                a(data.getData());
            } catch (SecurityException e) {
                ContactService.setResultAccount(new ContactAccount());
            } catch (Exception e2) {
                ContactService.setResultAccount(new ContactAccount());
            }
        }
    }

    private void a(Uri data) {
        ContactAccount contact = new ContactAccount();
        if (data != null) {
            Cursor phoneCursor = getContentResolver().query(data, new String[]{"data1", "display_name"}, null, null, null);
            if (phoneCursor != null) {
                try {
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex("data1"));
                        String name = phoneCursor.getString(phoneCursor.getColumnIndex("display_name"));
                        if (phoneNumber != null) {
                            contact.name = name;
                            contact.phoneNumber = phoneNumber;
                        }
                    }
                } catch (Exception e) {
                    if (phoneCursor != null) {
                        phoneCursor.close();
                    }
                }
            }
        }
        ContactService.setResultAccount(contact);
    }
}

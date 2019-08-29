package com.alipay.mobile.tinyappcustom.h5plugin.contact;

import android.content.Intent;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.ContactAccount;
import com.autonavi.map.core.MapCustomizeManager;

public class ContactService {
    private static ContactPickerCallback a;

    public static void setResultAccount(ContactAccount account) {
        if (a != null) {
            a.onAccountReturned(account);
        }
        a = null;
    }

    public static void pickFromContactsList(ContactPickerCallback callback) {
        H5Log.d("ContactService");
        a = callback;
        Intent intent = new Intent(TinyappUtils.getMicroApplicationContext().getApplicationContext(), TransparentActivity.class);
        intent.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        TinyappUtils.getMicroApplicationContext().startActivity((MicroApplication) TinyappUtils.getMicroApplicationContext().getTopApplication(), intent);
    }
}

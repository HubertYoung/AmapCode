package com.alibaba.wireless.security.open.securityguardaccsadapter;

import android.content.Context;
import com.taobao.accs.client.GlobalClientInfo;

public class AccsAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "AccsAdapter";
    public static Context gContext;

    public static void registerListner(Context context) {
        if (context != null) {
            gContext = context;
            GlobalClientInfo.getInstance(context).registerListener("securityguard_accs_service", new AccsListner());
        }
    }
}

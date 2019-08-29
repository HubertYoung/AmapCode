package com.taobao.agoo;

import android.content.Intent;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;

public class DefaultMeizuMsgParseImpl implements INotifyListener {
    private static final String TAG = "DefaultMeizuMsgParseImpl";

    public String getMsgSource() {
        return DeviceProperty.ALIAS_MEIZU;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str;
        Throwable th;
        if (intent == null) {
            ALog.e(TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            str = intent.getStringExtra("meizu_payload");
            try {
                ALog.i(TAG, "parseMsgFromIntent", "msg", str);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            str = null;
            ALog.e(TAG, "parseMsgFromIntent", th, new Object[0]);
            return str;
        }
        return str;
    }
}

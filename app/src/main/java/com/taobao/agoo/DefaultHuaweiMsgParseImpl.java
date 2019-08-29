package com.taobao.agoo;

import android.content.Intent;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;

public class DefaultHuaweiMsgParseImpl implements INotifyListener {
    private static final String TAG = "DefaultHuaweiMsgParseImpl";

    public String getMsgSource() {
        return DeviceProperty.ALIAS_HUAWEI;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str;
        if (intent == null) {
            ALog.e(TAG, "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            str = intent.getStringExtra("extras");
        } catch (Throwable th) {
            ALog.e(TAG, "parseMsgFromIntent", th, new Object[0]);
            str = null;
        }
        return str;
    }
}

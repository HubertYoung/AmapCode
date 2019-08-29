package org.android.agoo.oppo;

import android.content.Intent;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;

public class OppoMsgParseImpl implements INotifyListener {
    public String getMsgSource() {
        return DeviceProperty.ALIAS_OPPO;
    }

    public String parseMsgFromIntent(Intent intent) {
        String str;
        Throwable th;
        if (intent == null) {
            ALog.e("OppoMsgParseImpl", "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            str = intent.getStringExtra("oppo_payload");
            try {
                ALog.i("OppoMsgParseImpl", "parseMsgFromIntent", "msg", str);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            str = null;
            ALog.e("OppoMsgParseImpl", "parseMsgFromIntent", th, new Object[0]);
            return str;
        }
        return str;
    }
}

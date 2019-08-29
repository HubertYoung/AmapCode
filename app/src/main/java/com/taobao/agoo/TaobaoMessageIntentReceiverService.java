package com.taobao.agoo;

import android.content.Context;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.utl.ALog;
import org.android.agoo.message.MessageReceiverService;

public class TaobaoMessageIntentReceiverService extends MessageReceiverService {
    public String getIntentServiceClassName(Context context) {
        StringBuilder sb = new StringBuilder("getPackage Name=");
        sb.append(context.getPackageName());
        ALog.w("Taobao", sb.toString(), new Object[0]);
        return AdapterGlobalClientInfo.getAgooCustomServiceName(context.getPackageName());
    }
}

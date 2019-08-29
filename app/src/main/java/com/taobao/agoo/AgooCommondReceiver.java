package com.taobao.agoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.dispatch.IntentDispatch;

public class AgooCommondReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            intent.setClassName(context, AdapterGlobalClientInfo.getAgooCustomServiceName(context.getPackageName()));
            IntentDispatch.dispatchIntent(context, intent, true);
        } catch (Throwable unused) {
        }
    }
}

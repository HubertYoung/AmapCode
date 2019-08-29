package com.alipay.mobile.tinyappcommon.a;

import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.alipay.mobile.liteprocess.LiteProcess;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.liteprocess.ipc.IpcMsgClient;
import com.alipay.mobile.liteprocess.ipc.IpcMsgServer;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.List;

/* compiled from: IPCUtils */
public class c {
    private static final String a = c.class.getSimpleName();

    public static void a(int what, String appId, Bundle data) {
        try {
            if (!data.isEmpty() && !TextUtils.isEmpty(appId)) {
                Bundle bundle = new Bundle(data);
                long time = System.currentTimeMillis();
                H5Log.d(a, "sendDataToLiteProcess...appId " + appId + ", what=" + what + ",logTag=" + time);
                List liteProcessList = LiteProcessApi.getAllAliveProcess();
                if (liteProcessList == null || liteProcessList.isEmpty()) {
                    H5Log.d(a, "sendDataToLiteProcess...all alive process is empty, appId " + appId);
                    return;
                }
                bundle.putLong("logTag", time);
                for (LiteProcess liteProcess : liteProcessList) {
                    if (appId.equals(liteProcess.getAppId())) {
                        a(liteProcess, what, bundle);
                    }
                }
            }
        } catch (Throwable throwable) {
            H5Log.e(a, "sendDataToLiteProcess...e=" + throwable);
        }
    }

    public static void a(String appId, String key, String value) {
        if (!TextUtils.isEmpty(key)) {
            Bundle data = new Bundle();
            data.putString(key, value);
            a(1, appId, data);
        }
    }

    public static void a(Bundle data) {
        if (data != null) {
            List<LiteProcess> allLiteProcessList = LiteProcessApi.getAllAliveProcess();
            if (allLiteProcessList != null) {
                long time = System.currentTimeMillis();
                H5Log.d(a, "sendDataToAllLiteProcess..." + allLiteProcessList.size() + ",logTag=" + time);
                Bundle bundle = new Bundle(data);
                bundle.putLong("logTag", time);
                for (LiteProcess a2 : allLiteProcessList) {
                    a(a2, 1, bundle);
                }
            }
        }
    }

    private static void a(LiteProcess liteProcess, int what, Bundle data) {
        if (liteProcess != null) {
            Message message = Message.obtain();
            message.what = what;
            message.setData(data);
            IpcMsgServer.reply(liteProcess.getReplyTo(), "TINY_APP_BIZ", message);
        }
    }

    public static void a(int what, Bundle bundle) {
        Bundle data;
        if (bundle == null) {
            data = new Bundle();
        } else {
            data = new Bundle(bundle);
        }
        long time = System.currentTimeMillis();
        data.putLong("logTag", time);
        H5Log.d(a, "sendDataToMainProcess...logTag:" + time);
        Message message = Message.obtain();
        message.what = what;
        message.setData(data);
        IpcMsgClient.send("TINY_APP_BIZ", message);
    }

    public static void a(Messenger replyTo, int what, Bundle data) {
        Bundle bundle;
        if (replyTo != null) {
            if (data == null) {
                bundle = new Bundle();
            } else {
                bundle = new Bundle(data);
            }
            long time = System.currentTimeMillis();
            bundle.putLong("logTag", time);
            H5Log.d(a, "replyDataToLiteProcess...logTag:" + time);
            Message message = Message.obtain();
            message.what = what;
            message.setData(bundle);
            IpcMsgServer.reply(replyTo, "TINY_APP_BIZ", message);
        }
    }
}

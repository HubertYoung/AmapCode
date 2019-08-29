package com.alipay.mobile.tinyappcommon.a;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.liteprocess.LiteProcess;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.storage.H5SharedPreferenceStorage;
import com.alipay.mobile.tinyappcommon.utils.ipc.TinyAppIpcTask;
import com.alipay.mobile.tinyappcommon.utils.ipc.TinyAppIpcUtils;
import java.util.List;

/* compiled from: TinyAppHandler */
public class e extends Handler {
    private static e a;

    private e() {
    }

    public static e a() {
        if (a == null) {
            synchronized (e.class) {
                try {
                    if (a == null) {
                        a = new e();
                    }
                }
            }
        }
        return a;
    }

    public void handleMessage(Message msg) {
        if (msg != null) {
            Bundle data = msg.getData();
            if (data == null) {
                H5Log.d("TinyAppHandler", "handleMessage...data is null: " + msg.what);
                return;
            }
            long logTag = 0;
            try {
                logTag = data.getLong("logTag");
                data.remove("logTag");
            } catch (Throwable e) {
                H5Log.e((String) "TinyAppHandler", "handleMessage...e=" + e);
            }
            H5Log.d("TinyAppHandler", "handleMessage...what : " + msg.what + ",logTag: " + logTag);
            switch (msg.what) {
                case 1:
                    d(data);
                    return;
                case 2:
                    b(data);
                    return;
                case 4:
                    c(data);
                    return;
                case 5:
                    a(msg.replyTo);
                    return;
                case 6:
                    b(data, msg.replyTo);
                    return;
                case 7:
                    a(data, msg.replyTo);
                    return;
                case 8:
                    a(data);
                    return;
                default:
                    return;
            }
        }
    }

    private void a(final Bundle data, final Messenger messenger) {
        H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
            public final void run() {
                try {
                    boolean async = H5Utils.getBoolean(data, (String) TinyAppIpcUtils.KEY_IPC_ASYNC, false);
                    String taskClass = H5Utils.getString(data, (String) TinyAppIpcUtils.KEY_IPC_TASK);
                    if (!TextUtils.isEmpty(taskClass)) {
                        Object newInstance = Class.forName(taskClass).newInstance();
                        if (newInstance instanceof TinyAppIpcTask) {
                            TinyAppIpcTask task = (TinyAppIpcTask) newInstance;
                            task.setAsync(async);
                            if (async) {
                                task.setReplyMessenger(messenger);
                            }
                            task.setIpcId(data.getString(TinyAppIpcUtils.KEY_IPC_ID));
                            task.setCallFromLiteProcess(data.getBoolean(TinyAppIpcUtils.KEY_IPC_CALL_FROM_LITE_PROCESS));
                            task.setParam(JSON.parseObject(data.getString(TinyAppIpcUtils.KEY_IPC_PARAM)));
                            JSONObject result = task.run(task.getParam());
                            if (!async) {
                                task.setResult(result);
                                data.putString(TinyAppIpcUtils.KEY_IPC_RESULT, task.getResult() != null ? task.getResult().toString() : null);
                            }
                        }
                    }
                    if (!async) {
                        try {
                            c.a(messenger, 8, data);
                        } catch (Throwable e) {
                            H5Log.e((String) "TinyAppHandler", e);
                        }
                    }
                } catch (Throwable e2) {
                    H5Log.e((String) "TinyAppHandler", e2);
                }
            }
        });
    }

    private void a(final Bundle data) {
        H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
            public final void run() {
                String taskId = H5Utils.getString(data, (String) TinyAppIpcUtils.KEY_IPC_ID);
                if (!TextUtils.isEmpty(taskId)) {
                    TinyAppIpcTask task = TinyAppIpcUtils.removeIpcTaskFromCache(taskId);
                    if (task != null && task.getCallback() != null) {
                        try {
                            task.setResult(JSON.parseObject(data.getString(TinyAppIpcUtils.KEY_IPC_RESULT)));
                            try {
                                task.getCallback().callback(task.getResult());
                            } catch (Throwable e) {
                                H5Log.e((String) "TinyAppHandler", e);
                            }
                        } catch (Throwable e2) {
                            H5Log.e((String) "TinyAppHandler", e2);
                        }
                    }
                }
            }
        });
    }

    private static void b(Bundle data) {
        if (data != null) {
            for (String key : data.keySet()) {
                H5SharedPreferenceStorage.getInstance().removeCacheData(key);
            }
        }
    }

    private static void c(Bundle data) {
        H5SharedPreferenceStorage.getInstance().resetCache();
        d(data);
    }

    private static void d(Bundle data) {
        if (data != null) {
            for (String key : data.keySet()) {
                H5SharedPreferenceStorage.getInstance().updateCacheData(key, data.get(key));
            }
        }
    }

    private void a(final Messenger messenger) {
        if (messenger != null) {
            H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                public final void run() {
                    H5SharedPreferenceStorage.getInstance().initLoadStorage();
                    final Bundle replyData = H5SharedPreferenceStorage.getInstance().getAllCacheData();
                    H5Utils.runOnMain(new Runnable() {
                        public final void run() {
                            c.a(messenger, 4, replyData);
                        }
                    });
                }
            });
        }
    }

    private static void b(Bundle bundle, Messenger messenger) {
        String appId = bundle.getString("appId");
        bundle.remove("appId");
        if (bundle.isEmpty()) {
            H5Log.d("TinyAppHandler", "modifySpDataFromLite...bundle is empty: " + appId);
            return;
        }
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (value instanceof String) {
                H5SharedPreferenceStorage.getInstance().putString(appId, key, (String) value);
            } else if (value instanceof Integer) {
                H5SharedPreferenceStorage.getInstance().putInt(key, ((Integer) value).intValue(), true);
            }
            H5Log.d("TinyAppHandler", "modifySpDataFromLite..." + key);
        }
        if (TextUtils.isEmpty(appId)) {
            c.a(bundle);
            return;
        }
        List liteProcessList = LiteProcessApi.getAllAliveProcess();
        if (liteProcessList != null) {
            for (LiteProcess liteProcess : liteProcessList) {
                if (appId.equals(liteProcess.getAppId()) && messenger != liteProcess.getReplyTo()) {
                    H5Log.d("TinyAppHandler", "modifySpDataFromLite..notify other appId:" + appId);
                    c.a(liteProcess.getReplyTo(), 1, bundle);
                }
            }
        }
    }
}

package com.alipay.mobile.tinyappcommon.utils.ipc;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppLiteProcessService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import java.util.concurrent.ConcurrentHashMap;

public final class TinyAppIpcUtils {
    public static final String KEY_IPC_ASYNC = "IPC_ASYNC";
    public static final String KEY_IPC_CALL_FROM_LITE_PROCESS = "IPC_CALL_FROM_LP";
    public static final String KEY_IPC_ID = "IPC_ID";
    public static final String KEY_IPC_PARAM = "IPC_PARAM";
    public static final String KEY_IPC_RESULT = "IPC_RESULT";
    public static final String KEY_IPC_TASK = "IPC_TASK";
    private static final String TAG = "TinyAppIpcUtils";
    public static final int WHAT_TINY_APP_IPC_REQUEST = 7;
    public static final int WHAT_TINY_APP_IPC_RESPONSE = 8;
    private static volatile long sIdCount;
    /* access modifiers changed from: private */
    public static ConcurrentHashMap<String, TinyAppIpcTask> sTaskCache = new ConcurrentHashMap<>();

    public static boolean isLiteProcess() {
        TinyAppLiteProcessService service = TinyAppService.get().getLiteProcessService();
        if (service != null) {
            return service.isLiteProcess();
        }
        return false;
    }

    public static void runOnMainProcess(final TinyAppIpcTask task) {
        if (task != null) {
            Runnable subThreadTask = new Runnable() {
                public final void run() {
                    TinyAppLiteProcessService service = TinyAppService.get().getLiteProcessService();
                    if (service == null || !service.isLiteProcess()) {
                        boolean async = task.isAsync();
                        try {
                            JSONObject result = task.run(task.getParam());
                            if (!async) {
                                task.setResult(result);
                            }
                            if (!async) {
                                try {
                                    if (task.getCallback() != null) {
                                        task.replayedAsyncResultOnMain = true;
                                        task.getCallback().callback(task.getResult());
                                    }
                                } catch (Throwable e) {
                                    H5Log.e((String) TinyAppIpcUtils.TAG, e);
                                }
                            }
                        } catch (Throwable e2) {
                            H5Log.e((String) TinyAppIpcUtils.TAG, e2);
                        }
                    } else {
                        Bundle data = TinyAppIpcUtils.taskToBundleForCall(task, true);
                        String taskId = H5Utils.getString(data, (String) TinyAppIpcUtils.KEY_IPC_ID);
                        if (!TextUtils.isEmpty(taskId)) {
                            TinyAppIpcUtils.sTaskCache.put(taskId, task);
                        }
                        try {
                            service.sendDataToMainProcess(7, data);
                        } catch (Throwable th) {
                            TinyAppIpcUtils.sTaskCache.remove(taskId);
                        }
                    }
                }
            };
            if (H5Utils.isMain()) {
                H5Utils.runNotOnMain(H5ThreadType.IO, subThreadTask);
            } else {
                subThreadTask.run();
            }
        }
    }

    public static Bundle taskToBundleForCall(TinyAppIpcTask task) {
        return taskToBundleForCall(task, true);
    }

    public static Bundle taskToBundleForCall(TinyAppIpcTask task, boolean callFromLiteProcess) {
        String str = null;
        if (task == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        synchronized (TinyAppIpcUtils.class) {
            sIdCount++;
        }
        String ipcId = "7_" + sIdCount;
        task.setIpcId(ipcId);
        task.setCallFromLiteProcess(callFromLiteProcess);
        bundle.putString(KEY_IPC_ID, ipcId);
        if (task.getParam() != null) {
            str = task.getParam().toString();
        }
        bundle.putString(KEY_IPC_PARAM, str);
        bundle.putString(KEY_IPC_TASK, task.getClass().getName());
        bundle.putBoolean(KEY_IPC_ASYNC, task.isAsync());
        bundle.putBoolean(KEY_IPC_CALL_FROM_LITE_PROCESS, task.isCallFromLiteProcess());
        return bundle;
    }

    public static Bundle taskToBundleForReply(TinyAppIpcTask task) {
        String str = null;
        if (task == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IPC_ID, task.getIpcId());
        if (task.getResult() != null) {
            str = task.getResult().toString();
        }
        bundle.putString(KEY_IPC_RESULT, str);
        return bundle;
    }

    public static TinyAppIpcTask getIpcTaskFromCache(String taskId) {
        return sTaskCache.get(taskId);
    }

    public static TinyAppIpcTask removeIpcTaskFromCache(String taskId) {
        return sTaskCache.remove(taskId);
    }
}

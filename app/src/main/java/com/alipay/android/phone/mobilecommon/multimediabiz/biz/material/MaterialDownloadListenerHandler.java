package com.alipay.android.phone.mobilecommon.multimediabiz.biz.material;

import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCancelListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnProgressListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadCancel;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadError;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadProgress;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class MaterialDownloadListenerHandler {
    private static final Logger a = Logger.getLogger((String) "DLListenerHandler");
    private MaterialManager b;
    private Map<String, List<APOnProgressListener>> c = new HashMap();
    private Map<String, List<APOnCompleteListener>> d = new HashMap();
    private Map<String, List<APOnCancelListener>> e = new HashMap();
    private Map<String, List<APOnErrorListener>> f = new HashMap();

    public MaterialDownloadListenerHandler(MaterialManager mgr) {
        this.b = mgr;
        a.d("init MaterialDownloadListenerHandler, mMaterialManager: " + this.b, new Object[0]);
    }

    public void registerProgressListener(String taskId, APOnProgressListener listener) {
        a(taskId, this.c, listener);
    }

    public void unregisterProgressListener(String taskId, APOnProgressListener listener) {
        b(taskId, this.c, listener);
    }

    public void registerCompleteListener(String taskId, APOnCompleteListener listener) {
        a(taskId, this.d, listener);
    }

    public void unregisterCompleteListener(String taskId, APOnCompleteListener listener) {
        b(taskId, this.d, listener);
    }

    public void registerCancelListener(String taskId, APOnCancelListener listener) {
        a(taskId, this.e, listener);
    }

    public void unregisterCancelListener(String taskId, APOnCancelListener listener) {
        b(taskId, this.e, listener);
    }

    public void registerErrorListener(String taskId, APOnErrorListener listener) {
        a(taskId, this.f, listener);
    }

    public void unregisterErrorListener(String taskId, APOnErrorListener listener) {
        b(taskId, this.f, listener);
    }

    private static <T> void a(String key, Map<String, List<T>> map, T listener) {
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList());
        }
        map.get(key).add(listener);
    }

    private static <T> void b(String key, Map<String, List<T>> map, T listener) {
        if (map.containsKey(key)) {
            map.get(key).remove(listener);
        }
    }

    public void handleProgress(String taskId, APDownloadProgress progress) {
        if (this.c.containsKey(taskId)) {
            for (APOnProgressListener onProgress : new CopyOnWriteArrayList(this.c.get(taskId))) {
                onProgress.onProgress(progress);
            }
        }
    }

    public void handleComplete(String taskId, APDownloadComplete complete) {
        a.d("handleComplete taskId: " + taskId + ", complete: " + complete, new Object[0]);
        if (this.d.containsKey(taskId)) {
            for (APOnCompleteListener onComplete : new CopyOnWriteArrayList(this.d.get(taskId))) {
                onComplete.onComplete(complete);
            }
        }
    }

    public void handleCancel(String taskId, APDownloadCancel cancel) {
        a.d("handleCancel taskId: " + taskId + ", cancel: " + cancel, new Object[0]);
        if (this.e.containsKey(taskId)) {
            for (APOnCancelListener onCancel : new CopyOnWriteArrayList(this.e.get(taskId))) {
                onCancel.onCancel(cancel);
            }
        }
    }

    public void handleError(String taskId, APDownloadError error) {
        a.d("handleError taskId: " + taskId + ", error: " + error, new Object[0]);
        if (this.f.containsKey(taskId)) {
            for (APOnErrorListener onError : new CopyOnWriteArrayList(this.f.get(taskId))) {
                onError.onError(error);
            }
        }
    }
}

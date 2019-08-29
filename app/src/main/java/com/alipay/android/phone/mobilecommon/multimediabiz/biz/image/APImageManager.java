package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageUploadCallback;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager.APMultimediaTaskManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class APImageManager {
    public static final String IMAGE_TAG = APImageManager.class.getSimpleName();
    private static APImageManager a;
    private APMultimediaTaskManager b;
    private ConcurrentHashMap<String, Map> c = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Map> d = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> e = new ConcurrentHashMap<>();

    public APMultimediaTaskManager getTaskManager() {
        return this.b;
    }

    public void setTaskManager(APMultimediaTaskManager taskManager) {
        this.b = taskManager;
    }

    public void registUploadCallback(String taskId, APImageUploadCallback callBack) {
        if (taskId != null && callBack != null) {
            Map callBacks = this.c.get(taskId);
            if (callBacks == null) {
                callBacks = new ConcurrentHashMap();
            }
            callBacks.put(callBack, "");
            this.c.put(taskId, callBacks);
        }
    }

    public void registLoadCallback(String taskId, APImageDownLoadCallback callBack) {
        if (taskId != null && callBack != null) {
            Map callBacks = this.d.get(taskId);
            if (callBacks == null) {
                callBacks = new ConcurrentHashMap();
            }
            callBacks.put(callBack, "");
            this.d.put(taskId, callBacks);
        }
    }

    public void unregistUploadCallback(String taskId, APImageUploadCallback callBack) {
        Logger.P(IMAGE_TAG, "unregistUploadCallback " + taskId, new Object[0]);
        if (!TextUtils.isEmpty(taskId)) {
            if (callBack == null) {
                unregistUploadCallback(taskId);
                return;
            }
            Map callBacks = this.c.get(taskId);
            if (callBacks != null) {
                callBacks.remove(callBack);
                if (callBacks.isEmpty()) {
                    this.c.remove(taskId);
                }
                Logger.I(IMAGE_TAG, "unregistUploadCallback taskId: " + taskId + ", callbackSet: " + this.c.get(taskId), new Object[0]);
            }
        }
    }

    public void unregistUploadCallback(String taskId) {
        if (!TextUtils.isEmpty(taskId) && this.c != null) {
            Map callBacks = this.c.get(taskId);
            if (callBacks != null) {
                callBacks.clear();
            }
            this.c.remove(taskId);
        }
    }

    public void unregistLoadCallback(String taskId, APImageDownLoadCallback callBack) {
        Logger.P(IMAGE_TAG, "unregistLoadCallback " + taskId, new Object[0]);
        if (!TextUtils.isEmpty(taskId)) {
            if (callBack == null) {
                unregistLoadCallback(taskId);
                return;
            }
            Map callBacks = this.d.get(taskId);
            if (callBacks != null) {
                callBacks.remove(callBack);
                if (callBacks.isEmpty()) {
                    this.d.remove(taskId);
                }
                Logger.I(IMAGE_TAG, "unregistLoadCallback taskId: " + taskId + ", callbackSet: " + this.d.get(taskId), new Object[0]);
            }
        }
    }

    public void unregistLoadCallback(String taskId) {
        if (!TextUtils.isEmpty(taskId) && this.d != null) {
            Map callBacks = this.d.get(taskId);
            if (callBacks != null) {
                callBacks.clear();
            }
            this.d.remove(taskId);
        }
    }

    public Map<APImageUploadCallback, Object> getUpTaskCallback(String taskId) {
        if (!TextUtils.isEmpty(taskId)) {
            return this.c.get(taskId);
        }
        return null;
    }

    public Map<APImageDownLoadCallback, Object> getLoadTaskCallback(String taskId) {
        if (taskId != null) {
            return this.d.get(taskId);
        }
        return null;
    }

    public APMultimediaTaskModel getTaskStatusRecord(String taskId) {
        return this.b.getTaskRecord(taskId);
    }

    private APImageManager(Context context) {
        setTaskManager(APMultimediaTaskManager.getInstance(context));
    }

    public static APImageManager getInstance(Context context) {
        if (a == null) {
            synchronized (APImageManager.class) {
                try {
                    if (a == null) {
                        a = new APImageManager(context);
                    }
                }
            }
        }
        return a;
    }

    public void putLoadlingTaskTag(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            this.e.put(key, value);
        }
    }

    public String getLoadlingTaskTag(String key) {
        if (!TextUtils.isEmpty(key)) {
            return this.e.get(key);
        }
        return null;
    }

    public void removeLoadingTaskTag(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.e.remove(key);
        }
    }

    public boolean isUrlInNetTask(String key) {
        if (!TextUtils.isEmpty(key)) {
            return this.e.containsKey(key);
        }
        return false;
    }
}

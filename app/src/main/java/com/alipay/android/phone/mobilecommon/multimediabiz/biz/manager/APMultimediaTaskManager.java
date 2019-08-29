package com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ParamChecker;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.common.utils.MD5Util;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class APMultimediaTaskManager {
    private static APMultimediaTaskManager a;
    private Map<String, APMultimediaTaskModel> b = new ConcurrentHashMap();
    private Map<String, Map> c = new ConcurrentHashMap();

    private APMultimediaTaskManager(Context context) {
        ParamChecker.pmdCheck(context);
    }

    public static APMultimediaTaskManager getInstance(Context context) {
        if (a == null) {
            synchronized (APMultimediaTaskManager.class) {
                try {
                    if (a == null) {
                        a = new APMultimediaTaskManager(context);
                    }
                }
            }
        }
        return a;
    }

    public APMultimediaTaskModel getTaskRecord(String recordId) {
        try {
            return this.b.get(recordId);
        } catch (Exception e) {
            return null;
        }
    }

    public void removeTaskRecord(String recordId) {
        try {
            if (!TextUtils.isEmpty(recordId)) {
                this.b.remove(recordId);
            }
        } catch (Exception e) {
            ParamChecker.pmdCheck(e);
        }
    }

    public APMultimediaTaskModel getTaskRecordByCloudId(String cloudId) {
        if (cloudId == null) {
            return null;
        }
        APMultimediaTaskModel task = null;
        try {
            for (String taskId : this.b.keySet()) {
                task = this.b.get(taskId);
                if (task != null && cloudId.equals(task.getCloudId())) {
                    return task;
                }
            }
            return task;
        } catch (Exception e) {
            return null;
        }
    }

    public APMultimediaTaskModel updateTaskRecord(APMultimediaTaskModel taskRecord) {
        if (taskRecord != null) {
            try {
                if (!TextUtils.isEmpty(taskRecord.getTaskId())) {
                    this.b.put(taskRecord.getTaskId(), taskRecord);
                }
            } catch (Exception e) {
            }
        }
        return taskRecord;
    }

    public APMultimediaTaskModel delTaskRecord(String recordId) {
        try {
            if (this.b.containsKey(recordId)) {
                return this.b.remove(recordId);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public synchronized APMultimediaTaskModel addTaskRecord(APMultimediaTaskModel taskRecord) {
        if (taskRecord == null) {
            taskRecord = null;
        } else {
            if (TextUtils.isEmpty(taskRecord.getTaskId())) {
                taskRecord.setTaskId(a(taskRecord));
            }
            b(taskRecord);
        }
        return taskRecord;
    }

    private static String a(APMultimediaTaskModel task) {
        if (task == null) {
            return null;
        }
        return MD5Util.getMD5String(System.currentTimeMillis() + AUScreenAdaptTool.PREFIX_ID + task.hashCode());
    }

    private void b(APMultimediaTaskModel task) {
        this.b.put(task.getTaskId(), task);
    }

    public Map<Future, Object> getTaskFutureList(String taskId) {
        if (TextUtils.isEmpty(taskId)) {
            return null;
        }
        return this.c.get(taskId);
    }

    public void addTaskFuture(String taskId, Future future) {
        if (!TextUtils.isEmpty(taskId)) {
            Map futures = getTaskFutureList(taskId);
            if (futures == null) {
                Map futures2 = new ConcurrentHashMap();
                futures2.put(future, "");
                this.c.put(taskId, futures2);
                return;
            }
            futures.put(future, "");
        }
    }

    public void removeTaskFuture(String taskId) {
        if (!TextUtils.isEmpty(taskId)) {
            Map futures = getTaskFutureList(taskId);
            if (futures != null) {
                futures.clear();
            }
            this.c.remove(taskId);
        }
    }
}

package com.alipay.android.phone.mobilecommon.multimediabiz.biz.manager;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.DirConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbPersistence;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.common.utils.MD5Util;
import com.j256.ormlite.stmt.DeleteBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APFileTaskManager {
    private static final String a;
    /* access modifiers changed from: private */
    public static final Logger b;
    private static APFileTaskManager c;
    /* access modifiers changed from: private */
    public DbPersistence<APMultimediaTaskModel> d;
    private Context e;
    private ExecutorService f = Executors.newSingleThreadExecutor();
    private Map<String, APMultimediaTaskModel> g = new ConcurrentHashMap();

    static {
        String simpleName = APFileTaskManager.class.getSimpleName();
        a = simpleName;
        b = Logger.getLogger(simpleName);
    }

    private APFileTaskManager(Context context) {
        this.e = context;
        try {
            this.d = new MultiMediaTaskPersistence(this.e);
            b();
        } catch (Throwable e2) {
            b.e(e2, "APFileTaskManager init exception", new Object[0]);
        }
    }

    private void b() {
        this.f.submit(new Runnable() {
            public void run() {
                APFileTaskManager.this.c();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        APFileTaskManager.this.e();
                    }
                }, 15000);
            }
        });
    }

    public void deleteOutDateFile(long interval) {
        try {
            String savePath = DirConstants.getFileCache();
            String cachePath = this.e.getCacheDir().getAbsolutePath();
            for (APMultimediaTaskModel destPath : this.d.queryBuilder().where().le("create_time", Long.valueOf(System.currentTimeMillis() - interval)).query()) {
                String filePath = destPath.getDestPath();
                if (!TextUtils.isEmpty(filePath) && (filePath.startsWith(savePath) || filePath.startsWith(cachePath))) {
                    b.d("delete out date file : " + filePath, new Object[0]);
                    FileUtils.deleteFileByPath(filePath);
                }
            }
        } catch (Throwable e2) {
            b.e(e2, "deleteOutDateFile exception", new Object[0]);
        }
    }

    public int deleteOutDateTask(long interval) {
        try {
            DeleteBuilder deleteBuilder = this.d.deleteBuilder();
            deleteBuilder.where().le("create_time", Long.valueOf(System.currentTimeMillis() - interval));
            int removedCount = deleteBuilder.delete();
            b.d("delete out date task removedCount " + removedCount, new Object[0]);
            return removedCount;
        } catch (Throwable e2) {
            b.e(e2, "deleteOutDateTask exception", new Object[0]);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        try {
            List<APMultimediaTaskModel> failList = this.d.queryForEq(APMultimediaTaskModel.class, "status", "1");
            if (failList == null || failList.isEmpty()) {
                b.i("failList empty", new Object[0]);
                return;
            }
            b.i("failList SIZE  =  " + failList.size(), new Object[0]);
            for (APMultimediaTaskModel status : failList) {
                status.setStatus(3);
            }
            this.d.save(failList);
        } catch (Throwable e2) {
            b.e(e2, "resetTaskStatus exception", new Object[0]);
        }
    }

    public static APFileTaskManager getInstance(Context context) {
        if (c == null) {
            synchronized (APFileTaskManager.class) {
                try {
                    if (c == null) {
                        c = new APFileTaskManager(context);
                    }
                }
            }
        }
        return c;
    }

    public APMultimediaTaskModel getTaskRecord(String recordId) {
        b.d("getTaskRecord recordId: " + recordId, new Object[0]);
        try {
            APMultimediaTaskModel task = this.g.get(recordId);
            if (task == null) {
                task.loadTaskStatusFromDb = true;
                task = (APMultimediaTaskModel) this.d.query(APMultimediaTaskModel.class, recordId);
            }
            return task;
        } catch (Exception e2) {
            return null;
        }
    }

    public APMultimediaTaskModel getTaskRecordByCloudId(String cloudId) {
        b.d("getTaskRecordByCloudId cloudId: " + cloudId, new Object[0]);
        if (cloudId == null) {
            return null;
        }
        APMultimediaTaskModel task = null;
        try {
            for (String taskId : this.g.keySet()) {
                task = this.g.get(taskId);
                if (task != null && cloudId.equals(task.getCloudId())) {
                    return task;
                }
            }
            if (task != null) {
                return task;
            }
            List results = this.d.queryForEq(APMultimediaTaskModel.class, "cloud_id", cloudId);
            if (results == null || results.isEmpty()) {
                return task;
            }
            return results.get(0);
        } catch (Exception e2) {
            return null;
        }
    }

    public APMultimediaTaskModel updateTaskRecord(final APMultimediaTaskModel taskRecord) {
        if (taskRecord != null) {
            try {
                if (!TextUtils.isEmpty(taskRecord.getTaskId())) {
                    b.d("updateTaskRecord " + taskRecord, new Object[0]);
                    taskRecord.setUpdateTime(System.currentTimeMillis());
                    this.g.put(taskRecord.getTaskId(), taskRecord);
                    this.f.submit(new Runnable() {
                        public void run() {
                            try {
                                APFileTaskManager.this.d.save(taskRecord);
                            } catch (Exception e) {
                                APFileTaskManager.b.p("updateTaskRecord err: " + e, new Object[0]);
                            }
                        }
                    });
                }
            } catch (Exception e2) {
            }
        }
        return taskRecord;
    }

    public APMultimediaTaskModel delTaskRecord(String recordId) {
        b.d("delTaskRecord recordId: " + recordId, new Object[0]);
        try {
            if (this.g.containsKey(recordId)) {
                this.g.remove(recordId);
            }
            return (APMultimediaTaskModel) this.d.delete(APMultimediaTaskModel.class, recordId);
        } catch (Exception e2) {
            return null;
        }
    }

    public synchronized APMultimediaTaskModel addTaskRecord(APMultimediaTaskModel taskRecord) {
        b.d("addTaskRecord " + taskRecord, new Object[0]);
        if (taskRecord == null) {
            taskRecord = null;
        } else {
            if (TextUtils.isEmpty(taskRecord.getTaskId())) {
                taskRecord.setTaskId(a(taskRecord));
            }
            long cur = System.currentTimeMillis();
            taskRecord.setCreatTime(cur);
            taskRecord.setUpdateTime(cur);
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

    private void d() {
        this.f.submit(new Runnable() {
            public void run() {
                APFileTaskManager.b.d("checkAndSaveCache reach limit ，need save ", new Object[0]);
                APFileTaskManager.this.f();
            }
        });
    }

    /* access modifiers changed from: private */
    public void e() {
        try {
            long count = this.d.countOf();
            if (count >= 12000) {
                List<APMultimediaTaskModel> taskModels = this.d.queryBuilder().orderBy("create_time", true).limit(Long.valueOf((count - 12000) + 100)).query();
                if (taskModels != null) {
                    String savePath = DirConstants.getFileCache();
                    String cachePath = this.e.getCacheDir().getAbsolutePath();
                    b.d("found out date count: " + taskModels.size(), new Object[0]);
                    for (APMultimediaTaskModel destPath : taskModels) {
                        String filePath = destPath.getDestPath();
                        if (!TextUtils.isEmpty(filePath) && (filePath.startsWith(savePath) || filePath.startsWith(cachePath))) {
                            b.d("delete out date file : " + filePath, new Object[0]);
                            FileUtils.deleteFileByPath(filePath);
                        }
                    }
                    this.d.delete(taskModels);
                }
            }
        } catch (Exception e2) {
            b.e(e2, "clearOutDateData exception", new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public void f() {
        if (this.d != null && this.g != null && !this.g.isEmpty()) {
            try {
                this.d.save(new ArrayList<>(this.g.values()));
                this.g.clear();
            } catch (Exception e2) {
                b.e(e2, "saveCache error", new Object[0]);
            }
        }
    }

    private void b(APMultimediaTaskModel task) {
        this.g.put(task.getTaskId(), task);
        d();
    }

    public DbPersistence<APMultimediaTaskModel> getMediaTaskPersistence() {
        return this.d;
    }
}

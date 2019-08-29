package com.alipay.mobile.common.nbnet.biz.db;

import android.content.Context;
import android.util.LruCache;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.DeleteBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DownloadTaskDao {
    /* access modifiers changed from: private */
    public static final String a = DownloadTaskDao.class.getSimpleName();
    private final Context b;
    /* access modifiers changed from: private */
    public LruCache<Integer, DownloadTaskModel> c = new LruCache<>(20);

    class AsyncCreateOrUpdateRunnable implements Runnable {
        DownloadTaskDao a;
        DownloadTaskModel b;

        AsyncCreateOrUpdateRunnable(DownloadTaskDao downloadTaskDao, DownloadTaskModel downloadTaskModel) {
            this.a = downloadTaskDao;
            this.b = downloadTaskModel;
        }

        public void run() {
            DownloadTaskModel cacheDownloadTaskModel = (DownloadTaskModel) DownloadTaskDao.this.c.get(Integer.valueOf(this.b.requestId));
            if (cacheDownloadTaskModel == null || cacheDownloadTaskModel != this.b) {
                NBNetLogCat.d(DownloadTaskDao.a, "AsyncCreateOrUpdate. The cache has changed and can't be updated.");
            } else {
                this.a.b(this.b);
            }
        }
    }

    public DownloadTaskDao(Context context) {
        this.b = context;
    }

    public final DownloadTaskModel a(int requestId) {
        long queryDBStartTime = System.currentTimeMillis();
        try {
            DownloadTaskModel downloadTaskModel = this.c.get(Integer.valueOf(requestId));
            if (downloadTaskModel != null) {
                return downloadTaskModel;
            }
            List taskModels = d().getDao(DownloadTaskModel.class).queryBuilder().where().eq(DownloadTaskModel._REQUEST_ID, Integer.valueOf(requestId)).query();
            if (taskModels.size() > 1) {
                NBNetLogCat.d(a, taskModels.size() + " more than one task found " + requestId);
            }
            if (taskModels == null || taskModels.isEmpty()) {
                NBNetLogCat.b(a, String.format("monitor_perf: queryDownloadTask, requestId: %d , cost_time: %d", new Object[]{Integer.valueOf(requestId), Long.valueOf(System.currentTimeMillis() - queryDBStartTime)}));
                return null;
            }
            DownloadTaskModel downloadTaskModel2 = (DownloadTaskModel) taskModels.get(0);
            NBNetLogCat.b(a, String.format("monitor_perf: queryDownloadTask, requestId: %d , cost_time: %d", new Object[]{Integer.valueOf(requestId), Long.valueOf(System.currentTimeMillis() - queryDBStartTime)}));
            return downloadTaskModel2;
        } catch (SQLException e) {
            NBNetLogCat.b(a, (Throwable) e);
        } finally {
            r7 = "monitor_perf: queryDownloadTask, requestId: %d , cost_time: %d";
            NBNetLogCat.b(a, String.format(r7, new Object[]{Integer.valueOf(requestId), Long.valueOf(System.currentTimeMillis() - queryDBStartTime)}));
        }
    }

    public final void a(DownloadTaskModel downloadTask) {
        this.c.put(Integer.valueOf(downloadTask.requestId), downloadTask);
        NetworkAsyncTaskExecutor.executeIO(new AsyncCreateOrUpdateRunnable(this, downloadTask));
    }

    public final void b(DownloadTaskModel downloadTask) {
        NBNetLogCat.a(a, "createOrUpdateDownloadTask: " + downloadTask);
        long startTime = System.currentTimeMillis();
        try {
            DownloadTaskModel cacheDownloadTaskModel = this.c.get(Integer.valueOf(downloadTask.requestId));
            if (cacheDownloadTaskModel == null || cacheDownloadTaskModel != downloadTask) {
                this.c.put(Integer.valueOf(cacheDownloadTaskModel.requestId), cacheDownloadTaskModel);
            }
            CreateOrUpdateStatus status = d().getDao(DownloadTaskModel.class).createOrUpdate(downloadTask);
            if (status.isUpdated()) {
                NBNetLogCat.a(a, downloadTask.fileId + " task is updated");
            }
            if (status.isCreated()) {
                NBNetLogCat.a(a, downloadTask.fileId + " task is created");
            }
        } catch (SQLException e) {
            NBNetLogCat.b(a, (Throwable) e);
        } finally {
            r7 = "monitor_perf: createOrUpdateDownloadTask.  cost_time: %d";
            NBNetLogCat.a(a, String.format(r7, new Object[]{Long.valueOf(System.currentTimeMillis() - startTime)}));
        }
    }

    public final void b(int requestId) {
        long startTime = System.currentTimeMillis();
        try {
            this.c.remove(Integer.valueOf(requestId));
            DeleteBuilder deleteBuilder = d().getDao(DownloadTaskModel.class).deleteBuilder();
            deleteBuilder.where().eq(DownloadTaskModel._REQUEST_ID, Integer.valueOf(requestId));
            deleteBuilder.delete();
        } catch (SQLException e) {
            NBNetLogCat.b(a, (Throwable) e);
        } finally {
            r7 = "monitor_perf: deleteDownloadTask.  cost_time: ";
            NBNetLogCat.a(a, (System.currentTimeMillis() - startTime));
        }
    }

    public final List<DownloadTaskModel> a() {
        List expiredTasks = null;
        try {
            return d().getDao(DownloadTaskModel.class).queryBuilder().where().le(DownloadTaskModel._LAST_MODIFIED, Long.valueOf(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1))).query();
        } catch (SQLException e) {
            NBNetLogCat.b(a, (Throwable) e);
            return expiredTasks;
        }
    }

    public final List<DownloadTaskModel> b() {
        try {
            return d().getDao(DownloadTaskModel.class).queryBuilder().orderBy(DownloadTaskModel._LAST_MODIFIED, true).query();
        } catch (SQLException e) {
            NBNetLogCat.b(a, (Throwable) e);
            return null;
        }
    }

    private OrmLiteSqliteOpenHelper d() {
        return NBNetDbHelperFactory.a(this.b);
    }
}

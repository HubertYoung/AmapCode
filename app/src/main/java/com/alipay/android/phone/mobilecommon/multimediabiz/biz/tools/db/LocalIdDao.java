package com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.db;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.DbHelper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.persistence.db.OnDbCreateUpgradeHandler;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalIdDao implements OnDbCreateUpgradeHandler {
    private static final int DB_VER = 1;
    private static final int MSG_ADD_ALL = 100;
    private static final Logger logger = Logger.getLogger((String) "LocalIdDao");
    private AtomicInteger countOf = new AtomicInteger(0);
    private LruCache<String, LocalIdModel> mCache = new LruCache<>(20000);
    private Config mConfig = new Config();
    private Dao<LocalIdModel, String> mDao;
    private DbHelper mDbHelper = new DbHelper(AppUtils.getApplicationContext(), (String) "apm_local", 1, (OnDbCreateUpgradeHandler) this);
    private Handler mHandler;
    private final Map<String, LocalIdModel> mRefreshQueue = new HashMap();

    public static final class Config {
        public long lruDeleteCount = 100;
        public long maxCount = 60000;

        public final String toString() {
            return "Config{maxCount=" + this.maxCount + ", lruDeleteCount=" + this.lruDeleteCount + '}';
        }
    }

    public LocalIdDao(final Map<String, String> cache) {
        HandlerThread handlerThread = new HandlerThread("local_id_service");
        handlerThread.setPriority(1);
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper()) {
            public void handleMessage(Message msg) {
                if (100 == msg.what) {
                    LocalIdDao.this.loadAll(cache);
                } else {
                    LocalIdDao.this.onHandleMsg(msg);
                }
            }
        };
        this.mHandler.sendEmptyMessageDelayed(100, 10);
    }

    public synchronized long save(String localId, String path) {
        long numLinesChanged;
        numLinesChanged = 0;
        try {
            LocalIdModel model = (LocalIdModel) this.mCache.get(localId);
            if (model == null) {
                model = (LocalIdModel) getDao().queryBuilder().where().idEq(localId).queryForFirst();
            }
            boolean refreshNow = false;
            if (model == null) {
                refreshNow = true;
                model = new LocalIdModel();
                model.localId = localId;
                model.path = path;
                model.createTime = System.currentTimeMillis();
            }
            model.lastAccessTime = System.currentTimeMillis();
            this.mCache.put(localId, model);
            if (refreshNow) {
                CreateOrUpdateStatus status = getDao().createOrUpdate(model);
                numLinesChanged = (long) status.getNumLinesChanged();
                if (status.isCreated()) {
                    this.countOf.incrementAndGet();
                    numLinesChanged += (long) checkLru();
                }
            } else {
                synchronized (this.mRefreshQueue) {
                    this.mRefreshQueue.put(localId, model);
                }
                this.mHandler.removeMessages(0);
                this.mHandler.sendEmptyMessageDelayed(0, 5000);
            }
        } catch (Exception e) {
            logger.e(e, "save error", new Object[0]);
        }
        return numLinesChanged;
    }

    public synchronized void loadAll(Map<String, String> map) {
        if (map != null) {
            try {
                long start = System.currentTimeMillis();
                if (getDao() != null && getDao().isTableExists()) {
                    List<LocalIdModel> models = getDao().queryBuilder().orderBy(LocalIdModel.FIELD_LAST_ACCESS_TIME, true).query();
                    if (models != null && !models.isEmpty()) {
                        for (LocalIdModel m : models) {
                            map.put(m.localId, m.path);
                            this.mCache.put(m.localId, m);
                        }
                        this.countOf.set(models.size());
                    }
                    logger.d("loadAll size: " + map.size() + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
                }
            } catch (Exception e) {
                logger.e(e, "loadAll error", new Object[0]);
            }
        }
        return;
    }

    public synchronized String queryPathByLocalId(String localId) {
        String str = null;
        synchronized (this) {
            String path = null;
            if (!TextUtils.isEmpty(localId)) {
                try {
                    long start = System.currentTimeMillis();
                    LocalIdModel model = (LocalIdModel) this.mCache.get(localId);
                    if (model != null && !TextUtils.isEmpty(model.path)) {
                        str = model.path;
                    } else if (getDao() != null && getDao().isTableExists()) {
                        QueryBuilder builder = getDao().queryBuilder();
                        builder.where().eq("local_id", localId);
                        builder.orderBy(LocalIdModel.FIELD_LAST_ACCESS_TIME, true);
                        List models = builder.query();
                        if (models != null && !models.isEmpty()) {
                            Iterator it = models.iterator();
                            if (it.hasNext()) {
                                LocalIdModel m = (LocalIdModel) it.next();
                                this.mCache.put(m.localId, m);
                                this.countOf.addAndGet(1);
                                path = m.path;
                            }
                        }
                        logger.d("queryPathByLocalId localId: " + localId + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
                        str = path;
                    }
                } catch (Exception e) {
                    logger.e(e, "loadAll error", new Object[0]);
                }
            }
        }
        return str;
    }

    /* access modifiers changed from: protected */
    public int checkLru() {
        int deleted = 0;
        if (this.mConfig.maxCount > 0 && ((long) this.countOf.get()) > this.mConfig.maxCount && this.mConfig.lruDeleteCount > 0) {
            try {
                deleted = getDao().delete((Collection<T>) getDao().queryBuilder().limit(Long.valueOf(this.mConfig.lruDeleteCount)).orderBy(LocalIdModel.FIELD_LAST_ACCESS_TIME, true).query());
                this.countOf.addAndGet(-deleted);
            } catch (Exception e) {
                logger.e(e, "checkLru error", new Object[0]);
            }
            logger.d("checkLru deleted, config: " + this.mConfig + ", deleted: " + deleted + ", current: " + this.countOf.get(), new Object[0]);
        }
        return deleted;
    }

    private Dao<LocalIdModel, String> getDao() {
        if (this.mDao == null) {
            try {
                this.mDao = this.mDbHelper.getDao(LocalIdModel.class);
            } catch (Throwable e) {
                logger.e(e, "getDao error", new Object[0]);
            }
        }
        return this.mDao;
    }

    /* access modifiers changed from: private */
    public void onHandleMsg(Message msg) {
        Collection<LocalIdModel> toUpdateList;
        long start = System.currentTimeMillis();
        synchronized (this.mRefreshQueue) {
            logger.d("onHandleMsg msg: " + msg + ", toUpdate.length: " + this.mRefreshQueue.size(), new Object[0]);
            toUpdateList = new ArrayList<>(this.mRefreshQueue.values());
            this.mRefreshQueue.clear();
            logger.d("onHandleMsg msg: " + msg + ", toUpdate.length: " + toUpdateList.size() + " release synchronized", new Object[0]);
        }
        for (LocalIdModel m : toUpdateList) {
            try {
                getDao().update(m);
            } catch (Exception e) {
                logger.e(e, "onHandleMsg update error, " + m, new Object[0]);
            }
        }
        logger.d("onHandleMsg msg: " + msg + ", toUpdate.length: " + toUpdateList.size() + " finish, cost: " + (System.currentTimeMillis() - start), new Object[0]);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, LocalIdModel.class);
        } catch (SQLException e) {
            logger.e(e, "onCreate localId dataBase error", new Object[0]);
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
    }

    public void setConfig(Config config) {
        Config config2;
        if (config == null) {
            config2 = new Config();
        } else {
            config2 = config;
        }
        this.mConfig = config2;
        logger.d("setConfig update config: " + config, new Object[0]);
    }
}

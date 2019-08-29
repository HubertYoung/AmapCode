package com.alipay.mobile.common.nbnet.biz.db;

import android.content.Context;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import java.util.List;

public class UploadRecordDao {
    private static UploadRecordDao b;
    private Context a;

    private UploadRecordDao(Context context) {
        this.a = context;
    }

    public static final UploadRecordDao a(Context context) {
        if (b != null) {
            return b;
        }
        synchronized (UploadRecordDao.class) {
            if (b != null) {
                UploadRecordDao uploadRecordDao = b;
                return uploadRecordDao;
            }
            b = new UploadRecordDao(context);
            return b;
        }
    }

    /* JADX INFO: finally extract failed */
    public final int a(UploadRecordDo uploadRecordDo) {
        Throwable throwable;
        if (uploadRecordDo == null) {
            return 0;
        }
        try {
            if (uploadRecordDo.gmtCreated <= 0) {
                uploadRecordDo.gmtCreated = System.currentTimeMillis();
            }
            uploadRecordDo.gmtModifield = System.currentTimeMillis();
            CreateOrUpdateStatus status = a().getDao(UploadRecordDo.class).createOrUpdate(uploadRecordDo);
            int numLines = status.getNumLinesChanged();
            a(uploadRecordDo, numLines, null, status);
            return numLines;
        } catch (Throwable th) {
            a(uploadRecordDo, 0, throwable, null);
            throw th;
        }
    }

    public final void b(final UploadRecordDo uploadRecordDo) {
        if (NBNetEnvUtils.g()) {
            a(uploadRecordDo);
        } else {
            NetworkAsyncTaskExecutor.executeIO(new Runnable() {
                public void run() {
                    UploadRecordDao.this.a(uploadRecordDo);
                }
            });
        }
    }

    public final UploadRecordDo a(String md5) {
        long startTime = System.currentTimeMillis();
        try {
            List resultset = a().getDao(UploadRecordDo.class).queryBuilder().where().eq("md5", md5).query();
            if (resultset == null) {
                NBNetLogCat.a((String) "UploadRecordDao", (String) "queryUploadRecordByMD5 resultset is null.");
                NBNetLogCat.a((String) "UploadRecordDao", "queryUploadRecordByMD5 timing: " + (System.currentTimeMillis() - startTime));
                return null;
            }
            if (resultset.size() > 1) {
                NBNetLogCat.d("UploadRecordDao", resultset.size() + " more than one task found " + md5);
            }
            if (resultset == null || resultset.isEmpty()) {
                NBNetLogCat.a((String) "UploadRecordDao", "queryUploadRecordByMD5 timing: " + (System.currentTimeMillis() - startTime));
                return null;
            }
            UploadRecordDo uploadRecordDo = (UploadRecordDo) resultset.get(0);
            NBNetLogCat.a((String) "UploadRecordDao", "queryUploadRecordByMD5. " + uploadRecordDo.toString());
            return uploadRecordDo;
        } catch (Throwable e) {
            NBNetLogCat.b((String) "UploadRecordDao", e);
        } finally {
            r6 = "UploadRecordDao";
            r8 = "queryUploadRecordByMD5 timing: ";
            NBNetLogCat.a(r6, (System.currentTimeMillis() - startTime));
        }
    }

    private OrmLiteSqliteOpenHelper a() {
        return NBNetDbHelperFactory.a(this.a);
    }

    private static void a(UploadRecordDo uploadRecordDo, int numLines, Throwable throwable, CreateOrUpdateStatus status) {
        try {
            StringBuilder stringBuilder = new StringBuilder("insertOrUpdate");
            if (status != null) {
                stringBuilder.append(",operation: ").append(status.isCreated() ? "create" : "update");
            } else {
                stringBuilder.append(",operation: null");
            }
            stringBuilder.append(",numLines: ").append(numLines);
            if (throwable != null) {
                stringBuilder.append(",exception: ").append(throwable.toString());
            }
            stringBuilder.append(",").append(uploadRecordDo.toString());
            NBNetLogCat.a((String) "UploadRecordDao", stringBuilder.toString());
        } catch (Throwable e1) {
            NBNetLogCat.d("UploadRecordDao", "printInsertOrUpdateLog exception: " + e1.toString());
        }
    }
}

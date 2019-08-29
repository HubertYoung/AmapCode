package com.amap.bundle.drivecommon.map.db.helper;

import android.content.Context;
import com.amap.bundle.drivecommon.map.db.RdCameraPaymentItemDao;
import com.amap.bundle.drivecommon.map.db.RdCameraPaymentItemDao.Properties;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.List;

public class RdCameraDBHelper {
    private static RdCameraDBHelper a;
    private RdCameraPaymentItemDao b = ((RdCameraPaymentItemDao) xv.b().a(RdCameraPaymentItemDao.class));

    private RdCameraDBHelper() {
    }

    public static synchronized RdCameraDBHelper getInstance(Context context) {
        RdCameraDBHelper rdCameraDBHelper;
        synchronized (RdCameraDBHelper.class) {
            try {
                if (a == null) {
                    a = new RdCameraDBHelper();
                }
                rdCameraDBHelper = a;
            }
        }
        return rdCameraDBHelper;
    }

    public List<RdCameraPaymentItem> getAll() {
        QueryBuilder queryBuilder = this.b.queryBuilder();
        queryBuilder.orderDesc(Properties.a);
        return queryBuilder.list();
    }

    public void save(RdCameraPaymentItem rdCameraPaymentItem) {
        this.b.insertOrReplace(rdCameraPaymentItem);
    }

    public void saveAll(List<RdCameraPaymentItem> list) {
        if (list != null) {
            for (RdCameraPaymentItem save : list) {
                save(save);
            }
        }
    }

    public void delete(RdCameraPaymentItem rdCameraPaymentItem) {
        if (rdCameraPaymentItem != null) {
            this.b.delete(rdCameraPaymentItem);
        }
    }

    public void deleteWithTimeTamp(RdCameraPaymentItem rdCameraPaymentItem) {
        if (rdCameraPaymentItem != null) {
            QueryBuilder queryBuilder = this.b.queryBuilder();
            queryBuilder.where(Properties.a.eq(rdCameraPaymentItem.navi_timestamp), new WhereCondition[0]);
            queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }

    public void deleteAll() {
        this.b.deleteAll();
    }

    public List<RdCameraPaymentItem> getDataAfter(Long l) {
        if (l == null) {
            return null;
        }
        QueryBuilder queryBuilder = this.b.queryBuilder();
        queryBuilder.orderDesc(Properties.a);
        queryBuilder.where(Properties.a.ge(l), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public void deleteDataBefore(Long l) {
        if (l != null) {
            QueryBuilder queryBuilder = this.b.queryBuilder();
            queryBuilder.where(Properties.a.lt(l), new WhereCondition[0]);
            queryBuilder.buildDelete().executeDeleteWithoutDetachingEntities();
        }
    }
}

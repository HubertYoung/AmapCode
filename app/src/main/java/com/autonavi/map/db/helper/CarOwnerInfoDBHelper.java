package com.autonavi.map.db.helper;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.map.carowner.db.CarOwnerInfoDao;
import com.autonavi.map.carowner.db.CarOwnerInfoDao.Properties;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import java.util.Collections;
import java.util.List;

public class CarOwnerInfoDBHelper {
    private static CarOwnerInfoDBHelper a;
    private CarOwnerInfoDao b = ((CarOwnerInfoDao) xv.b().a(CarOwnerInfoDao.class));

    private CarOwnerInfoDBHelper() {
    }

    public static synchronized CarOwnerInfoDBHelper getInstance(Context context) {
        CarOwnerInfoDBHelper carOwnerInfoDBHelper;
        synchronized (CarOwnerInfoDBHelper.class) {
            if (a == null) {
                a = new CarOwnerInfoDBHelper();
            }
            carOwnerInfoDBHelper = a;
        }
        return carOwnerInfoDBHelper;
    }

    public List<bsy> getAll() {
        return this.b.queryBuilder().list();
    }

    public List<bsy> getDataByUid(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        QueryBuilder queryBuilder = this.b.queryBuilder();
        queryBuilder.where(Properties.a.eq(str), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public List<bsy> getDataByOwnerName(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        QueryBuilder queryBuilder = this.b.queryBuilder();
        queryBuilder.where(Properties.b.eq(str), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public List<bsy> getDataByPlateNo(String str) {
        if (TextUtils.isEmpty(str)) {
            return Collections.emptyList();
        }
        QueryBuilder queryBuilder = this.b.queryBuilder();
        queryBuilder.where(Properties.c.eq(str), new WhereCondition[0]);
        return queryBuilder.list();
    }

    public void save(bsy bsy) {
        this.b.insertOrReplace(bsy);
    }

    public void saveAll(List<bsy> list) {
        if (list != null) {
            for (bsy save : list) {
                save(save);
            }
        }
    }

    public void delete(bsy bsy) {
        if (bsy != null) {
            this.b.delete(bsy);
        }
    }

    public void deleteAll() {
        this.b.deleteAll();
    }
}

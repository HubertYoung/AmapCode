package com.autonavi.miniapp.biz.db.dao;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.nebula.util.H5Log;
import com.autonavi.miniapp.biz.db.AppDbHelper;
import com.autonavi.miniapp.biz.db.DaoExcutor;
import com.autonavi.miniapp.biz.db.model.RecentListEntity;
import com.autonavi.miniapp.biz.db.model.RecentSmallProModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.ArrayList;
import java.util.List;

public class RecentListDao extends DaoTemplate {
    private static final String TAG = "RecentListDao";
    private static RecentListDao instance = new RecentListDao();

    private RecentListDao() {
    }

    public static RecentListDao getDao() {
        if (instance == null) {
            instance = new RecentListDao();
        }
        return instance;
    }

    public List<RecentSmallProModel> getRecentList(final String str) {
        List<RecentSmallProModel> list;
        List<RecentSmallProModel> arrayList = new ArrayList<>();
        RecentListEntity recentListEntity = (RecentListEntity) excute(new DaoExcutor<RecentListEntity>() {
            public RecentListEntity excute(AppDbHelper appDbHelper) throws Exception {
                QueryBuilder queryBuilder = appDbHelper.getRecentListEntityDao().queryBuilder();
                queryBuilder.where().eq("userId", str);
                return (RecentListEntity) queryBuilder.queryForFirst();
            }
        });
        if (recentListEntity == null || TextUtils.isEmpty(recentListEntity.getRecentList())) {
            return arrayList;
        }
        try {
            list = JSON.parseArray(recentListEntity.getRecentList(), RecentSmallProModel.class);
        } catch (Exception e) {
            String str2 = TAG;
            StringBuilder sb = new StringBuilder("parse recent app list to JSONArray error: ");
            sb.append(e.getMessage());
            H5Log.e(str2, sb.toString());
            list = arrayList;
        }
        return list;
    }

    public void saveRecentList(final String str, List<RecentSmallProModel> list) {
        if (list != null) {
            final String jSONString = JSON.toJSONString(list);
            excute(new DaoExcutor<Object>() {
                public Object excute(AppDbHelper appDbHelper) throws Exception {
                    Dao<RecentListEntity, Integer> recentListEntityDao = appDbHelper.getRecentListEntityDao();
                    QueryBuilder queryBuilder = recentListEntityDao.queryBuilder();
                    queryBuilder.where().eq("userId", str);
                    RecentListEntity recentListEntity = (RecentListEntity) queryBuilder.queryForFirst();
                    if (recentListEntity != null) {
                        recentListEntity.setRecentList(jSONString);
                        recentListEntityDao.update(recentListEntity);
                    } else {
                        RecentListEntity recentListEntity2 = new RecentListEntity();
                        recentListEntity2.setUserId(str);
                        recentListEntity2.setRecentList(jSONString);
                        recentListEntityDao.create(recentListEntity2);
                    }
                    return null;
                }
            });
        }
    }
}

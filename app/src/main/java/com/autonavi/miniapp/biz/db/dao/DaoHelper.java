package com.autonavi.miniapp.biz.db.dao;

import com.alipay.mobile.common.logging.LogCatLog;
import com.autonavi.miniapp.biz.db.model.RecentSmallProModel;
import java.util.List;

public class DaoHelper {
    private static final String TAG = "DaoHelper";

    public static List<RecentSmallProModel> getRecentList(String str) {
        return RecentListDao.getDao().getRecentList(str);
    }

    public static void saveRecentList(String str, List<RecentSmallProModel> list) {
        StringBuilder sb = new StringBuilder("saveRecentList userId:");
        sb.append(str);
        sb.append(" RecentList:");
        sb.append(list.toString());
        LogCatLog.d(TAG, sb.toString());
        RecentListDao.getDao().saveRecentList(str, list);
    }
}

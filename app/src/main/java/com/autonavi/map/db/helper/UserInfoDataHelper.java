package com.autonavi.map.db.helper;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.db.UserInfoDao;
import com.autonavi.map.db.model.UserInfo;

public class UserInfoDataHelper {
    private UserInfoDao a;

    static class a {
        /* access modifiers changed from: private */
        public static final UserInfoDataHelper a = new UserInfoDataHelper(0);
    }

    /* synthetic */ UserInfoDataHelper(byte b) {
        this();
    }

    public static UserInfoDataHelper getInstance() {
        return a.a;
    }

    private UserInfoDataHelper() {
        try {
            this.a = (UserInfoDao) xv.b().a(UserInfoDao.class);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("UserInfoDataHelper init exception");
            sb.append(e.getMessage());
            AMapLog.debug("basemap.account", "UserInfoDataHelper", sb.toString());
        }
    }

    public void clear() {
        try {
            if (this.a != null) {
                this.a.deleteAll();
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("UserInfoDataHelper clear exception:");
            sb.append(e.getLocalizedMessage());
            AMapLog.debug("basemap.account", "UserInfoDataHelper", sb.toString());
        }
    }

    public UserInfo getCurrentUserInfo() {
        try {
            if (this.a == null) {
                return null;
            }
            return (UserInfo) this.a.queryBuilder().unique();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("UserInfoDataHelper getCurrentUserInfo exception:");
            sb.append(e.getLocalizedMessage());
            AMapLog.debug("basemap.account", "UserInfoDataHelper", sb.toString());
            return null;
        }
    }
}

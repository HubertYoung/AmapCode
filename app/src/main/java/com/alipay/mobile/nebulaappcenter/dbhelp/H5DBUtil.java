package com.alipay.mobile.nebulaappcenter.dbhelp;

import com.alibaba.sqlcrypto.sqlite.SQLiteDatabase;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5DBUtil {
    private static boolean a = false;

    private static class a {
        /* access modifiers changed from: private */
        public static final H5BaseDBHelper a = new H5DevDBOpenHelper();
    }

    private static class b {
        /* access modifiers changed from: private */
        public static final H5BaseDBHelper a = new H5OnLineDBHelper();
    }

    public static H5BaseDBHelper a() {
        return b.a;
    }

    public static H5BaseDBHelper b() {
        a = true;
        return a.a;
    }

    public static boolean c() {
        return a;
    }

    public static boolean d() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableNotQueryInstallApp"))) {
            return true;
        }
        return false;
    }

    public static boolean e() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !"yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableSaveAppInfoList"))) {
            return false;
        }
        return true;
    }

    public static void a(SQLiteDatabase db, String stmt) {
        try {
            db.execSQL(stmt);
            H5Log.d("H5DBUtil", "execSQL: " + stmt);
        } catch (Throwable t) {
            a(t);
            H5Log.e("H5DBUtil", "execSQL error!", t);
        }
    }

    public static void a(Throwable t) {
        H5LogUtil.logNebulaTech(H5LogData.seedId("h5_nebula_db_exception").param4().add(LogCategory.CATEGORY_EXCEPTION, t));
    }

    public static boolean f() {
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            H5Log.d("H5DBUtil", "isLogin : " + h5LoginProvider.isLogin());
            return h5LoginProvider.isLogin();
        }
        H5Log.d("H5DBUtil", "h5LoginProvider == null");
        return false;
    }
}

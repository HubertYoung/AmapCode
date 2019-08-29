package com.alipay.mobile.nebulaappcenter.dbapi;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5BaseDBHelper;
import com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil;

public class H5DaoTemplate {
    private static String a = "";

    protected static <T> T a(H5DaoExecutor<T> executor) {
        try {
            return executor.a(H5DBUtil.a());
        } catch (Throwable t) {
            H5Log.e("H5DaoTemplate", "execute error!", t);
            H5DBUtil.a(t);
            return null;
        }
    }

    @Nullable
    protected static <T> T a(H5DaoExecutor<T> executor, boolean useDev) {
        H5BaseDBHelper dbHelper;
        if (useDev) {
            dbHelper = H5DBUtil.b();
        } else {
            dbHelper = H5DBUtil.a();
        }
        try {
            return executor.a(dbHelper);
        } catch (Throwable t) {
            H5Log.e("H5DaoTemplate", "executeDB error!", t);
            H5DBUtil.a(t);
            return null;
        }
    }

    protected static H5BaseDBHelper a(boolean useDev) {
        return useDev ? H5DBUtil.b() : H5DBUtil.a();
    }

    public static String a() {
        if (TextUtils.isEmpty(a)) {
            H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
            if (h5LoginProvider != null) {
                String userId = h5LoginProvider.getUserId();
                if (TextUtils.isEmpty(userId)) {
                    H5Log.d("H5DaoTemplate", "getUserId null");
                    return "empty_user_id";
                }
                a = userId;
            } else {
                a = "empty_user_id";
            }
        }
        H5Log.d("H5DaoTemplate", "current user userIdCache : " + a);
        return a;
    }

    public static void b() {
        a = "";
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            a = h5LoginProvider.getUserId();
        } else {
            a = "empty_user_id";
        }
    }
}

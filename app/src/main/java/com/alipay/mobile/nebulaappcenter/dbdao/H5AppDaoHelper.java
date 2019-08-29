package com.alipay.mobile.nebulaappcenter.dbdao;

import android.text.TextUtils;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.Where;

public class H5AppDaoHelper {
    private static Boolean a = null;

    public static <T, S> Where<T, S> a(StatementBuilder<T, S> originalBuilder) {
        if (a()) {
            return originalBuilder.where().eq("user_id", H5DaoTemplate.a()).and();
        }
        return originalBuilder.where();
    }

    public static <T, S> void b(StatementBuilder<T, S> originalBuilder) {
        if (a()) {
            originalBuilder.where().eq("user_id", H5DaoTemplate.a());
        }
    }

    public static boolean a() {
        if (a == null) {
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider == null) {
                H5Log.e((String) "H5AppDaoHelper", (String) "h5ConfigProvider == null");
                a = Boolean.TRUE;
            } else {
                String configStr = h5ConfigProvider.getConfig("h5_enableAppPartitionWithUserId");
                if (TextUtils.isEmpty(configStr)) {
                    a = Boolean.TRUE;
                } else {
                    a = Boolean.valueOf(!"NO".equalsIgnoreCase(configStr));
                }
            }
        }
        return a.booleanValue();
    }

    public static void b() {
        a = null;
    }
}

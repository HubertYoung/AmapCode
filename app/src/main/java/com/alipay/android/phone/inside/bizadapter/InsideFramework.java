package com.alipay.android.phone.inside.bizadapter;

import com.alipay.android.phone.inside.bizadapter.log.LogContxtImpl;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LogContext;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.storage.StorageInit;

public class InsideFramework {
    private static LogContext a = new LogContxtImpl();

    public static void a() {
        LoggerFactory.a(a);
        AppInfo.a().a(StaticConfig.h());
        StorageInit.a(LauncherApplication.a());
    }
}

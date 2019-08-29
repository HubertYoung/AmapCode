package com.alipay.android.phone.inside.bizadapter.log;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LogContext;

public class LogContxtImpl implements LogContext {
    public Context getContext() {
        return LauncherApplication.a();
    }

    public String getInfo(String str) {
        if (TextUtils.equals(str, "log_inner_ver")) {
            return StaticConfig.f();
        }
        if (TextUtils.equals(str, "log_channel")) {
            return StaticConfig.e();
        }
        if (TextUtils.equals(str, "log_inside_mode")) {
            return StaticConfig.b();
        }
        if (TextUtils.equals(str, "log_product_id")) {
            return RunningConfig.c();
        }
        if (TextUtils.equals(str, "log_product_ver")) {
            return StaticConfig.c();
        }
        if (TextUtils.equals(str, "log_bussiness_id")) {
            return RunningConfig.b();
        }
        if (TextUtils.equals(str, "log_session_id")) {
            return RunningConfig.a();
        }
        if (TextUtils.equals(str, "log_user_id")) {
            return RunningConfig.e();
        }
        if (TextUtils.equals(str, "log_tid")) {
            return RunningConfig.a(true);
        }
        if (TextUtils.equals(str, "log_utdid")) {
            return RunningConfig.f();
        }
        if (TextUtils.equals(str, "log_biz_tid")) {
            return RunningConfig.k();
        }
        if (TextUtils.equals(str, "log_pid_token")) {
            return RunningConfig.l();
        }
        return "";
    }
}

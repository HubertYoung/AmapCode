package com.alipay.android.phone.inside.commonservice;

import android.content.Context;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.common.setting.InsideSetting;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.inside.android.phone.mrpc.core.Config;
import com.alipay.inside.android.phone.mrpc.core.HttpManager;
import com.alipay.inside.android.phone.mrpc.core.RpcParams;
import com.alipay.inside.android.phone.mrpc.core.Transport;

public class DefaultConfig implements Config {
    public RpcParams getRpcParams() {
        return null;
    }

    public boolean isGzip() {
        return false;
    }

    public boolean isResetCookie() {
        return false;
    }

    public String getUrl() {
        return InsideSetting.a();
    }

    public Transport getTransport() {
        return HttpManager.getInstance(getApplicationContext());
    }

    public Context getApplicationContext() {
        return LauncherApplication.a();
    }

    public String getAppid() {
        return AppInfo.a().e();
    }
}

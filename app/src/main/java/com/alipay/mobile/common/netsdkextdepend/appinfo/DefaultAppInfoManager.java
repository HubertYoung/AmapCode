package com.alipay.mobile.common.netsdkextdepend.appinfo;

import android.text.TextUtils;
import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.netsdkextdepend.selfutil.EnvUtil;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoManagerAdapter;

public class DefaultAppInfoManager extends AppInfoManagerAdapter {
    public String getProductVersion() {
        return AppInfo.createInstance(EnvUtil.getContext()).getProductVersion();
    }

    public String getTrackerID() {
        return LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
    }

    public String getProductId() {
        return AppInfo.createInstance(EnvUtil.getContext()).getProductID();
    }

    public String getReleaseType() {
        return AppInfo.createInstance(EnvUtil.getContext()).getReleaseType();
    }

    public boolean isReleaseTypeDev() {
        return TextUtils.equals(getReleaseType(), "dev");
    }

    public boolean isReleaseTypeRC() {
        return TextUtils.equals(getReleaseType(), LogContext.RELEASETYPE_RC);
    }

    public boolean isDebuggable() {
        return AppInfo.createInstance(EnvUtil.getContext()).isDebuggable();
    }
}

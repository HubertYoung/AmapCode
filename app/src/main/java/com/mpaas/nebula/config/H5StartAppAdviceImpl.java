package com.mpaas.nebula.config;

import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppBaseAdvice;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class H5StartAppAdviceImpl extends H5StartAppBaseAdvice {
    public boolean canHandler(String appId) {
        ApplicationDescription applicationDescription = LauncherApplicationAgent.getInstance().getMicroApplicationContext().findDescriptionByAppId(appId);
        H5Log.d("H5StartAppAdviceImpl", appId + Token.SEPARATOR + applicationDescription);
        if (applicationDescription == null || "H5App".equalsIgnoreCase(applicationDescription.getEngineType())) {
            a(appId);
        }
        if (H5AppUtil.isH5ContainerAppId(appId) || "H5DebugApp233".equals(appId)) {
            return false;
        }
        return true;
    }

    private static void a(String appId) {
        ApplicationDescription appDescription = new ApplicationDescription();
        appDescription.setAppId(appId);
        appDescription.setEngineType("H5App");
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().addDescription(appDescription);
    }
}

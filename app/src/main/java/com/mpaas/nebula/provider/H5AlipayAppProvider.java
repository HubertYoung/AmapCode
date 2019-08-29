package com.mpaas.nebula.provider;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppProvider;
import com.mpaas.nebula.util.Misc;

public class H5AlipayAppProvider extends H5BaseAppProvider {
    private String a;
    private String b;
    private String c;

    public boolean isNebulaApp(String appId) {
        if (TextUtils.equals("20000067", appId)) {
            return false;
        }
        return super.isNebulaApp(appId);
    }

    public AppReq setReq(AppReq appReq) {
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        if (TextUtils.isEmpty(this.a)) {
            this.a = Misc.getStringValueFromMetaData(context, "nebulamng_bundleid");
            if (TextUtils.isEmpty(this.a)) {
                LoggerFactory.getTraceLogger().error((String) "H5AlipayAppProvider", (String) "You haven't specified bundle id for nebula.");
            }
        }
        if (TextUtils.isEmpty(this.b)) {
            this.b = Misc.getStringValueFromMetaData(context, "nebulamng_channel");
            if (TextUtils.isEmpty(this.b)) {
                this.b = "offical";
            }
        }
        if (TextUtils.isEmpty(this.c)) {
            this.c = Misc.getStringValueFromMetaData(context, "nebulamng_env");
            if (TextUtils.isEmpty(this.c)) {
                this.c = "production";
            }
        }
        appReq.channel = this.b;
        appReq.env = this.c;
        appReq.bundleid = this.a;
        return appReq;
    }
}

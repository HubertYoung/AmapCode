package defpackage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alipay.android.nebulaapp.H5HomeListActivity;
import com.alipay.android.nebulaapp.MiniAppInitHelper;
import com.alipay.android.nebulaapp.MiniAppUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.miniapp.plugin.router.MiniAppRouter;

@BundleInterface(awh.class)
/* renamed from: awk reason: default package */
/* compiled from: MiniAppImpl */
public class awk implements awh {
    public final void a(Context context) {
        context.startActivity(new Intent(context, H5HomeListActivity.class));
    }

    public final void a(boolean z) {
        MiniAppUtil.reportMiniAppLog(z);
    }

    public final void a(String str) {
        if (!MiniAppRouter.isMiniAppScheme(str)) {
            MiniAppUtil.finishAllApps();
        }
    }

    public final boolean b(String str) {
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApplicationContext == null || microApplicationContext.findAppById(str) == null) {
            return false;
        }
        return true;
    }

    public final void a() {
        MiniAppInitHelper.getInstance().initMiniApp();
        String userId = LoggerFactory.getLogContext().getUserId();
        if (TextUtils.isEmpty(userId)) {
            userId = NetworkParam.getAdiu();
        }
        H5Utils.openUrl(String.format("https://render.alipay.com/p/s/upload-applog/index?account=%s", new Object[]{userId}));
    }
}

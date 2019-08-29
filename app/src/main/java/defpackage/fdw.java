package defpackage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import com.taobao.tao.remotebusiness.MtopBusiness;
import com.taobao.tao.remotebusiness.RequestPool;
import com.taobao.tao.remotebusiness.login.LoginContext;
import com.taobao.tao.remotebusiness.login.RemoteLogin;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.intf.Mtop;

/* renamed from: fdw reason: default package */
/* compiled from: CheckSessionDuplexFilter */
public final class fdw implements fdg, fdh {
    @NonNull
    public final String a() {
        return "mtopsdk.CheckSessionDuplexFilter";
    }

    public final String b(fdf fdf) {
        ffg ffg = fdf.o;
        if (!(ffg instanceof MtopBusiness)) {
            return "CONTINUE";
        }
        MtopBusiness mtopBusiness = (MtopBusiness) ffg;
        MtopRequest mtopRequest = fdf.b;
        Mtop mtop = fdf.a;
        boolean isNeedEcode = mtopRequest.isNeedEcode();
        try {
            String str = mtopBusiness.mtopProp.userInfo;
            if (!isNeedEcode || RemoteLogin.isSessionValid(mtop, str)) {
                if (isNeedEcode && fdd.b(mtop.c(str))) {
                    LoginContext loginContext = RemoteLogin.getLoginContext(mtop, str);
                    if (loginContext == null || fdd.b(loginContext.sid)) {
                        if (TBSdkLog.a(LogEnable.InfoEnable)) {
                            TBSdkLog.b((String) "mtopsdk.CheckSessionDuplexFilter", fdf.h, (String) "execute CheckSessionBeforeFilter.isSessionInvalid = true,getLoginContext = null");
                        }
                        RequestPool.addToRequestPool(mtop, str, mtopBusiness);
                        RemoteLogin.login(mtop, str, mtopBusiness.isShowLoginUI(), mtopRequest);
                        return "STOP";
                    }
                    if (TBSdkLog.a(LogEnable.WarnEnable)) {
                        TBSdkLog.c("mtopsdk.CheckSessionDuplexFilter", fdf.h, "session in loginContext is valid but mtopInstance's sid is null");
                    }
                    mtop.a(str, loginContext.sid, loginContext.userId);
                }
                return "CONTINUE";
            }
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                TBSdkLog.b((String) "mtopsdk.CheckSessionDuplexFilter", fdf.h, (String) "execute CheckSessionBeforeFilter.isSessionInvalid = false");
            }
            RequestPool.addToRequestPool(mtop, str, mtopBusiness);
            RemoteLogin.login(mtop, str, mtopBusiness.isShowLoginUI(), mtopRequest);
            return "STOP";
        } catch (Exception e) {
            TBSdkLog.b("mtopsdk.CheckSessionDuplexFilter", fdf.h, " execute CheckSessionBeforeFilter error.", e);
        }
    }

    public final String a(fdf fdf) {
        ffg ffg = fdf.o;
        if (!(ffg instanceof MtopBusiness)) {
            return "CONTINUE";
        }
        MtopBusiness mtopBusiness = (MtopBusiness) ffg;
        MtopRequest mtopRequest = fdf.b;
        Mtop mtop = fdf.a;
        MtopResponse mtopResponse = fdf.c;
        if (mtop.c.A) {
            String a = fcz.a(mtopResponse.getHeaderFields(), "x-session-ret");
            if (fdd.a(a)) {
                Bundle bundle = new Bundle();
                bundle.putString("x-session-ret", a);
                bundle.putString(AutoDownloadLogRequest.AUTO_KEY_DATE, fcz.a(mtopResponse.getHeaderFields(), AutoDownloadLogRequest.AUTO_KEY_DATE));
                RemoteLogin.setSessionInvalid(mtop, bundle);
            }
        }
        if (!mtopResponse.isSessionInvalid() || !mtopRequest.isNeedEcode() || mtopBusiness.getRetryTime() != 0) {
            return "CONTINUE";
        }
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            TBSdkLog.b((String) "mtopsdk.CheckSessionDuplexFilter", fdf.h, (String) "execute CheckSessionAfterFilter.");
        }
        String str = mtopBusiness.mtopProp.userInfo;
        RequestPool.addToRequestPool(mtop, str, mtopBusiness);
        RemoteLogin.login(mtop, str, mtopBusiness.isShowLoginUI(), mtopResponse);
        return "STOP";
    }
}

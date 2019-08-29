package defpackage;

import android.support.annotation.NonNull;
import com.taobao.tao.remotebusiness.MtopBusiness;
import com.taobao.tao.remotebusiness.RequestPool;
import com.taobao.tao.remotebusiness.auth.AuthParam;
import com.taobao.tao.remotebusiness.auth.RemoteAuth;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.domain.MtopRequest;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.intf.Mtop;

/* renamed from: fdv reason: default package */
/* compiled from: CheckAuthDuplexFilter */
public final class fdv implements fdg, fdh {
    @NonNull
    public final String a() {
        return "mtopsdk.CheckAuthDuplexFilter";
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
        boolean isNeedAuth = mtopBusiness.isNeedAuth();
        if (isNeedEcode && isNeedAuth) {
            try {
                if (mtopBusiness.getRetryTime() < 3) {
                    AuthParam authParam = new AuthParam(mtopBusiness.mtopProp.openAppKey, mtopBusiness.authParam, mtopBusiness.showAuthUI);
                    if (!RemoteAuth.isAuthInfoValid(mtop, authParam)) {
                        if (TBSdkLog.a(LogEnable.InfoEnable)) {
                            TBSdkLog.b((String) "mtopsdk.CheckAuthDuplexFilter", fdf.h, (String) " execute CheckAuthBeforeFilter.isAuthInfoValid = false");
                        }
                        RequestPool.addToRequestPool(mtop, authParam.openAppKey, mtopBusiness);
                        RemoteAuth.authorize(mtop, authParam);
                        return "STOP";
                    }
                    String a = fdd.a(mtop.b, authParam.openAppKey);
                    if (fdd.b(fgy.a(a, "accessToken"))) {
                        String authToken = RemoteAuth.getAuthToken(mtop, authParam);
                        if (fdd.a(authToken)) {
                            fgy.a(a, "accessToken", authToken);
                        } else {
                            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                                TBSdkLog.b((String) "mtopsdk.CheckAuthDuplexFilter", fdf.h, (String) " execute CheckAuthBeforeFilter.isAuthInfoValid = true,getAuthToken is null.");
                            }
                            RequestPool.addToRequestPool(mtop, authParam.openAppKey, mtopBusiness);
                            RemoteAuth.authorize(mtop, authParam);
                            return "STOP";
                        }
                    }
                }
            } catch (Exception e) {
                TBSdkLog.b("mtopsdk.CheckAuthDuplexFilter", fdf.h, " execute CheckAuthBeforeFilter error.", e);
            }
        }
        return "CONTINUE";
    }

    public final String a(fdf fdf) {
        ffg ffg = fdf.o;
        if (!(ffg instanceof MtopBusiness)) {
            return "CONTINUE";
        }
        MtopBusiness mtopBusiness = (MtopBusiness) ffg;
        Mtop mtop = fdf.a;
        MtopResponse mtopResponse = fdf.c;
        String retCode = mtopResponse.getRetCode();
        try {
            if (mtopBusiness.isNeedAuth() && mtopBusiness.getRetryTime() < 3 && fff.e.contains(retCode)) {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    TBSdkLog.b((String) "mtopsdk.CheckAuthDuplexFilter", fdf.h, (String) " execute CheckAuthAfterFilter.");
                }
                AuthParam authParam = new AuthParam(mtopBusiness.mtopProp.openAppKey, mtopBusiness.authParam, mtopBusiness.showAuthUI);
                authParam.apiInfo = mtopBusiness.request.getKey();
                if (mtopBusiness.mtopProp.isInnerOpen) {
                    authParam.failInfo = retCode;
                } else {
                    authParam.failInfo = fcz.a(mtopResponse.getHeaderFields(), "x-act-hint");
                }
                RequestPool.addToRequestPool(mtop, authParam.openAppKey, mtopBusiness);
                RemoteAuth.authorize(mtop, authParam);
                return "STOP";
            }
        } catch (Exception e) {
            TBSdkLog.b("mtopsdk.CheckAuthDuplexFilter", fdf.h, " execute CheckAuthAfterFilter error.", e);
        }
        return "CONTINUE";
    }
}

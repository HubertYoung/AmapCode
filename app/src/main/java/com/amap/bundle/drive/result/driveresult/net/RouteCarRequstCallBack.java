package com.amap.bundle.drive.result.driveresult.net;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.model.RouteCarResultData;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.Callback;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.locks.ReentrantLock;

@SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class RouteCarRequstCallBack extends RouteRequestNoCacheCallBack<tb> {
    private static final ReentrantLock i = new ReentrantLock();
    public boolean a = false;
    private boolean h = false;
    private ta j;
    private int k;

    public final /* synthetic */ void a(Object obj) {
        tb tbVar = (tb) obj;
        StringBuilder sb = new StringBuilder("3.RouteCarRequstCallBack callback mHasExecCacheCallback:");
        sb.append(this.h);
        DriveUtil.addCarRouteLog(sb.toString());
        td.a().a(4);
        if (this.b != null && !this.h) {
            tbVar.a.setIsMultiRoute(this.a);
            this.b.callback(tbVar);
        }
    }

    public RouteCarRequstCallBack(Callback<tb> callback, ta taVar) {
        super(callback, taVar.a, taVar.c, taVar.b, taVar.e);
        this.j = taVar;
        this.k = taVar.s;
    }

    public final void a(Throwable th) {
        DriveUtil.addCarRouteLog("4.RouteCarRequstCallBack error callbackError:false");
        if (this.b != null) {
            this.b.error(th, false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public synchronized tb a(byte[] bArr) {
        String str;
        tb tbVar;
        try {
            td.a().a(3);
            i.lock();
            RouteCarResultData routeCarResultData = new RouteCarResultData(this.j.A);
            routeCarResultData.setFromPOI(this.c);
            routeCarResultData.setToPOI(this.d);
            routeCarResultData.setMidPOIs(agj.a(this.e));
            routeCarResultData.setMethod(this.f);
            routeCarResultData.setSceneResult(this.j.t);
            routeCarResultData.setMainPoi(this.j.x);
            if (DriveUtil.getCarTypeByVtype(this.j.D) == 1) {
                str = DriveUtil.getTruckCarPlateNumber();
            } else {
                str = DriveUtil.getCarPlateNumber();
            }
            routeCarResultData.setCarPlate(str);
            tbVar = new tb(routeCarResultData);
            tbVar.c = this.k;
            tbVar.parser(bArr);
            i.unlock();
        } catch (Throwable th) {
            i.unlock();
            throw th;
        }
        return tbVar;
    }

    /* renamed from: a */
    public final void onSuccess(AosByteResponse aosByteResponse) {
        a(aosByteResponse == null ? null : aosByteResponse.getAosRequest());
        super.onSuccess(aosByteResponse);
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(aosRequest);
        super.onFailure(aosRequest, aosResponseException);
    }

    private static void a(AosRequest aosRequest) {
        if (aosRequest != null) {
            String url = aosRequest.getUrl();
            if (!TextUtils.isEmpty(url)) {
                String queryParameter = Uri.parse(url).getQueryParameter("csid");
                if (!TextUtils.isEmpty(queryParameter)) {
                    td.a().a(queryParameter);
                }
            }
        }
    }
}

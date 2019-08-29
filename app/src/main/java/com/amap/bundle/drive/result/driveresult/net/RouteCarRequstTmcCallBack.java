package com.amap.bundle.drive.result.driveresult.net;

import com.amap.bundle.drivecommon.model.RouteCarResultData;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.Callback;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class RouteCarRequstTmcCallBack extends RouteRequestNoCacheCallBack<tc> {
    private static final ReentrantLock h = new ReentrantLock();
    private boolean a = false;
    private ta i;
    private ta j;
    private int k;

    public final /* synthetic */ void a(Object obj) {
        tc tcVar = (tc) obj;
        StringBuilder sb = new StringBuilder("3.RouteCarRequstCallBack callback mHasExecCacheCallback:");
        sb.append(this.a);
        DriveUtil.addCarRouteLog(sb.toString());
        if (this.b != null && !this.a) {
            this.b.callback(tcVar);
        }
    }

    public RouteCarRequstTmcCallBack(Callback<tc> callback, ta... taVarArr) {
        this.b = callback;
        for (ta taVar : taVarArr) {
            if (CalcRouteScene.SCENE_HOME_TMC == taVar.A) {
                this.i = taVar;
                this.k = taVar.s;
            } else if (CalcRouteScene.SCENE_COMPANY_TMC == taVar.A) {
                this.j = taVar;
                this.k = taVar.s;
            }
        }
    }

    public final void a(Throwable th) {
        DriveUtil.addCarRouteLog("4.RouteCarRequstCallBack error callbackError:false");
        if (this.b != null) {
            this.b.error(th, false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public synchronized tc a(byte[] bArr) {
        tc tcVar;
        try {
            h.lock();
            ArrayList arrayList = new ArrayList();
            if (this.i != null) {
                RouteCarResultData routeCarResultData = new RouteCarResultData(this.i.A);
                routeCarResultData.setFromPOI(this.i.a);
                routeCarResultData.setToPOI(this.i.b);
                routeCarResultData.setMidPOIs(agj.a(this.i.c));
                routeCarResultData.setMethod(this.i.e);
                routeCarResultData.setSceneResult(this.i.t);
                routeCarResultData.setMainPoi(this.i.x);
                routeCarResultData.setCarPlate(a(this.i.D));
                arrayList.add(routeCarResultData);
            }
            if (this.j != null) {
                RouteCarResultData routeCarResultData2 = new RouteCarResultData(this.j.A);
                routeCarResultData2.setFromPOI(this.j.a);
                routeCarResultData2.setToPOI(this.j.b);
                routeCarResultData2.setMidPOIs(agj.a(this.j.c));
                routeCarResultData2.setMethod(this.j.e);
                routeCarResultData2.setSceneResult(this.j.t);
                routeCarResultData2.setMainPoi(this.j.x);
                routeCarResultData2.setCarPlate(a(this.j.D));
                arrayList.add(routeCarResultData2);
            }
            tcVar = new tc(arrayList);
            tcVar.c = this.k;
            tcVar.parser(bArr);
            h.unlock();
        } catch (Throwable th) {
            h.unlock();
            throw th;
        }
        return tcVar;
    }

    private synchronized String a(int i2) {
        String str;
        int carTypeByVtype = DriveUtil.getCarTypeByVtype(i2);
        if (carTypeByVtype == 1) {
            str = DriveUtil.getTruckCarPlateNumber();
        } else if (carTypeByVtype == 11) {
            str = DriveUtil.getMotorPlateNum();
        } else {
            str = DriveUtil.getCarPlateNumber();
        }
        return str;
    }
}

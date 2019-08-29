package com.amap.bundle.drive.result;

import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.model.OfflineMsgCode;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.minimap.route.export.model.IRouteResultData;

public class DriveRouteManager$1 implements Callback<tb> {
    final /* synthetic */ ta a;
    final /* synthetic */ axa b;

    public DriveRouteManager$1(ta taVar, axa axa) {
        this.a = taVar;
        this.b = axa;
    }

    public final void error(Throwable th, boolean z) {
        axa axa = this.b;
        RouteType routeType = RouteType.CAR;
        agj.a(this.a.c);
        axa.a(th, z);
    }

    public final void callback(tb tbVar) {
        ICarRouteResult iCarRouteResult = tbVar.a;
        if (iCarRouteResult != null) {
            tn.a().a(this.a.A, iCarRouteResult.getCalcRouteResult());
            NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
            NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
            if (naviResultData == null || naviResultData.mPaths == null || naviResultData.mPathNum <= 0 || focusNavigationPath == null) {
                this.b.a(RouteType.CAR, tbVar.errorCode, tbVar.b());
            } else if (!tbVar.a() && tbVar.errorCode != OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode()) {
                if (tbVar.errorCode == -1 || tbVar.errorCode == 13) {
                    ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_NEEDREBOOT.getStrCodeMsg());
                }
            } else if (iCarRouteResult.getNaviResultData() == null) {
                this.b.a(RouteType.CAR, tbVar.errorCode, tbVar.b());
            } else {
                if (tbVar.errorCode == OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode() && !iCarRouteResult.isM_bNative()) {
                    ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getStrCodeMsg());
                }
                this.b.a((IRouteResultData) iCarRouteResult, RouteType.CAR);
            }
        } else {
            this.b.a(RouteType.CAR, tbVar.errorCode, tbVar.b());
        }
    }
}

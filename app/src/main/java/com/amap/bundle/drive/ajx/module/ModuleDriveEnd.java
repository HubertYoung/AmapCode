package com.amap.bundle.drive.ajx.module;

import com.amap.bundle.drive.ajx.inter.INaviEnd;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("drive_end")
@Keep
@KeepClassMembers
public class ModuleDriveEnd extends AbstractModule {
    public static final String MODULE_NAME = "drive_end";
    private static final String TAG = "ModuleDriveEnd";
    private INaviEnd mINaviEnd;
    private JsFunctionCallback mJsNewYearActivityCallback = null;
    private ModuleDriveEndImpl mModuleDriveEndImpl = new ModuleDriveEndImpl();

    public ModuleDriveEnd(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("requestDriveEndOperationActivities")
    public void requestDriveEndOperationActivities(String str) {
        log("AjxMethod   requestDriveEndOperationActivities  param:".concat(String.valueOf(str)));
        this.mModuleDriveEndImpl.requestDriveEndOperationActivities(str, this.mINaviEnd);
    }

    @AjxMethod("reportDriveEndError")
    public void reportDriveEndError(String str) {
        log("AjxMethod   reportDriveEndError  param:".concat(String.valueOf(str)));
        this.mModuleDriveEndImpl.reportDriveEndError(str, this.mINaviEnd);
    }

    @AjxMethod("reportDestinationError")
    public void reportDestinationError(String str) {
        log("AjxMethod   reportDestinationError  param:".concat(String.valueOf(str)));
        this.mModuleDriveEndImpl.reportDestinationError(str, this.mINaviEnd);
    }

    @AjxMethod("saveDriveEndPayforData")
    public void saveDriveEndPayforData(String str) {
        log("AjxMethod   saveDriveEndPayforData  param:".concat(String.valueOf(str)));
        this.mModuleDriveEndImpl.saveDriveEndPayforData(str);
    }

    @AjxMethod("saveDriveEndTrcComensateInfo")
    public void saveDriveEndTrcComensateInfo(String str) {
        log("AjxMethod   saveDriveEndTrcComensateInfo  param:".concat(String.valueOf(str)));
        this.mModuleDriveEndImpl.saveDriveEndTrcComensateInfo(str);
    }

    public void setDriveEndCallback(INaviEnd iNaviEnd) {
        this.mINaviEnd = iNaviEnd;
    }

    private void log(String str) {
        AMapLog.i(TAG, "module_opt ".concat(String.valueOf(str)));
    }

    @AjxMethod(invokeMode = "sync", value = "getErrorReportNum")
    public int getErrorReportNum() {
        return this.mModuleDriveEndImpl.getErrorReportNum(this.mINaviEnd);
    }

    @AjxMethod("registerNewYearOperation")
    public void registerNewYearOperation(JsFunctionCallback jsFunctionCallback) {
        ku.a().c("NaviMonitor", "[ModuleDriveEnd] registerNewYearOperation ");
        this.mJsNewYearActivityCallback = jsFunctionCallback;
    }

    public void notifyNewYearAcitivity(String str) {
        AMapLog.i(TAG, "notifyNewYearAcitivity    ".concat(String.valueOf(str)));
        if (this.mJsNewYearActivityCallback != null && str != null) {
            ku.a().c("NaviMonitor", "[ModuleDriveEnd] notifyNewYearAcitivity ".concat(String.valueOf(str)));
            this.mJsNewYearActivityCallback.callback(str);
        }
    }

    @AjxMethod("driveEndShare")
    @Deprecated
    public void driveEndShare(String str) {
        dct dct = new dct(0);
        dct.d = true;
        dct.e = true;
        dct.h = true;
        NaviEndShareStatusCallback naviEndShareStatusCallback = new NaviEndShareStatusCallback(str, null);
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            dcb.a(dct, (dcd) naviEndShareStatusCallback);
        }
    }
}

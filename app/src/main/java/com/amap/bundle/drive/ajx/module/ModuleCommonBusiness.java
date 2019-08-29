package com.amap.bundle.drive.ajx.module;

import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.inter.IMotorActivityCallback;
import com.amap.bundle.drive.ajx.inter.INaviUiActionListener;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("common_business")
@Keep
@KeepClassMembers
public class ModuleCommonBusiness extends AbstractModule {
    public static final String MODULE_NAME = "common_business";
    private static final String TAG = "ModuleCommonBusiness";
    private ModuleCommonBusinessImpl mModuleCommonBusinessImpl = new ModuleCommonBusinessImpl();

    public ModuleCommonBusiness(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getCarrierInfo")
    public String getCarrierInfo() {
        log("AjxMethod  getCarrierInfo");
        return this.mModuleCommonBusinessImpl.getCarrierInfo();
    }

    @AjxMethod(invokeMode = "sync", value = "getAosUrl")
    public String getAosUrl() {
        return this.mModuleCommonBusinessImpl.getAosUrl();
    }

    @AjxMethod("startAlipay")
    public void startAlipay() {
        this.mModuleCommonBusinessImpl.startAlipay();
    }

    @AjxMethod("jumpToMainPage")
    public void jumpToMainPage() {
        this.mModuleCommonBusinessImpl.jumpToMainPage();
    }

    private void log(String str) {
        AMapLog.i(TAG, "module_opt ".concat(String.valueOf(str)));
    }

    @AjxMethod(invokeMode = "sync", value = "isTaxiOpen")
    public boolean isTaxiOpen() {
        String a = lo.a().a((String) DIYMainMapPresenter.DIY_MAIN_MAP_CONFIG_MODULE_NAME);
        return !TextUtils.isEmpty(a) && a.contains("\"cab\"");
    }

    public void setINaviUiActionListener(INaviUiActionListener iNaviUiActionListener) {
        this.mModuleCommonBusinessImpl.setINaviUiActionListener(iNaviUiActionListener);
    }

    public void setMotorEndActivityCallback(IMotorActivityCallback iMotorActivityCallback) {
        this.mModuleCommonBusinessImpl.setMotorEndActivityCallback(iMotorActivityCallback);
    }

    public void setMotorResultActivityCallback(IMotorActivityCallback iMotorActivityCallback) {
        this.mModuleCommonBusinessImpl.setMotorResultActivityCallback(iMotorActivityCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "checkPhoneCalling")
    public String checkPhoneCalling() {
        AMapLog.i(TAG, "checkPhoneCalling");
        ku.a().c("NaviMonitor", "ModuleCommonBusiness checkPhoneCalling");
        return ((TelephonyManager) AMapAppGlobal.getApplication().getSystemService("phone")).getCallState() == 0 ? "0" : "1";
    }

    @AjxMethod(invokeMode = "sync", value = "fetchActivityWithSceneType")
    public void fetchActivityWithSceneType(String str) {
        this.mModuleCommonBusinessImpl.fetchActivityWithSceneType(str);
    }
}

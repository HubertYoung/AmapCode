package com.amap.bundle.drive.ajx.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.tools.DriveCarOwnerAjxTools;
import com.amap.bundle.drive.ajx.tools.DriveRouteAjxTools;
import com.amap.bundle.drive.ajx.tools.DriveRouteHomeCompanyManager;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("route_motor")
@Keep
public final class ModuleRouteMotor extends AbstractModule {
    private static final String CARINFO = "carInfo";
    private static final String CARINFOMINE = "carInfoMine";
    private static final String CARINFONATIVE = "carInfoNative";
    private static final String CARINFOREGISTER = "carInfoRegister";
    private static final String CARTOOLBOX = "carToolBox";
    public static final String MODULE_NAME = "route_motor";
    private static final String NAVINFO = "navInfo";
    private static final String TAG = "ModuleDriveRoute";
    private JsFunctionCallback mCarOwnerCallback;
    /* access modifiers changed from: private */
    public IRouteMotorModuleListener mModuleListener;
    private bid mPageContext = AMapPageUtil.getPageContext();
    private JsFunctionCallback mPreferCallback;

    public interface IRouteMotorModuleListener {
        boolean startRouteMotorResultPage(String str);
    }

    public ModuleRouteMotor(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setSettingInfo")
    public final void setSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (!TextUtils.isEmpty(str)) {
            if (str.equalsIgnoreCase(NAVINFO)) {
                this.mPreferCallback = jsFunctionCallback;
                DriveRouteAjxTools.startMotorSettingPage();
            } else if (str.equalsIgnoreCase(CARINFOMINE)) {
                this.mCarOwnerCallback = jsFunctionCallback;
                if (bsl.a().b(1) == null) {
                    DriveUtil.startAddCarPage(1, AMapPageUtil.getPageContext());
                    return;
                }
                Intent intent = new Intent();
                intent.setData(Uri.parse("amapuri://carownerservice/homepage"));
                DoNotUseTool.startScheme(intent);
            } else {
                if (str.equalsIgnoreCase(CARINFOREGISTER)) {
                    this.mCarOwnerCallback = jsFunctionCallback;
                }
            }
        }
    }

    @AjxMethod("getSettingInfo")
    public final void getSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase(NAVINFO)) {
            this.mPreferCallback = jsFunctionCallback;
            String b = rc.b();
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(b);
            }
        }
    }

    @AjxMethod("getNativeImgPath")
    public final void getNativeImgPath(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (TextUtils.isEmpty(str)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
            return;
        }
        DriveRouteHomeCompanyManager.getInstace().requestTMCAndSavePic(11, str, jsFunctionCallback);
    }

    @AjxMethod("requestRoute")
    public final void requestRoute(final String str) {
        tj.b("ModuleDriveRoute", str);
        aho.a(new Runnable() {
            public void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                IPageHost iPageHost = (pageContext == null || pageContext.getActivity() == null) ? null : (IPageHost) pageContext.getActivity();
                if ((iPageHost == null || !iPageHost.isHostPaused()) && ModuleRouteMotor.this.mModuleListener != null) {
                    ModuleRouteMotor.this.mModuleListener.startRouteMotorResultPage(str);
                }
            }
        });
    }

    public final void updatePreference() {
        tj.c("ModuleDriveRoute", "");
        String b = rc.b();
        if (this.mPreferCallback != null) {
            this.mPreferCallback.callback(b);
        }
    }

    public final void updateCarOwner() {
        tj.b("ModuleDriveRoute", "");
        if (this.mCarOwnerCallback != null) {
            DriveCarOwnerAjxTools.getCarDBData(this.mCarOwnerCallback);
        }
    }

    public final void setContext(bid bid) {
        this.mPageContext = bid;
    }

    public final void setManagerListener(IRouteMotorModuleListener iRouteMotorModuleListener) {
        this.mModuleListener = iRouteMotorModuleListener;
    }

    public final void release() {
        this.mPreferCallback = null;
        this.mCarOwnerCallback = null;
    }
}

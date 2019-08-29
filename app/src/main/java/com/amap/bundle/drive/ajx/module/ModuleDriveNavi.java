package com.amap.bundle.drive.ajx.module;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.amap.bundle.drive.ajx.inter.IFullScreenChangeCallback;
import com.amap.bundle.drive.ajx.inter.ILowBrightness;
import com.amap.bundle.drive.ajx.inter.IRealNaviEventCallback;
import com.amap.bundle.drive.ajx.inter.IRealNaviPageEventCallback;
import com.amap.bundle.drive.ajx.inter.IReportEvent;
import com.amap.bundle.drive.ajx.inter.IStatusBarChange;
import com.amap.bundle.drive.ajx.inter.IWritingPermissionStateCallback;
import com.amap.bundle.drive.ajx.inter.OnRealCityStateChangeListener;
import com.amap.bundle.drive.ajx.inter.ShareStatusListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.vcs.NativeVcsManager;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("drive_navi")
@Keep
@KeepClassMembers
public class ModuleDriveNavi extends AbstractModule {
    public static final String CAR_NAVI_END = "path://amap_bundle_drive/src/car/end_page/CarEndPage.page.js";
    public static final String MODULE_NAME = "drive_navi";
    private static final String TAG = "ModuleDriveNavi";
    private IFullScreenChangeCallback mFullScreenChangeCallback;
    private ILowBrightness mILowBrightness = null;
    private IWritingPermissionStateCallback mIWritingPermissionStateCallback;
    private JsFunctionCallback mJsGroupClipBoardMsgCallback = null;
    private JsFunctionCallback mJsStatusBarCallback = null;
    private JsFunctionCallback mJsVUIEventCallback = null;
    private ModuleDriveNaviImpl mModuleDriveNaviImpl = new ModuleDriveNaviImpl();
    private OnRealCityStateChangeListener mOnRealCityStateChangeListener = null;
    private IRealNaviEventCallback mRealNaviEventCallback;
    private IRealNaviPageEventCallback mRealNaviPageEventCallback;
    private IReportEvent mReportEvent;
    private ShareStatusListener mShareStatusListener;
    private IStatusBarChange mStatusBarChange;
    private JsFunctionCallback mVoiceSquareCallback = null;

    @AjxMethod(invokeMode = "sync", value = "hasUnCompleteReport")
    public int hasUnCompleteReport() {
        return 0;
    }

    public ModuleDriveNavi(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setReportEventListener(IReportEvent iReportEvent) {
        this.mReportEvent = iReportEvent;
    }

    public void setShareStatusListener(ShareStatusListener shareStatusListener) {
        this.mShareStatusListener = shareStatusListener;
    }

    public void setNaviEventListener(IRealNaviEventCallback iRealNaviEventCallback) {
        this.mRealNaviEventCallback = iRealNaviEventCallback;
    }

    public void setNaviPageEventListener(IRealNaviPageEventCallback iRealNaviPageEventCallback) {
        this.mRealNaviPageEventCallback = iRealNaviPageEventCallback;
    }

    public void setStatusBarChangeListener(IStatusBarChange iStatusBarChange) {
        this.mStatusBarChange = iStatusBarChange;
    }

    public void setIFullScreenChangeCallback(IFullScreenChangeCallback iFullScreenChangeCallback) {
        this.mFullScreenChangeCallback = iFullScreenChangeCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "onReportButtonClick")
    public void onReportButtonClick(int i) {
        this.mModuleDriveNaviImpl.onReportButtonClick(i, this.mReportEvent);
    }

    @AjxMethod(invokeMode = "sync", value = "getErrorReportNum")
    public int getErrorReportNum() {
        if (this.mReportEvent != null) {
            return this.mReportEvent.getErrorReportNum();
        }
        return 0;
    }

    @AjxMethod("onGroupOverlayHighlighted")
    public void onGroupOverlayHighlighted(JsFunctionCallback jsFunctionCallback) {
        if (this.mModuleDriveNaviImpl != null) {
            this.mModuleDriveNaviImpl.onGroupOverlayHighlighted(jsFunctionCallback);
        }
    }

    @AjxMethod("setGroupClipBoardMsgCallback")
    public void setGroupClipBoardMsgCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsGroupClipBoardMsgCallback = jsFunctionCallback;
    }

    public void sendGroupClipBoardMsg(int i, String str) {
        if (this.mJsGroupClipBoardMsgCallback != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i);
                jSONObject.put("value", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.mJsGroupClipBoardMsgCallback.callback(jSONObject.toString());
        }
    }

    @AjxMethod("safetyShare")
    public void safetyShare(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (this.mModuleDriveNaviImpl != null) {
            this.mModuleDriveNaviImpl.safetyShare(str, str2, this.mShareStatusListener, jsFunctionCallback);
        }
    }

    @AjxMethod("openVoiceSquare")
    public void onOpenVoiceSquare(JsFunctionCallback jsFunctionCallback) {
        this.mVoiceSquareCallback = jsFunctionCallback;
        if (this.mRealNaviPageEventCallback != null) {
            this.mRealNaviPageEventCallback.onOpenVoiceSquare();
        }
    }

    public void onVoiceSquareBack(String str) {
        if (this.mVoiceSquareCallback != null) {
            this.mVoiceSquareCallback.callback(str);
        }
    }

    @AjxMethod("startDriveEndPage")
    public void startDriveEndPage(String str) {
        if (this.mRealNaviPageEventCallback != null) {
            this.mRealNaviPageEventCallback.startDriveEndPage(str);
        }
    }

    @AjxMethod("updateTravelPoints")
    public void updateTravelPoints(String str) {
        if (this.mRealNaviEventCallback != null) {
            this.mRealNaviEventCallback.onTravelPointsUpdate(str);
        }
    }

    @AjxMethod("onCalRoute")
    public void onCalRoute(String str) {
        if (this.mRealNaviEventCallback != null) {
            this.mRealNaviEventCallback.onCalRoute(str);
        }
    }

    @AjxMethod("unHighlightedGroupOverlay")
    public void unHighlightedGroupOverlay() {
        if (this.mModuleDriveNaviImpl != null) {
            this.mModuleDriveNaviImpl.unHighlightedGroupOverlay();
        }
    }

    @AjxMethod("updateDayNightState")
    public void updateDayNightState(String str) {
        if (this.mModuleDriveNaviImpl != null) {
            this.mModuleDriveNaviImpl.updateDayNightState(str);
        }
    }

    @AjxMethod("onStatusBarChanged")
    public void onStatusBarChanged(JsFunctionCallback jsFunctionCallback) {
        this.mJsStatusBarCallback = jsFunctionCallback;
        if (this.mStatusBarChange != null) {
            this.mStatusBarChange.registeStatusBarInfoChange();
        }
    }

    public void notifyStatusBarChange(JSONObject jSONObject) {
        AMapLog.i(TAG, "notifyStatusBarChange    ".concat(String.valueOf(jSONObject)));
        if (this.mJsStatusBarCallback != null && jSONObject != null) {
            this.mJsStatusBarCallback.callback(jSONObject.toString());
        }
    }

    @AjxMethod("checkBTDevicesConnected")
    public void checkBTDevicesConnected(JsFunctionCallback jsFunctionCallback, String str) {
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(str);
            if (this.mRealNaviEventCallback != null) {
                this.mRealNaviEventCallback.onSpeakerChanged(Integer.parseInt(str));
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getRealDayNightMode")
    public String getRealDayNightMode() {
        return this.mRealNaviEventCallback != null ? this.mRealNaviEventCallback.isRealDayNightMode() : true ? "1" : "0";
    }

    @AjxMethod("handleGroupMemberLayer")
    public void handleGroupMemberLayer(final boolean z) {
        Activity activity;
        if (TextUtils.equals("main", Thread.currentThread().getName())) {
            dealWithGroupMemberLayer(z);
            return;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext == null) {
            activity = null;
        } else {
            activity = pageContext.getActivity();
        }
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ModuleDriveNavi.this.dealWithGroupMemberLayer(z);
                }
            });
        } else {
            aho.a(new Runnable() {
                public void run() {
                    ModuleDriveNavi.this.dealWithGroupMemberLayer(z);
                }
            });
        }
    }

    @AjxMethod("receiveMitNaviCalcRoute")
    public void receiveMitNaviCalcRoute(String str) {
        this.mModuleDriveNaviImpl.receiveMitNaviCalcRoute(str);
    }

    @AjxMethod("registerVUIEventCallBack")
    public void registerVUIEventCallBack(JsFunctionCallback jsFunctionCallback) {
        if (bno.a) {
            AMapLog.i(TAG, "registerVUIEventCallBack");
        }
        this.mJsVUIEventCallback = jsFunctionCallback;
        NativeVcsManager.getInstance().stopListening();
    }

    public void onVUIEventCallback(String str) {
        AMapLog.i(TAG, "onVoiceEventCallback json=".concat(String.valueOf(str)));
        ku.a().c("NaviMonitor", "onVoiceEventCallback json:".concat(String.valueOf(str)));
        if (this.mJsVUIEventCallback != null) {
            this.mJsVUIEventCallback.callback(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "checkWritingPermission")
    public void checkWritingPermission() {
        if (this.mIWritingPermissionStateCallback != null) {
            this.mIWritingPermissionStateCallback.checkWritingPermission();
        }
    }

    public void setWritingPermissionStateCallback(IWritingPermissionStateCallback iWritingPermissionStateCallback) {
        this.mIWritingPermissionStateCallback = iWritingPermissionStateCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "getRealCityConfig")
    public String getRealCityConfig() {
        return rs.a();
    }

    @AjxMethod(invokeMode = "sync", value = "setRealcityState")
    public void setRealcityState(String str) {
        int parseInt = Integer.parseInt(str);
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (DoNotUseTool.getMapManager() != null) {
            boolean z = false;
            if (parseInt == 1) {
                bty mapView = mapManager.getMapView();
                if (mapView != null) {
                    mapView.f(false);
                }
            } else if (parseInt == 0) {
                bty mapView2 = mapManager.getMapView();
                if (mapView2 != null) {
                    mapView2.f(true);
                }
            }
            if (this.mOnRealCityStateChangeListener != null) {
                OnRealCityStateChangeListener onRealCityStateChangeListener = this.mOnRealCityStateChangeListener;
                if (parseInt == 1) {
                    z = true;
                }
                onRealCityStateChangeListener.onRealCityStateChange(z);
            }
        }
    }

    public void setOnRealCityStateChangeListener(OnRealCityStateChangeListener onRealCityStateChangeListener) {
        this.mOnRealCityStateChangeListener = onRealCityStateChangeListener;
    }

    @AjxMethod(invokeMode = "sync", value = "setLowBrightness")
    public void setLowBrightness(int i) {
        if (this.mILowBrightness != null) {
            ILowBrightness iLowBrightness = this.mILowBrightness;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            iLowBrightness.onLowBrightnessOpen(z);
        }
    }

    public void setILowBrightness(ILowBrightness iLowBrightness) {
        this.mILowBrightness = iLowBrightness;
    }

    @AjxMethod(invokeMode = "sync", value = "isNaviTTSDialectNewVersion")
    public boolean isNaviTTSDialectNewVersion() {
        IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
        if (iVoicePackageManager != null) {
            return iVoicePackageManager.isNaviTtsNewVersion();
        }
        return false;
    }

    @AjxMethod("fakeNetworkLocation")
    public void fakeNetworkLocation(int i) {
        ku a = ku.a();
        StringBuilder sb = new StringBuilder("fakeNetworkLocation ");
        boolean z = true;
        sb.append(i == 1 ? "open" : CameraParams.FLASH_MODE_OFF);
        a.c(TAG, sb.toString());
        LocationInstrument instance = LocationInstrument.getInstance();
        if (i != 1) {
            z = false;
        }
        instance.fakeNetworkLocation(z);
    }

    @AjxMethod("setFullScreen")
    public void setFullScreen(String str) {
        if (this.mModuleDriveNaviImpl != null) {
            this.mModuleDriveNaviImpl.setFullScreen(str, this.mFullScreenChangeCallback);
        }
    }

    /* access modifiers changed from: private */
    public void dealWithGroupMemberLayer(boolean z) {
        cuh cuh = (cuh) a.a.a(cuh.class);
        IAgroupOverlayService b = cuh != null ? cuh.b() : null;
        if (b != null) {
            b.a(!z);
        }
    }
}

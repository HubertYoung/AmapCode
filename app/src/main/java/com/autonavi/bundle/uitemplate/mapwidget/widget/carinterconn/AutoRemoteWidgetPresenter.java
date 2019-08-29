package com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn;

import android.os.Handler;
import android.view.View;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONObject;

public class AutoRemoteWidgetPresenter extends BaseMapWidgetPresenter<AutoRemoteMapWidget> {
    private final String SP_KEY_IsShowAutoLinkTip = "sp_key_is_show_auto_link_tip";

    public void bindListener() {
        if (isWidgetNotNull()) {
            View autoRemoteView = ((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteView();
            View autoRemoteTipView = ((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteTipView();
            setWidgetEventIndex(autoRemoteView, 0);
            setWidgetEventIndex(autoRemoteTipView, 1);
            onBindListener(autoRemoteView, autoRemoteTipView);
        }
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            vpVar.a(new vq() {
                public void onHeadunitLoginStateChanged(int i) {
                    AutoRemoteWidgetPresenter.this.updateIconVisible();
                }

                public void onHeadunitWifiConnectStateChanged(boolean z) {
                    AutoRemoteWidgetPresenter.this.updateIconVisible();
                }
            });
        }
    }

    public void internalClickListener(View view) {
        if (isWidgetNotNull()) {
            if (view.equals(((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteView())) {
                doAction(view);
                return;
            }
            if (view.equals(((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteTipView())) {
                hideAutoRemoteTipView();
            }
        }
    }

    public void hideAutoRemoteTipView() {
        ((AutoRemoteMapWidget) this.mBindWidget).setVisibility(((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteTipView(), 8);
    }

    private void doAction(View view) {
        sendPoiToHeadUnit(view, null, true);
    }

    public void dealWithAutoRemoteView(final View view) {
        if (view != null) {
            view.setClickable(false);
            view.setAlpha(0.6f);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    view.setClickable(true);
                    view.setAlpha(1.0f);
                }
            }, 5000);
            LogManager.actionLogV2("P00001", "B260");
        }
    }

    public void actionLogClickHeadUnitIcon(POI poi) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (poi != null) {
                jSONObject.put("result", 1);
            } else {
                jSONObject.put("result", 0);
            }
            vp vpVar = (vp) a.a.a(vp.class);
            if (vpVar != null) {
                if (vpVar.c()) {
                    jSONObject.put("status", "wifi");
                } else if (vpVar.d()) {
                    jSONObject.put("status", "mqtt");
                }
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_CLICK_HEADUNIT_ICON, jSONObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAutoRemoteTipState() {
        if (isWidgetNotNull()) {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (mapSharePreference.getBooleanValue("sp_key_is_show_auto_link_tip", true)) {
                ((AutoRemoteMapWidget) this.mBindWidget).setVisibility(((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteTipView(), 0);
                mapSharePreference.putBooleanValue("sp_key_is_show_auto_link_tip", false);
            }
        }
    }

    public void sendPoiToHeadUnit(POI poi, boolean z) {
        if (isWidgetNotNull()) {
            sendPoiToHeadUnit(((AutoRemoteMapWidget) this.mBindWidget).getContentView(), poi, z);
        }
    }

    public void sendPoiToHeadUnit(View view, POI poi, boolean z) {
        doSendPoiAction(poi, z);
        hideAutoRemoteTipView();
        dealWithAutoRemoteView(view);
    }

    public boolean updateIconVisible() {
        int i = 0;
        if (!isWidgetNotNull()) {
            return false;
        }
        boolean autoRemoteIconVisible = getAutoRemoteIconVisible();
        if (autoRemoteIconVisible) {
            LogManager.actionLogV2("P00001", LogConstant.AUTO_ROUTE_SHOW);
            updateAutoRemoteTipState();
        }
        AutoRemoteMapWidget autoRemoteMapWidget = (AutoRemoteMapWidget) this.mBindWidget;
        View autoRemoteView = ((AutoRemoteMapWidget) this.mBindWidget).getAutoRemoteView();
        if (!autoRemoteIconVisible) {
            i = 8;
        }
        autoRemoteMapWidget.setVisibility(autoRemoteView, i);
        return autoRemoteIconVisible;
    }

    private void doSendPoiAction(POI poi, boolean z) {
        if (poi != null) {
            vp vpVar = (vp) a.a.a(vp.class);
            if (vpVar != null && vpVar.b()) {
                vpVar.a(poi, (vo) new vo() {
                    public void onSuccess(int i) {
                        ku.a().c(ModuleHeadunit.MODULE_NAME, "发送POI到车机  onSuccess  sendType:".concat(String.valueOf(i)));
                        switch (i) {
                            case 0:
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_linksdk));
                                return;
                            case 1:
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_aos));
                                break;
                        }
                    }

                    public void onError(String str, String str2) {
                        ku a = ku.a();
                        StringBuilder sb = new StringBuilder("发送POI到车机  onError code:");
                        sb.append(str);
                        sb.append("   msg:");
                        sb.append(str2);
                        a.c(ModuleHeadunit.MODULE_NAME, sb.toString());
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                    }
                });
            }
        } else {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.no_content_select));
        }
        if (z) {
            actionLogClickHeadUnitIcon(poi);
        }
    }

    private boolean getAutoRemoteIconVisible() {
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            return vpVar.b();
        }
        return false;
    }
}

package com.amap.bundle.drive.ajx.module;

import android.text.TextUtils;
import com.amap.bundle.drive.ajx.inter.HeadunitBtnEventCallback;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.AjxErrorUtil;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONObject;

public class ModuleHeadunitImpl {
    public static final String HEADUNIT_BTN_EVENT_CLICK = "click";
    public static final String HEADUNIT_BTN_EVENT_HIDE = "hide";
    public static final String HEADUNIT_BTN_EVENT_REGISTER = "register";
    public static final String HEADUNIT_BTN_EVENT_SHOW = "show";
    public static final String HEADUNIT_BTN_EVENT_UNREGISTER = "unregister";
    private static final String TAG = "ModuleHeadunitImpl";
    private HeadunitBtnEventCallback mHeadunitBtnEventCallback;
    private String mRouteInfo;

    public void routeResultInfo(String str) {
        AMapLog.i(TAG, "routeResultInfo     ".concat(String.valueOf(str)));
        this.mRouteInfo = str;
    }

    public void sendNaviRouteInfo(String str) {
        AMapLog.i(TAG, "sendNaviRouteInfo     ".concat(String.valueOf(str)));
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            vpVar.a(str, (vo) null);
        }
    }

    public void headunitBtnEvent(String str, final JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.equals("register", str)) {
            vp vpVar = (vp) a.a.a(vp.class);
            if (vpVar != null) {
                vpVar.a(new vq() {
                    public void onHeadunitLoginStateChanged(int i) {
                        JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                        Object[] objArr = new Object[1];
                        objArr[0] = i != 0 ? ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW : ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE;
                        jsFunctionCallback.callback(objArr);
                    }

                    public void onHeadunitWifiConnectStateChanged(boolean z) {
                        JsFunctionCallback jsFunctionCallback = jsFunctionCallback;
                        Object[] objArr = new Object[1];
                        objArr[0] = z ? ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW : ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE;
                        jsFunctionCallback.callback(objArr);
                    }
                });
                if (vpVar.b()) {
                    jsFunctionCallback.callback(HEADUNIT_BTN_EVENT_SHOW);
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("status", vpVar.c() ? "wifi" : "mqtt");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, "B129", jSONObject);
            }
            return;
        }
        if (TextUtils.equals("click", str)) {
            if (this.mHeadunitBtnEventCallback != null) {
                this.mHeadunitBtnEventCallback.action(str);
            }
        } else if (TextUtils.equals("unregister", str)) {
            vp vpVar2 = (vp) a.a.a(vp.class);
            if (vpVar2 != null) {
                vpVar2.a(null);
            }
        }
    }

    public void setHeadunitBtnEventCallback(HeadunitBtnEventCallback headunitBtnEventCallback) {
        this.mHeadunitBtnEventCallback = headunitBtnEventCallback;
    }

    public String getRouteInfo() {
        return this.mRouteInfo;
    }

    public void sendPoiToHeadunit(String str, final JsFunctionCallback jsFunctionCallback) {
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            try {
                vpVar.a(bnx.a(str), (vo) new vo() {
                    public void onSuccess(int i) {
                        toast(i);
                        if (jsFunctionCallback != null) {
                            jsFunctionCallback.callback(new Object[0]);
                        }
                    }

                    public void onError(String str, String str2) {
                        toast(-1);
                        if (jsFunctionCallback != null) {
                            jsFunctionCallback.callback(AjxErrorUtil.getError(100, "send sendPoiToHeadunit failure", str, str2));
                        }
                    }

                    private void toast(int i) {
                        switch (i) {
                            case -1:
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                                break;
                            case 0:
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_linksdk));
                                return;
                            case 1:
                                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_aos));
                                return;
                        }
                    }
                });
            } catch (Exception e) {
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(AjxErrorUtil.getError(1, "invalid param", str, e.toString()));
                }
            }
        } else {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(AjxErrorUtil.getError(2, "internal error", new String[0]));
            }
        }
    }
}

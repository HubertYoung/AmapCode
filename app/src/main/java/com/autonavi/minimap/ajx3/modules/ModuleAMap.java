package com.autonavi.minimap.ajx3.modules;

import android.graphics.Point;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("AMap")
public class ModuleAMap extends AbstractModule {
    private static final int IN_POI_TRAFFIC_MAIN_PAGE = 2;
    private static final int IN_REAL_MAP_HOME_PAGE = 1;
    public static final String MODULE_NAME = "AMap";
    private static final int NOT_IN_MAP_HOME_PAGE = 0;
    private static final String TAG = "ModuleAMap";
    private static JsFunctionCallback sBackListner = null;
    private static String sIntercept = "0";
    public static long sStart;
    /* access modifiers changed from: private */
    public ccs mAMapSuspendView;

    public ModuleAMap(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setAMapSuspendView(ccs ccs) {
        this.mAMapSuspendView = ccs;
    }

    private boolean checkSuspendView() {
        if (this.mAMapSuspendView != null) {
            return true;
        }
        AMapLog.e(TAG, "AMapSuspendView is null!");
        return false;
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @AjxMethod("bottomLine")
    public void bottomLine(float f, final int i) {
        if (checkSuspendView()) {
            final int standardUnitToPixel = DimensionUtils.standardUnitToPixel(f);
            if (isMainThread()) {
                this.mAMapSuspendView.setMarginBottom(standardUnitToPixel, i);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.setMarginBottom(standardUnitToPixel, i);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("topLine")
    public void topLine(float f, final int i) {
        if (checkSuspendView()) {
            final int standardUnitToPixel = DimensionUtils.standardUnitToPixel(f);
            if (isMainThread()) {
                this.mAMapSuspendView.setMarginTop(standardUnitToPixel, i);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.setMarginTop(standardUnitToPixel, i);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("AMapControlOpacity")
    public void aMapControlOpacity(final float f, final int i) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.setViewAlpha(f, i);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.setViewAlpha(f, i);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("box")
    public void box(float f, float f2, int i, int i2) {
        if (checkSuspendView()) {
            final int standardUnitToPixel = DimensionUtils.standardUnitToPixel(f);
            final int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(f2);
            if (isMainThread()) {
                this.mAMapSuspendView.setVerticalMargin(standardUnitToPixel, standardUnitToPixel2, i, i2);
                return;
            }
            final int i3 = i;
            final int i4 = i2;
            AnonymousClass4 r1 = new Runnable() {
                public void run() {
                    if (ModuleAMap.this.mAMapSuspendView != null) {
                        ModuleAMap.this.mAMapSuspendView.setVerticalMargin(standardUnitToPixel, standardUnitToPixel2, i3, i4);
                    }
                }
            };
            aho.a(r1);
        }
    }

    @AjxMethod("addControl")
    public void addControl(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.addControl(str, jsFunctionCallback);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.addControl(str, jsFunctionCallback);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("updateControl")
    public void updateControl(final String str) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.updateControl(str);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.updateControl(str);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("setCommonControl")
    public void setCommonControl(final String str, final JsFunctionCallback jsFunctionCallback) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.setCommonControl(str, jsFunctionCallback);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.setCommonControl(str, jsFunctionCallback);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("showControl")
    public void showControl(final String str, final boolean z) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.showControl(str, z);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.showControl(str, z);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod("hideControl")
    public void hideControl(final String str) {
        if (checkSuspendView()) {
            if (isMainThread()) {
                this.mAMapSuspendView.hideControl(str);
            } else {
                aho.a(new Runnable() {
                    public void run() {
                        if (ModuleAMap.this.mAMapSuspendView != null) {
                            ModuleAMap.this.mAMapSuspendView.hideControl(str);
                        }
                    }
                });
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "toPixels")
    public String toPixels(String str) {
        bty mapView = DoNotUseTool.getMapView();
        if (!TextUtils.isEmpty(str) && mapView != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                double optDouble = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, Double.MIN_VALUE);
                double optDouble2 = jSONObject.optDouble("lat", Double.MIN_VALUE);
                if (optDouble > Double.MIN_VALUE && optDouble2 > Double.MIN_VALUE) {
                    GeoPoint a = cob.a(optDouble2, optDouble);
                    Point point = new Point();
                    mapView.a((GLGeoPoint) a, point);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, point.x);
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, point.y);
                    return jSONObject2.toString();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put(DictionaryKeys.CTRLXY_X, 0);
            jSONObject3.put(DictionaryKeys.CTRLXY_Y, 0);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject3.toString();
    }

    @AjxMethod("isMainMap")
    public void isMainMap(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(Boolean.valueOf(AMapPageUtil.isHomePage()));
    }

    @AjxMethod("listenBackPress")
    public void listenBackPress(JsFunctionCallback jsFunctionCallback) {
        sBackListner = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "notifyBackOver")
    public void notifyBackOver(boolean z) {
        if (z) {
            sIntercept = "1";
        } else {
            sIntercept = "2";
        }
    }

    public static boolean isTimeOut() {
        return System.currentTimeMillis() - sStart > 400;
    }

    public static String getIntercept() {
        return sIntercept;
    }

    public static void setIntercept(String str) {
        sIntercept = str;
    }

    public static void doBack() {
        if (sBackListner != null) {
            sStart = System.currentTimeMillis();
            sBackListner.callback(new Object[0]);
        }
    }

    @AjxMethod("isInMainMap")
    public void isInMainMap(JsFunctionCallback jsFunctionCallback) {
        apr apr = (apr) a.a.a(apr.class);
        if (apr == null) {
            jsFunctionCallback.callback(Integer.valueOf(0));
        } else if (apr.a(AMapPageUtil.getPageContext())) {
            jsFunctionCallback.callback(Integer.valueOf(1));
        } else if (AMapPageUtil.isHomePage()) {
            jsFunctionCallback.callback(Integer.valueOf(2));
        } else {
            jsFunctionCallback.callback(Integer.valueOf(0));
        }
    }
}

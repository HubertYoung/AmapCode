package com.amap.bundle.drive.ajx.tools;

import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;
import com.amap.bundle.drive.ajx.module.ModuleRouteTruck;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.model.Car;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DriveCarOwnerAjxTools {
    private static final String TAG = "DriveHomeCarOwnerAjxTools";

    @Deprecated
    public static void getDBData(final JsFunctionCallback jsFunctionCallback) {
        ahn.b().execute(new Runnable() {
            public final void run() {
                ato ato = (ato) a.a.a(ato.class);
                Car b = ato != null ? ato.a().b(1) : null;
                String str = bny.c;
                if (b != null) {
                    str = DriveCarOwnerAjxTools.toJson(b, true);
                }
                tj.a(DriveCarOwnerAjxTools.TAG, str);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }
        });
    }

    public static void getCarDBData(final JsFunctionCallback jsFunctionCallback) {
        ahn.b().execute(new Runnable() {
            public final void run() {
                ato ato = (ato) a.a.a(ato.class);
                Car b = ato != null ? ato.a().b(1) : null;
                String str = bny.c;
                if (b != null) {
                    str = DriveCarOwnerAjxTools.toJson(b, true);
                }
                StringBuilder sb = new StringBuilder("getTruckDBData run（）json=");
                sb.append(String.valueOf(str));
                tj.a(DriveCarOwnerAjxTools.TAG, sb.toString());
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(str);
                }
            }
        });
    }

    public static void getTruckDBData(final Callback<String> callback) {
        ahn.b().execute(new Runnable() {
            public final void run() {
                ato ato = (ato) a.a.a(ato.class);
                Car b = ato != null ? ato.a().b(2) : null;
                String str = bny.c;
                if (b != null) {
                    str = DriveCarOwnerAjxTools.toJson(b, true);
                }
                if (callback != null) {
                    callback.callback(str);
                }
                tj.b(ModuleRouteTruck.TAG, "getTruckDBData run（）json=".concat(String.valueOf(str)));
            }
        });
    }

    public static String toJson(Car car, boolean z) {
        if (car == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("plateNum", car.plateNum);
            jSONObject.put("vehiclecode", car.vehicleCode);
            jSONObject.put("vehicleMsg", car.vehicleMsg);
            jSONObject.put("vehicleLogo", car.vehicleLogo);
            jSONObject.put("engineNum", car.engineNum);
            jSONObject.put("frameNum", car.frameNum);
            jSONObject.put("telphone", car.telphone);
            jSONObject.put("validityPeriod", car.validityPeriod);
            jSONObject.put("ocr_request_id", car.ocrRequestId);
            jSONObject.put("violationReminder", car.violationReminder);
            jSONObject.put("checkReminder", car.checkReminder);
            jSONObject.put("limitReminder", car.limitReminder);
            jSONObject.put("truckAvoidWeightLimit", car.truckAvoidWeightLimit);
            jSONObject.put("createTime", car.createTime);
            jSONObject.put("updateTime", car.updateTime);
            jSONObject.put("oftenUse", z ? 1 : 0);
            jSONObject.put("vehicleType", car.vehicleType);
            jSONObject.put("vehiclePowerType", car.vehiclePowerType);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("truckType", car.truckType);
            jSONObject2.put("length", car.length);
            jSONObject2.put("width", car.width);
            jSONObject2.put("height", car.height);
            jSONObject2.put("capacity", car.capacity);
            jSONObject2.put(MetaInfoXmlParser.KEY_VALVE_WEIGHT, car.weight);
            jSONObject2.put("axleNum", car.axleNum);
            jSONObject.put("truckInfo", jSONObject2);
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startMyCarPage(bid bid) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_carowner/src/car_owner/CarListViewController.page.js");
        bid.startPageForResult(Ajx3Page.class, pageBundle, 1100);
    }
}

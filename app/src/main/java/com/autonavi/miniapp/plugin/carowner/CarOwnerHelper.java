package com.autonavi.miniapp.plugin.carowner;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.nebula.basebridge.H5BaseBridgeContext;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.sdk.cons.c;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.db.model.Car;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.Collection;
import java.util.regex.Pattern;

public class CarOwnerHelper {
    public static final String OPTIONAL_ITEM_ENGINE_NO = "engineNo";
    public static final String OPTIONAL_ITEM_VIN = "vin";
    public static final int VEHICLE_POWER_TYPE_DEFAULT = 0;
    public static final int VEHICLE_POWER_TYPE_ELECTRIC = 1;
    public static final int VEHICLE_POWER_TYPE_HYBIRD = 2;

    public interface Callback {
        void onResponse(JSONObject jSONObject);
    }

    public static int transCarTypeToMiniAppType(int i, int i2) {
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return (i2 - 1) + 20;
        }
        return 0;
    }

    public static void filterOptionalItems(JSONArray jSONArray, JSONArray jSONArray2) {
        for (int i = 0; i < jSONArray.size(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (!jSONArray2.contains(OPTIONAL_ITEM_VIN)) {
                jSONObject.remove(OPTIONAL_ITEM_VIN);
            }
            if (!jSONArray2.contains(OPTIONAL_ITEM_ENGINE_NO)) {
                jSONObject.remove(OPTIONAL_ITEM_ENGINE_NO);
            }
        }
    }

    public static void getVehicleAuthInfo(ant ant, H5Event h5Event, Collection<MiniAppVehicle> collection, JSONArray jSONArray, Callback callback) {
        JSONArray vehicleMd5Array = getVehicleMd5Array(collection, jSONArray);
        if (vehicleMd5Array == null) {
            callback.onResponse(new JSONObject());
            return;
        }
        String jSONString = vehicleMd5Array.toJSONString();
        AMapLog.debug("infoservice.miniapp", CarOwnerPlugin.TAG, "vehicle digits: ".concat(String.valueOf(jSONString)));
        String str = ant.a;
        String str2 = TextUtils.isEmpty(ant.u) ? ant.u : "";
        String hostAppId = TinyAppParamUtils.getHostAppId(h5Event.getH5page());
        if (TextUtils.isEmpty(hostAppId)) {
            AMapLog.debug("infoservice.miniapp", CarOwnerPlugin.TAG, "can't find appid in h5Page params");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) str);
        jSONObject.put((String) Key.ALIPAY_UID, (Object) str2);
        jSONObject.put((String) "appId", (Object) hostAppId);
        jSONObject.put((String) "carInfoJsonStr", (Object) jSONString);
        callMTop(h5Event, "mtop.autonavi.miniprogram.userCarOauth.listAndCorrect", jSONObject, callback);
    }

    public static void authVehicles(ant ant, H5Event h5Event, Collection<MiniAppVehicle> collection, JSONArray jSONArray, Callback callback) {
        JSONArray vehicleMd5Array = getVehicleMd5Array(collection, jSONArray);
        if (vehicleMd5Array == null) {
            callback.onResponse(new JSONObject());
            return;
        }
        String jSONString = vehicleMd5Array.toJSONString();
        String str = ant.a;
        String str2 = TextUtils.isEmpty(ant.u) ? ant.u : "";
        String hostAppId = TinyAppParamUtils.getHostAppId(h5Event.getH5page());
        if (TextUtils.isEmpty(hostAppId)) {
            AMapLog.debug("infoservice.miniapp", CarOwnerPlugin.TAG, "can't find appid in h5Page params");
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "oauthChannel", (Object) Integer.valueOf(0));
        jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) str);
        jSONObject.put((String) Key.ALIPAY_UID, (Object) str2);
        jSONObject.put((String) "appId", (Object) hostAppId);
        jSONObject.put((String) "carInfoJsonStr", (Object) jSONString);
        callMTop(h5Event, "mtop.autonavi.miniprogram.userCarOauth.bind", jSONObject, callback);
    }

    public static boolean verifyPlateNo(String str) {
        return Pattern.compile("^[京津沪渝冀豫黑蒙辽吉新云藏陕粤桂苏赣闽浙皖鲁晋湘鄂甘宁贵川琼青][A-Z][A-Z0-9]{5,6}$").matcher(str).find();
    }

    public static boolean verifyAlphaDigit(String str) {
        return Pattern.compile("^[A-Z0-9]+$").matcher(str).find();
    }

    private static void callMTop(H5Event h5Event, String str, JSONObject jSONObject, final Callback callback) {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put((String) c.n, (Object) str);
        jSONObject2.put((String) c.m, (Object) "1.0");
        jSONObject2.put((String) "needEcodeSign", (Object) Boolean.TRUE);
        jSONObject2.put((String) "usePost", (Object) Boolean.TRUE);
        jSONObject2.put((String) "data", (Object) jSONObject);
        Nebula.getDispatcher().dispatch(new Builder().action("mtop").param(jSONObject2).target(h5Event.getTarget()).build(), new H5BaseBridgeContext() {
            public final boolean sendBack(JSONObject jSONObject, boolean z) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (jSONObject2 != null) {
                    callback.onResponse(jSONObject2);
                } else {
                    callback.onResponse(new JSONObject());
                }
                return false;
            }
        });
    }

    public static JSONArray getVehicleMd5Array(Collection<MiniAppVehicle> collection, JSONArray jSONArray) {
        if (collection == null || collection.size() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (MiniAppVehicle next : collection) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "plateNo", (Object) next.plateNo);
            jSONObject.put((String) "carInfoEncrypt", (Object) getVehicleDigit(next, jSONArray));
            jSONArray2.add(jSONObject);
        }
        return jSONArray2;
    }

    public static String getVehicleDigit(MiniAppVehicle miniAppVehicle, JSONArray jSONArray) {
        if (miniAppVehicle == null || TextUtils.isEmpty(miniAppVehicle.plateNo)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (jSONArray.contains(OPTIONAL_ITEM_ENGINE_NO)) {
            sb.append("engineNo=");
            sb.append(miniAppVehicle.engineNo);
            sb.append('&');
        }
        sb.append("plateNo=");
        sb.append(miniAppVehicle.plateNo);
        sb.append('&');
        sb.append("powerType=");
        sb.append(miniAppVehicle.powerType.intValue() == 0 ? "" : miniAppVehicle.powerType);
        sb.append('&');
        sb.append("vehicleType=");
        sb.append(miniAppVehicle.vehicleType);
        if (jSONArray.contains(OPTIONAL_ITEM_VIN)) {
            sb.append('&');
            sb.append("vin=");
            sb.append(miniAppVehicle.vin);
        }
        return agy.a(sb.toString());
    }

    public static int transMiniAppTypeToCarTypeMinor(int i) {
        if (i >= 10) {
            return i % 10;
        }
        return 0;
    }

    public static Car transMiniAppVehicleToCar(MiniAppVehicle miniAppVehicle) {
        Car car = new Car();
        transMiniAppVehicleToCar(miniAppVehicle, car);
        return car;
    }

    public static void mergeCarInfo(Car car, Car car2) {
        if (car2 != null && car != null) {
            car2.plateNum = car.plateNum;
            car2.vehicleType = car.vehicleType;
            car2.createTime = car.createTime;
            car2.updateTime = System.currentTimeMillis() / 1000;
            car2.vehicleCode = car.vehicleCode;
            car2.vehicleMsg = car.vehicleMsg;
            car2.vehicleLogo = car.vehicleLogo;
            car2.engineNum = car.engineNum;
            car2.frameNum = car.frameNum;
            car2.telphone = car.telphone;
            car2.validityPeriod = car.validityPeriod;
            car2.ocrRequestId = car.ocrRequestId;
            car2.violationReminder = car.violationReminder;
            car2.checkReminder = car.checkReminder;
            car2.limitReminder = car.limitReminder;
            car2.truckAvoidWeightLimit = car.truckAvoidWeightLimit;
            car2.vehiclePowerType = car.vehiclePowerType;
            car2.truckType = car.truckType;
            car2.length = car.length;
            car2.width = car.width;
            car2.height = car.height;
            car2.capacity = car.capacity;
            car2.weight = car.weight;
            car2.axleNum = car.axleNum;
            car2.uid = car.uid;
            car2.sourcePage = car.sourcePage;
            car2.from = car.from;
        }
    }

    public static void transMiniAppVehicleToCar(MiniAppVehicle miniAppVehicle, Car car) {
        int i;
        int i2;
        car.plateNum = miniAppVehicle.plateNo == null ? car.plateNum : miniAppVehicle.plateNo.toUpperCase();
        car.engineNum = miniAppVehicle.engineNo == null ? car.engineNum : miniAppVehicle.engineNo.toUpperCase();
        car.vehiclePowerType = miniAppVehicle.powerType == null ? car.vehiclePowerType : miniAppVehicle.powerType.intValue();
        car.frameNum = miniAppVehicle.vin == null ? car.frameNum : miniAppVehicle.vin.toUpperCase();
        if (miniAppVehicle.vehicleType == null) {
            i = car.vehicleType;
        } else {
            i = transMiniAppTypeToCarTypeMajor(miniAppVehicle.vehicleType.intValue());
        }
        car.vehicleType = i;
        if (miniAppVehicle.vehicleType == null) {
            i2 = car.truckType;
        } else {
            i2 = transMiniAppTypeToCarTypeMinor(miniAppVehicle.vehicleType.intValue());
        }
        car.truckType = i2;
    }

    public static MiniAppVehicle transCarToMiniAppVehicle(Car car) {
        MiniAppVehicle miniAppVehicle = new MiniAppVehicle();
        miniAppVehicle.plateNo = car.plateNum;
        miniAppVehicle.engineNo = car.engineNum;
        miniAppVehicle.powerType = Integer.valueOf(car.vehiclePowerType);
        miniAppVehicle.vin = car.frameNum;
        miniAppVehicle.vehicleType = Integer.valueOf(transCarTypeToMiniAppType(car.vehicleType, car.truckType));
        return miniAppVehicle;
    }

    public static int transMiniAppTypeToCarTypeMajor(int i) {
        if (i >= 10) {
            i /= 10;
        }
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }
}

package com.autonavi.minimap.ajx3;

import android.text.TextUtils;
import com.autonavi.map.db.model.Car;
import org.json.JSONException;
import org.json.JSONObject;

public class CarInfo {
    private static final String ADD_LICENSE_MANUALLY = "addLicenseManually";
    private static final String ADD_TYPE = "addType";
    private static final String AXLE_NUM = "axleNum";
    private static final String BRAND_LEVEL = "brandLevel";
    private static final String BRAND_TYPE = "brandType";
    private static final String CAMERA_PERMISSION = "cameraPermission";
    private static final String CAR_INFO = "carInfo";
    private static final String CHECK_REMINDER = "checkReminder";
    private static final String CREATE_TIME = "createTime";
    private static final String DATA = "data";
    private static final String ENGINE_NUM = "engineNum";
    private static final String ENTRY_TYPE = "entryType";
    private static final String FRAME_NUM = "frameNum";
    private static final String FROM_PAGE = "fromPage";
    private static final String LIMIT_REMINDER = "limitReminder";
    private static final String NEED_OPEN_URI = "needOpenUri";
    private static final String OCR_REQUESTID = "ocrRequestId";
    private static final String OP_TYPE = "opType";
    private static final String OUT_URI = "outUri";
    private static final String PERFECT_TRUCK = "perfectTruck";
    private static final String PLATE_NUM = "plateNum";
    private static final String TELEPHONE = "telphone";
    private static final String TRUCK_AVOID_WEIGHT_LIMIT = "truckAvoidWeightLimit";
    private static final String TRUCK_CAPACITY = "capacity";
    private static final String TRUCK_HEIGHT = "height";
    private static final String TRUCK_INFO = "truckInfo";
    private static final String TRUCK_LENGTH = "length";
    private static final String TRUCK_TYPE = "truckType";
    private static final String TRUCK_WEIGHT = "weight";
    private static final String TRUCK_WIDTH = "width";
    private static final String UPDATE_TIME = "updateTime";
    private static final String VALIDITY_PERIOD = "validityPeriod";
    private static final String VEHICLE_CODE = "vehicleCode";
    private static final String VEHICLE_LOGO = "vehicleLogo";
    private static final String VEHICLE_MSG = "vehicleMsg";
    private static final String VEHICLE_POWER_TYPE = "vehiclePowerType";
    private static final String VEHICLE_TYPE = "vehicleType";
    private static final String VIOLATION_REMINDER = "violationReminder";
    public String addLicenseManually;
    public String addType;
    public String brandLevel = "";
    public String brandType = "";
    public String cameraPermission;
    public Car car = new Car();
    public String entryType = "";
    public String fromPage;
    public String needOpenUri;
    public String opType = "";
    public String outUri;
    public String perfectTruck;
    public String plateNum = "";

    public static CarInfo JsonToCarInfo(String str) {
        JSONObject jSONObject;
        CarInfo carInfo = new CarInfo();
        if (TextUtils.isEmpty(str)) {
            return carInfo;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(str).getJSONObject("data");
            carInfo.entryType = jSONObject2.optString(ENTRY_TYPE);
            carInfo.opType = jSONObject2.optString(OP_TYPE);
            carInfo.fromPage = jSONObject2.optString(FROM_PAGE);
            carInfo.cameraPermission = jSONObject2.optString(CAMERA_PERMISSION);
            carInfo.needOpenUri = jSONObject2.optString(NEED_OPEN_URI);
            carInfo.outUri = jSONObject2.optString(OUT_URI);
            carInfo.addLicenseManually = jSONObject2.optString(ADD_LICENSE_MANUALLY);
            carInfo.addType = jSONObject2.optString(ADD_TYPE);
            jSONObject = jSONObject2.optJSONObject(CAR_INFO);
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject == null) {
            return carInfo;
        }
        String optString = jSONObject.optString(PLATE_NUM);
        if (TextUtils.isEmpty(optString)) {
            optString = carInfo.plateNum;
        }
        Car car2 = new Car();
        if (!TextUtils.isEmpty(optString)) {
            carInfo.plateNum = jSONObject.optString(PLATE_NUM);
            car2.plateNum = carInfo.plateNum;
            car2.vehicleCode = jSONObject.optString(VEHICLE_CODE);
            car2.engineNum = jSONObject.optString(ENGINE_NUM);
            car2.frameNum = jSONObject.optString(FRAME_NUM);
            car2.telphone = jSONObject.optString(TELEPHONE);
            car2.validityPeriod = jSONObject.optString(VALIDITY_PERIOD);
            car2.ocrRequestId = jSONObject.optString(OCR_REQUESTID);
            car2.vehicleMsg = jSONObject.optString(VEHICLE_MSG);
            car2.vehicleLogo = jSONObject.optString(VEHICLE_LOGO);
            car2.vehicleType = jSONObject.optInt(VEHICLE_TYPE);
            car2.violationReminder = jSONObject.optInt(VIOLATION_REMINDER);
            car2.checkReminder = jSONObject.optInt(CHECK_REMINDER);
            car2.limitReminder = jSONObject.optInt(LIMIT_REMINDER);
            car2.truckAvoidWeightLimit = jSONObject.optInt(TRUCK_AVOID_WEIGHT_LIMIT);
            car2.createTime = jSONObject.optLong(CREATE_TIME);
            car2.updateTime = jSONObject.optLong(UPDATE_TIME);
            car2.vehiclePowerType = jSONObject.optInt(VEHICLE_POWER_TYPE);
            if (car2.vehicleType == 2) {
                JSONObject optJSONObject = jSONObject.optJSONObject(TRUCK_INFO);
                car2.truckType = optJSONObject.optInt(TRUCK_TYPE);
                car2.length = optJSONObject.optString(TRUCK_LENGTH);
                car2.width = optJSONObject.optString("width");
                car2.height = optJSONObject.optString("height");
                car2.capacity = optJSONObject.optString(TRUCK_CAPACITY);
                car2.weight = optJSONObject.optString("weight");
                car2.axleNum = optJSONObject.optString(AXLE_NUM);
            }
        }
        carInfo.car = car2;
        return carInfo;
    }

    public static JSONObject CarInfoToJson(CarInfo carInfo) {
        JSONObject jSONObject = new JSONObject();
        if (carInfo == null) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("data", jSONObject2);
            jSONObject2.put(PLATE_NUM, carInfo.plateNum);
            jSONObject2.put(ENTRY_TYPE, carInfo.entryType);
            jSONObject2.put(OP_TYPE, carInfo.opType);
            jSONObject2.put(FROM_PAGE, carInfo.fromPage);
            jSONObject2.put(CAMERA_PERMISSION, carInfo.cameraPermission);
            jSONObject2.put(NEED_OPEN_URI, carInfo.needOpenUri);
            jSONObject2.put(OUT_URI, carInfo.outUri);
            jSONObject2.put(ADD_LICENSE_MANUALLY, carInfo.addLicenseManually);
            jSONObject2.put(ADD_TYPE, carInfo.addType);
            jSONObject2.put(PERFECT_TRUCK, carInfo.perfectTruck);
            jSONObject2.put(BRAND_TYPE, carInfo.brandType);
            jSONObject2.put(BRAND_LEVEL, carInfo.brandLevel);
        } catch (Exception unused) {
        }
        JSONObject jSONObject3 = new JSONObject();
        Car car2 = carInfo.car;
        try {
            jSONObject2.put(CAR_INFO, jSONObject3);
            if (car2 == null) {
                return jSONObject;
            }
            jSONObject3.put(PLATE_NUM, car2.plateNum);
            jSONObject3.put(VEHICLE_CODE, car2.vehicleCode);
            jSONObject3.put(ENGINE_NUM, car2.engineNum);
            jSONObject3.put(FRAME_NUM, car2.frameNum);
            jSONObject3.put(TELEPHONE, car2.telphone);
            jSONObject3.put(VALIDITY_PERIOD, car2.validityPeriod);
            jSONObject3.put(OCR_REQUESTID, car2.ocrRequestId);
            jSONObject3.put(VEHICLE_MSG, car2.vehicleMsg);
            jSONObject3.put(VEHICLE_LOGO, car2.vehicleLogo);
            jSONObject3.put(VEHICLE_TYPE, car2.vehicleType);
            jSONObject3.put(VIOLATION_REMINDER, car2.violationReminder);
            jSONObject3.put(CHECK_REMINDER, car2.checkReminder);
            jSONObject3.put(LIMIT_REMINDER, car2.limitReminder);
            jSONObject3.put(TRUCK_AVOID_WEIGHT_LIMIT, car2.truckAvoidWeightLimit);
            jSONObject3.put(CREATE_TIME, car2.createTime);
            jSONObject3.put(UPDATE_TIME, car2.updateTime);
            jSONObject3.put(VEHICLE_POWER_TYPE, car2.vehiclePowerType);
            if (car2.vehicleType > 0) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put(TRUCK_TYPE, car2.truckType);
                jSONObject4.put(TRUCK_LENGTH, car2.length);
                jSONObject4.put("width", car2.width);
                jSONObject4.put("height", car2.height);
                jSONObject4.put(TRUCK_CAPACITY, car2.capacity);
                jSONObject4.put("weight", car2.weight);
                jSONObject4.put(AXLE_NUM, car2.axleNum);
                jSONObject3.put(TRUCK_INFO, jSONObject4);
            }
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

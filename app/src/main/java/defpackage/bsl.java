package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;
import com.amap.bundle.tripgroup.api.IVehicleInfoEvent;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.db.model.Car;
import com.autonavi.sync.Consts;
import com.autonavi.sync.GirfSyncServiceSDK.GirfSyncService;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bsl reason: default package */
/* compiled from: CarHelper */
public final class bsl implements atm {
    private static bsl b;
    public GirfSyncService a = bin.a.W();

    private static int d(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1287) {
            return 5;
        }
        switch (i) {
            case Consts.GIRF_RET_CAR_NOT_INITED /*1681*/:
                return 8;
            case Consts.GIRF_RET_CAR_MAX_EXCEEDED /*1682*/:
                return 2;
            case Consts.GIRF_RET_CAR_ALREADY_EXISTED /*1683*/:
                return 1;
            case Consts.GIRF_RET_CAR_INVALID_CAR_JSON /*1684*/:
                return 7;
            case Consts.GIRF_RET_CAR_TYPE_INVALID /*1685*/:
            case Consts.GIRF_RET_CAR_PLATENUM_INVALID /*1686*/:
            case Consts.GIRF_RET_CAR_TRUCK_SUB_TYPE_INVALID /*1687*/:
            case Consts.GIRF_RET_CAR_CREATE_TIME_INVALID /*1688*/:
            case Consts.GIRF_RET_CAR_UPDATE_TIME_INVALID /*1689*/:
                return 3;
            default:
                return i;
        }
    }

    private bsl() {
    }

    public static synchronized bsl a() {
        bsl bsl;
        synchronized (bsl.class) {
            try {
                if (b == null) {
                    b = new bsl();
                }
                bsl = b;
            }
        }
        return bsl;
    }

    public final int a(Car car) {
        if (this.a == null) {
            return 8;
        }
        if (!c(car)) {
            return 6;
        }
        int addCar = this.a.addCar(agy.a(car.plateNum), b(car), 1);
        if (b()) {
            bnm.a(true);
        }
        if (addCar == 0) {
            e(1);
        }
        return d(addCar);
    }

    public final int a(Car car, String str) {
        String str2;
        if (this.a == null) {
            return 8;
        }
        if (!c(car)) {
            return 6;
        }
        String a2 = agy.a(car.plateNum);
        if (!TextUtils.isEmpty(str)) {
            str2 = agy.a(str);
        } else {
            str2 = "";
        }
        int updateCar = this.a.updateCar(a2, b(car), str2, str, 1);
        if (b()) {
            bnm.a(true);
        }
        if (updateCar == 0) {
            e(1);
        }
        return d(updateCar);
    }

    public final int a(String str, int i) {
        if (this.a == null) {
            return 8;
        }
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        int deleteCar = this.a.deleteCar(agy.a(str), str, i, 1);
        if (deleteCar == 0) {
            e(2);
        }
        return d(deleteCar);
    }

    public final List<Car> a(int i) {
        if (this.a == null) {
            return null;
        }
        return a(this.a.getCarList(i));
    }

    public final Car a(String str) {
        if (this.a == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return b(this.a.getCar(agy.a(str)));
    }

    public final String c(int i) {
        if (this.a == null) {
            return null;
        }
        List<String> carList = this.a.getCarList(i);
        JSONArray jSONArray = new JSONArray();
        for (String jSONObject : carList) {
            try {
                jSONArray.put(new JSONObject(jSONObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONArray.toString();
    }

    public final int a(String str, Car car) {
        if (this.a == null) {
            return 8;
        }
        if (car == null || !c(car)) {
            return 6;
        }
        String b2 = b(car);
        if (TextUtils.isEmpty(b2)) {
            return 7;
        }
        return d(bin.a.b(str, "901", agy.a(car.plateNum), b2));
    }

    public final int b(int i, String str) {
        if (this.a == null) {
            return 8;
        }
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        String str2 = (i == 1 || i == 2) ? i == 1 ? "303" : "305" : null;
        if (str2 == null) {
            return 6;
        }
        return d(a(str2, str));
    }

    public final int a(int i, String str) {
        if (this.a == null) {
            return 8;
        }
        if (TextUtils.isEmpty(str)) {
            return 6;
        }
        return d(this.a.setOftenUsedCar(agy.a(str), str, i));
    }

    public final Car b(int i) {
        if (this.a == null) {
            return null;
        }
        return a(c(this.a.getOftenUsedCar(i)));
    }

    private static boolean c(Car car) {
        if (car == null || TextUtils.isEmpty(car.plateNum)) {
            return false;
        }
        if (car.vehicleType != 1 && car.vehicleType != 2) {
            return false;
        }
        if (car.createTime == 0) {
            car.createTime = System.currentTimeMillis() / 1000;
        }
        if (car.updateTime == 0) {
            car.updateTime = car.createTime;
        }
        return true;
    }

    private static int a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return 6;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("value", str2);
            jSONObject.put("version", 1);
            return d(bin.a.c("201", str, jSONObject.toString(), 0));
        } catch (JSONException e) {
            e.printStackTrace();
            return 7;
        }
    }

    private static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new JSONObject(str).optString("value");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Car b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Car car = new Car();
            car.plateNum = a(jSONObject, (String) "plateNum");
            car.vehicleType = jSONObject.optInt("vehicleType");
            car.vehicleCode = a(jSONObject, (String) "vehicleCode");
            car.vehicleMsg = a(jSONObject, (String) "vehicleMsg");
            car.vehicleLogo = a(jSONObject, (String) "vehicleLogo");
            car.engineNum = a(jSONObject, (String) "engineNum");
            car.frameNum = a(jSONObject, (String) "frameNum");
            car.telphone = a(jSONObject, (String) "telphone");
            car.validityPeriod = a(jSONObject, (String) "validityPeriod");
            car.ocrRequestId = a(jSONObject, (String) "ocrRequestId");
            car.violationReminder = jSONObject.optInt("violationReminder");
            car.checkReminder = jSONObject.optInt("checkReminder");
            car.limitReminder = jSONObject.optInt("limitReminder");
            car.truckAvoidWeightLimit = jSONObject.optInt("truckAvoidWeightLimit");
            car.createTime = (long) jSONObject.optInt("createTime");
            car.updateTime = (long) jSONObject.optInt("updateTime");
            car.vehiclePowerType = jSONObject.optInt("vehiclePowerType");
            if (car.vehicleType == 2) {
                JSONObject optJSONObject = jSONObject.optJSONObject("truckInfo");
                if (optJSONObject != null) {
                    car.truckType = optJSONObject.optInt("truckType");
                    car.length = a(optJSONObject, (String) "length");
                    car.width = a(optJSONObject, (String) "width");
                    car.height = a(optJSONObject, (String) "height");
                    car.capacity = a(optJSONObject, (String) "capacity");
                    car.weight = a(optJSONObject, (String) MetaInfoXmlParser.KEY_VALVE_WEIGHT);
                    car.axleNum = a(optJSONObject, (String) "axleNum");
                }
            }
            return car;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String b(Car car) {
        if (car == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("plateNum", car.plateNum);
            jSONObject.put("vehicleType", car.vehicleType);
            jSONObject.put("vehicleCode", car.vehicleCode);
            jSONObject.put("vehicleMsg", car.vehicleMsg);
            jSONObject.put("vehicleLogo", car.vehicleLogo);
            jSONObject.put("engineNum", car.engineNum);
            jSONObject.put("frameNum", car.frameNum);
            jSONObject.put("telphone", car.telphone);
            jSONObject.put("validityPeriod", car.validityPeriod);
            jSONObject.put("ocrRequestId", car.ocrRequestId);
            jSONObject.put("violationReminder", car.violationReminder);
            jSONObject.put("checkReminder", car.checkReminder);
            jSONObject.put("limitReminder", car.limitReminder);
            jSONObject.put("truckAvoidWeightLimit", car.truckAvoidWeightLimit);
            jSONObject.put("createTime", car.createTime);
            jSONObject.put("updateTime", car.updateTime);
            jSONObject.put("vehiclePowerType", car.vehiclePowerType);
            if (car.vehicleType == 2) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("truckType", car.truckType);
                jSONObject2.put("length", car.length);
                jSONObject2.put("width", car.width);
                jSONObject2.put("height", car.height);
                jSONObject2.put("capacity", car.capacity);
                jSONObject2.put(MetaInfoXmlParser.KEY_VALVE_WEIGHT, car.weight);
                jSONObject2.put("axleNum", car.axleNum);
                jSONObject.put("truckInfo", jSONObject2);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Car> a(List<String> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String b2 : list) {
            Car b3 = b(b2);
            if (b3 != null) {
                arrayList.add(b3);
            }
        }
        return arrayList;
    }

    private static String a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return "";
        }
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String optString = jSONObject.optString(str);
        return optString.equals("null") ? "" : optString;
    }

    private static void e(int i) {
        IVehicleInfoEvent iVehicleInfoEvent = (IVehicleInfoEvent) ank.a(IVehicleInfoEvent.class);
        if (iVehicleInfoEvent != null) {
            iVehicleInfoEvent.onVehicleInfoChanged(i);
        }
    }

    private static boolean b() {
        if (bin.a.a()) {
            return false;
        }
        AMapAppGlobal.getApplication();
        bst.a();
        int a2 = bst.a(bin.a.Z());
        AMapAppGlobal.getApplication();
        bss.a();
        int a3 = a2 + bss.a(bin.a.Z()) + bin.a.r("901");
        if (a3 <= 0 || (a3 != 3 && a3 % 5 != 0)) {
            return false;
        }
        return true;
    }
}

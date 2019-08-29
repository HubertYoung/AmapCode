package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.map.db.model.Car;

/* renamed from: tk reason: default package */
/* compiled from: DriveTruckUtil */
public final class tk {
    public static boolean a() {
        Car carTruckInfo = DriveUtil.getCarTruckInfo();
        return carTruckInfo == null || (!TextUtils.isEmpty(carTruckInfo.length) && !TextUtils.isEmpty(carTruckInfo.width) && !TextUtils.isEmpty(carTruckInfo.height) && !TextUtils.isEmpty(carTruckInfo.capacity) && !TextUtils.isEmpty(carTruckInfo.weight) && !TextUtils.isEmpty(carTruckInfo.axleNum));
    }

    public static String b() {
        Car carTruckInfo = DriveUtil.getCarTruckInfo();
        return (carTruckInfo == null || carTruckInfo.vehicleType != 2) ? "" : carTruckInfo.plateNum;
    }

    public static boolean c() {
        Car carTruckInfo = DriveUtil.getCarTruckInfo();
        if (carTruckInfo == null) {
            return false;
        }
        try {
            boolean z = Float.parseFloat(carTruckInfo.weight) < Float.parseFloat(carTruckInfo.capacity);
            if (((double) Float.parseFloat(carTruckInfo.width)) > 2.55d) {
                z = true;
            }
            if (Float.parseFloat(carTruckInfo.height) > 4.0f) {
                return true;
            }
            return z;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static void a(String str, String str2) {
        bic.a((String) "truck_param_invalid").set(str2, str);
    }

    public static String a(String str) {
        WebStorage a = bic.a((String) "truck_param_invalid");
        if (a == null) {
            return "0";
        }
        String str2 = a.get(str);
        return TextUtils.isEmpty(str2) ? "0" : str2;
    }
}

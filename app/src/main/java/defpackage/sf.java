package defpackage;

import android.text.TextUtils;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.map.db.model.Car;

/* renamed from: sf reason: default package */
/* compiled from: TruckPlateInputUtil */
public final class sf {
    public static String a(String str) {
        int i;
        if (str == null || !str.contains(".")) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                i = 0;
            }
            return String.valueOf(i);
        } else if (str.startsWith(".")) {
            String substring = str.substring(str.indexOf(46), str.length());
            StringBuilder sb = new StringBuilder(substring);
            char[] charArray = substring.toCharArray();
            int length = charArray.length - 1;
            while (length >= 0 && (charArray[length] == '0' || (length == 0 && charArray[length] == '.'))) {
                sb.deleteCharAt(length);
                length--;
            }
            if (TextUtils.isEmpty(sb.toString())) {
                return "0";
            }
            StringBuilder sb2 = new StringBuilder("0");
            sb2.append(sb.toString());
            return sb2.toString();
        } else {
            String substring2 = str.substring(0, str.indexOf(46));
            String substring3 = str.substring(str.indexOf(46), str.length());
            StringBuilder sb3 = new StringBuilder(substring3);
            char[] charArray2 = substring3.toCharArray();
            int length2 = charArray2.length - 1;
            while (length2 >= 0 && (charArray2[length2] == '0' || (length2 == 0 && charArray2[length2] == '.'))) {
                sb3.deleteCharAt(length2);
                length2--;
            }
            if (TextUtils.isEmpty(sb3.toString())) {
                return substring2;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(substring2);
            sb4.append(sb3.toString());
            return sb4.toString();
        }
    }

    public static String[] a() {
        Car carTruckInfo = DriveUtil.getCarTruckInfo();
        if (carTruckInfo == null) {
            return null;
        }
        String[] strArr = new String[2];
        String truckType = DriveUtil.getTruckType(carTruckInfo.truckType);
        String str = "";
        if (!TextUtils.isEmpty(truckType)) {
            str = " • ".concat(String.valueOf(truckType));
        }
        strArr[0] = str;
        String str2 = TextUtils.isEmpty(carTruckInfo.length) ? "0" : carTruckInfo.length;
        String str3 = TextUtils.isEmpty(carTruckInfo.width) ? "0" : carTruckInfo.width;
        String str4 = TextUtils.isEmpty(carTruckInfo.height) ? "0" : carTruckInfo.height;
        String str5 = TextUtils.isEmpty(carTruckInfo.weight) ? "0" : carTruckInfo.weight;
        String a = a(str4);
        String a2 = a(str5);
        StringBuilder sb = new StringBuilder("长");
        sb.append(str2);
        sb.append("米 • 宽");
        sb.append(str3);
        sb.append("米 • 高");
        sb.append(a);
        sb.append("米 • 重");
        sb.append(a2);
        sb.append("吨");
        strArr[1] = sb.toString();
        return strArr;
    }
}

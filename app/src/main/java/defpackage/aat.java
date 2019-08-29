package defpackage;

import android.os.Build;
import android.text.TextUtils;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.server.aos.serverkey;

/* renamed from: aat reason: default package */
/* compiled from: CdnMacUtil */
public final class aat {
    private static String a;
    private static String[] b = {".jpg", ".png", ".gif", ".apk", ".jpeg", ".JPG", FilePathHelper.SUFFIX_DOT_ZIP, ".js", ".css"};
    private static String[] c = {"amap.com", "alibaba.com", "alicdn.com", "autonavi.com"};
    private static String[] d = {".mp3", PhotoParam.VIDEO_SUFFIX};

    public static String a() {
        String str;
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        StringBuffer stringBuffer = new StringBuffer();
        GeoPoint a2 = aaf.f() == null ? null : aaf.f().a();
        stringBuffer.append("diu:");
        stringBuffer.append(NetworkParam.getDiu());
        stringBuffer.append(";");
        stringBuffer.append("adiu:");
        stringBuffer.append(NetworkParam.getAdiu());
        stringBuffer.append(";");
        stringBuffer.append("tid:");
        stringBuffer.append(NetworkParam.getTaobaoID());
        stringBuffer.append(";");
        stringBuffer.append("div:");
        stringBuffer.append(NetworkParam.getDiv());
        stringBuffer.append(";");
        stringBuffer.append("dibv:");
        stringBuffer.append(NetworkParam.getDibv());
        stringBuffer.append(";");
        stringBuffer.append("lat:");
        stringBuffer.append(a2 == null ? "" : Double.valueOf(a2.getLatitude()));
        stringBuffer.append(";");
        stringBuffer.append("lon:");
        stringBuffer.append(a2 == null ? "" : Double.valueOf(a2.getLongitude()));
        stringBuffer.append(";");
        stringBuffer.append("manufacture:");
        stringBuffer.append(Build.MANUFACTURER);
        stringBuffer.append(";");
        stringBuffer.append("model:");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append(";");
        stringBuffer.append("networktype:");
        switch (NetworkReachability.c()) {
            case G2:
                str = "2G";
                break;
            case G3:
                str = "3G";
                break;
            case G4:
                str = "4G";
                break;
            case WIFI:
                str = "wifi";
                break;
            default:
                str = "unknown";
                break;
        }
        stringBuffer.append(str);
        stringBuffer.append(";");
        DeviceInfo instance = DeviceInfo.getInstance(AMapAppGlobal.getApplication());
        stringBuffer.append("carrier:");
        stringBuffer.append(instance.getMcc());
        stringBuffer.append("-");
        stringBuffer.append(instance.getMnc());
        String amapEncode = serverkey.amapEncode(stringBuffer.toString());
        a = amapEncode;
        return amapEncode;
    }
}

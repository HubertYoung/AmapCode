package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.nebula.resourcehandler.H5ResourceHandlerUtil;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.taobao.accs.common.Constants;
import java.util.Map;

/* renamed from: ce reason: default package */
/* compiled from: DispatchParamBuilder */
final class ce {
    static String a(cg cgVar, Map<String, String> map) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(ci.d(map.get("appkey")));
        sb.append("&");
        sb.append(ci.d(map.get("domain")));
        sb.append("&");
        sb.append(ci.d(map.get("appName")));
        sb.append("&");
        sb.append(ci.d(map.get("appVersion")));
        sb.append("&");
        sb.append(ci.d(map.get("bssid")));
        sb.append("&");
        sb.append(ci.d(map.get("channel")));
        sb.append("&");
        sb.append(ci.d(map.get("deviceId")));
        sb.append("&");
        sb.append(ci.d(map.get("lat")));
        sb.append("&");
        sb.append(ci.d(map.get(CameraControllerManager.MY_POILOCATION_LNG)));
        sb.append("&");
        sb.append(ci.d(map.get("machine")));
        sb.append("&");
        sb.append(ci.d(map.get("netType")));
        sb.append("&");
        sb.append(ci.d(map.get(H5ResourceHandlerUtil.OTHER)));
        sb.append("&");
        sb.append(ci.d(map.get("platform")));
        sb.append("&");
        sb.append(ci.d(map.get("platformVersion")));
        sb.append("&");
        sb.append(ci.d(map.get("preIp")));
        sb.append("&");
        sb.append(ci.d(map.get(Constants.KEY_SID)));
        sb.append("&");
        sb.append(ci.d(map.get(LogItem.MM_C15_K4_TIME)));
        sb.append("&");
        sb.append(ci.d(map.get("v")));
        sb.append("&");
        sb.append(ci.d(map.get("signType")));
        try {
            return cgVar.a(sb.toString());
        } catch (Exception unused) {
            cl.e("amdc.DispatchParamBuilder", "get sign failed", null, new Object[0]);
            return null;
        }
    }
}

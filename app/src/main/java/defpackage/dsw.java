package defpackage;

import android.os.Build.VERSION;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.location.LocationInstrument;
import com.ut.device.UTDevice;

/* renamed from: dsw reason: default package */
/* compiled from: GetUserInfo */
public final class dsw {
    public static JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "os_version", (Object) VERSION.RELEASE);
        jSONObject.put((String) "client_version", (Object) a.a().a);
        jSONObject.put((String) "tid", (Object) UTDevice.getUtdid(AMapPageUtil.getAppContext()));
        jSONObject.put((String) LocationParams.PARA_COMMON_DIU, (Object) NetworkParam.getDiu());
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(0);
        StringBuffer stringBuffer = new StringBuffer("x=");
        stringBuffer.append(latestPosition.x);
        stringBuffer.append(",y=");
        stringBuffer.append(latestPosition.y);
        jSONObject.put((String) "poi", (Object) stringBuffer.toString());
        return jSONObject;
    }
}

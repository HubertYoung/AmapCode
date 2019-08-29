package defpackage;

import android.util.Base64;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.ae.data.DataService;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import java.io.File;
import org.json.JSONObject;

/* renamed from: rs reason: default package */
/* compiled from: RealCityUtil */
public final class rs {
    public static String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("portrait", a("3dportrait.xml"));
            jSONObject.put(H5Param.LONG_LANDSCAPE, a("3dlandscape.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    private static String a(String str) {
        byte[] bArr;
        String dataPath = DataService.getInstance().getDataPathManager().getDataPath();
        StringBuilder sb = new StringBuilder();
        sb.append(dataPath);
        sb.append(str);
        File file = new File(sb.toString());
        if (file.exists()) {
            bArr = amz.a(file.getAbsolutePath());
        } else {
            bArr = GLMapUtil.decodeAssetResData(AMapAppGlobal.getApplication(), "map_assets/".concat(String.valueOf(str)));
        }
        return new String(Base64.encode(bArr, 0));
    }
}

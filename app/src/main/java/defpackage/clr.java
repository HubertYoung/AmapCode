package defpackage;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.alc.inter.IALCNetwork;
import com.autonavi.minimap.app.init.amaplog.network.ALCUploadParam;

/* renamed from: clr reason: default package */
/* compiled from: AMAPLogNetwork */
public final class clr implements IALCNetwork {
    public final String requestSynchronous(String str, String str2) {
        if (bno.a) {
            AMapLog.debug("paas.main", "AMAPLogNetwork", "requestSynchronous,content:".concat(String.valueOf(str)));
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        AosPostRequest b = aax.b(new ALCUploadParam(str));
        try {
            yq.a();
            AosStringResponse aosStringResponse = (AosStringResponse) yq.a((AosRequest) b, AosStringResponse.class);
            if (aosStringResponse == null) {
                return "";
            }
            if (bno.a) {
                AMapLog.debug("paas.main", "AMAPLogNetwork", (String) aosStringResponse.getResult());
            }
            return (String) aosStringResponse.getResult();
        } catch (Throwable th) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("Throwable:");
                sb.append(Log.getStackTraceString(th));
                AMapLog.debug("paas.main", "AMAPLogNetwork", sb.toString());
            }
            return null;
        }
    }
}

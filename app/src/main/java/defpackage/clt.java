package defpackage;

import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.jni.alc.inter.IALCRecordNetwork;
import com.autonavi.minimap.app.init.amaplog.network.ALCRecordUploadParam;

/* renamed from: clt reason: default package */
/* compiled from: AMapLogAllInNetwork */
public final class clt implements IALCRecordNetwork {
    public final String requestSynchronous(String str, int i, String str2, int i2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (bno.a) {
            AMapLog.debug("paas.main", "AMapLogAllInNetwork", "requestSynchronous,msg:".concat(String.valueOf(str)));
        }
        AosPostRequest b = aax.b(new ALCRecordUploadParam(str));
        try {
            yq.a();
            AosStringResponse aosStringResponse = (AosStringResponse) yq.a((AosRequest) b, AosStringResponse.class);
            if (aosStringResponse == null) {
                return "";
            }
            if (bno.a) {
                StringBuilder sb = new StringBuilder("response:");
                sb.append((String) aosStringResponse.getResult());
                AMapLog.debug("paas.main", "AMapLogAllInNetwork", sb.toString());
            }
            return (String) aosStringResponse.getResult();
        } catch (Throwable th) {
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder("Throwable:");
                sb2.append(Log.getStackTraceString(th));
                AMapLog.debug("paas.main", "AMapLogAllInNetwork", sb2.toString());
            }
            return null;
        }
    }
}

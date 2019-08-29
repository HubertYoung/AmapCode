package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.impressionreporter.log.LbpH5LogCallback;
import com.amap.bundle.impressionreporter.log.LbpH5LogParam;
import com.amap.bundle.impressionreporter.log.OptimusH5LogCallback;
import com.amap.bundle.impressionreporter.log.OptimusH5LogParam;
import com.autonavi.annotation.BundleInterface;

@BundleInterface(vw.class)
/* renamed from: vu reason: default package */
/* compiled from: ImpressionReporterService */
public class vu extends esi implements vw {
    public final void a(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            vt vtVar = new vt(str);
            yq.a();
            yq.a((bph) vtVar, (bpl<T>) new vs<T>(i));
        }
    }

    public final void a(String str, int i) {
        OptimusH5LogParam optimusH5LogParam = new OptimusH5LogParam();
        optimusH5LogParam.msg_id = str;
        optimusH5LogParam.tag = 12;
        optimusH5LogParam.operateType = i;
        OptimusH5LogCallback optimusH5LogCallback = new OptimusH5LogCallback(str);
        AosGetRequest a = aax.a(optimusH5LogParam);
        yq.a();
        yq.a((AosRequest) a, (AosResponseCallback<T>) optimusH5LogCallback);
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            LbpH5LogParam lbpH5LogParam = new LbpH5LogParam();
            lbpH5LogParam.lbpvia = str;
            LbpH5LogCallback lbpH5LogCallback = new LbpH5LogCallback(str);
            AosGetRequest a = aax.a(lbpH5LogParam);
            yq.a();
            yq.a((AosRequest) a, (AosResponseCallback<T>) lbpH5LogCallback);
        }
    }
}

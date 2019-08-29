package defpackage;

import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.paramopt.ParamUploader$1;
import com.amap.location.sdk.fusion.LocationParams;

/* renamed from: aap reason: default package */
/* compiled from: ParamUploader */
public final class aap {
    private AosPostRequest a;

    public final void a() {
        AMapLog.e("TokenManager", " ParamUploader upload + ");
        this.a = new AosPostRequest();
        String b = aaf.b(ConfigerHelper.AOS_URL_KEY);
        AosPostRequest aosPostRequest = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("/api/parameter/upload");
        aosPostRequest.setUrl(sb.toString());
        this.a.addSignParam("channel");
        this.a.addSignParam(LocationParams.PARA_COMMON_ADIU);
        this.a.addSignParam("tid");
        String url = this.a.getUrl();
        yq.a();
        yq.a((AosRequest) this.a, (AosResponseCallback<T>) new ParamUploader$1<T>(this, url));
    }

    public final void b() {
        yq.a();
        yq.a((AosRequest) this.a);
    }
}

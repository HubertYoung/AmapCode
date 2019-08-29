package defpackage;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DrivingHistoryNetManager$1;
import com.autonavi.mine.feedbackv2.drivenavigationissues.DrivingHistoryParam;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.io.UnsupportedEncodingException;
import java.util.List;

/* renamed from: cgl reason: default package */
/* compiled from: DrivingHistoryNetManager */
public final class cgl {
    AosResponseCallbackOnUi<AosStringResponse> a;

    /* renamed from: cgl$a */
    /* compiled from: DrivingHistoryNetManager */
    public interface a<T> {
        void a();

        void a(T t);
    }

    public final void a(long j, Integer num, a<List<cgk>> aVar) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (!iAccountService.a()) {
                aVar.a(null);
                return;
            }
            AosPostRequest b = aax.b(new DrivingHistoryParam());
            JSONObject jSONObject = new JSONObject();
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            jSONObject.put((String) "ts", (Object) Long.valueOf(currentTimeMillis));
            jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) iAccountService.b());
            jSONObject.put((String) "sign", (Object) agy.a("tinfo,".concat(String.valueOf(currentTimeMillis))));
            jSONObject.put((String) "time", (Object) Long.valueOf(j));
            jSONObject.put((String) H5RpcFailResult.LIMIT, (Object) num);
            try {
                b.setBody(serverkey.amapEncode(jSONObject.toString()).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            b.setContentType("application/json");
            this.a = new DrivingHistoryNetManager$1(this, aVar);
            yq.a();
            yq.a((AosRequest) b, (AosResponseCallback<T>) this.a);
        }
    }
}

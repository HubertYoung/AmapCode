package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.adiu.internal.net.AdiuRequest$1;
import com.amap.bundle.adiu.internal.net.AdiuWrapper;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.server.aos.serverkey;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: im reason: default package */
/* compiled from: AdiuRequest */
public final class im {
    public a a;
    private final Context b;
    private final AosResponseCallback c = new AdiuRequest$1(this);

    /* renamed from: im$a */
    /* compiled from: AdiuRequest */
    public interface a {
        void a(String str);
    }

    public im(@NonNull Context context) {
        this.b = context.getApplicationContext();
    }

    public final void a(a aVar) {
        this.a = aVar;
        AosPostRequest b2 = aax.b(new AdiuWrapper());
        b2.setCommonParamStrategy(-1);
        String taobaoID = NetworkParam.getTaobaoID();
        String diu = NetworkParam.getDiu();
        String mac = NetworkParam.getMac();
        String isn = NetworkParam.getIsn();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("tid", taobaoID);
            jSONObject.put(LocationParams.PARA_COMMON_DIU, diu);
            jSONObject.put(LocationParams.PARA_COMMON_DIU2, mac);
            jSONObject.put(LocationParams.PARA_COMMON_DIU3, isn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jSONObject2 = jSONObject.toString();
        if (!TextUtils.isEmpty(jSONObject2) || !bno.a) {
            String a2 = cib.a();
            if (!TextUtils.isEmpty(a2)) {
                String amapEncode = serverkey.amapEncode(a2, jSONObject2);
                if (!TextUtils.isEmpty(amapEncode)) {
                    try {
                        String a3 = chz.a(cia.a(a2.getBytes("utf-8"), cia.a(bno.c ? "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFKU1DQHZFzmPJe6aW7I7+nca7EKalaXB70eWtrp+uahT4hP73QB5FNaoMHciU+2D6NwGyVVvC0oLauTHEcfp74tNnOjP3q3M1FEueNZ/w+6X/8KbvjAiqJ4078cxHGZMthHMANlCgi+ewtlIbcAmrZzKkXPOZNOcbM1wEMRJKYwIDAQAB" : "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUi5cTlCSDfczffuYb2UyvrKXuW/7xqBhLYG1ro+PmCNdJ01U1o7uc18YP6VNl5ZBF1IadY/XC6JphzBzCARVOqk1OE/Qfb1dQF6tO2nEZmDVDFeMHXsDtM1Jic/ntBcAy7Y6GP3OyqPRLgUribU7+m4CmAtk8b5y117cyWMBsXwIDAQAB")));
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new abg("key", a3));
                        arrayList.add(new abg("data", amapEncode));
                        b2.setBody(aba.a((List<? extends abg>) arrayList, (String) "UTF-8").getBytes("utf-8"));
                        yq.a();
                        yq.a((AosRequest) b2, this.c);
                        AMapLog.info("paas.adiu", "AdiuRequest", "getAdiu");
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                } else {
                    if (bno.a) {
                        AMapLog.debug("paas.adiu", "AdiuService", "bodyVal == null");
                    }
                }
            } else {
                if (bno.a) {
                    AMapLog.debug("paas.adiu", "AdiuService", "encodeKey == null");
                }
            }
        } else {
            AMapLog.debug("paas.adiu", "AdiuService", "info == null");
        }
    }
}

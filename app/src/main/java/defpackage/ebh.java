package defpackage;

import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.URLBuilderFactory;
import com.amap.bundle.network.request.param.builder.URLBuilderFactory.a;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.net.Sign;
import com.autonavi.minimap.route.bus.localbus.net.param.RouteBusParamUrlWrapper;
import com.autonavi.server.aos.serverkey;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ebh reason: default package */
/* compiled from: RouteNetworkUtil */
public final class ebh {
    public static AosPostRequest a(AosResponseCallback aosResponseCallback, RouteBusParamUrlWrapper routeBusParamUrlWrapper) {
        a entityInfo = URLBuilderFactory.getEntityInfo(routeBusParamUrlWrapper);
        HashMap hashMap = new HashMap();
        if (entityInfo.b != null) {
            for (Entry next : entityInfo.b.entrySet()) {
                Object obj = null;
                try {
                    obj = ((Field) next.getValue()).get(routeBusParamUrlWrapper);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    hashMap.put(next.getKey(), obj);
                }
            }
        }
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : hashMap.entrySet()) {
            try {
                Object value = entry.getValue();
                jSONObject.put((String) entry.getKey(), value == null ? "" : value.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("channel", serverkey.getAosChannel());
        hashMap2.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, "json");
        hashMap2.putAll(NetworkParam.getNetworkParamMap(entityInfo.a.url()));
        String[] sign = entityInfo.a.sign();
        StringBuilder sb = new StringBuilder();
        for (String str : sign) {
            Object obj2 = hashMap2.get(str);
            if (obj2 == null) {
                obj2 = hashMap.get(str);
                if (obj2 == null) {
                    obj2 = "";
                }
                hashMap2.put(str, obj2);
            }
            sb.append(obj2);
        }
        hashMap2.put("sign", Sign.getSign(sb.toString()));
        aaz aaz = new aaz(aai.a(entityInfo.a.host(), entityInfo.a.url()));
        for (Entry entry2 : hashMap2.entrySet()) {
            aaz.a((String) entry2.getKey(), String.valueOf(entry2.getValue()));
        }
        String b = aaz.b("UTF-8");
        AosPostRequest aosPostRequest = new AosPostRequest();
        aosPostRequest.setEncryptStrategy(-1);
        aosPostRequest.setUrl(b);
        aosPostRequest.setContentType("application/json");
        aosPostRequest.setBody(jSONObject.toString().getBytes(Charset.forName("utf-8")));
        aosPostRequest.setWithoutSign(true);
        aosPostRequest.setOutput(0);
        dkn dkn = new dkn();
        aosPostRequest.addHeaders(dkn.d);
        aosPostRequest.setTimeout(dkn.b);
        aosPostRequest.setRetryTimes(dkn.c);
        in.a().a((AosRequest) aosPostRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        return aosPostRequest;
    }
}

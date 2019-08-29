package com.amap.bundle.network.request.param.builder;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.net.Sign;
import com.autonavi.server.aos.serverkey;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SnsURLBuilder extends URLBuilder {
    private String a;
    private String b;
    private Map<String, Object> c;

    public void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException {
        this.a = aai.a(path.host(), path.url());
        HashMap hashMap = new HashMap();
        String str = this.a;
        hashMap.put("channel", serverkey.getAosChannel());
        hashMap.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, "json");
        hashMap.putAll(NetworkParam.getNetworkParamMap(str));
        if (map != null) {
            for (Entry next : map.entrySet()) {
                Object obj = ((Field) next.getValue()).get(paramEntity);
                if (obj != null) {
                    hashMap.put(next.getKey(), obj);
                }
            }
        }
        String a2 = aai.a(path.option_sign(), (Map<String, Object>) hashMap);
        if (TextUtils.isEmpty(a2)) {
            a2 = aai.a(path.sign(), (Map<String, Object>) hashMap);
        }
        this.b = Sign.getSign(a2);
        hashMap.put("sign", this.b);
        addCombinParam(path, hashMap);
        this.a = aai.a(this.a, (Map<String, Object>) hashMap);
        if (!z) {
            StringBuffer stringBuffer = new StringBuffer("");
            try {
                for (Entry entry : hashMap.entrySet()) {
                    stringBuffer.append("&");
                    stringBuffer.append((String) entry.getKey());
                    stringBuffer.append("=");
                    stringBuffer.append(Uri.encode(String.valueOf(entry.getValue()), "UTF-8"));
                }
            } catch (Throwable unused) {
                stringBuffer = new StringBuffer("");
                this.c = hashMap;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(stringBuffer.toString());
            this.a = sb.toString();
            return;
        }
        this.c = hashMap;
    }

    public String getUrl() {
        return this.a;
    }

    public Map<String, Object> getParams() {
        return this.c;
    }
}

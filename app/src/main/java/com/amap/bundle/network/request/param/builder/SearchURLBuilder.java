package com.amap.bundle.network.request.param.builder;

import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.net.Sign;
import com.autonavi.server.aos.serverkey;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SearchURLBuilder extends URLBuilder {
    private String a;
    private String b;
    private Map<String, Object> c;

    public void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException {
        String host = path.host();
        if (TextUtils.isEmpty(host)) {
            host = ConfigerHelper.SEARCH_AOS_URL_KEY;
        }
        this.a = aai.a(host, path.url());
        this.c = new HashMap();
        Map<String, Object> map2 = this.c;
        String str = this.a;
        map2.put("version", "2.13");
        map2.put("channel", serverkey.getAosChannel());
        map2.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, "json");
        map2.putAll(NetworkParam.getNetworkParamMap(str));
        if (map != null) {
            for (Entry next : map.entrySet()) {
                Object obj = ((Field) next.getValue()).get(paramEntity);
                if (obj != null) {
                    this.c.put(next.getKey(), obj);
                }
            }
        }
        String a2 = aai.a(path.option_sign(), this.c);
        if (TextUtils.isEmpty(a2)) {
            a2 = aai.a(path.sign(), this.c);
        }
        this.b = Sign.getSign(a2);
        this.c.put("sign", this.b);
        addCombinParam(path, this.c);
        this.a = aai.a(this.a, this.c);
    }

    public String getUrl() {
        return this.a;
    }

    public Map<String, Object> getParams() {
        return this.c;
    }
}

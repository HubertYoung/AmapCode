package com.amap.bundle.adiu.internal.net;

import android.text.TextUtils;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.net.Sign;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class NoComParamURLBuilder extends URLBuilder {
    private static final String OUTPUT = "json";
    private Map<String, Object> paramsMap;
    private String sign;
    private String url;

    public void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException {
        this.url = aai.a(path.host(), path.url());
        this.paramsMap = new HashMap();
        addCommonParams(this.paramsMap);
        if (map != null) {
            for (Entry next : map.entrySet()) {
                Object obj = ((Field) next.getValue()).get(paramEntity);
                if (obj != null) {
                    this.paramsMap.put(next.getKey(), obj);
                }
            }
        }
        String a = aai.a(path.option_sign(), this.paramsMap);
        if (TextUtils.isEmpty(a)) {
            a = aai.a(path.sign(), this.paramsMap);
        }
        this.sign = Sign.getSign(a);
        this.paramsMap.put("sign", this.sign);
        addCombinParam(path, this.paramsMap);
        this.url = aai.a(this.url, this.paramsMap);
    }

    private void addCommonParams(Map<String, Object> map) {
        map.put(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, OUTPUT);
    }

    public String getUrl() {
        return this.url;
    }

    public Map<String, Object> getParams() {
        return this.paramsMap;
    }
}

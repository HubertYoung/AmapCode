package com.amap.bundle.network.request.param.builder;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import proguard.annotation.KeepImplementations;

@KeepImplementations
public abstract class URLBuilder {

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CombineParam {
        String key() default "";

        SpecialParam[] special() default {@SpecialParam};

        String[] value() default {""};
    }

    public static class DefaultURLBuilder extends URLBuilder {
        private String a;
        private Map<String, Object> b;

        public void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException {
            StringBuilder sb = new StringBuilder();
            sb.append(path.host());
            sb.append(path.url());
            this.a = sb.toString();
            this.b = new HashMap();
            if (map != null) {
                for (Entry next : map.entrySet()) {
                    Object obj = ((Field) next.getValue()).get(paramEntity);
                    if (obj != null) {
                        this.b.put(next.getKey(), obj);
                    }
                }
            }
        }

        public String getUrl() {
            return this.a;
        }

        public Map<String, Object> getParams() {
            return this.b;
        }
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Path {
        Class<? extends URLBuilder> builder() default DefaultURLBuilder.class;

        CombineParam[] combine() default {@CombineParam(key = "", value = {""})};

        String host() default "";

        String[] option_sign() default {""};

        String[] sign() default {""};

        String url();
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ResultProperty {
        Class<? extends Object> parser() default aah.class;

        String value() default "";
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SpecialParam {
        String field() default "";

        String name() default "";

        String value() default "";
    }

    public abstract Map<String, Object> getParams();

    public List<String> getSignParams() {
        return null;
    }

    public abstract String getUrl();

    public abstract void parse(Path path, Map<String, Field> map, ParamEntity paramEntity, boolean z) throws IllegalAccessException;

    /* access modifiers changed from: protected */
    public void addCombinParam(Path path, Map<String, Object> map) {
        Map<String, Object> map2 = map;
        String[] sign = path.sign();
        Arrays.sort(sign);
        CombineParam[] combine = path.combine();
        if (combine != null && combine.length > 0) {
            StringBuffer stringBuffer = new StringBuffer("");
            for (CombineParam combineParam : combine) {
                String key = combineParam.key();
                String[] value = combineParam.value();
                SpecialParam[] special = combineParam.special();
                if (!TextUtils.isEmpty(key) && ((value != null && value.length > 0) || (special != null && special.length > 0))) {
                    StringBuffer stringBuffer2 = new StringBuffer("");
                    for (String str : value) {
                        if (!TextUtils.isEmpty(str)) {
                            if (!TextUtils.isEmpty(stringBuffer2) && !",".equals(String.valueOf(stringBuffer2.charAt(stringBuffer2.length() - 1)))) {
                                stringBuffer2.append(",");
                            }
                            if (Arrays.binarySearch(sign, str) < 0) {
                                Object remove = map2.remove(str);
                                if (remove != null) {
                                    stringBuffer2.append("\"");
                                    stringBuffer2.append(str);
                                    stringBuffer2.append("\":");
                                    StringBuilder sb = new StringBuilder("\"");
                                    sb.append(String.valueOf(remove));
                                    sb.append("\"");
                                    stringBuffer2.append(sb.toString());
                                }
                            }
                        }
                    }
                    ArrayList arrayList = new ArrayList();
                    for (SpecialParam specialParam : special) {
                        if (!TextUtils.isEmpty(specialParam.name())) {
                            if (!TextUtils.isEmpty(stringBuffer2) && !",".equals(String.valueOf(stringBuffer2.charAt(stringBuffer2.length() - 1)))) {
                                stringBuffer2.append(",");
                            }
                            if (!TextUtils.isEmpty(specialParam.value())) {
                                stringBuffer2.append("\"");
                                stringBuffer2.append(specialParam.name());
                                stringBuffer2.append("\":");
                                StringBuilder sb2 = new StringBuilder("\"");
                                sb2.append(String.valueOf(specialParam.value()));
                                sb2.append("\"");
                                stringBuffer2.append(sb2.toString());
                            } else {
                                arrayList.add(specialParam.field());
                                Object obj = map2.get(specialParam.field());
                                if (obj != null) {
                                    stringBuffer2.append("\"");
                                    stringBuffer2.append(specialParam.name());
                                    stringBuffer2.append("\":");
                                    StringBuilder sb3 = new StringBuilder("\"");
                                    sb3.append(String.valueOf(obj));
                                    sb3.append("\"");
                                    stringBuffer2.append(sb3.toString());
                                }
                            }
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        map2.remove((String) it.next());
                    }
                    if (!TextUtils.isEmpty(stringBuffer)) {
                        stringBuffer.append(",");
                    }
                    if (!TextUtils.isEmpty(stringBuffer2)) {
                        stringBuffer.append("\"");
                        stringBuffer.append(key);
                        stringBuffer.append("\":{");
                        stringBuffer.append(stringBuffer2);
                        stringBuffer.append(h.d);
                    }
                }
            }
            if (!TextUtils.isEmpty(stringBuffer)) {
                stringBuffer.insert(0, "{").append(h.d);
                map2.put("dsp_svr_param", stringBuffer.toString());
            }
        }
    }
}

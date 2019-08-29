package defpackage;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.util.h;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.MtopRequest;
import org.eclipse.mat.hprof.IHprofParserHandler;

/* renamed from: ffz reason: default package */
/* compiled from: ReflectUtil */
public final class ffz {
    public static void a(MtopRequest mtopRequest, Object obj) {
        try {
            HashMap hashMap = new HashMap();
            Class<?> cls = obj.getClass();
            HashSet hashSet = new HashSet();
            hashSet.addAll(Arrays.asList(cls.getFields()));
            hashSet.addAll(Arrays.asList(cls.getDeclaredFields()));
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                Field field = (Field) it.next();
                String name = field.getName();
                if (name.indexOf("$") == -1 && !name.equals("serialVersionUID") && !name.equals("ORIGINALJSON")) {
                    boolean z = true;
                    field.setAccessible(true);
                    if (name.equals("API_NAME")) {
                        Object obj2 = field.get(obj);
                        if (obj2 != null) {
                            mtopRequest.setApiName(obj2.toString());
                        }
                    } else if (name.equals(IHprofParserHandler.VERSION)) {
                        Object obj3 = field.get(obj);
                        if (obj3 != null) {
                            mtopRequest.setVersion(obj3.toString());
                        }
                    } else if (name.equals("NEED_ECODE")) {
                        Boolean valueOf = Boolean.valueOf(field.getBoolean(obj));
                        if (valueOf == null || !valueOf.booleanValue()) {
                            z = false;
                        }
                        mtopRequest.setNeedEcode(z);
                    } else if (name.equals("NEED_SESSION")) {
                        Boolean valueOf2 = Boolean.valueOf(field.getBoolean(obj));
                        if (valueOf2 == null || !valueOf2.booleanValue()) {
                            z = false;
                        }
                        mtopRequest.setNeedSession(z);
                    } else {
                        Object obj4 = field.get(obj);
                        if (obj4 != null) {
                            if (obj4 instanceof String) {
                                hashMap.put(name, obj4.toString());
                            } else {
                                hashMap.put(name, JSON.toJSONString(obj4));
                            }
                        }
                    }
                }
            }
            mtopRequest.dataParams = hashMap;
            mtopRequest.setData(a(hashMap));
        } catch (Exception e) {
            TBSdkLog.b((String) "mtopsdk.ReflectUtil", (String) "parseParams failed.", (Throwable) e);
        }
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder(64);
        sb.append("{");
        if (map != null && !map.isEmpty()) {
            for (Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                if (!(str == null || str2 == null)) {
                    try {
                        sb.append(JSON.toJSONString(str));
                        sb.append(":");
                        sb.append(JSON.toJSONString(str2));
                        sb.append(",");
                    } catch (Throwable th) {
                        StringBuilder sb2 = new StringBuilder(64);
                        sb2.append("[convertMapToDataStr] convert key=");
                        sb2.append(str);
                        sb2.append(",value=");
                        sb2.append(str2);
                        sb2.append(" to dataStr error.");
                        TBSdkLog.b((String) "mtopsdk.ReflectUtil", sb2.toString(), th);
                    }
                }
            }
            int length = sb.length();
            if (length > 1) {
                sb.deleteCharAt(length - 1);
            }
        }
        sb.append(h.d);
        return sb.toString();
    }
}

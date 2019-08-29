package defpackage;

import anet.channel.statist.AmdcStatistic;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.SessionStatistic;
import anet.channel.statist.StatObject;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

/* renamed from: zj reason: default package */
/* compiled from: AccsStatistic */
public final class zj {
    public static volatile SessionStatistic a;
    public static volatile RequestStatistic b;
    public static volatile ExceptionStatistic c;
    public static volatile AmdcStatistic d;

    static Map<String, String> a(StatObject statObject, Map<String, String> map) {
        Field[] declaredFields;
        if (statObject == null) {
            return map;
        }
        try {
            int i = 0;
            for (Field field : statObject.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(statObject) != null && !"".equals(field.get(statObject).toString()) && !"bizId".equals(field.getName())) {
                    String obj = field.get(statObject).toString();
                    if (obj.equals("false") || obj.equals("true")) {
                        obj = obj.equals("true") ? "1" : "0";
                    }
                    map.put(field.getName(), obj);
                }
            }
            if (statObject instanceof RequestStatistic) {
                RequestStatistic requestStatistic = (RequestStatistic) statObject;
                String str = requestStatistic.url;
                int indexOf = str.indexOf(requestStatistic.host);
                int indexOf2 = str.indexOf(63);
                if (indexOf < 0) {
                    int indexOf3 = str.indexOf(47, 7);
                    if (indexOf3 >= 0) {
                        i = indexOf3;
                    }
                } else {
                    i = requestStatistic.host.length() + indexOf;
                }
                if (indexOf2 < 0) {
                    indexOf2 = str.length();
                }
                if (requestStatistic.bizId == null || !requestStatistic.bizId.equals("0")) {
                    map.put("method", "POST");
                } else {
                    map.put("method", "GET");
                }
                map.remove(H5PageData.KEY_UC_START);
                map.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, String.valueOf(requestStatistic.start));
                String substring = str.substring(i, indexOf2);
                if (substring.endsWith("/")) {
                    map.put("url", substring);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(substring);
                    sb.append("/");
                    map.put("url", sb.toString());
                }
                map.remove("msg");
                map.put("errorMsg", requestStatistic.msg);
                map.remove("ret");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(requestStatistic.ret);
                map.put("result", sb2.toString());
                map.remove("tcpLinkDate");
                map.remove("isDNS");
                map.remove("isSSL");
                map.remove("isProxy");
                map.remove("cacheTime");
                map.remove("netType");
                map.put("network_class", requestStatistic.netType);
                map.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof SessionStatistic) {
                SessionStatistic sessionStatistic = (SessionStatistic) statObject;
                map.remove("ret");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sessionStatistic.ret);
                map.put("result", sb3.toString());
                map.remove("netType");
                map.put("network_class", sessionStatistic.netType);
                map.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof AmdcStatistic) {
                map.remove("ttid");
                map.remove("url");
                map.remove("netType");
                map.put("network_class", ((AmdcStatistic) statObject).netType);
                map.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof ExceptionStatistic) {
                map.put("exceptionTime", String.valueOf(new Date(System.currentTimeMillis())));
                map.remove("netType");
                map.put("network_class", ((ExceptionStatistic) statObject).netType);
                map.put("client_network_class", NetworkReachability.c().toString());
            }
        } catch (Exception e) {
            if (bno.a) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}

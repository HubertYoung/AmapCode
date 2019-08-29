package defpackage;

import android.text.TextUtils;
import anet.channel.statist.AmdcStatistic;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestMonitor;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.SessionConnStat;
import anet.channel.statist.SessionStatistic;
import anet.channel.statist.StatObject;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zf reason: default package */
/* compiled from: AccsAppMonitor */
public final class zf implements z {
    private AtomicBoolean a = new AtomicBoolean(false);

    public final void a(bj bjVar) {
    }

    public final void a(bk bkVar) {
    }

    public final void a(StatObject statObject) {
        if (statObject != null && !(statObject instanceof RequestMonitor)) {
            JSONObject b = b(statObject);
            try {
                if (statObject instanceof RequestStatistic) {
                    zj.b = (RequestStatistic) statObject;
                    aaf.a((String) "2000", (String) "B004", b);
                } else if (statObject instanceof SessionStatistic) {
                    zj.a = (SessionStatistic) statObject;
                    aaf.a((String) "2000", (String) "B005", b);
                } else if (statObject instanceof SessionConnStat) {
                    aaf.a((String) "2000", (String) "B016", b);
                } else if (statObject instanceof AmdcStatistic) {
                    zj.d = (AmdcStatistic) statObject;
                    aaf.a((String) "2000", (String) "B006", b);
                } else {
                    if (statObject instanceof ExceptionStatistic) {
                        zj.c = (ExceptionStatistic) statObject;
                    }
                    aaf.a((String) "2000", (String) "B007", b);
                }
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("jo=");
                    sb.append(statObject.getClass().getSimpleName());
                    sb.append(b.toString());
                    AMapLog.d("network.AccsAppMonitor", sb.toString());
                }
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("commitStat");
                sb2.append(e.getLocalizedMessage());
                AMapLog.e("network.AccsAppMonitor", sb2.toString());
            }
        }
    }

    private static JSONObject b(StatObject statObject) {
        Field[] declaredFields;
        int i;
        JSONObject jSONObject = new JSONObject();
        try {
            for (Field field : statObject.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(statObject) != null && !"".equals(field.get(statObject).toString()) && !"bizId".equals(field.getName())) {
                    String obj = field.get(statObject).toString();
                    if (obj.equals("false") || obj.equals("true")) {
                        obj = obj.equals("true") ? "1" : "0";
                    }
                    jSONObject.put(field.getName(), obj);
                }
            }
            if (statObject instanceof RequestStatistic) {
                RequestStatistic requestStatistic = (RequestStatistic) statObject;
                String str = requestStatistic.url;
                String str2 = null;
                if (!TextUtils.isEmpty(str)) {
                    if (str.contains("&csid=")) {
                        int indexOf = str.indexOf("&csid=") + 6;
                        int i2 = indexOf + 36;
                        if (str.length() >= i2) {
                            str2 = str.substring(indexOf, i2);
                        }
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put("csid", str2);
                }
                int indexOf2 = str.indexOf(requestStatistic.host);
                int indexOf3 = str.indexOf(63);
                if (indexOf2 < 0) {
                    i = str.indexOf(47, 7);
                    if (i < 0) {
                        i = 0;
                    }
                } else {
                    i = indexOf2 + requestStatistic.host.length();
                }
                if (indexOf3 < 0) {
                    indexOf3 = str.length();
                }
                if (requestStatistic.bizId == null || !requestStatistic.bizId.equals("0")) {
                    jSONObject.put("method", "POST");
                } else {
                    jSONObject.put("method", "GET");
                }
                jSONObject.remove(H5PageData.KEY_UC_START);
                jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, a(new Date(requestStatistic.start)));
                String substring = str.substring(i, indexOf3);
                if (substring.endsWith("/")) {
                    jSONObject.put("url", substring);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(substring);
                    sb.append("/");
                    jSONObject.put("url", sb.toString());
                }
                jSONObject.remove("msg");
                jSONObject.put("errorMsg", requestStatistic.msg);
                jSONObject.remove("ret");
                if (requestStatistic.ret == 1) {
                    jSONObject.put("result", 1);
                } else {
                    jSONObject.put("result", 0);
                }
                jSONObject.remove("tcpLinkDate");
                jSONObject.remove("isDNS");
                jSONObject.remove("isSSL");
                jSONObject.remove("isProxy");
                jSONObject.remove("cacheTime");
                jSONObject.remove("netType");
                jSONObject.remove("userInfo");
                if (!TextUtils.isEmpty(requestStatistic.userInfo)) {
                    try {
                        JSONObject jSONObject2 = new JSONObject(requestStatistic.userInfo);
                        Long valueOf = Long.valueOf(jSONObject2.optLong("xsign_cost", -1));
                        Long valueOf2 = Long.valueOf(jSONObject2.optLong("wua_cost", -1));
                        if (valueOf.longValue() >= 0) {
                            jSONObject.put("xsign_cost", valueOf);
                            jSONObject.put("wua_cost", valueOf2);
                        }
                    } catch (Throwable unused) {
                    }
                }
                jSONObject.put("network_class", requestStatistic.netType);
                jSONObject.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof SessionStatistic) {
                SessionStatistic sessionStatistic = (SessionStatistic) statObject;
                jSONObject.remove("ret");
                jSONObject.put("result", sessionStatistic.ret);
                jSONObject.remove("netType");
                jSONObject.put("network_class", sessionStatistic.netType);
                jSONObject.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof SessionConnStat) {
                SessionConnStat sessionConnStat = (SessionConnStat) statObject;
                jSONObject.remove("ret");
                jSONObject.put("result", sessionConnStat.ret);
                jSONObject.remove("netType");
                jSONObject.put("network_class", sessionConnStat.netType);
                jSONObject.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof AmdcStatistic) {
                jSONObject.remove("ttid");
                jSONObject.remove("url");
                jSONObject.remove("netType");
                jSONObject.put("network_class", ((AmdcStatistic) statObject).netType);
                jSONObject.put("client_network_class", NetworkReachability.c().toString());
            }
            if (statObject instanceof ExceptionStatistic) {
                jSONObject.put("exceptionTime", a(new Date(System.currentTimeMillis())));
                jSONObject.remove("netType");
                jSONObject.put("network_class", ((ExceptionStatistic) statObject).netType);
                jSONObject.put("client_network_class", NetworkReachability.c().toString());
            }
        } catch (IllegalAccessException e) {
            StringBuilder sb2 = new StringBuilder("getStatJsonObject:");
            sb2.append(e.getLocalizedMessage());
            AMapLog.e("network.AccsAppMonitor", sb2.toString());
        } catch (JSONException e2) {
            StringBuilder sb3 = new StringBuilder("getStatJsonObject:");
            sb3.append(e2.getLocalizedMessage());
            AMapLog.e("network.AccsAppMonitor", sb3.toString());
        }
        StringBuilder sb4 = new StringBuilder("getStatJsonObject :");
        sb4.append(statObject.getClass().getSimpleName());
        sb4.append("--");
        sb4.append(jSONObject.toString());
        AMapLog.d("network.AccsAppMonitor", sb4.toString());
        return jSONObject;
    }

    private static String a(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(date);
    }
}

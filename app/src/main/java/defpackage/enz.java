package defpackage;

import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: enz reason: default package */
/* compiled from: CloudSwitch */
public final class enz {
    private static enz c = new enz();
    private int a = 10;
    private HashMap<String, Boolean> b = new HashMap<>();
    private long d = 0;
    private String e = "QAInfo_CloudSwitch";

    private enz() {
    }

    public static enz a() {
        if (c == null) {
            c = new enz();
        }
        return c;
    }

    public final boolean a(String str, String str2) {
        if (!this.b.containsKey(str) || a(this.d)) {
            if (a(this.d)) {
                AMapLog.d(this.e, "超过1小时");
                if (this.a <= 0) {
                    AMapLog.d(this.e, "重启请求次数多3次");
                    this.a = 1;
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("name", str);
            hashMap.put(LocationParams.PARA_COMMON_ADIU, str2);
            if (this.a > 0) {
                String str3 = this.e;
                StringBuilder sb = new StringBuilder("FailCount");
                sb.append(this.a);
                AMapLog.d(str3, sb.toString());
                String a2 = eoe.a((String) "/bridge/switchstate", (Map<String, Object>) hashMap);
                if ("".equals(a2)) {
                    AMapLog.d(this.e, "req result equals null");
                    this.a--;
                } else {
                    AMapLog.d(this.e, "REQ OK");
                    try {
                        JSONObject jSONObject = new JSONObject(a2);
                        if (((Integer) jSONObject.get("errorCode")).intValue() == 0) {
                            String str4 = (String) jSONObject.get("name");
                            boolean z = ((Integer) jSONObject.get("state")).intValue() == 1;
                            this.b.put(str4, Boolean.valueOf(z));
                            AMapLog.d(this.e, "云端返回该adiu控制:".concat(String.valueOf(z)));
                        } else {
                            this.a--;
                        }
                        this.d = System.currentTimeMillis();
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                        this.a--;
                        this.d = System.currentTimeMillis();
                    }
                }
            }
            if (this.b.containsKey(str)) {
                return this.b.get(str).booleanValue();
            }
            return true;
        }
        AMapLog.d(this.e, "获取云控状态");
        return this.b.get(str).booleanValue();
    }

    private static boolean a(long j) {
        int i = Calendar.getInstance().get(11);
        if ((i > 21 || i < 9) && System.currentTimeMillis() - j > H5PreferAppList.defaultTime) {
            return true;
        }
        return false;
    }
}

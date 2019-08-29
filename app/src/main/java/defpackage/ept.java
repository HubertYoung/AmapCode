package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Iterator;
import org.json.JSONObject;

/* renamed from: ept reason: default package */
/* compiled from: CloudParam */
public final class ept {
    public static boolean a = true;
    public static boolean b = false;
    public static String c = "";
    public static boolean d = false;

    public static synchronized void a() {
        synchronized (ept.class) {
            if (!d) {
                lo.a().a((String) "alc_record_control", (lp) new lp() {
                    public final void onConfigCallBack(int i) {
                    }

                    public final void onConfigResultCallBack(int i, String str) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put(LocationParams.PARA_AMAP_CLOUD_ALC, str);
                                LocationInstrument.getInstance().setParams(6, jSONObject);
                            } catch (Exception unused) {
                            }
                        }
                    }
                });
                lo.a().a((String) LocationParams.PARA_AMAP_CLOUD_LOCSDK_PLUGIN, (lp) new lp() {
                    public final void onConfigCallBack(int i) {
                    }

                    public final void onConfigResultCallBack(int i, String str) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put(LocationParams.PARA_AMAP_CLOUD_LOCSDK_PLUGIN, str);
                                LocationInstrument.getInstance().setParams(6, jSONObject);
                            } catch (Exception unused) {
                            }
                        }
                    }
                });
                lo.a().a((String) "engine_pos", (lp) new lp() {
                    public final void onConfigCallBack(int i) {
                    }

                    public final void onConfigResultCallBack(int i, String str) {
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                JSONObject jSONObject = new JSONObject(str);
                                boolean z = true;
                                ept.a = jSONObject.optInt("monitor", 1) == 1;
                                if (jSONObject.optInt("wifi_navigation", 0) == 0) {
                                    z = false;
                                }
                                ept.b = z;
                                StringBuilder sb = new StringBuilder();
                                Iterator<String> keys = jSONObject.keys();
                                if (keys != null) {
                                    while (keys.hasNext()) {
                                        String next = keys.next();
                                        if (!TextUtils.isEmpty(next) && !"monitor".equals(next)) {
                                            try {
                                                int optInt = jSONObject.optInt(next, -1);
                                                if (optInt >= 0) {
                                                    StringBuilder sb2 = new StringBuilder();
                                                    sb2.append(next);
                                                    sb2.append(":");
                                                    sb.append(sb2.toString());
                                                    sb.append(optInt);
                                                    sb.append(MergeUtil.SEPARATOR_KV);
                                                }
                                            } catch (Exception unused) {
                                            }
                                        }
                                    }
                                }
                                ept.c = sb.toString();
                            } catch (Exception e) {
                                if (epk.a) {
                                    epk.a(e);
                                }
                            }
                            if (epk.a) {
                                StringBuilder sb3 = new StringBuilder("当前云控指令 ab：");
                                sb3.append(ept.c);
                                sb3.append(" 定位监控功能：");
                                sb3.append(ept.a);
                                sb3.append(" 网络点导航：");
                                sb3.append(ept.b);
                                epk.a("CloudParam", sb3.toString());
                            }
                        }
                    }
                });
            }
        }
    }
}

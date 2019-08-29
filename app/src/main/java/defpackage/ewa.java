package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import com.amap.location.common.model.AmapLoc;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* renamed from: ewa reason: default package */
/* compiled from: OnlineTask */
public class ewa implements Runnable {
    public static final String a = "ewa";
    private Context b;
    private JSONObject c;
    private ConcurrentHashMap<String, String> d;
    private b e;
    private a f;
    private String g;

    /* renamed from: ewa$a */
    /* compiled from: OnlineTask */
    public interface a {
        boolean a();
    }

    /* renamed from: ewa$b */
    /* compiled from: OnlineTask */
    public interface b {
        boolean a(JSONObject jSONObject);
    }

    public ewa(Context context, String str, JSONObject jSONObject, ConcurrentHashMap concurrentHashMap, a aVar, b bVar) {
        this.b = context;
        this.c = jSONObject;
        this.d = concurrentHashMap;
        this.g = str;
        this.f = aVar;
        this.e = bVar;
    }

    public void run() {
        try {
            if (this.g.equals("wa")) {
                ewc.b = "1";
            }
            String optString = this.c.optString("key");
            String optString2 = this.c.optString("per");
            if (!TextUtils.isEmpty(optString2)) {
                String[] split = optString2.split("-");
                if (split.length == 3) {
                    int i = 0;
                    int intValue = Integer.valueOf(split[0]).intValue();
                    int intValue2 = Integer.valueOf(split[1]).intValue();
                    int intValue3 = Integer.valueOf(split[2]).intValue();
                    StringBuilder sb = new StringBuilder();
                    sb.append(optString);
                    sb.append(this.d.get("device_id"));
                    long abs = (long) (Math.abs(sb.toString().hashCode()) % intValue3);
                    if (abs >= ((long) intValue) && abs <= ((long) intValue2)) {
                        String optString3 = this.c.optString("id");
                        int optInt = this.c.optInt("freq");
                        String str = this.g;
                        Context context = this.b;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(optString3);
                        sb2.append(this.d.get("device_id"));
                        int intValue4 = ((Integer) ewp.b(context, "hmt_tasks_info", sb2.toString(), Integer.valueOf(0))).intValue();
                        String str2 = this.g;
                        Context context2 = this.b;
                        StringBuilder sb3 = new StringBuilder("task_execute_time");
                        sb3.append(str2);
                        sb3.append(optString3);
                        sb3.append(this.d.get("device_id"));
                        long longValue = ((Long) ewp.b(context2, "hmt_tasks_info", sb3.toString(), Long.valueOf(0))).longValue();
                        if (optInt == 0 || optInt < -1) {
                            return;
                        }
                        if (optInt != -1 || ((long) intValue4) <= 0) {
                            if (DateUtils.isToday(longValue)) {
                                if (intValue4 < optInt) {
                                    i = intValue4;
                                } else {
                                    return;
                                }
                            }
                            if (this.g.equals("wa")) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(ewc.b);
                                sb4.append("2");
                                ewc.b = sb4.toString();
                            }
                            if (this.f != null ? this.f.a() : true) {
                                if (this.g.equals("wa")) {
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append(ewc.b);
                                    sb5.append("3");
                                    ewc.b = sb5.toString();
                                }
                                String str3 = this.g;
                                Context context3 = this.b;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append(str3);
                                sb6.append(optString3);
                                sb6.append(this.d.get("device_id"));
                                ewp.a(context3, "hmt_tasks_info", sb6.toString(), Integer.valueOf(i + 1));
                                String str4 = this.g;
                                Context context4 = this.b;
                                StringBuilder sb7 = new StringBuilder("task_execute_time");
                                sb7.append(str4);
                                sb7.append(optString3);
                                sb7.append(this.d.get("device_id"));
                                ewp.a(context4, "hmt_tasks_info", sb7.toString(), Long.valueOf(System.currentTimeMillis()));
                                if (this.e != null) {
                                    this.e.a(this.c);
                                }
                            } else if (this.g.equals("wa")) {
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append(ewc.b);
                                sb8.append(AmapLoc.RESULT_TYPE_CAS_INDOOR);
                                ewc.b = sb8.toString();
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            StringBuilder sb9 = new StringBuilder("Collected:");
            sb9.append(th.getMessage());
            euw.a(sb9.toString());
        }
    }
}

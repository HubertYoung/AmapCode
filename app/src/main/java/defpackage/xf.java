package defpackage;

import android.content.SharedPreferences.Editor;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: xf reason: default package */
/* compiled from: LotusPoolStorage */
public class xf {
    public static final String a = "xf";
    public List<xh> b = new ArrayList();
    public int c = 0;
    public Map<String, xg> d;
    public xe e;
    public boolean f;

    public final void a() {
        this.c = 0;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            xh xhVar = this.b.get(size);
            if (xhVar.e == null || xhVar.e.isEmpty()) {
                this.b.remove(size);
            }
        }
    }

    public final void a(CommandResult commandResult) {
        if (commandResult != null) {
            xg xgVar = this.d.get(commandResult.a());
            if (xgVar == null) {
                xgVar = new xg(commandResult.a, commandResult.b, commandResult.d, commandResult.e);
                this.d.put(xgVar.a(), xgVar);
            }
            xgVar.a(commandResult);
            if (bno.a) {
                String str = a;
                StringBuilder sb = new StringBuilder("put command results=");
                sb.append(commandResult.c);
                sb.append(":");
                sb.append(commandResult.i);
                AMapLog.d(str, sb.toString());
            }
            this.e.a(commandResult);
        }
    }

    private static void d(String str, String str2) {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static void a(String str, long j) {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).edit();
        edit.putLong(str, j);
        edit.apply();
    }

    public static long b(String str, long j) {
        return AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).getLong(str, j);
    }

    private static int a(String str) {
        return AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).getInt(str, 0);
    }

    public final void a(List<xh> list) {
        if (list != null && list.size() != 0) {
            this.b.addAll(list);
            boolean z = false;
            for (int size = list.size() - 1; size >= 0; size--) {
                xh xhVar = list.get(size);
                if (!z && xhVar.d == 0) {
                    d("last_dispatch_sequence", String.valueOf(xhVar.b));
                    if (this.f) {
                        break;
                    }
                    z = true;
                }
                if (!this.f) {
                    Iterator<Command> it = xhVar.e.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (xi.a(it.next())) {
                                this.f = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            xe xeVar = this.e;
            if (list != null && !list.isEmpty()) {
                for (xh next : list) {
                    if (!(next.d == 1 || next.e == null || next.e.isEmpty())) {
                        for (Command insertOrReplace : next.e) {
                            xeVar.a.insertOrReplace(insertOrReplace);
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public final String c() {
        if (this.d.isEmpty()) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (xg next : this.d.values()) {
            if (next != null) {
                try {
                    JSONObject b2 = next.b();
                    if (b2 != null) {
                        jSONArray.put(b2);
                    }
                } catch (JSONException e2) {
                    if (bno.a) {
                        String str = a;
                        StringBuilder sb = new StringBuilder("getFeedbackResults()-error ");
                        sb.append(Log.getStackTraceString(e2));
                        AMapLog.e(str, sb.toString(), true);
                    }
                }
            }
        }
        return jSONArray.toString();
    }

    public static long d() {
        try {
            return b((String) "last_cold_launch_request_time", 0);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static void a(long j) {
        if (j > 0) {
            a((String) "last_cold_launch_request_time", j);
        }
    }

    public static long e() {
        try {
            return b((String) "last_background_launch_request_time", 0);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static void b(long j) {
        if (j > 0) {
            a((String) "last_background_launch_request_time", j);
        }
    }

    @Nullable
    private static String e(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("interrupted_");
        sb.append(str);
        sb.append(MetaRecord.LOG_SEPARATOR);
        sb.append(str2);
        return sb.toString();
    }

    public static boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || a(e(str, str2)) < 3) {
            return false;
        }
        return true;
    }

    public static void b(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String e2 = e(str, str2);
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).edit();
            edit.putInt(e2, a(e2) + 1);
            edit.apply();
        }
    }

    public static void c(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String e2 = e(str, str2);
            if (!TextUtils.isEmpty(e2)) {
                Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).edit();
                edit.remove(e2);
                edit.apply();
            }
        }
    }

    public final String b() {
        long j;
        long j2 = 0;
        try {
            j = Long.parseLong(AMapAppGlobal.getApplication().getSharedPreferences("lotus_data", 0).getString("last_dispatch_sequence", "-1"));
        } catch (Exception unused) {
            d("last_dispatch_sequence", "-1");
            j = 0;
        }
        if (!this.b.isEmpty()) {
            j2 = this.b.get(this.b.size() - 1).b;
        }
        return String.valueOf(Math.max(j, j2));
    }
}

package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.autonavi.server.aos.serverkey;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

/* renamed from: zh reason: default package */
/* compiled from: HttpLogRecord */
public final class zh {
    private static final SimpleDateFormat p = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
    String a;
    String b;
    long c;
    long d;
    long e;
    long f = -1;
    long g = -1;
    String h = "0";
    int i;
    int j = 0;
    long k;
    long l;
    long m;
    boolean n = false;
    public SharedPreferences o;
    /* access modifiers changed from: private */
    public String q;
    private long r;
    private long s;
    private long t;
    private long u;
    private String v;
    private String w;
    private boolean x = false;

    public zh(String str, final String str2) {
        this.a = str;
        this.b = str2;
        if (!TextUtils.isEmpty(str2) && str2.contains("&csid=")) {
            int indexOf = str2.indexOf("&csid=") + 6;
            int i2 = indexOf + 36;
            if (str2.length() >= i2) {
                this.w = str2.substring(indexOf, i2);
            }
        }
        this.m = NetworkParam.getStepId();
        this.o = new MapSharePreference(SharePreferenceName.SharedPreferences.toString()).sharedPrefs();
        new Thread("HttpLogRecordThread") {
            public final void run() {
                zh.this.q = zh.d(str2);
            }
        }.start();
    }

    private static String b(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int indexOf = str.indexOf("in=");
        if (indexOf <= 0) {
            return null;
        }
        String substring = str.substring(indexOf + 3, str.length() - 1);
        if (TextUtils.isEmpty(substring)) {
            return null;
        }
        int indexOf2 = substring.indexOf("&");
        if (indexOf2 <= 0) {
            return null;
        }
        try {
            str2 = URLDecoder.decode(substring.substring(0, indexOf2), "UTF-8");
        } catch (Exception e2) {
            if (bno.a) {
                e2.printStackTrace();
            }
            str2 = null;
        }
        return str2;
    }

    private static long c(String str) {
        long j2 = 0;
        try {
            String amapDecode = serverkey.amapDecode(str);
            if (amapDecode == null) {
                return 0;
            }
            int indexOf = amapDecode.indexOf("stepid=");
            if (indexOf <= 0) {
                return 0;
            }
            String substring = amapDecode.substring(indexOf + 7, amapDecode.length() - 1);
            if (TextUtils.isEmpty(substring)) {
                return 0;
            }
            int indexOf2 = substring.indexOf("&");
            if (indexOf2 <= 0) {
                return 0;
            }
            String substring2 = substring.substring(0, indexOf2);
            if (TextUtils.isEmpty(substring2)) {
                return 0;
            }
            j2 = Long.parseLong(substring2);
            return j2;
        } catch (Exception e2) {
            if (bno.a) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final String a(boolean z) {
        long j2;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("client_network_class", this.h);
            jSONObject.put("method", this.a);
            jSONObject.put(OnNativeInvokeListener.ARG_IP, this.q);
            boolean z2 = false;
            if (!TextUtils.isEmpty(this.b)) {
                int indexOf = this.b.indexOf(47, 8);
                int indexOf2 = this.b.indexOf(63);
                if (indexOf < 0) {
                    indexOf = 0;
                }
                if (indexOf2 < 0) {
                    indexOf2 = this.b.length();
                }
                jSONObject.put("url", this.b.substring(indexOf, indexOf2));
            }
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME, p.format(new Date(this.c)));
            jSONObject.put("start_receive_time", p.format(new Date(this.d)));
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME, p.format(new Date(this.e)));
            jSONObject.put("result", this.i);
            jSONObject.put("response_code", this.j);
            jSONObject.put("data_size", this.k);
            int i2 = (int) (((this.k <= 0 || this.d <= 0 || this.e <= this.d) ? 0 : (this.k * 1000) / (this.e - this.d)) / 1024);
            jSONObject.put("download_rate", i2);
            if (this.o != null) {
                z2 = this.o.getBoolean("maa_4g_switch", false);
            }
            jSONObject.put("type", z2);
            if (!TextUtils.isEmpty(this.w)) {
                jSONObject.put("csid", this.w);
            }
            if (this.f >= 0) {
                jSONObject.put("wua_cost", this.g);
                jSONObject.put("xsign_cost", this.f);
            }
            if (!this.x) {
                if (!TextUtils.isEmpty(this.v)) {
                    j2 = c(this.v);
                } else {
                    String b2 = b(this.b);
                    j2 = !TextUtils.isEmpty(b2) ? c(b2) : 0;
                }
                if (j2 > 0) {
                    this.m = j2;
                }
            }
            if (z) {
                new StringBuilder("ip   =").append(this.q);
                new StringBuilder("url  =").append(this.b);
                if (this.a.equals("POST") || !TextUtils.isEmpty(this.v)) {
                    new StringBuilder("body  =").append(this.v);
                }
                new StringBuilder("size =").append(this.k);
                StringBuilder sb = new StringBuilder("download rate\t = ");
                sb.append(i2);
                sb.append(" k/s");
                new StringBuilder("ResponseCode\t = ").append(this.j);
                String str = "";
                if (this.i == 0) {
                    str = "HTTP_RESULT_FAIL";
                } else if (this.i == 1) {
                    str = "HTTP_RESULT_SUCCESS";
                } else if (this.i == 2) {
                    str = "HTTP_RESULT_CANCEL";
                } else if (this.i == 3) {
                    str = "HTTP_RESULT_CACHE";
                }
                StringBuilder sb2 = new StringBuilder("result type\t = ");
                sb2.append(this.i);
                sb2.append("\t(");
                sb2.append(str);
                sb2.append(")");
                if (this.r > 0) {
                    new StringBuilder("Action Start Time\t = ").append(p.format(new Date(this.r)));
                }
                if (this.s > 0) {
                    new StringBuilder("Task Start Time\t = ").append(p.format(new Date(this.s)));
                }
                if (this.c > 0) {
                    new StringBuilder("Request Start Time\t = ").append(p.format(new Date(this.c)));
                    new StringBuilder("Receive Start Time\t = ").append(p.format(new Date(this.d)));
                    new StringBuilder("Request End Time\t = ").append(p.format(new Date(this.e)));
                }
                if (this.t > 0) {
                    new StringBuilder("Callback Start Time\t = ").append(p.format(new Date(this.t)));
                    new StringBuilder("Callback End Time\t = ").append(p.format(new Date(this.u)));
                }
            }
            return jSONObject.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            r13 = "";
            return "";
        }
    }

    /* access modifiers changed from: private */
    public static String d(String str) {
        String str2;
        long currentTimeMillis = System.currentTimeMillis();
        if (str.startsWith(AjxHttpLoader.DOMAIN_HTTP)) {
            str = str.substring(7, str.length() - 1);
        }
        if (str.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            str = str.substring(8, str.length() - 1);
        }
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        int indexOf2 = str.indexOf("/");
        if (indexOf2 > 0) {
            str = str.substring(0, indexOf2);
        }
        try {
            str2 = InetAddress.getByName(str).getHostAddress();
        } catch (UnknownHostException unused) {
            str2 = "";
        } catch (Exception unused2) {
            str2 = "";
        }
        AMapLog.d("network.HttpLogRecord", String.format("getHostIP cost %d ms!", new Object[]{Long.valueOf(System.currentTimeMillis() - currentTimeMillis)}));
        return str2;
    }
}

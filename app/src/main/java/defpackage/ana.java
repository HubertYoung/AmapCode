package defpackage;

import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ana reason: default package */
/* compiled from: GLLogUtil */
public final class ana {
    private static final String a;
    private static Executor b;

    public static void a() {
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/log/mapengine_log.txt");
        a = sb.toString();
    }

    public static void a(final String str, final String str2) {
        if (b == null) {
            b = Executors.newSingleThreadExecutor();
        }
        b.execute(new Runnable() {
            public final void run() {
                ana.b(str, str2);
            }
        });
    }

    public static boolean b(String str, String str2) {
        try {
            File file = new File(a);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(DateFormat.format("[yyyy-MM-dd kk:mm:ss]", System.currentTimeMillis()).toString());
            stringBuffer.append(SimpleComparison.LESS_THAN_OPERATION);
            stringBuffer.append(str);
            stringBuffer.append(SimpleComparison.GREATER_THAN_OPERATION);
            stringBuffer.append(str2);
            stringBuffer.append("\n");
            fileOutputStream.write(stringBuffer.toString().getBytes("UTF-8"));
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } catch (Throwable unused) {
        }
        return true;
    }

    public static void a(int i, int i2, String str) {
        if (akq.a() != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i);
                StringBuilder sb = new StringBuilder("code:");
                sb.append(i2);
                sb.append(",");
                sb.append(str);
                jSONObject.put(TrafficUtil.KEYWORD, sb.toString());
                akq.a().a("P00063", "B001", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(GLGeoPoint gLGeoPoint, int i, int i2, int i3, int i4, int i5, int i6) {
        if (akq.a() != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                double[] a2 = akq.a().a(gLGeoPoint);
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, a2[0]);
                jSONObject.put("lat", a2[1]);
                jSONObject.put("from", i);
                jSONObject.put("type", i2);
                jSONObject.put("itemid", i3);
                jSONObject.put("status", i4);
                jSONObject.put("text", i5);
                jSONObject.put("islogin", i6);
                akq.a().a("P00001", "B069", jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void a(String str, String str2, String str3) {
        if (akq.a() != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !str3.isEmpty()) {
            JSONObject jSONObject = new JSONObject();
            String[] split = str3.split("&");
            for (String split2 : split) {
                String[] split3 = split2.split(":");
                if (split3 != null && split3.length >= 2) {
                    try {
                        jSONObject.put(split3[0], split3[1]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            akq.a().a(str, str2, jSONObject);
        }
    }
}

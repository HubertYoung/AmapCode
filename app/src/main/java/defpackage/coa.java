package defpackage;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;
import com.autonavi.minimap.ajx3.debug.DevToolLog;
import java.net.HttpURLConnection;
import java.net.URL;

/* renamed from: coa reason: default package */
/* compiled from: NetUtil */
public final class coa {
    public static void a(final String str, final Callback callback) {
        new Thread() {
            public final void run() {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setReadTimeout(10000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        DevToolLog.log("get success");
                        if (callback != null) {
                            callback.handleMessage(Message.obtain(null, 200));
                            DevToolLog.log("Get方式请求成功，callback ok");
                        } else {
                            DevToolLog.log("Get方式请求成功，callback is null");
                        }
                    } else {
                        DevToolLog.log("Get方式请求失败");
                        if (callback != null) {
                            callback.handleMessage(Message.obtain(null, 404));
                            DevToolLog.log("Get方式请求失败，callback ok");
                        } else {
                            DevToolLog.log("Get方式请求失败，callback is null");
                        }
                    }
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("get exception: ");
                    sb.append(Log.getStackTraceString(e));
                    DevToolLog.log(sb.toString());
                }
            }
        }.start();
    }

    public static String a(Context context) {
        return Formatter.formatIpAddress(((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo().getIpAddress());
    }
}

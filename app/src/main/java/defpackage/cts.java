package defpackage;

import android.os.Vibrator;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cts reason: default package */
/* compiled from: VibrateAction */
public class cts extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        long j;
        if (a() != null) {
            String optString = jSONObject.optString("type");
            char c = 65535;
            int hashCode = optString.hashCode();
            if (hashCode != -1039745817) {
                if (hashCode != 111185) {
                    if (hashCode == 3436767 && optString.equals("peak")) {
                        c = 0;
                    }
                } else if (optString.equals("pop")) {
                    c = 1;
                }
            } else if (optString.equals("normal")) {
                c = 2;
            }
            switch (c) {
                case 0:
                    j = 30;
                    break;
                case 1:
                    j = 50;
                    break;
                default:
                    j = 500;
                    break;
            }
            try {
                Vibrator vibrator = (Vibrator) AMapAppGlobal.getApplication().getSystemService("vibrator");
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(j);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}

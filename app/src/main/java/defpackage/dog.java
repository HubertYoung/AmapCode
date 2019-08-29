package defpackage;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import org.json.JSONObject;

/* renamed from: dog reason: default package */
/* compiled from: LifeBaseAosResponser */
public class dog {
    public static final String a = AMapAppGlobal.getApplication().getString(R.string.network_error_message);
    public static final String b = AMapAppGlobal.getApplication().getString(R.string.error_fail_to_parse_data);
    public int c = -1;
    public String d = a;
    public String e = "";
    public long f = 0;
    public boolean g = false;
    public JSONObject h = null;

    public void a(byte[] bArr) {
        if (bArr == null) {
            this.c = -1;
            return;
        }
        try {
            this.h = new JSONObject(new String(bArr, "UTF-8"));
            this.e = this.h.getString("version");
            this.g = this.h.getBoolean("result");
            this.c = this.h.getInt("code");
            this.f = this.h.getLong("timestamp");
            this.d = this.h.optString("message");
        } catch (Exception unused) {
            this.g = false;
            this.c = -2;
            this.d = b;
        }
    }

    public String a() {
        return TextUtils.isEmpty(this.d) ? a : this.d;
    }
}

package defpackage;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cmg reason: default package */
/* compiled from: SceneConfigProvider */
class cmg implements i, lp {
    private boolean a = false;

    public void onConfigCallBack(int i) {
    }

    public final boolean a() {
        return this.a;
    }

    public void onConfigResultCallBack(int i, String str) {
        if (i != 4) {
            switch (i) {
                case 0:
                case 1:
                    break;
            }
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                int i2 = new JSONObject(str).getInt("url_detail_enable");
                boolean z = true;
                if (i2 != 1) {
                    z = false;
                }
                this.a = z;
            } catch (JSONException unused) {
                this.a = false;
            }
        }
    }
}

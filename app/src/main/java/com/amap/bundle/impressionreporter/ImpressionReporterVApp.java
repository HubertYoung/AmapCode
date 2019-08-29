package com.amap.bundle.impressionreporter;

import android.text.TextUtils;
import com.autonavi.annotation.VirtualApp;
import org.json.JSONException;
import org.json.JSONObject;

@VirtualApp(priority = 1000)
public class ImpressionReporterVApp extends esh {
    private a a = new a(0);

    static class a implements lp {
        public final void onConfigCallBack(int i) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (!TextUtils.isEmpty(str)) {
                try {
                    vt.a = new JSONObject(str).optInt("impression_retry_count", 5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        lo.a().b("impression_retry_count", this.a);
        lo.a().a((String) "impression_retry_count", (lp) this.a);
    }

    public void vAppDestroy() {
        lo.a().b("impression_retry_count", this.a);
    }
}

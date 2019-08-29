package defpackage;

import org.json.JSONObject;

/* renamed from: bgp reason: default package */
/* compiled from: CloudController */
public final class bgp {
    public static boolean a;
    public static boolean b;
    public lp c = new lp() {
        public final void onConfigCallBack(int i) {
        }

        public final void onConfigResultCallBack(int i, String str) {
            if (str != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.has("record_refactor")) {
                        bgt.b((String) "record_refactor", jSONObject.optInt("record_refactor", 1));
                    }
                    if (jSONObject.has("ossupload_switch")) {
                        bgt.b((String) "ossupload_switch", jSONObject.optInt("ossupload_switch", 1));
                    }
                    if (jSONObject.has("hotword_upload")) {
                        bgt.b((String) "hotword_upload", jSONObject.optInt("hotword_upload", 1));
                    }
                    if (jSONObject.has("wuws_switch")) {
                        bgt.b((String) "wuws_switch", jSONObject.optInt("wuws_switch", 0));
                    }
                    if (jSONObject.has("VUI_awake_switch")) {
                        bgt.b((String) "VUI_awake_switch", jSONObject.optInt("VUI_awake_switch", 0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public bgp() {
        boolean z = true;
        a = bgt.a((String) "record_refactor", 1) == 1;
        b = bgt.a((String) "VUI_awake_switch", 0) != 1 ? false : z;
        lo.a().a((String) "vui_navi", this.c);
    }

    public static boolean a() {
        return bgt.a((String) "ossupload_switch", 0) == 1;
    }
}

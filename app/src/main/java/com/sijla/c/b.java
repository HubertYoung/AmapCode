package com.sijla.c;

import android.content.Context;
import android.os.Build.VERSION;
import com.alipay.sdk.sys.a;
import com.sijla.g.h;
import com.sijla.g.i;
import com.sijla.g.j;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private String a;
    private String b;
    private Context c;
    private JSONObject d = null;

    public b(Context context, String str, String str2) {
        this.c = context;
        String str3 = (String) j.b(context, "cfgver", "");
        String packageName = context.getPackageName();
        String b2 = i.b(context);
        StringBuilder sb = new StringBuilder(str);
        sb.append("?tp=1");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("app", packageName);
            jSONObject.put(Oauth2AccessToken.KEY_UID, b2);
            jSONObject.put(a.h, com.sijla.b.a.a);
            jSONObject.put("appver", com.sijla.g.a.a.j(context));
            jSONObject.put("osver", VERSION.SDK_INT);
            jSONObject.put("cver", str3);
            jSONObject.put("chl", com.sijla.g.b.f(context));
            jSONObject.put("yz", "0");
            this.d = com.sijla.g.b.a(jSONObject);
            if (this.d != null) {
                sb.append("&ts=");
                sb.append(this.d.getString("ts"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.a = sb.toString();
        this.b = str2;
    }

    public File a() {
        File file;
        if (!com.sijla.g.a.a.g(this.c)) {
            return null;
        }
        if (this.d != null) {
            file = h.a(this.a, this.d, this.b);
        } else {
            file = h.a(this.a, this.b);
        }
        if (file != null && file.exists()) {
            file.isFile();
        }
        return file;
    }
}

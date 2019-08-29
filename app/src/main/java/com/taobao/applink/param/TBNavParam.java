package com.taobao.applink.param;

import com.taobao.applink.a.a;
import com.taobao.applink.exception.TBAppLinkException;
import com.taobao.applink.util.e;
import java.net.URLEncoder;
import java.util.Iterator;
import org.json.JSONObject;

public class TBNavParam extends TBBaseParam {
    public static final String ACTION_NAME = "ali.open.nav";

    protected TBNavParam() {
        super(a.JUMP);
        this.mParams.put("action", "ali.open.nav");
    }

    private void setExtraParams(JSONObject jSONObject) {
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                String encode = URLEncoder.encode(keys.next(), "UTF-8");
                this.mExtraParams.put(encode, URLEncoder.encode(jSONObject.getString(encode), "UTF-8"));
            } catch (Throwable unused) {
            }
        }
    }

    public boolean checkParams(JSONObject jSONObject) {
        return false;
    }

    public String getH5URL() throws TBAppLinkException {
        return super.getH5URL(null);
    }

    public void setParams(JSONObject jSONObject) {
        if (jSONObject != null) {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                try {
                    String encode = URLEncoder.encode(keys.next(), "UTF-8");
                    String encode2 = URLEncoder.encode(jSONObject.getString(encode), "UTF-8");
                    if (!e.a(encode2)) {
                        if ("e".equals(encode)) {
                            setE(encode2);
                        } else if ("type".equals(encode)) {
                            setType(encode2);
                        } else if ("pid".equals(encode)) {
                            this.mExtraParams.put("pid", encode2);
                        } else if ("params".equals(encode)) {
                            String string = jSONObject.getString("params");
                            if (!e.a(string)) {
                                setExtraParams(new JSONObject(string));
                            }
                        } else {
                            this.mParams.put(encode, encode2);
                        }
                    }
                } catch (Throwable unused) {
                }
            }
        }
    }
}

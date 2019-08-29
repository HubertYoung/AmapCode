package com.taobao.applink.c;

import com.alibaba.baichuan.android.trade.constants.ConfigConstant;
import com.taobao.applink.util.c;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public Map a = new HashMap();
    public String b;
    public String c;
    public long d;
    public String e;
    public String f;

    private String b() {
        StringBuilder sb = new StringBuilder();
        for (String str : this.a.keySet()) {
            if (!ConfigConstant.CHECK_GROUP_NAME.equals(str)) {
                sb.append(str);
                Map map = (Map) this.a.get(str);
                for (String str2 : map.keySet()) {
                    sb.append(str2);
                    sb.append((String) map.get(str2));
                }
            }
        }
        try {
            sb.append(ConfigConstant.MD5_SALT);
            char[] charArray = sb.toString().toCharArray();
            Arrays.sort(charArray);
            return c.a(new String(charArray).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void a(JSONObject jSONObject) {
        try {
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                HashMap hashMap = new HashMap();
                String next = keys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                Iterator<String> keys2 = jSONObject2.keys();
                while (keys2.hasNext()) {
                    String next2 = keys2.next();
                    hashMap.put(next2, jSONObject2.getString(next2));
                }
                this.a.put(next, hashMap);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public boolean a() {
        if (!this.a.isEmpty()) {
            String b2 = b();
            Map map = (Map) this.a.get(ConfigConstant.CHECK_GROUP_NAME);
            if (map != null) {
                String str = (String) map.get("sign");
                if (str != null && str.equals(b2)) {
                    return true;
                }
            }
        }
        return false;
    }
}

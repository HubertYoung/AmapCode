package com.alibaba.baichuan.android.trade.config.a;

import com.alibaba.baichuan.android.trade.constants.ConfigConstant;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public static final String b = "a";
    public Map a = new HashMap();
    private Map c;

    public Map a() {
        return this.a;
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
        } catch (JSONException e) {
            AlibcLogger.e(b, "json转换为map失败");
            e.printStackTrace();
        }
    }

    public boolean b() {
        if (this.a == null) {
            return false;
        }
        this.c = (Map) this.a.get(ConfigConstant.CHECK_GROUP_NAME);
        if (this.c != null) {
            this.a.remove(ConfigConstant.CHECK_GROUP_NAME);
        }
        return true;
    }

    public void c() {
        if (this.c != null && this.a != null) {
            this.a.put(ConfigConstant.CHECK_GROUP_NAME, this.c);
        }
    }
}

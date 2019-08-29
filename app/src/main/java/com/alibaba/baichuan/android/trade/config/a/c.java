package com.alibaba.baichuan.android.trade.config.a;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.constants.ConfigConstant;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private final String a = c.class.getSimpleName();
    private SharedPreferences b = AlibcContext.context.getSharedPreferences(ConfigConstant.SP_CONFIG_NAME, 0);

    public a a() {
        JSONObject jSONObject;
        a aVar;
        a aVar2 = null;
        String string = this.b.getString(ConfigConstant.SP_CONFIG_NAME, null);
        AlibcLogger.d(this.a, "SP里面的值为:".concat(String.valueOf(string)));
        if (string != null) {
            try {
                jSONObject = new JSONObject(string);
                aVar = new a();
            } catch (JSONException e) {
                e = e;
                String str = this.a;
                StringBuilder sb = new StringBuilder("拼接json出错");
                sb.append(e.getMessage());
                AlibcLogger.e(str, sb.toString());
                return aVar2;
            }
            try {
                aVar.a(jSONObject);
                return aVar;
            } catch (JSONException e2) {
                e = e2;
                aVar2 = aVar;
                String str2 = this.a;
                StringBuilder sb2 = new StringBuilder("拼接json出错");
                sb2.append(e.getMessage());
                AlibcLogger.e(str2, sb2.toString());
                return aVar2;
            }
        }
        return aVar2;
    }

    public void a(a aVar) {
        Editor edit = this.b.edit();
        JSONObject jSONObject = new JSONObject();
        for (String str : aVar.a().keySet()) {
            try {
                jSONObject.put(str, new JSONObject((Map) aVar.a().get(str)));
            } catch (JSONException e) {
                String str2 = this.a;
                StringBuilder sb = new StringBuilder("拼接json出错");
                sb.append(e.getMessage());
                AlibcLogger.e(str2, sb.toString());
            }
        }
        edit.putString(ConfigConstant.SP_CONFIG_NAME, jSONObject.toString());
        edit.commit();
    }
}

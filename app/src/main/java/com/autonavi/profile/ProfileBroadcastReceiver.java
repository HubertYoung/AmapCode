package com.autonavi.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.taobao.agoo.control.data.BaseDO;
import org.json.JSONObject;

public class ProfileBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if ("com.autonavi.minimap.debug.profile".equals(intent.getAction())) {
            StringBuilder sb = new StringBuilder("accept action: ");
            sb.append(intent.getAction());
            enx.a(sb.toString());
            try {
                String stringExtra = intent.getStringExtra(BaseDO.JSON_CMD);
                if (stringExtra != null) {
                    if (stringExtra.trim().length() != 0) {
                        enx.a("receive: ".concat(String.valueOf(stringExtra)));
                        JSONObject b = enw.b(stringExtra);
                        if (b == null) {
                            enx.a((String) "parser cmd error, skip!");
                            StringBuilder sb2 = new StringBuilder("cmd template: [");
                            sb2.append("{\"action\":\"reset\"}");
                            sb2.append("] [");
                            sb2.append(enw.a("{\"action\":\"reset\"}"));
                            sb2.append("]");
                            enx.b(sb2.toString());
                            StringBuilder sb3 = new StringBuilder("cmd template: [");
                            sb3.append("{\"action\":\"init\", \"params\":\"/sdcard/autonavi/lib\"}");
                            sb3.append("] [");
                            sb3.append(enw.a("{\"action\":\"init\", \"params\":\"/sdcard/autonavi/lib\"}"));
                            sb3.append("]");
                            enx.b(sb3.toString());
                            return;
                        }
                        String optString = b.optString("action");
                        String optString2 = b.optString("params");
                        String optString3 = b.optString("callback");
                        enx.a("start cmd: ".concat(String.valueOf(optString)));
                        enx.a("cmd params: ".concat(String.valueOf(optString2)));
                        enx.a("cmd callback: ".concat(String.valueOf(optString3)));
                        try {
                            Class<?> cls = Class.forName("com.autonavi.profile.ProfileBroadcastExecutor");
                            cls.getMethod(optString, new Class[]{Context.class, String.class, String.class}).invoke(cls, new Object[]{context, optString2, optString3});
                        } catch (Exception e) {
                            e.printStackTrace();
                            StringBuilder sb4 = new StringBuilder("cmd error: ");
                            sb4.append(e.getMessage());
                            enx.b(sb4.toString());
                        }
                        enx.a("finish cmd: ".concat(String.valueOf(optString)));
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}

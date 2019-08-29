package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: evb reason: default package */
/* compiled from: GetInfoFromFile */
public class evb implements Runnable {
    public static Object a = new Object();
    private static final String e = "evb";
    private Context b;
    private int c = 1;
    private evo d = null;
    private boolean f = true;
    private boolean g = true;

    public evb(Context context) {
        this.b = context.getApplicationContext();
    }

    public evb(Context context, byte b2) {
        this.b = context.getApplicationContext();
        this.c = 0;
    }

    public void run() {
        synchronized (a) {
            if (!((String) ewp.b(this.b, "hmt_send_switch", "1")).equals("1")) {
                euw.a((String) "Forbid send data by sendSwitch!");
            } else if (this.c != 0) {
                euw.a((String) "Empty data in the database!");
                eve.b();
                try {
                    evl a2 = evl.a(this.b);
                    a(a2, eve.a(this.b, evd.h), "hmtInfo");
                    a(a2, eve.a(this.b, evd.i), "reqInfo");
                    if (this.f && this.g) {
                        a();
                    }
                    eve.b();
                } catch (SQLiteException e2) {
                    StringBuilder sb = new StringBuilder("Collected:");
                    sb.append(e2.getMessage());
                    euw.a(sb.toString());
                }
            } else if (eve.e(this.b)) {
                euw.a((String) "Send every day client data!");
                eve.b();
                String g2 = euw.g(this.b);
                if (g2 != null && !g2.equals("")) {
                    JSONObject a3 = evx.a(this.b);
                    JSONArray jSONArray = new JSONArray();
                    try {
                        jSONArray.put(0, eve.a(this.b, a3, "client_data_list"));
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("client_data_list", jSONArray);
                        if (evh.a(this.b, eve.a(this.b, evd.h), jSONObject.toString(), evd.p, euw.d(this.b))) {
                            a();
                        }
                    } catch (JSONException e3) {
                        StringBuilder sb2 = new StringBuilder("Collected:");
                        sb2.append(e3.getMessage());
                        euw.a(sb2.toString());
                    }
                }
            }
        }
    }

    private void a(evl evl, String str, String str2) {
        int i = 0;
        while (true) {
            if (i >= evd.z / evd.u) {
                break;
            }
            new ArrayList();
            try {
                ArrayList<ewj> a2 = evl.a(str2, evd.u);
                if (a2.size() == 0) {
                    euw.a((String) "No data 4 upload!");
                    break;
                }
                ewr ewr = new ewr(this.b, a2, str, euw.d(this.b));
                if (evh.a(ewr.d, ewr.b, ewr.a.toString(), "all_data", ewr.c)) {
                    evl.b(str2, a2.get(a2.size() - 1).a.intValue());
                    if (a2.size() < evd.u) {
                        break;
                    }
                    i++;
                } else if (str2.equals("hmtInfo")) {
                    this.f = false;
                } else if (str2.equals("reqInfo")) {
                    this.g = false;
                }
            } catch (SQLiteException e2) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e2.getMessage());
                euw.a(sb.toString());
                return;
            }
        }
        evl.a(str2);
    }

    private void a() {
        ewp.a(this.b, "hmt_init_savetime", "upload_save_time", Long.valueOf(System.currentTimeMillis()));
        ewp.a(this.b, (String) "hmt_send_all_data_success_once", (Object) Boolean.TRUE);
        ewp.a(this.b, (String) "hmt_all_data_send_time", (Object) Long.valueOf(System.currentTimeMillis()));
    }
}

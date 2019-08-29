package com.sijla.g;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.sijla.a.a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class e {
    public static void a(Context context, String str, String str2) {
        a(context, str, str2, null, "-1", "");
    }

    public static void a(Context context, String str, String str2, JSONObject jSONObject, String str3, String str4) {
        final Context context2 = context;
        final String str5 = str;
        final String str6 = str2;
        final String str7 = str4;
        final JSONObject jSONObject2 = jSONObject;
        final String str8 = str3;
        AnonymousClass1 r0 = new Runnable() {
            public final void run() {
                try {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(b.g(context2));
                    arrayList.add(str5);
                    arrayList.add(str6);
                    if (!b.a(str7)) {
                        arrayList.add(str7);
                    } else {
                        arrayList.add("1");
                    }
                    arrayList.add(jSONObject2.toString());
                    arrayList.add(b.c(context2));
                    arrayList.add(String.valueOf(d.d()));
                    arrayList.add(str8);
                    arrayList.add(Build.BRAND);
                    arrayList.add(Build.MODEL);
                    arrayList.add(VERSION.RELEASE);
                    c.a(context2, b.h((String) "E"), (List<String>) arrayList);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        a.a(r0);
    }
}

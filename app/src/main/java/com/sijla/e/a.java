package com.sijla.e;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import com.sijla.g.b;
import com.sijla.g.c;
import com.sijla.g.d;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class a implements Runnable {
    private Context a;
    private String b = "";
    private String c = "";

    public a(Context context, Intent intent) {
        this.a = context.getApplicationContext();
        this.c = intent.getAction();
        this.b = intent.getDataString().substring(8);
    }

    public void run() {
        try {
            String str = this.c;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -810471698) {
                if (hashCode != 525384130) {
                    if (hashCode == 1544582882) {
                        if (str.equals("android.intent.action.PACKAGE_ADDED")) {
                            c2 = 0;
                        }
                    }
                } else if (str.equals("android.intent.action.PACKAGE_REMOVED")) {
                    c2 = 2;
                }
            } else if (str.equals("android.intent.action.PACKAGE_REPLACED")) {
                c2 = 1;
            }
            switch (c2) {
                case 0:
                    ArrayList<String> a2 = a("1");
                    StringBuilder sb = new StringBuilder("AS.csv-");
                    sb.append(System.currentTimeMillis());
                    sb.append("_1");
                    c.a(this.a, sb.toString(), (List<String>) a2);
                    a();
                    return;
                case 1:
                    ArrayList<String> a3 = a("2");
                    StringBuilder sb2 = new StringBuilder("AS.csv-");
                    sb2.append(System.currentTimeMillis());
                    sb2.append("_2");
                    c.a(this.a, sb2.toString(), (List<String>) a3);
                    return;
                case 2:
                    if (!this.b.equals(this.a.getPackageName())) {
                        ArrayList<String> a4 = a("0");
                        StringBuilder sb3 = new StringBuilder("AS.csv-");
                        sb3.append(System.currentTimeMillis());
                        sb3.append("_0");
                        c.a(this.a, sb3.toString(), (List<String>) a4);
                        break;
                    } else {
                        return;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void a() {
        if (VERSION.SDK_INT < 26) {
            try {
                int a2 = b.a(this.a, this.b);
                if (a2 != -1) {
                    com.sijla.g.a a3 = com.sijla.g.a.a(this.a);
                    JSONObject b2 = a3.b("tr4djs");
                    if (-1 == b2.optLong(String.valueOf(a2), -1)) {
                        b2.put(String.valueOf(a2), 0);
                        a3.a((String) "tr4djs", b2);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private ArrayList<String> a(String str) {
        String a2 = com.sijla.g.a.a.a(this.b, this.a);
        if (b.a(a2)) {
            a2 = "";
        }
        String str2 = com.sijla.g.a.a.a(this.a, this.b) ? "1" : "0";
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(b.g(this.a));
        arrayList.add(str);
        arrayList.add(this.b);
        arrayList.add(str2);
        arrayList.add(a2);
        if ("0".equals(str)) {
            arrayList.add("");
            arrayList.add("");
        } else {
            String[] b2 = b(this.b);
            arrayList.add(b2[0]);
            arrayList.add(b2[1]);
        }
        arrayList.add(String.valueOf(d.d()));
        return arrayList;
    }

    private String[] b(String str) {
        String[] strArr = {"", ""};
        try {
            strArr[1] = this.a.getPackageManager().getInstallerPackageName(str);
            com.sijla.b.c a2 = b.a(this.a, (String) "AK", str);
            if (a2.b()) {
                File file = new File((String) a2.a());
                if (file.exists()) {
                    String b2 = b.b(b.a(file));
                    if (!b.a(b2)) {
                        strArr[0] = b2.split("\t")[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strArr;
    }
}

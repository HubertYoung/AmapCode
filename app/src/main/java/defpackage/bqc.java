package defpackage;

import android.content.Context;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/* renamed from: bqc reason: default package */
/* compiled from: BuildTypeConfigHelper */
public class bqc {
    private static bqc a;
    private Context b = AMapAppGlobal.getApplication();
    private HashMap<String, String> c = new HashMap<>();

    public static bqc a() {
        if (a == null) {
            synchronized (bqc.class) {
                try {
                    if (a == null) {
                        a = new bqc();
                    }
                }
            }
        }
        return a;
    }

    private bqc() {
        d();
    }

    private InputStream c() {
        try {
            return this.b.getResources().getAssets().open("buildTypeConfig.data");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void d() {
        try {
            InputStream c2 = c();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(c2, "UTF-8"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    c2.close();
                    return;
                } else if (readLine.length() > 0 && !readLine.startsWith(MetaRecord.LOG_SEPARATOR)) {
                    String[] split = readLine.split("=");
                    if (split.length >= 2) {
                        String str = split[0];
                        String str2 = split[1];
                        for (int i = 0; i < split.length - 2; i++) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str2);
                            sb.append("=");
                            String sb2 = sb.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append(split[i + 2]);
                            str2 = sb3.toString();
                        }
                        if (!(str == null || str2 == null)) {
                            String trim = str.trim();
                            String trim2 = str2.trim();
                            if (trim.length() > 0 && trim2.length() > 0) {
                                this.c.put(trim, trim2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String b() {
        return this.c.containsKey("default_network") ? this.c.get("default_network") : "public";
    }
}

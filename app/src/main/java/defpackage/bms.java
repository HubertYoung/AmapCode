package defpackage;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* renamed from: bms reason: default package */
/* compiled from: FDManager */
public class bms {
    private static bms c;
    public String a;
    public File b;
    private FileInputStream[] d;

    public static bms a() {
        if (c == null) {
            synchronized (bms.class) {
                try {
                    c = new bms();
                }
            }
        }
        return c;
    }

    private bms() {
    }

    public final String b() {
        if (TextUtils.isEmpty(this.a)) {
            return null;
        }
        d();
        List<String> e = e();
        if (e.size() == 0 || e.size() < 300) {
            return null;
        }
        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append("FD List:\n");
        for (String append : e) {
            sb.append(append);
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }

    public final void c() {
        this.d = new FileInputStream[20];
        for (int i = 0; i < 20; i++) {
            try {
                this.d[i] = new FileInputStream(this.b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public final void d() {
        if (this.d != null) {
            for (int i = 0; i < 20; i++) {
                if (this.d[i] != null) {
                    try {
                        this.d[i].close();
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }

    private static List<String> e() {
        ArrayList arrayList = new ArrayList();
        try {
            File[] listFiles = new File("/proc/self/fd").listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    String canonicalPath = file.getCanonicalPath();
                    StringBuilder sb = new StringBuilder();
                    sb.append(file.getName());
                    sb.append(" -> ");
                    sb.append(canonicalPath);
                    arrayList.add(sb.toString());
                }
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }
}

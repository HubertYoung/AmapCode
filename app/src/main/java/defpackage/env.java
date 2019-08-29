package defpackage;

import android.content.Context;
import java.io.File;

/* renamed from: env reason: default package */
/* compiled from: ProfileCopyThread */
public final class env extends Thread {
    private static boolean a = false;
    private String b;
    private Context c;
    private int d;

    public env(Context context, String str, int i) {
        this.b = str;
        this.c = context;
        this.d = i;
    }

    public static boolean a() {
        return a;
    }

    public final void run() {
        a = true;
        try {
            akp.a(this.b);
            String a2 = enx.a();
            StringBuilder sb = new StringBuilder();
            sb.append(enx.b());
            sb.append("/libajx_v3.so");
            String sb2 = sb.toString();
            File file = new File(sb2);
            StringBuilder sb3 = new StringBuilder("copy ajx so:");
            sb3.append(file.exists());
            sb3.append("! ");
            enx.a(sb3.toString());
            if (file.exists()) {
                enx.a(sb2, a2);
                enx.c(a2);
                file.delete();
            }
            enx.c(enx.b());
            enx.c(enx.c());
            StringBuilder sb4 = new StringBuilder("copy finish! count: [");
            sb4.append(this.d);
            sb4.append("].");
            enx.a(sb4.toString());
            enx.a(this.c);
        } catch (Exception e) {
            e.printStackTrace();
            StringBuilder sb5 = new StringBuilder("copy error! ");
            sb5.append(e.getMessage());
            enx.b(sb5.toString());
        }
        a = false;
    }
}

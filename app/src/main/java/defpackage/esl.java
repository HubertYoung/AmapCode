package defpackage;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

/* renamed from: esl reason: default package */
public class esl {
    private static int h;
    public Context a;
    public List<est> b;
    public List<esp> c;
    public String d;
    public String e;
    public String f;
    public etb g;

    private esl() {
        this.b = new ArrayList();
        this.c = new ArrayList();
        synchronized (esl.class) {
            if (h > 0) {
                throw new RuntimeException("PushManager can't create again!");
            }
            h++;
        }
        a((esp) new esm());
        a((esp) new esq());
        a((esp) new esn());
        a((est) new esr());
        a((est) new esu());
        a((est) new ess());
    }

    /* synthetic */ esl(byte b2) {
        this();
    }

    public static esl a() {
        return esv.a;
    }

    private synchronized void a(esp esp) {
        this.c.add(esp);
    }

    private synchronized void a(est est) {
        this.b.add(est);
    }

    public static boolean a(Context context) {
        return esy.a(context, "com.coloros.mcs") && esy.b(context, "com.coloros.mcs") >= 1012 && esy.a(context, "com.coloros.mcs", "supportOpenPush");
    }

    public static void a(Context context, etc etc, String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("com.coloros.mcssdk.action.RECEIVE_SDK_MESSAGE");
            intent.setPackage("com.coloros.mcs");
            intent.putExtra("type", 12291);
            StringBuilder sb = new StringBuilder();
            sb.append(etc.l);
            intent.putExtra("taskID", sb.toString());
            intent.putExtra("appPackage", etc.k);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(etc.j);
            intent.putExtra("messageID", sb2.toString());
            intent.putExtra("messageType", 4098);
            intent.putExtra("eventID", str);
            context.startService(intent);
        } catch (Exception e2) {
            StringBuilder sb3 = new StringBuilder("statisticMessage--Exception");
            sb3.append(e2.getMessage());
            esx.b(sb3.toString());
        }
    }

    public static void a(Context context, etf etf, String str) {
        try {
            Intent intent = new Intent();
            intent.setAction("com.coloros.mcssdk.action.RECEIVE_SDK_MESSAGE");
            intent.setPackage("com.coloros.mcs");
            intent.putExtra("type", 12291);
            StringBuilder sb = new StringBuilder();
            sb.append(etf.l);
            intent.putExtra("taskID", sb.toString());
            intent.putExtra("appPackage", etf.k);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(etf.j);
            intent.putExtra("messageID", sb2.toString());
            intent.putExtra("messageType", 4103);
            intent.putExtra("eventID", str);
            context.startService(intent);
        } catch (Exception e2) {
            StringBuilder sb3 = new StringBuilder("statisticMessage--Exception");
            sb3.append(e2.getMessage());
            esx.b(sb3.toString());
        }
    }
}

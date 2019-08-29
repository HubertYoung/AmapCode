package com.xiaomi.mipush.sdk;

import android.content.Context;
import com.xiaomi.channel.commonutils.logger.b;
import java.util.HashMap;
import java.util.Map;

public class e implements AbstractPushManager {
    private static volatile e a;
    private Context b;
    private PushConfiguration c;
    private Map<d, AbstractPushManager> d = new HashMap();

    private e(Context context) {
        this.b = context;
    }

    public static e a(Context context) {
        if (a == null) {
            synchronized (e.class) {
                try {
                    if (a == null) {
                        a = new e(context);
                    }
                }
            }
        }
        return a;
    }

    private void a() {
        if (this.c != null) {
            if (this.c.getOpenHmsPush()) {
                StringBuilder sb = new StringBuilder();
                sb.append(" HW user switch : ");
                sb.append(this.c.getOpenHmsPush());
                sb.append(" HW online switch : ");
                sb.append(g.b(this.b, d.ASSEMBLE_PUSH_HUAWEI));
                sb.append(" HW isSupport : ");
                sb.append(ac.HUAWEI.equals(k.a(this.b)));
                b.c(sb.toString());
            }
            if (this.c.getOpenHmsPush() && g.b(this.b, d.ASSEMBLE_PUSH_HUAWEI) && ac.HUAWEI.equals(k.a(this.b))) {
                if (!b(d.ASSEMBLE_PUSH_HUAWEI)) {
                    a(d.ASSEMBLE_PUSH_HUAWEI, ag.a(this.b, d.ASSEMBLE_PUSH_HUAWEI));
                }
                b.c("hw manager add to list");
            } else if (b(d.ASSEMBLE_PUSH_HUAWEI)) {
                AbstractPushManager c2 = c(d.ASSEMBLE_PUSH_HUAWEI);
                if (c2 != null) {
                    a(d.ASSEMBLE_PUSH_HUAWEI);
                    c2.unregister();
                }
            }
            if (this.c.getOpenFCMPush()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" FCM user switch : ");
                sb2.append(this.c.getOpenFCMPush());
                sb2.append(" FCM online switch : ");
                sb2.append(g.b(this.b, d.ASSEMBLE_PUSH_FCM));
                sb2.append(" FCM isSupport : ");
                sb2.append(k.b(this.b));
                b.c(sb2.toString());
            }
            if (this.c.getOpenFCMPush() && g.b(this.b, d.ASSEMBLE_PUSH_FCM) && k.b(this.b)) {
                if (!b(d.ASSEMBLE_PUSH_FCM)) {
                    a(d.ASSEMBLE_PUSH_FCM, ag.a(this.b, d.ASSEMBLE_PUSH_FCM));
                }
                b.c("fcm manager add to list");
            } else if (b(d.ASSEMBLE_PUSH_FCM)) {
                AbstractPushManager c3 = c(d.ASSEMBLE_PUSH_FCM);
                if (c3 != null) {
                    a(d.ASSEMBLE_PUSH_FCM);
                    c3.unregister();
                }
            }
            if (this.c.getOpenCOSPush()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(" COS user switch : ");
                sb3.append(this.c.getOpenCOSPush());
                sb3.append(" COS online switch : ");
                sb3.append(g.b(this.b, d.ASSEMBLE_PUSH_COS));
                sb3.append(" COS isSupport : ");
                sb3.append(k.c(this.b));
                b.c(sb3.toString());
            }
            if (this.c.getOpenCOSPush() && g.b(this.b, d.ASSEMBLE_PUSH_COS) && k.c(this.b)) {
                a(d.ASSEMBLE_PUSH_COS, ag.a(this.b, d.ASSEMBLE_PUSH_COS));
            } else if (b(d.ASSEMBLE_PUSH_COS)) {
                AbstractPushManager c4 = c(d.ASSEMBLE_PUSH_COS);
                if (c4 != null) {
                    a(d.ASSEMBLE_PUSH_COS);
                    c4.unregister();
                }
            }
        }
    }

    public void a(PushConfiguration pushConfiguration) {
        this.c = pushConfiguration;
    }

    public void a(d dVar) {
        this.d.remove(dVar);
    }

    public void a(d dVar, AbstractPushManager abstractPushManager) {
        if (abstractPushManager != null) {
            if (this.d.containsKey(dVar)) {
                this.d.remove(dVar);
            }
            this.d.put(dVar, abstractPushManager);
        }
    }

    public boolean b(d dVar) {
        return this.d.containsKey(dVar);
    }

    public AbstractPushManager c(d dVar) {
        return this.d.get(dVar);
    }

    public boolean d(d dVar) {
        switch (f.a[dVar.ordinal()]) {
            case 1:
                if (this.c != null) {
                    return this.c.getOpenHmsPush();
                }
                break;
            case 2:
                if (this.c != null) {
                    return this.c.getOpenFCMPush();
                }
                break;
            case 3:
                if (this.c != null) {
                    return this.c.getOpenCOSPush();
                }
                break;
        }
        return false;
    }

    public void register() {
        b.c("assemble push register");
        if (this.d.size() <= 0) {
            a();
        }
        for (AbstractPushManager next : this.d.values()) {
            if (next != null) {
                next.register();
            }
        }
    }

    public void unregister() {
        b.c("assemble push unregister");
        for (AbstractPushManager next : this.d.values()) {
            if (next != null) {
                next.unregister();
            }
        }
        this.d.clear();
    }
}

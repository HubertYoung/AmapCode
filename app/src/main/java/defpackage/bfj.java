package defpackage;

import android.os.Looper;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.vcs.NativeVcsManager;
import java.util.LinkedList;
import java.util.List;

/* renamed from: bfj reason: default package */
/* compiled from: VUIVoiceAwakeSwicth */
public class bfj {
    private static volatile bfj d;
    public MapSharePreference a = new MapSharePreference((String) "vui_voice_awake");
    public int b = 0;
    /* access modifiers changed from: private */
    public List<bfm> c = new LinkedList();

    private bfj() {
        if (this.a.contains("voice_awake_switch")) {
            if (!this.a.contains("voice_wakeup_switch")) {
                boolean booleanValue = this.a.getBooleanValue("voice_awake_switch", false);
                if (booleanValue) {
                    this.a.putBooleanValue("voice_wakeup_switch", booleanValue);
                }
            }
            this.a.remove("voice_awake_switch");
        }
    }

    public static bfj a() {
        if (d == null) {
            synchronized (bfj.class) {
                try {
                    if (d == null) {
                        d = new bfj();
                    }
                }
            }
        }
        return d;
    }

    public final synchronized void a(bfm bfm) {
        if (bfm != null) {
            if (!this.c.contains(bfm)) {
                this.c.add(bfm);
            }
        }
    }

    public final synchronized void b(bfm bfm) {
        if (bfm != null) {
            if (this.c.contains(bfm)) {
                this.c.remove(bfm);
            }
        }
    }

    public final boolean b() {
        if (this.a.contains("voice_wakeup_switch")) {
            return this.a.getBooleanValue("voice_wakeup_switch", false);
        }
        if (bgt.a("VUI_awake_switch")) {
            return bgp.b;
        }
        return false;
    }

    private void a(final boolean z) {
        if (b()) {
            AnonymousClass2 r0 = new Runnable() {
                public final void run() {
                    synchronized (this) {
                        for (bfm a2 : bfj.this.c) {
                            a2.a(z);
                        }
                    }
                }
            };
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                aho.a(r0);
                return;
            }
            r0.run();
        }
    }

    public final void a(int i) {
        this.b = i;
        if (i == 1) {
            a(false);
            if (NativeVcsManager.getInstance().isInit()) {
                NativeVcsManager.getInstance().stopListening(true);
            }
        } else {
            a(true);
            if (NativeVcsManager.getInstance().isInit()) {
                NativeVcsManager.getInstance().tryStartListening();
            }
        }
    }
}

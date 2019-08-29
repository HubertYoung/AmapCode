package defpackage;

import com.autonavi.minimap.bundle.share.util.ShareFinishCallback;
import java.util.ArrayList;

/* renamed from: ddi reason: default package */
/* compiled from: ShareCallbackManager */
public class ddi {
    private static volatile ddi b;
    ArrayList<ShareFinishCallback> a = new ArrayList<>();
    private ArrayList<dcd> c = new ArrayList<>();

    private ddi() {
    }

    public static ddi a() {
        if (b == null) {
            synchronized (ddi.class) {
                try {
                    if (b == null) {
                        b = new ddi();
                    }
                }
            }
        }
        return b;
    }

    public final void a(dcd dcd) {
        if (dcd != null && !this.c.contains(dcd)) {
            this.c.add(dcd);
        }
    }

    public final void b() {
        this.a.clear();
        this.c.clear();
    }

    public final void a(int i, int i2) {
        for (int i3 = 0; i3 < this.a.size(); i3++) {
            ShareFinishCallback shareFinishCallback = this.a.get(i3);
            if (shareFinishCallback != null) {
                shareFinishCallback.a(i2);
            }
        }
        for (int i4 = 0; i4 < this.c.size(); i4++) {
            dcd dcd = this.c.get(i4);
            if (dcd != null) {
                dcd.onFinish(i, i2);
            }
        }
    }
}

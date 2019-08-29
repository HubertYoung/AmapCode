package defpackage;

import com.autonavi.minimap.R;

/* renamed from: ebp reason: default package */
/* compiled from: ShareBikeLineConfig */
final class ebp extends eap {
    private static ebp a;

    public final int b() {
        return -14395987;
    }

    private ebp() {
    }

    public static synchronized ebp g() {
        ebp ebp;
        synchronized (ebp.class) {
            try {
                if (a == null) {
                    a = new ebp();
                }
                ebp = a;
            }
        }
        return ebp;
    }

    public final int c() {
        return R.drawable.route_sharebike_aolr;
    }
}

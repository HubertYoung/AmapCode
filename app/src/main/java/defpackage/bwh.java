package defpackage;

import android.os.Looper;
import com.autonavi.map.search.comment.common.redux.MustOnMainThreadException;

/* renamed from: bwh reason: default package */
/* compiled from: Utils */
public final class bwh {
    public static void a() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new MustOnMainThreadException();
        }
    }
}

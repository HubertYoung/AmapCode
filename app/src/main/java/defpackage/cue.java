package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.bundle.banner.net.BannerResult;

/* renamed from: cue reason: default package */
/* compiled from: ActivitiesDataStore */
public final class cue {
    private static cue b;
    public BannerResult a = new BannerResult();

    @NonNull
    public static cue a() {
        if (b == null) {
            b = new cue();
        }
        return b;
    }
}

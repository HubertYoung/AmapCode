package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bcs reason: default package */
/* compiled from: ResultCommonUtils */
public final class bcs {
    public static List<aup> a(InfoliteResult infoliteResult) {
        if (c(infoliteResult) && infoliteResult.searchInfo.h != null) {
            return new ArrayList(infoliteResult.searchInfo.h);
        }
        return null;
    }

    public static boolean b(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.mWrapper == null) ? false : true;
    }

    private static boolean c(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.searchInfo == null) ? false : true;
    }
}

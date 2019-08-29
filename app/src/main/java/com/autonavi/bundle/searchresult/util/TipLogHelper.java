package com.autonavi.bundle.searchresult.util;

import com.autonavi.common.model.POI;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class TipLogHelper {
    public static String a;
    public static String b;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TipStatus {
    }

    public static boolean a(POI poi) {
        bck bck = (bck) a.a.a(bck.class);
        if (bck != null) {
            return bck.a(poi);
        }
        return false;
    }
}

package com.autonavi.minimap.base.overlay;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;

@SuppressFBWarnings({"LI_LAZY_INIT_STATIC", "UWF_NULL_FIELD"})
class OverlayDebugUtil {
    private static final String FILE_NAME = "texturename.txt";
    private static final int MARKER_MAX_SIZE = 290;
    private static ArrayList<Integer> mMarkerIdArray;

    static void writeOverlayTextureId(int i) {
    }

    OverlayDebugUtil() {
    }

    static void clearDebugCache() {
        if (mMarkerIdArray != null) {
            mMarkerIdArray.clear();
            mMarkerIdArray = null;
        }
    }
}

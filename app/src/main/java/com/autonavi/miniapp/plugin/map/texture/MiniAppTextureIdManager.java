package com.autonavi.miniapp.plugin.map.texture;

import android.util.SparseIntArray;
import java.util.ArrayList;

public class MiniAppTextureIdManager {
    private static final int DYNAMIC_MARKER_ID_MAX = 3350;
    private static final int DYNAMIC_MARKER_ID_START = 3001;
    public static final int ID_CALLOUT = 9999;
    private static final int ID_START = 3000000;
    private static final int INVALID_MARKER_ID = 0;
    private static final int MAX_ID_COUNT_OF_OVERLAY = 10000;
    private static ArrayList<Class> table = new ArrayList<>();
    private int dynamicMarkerIdCursor = 3001;
    private SparseIntArray markerIdMap = new SparseIntArray();

    public int generateMarkerId(int i, Class cls) {
        int i2;
        if (i < 0 || i >= 10000) {
            throw new IllegalArgumentException("index must be n(0 <= n < 10000)");
        }
        int indexOf = table.indexOf(cls);
        if (indexOf != -1) {
            i2 = (indexOf * 10000) + ID_START + i;
        } else {
            table.add(cls);
            i2 = ((table.size() - 1) * 10000) + ID_START + i;
        }
        return getMarkerIdByIndexId(i2);
    }

    private int getMarkerIdByIndexId(int i) {
        int i2 = this.markerIdMap.get(i, 0);
        if (i2 == 0) {
            i2 = this.dynamicMarkerIdCursor;
            this.dynamicMarkerIdCursor = i2 + 1;
            if (i2 > DYNAMIC_MARKER_ID_MAX) {
                return -999;
            }
            this.markerIdMap.put(i, i2);
        }
        return i2;
    }
}

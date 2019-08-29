package com.autonavi.ae.gmap.gloverlay;

import android.util.SparseIntArray;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import java.util.ArrayList;

public abstract class BaseOverlay<T extends GLOverlay, E> extends BaseMapOverlay<T, E> {
    private static final int DYNAMIC_MARKER_ID_MAX = 3500;
    private static final int DYNAMIC_MARKER_ID_START = 3001;
    private static final int ID_START = 3000000;
    private static final int INVALID_MARKER_ID = 0;
    private static final int MAX_ID_COUNT_OF_OVERLAY = 10000;
    private static int sDynamicMarkerIdCursor = 3001;
    private static SparseIntArray sMarkerIdMap = new SparseIntArray();
    private static final long serialVersionUID = 1;
    private static ArrayList<Class> table = new ArrayList<>();

    public abstract void onPause();

    public abstract void onResume();

    public void resumeMarker() {
    }

    /* access modifiers changed from: protected */
    public synchronized int generateMarkerId(int i) {
        int i2;
        if (i < 0 || i >= 10000) {
            throw new IllegalArgumentException("index must be n(0 <= n < 10000)");
        }
        try {
            Class<?> cls = getClass();
            int indexOf = table.indexOf(cls);
            if (indexOf != -1) {
                i2 = (indexOf * 10000) + ID_START + i;
            } else {
                table.add(cls);
                i2 = ((table.size() - 1) * 10000) + ID_START + i;
            }
        }
        return getMarkerIdByIndexId(i2);
    }

    private int getMarkerIdByIndexId(int i) {
        int i2 = sMarkerIdMap.get(i, 0);
        if (i2 == 0) {
            i2 = sDynamicMarkerIdCursor;
            sDynamicMarkerIdCursor = i2 + 1;
            if (i2 > 3500) {
                throw new IllegalArgumentException(String.format("Dynamic marker id exceeds %d!", new Object[]{Integer.valueOf(3500)}));
            }
            sMarkerIdMap.put(i, i2);
        }
        return i2;
    }

    public final int getLastFocusedIndex() {
        return this.mLastFocusedIndex;
    }

    public E getFocus() {
        return getItem(this.mLastFocusedIndex);
    }

    /* access modifiers changed from: protected */
    public void addMarkerToEngine(int i, amh amh) {
        if (amh != null) {
            this.mMapView.a(i, amh);
        }
    }

    public BaseOverlay(int i, akq akq) {
        super(i, akq.c, akq);
    }
}

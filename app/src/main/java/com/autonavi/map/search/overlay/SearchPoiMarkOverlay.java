package com.autonavi.map.search.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressFBWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED"})
public class SearchPoiMarkOverlay extends PointOverlay<bzx> {
    private static final long serialVersionUID = -2136856204041231171L;
    private int mCIndex = 0;
    private transient Bitmap mDefaultIcon = null;
    private transient ArrayList<bkf> mKeys;
    private transient HashMap<bkf, Object> mMap;
    private transient HashMap<String, Marker> mMarkerCache;

    public SearchPoiMarkOverlay(bty bty) {
        super(bty);
        setMoveToFocus(false);
        this.mMap = new HashMap<>();
        this.mKeys = new ArrayList<>();
        this.mMarkerCache = new HashMap<>();
        try {
            InputStream openRawResource = AMapAppGlobal.getApplication().getResources().openRawResource(R.raw.search_brand_default);
            if (openRawResource != null) {
                this.mDefaultIcon = BitmapFactory.decodeStream(openRawResource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void iniGLOverlay() {
        super.iniGLOverlay();
    }

    public void addMarkItem(POI poi, String str, boolean z, boolean z2) {
        if (poi != null) {
            bzx bzx = new bzx(poi);
            bzx.a = str;
            if (this.mOverlayDefaultMarker == null || z2) {
                this.mOverlayDefaultMarker = createMarker(R.drawable.marker_other, 4);
            }
            addItem(bzx);
        }
    }

    public boolean removeTilesPoiFromOverlay(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mItemList.size(); i++) {
            bzx bzx = (bzx) this.mItemList.get(i);
            if (bzx.a.equals(str)) {
                arrayList.add(bzx);
            }
        }
        if (arrayList.size() > 0) {
            removeAll(arrayList);
            int size = this.mKeys.size();
            if (size >= arrayList.size()) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    int i3 = (size - i2) - 1;
                    this.mMap.remove(this.mKeys.get(i3));
                    this.mKeys.remove(i3);
                }
                this.mCIndex -= arrayList.size();
            }
        }
        return true;
    }

    public void clearBandeData() {
        if (this.mMap != null) {
            for (bkf a : this.mMap.keySet()) {
                ImageLoader.a((Context) AMapAppGlobal.getApplication()).a((Object) a);
            }
            this.mMap.clear();
        }
        if (this.mMarkerCache != null) {
            this.mMarkerCache.clear();
        }
        if (this.mKeys != null) {
            this.mKeys.clear();
        }
        this.mCIndex = 0;
    }

    public boolean clear() {
        clearBandeData();
        return super.clear();
    }

    public void onlyClear() {
        super.clear();
    }

    public void clearFocus() {
        super.clearFocus();
    }
}

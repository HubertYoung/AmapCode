package com.autonavi.minimap.map;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.Iterator;

public class BasePoiOverlay extends PointOverlay<BasePoiOverlayItem> {
    public BasePoiOverlay(bty bty) {
        super(bty);
    }

    public void setItem(BasePoiOverlayItem basePoiOverlayItem) {
        if (this.mItemList.size() == 0) {
            addItem(basePoiOverlayItem);
            return;
        }
        clear();
        addItem(basePoiOverlayItem);
    }

    public BasePoiOverlayItem addPoi(POI poi, int i) {
        BasePoiOverlayItem createPoiOverlayItem = createPoiOverlayItem(poi, i);
        if (poi != null && ((ISearchPoiData) poi.as(ISearchPoiData.class)).getDisplayIconNameState() == 1 && ((ISearchPoiData) poi.as(ISearchPoiData.class)).getMarkerBGRes() > 0) {
            createPoiOverlayItem.mBgMarker = createMarker(((ISearchPoiData) poi.as(ISearchPoiData.class)).getMarkerBGRes(), 4);
        }
        addItem(createPoiOverlayItem);
        return createPoiOverlayItem;
    }

    public boolean isAdded(POI poi) {
        if (poi == null) {
            return true;
        }
        String id = poi.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        int size = this.mItemList.size();
        for (int i = 0; i < size; i++) {
            Object obj = this.mItemList.get(i);
            if (obj instanceof BasePoiOverlayItem) {
                String id2 = ((BasePoiOverlayItem) obj).getPOI().getId();
                if (!TextUtils.isEmpty(id2) && id.equals(id2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean remove(POI poi) {
        int size = this.mItemList.size();
        int i = 0;
        while (i < size) {
            Object obj = this.mItemList.get(i);
            if (!(obj instanceof BasePoiOverlayItem) || ((BasePoiOverlayItem) obj).getPOI() != poi) {
                i++;
            } else {
                removeItem(i);
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<POI> getOverlayPois() {
        ArrayList<POI> arrayList;
        arrayList = new ArrayList<>();
        Iterator it = this.mItemList.iterator();
        while (it.hasNext()) {
            arrayList.add(((BasePoiOverlayItem) it.next()).getPOI());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public BasePoiOverlayItem createPoiOverlayItem(POI poi, int i) {
        BasePoiOverlayItem basePoiOverlayItem = new BasePoiOverlayItem(poi, i);
        basePoiOverlayItem.setPageIndex(i);
        return basePoiOverlayItem;
    }
}

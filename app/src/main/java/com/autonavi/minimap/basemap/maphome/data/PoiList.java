package com.autonavi.minimap.basemap.maphome.data;

import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.map.BasePoiOverlay;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PoiList implements Serializable {
    private static final long serialVersionUID = 8698761739518556640L;
    public int mCurrentPage = 1;
    public int mFocusPoiIndex = 0;
    public int mMaxPage = 1;
    public ArrayList<POI> pois = new ArrayList<>();

    public void addDataToOverLay(BasePoiOverlay basePoiOverlay) {
        if (basePoiOverlay != null && this.pois != null && this.pois.size() > 0) {
            for (int i = 0; i < this.pois.size(); i++) {
                basePoiOverlay.addPoi(this.pois.get(i), 0);
                if (i == this.mFocusPoiIndex) {
                    basePoiOverlay.setFocus(i, true);
                }
            }
        }
    }

    public void addPoiArray(POI[] poiArr, boolean z) {
        if (z) {
            this.pois.clear();
            this.mFocusPoiIndex = 0;
        } else if (poiArr.length > 0) {
            this.mFocusPoiIndex = this.pois.size();
        }
        for (POI add : poiArr) {
            this.pois.add(add);
        }
    }

    public void clear() {
        this.pois.clear();
        this.mFocusPoiIndex = 0;
    }

    public void addPoi(POI poi) {
        this.pois.clear();
        this.mFocusPoiIndex = 0;
        this.pois.add(poi);
    }

    public void addPOIWithoutClear(POI poi) {
        this.pois.add(poi);
    }

    public boolean parse(JSONObject jSONObject) {
        try {
            if (!jSONObject.getString("result").equals("succ")) {
                return false;
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            this.mMaxPage = jSONObject2.getInt("maxpage");
            this.mCurrentPage = jSONObject2.getInt(Subscribe.THREAD_CURRENT);
            if (this.mCurrentPage == 1) {
                this.pois.clear();
            }
            this.mFocusPoiIndex = (this.mCurrentPage - 1) * 10;
            JSONArray jSONArray = jSONObject2.getJSONArray("infolist");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                POI createPOI = POIFactory.createPOI(jSONObject3.getString("title"), new GeoPoint(jSONObject3.getInt(DictionaryKeys.CTRLXY_X), jSONObject3.getInt(DictionaryKeys.CTRLXY_Y)));
                createPOI.setAddr(jSONObject3.getString("address"));
                createPOI.setPhone(jSONObject3.getString("tel"));
                createPOI.setId(jSONObject3.getString("pguid"));
                this.pois.add(createPOI);
            }
            return true;
        } catch (JSONException e) {
            kf.a((Throwable) e);
            return false;
        }
    }
}

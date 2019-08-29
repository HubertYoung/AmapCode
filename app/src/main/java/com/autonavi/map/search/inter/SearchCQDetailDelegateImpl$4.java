package com.autonavi.map.search.inter;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import java.util.Collection;

public class SearchCQDetailDelegateImpl$4 implements Callback<Integer> {
    final /* synthetic */ PageBundle a;
    final /* synthetic */ POI b;
    final /* synthetic */ a c;
    final /* synthetic */ byk d;

    public void error(Throwable th, boolean z) {
    }

    public SearchCQDetailDelegateImpl$4(byk byk, PageBundle pageBundle, POI poi, a aVar) {
        this.d = byk;
        this.a = pageBundle;
        this.b = poi;
        this.c = aVar;
    }

    public void callback(Integer num) {
        this.d.a.reset();
        AbstractPoiDetailView abstractPoiDetailView = this.d.a;
        PageBundle pageBundle = this.a;
        if (!(pageBundle == null || abstractPoiDetailView == null)) {
            String string = pageBundle.getString("mainTitle");
            String string2 = pageBundle.getString("viceTitle");
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (string == null && string2 == null) {
                    if (!((FavoritePOI) poi.as(FavoritePOI.class)).isSaved()) {
                        string = poi.getName();
                        string2 = poi.getAddr();
                    } else {
                        string = ((FavoritePOI) poi.as(FavoritePOI.class)).getCustomName();
                        if (TextUtils.isEmpty(string)) {
                            string = ((FavoritePOI) poi.as(FavoritePOI.class)).getName();
                        }
                        string2 = ((FavoritePOI) poi.as(FavoritePOI.class)).getAddr();
                    }
                }
                boolean isInstance = GpsPOI.class.isInstance(poi);
                abstractPoiDetailView.setMainTitle(string);
                abstractPoiDetailView.setViceTitle(string2);
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                if (iSearchPoiData.getPoiChildrenInfo() != null) {
                    Collection<? extends POI> collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                    if (collection != null && collection.size() > 0) {
                        abstractPoiDetailView.setPoi(((ChildStationPoiData[]) collection.toArray(new ChildStationPoiData[collection.size()]))[0]);
                    }
                }
                if (isInstance && TextUtils.isEmpty(poi.getName())) {
                    poi.setName(AMapAppGlobal.getApplication().getString(R.string.my_location));
                }
                abstractPoiDetailView.setPoi(poi);
            }
        }
        if (GeocodePOI.class.isInstance(this.b)) {
            this.d.a(this.b.getPoint());
        }
    }
}

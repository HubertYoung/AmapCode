package com.autonavi.miniapp.plugin.adapter;

import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.framework.service.GeocodeService;
import com.alipay.mobile.framework.service.OnPoiSearchListener;
import com.alipay.mobile.framework.service.OnReGeocodeListener;
import com.alipay.mobile.map.exception.GeocodeException;
import com.alipay.mobile.map.model.LatLonPoint;
import com.alipay.mobile.map.model.SearchPoiRequest;
import com.alipay.mobile.map.model.geocode.GeocodeResult;
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.alipay.mobile.map.model.geocode.ReGeocodeResult;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.map.DPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmapGeocodeService extends GeocodeService {
    private GeoPoint geoPoint;
    private adx mSearchCancelable;
    private ISearchService server;

    public void cityKeywordSearchPoi(Context context, String str, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener) {
    }

    public List<GeocodeResult> geocode(String str) throws GeocodeException {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public List<ReGeocodeResult> reGeocode(LatLonPoint latLonPoint, float f) throws GeocodeException {
        return null;
    }

    public List<ReGeocodeResult> reGeocode(LatLonPoint latLonPoint, float f, String str) throws GeocodeException {
        return null;
    }

    public ReGeocodeResult reverse(LatLonPoint latLonPoint, float f, String str) throws GeocodeException {
        return null;
    }

    public ReGeocodeResult reverse(LatLonPoint latLonPoint, float f, String str, int i) throws GeocodeException {
        return null;
    }

    public void reverse(LatLonPoint latLonPoint, float f, String str, int i, OnReGeocodeListener onReGeocodeListener) throws GeocodeException {
    }

    public void reverse(LatLonPoint latLonPoint, float f, String str, OnReGeocodeListener onReGeocodeListener) throws GeocodeException {
    }

    public void searchpoi(Context context, OnPoiSearchListener onPoiSearchListener, SearchPoiRequest searchPoiRequest) {
    }

    public void searchpoi(Context context, LatLonPoint latLonPoint, String str, int i, int i2, OnPoiSearchListener onPoiSearchListener) {
    }

    public void searchpoi(Context context, LatLonPoint latLonPoint, String str, int i, int i2, OnPoiSearchListener onPoiSearchListener, int i3) {
    }

    public void searchpoi(Context context, String str, LatLonPoint latLonPoint, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener) {
    }

    public void searchpoi(Context context, String str, LatLonPoint latLonPoint, String str2, int i, int i2, OnPoiSearchListener onPoiSearchListener, int i3) {
    }

    public void setGeoPoint(GeoPoint geoPoint2) {
        this.geoPoint = geoPoint2;
    }

    public void searchpoi(Context context, String str, final OnPoiSearchListener onPoiSearchListener, SearchPoiRequest searchPoiRequest) {
        final ael ael = new ael(searchPoiRequest.getKeywords(), this.geoPoint);
        if (this.server != null) {
            InfoliteParam infoliteParam = new InfoliteParam();
            infoliteParam.query_type = "RQBXY";
            infoliteParam.keywords = searchPoiRequest.getKeywords();
            infoliteParam.user_loc = AppManager.getInstance().getUserLocInfo();
            DPoint a = cfg.a((long) this.geoPoint.x, (long) this.geoPoint.y);
            infoliteParam.longitude = a.x;
            infoliteParam.latitude = a.y;
            infoliteParam.pagenum = searchPoiRequest.getPagenum();
            infoliteParam.pagesize = searchPoiRequest.getPagesize();
            this.mSearchCancelable = this.server.a(infoliteParam, 2, (aeb<InfoliteResult>) new aeb<InfoliteResult>() {
                public void error(int i) {
                }

                public void callback(InfoliteResult infoliteResult) {
                    int i;
                    ArrayList<POI> arrayList;
                    if (infoliteResult == null || infoliteResult.searchInfo == null) {
                        arrayList = null;
                        i = 0;
                    } else {
                        arrayList = infoliteResult.searchInfo.l;
                        i = infoliteResult.searchInfo.p;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    if (arrayList != null) {
                        Iterator<POI> it = arrayList.iterator();
                        while (it.hasNext()) {
                            POI next = it.next();
                            PoiItem poiItem = new PoiItem();
                            poiItem.setTitle(next.getName());
                            poiItem.setSnippet(next.getAddr());
                            poiItem.setDistance(next.getDistance());
                            poiItem.setLatLonPoint(new LatLonPoint(next.getPoint().getLatitude(), next.getPoint().getLongitude()));
                            arrayList2.add(poiItem);
                        }
                    }
                    if (onPoiSearchListener != null) {
                        onPoiSearchListener.onPoiSearched(arrayList2, ael.d, (i / ael.d) + 1);
                    }
                }
            });
        }
    }

    public void destroy() {
        if (this.mSearchCancelable != null) {
            this.mSearchCancelable.a();
        }
    }

    public void create() {
        this.server = (ISearchService) a.a.a(ISearchService.class);
    }
}

package com.amap.bundle.maplayer;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.ae.search.interfaces.OnSearchResultListener;
import com.autonavi.ae.search.model.GPoiResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.MapPointPOI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.List;

public class OverlayManagerImpl$6 implements Callback<List<POI>> {
    final /* synthetic */ int a;
    final /* synthetic */ boolean b;
    final /* synthetic */ POI c;
    final /* synthetic */ xn d;

    public OverlayManagerImpl$6(xn xnVar, int i, boolean z, POI poi) {
        this.d = xnVar;
        this.a = i;
        this.b = z;
        this.c = poi;
    }

    public void callback(List<POI> list) {
        if (list == null || list.size() == 0) {
            if (this.d.m != null && this.d.m.isTokenAvailable(this.a)) {
                this.d.m.onMapPointRequestReturnNull();
            }
        } else if (this.d.m != null && this.d.m.isTokenAvailable(this.a)) {
            POI poi = list.get(0);
            if (poi != null && !TextUtils.isEmpty(poi.getName())) {
                if (this.b) {
                    PoiButtonTemplate poiButtonTemplate = new PoiButtonTemplate();
                    poiButtonTemplate.setValue("");
                    poiButtonTemplate.setAction("share");
                    poiButtonTemplate.setId(1012);
                    poiButtonTemplate.setType(PoiLayoutTemplate.BUTTON);
                    ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                    if (iSearchPoiData.getTemplateDataMap() == null) {
                        xn.b(poi, poi.getName());
                        if (!TextUtils.isEmpty(this.c.getAddr())) {
                            if (TextUtils.isEmpty(poi.getAddr())) {
                                poi.setAddr(this.c.getAddr());
                            }
                            PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
                            poiTextTemplate.setValue(this.c.getAddr());
                            poiTextTemplate.setId(1010);
                            poiTextTemplate.setType("text");
                            poiTextTemplate.setName("address");
                            ((ISearchPoiData) poi.as(ISearchPoiData.class)).getTemplateDataMap().put(Integer.valueOf(1010), poiTextTemplate);
                            ((ISearchPoiData) poi.as(ISearchPoiData.class)).getTemplateData().add(poiTextTemplate);
                        }
                    }
                    if (iSearchPoiData.getTemplateDataMap() != null) {
                        iSearchPoiData.getTemplateDataMap().put(Integer.valueOf(1012), poiButtonTemplate);
                    }
                    if (iSearchPoiData.getTemplateData() != null) {
                        iSearchPoiData.getTemplateData().add(poiButtonTemplate);
                    }
                    PoiTextTemplate poiTextTemplate2 = new PoiTextTemplate();
                    poiTextTemplate2.setValue(xn.a((FavoritePOI) this.c));
                    poiTextTemplate2.setId(2001);
                    poiTextTemplate2.setType("text");
                    if (iSearchPoiData.getTemplateDataMap() != null) {
                        iSearchPoiData.getTemplateDataMap().put(Integer.valueOf(poiTextTemplate2.getId()), poiTextTemplate2);
                    }
                    if (iSearchPoiData.getTemplateData() != null) {
                        iSearchPoiData.getTemplateData().add(poiTextTemplate2);
                    }
                    com com2 = (com) ank.a(com.class);
                    if (com2 != null) {
                        cop b2 = com2.b(com2.a());
                        if (b2 != null) {
                            b2.a(this.c, poi);
                        }
                    }
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("POI", poi.as(MapPointPOI.class));
                this.d.m.refreshPoiFooter(pageBundle, this.a);
                if (this.d.r != null) {
                    this.d.r;
                }
                this.d.m.drawOverlay(poi);
            }
        }
        this.d.o.clear();
    }

    public void error(Throwable th, boolean z) {
        this.d.o.clear();
        if (this.c != null) {
            adz adz = (adz) a.a.a(adz.class);
            if (adz != null) {
                final ady b2 = adz.b();
                if (b2 != null) {
                    b2.a(this.c.getPoint(), this.c.getId(), (OnSearchResultListener) new OnSearchResultListener() {
                        public final void onGetSearchResult(int i, final GPoiResult gPoiResult) {
                            aho.a(new Runnable() {
                                public final void run() {
                                    if (!(gPoiResult == null || gPoiResult.getPoiList() == null || gPoiResult.getPoiList().size() <= 0)) {
                                        POI a2 = b2.a(gPoiResult.getPoiList().get(0));
                                        if (a2 != null && !TextUtils.isEmpty(a2.getName())) {
                                            PageBundle pageBundle = new PageBundle();
                                            pageBundle.putObject("POI", a2.as(MapPointPOI.class));
                                            if (OverlayManagerImpl$6.this.d.m != null) {
                                                OverlayManagerImpl$6.this.d.m.refreshPoiFooter(pageBundle, OverlayManagerImpl$6.this.a);
                                            }
                                            if (OverlayManagerImpl$6.this.d.r != null) {
                                                OverlayManagerImpl$6.this.d.r;
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }
}

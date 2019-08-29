package com.amap.bundle.searchservice.service.search;

import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.amap.bundle.searchservice.service.offline.impl.CodeMsgOffline;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.ae.search.model.GPoiBean;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.searchservice.utils.SearchUtils;
import java.util.ArrayList;
import java.util.List;

public class SearchSuggServiceImpl$3 extends AbsSearchCallBack {
    final /* synthetic */ adx a;
    final /* synthetic */ aeb b;
    final /* synthetic */ aek c;

    public SearchSuggServiceImpl$3(aek aek, adx adx, aeb aeb) {
        this.c = aek;
        this.a = adx;
        this.b = aeb;
    }

    public void callback(InfoliteResult infoliteResult) {
        super.callback(infoliteResult);
        List<GPoiBase> list = infoliteResult.searchInfo.m;
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            int i = 0;
            for (GPoiBase next : list) {
                TipItem convertGPoiBase2TipItem = SearchUtils.convertGPoiBase2TipItem(next);
                if (next instanceof GPoiBean) {
                    convertGPoiBase2TipItem.poi = aee.a(next);
                }
                if (convertGPoiBase2TipItem != null) {
                    arrayList.add(convertGPoiBase2TipItem);
                    i++;
                    if (i >= 10) {
                        break;
                    }
                }
            }
        }
        final aux aux = new aux();
        aux.b = arrayList;
        if (!this.a.b()) {
            aho.a(new Runnable() {
                final aeb a = SearchSuggServiceImpl$3.this.b;

                public final void run() {
                    this.a.callback(aux);
                }
            });
        }
    }

    public void error(final int i, String str) {
        super.error(i, str);
        if (i == 0) {
            i = CodeMsgOffline.CODE_NATIVE_POI_NORESULT.getnCode();
        }
        if (!this.a.b()) {
            aho.a(new Runnable() {
                public final void run() {
                    SearchSuggServiceImpl$3.this.b.error(i);
                }
            });
        }
    }
}

package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.map.DPoint;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/* renamed from: ccg reason: default package */
/* compiled from: SearchRouteHistoryUtil */
public final class ccg {
    private static ccg b;
    public String a;
    private Object c;
    private int d;

    private ccg() {
    }

    public static synchronized ccg a() {
        ccg ccg;
        synchronized (ccg.class) {
            try {
                if (b == null) {
                    b = new ccg();
                }
                ccg = b;
            }
        }
        return ccg;
    }

    public final void a(InfoliteResult infoliteResult, POI poi, boolean z) {
        if (infoliteResult != null && infoliteResult.responseHeader.f) {
            if (!z) {
                if (a(infoliteResult, poi)) {
                    this.a = infoliteResult.mWrapper.keywords;
                } else {
                    return;
                }
            } else if (b(poi)) {
                this.a = this.c.toString();
            } else {
                return;
            }
            a(poi);
        }
    }

    public final void a(final POI poi) {
        ahm.a(new Runnable() {
            public final void run() {
                TipItem tipItem = new TipItem();
                tipItem.poiid = poi.getId();
                tipItem.name = poi.getName();
                tipItem.addr = poi.getAddr();
                tipItem.super_address = ((SearchPoi) poi.as(SearchPoi.class)).getSuperAddress();
                if (!TextUtils.isEmpty(tipItem.super_address) && tipItem.super_address.contains("757575")) {
                    tipItem.super_address = tipItem.super_address.replaceAll("757575", "42a5ff");
                }
                tipItem.x = poi.getPoint().getLongitude();
                tipItem.y = poi.getPoint().getLatitude();
                tipItem.time = new Date();
                tipItem.endPoiExtension = poi.getEndPoiExtension();
                tipItem.transparent = poi.getTransparent();
                if (poi.getEntranceList() != null && !poi.getEntranceList().isEmpty()) {
                    Iterator<GeoPoint> it = poi.getEntranceList().iterator();
                    while (it.hasNext()) {
                        GeoPoint next = it.next();
                        DPoint a2 = cfg.a((long) next.x, (long) next.y);
                        if (a2.x > 0.0d) {
                            tipItem.x_entr = a2.x;
                        }
                        if (a2.y > 0.0d) {
                            tipItem.y_entr = a2.y;
                        }
                    }
                }
                tipItem.newType = poi.getType();
                tipItem.userInput = ccg.this.a;
                tipItem.historyType = 0;
                tipItem.adcode = poi.getAdCode();
                tipItem.parent = ((SearchPoi) poi.as(SearchPoi.class)).getParent();
                tipItem.childType = ((SearchPoi) poi.as(SearchPoi.class)).getChildType();
                tipItem.towardsAngle = ((SearchPoi) poi.as(SearchPoi.class)).getTowardsAngle();
                tipItem.f_nona = ((SearchPoi) poi.as(SearchPoi.class)).getFnona();
                if (((SearchPoi) poi.as(SearchPoi.class)).getIndoorPoiInfo() != null) {
                    tipItem.sndtFloorName = ((SearchPoi) poi.as(SearchPoi.class)).getIndoorPoiInfo().sndtFloorName;
                }
                SearchHistoryHelper.getInstance().saveTipItemFromRoute(tipItem);
            }
        });
    }

    private static boolean a(InfoliteResult infoliteResult, POI poi) {
        if (poi == null || infoliteResult == null || TextUtils.isEmpty(poi.getId()) || infoliteResult.searchInfo.a.N == 2 || infoliteResult.searchInfo.w != 0) {
            return false;
        }
        return true;
    }

    private boolean b(POI poi) {
        this.c = null;
        if (poi == null) {
            return false;
        }
        HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
        if (poiExtra.containsKey("detail_fragment_key_word") && poiExtra.containsKey("detail_data_from_page")) {
            this.c = poiExtra.get("detail_fragment_key_word");
            this.d = ((Integer) poiExtra.get("detail_data_from_page")).intValue();
            if (this.c != null && this.d == 0) {
                return true;
            }
        }
        return false;
    }
}

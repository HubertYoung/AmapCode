package defpackage;

import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* renamed from: bxv reason: default package */
/* compiled from: SearchMapHelper */
public final class bxv {
    public static void a(AbstractBaseMapPage abstractBaseMapPage) {
        if (abstractBaseMapPage != null) {
            cde suspendManager = abstractBaseMapPage.getSuspendManager();
            if (suspendManager != null) {
                cdz d = suspendManager.d();
                if (d != null) {
                    d.f();
                }
            }
        }
    }

    public static List<POI> a(SearchPoi searchPoi) {
        if (searchPoi == null) {
            return null;
        }
        ChildrenPoiData poiChildrenInfo = searchPoi.getPoiChildrenInfo();
        if (poiChildrenInfo != null && poiChildrenInfo.childType == 2) {
            Collection<? extends POI> collection = poiChildrenInfo.poiList;
            if (collection != null) {
                ArrayList arrayList = new ArrayList();
                int distance = searchPoi.getDistance();
                Map templateDataMap = searchPoi.getTemplateDataMap();
                boolean z = false;
                if (templateDataMap != null) {
                    PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) templateDataMap.get(Integer.valueOf(1014));
                    if (poiLayoutTemplate != null && poiLayoutTemplate.isShown() == 0) {
                        z = true;
                    }
                }
                for (POI poi : collection) {
                    if (z) {
                        poi.setDistance(distance);
                    }
                    arrayList.add(poi);
                }
                return arrayList;
            }
        }
        return null;
    }
}

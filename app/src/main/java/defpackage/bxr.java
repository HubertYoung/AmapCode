package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: bxr reason: default package */
/* compiled from: SearchCQDetailRequestHelper */
public final class bxr {
    private byj a;
    private List<AosRequest> b = new ArrayList();

    public bxr(byj byj) {
        this.a = byj;
    }

    public static void a(POI poi, String str) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
        poiTextTemplate.setValue(str);
        poiTextTemplate.setId(2001);
        poiTextTemplate.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate.getId()), poiTextTemplate);
        arrayList.add(poiTextTemplate);
        PoiTextTemplate poiTextTemplate2 = new PoiTextTemplate();
        if (NetworkReachability.b()) {
            poiTextTemplate2.setValue("                      ");
        } else {
            poiTextTemplate2.setValue("");
        }
        poiTextTemplate2.setId(1010);
        poiTextTemplate2.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate2.getId()), poiTextTemplate2);
        arrayList.add(poiTextTemplate2);
        PoiLinkTemplate poiLinkTemplate = new PoiLinkTemplate();
        poiLinkTemplate.setAction("detail");
        poiLinkTemplate.setId(1002);
        poiLinkTemplate.setType("link");
        hashMap.put(Integer.valueOf(poiLinkTemplate.getId()), poiLinkTemplate);
        arrayList.add(poiLinkTemplate);
        PoiButtonTemplate poiButtonTemplate = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate2 = poiButtonTemplate;
        poiButtonTemplate2.setValue("");
        poiButtonTemplate2.setAction("sebxy");
        poiButtonTemplate.setId(1003);
        poiButtonTemplate.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(1003), poiButtonTemplate);
        arrayList.add(poiButtonTemplate);
        PoiButtonTemplate poiButtonTemplate3 = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate4 = poiButtonTemplate3;
        poiButtonTemplate4.setValue("");
        poiButtonTemplate4.setAction(AutoConstants.AUTO_FILE_ROUTE);
        poiButtonTemplate3.setId(2003);
        poiButtonTemplate3.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(2003), poiButtonTemplate3);
        arrayList.add(poiButtonTemplate3);
        PoiButtonTemplate poiButtonTemplate5 = new PoiButtonTemplate();
        PoiButtonTemplate poiButtonTemplate6 = poiButtonTemplate5;
        poiButtonTemplate6.setValue("");
        poiButtonTemplate6.setAction("nav");
        poiButtonTemplate5.setId(1005);
        poiButtonTemplate5.setType(PoiLayoutTemplate.BUTTON);
        hashMap.put(Integer.valueOf(1005), poiButtonTemplate5);
        arrayList.add(poiButtonTemplate5);
        if (poi.as(ISearchPoiData.class) != null) {
            ((ISearchPoiData) poi.as(ISearchPoiData.class)).setTemplateDataMap(hashMap);
            ((ISearchPoiData) poi.as(ISearchPoiData.class)).setTemplateData(arrayList);
        }
    }
}

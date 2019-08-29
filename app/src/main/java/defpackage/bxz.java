package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: bxz reason: default package */
/* compiled from: SearchResultHelper */
public final class bxz {
    public static POI a(Context context, POI poi) {
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
        PoiTextTemplate poiTextTemplate2 = new PoiTextTemplate();
        poiTextTemplate.setValue(searchPoi.getName());
        poiTextTemplate.setId(2001);
        poiTextTemplate.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate.getId()), poiTextTemplate);
        arrayList.add(poiTextTemplate);
        PoiTextTemplate poiTextTemplate3 = new PoiTextTemplate();
        poiTextTemplate3.setValue(searchPoi.getAddr() == null ? "" : searchPoi.getAddr());
        poiTextTemplate3.setId(1010);
        poiTextTemplate3.setType("text");
        hashMap.put(Integer.valueOf(poiTextTemplate3.getId()), poiTextTemplate3);
        arrayList.add(poiTextTemplate3);
        poiTextTemplate2.setValue(searchPoi.getAddr() == null ? "" : searchPoi.getAddr());
        poiTextTemplate2.setId(2009);
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
        if (!TextUtils.isEmpty(searchPoi.getPhone())) {
            PoiButtonTemplate poiButtonTemplate7 = new PoiButtonTemplate();
            PoiButtonTemplate poiButtonTemplate8 = poiButtonTemplate7;
            poiButtonTemplate8.setValue(searchPoi.getPhone());
            poiButtonTemplate8.setAction("tel");
            poiButtonTemplate7.setId(1012);
            poiButtonTemplate7.setType(PoiLayoutTemplate.BUTTON);
            hashMap.put(Integer.valueOf(1012), poiButtonTemplate7);
            arrayList.add(poiButtonTemplate7);
            PoiButtonTemplate poiButtonTemplate9 = new PoiButtonTemplate();
            PoiButtonTemplate poiButtonTemplate10 = poiButtonTemplate9;
            poiButtonTemplate10.setValue(searchPoi.getPhone());
            poiButtonTemplate10.setAction("tel");
            poiButtonTemplate9.setId(2004);
            poiButtonTemplate9.setType(PoiLayoutTemplate.BUTTON);
            hashMap.put(Integer.valueOf(poiButtonTemplate9.getId()), poiButtonTemplate9);
            arrayList.add(poiButtonTemplate9);
        }
        if (searchPoi.getPoint().getAdCode() == LocationInstrument.getInstance().getLatestPosition().getAdCode()) {
            String formatDistance = SearchUtils.formatDistance(context, searchPoi, false);
            if (!TextUtils.isEmpty(formatDistance)) {
                PoiTextTemplate poiTextTemplate4 = new PoiTextTemplate();
                poiTextTemplate4.setValue(formatDistance);
                poiTextTemplate4.setId(1014);
                poiTextTemplate4.setType("text");
                hashMap.put(Integer.valueOf(1014), poiTextTemplate4);
                arrayList.add(poiTextTemplate4);
                PoiTextTemplate poiTextTemplate5 = new PoiTextTemplate();
                poiTextTemplate5.setValue(formatDistance);
                poiTextTemplate5.setId(2002);
                poiTextTemplate5.setType("text");
                hashMap.put(Integer.valueOf(poiTextTemplate5.getId()), poiTextTemplate5);
                arrayList.add(poiTextTemplate5);
            }
        }
        searchPoi.setTemplateDataMap(hashMap);
        searchPoi.setTemplateData(arrayList);
        return poi;
    }

    public static boolean a(List<POI> list) {
        if (list == null || list.size() <= 0) {
            return false;
        }
        return a(list.get(0));
    }

    public static boolean a(POI poi) {
        if (poi != null && poi.getPoiExtra().containsKey("SrcType")) {
            String str = (String) poi.getPoiExtra().get("SrcType");
            if (!TextUtils.isEmpty(str) && "nativepoi".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static PageBundle a(PageBundle pageBundle, SuperId superId, InfoliteResult infoliteResult, POI poi, String str, int i) {
        if (!(poi == null || infoliteResult == null || infoliteResult.searchInfo.v == 1)) {
            HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
            poiExtra.put("detail_fragment_key_word", infoliteResult.mWrapper.keywords);
            poiExtra.put("detail_data_from_page", Integer.valueOf(i));
        }
        pageBundle.putObject("POI", poi);
        pageBundle.putString("fromSource", str);
        if (infoliteResult != null) {
            pageBundle.putObject("poi_search_result", infoliteResult);
        }
        if (superId != null) {
            pageBundle.putSerializable("SUPER_ID", superId);
        }
        return pageBundle;
    }
}

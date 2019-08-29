package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.searchservice.utils.PoiArrayTemplate;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import org.json.JSONObject;

/* renamed from: elt reason: default package */
/* compiled from: PoiArrayTemplateParser */
final class elt extends bcr {
    private SearchPoi a;
    private auk b;

    public elt(auk auk, SearchPoi searchPoi) {
        this.a = searchPoi;
        this.b = auk;
    }

    public final PoiLayoutTemplate a(JSONObject jSONObject) {
        PoiArrayTemplate poiArrayTemplate = new PoiArrayTemplate();
        String str = "";
        if (jSONObject.has("type")) {
            str = jSONObject.optString("type");
        }
        int optInt = jSONObject.optInt("id");
        if (optInt == 2015 || optInt == 2013 || optInt == 6001 || optInt == 1103) {
            poiArrayTemplate.setPoiids(jSONObject.optString("poiids"));
            poiArrayTemplate.setPxs(jSONObject.optString("pxs"));
            poiArrayTemplate.setPys(jSONObject.optString("pys"));
            poiArrayTemplate.setValue(jSONObject.optString("values"));
            poiArrayTemplate.setShortName(jSONObject.optString("shortname"));
            poiArrayTemplate.setChildType(jSONObject.optString("childtype"));
            poiArrayTemplate.setAddress(jSONObject.optString("address"));
            poiArrayTemplate.setPoiName(jSONObject.optString("poiname"));
            poiArrayTemplate.setDeepinfo(jSONObject.optString("deepinfo"));
            poiArrayTemplate.setDistence(jSONObject.optString("distance"));
            poiArrayTemplate.setAnchor(jSONObject.optString("anchor"));
            poiArrayTemplate.setRenderStyleMain(jSONObject.optString("render_style_main"));
            poiArrayTemplate.setRenderStyleSub(jSONObject.optString("render_style_sub"));
            poiArrayTemplate.setMiniZoom(jSONObject.optString("minizoom"));
            poiArrayTemplate.setRenderRank(jSONObject.optString("render_rank"));
            poiArrayTemplate.setXEntr(jSONObject.optString("pentrxs"));
            poiArrayTemplate.setYEntr(jSONObject.optString("pentrys"));
            poiArrayTemplate.setShowChild(jSONObject.optInt("show_child", 1));
            poiArrayTemplate.setTags(jSONObject.optString("tags"));
            poiArrayTemplate.setTagColors(jSONObject.optString("tag_colors"));
            if (optInt == 2015) {
                poiArrayTemplate.setId(optInt);
                poiArrayTemplate.setName(jSONObject.optString("name"));
                poiArrayTemplate.setType(str);
                poiArrayTemplate.setLabel(jSONObject.optString("label"));
                if (this.a.getPoiChildrenInfo() == null) {
                    this.a.setPoiChildrenInfo(new ChildrenPoiData());
                }
                this.a.getPoiChildrenInfo().poiList = poiArrayTemplate.getChildPois(this.a);
                this.a.getPoiChildrenInfo().childType = 2;
            } else if (optInt == 1103) {
                poiArrayTemplate.setId(optInt);
                poiArrayTemplate.setName(jSONObject.optString("name"));
                poiArrayTemplate.setType(str);
                poiArrayTemplate.setLabel(jSONObject.optString("label"));
                auw auw = new auw();
                auw.b = elw.a(jSONObject.optString("max_rows"));
                auw.a = elw.a(jSONObject.optString("columns"));
                auw.c = elw.a(jSONObject.optString("default_rows"));
                poiArrayTemplate.setChildConfig(auw);
                if (this.a.getPoiChildrenInfo() == null) {
                    this.a.setPoiChildrenInfo(new ChildrenPoiData());
                }
                this.a.getPoiChildrenInfo().poiList = poiArrayTemplate.getChildPois(this.a);
                this.a.getPoiChildrenInfo().childType = 2;
            } else if (optInt == 2013) {
                poiArrayTemplate.setId(optInt);
                poiArrayTemplate.setName(jSONObject.optString("name"));
                poiArrayTemplate.setType(str);
                poiArrayTemplate.setBusAlias(jSONObject.optString("bus_alias"));
                if (this.a.getPoiChildrenInfo() == null) {
                    this.a.setPoiChildrenInfo(new ChildrenPoiData());
                }
                this.a.getPoiChildrenInfo().stationList = poiArrayTemplate.getChildStation();
                this.a.getPoiChildrenInfo().childType = 1;
            }
        } else if (optInt == 3001) {
            String optString = jSONObject.optString("src");
            poiArrayTemplate.setSrc(optString);
            poiArrayTemplate.setId(optInt);
            poiArrayTemplate.setName(jSONObject.optString("name"));
            if (!poiArrayTemplate.isOnlineBg(optString) || this.b == null || this.b.m == null) {
                this.a.setIconSrcName(poiArrayTemplate.getMarkBGId());
            } else {
                this.a.setMarkerBGOnline(this.b.m.get(optString));
            }
        } else if (optInt == 2027) {
            poiArrayTemplate.setShoppingMallDatas(jSONObject.optString("value"));
            poiArrayTemplate.setShoppingMallUrl(jSONObject.optString("url"));
            poiArrayTemplate.setId(optInt);
            poiArrayTemplate.setName(jSONObject.optString("name"));
        } else if (optInt == 2029) {
            poiArrayTemplate.setId(optInt);
            String optString2 = jSONObject.optString("gas_type");
            String optString3 = jSONObject.optString("gas_price");
            String optString4 = jSONObject.optString("gas_unit");
            if (TextUtils.isEmpty(optString2) || TextUtils.isEmpty(optString3) || TextUtils.isEmpty(optString4)) {
                return poiArrayTemplate;
            }
            String[] split = optString2.split(PoiLayoutTemplate.SPLITER_REG);
            int length = split.length;
            String[] split2 = optString3.split(PoiLayoutTemplate.SPLITER_REG);
            if (length > split2.length) {
                length = split2.length;
            }
            String[] split3 = optString4.split(PoiLayoutTemplate.SPLITER_REG);
            if (length > split3.length) {
                length = split3.length;
            }
            if (length == 0) {
                return poiArrayTemplate;
            }
            if (length > 2) {
                length = 2;
            }
            String[] strArr = new String[length];
            String[] strArr2 = new String[length];
            String[] strArr3 = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = split[i].trim();
                strArr2[i] = split2[i].trim();
                strArr3[i] = split3[i].trim();
            }
            poiArrayTemplate.setGas_types(strArr);
            poiArrayTemplate.setGas_prices(strArr2);
            poiArrayTemplate.setGas_utils(strArr3);
        } else if (optInt == 2038) {
            poiArrayTemplate.setValue(jSONObject.optString("value"));
        }
        return poiArrayTemplate;
    }
}

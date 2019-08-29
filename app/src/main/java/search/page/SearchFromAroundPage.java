package search.page;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.analytics.core.config.UTTPKItem;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.autonavi.annotation.PageAction;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.OfflineParam;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.searcharound.module.AjxModuleSearchAroundInput;
import com.autonavi.bundle.searcharound.module.AjxModuleSearchAroundInput.a;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleInstance;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.search.action.arround")
public class SearchFromAroundPage extends Ajx3Page implements bgm, a, launchModeSingleInstance {
    public int a = LogConstant.SEARCH_FROM_ARROUND;
    public InfoliteParam b;

    public void finishSelf() {
    }

    public long getScenesID() {
        return 4398046511104L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public fhv createPresenter() {
        return new fhv(this);
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", "path://amap_bundle_search/src/around_search_input/pages/BizAroundSearchInputPage.page.js");
        super.onCreate(context);
    }

    public void resume() {
        this.ajxPageStateInvoker.setResumeData(b());
        super.resume();
    }

    private String b() {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            return "";
        }
        String string = arguments.getString("feed");
        String str = "620000".equals(arguments.getString("from_page")) ? UTTPKItem.TYPE_NEARBY : "around";
        boolean z = arguments.getBoolean("key_can_search_with_hint", false);
        String string2 = arguments.getString("hint_content", "");
        POI poi = arguments.containsKey("center_poi") ? (POI) arguments.get("center_poi") : arguments.containsKey("POI") ? (POI) arguments.getObject("POI") : null;
        if (poi == null || poi.getPoint() == null || poi.getPoint().x <= 0 || poi.getPoint().y <= 0) {
            poi = POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition());
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("placeholder", string2);
            jSONObject2.put("placeholderCanBeSearch", z);
            jSONObject2.put("centerName", poi.getName());
            jSONObject.put("searchBar", jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            GeoPointHD geoPointHD = new GeoPointHD(getMapView().n());
            jSONObject3.put("city", li.a().b(geoPointHD.x, geoPointHD.y).j);
            jSONObject3.put("longitude", poi.getPoint().getLongitude());
            jSONObject3.put("latitude", poi.getPoint().getLatitude());
            jSONObject.put("suggParam", jSONObject3);
            jSONObject.put("fromPage", str);
            jSONObject.put("feedTransparent", string);
            jSONObject.put(TrafficUtil.KEYWORD, ((fhv) this.mPresenter).g);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((fhv) this.mPresenter).g = null;
        return jSONObject.toString();
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        AjxModuleSearchAroundInput ajxModuleSearchAroundInput = (AjxModuleSearchAroundInput) this.mAjxView.getJsModule(AjxModuleSearchAroundInput.MODULE_NAME);
        if (ajxModuleSearchAroundInput != null) {
            ajxModuleSearchAroundInput.setModulePage(this);
        }
    }

    public final void a(TipItem tipItem, boolean z, String str) {
        if (!TextUtils.isEmpty(tipItem.searchQuery)) {
            tipItem.name = tipItem.searchQuery;
        }
        if (tipItem.type == 0) {
            SuperId.getInstance().setBit2("02");
            if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                SuperId.getInstance().setBit3("06");
            } else {
                SuperId.getInstance().setBit3("07");
            }
        } else {
            tipItem.userInput = str;
            SuperId.getInstance().setBit2("01");
            if (TextUtils.isEmpty(tipItem.poiid) && z) {
                SuperId.getInstance().setBit3("15");
            } else if ((tipItem.tipItemList == null || tipItem.tipItemList.size() <= 0) && !tipItem.isSugChildClick) {
                if (SearchHistoryHelper.isUserfulPoi(tipItem)) {
                    SuperId.getInstance().setBit3("01");
                }
            } else if (TextUtils.isEmpty(tipItem.poiid)) {
                SuperId.getInstance().setBit3("02");
            } else {
                SuperId.getInstance().setBit3("03");
            }
        }
        try {
            if (!TextUtils.isEmpty(tipItem.poiid)) {
                if (SearchHistoryHelper.idType(tipItem) == 0) {
                    fhv fhv = (fhv) this.mPresenter;
                    bck bck = (bck) a.a.a(bck.class);
                    if (bck != null) {
                        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), tipItem.name, tipItem.poiid);
                        a2.search_sceneid = !TextUtils.isEmpty(fhv.c) ? SuperId.getInstance().getScenceId() : null;
                        a2.geoobj = bbw.a(fhv.b);
                        a2.sugpoiname = tipItem.name;
                        a2.sugadcode = tipItem.adcode;
                        a2.log_center_id = fhv.e.getId();
                        a2.offline_param = new OfflineParam();
                        a2.offline_param.longitude = tipItem.x;
                        a2.offline_param.latitude = tipItem.y;
                        a2.offline_param.keyword = tipItem.name;
                        if (fhv.e.getPoint() != null) {
                            a2.latitude = fhv.e.getPoint().getLatitude();
                            a2.longitude = fhv.e.getPoint().getLongitude();
                        }
                        bcl bcl = new bcl();
                        bcl.b = fhv.a;
                        bcl.c = a2.keywords;
                        bcl.h = fhv.e;
                        bcl.a = SearchFor.DEFAULT;
                        bcl.g = fhv.b;
                        bcl.d = tipItem;
                        bcl.k = fhv.h;
                        bcl.n = fhv.i;
                        fhv.f = bck.a(a2, bcl);
                    }
                    return;
                }
            }
            ((fhv) this.mPresenter).a(tipItem.name, tipItem, "");
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    public final void a(String str, String str2) {
        ((fhv) this.mPresenter).a(str, null, str2);
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public JSONObject getScenesData() {
        fhv fhv = (fhv) this.mPresenter;
        String str = null;
        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), null, fhv.e.getPoint(), null);
        if (!TextUtils.isEmpty(fhv.c)) {
            str = SuperId.getInstance().getScenceId();
        }
        a2.superid = str;
        a2.search_sceneid = str;
        a2.log_center_id = fhv.e.getId();
        this.b = a2;
        this.b.citysuggestion = false;
        this.b.need_utd = false;
        return bbv.a(this.b);
    }
}

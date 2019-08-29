package com.autonavi.minimap.bundle.frequentlocation.ajx;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.bundle.frequentlocation.entity.FrequentLocationData;
import com.autonavi.minimap.bundle.frequentlocation.util.FrequentLocationInfoEx;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("frequentLocation")
public class ModuleFrequentLocation extends AbstractModule {
    public static final String MODULE_NAME = "frequentLocation";
    cyj mFrequentLocationsUtil = new cyj();

    public ModuleFrequentLocation(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getHome")
    @Deprecated
    public String getHome() {
        return getHomeOrCompany(true);
    }

    @AjxMethod("toSetHome")
    public void toSetHome(String str, JsFunctionCallback jsFunctionCallback) {
        toSetHomeOrCompany(true, str, jsFunctionCallback);
    }

    @AjxMethod("deleteHome")
    public void deleteHome(JsFunctionCallback jsFunctionCallback) {
        deleteHomeOrCompany(true);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback("1");
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCompany")
    @Deprecated
    public String getCompany() {
        return getHomeOrCompany(false);
    }

    @AjxMethod("toSetCompany")
    public void toSetCompany(String str, JsFunctionCallback jsFunctionCallback) {
        toSetHomeOrCompany(false, str, jsFunctionCallback);
    }

    @AjxMethod("deleteCompany")
    public void deleteCompany(JsFunctionCallback jsFunctionCallback) {
        deleteHomeOrCompany(false);
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback("1");
        }
    }

    private void deleteHomeOrCompany(boolean z) {
        String str = "";
        coy coy = (coy) ank.a(coy.class);
        if (coy != null) {
            str = coy.a();
        }
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(str);
            if (b != null) {
                FavoritePOI c = z ? b.c() : b.d();
                if (c != null) {
                    b.a((POI) c);
                }
            }
        }
    }

    private String getHomeOrCompany(boolean z) {
        String str = "";
        coy coy = (coy) ank.a(coy.class);
        if (coy != null) {
            str = coy.a();
        }
        com com2 = (com) ank.a(com.class);
        if (com2 == null) {
            return "";
        }
        cop b = com2.b(str);
        if (b == null) {
            return "";
        }
        JSONObject b2 = bnx.b(z ? b.c() : b.d());
        return b2 != null ? b2.toString() : "";
    }

    private void toSetHomeOrCompany(final boolean z, String str, final JsFunctionCallback jsFunctionCallback) {
        FavoritePOI favoritePOI;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("address", getContext().getNativeContext().getString(z ? R.string.home : R.string.company));
        pageBundle.putString("search_hint", getContext().getNativeContext().getString(z ? R.string.act_fromto_home_input_hint : R.string.act_fromto_company_input_hint));
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString(TrafficUtil.KEYWORD, str);
        }
        pageBundle.putObject("callback", new Callback<POI>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(POI poi) {
                if (poi != null) {
                    String str = "";
                    coy coy = (coy) ank.a(coy.class);
                    if (coy != null) {
                        str = coy.a();
                    }
                    com com2 = (com) ank.a(com.class);
                    if (com2 != null) {
                        cop b2 = com2.b(str);
                        if (b2 != null) {
                            if (z) {
                                b2.f(poi);
                            } else {
                                b2.e(poi);
                            }
                        }
                    }
                }
                if (jsFunctionCallback != null) {
                    String str2 = "";
                    if (poi != null) {
                        JSONObject b3 = bnx.b(poi);
                        str2 = b3 != null ? b3.toString() : "";
                    }
                    jsFunctionCallback.callback(str2);
                }
            }
        });
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            com com2 = (com) ank.a(com.class);
            String str2 = null;
            cop b = com2 != null ? com2.b(com2.a()) : null;
            if (b != null) {
                if (z) {
                    favoritePOI = b.c();
                } else {
                    favoritePOI = b.d();
                }
                if (favoritePOI != null) {
                    str2 = favoritePOI.getName();
                }
                if (!TextUtils.isEmpty(str2)) {
                    pageBundle.putString(TrafficUtil.KEYWORD, str2);
                }
            }
            pageContext.startPage((String) "amap.basemap.action.save_search_page", pageBundle);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getCloudSyncData")
    public String getCloudSyncData() {
        return bim.aa().n().getFrequentAddress();
    }

    @AjxMethod(invokeMode = "sync", value = "setCloudSyncData")
    public void setCloudSyncData(String str) {
        bim.aa().n().setFrequentAddress(str);
    }

    @AjxMethod(invokeMode = "sync", value = "getFrequentLocations")
    public String getFrequentLocations(String str) {
        JSONArray jSONArray;
        Object obj;
        try {
            jSONArray = new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONArray = null;
        }
        ArrayList arrayList = new ArrayList();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                String optString = jSONArray.optString(i);
                if (!TextUtils.isEmpty(optString)) {
                    arrayList.add(optString);
                }
            }
        }
        List<FrequentLocationDBInfo> b = cyc.e().b(cyi.a(arrayList));
        JSONArray jSONArray2 = new JSONArray();
        for (FrequentLocationDBInfo a : b) {
            POI b2 = FrequentLocationData.a(a).b();
            if (b2 != null) {
                FrequentLocationInfoEx frequentLocationInfoEx = new FrequentLocationInfoEx();
                frequentLocationInfoEx.copyFromPoi(b2);
                try {
                    obj = new JSONObject(cyj.a(frequentLocationInfoEx));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    obj = null;
                }
                if (obj != null) {
                    jSONArray2.put(obj);
                }
            }
        }
        return jSONArray2.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "getFrequentLocationsSeq")
    public int getFrequentLocationsSeq() {
        if (cyc.e() != null) {
            return cyc.e().b();
        }
        return -1;
    }

    @AjxMethod(invokeMode = "sync", value = "delFrequentLocation")
    public void delFrequentLocation(String str) {
        if (cyc.e() != null) {
            cyc.e().a(str);
        }
    }

    @AjxMethod("toRoute")
    public void toRoute(String str) {
        POI a = bnx.a(str);
        if (a != null) {
            GeoPoint point = a.getPoint();
            if ((point == null || (point.getLatitude() == 0.0d && point.getLongitude() == 0.0d)) ? false : true) {
                bax bax = (bax) a.a.a(bax.class);
                if (bax != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("bundle_key_poi_end", a);
                    pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
                    bax.a(pageBundle);
                }
            }
        }
    }

    @AjxMethod("selectPOIToAdd")
    public void selectPOIToAdd(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                jsFunctionCallback.callback("");
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("search_for", 2);
            pageBundle.putBoolean("isHideMyPosition", true);
            pageBundle.putString("hint", getContext().getNativeContext().getString(R.string.act_search_poi_bar));
            pageBundle.putObject("callback", new Callback<POI>() {
                public void callback(POI poi) {
                    String str;
                    try {
                        JSONObject b2 = bnx.b(poi);
                        str = b2 != null ? b2.toString() : "";
                    } catch (Exception unused) {
                        str = "";
                    }
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(str);
                    }
                }

                public void error(Throwable th, boolean z) {
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback("");
                    }
                }
            });
            pageContext.startPage((String) "search.fragment.SearchCallbackFragment", pageBundle);
        }
    }
}

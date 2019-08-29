package com.autonavi.minimap.bundle.favorites;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("favorite")
public class ModuleFavorite extends AbstractModule {
    static final String MODULE_NAME = "favorite";
    /* access modifiers changed from: private */
    public int mMaxCount;
    /* access modifiers changed from: private */
    public JsFunctionCallback mUpdateFavoriteCallback;

    enum SAVEPOI {
        HOME,
        COMPANY,
        BOTH
    }

    public ModuleFavorite(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("isPoiSaved")
    public void isPoiSaved(String str, JsFunctionCallback jsFunctionCallback) {
        boolean z;
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str)) {
                jsFunctionCallback.callback(buildError(1, "param error, poi param is not a valid JSON", str), Boolean.FALSE);
                return;
            }
            com com2 = (com) ank.a(com.class);
            if (com2 == null) {
                jsFunctionCallback.callback(buildError(2, "internal error, fail to get favoriteFactory", new String[0]), Boolean.FALSE);
                return;
            }
            cop b = com2.b(com2.a());
            if (b != null) {
                try {
                    z = b.c(bnx.a(str));
                } catch (Exception unused) {
                    jsFunctionCallback.callback(buildError(1, "param error, poi param is not a valid JSON", new String[0]), Boolean.FALSE);
                    return;
                }
            } else {
                z = false;
            }
            jsFunctionCallback.callback(null, Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "setHomeOrCompany")
    public boolean setHomeOrCompany(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (str.equals("home")) {
            POI a = bnx.a(str2);
            if (a != null) {
                auz auz = (auz) a.a.a(auz.class);
                if (auz != null) {
                    auz.a(a);
                    return true;
                }
            }
        } else if (str.equals("company")) {
            POI a2 = bnx.a(str2);
            if (a2 != null) {
                auz auz2 = (auz) a.a.a(auz.class);
                if (auz2 != null) {
                    auz2.b(a2);
                    return true;
                }
            }
        }
        return false;
    }

    @AjxMethod("getHomeAndCompany")
    public void getHomeAndCompany(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            String str2 = bny.c;
            if (((com) ank.a(com.class)) != null) {
                if (str.contains("home") && str.contains("company")) {
                    str2 = getHomeCompanyPOIWithJson(SAVEPOI.BOTH);
                } else if (str.contains("home")) {
                    str2 = getHomeCompanyPOIWithJson(SAVEPOI.HOME);
                } else if (str.contains("company")) {
                    str2 = getHomeCompanyPOIWithJson(SAVEPOI.COMPANY);
                }
            }
            jsFunctionCallback.callback(str2);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getHome")
    public String getHome() {
        POI homeCompanyPOI = getHomeCompanyPOI(SAVEPOI.HOME);
        if (homeCompanyPOI == null) {
            return null;
        }
        return bnx.b(homeCompanyPOI).toString();
    }

    @AjxMethod("getNormalPoints")
    public void getNormalPoints(int i, JsFunctionCallback jsFunctionCallback) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && pageContext.getActivity() != null) {
            this.mUpdateFavoriteCallback = jsFunctionCallback;
            this.mMaxCount = i;
            updateFavoritePlace();
        }
    }

    private void updateFavoritePlace() {
        ahl.b(new a<Object>() {
            public final Object doBackground() throws Exception {
                com com2 = (com) ank.a(com.class);
                cop b = com2 != null ? com2.b(com2.a()) : null;
                JSONArray jSONArray = new JSONArray();
                if (b == null) {
                    ModuleFavorite.this.mUpdateFavoriteCallback.callback(jSONArray.toString());
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                List<FavoritePOI> f = b.f();
                if (f.size() > 0) {
                    arrayList.addAll(f);
                }
                int size = arrayList.size() <= ModuleFavorite.this.mMaxCount ? arrayList.size() : ModuleFavorite.this.mMaxCount;
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < size; i++) {
                    FavoritePOI favoritePOI = (FavoritePOI) arrayList.get(i);
                    bnx.b(favoritePOI);
                    JSONObject b2 = bnx.b(favoritePOI);
                    jSONArray.put(b2);
                    stringBuffer.append(b2.toString());
                }
                ModuleFavorite.this.mUpdateFavoriteCallback.callback(jSONArray.toString());
                return null;
            }
        });
    }

    private POI getHomeCompanyPOI(SAVEPOI savepoi) {
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b = com2.b(com2.a());
            if (b != null) {
                if (savepoi == SAVEPOI.HOME) {
                    return b.c();
                }
                if (savepoi == SAVEPOI.COMPANY) {
                    return b.d();
                }
            }
        }
        return null;
    }

    private String getHomeCompanyPOIWithJson(SAVEPOI savepoi) {
        POI poi;
        POI poi2 = null;
        if (savepoi == SAVEPOI.HOME || savepoi == SAVEPOI.BOTH) {
            poi = getHomeCompanyPOI(SAVEPOI.HOME);
        } else {
            poi = null;
        }
        if (savepoi == SAVEPOI.COMPANY || savepoi == SAVEPOI.BOTH) {
            poi2 = getHomeCompanyPOI(SAVEPOI.COMPANY);
        }
        JSONObject jSONObject = new JSONObject();
        if (poi != null) {
            try {
                jSONObject.put("home", bnx.b(poi));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (savepoi == SAVEPOI.HOME) {
            return bny.c;
        }
        if (poi2 != null) {
            jSONObject.put("company", bnx.b(poi2));
        } else if (savepoi == SAVEPOI.COMPANY) {
            return bny.c;
        }
        return jSONObject.toString();
    }

    private JSONObject buildError(int i, String str, String... strArr) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            if (str != null) {
                jSONObject.put("msg", str);
            }
            if (strArr != null) {
                StringBuilder sb = new StringBuilder();
                for (String append : strArr) {
                    sb.append(append);
                    sb.append(MergeUtil.SEPARATOR_KV);
                }
                String sb2 = sb.toString();
                if (!TextUtils.isEmpty(sb2)) {
                    jSONObject.put("extra", sb2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}

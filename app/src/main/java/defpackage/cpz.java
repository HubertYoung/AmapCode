package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.save.page.SaveEditPointPage;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpz reason: default package */
/* compiled from: SetFavoriteMarkAction */
public class cpz extends vz {
    private wa a;

    private void b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("_action", this.a.b);
            jSONObject.put("status", c());
            jSONObject.put("favInfo", bnx.a((FavoritePOI) ((POI) a().getBundle().getObject("POI")).as(FavoritePOI.class)));
            a().callJs(this.a.a, jSONObject.toString());
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
    }

    private boolean c() {
        PageBundle bundle = a().getBundle();
        if (bundle != null) {
            POI poi = (POI) bundle.getObject("POI");
            if (poi != null) {
                coy coy = (coy) ank.a(coy.class);
                if (coy != null) {
                    return coy.a(poi);
                }
            }
        }
        return false;
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        POI poi;
        JsAdapter a2 = a();
        if (a2 != null && a2.getBundle() != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("poiInfo");
                this.a = waVar;
                POIFactory.createPOI();
                POI poi2 = null;
                if (jSONObject2 != null) {
                    poi = kv.a(jSONObject2.toString());
                } else {
                    PageBundle bundle = a().getBundle();
                    poi = bundle != null ? (POI) bundle.getObject("POI") : null;
                }
                if (cpm.b().a(poi)) {
                    boolean z = !cpm.b().a(poi);
                    if (a() != null) {
                        if (a().getBundle() != null) {
                            poi2 = (POI) a().getBundle().getObject("POI");
                        }
                        if (poi2 != null) {
                            if (z) {
                                FavoritePOI favoritePOI = (FavoritePOI) poi2.as(FavoritePOI.class);
                                favoritePOI.setSaved(true);
                                if (TextUtils.isEmpty(favoritePOI.getName()) && !TextUtils.isEmpty(favoritePOI.getAddr())) {
                                    favoritePOI.setName(favoritePOI.getAddr());
                                }
                                cpf.b(cpm.b().a()).b((POI) favoritePOI);
                                if (a().getBundle() != null && !a().getBundle().getBoolean("mBSubScribe")) {
                                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.poi_add_favourite_success));
                                }
                                b();
                                brn brn = (brn) ank.a(brn.class);
                                if (brn != null) {
                                    brn.b();
                                }
                                return;
                            }
                            cpf.b(cpm.b().a()).a(poi2);
                            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.poi_cancel_favourite));
                            ((FavoritePOI) poi2.as(FavoritePOI.class)).setSaved(false);
                            b();
                            brn brn2 = (brn) ank.a(brn.class);
                            if (brn2 != null) {
                                brn2.b();
                            }
                        }
                    }
                    return;
                }
                JsAdapter a3 = a();
                if (a3 != null) {
                    a3.mPageContext.startPageForResult(SaveEditPointPage.class, new PageBundle(), 1000);
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}

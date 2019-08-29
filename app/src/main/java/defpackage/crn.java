package defpackage;

import android.content.res.Configuration;
import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.basemap.save.page.SaveSearchPage;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: crn reason: default package */
/* compiled from: SaveSearchPresenter */
public final class crn extends AbstractBasePresenter<SaveSearchPage> {
    public crn(SaveSearchPage saveSearchPage) {
        super(saveSearchPage);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SaveSearchPage saveSearchPage = (SaveSearchPage) this.mPage;
        if (saveSearchPage.i != null) {
            saveSearchPage.i.initNoHistoryTipText();
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        SaveSearchPage saveSearchPage = (SaveSearchPage) this.mPage;
        if (ResultType.OK.equals(resultType)) {
            if (i == 1 && pageBundle.containsKey("SelectPoiFromMapFragment.MapClickResult")) {
                POI poi = (POI) pageBundle.getObject("SelectPoiFromMapFragment.MapClickResult");
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putObject("result_poi", poi);
                if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                    pageBundle2.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                }
                if (saveSearchPage.b != null) {
                    saveSearchPage.b.setFocusable(false);
                    saveSearchPage.b.setFocusableInTouchMode(false);
                    saveSearchPage.b.clearFocus();
                }
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("result_poi", bnx.b(poi));
                    if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                        jSONObject.put(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                    }
                    jSONObject.put("result_code", saveSearchPage.getRequestCode());
                    pageBundle2.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saveSearchPage.setResult(ResultType.OK, pageBundle2);
                if (saveSearchPage.l != null) {
                    saveSearchPage.l.callback(poi);
                }
                saveSearchPage.finish();
            } else if (i == 2) {
                if (pageBundle.containsKey("result_poi")) {
                    POI poi2 = (POI) pageBundle.getObject("result_poi");
                    PageBundle pageBundle3 = new PageBundle();
                    pageBundle3.putObject("result_poi", poi2);
                    if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                        pageBundle3.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                    }
                    if (saveSearchPage.b != null) {
                        saveSearchPage.b.setFocusable(false);
                        saveSearchPage.b.setFocusableInTouchMode(false);
                        saveSearchPage.b.clearFocus();
                    }
                    try {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("result_poi", bnx.b(poi2));
                        if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                            jSONObject2.put(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                        }
                        jSONObject2.put("result_code", saveSearchPage.getRequestCode());
                        pageBundle3.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject2.toString());
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    saveSearchPage.setResult(ResultType.OK, pageBundle3);
                    if (saveSearchPage.l != null) {
                        saveSearchPage.l.callback(poi2);
                    }
                    saveSearchPage.finish();
                } else if (pageBundle.containsKey(TrafficUtil.KEYWORD)) {
                    saveSearchPage.b.setText(pageBundle.getString(TrafficUtil.KEYWORD));
                    saveSearchPage.e = false;
                    SuperId.getInstance().setBit3("09");
                    saveSearchPage.a((String) null);
                }
            }
        } else if (ResultType.CANCEL.equals(resultType) && saveSearchPage.h.getVisibility() == 0) {
            saveSearchPage.i.showHistory();
        }
    }

    public final void onStart() {
        super.onStart();
        SaveSearchPage saveSearchPage = (SaveSearchPage) this.mPage;
        SuperId.getInstance().reset();
        saveSearchPage.f = "f";
        if (!TextUtils.isEmpty(saveSearchPage.g)) {
            saveSearchPage.f = saveSearchPage.g;
        }
        if (saveSearchPage.a != null) {
            saveSearchPage.a.requestEditFocus();
            saveSearchPage.a.showInputMethod();
            saveSearchPage.a.setSuperIdBit1(saveSearchPage.f);
        }
        SuperId.getInstance().setBit1(saveSearchPage.f);
    }

    public final void onStop() {
        super.onStop();
        SaveSearchPage saveSearchPage = (SaveSearchPage) this.mPage;
        saveSearchPage.a.hideInputMethod();
        saveSearchPage.a.dissmissIatDialog();
    }

    public final void onDestroy() {
        SaveSearchPage saveSearchPage = (SaveSearchPage) this.mPage;
        saveSearchPage.i.cancelTask();
        saveSearchPage.j.cancelTask();
        super.onDestroy();
        SaveSearchPage saveSearchPage2 = (SaveSearchPage) this.mPage;
        if (saveSearchPage2.b != null) {
            saveSearchPage2.a.hideInputMethod();
        }
        if (saveSearchPage2.j != null) {
            saveSearchPage2.j.onDestroy();
        }
        saveSearchPage2.b();
        SaveSearchPage saveSearchPage3 = (SaveSearchPage) this.mPage;
        if (saveSearchPage3.a != null) {
            saveSearchPage3.a.clearFocus();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((SaveSearchPage) this.mPage).a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}

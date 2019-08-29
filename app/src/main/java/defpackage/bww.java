package defpackage;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.page.SearchAjxCitySuggestionPage;
import com.autonavi.map.search.page.SearchErrorCityPage;
import com.autonavi.map.search.page.SearchErrorCityPage.a;
import com.autonavi.map.search.page.SearchErrorSuggestionPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;

/* renamed from: bww reason: default package */
/* compiled from: PoiSearchResultActionImpl */
public final class bww implements bwr {
    public bwx a;

    public bww(bwx bwx) {
        this.a = bwx;
    }

    public final void a(String str) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_DIRCTJUMP);
        this.a.b.a(intent);
    }

    public final void a(final InfoliteResult infoliteResult) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, infoliteResult);
        pageBundle.putObject(SearchErrorSuggestionPage.BUNDLE_KEY_ONITEMLISTENER, new a() {
            public final void a(String str) {
                bww bww = bww.this;
                InfoliteParam clone = infoliteResult.mWrapper.clone();
                clone.city = str;
                clone.utd_sceneid = "400002";
                SuperId.getInstance().setBit3("09");
                clone.superid = SuperId.getInstance().getScenceId();
                ekv ekv = new ekv();
                bvt bvt = new bvt();
                bvt.e = bww.a.e;
                bvt.b = bwx.a;
                bvt.d = bww.a;
                ekv.a(clone, 1, bvt);
            }

            public final void b(String str) {
                bww.this.a.e = str;
                SuperId.getInstance().setBit3("08");
                bww.this.a.c.a((String) "101000", (String) "", SuperId.getInstance());
            }
        });
        this.a.b.a(SearchErrorCityPage.class, pageBundle);
    }

    public final void a(InfoliteResult infoliteResult, ArrayList<POI> arrayList) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            bwv bwv = new bwv(pageContext, Boolean.valueOf(this.a.l));
            if (arrayList == null) {
                if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    if (latestPosition != null) {
                        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
                        edit.putInt("X", latestPosition.x);
                        edit.putInt("Y", latestPosition.y);
                        edit.putInt("Z", 15);
                        edit.apply();
                        if (DoNotUseTool.getMapView() != null) {
                            DoNotUseTool.getMapView().a((GLGeoPoint) latestPosition);
                        }
                        PageBundle pageBundle = new PageBundle("amap.basemap.action.default_page", "com.autonavi.minimap");
                        pageBundle.putObject(Constants.KEY_ACTION, "action_move_to_current");
                        apr apr = (apr) a.a.a(apr.class);
                        if (apr != null) {
                            apr.a(AMapPageUtil.getPageContext(), pageBundle);
                        }
                    }
                    return;
                }
                this.a.b.a(R.string.alert_errortip, R.string.ic_loc_fail);
            } else if (arrayList.size() == 1) {
                bwv.a(infoliteResult, arrayList.get(0), false);
            } else {
                if (arrayList.size() > 1) {
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, infoliteResult);
                    pageBundle2.putObject("Areas", arrayList);
                    pageBundle2.putBoolean("mFromVoice", this.a.l);
                    pageBundle2.putString("page_type", "area_suggest");
                    this.a.b.a(SearchAjxCitySuggestionPage.class, pageBundle2);
                }
            }
        }
    }
}

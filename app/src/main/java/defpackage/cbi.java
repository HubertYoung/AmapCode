package defpackage;

import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.searchhome.ajx.ModuleSearchHome;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.page.SearchPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cbi reason: default package */
/* compiled from: SearchPresenter */
public final class cbi extends Ajx3PagePresenter implements bgo {
    private String a = "";
    private cbg b;
    private bbq c;
    private bzy d;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public cbi(SearchPage searchPage) {
        super(searchPage);
        Ajx.getInstance().prepare();
        bim.aa().z();
        this.d = new bzy();
    }

    public final void onPageCreated() {
        super.onPageCreated();
        c();
    }

    public final void onStart() {
        super.onStart();
        zt.a().a(ModuleSearchHome.MODULE_NAME, true);
        ((Ajx3Page) this.mPage).setSoftInputMode(32);
    }

    public final void onStop() {
        super.onStop();
        d.a.n = null;
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.c != null && !this.c.b()) {
            this.c.a();
            this.c = null;
        }
        bim.aa().z();
        AMapPageUtil.removePageStateListener((bid) this.mPage);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        c();
    }

    public final void onResume() {
        super.onResume();
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1("a");
    }

    public final void onPause() {
        super.onPause();
        zt.a().a(ModuleSearchHome.MODULE_NAME, false);
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.b.c == null || !this.b.c.a) {
            return super.onBackPressed();
        }
        String str = this.b.c.c;
        a b2 = new a(((Ajx3Page) this.mPage).getContext()).a(R.string.be_sure_where_to_back).a(R.string.stay_at_amap, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                ((Ajx3Page) cbi.this.mPage).dismissViewLayer(alertView);
                cbi.a(cbi.this, 1);
            }
        }).b((CharSequence) ((Ajx3Page) this.mPage).getString(R.string.back_to, str), (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                ((Ajx3Page) cbi.this.mPage).dismissViewLayer(alertView);
                cbi.a(cbi.this, 2);
            }
        });
        b2.c = new a() {
            public final void onClick(AlertView alertView, int i) {
                ((Ajx3Page) cbi.this.mPage).dismissViewLayer(alertView);
            }
        };
        ccf.a((bid) this.mPage, b2.a(true).a());
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    private void b() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("poi_detail_page_type", this.b.b);
        ((Ajx3Page) this.mPage).setResult(ResultType.OK, pageBundle);
    }

    private void c() {
        this.b = cbg.a(((Ajx3Page) this.mPage).getArguments());
        this.a = this.b.a;
        d();
        b();
    }

    public final String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("recordLog", true);
            jSONObject.put("updateHistory", true);
            jSONObject.put(TrafficUtil.KEYWORD, this.a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.a = null;
        return jSONObject.toString();
    }

    private void d() {
        if (this.b.e == SearchFor.SCHEME_POI) {
            Rect rect = this.b.d;
            if (rect == null || rect.isEmpty()) {
                rect = e();
            }
            this.c = this.d.a(this.b.a, this.b.g, this.b.h, rect);
        }
    }

    private static Rect e() {
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            return mapView.H();
        }
        return new Rect();
    }

    public final void a(TipItem tipItem, boolean z) {
        if (tipItem != null) {
            b(tipItem, z);
            a(tipItem.name, tipItem);
        }
    }

    private void a(String str, TipItem tipItem) {
        if (tipItem == null || !SearchHistoryHelper.isUserfulPoi(tipItem)) {
            this.c = this.d.a(str, tipItem != null ? tipItem.adcode : null, tipItem, e());
        } else {
            this.c = this.d.a(tipItem, e());
        }
    }

    private static void b(TipItem tipItem, boolean z) {
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1("a");
        if (tipItem.type == 0) {
            SuperId.getInstance().setBit2("02");
            if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                SuperId.getInstance().setBit3("06");
            } else {
                SuperId.getInstance().setBit3("07");
            }
        } else {
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
    }

    static /* synthetic */ void a(cbi cbi, int i) {
        if (i == 1) {
            cbi.b.c = null;
            ((Ajx3Page) cbi.mPage).startPage((String) "amap.basemap.action.default_page", new PageBundle());
            return;
        }
        if (i == 2) {
            Intent a2 = cbi.b.c.a();
            if (a2 != null) {
                ((Ajx3Page) cbi.mPage).getActivity().startActivity(a2);
            }
            cbi.b.c = null;
        }
    }
}

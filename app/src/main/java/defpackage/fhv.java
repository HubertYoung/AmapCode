package defpackage;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;
import search.page.SearchFromAroundPage;

/* renamed from: fhv reason: default package */
/* compiled from: SearchFromAroundPresenter */
public final class fhv extends Ajx3PagePresenter implements bgo {
    public int a = -1;
    public Rect b;
    public String c = "";
    public String d = "";
    public POI e = POIFactory.createPOI();
    public bbq f;
    public String g;
    public boolean h;
    public String i = "";
    private boolean j = false;
    private AosRequest k;
    private boolean l;
    private boolean m;
    private int n;

    public fhv(SearchFromAroundPage searchFromAroundPage) {
        super(searchFromAroundPage);
    }

    public final void onPageCreated() {
        a();
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        ((Ajx3Page) this.mPage).setSoftInputMode(32);
        PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
        if (arguments != null) {
            this.c = arguments.getString("from_page");
            SuperId.getInstance().reset();
            if ("220000".equals(this.c)) {
                this.d = SuperId.BIT_1_RQBXY;
                SuperId.getInstance().setBit1(SuperId.BIT_1_RQBXY);
            } else if ("620000".equals(this.c)) {
                this.d = SuperId.BIT_1_NEARBY_SEARCH;
                SuperId.getInstance().setBit1(SuperId.BIT_1_NEARBY_SEARCH);
            }
        }
    }

    public final void onStop() {
        super.onStop();
        d.a.n = null;
    }

    public final void onResume() {
        super.onResume();
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.f != null && !this.f.b()) {
            this.f.a();
            this.f = null;
        }
        if (this.k != null) {
            yq.a();
            yq.a(this.k);
            this.k = null;
        }
    }

    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 84) {
            return true;
        }
        return super.onKeyDown(i2, keyEvent);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        a();
    }

    @TargetApi(12)
    private void a() {
        PageBundle arguments = ((Ajx3Page) this.mPage).getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
        }
        this.c = arguments.getString("from_page");
        if ("220000".equals(this.c)) {
            this.a = 2;
        } else if ("620000".equals(this.c)) {
            this.a = 1;
        }
        String string = arguments.getString(TrafficUtil.KEYWORD);
        this.g = string;
        if (arguments.containsKey("center_poi")) {
            this.e = (POI) arguments.get("center_poi");
        } else if (arguments.containsKey("POI")) {
            this.e = (POI) arguments.getObject("POI");
        } else {
            arguments.getBoolean("from_search_native_no_result_page", false);
        }
        if (this.e == null || this.e.getPoint() == null || this.e.getPoint().x <= 0 || this.e.getPoint().y <= 0) {
            this.e = POIFactory.createPOI("", LocationInstrument.getInstance().getLatestPosition());
        }
        if (arguments.containsKey("search_type")) {
            this.n = arguments.getInt("search_type", 2);
        }
        this.j = arguments.getBoolean("draw_center", false);
        if (this.n == 2) {
            this.b = DoNotUseTool.getMapView().H();
        } else if (1 == this.n) {
            this.b = null;
            if (this.j && this.e != null) {
                Rect H = DoNotUseTool.getMapView().H();
                int i2 = (H.bottom - H.top) / 2;
                int i3 = (H.right - H.left) / 2;
                this.b = new Rect(this.e.getPoint().x - i3, this.e.getPoint().y - i2, this.e.getPoint().x + i3, this.e.getPoint().y + i2);
            }
            if (arguments.containsKey("map_rect") && arguments.getObject("map_rect") != null) {
                this.b = (Rect) arguments.getObject("map_rect");
                this.e.setPoint(new GeoPoint(this.b.left + ((this.b.right - this.b.left) / 2), this.b.top + ((this.b.bottom - this.b.top) / 2)));
            }
        }
        if (this.e != null) {
            try {
                li.a().a(this.e.getPoint().x, this.e.getPoint().y);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (!TextUtils.isEmpty(string) && arguments.getBoolean("start_search")) {
                a(string, null, "");
            }
            this.h = arguments.getBoolean("key_can_search_with_hint", false);
            this.l = arguments.getBoolean("is_cache", false);
            this.m = arguments.getBoolean("is_from_new_nearby", false);
            this.i = arguments.getString("hint_content", "");
        }
    }

    public final void a(String str, TipItem tipItem, String str2) {
        if (str2 == null) {
            str2 = !TextUtils.isEmpty(this.c) ? SuperId.getInstance().getScenceId() : null;
        }
        if (!TextUtils.isEmpty(str)) {
            str = str.trim();
        }
        bck bck = (bck) a.a.a(bck.class);
        if (bck != null) {
            if (this.n == 2) {
                InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), str, this.b);
                a2.search_operate = 1;
                a2.search_sceneid = str2;
                a2.log_center_id = this.e.getId();
                this.f = bck.a(a2, new bcl());
                return;
            }
            if (!TextUtils.isEmpty(str)) {
                InfoliteParam a3 = bbv.a(AppManager.getInstance().getUserLocInfo(), str, this.e.getPoint(), null);
                a3.superid = str2;
                a3.search_sceneid = str2;
                a3.log_center_id = this.e.getId();
                bcl bcl = new bcl();
                bcl.b = this.a;
                bcl.d = tipItem;
                bcl.g = this.b;
                bcl.c = a3.keywords;
                bcl.m = this.m;
                bcl.l = this.l;
                bcl.k = this.h;
                bcl.n = this.i;
                bcl.h = this.e;
                this.f = bck.a(a3, bcl);
            }
        }
    }

    public final boolean handleVUICmd(final bgb bgb, bfb bfb) {
        long scenesID = ((SearchFromAroundPage) this.mPage).getScenesID();
        boolean z = false;
        if (!"searchPoi".equals(bgb.d)) {
            return false;
        }
        JSONObject a2 = bwu.a(scenesID, bgb);
        bck bck = (bck) a.a.a(bck.class);
        if (!(a2 == null || bck == null)) {
            bck.a();
            String str = bgb.c;
            bcl bcl = new bcl();
            bcl.b = this.a;
            bcl.h = this.e;
            bcl.c = str;
            bcl.g = this.b;
            bcl.m = this.m;
            bcl.l = this.l;
            bcl.k = this.h;
            bcl.n = this.i;
            bvt bvt = new bvt(bcl);
            ((SearchFromAroundPage) this.mPage).b.keywords = bgb.c;
            ((SearchFromAroundPage) this.mPage).b.need_utd = true;
            z = bck.a().a(bvt, a2, ((SearchFromAroundPage) this.mPage).b);
        }
        if (z) {
            UiExecutor.post(new Runnable() {
                public final void run() {
                    d.a.a(bgb.a, 10000, (String) null);
                }
            });
        } else {
            d.a.a(bgb.a, 10007, (String) null);
        }
        return true;
    }
}

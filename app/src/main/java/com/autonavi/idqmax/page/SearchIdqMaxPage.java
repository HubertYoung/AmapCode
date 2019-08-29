package com.autonavi.idqmax.page;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.IdqMaxCMD;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTop;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage.POI_DETAIL_TYPE;
import com.autonavi.map.search.view.SearchResultHeaderView;
import com.autonavi.minimap.R;
import org.json.JSONObject;

public class SearchIdqMaxPage extends MapBasePage<bqm> implements bgm, launchModeSingleTop {
    protected SearchResultHeaderView a;
    protected bvn b;
    protected View c;
    protected View d;
    protected FrameLayout e;
    protected bqj f;
    public bzd g;

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 17592187092992L;
    }

    public boolean isGpsTipDisable() {
        return true;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean isUsePoiDelegate() {
        return true;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: m */
    public bqm createPresenter() {
        return new bqm(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.search_result_map_fragment);
        this.c = findViewById(R.id.mapBottomInteractiveView);
        this.e = (FrameLayout) findViewById(R.id.root_layout);
        this.a = (SearchResultHeaderView) findViewById(R.id.view_normal_title);
        this.f = new bqj(this);
        this.f.a();
        this.d = findViewById(R.id.mapTopInteractiveView);
        this.b = new bvn(getContext());
        this.b.a = this.d;
        getMapManager().getOverlayManager().getMapPointOverlay();
        this.g = new bzd(new bql() {
            public final void a(GeoPoint geoPoint) {
            }

            public final MapBasePage a() {
                return SearchIdqMaxPage.this;
            }
        });
        this.g.a();
        bbx.a((bid) this);
    }

    public void onPageStart() {
        super.onPageStart();
        this.a.setOnSearchKeywordResultTitleViewListener((a) this.mPresenter);
        if (this.a != null) {
            this.a.setKeyword(((bqm) this.mPresenter).b.a());
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
            if (awo.d()) {
                awo.e();
            }
        }
    }

    public void onPagePause() {
        super.onPagePause();
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c();
            awo.f();
        }
    }

    public View getMapSuspendView() {
        return this.f.getSuspendView();
    }

    public BaseCQLayerOwner createCQLayerOwner() {
        bqm bqm = (bqm) this.mPresenter;
        return new a((MapBasePage) bqm.mPage);
    }

    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        bqh bqh = ((bqm) this.mPresenter).a;
        if (bqh.b != null) {
            bqh.b.a(bqh.a, i, resultType, pageBundle);
        }
    }

    public POI_DETAIL_TYPE getPoiDetailType() {
        return POI_DETAIL_TYPE.CQ_VIEW;
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public void finishSelf() {
        finish();
    }

    public void onPageGpsBtnClicked() {
        bqh bqh = ((bqm) this.mPresenter).a;
        if ((bqh.b instanceof bqg) && bqh.d.l() != null) {
            bqh.d.l().sendIdqMaxCmd(IdqMaxCMD.CMD_GPS_CLICK, null);
        }
    }

    public final SearchResultHeaderView a() {
        return this.a;
    }

    public final bvn b() {
        return this.b;
    }

    public final View c() {
        return this.c;
    }

    public final FrameLayout d() {
        return this.e;
    }

    public final View e() {
        return this.f.e;
    }

    public final View f() {
        return this.f.d;
    }

    public final View g() {
        return this.f.f;
    }

    public final View h() {
        return this.f.c;
    }

    public final View i() {
        return this.f.g;
    }

    public final View j() {
        return this.f.h;
    }

    public final View k() {
        return this.f.i;
    }

    public final bqj l() {
        return this.f;
    }
}

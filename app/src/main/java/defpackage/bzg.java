package defpackage;

import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchcommon.view.SearchKeywordResultTitleView.a;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.view.SearchResultHeaderView;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import defpackage.bxo;

/* renamed from: bzg reason: default package */
/* compiled from: SearchResultBaseViewManager */
public abstract class bzg<T extends bxo> implements bzm, bzs {
    private float a;
    protected Handler b = new Handler();
    public T c;
    protected View d;
    protected SearchResultHeaderView e;
    protected bvn f;
    public FrameLayout g;
    protected View h;
    protected LinearLayout i;
    protected bzj j;
    private a k = new a() {
        public final void d() {
        }

        public final void a() {
            LogManager.actionLogV2(LogConstant.SEARCH_RESULT_MAP, "B001");
            bzg.this.c.q();
        }

        public final void c() {
            bid pageContext = AMapPageUtil.getPageContext();
            apr apr = (apr) a.a.a(apr.class);
            if (apr != null) {
                apr.b(pageContext);
            }
        }

        public final void b() {
            bzg.this.c.C();
        }
    };

    public Point l() {
        return null;
    }

    public bzg(T t, bzj bzj) {
        this.c = t;
        this.j = bzj;
    }

    public final void b(View view) {
        if (view != null) {
            this.e = (SearchResultHeaderView) view.findViewById(R.id.view_normal_title);
            if (this.e != null) {
                this.e.setVisibility(0);
                this.e.setOnSearchKeywordResultTitleViewListener(this.k);
            }
        }
    }

    public void a(View view) {
        if (this.d == null) {
            this.d = view.findViewById(R.id.mapTopInteractiveView);
            this.d.setVisibility(8);
            this.f = new bvn(this.c.f.getContext());
            this.f.a = this.d;
        }
        this.h = view.findViewById(R.id.mapBottomInteractiveView);
        this.g = (FrameLayout) view.findViewById(R.id.root_layout);
        this.i = (LinearLayout) view.findViewById(R.id.top_find_here_layout);
        this.i.setOnClickListener(this.c);
        this.a = this.c.f.getResources().getDimension(R.dimen.top_searchbar_height) + this.c.f.getResources().getDimension(R.dimen.map_container_btn_margin);
    }

    public final void a(InfoliteResult infoliteResult) {
        if (this.e != null) {
            this.e.setKeyword(infoliteResult.mWrapper.keywords);
        }
    }

    public final void b(String str) {
        if (this.e != null) {
            this.e.setKeyword(str);
        }
    }

    public void s() {
        if (this.d != null) {
            this.d.setVisibility(8);
        }
    }

    public void p() {
        if (this.e != null) {
            this.e.setOnSearchKeywordResultTitleViewListener(null);
        }
    }

    public final void a(boolean z) {
        if (z) {
            this.f.a();
        } else {
            this.f.b();
        }
    }

    public final boolean t() {
        if (this.i != null && this.i.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public final void b(int i2) {
        bxd ag = this.c.ag();
        if (ag != null) {
            ag.i = true;
            switch (i2) {
                case 1:
                    return;
                case 2:
                    return;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    d(false);
                    ag.i = false;
                    break;
            }
        }
    }

    public int u() {
        if (this.h != null) {
            return this.h.getHeight();
        }
        return 0;
    }

    public final void a(int i2) {
        this.h.getLayoutParams().height = i2;
        this.h.requestLayout();
    }

    public final float i() {
        return this.a;
    }

    public final String v() {
        if (this.e != null) {
            return this.e.getKeyword();
        }
        return null;
    }

    public final void b(boolean z) {
        bxd ag = this.c.ag();
        if (!this.c.R() && ag != null && ag.i) {
            this.i.setVisibility(z ? 0 : 8);
        }
    }
}

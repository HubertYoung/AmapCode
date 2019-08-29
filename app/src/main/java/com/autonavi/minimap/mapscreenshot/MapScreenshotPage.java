package com.autonavi.minimap.mapscreenshot;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.LoadingViewBL;
import com.autonavi.widget.ui.TitleBar;

public class MapScreenshotPage extends AbstractBaseMapPage<drv> {
    public TitleBar a;
    public LoadingViewBL b;
    private a c;
    private final OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            drv drv = (drv) MapScreenshotPage.this.mPresenter;
            ((MapScreenshotPage) drv.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
            ((MapScreenshotPage) drv.mPage).finish();
        }
    };
    private final OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            drv drv = (drv) MapScreenshotPage.this.mPresenter;
            cfc.a().a(((MapScreenshotPage) drv.mPage).getMapManager(), (defpackage.cfc.a) drv);
        }
    };

    static class a extends ccx {
        public final void c() {
        }

        public final void d() {
        }

        public final void e() {
        }

        public a(IMapPage iMapPage) {
            super(iMapPage);
        }

        public final void b() {
            View a = this.e.a(false);
            a.setContentDescription("指南针");
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.leftMargin = agn.a(this.b, 8.0f);
            layoutParams.topMargin = agn.a(this.b, 4.0f);
            a(a, layoutParams);
        }

        public final void f() {
            ZoomView l = this.e.l();
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.rightMargin = agn.a(this.b, 5.0f);
            layoutParams.topMargin = agn.a(this.b, 4.0f);
            d(l, layoutParams);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public drv createPresenter() {
        return new drv(this);
    }

    public View getMapSuspendView() {
        return this.c.a();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.feedback_map_screenshot_page);
        View contentView = getContentView();
        this.c = new a(this);
        this.a = (TitleBar) contentView.findViewById(R.id.tbTitle);
        this.a.setOnBackClickListener(this.d);
        this.a.setOnActionClickListener(this.e);
        this.a.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public final void a() {
        if (getPageContext() != null && this.b != null) {
            getPageContext().dismissViewLayer(this.b);
        }
    }
}

package com.autonavi.minimap.bundle.locationselect.page;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.bundle.locationselect.presenter.SelectRoadFromMapPresenter;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.basemap.action.select_road_from_map")
public class SelectRoadFromMapPage extends AbstractBaseMapPage<SelectRoadFromMapPresenter> implements OnClickListener {
    public b a;
    public b b;
    public PointOverlay<PointOverlayItem> c;
    public bty d;
    public boolean e = true;
    public cdp f = new cdp() {
        public final void onFloorChanged(int i, int i2) {
        }

        public final void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i) {
            elc.b = z;
            elc.a = z;
            SelectRoadFromMapPage.a(SelectRoadFromMapPage.this, z);
        }
    };
    private TitleBar g;
    /* access modifiers changed from: private */
    public TranslateAnimation h;
    private a i;
    private final OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            ((SelectRoadFromMapPresenter) SelectRoadFromMapPage.this.mPresenter).a();
        }
    };
    /* access modifiers changed from: private */
    public int k = 0;

    static class a extends ccx {
        public final void e() {
        }

        public a(IMapPage iMapPage) {
            super(iMapPage);
        }

        public final void c() {
            b(this.e.n(), this.e.q());
        }

        public final void d() {
            View b = this.e.b(false);
            LayoutParams layoutParams = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
            layoutParams.rightMargin = agn.a(this.a.getContext(), 5.0f);
            c(b, layoutParams);
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

    public static class b {
        TextView a;
        public TextView b;
        public Button c;
        public ImageView d;

        /* synthetic */ b(View view, int i, int i2, int i3, int i4, OnClickListener onClickListener, byte b2) {
            this(view, i, i2, i3, i4, onClickListener);
        }

        private b(View view, @IdRes int i, @IdRes int i2, @IdRes int i3, @IdRes int i4, OnClickListener onClickListener) {
            this.d = (ImageView) view.findViewById(i);
            this.a = (TextView) view.findViewById(i2);
            this.b = (TextView) view.findViewById(i3);
            this.c = (Button) view.findViewById(i4);
            this.c.setOnClickListener(onClickListener);
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.layout_select_road_page);
        View contentView = getContentView();
        this.d = getMapManager().getMapView();
        this.i = new a(this);
        this.g = (TitleBar) contentView.findViewById(R.id.tbTitle);
        this.g.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.g.setOnBackClickListener(this.j);
        View view = contentView;
        b bVar = new b(view, R.id.ivStartBubble, R.id.tvStartPoiName, R.id.tvStartPointSelected, R.id.btnConfirmStartPoint, this, 0);
        this.a = bVar;
        b bVar2 = new b(view, R.id.ivEndBubble, R.id.tvEndPoiName, R.id.tvEndPointSelected, R.id.btnConfirmEndPoint, this, 0);
        this.b = bVar2;
        Button button = this.b.c;
        button.setEnabled(false);
        button.setTextColor(getContext().getResources().getColor(R.color.f_c_1_a));
        button.setTextSize(0, getContext().getResources().getDimension(R.dimen.f_s_15));
        if (this.h == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            this.h = translateAnimation;
            this.h.setDuration(250);
            this.h.setInterpolator(new AccelerateInterpolator());
        }
        contentView.findViewById(R.id.mapBottomInteractiveView).setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        getContentView().postDelayed(new Runnable() {
            public final void run() {
                if (SelectRoadFromMapPage.this.getMapManager() != null) {
                    ((SelectRoadFromMapPresenter) SelectRoadFromMapPage.this.mPresenter).a(SelectRoadFromMapPage.this.getMapManager().getMapView().o(), true);
                }
            }
        }, 500);
    }

    public final void a() {
        getSuspendManager().b().getMapLayerDialogCustomActions().a = 1;
    }

    public final void b() {
        getContentView().post(new Runnable() {
            public final void run() {
                if (SelectRoadFromMapPage.this.e) {
                    SelectRoadFromMapPage.this.a.d.startAnimation(SelectRoadFromMapPage.this.h);
                } else {
                    SelectRoadFromMapPage.this.b.d.startAnimation(SelectRoadFromMapPage.this.h);
                }
                SelectRoadFromMapPage.this.h.startNow();
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public SelectRoadFromMapPresenter createPresenter() {
        return new SelectRoadFromMapPresenter(this);
    }

    public View getMapSuspendView() {
        return this.i.a();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnConfirmStartPoint) {
            SelectRoadFromMapPresenter selectRoadFromMapPresenter = (SelectRoadFromMapPresenter) this.mPresenter;
            selectRoadFromMapPresenter.f = AMapAppGlobal.getApplication().getString(R.string.map_selected_location);
            selectRoadFromMapPresenter.b = ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).getMapManager().getMapView().o();
            selectRoadFromMapPresenter.a(selectRoadFromMapPresenter.b, true);
            SelectRoadFromMapPage selectRoadFromMapPage = (SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage;
            selectRoadFromMapPage.a.c.setVisibility(8);
            selectRoadFromMapPage.a.b.setVisibility(0);
            Button button = selectRoadFromMapPage.b.c;
            button.setEnabled(true);
            button.setTextColor(selectRoadFromMapPage.getContext().getResources().getColor(R.color.f_c_1));
            button.setTextSize(0, selectRoadFromMapPage.getContext().getResources().getDimension(R.dimen.f_s_14));
            selectRoadFromMapPage.a.d.setVisibility(8);
            selectRoadFromMapPage.b.d.setVisibility(0);
            if (selectRoadFromMapPage.c == null) {
                eq eqVar = (eq) defpackage.esb.a.a.a(eq.class);
                if (eqVar != null) {
                    selectRoadFromMapPage.c = eqVar.a(selectRoadFromMapPage.d);
                    selectRoadFromMapPage.addOverlay(selectRoadFromMapPage.c);
                }
                selectRoadFromMapPage.e = false;
                ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).a(R.string.feedback_drag_map_to_select_end_point);
                selectRoadFromMapPresenter.f = null;
                selectRoadFromMapPresenter.a(((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).getMapManager().getMapView().o(), ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).e);
                ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).b();
                return;
            }
            selectRoadFromMapPage.c.clear();
            PointOverlayItem pointOverlayItem = new PointOverlayItem(GeoPoint.glGeoPoint2GeoPoint(selectRoadFromMapPage.d.c(selectRoadFromMapPage.d.al() / 2, selectRoadFromMapPage.d.am() / 2)));
            pointOverlayItem.mDefaultMarker = selectRoadFromMapPage.c.createMarker(R.drawable.bubble_start, 5);
            selectRoadFromMapPage.c.addItem(pointOverlayItem);
            selectRoadFromMapPage.e = false;
            ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).a(R.string.feedback_drag_map_to_select_end_point);
            selectRoadFromMapPresenter.f = null;
            selectRoadFromMapPresenter.a(((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).getMapManager().getMapView().o(), ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).e);
            ((SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage).b();
            return;
        }
        if (view.getId() == R.id.btnConfirmEndPoint) {
            SelectRoadFromMapPresenter selectRoadFromMapPresenter2 = (SelectRoadFromMapPresenter) this.mPresenter;
            selectRoadFromMapPresenter2.f = null;
            selectRoadFromMapPresenter2.c = ((SelectRoadFromMapPage) selectRoadFromMapPresenter2.mPage).getMapManager().getMapView().o();
            SelectRoadFromMapPage selectRoadFromMapPage2 = (SelectRoadFromMapPage) selectRoadFromMapPresenter2.mPage;
            selectRoadFromMapPage2.b.c.setVisibility(8);
            selectRoadFromMapPage2.b.b.setVisibility(0);
            if (!selectRoadFromMapPresenter2.b()) {
                POI createPOI = POIFactory.createPOI(selectRoadFromMapPresenter2.d, selectRoadFromMapPresenter2.b);
                POI createPOI2 = POIFactory.createPOI(selectRoadFromMapPresenter2.e, selectRoadFromMapPresenter2.c);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject(H5PageData.KEY_UC_START, createPOI);
                pageBundle.putObject("end", createPOI2);
                ((SelectRoadFromMapPage) selectRoadFromMapPresenter2.mPage).setResult(ResultType.OK, pageBundle);
                ((SelectRoadFromMapPage) selectRoadFromMapPresenter2.mPage).finish();
            }
        }
    }

    public final void a(@StringRes final int i2) {
        if (this.k == 0) {
            getContentView().post(new Runnable() {
                public final void run() {
                    View findViewById = SelectRoadFromMapPage.this.getContentView().findViewById(R.id.mapBottomInteractiveView);
                    SelectRoadFromMapPage.this.k = (findViewById.getBottom() - findViewById.getTop()) + 10;
                    ToastHelper.showLongToast(SelectRoadFromMapPage.this.getString(i2), 80, 0, SelectRoadFromMapPage.this.k);
                }
            });
        } else {
            ToastHelper.showLongToast(getString(i2), 80, 0, this.k);
        }
    }

    static /* synthetic */ void a(SelectRoadFromMapPage selectRoadFromMapPage, boolean z) {
        if (z) {
            selectRoadFromMapPage.getSuspendWidgetHelper().b(false).setVisibility(8);
        } else {
            selectRoadFromMapPage.getSuspendWidgetHelper().b(false).setVisibility(0);
        }
    }
}

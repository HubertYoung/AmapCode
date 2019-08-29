package com.autonavi.mine.measure.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.mine.measure.MeasureLineOverlay;
import com.autonavi.mine.measure.MeasurePointOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import java.util.Vector;

@OverlayPageProperty(overlays = {@OvProperty(clickable = false, moveToFocus = false, overlay = UvOverlay.SaveOverlay, visible = false)})
@PageAction("amap.basemap.action.measure_page")
public class MeasurePage extends AbstractBaseMapPage<cgo> {
    public MapManager a;
    public MeasurePointOverlay b;
    public MeasureLineOverlay c;
    public TextView d;
    public TextView e;
    private bty f;
    private cde g;
    private cgn h;
    private MeasurePointOverlay i;
    private boolean j = false;

    /* access modifiers changed from: private */
    /* renamed from: b */
    public cgo createPresenter() {
        return new cgo(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.measure_layout);
        View contentView = getContentView();
        this.g = getSuspendManager();
        this.a = getMapManager();
        if (this.g != null) {
            this.g.d().f();
            this.g.b().disableCustomView(256);
            this.g.b().disableView(1024);
        }
        this.h = new cgn(this);
        this.f = getMapView();
        if (this.a != null) {
            this.b = new MeasurePointOverlay(this.a.getMapView());
            addOverlay(this.b);
            this.i = new MeasurePointOverlay(this.a.getMapView());
            addOverlay(this.i);
            this.c = new MeasureLineOverlay(this.a.getMapView());
            addOverlay(this.c);
            this.i.setMoveToFocus(false);
            this.i.setOnItemClickListener(new OnItemClickListener<PointOverlayItem>() {
                public final /* synthetic */ void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                    PointOverlayItem pointOverlayItem = (PointOverlayItem) obj;
                    if (pointOverlayItem.mDefaultMarker == null || pointOverlayItem.mDefaultMarker.mID != cgm.a("bubble_start")) {
                        Vector<POI> vector = ((cgo) MeasurePage.this.mPresenter).a;
                        Vector<LineOverlayItem> vector2 = ((cgo) MeasurePage.this.mPresenter).b;
                        if (vector.size() > 0) {
                            if (vector.size() == 1) {
                                MeasurePage.a(MeasurePage.this);
                                return;
                            }
                            POI lastElement = vector.lastElement();
                            MeasurePage.this.b.removeItem(vector.size() - 1);
                            MeasurePage.this.c.removeItem(vector2.size() - 1);
                            vector.remove(vector.size() - 1);
                            vector2.remove(vector2.size() - 1);
                            POIOverlayItem pOIOverlayItem = new POIOverlayItem(vector.lastElement());
                            pOIOverlayItem.mDefaultMarker = MeasurePage.this.b.createMarker(cgm.a("measure_point_red"), 4);
                            MeasurePage.this.b.removeItem(vector.size() - 1);
                            MeasurePage.this.b.addItem(pOIOverlayItem);
                            cgo cgo = (cgo) MeasurePage.this.mPresenter;
                            if (cgo.a.size() > 0) {
                                cgo.c -= cgo.a(cgo.a.lastElement().getPoint(), lastElement.getPoint());
                            } else {
                                cgo.c = 0;
                            }
                            StringBuilder sb = new StringBuilder();
                            sb.append(cgo.c);
                            sb.append("m");
                            cgo.d = sb.toString();
                            if (cgo.c > 1000) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(cgo.c / 1000);
                                sb2.append(".");
                                sb2.append((cgo.c % 1000) / 100);
                                sb2.append("km");
                                cgo.d = sb2.toString();
                            }
                            MeasurePage.this.a();
                        }
                    }
                }
            });
        }
        contentView.findViewWithTag("measure_fragment_title").setId(Constant.MAP_TOP_INTERACTIVE_VIEW_ID);
        this.d = (TextView) contentView.findViewById(R.id.title_text_name);
        this.e = (TextView) contentView.findViewById(R.id.doconfirmmappoint);
        this.d.setText(R.string.click_start);
        this.e.setText(R.string.action_clear);
        this.e.setVisibility(0);
        this.e.setEnabled(false);
        this.e.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MeasurePage.a(MeasurePage.this);
            }
        });
        contentView.findViewById(R.id.title_btn_left).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                MeasurePage.this.finish();
            }
        });
    }

    public View getMapSuspendView() {
        return this.h.a();
    }

    public final void a() {
        Vector<POI> vector = ((cgo) this.mPresenter).a;
        this.i.clear();
        if (vector.size() == 1) {
            String string = AMapPageUtil.getAppContext().getString(R.string.starting_point);
            POIOverlayItem pOIOverlayItem = new POIOverlayItem(vector.lastElement());
            pOIOverlayItem.mDefaultMarker = this.i.createViewMarker(0, string, vector.lastElement(), 5);
            this.i.addItem(pOIOverlayItem);
            return;
        }
        if (vector.size() >= 2) {
            POIOverlayItem pOIOverlayItem2 = new POIOverlayItem(vector.firstElement());
            pOIOverlayItem2.mDefaultMarker = this.i.createMarker(cgm.a("bubble_start"), 5);
            this.i.addItem(pOIOverlayItem2);
            POIOverlayItem pOIOverlayItem3 = new POIOverlayItem(vector.lastElement());
            pOIOverlayItem3.mDefaultMarker = this.i.createViewMarker(1, ((cgo) this.mPresenter).d, vector.lastElement(), 5);
            this.i.addItem(pOIOverlayItem3);
        }
    }

    static /* synthetic */ void a(MeasurePage measurePage) {
        cgo cgo = (cgo) measurePage.mPresenter;
        cgo.c = 0;
        cgo.d = null;
        cgo.a.clear();
        cgo.b.clear();
        measurePage.d.setText(R.string.click_start);
        measurePage.e.setEnabled(false);
        measurePage.b.clear();
        measurePage.i.clear();
        measurePage.c.clear();
    }
}

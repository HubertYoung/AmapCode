package com.autonavi.minimap.drive.sticker.page;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay.OnFocusChangedListener;
import com.autonavi.minimap.drive.sticker.StickerTipView;
import com.autonavi.minimap.drive.sticker.overlay.StickersLineOverlay;
import com.autonavi.minimap.drive.sticker.overlay.StickersPointOverlay;
import com.autonavi.widget.ui.BalloonLayout;
import com.autonavi.widget.ui.CommonTips;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@PageAction("amap.drive.action.stickers")
public class StickersPage extends DriveBaseMapPage<dje> {
    public StickersPointOverlay a;
    public StickersPointOverlay b;
    public StickersLineOverlay c;
    public StickersLineOverlay d;
    public StickerTipView e;
    public CommonTips f;
    public View g;
    public View h;
    public a i;
    public boolean j = false;
    public String k;
    public bty l;
    private com.autonavi.minimap.drive.sticker.StickerTipView.a m = new com.autonavi.minimap.drive.sticker.StickerTipView.a() {
        public final void a(div div) {
            Message message = new Message();
            message.what = 900;
            message.obj = div;
            ((dje) StickersPage.this.mPresenter).c.sendMessage(message);
        }

        public final void b(div div) {
            String string = StickersPage.this.getString(R.string.car_scene_parking);
            GeoPoint geoPoint = new GeoPoint(div.c, div.d);
            Rect rect = new Rect(geoPoint.x - 100, geoPoint.y - 100, geoPoint.x + 100, geoPoint.y + 100);
            ael ael = new ael(string, geoPoint);
            ael.e = bbw.a(rect);
            bck bck = (bck) defpackage.esb.a.a.a(bck.class);
            if (bck != null) {
                bck.a(ael, 2);
            }
        }

        public final void c(div div) {
            POI createPOI = POIFactory.createPOI(div.b, new GeoPoint(div.c, div.d));
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.a(createPOI);
            }
        }
    };

    public static class a extends ccv {
        private ScaleView a;
        private IMapPage b;

        a(IMapPage iMapPage) {
            super(iMapPage.getContext());
            this.b = iMapPage;
            ccy suspendWidgetHelper = this.b.getSuspendWidgetHelper();
            if (suspendWidgetHelper != null) {
                addWidget(suspendWidgetHelper.a(), suspendWidgetHelper.c(), 1);
                suspendWidgetHelper.a(suspendWidgetHelper.n());
                addWidget(suspendWidgetHelper.n(), suspendWidgetHelper.q(), 3);
                suspendWidgetHelper.a(suspendWidgetHelper.d());
                suspendWidgetHelper.a(getSuspendView(), suspendWidgetHelper.d(), suspendWidgetHelper.e(), 3);
                a();
                addWidget(suspendWidgetHelper.l(), suspendWidgetHelper.m(), 6);
            }
        }

        private static void a(View view) {
            if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }

        public final void a() {
            ccy suspendWidgetHelper = this.b.getSuspendWidgetHelper();
            if (suspendWidgetHelper != null) {
                this.a = suspendWidgetHelper.f();
                if (this.a != null) {
                    a(this.a);
                    addWidget(this.a, suspendWidgetHelper.g(), 7);
                    this.a.setScaleStatus(0);
                    this.a.changeLogoStatus(true);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public dje createPresenter() {
        return new dje(this);
    }

    public View getMapSuspendView() {
        this.i = new a(this);
        return this.i.getSuspendView();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.route_stickers);
    }

    public final void a() {
        this.g.setVisibility(8);
        this.h.setVisibility(8);
    }

    public final void b() {
        if (this.a != null) {
            this.a.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        d();
    }

    public final void a(String str) {
        Handler handler = ((dje) this.mPresenter).c;
        String str2 = this.k;
        if (this.f.getVisibility() != 0 || str2 == null || !str2.equals(getString(R.string.tip_sticker_parking_suggest))) {
            handler.removeMessages(100);
            this.k = str;
            this.f.setTipText((CharSequence) this.k);
            this.f.setVisibility(0);
            handler.sendEmptyMessageDelayed(100, BalloonLayout.DEFAULT_DISPLAY_DURATION);
            return;
        }
        Message obtainMessage = handler.obtainMessage();
        obtainMessage.obj = str;
        obtainMessage.what = 700;
        handler.sendMessageDelayed(obtainMessage, 3500);
    }

    private void d() {
        if (getBottomTipsContainer() != null) {
            getBottomTipsContainer().dimissTips();
        }
    }

    public final void a(@NonNull final djg djg, boolean z) {
        boolean z2;
        String str = djg.b;
        String str2 = null;
        boolean z3 = true;
        if (!TextUtils.isEmpty(str)) {
            MapSharePreference mapSharePreference = new MapSharePreference((String) "Stickers");
            String stringValue = mapSharePreference.getStringValue("stickers_date", null);
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            if (TextUtils.isEmpty(stringValue) || !format.equalsIgnoreCase(stringValue)) {
                mapSharePreference.putStringValue("stickers_date", format);
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                a(str);
                djg.b = null;
            }
        }
        if (djg.c == 1) {
            str2 = "该城市即将开通此功能，敬请期待";
        } else if (djg.c == 2) {
            str2 = "附近还没有贴条记录";
        }
        if (!TextUtils.isEmpty(str2)) {
            a(str2);
            djg.c = 0;
        }
        if (djg.a == null || djg.a.size() == 0) {
            z3 = false;
        }
        if (z3) {
            List<div> list = djg.a;
            if (this.a != null) {
                this.a.clear();
            }
            if (this.c != null) {
                this.c.clear();
            }
            if (this.d != null) {
                this.d.clear();
            }
            if (this.b != null) {
                this.b.clear();
            }
            if (this.a != null) {
                this.a.setMinDisplayLevel(11);
                this.a.setOnFocusChangedListener(new OnFocusChangedListener<dja>() {
                    public final /* synthetic */ void onFocusChanged(boolean z, PointOverlay pointOverlay, Object obj) {
                        dja dja = (dja) obj;
                        if (!z || dja == null) {
                            StickersPage.d(StickersPage.this);
                            if (dja != null) {
                                djg.i = -1;
                            }
                            return;
                        }
                        StickersPage.this.a(djg, dja.a);
                    }
                });
            }
            d();
            int i2 = 0;
            for (div next : list) {
                POI a2 = next.a(i2);
                if (this.a != null) {
                    this.a.addItem(dja.a(a2));
                }
                ArrayList<diz> b2 = next.b(false);
                if (b2.size() > 0) {
                    Iterator<diz> it = b2.iterator();
                    while (it.hasNext()) {
                        this.c.addItem((LineOverlayItem) it.next());
                    }
                } else {
                    diz a3 = next.a(false);
                    if (a3 != null) {
                        this.c.addItem((LineOverlayItem) a3);
                    }
                }
                i2++;
            }
            if (z && getMapView() != null && getMapView().w() > 15 && isAlive()) {
                getMapView().W();
                getMapView().a(500, 15.0f, -9999, -9999, -9999, -9999);
            }
        }
    }

    public final void a(@NonNull djg djg, int i2) {
        List<div> list = djg.a;
        if (list != null && list.size() != 0 && i2 >= 0 && i2 < list.size()) {
            div div = list.get(i2);
            djg.i = i2;
            if (this.d != null) {
                this.d.clear();
            }
            ArrayList<diz> b2 = div.b(true);
            if (b2.size() > 0) {
                Iterator<diz> it = b2.iterator();
                while (it.hasNext()) {
                    this.d.addItem((LineOverlayItem) it.next());
                }
            } else {
                diz a2 = div.a(true);
                if (a2 != null) {
                    this.d.addItem((LineOverlayItem) a2);
                }
            }
            if (this.b != null) {
                this.b.clear();
            }
            POI a3 = div.a(i2);
            int iconId = a3.getIconId();
            if (a3.getPoiExtra() != null && a3.getPoiExtra().containsKey("big_icon_id")) {
                Serializable serializable = a3.getPoiExtra().get("big_icon_id");
                if (serializable != null && (serializable instanceof Integer)) {
                    iconId = ((Integer) serializable).intValue();
                }
            }
            if (this.b != null) {
                this.b.addItem(dja.a(a3.getPoint(), iconId));
                this.b.setFocus(0, false);
            }
            if (getMapView() != null) {
                getMapView().a((GLGeoPoint) new GeoPoint(div.c, div.d));
            }
            this.e.refreshView(i2, div);
            this.e.setTipsClickListener(this.m);
            StickerTipView stickerTipView = this.e;
            if (getBottomTipsContainer() != null) {
                getBottomTipsContainer().showTip(stickerTipView, 0, null);
            }
        }
    }

    static /* synthetic */ void d(StickersPage stickersPage) {
        if (stickersPage.d != null) {
            stickersPage.d.clear();
        }
        if (stickersPage.b != null) {
            stickersPage.b.clear();
        }
        stickersPage.d();
    }
}

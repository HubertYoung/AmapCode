package com.autonavi.map.suspend.refactor.zoom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.widget.LaterImageButton;
import com.autonavi.map.widget.LaterTouchListener;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.BalloonLayout;
import org.json.JSONException;
import org.json.JSONObject;

public final class ZoomViewPresenter implements OnClickListener {
    public cev a;
    public View b;
    public View c;
    Context d;
    public TextView e;
    public TextView f;
    public LaterImageButton g;
    public LaterImageButton h;
    public LaterTouchListener i = new LaterTouchListener() {
        Handler a = new Handler() {
            public final void handleMessage(Message message) {
                AnonymousClass1.this.a.removeCallbacks(AnonymousClass1.this.b);
                AnonymousClass1.this.a.removeCallbacks(AnonymousClass1.this.c);
                if (message.what == 1028) {
                    ZoomViewPresenter.this.b.setVisibility(8);
                    return;
                }
                if (message.what == 1280) {
                    ZoomViewPresenter.this.c.setVisibility(8);
                }
            }
        };
        Runnable b = new Runnable() {
            public final void run() {
                AnonymousClass1.this.a.obtainMessage(1028).sendToTarget();
            }
        };
        Runnable c = new Runnable() {
            public final void run() {
                AnonymousClass1.this.a.obtainMessage(1280).sendToTarget();
            }
        };

        public final void timeIsComing(View view) {
            ZoomType zoomType;
            ZoomType zoomType2;
            if (lh.a().orientation != 2) {
                int w = ZoomViewPresenter.this.n.getMapView().w();
                if (view.equals(ZoomViewPresenter.this.a.getZoomInBtn())) {
                    if (w < 17) {
                        try {
                            ((Vibrator) ZoomViewPresenter.this.d.getSystemService("vibrator")).vibrate(50);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                        if (w >= 11) {
                            ZoomViewPresenter.this.e.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_street));
                            zoomType2 = ZoomType.STREET;
                        } else if (w >= 8) {
                            ZoomViewPresenter.this.e.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_city));
                            zoomType2 = ZoomType.CITY;
                        } else if (w >= 4) {
                            ZoomViewPresenter.this.e.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_province));
                            zoomType2 = ZoomType.PROVINCE;
                        } else {
                            ZoomViewPresenter.this.e.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_china));
                            zoomType2 = ZoomType.COUNTRY;
                        }
                        ZoomViewPresenter.this.c.setVisibility(4);
                        this.a.removeCallbacks(this.c);
                        this.a.postDelayed(this.b, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                        ZoomViewPresenter.this.b.setVisibility(0);
                        ZoomViewPresenter.this.b.setTag(zoomType2);
                    }
                } else if (w > 4) {
                    try {
                        ((Vibrator) ZoomViewPresenter.this.d.getSystemService("vibrator")).vibrate(50);
                    } catch (SecurityException e2) {
                        e2.printStackTrace();
                    }
                    if (w <= 8) {
                        ZoomViewPresenter.this.f.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_china));
                        zoomType = ZoomType.COUNTRY;
                    } else if (w <= 11) {
                        ZoomViewPresenter.this.f.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_province));
                        zoomType = ZoomType.PROVINCE;
                    } else if (w <= 17) {
                        ZoomViewPresenter.this.f.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_city));
                        zoomType = ZoomType.CITY;
                    } else {
                        ZoomViewPresenter.this.f.setText(ZoomViewPresenter.this.d.getResources().getString(R.string.v4_zoom_tip_street));
                        zoomType = ZoomType.STREET;
                    }
                    ZoomViewPresenter.this.b.setVisibility(4);
                    this.a.removeCallbacks(this.b);
                    this.a.postDelayed(this.c, BalloonLayout.DEFAULT_DISPLAY_DURATION);
                    ZoomViewPresenter.this.c.setVisibility(0);
                    ZoomViewPresenter.this.c.setTag(zoomType);
                }
            }
        }

        public final void touchToZoom(View view) {
            Object obj;
            Object obj2;
            cct cct = (cct) ank.a(cct.class);
            if (cct != null && cct.a(ZoomViewPresenter.this.m.d().c())) {
                cdd.n().e(true);
            }
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(ZoomViewPresenter.this.n.getMapView().n());
            int i = ZoomViewPresenter.this.n.getMapView().s() ? 1 : 2;
            int j = ZoomViewPresenter.this.n.getMapView().j(false) + 1;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", j);
                jSONObject.put("from", ZoomViewPresenter.this.n.getMapView().w());
                if (glGeoPoint2GeoPoint == null) {
                    obj = "";
                } else {
                    obj = Double.valueOf(glGeoPoint2GeoPoint.getLatitude());
                }
                jSONObject.put("lat", obj);
                if (glGeoPoint2GeoPoint == null) {
                    obj2 = "";
                } else {
                    obj2 = Double.valueOf(glGeoPoint2GeoPoint.getLongitude());
                }
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, obj2);
                jSONObject.put("status", i);
                jSONObject.put(TrafficUtil.KEYWORD, 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (view.equals(ZoomViewPresenter.this.g)) {
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REPORT_ZOOMIN, jSONObject);
                if (ZoomViewPresenter.this.o != null) {
                    ZoomViewPresenter.this.o.onZoomInClick();
                }
                ZoomViewPresenter.j(ZoomViewPresenter.this);
                ZoomViewPresenter.k(ZoomViewPresenter.this);
                ZoomViewPresenter.c();
                return;
            }
            LogManager.actionLogV2("P00001", LogConstant.MAIN_MAP_REPORT_ZOOMOUT, jSONObject);
            if (ZoomViewPresenter.this.o != null) {
                ZoomViewPresenter.this.o.onZoomOutClick();
            }
            ZoomViewPresenter.l(ZoomViewPresenter.this);
            ZoomViewPresenter.k(ZoomViewPresenter.this);
            ZoomViewPresenter.c();
        }
    };
    Handler j = new Handler() {
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 101 && i == 110) {
                ZoomViewPresenter.this.a();
            }
        }
    };
    g k = new g() {
        public final void updateZoomButtonState(bty bty) {
            ZoomViewPresenter.this.a();
        }

        public final void updateZoomViewVisibility() {
            ZoomViewPresenter.this.b();
        }
    };
    int l = 0;
    /* access modifiers changed from: private */
    public cde m;
    /* access modifiers changed from: private */
    public MapManager n;
    /* access modifiers changed from: private */
    public a o;

    enum ZoomType {
        STREET(17),
        CITY(11),
        PROVINCE(8),
        COUNTRY(4);
        
        int e;

        private ZoomType(int i) {
            this.e = 0;
            this.e = i;
        }
    }

    public interface a {
        void onZoomInClick();

        void onZoomOutClick();
    }

    static /* synthetic */ void c() {
    }

    public ZoomViewPresenter(Context context, cde cde, MapManager mapManager) {
        this.m = cde;
        this.n = mapManager;
        cdd.n().a(this.k);
        this.d = context;
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.zoomInTip) {
            ZoomType zoomType = (ZoomType) this.b.getTag();
            if (zoomType != null) {
                cct cct = (cct) ank.a(cct.class);
                if (cct != null && cct.a(this.m.d().c())) {
                    cdd.n().e(true);
                }
                this.b.setTag(null);
                this.b.setVisibility(8);
                this.c.setVisibility(8);
                this.n.getMapView().c((float) zoomType.e);
            }
            return;
        }
        if (id == R.id.zoomOutTip) {
            ZoomType zoomType2 = (ZoomType) this.c.getTag();
            if (zoomType2 != null) {
                cct cct2 = (cct) ank.a(cct.class);
                if (cct2 != null && cct2.a(this.m.d().c())) {
                    cdd.n().e(true);
                }
                this.c.setTag(null);
                this.b.setVisibility(8);
                this.c.setVisibility(8);
                this.n.getMapView().c((float) zoomType2.e);
            }
        }
    }

    public final void a() {
        bty mapView = this.n != null ? this.n.getMapView() : null;
        if (mapView != null) {
            boolean z = false;
            this.g.setEnabled(mapView.w() < mapView.l());
            LaterImageButton laterImageButton = this.h;
            if (mapView.w() > mapView.m()) {
                z = true;
            }
            laterImageButton.setEnabled(z);
        }
    }

    public final void b() {
        if (this.a != null) {
            this.a.setVisibility(bin.a.o(UploadConstants.STATUS_TASK_BY_PUSH) ? this.l : 8, true);
        }
    }

    static /* synthetic */ void j(ZoomViewPresenter zoomViewPresenter) {
        if (zoomViewPresenter.m.b() == null || zoomViewPresenter.m.b().isSuspendActionEnable(1)) {
            zoomViewPresenter.n.getMapView().x();
        }
    }

    static /* synthetic */ void k(ZoomViewPresenter zoomViewPresenter) {
        if (zoomViewPresenter.j != null) {
            zoomViewPresenter.j.removeMessages(110);
            zoomViewPresenter.j.sendEmptyMessageDelayed(110, 2000);
        }
    }

    static /* synthetic */ void l(ZoomViewPresenter zoomViewPresenter) {
        if (zoomViewPresenter.m.b() == null || zoomViewPresenter.m.b().isSuspendActionEnable(2)) {
            zoomViewPresenter.n.getMapView().y();
        }
    }
}

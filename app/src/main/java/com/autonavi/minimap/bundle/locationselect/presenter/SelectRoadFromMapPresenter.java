package com.autonavi.minimap.bundle.locationselect.presenter;

import android.graphics.Rect;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.locationselect.page.SelectRoadFromMapPage;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

public final class SelectRoadFromMapPresenter extends AbstractBaseMapPagePresenter<SelectRoadFromMapPage> {
    boolean a;
    public GeoPoint b;
    public GeoPoint c;
    public String d = null;
    public String e = null;
    public String f = null;
    private a g;
    private int h = -1;
    private Float i = null;
    private Float j = null;
    private Float k = null;
    private GeoPoint l = null;
    private Integer m = null;
    private Integer n = null;

    static class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private boolean mIsStartPoint;
        private WeakReference<SelectRoadFromMapPresenter> mRefPresenter;

        /* synthetic */ ReverseGeocodeListener(SelectRoadFromMapPresenter selectRoadFromMapPresenter, boolean z, byte b) {
            this(selectRoadFromMapPresenter, z);
        }

        private ReverseGeocodeListener(SelectRoadFromMapPresenter selectRoadFromMapPresenter, boolean z) {
            this.mRefPresenter = new WeakReference<>(selectRoadFromMapPresenter);
            this.mIsStartPoint = z;
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            String desc = (reverseGeocodeResponser.errorCode == -1 || reverseGeocodeResponser.errorCode == 500 || reverseGeocodeResponser.errorCode == 7) ? null : reverseGeocodeResponser.getDesc();
            if (this.mRefPresenter != null && this.mRefPresenter.get() != null) {
                SelectRoadFromMapPresenter.a((SelectRoadFromMapPresenter) this.mRefPresenter.get(), desc, this.mIsStartPoint);
            }
        }

        public void error(Throwable th, boolean z) {
            if (this.mRefPresenter != null && this.mRefPresenter.get() != null) {
                SelectRoadFromMapPresenter.a((SelectRoadFromMapPresenter) this.mRefPresenter.get(), null, this.mIsStartPoint);
            }
        }
    }

    public SelectRoadFromMapPresenter(SelectRoadFromMapPage selectRoadFromMapPage) {
        super(selectRoadFromMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((SelectRoadFromMapPage) this.mPage).getArguments();
        this.h = arguments.getInt("zoom_level", -1);
        POI poi = (POI) arguments.getObject(H5PageData.KEY_UC_START);
        if (poi != null) {
            GeoPoint point = poi.getPoint();
            if (point != null) {
                Rect a2 = ags.a(((SelectRoadFromMapPage) this.mPage).getActivity());
                int d2 = ags.d(((SelectRoadFromMapPage) this.mPage).getContext());
                bty mapView = ((SelectRoadFromMapPage) this.mPage).getMapView();
                if (mapView != null) {
                    int height = (a2.height() / 2) - d2;
                    mapView.b(point.x, point.y, a2.width() / 2, height);
                }
            }
        }
        if (this.h > 0) {
            ((SelectRoadFromMapPage) this.mPage).getMapView().f((float) this.h);
        }
        ((SelectRoadFromMapPage) this.mPage).a(R.string.feedback_drag_map_to_select_start_point);
        ((SelectRoadFromMapPage) this.mPage).getSuspendManager().d().a((a) new a() {
            public final void a() {
                SelectRoadFromMapPresenter.this.a = true;
            }
        });
        bty mapView2 = ((SelectRoadFromMapPage) this.mPage).getMapView();
        if (mapView2 != null) {
            this.j = Float.valueOf(mapView2.v());
            this.k = Float.valueOf(mapView2.J());
            this.i = Float.valueOf(mapView2.I());
            this.l = mapView2.o();
            this.m = Integer.valueOf(mapView2.h());
            this.n = Integer.valueOf(mapView2.i());
            mapView2.b(mapView2.al() / 2, mapView2.am() / 2);
        }
    }

    public final boolean onMapMotionStop() {
        this.f = null;
        ((SelectRoadFromMapPage) this.mPage).getContentView().postDelayed(new Runnable() {
            public final void run() {
                SelectRoadFromMapPresenter.this.a(((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).getMapManager().getMapView().o(), ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).e);
                ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).b();
            }
        }, !this.a ? 0 : 500);
        this.a = false;
        return true;
    }

    public final void a(GeoPoint geoPoint, boolean z) {
        if (((SelectRoadFromMapPage) this.mPage).isAlive()) {
            c();
            this.g = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, new ReverseGeocodeListener(this, z, 0));
        }
    }

    public final void onStart() {
        super.onStart();
        ((SelectRoadFromMapPage) this.mPage).a();
        SelectRoadFromMapPage selectRoadFromMapPage = (SelectRoadFromMapPage) this.mPage;
        cde suspendManager = selectRoadFromMapPage.getSuspendManager();
        if (suspendManager != null) {
            suspendManager.c().a(selectRoadFromMapPage.f);
        }
    }

    public final void onStop() {
        super.onStop();
        ((SelectRoadFromMapPage) this.mPage).a();
        SelectRoadFromMapPage selectRoadFromMapPage = (SelectRoadFromMapPage) this.mPage;
        cde suspendManager = selectRoadFromMapPage.getSuspendManager();
        if (suspendManager != null) {
            suspendManager.c().b(selectRoadFromMapPage.f);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        c();
        SelectRoadFromMapPage selectRoadFromMapPage = (SelectRoadFromMapPage) this.mPage;
        if (selectRoadFromMapPage.c != null) {
            selectRoadFromMapPage.c.clear();
        }
        cde suspendManager = ((SelectRoadFromMapPage) this.mPage).getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().a((a) null);
        }
        bty mapView = ((SelectRoadFromMapPage) this.mPage).getMapView();
        if (mapView != null) {
            if (this.l != null) {
                mapView.a(this.l.x, this.l.y);
            }
            if (this.i != null) {
                mapView.e(this.i.floatValue());
            }
            if (this.j != null) {
                mapView.d(this.j.floatValue());
            }
            if (this.k != null) {
                mapView.g(this.k.floatValue());
            }
            if (this.m != null && this.n != null) {
                mapView.b(this.m.intValue(), this.n.intValue());
            }
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    private void c() {
        if (this.g != null) {
            this.g.cancel();
            this.g = null;
        }
    }

    public final void a() {
        ((SelectRoadFromMapPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
        ((SelectRoadFromMapPage) this.mPage).finish();
    }

    public final boolean b() {
        final Callback callback;
        PageBundle arguments = ((SelectRoadFromMapPage) this.mPage).getArguments();
        if (!(arguments == null || !arguments.getBoolean(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, false) || this.b == null || this.c == null)) {
            try {
                callback = (Callback) arguments.getObject("resultCallback");
            } catch (Exception e2) {
                e2.printStackTrace();
                callback = null;
            }
            if (callback != null) {
                MapManager mapManager = DoNotUseTool.getMapManager();
                if (mapManager != null) {
                    final GeoPoint clone = this.b.clone();
                    GeoPoint clone2 = this.c.clone();
                    final String str = this.d;
                    final String str2 = this.e;
                    cfc a2 = cfc.a();
                    final GeoPoint geoPoint = clone2;
                    AnonymousClass3 r5 = new a() {
                        public final void onPrepare() {
                        }

                        public final void onFailure() {
                            if (SelectRoadFromMapPresenter.this.mPage != null && ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).isAlive()) {
                                callback.callback("");
                                aho.a(new Runnable() {
                                    public final void run() {
                                        if (SelectRoadFromMapPresenter.this.mPage != null && ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).isAlive()) {
                                            ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).finish();
                                        }
                                    }
                                });
                            }
                        }

                        public final void onScreenShotFinish(String str) {
                            if (SelectRoadFromMapPresenter.this.mPage != null && ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).isAlive()) {
                                JSONObject jSONObject = new JSONObject();
                                if (str == null) {
                                    str = "";
                                }
                                try {
                                    jSONObject.put("filePath", str);
                                    jSONObject.put(DictionaryKeys.CTRLXY_X, clone.getLongitude());
                                    jSONObject.put(DictionaryKeys.CTRLXY_Y, clone.getLatitude());
                                    jSONObject.put("poiName", str);
                                    jSONObject.put("x2", geoPoint.getLongitude());
                                    jSONObject.put("y2", geoPoint.getLatitude());
                                    jSONObject.put("poiName2", str2);
                                    callback.callback(jSONObject.toString());
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            if (SelectRoadFromMapPresenter.this.mPage != null && ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).isAlive()) {
                                                ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).finish();
                                            }
                                        }
                                    });
                                } catch (Exception unused) {
                                    callback.callback("");
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            if (SelectRoadFromMapPresenter.this.mPage != null && ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).isAlive()) {
                                                ((SelectRoadFromMapPage) SelectRoadFromMapPresenter.this.mPage).finish();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    };
                    cfd a3 = cfd.a(mapManager.getMapView(), clone2, arguments.getInt("w", 0), arguments.getInt("h", 0));
                    a3.i = true;
                    a2.a(mapManager, r5, a3);
                    return true;
                }
            }
        }
        return false;
    }

    static /* synthetic */ void a(SelectRoadFromMapPresenter selectRoadFromMapPresenter, String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            selectRoadFromMapPresenter.f = str;
        }
        SelectRoadFromMapPage selectRoadFromMapPage = (SelectRoadFromMapPage) selectRoadFromMapPresenter.mPage;
        String str2 = selectRoadFromMapPresenter.f;
        if (selectRoadFromMapPage.getContentView() != null) {
            selectRoadFromMapPage.getContentView().post(new Runnable(str2, z) {
                final /* synthetic */ String a;
                final /* synthetic */ boolean b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void run() {
                    if (!TextUtils.isEmpty(this.a)) {
                        if (this.b) {
                            SelectRoadFromMapPage.this.a.a.setText(this.a);
                            return;
                        }
                        SelectRoadFromMapPage.this.b.a.setText(this.a);
                    }
                }
            });
        }
        if (z) {
            selectRoadFromMapPresenter.d = str;
        } else {
            selectRoadFromMapPresenter.e = str;
        }
    }
}

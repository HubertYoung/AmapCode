package com.autonavi.minimap.intent;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.MapApplication;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.maphome.data.PoiList;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.server.ShortURLResponser;
import java.net.UnknownHostException;
import java.util.List;

public abstract class BaseIntent {
    protected Activity a;
    protected Intent b;
    public Intent c = null;
    protected String d;
    public String e;
    public boolean f = false;
    protected boolean g = false;
    protected IRouteResultData h;
    protected IRouteResultData i;
    protected List<String> j = null;
    protected c k = null;
    protected dmf l = null;
    protected boolean m = false;
    public cqd n = null;
    Handler o = new Handler();
    protected d p = null;
    protected a q = null;
    protected b r = null;
    private ProgressDlg s;

    public class MyShortUrlListener implements Callback<ShortURLResponser> {
        public MyShortUrlListener() {
        }

        public void callback(ShortURLResponser shortURLResponser) {
            if (shortURLResponser != null) {
                if ("1".equals(BaseIntent.this.j.get(0))) {
                    BaseIntent.this.a(shortURLResponser.value_url);
                } else {
                    BaseIntent.this.a(shortURLResponser.transfer_url);
                }
            }
        }

        public void error(Throwable th, boolean z) {
            BaseIntent.this.e();
        }
    }

    class ReverseGeocodeListener implements Callback<ReverseGeocodeResponser> {
        private final GeoPoint mGeoPoint;

        public ReverseGeocodeListener(GeoPoint geoPoint) {
            this.mGeoPoint = geoPoint;
        }

        public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
            BaseIntent.this.a();
            if (BaseIntent.this.g || reverseGeocodeResponser == null) {
                BaseIntent.this.g = false;
                return;
            }
            String desc = reverseGeocodeResponser.getDesc();
            if (TextUtils.isEmpty(desc)) {
                desc = MapApplication.getApplication().getString(R.string.my_location);
            }
            POI createPOI = POIFactory.createPOI(desc, this.mGeoPoint);
            createPOI.getPoint().x = this.mGeoPoint.x;
            createPOI.getPoint().y = this.mGeoPoint.y;
            createPOI.setIconId(R.drawable.b_poi);
            if (BaseIntent.this.n != null) {
                BaseIntent.this.n.a(createPOI, 13);
            }
            BaseIntent.this.f();
        }

        public void error(Throwable th, boolean z) {
            BaseIntent.this.a();
            if (BaseIntent.this.g) {
                BaseIntent.this.g = false;
            } else {
                BaseIntent.this.e();
            }
        }
    }

    public class a {
        public POI a = null;
        public int b = 17;

        public a() {
        }

        public final void a() {
            if (BaseIntent.this.n != null) {
                BaseIntent.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        BaseIntent.this.n.a(a.this.a, a.this.b);
                        BaseIntent.this.f();
                    }
                }, 100);
            }
        }
    }

    public class b {
        public PoiList a = null;

        public b() {
        }

        public final void a() {
            if (BaseIntent.this.n != null) {
                BaseIntent.this.o.postDelayed(new Runnable() {
                    public final void run() {
                        BaseIntent.this.n.a(b.this.a);
                        BaseIntent.this.f();
                    }
                }, 100);
            }
        }
    }

    public class c {
        public String a = null;

        public c() {
        }
    }

    public class d {
        public String a = "";
        public GeoPoint b = new GeoPoint(0, 0);
        public String c = "";
        public GeoPoint d = new GeoPoint(0, 0);
        public String e;

        public d() {
        }

        public final void a() {
            POI createPOI = POIFactory.createPOI(BaseIntent.this.p.a, BaseIntent.this.p.b);
            POI createPOI2 = POIFactory.createPOI(BaseIntent.this.p.c, BaseIntent.this.p.d);
            RouteType routeType = RouteType.CAR;
            if (BaseIntent.this.p.e.equals(UploadQueueMgr.MSGTYPE_REALTIME)) {
                routeType = RouteType.BUS;
            } else if (BaseIntent.this.p.e.equals("d")) {
                routeType = RouteType.CAR;
            } else if (BaseIntent.this.p.e.equals("w")) {
                routeType = RouteType.ONFOOT;
            }
            bax bax = (bax) defpackage.esb.a.a.a(bax.class);
            if (bax != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putSerializable("fromPoi", createPOI);
                pageBundle.putSerializable("toPoi", createPOI2);
                pageBundle.putObject("routeType", routeType);
                bax.a(pageBundle);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(String str);

    public abstract boolean c();

    public BaseIntent(Activity activity, Intent intent) {
        this.a = activity;
        this.b = intent;
        if (this.a != null && this.b != null) {
            this.d = this.b.getAction();
            this.e = this.b.getDataString();
            this.f = false;
        }
    }

    /* access modifiers changed from: protected */
    public final void a() {
        if (this.s != null) {
            this.s.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void b() {
        this.f = false;
        this.h = null;
        this.l = null;
        this.j = null;
        this.i = null;
    }

    public final void a(cqd cqd) {
        this.n = cqd;
    }

    public boolean d() {
        if (!this.f) {
            return true;
        }
        this.f = false;
        if (this.p != null) {
            this.p.a();
            return true;
        } else if (this.k != null) {
            c cVar = this.k;
            BaseIntent.this.o.postDelayed(new Runnable() {
                public final void run() {
                    if (AMapPageUtil.getPageContext() != null) {
                        ael ael = new ael(c.this.a, DoNotUseTool.getMapManager().getMapView().H());
                        bck bck = (bck) defpackage.esb.a.a.a(bck.class);
                        if (bck != null) {
                            bck.a(ael, -1);
                        }
                    }
                }
            }, 100);
            return true;
        } else if (this.q != null) {
            this.q.a();
            return true;
        } else if (this.r != null) {
            this.r.a();
            return true;
        } else if (this.l != null) {
            GeoPoint geoPoint = this.l.a;
            final com.autonavi.common.Callback.a reverseGeocodeResult = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, new ReverseGeocodeListener(geoPoint));
            String str = this.l.b;
            a();
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            this.g = false;
            this.s = new ProgressDlg(this.a, str, "");
            this.s.setCancelable(true);
            this.s.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    BaseIntent.this.g = true;
                    if (reverseGeocodeResult != null) {
                        reverseGeocodeResult.cancel();
                    }
                }
            });
            this.s.show();
            return true;
        } else if (this.h != null) {
            final POI fromPOI = this.h.getFromPOI();
            final POI toPOI = this.h.getToPOI();
            atb atb = (atb) defpackage.esb.a.a.a(atb.class);
            if (atb != null) {
                atb.c().a(this.a, fromPOI, toPOI, this.h.getMethod(), atb.g(), new axa() {
                    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
                        if (iRouteResultData != null) {
                            BaseIntent.this.h = iRouteResultData;
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("key_result", iRouteResultData);
                            pageBundle.putInt("key_type", RouteType.BUS.getValue());
                            if (BaseIntent.this.c != null) {
                                pageBundle.putBoolean("voice_process", BaseIntent.this.c.getBooleanExtra("voice_process", false));
                                pageBundle.putString("voice_keyword", BaseIntent.this.c.getStringExtra("voice_keyword"));
                            }
                            bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                            if (bax != null) {
                                bax.b(pageBundle);
                            }
                        }
                    }

                    public final void a(RouteType routeType, int i, String str) {
                        if (i == 2) {
                            BaseIntent.this.a(fromPOI, toPOI);
                            ToastHelper.showToast(BaseIntent.this.a.getString(R.string.act_fromto_onfoot_suggest));
                            return;
                        }
                        ToastHelper.showLongToast(str);
                    }

                    public final void a(Throwable th, boolean z) {
                        if (z) {
                            ToastHelper.showLongToast(BaseIntent.this.a.getString(R.string.tip_no_route_result));
                        } else if (th instanceof UnknownHostException) {
                            ToastHelper.showLongToast(BaseIntent.this.a.getString(R.string.network_error_message));
                        } else {
                            ToastHelper.showLongToast(BaseIntent.this.a.getString(R.string.tip_no_route_result));
                        }
                    }
                });
            }
            return true;
        } else if (this.i != null) {
            a(this.i.getFromPOI(), this.i.getToPOI());
            return true;
        } else if (this.j == null) {
            return false;
        } else {
            bnw.a(this.j.get(0), this.j.get(1), "", new MyShortUrlListener());
            return true;
        }
    }

    public final void f() {
        if (this.n != null) {
            this.n.a(1);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(POI poi, POI poi2) {
        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
        if (bax != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_poi_start", poi);
            pageBundle.putObject("bundle_key_poi_end", poi2);
            pageBundle.putBoolean("bundle_key_auto_route", true);
            pageBundle.putInt("key_type", RouteType.ONFOOT.getValue());
            if (this.c != null) {
                pageBundle.putBoolean("voice_process", this.c.getBooleanExtra("voice_process", false));
                pageBundle.putString("voice_keyword", this.c.getStringExtra("voice_keyword"));
            }
            bax.b(pageBundle);
        }
    }

    public final void e() {
        this.f = false;
        b();
        if (this.n != null) {
            this.n.a(0);
        }
    }
}

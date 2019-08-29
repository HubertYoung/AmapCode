package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bicycle.param.ShareBikeRideStateRequest;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.File;
import java.util.ArrayList;

/* renamed from: eht reason: default package */
/* compiled from: ShareBikeUtils */
public final class eht {
    private static boolean a = false;
    private static eho b = null;
    private static final Object c = new Object();
    private static ArrayList<bkf> d = new ArrayList<>();
    /* access modifiers changed from: private */
    public static boolean e = true;
    /* access modifiers changed from: private */
    public static a f;

    /* renamed from: eht$a */
    /* compiled from: ShareBikeUtils */
    public interface a {
        void a();
    }

    public static boolean a(bid bid) {
        return bid != null && bid.isAlive();
    }

    public static String a(GeoPoint geoPoint) {
        if (geoPoint != null) {
            li a2 = li.a();
            if (a2 != null) {
                lj b2 = a2.b(geoPoint.x, geoPoint.y);
                if (b2 != null) {
                    return String.valueOf(b2.i);
                }
            }
        }
        return "";
    }

    public static String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(".png");
        String sb2 = sb.toString();
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb3 = new StringBuilder("autonavi");
            sb3.append(File.separator);
            sb3.append("iconconf");
            File file = new File(externalStorageDirectory, sb3.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(file.getAbsolutePath());
            sb4.append("/");
            sb4.append(sb2);
            str2 = sb4.toString();
        }
        return str2;
    }

    public static boolean a() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return false;
        }
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append("iconconf");
        return new File(externalStorageDirectory, sb.toString()).exists();
    }

    public static void a(a aVar) {
        f = aVar;
    }

    public static void a(String str, String str2) {
        a(str, str2, 0, 0, false);
    }

    public static void a(String str, String str2, int i, int i2) {
        a(str, str2, i, i2, true);
    }

    private static void a(String str, final String str2, int i, int i2, boolean z) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            AnonymousClass1 r0 = new bkf() {
                String a = str2;

                public final void onPrepareLoad(Drawable drawable) {
                }

                public final void onBitmapLoaded(final Bitmap bitmap, LoadedFrom loadedFrom) {
                    ahn.b().execute(new Runnable() {
                        public final void run() {
                            boolean a2 = aha.a(bitmap, eht.a(str2), CompressFormat.PNG);
                            if (eht.e) {
                                ehs.a(a2);
                                eht.e = a2;
                                if (TextUtils.equals(AnonymousClass1.this.a, "scan_btn_normal")) {
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            if (eht.f != null) {
                                                eht.f.a();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }

                public final void onBitmapFailed(Drawable drawable) {
                    eht.e = false;
                    ehs.a(false);
                }
            };
            d.add(r0);
            if (z) {
                ImageLoader.a(AMapPageUtil.getAppContext()).a(str).a(agn.a(AMapPageUtil.getAppContext(), (float) i), agn.a(AMapPageUtil.getAppContext(), (float) i2)).a((bkf) r0);
            } else {
                ImageLoader.a(AMapPageUtil.getAppContext()).a(str).a((bkf) r0);
            }
        }
    }

    public static void b() {
        if (d != null) {
            d.clear();
        }
    }

    public static boolean c() {
        boolean equalsIgnoreCase;
        synchronized (c) {
            equalsIgnoreCase = "true".equalsIgnoreCase(ehs.b("share_bike_unlocking_status_id"));
        }
        return equalsIgnoreCase;
    }

    public static void d() {
        synchronized (c) {
            b = null;
        }
    }

    public static void a(long j) {
        StringBuilder sb = new StringBuilder("ShareBikeUtils startOfoCountdown ");
        sb.append(j);
        sb.append("秒");
        eao.a((String) "ofoCountDown", sb.toString());
        if (j > 0) {
            synchronized (c) {
                if (b == null) {
                    ehs.a((int) j);
                    eho eho = new eho(j);
                    b = eho;
                    eho.b();
                }
            }
        }
    }

    public static void e() {
        StringBuilder sb = new StringBuilder("ShareBikeUtils stopOfoCountdown timer = ");
        sb.append(b);
        eao.a((String) "ofoCountDown", sb.toString());
        synchronized (c) {
            if (b != null) {
                b.c();
                b = null;
            } else {
                ehs.a((String) "share_bike_unlocking_status_id", (String) "false");
            }
        }
    }

    public static void a(ehp ehp) {
        synchronized (c) {
            if (b != null) {
                eho eho = b;
                if (ehp != null && !eho.a.contains(ehp)) {
                    eho.a.add(ehp);
                }
            }
        }
    }

    public static void b(ehp ehp) {
        synchronized (c) {
            if (b != null) {
                eho eho = b;
                if (ehp != null) {
                    eho.a.remove(ehp);
                }
            }
        }
    }

    public static void a(final boolean z) {
        aho.a(new Runnable() {
            final /* synthetic */ boolean a = true;

            public final void run() {
                if (Boolean.parseBoolean(ehs.b("share_bike_riding_status_id")) || this.a) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    double d = 0.0d;
                    double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
                    if (latestPosition != null) {
                        d = latestPosition.getLatitude();
                    }
                    egj.a().a(ehs.b("share_bike_cp_source"), (float) longitude, (float) d, eht.a(latestPosition), ehs.b("share_bike_order_id"));
                    egj.a().a(true);
                    egx.a().a((egw) ehj.a());
                    String b2 = ehs.b("share_bike_order_id");
                    if (!TextUtils.isEmpty(b2)) {
                        String b3 = ehl.a(AMapPageUtil.getAppContext()).b();
                        if (!TextUtils.equals(b2, b3) && !TextUtils.isEmpty(b3)) {
                            ehl.a(AMapPageUtil.getAppContext()).b(b3);
                        }
                        if (!ehl.a(AMapPageUtil.getAppContext()).a) {
                            ehl.a(AMapPageUtil.getAppContext()).a(b2);
                        }
                        if (!z) {
                            ehl.a(AMapPageUtil.getAppContext()).a();
                        }
                    }
                }
                egx.a().a((egw) egb.a());
            }
        });
    }

    public static void g() {
        aho.a(new Runnable() {
            public final void run() {
                GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                double d = 0.0d;
                double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
                if (latestPosition != null) {
                    d = latestPosition.getLatitude();
                }
                egj.a().a(ehs.b("share_bike_cp_source"), (float) longitude, (float) d, eht.a(latestPosition), ehs.b("share_bike_order_id"));
                egj.a().a(true);
                egx.a().a((egw) ehj.a());
                String b = ehs.b("share_bike_order_id");
                if (!TextUtils.isEmpty(b)) {
                    String b2 = ehl.a(AMapPageUtil.getAppContext()).b();
                    if (!TextUtils.equals(b, b2) && !TextUtils.isEmpty(b2)) {
                        ehl.a(AMapPageUtil.getAppContext()).b(b2);
                    }
                    ehl.a(AMapPageUtil.getAppContext()).a(b);
                }
                egx.a().a((egw) egb.a());
            }
        });
    }

    public static boolean h() {
        return a;
    }

    public static void b(boolean z) {
        a = z;
    }

    public static void b(String str, String str2) {
        final PageBundle pageBundle = new PageBundle();
        pageBundle.putString("bundle_orderid_key", str);
        pageBundle.putString("bundle_cpsource_key", str2);
        pageBundle.putString("bundle_key_back_page", "page_go_back_last_page");
        StringBuilder sb = new StringBuilder("gotoShareBikeTrackPage orderId:");
        sb.append(str);
        sb.append(", cpSource:");
        sb.append(str2);
        eao.a((String) "sharebike", sb.toString());
        if (TextUtils.isEmpty(str)) {
            a(pageBundle);
            return;
        }
        eab b2 = eaa.a().b(str);
        if (b2 != null) {
            AMapPageUtil.getAppContext();
            final RideTraceHistory a2 = ees.a(bsp.a().a(b2.c));
            if (a2 != null) {
                aho.a(new Runnable() {
                    final /* synthetic */ boolean c = false;

                    public final void run() {
                        pageBundle.putObject("data", a2);
                        bid pageContext = AMapPageUtil.getPageContext();
                        if (pageContext == null) {
                            eao.a((String) "Amap#", (String) "gotoShareBikeTrackPage context is null !!");
                        } else if (this.c) {
                            pageContext.startPageForResult(ShareRidingFinishPage.class, pageBundle, 48);
                        } else {
                            pageContext.startPage(ShareRidingFinishPage.class, pageBundle);
                        }
                    }
                });
            } else {
                a(pageBundle);
            }
        } else {
            a(pageBundle);
        }
    }

    private static void a(final PageBundle pageBundle) {
        aho.a(new Runnable() {
            final /* synthetic */ boolean a = false;

            public final void run() {
                ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_bike_no_refer_order));
                if (this.a) {
                    AMapPageUtil.getPageContext().startPageForResult(ShareRidingFinishPage.class, pageBundle, 48);
                } else {
                    AMapPageUtil.getPageContext().startPage(ShareRidingFinishPage.class, pageBundle);
                }
            }
        });
    }

    public static void f() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        double d2 = 0.0d;
        double longitude = latestPosition == null ? 0.0d : latestPosition.getLongitude();
        if (latestPosition != null) {
            d2 = latestPosition.getLatitude();
        }
        String a2 = a(latestPosition);
        String b2 = ehs.b("share_bike_order_id");
        float f2 = (float) longitude;
        float f3 = (float) d2;
        egj.a().a(ehs.b("share_bike_cp_source"), f2, f3, a2, b2);
        ShareBikeRideStateRequest shareBikeRideStateRequest = new ShareBikeRideStateRequest();
        shareBikeRideStateRequest.b = ehs.b("share_bike_cp_source");
        shareBikeRideStateRequest.c = String.valueOf(f2);
        shareBikeRideStateRequest.d = String.valueOf(f3);
        shareBikeRideStateRequest.e = a2;
        shareBikeRideStateRequest.f = b2;
        egu.a(shareBikeRideStateRequest, (com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a) new com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a() {
            public final void a(BaseNetResult baseNetResult) {
                boolean z = true;
                if (baseNetResult != null) {
                    RideState rideState = (RideState) baseNetResult;
                    if (!rideState.result || !TextUtils.isEmpty(rideState.orderId) || rideState.status != 1) {
                        z = false;
                    }
                    if (rideState.result) {
                        if (rideState.status != 2 || !"OFO".equalsIgnoreCase(ehs.b("share_bike_cp_source"))) {
                            eht.e();
                        } else {
                            long b = ehs.b() - (rideState.currentTimestamp - rideState.createTime);
                            long j = 0;
                            if (b >= 0) {
                                j = b;
                            }
                            eht.a(j);
                            egj.a().d();
                        }
                    }
                }
                eht.a(z);
            }
        });
    }
}

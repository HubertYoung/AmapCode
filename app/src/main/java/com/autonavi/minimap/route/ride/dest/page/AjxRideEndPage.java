package com.autonavi.minimap.route.ride.dest.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.bundle.share.entity.ShareParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam;
import com.autonavi.minimap.bundle.share.entity.ShareParam.DingDingParam.SendType;
import com.autonavi.minimap.bundle.share.entity.ShareParam.e;
import com.autonavi.minimap.bundle.share.entity.ShareParam.f;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.lang.ref.WeakReference;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRideEndPage extends Ajx3Page implements axl, OnErrorReportClickInterface {
    private edu a;
    /* access modifiers changed from: private */
    public ctl b;
    private int c = 0;
    private boolean d;

    static class JsLoadCallback implements Callback<AmapAjxView> {
        private WeakReference<AjxRideEndPage> mPageRef;

        public void error(Throwable th, boolean z) {
        }

        JsLoadCallback(AjxRideEndPage ajxRideEndPage) {
            this.mPageRef = new WeakReference<>(ajxRideEndPage);
        }

        public void callback(AmapAjxView amapAjxView) {
            AjxRideEndPage ajxRideEndPage = (AjxRideEndPage) this.mPageRef.get();
            if (ajxRideEndPage != null && ajxRideEndPage.isAlive() && amapAjxView != null) {
                AjxRideEndPage.a(ajxRideEndPage);
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("bundle_key_page_from_history")) {
            this.d = arguments.getBoolean("bundle_key_page_from_history");
        }
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        edo.a("performance-", "showRideEndPage");
        FrameLayout frameLayout = new FrameLayout(getContext());
        MapInteractiveRelativeLayout mapInteractiveRelativeLayout = new MapInteractiveRelativeLayout(getContext());
        if (euk.a()) {
            mapInteractiveRelativeLayout.setPadding(mapInteractiveRelativeLayout.getPaddingLeft(), mapInteractiveRelativeLayout.getPaddingTop() + euk.a(getContext()), mapInteractiveRelativeLayout.getPaddingRight(), mapInteractiveRelativeLayout.getPaddingBottom());
        }
        frameLayout.addView(mapInteractiveRelativeLayout, new LayoutParams(-1, -1));
        frameLayout.addView(amapAjxView, new LayoutParams(-1, -1));
        amapAjxView.onAjxContextCreated(new JsLoadCallback(this));
        return frameLayout;
    }

    public View getMapSuspendView() {
        if (this.d) {
            return null;
        }
        this.a = new edu(this);
        this.a.c = this;
        return this.a.b.getSuspendView();
    }

    public void onJsBack(Object obj, String str) {
        if (this.a != null) {
            this.a.c = null;
            this.a = null;
        }
        AMapPageUtil.removeActivityStateListener(this);
        finish();
    }

    public void resume() {
        super.resume();
        bty mapView = getMapView();
        if (mapView != null) {
            this.c = mapView.k(false);
            ebf.a(mapView, mapView.j(false), 0, 12);
        }
    }

    public void destroy() {
        super.destroy();
        bty mapView = getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), mapView.l(false), this.c);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00aa A[Catch:{ JSONException -> 0x00dd }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(java.lang.String r9) {
        /*
            r8 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto L_0x000f
            java.lang.String r9 = "songping:"
            java.lang.String r0 = "onScreenShotFinish imgPath is empty"
            defpackage.edo.a(r9, r0)
            return
        L_0x000f:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00dd }
            r0.<init>(r9)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r9 = "imgpath"
            java.lang.String r9 = r0.optString(r9)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r1 = "startTime"
            long r0 = r0.optLong(r1)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r2 = "songping:"
            java.lang.String r3 = "onScreenShotFinish imgPath = "
            java.lang.String r4 = java.lang.String.valueOf(r9)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ JSONException -> 0x00dd }
            defpackage.edo.a(r2, r3)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r2 = "songping:"
            java.lang.String r3 = "onScreenShotFinish startTime = "
            java.lang.String r4 = java.lang.String.valueOf(r0)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ JSONException -> 0x00dd }
            defpackage.edo.a(r2, r3)     // Catch:{ JSONException -> 0x00dd }
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x00dd }
            if (r2 != 0) goto L_0x0057
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x004e
            goto L_0x0057
        L_0x004e:
            com.autonavi.minimap.route.ride.dest.page.AjxRideEndPage$1 r2 = new com.autonavi.minimap.route.ride.dest.page.AjxRideEndPage$1     // Catch:{ JSONException -> 0x00dd }
            r2.<init>(r0, r9)     // Catch:{ JSONException -> 0x00dd }
            defpackage.ahm.a(r2)     // Catch:{ JSONException -> 0x00dd }
            goto L_0x0068
        L_0x0057:
            java.lang.String r0 = "songping:"
            java.lang.String r1 = "updateScreenShot  imgpath = "
            java.lang.String r2 = java.lang.String.valueOf(r9)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r1 = r1.concat(r2)     // Catch:{ JSONException -> 0x00dd }
            defpackage.edo.a(r0, r1)     // Catch:{ JSONException -> 0x00dd }
        L_0x0068:
            boolean r0 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x00dd }
            if (r0 != 0) goto L_0x00dc
            java.lang.String r0 = defpackage.edn.a()     // Catch:{ JSONException -> 0x00dd }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x00dd }
            if (r0 != 0) goto L_0x00dc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00dd }
            r0.<init>()     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r1 = defpackage.edn.a()     // Catch:{ JSONException -> 0x00dd }
            r0.append(r1)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r1 = java.io.File.separator     // Catch:{ JSONException -> 0x00dd }
            r0.append(r1)     // Catch:{ JSONException -> 0x00dd }
            r0.append(r9)     // Catch:{ JSONException -> 0x00dd }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x00dd }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ JSONException -> 0x00dd }
            if (r1 != 0) goto L_0x00a6
            java.io.File r1 = new java.io.File     // Catch:{ JSONException -> 0x00dd }
            r1.<init>(r0)     // Catch:{ JSONException -> 0x00dd }
            boolean r1 = r1.exists()     // Catch:{ JSONException -> 0x00dd }
            if (r1 == 0) goto L_0x00a6
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch:{ JSONException -> 0x00dd }
            goto L_0x00a7
        L_0x00a6:
            r0 = 0
        L_0x00a7:
            r1 = r0
            if (r1 == 0) goto L_0x00dc
            int r4 = r1.getWidth()     // Catch:{ JSONException -> 0x00dd }
            int r0 = r1.getHeight()     // Catch:{ JSONException -> 0x00dd }
            int r2 = r4 * 3
            int r5 = r2 / 4
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ JSONException -> 0x00dd }
            r3 = 1132068864(0x437a0000, float:250.0)
            int r2 = defpackage.agn.a(r2, r3)     // Catch:{ JSONException -> 0x00dd }
            android.app.Application r3 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ JSONException -> 0x00dd }
            r6 = 1116078080(0x42860000, float:67.0)
            int r3 = defpackage.agn.a(r3, r6)     // Catch:{ JSONException -> 0x00dd }
            int r0 = r0 - r2
            int r0 = r0 - r3
            int r0 = r0 - r5
            int r0 = r0 / 2
            if (r0 <= 0) goto L_0x00d2
            int r3 = r3 + r0
        L_0x00d2:
            r2 = 0
            r6 = 0
            r7 = 0
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ JSONException -> 0x00dd }
            defpackage.eet.a(r0, r9)     // Catch:{ JSONException -> 0x00dd }
        L_0x00dc:
            return
        L_0x00dd:
            r9 = move-exception
            r9.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.ride.dest.page.AjxRideEndPage.b(java.lang.String):void");
    }

    public void onPageCover() {
        super.onPageCover();
        if (this.a != null) {
            this.a.a(false);
        }
        PlaySoundUtils.getInstance().release();
        if (this.b != null) {
            this.b.a(a());
        }
    }

    public void onPageAppear() {
        super.onPageAppear();
        if (this.a != null) {
            this.a.a(true);
        }
    }

    private static String a() {
        boolean z = true;
        if (edr.a() != 1) {
            z = false;
        }
        return z ? "22" : "8";
    }

    public void pageCreated() {
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.f();
            }
        }
        if (!this.d) {
            this.b = (ctl) a.a.a(ctl.class);
            if (this.b != null) {
                final String a2 = a();
                this.b.a(a2, new Callback<ctm>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(final ctm ctm) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (ctm != null && AjxRideEndPage.this.isStarted()) {
                                    AjxRideEndPage.this.b.a(AjxRideEndPage.this, a2, ctm.c);
                                }
                            }
                        }, 1000);
                    }
                });
            }
        }
    }

    public final void a(final String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showToast(getString(R.string.screenshot_fail));
            return;
        }
        if (str.contains("file://")) {
            str = str.replace("file://", "");
        }
        dct dct = new dct(0);
        dct.f = true;
        dct.d = true;
        dct.e = true;
        dct.h = true;
        dct.l = true;
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            dcb.a(dct, (dcd) new dcd() {
                public final ShareParam getShareDataByType(int i) {
                    if (i != 11) {
                        switch (i) {
                            case 3:
                                e eVar = new e(0);
                                Bitmap c = AjxRideEndPage.c(str);
                                if (c != null) {
                                    eVar.g = c;
                                }
                                eVar.h = str;
                                eVar.c = false;
                                eVar.e = 3;
                                return eVar;
                            case 4:
                                e eVar2 = new e(1);
                                Bitmap c2 = AjxRideEndPage.c(str);
                                if (c2 != null) {
                                    eVar2.g = c2;
                                }
                                eVar2.h = str;
                                eVar2.c = false;
                                eVar2.e = 3;
                                return eVar2;
                            case 5:
                                f fVar = new f();
                                fVar.a = AjxRideEndPage.this.getString(R.string.ride_finish_share_weibo_msg);
                                fVar.j = true;
                                fVar.h = str;
                                fVar.c = false;
                                return fVar;
                            default:
                                return null;
                        }
                    } else {
                        DingDingParam dingDingParam = new DingDingParam();
                        dingDingParam.e = SendType.LocalImage;
                        dingDingParam.c = false;
                        Bitmap c3 = AjxRideEndPage.c(str);
                        if (c3 != null) {
                            dingDingParam.g = c3;
                        }
                        StringBuilder sb = new StringBuilder("file://");
                        sb.append(str);
                        dingDingParam.i = sb.toString();
                        return dingDingParam;
                    }
                }
            });
        }
    }

    public void onErrorReportClickBtn(String str) {
        awy awy = (awy) a.a.a(awy.class);
        String b2 = awy != null ? awy.b().b(this.mAjxView) : null;
        if (b2 != null && !b2.trim().equals("")) {
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                PageBundle b3 = ebc.b(getContext(), str, b2);
                b3.putInt("sourcepage", 35);
                b3.putString("sourcepage", "35");
                b3.putInt("page_id", 26);
                b3.putInt("route_line_type", 2);
                iErrorReportStarter.startFeedback(b3);
            }
        }
    }

    static /* synthetic */ void a(AjxRideEndPage ajxRideEndPage) {
        if (ajxRideEndPage.isAlive()) {
            awy awy = (awy) a.a.a(awy.class);
            if (awy != null) {
                awy.b().a(ajxRideEndPage.mAjxView, (axl) ajxRideEndPage);
            }
        }
    }

    static /* synthetic */ Bitmap c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        return edn.a(ahc.a(decodeFile, decodeFile.getWidth() >> 3, decodeFile.getHeight() >> 3), edn.a("runTraceThumbnail.png"));
    }
}

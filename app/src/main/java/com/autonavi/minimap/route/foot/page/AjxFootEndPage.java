package com.autonavi.minimap.route.foot.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSON;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;
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
import com.autonavi.minimap.route.foot.model.FootEndUGCData;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxFootEndPage extends Ajx3Page implements axl, axm, OnErrorReportClickInterface {
    private edu a;
    /* access modifiers changed from: private */
    public ctl b;
    private int c = 0;
    private boolean d;
    /* access modifiers changed from: private */
    public String e;
    private dcd f = new dcd() {
        public final ShareParam getShareDataByType(int i) {
            if (i != 11) {
                switch (i) {
                    case 3:
                        e eVar = new e(0);
                        Bitmap d = AjxFootEndPage.d(AjxFootEndPage.this.e);
                        if (d != null) {
                            eVar.g = d;
                        }
                        eVar.h = AjxFootEndPage.this.e;
                        eVar.c = false;
                        eVar.e = 3;
                        return eVar;
                    case 4:
                        e eVar2 = new e(1);
                        Bitmap d2 = AjxFootEndPage.d(AjxFootEndPage.this.e);
                        if (d2 != null) {
                            eVar2.g = d2;
                        }
                        eVar2.h = AjxFootEndPage.this.e;
                        eVar2.c = false;
                        eVar2.e = 3;
                        return eVar2;
                    case 5:
                        f fVar = new f();
                        fVar.a = AjxFootEndPage.this.getString(R.string.foot_navi_share_weibo_body);
                        fVar.j = true;
                        fVar.h = AjxFootEndPage.this.e;
                        fVar.c = false;
                        return fVar;
                    default:
                        return null;
                }
            } else {
                DingDingParam dingDingParam = new DingDingParam();
                dingDingParam.e = SendType.LocalImage;
                dingDingParam.c = false;
                Bitmap d3 = AjxFootEndPage.d(AjxFootEndPage.this.e);
                if (d3 != null) {
                    dingDingParam.g = d3;
                }
                StringBuilder sb = new StringBuilder("file://");
                sb.append(AjxFootEndPage.this.e);
                dingDingParam.i = sb.toString();
                return dingDingParam;
            }
        }
    };

    static class JsLoadCallback implements Callback<AmapAjxView> {
        private WeakReference<AjxFootEndPage> mPageRef;

        public void error(Throwable th, boolean z) {
        }

        JsLoadCallback(AjxFootEndPage ajxFootEndPage) {
            this.mPageRef = new WeakReference<>(ajxFootEndPage);
        }

        public void callback(AmapAjxView amapAjxView) {
            AjxFootEndPage ajxFootEndPage = (AjxFootEndPage) this.mPageRef.get();
            if (ajxFootEndPage != null && ajxFootEndPage.isAlive() && amapAjxView != null) {
                AjxFootEndPage.a(ajxFootEndPage);
                AjxFootEndPage.b(ajxFootEndPage);
                eau.a("performance-", "AjxFootEndPage  JsLoadCallback");
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eau.a("performance-", "AjxFootEndPage  onCreate");
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("bundle_key_page_from_history")) {
            this.d = arguments.getBoolean("bundle_key_page_from_history");
        }
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        eau.a("performance-", "showFootEndPage");
        eau.a("performance-", "AjxFootEndPage  onCreateView");
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
        finish();
    }

    public void resume() {
        super.resume();
        bty mapView = getMapView();
        if (mapView != null) {
            this.c = mapView.k(false);
            ebf.a(mapView, mapView.j(false), 0, 6);
        }
        eau.a("performance-", "AjxFootEndPage  resume");
    }

    public void destroy() {
        super.destroy();
        this.f = null;
        this.e = null;
        avl avl = (avl) a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axm) null);
            avl.b().a((AmapAjxViewInterface) this.mAjxView, (axl) null);
        }
        bty mapView = getMapView();
        if (mapView != null) {
            ebf.a(mapView, mapView.j(false), mapView.l(false), this.c);
        }
    }

    public final void b(String str) {
        Bitmap bitmap;
        if (TextUtils.isEmpty(str)) {
            eau.a("songping:", "onScreenShotFinish imgPath is empty");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("imgpath");
            long optLong = jSONObject.optLong("startTime");
            eau.a("songping:", "onScreenShotFinish imgPath = ".concat(String.valueOf(optString)));
            eau.a("songping:", "onScreenShotFinish startTime = ".concat(String.valueOf(optLong)));
            if (!TextUtils.isEmpty(optString)) {
                if (optLong != 0) {
                    ahm.a(new Runnable(optLong, optString) {
                        final /* synthetic */ long a;
                        final /* synthetic */ String b;

                        {
                            this.a = r1;
                            this.b = r3;
                        }

                        public final void run() {
                            AMapPageUtil.getAppContext();
                            btg a2 = bsq.a().a(this.a);
                            if (a2 != null && TextUtils.isEmpty(a2.h)) {
                                a2.h = this.b;
                                AMapPageUtil.getAppContext();
                                bsq.a().a(a2);
                            }
                        }
                    });
                }
            }
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(edn.a())) {
                StringBuilder sb = new StringBuilder();
                sb.append(edn.a());
                sb.append(File.separator);
                sb.append(optString);
                String sb2 = sb.toString();
                if (TextUtils.isEmpty(sb2) || !new File(sb2).exists()) {
                    bitmap = null;
                } else {
                    bitmap = BitmapFactory.decodeFile(sb2);
                }
                if (bitmap != null) {
                    Bitmap a2 = efw.a(bitmap);
                    if (a2 != null) {
                        efw.a(a2, optString);
                        a2.recycle();
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void c(String str) {
        eau.a("yangqiang", "UGC CLICK=    ".concat(String.valueOf(str)));
        FootEndUGCData footEndUGCData = (FootEndUGCData) JSON.parseObject(str, FootEndUGCData.class);
        if (footEndUGCData != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString(H5PageData.KEY_UC_START, footEndUGCData.start.name);
            pageBundle.putString("end", footEndUGCData.end.name);
            pageBundle.putString("naviid", footEndUGCData.naviid);
            pageBundle.putInt("source", footEndUGCData.source);
            bdp bdp = (bdp) a.a.a(bdp.class);
            if (bdp != null) {
                startPageForResult(bdp.c(), pageBundle, 0);
            }
        }
    }

    public void onPageCover() {
        super.onPageCover();
        if (this.a != null) {
            this.a.a(false);
        }
        PlaySoundUtils.getInstance().release();
        if (this.b != null) {
            this.b.a("13");
        }
    }

    public void onPageAppear() {
        super.onPageAppear();
        if (this.a != null) {
            this.a.a(true);
        }
    }

    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        super.result(i, resultType, pageBundle);
        if (resultType == ResultType.OK) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("submit", true);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            avl avl = (avl) a.a.a(avl.class);
            if (avl != null) {
                avl.b().a((AmapAjxViewInterface) this.mAjxView, jSONObject.toString());
            }
        }
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
                this.b.a("13", new Callback<ctm>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(final ctm ctm) {
                        aho.a(new Runnable() {
                            public final void run() {
                                if (ctm != null && AjxFootEndPage.this.isStarted()) {
                                    AjxFootEndPage.this.b.a(AjxFootEndPage.this, "13", ctm.c);
                                }
                            }
                        }, 1000);
                    }
                });
            }
        }
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showToast(getString(R.string.screenshot_fail));
            return;
        }
        if (str.contains("file://")) {
            this.e = str.replace("file://", "");
        }
        dct dct = new dct(0);
        dct.f = true;
        dct.d = true;
        dct.e = true;
        dct.l = true;
        dct.h = true;
        dcb dcb = (dcb) a.a.a(dcb.class);
        if (dcb != null) {
            dcb.a(dct, this.f);
        }
    }

    public void onErrorReportClickBtn(String str) {
        avl avl = (avl) a.a.a(avl.class);
        String a2 = avl != null ? avl.b().a(this.mAjxView) : null;
        if (a2 != null && !a2.trim().equals("")) {
            LogManager.actionLogV2("P00032", "B002");
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                PageBundle a3 = ebc.a(getContext(), str, a2);
                a3.putBoolean("bundle_key_boolean_default", false);
                a3.putInt("sourcepage", 21);
                a3.putInt("page_id", 3);
                a3.putInt("route_line_type", 2);
                iErrorReportStarter.startFeedback(a3);
            }
        }
    }

    static /* synthetic */ void a(AjxFootEndPage ajxFootEndPage) {
        avl avl = (avl) a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) ajxFootEndPage.mAjxView, (axl) ajxFootEndPage);
        }
    }

    static /* synthetic */ void b(AjxFootEndPage ajxFootEndPage) {
        avl avl = (avl) a.a.a(avl.class);
        if (avl != null) {
            avl.b().a((AmapAjxViewInterface) ajxFootEndPage.mAjxView, (axm) ajxFootEndPage);
        }
    }

    static /* synthetic */ Bitmap d(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        return edn.a(ahc.a(decodeFile, decodeFile.getWidth() >> 3, decodeFile.getHeight() >> 3), edn.a("runTraceThumbnail.png"));
    }
}

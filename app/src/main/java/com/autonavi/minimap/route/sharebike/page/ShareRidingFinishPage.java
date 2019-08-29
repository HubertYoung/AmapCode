package com.autonavi.minimap.route.sharebike.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.order.param.BikeBalancePayRequest;
import com.autonavi.minimap.order.param.BikePayRequest;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.model.PayInfo;
import com.autonavi.minimap.route.sharebike.net.request.BalancePayRequest;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import com.autonavi.minimap.route.sharebike.net.request.PayRequest;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.route.sharebike.view.ShareBikeFeeTipView;
import com.autonavi.minimap.route.sharebike.view.ShareRideFinishBottomView;
import java.util.ArrayList;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class ShareRidingFinishPage extends AbstractBaseMapPage<ehf> implements OnClickListener, OnTouchListener, OnPreDrawListener, LocationNone {
    public ShareRideFinishBottomView a;
    public int b;
    public boolean c;
    public View d;
    public LinearLayout e;
    public ShareBikeFeeTipView f;
    public View g;
    private ImageView h;
    private ProgressDlg i;
    private ImageView j;
    private ImageButton k;
    private TextView l;
    private TextView m;
    private Button n;

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public ehf createPresenter() {
        return new ehf(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.share_bike_finish_map_layout);
        View contentView = getContentView();
        this.a = (ShareRideFinishBottomView) findViewById(R.id.run_distance_bottom_view);
        this.h = (ImageView) findViewById(R.id.shareButton);
        this.l = (TextView) findViewById(R.id.run_share_time);
        this.j = (ImageView) findViewById(R.id.img_logo);
        this.f = (ShareBikeFeeTipView) findViewById(R.id.share_bike_fee_detail_pop_view);
        this.m = (TextView) findViewById(R.id.share_riding_fee_detail);
        this.d = contentView.findViewById(R.id.run_finish_title);
        this.e = (LinearLayout) contentView.findViewById(R.id.rainbow_flag);
        this.k = (ImageButton) contentView.findViewById(R.id.route_title_back);
        this.n = (Button) contentView.findViewById(R.id.share_bike_finish_pay_bill);
        this.g = contentView.findViewById(R.id.line_division);
        this.l.getPaint().setShadowLayer(5.0f, 0.0f, 0.0f, getResources().getColor(R.color.f_c_1));
        if (euk.a()) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.d.getLayoutParams();
            marginLayoutParams.topMargin += euk.a(getContext());
            this.d.setLayoutParams(marginLayoutParams);
        }
        this.a.setOnTouchListener(this);
        this.h.setOnClickListener(this);
        this.d.setOnTouchListener(this);
        this.k.setOnClickListener(this);
        this.n.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ehf ehf = (ehf) ShareRidingFinishPage.this.mPresenter;
                if (ehf.mPage != null && ((ShareRidingFinishPage) ehf.mPage).isAlive()) {
                    if (!"ofo".equalsIgnoreCase(ehf.e) || TextUtils.isEmpty(ehf.f)) {
                        BikePayRequest bikePayRequest = new BikePayRequest();
                        bikePayRequest.b = ehf.e;
                        bikePayRequest.c = ehf.f;
                        a.a((BaseRequest) new PayRequest(bikePayRequest, new a() {
                            public final void a(BaseNetResult baseNetResult) {
                                if (baseNetResult == null) {
                                    ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_result_failed_payment));
                                    return;
                                }
                                if (baseNetResult instanceof PayInfo) {
                                    PayInfo payInfo = (PayInfo) baseNetResult;
                                    if (!TextUtils.isEmpty(payInfo.pasString)) {
                                        ebr.a(false).post(new Runnable(new egl(((ShareRidingFinishPage) ehf.this.mPage).getActivity()), payInfo.pasString, ehf.M(ehf.this)) {
                                            final /* synthetic */ String a;
                                            final /* synthetic */ a b;
                                            final /* synthetic */ egl c;

                                            public final 
/*
Method generation error in method: egl.1.run():null, dex: classes3.dex
                                            java.lang.NullPointerException
                                            	at jadx.core.codegen.ClassGen.useType(ClassGen.java:441)
                                            	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:109)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:310)
                                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:664)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:598)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:356)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:776)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:716)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:360)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:242)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:315)
                                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:664)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:598)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:356)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:776)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:628)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:356)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:776)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:716)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:360)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:242)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:315)
                                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:664)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:598)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:356)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:223)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:105)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:776)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:716)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:360)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:242)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:213)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                                            	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:210)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:203)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:315)
                                            	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:261)
                                            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:224)
                                            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:109)
                                            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:75)
                                            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                                            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:32)
                                            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:20)
                                            	at jadx.core.ProcessClass.process(ProcessClass.java:36)
                                            	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
                                            	at jadx.api.JavaClass.decompile(JavaClass.java:62)
                                            	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
                                            
*/
                                        });
                                    } else if (TextUtils.isEmpty(payInfo.pasString)) {
                                        ehf.this.e();
                                    }
                                }
                            }
                        }));
                        return;
                    }
                    BikeBalancePayRequest bikeBalancePayRequest = new BikeBalancePayRequest();
                    bikeBalancePayRequest.b = ehf.e;
                    bikeBalancePayRequest.c = ehf.f;
                    a.a((BaseRequest) new BalancePayRequest(bikeBalancePayRequest, new a() {
                        public final void a(BaseNetResult baseNetResult) {
                            if (baseNetResult == null) {
                                ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_result_failed_payment));
                            } else if (baseNetResult.errorCode == 164) {
                                ToastHelper.showToast(((ShareRidingFinishPage) ehf.this.mPage).getString(R.string.share_bike_alipay_failed));
                            } else {
                                ehf.this.e();
                            }
                        }
                    }));
                }
            }
        });
        this.m.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!ShareRidingFinishPage.this.isAlive() || ((ehf) ShareRidingFinishPage.this.mPresenter).d == null || !((ehf) ShareRidingFinishPage.this.mPresenter).d.result) {
                    ShareRidingFinishPage.this.f.setVisibility(8);
                } else {
                    ShareRidingFinishPage.this.f.setVisibility(0);
                }
            }
        });
    }

    public View getMapSuspendView() {
        return new ccv(AMapPageUtil.getAppContext()).getSuspendView();
    }

    public final synchronized void a(String str, boolean z) {
        String str2 = "";
        try {
            if (!TextUtils.isEmpty(str)) {
                str2 = str;
            }
            if (this.i == null) {
                this.i = new ProgressDlg(getActivity(), str2, "");
            }
            this.i.setCancelable(z);
            this.i.setMessage(str);
            if (!this.i.isShowing()) {
                this.i.show();
            }
            eao.e("ShareRidingFinishPage", "showProgressDialog ---> ".concat(String.valueOf(str)));
        } catch (Exception e2) {
            kf.a((Throwable) e2);
            StringBuilder sb = new StringBuilder("CatchExceptionUtil  ---> ");
            sb.append(e2.getMessage());
            eao.e("ShareRidingFinishPage", sb.toString());
        }
    }

    public final synchronized void a() {
        if (this.i != null && this.i.isShowing()) {
            this.i.dismiss();
            eao.e("ShareRidingFinishPage", "dismissProgressDialog ---> ");
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.shareButton) {
            ehf ehf = (ehf) this.mPresenter;
            if (!((ShareRidingFinishPage) ehf.mPage).c) {
                LogManager.actionLogV2("P00301", "B001");
                ((ShareRidingFinishPage) ehf.mPage).c = true;
                ((ShareRidingFinishPage) ehf.mPage).a(((ShareRidingFinishPage) ehf.mPage).getString(R.string.route_foot_navi_end_getting_share_content), false);
                if (ehf.b == null || ehf.b.c <= 0 || ehf.b.j == null || ehf.b.j.a == null || ehf.b.j.b == null || ehf.b.j.e == null || ehf.b.j.e.size() == 0) {
                    ebr.a(true).postDelayed(new Runnable() {
                        public final void run() {
                            ((ShareRidingFinishPage) ehf.this.mPage).getMapView().a(((ShareRidingFinishPage) ehf.this.mPage).getMapView().al(), ((ShareRidingFinishPage) ehf.this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                                public final void onCallBack(final Bitmap bitmap) {
                                    if (ehf.p) {
                                        eao.e(ShareRidingMapPage.class.getName(), "createScreenShot new IMapView.ICraopMapCallBack()");
                                    }
                                    ebr.a(true).post(new Runnable() {
                                        public final void run() {
                                            ehf.a(ehf.this, bitmap);
                                        }
                                    });
                                }
                            });
                            if (ehf.p) {
                                eao.e(ShareRidingMapPage.class.getName(), "mTraceHistory == null");
                            }
                        }
                    }, 500);
                    return;
                } else {
                    ehf.a();
                    ebr.a(true).postDelayed(new Runnable() {
                        public final void run() {
                            ((ShareRidingFinishPage) ehf.this.mPage).getMapView().a(((ShareRidingFinishPage) ehf.this.mPage).getMapView().al(), ((ShareRidingFinishPage) ehf.this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                                public final void onCallBack(final Bitmap bitmap) {
                                    if (ehf.p) {
                                        eao.e(ShareRidingMapPage.class.getName(), "createScreenShot new IMapView.ICraopMapCallBack()");
                                    }
                                    ebr.a(true).post(new Runnable() {
                                        public final void run() {
                                            ehf.a(ehf.this, bitmap);
                                        }
                                    });
                                }
                            });
                            if (ehf.p) {
                                eao.e(ShareRidingMapPage.class.getName(), "zoomBoundMap createScreenShot");
                            }
                        }
                    }, 500);
                }
            }
            return;
        }
        if (id == R.id.route_title_back) {
            b();
        }
    }

    public final void b() {
        if (((ehf) this.mPresenter).g == 1) {
            startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
        } else if (((ehf) this.mPresenter).g == 2) {
            startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                bax.a((PageBundle) null);
            }
        } else if (((ehf) this.mPresenter).g == 3) {
            ajw ajw = (ajw) ank.a(ajw.class);
            if (ajw != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add("amapuri://bus/busRemind");
                ajw.a(arrayList, this);
            } else {
                return;
            }
        } else {
            finish();
        }
        setResult(ResultType.CANCEL, (PageBundle) null);
    }

    public final void a(String str, OrderDetail orderDetail) {
        boolean z = true;
        int i2 = 0;
        if (orderDetail != null && !TextUtils.isEmpty(orderDetail.isPay)) {
            try {
                if (Integer.parseInt(orderDetail.isPay) != 0) {
                    z = false;
                }
            } catch (NumberFormatException unused) {
            }
        }
        this.h.setVisibility(z ? 0 : 8);
        Button button = this.n;
        if (z) {
            i2 = 8;
        }
        button.setVisibility(i2);
        this.a.setOrderDetail(str, orderDetail);
        this.f.setOrderDetail(orderDetail);
    }

    public final void a(String str) {
        this.l.setText(str);
    }

    public boolean onPreDraw() {
        int i2;
        if (!isStarted() || !((ehf) this.mPresenter).j || this.a == null || !this.a.isShown()) {
            return true;
        }
        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
        ehf ehf = (ehf) this.mPresenter;
        ((ShareRidingFinishPage) ehf.mPage).b = ((ShareRidingFinishPage) ehf.mPage).a.getHeight();
        if (((ShareRidingFinishPage) ehf.mPage).b < 10) {
            ((ShareRidingFinishPage) ehf.mPage).b = 738;
        }
        int height = ((ShareRidingFinishPage) ehf.mPage).d.getHeight();
        if (height < 10) {
            height = 132;
        }
        int a2 = ((ShareRidingFinishPage) ehf.mPage).b + agn.a(AMapPageUtil.getAppContext(), 52.0f);
        int height2 = (((ags.a(AMapPageUtil.getAppContext()).height() - a2) - height) - ((int) (((float) ags.a(AMapPageUtil.getAppContext()).width()) * 0.75f))) / 2;
        int a3 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
        int a4 = agn.a(AMapPageUtil.getAppContext(), 10.0f);
        if ((ehf.b == null || ehf.b.k != RideType.RIDE_TYPE) && (ehf.b == null || ehf.b.k != RideType.SHARE_RIDE_TYPE)) {
            i2 = agn.a(AMapPageUtil.getAppContext(), 50.0f);
        } else {
            i2 = a4;
        }
        ehf.a.a(a3, height + height2 + i2, a3, a2 + height2 + a4);
        getMapView().X();
        ((ehf) this.mPresenter).a();
        ((ehf) this.mPresenter).h = true;
        getMapView().f(false);
        return true;
    }
}

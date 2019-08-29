package com.autonavi.minimap.drive.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.amap.bundle.drivecommon.model.NavigationPath;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.model.OfflineMsgCode;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.List;

public class RouteFragmentHomeAddressView extends LinearLayout {
    public static final int MAX_LENGTH = 50000;
    public static final int MIN_LENGTH = 1000;
    public static final String MY_LOCATION_DES = "我的位置";
    private static final int TMC_REQUEST_FAIL = 2;
    private static final int TMC_REQUEST_SUCCESS = 1;
    final Callback<tc> innerCallback = new Callback<tc>() {
        public void callback(tc tcVar) {
            Message obtainMessage = RouteFragmentHomeAddressView.this.mCallHandler.obtainMessage();
            obtainMessage.what = 1;
            obtainMessage.obj = tcVar;
            obtainMessage.sendToTarget();
        }

        public void error(Throwable th, boolean z) {
            RouteFragmentHomeAddressView.this.mCallHandler.sendEmptyMessage(2);
        }
    };
    private dji mAsyncTrafficBarLoader;
    /* access modifiers changed from: private */
    public final a mCallHandler = new a(this);
    private ImageButton mCompanyEditButton;
    /* access modifiers changed from: private */
    public View mCompanyEditView;
    private View mCompanyLayout;
    /* access modifiers changed from: private */
    public ISearchPoiData mCompanyPOI;
    private TextView mCompanyTV;
    /* access modifiers changed from: private */
    public ImageView mCompanyTmcBar;
    /* access modifiers changed from: private */
    public View mCompanyTmcLayout;
    /* access modifiers changed from: private */
    public TextView mCompanyTmcTV;
    private ImageButton mHomeEditButton;
    /* access modifiers changed from: private */
    public View mHomeEditView;
    private View mHomeLayout;
    /* access modifiers changed from: private */
    public ISearchPoiData mHomePOI;
    private TextView mHomeTV;
    /* access modifiers changed from: private */
    public ImageView mHomeTmcBar;
    /* access modifiers changed from: private */
    public View mHomeTmcLayout;
    /* access modifiers changed from: private */
    public TextView mHomeTmcTV;
    private boolean mIsFinished = false;
    /* access modifiers changed from: private */
    public b mListener;
    private OnClickListener mOnClickListener = new OnClickListener() {
        public final void onClick(View view) {
            b bVar;
            ISearchPoiData iSearchPoiData;
            int id = view.getId();
            boolean z = false;
            if (id != R.id.home_layout) {
                if (id == R.id.company_layout) {
                    if (RouteFragmentHomeAddressView.this.mListener != null) {
                        bVar = RouteFragmentHomeAddressView.this.mListener;
                        iSearchPoiData = RouteFragmentHomeAddressView.this.mCompanyPOI;
                    }
                } else if (id == R.id.home_layout_right || id == R.id.home_image_edit) {
                    if (RouteFragmentHomeAddressView.this.mListener != null) {
                        b access$000 = RouteFragmentHomeAddressView.this.mListener;
                        ISearchPoiData access$100 = RouteFragmentHomeAddressView.this.mHomePOI;
                        if (RouteFragmentHomeAddressView.this.mHomeEditView.getVisibility() == 0) {
                            z = true;
                        }
                        access$000.a(access$100, z);
                        return;
                    }
                } else if ((id == R.id.company_layout_right || id == R.id.company_image_edit) && RouteFragmentHomeAddressView.this.mListener != null) {
                    bVar = RouteFragmentHomeAddressView.this.mListener;
                    iSearchPoiData = RouteFragmentHomeAddressView.this.mCompanyPOI;
                    if (RouteFragmentHomeAddressView.this.mCompanyEditView.getVisibility() == 0) {
                        z = true;
                    }
                }
                bVar.b(iSearchPoiData, z);
            } else if (RouteFragmentHomeAddressView.this.mListener != null) {
                RouteFragmentHomeAddressView.this.mListener.a(RouteFragmentHomeAddressView.this.mHomePOI, false);
            }
        }
    };
    /* access modifiers changed from: private */
    public AbstractBasePage mPage;
    private boolean mShowTmcBar = false;
    private AosRequest mTmcRequest;

    static class a extends Handler {
        private WeakReference<RouteFragmentHomeAddressView> a = null;

        public a(RouteFragmentHomeAddressView routeFragmentHomeAddressView) {
            this.a = new WeakReference<>(routeFragmentHomeAddressView);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    if (!(this.a == null || this.a.get() == null)) {
                        ((RouteFragmentHomeAddressView) this.a.get()).onTmcRequestSuccess((tc) message.obj);
                        return;
                    }
                case 2:
                    if (!(this.a == null || this.a.get() == null)) {
                        ((RouteFragmentHomeAddressView) this.a.get()).onTmcRequesetFailed();
                        break;
                    }
            }
        }
    }

    public interface b {
        void a(POI poi, boolean z);

        void b(POI poi, boolean z);
    }

    /* access modifiers changed from: private */
    public void onTmcRequesetFailed() {
    }

    public void setOnRouteHomeAddressClickListener(b bVar) {
        this.mListener = bVar;
    }

    public RouteFragmentHomeAddressView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        initView(context);
    }

    public RouteFragmentHomeAddressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public RouteFragmentHomeAddressView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.custom_address_layout, this, true);
        this.mHomeTV = (TextView) findViewById(R.id.home_tv);
        this.mCompanyTV = (TextView) findViewById(R.id.company_tv);
        this.mHomeTmcLayout = findViewById(R.id.home_tmc_layout);
        this.mCompanyTmcLayout = findViewById(R.id.company_tmc_layout);
        this.mHomeLayout = findViewById(R.id.home_layout);
        this.mCompanyLayout = findViewById(R.id.company_layout);
        this.mHomeEditView = findViewById(R.id.home_layout_right);
        this.mHomeEditButton = (ImageButton) findViewById(R.id.home_image_edit);
        this.mHomeTmcTV = (TextView) findViewById(R.id.home_tmc_textview);
        this.mHomeTmcBar = (ImageView) findViewById(R.id.home_tmc_bar);
        this.mCompanyTmcTV = (TextView) findViewById(R.id.company_tmc_textview);
        this.mCompanyTmcBar = (ImageView) findViewById(R.id.company_tmc_bar);
        this.mCompanyEditView = findViewById(R.id.company_layout_right);
        this.mCompanyEditButton = (ImageButton) findViewById(R.id.company_image_edit);
        this.mAsyncTrafficBarLoader = new dji(context);
        NoDBClickUtil.a(this.mHomeEditView, this.mOnClickListener);
        NoDBClickUtil.a((View) this.mHomeEditButton, this.mOnClickListener);
        NoDBClickUtil.a(this.mCompanyEditView, this.mOnClickListener);
        NoDBClickUtil.a((View) this.mCompanyEditButton, this.mOnClickListener);
        NoDBClickUtil.a(this.mHomeLayout, this.mOnClickListener);
        NoDBClickUtil.a(this.mCompanyLayout, this.mOnClickListener);
        ahm.a(new Runnable() {
            public final void run() {
                final POI pOICompany = DriveUtil.getPOICompany();
                final POI pOIHome = DriveUtil.getPOIHome();
                aho.a(new Runnable() {
                    public final void run() {
                        RouteFragmentHomeAddressView.this.setCompanyPOI(pOICompany);
                        RouteFragmentHomeAddressView.this.setHomePOI(pOIHome);
                    }
                });
            }
        });
    }

    public void setHomePOI(POI poi) {
        if (poi == null) {
            this.mHomeEditView.setVisibility(8);
            this.mHomeTV.setVisibility(0);
            this.mHomeTV.setText(R.string.click_for_setting);
        } else {
            this.mHomeEditView.setVisibility(0);
        }
        if (!(this.mHomePOI == null || poi == null || (this.mHomePOI.getName().equals(poi.getName()) && this.mHomePOI.getPoint().x == poi.getPoint().x && this.mHomePOI.getPoint().y == poi.getPoint().y))) {
            this.mHomeTmcLayout.setTag(null);
        }
        if (poi != null) {
            this.mHomePOI = (ISearchPoiData) poi.as(ISearchPoiData.class);
        }
        if (poi != null) {
            this.mHomeTV.setVisibility(0);
            String name = poi.getName();
            String addr = poi.getAddr();
            if (name == null || name.length() <= 0) {
                if (addr != null && addr.length() > 0) {
                    this.mHomeTV.setText(addr);
                }
                return;
            }
            this.mHomeTV.setText(name);
            return;
        }
        this.mHomeTmcLayout.setTag(null);
    }

    public void setCompanyPOI(POI poi) {
        if (poi == null) {
            this.mCompanyEditView.setVisibility(8);
            this.mCompanyTV.setVisibility(0);
            this.mCompanyTV.setText(R.string.click_for_setting);
        } else {
            this.mCompanyEditView.setVisibility(0);
        }
        if (!(this.mCompanyPOI == null || poi == null || (this.mCompanyPOI.getName().equals(poi.getName()) && this.mCompanyPOI.getPoint().x == poi.getPoint().x && this.mCompanyPOI.getPoint().y == poi.getPoint().y))) {
            this.mCompanyTmcLayout.setTag(null);
        }
        if (poi != null) {
            this.mCompanyPOI = (ISearchPoiData) poi.as(ISearchPoiData.class);
        }
        if (this.mCompanyPOI != null) {
            this.mCompanyTV.setVisibility(0);
            String name = this.mCompanyPOI.getName();
            String addr = this.mCompanyPOI.getAddr();
            if (name == null || name.length() <= 0) {
                if (addr != null && addr.length() > 0) {
                    this.mCompanyTV.setText(addr);
                }
                return;
            }
            this.mCompanyTV.setText(name);
            return;
        }
        this.mCompanyTmcLayout.setTag(null);
    }

    public void showTmcBar(AbstractBasePage abstractBasePage) {
        this.mShowTmcBar = true;
        this.mPage = abstractBasePage;
        ahm.a(new Runnable() {
            public final void run() {
                final POI pOICompany = DriveUtil.getPOICompany();
                final POI pOIHome = DriveUtil.getPOIHome();
                aho.a(new Runnable() {
                    public final void run() {
                        if (RouteFragmentHomeAddressView.this.mPage.isAlive()) {
                            if (!DriveUtil.isSamePoi(pOIHome, RouteFragmentHomeAddressView.this.mHomePOI)) {
                                RouteFragmentHomeAddressView.this.setHomePOI(pOIHome);
                                if (RouteFragmentHomeAddressView.this.mHomePOI == null) {
                                    RouteFragmentHomeAddressView.this.mHomeTmcBar.setVisibility(8);
                                    RouteFragmentHomeAddressView.this.mHomeTmcTV.setVisibility(4);
                                } else {
                                    RouteFragmentHomeAddressView.this.mHomeTmcBar.setImageDrawable(null);
                                    RouteFragmentHomeAddressView.this.mHomeTmcBar.setVisibility(0);
                                    RouteFragmentHomeAddressView.this.mHomeTmcTV.setVisibility(0);
                                    RouteFragmentHomeAddressView.this.mHomeTmcTV.setText(null);
                                }
                            }
                            if (!DriveUtil.isSamePoi(pOICompany, RouteFragmentHomeAddressView.this.mCompanyPOI)) {
                                RouteFragmentHomeAddressView.this.setCompanyPOI(pOICompany);
                                if (RouteFragmentHomeAddressView.this.mCompanyPOI == null) {
                                    RouteFragmentHomeAddressView.this.mCompanyTmcBar.setVisibility(8);
                                    RouteFragmentHomeAddressView.this.mCompanyTmcTV.setVisibility(4);
                                } else {
                                    RouteFragmentHomeAddressView.this.mCompanyTmcBar.setImageDrawable(null);
                                    RouteFragmentHomeAddressView.this.mCompanyTmcBar.setVisibility(0);
                                    RouteFragmentHomeAddressView.this.mCompanyTmcTV.setVisibility(0);
                                    RouteFragmentHomeAddressView.this.mCompanyTmcTV.setText(null);
                                }
                            }
                            if (!(RouteFragmentHomeAddressView.this.mHomeTmcLayout == null || RouteFragmentHomeAddressView.this.mCompanyTmcLayout == null)) {
                                RouteFragmentHomeAddressView.this.requestHomeCompanyTmcBar();
                            }
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0152  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void requestHomeCompanyTmcBar() {
        /*
            r11 = this;
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = r11.mPage
            if (r0 == 0) goto L_0x000d
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r0 = r11.mPage
            boolean r0 = r0.isAlive()
            if (r0 != 0) goto L_0x000d
            return
        L_0x000d:
            com.autonavi.common.model.POI r0 = r11.getCurLocation()
            if (r0 == 0) goto L_0x0187
            boolean r1 = r11.mShowTmcBar
            if (r1 == 0) goto L_0x0187
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r1 = r11.mHomePOI
            r2 = 0
            r3 = 1195593728(0x47435000, float:50000.0)
            r4 = 1148846080(0x447a0000, float:1000.0)
            r5 = 4
            r6 = 1
            r7 = 8
            r8 = 0
            if (r1 == 0) goto L_0x007d
            android.widget.TextView r1 = r11.mHomeTV
            r1.setVisibility(r8)
            com.autonavi.common.model.GeoPoint r1 = r0.getPoint()
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r9 = r11.mHomePOI
            com.autonavi.common.model.GeoPoint r9 = r9.getPoint()
            float r1 = defpackage.cfe.a(r1, r9)
            int r9 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r9 < 0) goto L_0x007d
            int r9 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r9 <= 0) goto L_0x0052
            int r9 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r9 >= 0) goto L_0x0052
            boolean r1 = r11.isHomePOIChanged()
            if (r1 != 0) goto L_0x0050
            android.widget.TextView r1 = r11.mHomeTV
            r1.setVisibility(r7)
        L_0x0050:
            r1 = 1
            goto L_0x007e
        L_0x0052:
            int r9 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r9 > 0) goto L_0x0068
            android.widget.TextView r1 = r11.mHomeTV
            int r9 = com.autonavi.minimap.R.string.superfromto_home_extmin
            r1.setText(r9)
            android.widget.TextView r1 = r11.mHomeTmcTV
            r1.setVisibility(r5)
            android.widget.ImageView r1 = r11.mHomeTmcBar
            r1.setVisibility(r7)
            goto L_0x007d
        L_0x0068:
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 < 0) goto L_0x007d
            android.widget.TextView r1 = r11.mHomeTV
            int r9 = com.autonavi.minimap.R.string.superfromto_home_extmax
            r1.setText(r9)
            android.widget.TextView r1 = r11.mHomeTmcTV
            r1.setVisibility(r5)
            android.widget.ImageView r1 = r11.mHomeTmcBar
            r1.setVisibility(r7)
        L_0x007d:
            r1 = 0
        L_0x007e:
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r9 = r11.mCompanyPOI
            if (r9 == 0) goto L_0x0109
            com.autonavi.common.model.GeoPoint r9 = r0.getPoint()
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r10 = r11.mCompanyPOI
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            float r9 = defpackage.cfe.a(r9, r10)
            int r2 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r2 < 0) goto L_0x0109
            android.widget.TextView r2 = r11.mCompanyTV
            r2.setVisibility(r8)
            int r2 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x00e6
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
            java.lang.String r3 = "HH"
            r2.<init>(r3)
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat
            java.lang.String r4 = "mm"
            r3.<init>(r4)
            java.util.Date r4 = new java.util.Date
            r4.<init>()
            java.lang.String r2 = r2.format(r4)
            int r2 = java.lang.Integer.parseInt(r2)
            java.lang.String r3 = r3.format(r4)
            int r3 = java.lang.Integer.parseInt(r3)
            r4 = 20
            if (r2 >= r4) goto L_0x00d4
            r4 = 3
            if (r2 < r4) goto L_0x00d4
            if (r2 != r4) goto L_0x00cc
            if (r3 != 0) goto L_0x00cc
            goto L_0x00d4
        L_0x00cc:
            android.widget.TextView r2 = r11.mCompanyTV
            int r3 = com.autonavi.minimap.R.string.superfromto_company_extmin
            r2.setText(r3)
            goto L_0x00db
        L_0x00d4:
            android.widget.TextView r2 = r11.mCompanyTV
            int r3 = com.autonavi.minimap.R.string.superfromto_company_extmin_late_tips
            r2.setText(r3)
        L_0x00db:
            android.widget.TextView r2 = r11.mCompanyTmcTV
            r2.setVisibility(r5)
            android.widget.ImageView r2 = r11.mCompanyTmcBar
            r2.setVisibility(r7)
            goto L_0x0109
        L_0x00e6:
            int r2 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r2 < 0) goto L_0x00fc
            android.widget.TextView r2 = r11.mCompanyTV
            int r3 = com.autonavi.minimap.R.string.superfromto_company_extmax
            r2.setText(r3)
            android.widget.TextView r2 = r11.mCompanyTmcTV
            r2.setVisibility(r5)
            android.widget.ImageView r2 = r11.mCompanyTmcBar
            r2.setVisibility(r7)
            goto L_0x0109
        L_0x00fc:
            boolean r2 = r11.isCompanyPOIChanged()
            if (r2 != 0) goto L_0x0107
            android.widget.TextView r2 = r11.mCompanyTV
            r2.setVisibility(r7)
        L_0x0107:
            r2 = 1
            goto L_0x010a
        L_0x0109:
            r2 = 0
        L_0x010a:
            r3 = 0
            if (r1 == 0) goto L_0x0121
            boolean r1 = r11.mShowTmcBar
            if (r1 == 0) goto L_0x0121
            boolean r1 = r11.isHomePOIChanged()
            if (r1 == 0) goto L_0x0121
            ta r1 = new ta
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r4 = r11.mHomePOI
            com.autonavi.minimap.drive.route.CalcRouteScene r5 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_HOME_TMC
            r1.<init>(r0, r4, r5)
            goto L_0x0122
        L_0x0121:
            r1 = r3
        L_0x0122:
            if (r2 == 0) goto L_0x0137
            boolean r2 = r11.mShowTmcBar
            if (r2 == 0) goto L_0x0137
            boolean r2 = r11.isCompanyPOIChanged()
            if (r2 == 0) goto L_0x0137
            ta r3 = new ta
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r2 = r11.mCompanyPOI
            com.autonavi.minimap.drive.route.CalcRouteScene r4 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_COMPANY_TMC
            r3.<init>(r0, r2, r4)
        L_0x0137:
            com.amap.bundle.aosservice.request.AosRequest r0 = r11.mTmcRequest
            if (r0 == 0) goto L_0x0144
            in r0 = defpackage.in.a()
            com.amap.bundle.aosservice.request.AosRequest r2 = r11.mTmcRequest
            r0.a(r2)
        L_0x0144:
            esb r0 = defpackage.esb.a.a
            java.lang.Class<ms> r2 = defpackage.ms.class
            esc r0 = r0.a(r2)
            ms r0 = (defpackage.ms) r0
            if (r0 == 0) goto L_0x0187
            if (r1 == 0) goto L_0x0166
            if (r3 == 0) goto L_0x0166
            com.autonavi.common.Callback<tc> r2 = r11.innerCallback
            r4 = 2
            ta[] r4 = new defpackage.ta[r4]
            r4[r8] = r1
            r4[r6] = r3
            com.amap.bundle.aosservice.request.AosRequest r0 = r0.a(r2, r4)
            r11.mTmcRequest = r0
            return
        L_0x0166:
            if (r3 == 0) goto L_0x0177
            if (r1 != 0) goto L_0x0177
            com.autonavi.common.Callback<tc> r1 = r11.innerCallback
            ta[] r2 = new defpackage.ta[r6]
            r2[r8] = r3
            com.amap.bundle.aosservice.request.AosRequest r0 = r0.a(r1, r2)
            r11.mTmcRequest = r0
            return
        L_0x0177:
            if (r1 == 0) goto L_0x0187
            if (r3 != 0) goto L_0x0187
            com.autonavi.common.Callback<tc> r2 = r11.innerCallback
            ta[] r3 = new defpackage.ta[r6]
            r3[r8] = r1
            com.amap.bundle.aosservice.request.AosRequest r0 = r0.a(r2, r3)
            r11.mTmcRequest = r0
        L_0x0187:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.widget.RouteFragmentHomeAddressView.requestHomeCompanyTmcBar():void");
    }

    /* access modifiers changed from: private */
    public void onTmcRequestSuccess(tc tcVar) {
        List<ICarRouteResult> c = tcVar.c();
        if (c == null || c.size() < 0) {
            onTmcRequesetFailed();
        } else if (c.size() == 1) {
            handleSingleResult(tcVar, c, c.get(0));
        } else {
            ICarRouteResult iCarRouteResult = c.get(0);
            ICarRouteResult iCarRouteResult2 = c.get(1);
            if (iCarRouteResult == null && iCarRouteResult2 != null) {
                handleSingleResult(tcVar, c, iCarRouteResult2);
            } else if (iCarRouteResult != null && iCarRouteResult2 == null) {
                handleSingleResult(tcVar, c, iCarRouteResult);
            } else if (iCarRouteResult == null || iCarRouteResult2 == null) {
                onTmcRequesetFailed();
            } else {
                tn.a().a(iCarRouteResult.getCalcRouteScene(), iCarRouteResult.getCalcRouteResult());
                tn.a().a(iCarRouteResult2.getCalcRouteScene(), iCarRouteResult2.getCalcRouteResult());
                NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
                NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
                NavigationResult naviResultData2 = iCarRouteResult.getNaviResultData();
                NavigationPath focusNavigationPath2 = iCarRouteResult2.getFocusNavigationPath();
                if ((naviResultData == null || naviResultData.mPaths == null || naviResultData.mPathNum <= 0 || focusNavigationPath == null) && (naviResultData2 == null || naviResultData2.mPaths == null || naviResultData2.mPathNum <= 0 || focusNavigationPath2 == null)) {
                    onTmcRequesetFailed();
                } else if (!tcVar.a() && tcVar.errorCode != OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode()) {
                    if (tcVar.errorCode == -1 || tcVar.errorCode == 13) {
                        ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_NEEDREBOOT.getStrCodeMsg());
                    }
                } else if (naviResultData == null && naviResultData2 == null) {
                    onTmcRequesetFailed();
                } else {
                    if (tcVar.errorCode == OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode() && !iCarRouteResult.isM_bNative()) {
                        ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getStrCodeMsg());
                    }
                    handleTmcRequsetData(c);
                }
            }
        }
    }

    private void handleSingleResult(tc tcVar, List<ICarRouteResult> list, ICarRouteResult iCarRouteResult) {
        if (iCarRouteResult != null) {
            tn.a().a(iCarRouteResult.getCalcRouteScene(), iCarRouteResult.getCalcRouteResult());
            NavigationResult naviResultData = iCarRouteResult.getNaviResultData();
            NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
            if (naviResultData == null || naviResultData.mPaths == null || naviResultData.mPathNum <= 0 || focusNavigationPath == null) {
                this.mCallHandler.sendEmptyMessage(2);
            } else if (!tcVar.a() && tcVar.errorCode != OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode()) {
                if (tcVar.errorCode == -1 || tcVar.errorCode == 13) {
                    ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_NEEDREBOOT.getStrCodeMsg());
                }
            } else if (iCarRouteResult.getNaviResultData() == null) {
                onTmcRequesetFailed();
            } else {
                if (tcVar.errorCode == OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getnCode() && !iCarRouteResult.isM_bNative()) {
                    ToastHelper.showLongToast(OfflineMsgCode.CODE_NATIVE_TBT_SUCCESS.getStrCodeMsg());
                }
                handleTmcRequsetData(list);
            }
        } else {
            onTmcRequesetFailed();
        }
    }

    private void handleTmcRequsetData(List<ICarRouteResult> list) {
        if (list != null && list.size() > 0) {
            for (IRouteResultData next : list) {
                if (next != null) {
                    ICarRouteResult iCarRouteResult = (ICarRouteResult) next;
                    if (iCarRouteResult.getNaviResultData() != null) {
                        iCarRouteResult.setFocusRouteIndex(0);
                        if (iCarRouteResult.getFocusNavigationPath() != null) {
                            carRouteListenerCallback(iCarRouteResult);
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private boolean isHomePOIChanged() {
        return this.mHomeTmcLayout.getTag() == null || !((Boolean) this.mHomeTmcLayout.getTag()).booleanValue();
    }

    private boolean isCompanyPOIChanged() {
        return this.mCompanyTmcLayout.getTag() == null || !((Boolean) this.mCompanyTmcLayout.getTag()).booleanValue();
    }

    private void carRouteListenerCallback(ICarRouteResult iCarRouteResult) {
        if (iCarRouteResult.getNaviResultData() != null && iCarRouteResult.getFocusNavigationPath() != null && !this.mIsFinished && this.mShowTmcBar) {
            if (CalcRouteScene.SCENE_HOME_TMC == iCarRouteResult.getCalcRouteScene()) {
                loadCarRouteResult(iCarRouteResult, this.mHomeTmcTV, this.mHomeTmcBar);
                this.mHomeTmcLayout.setTag(Boolean.TRUE);
                this.mHomeTV.setVisibility(8);
                this.mHomeTmcTV.setTag(this.mHomePOI);
            } else if (CalcRouteScene.SCENE_COMPANY_TMC == iCarRouteResult.getCalcRouteScene()) {
                loadCarRouteResult(iCarRouteResult, this.mCompanyTmcTV, this.mCompanyTmcBar);
                this.mCompanyTmcLayout.setTag(Boolean.TRUE);
                this.mCompanyTV.setVisibility(8);
                this.mCompanyTmcTV.setTag(this.mCompanyPOI);
            }
        }
    }

    private void loadCarRouteResult(ICarRouteResult iCarRouteResult, TextView textView, ImageView imageView) {
        if (iCarRouteResult != null && this.mAsyncTrafficBarLoader != null) {
            defpackage.dji.a aVar = new defpackage.dji.a(imageView, iCarRouteResult);
            imageView.setImageDrawable(new defpackage.dji.b(aVar));
            aVar.execute(new NavigationPath[0]);
            NavigationPath focusNavigationPath = iCarRouteResult.getFocusNavigationPath();
            if (focusNavigationPath != null) {
                textView.setText(getRestTime(focusNavigationPath.mCostTime));
            }
            imageView.setVisibility(0);
        }
    }

    public String getRestTime(int i) {
        int i2 = (i + 30) / 60;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 / 60);
            sb.append(" 小时");
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 <= 0) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(i3);
            sb3.append(" 分钟");
            return sb3.toString();
        } else if (i2 == 0) {
            return "<1 分钟";
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i2);
            sb4.append(" 分钟");
            return sb4.toString();
        }
    }

    public void onResume() {
        this.mIsFinished = false;
    }

    public void cancelTmcRequest() {
        this.mIsFinished = true;
        if (this.mTmcRequest != null) {
            this.mTmcRequest.cancel();
        }
    }

    private POI getCurLocation() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (latestPosition == null) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        createPOI.setName("我的位置");
        createPOI.setPoint(latestPosition);
        return createPOI;
    }
}

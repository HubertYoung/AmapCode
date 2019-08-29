package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.ae.bl.map.IMapPageConstant;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RotateTextView;
import com.autonavi.minimap.route.common.view.RouteBanner;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter;
import com.autonavi.minimap.route.train.controller.TrainUIStatusController;
import com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.inter.ITrainRouteResult;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import com.autonavi.minimap.route.train.net.TrainTicketPurchaseCallback;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.SubmitOrderRequest;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.uc.webview.export.internal.SDKFactory;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

public class TrainPlanListPage extends AbstractBasePage<ejm> implements axa, bgm, IVoiceCmdResponder, LocationNone, launchModeSingleTask {
    /* access modifiers changed from: private */
    public TrainPlanListAdapter A;
    private ArrayList<TrainPlanBaseInfoItem> B;
    private eim C;
    /* access modifiers changed from: private */
    public a D = null;
    private ebl E;
    /* access modifiers changed from: private */
    public CompatDialog F;
    private int G = -1;
    private POI H;
    public ListView a;
    public View b;
    public boolean c;
    public RelativeLayout d;
    public boolean e;
    public boolean f = true;
    public TrainUIStatusController g;
    public AosRequest h;
    public RouteBanner i;
    public ein j;
    public boolean k = false;
    public eip l;
    public boolean m;
    public boolean n;
    public boolean o;
    public axe p = new axe() {
        public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
            switch (AnonymousClass9.a[iRouteHeaderEvent.ordinal()]) {
                case 1:
                    TrainPlanListPage.this.j();
                    break;
                case 2:
                    TrainPlanListPage.this.f = true;
                    break;
                case 3:
                    TrainPlanListPage.this.k();
                    break;
            }
            return false;
        }
    };
    final anq q = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
        }
    };
    final anq r = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
            String str;
            if (z) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    ant e = iAccountService.e();
                    if (iAccountService.a()) {
                        if (e == null) {
                            str = null;
                        } else {
                            str = e.h;
                        }
                        if (!TextUtils.isEmpty(str)) {
                            TrainPlanListPage.b(TrainPlanListPage.this, TrainPlanListPage.this.w);
                            return;
                        }
                        iAccountService.a(TrainPlanListPage.this.getPageContext(), TrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone), TrainPlanListPage.this.q);
                    }
                }
            }
        }
    };
    private LayoutInflater s;
    private View t;
    private View u;
    private PullToRefreshListView v;
    /* access modifiers changed from: private */
    public TrainPlanBaseInfoItem w;
    private boolean x = false;
    /* access modifiers changed from: private */
    public boolean y = true;
    private eio z;

    /* renamed from: com.autonavi.minimap.route.train.page.TrainPlanListPage$9 reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] a = new int[IRouteHeaderEvent.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent[] r0 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.START_CLICK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.EXCHANGE_CLICK     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.autonavi.bundle.routecommon.model.IRouteHeaderEvent r1 = com.autonavi.bundle.routecommon.model.IRouteHeaderEvent.END_CLICK     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.train.page.TrainPlanListPage.AnonymousClass9.<clinit>():void");
        }
    }

    class TrainTicketPurchasingListener implements Callback<eji> {
        private TrainTicketPurchasingListener() {
        }

        /* synthetic */ TrainTicketPurchasingListener(TrainPlanListPage trainPlanListPage, byte b) {
            this();
        }

        public void callback(eji eji) {
            if (TrainPlanListPage.this.F != null && TrainPlanListPage.this.F.isShowing()) {
                TrainPlanListPage.this.F.dismiss();
            }
            if (eji == null || !TrainPlanListPage.this.isAlive()) {
                return;
            }
            if (eji.errorCode == 14) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(TrainPlanListPage.this.getPageContext(), TrainPlanListPage.this.r);
                    ToastHelper.showLongToast(TrainPlanListPage.this.getString(R.string.train_plan_not_login));
                }
            } else if (eji.errorCode == 44) {
                IAccountService iAccountService2 = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService2 != null) {
                    iAccountService2.a(TrainPlanListPage.this.getPageContext(), TrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone), TrainPlanListPage.this.q);
                    ToastHelper.showLongToast(TrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone));
                }
            } else if (TextUtils.isEmpty(eji.a)) {
                ToastHelper.showLongToast(TrainPlanListPage.this.getString(R.string.train_plan_elong_network_error));
            } else {
                aja aja = new aja(eji.a);
                aja.b = new ajf() {
                    public final boolean g() {
                        return true;
                    }

                    public final boolean h() {
                        return true;
                    }
                };
                ((ejm) TrainPlanListPage.this.mPresenter).a();
                aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                if (aix != null) {
                    aix.a(AMapPageUtil.getPageContext(), aja);
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (TrainPlanListPage.this.F != null && TrainPlanListPage.this.F.isShowing()) {
                TrainPlanListPage.this.F.dismiss();
            }
            if (z || !(th instanceof UnknownHostException)) {
                ToastHelper.showLongToast(TrainPlanListPage.this.getString(R.string.train_plan_network_status_error_callback));
            } else {
                ToastHelper.showLongToast(TrainPlanListPage.this.getString(R.string.train_plan_network_status_error_not_reach));
            }
        }
    }

    static class a extends ecs<TrainPlanListPage> {
        public a(TrainPlanListPage trainPlanListPage) {
            super(trainPlanListPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            TrainPlanListPage trainPlanListPage = (TrainPlanListPage) a();
            if (trainPlanListPage != null && message.what == 1) {
                trainPlanListPage.a();
            }
        }
    }

    public static boolean a(POI poi, POI poi2) {
        return (poi == null || poi2 == null) ? false : true;
    }

    public void finishSelf() {
    }

    public long getScene() {
        return IMapPageConstant.BL_MAP_FLAG_MAP_STATE_LOCK_ROTATE;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return IMapPageConstant.BL_MAP_FLAG_MAP_STATE_LOCK_ROTATE;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        this.s = LayoutInflater.from(context);
        this.D = new a(this);
        this.s = this.s;
        this.t = this.s.inflate(R.layout.train_plan_info_list, null);
        setContentView(this.t);
        this.g = new TrainUIStatusController(this, this.t);
        this.u = this.t.findViewById(R.id.train_plan_non_list_item_tips);
        this.v = (PullToRefreshListView) this.t.findViewById(R.id.pull_to_train_plan_info_listview);
        this.v.setMode(Mode.PULL_FROM_START);
        this.v.setFootershowflag(false);
        this.v.setVisibility(0);
        this.i = (RouteBanner) this.t.findViewById(R.id.train_banner);
        this.a = (ListView) this.v.getRefreshableView();
        this.v.mLvFooterLoadingFrame.removeView(this.v.mFooterLoadingView);
        this.i.loadbanner(RouteBanner.REQUEST_KEY_TRAIN);
        this.v.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            }

            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (!NetworkReachability.b()) {
                    if (!TrainPlanListPage.this.e) {
                        if (TrainPlanListPage.this.D != null) {
                            Message obtainMessage = TrainPlanListPage.this.D.obtainMessage();
                            obtainMessage.what = 1;
                            TrainPlanListPage.this.D.sendMessageDelayed(obtainMessage, 200);
                        }
                        if (TrainPlanListPage.this.b != null) {
                            TrainPlanListPage.this.b.setVisibility(4);
                        }
                        TrainPlanListPage.this.a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                    }
                } else if (!TrainPlanListPage.this.e) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    TrainPlanListPage.this.e = true;
                    if (TrainPlanListPage.this.h != null && !TrainPlanListPage.this.h.isCanceled()) {
                        TrainPlanListPage.this.h.cancel();
                    }
                    TrainPlanListPage.this.h = eje.a(TrainPlanListPage.this.getContext(), ((ejm) TrainPlanListPage.this.mPresenter).a, ((ejm) TrainPlanListPage.this.mPresenter).b, false, simpleDateFormat.format(TrainPlanListPage.this.j.h), TrainPlanListPage.this);
                }
            }
        });
        this.b = this.t.findViewById(R.id.train_plan_bottom_condition_view);
        this.d = (RelativeLayout) this.s.inflate(R.layout.train_plan_filter_layout, null);
        this.j = new ein(this);
        ein ein = this.j;
        View view = this.t;
        ein.a = (TextView) view.findViewById(R.id.yesterday_btn);
        ein.d = view.findViewById(R.id.today_container);
        ein.b = (RotateTextView) view.findViewById(R.id.today_btn);
        ein.c = (TextView) view.findViewById(R.id.tomorrow_btn);
        this.E = new ebl(this);
        this.E.a = this.a;
        this.E.b = this.t.findViewById(R.id.slide_list_shadow);
        this.E.a();
    }

    public final void a(RequestStatus requestStatus) {
        if (this.g != null) {
            this.g.a(requestStatus);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:0x01a5  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0297  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0369  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.autonavi.common.PageBundle r7) {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x0004
            r0 = r7
            goto L_0x0008
        L_0x0004:
            com.autonavi.common.PageBundle r0 = r6.getArguments()
        L_0x0008:
            eqc r1 = defpackage.eqc.a.a
            boolean r1 = r1.b
            if (r1 == 0) goto L_0x0090
            if (r0 == 0) goto L_0x0090
            java.lang.String r1 = "bundle_key_token"
            int r2 = r6.G
            int r1 = r0.getInt(r1, r2)
            r6.G = r1
            java.lang.String r1 = "bundle_key_poi_end"
            java.lang.Object r1 = r0.getObject(r1)
            if (r1 == 0) goto L_0x002c
            java.lang.String r1 = "bundle_key_poi_end"
            java.lang.Object r1 = r0.getObject(r1)
            com.autonavi.common.model.POI r1 = (com.autonavi.common.model.POI) r1
            r6.H = r1
        L_0x002c:
            android.view.View r1 = r6.getContentView()
            android.view.ViewParent r1 = r1.getParent()
            axd r1 = (defpackage.axd) r1
            com.autonavi.bundle.routecommon.inter.IRouteUI r1 = r1.getRouteInputUI()
            if (r1 == 0) goto L_0x0090
            boolean r1 = r1.o()
            if (r1 == 0) goto L_0x0090
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r6.mPresenter
            ejm r1 = (defpackage.ejm) r1
            com.autonavi.common.model.POI r1 = r1.a
            if (r1 == 0) goto L_0x0090
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r6.mPresenter
            ejm r1 = (defpackage.ejm) r1
            com.autonavi.common.model.POI r1 = r1.b
            if (r1 != 0) goto L_0x0053
            goto L_0x0090
        L_0x0053:
            com.autonavi.common.model.POI r1 = r6.H
            com.autonavi.map.fragmentcontainer.page.IPresenter r2 = r6.mPresenter
            ejm r2 = (defpackage.ejm) r2
            com.autonavi.common.model.POI r2 = r2.b
            boolean r1 = defpackage.bnx.a(r1, r2)
            if (r1 == 0) goto L_0x0070
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r6.mPresenter
            ejm r1 = (defpackage.ejm) r1
            com.autonavi.common.model.POI r2 = r6.H
            r1.a(r2)
            boolean r1 = r6.o
            r6.b(r1)
            goto L_0x0090
        L_0x0070:
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r6.mPresenter
            ejm r1 = (defpackage.ejm) r1
            com.autonavi.common.model.POI r2 = r6.H
            r1.a(r2)
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r6.mPresenter
            ejm r1 = (defpackage.ejm) r1
            ejm$b r2 = r1.c
            if (r2 == 0) goto L_0x0090
            ejm$b r2 = r1.c
            ejm$b$a r3 = new ejm$b$a
            r3.<init>()
            r2.a(r3)
            ejm$b r1 = r1.c
            r1.a()
        L_0x0090:
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0096
            r3 = 1
            goto L_0x0097
        L_0x0096:
            r3 = 0
        L_0x0097:
            if (r7 == 0) goto L_0x009b
            r7 = 1
            goto L_0x009c
        L_0x009b:
            r7 = 0
        L_0x009c:
            if (r3 == 0) goto L_0x00a8
            java.lang.String r4 = "bundle_is_result_page"
            boolean r4 = r0.containsKey(r4)
            if (r4 == 0) goto L_0x00a8
            r6.x = r2
        L_0x00a8:
            if (r3 == 0) goto L_0x00b8
            java.lang.String r4 = "bundle_is_from_which_page"
            boolean r4 = r0.containsKey(r4)
            if (r4 == 0) goto L_0x00b8
            java.lang.String r4 = "bundle_is_from_which_page"
            int r1 = r0.getInt(r4, r1)
        L_0x00b8:
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            if (r1 == r2) goto L_0x00cc
            r5 = 2
            if (r1 != r5) goto L_0x00c3
            goto L_0x00cc
        L_0x00c3:
            java.lang.String r1 = "type"
            java.lang.String r5 = "2"
            r4.put(r1, r5)     // Catch:{ JSONException -> 0x00d5 }
            goto L_0x00d9
        L_0x00cc:
            java.lang.String r1 = "type"
            java.lang.String r5 = "1"
            r4.put(r1, r5)     // Catch:{ JSONException -> 0x00d5 }
            goto L_0x00d9
        L_0x00d5:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00d9:
            java.lang.String r1 = "P00248"
            java.lang.String r5 = "B001"
            com.amap.bundle.statistics.LogManager.actionLogV2(r1, r5, r4)
            ein r1 = r6.j
            java.lang.String r4 = r1.g
            if (r4 == 0) goto L_0x00fa
            if (r3 == 0) goto L_0x00fa
            java.lang.String r3 = "bundle_selected_date_key"
            boolean r3 = r0.containsKey(r3)
            if (r3 == 0) goto L_0x00fa
            java.lang.String r3 = "bundle_selected_date_key"
            java.lang.Object r3 = r0.get(r3)
            java.lang.String r3 = (java.lang.String) r3
            r1.g = r3
        L_0x00fa:
            java.lang.String r3 = r1.g     // Catch:{ ParseException -> 0x0157 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ ParseException -> 0x0157 }
            if (r3 != 0) goto L_0x0124
            boolean r3 = r1.e     // Catch:{ ParseException -> 0x0157 }
            if (r3 != 0) goto L_0x0124
            com.autonavi.minimap.route.common.view.RotateTextView r3 = r1.b     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = r1.g     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = defpackage.ein.a(r4)     // Catch:{ ParseException -> 0x0157 }
            r3.setText(r4)     // Catch:{ ParseException -> 0x0157 }
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = "yyyy-MM-dd"
            java.util.Locale r5 = java.util.Locale.CHINA     // Catch:{ ParseException -> 0x0157 }
            r3.<init>(r4, r5)     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = r1.g     // Catch:{ ParseException -> 0x0157 }
            java.util.Date r3 = r3.parse(r4)     // Catch:{ ParseException -> 0x0157 }
            r1.h = r3     // Catch:{ ParseException -> 0x0157 }
            goto L_0x0179
        L_0x0124:
            java.util.Date r3 = r1.h     // Catch:{ ParseException -> 0x0157 }
            if (r3 != 0) goto L_0x0147
            java.util.Date r3 = new java.util.Date     // Catch:{ ParseException -> 0x0157 }
            ejt r4 = defpackage.ejt.a()     // Catch:{ ParseException -> 0x0157 }
            long r4 = r4.b()     // Catch:{ ParseException -> 0x0157 }
            r3.<init>(r4)     // Catch:{ ParseException -> 0x0157 }
            r1.h = r3     // Catch:{ ParseException -> 0x0157 }
            com.autonavi.minimap.route.common.view.RotateTextView r3 = r1.b     // Catch:{ ParseException -> 0x0157 }
            java.util.Date r4 = r1.h     // Catch:{ ParseException -> 0x0157 }
            long r4 = r4.getTime()     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = defpackage.axt.a(r4)     // Catch:{ ParseException -> 0x0157 }
            r3.setText(r4)     // Catch:{ ParseException -> 0x0157 }
            goto L_0x0179
        L_0x0147:
            com.autonavi.minimap.route.common.view.RotateTextView r3 = r1.b     // Catch:{ ParseException -> 0x0157 }
            java.util.Date r4 = r1.h     // Catch:{ ParseException -> 0x0157 }
            long r4 = r4.getTime()     // Catch:{ ParseException -> 0x0157 }
            java.lang.String r4 = defpackage.axt.a(r4)     // Catch:{ ParseException -> 0x0157 }
            r3.setText(r4)     // Catch:{ ParseException -> 0x0157 }
            goto L_0x0179
        L_0x0157:
            r3 = move-exception
            r3.printStackTrace()
            java.util.Date r3 = new java.util.Date
            ejt r4 = defpackage.ejt.a()
            long r4 = r4.b()
            r3.<init>(r4)
            r1.h = r3
            com.autonavi.minimap.route.common.view.RotateTextView r3 = r1.b
            java.util.Date r1 = r1.h
            long r4 = r1.getTime()
            java.lang.String r1 = defpackage.axt.a(r4)
            r3.setText(r1)
        L_0x0179:
            ein r1 = r6.j
            ein$1 r3 = new ein$1
            r3.<init>()
            android.view.View r4 = r1.d
            r4.setOnClickListener(r3)
            android.widget.TextView r4 = r1.a
            r4.setOnClickListener(r3)
            android.widget.TextView r1 = r1.c
            r1.setOnClickListener(r3)
            android.view.View r1 = r6.getContentView()
            int r3 = com.autonavi.minimap.R.id.train_plan_non_list_item_tips
            android.view.View r1 = r1.findViewById(r3)
            com.autonavi.minimap.route.train.page.TrainPlanListPage$2 r3 = new com.autonavi.minimap.route.train.page.TrainPlanListPage$2
            r3.<init>()
            r1.setOnClickListener(r3)
            eim r1 = r6.C
            if (r1 != 0) goto L_0x0228
            eim r1 = new eim
            r1.<init>(r6)
            r6.C = r1
            eim r1 = r6.C
            android.view.View r3 = r6.t
            int r4 = com.autonavi.minimap.R.id.train_list_sort_by_time_checkbox
            android.view.View r4 = r3.findViewById(r4)
            android.widget.CheckBox r4 = (android.widget.CheckBox) r4
            r1.d = r4
            int r4 = com.autonavi.minimap.R.id.train_list_sort_by_time_textview
            android.view.View r4 = r3.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r1.e = r4
            int r4 = com.autonavi.minimap.R.id.train_list_sort_by_ticket_remind_checkbox
            android.view.View r4 = r3.findViewById(r4)
            android.widget.CheckBox r4 = (android.widget.CheckBox) r4
            r1.f = r4
            int r4 = com.autonavi.minimap.R.id.train_list_sort_by_ticket_remain_textview
            android.view.View r4 = r3.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r1.g = r4
            int r4 = com.autonavi.minimap.R.id.train_plan_filter_more
            android.view.View r4 = r3.findViewById(r4)
            r1.h = r4
            int r4 = com.autonavi.minimap.R.id.filter_more_select_view
            android.view.View r4 = r3.findViewById(r4)
            android.widget.ImageView r4 = (android.widget.ImageView) r4
            r1.i = r4
            int r4 = com.autonavi.minimap.R.id.filter_more_select_text
            android.view.View r3 = r3.findViewById(r4)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.j = r3
            eim r1 = r6.C
            android.widget.CheckBox r3 = r1.d
            eim$1 r4 = new eim$1
            r4.<init>()
            r3.setOnClickListener(r4)
            android.widget.TextView r3 = r1.e
            eim$2 r4 = new eim$2
            r4.<init>()
            r3.setOnClickListener(r4)
            android.widget.CheckBox r3 = r1.f
            eim$3 r4 = new eim$3
            r4.<init>()
            r3.setOnClickListener(r4)
            android.widget.TextView r3 = r1.g
            eim$4 r4 = new eim$4
            r4.<init>()
            r3.setOnClickListener(r4)
            android.view.View r3 = r1.h
            eim$5 r4 = new eim$5
            r4.<init>()
            r3.setOnClickListener(r4)
        L_0x0228:
            eio r1 = new eio
            android.widget.RelativeLayout r3 = r6.d
            r1.<init>(r3, r6)
            r6.z = r1
            eip r1 = new eip
            r1.<init>(r6)
            r6.l = r1
            eip r1 = r6.l
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r3 = r6.A
            r1.f = r3
            eip r1 = r6.l
            android.widget.RelativeLayout r3 = r6.d
            r1.b = r3
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.cancel_filter_more
            android.view.View r3 = r3.findViewById(r4)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.c = r3
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.confirm_filter_more
            android.view.View r3 = r3.findViewById(r4)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.d = r3
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_plan_filter_cancel_mask
            android.view.View r3 = r3.findViewById(r4)
            r1.e = r3
            eip r1 = r6.l
            eio r3 = r6.z
            r1.g = r3
            eip r1 = r6.l
            android.widget.TextView r3 = r1.c
            eip$1 r4 = new eip$1
            r4.<init>()
            r3.setOnClickListener(r4)
            android.view.View r3 = r1.e
            eip$2 r4 = new eip$2
            r4.<init>()
            r3.setOnClickListener(r4)
            android.widget.TextView r3 = r1.d
            eip$3 r4 = new eip$3
            r4.<init>()
            r3.setOnClickListener(r4)
            eip$4 r3 = new eip$4
            r3.<init>()
            r1.h = r3
            android.widget.RelativeLayout r3 = r1.b
            if (r3 == 0) goto L_0x0366
            android.widget.RelativeLayout r3 = r1.b
            if (r3 == 0) goto L_0x02dc
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_type_filter_no_condition
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_type_filter_condition_0
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_type_filter_condition_1
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_type_filter_condition_2
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_type_filter_condition_3
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
        L_0x02dc:
            android.widget.RelativeLayout r3 = r1.b
            if (r3 == 0) goto L_0x0321
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_departure_filter_no_condition
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_departure_filter_condition_0
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_departure_filter_condition_1
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_departure_filter_condition_2
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_departure_filter_condition_3
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
        L_0x0321:
            android.widget.RelativeLayout r3 = r1.b
            if (r3 == 0) goto L_0x0366
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_arrival_filter_no_condition
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_arrival_filter_condition_0
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_arrival_filter_condition_1
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_arrival_filter_condition_2
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r4 = r1.h
            r3.setOnClickListener(r4)
            android.widget.RelativeLayout r3 = r1.b
            int r4 = com.autonavi.minimap.R.id.train_arrival_filter_condition_3
            android.view.View r3 = r3.findViewById(r4)
            android.view.View$OnClickListener r1 = r1.h
            r3.setOnClickListener(r1)
        L_0x0366:
            r1 = 0
            if (r0 == 0) goto L_0x0377
            java.lang.String r1 = "bundle_train_plan_key"
            java.lang.Object r1 = r0.get(r1)
            java.lang.String r3 = "bundle_train_plan_service_switch"
            boolean r0 = r0.getBoolean(r3, r2)
            r6.y = r0
        L_0x0377:
            if (r1 == 0) goto L_0x0381
            boolean r0 = r1 instanceof java.util.ArrayList
            if (r0 == 0) goto L_0x0381
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r6.B = r1
        L_0x0381:
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r6.mPresenter
            ejm r0 = (defpackage.ejm) r0
            com.autonavi.common.model.POI r0 = r0.a
            if (r0 == 0) goto L_0x03f1
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r6.mPresenter
            ejm r0 = (defpackage.ejm) r0
            com.autonavi.common.model.POI r0 = r0.b
            if (r0 != 0) goto L_0x0392
            goto L_0x03f1
        L_0x0392:
            boolean r0 = r6.f
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r1 = r6.A
            if (r1 != 0) goto L_0x03b3
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r0 = new com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter
            android.content.Context r1 = r6.getContext()
            java.util.ArrayList<com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem> r2 = r6.B
            r0.<init>(r1, r2)
            r6.A = r0
            eip r0 = r6.l
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r1 = r6.A
            r0.f = r1
            android.widget.ListView r0 = r6.a
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r1 = r6.A
            r0.setAdapter(r1)
            goto L_0x03cc
        L_0x03b3:
            if (r0 == 0) goto L_0x03c5
            r6.h()
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r0 = r6.A
            java.util.ArrayList<com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem> r1 = r6.B
            r0.setOriginalTrainList(r1)
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r0 = r6.A
            r0.refreshFilterCondition()
            goto L_0x03cc
        L_0x03c5:
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r0 = r6.A
            java.util.ArrayList<com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem> r1 = r6.B
            r0.setOriginalTrainList(r1)
        L_0x03cc:
            eim r0 = r6.C
            if (r0 == 0) goto L_0x03dc
            eim r0 = r6.C
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r1 = r6.A
            r0.a = r1
            eim r0 = r6.C
            java.util.ArrayList<com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem> r1 = r6.B
            r0.b = r1
        L_0x03dc:
            android.widget.ListView r0 = r6.a
            com.autonavi.minimap.route.train.page.TrainPlanListPage$6 r1 = new com.autonavi.minimap.route.train.page.TrainPlanListPage$6
            r1.<init>()
            r0.setOnItemClickListener(r1)
            if (r7 == 0) goto L_0x03eb
            r6.c()
        L_0x03eb:
            ein r7 = r6.j
            r7.a()
            return
        L_0x03f1:
            com.autonavi.minimap.route.train.controller.TrainUIStatusController$RequestStatus r7 = com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus.FAILED_NO_RESULT
            r6.a(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.train.page.TrainPlanListPage.a(com.autonavi.common.PageBundle):void");
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public final void a() {
        if (this.v != null) {
            this.v.onRefreshComplete();
        }
    }

    public final void b() {
        if (this.C != null) {
            this.C.c();
        }
    }

    public final void c() {
        if (this.A != null && this.u != null) {
            a();
            this.A.refreshList();
            if (!this.A.isEmpty()) {
                this.a.setSelection(0);
            }
            if (this.A.getOriginalTrainListSize() > 0 && this.A.getCount() == 0) {
                a(RequestStatus.FAILED_FILTER_NO_RESULT);
            } else if (this.A.getOriginalTrainListSize() == 0) {
                a(RequestStatus.FAILED_NO_RESULT);
            } else {
                a(RequestStatus.SUCCESS);
            }
        }
    }

    public final void d() {
        if (this.i != null) {
            this.i.setbannerShowStatus();
        }
    }

    private void h() {
        if (this.C != null) {
            this.C.b();
        }
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        this.f = false;
        this.e = false;
        if (this.v != null && this.v.isRefreshing()) {
            a(RequestStatus.SUCCESS);
            this.v.onRefreshComplete();
        }
        if (iRouteResultData instanceof ITrainRouteResult) {
            ITrainRouteResult iTrainRouteResult = (ITrainRouteResult) iRouteResultData;
            this.A.setOriginalTrainList(iTrainRouteResult.getTrainPlanInfoResult());
            this.B = iTrainRouteResult.getTrainPlanInfoResult();
            if (this.C != null) {
                this.C.a = this.A;
                this.C.b = this.B;
            }
            this.y = iTrainRouteResult.isNeedServiceSwitch();
            this.j.i = false;
        }
        this.j.d();
        this.j.a();
        c();
    }

    public final void a(RouteType routeType, int i2, String str) {
        this.e = false;
        a();
        if (this.b != null) {
            this.b.setVisibility(4);
        }
        if (i2 / 10 == 4) {
            int i3 = i2 % 10;
            if (i3 == 1) {
                a(RequestStatus.FAILED_NO_RESULT);
            } else if (i3 == 2) {
                a(RequestStatus.FAILED_NO_RESULT);
            } else if (i3 == 3) {
                a(RequestStatus.FAILED_SERVER_ERROR);
            } else {
                a(RequestStatus.FAILED_NO_RESULT);
            }
        } else {
            a(RequestStatus.FAILED_NET_ERROR);
        }
        this.j.i = false;
        this.j.d();
        this.j.a();
    }

    public final void e() {
        this.B = null;
        this.A = new TrainPlanListAdapter(getContext(), this.B);
        if (this.l != null) {
            this.l.f = this.A;
        }
        if (this.a != null) {
            this.a.setAdapter(this.A);
        }
        if (this.C != null) {
            this.C.a = this.A;
            this.C.b = this.B;
        }
    }

    public final void a(Throwable th, boolean z2) {
        this.e = false;
        a();
        if (this.b != null) {
            this.b.setVisibility(4);
        }
        this.j.i = false;
        this.j.d();
        this.j.a();
        a(RequestStatus.FAILED_NET_ERROR);
    }

    private void i() {
        this.e = false;
        a();
    }

    /* access modifiers changed from: private */
    public boolean j() {
        ebm.a(4);
        this.f = true;
        return true;
    }

    /* access modifiers changed from: private */
    public boolean k() {
        ebm.a(4);
        this.f = true;
        return true;
    }

    public final void f() {
        this.i.setVisibility(8);
        boolean z2 = false;
        if (!NetworkReachability.b()) {
            if (this.b != null) {
                this.b.setVisibility(4);
            }
            this.B = null;
            this.A = new TrainPlanListAdapter(getContext(), this.B);
            this.l.f = this.A;
            this.a.setAdapter(this.A);
            if (this.C != null) {
                this.C.a = this.A;
                this.C.b = this.B;
            }
            if (!this.f) {
                a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                return;
            }
            a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD_NO_DATE);
            this.f = false;
        } else if (!a(((ejm) this.mPresenter).a, ((ejm) this.mPresenter).b)) {
            if (this.b != null) {
                this.b.setVisibility(4);
            }
            a(RequestStatus.FAILED_NO_RESULT);
        } else {
            POI poi = ((ejm) this.mPresenter).a;
            POI poi2 = ((ejm) this.mPresenter).b;
            if (!(poi == null || poi2 == null)) {
                z2 = bnx.a(poi, poi2);
            }
            if (z2) {
                if (this.b != null) {
                    this.b.setVisibility(4);
                }
                a(RequestStatus.FAILED_NO_RESULT);
                return;
            }
            String str = Token.SEPARATOR;
            if (this.j.h == null) {
                try {
                    this.j.h = new Date(ejt.a().b());
                } catch (Exception unused) {
                }
            } else {
                str = new SimpleDateFormat("yyyy-MM-dd").format(this.j.h);
            }
            String str2 = str;
            if (!this.f) {
                a(RequestStatus.LOADING);
            } else {
                a(RequestStatus.LOADING_NO_DATE);
            }
            IRouteUI routeInputUI = ((axd) getContentView().getParent()).getRouteInputUI();
            g();
            this.h = eje.a(AMapAppGlobal.getApplication(), ((ejm) this.mPresenter).a, ((ejm) this.mPresenter).b, false, str2, new eaj(this, routeInputUI, str2, (ejm) this.mPresenter));
        }
    }

    public final void g() {
        if (this.h != null && !this.h.isCanceled()) {
            this.h.cancel();
        }
        i();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0075 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0076  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(boolean r8) {
        /*
            r7 = this;
            if (r8 == 0) goto L_0x000e
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r8 = r7.A
            if (r8 == 0) goto L_0x000e
            r7.h()
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter r8 = r7.A
            r8.refreshFilterCondition()
        L_0x000e:
            ein r8 = r7.j
            r8.a()
            r7.b()
            eio r8 = r7.z
            if (r8 == 0) goto L_0x001f
            eio r8 = r7.z
            r8.a()
        L_0x001f:
            ein r8 = r7.j
            boolean r8 = r8.e
            if (r8 == 0) goto L_0x00b2
            com.autonavi.map.fragmentcontainer.page.IPresenter r8 = r7.mPresenter
            ejm r8 = (defpackage.ejm) r8
            com.autonavi.common.model.POI r8 = r8.a
            if (r8 == 0) goto L_0x00b2
            com.autonavi.map.fragmentcontainer.page.IPresenter r8 = r7.mPresenter
            ejm r8 = (defpackage.ejm) r8
            com.autonavi.common.model.POI r8 = r8.b
            if (r8 == 0) goto L_0x00b2
            ein r8 = r7.j
            java.util.Date r8 = r8.h
            if (r8 == 0) goto L_0x00b2
            java.text.SimpleDateFormat r8 = new java.text.SimpleDateFormat
            java.lang.String r0 = "yyyy-MM-dd"
            r8.<init>(r0)
            ein r0 = r7.j
            com.autonavi.minimap.route.common.view.RotateTextView r1 = r0.b
            java.lang.CharSequence r1 = r1.getText()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0072
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            com.autonavi.minimap.route.common.view.RotateTextView r4 = r0.b
            java.lang.CharSequence r4 = r4.getText()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.String r0 = r0.f()
            int r0 = r1.compareTo(r0)
            if (r0 != 0) goto L_0x0070
            goto L_0x0072
        L_0x0070:
            r0 = 0
            goto L_0x0073
        L_0x0072:
            r0 = 1
        L_0x0073:
            if (r0 == 0) goto L_0x0076
            return
        L_0x0076:
            r7.e = r2
            ein r0 = r7.j
            r0.e = r3
            ein r0 = r7.j
            com.autonavi.minimap.route.common.view.RotateTextView r1 = r0.b
            java.lang.String r2 = r0.f()
            r1.setText(r2)
            r0.a()
            com.autonavi.minimap.route.train.controller.TrainUIStatusController$RequestStatus r0 = com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus.LOADING
            r7.a(r0)
            r7.g()
            android.content.Context r1 = r7.getContext()
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r7.mPresenter
            ejm r0 = (defpackage.ejm) r0
            com.autonavi.common.model.POI r2 = r0.a
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r7.mPresenter
            ejm r0 = (defpackage.ejm) r0
            com.autonavi.common.model.POI r3 = r0.b
            r4 = 0
            ein r0 = r7.j
            java.util.Date r0 = r0.h
            java.lang.String r5 = r8.format(r0)
            r6 = r7
            com.amap.bundle.aosservice.request.AosRequest r8 = defpackage.eje.a(r1, r2, r3, r4, r5, r6)
            r7.h = r8
        L_0x00b2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.train.page.TrainPlanListPage.a(boolean):void");
    }

    public final void b(boolean z2) {
        if (defpackage.eqc.a.a.b && this.n) {
            if (z2) {
                d.a.a(this.G, 10000, (String) null);
            } else {
                d.a.a(this.G, (int) SDKFactory.getCoreType, (String) null);
            }
            defpackage.eqc.a.a.b = false;
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ejm(this);
    }

    static /* synthetic */ void b(TrainPlanListPage trainPlanListPage, TrainPlanBaseInfoItem trainPlanBaseInfoItem) {
        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();
        submitOrderRequest.b = trainPlanBaseInfoItem.trainDepartureName;
        submitOrderRequest.c = trainPlanBaseInfoItem.trainArrivalName;
        ein ein = trainPlanListPage.j;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar instance = Calendar.getInstance();
        instance.setTime(ein.h);
        submitOrderRequest.d = simpleDateFormat.format(instance.getTime());
        submitOrderRequest.e = trainPlanBaseInfoItem.trip;
        TrainTicketPurchaseCallback trainTicketPurchaseCallback = new TrainTicketPurchaseCallback(new eji(), new TrainTicketPurchasingListener(trainPlanListPage, 0));
        trainPlanListPage.F = aav.a(submitOrderRequest, AMapAppGlobal.getApplication().getString(R.string.progress_message));
        trainPlanListPage.F.show();
        TrainRequestHolder.getInstance().sendSubmitOrder(submitOrderRequest, trainTicketPurchaseCallback);
    }
}

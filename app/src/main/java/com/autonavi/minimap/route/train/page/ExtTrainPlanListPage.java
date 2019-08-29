package com.autonavi.minimap.route.train.page;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.utils.view.OneClickListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
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
import com.autonavi.minimap.route.train.controller.ExtTrainUIStatusController;
import com.autonavi.minimap.route.train.controller.ExtTrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.inter.ITrainRouteResult;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import com.autonavi.minimap.route.train.net.TrainTicketPurchaseCallback;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.SubmitOrderRequest;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtTrainPlanListPage extends AbstractBasePage<ejj> implements axa, LocationNone, launchModeSingleTask {
    private TextView A;
    private boolean B;
    /* access modifiers changed from: private */
    public boolean C;
    /* access modifiers changed from: private */
    public boolean D = false;
    /* access modifiers changed from: private */
    public boolean E = true;
    /* access modifiers changed from: private */
    public ExtTrainUIStatusController F;
    /* access modifiers changed from: private */
    public Date G;
    private String H;
    /* access modifiers changed from: private */
    public POI I;
    /* access modifiers changed from: private */
    public POI J;
    /* access modifiers changed from: private */
    public AosRequest K;
    /* access modifiers changed from: private */
    public ArrayList<TrainPlanBaseInfoItem> L;
    private ebl M;
    /* access modifiers changed from: private */
    public a N;
    /* access modifiers changed from: private */
    public CheckBox O;
    /* access modifiers changed from: private */
    public TextView P;
    /* access modifiers changed from: private */
    public CheckBox Q;
    /* access modifiers changed from: private */
    public TextView R;
    private View S;
    private RouteBanner T;
    private OnClickListener U = new OnClickListener() {
        public final void onClick(View view) {
            ExtTrainPlanListPage.a(view.getId());
            ExtTrainPlanListPage.a(ExtTrainPlanListPage.this, view.getId());
            ExtTrainPlanListPage.this.k();
        }
    };
    /* access modifiers changed from: private */
    public CompatDialog V;
    public ViewGroup a;
    public boolean b;
    public RelativeLayout c;
    public boolean d;
    eio e;
    TrainPlanListAdapter f;
    public boolean g = false;
    OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            boolean[] q = ExtTrainPlanListPage.q(ExtTrainPlanListPage.this);
            boolean[] r = ExtTrainPlanListPage.r(ExtTrainPlanListPage.this);
            boolean[] s = ExtTrainPlanListPage.s(ExtTrainPlanListPage.this);
            int id = view.getId();
            if (id == R.id.train_type_filter_no_condition || id == R.id.train_type_filter_condition_0 || id == R.id.train_type_filter_condition_1 || id == R.id.train_type_filter_condition_2 || id == R.id.train_type_filter_condition_3) {
                if (id == R.id.train_type_filter_no_condition) {
                    if (q[0]) {
                        ExtTrainPlanListPage.this.a(new boolean[]{true, false, false, false, false});
                    } else {
                        q[0] = true;
                        ExtTrainPlanListPage.this.a(q);
                    }
                } else if (q[1] && q[2] && q[3] && q[4]) {
                    ExtTrainPlanListPage.this.a(new boolean[]{true, false, false, false, false});
                } else if (q[1] || q[2] || q[3] || q[4]) {
                    q[0] = false;
                    ExtTrainPlanListPage.this.a(q);
                } else {
                    ExtTrainPlanListPage.this.a(new boolean[]{true, false, false, false, false});
                }
            }
            if (id == R.id.train_departure_filter_no_condition || id == R.id.train_departure_filter_condition_0 || id == R.id.train_departure_filter_condition_1 || id == R.id.train_departure_filter_condition_2 || id == R.id.train_departure_filter_condition_3) {
                if (id == R.id.train_departure_filter_no_condition) {
                    if (r[0]) {
                        ExtTrainPlanListPage.this.b(new boolean[]{true, false, false, false, false});
                    } else {
                        r[0] = true;
                        ExtTrainPlanListPage.this.b(r);
                    }
                } else if (r[1] && r[2] && r[3] && r[4]) {
                    ExtTrainPlanListPage.this.b(new boolean[]{true, false, false, false, false});
                } else if (r[1] || r[2] || r[3] || r[4]) {
                    r[0] = false;
                    ExtTrainPlanListPage.this.b(r);
                } else {
                    ExtTrainPlanListPage.this.b(new boolean[]{true, false, false, false, false});
                }
            }
            if (id == R.id.train_arrival_filter_no_condition || id == R.id.train_arrival_filter_condition_0 || id == R.id.train_arrival_filter_condition_1 || id == R.id.train_arrival_filter_condition_2 || id == R.id.train_arrival_filter_condition_3) {
                if (id == R.id.train_arrival_filter_no_condition) {
                    if (s[0]) {
                        ExtTrainPlanListPage.this.c(new boolean[]{true, false, false, false, false});
                    } else {
                        s[0] = true;
                        ExtTrainPlanListPage.this.c(s);
                    }
                } else if (s[1] && s[2] && s[3] && s[4]) {
                    ExtTrainPlanListPage.this.c(new boolean[]{true, false, false, false, false});
                } else if (s[1] || s[2] || s[3] || s[4]) {
                    s[0] = false;
                    ExtTrainPlanListPage.this.c(s);
                } else {
                    ExtTrainPlanListPage.this.c(new boolean[]{true, false, false, false, false});
                }
            }
            if (ExtTrainPlanListPage.this.e != null) {
                ExtTrainPlanListPage.this.e.a();
            }
        }
    };
    final anq i = new anq() {
        public final void loginOrBindCancel() {
        }

        public final void onComplete(boolean z) {
        }
    };
    final anq j = new anq() {
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
                            ExtTrainPlanListPage.b(ExtTrainPlanListPage.this, ExtTrainPlanListPage.this.u);
                            return;
                        }
                        iAccountService.a(ExtTrainPlanListPage.this.getPageContext(), ExtTrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone), (anq) new anq() {
                            public final void loginOrBindCancel() {
                            }

                            public final void onComplete(boolean z) {
                            }
                        });
                    }
                }
            }
        }
    };
    private LayoutInflater k;
    private View l;
    private PullToRefreshListView m;
    private ListView n;
    /* access modifiers changed from: private */
    public View o;
    private TitleBar p;
    /* access modifiers changed from: private */
    public TextView q;
    private TextView r;
    private TextView s;
    private View t;
    /* access modifiers changed from: private */
    public TrainPlanBaseInfoItem u;
    private TextView v;
    private RotateTextView w;
    private TextView x;
    private View y;
    private ImageView z;

    class TrainTicketPurchasingListener implements Callback<eji> {
        private TrainTicketPurchasingListener() {
        }

        /* synthetic */ TrainTicketPurchasingListener(ExtTrainPlanListPage extTrainPlanListPage, byte b) {
            this();
        }

        public void callback(eji eji) {
            if (ExtTrainPlanListPage.this.V != null && ExtTrainPlanListPage.this.V.isShowing()) {
                ExtTrainPlanListPage.this.V.dismiss();
            }
            if (eji == null || !ExtTrainPlanListPage.this.isAlive()) {
                return;
            }
            if (eji.errorCode == 14) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(ExtTrainPlanListPage.this.getPageContext(), ExtTrainPlanListPage.this.j);
                    ToastHelper.showLongToast(ExtTrainPlanListPage.this.getString(R.string.train_plan_not_login));
                }
            } else if (eji.errorCode == 44) {
                IAccountService iAccountService2 = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService2 != null) {
                    iAccountService2.a(ExtTrainPlanListPage.this.getPageContext(), ExtTrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone), ExtTrainPlanListPage.this.i);
                    ToastHelper.showLongToast(ExtTrainPlanListPage.this.getString(R.string.train_plan_buy_ticket_bind_phone));
                }
            } else if (!TextUtils.isEmpty(eji.a)) {
                aja aja = new aja(eji.a);
                aja.b = new ajf() {
                    public final boolean g() {
                        return true;
                    }

                    public final boolean h() {
                        return true;
                    }
                };
                ExtTrainPlanListPage.this.g = true;
                aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                if (aix != null) {
                    aix.a((bid) ExtTrainPlanListPage.this, aja);
                }
            }
        }

        public void error(Throwable th, boolean z) {
            if (ExtTrainPlanListPage.this.V != null && ExtTrainPlanListPage.this.V.isShowing()) {
                ExtTrainPlanListPage.this.V.dismiss();
            }
            ExtTrainPlanListPage.this.a(RequestStatus.FAILED_SERVER_ERROR);
        }
    }

    static class a extends ecs<ExtTrainPlanListPage> {
        public a(ExtTrainPlanListPage extTrainPlanListPage) {
            super(extTrainPlanListPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                ExtTrainPlanListPage extTrainPlanListPage = (ExtTrainPlanListPage) a();
                if (extTrainPlanListPage != null && extTrainPlanListPage.isAlive()) {
                    extTrainPlanListPage.d();
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        this.k = LayoutInflater.from(context);
        this.k = this.k;
        View inflate = this.k.inflate(R.layout.train_plan_info_list, null);
        setContentView(inflate);
        this.F = new ExtTrainUIStatusController(this, inflate);
        this.l = inflate.findViewById(R.id.train_plan_non_list_item_tips);
        this.m = (PullToRefreshListView) inflate.findViewById(R.id.pull_to_train_plan_info_listview);
        this.m.setMode(Mode.PULL_FROM_START);
        this.m.setFootershowflag(false);
        this.m.setVisibility(0);
        this.T = (RouteBanner) inflate.findViewById(R.id.train_banner);
        this.n = (ListView) this.m.getRefreshableView();
        this.m.mLvFooterLoadingFrame.removeView(this.m.mFooterLoadingView);
        this.T.loadbanner(RouteBanner.REQUEST_KEY_TRAIN);
        this.m.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            }

            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (!NetworkReachability.b()) {
                    if (!ExtTrainPlanListPage.this.C) {
                        if (ExtTrainPlanListPage.this.N != null) {
                            Message obtainMessage = ExtTrainPlanListPage.this.N.obtainMessage();
                            obtainMessage.what = 1;
                            ExtTrainPlanListPage.this.N.sendMessageDelayed(obtainMessage, 200);
                        }
                        if (ExtTrainPlanListPage.this.o != null) {
                            ExtTrainPlanListPage.this.o.setVisibility(4);
                        }
                        ExtTrainPlanListPage.this.a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                    }
                } else if (!ExtTrainPlanListPage.this.C) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    ExtTrainPlanListPage.this.C = true;
                    ExtTrainPlanListPage.this.g();
                    POI createPOI = POIFactory.createPOI(ExtTrainPlanListPage.this.I.getName(), ExtTrainPlanListPage.this.I.getPoint());
                    POI createPOI2 = POIFactory.createPOI(ExtTrainPlanListPage.this.J.getName(), ExtTrainPlanListPage.this.J.getPoint());
                    createPOI.setId(ExtTrainPlanListPage.this.I.getId());
                    createPOI2.setId(ExtTrainPlanListPage.this.J.getId());
                    ExtTrainPlanListPage.this.K = eje.a(ExtTrainPlanListPage.this.getContext(), createPOI, createPOI2, true, simpleDateFormat.format(ExtTrainPlanListPage.this.G), ExtTrainPlanListPage.this);
                }
            }
        });
        this.M = new ebl(this);
        this.M.a = this.n;
        this.M.b = inflate.findViewById(R.id.slide_list_shadow);
        this.M.a();
        this.p = (TitleBar) inflate.findViewById(R.id.title_bar);
        this.q = (TextView) this.p.findViewById(R.id.title_title);
        this.p.setVisibility(0);
        this.o = inflate.findViewById(R.id.train_plan_bottom_condition_view);
        this.c = (RelativeLayout) this.k.inflate(R.layout.train_plan_filter_layout, null);
        this.r = (TextView) this.c.findViewById(R.id.cancel_filter_more);
        this.s = (TextView) this.c.findViewById(R.id.confirm_filter_more);
        this.t = this.c.findViewById(R.id.train_plan_filter_cancel_mask);
        this.O = (CheckBox) inflate.findViewById(R.id.train_list_sort_by_time_checkbox);
        this.P = (TextView) inflate.findViewById(R.id.train_list_sort_by_time_textview);
        this.Q = (CheckBox) inflate.findViewById(R.id.train_list_sort_by_ticket_remind_checkbox);
        this.R = (TextView) inflate.findViewById(R.id.train_list_sort_by_ticket_remain_textview);
        this.S = inflate.findViewById(R.id.train_plan_filter_more);
        this.v = (TextView) inflate.findViewById(R.id.yesterday_btn);
        this.y = inflate.findViewById(R.id.today_container);
        this.w = (RotateTextView) inflate.findViewById(R.id.today_btn);
        this.x = (TextView) inflate.findViewById(R.id.tomorrow_btn);
        this.z = (ImageView) inflate.findViewById(R.id.filter_more_select_view);
        this.A = (TextView) inflate.findViewById(R.id.filter_more_select_text);
        this.a = (ViewGroup) inflate;
        this.N = new a(this);
    }

    public final void a(RequestStatus requestStatus) {
        StringBuilder sb = new StringBuilder("TrainPlanlistpage updateRequestUI is null?");
        sb.append(this.F == null);
        sb.append("status ");
        sb.append(requestStatus);
        eao.e("tylorvan", sb.toString());
        if (this.F != null) {
            this.F.a(requestStatus);
        }
    }

    public final void a() {
        if (this.c != null && this.b) {
            final View findViewById = this.c.findViewById(R.id.train_plan_filter_cancel_mask);
            final View findViewById2 = this.c.findViewById(R.id.train_plan_filter_content);
            final int measuredHeight = findViewById2.getMeasuredHeight();
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
            ofInt.setDuration(200);
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    findViewById.setAlpha(((float) (200 - intValue)) / 200.0f);
                    findViewById2.setTranslationY((float) ((measuredHeight / 200) * intValue));
                    if (intValue == 200) {
                        ExtTrainPlanListPage.this.c.setVisibility(8);
                    }
                }
            });
            ofInt.start();
        }
    }

    private static float a(String str, float f2) {
        if (str == null || TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f2);
        return textPaint.measureText(str);
    }

    public final void a(PageBundle pageBundle) {
        PageBundle arguments = pageBundle != null ? pageBundle : getArguments();
        int i2 = 0;
        boolean z2 = arguments != null;
        boolean z3 = pageBundle != null;
        if (this.H != null && z2 && arguments.containsKey("bundle_selected_date_key")) {
            this.H = (String) arguments.get("bundle_selected_date_key");
        }
        if (z2 && arguments.containsKey("bundle_start_city_key")) {
            Object obj = arguments.get("bundle_start_city_key");
            if (obj instanceof POI) {
                this.I = (POI) obj;
            }
        }
        if (z2 && arguments.containsKey("bundle_end_city_key")) {
            Object obj2 = arguments.get("bundle_end_city_key");
            if (obj2 instanceof POI) {
                this.J = (POI) obj2;
            }
        }
        if (z2 && arguments.containsKey("bundle_is_result_page")) {
            this.D = true;
        }
        if (z2 && arguments.containsKey("bundle_is_from_which_page")) {
            i2 = arguments.getInt("bundle_is_from_which_page", 0);
        }
        JSONObject jSONObject = new JSONObject();
        if (i2 == 1 || i2 == 2) {
            jSONObject.put("type", "1");
        } else {
            try {
                jSONObject.put("type", "2");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        LogManager.actionLogV2("P00248", "B001", jSONObject);
        try {
            if (!TextUtils.isEmpty(this.H)) {
                StringBuilder sb = new StringBuilder("train list mSelectedDate--->");
                sb.append(this.H);
                eao.e("tylorvan", sb.toString());
                this.w.setText(a(this.H));
                this.G = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(this.H);
            } else if (this.G == null) {
                this.G = new Date(ejt.a().b());
                this.w.setText(axt.a(this.G.getTime()));
            } else {
                this.w.setText(axt.a(this.G.getTime()));
            }
        } catch (ParseException e3) {
            e3.printStackTrace();
            this.G = new Date(ejt.a().b());
            this.w.setText(axt.a(this.G.getTime()));
        }
        this.y.setOnClickListener(this.U);
        this.v.setOnClickListener(this.U);
        this.x.setOnClickListener(this.U);
        getContentView().findViewById(R.id.train_plan_non_list_item_tips).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ExtTrainPlanListPage.this.isAlive() && ExtTrainPlanListPage.this.F != null) {
                    if (ExtTrainPlanListPage.this.F.b == RequestStatus.FAILED_NET_ERROR || ExtTrainPlanListPage.this.F.b == RequestStatus.FAILED_SERVER_ERROR) {
                        ExtTrainPlanListPage.this.f();
                    }
                }
            }
        });
        this.O.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                JSONObject jSONObject = new JSONObject();
                try {
                    if (ExtTrainPlanListPage.this.O.isChecked()) {
                        jSONObject.put("type", "1");
                    } else {
                        jSONObject.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00248", "B005", jSONObject);
                if (((CheckBox) view).isChecked()) {
                    ExtTrainPlanListPage.this.P.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_6));
                    if (ExtTrainPlanListPage.this.f != null && ExtTrainPlanListPage.this.L.size() != 0) {
                        ExtTrainPlanListPage.this.f.sort(1);
                        return;
                    }
                    return;
                }
                ExtTrainPlanListPage.this.P.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_3));
                ExtTrainPlanListPage.this.f.sort(2);
            }
        });
        this.P.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ExtTrainPlanListPage.this.O.setChecked(!ExtTrainPlanListPage.this.O.isChecked());
                JSONObject jSONObject = new JSONObject();
                try {
                    if (ExtTrainPlanListPage.this.O.isChecked()) {
                        jSONObject.put("type", "1");
                    } else {
                        jSONObject.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00248", "B005", jSONObject);
                if (ExtTrainPlanListPage.this.O.isChecked()) {
                    ExtTrainPlanListPage.this.P.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_6));
                    if (ExtTrainPlanListPage.this.f != null && ExtTrainPlanListPage.this.L.size() != 0) {
                        ExtTrainPlanListPage.this.f.sort(1);
                        return;
                    }
                    return;
                }
                ExtTrainPlanListPage.this.P.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_3));
                ExtTrainPlanListPage.this.f.sort(2);
            }
        });
        this.Q.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                JSONObject jSONObject = new JSONObject();
                try {
                    if (ExtTrainPlanListPage.this.Q.isChecked()) {
                        jSONObject.put("type", "1");
                    } else {
                        jSONObject.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00248", "B006", jSONObject);
                if (((CheckBox) view).isChecked()) {
                    ExtTrainPlanListPage.this.R.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_6));
                    if (ExtTrainPlanListPage.this.f != null && ExtTrainPlanListPage.this.L.size() != 0) {
                        ExtTrainPlanListPage.this.f.getFilterCondition().c = true;
                    } else {
                        return;
                    }
                } else {
                    ExtTrainPlanListPage.this.R.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_3));
                    ExtTrainPlanListPage.this.f.getFilterCondition().c = false;
                }
                ExtTrainPlanListPage.this.l();
            }
        });
        this.R.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ExtTrainPlanListPage.this.Q.setChecked(!ExtTrainPlanListPage.this.Q.isChecked());
                JSONObject jSONObject = new JSONObject();
                try {
                    if (ExtTrainPlanListPage.this.Q.isChecked()) {
                        jSONObject.put("type", "1");
                    } else {
                        jSONObject.put("type", "2");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00248", "B006", jSONObject);
                if (ExtTrainPlanListPage.this.Q.isChecked()) {
                    ExtTrainPlanListPage.this.R.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_6));
                    ExtTrainPlanListPage.this.f.getFilterCondition().c = true;
                } else {
                    ExtTrainPlanListPage.this.R.setTextColor(ExtTrainPlanListPage.this.getResources().getColor(R.color.f_c_3));
                    ExtTrainPlanListPage.this.f.getFilterCondition().c = false;
                }
                ExtTrainPlanListPage.this.l();
            }
        });
        this.r.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ExtTrainPlanListPage.this.c != null) {
                    LogManager.actionLogV2("P00248", "B004");
                    ExtTrainPlanListPage.this.a();
                    ExtTrainPlanListPage.this.i();
                }
            }
        });
        this.t.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ExtTrainPlanListPage.this.c != null) {
                    LogManager.actionLogV2("P00248", "B004");
                    ExtTrainPlanListPage.this.a();
                    ExtTrainPlanListPage.this.i();
                }
            }
        });
        this.s.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (ExtTrainPlanListPage.this.c != null) {
                    LogManager.actionLogV2("P00248", "B003");
                    ExtTrainPlanListPage.this.a();
                    if (!(ExtTrainPlanListPage.this.f == null || ExtTrainPlanListPage.this.f.getFilterCondition() == null)) {
                        com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a filterCondition = ExtTrainPlanListPage.this.f.getFilterCondition();
                        filterCondition.d = ExtTrainPlanListPage.q(ExtTrainPlanListPage.this);
                        filterCondition.e = ExtTrainPlanListPage.r(ExtTrainPlanListPage.this);
                        filterCondition.f = ExtTrainPlanListPage.s(ExtTrainPlanListPage.this);
                        ExtTrainPlanListPage.this.l();
                    }
                    ExtTrainPlanListPage.this.i();
                }
            }
        });
        this.S.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV2("P00248", "B002");
                ExtTrainPlanListPage.t(ExtTrainPlanListPage.this);
                ExtTrainPlanListPage.u(ExtTrainPlanListPage.this);
                ExtTrainPlanListPage.this.i();
            }
        });
        if (this.c != null) {
            if (this.c != null) {
                this.c.findViewById(R.id.train_type_filter_no_condition).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_type_filter_condition_0).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_type_filter_condition_1).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_type_filter_condition_2).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_type_filter_condition_3).setOnClickListener(this.h);
            }
            if (this.c != null) {
                this.c.findViewById(R.id.train_departure_filter_no_condition).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_departure_filter_condition_0).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_departure_filter_condition_1).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_departure_filter_condition_2).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_departure_filter_condition_3).setOnClickListener(this.h);
            }
            if (this.c != null) {
                this.c.findViewById(R.id.train_arrival_filter_no_condition).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_arrival_filter_condition_0).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_arrival_filter_condition_1).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_arrival_filter_condition_2).setOnClickListener(this.h);
                this.c.findViewById(R.id.train_arrival_filter_condition_3).setOnClickListener(this.h);
            }
        }
        this.e = new eio(this.c, this);
        this.p.setOnBackClickListener((OnClickListener) new OneClickListener() {
            public final void doClick(View view) {
                if (ExtTrainPlanListPage.this.D) {
                    ExtTrainPlanListPage.this.finish();
                    ExtTrainPlanListPage.this.onBackPressed();
                    return;
                }
                ExtTrainPlanListPage.this.finish();
            }
        });
        this.q.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public final boolean onPreDraw() {
                if (ExtTrainPlanListPage.this.I == null || ExtTrainPlanListPage.this.J == null) {
                    ExtTrainPlanListPage.this.q.setText("-");
                } else {
                    ExtTrainPlanListPage.this.q.getViewTreeObserver().removeOnPreDrawListener(this);
                    ExtTrainPlanListPage.this.q.setText(ExtTrainPlanListPage.a(ExtTrainPlanListPage.this, ExtTrainPlanListPage.this.I.getName(), ExtTrainPlanListPage.this.J.getName()));
                }
                return true;
            }
        });
        Object obj3 = null;
        if (arguments != null) {
            obj3 = arguments.get("bundle_train_plan_key");
            this.E = arguments.getBoolean("bundle_train_plan_service_switch", true);
        }
        if (obj3 != null && (obj3 instanceof ArrayList)) {
            this.L = (ArrayList) obj3;
        }
        if (this.I == null || this.J == null) {
            a(RequestStatus.FAILED_NO_RESULT);
            return;
        }
        boolean z4 = this.d;
        if (this.f == null) {
            this.f = new TrainPlanListAdapter(getContext(), this.L);
            this.n.setAdapter(this.f);
        } else if (z4) {
            m();
            this.f.setOriginalTrainList(this.L);
            this.f.refreshFilterCondition();
        } else {
            this.f.setOriginalTrainList(this.L);
        }
        this.n.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!ExtTrainPlanListPage.this.C) {
                    dys.a((String) "P00248", (String) "B010", (JSONObject) null);
                    if (ExtTrainPlanListPage.this.f == null || !ExtTrainPlanListPage.this.E) {
                        ToastHelper.showToast(ExtTrainPlanListPage.this.getString(R.string.train_plan_elong_network_error));
                    } else if (ExtTrainPlanListPage.this.f.getItem(i) != null) {
                        TrainPlanBaseInfoItem item = ExtTrainPlanListPage.this.f.getItem(i);
                        if (item != null) {
                            ExtTrainPlanListPage.this.u = item;
                            if (!NetworkReachability.b()) {
                                ToastHelper.showToast(ExtTrainPlanListPage.this.getString(R.string.train_plan_network_status_error_not_reach));
                            } else {
                                ExtTrainPlanListPage.b(ExtTrainPlanListPage.this, item);
                            }
                        }
                    }
                }
            }
        });
        if (!this.d && z3) {
            l();
        }
        k();
    }

    private String h() {
        new SimpleDateFormat("MM月dd日 E");
        this.G = new Date(ejt.a().b());
        return axt.a(this.G.getTime());
    }

    /* access modifiers changed from: private */
    public void i() {
        if (this.f != null && this.f.getFilterCondition() != null && this.z != null && this.A != null) {
            com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a filterCondition = this.f.getFilterCondition();
            if (!filterCondition.d[0] || !filterCondition.f[0] || !filterCondition.e[0]) {
                this.z.setImageResource(R.drawable.train_plan_filter_icon_selected);
                this.A.setTextColor(getResources().getColor(R.color.f_c_6));
                return;
            }
            this.z.setImageResource(R.drawable.train_plan_filter_icon);
            this.A.setTextColor(getResources().getColor(R.color.f_c_3));
        }
    }

    public final boolean b() {
        ejt.a().a(this.G.getTime());
        return true;
    }

    private static String a(String str) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        new SimpleDateFormat("MM月dd日 E");
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException unused) {
            date = simpleDateFormat.parse(b((String) "yyyy-MM-dd"));
        }
        return axt.a(date.getTime());
    }

    private static String j() throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        new SimpleDateFormat("MM月dd日 E");
        try {
            date = simpleDateFormat.parse(b((String) "yyyy-MM-dd"));
        } catch (ParseException unused) {
            date = simpleDateFormat.parse(b((String) "yyyy-MM-dd"));
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, ejs.a().a(0) - 1);
        return axt.a(instance.getTime().getTime());
    }

    /* access modifiers changed from: private */
    public void k() {
        try {
            if (a((String) "").compareTo(this.w.getText().toString()) == 0) {
                this.v.setTextColor(getResources().getColor(R.color.f_c_6_a));
            } else {
                this.v.setTextColor(getResources().getColor(R.color.f_c_6));
            }
            if (j().compareTo(this.w.getText().toString()) == 0) {
                this.x.setTextColor(getResources().getColor(R.color.f_c_6_a));
            } else {
                this.x.setTextColor(getResources().getColor(R.color.f_c_6));
            }
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean[] zArr) {
        if (this.c != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_no_condition)).setChecked(true);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_0)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_1)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_2)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_no_condition)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.c.findViewById(R.id.train_type_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean[] zArr) {
        if (this.c != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_no_condition)).setChecked(true);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_0)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_1)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_2)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_no_condition)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.c.findViewById(R.id.train_departure_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(boolean[] zArr) {
        if (this.c != null && zArr != null && zArr.length == 5) {
            if (zArr[0] || (zArr[1] && zArr[2] && zArr[3] && zArr[4])) {
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_no_condition)).setChecked(true);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_0)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_1)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_2)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_3)).setChecked(false);
            } else {
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_no_condition)).setChecked(false);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_0)).setChecked(zArr[1]);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_1)).setChecked(zArr[2]);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_2)).setChecked(zArr[3]);
                ((CheckBox) this.c.findViewById(R.id.train_arrival_filter_condition_3)).setChecked(zArr[4]);
            }
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.f != null && this.l != null) {
            this.f.refreshList();
            if (!this.f.isEmpty()) {
                this.n.setSelection(0);
            }
            if (this.f.getOriginalTrainListSize() > 0 && this.f.getCount() == 0) {
                a(RequestStatus.FAILED_FILTER_NO_RESULT);
            } else if (this.f.getOriginalTrainListSize() == 0) {
                a(RequestStatus.FAILED_NO_RESULT);
            } else {
                a(RequestStatus.SUCCESS);
            }
        }
    }

    public final void c() {
        if (this.T != null) {
            this.T.setbannerShowStatus();
        }
    }

    private void m() {
        this.O.setChecked(false);
        this.P.setTextColor(getResources().getColor(R.color.f_c_3));
        this.Q.setChecked(false);
        this.R.setTextColor(getResources().getColor(R.color.f_c_3));
    }

    public final void a(RouteType routeType, int i2, String str) {
        this.C = false;
        d();
        if (this.o != null) {
            this.o.setVisibility(4);
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
    }

    public final void d() {
        if (this.m != null && this.m.isRefreshing()) {
            this.m.onRefreshComplete();
        }
    }

    public final void e() {
        this.L = null;
        this.f = new TrainPlanListAdapter(getContext(), this.L);
        if (this.n != null) {
            this.n.setAdapter(this.f);
        }
    }

    public final void a(Throwable th, boolean z2) {
        this.C = false;
        d();
        if (this.o != null) {
            this.o.setVisibility(4);
        }
        a(RequestStatus.FAILED_NET_ERROR);
    }

    private void n() {
        this.C = false;
        d();
    }

    public final void f() {
        if (isAlive()) {
            this.T.setVisibility(8);
            if (!NetworkReachability.b()) {
                if (this.o != null) {
                    this.o.setVisibility(4);
                }
                this.f = new TrainPlanListAdapter(getContext(), this.L);
                this.L = null;
                this.n.setAdapter(this.f);
                a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                return;
            }
            boolean z2 = false;
            if (!((this.I == null || this.J == null) ? false : true)) {
                if (this.o != null) {
                    this.o.setVisibility(4);
                }
                a(RequestStatus.FAILED_NO_RESULT);
                return;
            }
            POI poi = this.I;
            POI poi2 = this.J;
            if (!(poi == null || poi2 == null)) {
                z2 = TextUtils.equals(poi.getName(), poi2.getName());
            }
            if (z2) {
                if (this.o != null) {
                    this.o.setVisibility(4);
                }
                a(RequestStatus.FAILED_NO_RESULT);
                return;
            }
            String str = Token.SEPARATOR;
            if (this.G == null) {
                try {
                    this.G = new Date(ejt.a().b());
                } catch (Exception unused) {
                }
            } else {
                str = new SimpleDateFormat("yyyy-MM-dd").format(this.G);
            }
            String str2 = str;
            if (!this.d) {
                a(RequestStatus.LOADING);
            } else {
                a(RequestStatus.LOADING_NO_DATE);
            }
            g();
            POI createPOI = POIFactory.createPOI(this.I.getName(), this.I.getPoint());
            POI createPOI2 = POIFactory.createPOI(this.J.getName(), this.J.getPoint());
            createPOI.setId(this.I.getId());
            createPOI2.setId(this.J.getId());
            this.K = eje.a(AMapAppGlobal.getApplication(), createPOI, createPOI2, true, str2, new eai(this, this.I, this.J, str2));
        }
    }

    public final void g() {
        if (this.K != null && !this.K.isCanceled()) {
            this.K.cancel();
        }
        n();
    }

    private static String b(String str) {
        return new SimpleDateFormat(str, Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    public final void a(boolean z2) {
        if (isAlive()) {
            if (z2 && this.f != null) {
                m();
                this.f.refreshFilterCondition();
            }
            k();
            i();
            if (this.e != null) {
                this.e.a();
            }
            if (!(!this.B || this.I == null || this.J == null || this.G == null)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if (!this.C && !TextUtils.isEmpty(this.w.getText())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.w.getText());
                    if (sb.toString().compareTo(h()) != 0) {
                        this.C = true;
                        this.B = false;
                        this.w.setText(h());
                        k();
                        a(RequestStatus.LOADING);
                        POI createPOI = POIFactory.createPOI(this.I.getName(), this.I.getPoint());
                        POI createPOI2 = POIFactory.createPOI(this.J.getName(), this.J.getPoint());
                        createPOI.setId(this.I.getId());
                        createPOI2.setId(this.J.getId());
                        eje.a(getContext(), createPOI, createPOI2, true, simpleDateFormat.format(this.G), this);
                    }
                }
            }
        }
    }

    public final void a(IRouteResultData iRouteResultData, RouteType routeType) {
        this.C = false;
        if (this.m != null && this.m.isRefreshing()) {
            a(RequestStatus.SUCCESS);
            this.m.onRefreshComplete();
        }
        if (iRouteResultData instanceof ITrainRouteResult) {
            ITrainRouteResult iTrainRouteResult = (ITrainRouteResult) iRouteResultData;
            this.f.setOriginalTrainList(iTrainRouteResult.getTrainPlanInfoResult());
            this.L = iTrainRouteResult.getTrainPlanInfoResult();
            this.E = iTrainRouteResult.isNeedServiceSwitch();
        }
        l();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ejj(this);
    }

    static /* synthetic */ boolean[] q(ExtTrainPlanListPage extTrainPlanListPage) {
        boolean[] zArr = new boolean[5];
        if (extTrainPlanListPage.c == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_type_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_type_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_type_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_type_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_type_filter_condition_3)).isChecked();
        return zArr;
    }

    static /* synthetic */ boolean[] r(ExtTrainPlanListPage extTrainPlanListPage) {
        boolean[] zArr = new boolean[5];
        if (extTrainPlanListPage.c == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_departure_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_departure_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_departure_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_departure_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_departure_filter_condition_3)).isChecked();
        return zArr;
    }

    static /* synthetic */ boolean[] s(ExtTrainPlanListPage extTrainPlanListPage) {
        boolean[] zArr = new boolean[5];
        if (extTrainPlanListPage.c == null) {
            return zArr;
        }
        zArr[0] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_arrival_filter_no_condition)).isChecked();
        zArr[1] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_arrival_filter_condition_0)).isChecked();
        zArr[2] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_arrival_filter_condition_1)).isChecked();
        zArr[3] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_arrival_filter_condition_2)).isChecked();
        zArr[4] = ((CheckBox) extTrainPlanListPage.c.findViewById(R.id.train_arrival_filter_condition_3)).isChecked();
        return zArr;
    }

    static /* synthetic */ void t(ExtTrainPlanListPage extTrainPlanListPage) {
        com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a filterCondition = extTrainPlanListPage.f.getFilterCondition();
        extTrainPlanListPage.a(filterCondition.d);
        extTrainPlanListPage.b(filterCondition.e);
        extTrainPlanListPage.c(filterCondition.f);
    }

    static /* synthetic */ void u(ExtTrainPlanListPage extTrainPlanListPage) {
        if (extTrainPlanListPage.c != null && extTrainPlanListPage.b) {
            final View findViewById = extTrainPlanListPage.c.findViewById(R.id.train_plan_filter_cancel_mask);
            final View findViewById2 = extTrainPlanListPage.c.findViewById(R.id.train_plan_filter_content);
            extTrainPlanListPage.c.bringToFront();
            final int measuredHeight = findViewById2.getMeasuredHeight();
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
            ofInt.setDuration(200);
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    findViewById.setAlpha(((float) intValue) / 200.0f);
                    findViewById2.setTranslationY((float) ((measuredHeight / 200) * (200 - intValue)));
                }
            });
            ofInt.start();
            extTrainPlanListPage.c.setVisibility(0);
        }
    }

    static /* synthetic */ String a(ExtTrainPlanListPage extTrainPlanListPage, String str, String str2) {
        int length;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "-";
        }
        float textSize = extTrainPlanListPage.q.getTextSize();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" - ");
        sb.append(str2);
        float a2 = a(sb.toString(), textSize) - 100.0f;
        float width = VERSION.SDK_INT >= 16 ? (float) extTrainPlanListPage.q.getWidth() : 10000.0f;
        if (a2 < width) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" - ");
            sb2.append(str2);
            return sb2.toString();
        }
        String str3 = str;
        String str4 = str3;
        String str5 = str2;
        String str6 = str5;
        boolean z2 = false;
        boolean z3 = false;
        do {
            int length2 = str3.length();
            length = str5.length();
            if (length2 > length) {
                str3 = str3.substring(0, length2 - 1);
            } else {
                str5 = str5.substring(0, length - 1);
            }
            if (z2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str3);
                sb3.append("…");
                str4 = sb3.toString();
            }
            if (z3) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str5);
                sb4.append("…");
                str6 = sb4.toString();
            }
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str4);
            sb5.append(" - ");
            sb5.append(str6);
            float a3 = a(sb5.toString(), textSize);
            if (length2 > length) {
                length2--;
                z2 = true;
            } else {
                length--;
                z3 = true;
            }
            if (a3 >= width) {
                if (length2 == 0) {
                    break;
                }
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str4);
                sb6.append(" - ");
                sb6.append(str6);
                return sb6.toString();
            }
        } while (length != 0);
        StringBuilder sb7 = new StringBuilder();
        sb7.append(str);
        sb7.append(" - ");
        sb7.append(str2);
        return sb7.toString();
    }

    static /* synthetic */ void a(int i2) {
        if (i2 == R.id.today_btn) {
            dys.a((String) "P00248", (String) "B008", (JSONObject) null);
        } else if (i2 == R.id.yesterday_btn) {
            dys.a((String) "P00248", (String) "B007", (JSONObject) null);
        } else {
            if (i2 == R.id.tomorrow_btn) {
                dys.a((String) "P00248", (String) "B009", (JSONObject) null);
            }
        }
    }

    static /* synthetic */ void a(ExtTrainPlanListPage extTrainPlanListPage, int i2) {
        if (extTrainPlanListPage.w != null && !TextUtils.isEmpty(extTrainPlanListPage.w.getText())) {
            try {
                boolean z2 = a((String) "").compareTo(extTrainPlanListPage.w.getText().toString()) == 0;
                boolean z3 = j().compareTo(extTrainPlanListPage.w.getText().toString()) == 0;
                new SimpleDateFormat("MM月dd日 E");
                Calendar instance = Calendar.getInstance();
                instance.setTime(extTrainPlanListPage.G);
                if (i2 == R.id.yesterday_btn) {
                    if (!z2) {
                        if (!NetworkReachability.b()) {
                            extTrainPlanListPage.a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                            return;
                        }
                        instance.add(5, -1);
                        extTrainPlanListPage.G = instance.getTime();
                        extTrainPlanListPage.b();
                        extTrainPlanListPage.w.setText(axt.a(extTrainPlanListPage.G.getTime()));
                    } else {
                        return;
                    }
                } else if (i2 != R.id.tomorrow_btn) {
                    extTrainPlanListPage.B = true;
                    PageBundle pageBundle = new PageBundle();
                    if (extTrainPlanListPage.G != null) {
                        pageBundle.putLong("bundle_ticket_time", extTrainPlanListPage.G.getTime());
                    }
                    extTrainPlanListPage.startPage(TrainDataPage.class, pageBundle);
                    return;
                } else if (!z3) {
                    if (!NetworkReachability.b()) {
                        extTrainPlanListPage.a(RequestStatus.FAILED_NET_ERROR_SIM_LOAD);
                        return;
                    }
                    instance.add(5, 1);
                    extTrainPlanListPage.G = instance.getTime();
                    extTrainPlanListPage.b();
                    extTrainPlanListPage.w.setText(axt.a(extTrainPlanListPage.G.getTime()));
                } else {
                    return;
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                extTrainPlanListPage.C = true;
                extTrainPlanListPage.a(RequestStatus.LOADING);
                extTrainPlanListPage.g();
                if (extTrainPlanListPage.I == null || extTrainPlanListPage.J == null) {
                    extTrainPlanListPage.a(RequestStatus.FAILED_NO_RESULT);
                    extTrainPlanListPage.C = false;
                    return;
                }
                POI createPOI = POIFactory.createPOI(extTrainPlanListPage.I.getName(), extTrainPlanListPage.I.getPoint());
                POI createPOI2 = POIFactory.createPOI(extTrainPlanListPage.J.getName(), extTrainPlanListPage.J.getPoint());
                createPOI.setId(extTrainPlanListPage.I.getId());
                createPOI2.setId(extTrainPlanListPage.J.getId());
                extTrainPlanListPage.K = eje.a(extTrainPlanListPage.getContext(), createPOI, createPOI2, true, simpleDateFormat.format(extTrainPlanListPage.G), extTrainPlanListPage);
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void b(ExtTrainPlanListPage extTrainPlanListPage, TrainPlanBaseInfoItem trainPlanBaseInfoItem) {
        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();
        submitOrderRequest.b = trainPlanBaseInfoItem.trainDepartureName;
        submitOrderRequest.c = trainPlanBaseInfoItem.trainArrivalName;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar instance = Calendar.getInstance();
        instance.setTime(extTrainPlanListPage.G);
        submitOrderRequest.d = simpleDateFormat.format(instance.getTime());
        submitOrderRequest.e = trainPlanBaseInfoItem.trip;
        TrainTicketPurchaseCallback trainTicketPurchaseCallback = new TrainTicketPurchaseCallback(new eji(), new TrainTicketPurchasingListener(extTrainPlanListPage, 0));
        extTrainPlanListPage.V = aav.a(submitOrderRequest, AMapPageUtil.getAppContext().getString(R.string.progress_message));
        extTrainPlanListPage.V.show();
        TrainRequestHolder.getInstance().sendSubmitOrder(submitOrderRequest, trainTicketPurchaseCallback);
    }
}

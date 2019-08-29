package com.autonavi.minimap.route.coach.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.ae.bl.map.IMapPageConstant;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.coach.CoachRequestHolder;
import com.autonavi.minimap.coach.param.TicketsRequest;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.TicketStockRequest;
import com.autonavi.minimap.route.coach.adapter.CoachPlanAdapter;
import com.autonavi.minimap.route.coach.controller.CoachUIStatusController;
import com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus;
import com.autonavi.minimap.route.coach.inter.ICoachRouteResult;
import com.autonavi.minimap.route.coach.model.CoachPlanData;
import com.autonavi.minimap.route.coach.model.CoachPlanItem;
import com.autonavi.minimap.route.coach.net.CoachRequestHelper$1;
import com.autonavi.minimap.route.coach.net.RouteCoachRequestCallback;
import com.autonavi.minimap.route.coach.util.CoachPurchaseUtil;
import com.autonavi.minimap.route.coach.widget.FilterBottomContainer;
import com.autonavi.minimap.route.coach.widget.FilterBottomContainer.a;
import com.autonavi.minimap.route.train.stationlist.Station;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.CommonTips;
import com.uc.webview.export.internal.SDKFactory;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class CoachResultListPage extends AbstractBasePage<dzr> implements axe, bgm, IVoiceCmdResponder, a {
    View a;
    public CoachUIStatusController b;
    public dzd c;
    public ListView d;
    public View e;
    public FilterBottomContainer f;
    public CoachPlanAdapter g;
    public ICoachRouteResult h;
    public CoachPlanData i;
    public boolean j;
    public Station k;
    public Station l;
    public String m;
    public boolean n;
    public boolean o;
    public boolean p;
    private ebl q;
    private PullToRefreshListView r;
    private View s;
    /* access modifiers changed from: private */
    public CommonTips t;
    private AosRequest u;

    public void finishSelf() {
    }

    public long getScene() {
        return IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE;
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
        this.a = getLayoutInflater().inflate(R.layout.coach_plan_list, null);
        setContentView(this.a);
        View view = this.a;
        this.r = (PullToRefreshListView) view.findViewById(R.id.pull_to_coach_plan_info_listview);
        this.s = view.findViewById(R.id.coach_title_time);
        this.t = (CommonTips) view.findViewById(R.id.coach_shift_banner);
        this.e = getLayoutInflater().inflate(R.layout.coach_filter_container, null);
        if (this.e != null) {
            this.f = (FilterBottomContainer) this.e.findViewById(R.id.coach_filter_bottom);
        }
        this.r.setMode(Mode.PULL_FROM_START);
        this.r.setFootershowflag(false);
        this.r.setVisibility(0);
        this.d = (ListView) this.r.getRefreshableView();
        this.r.mLvFooterLoadingFrame.removeView(this.r.mFooterLoadingView);
        this.g = new CoachPlanAdapter(getContext());
        this.d.setAdapter(this.g);
        this.c = new dzd(this, this.s);
        this.r.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            }

            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (!agr.b(AMapPageUtil.getAppContext())) {
                    if (!CoachResultListPage.this.j) {
                        CoachResultListPage.this.a(ResultStatus.FAILED_NET_ERROR_SIM_LOAD);
                    }
                } else if (!CoachResultListPage.this.j) {
                    CoachResultListPage.this.j = true;
                    CoachResultListPage.this.b((ResultStatus) null);
                    CoachResultListPage.this.a(CoachResultListPage.this.i);
                }
            }
        });
        this.d.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                LogManager.actionLogV2("P00286", "B004");
                if (!CoachResultListPage.this.j) {
                    if (CoachResultListPage.this.g == null || CoachResultListPage.this.i.service_switch != 1) {
                        ToastHelper.showToast(CoachResultListPage.this.getString(R.string.coach_plan_elong_network_error));
                        return;
                    }
                    final CoachPlanItem item = CoachResultListPage.this.g.getItem(i - 1);
                    if (item != null) {
                        if (!NetworkReachability.b()) {
                            ToastHelper.showToast(CoachResultListPage.this.getString(R.string.train_plan_network_status_error_not_reach));
                            return;
                        }
                        dzs.a();
                        final String b = dzs.b(CoachResultListPage.this.c.d.getTime());
                        TicketStockRequest ticketStockRequest = new TicketStockRequest();
                        StringBuilder sb = new StringBuilder();
                        sb.append(item.dateSource);
                        ticketStockRequest.b = sb.toString();
                        ticketStockRequest.c = item.busNumber;
                        ticketStockRequest.g = (int) item.fullPrice;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b);
                        sb2.append(Token.SEPARATOR);
                        sb2.append(item.departTime);
                        ticketStockRequest.f = sb2.toString();
                        ticketStockRequest.e = item.depName;
                        ticketStockRequest.d = item.depCity;
                        ticketStockRequest.i = item.arrName;
                        ticketStockRequest.h = item.arrCity;
                        OrderRequestHolder.getInstance().sendTicketStock(ticketStockRequest, new FalconAosPrepareResponseCallback<JSONObject>() {
                            public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
                                return b(aosByteResponse);
                            }

                            public final /* synthetic */ void a(Object obj) {
                                JSONObject jSONObject = (JSONObject) obj;
                                AMapLog.d("CoachRequest", String.valueOf(jSONObject));
                                if (jSONObject != null) {
                                    int optInt = jSONObject.optInt("code");
                                    int optInt2 = jSONObject.optInt("stock", -1);
                                    if (optInt == 1) {
                                        item.stock = optInt2;
                                        CoachResultListPage.this.g.notifyDataSetChanged();
                                        if (optInt2 >= 0) {
                                            if (optInt2 == 0) {
                                                ToastHelper.showToast("余票不足，请试试其他车次");
                                                return;
                                            } else {
                                                CoachPurchaseUtil.a().a(CoachResultListPage.this, item, b, "", "");
                                                return;
                                            }
                                        }
                                    }
                                    ToastHelper.showToast("服务器开小差了，请稍后再试");
                                    return;
                                }
                                ToastHelper.showToast("服务器开小差了，请稍后再试");
                            }

                            private static JSONObject b(AosByteResponse aosByteResponse) {
                                try {
                                    byte[] bArr = (byte[]) aosByteResponse.getResult();
                                    if (bArr == null || bArr.length <= 0) {
                                        return null;
                                    }
                                    return new JSONObject(new String((byte[]) aosByteResponse.getResult(), "UTF-8").trim());
                                } catch (UnsupportedEncodingException | JSONException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }

                            public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
                                ToastHelper.showToast("服务器开小差了，请稍后再试");
                            }
                        });
                    }
                }
            }
        });
        this.t.setRightViewOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                CoachResultListPage.this.t.setVisibility(8);
            }
        });
        getContentView().findViewById(R.id.coach_plan_non_list_item_tips).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (CoachResultListPage.this.isAlive() && CoachResultListPage.this.b != null && (CoachResultListPage.this.b.b == ResultStatus.FAILED_NET_ERROR || CoachResultListPage.this.b.b == ResultStatus.FAILED_SERVER_ERROR)) {
                    if (!agr.b(AMapPageUtil.getAppContext())) {
                        if (!CoachResultListPage.this.j) {
                            CoachResultListPage.this.a(ResultStatus.FAILED_NET_ERROR_SIM_LOAD);
                        }
                    } else if (!CoachResultListPage.this.j) {
                        CoachResultListPage.this.j = true;
                        CoachResultListPage.this.b(ResultStatus.LOADING);
                    }
                }
            }
        });
        View findViewById = view.findViewById(R.id.slide_list_shadow);
        ListView listView = this.d;
        this.q = new ebl(this);
        this.q.a = listView;
        this.q.b = findViewById;
        this.q.a();
        this.f.setFilterCallback(this);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.k = (Station) arguments.getObject("coach_station_start");
            this.l = (Station) arguments.getObject("coach_station_end");
            a(this.k);
            a(this.l);
        }
        this.b = new CoachUIStatusController(this.a, this.f);
    }

    private void a(Station station) {
        if (station != null) {
            this.m = station.coachAgentID;
        }
    }

    public final void a(CoachPlanData coachPlanData) {
        if (!b(coachPlanData) || TextUtils.isEmpty(coachPlanData.hasShiftType)) {
            this.t.setVisibility(8);
            return;
        }
        this.t.setTipText((CharSequence) coachPlanData.hasShiftType);
        this.t.setVisibility(0);
    }

    private static boolean b(CoachPlanData coachPlanData) {
        if (coachPlanData == null) {
            return false;
        }
        ArrayList<CoachPlanItem> arrayList = coachPlanData.planItems;
        if (arrayList == null || arrayList.size() <= 0 || arrayList.get(0).shiftType != 1) {
            return false;
        }
        return true;
    }

    public static List<String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(Token.SEPARATOR);
        if (split.length == 0) {
            return null;
        }
        return Arrays.asList(split);
    }

    public final ResultStatus a() {
        if (this.b != null) {
            return this.b.b;
        }
        return null;
    }

    public final void a(ResultStatus resultStatus) {
        if (this.b != null) {
            this.b.a(resultStatus);
        }
    }

    public final void b() {
        if (this.r != null && this.r.isRefreshing()) {
            this.r.onRefreshComplete();
        }
    }

    public static void a(ICoachRouteResult iCoachRouteResult) {
        if (iCoachRouteResult != null && iCoachRouteResult.getCoachPlanResult() != null) {
            String str = iCoachRouteResult.getCoachPlanResult().isEnlargeCity;
            if (!TextUtils.isEmpty(str)) {
                ToastHelper.showToast(str);
            }
        }
    }

    public final void b(ResultStatus resultStatus) {
        IRouteUI iRouteUI = ((dzr) this.mPresenter).a;
        if (isAlive() && iRouteUI != null) {
            if (resultStatus != null) {
                a(resultStatus);
            }
            if (this.u != null && !this.u.isCanceled()) {
                this.u.cancel();
                this.j = false;
            }
            this.j = true;
            dzs.a();
            String b2 = dzs.b(this.c.d.getTime());
            TicketsRequest ticketsRequest = null;
            if (resultStatus != null) {
                a((CoachPlanData) null);
            }
            if (((dzr) this.mPresenter).c != null && ((dzr) this.mPresenter).d != null) {
                dzi.a().c = false;
                Context context = getContext();
                POI a2 = a(((dzr) this.mPresenter).c);
                POI a3 = a(((dzr) this.mPresenter).d);
                int d2 = d();
                dzm dzm = new dzm(this, (dzr) this.mPresenter);
                if (!NetworkReachability.b()) {
                    RouteType routeType = RouteType.COACH;
                    dzm.a((Throwable) new Exception(context.getString(R.string.train_plan_network_status_error_not_reach)), false);
                } else {
                    RouteCoachRequestCallback routeCoachRequestCallback = new RouteCoachRequestCallback(new CoachRequestHelper$1(dzm), a2, a3);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm", Locale.CHINA);
                    Date date = new Date(System.currentTimeMillis());
                    String str = "00-00";
                    if (b2.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) == 0) {
                        str = simpleDateFormat.format(date);
                    }
                    String valueOf = String.valueOf(d2);
                    if (!(a2 == null || a3 == null)) {
                        acg acg = (acg) a.a.a(acg.class);
                        if (acg != null) {
                            if (!(acg.j() == null || acg.j().getValue() == RouteType.COACH.getValue())) {
                                a2.setName("");
                            }
                            if (!(acg.k() == null || acg.k().getValue() == RouteType.COACH.getValue())) {
                                a3.setName("");
                            }
                        }
                    }
                    ticketsRequest = new TicketsRequest();
                    ticketsRequest.b = String.valueOf(a2.getPoint().getLongitude());
                    ticketsRequest.c = String.valueOf(a2.getPoint().getLatitude());
                    ticketsRequest.f = a2.getPid();
                    ticketsRequest.h = a2.getName();
                    ticketsRequest.d = String.valueOf(a3.getPoint().getLongitude());
                    ticketsRequest.e = String.valueOf(a3.getPoint().getLatitude());
                    ticketsRequest.g = a3.getPid();
                    ticketsRequest.i = a3.getName();
                    ticketsRequest.k = b2;
                    ticketsRequest.l = str;
                    ticketsRequest.j = "0";
                    ticketsRequest.m = valueOf;
                    CoachRequestHolder.getInstance().sendTickets(ticketsRequest, routeCoachRequestCallback);
                }
                this.u = ticketsRequest;
            }
        }
    }

    private int d() {
        if (!TextUtils.isEmpty(this.m)) {
            try {
                return Integer.parseInt(this.m);
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }

    private static POI a(POI poi) {
        if (poi == null) {
            return null;
        }
        POI clone = poi.clone();
        if (TextUtils.isEmpty(poi.getAdCode()) || !TextUtils.isEmpty(poi.getAddr())) {
            clone.setName("");
        }
        return clone;
    }

    public final void c() {
        if (this.f != null) {
            this.f.resetFilterUI();
        }
    }

    private static boolean e() {
        ebm.a(5);
        return true;
    }

    private static boolean f() {
        ebm.a(5);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(defpackage.dzu r10) {
        /*
            r9 = this;
            com.autonavi.minimap.route.coach.model.CoachPlanData r0 = r9.i
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.autonavi.minimap.route.coach.model.CoachPlanData r0 = r9.i
            java.util.ArrayList<com.autonavi.minimap.route.coach.model.CoachPlanItem> r0 = r0.planItems
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r4 = 0
            r5 = 0
        L_0x001a:
            int r6 = r0.size()
            r7 = 1
            if (r5 >= r6) goto L_0x0034
            java.lang.Object r6 = r0.get(r5)
            com.autonavi.minimap.route.coach.model.CoachPlanItem r6 = (com.autonavi.minimap.route.coach.model.CoachPlanItem) r6
            int r8 = r6.shiftType
            if (r8 != r7) goto L_0x002d
            r7 = r2
            goto L_0x002e
        L_0x002d:
            r7 = r3
        L_0x002e:
            r7.add(r6)
            int r5 = r5 + 1
            goto L_0x001a
        L_0x0034:
            boolean r0 = r10.b
            if (r0 != 0) goto L_0x003e
            java.util.Collections.reverse(r2)
            java.util.Collections.reverse(r3)
        L_0x003e:
            r1.addAll(r2)
            r1.addAll(r3)
            r0 = 0
        L_0x0045:
            int r2 = r1.size()
            if (r0 >= r2) goto L_0x0103
            java.lang.Object r2 = r1.get(r0)
            com.autonavi.minimap.route.coach.model.CoachPlanItem r2 = (com.autonavi.minimap.route.coach.model.CoachPlanItem) r2
            boolean r3 = r10.j()
            if (r3 == 0) goto L_0x00ea
            java.lang.String r3 = r2.depName
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.c
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x0063
        L_0x0061:
            r3 = 1
            goto L_0x009a
        L_0x0063:
            android.content.Context r5 = r10.a
            int r6 = com.autonavi.minimap.R.string.filter_by_none
            java.lang.String r5 = r5.getString(r6)
            java.util.HashMap<java.lang.String, java.lang.Boolean> r6 = r10.c
            boolean r6 = r6.containsKey(r5)
            if (r6 == 0) goto L_0x0082
            java.util.HashMap<java.lang.String, java.lang.Boolean> r6 = r10.c
            java.lang.Object r5 = r6.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x0082
            goto L_0x0061
        L_0x0082:
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.c
            boolean r5 = r5.containsKey(r3)
            if (r5 == 0) goto L_0x0099
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.c
            java.lang.Object r3 = r5.get(r3)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L_0x0099
            goto L_0x0061
        L_0x0099:
            r3 = 0
        L_0x009a:
            if (r3 != 0) goto L_0x00a0
            r1.remove(r0)
            goto L_0x0045
        L_0x00a0:
            java.lang.String r3 = r2.arrName
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.d
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x00ac
        L_0x00aa:
            r3 = 1
            goto L_0x00e3
        L_0x00ac:
            android.content.Context r5 = r10.a
            int r6 = com.autonavi.minimap.R.string.filter_by_none
            java.lang.String r5 = r5.getString(r6)
            java.util.HashMap<java.lang.String, java.lang.Boolean> r6 = r10.d
            boolean r6 = r6.containsKey(r5)
            if (r6 == 0) goto L_0x00cb
            java.util.HashMap<java.lang.String, java.lang.Boolean> r6 = r10.d
            java.lang.Object r5 = r6.get(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x00cb
            goto L_0x00aa
        L_0x00cb:
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.d
            boolean r5 = r5.containsKey(r3)
            if (r5 == 0) goto L_0x00e2
            java.util.HashMap<java.lang.String, java.lang.Boolean> r5 = r10.d
            java.lang.Object r3 = r5.get(r3)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L_0x00e2
            goto L_0x00aa
        L_0x00e2:
            r3 = 0
        L_0x00e3:
            if (r3 != 0) goto L_0x00ea
            r1.remove(r0)
            goto L_0x0045
        L_0x00ea:
            boolean r3 = r10.i()
            if (r3 == 0) goto L_0x00ff
            java.lang.String r3 = r2.departTime
            java.lang.String r2 = r2.lastDepartTime
            boolean r2 = r10.a(r3, r2)
            if (r2 != 0) goto L_0x00ff
            r1.remove(r0)
            goto L_0x0045
        L_0x00ff:
            int r0 = r0 + 1
            goto L_0x0045
        L_0x0103:
            com.autonavi.minimap.route.coach.adapter.CoachPlanAdapter r10 = r9.g
            if (r10 == 0) goto L_0x010c
            com.autonavi.minimap.route.coach.adapter.CoachPlanAdapter r10 = r9.g
            r10.setListDataChanged(r1)
        L_0x010c:
            android.widget.ListView r10 = r9.d
            if (r10 == 0) goto L_0x0115
            android.widget.ListView r10 = r9.d
            r10.setSelection(r4)
        L_0x0115:
            boolean r10 = r1.isEmpty()
            if (r10 == 0) goto L_0x0121
            com.autonavi.minimap.route.coach.controller.CoachUIStatusController$ResultStatus r10 = com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus.FAILED_FILTER_NO_RESULT
            r9.a(r10)
            return
        L_0x0121:
            com.autonavi.minimap.route.coach.controller.CoachUIStatusController$ResultStatus r10 = com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus.SUCCESS
            r9.a(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.coach.page.CoachResultListPage.a(dzu):void");
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if (iRouteHeaderEvent == null) {
            return false;
        }
        switch (iRouteHeaderEvent) {
            case START_CLICK:
                e();
                break;
            case END_CLICK:
                f();
                break;
        }
        return false;
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public final void a(boolean z) {
        PageBundle arguments = getArguments();
        int i2 = -1;
        if (arguments != null) {
            i2 = arguments.getInt("bundle_key_token", -1);
        }
        if (this.o && a.a.b) {
            if (z) {
                d.a.a(i2, 10000, (String) null);
            } else {
                d.a.a(i2, (int) SDKFactory.getCoreType, (String) null);
            }
            a.a.b = false;
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dzr(this);
    }
}

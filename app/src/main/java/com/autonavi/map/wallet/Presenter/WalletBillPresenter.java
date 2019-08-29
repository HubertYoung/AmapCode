package com.autonavi.map.wallet.Presenter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.wallet.Page.WalletBillPage;
import com.autonavi.map.wallet.WalletNetManager$1;
import com.autonavi.map.wallet.adapter.WalletBillListAdapter;
import com.autonavi.map.wallet.model.WalletBillStatus;
import com.autonavi.minimap.R;
import com.autonavi.minimap.wallet.WalletRequestHolder;
import com.autonavi.minimap.wallet.param.BillsRequest;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class WalletBillPresenter extends AbstractBasePresenter<WalletBillPage> implements OnClickListener {
    private static final String[] h = {"全部", "对账中", "可提现", "提现中", "提现失败", "已提现", "冻结中"};
    public int a = 0;
    public int b = 0;
    public d<ListView> c = new d<ListView>() {
        public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            String str;
            if (WalletBillPresenter.this.t.size() > WalletBillPresenter.this.b && WalletBillPresenter.this.b != 0) {
                cfm cfm = (cfm) WalletBillPresenter.this.t.get(WalletBillPresenter.this.b);
                if (cfm != null) {
                    str = cfm.a;
                    WalletBillPresenter.this.n = 1;
                    WalletBillPresenter.this.a(WalletBillPresenter.this.n, WalletBillPresenter.this.a, str);
                }
            }
            str = null;
            WalletBillPresenter.this.n = 1;
            WalletBillPresenter.this.a(WalletBillPresenter.this.n, WalletBillPresenter.this.a, str);
        }

        public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
            String str;
            if (WalletBillPresenter.this.t.size() > WalletBillPresenter.this.b && WalletBillPresenter.this.b != 0) {
                cfm cfm = (cfm) WalletBillPresenter.this.t.get(WalletBillPresenter.this.b);
                if (cfm != null) {
                    str = cfm.a;
                    WalletBillPresenter.this.a(WalletBillPresenter.this.n, WalletBillPresenter.this.a, str);
                }
            }
            str = null;
            WalletBillPresenter.this.a(WalletBillPresenter.this.n, WalletBillPresenter.this.a, str);
        }
    };
    public OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            if (WalletBillPresenter.this.l != null) {
                ((WalletBillPage) WalletBillPresenter.this.mPage).a(WalletBillPresenter.this.l);
            } else {
                ((WalletBillPage) WalletBillPresenter.this.mPage).a();
            }
            if (((WalletBillPage) WalletBillPresenter.this.mPage).l.getVisibility() == 0) {
                ((WalletBillPage) WalletBillPresenter.this.mPage).c();
            }
            if (((WalletBillPage) WalletBillPresenter.this.mPage).k.getVisibility() == 0) {
                ((WalletBillPage) WalletBillPresenter.this.mPage).b();
            }
        }
    };
    public OnClickListener e = new OnClickListener() {
        public final void onClick(View view) {
            WalletBillPresenter.this.a(0);
        }
    };
    public OnClickListener f = new OnClickListener() {
        public final void onClick(View view) {
            WalletBillPresenter.this.a(1);
        }
    };
    public OnClickListener g = new OnClickListener() {
        public final void onClick(View view) {
            WalletBillPresenter.this.a(2);
        }
    };
    private com.autonavi.common.Callback.a i = null;
    private ArrayList<TextView> j = new ArrayList<>();
    private ArrayList<TextView> k = new ArrayList<>();
    /* access modifiers changed from: private */
    public String l;
    private int m;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public int o;
    /* access modifiers changed from: private */
    public int p;
    private b[] q;
    private WalletBillListAdapter r;
    /* access modifiers changed from: private */
    public ArrayList<cfn> s = new ArrayList<>();
    /* access modifiers changed from: private */
    public List<cfm> t = new ArrayList();

    public class RequestCallback implements Callback<JSONObject> {
        public RequestCallback() {
        }

        public void callback(JSONObject jSONObject) {
            if (jSONObject.optInt("code", 0) == 14) {
                WalletBillPresenter.a(WalletBillPresenter.this);
                return;
            }
            ArrayList arrayList = new ArrayList();
            JSONArray optJSONArray = jSONObject.optJSONArray("sources");
            JSONObject optJSONObject = jSONObject.optJSONObject("type_info");
            if (optJSONArray != null) {
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                    String optString = optJSONObject2.optString("id");
                    String optString2 = optJSONObject2.optString("name");
                    String str = "";
                    JSONObject optJSONObject3 = optJSONObject.optJSONObject(optString);
                    if (optJSONObject3 != null) {
                        str = optJSONObject3.optString(H5Param.MENU_ICON, "");
                    }
                    arrayList.add(new cfm(optString, optString2, str));
                }
            }
            List<cfn> a2 = cfr.a(jSONObject);
            WalletBillPresenter.this.l = jSONObject.optString("words", "");
            int optInt = jSONObject.optInt("total", 0);
            AMapLog.d("WalletBillPage", jSONObject.toString());
            WalletBillPresenter.a(WalletBillPresenter.this, (List) arrayList);
            if (WalletBillPresenter.this.n == 1) {
                WalletBillPresenter.this.s.clear();
                WalletBillPresenter.this.o = -1;
                WalletBillPresenter.this.p = -1;
            }
            Calendar.getInstance();
            for (cfn next : a2) {
                GregorianCalendar gregorianCalendar = next.a;
                if (gregorianCalendar.get(2) != WalletBillPresenter.this.o || gregorianCalendar.get(1) != WalletBillPresenter.this.p) {
                    WalletBillPresenter.this.o = gregorianCalendar.get(2);
                    WalletBillPresenter.this.p = gregorianCalendar.get(1);
                    next.h = String.format("%d年%d月", new Object[]{Integer.valueOf(WalletBillPresenter.this.p), Integer.valueOf(WalletBillPresenter.this.o + 1)});
                }
            }
            if (WalletBillPresenter.this.s.size() < optInt) {
                WalletBillPresenter.this.s.addAll(a2);
                WalletBillPresenter.this.n = WalletBillPresenter.this.n + 1;
            } else {
                ToastHelper.showToast(((WalletBillPage) WalletBillPresenter.this.mPage).getContext().getString(R.string.no_more_content));
            }
            if (a2.isEmpty()) {
                ((WalletBillPage) WalletBillPresenter.this.mPage).a.setEmptyView(((WalletBillPage) WalletBillPresenter.this.mPage).m);
            }
            ((WalletBillPage) WalletBillPresenter.this.mPage).a(WalletBillPresenter.this.l);
            WalletBillPresenter.this.b();
        }

        public void error(Throwable th, boolean z) {
            WalletBillPresenter.m(WalletBillPresenter.this);
        }
    }

    public static class a {
        public String a;
        public int b;
        public int c;
    }

    public static class b {
        public String a;
        public String b;
    }

    public WalletBillPresenter(WalletBillPage walletBillPage) {
        super(walletBillPage);
    }

    public final void onPageCreated() {
        int i2;
        super.onPageCreated();
        int length = h.length;
        this.q = new b[length];
        for (int i3 = 0; i3 < length; i3++) {
            this.q[i3] = new b();
            this.q[i3].a = Integer.toString(i3);
            this.q[i3].b = h[i3];
        }
        if (this.q != null && this.q.length > 0) {
            if (this.q.length % 4 == 0) {
                i2 = this.q.length / 4;
            } else {
                i2 = (this.q.length / 4) + 1;
            }
            this.j.clear();
            int i4 = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                View inflate = LayoutInflater.from(((WalletBillPage) this.mPage).getContext()).inflate(R.layout.wallet_bill_select_item_layout, null);
                LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.select_item_line_ll);
                TextView[] textViewArr = new TextView[4];
                textViewArr[0] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_0);
                this.j.add(textViewArr[0]);
                textViewArr[1] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_1);
                this.j.add(textViewArr[1]);
                textViewArr[2] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_2);
                this.j.add(textViewArr[2]);
                textViewArr[3] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_3);
                this.j.add(textViewArr[3]);
                for (int i6 = 0; i6 < 4; i6++) {
                    textViewArr[i6].setText("");
                }
                int i7 = i4;
                for (int i8 = 0; i8 < linearLayout.getChildCount(); i8++) {
                    a aVar = new a();
                    aVar.a = "status";
                    aVar.b = i5;
                    aVar.c = i8;
                    linearLayout.getChildAt(i8).setTag(aVar);
                    linearLayout.getChildAt(i8).setOnClickListener(this);
                    textViewArr[i8].setText(this.q[(i5 * 4) + i8].b);
                    i7++;
                    if (i7 == this.q.length) {
                        break;
                    }
                }
                i4 = i7;
                if (i5 == i2 - 1) {
                    inflate.findViewById(R.id.select_item_divider).setVisibility(8);
                }
                ((WalletBillPage) this.mPage).l.addView(inflate);
            }
        }
        this.r = new WalletBillListAdapter(((WalletBillPage) this.mPage).getContext(), this.s, this.t);
        ((WalletBillPage) this.mPage).a.setAdapter(this.r);
        PageBundle arguments = ((WalletBillPage) this.mPage).getArguments();
        if (arguments != null) {
            Object obj = arguments.get("status");
            if (obj != null && (obj instanceof WalletBillStatus)) {
                this.a = ((WalletBillStatus) obj).ordinal();
            }
        }
        if (this.a == 0) {
            a(0);
            a(this.m, 0);
            return;
        }
        a(1);
        a(1, this.a);
    }

    public final void a(int i2, int i3, String str) {
        if (this.i != null) {
            this.i.cancel();
        }
        RequestCallback requestCallback = new RequestCallback();
        BillsRequest billsRequest = new BillsRequest();
        billsRequest.c = 20;
        billsRequest.b = i2;
        billsRequest.d = i3;
        billsRequest.e = str;
        billsRequest.f = null;
        StringBuilder sb = new StringBuilder();
        sb.append("Sending request:");
        sb.append("\npage: ");
        sb.append(i2);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("\nstatus: ");
        sb3.append(i3);
        String sb4 = sb3.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(sb4);
        sb5.append("\nsourceID: ");
        sb5.append(str);
        String sb6 = sb5.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(sb6);
        sb7.append("\nsourceMD5: ");
        sb7.append(null);
        AMapLog.d("WalletBillPage", sb7.toString());
        aas aas = new aas(billsRequest);
        WalletRequestHolder.getInstance().sendBills(billsRequest, new WalletNetManager$1(requestCallback));
        this.i = aas;
    }

    public final void a(int i2) {
        ((WalletBillPage) this.mPage).a();
        int i3 = 2;
        switch (i2) {
            case 0:
                this.m = i2;
                this.a = 0;
                this.b = 0;
                ((WalletBillPage) this.mPage).f.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.blue));
                ((WalletBillPage) this.mPage).g.setText(R.string.status);
                ((WalletBillPage) this.mPage).g.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                ((WalletBillPage) this.mPage).h.setText(R.string.source);
                ((WalletBillPage) this.mPage).h.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                ((WalletBillPage) this.mPage).i.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                ((WalletBillPage) this.mPage).j.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                ((WalletBillPage) this.mPage).l.setVisibility(8);
                ((WalletBillPage) this.mPage).k.setVisibility(8);
                ((WalletBillPage) this.mPage).c.setVisibility(0);
                ((WalletBillPage) this.mPage).d.setVisibility(8);
                ((WalletBillPage) this.mPage).e.setVisibility(8);
                this.n = 1;
                a(this.n, this.a, null);
                LogManager.actionLogV2("P00162", "B001");
                return;
            case 1:
                if (this.m == i2 && ((WalletBillPage) this.mPage).l.getVisibility() == 0) {
                    if (this.l != null) {
                        ((WalletBillPage) this.mPage).a(this.l);
                    } else {
                        ((WalletBillPage) this.mPage).a();
                    }
                    ((WalletBillPage) this.mPage).c();
                    return;
                }
                this.m = i2;
                ((WalletBillPage) this.mPage).l.setVisibility(0);
                for (int i4 = 0; i4 < this.q.length; i4++) {
                    if (i4 == this.a) {
                        this.j.get(i4).setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.blue));
                    } else {
                        this.j.get(i4).setTextColor(Color.rgb(51, 51, 51));
                    }
                }
                ((WalletBillPage) this.mPage).k.setVisibility(8);
                ((WalletBillPage) this.mPage).c.setVisibility(8);
                ((WalletBillPage) this.mPage).d.setVisibility(0);
                ((WalletBillPage) this.mPage).e.setVisibility(8);
                ((WalletBillPage) this.mPage).f.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                ((WalletBillPage) this.mPage).g.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.blue));
                ((WalletBillPage) this.mPage).h.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                ((WalletBillPage) this.mPage).i.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_up));
                ((WalletBillPage) this.mPage).j.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                ((WalletBillPage) this.mPage).n.setVisibility(0);
                ((WalletBillPage) this.mPage).b.setMode(Mode.DISABLED);
                switch (this.a) {
                    case 0:
                        i3 = 1;
                        break;
                    case 1:
                        break;
                    case 2:
                        i3 = 3;
                        break;
                    case 4:
                        i3 = 4;
                        break;
                    case 5:
                        i3 = 5;
                        break;
                    case 6:
                        i3 = 6;
                        break;
                    default:
                        i3 = -1;
                        break;
                }
                if (-1 != i3) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", i3);
                        LogManager.actionLogV2("P00162", "B002", jSONObject);
                        return;
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
                return;
            case 2:
                if (this.m != i2 || ((WalletBillPage) this.mPage).k.getVisibility() != 0) {
                    this.m = i2;
                    ((WalletBillPage) this.mPage).l.setVisibility(8);
                    ((WalletBillPage) this.mPage).k.setVisibility(0);
                    for (int i5 = 0; i5 < this.t.size(); i5++) {
                        if (i5 == this.b) {
                            this.k.get(i5).setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.blue));
                        } else {
                            this.k.get(i5).setTextColor(Color.rgb(51, 51, 51));
                        }
                    }
                    ((WalletBillPage) this.mPage).c.setVisibility(8);
                    ((WalletBillPage) this.mPage).d.setVisibility(8);
                    ((WalletBillPage) this.mPage).e.setVisibility(0);
                    ((WalletBillPage) this.mPage).f.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                    ((WalletBillPage) this.mPage).g.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.f_c_3));
                    ((WalletBillPage) this.mPage).h.setTextColor(((WalletBillPage) this.mPage).getResources().getColor(R.color.blue));
                    ((WalletBillPage) this.mPage).i.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                    ((WalletBillPage) this.mPage).j.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_up));
                    ((WalletBillPage) this.mPage).n.setVisibility(0);
                    ((WalletBillPage) this.mPage).b.setMode(Mode.DISABLED);
                    switch (this.b) {
                        case 0:
                            i3 = 1;
                            break;
                        case 1:
                            break;
                        case 2:
                            i3 = 3;
                            break;
                        case 3:
                            i3 = 4;
                            break;
                        case 4:
                            i3 = 5;
                            break;
                        case 5:
                            i3 = 6;
                            break;
                        default:
                            i3 = -1;
                            break;
                    }
                    if (-1 != i3) {
                        try {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("type", i3);
                            LogManager.actionLogV2("P00162", "B003", jSONObject2);
                            return;
                        } catch (JSONException e3) {
                            e3.printStackTrace();
                            break;
                        }
                    }
                } else {
                    if (this.l != null) {
                        ((WalletBillPage) this.mPage).a(this.l);
                    } else {
                        ((WalletBillPage) this.mPage).a();
                    }
                    ((WalletBillPage) this.mPage).b();
                    return;
                }
                break;
        }
    }

    private void a(int i2, int i3) {
        String str = null;
        switch (i2) {
            case 1:
                if (i3 < this.q.length) {
                    this.a = i3;
                    ((WalletBillPage) this.mPage).l.setVisibility(8);
                    ((WalletBillPage) this.mPage).g.setText(this.q[i3].b);
                    ((WalletBillPage) this.mPage).i.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                    this.n = 1;
                    if (!(((WalletBillPage) this.mPage).k == null || this.b == 0)) {
                        str = this.t.get(this.b).a;
                    }
                    a(this.n, this.a, str);
                    return;
                }
                break;
            case 2:
                if (i3 < this.t.size()) {
                    this.b = i3;
                    ((WalletBillPage) this.mPage).k.setVisibility(8);
                    ((WalletBillPage) this.mPage).h.setText(this.t.get(i3).b);
                    ((WalletBillPage) this.mPage).j.setImageDrawable(((WalletBillPage) this.mPage).getResources().getDrawable(R.drawable.wallet_bill_nav_down));
                    this.n = 1;
                    if (!(((WalletBillPage) this.mPage).k == null || this.b == 0)) {
                        str = this.t.get(this.b).a;
                    }
                    a(this.n, this.a, str);
                    break;
                }
                break;
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        int i2;
        this.r.notifyDataSetChanged();
        ((WalletBillPage) this.mPage).k.removeAllViews();
        if (this.t != null && this.t.size() > 0) {
            if (this.t.size() % 4 == 0) {
                i2 = this.t.size() / 4;
            } else {
                i2 = (this.t.size() / 4) + 1;
            }
            this.k.clear();
            int i3 = 0;
            for (int i4 = 0; i4 < i2; i4++) {
                View inflate = LayoutInflater.from(((WalletBillPage) this.mPage).getContext()).inflate(R.layout.wallet_bill_select_item_layout, null);
                LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.select_item_line_ll);
                TextView[] textViewArr = new TextView[4];
                textViewArr[0] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_0);
                this.k.add(textViewArr[0]);
                textViewArr[1] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_1);
                this.k.add(textViewArr[1]);
                textViewArr[2] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_2);
                this.k.add(textViewArr[2]);
                textViewArr[3] = (TextView) linearLayout.findViewById(R.id.wallet_select_item_x_3);
                this.k.add(textViewArr[3]);
                for (int i5 = 0; i5 < 4; i5++) {
                    textViewArr[i5].setText("");
                }
                int i6 = i3;
                for (int i7 = 0; i7 < linearLayout.getChildCount(); i7++) {
                    a aVar = new a();
                    aVar.a = "source";
                    aVar.b = i4;
                    aVar.c = i7;
                    linearLayout.getChildAt(i7).setTag(aVar);
                    linearLayout.getChildAt(i7).setOnClickListener(this);
                    textViewArr[i7].setText(this.t.get((i4 * 4) + i7).b);
                    i6++;
                    if (i6 == this.t.size()) {
                        break;
                    }
                }
                i3 = i6;
                if (i4 == i2 - 1) {
                    inflate.findViewById(R.id.select_item_divider).setVisibility(8);
                }
                ((WalletBillPage) this.mPage).k.addView(inflate);
            }
        }
        if (((WalletBillPage) this.mPage).b.isRefreshing()) {
            ((WalletBillPage) this.mPage).b.onRefreshComplete();
        }
        ((WalletBillPage) this.mPage).n.setVisibility(8);
        ((WalletBillPage) this.mPage).b.setMode(Mode.BOTH);
    }

    public final void onClick(View view) {
        if (view.getTag() != null && (view.getTag() instanceof a)) {
            a aVar = (a) view.getTag();
            if (!TextUtils.isEmpty(aVar.a)) {
                if (aVar.a.contentEquals("status")) {
                    a(1, (aVar.b * 4) + aVar.c);
                } else if (aVar.a.contentEquals("source")) {
                    a(2, (aVar.b * 4) + aVar.c);
                }
            }
        }
    }

    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 4) {
            return super.onKeyDown(i2, keyEvent);
        }
        a();
        return true;
    }

    public final void a() {
        ((WalletBillPage) this.mPage).setResult(ResultType.OK, (PageBundle) null);
        ((WalletBillPage) this.mPage).finish();
    }

    static /* synthetic */ void a(WalletBillPresenter walletBillPresenter) {
        ToastHelper.showToast(((WalletBillPage) walletBillPresenter.mPage).getContext().getString(R.string.login_expired));
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(((WalletBillPage) walletBillPresenter.mPage).getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        WalletBillPresenter.this.a(WalletBillPresenter.this.n, WalletBillPresenter.this.a, null);
                    } else {
                        ((WalletBillPage) WalletBillPresenter.this.mPage).finish();
                    }
                }
            });
        }
    }

    static /* synthetic */ void a(WalletBillPresenter walletBillPresenter, List list) {
        walletBillPresenter.t.clear();
        walletBillPresenter.t.add(new cfm("0", "全部", ""));
        walletBillPresenter.t.addAll(list);
    }

    static /* synthetic */ void m(WalletBillPresenter walletBillPresenter) {
        ToastHelper.showToast(((WalletBillPage) walletBillPresenter.mPage).getContext().getString(R.string.network_error_and_retry));
        walletBillPresenter.s.clear();
        ((WalletBillPage) walletBillPresenter.mPage).a.setEmptyView(((WalletBillPage) walletBillPresenter.mPage).m);
        walletBillPresenter.b();
    }
}

package com.autonavi.map.wallet.Page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.wallet.model.WalletBillStatus;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

public class WalletMainPage extends AbstractBasePage<cfh> implements OnClickListener {
    public cfl a;
    public boolean b = true;
    public OnClickListener c = new OnClickListener() {
        public final void onClick(View view) {
            WalletMainPage.this.finish();
        }
    };
    public OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            ((cfh) WalletMainPage.this.mPresenter).a(WalletBillStatus.ALL);
            LogManager.actionLogV2("P00163", "B002");
        }
    };
    private TitleBar e;
    private View f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private TextView k;
    private ImageView l;
    private View m;
    private View n;
    private View o;
    private View p;
    private View q;
    private View r;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.wallet_cash_dlg);
        View contentView = getContentView();
        this.e = (TitleBar) contentView.findViewById(R.id.title);
        this.e.setTitle(getString(R.string.wallet_mine));
        this.e.setOnBackClickListener(this.c);
        this.e.setOnActionClickListener(this.d);
        this.e.setActionText(getString(R.string.wallet_bill));
        this.g = (TextView) contentView.findViewById(R.id.cash);
        this.h = (TextView) contentView.findViewById(R.id.cash_fen);
        this.f = contentView.findViewById(R.id.withdraw);
        this.f.setOnClickListener(this);
        this.f.setVisibility(0);
        this.k = (TextView) contentView.findViewById(R.id.withdraw_title);
        this.l = (ImageView) contentView.findViewById(R.id.right_arrow);
        this.i = (TextView) contentView.findViewById(R.id.words);
        this.j = (TextView) contentView.findViewById(R.id.note);
        this.m = a(contentView, R.id.item_total, R.string.wallet_money_item_total);
        this.n = a(contentView, R.id.item_cashouting, R.string.wallet_money_item_cashouting);
        this.o = a(contentView, R.id.item_freeze, R.string.wallet_money_item_freeze);
        this.p = a(contentView, R.id.item_checking, R.string.wallet_money_item_checking);
        this.r = a(contentView, R.id.item_failure, R.string.wallet_money_item_failure);
        this.q = a(contentView, R.id.item_success, R.string.wallet_money_item_success);
        this.a = new cfl(getPageContext());
        this.a = this.a;
        a(getArguments());
        this.b = true;
    }

    private View a(View view, int i2, int i3) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(i2);
        ((TextView) linearLayout.findViewById(R.id.title)).setText(i3);
        RelativeLayout relativeLayout = (RelativeLayout) linearLayout.findViewById(R.id.money_item);
        relativeLayout.setOnClickListener(this);
        relativeLayout.setTag(Integer.valueOf(i2));
        return linearLayout;
    }

    public final void a(PageBundle pageBundle) {
        CharSequence charSequence;
        CharSequence charSequence2;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7 = null;
        if (pageBundle != null) {
            str7 = (String) pageBundle.getObject("AVAILABLE");
            str6 = (String) pageBundle.getObject("CASHOUTING");
            str5 = (String) pageBundle.getObject("CHECKING");
            str4 = (String) pageBundle.getObject("FAILURE");
            str3 = (String) pageBundle.getObject("FREEZE");
            str2 = (String) pageBundle.getObject(GenBusCodeService.CODE_SUCESS);
            str = (String) pageBundle.getObject("TOTAL");
            charSequence2 = (String) pageBundle.getObject("WORDS");
            charSequence = (String) pageBundle.getObject("NOTE");
        } else {
            charSequence = null;
            str6 = null;
            str5 = null;
            str4 = null;
            str3 = null;
            str2 = null;
            str = null;
            charSequence2 = null;
        }
        if (!TextUtils.isEmpty(str7)) {
            String[] split = str7.split("\\.");
            this.g.setText(split[0]);
            TextView textView = this.h;
            StringBuilder sb = new StringBuilder(".");
            sb.append(split[1]);
            textView.setText(sb.toString());
        } else {
            this.g.setText("0");
            this.h.setText(".00");
        }
        if (TextUtils.isEmpty(str7) || str7.equals("0.00")) {
            a(false);
        } else {
            a(true);
        }
        if (!TextUtils.isEmpty(charSequence2)) {
            this.i.setVisibility(0);
            this.i.setText(charSequence2);
        } else {
            this.i.setVisibility(8);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.j.setVisibility(0);
            this.j.setText(charSequence);
        } else {
            this.j.setVisibility(8);
        }
        cfh.a(this.m, str, false);
        cfh.a(this.o, str3, true);
        cfh.a(this.n, str6, true);
        cfh.a(this.p, str5, true);
        cfh.a(this.q, str2, true);
        cfh.a(this.r, str4, true);
    }

    @SuppressLint({"NewApi"})
    private void a(boolean z) {
        this.f.setEnabled(z);
        if (z) {
            this.l.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wallet_right_white));
            this.k.setTextColor(getContext().getResources().getColor(R.color.white));
            return;
        }
        this.l.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wallet_right));
        this.k.setTextColor(getContext().getResources().getColor(R.color.text_disbaled));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.withdraw) {
            cfl cfl = this.a;
            a aVar = (a) this.mPresenter;
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (iAccountService.a()) {
                    cfl.a(null, aVar, false);
                } else {
                    cfl.a(cfl.a, AMapAppGlobal.getApplication().getString(R.string.wallet_alert), AMapAppGlobal.getApplication().getString(R.string.wallet_relogin), new a(iAccountService, aVar) {
                        final /* synthetic */ IAccountService a;
                        final /* synthetic */ a b;

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public final void onClick(AlertView alertView, int i) {
                            this.a.a(cfl.this.a, (anq) new anq() {
                                public final void loginOrBindCancel() {
                                }

                                public final void onComplete(boolean z) {
                                    cfl.a(AnonymousClass1.this.b, Boolean.valueOf(z));
                                }
                            });
                        }
                    }, null, null, false, null);
                }
            }
            LogManager.actionLogV2("P00163", "B001");
            return;
        }
        if (id == R.id.money_item) {
            Integer num = (Integer) view.getTag();
            if (num.intValue() == R.id.item_cashouting) {
                ((cfh) this.mPresenter).a(WalletBillStatus.IN_PROGRESS_CASH_OUT);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", 3);
                    LogManager.actionLogV2("P00163", "B003", jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            } else if (num.intValue() == R.id.item_checking) {
                ((cfh) this.mPresenter).a(WalletBillStatus.CHECKING);
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("type", 2);
                    LogManager.actionLogV2("P00163", "B003", jSONObject2);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            } else if (num.intValue() == R.id.item_success) {
                ((cfh) this.mPresenter).a(WalletBillStatus.ALREADY_CASH_OUT);
                try {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("type", 4);
                    LogManager.actionLogV2("P00163", "B003", jSONObject3);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
            } else if (num.intValue() == R.id.item_freeze) {
                ((cfh) this.mPresenter).a(WalletBillStatus.FROZEN);
                try {
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("type", 5);
                    LogManager.actionLogV2("P00163", "B003", jSONObject4);
                } catch (JSONException e5) {
                    e5.printStackTrace();
                }
            } else if (num.intValue() == R.id.item_failure) {
                ((cfh) this.mPresenter).a(WalletBillStatus.FAIL_TO_CASH_OUT);
                try {
                    JSONObject jSONObject5 = new JSONObject();
                    jSONObject5.put("type", 6);
                    LogManager.actionLogV2("P00163", "B003", jSONObject5);
                } catch (JSONException e6) {
                    e6.printStackTrace();
                }
            } else if (num.intValue() == R.id.item_total) {
                ((cfh) this.mPresenter).a(WalletBillStatus.ALL);
                try {
                    JSONObject jSONObject6 = new JSONObject();
                    jSONObject6.put("type", 1);
                    LogManager.actionLogV2("P00163", "B003", jSONObject6);
                } catch (JSONException e7) {
                    e7.printStackTrace();
                }
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cfh(this);
    }
}

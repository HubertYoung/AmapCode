package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.autonavi.widget.ui.TitleBar;

public class RTBusFollowSettingPage extends AbstractBasePage<dyo> implements a {
    public TitleBar a;
    public TextView b;
    public TextView c;
    public TextView d;
    public TextView e;
    public ImageView f;
    public ImageView g;
    public ProgressDlg h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public boolean m;
    public int n;
    public OnClickListener o = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            RTBusFollowSettingPage.this.m = true;
            if (id == R.id.tv_select_time) {
                RTBusFollowSettingPage.this.a.setActionTextEnable(true);
                RTBusFollowSettingPage.this.r.b = ((dyo) RTBusFollowSettingPage.this.mPresenter).c;
                RTBusFollowSettingPage.this.r.show();
            } else if (id == R.id.tv_repeate) {
                RTBusFollowSettingPage.this.a.setActionTextEnable(true);
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("position.list", ((dyo) RTBusFollowSettingPage.this.mPresenter).b);
                RTBusFollowSettingPage.this.startPageForResult(RTBusDateSettingPage.class, pageBundle, 100);
            } else if (id == R.id.route_attention_switch_btn) {
                RTBusFollowSettingPage.this.a.setActionTextEnable(true);
                if (RTBusFollowSettingPage.this.i) {
                    RTBusFollowSettingPage.this.f.setBackgroundResource(R.drawable.prefer_setting_btn_off);
                    RTBusFollowSettingPage.this.i = false;
                    RTBusFollowSettingPage.this.g.setBackgroundResource(R.drawable.prefer_setting_btn_off);
                    RTBusFollowSettingPage.this.j = false;
                } else {
                    RTBusFollowSettingPage.this.f.setBackgroundResource(R.drawable.prefer_setting_btn_on);
                    RTBusFollowSettingPage.this.i = true;
                    RTBusFollowSettingPage.this.g.setBackgroundResource(R.drawable.prefer_setting_btn_on);
                    RTBusFollowSettingPage.this.j = true;
                }
                RTBusFollowSettingPage.this.a();
            } else {
                if (id == R.id.route_alert_switch_btn) {
                    RTBusFollowSettingPage.this.a.setActionTextEnable(true);
                    if (RTBusFollowSettingPage.this.j) {
                        RTBusFollowSettingPage.this.g.setBackgroundResource(R.drawable.prefer_setting_btn_off);
                        RTBusFollowSettingPage.this.j = false;
                    } else {
                        RTBusFollowSettingPage.this.g.setBackgroundResource(R.drawable.prefer_setting_btn_on);
                        RTBusFollowSettingPage.this.j = true;
                    }
                    RTBusFollowSettingPage.this.a();
                }
            }
        }
    };
    private LinearLayout p;
    private LinearLayout q;
    /* access modifiers changed from: private */
    public dyx r;
    private AlertView s;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.busline_attention_setting);
        View contentView = getContentView();
        this.a = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.p = (LinearLayout) contentView.findViewById(R.id.ll_alart);
        this.q = (LinearLayout) contentView.findViewById(R.id.ll_timeday);
        this.d = (TextView) contentView.findViewById(R.id.tv_bus_name);
        this.e = (TextView) contentView.findViewById(R.id.tv_bus_to);
        this.b = (TextView) contentView.findViewById(R.id.tv_select_time);
        this.c = (TextView) contentView.findViewById(R.id.tv_repeate);
        this.f = (ImageView) contentView.findViewById(R.id.route_attention_switch_btn);
        this.g = (ImageView) contentView.findViewById(R.id.route_alert_switch_btn);
        this.r = new dyx(getActivity());
        this.r.a = this;
        this.a.setActionTextEnable(false);
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.setText(str);
        }
    }

    public final void a() {
        if (this.i) {
            this.p.setVisibility(0);
            if (this.j) {
                this.q.setVisibility(0);
            } else {
                this.q.setVisibility(8);
            }
        } else {
            this.p.setVisibility(8);
        }
    }

    public final void a(String str, String str2, String str3) {
        String str4;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(Token.SEPARATOR);
        sb.append(str2);
        sb.append(":");
        sb.append(str3);
        this.b.setText(sb);
        if (str.equals(getString(R.string.date_am))) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(":");
            sb2.append(str3);
            str4 = sb2.toString();
        } else if (TextUtils.equals("12", str2)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("00");
            sb3.append(":");
            sb3.append(str3);
            str4 = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(ahh.b(str2) + 12);
            sb4.append(":");
            sb4.append(str3);
            str4 = sb4.toString();
        }
        ((dyo) this.mPresenter).c = str4;
    }

    public final void b() {
        if (this.s == null) {
            int i2 = R.string.busline_setting_save_title;
            int i3 = R.string.busline_setting_save;
            int i4 = R.string.busline_setting_notsave;
            AnonymousClass5 r3 = new a() {
                public final void onClick(AlertView alertView, int i) {
                    ((dyo) RTBusFollowSettingPage.this.mPresenter).d();
                    RTBusFollowSettingPage.a(RTBusFollowSettingPage.this, alertView);
                }
            };
            AnonymousClass6 r4 = new a() {
                public final void onClick(AlertView alertView, int i) {
                    RTBusFollowSettingPage.a(RTBusFollowSettingPage.this, alertView);
                    RTBusFollowSettingPage.this.finish();
                }
            };
            a aVar = new a(getContext());
            aVar.a(i2);
            aVar.a(i3, (a) r3);
            if (i4 != -1) {
                aVar.b(i4, (a) r4);
            }
            AlertView a2 = aVar.a();
            showViewLayer(a2);
            a2.startAnimation();
            this.s = a2;
        }
    }

    public final void c() {
        if (this.h != null) {
            this.h.dismiss();
        }
    }

    public static void b(String str) {
        ToastHelper.showToast(str);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dyo(this);
    }

    static /* synthetic */ void a(RTBusFollowSettingPage rTBusFollowSettingPage, AlertView alertView) {
        rTBusFollowSettingPage.dismissViewLayer(alertView);
        rTBusFollowSettingPage.s = null;
    }
}

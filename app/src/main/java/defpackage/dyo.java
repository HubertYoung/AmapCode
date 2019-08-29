package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.newmodel.RTBusFollowSettingModel;
import com.autonavi.minimap.route.bus.realtimebus.newmodel.RTBusFollowSettingModel.SubscribeCallback;
import com.autonavi.minimap.route.bus.realtimebus.newmodel.RTBusFollowSettingModel.a;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusFollowSettingPage;
import com.autonavi.minimap.route.bus.realtimebus.view.RealTimeTipView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dyo reason: default package */
/* compiled from: RTBusFollowSettingPresenter */
public final class dyo extends eaf<RTBusFollowSettingPage> implements a {
    public btd a;
    public List<Integer> b;
    public String c = "8:00";
    private RTBusFollowSettingModel d;
    private RealTimeBusAndStationMatchup e;
    private boolean f;
    private String g = "1,2,3,4,5";

    public dyo(RTBusFollowSettingPage rTBusFollowSettingPage) {
        super(rTBusFollowSettingPage);
        this.d = new RTBusFollowSettingModel(rTBusFollowSettingPage.getContext());
        this.d.d = this;
    }

    public final void onPageCreated() {
        btd btd;
        super.onPageCreated();
        PageBundle arguments = ((RTBusFollowSettingPage) this.mPage).getArguments();
        if (arguments != null) {
            this.e = (RealTimeBusAndStationMatchup) arguments.getObject("RealTimeBusSettingFragmnet.IBusLineResult");
        }
        int i = 0;
        if (this.e != null) {
            RTBusFollowSettingModel rTBusFollowSettingModel = this.d;
            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = this.e;
            this.f = realTimeBusAndStationMatchup != null && rTBusFollowSettingModel.b.a(realTimeBusAndStationMatchup.mBuslineID, realTimeBusAndStationMatchup.mStationID);
            if (!this.f) {
                btd = this.e.mBean;
            } else {
                RTBusFollowSettingModel rTBusFollowSettingModel2 = this.d;
                RealTimeBusAndStationMatchup realTimeBusAndStationMatchup2 = this.e;
                btd = realTimeBusAndStationMatchup2 != null ? rTBusFollowSettingModel2.b.b(realTimeBusAndStationMatchup2.mBuslineID, realTimeBusAndStationMatchup2.mStationID) : null;
            }
            this.a = btd;
        }
        if (this.e != null) {
            RTBusFollowSettingPage rTBusFollowSettingPage = (RTBusFollowSettingPage) this.mPage;
            rTBusFollowSettingPage.a.setOnActionClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    ((dyo) RTBusFollowSettingPage.this.mPresenter).d();
                }
            });
            rTBusFollowSettingPage.a.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    if (RTBusFollowSettingPage.this.m) {
                        RTBusFollowSettingPage.this.b();
                    } else {
                        RTBusFollowSettingPage.this.finish();
                    }
                }
            });
            NoDBClickUtil.a((View) rTBusFollowSettingPage.b, rTBusFollowSettingPage.o);
            NoDBClickUtil.a((View) rTBusFollowSettingPage.c, rTBusFollowSettingPage.o);
            NoDBClickUtil.a((View) rTBusFollowSettingPage.f, rTBusFollowSettingPage.o);
            NoDBClickUtil.a((View) rTBusFollowSettingPage.g, rTBusFollowSettingPage.o);
            RTBusFollowSettingPage rTBusFollowSettingPage2 = (RTBusFollowSettingPage) this.mPage;
            RealTimeBusAndStationMatchup realTimeBusAndStationMatchup3 = this.e;
            TextView textView = rTBusFollowSettingPage2.d;
            StringBuilder sb = new StringBuilder();
            sb.append(rTBusFollowSettingPage2.getString(R.string.busline_setting_attention));
            sb.append(realTimeBusAndStationMatchup3.busName());
            textView.setText(sb.toString());
            rTBusFollowSettingPage2.e.setText(realTimeBusAndStationMatchup3.mStationName);
            RTBusFollowSettingPage rTBusFollowSettingPage3 = (RTBusFollowSettingPage) this.mPage;
            btd btd2 = this.a;
            if (!this.f || btd2 == null) {
                rTBusFollowSettingPage3.f.setBackgroundResource(R.drawable.prefer_setting_btn_off);
                rTBusFollowSettingPage3.i = false;
                rTBusFollowSettingPage3.k = false;
            } else {
                rTBusFollowSettingPage3.f.setBackgroundResource(R.drawable.prefer_setting_btn_on);
                rTBusFollowSettingPage3.i = true;
                rTBusFollowSettingPage3.k = true;
                if (!TextUtils.isEmpty(btd2.is_push)) {
                    rTBusFollowSettingPage3.g.setBackgroundResource(R.drawable.prefer_setting_btn_on);
                    rTBusFollowSettingPage3.j = true;
                    rTBusFollowSettingPage3.l = true;
                } else {
                    rTBusFollowSettingPage3.g.setBackgroundResource(R.drawable.prefer_setting_btn_off);
                    rTBusFollowSettingPage3.j = false;
                    rTBusFollowSettingPage3.l = false;
                }
            }
        }
        if (this.a != null && !TextUtils.isEmpty(this.a.alert_time)) {
            this.g = this.a.alert_day;
            this.c = this.a.alert_time;
        }
        this.b = RTBusFollowSettingModel.a(this.g);
        ((RTBusFollowSettingPage) this.mPage).a(this.d.b(this.b));
        RTBusFollowSettingPage rTBusFollowSettingPage4 = (RTBusFollowSettingPage) this.mPage;
        String str = this.c;
        try {
            i = ahh.b(str.split(":")[0]);
        } catch (NumberFormatException unused) {
        }
        String str2 = str.split(":")[1];
        if (i >= 13) {
            TextView textView2 = rTBusFollowSettingPage4.b;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(rTBusFollowSettingPage4.getString(R.string.date_pm));
            sb2.append(Token.SEPARATOR);
            sb2.append(i - 12);
            sb2.append(":");
            sb2.append(str2);
            textView2.setText(sb2.toString());
        } else if (i == 0) {
            TextView textView3 = rTBusFollowSettingPage4.b;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(rTBusFollowSettingPage4.getString(R.string.date_pm));
            sb3.append(" 12:");
            sb3.append(str2);
            textView3.setText(sb3.toString());
        } else {
            TextView textView4 = rTBusFollowSettingPage4.b;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(rTBusFollowSettingPage4.getString(R.string.date_am));
            sb4.append(Token.SEPARATOR);
            sb4.append(str);
            textView4.setText(sb4.toString());
        }
        ((RTBusFollowSettingPage) this.mPage).a();
        aho.a(new Runnable() {
            public final void run() {
                RTBusFollowSettingPage.this.f.setEnabled(true);
                RTBusFollowSettingPage.this.g.setEnabled(true);
            }
        }, 500);
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (i == 100 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("position.list")) {
            this.b = (ArrayList) pageBundle.getObject("position.list");
            String b2 = this.d.b(this.b);
            if (!TextUtils.isEmpty(b2)) {
                ((RTBusFollowSettingPage) this.mPage).a(b2);
            }
            this.g = RTBusFollowSettingModel.a(this.b);
        }
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((RTBusFollowSettingPage) this.mPage).m) {
            ((RTBusFollowSettingPage) this.mPage).b();
        } else {
            ((RTBusFollowSettingPage) this.mPage).finish();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void a() {
        ((RTBusFollowSettingPage) this.mPage).c();
        RTBusFollowSettingModel rTBusFollowSettingModel = this.d;
        int i = ((RTBusFollowSettingPage) this.mPage).n;
        btd btd = this.a;
        String str = this.g;
        String str2 = this.c;
        switch (i) {
            case 1:
                rTBusFollowSettingModel.b(btd.station_id);
                break;
            case 2:
                btd.alert_day = str;
                btd.alert_time = str2;
                btd.is_push = "1";
                rTBusFollowSettingModel.a(btd);
                break;
            case 3:
                btd.alert_day = str;
                btd.alert_time = str2;
                btd.is_push = "1";
                rTBusFollowSettingModel.b(btd);
                break;
            case 4:
                btd.alert_day = str;
                btd.alert_time = str2;
                btd.is_push = "";
                rTBusFollowSettingModel.b(btd);
                break;
        }
        RealTimeTipView.notifyRealTimeDataChanged(this.e);
        ((RTBusFollowSettingPage) this.mPage).finish();
    }

    public final void a(String str) {
        ((RTBusFollowSettingPage) this.mPage).c();
        RTBusFollowSettingPage.b(str);
    }

    public final void b() {
        this.g = RTBusFollowSettingModel.a(this.b);
        f();
    }

    public final void c() {
        RTBusFollowSettingPage.b(((RTBusFollowSettingPage) this.mPage).getContext().getString(R.string.busline_attention_save_error));
    }

    public final void d() {
        if (this.d.a()) {
            this.d.b();
            return;
        }
        this.g = RTBusFollowSettingModel.a(this.b);
        f();
    }

    private void a(boolean z) {
        RTBusFollowSettingPage rTBusFollowSettingPage = (RTBusFollowSettingPage) this.mPage;
        RTBusFollowSettingModel rTBusFollowSettingModel = this.d;
        RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = this.e;
        String str = this.g;
        AosRequest a2 = dyj.a(realTimeBusAndStationMatchup.stationId(), realTimeBusAndStationMatchup.mBuslineID, String.valueOf(z), this.c, str, new SubscribeCallback(rTBusFollowSettingModel, 0));
        rTBusFollowSettingPage.h = new ProgressDlg(rTBusFollowSettingPage.getActivity(), ((RTBusFollowSettingPage) this.mPage).getContext().getString(R.string.busline_loading));
        rTBusFollowSettingPage.h.setOnCancelListener(new OnCancelListener(a2) {
            final /* synthetic */ AosRequest a;

            {
                this.a = r2;
            }

            public final void onCancel(DialogInterface dialogInterface) {
                if (this.a != null) {
                    this.a.cancel();
                }
            }
        });
        rTBusFollowSettingPage.h.show();
    }

    private void e() {
        this.a.alert_day = "";
        this.a.alert_time = "";
        this.a.is_push = "";
        this.d.a(this.a);
    }

    private void f() {
        boolean z = ((RTBusFollowSettingPage) this.mPage).i;
        boolean z2 = ((RTBusFollowSettingPage) this.mPage).j;
        if (!z) {
            if (!((RTBusFollowSettingPage) this.mPage).k) {
                ((RTBusFollowSettingPage) this.mPage).finish();
            } else if (((RTBusFollowSettingPage) this.mPage).l) {
                ((RTBusFollowSettingPage) this.mPage).n = 1;
                a(z2);
            } else {
                this.d.b(this.a.station_id);
                RealTimeTipView.notifyRealTimeDataChanged(this.e);
                ((RTBusFollowSettingPage) this.mPage).finish();
            }
        } else if (!((RTBusFollowSettingPage) this.mPage).k) {
            if (z2) {
                ((RTBusFollowSettingPage) this.mPage).n = 2;
                a(true);
                return;
            }
            e();
            RealTimeTipView.notifyRealTimeDataChanged(this.e);
            ((RTBusFollowSettingPage) this.mPage).finish();
        } else if (((RTBusFollowSettingPage) this.mPage).l) {
            if (!z2) {
                ((RTBusFollowSettingPage) this.mPage).n = 4;
                a(false);
            } else if (!TextUtils.equals(this.g, this.a.alert_day) || !TextUtils.equals(this.c, this.a.alert_time)) {
                ((RTBusFollowSettingPage) this.mPage).n = 3;
                a(true);
            } else {
                ((RTBusFollowSettingPage) this.mPage).finish();
            }
        } else if (!z2) {
            ((RTBusFollowSettingPage) this.mPage).finish();
        } else {
            ((RTBusFollowSettingPage) this.mPage).n = 3;
            a(true);
        }
    }
}

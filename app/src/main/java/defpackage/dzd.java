package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus;
import com.autonavi.minimap.route.coach.page.CoachResultListPage;
import com.autonavi.minimap.route.coach.util.CoachActionLogUtil;
import com.autonavi.minimap.route.coach.util.CoachActionLogUtil.DateType;
import com.autonavi.minimap.route.common.view.RotateTextView;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

/* renamed from: dzd reason: default package */
/* compiled from: CoachDateController */
public final class dzd {
    CoachResultListPage a;
    RotateTextView b;
    public boolean c;
    public Date d = new Date(dzs.a().b());
    public boolean e = true;
    private TextView f;
    private TextView g;
    private View h;

    public dzd(CoachResultListPage coachResultListPage, View view) {
        this.a = coachResultListPage;
        this.f = (TextView) view.findViewById(R.id.yesterday_btn);
        this.h = view.findViewById(R.id.today_container);
        this.b = (RotateTextView) view.findViewById(R.id.today_btn);
        this.g = (TextView) view.findViewById(R.id.tomorrow_btn);
        AnonymousClass1 r3 = new OnClickListener() {
            public final void onClick(View view) {
                dzd dzd = dzd.this;
                int id = view.getId();
                dzs a2 = dzs.a();
                boolean z = false;
                boolean z2 = dzs.e().compareTo(dzd.b.getText().toString()) == 0;
                boolean z3 = dzs.d().compareTo(dzd.b.getText().toString()) == 0;
                Calendar instance = Calendar.getInstance();
                instance.setTime(dzd.d);
                if (id == R.id.yesterday_btn) {
                    if (!z2) {
                        if (!NetworkReachability.b()) {
                            dzd.a.a(ResultStatus.FAILED_NET_ERROR_SIM_LOAD);
                        } else {
                            CoachActionLogUtil.a(DateType.YESTERDAY);
                            instance.add(5, -1);
                            dzd.d = instance.getTime();
                            a2.a(dzd.d.getTime());
                            dzd.b.setText(axt.a(a2.b()));
                            dzd.a.b(ResultStatus.LOADING);
                        }
                    }
                } else if (id != R.id.tomorrow_btn) {
                    bgx bgx = (bgx) a.a.a(bgx.class);
                    if (bgx != null) {
                        String url = bgx.getUrl("exHotelCalendar.html");
                        if (url != null && url.length() > 7 && new File(url.substring(7)).exists()) {
                            z = true;
                        }
                    }
                    if (!z) {
                        ToastHelper.showToast("页面加载出错，请稍后尝试");
                    } else {
                        bdo bdo = (bdo) a.a.a(bdo.class);
                        if (bdo != null) {
                            dzd.c = true;
                            CoachActionLogUtil.a(DateType.TODAY);
                            PageBundle pageBundle = new PageBundle();
                            if (dzd.d != null) {
                                pageBundle.putLong("bundle_ticket_time", dzd.d.getTime());
                                pageBundle.putString("date_type_key", "coach_date");
                            }
                            bdo.a(pageBundle);
                        }
                    }
                } else if (!z3) {
                    if (!NetworkReachability.b()) {
                        dzd.a.a(ResultStatus.FAILED_NET_ERROR_SIM_LOAD);
                    } else {
                        CoachActionLogUtil.a(DateType.TOMORROW);
                        instance.add(5, 1);
                        dzd.d = instance.getTime();
                        a2.a(dzd.d.getTime());
                        dzd.b.setText(axt.a(a2.b()));
                        dzd.a.b(ResultStatus.LOADING);
                    }
                }
                dzd.this.a();
            }
        };
        this.h.setOnClickListener(r3);
        this.f.setOnClickListener(r3);
        this.g.setOnClickListener(r3);
        b();
    }

    public final void a() {
        if (this.a.isAlive()) {
            dzs.a();
            if (dzs.e().compareTo(this.b.getText().toString()) == 0) {
                this.f.setTextColor(this.a.getResources().getColor(R.color.f_c_6_a));
            } else {
                this.f.setTextColor(this.a.getResources().getColor(R.color.f_c_6));
            }
            if (dzs.d().compareTo(this.b.getText().toString()) == 0) {
                this.g.setTextColor(this.a.getResources().getColor(R.color.f_c_6_a));
            } else {
                this.g.setTextColor(this.a.getResources().getColor(R.color.f_c_6));
            }
        }
    }

    public final void b() {
        this.b.setText(axt.a(dzs.a().b()));
        this.d = new Date(dzs.a().b());
        a();
    }
}

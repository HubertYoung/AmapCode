package defpackage;

import android.content.SharedPreferences.Editor;
import android.view.View;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RotateTextView;
import com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* renamed from: ein reason: default package */
/* compiled from: TrainPlanDateController */
public final class ein {
    public TextView a;
    public RotateTextView b;
    public TextView c;
    public View d;
    public boolean e;
    public boolean f;
    public String g;
    public Date h;
    public boolean i;
    AbstractBasePage<?> j;

    public ein(AbstractBasePage<?> abstractBasePage) {
        this.j = abstractBasePage;
    }

    public final void a() {
        if (c()) {
            try {
                if (a((String) "").compareTo(this.b.getText().toString()) == 0) {
                    this.a.setTextColor(this.j.getResources().getColor(R.color.f_c_6_a));
                } else {
                    this.a.setTextColor(this.j.getResources().getColor(R.color.f_c_6));
                }
                if (b().compareTo(this.b.getText().toString()) == 0) {
                    this.c.setTextColor(this.j.getResources().getColor(R.color.f_c_6_a));
                } else {
                    this.c.setTextColor(this.j.getResources().getColor(R.color.f_c_6));
                }
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String a(String str) throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        new SimpleDateFormat("MM月dd日 E");
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException unused) {
            date = simpleDateFormat.parse(b("yyyy-MM-dd"));
        }
        return axt.a(date.getTime());
    }

    private static String b(String str) {
        return new SimpleDateFormat(str, Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    static String b() throws ParseException {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        new SimpleDateFormat("MM月dd日 E");
        try {
            date = simpleDateFormat.parse(b("yyyy-MM-dd"));
        } catch (ParseException unused) {
            date = simpleDateFormat.parse(b("yyyy-MM-dd"));
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(5, ejs.a().a(0) - 1);
        return axt.a(instance.getTime().getTime());
    }

    /* access modifiers changed from: 0000 */
    public final boolean c() {
        return this.j != null && this.j.isAlive();
    }

    public final void d() {
        this.b.setText(axt.a(this.h.getTime()));
    }

    /* access modifiers changed from: 0000 */
    public final void a(RequestStatus requestStatus) {
        if (c() && (this.j instanceof TrainPlanListPage)) {
            ((TrainPlanListPage) this.j).a(requestStatus);
        }
    }

    public final boolean e() {
        Editor edit = AMapAppGlobal.getApplication().getSharedPreferences("TrainDataSelected", 0).edit();
        if (this.h != null) {
            edit.putLong("TrainData", this.h.getTime());
        } else {
            edit.putLong("TrainData", System.currentTimeMillis());
        }
        edit.apply();
        return true;
    }

    public final String f() {
        this.h = new Date(ejt.a().b());
        return axt.a(this.h.getTime());
    }
}

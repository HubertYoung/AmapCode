package defpackage;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;

/* renamed from: eio reason: default package */
/* compiled from: TrainPlanFilterViewController */
public final class eio {
    private RelativeLayout a;
    private AbstractBasePage b;
    private CheckBox[] c = new CheckBox[5];
    private CheckBox[] d = new CheckBox[5];
    private CheckBox[] e = new CheckBox[5];
    private View[] f = new View[5];
    private View[] g = new View[5];
    private View[] h = new View[5];

    public eio(RelativeLayout relativeLayout, AbstractBasePage abstractBasePage) {
        this.a = relativeLayout;
        this.b = abstractBasePage;
        if (this.a != null && this.b != null) {
            this.c[0] = (CheckBox) this.a.findViewById(R.id.train_type_filter_no_condition);
            this.c[1] = (CheckBox) this.a.findViewById(R.id.train_type_filter_condition_0);
            this.c[2] = (CheckBox) this.a.findViewById(R.id.train_type_filter_condition_1);
            this.c[3] = (CheckBox) this.a.findViewById(R.id.train_type_filter_condition_2);
            this.c[4] = (CheckBox) this.a.findViewById(R.id.train_type_filter_condition_3);
            this.d[0] = (CheckBox) this.a.findViewById(R.id.train_departure_filter_no_condition);
            this.d[1] = (CheckBox) this.a.findViewById(R.id.train_departure_filter_condition_0);
            this.d[2] = (CheckBox) this.a.findViewById(R.id.train_departure_filter_condition_1);
            this.d[3] = (CheckBox) this.a.findViewById(R.id.train_departure_filter_condition_2);
            this.d[4] = (CheckBox) this.a.findViewById(R.id.train_departure_filter_condition_3);
            this.e[0] = (CheckBox) this.a.findViewById(R.id.train_arrival_filter_no_condition);
            this.e[1] = (CheckBox) this.a.findViewById(R.id.train_arrival_filter_condition_0);
            this.e[2] = (CheckBox) this.a.findViewById(R.id.train_arrival_filter_condition_1);
            this.e[3] = (CheckBox) this.a.findViewById(R.id.train_arrival_filter_condition_2);
            this.e[4] = (CheckBox) this.a.findViewById(R.id.train_arrival_filter_condition_3);
            this.f[0] = this.a.findViewById(R.id.train_type_filter_no_condition_front);
            this.f[1] = this.a.findViewById(R.id.train_type_filter_condition_0_front);
            this.f[2] = this.a.findViewById(R.id.train_type_filter_condition_1_front);
            this.f[3] = this.a.findViewById(R.id.train_type_filter_condition_2_front);
            this.f[4] = this.a.findViewById(R.id.train_type_filter_condition_3_front);
            this.g[0] = this.a.findViewById(R.id.train_departure_filter_no_condition_front);
            this.g[1] = this.a.findViewById(R.id.train_departure_filter_condition_0_front);
            this.g[2] = this.a.findViewById(R.id.train_departure_filter_condition_1_front);
            this.g[3] = this.a.findViewById(R.id.train_departure_filter_condition_2_front);
            this.g[4] = this.a.findViewById(R.id.train_departure_filter_condition_3_front);
            this.h[0] = this.a.findViewById(R.id.train_arrival_filter_no_condition_front);
            this.h[1] = this.a.findViewById(R.id.train_arrival_filter_condition_0_front);
            this.h[2] = this.a.findViewById(R.id.train_arrival_filter_condition_1_front);
            this.h[3] = this.a.findViewById(R.id.train_arrival_filter_condition_2_front);
            this.h[4] = this.a.findViewById(R.id.train_arrival_filter_condition_3_front);
        }
    }

    public final boolean a() {
        if (this.a == null || this.b == null) {
            return false;
        }
        for (int i = 0; i < this.c.length; i++) {
            a(this.f[i], this.c[i]);
        }
        for (int i2 = 0; i2 < this.d.length; i2++) {
            a(this.g[i2], this.d[i2]);
        }
        for (int i3 = 0; i3 < this.e.length; i3++) {
            a(this.h[i3], this.e[i3]);
        }
        return true;
    }

    private static boolean a(View view, CheckBox checkBox) {
        if (view == null || checkBox == null) {
            return false;
        }
        if (checkBox.isChecked()) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
        return true;
    }
}

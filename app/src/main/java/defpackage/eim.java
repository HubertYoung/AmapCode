package defpackage;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter;
import com.autonavi.minimap.route.train.adapter.TrainPlanListAdapter.a;
import com.autonavi.minimap.route.train.model.TrainPlanBaseInfoItem;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;
import java.util.ArrayList;

/* renamed from: eim reason: default package */
/* compiled from: TrainPlanBottomBarController */
public final class eim {
    public TrainPlanListAdapter a;
    public ArrayList<TrainPlanBaseInfoItem> b;
    AbstractBasePage<?> c;
    public CheckBox d;
    public TextView e;
    public CheckBox f;
    public TextView g;
    public View h;
    public ImageView i;
    public TextView j;

    public eim(AbstractBasePage<?> abstractBasePage) {
        this.c = abstractBasePage;
    }

    public final void a() {
        if (!this.a.isEmpty() && this.c != null && this.c.isAlive() && (this.c instanceof TrainPlanListPage)) {
            TrainPlanListPage trainPlanListPage = (TrainPlanListPage) this.c;
            if (trainPlanListPage.a != null && trainPlanListPage.a.getAdapter() != null && !trainPlanListPage.a.getAdapter().isEmpty()) {
                trainPlanListPage.a.setSelection(0);
            }
        }
    }

    public final void b() {
        this.d.setChecked(false);
        this.e.setTextColor(this.c.getResources().getColor(R.color.f_c_3));
        this.f.setChecked(false);
        this.g.setTextColor(this.c.getResources().getColor(R.color.f_c_3));
    }

    public final void c() {
        if (this.a != null && this.a.getFilterCondition() != null && this.i != null && this.j != null) {
            a filterCondition = this.a.getFilterCondition();
            if (!filterCondition.d[0] || !filterCondition.f[0] || !filterCondition.e[0]) {
                this.i.setImageResource(R.drawable.train_plan_filter_icon_selected);
                this.j.setTextColor(this.c.getResources().getColor(R.color.f_c_6));
                return;
            }
            this.i.setImageResource(R.drawable.train_plan_filter_icon);
            this.j.setTextColor(this.c.getResources().getColor(R.color.f_c_3));
        }
    }

    static /* synthetic */ void a(eim eim) {
        if (eim.c != null && eim.c.isAlive() && (eim.c instanceof TrainPlanListPage)) {
            ((TrainPlanListPage) eim.c).c();
        }
    }
}

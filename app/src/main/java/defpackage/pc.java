package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage;
import com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage;
import com.amap.bundle.drive.result.view.RouteCarLongScenePanel.a;
import com.amap.bundle.drivecommon.widget.ExpandableIconView;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.map.suspend.SuspendViewCommonTemplate;
import com.autonavi.minimap.R;

/* renamed from: pc reason: default package */
/* compiled from: TripSuspendHelper */
public final class pc extends sx {
    public ViewGroup a;
    public OnClickListener b;
    private a h;
    private Context i;

    public pc(@NonNull AjxRouteCarResultPage ajxRouteCarResultPage, Context context) {
        super(ajxRouteCarResultPage);
        this.i = context;
    }

    public pc(@NonNull AjxRouteTruckResultPage ajxRouteTruckResultPage, Context context) {
        super(ajxRouteTruckResultPage);
        this.i = context;
    }

    public pc(@NonNull AjxRouteMotorResultPage ajxRouteMotorResultPage, Context context) {
        super(ajxRouteMotorResultPage);
        this.i = context;
    }

    public final void b() {
        d();
        h();
    }

    private void d() {
        this.a = (ViewGroup) LayoutInflater.from(this.i).inflate(R.layout.route_car_result_tip_entrance, null);
        LayoutParams layoutParams = new LayoutParams(agn.a(this.i, 44.0f), agn.a(this.i, 44.0f));
        layoutParams.leftMargin = agn.a(this.i, 4.0f);
        layoutParams.topMargin = agn.a(this.i, 4.0f);
        addWidget(this.a, layoutParams, 1);
    }

    private void h() {
        View suspendView = getSuspendView();
        if (suspendView instanceof SuspendViewCommonTemplate) {
            enabledAdjustWidgetCallback();
            SuspendViewCommonTemplate suspendViewCommonTemplate = (SuspendViewCommonTemplate) suspendView;
            suspendViewCommonTemplate.setPartitionPriority(1, 4);
            suspendViewCommonTemplate.setPartitionPriority(4, 4);
            suspendViewCommonTemplate.setPartitionPriority(2, 3);
            suspendViewCommonTemplate.setPartitionPriority(5, 3);
            suspendViewCommonTemplate.setPartitionPriority(3, 1);
            suspendViewCommonTemplate.setPartitionPriority(6, 1);
        }
    }

    public final void c() {
        if (this.e == null) {
            this.e = (ExpandableIconView) LayoutInflater.from(this.c).inflate(R.layout.suspend_view_search_along, null);
            this.e.setContentDescription(this.c.getString(R.string.along_search_title));
        }
        NoDBClickUtil.a(this.e.findViewById(R.id.route_car_result_search_along_btn), (OnClickListener) null);
        this.b = null;
        this.h = null;
        if (this.d != null) {
            this.d.destroy();
        }
    }
}

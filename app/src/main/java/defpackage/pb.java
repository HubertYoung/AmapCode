package defpackage;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage;
import com.amap.bundle.drive.result.view.DriveRecommendView;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.inter.IRouteUI.ContainerType;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;

/* renamed from: pb reason: default package */
/* compiled from: MotorMapWidgetHelper */
public final class pb {
    AjxRouteMotorResultPage a;
    public boolean b = false;
    DriveRecommendView c;
    public Context d;
    qb e;
    ContainerType[] f = null;
    public View g = null;
    private ps h;

    public pb(AjxRouteMotorResultPage ajxRouteMotorResultPage, qb qbVar) {
        this.a = ajxRouteMotorResultPage;
        this.h = (ps) ajxRouteMotorResultPage.getPresenter();
        this.e = qbVar;
        this.d = this.a.getContext();
    }

    public final void a() {
        if (this.b) {
            this.c.hideRecommendViewAnim(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    pb.this.c.post(new Runnable() {
                        public final void run() {
                            IRouteUI b = pb.this.e.b();
                            if (b != null) {
                                b.b((View) pb.this.c);
                                if (pb.this.f != null) {
                                    b.a(pb.this.f);
                                    pb.this.f = null;
                                }
                            }
                            pb.this.c = null;
                        }
                    });
                }
            });
            this.e.e();
            if (this.c != null && this.c.selectedHasChange()) {
                new pf(this.e).i = "planresult_preference";
                if (!AMapPageUtil.getTopPageClass().equals(AjxRouteCarNaviPage.class)) {
                    this.h.b();
                }
            }
            this.b = false;
            if (this.g != null) {
                TextView textView = (TextView) this.g.findViewById(R.id.recommend_tv);
                if (textView != null) {
                    textView.setText(DriveUtil.getChoiceString(DriveUtil.getMotorRoutingChoice(), 11));
                }
            }
        }
    }

    public static void a(int i) {
        BaseMapWidgetPresenter baseMapWidgetPresenter = (BaseMapWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.PATHTIPSENTERVIEW);
        if (baseMapWidgetPresenter != null) {
            IMapWidget widget = baseMapWidgetPresenter.getWidget();
            if (widget != null) {
                View contentView = widget.getContentView();
                if (contentView != null) {
                    contentView.setVisibility(i);
                }
            }
        }
    }
}

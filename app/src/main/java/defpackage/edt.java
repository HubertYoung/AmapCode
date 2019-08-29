package defpackage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;

/* renamed from: edt reason: default package */
/* compiled from: RideBrowserSVHelper */
public final class edt {
    MapManager a;
    public ccv b;
    public OnErrorReportClickInterface c;
    coi d = new coi() {
        public final void doReportError(String str) {
            if (edt.this.c != null) {
                edt.this.c.onErrorReportClickBtn(str);
            }
        }
    };
    private Context e;
    private ccy f;
    private int g;

    public edt(IMapPage iMapPage, int i) {
        this.e = iMapPage.getContext();
        this.a = iMapPage.getMapManager();
        this.f = iMapPage.getSuspendWidgetHelper();
        this.b = new ccv(this.e);
        this.g = i;
        LayoutParams layoutParams = new LayoutParams(agn.a(this.e, 44.0f), agn.a(this.e, 44.0f));
        layoutParams.leftMargin = agn.a(AMapPageUtil.getAppContext(), 4.0f);
        layoutParams.bottomMargin = agn.a(AMapPageUtil.getAppContext(), 52.0f);
        this.f.a(this.f.d());
        this.f.a(this.b.getSuspendView(), this.f.d(), layoutParams, 3);
        LayoutParams layoutParams2 = new LayoutParams(-1, agn.a(this.e, 48.0f));
        layoutParams2.leftMargin = agn.a(this.e, 6.0f);
        layoutParams2.bottomMargin = agn.a(this.e, 52.0f);
        this.b.addWidget(this.f.f(), layoutParams2, 7);
        if (this.g != 102) {
            LayoutParams layoutParams3 = new LayoutParams(-2, -2);
            layoutParams3.rightMargin = agn.a(this.e, 4.0f);
            layoutParams3.bottomMargin = agn.a(this.e, 52.0f);
            this.b.addWidget(this.f.l(), layoutParams3, 6);
            LayoutParams layoutParams4 = new LayoutParams(-2, -2);
            layoutParams4.leftMargin = agn.a(this.e, 4.0f);
            layoutParams4.topMargin = agn.a(this.e, 94.0f);
            this.b.addWidget(this.f.a(), layoutParams4, 1);
            MvpImageView mvpImageView = new MvpImageView(this.e);
            LayoutParams layoutParams5 = new LayoutParams(agn.a(this.e, 44.0f), agn.a(this.e, 44.0f));
            layoutParams5.rightMargin = agn.a(AMapPageUtil.getAppContext(), 4.0f);
            layoutParams5.topMargin = agn.a(AMapPageUtil.getAppContext(), 94.0f);
            mvpImageView.setScaleType(ScaleType.CENTER_INSIDE);
            mvpImageView.setImageResource(R.drawable.icon_c18_selector);
            mvpImageView.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
            mvpImageView.setContentDescription("报错");
            this.b.addWidget(mvpImageView, layoutParams5, 4);
            mvpImageView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    edt edt = edt.this;
                    IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter != null) {
                        iErrorReportStarter.doReportError(edt.a, edt.d);
                    }
                }
            });
        }
    }
}

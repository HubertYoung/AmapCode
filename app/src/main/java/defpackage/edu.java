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

/* renamed from: edu reason: default package */
/* compiled from: RideEndSVHelper */
public final class edu {
    MapManager a;
    public ccv b;
    public OnErrorReportClickInterface c;
    coi d = new coi() {
        public final void doReportError(String str) {
            if (edu.this.c != null) {
                edu.this.c.onErrorReportClickBtn(str);
            }
        }
    };
    private Context e;
    private MvpImageView f;

    public edu(IMapPage iMapPage) {
        this.e = iMapPage.getContext();
        this.b = new ccv(this.e);
        this.a = iMapPage.getMapManager();
        this.f = new MvpImageView(this.e);
        this.f.setScaleType(ScaleType.CENTER_INSIDE);
        this.f.setImageResource(R.drawable.icon_c18_selector);
        this.f.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
        this.f.setContentDescription("报错");
        int dimensionPixelSize = this.e.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.rightMargin = agn.a(AMapPageUtil.getAppContext(), 9.0f);
        layoutParams.topMargin = agn.a(AMapPageUtil.getAppContext(), 9.0f);
        this.b.addWidget(this.f, layoutParams, 4);
        this.f.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                edu edu2 = edu.this;
                IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                if (iErrorReportStarter != null) {
                    iErrorReportStarter.doReportError(edu2.a, edu2.d);
                }
            }
        });
    }

    public final void a(boolean z) {
        if (this.f != null) {
            this.f.setVisibility(z ? 0 : 8);
        }
    }
}

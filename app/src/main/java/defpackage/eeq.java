package defpackage;

import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.SuspendViewDefaultTemplate;
import com.autonavi.map.suspend.refactor.scale.ScaleLineView;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;

/* renamed from: eeq reason: default package */
/* compiled from: RideMapSVManager */
public final class eeq {
    public SuspendViewDefaultTemplate a = new SuspendViewDefaultTemplate(AMapPageUtil.getAppContext());
    public eep b;
    private ScaleView c;
    private MapManager d;
    private Context e;
    private int f = agn.a(AMapPageUtil.getAppContext(), 4.0f);

    public eeq(IMapPage iMapPage) {
        this.e = iMapPage.getContext();
        this.d = iMapPage.getMapManager();
        MvpImageView mvpImageView = new MvpImageView(AMapPageUtil.getAppContext());
        this.b = new eep();
        this.b.a(mvpImageView);
        mvpImageView.setContentDescription("跟随/指北模式");
        int a2 = agn.a(AMapPageUtil.getAppContext(), 48.0f);
        LayoutParams layoutParams = new LayoutParams(a2, a2);
        int i = this.f;
        layoutParams.leftMargin = i;
        layoutParams.bottomMargin = i;
        this.a.addView(mvpImageView, layoutParams, 3);
        ScaleView scaleView = new ScaleView(this.e);
        scaleView.setMapManager(this.d);
        ScaleLineView scaleLineView = scaleView.getScaleLineView();
        if (scaleLineView != null) {
            scaleLineView.mAlignRight = false;
        }
        this.c = scaleView;
        this.c.setContentDescription("比例尺");
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) this.e.getResources().getDimension(R.dimen.my_route_height));
        this.c.setPadding(0, 0, 0, agn.a(this.e, 6.0f));
        layoutParams2.leftMargin = agn.a(this.e, 2.0f) + ((int) this.e.getResources().getDimension(R.dimen.map_container_btn_new_size));
        this.a.addView(this.c, layoutParams2, 7);
    }
}

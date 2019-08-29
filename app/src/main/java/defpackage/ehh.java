package defpackage;

import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.SuspendViewDefaultTemplate;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;

/* renamed from: ehh reason: default package */
/* compiled from: RidingSVManager */
public final class ehh {
    public SuspendViewDefaultTemplate a = new SuspendViewDefaultTemplate(AMapPageUtil.getAppContext());
    public ehi b;
    private ScaleView c;
    private MapManager d;
    private Context e;
    private int f = 0;

    public ehh(IMapPage iMapPage) {
        this.e = iMapPage.getContext();
        this.d = iMapPage.getMapManager();
        MvpImageView mvpImageView = new MvpImageView(AMapPageUtil.getAppContext());
        this.b = new ehi();
        this.b.a(mvpImageView);
        mvpImageView.setContentDescription("跟随/指北模式");
        int a2 = agn.a(this.e, 4.0f);
        int a3 = agn.a(AMapPageUtil.getAppContext(), 52.0f);
        LayoutParams layoutParams = new LayoutParams(a3, a3);
        int i = this.f;
        layoutParams.leftMargin = a2;
        layoutParams.bottomMargin = i;
        this.a.addView(mvpImageView, layoutParams, 3);
        ScaleView scaleView = new ScaleView(this.e);
        MapManager mapManager = this.d;
        scaleView.setLogoSize((int) this.e.getResources().getDimension(R.dimen.sharebick_logo_width), (int) this.e.getResources().getDimension(R.dimen.sharebick_logo_height));
        scaleView.setMapManager(mapManager);
        scaleView.getScaleLineView().mAlignRight = false;
        this.c = scaleView;
        this.c.setContentDescription("比例尺");
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) this.e.getResources().getDimension(R.dimen.my_route_height));
        this.c.setPadding(0, 0, 0, agn.a(this.e, 6.0f));
        layoutParams2.leftMargin = agn.a(this.e, 2.0f) + ((int) this.e.getResources().getDimension(R.dimen.map_container_btn_new_size));
        this.a.addView(this.c, layoutParams2, 7);
    }
}

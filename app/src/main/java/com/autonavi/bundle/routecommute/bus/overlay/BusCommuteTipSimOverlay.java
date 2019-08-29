package com.autonavi.bundle.routecommute.bus.overlay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class BusCommuteTipSimOverlay extends PointOverlay {
    private View commuteTipSimView;
    private ImageView simIconView;

    public BusCommuteTipSimOverlay(bty bty) {
        super(bty);
        initView();
    }

    private void initView() {
        this.commuteTipSimView = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.bus_commute_tips_sim_layout, null);
        this.commuteTipSimView.setLayoutParams(new LayoutParams(-2, -2));
        this.simIconView = (ImageView) this.commuteTipSimView.findViewById(R.id.commute_tips_sim_icon);
    }

    public void show(BusCommuteTipBean busCommuteTipBean, boolean z) {
        ayp.a(ayp.a, ayp.h, busCommuteTipBean.isSettingUser, busCommuteTipBean.isGoHome);
        if (z) {
            this.simIconView.setImageResource(busCommuteTipBean.simIconId);
            this.commuteTipSimView.destroyDrawingCache();
            this.mOverlayDefaultMarker = createMarker(0, this.commuteTipSimView, 5, 0.0f, 0.0f, false);
        }
        PointOverlayItem pointOverlayItem = new PointOverlayItem(busCommuteTipBean.currentLocPoint);
        clear();
        addItem(pointOverlayItem);
    }

    public void hide() {
        removeAll();
    }
}

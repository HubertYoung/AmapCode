package com.autonavi.minimap.route.sharebike.overlay;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;
import java.util.List;

public class ShareBikeAddressTipOverlay extends PointOverlay<PointOverlayItem> {
    private List<aly> mGlCliskList = new ArrayList();
    private View mLine;
    /* access modifiers changed from: private */
    public a mListener;
    private View mRootView;
    private LinearLayout withAdressLin;

    public interface a {
        void a(boolean z);

        void b();
    }

    public ShareBikeAddressTipOverlay(bty bty) {
        super(bty);
    }

    public void updataOverlay(String str, GeoPoint geoPoint) {
        clear();
        this.mGlCliskList.clear();
        this.mRootView = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.sharebike_riding_overlay_tip, null);
        this.withAdressLin = (LinearLayout) this.mRootView.findViewById(R.id.sharebike_riding_tip_with_addresss);
        LinearLayout linearLayout = (LinearLayout) this.mRootView.findViewById(R.id.sharebike_riding_tip_no_address);
        this.mLine = this.mRootView.findViewById(R.id.sharebike_riding_tip_line);
        if (TextUtils.isEmpty(str)) {
            this.withAdressLin.setVisibility(8);
            linearLayout.setVisibility(0);
            editClick(this.mRootView, false);
        } else {
            this.withAdressLin.setVisibility(0);
            linearLayout.setVisibility(8);
            ((TextView) this.mRootView.findViewById(R.id.sharebike_tip_address)).setText(str);
            editClick((LinearLayout) this.mRootView.findViewById(R.id.sharebike_riding_tip_edit), true);
            naviClick((ImageView) this.mRootView.findViewById(R.id.sharebike_riding_tip_navi));
        }
        setClickList(this.mGlCliskList);
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(this.mRootView, (LayoutParams) mapViewLayoutParams);
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createMarker(0, this.mRootView, 5, 0.0f, 0.0f, false);
        this.mMapView.a(this.mRootView);
        addItem(pointOverlayItem);
    }

    private void editClick(View view, final boolean z) {
        view.measure(0, 0);
        aly aly = new aly(view.getMeasuredWidth() + agn.a(AMapPageUtil.getAppContext(), 20.0f), view.getMeasuredHeight() + agn.a(AMapPageUtil.getAppContext(), 10.0f));
        aly.e = new defpackage.aly.a() {
            public final void a() {
                if (ShareBikeAddressTipOverlay.this.mListener != null) {
                    ShareBikeAddressTipOverlay.this.mListener.a(z);
                }
            }
        };
        this.mGlCliskList.add(aly);
    }

    private void naviClick(View view) {
        view.measure(0, 0);
        aly aly = new aly(view.getMeasuredWidth() + agn.a(AMapPageUtil.getAppContext(), 20.0f), view.getMeasuredHeight() + agn.a(AMapPageUtil.getAppContext(), 10.0f));
        aly.e = new defpackage.aly.a() {
            public final void a() {
                if (ShareBikeAddressTipOverlay.this.mListener != null) {
                    ShareBikeAddressTipOverlay.this.mListener.b();
                }
            }
        };
        this.mGlCliskList.add(aly);
    }

    public void setOnAddressTipClickListener(a aVar) {
        this.mListener = aVar;
    }
}

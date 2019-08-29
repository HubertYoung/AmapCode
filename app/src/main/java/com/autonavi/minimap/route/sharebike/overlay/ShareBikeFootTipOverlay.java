package com.autonavi.minimap.route.sharebike.overlay;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class ShareBikeFootTipOverlay extends PointOverlay<PointOverlayItem> {
    a mClickListener;
    private boolean mIsOverlayEnable = true;
    private PointOverlayItem mTipPointItem;

    public interface a {
        void a();
    }

    public ShareBikeFootTipOverlay(bty bty) {
        super(bty);
        if (getGLOverlay() != null) {
            ((GLPointOverlay) getGLOverlay()).setOnlyRespToClickArea(true);
        }
    }

    public void setOverlayEnabled(boolean z) {
        this.mIsOverlayEnable = z;
    }

    public void drawFootTipOverlay(String[] strArr, GeoPoint geoPoint) {
        clear();
        if (geoPoint == null) {
            eao.a((String) "Amap#", (String) "Bike# drawFootTip error !! point is null");
            return;
        }
        View inflate = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.sharebike_page_foot_tip_view, null);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.sharebike_onfoot_tip);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.sharebike_foot_tip_navi_icon);
        TextView textView = (TextView) inflate.findViewById(R.id.sharebike_foot_distance);
        TextView textView2 = (TextView) inflate.findViewById(R.id.sharebike_foot_time);
        TextView textView3 = (TextView) inflate.findViewById(R.id.sharebike_foot_loading);
        if (this.mIsOverlayEnable) {
            imageView.setImageResource(R.drawable.walk_navi_icon);
        } else {
            imageView.setImageResource(R.drawable.walk_navi_disable_icon);
        }
        if (strArr == null || strArr.length != 2) {
            textView.setVisibility(8);
            textView2.setVisibility(8);
            textView3.setVisibility(0);
            if (strArr != null) {
                textView3.setText(strArr[0]);
            }
        } else {
            textView.setVisibility(0);
            textView2.setVisibility(0);
            textView3.setVisibility(8);
            textView.setText(strArr[0]);
            textView2.setText(strArr[1]);
        }
        setItemClickListener(linearLayout);
        this.mTipPointItem = new PointOverlayItem(geoPoint);
        this.mTipPointItem.mDefaultMarker = createMarker(0, inflate, 5, 0.0f, 0.0f, false);
        addItem(this.mTipPointItem);
    }

    private void setItemClickListener(View view) {
        if (view != null) {
            ArrayList arrayList = new ArrayList();
            view.measure(0, 0);
            alz alz = new alz(view.getMeasuredWidth(), view.getMeasuredHeight(), 0, 0);
            alz.d = true;
            alz.e = new defpackage.aly.a() {
                public final void a() {
                    if (ShareBikeFootTipOverlay.this.mClickListener != null) {
                        ShareBikeFootTipOverlay.this.mClickListener.a();
                    }
                }
            };
            arrayList.add(alz);
            setClickList(arrayList);
        }
    }

    public boolean clear() {
        synchronized (this) {
            this.mTipPointItem = null;
        }
        return super.clear();
    }

    public void setonTipItemClick(a aVar) {
        this.mClickListener = aVar;
    }
}

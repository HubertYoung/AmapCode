package com.autonavi.bundle.routecommute.drive.tips;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;
import java.util.ArrayList;

public class DriveCommuteGuideTipsOverlay extends PointOverlay {
    private static final String TAG = "DriveCommuteGuideTipsOverlay";
    private TextView mCommuteDescriptionTextView;
    private View mCommuteTipCloseView;
    private View mCommuteTipContainer;
    private ImageView mCommuteTipIconView;
    private View mCommuteTipView;
    private TextView mCommutetitleTextView;
    /* access modifiers changed from: private */
    public a mListener;

    public interface a {
    }

    public void setOnDriveCommuteTipListener(a aVar) {
        this.mListener = aVar;
    }

    public DriveCommuteGuideTipsOverlay(bty bty) {
        super(bty);
        initView();
        setMoveToFocus(false);
        GLOverlay gLOverlay = getGLOverlay();
        if (gLOverlay != null && (gLOverlay instanceof GLPointOverlay)) {
            ((GLPointOverlay) gLOverlay).setOnlyRespToClickArea(true);
        }
    }

    private void initView() {
        this.mCommuteTipView = LayoutInflater.from(this.mContext).inflate(R.layout.drive_commute_guide_tips_layout, null);
        this.mCommuteTipView.setLayoutParams(new LayoutParams(-2, -2));
        this.mCommuteTipContainer = this.mCommuteTipView.findViewById(R.id.commute_tips);
        this.mCommuteTipCloseView = this.mCommuteTipView.findViewById(R.id.commute_tips_close);
        this.mCommuteDescriptionTextView = (TextView) this.mCommuteTipView.findViewById(R.id.commute_tips_description);
        this.mCommutetitleTextView = (TextView) this.mCommuteTipView.findViewById(R.id.commute_tips_title);
        this.mCommuteTipIconView = (ImageView) this.mCommuteTipView.findViewById(R.id.commute_tips_icon);
    }

    public void updateView(int i, GeoPoint geoPoint) {
        String str = "";
        String str2 = "";
        if (i == 11) {
            this.mCommuteTipIconView.setImageResource(R.drawable.drive_commute_tips_work);
            str = "去上班吗？";
            str2 = "设置地址，路况早知道";
        } else if (i == 12) {
            this.mCommuteTipIconView.setImageResource(R.drawable.drive_commute_tips_home);
            str = "回家吗？";
            str2 = "设置地址，路况早知道";
        }
        this.mCommutetitleTextView.setText(str);
        this.mCommuteDescriptionTextView.setText(str2);
        setTipClick();
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createViewMarker(this.mCommuteTipView, 0, geoPoint);
        addItem(pointOverlayItem);
    }

    private Marker createViewMarker(View view, int i, GeoPoint geoPoint) {
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(view, (FrameLayout.LayoutParams) mapViewLayoutParams);
        Bitmap convertViewToBitmap = OverlayUtil.convertViewToBitmap(view);
        Marker createMarker = createMarker(i, convertViewToBitmap, 5, false);
        this.mMapView.a(view);
        if (convertViewToBitmap != null && !convertViewToBitmap.isRecycled()) {
            convertViewToBitmap.recycle();
        }
        return createMarker;
    }

    private void setTipClick() {
        ArrayList arrayList = new ArrayList();
        this.mCommuteTipContainer.measure(0, 0);
        alz alz = new alz(this.mCommuteTipContainer.getMeasuredWidth(), this.mCommuteTipContainer.getMeasuredHeight(), agn.a(AMapPageUtil.getAppContext(), 20.0f), agn.a(AMapPageUtil.getAppContext(), 20.0f));
        alz.e = new defpackage.aly.a() {
            public final void a() {
                if (DriveCommuteGuideTipsOverlay.this.mListener != null) {
                    DriveCommuteGuideTipsOverlay.this.mListener;
                }
            }
        };
        this.mCommuteTipView.measure(0, 0);
        int measuredWidth = this.mCommuteTipView.getMeasuredWidth();
        this.mCommuteTipCloseView.measure(0, 0);
        int measuredWidth2 = this.mCommuteTipCloseView.getMeasuredWidth();
        alz alz2 = new alz(measuredWidth2, this.mCommuteTipCloseView.getMeasuredHeight(), measuredWidth - measuredWidth2, 0);
        alz2.e = new defpackage.aly.a() {
            public final void a() {
                if (DriveCommuteGuideTipsOverlay.this.mListener != null) {
                    DriveCommuteGuideTipsOverlay.this.mListener;
                }
            }
        };
        arrayList.add(alz);
        arrayList.add(alz2);
        setClickList(arrayList);
    }

    public void addItem(PointOverlayItem pointOverlayItem) {
        clear();
        super.addItem(pointOverlayItem);
    }
}

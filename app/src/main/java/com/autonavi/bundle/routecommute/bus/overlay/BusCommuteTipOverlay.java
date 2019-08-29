package com.autonavi.bundle.routecommute.bus.overlay;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class BusCommuteTipOverlay extends PointOverlay {
    private TextView mBusStatusTv;
    private ImageView mCloseIv;
    private TextView mEtaTv;
    /* access modifiers changed from: private */
    public ImageView mIconIv;
    private LinearLayout mJumpAreaView;
    private TextView mLineNameTv;
    /* access modifiers changed from: private */
    public a mListener;
    private TextView mRealTimeTv;
    private View mRootView;
    private TextView mdestinationTv;
    private bkf target;

    public interface a {
        void k();

        void l();
    }

    public BusCommuteTipOverlay(bty bty) {
        super(bty);
        initView();
    }

    @SuppressLint({"InflateParams"})
    private void initView() {
        this.mRootView = LayoutInflater.from(AMapAppGlobal.getApplication()).inflate(R.layout.bus_commute_tip_overlay, null);
        this.mRootView.setLayoutParams(new LayoutParams(-2, -2));
        this.mJumpAreaView = (LinearLayout) this.mRootView.findViewById(R.id.bus_commute_tip_content);
        this.mIconIv = (ImageView) this.mRootView.findViewById(R.id.bus_commute_tip_icon);
        this.mCloseIv = (ImageView) this.mRootView.findViewById(R.id.bus_commute_tip_close);
        this.mdestinationTv = (TextView) this.mRootView.findViewById(R.id.bus_commute_tip_destination);
        this.mEtaTv = (TextView) this.mRootView.findViewById(R.id.bus_commute_tip_eta);
        this.mBusStatusTv = (TextView) this.mRootView.findViewById(R.id.bus_commute_tip_busstatus);
        this.mLineNameTv = (TextView) this.mRootView.findViewById(R.id.bus_commute_tip_linename);
        this.mRealTimeTv = (TextView) this.mRootView.findViewById(R.id.bus_commute_tip_realtime);
    }

    private void bindData(BusCommuteTipBean busCommuteTipBean) {
        this.mIconIv.setImageResource(busCommuteTipBean.iconId);
        this.mIconIv.setVisibility(busCommuteTipBean.hasIcon ? 0 : 8);
        if (!TextUtils.isEmpty(busCommuteTipBean.destinationStr)) {
            if (busCommuteTipBean.destinationStr.length() <= 10) {
                this.mdestinationTv.setText(busCommuteTipBean.destinationStr);
            } else {
                TextView textView = this.mdestinationTv;
                StringBuilder sb = new StringBuilder();
                sb.append(busCommuteTipBean.destinationStr.substring(0, 10));
                sb.append("...");
                textView.setText(sb.toString());
            }
        }
        if (!TextUtils.isEmpty(busCommuteTipBean.etaStr)) {
            this.mEtaTv.setText(busCommuteTipBean.etaStr);
        }
        Resources resources = AMapAppGlobal.getApplication().getResources();
        if (!TextUtils.isEmpty(busCommuteTipBean.lineName)) {
            if (busCommuteTipBean.lineName.length() <= 6) {
                this.mLineNameTv.setText(busCommuteTipBean.lineName);
            } else {
                TextView textView2 = this.mLineNameTv;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(busCommuteTipBean.lineName.substring(0, 6));
                sb2.append("...");
                textView2.setText(sb2.toString());
            }
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (busCommuteTipBean.firstSegmentBusType == 2 || busCommuteTipBean.firstSegmentBusType == 3 || busCommuteTipBean.firstSegmentBusType == 10) {
            gradientDrawable.setColor(Color.parseColor(busCommuteTipBean.customLineColor));
            this.mLineNameTv.setTextColor(resources.getColor(R.color.f_c_1));
        } else if (busCommuteTipBean.firstSegmentBusType == 12) {
            gradientDrawable.setStroke(DimensionUtils.dipToPixel(1.0f), Color.parseColor(busCommuteTipBean.customLineColor));
            this.mLineNameTv.setTextColor(Color.parseColor(busCommuteTipBean.customLineColor));
        } else {
            gradientDrawable.setStroke(DimensionUtils.dipToPixel(1.0f), resources.getColor(R.color.f_c_6));
            this.mLineNameTv.setTextColor(resources.getColor(R.color.f_c_6));
        }
        gradientDrawable.setCornerRadius((float) DimensionUtils.dipToPixel(15.0f));
        this.mLineNameTv.setBackgroundDrawable(gradientDrawable);
        if (!busCommuteTipBean.isRealtime || TextUtils.isEmpty(busCommuteTipBean.realtimeStr)) {
            this.mRealTimeTv.setText(busCommuteTipBean.transferInfo);
            this.mRealTimeTv.setTextColor(resources.getColor(R.color.f_c_4));
        } else {
            this.mRealTimeTv.setText(busCommuteTipBean.realtimeStr);
            this.mRealTimeTv.setTextColor(resources.getColor(busCommuteTipBean.realtimeColorId));
        }
        if (busCommuteTipBean.time_tag != 0) {
            this.mBusStatusTv.setVisibility(0);
            this.mEtaTv.setVisibility(8);
            this.mBusStatusTv.setText(busCommuteTipBean.busStatus);
            return;
        }
        this.mBusStatusTv.setVisibility(8);
        this.mEtaTv.setVisibility(0);
    }

    public synchronized void show(final BusCommuteTipBean busCommuteTipBean, boolean z) {
        if (z) {
            bindData(busCommuteTipBean);
            setTipClick();
            if (TextUtils.isEmpty(busCommuteTipBean.iconUrl) || TextUtils.isEmpty(busCommuteTipBean.iconUrl.trim())) {
                updateMarkerAndPosition(busCommuteTipBean);
                return;
            }
            this.target = new bkf() {
                public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                    BusCommuteTipOverlay.this.mIconIv.setImageBitmap(bitmap);
                    BusCommuteTipOverlay.this.updateMarkerAndPosition(busCommuteTipBean);
                }

                public final void onBitmapFailed(Drawable drawable) {
                    BusCommuteTipOverlay.this.mIconIv.setImageResource(busCommuteTipBean.iconId);
                    BusCommuteTipOverlay.this.updateMarkerAndPosition(busCommuteTipBean);
                }

                public final void onPrepareLoad(Drawable drawable) {
                    BusCommuteTipOverlay.this.mIconIv.setImageResource(busCommuteTipBean.iconId);
                    BusCommuteTipOverlay.this.updateMarkerAndPosition(busCommuteTipBean);
                }
            };
            ko.a(this.mIconIv, busCommuteTipBean.iconUrl, null, busCommuteTipBean.iconId, this.target);
            return;
        }
        updateOverlayPosition(busCommuteTipBean.currentLocPoint);
    }

    /* access modifiers changed from: private */
    public void updateMarkerAndPosition(BusCommuteTipBean busCommuteTipBean) {
        this.mRootView.destroyDrawingCache();
        this.mOverlayDefaultMarker = createMarker(0, this.mRootView, 5, 0.0f, 0.0f, false);
        updateOverlayPosition(busCommuteTipBean.currentLocPoint);
        ayp.a(ayp.a, ayp.i, busCommuteTipBean, false);
    }

    private void updateOverlayPosition(GeoPoint geoPoint) {
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        removeAll();
        addItem(pointOverlayItem);
    }

    private void setTipClick() {
        ArrayList arrayList = new ArrayList();
        this.mJumpAreaView.measure(0, 0);
        aly aly = new aly(this.mJumpAreaView.getMeasuredWidth() - DimensionUtils.dipToPixel(10.0f), this.mJumpAreaView.getMeasuredHeight());
        aly.d = true;
        aly.e = new defpackage.aly.a() {
            public final void a() {
                azb.a("song---", "onTipOverlayClick ---");
                if (BusCommuteTipOverlay.this.mListener != null) {
                    BusCommuteTipOverlay.this.mListener.k();
                }
            }
        };
        this.mCloseIv.measure(0, 0);
        aly aly2 = new aly(this.mCloseIv.getMeasuredWidth() + DimensionUtils.dipToPixel(10.0f), this.mCloseIv.getMeasuredHeight() - DimensionUtils.dipToPixel(10.0f));
        aly2.d = true;
        aly2.e = new defpackage.aly.a() {
            public final void a() {
                if (BusCommuteTipOverlay.this.mListener != null) {
                    BusCommuteTipOverlay.this.mListener.l();
                }
            }
        };
        arrayList.add(aly);
        arrayList.add(aly2);
        setClickList(arrayList);
    }

    public void setOnBusCommuteTipClickListener(a aVar) {
        this.mListener = aVar;
    }

    public void hide() {
        removeAll();
    }
}

package com.autonavi.bundle.uitemplate.mapwidget.widget.compass;

import android.content.res.Configuration;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.compass.UICompassView.CompassAngleChangeListener;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;

public class CompassWidgetPresenter extends BaseMapWidgetPresenter<CompassMapWidget> implements cdf {
    private bty mMapView = DoNotUseTool.getMapManager().getMapView();

    public CompassWidgetPresenter() {
        cdd.n().a((cdf) this);
    }

    public void bindListener() {
        if (this.mBindWidget != null) {
            setWidgetEventIndex(((CompassMapWidget) this.mBindWidget).getCompassView(), 0);
            onBindListener(((CompassMapWidget) this.mBindWidget).getCompassView());
            if (isViewNull()) {
                ((CompassMapWidget) this.mBindWidget).getCompassView().setAngleListener(new CompassAngleChangeListener() {
                    public void onAngleChanged(float f, float f2) {
                        CompassWidgetPresenter.this.resetCompassState();
                    }
                });
            }
        }
    }

    public void internalClickListener(View view) {
        if (!isViewNull() && view != null && view.getId() == ((CompassMapWidget) this.mBindWidget).getCompassView().getId()) {
            onCompassWidgetClick(true);
            logUpdate();
            brm brm = (brm) ank.a(brm.class);
            if (brm != null) {
                brm.h();
            }
        }
    }

    private void logUpdate() {
        LogManager.actionLogV2("P00001", "B006", getLogVersionStateParam());
    }

    public boolean isViewNull() {
        return this.mBindWidget == null || ((CompassMapWidget) this.mBindWidget).getContentView() == null || ((CompassMapWidget) this.mBindWidget).getCompassView() == null;
    }

    public void paintCompass() {
        if (!isAddToWidgetContainer()) {
            setCompassViewVisible(8);
        } else if (!isViewNull() && this.mMapView != null) {
            UICompassView compassView = ((CompassMapWidget) this.mBindWidget).getCompassView();
            float I = this.mMapView.I();
            float J = this.mMapView.J();
            if (((CompassMapWidget) this.mBindWidget).getVisibility() == 0) {
                changeCompassVisibility(I, J);
                compassView.paintCompass(J, I);
                return;
            }
            if (I > 0.0f || J > 0.0f) {
                Integer resetCompassState = resetCompassState();
                if (resetCompassState != null && resetCompassState.intValue() == 0) {
                    compassView.paintCompass(J, I);
                }
            }
        }
    }

    private void changeCompassVisibility(float f, float f2) {
        if (!isViewNull()) {
            int i = 0;
            boolean z = f > 0.0f || f2 > 0.0f;
            if (z) {
                this.mMapView.B();
            }
            if (!z) {
                i = 8;
            }
            setCompassViewVisible(i);
        }
    }

    private void setCompassViewVisible(int i) {
        if (!isViewNull()) {
            ((CompassMapWidget) this.mBindWidget).setVisibility(i);
        }
    }

    public Integer resetCompassState() {
        if (isViewNull()) {
            return null;
        }
        int i = (this.mMapView.I() > 0.0f || this.mMapView.J() > 0.0f) ? 0 : 8;
        setCompassViewVisible(i);
        if (i == 0) {
            this.mMapView.B();
        }
        return Integer.valueOf(i);
    }

    private void onCompassWidgetClick(boolean z) {
        if (!isViewNull()) {
            if (this.mMapView.J() > 0.0f) {
                if (bin.a.o("201")) {
                    this.mMapView.e(true);
                } else {
                    this.mMapView.B();
                }
            }
            if (z) {
                this.mMapView.K();
            } else {
                this.mMapView.L();
            }
            if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                DoNotUseTool.getSuspendManager().d().e();
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        updateCompassByOrientation(configuration.orientation == 2);
    }

    private void updateCompassByOrientation(boolean z) {
        if (!isViewNull()) {
            LayoutParams layoutParams = (LayoutParams) ((CompassMapWidget) this.mBindWidget).getCompassView().getLayoutParams();
            int dimension = (int) ((CompassMapWidget) this.mBindWidget).getContentView().getContext().getResources().getDimension(R.dimen.compass_margin_left);
            if (z) {
                layoutParams.setMargins(0, 0, dimension, 0);
            } else {
                layoutParams.setMargins(0, 0, 0, 0);
            }
            ((CompassMapWidget) this.mBindWidget).getCompassView().setLayoutParams(layoutParams);
        }
    }
}

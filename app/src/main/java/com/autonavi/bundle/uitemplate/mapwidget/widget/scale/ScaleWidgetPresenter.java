package com.autonavi.bundle.uitemplate.mapwidget.widget.scale;

import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.suspend.refactor.scale.ScaleView;

public class ScaleWidgetPresenter extends BaseMapWidgetPresenter<ScaleMapWidget> {
    public void initialize(ScaleMapWidget scaleMapWidget) {
        super.initialize(scaleMapWidget);
        if (isWidgetNotNull()) {
            ScaleView scaleView = ((ScaleMapWidget) this.mBindWidget).getScaleView();
            if (scaleView != null) {
                scaleView.setMapManager(DoNotUseTool.getMapManager());
                scaleView.setAmapLogoVisibility(true);
                scaleView.setVisibility(0);
                scaleView.setContentDescription(null);
            }
        }
    }

    public void internalClickListener(View view) {
        changeScaleViewLogoStatus(true);
    }

    public void pageResume(bid bid) {
        changeScaleViewLogoStatus(true);
    }

    public void changeScaleViewLogoStatus(boolean z) {
        ScaleView scaleView = ((ScaleMapWidget) this.mBindWidget).getScaleView();
        if (scaleView != null) {
            scaleView.setAmapLogoVisibility(z);
        }
    }
}

package com.autonavi.minimap.route.ride.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.extension.UCCore;

public class RideRelativeLayout extends RelativeLayout {
    private DisplayMetrics deviceMetric;

    public RideRelativeLayout(Context context) {
        super(context);
    }

    public RideRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RideRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private DisplayMetrics getDeviceMetric() {
        if (this.deviceMetric == null) {
            this.deviceMetric = new DisplayMetrics();
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext != null) {
                WindowManager windowManager = (WindowManager) appContext.getSystemService(TemplateTinyApp.WINDOW_KEY);
                if (!(windowManager == null || windowManager.getDefaultDisplay() == null)) {
                    windowManager.getDefaultDisplay().getMetrics(this.deviceMetric);
                }
            }
        }
        return this.deviceMetric;
    }

    @TargetApi(21)
    public RideRelativeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        DisplayMetrics deviceMetric2 = getDeviceMetric();
        int i3 = ((int) (((float) deviceMetric2.widthPixels) - (deviceMetric2.density * 58.0f))) / 2;
        super.onMeasure(MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec((i3 * 3) / 4, UCCore.VERIFY_POLICY_QUICK));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }
}

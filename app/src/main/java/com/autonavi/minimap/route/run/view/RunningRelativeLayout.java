package com.autonavi.minimap.route.run.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import com.uc.webview.export.extension.UCCore;

public class RunningRelativeLayout extends RelativeLayout {
    private DisplayMetrics metric = new DisplayMetrics();

    public RunningRelativeLayout(Context context) {
        super(context);
    }

    public RunningRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RunningRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public RunningRelativeLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ((WindowManager) AMapAppGlobal.getApplication().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(this.metric);
        int i3 = ((int) (((float) this.metric.widthPixels) - (this.metric.density * 58.0f))) / 2;
        super.onMeasure(MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec((i3 * 3) / 4, UCCore.VERIFY_POLICY_QUICK));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }
}

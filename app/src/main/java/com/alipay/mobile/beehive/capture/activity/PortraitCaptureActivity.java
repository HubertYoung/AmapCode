package com.alipay.mobile.beehive.capture.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.views.CaptureV2MaskView.MaskOptions;

public class PortraitCaptureActivity extends CaptureV2OrientationActivity {
    private static final float DEFAULT_HEIGHT_PERCENT = 0.627f;
    private static final float DEFAULT_WIDTH_PERCENT = 0.752f;
    private RelativeLayout contentRoot;

    /* access modifiers changed from: protected */
    public int getLayoutResId() {
        return R.layout.activity_capture_v2;
    }

    /* access modifiers changed from: protected */
    public void initViews() {
        super.initViews();
        this.contentRoot = (RelativeLayout) findViewById(R.id.rl_portrait_capture_content);
    }

    /* access modifiers changed from: protected */
    public int getActivityRotation() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public MaskOptions calcMaskOptions(int width, int height) {
        int titleHeight = findViewById(R.id.title_panel).getHeight();
        int bottomHeight = findViewById(R.id.control_panel).getHeight();
        this.widthPercent = this.widthPercent <= 0.0f ? DEFAULT_WIDTH_PERCENT : this.widthPercent;
        this.heightPercent = this.heightPercent <= 0.0f ? DEFAULT_HEIGHT_PERCENT : this.heightPercent;
        int rectWidth = (int) (((float) width) * this.widthPercent);
        int rectHeight = (int) (((float) height) * this.heightPercent);
        int top = ((((height - titleHeight) - bottomHeight) - rectHeight) / 2) + titleHeight;
        return new MaskOptions(new Rect((width - rectWidth) / 2, top, (width + rectWidth) / 2, top + rectHeight));
    }

    /* access modifiers changed from: protected */
    public Class<?> getPreviewClass() {
        return PortraitRecordPreview.class;
    }

    /* access modifiers changed from: protected */
    public void dispatchUpdateUI(Bundle args) {
        super.dispatchUpdateUI(args);
        renderCenterTip(args, this.contentRoot);
    }
}

package com.alipay.mobile.beehive.capture.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.views.CaptureV2MaskView.MaskOptions;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

public class LandscapeCaptureActivity extends CaptureV2OrientationActivity {
    private static final float DEFAULT_HEIGHT_PERCENT = 0.752f;
    private static final float DEFAULT_WIDTH_PERCENT = 0.675f;
    private RelativeLayout contentRoot;
    /* access modifiers changed from: private */
    public int mLastRotation;
    private OrientationEventListener orientationEventListener;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orientationEventListener = new OrientationEventListener(this) {
            public final void onOrientationChanged(int orientation) {
                int rotation = LandscapeCaptureActivity.this.getRotation();
                if ((rotation == 1 || rotation == 3) && rotation != LandscapeCaptureActivity.this.mLastRotation) {
                    LandscapeCaptureActivity.this.mLastRotation = rotation;
                    LandscapeCaptureActivity.this.resetCameraViewRotation(LandscapeCaptureActivity.this.covertAngle());
                }
            }
        };
        if (this.orientationEventListener.canDetectOrientation()) {
            this.orientationEventListener.enable();
        }
    }

    /* access modifiers changed from: protected */
    public void initViews() {
        super.initViews();
        this.contentRoot = (RelativeLayout) findViewById(R.id.rl_capture_content);
    }

    /* access modifiers changed from: private */
    public int covertAngle() {
        return 360 - (this.mLastRotation * 90);
    }

    /* access modifiers changed from: protected */
    public int getLayoutResId() {
        return R.layout.activity_capture_v2_landscape;
    }

    /* access modifiers changed from: protected */
    public int getActivityRotation() {
        this.mLastRotation = getRotation();
        return covertAngle();
    }

    /* access modifiers changed from: protected */
    public MaskOptions calcMaskOptions(int width, int height) {
        int panelWidth = findViewById(R.id.control_panel).getWidth();
        this.widthPercent = this.widthPercent <= 0.0f ? DEFAULT_WIDTH_PERCENT : this.widthPercent;
        this.heightPercent = this.heightPercent <= 0.0f ? DEFAULT_HEIGHT_PERCENT : this.heightPercent;
        int rectWidth = (int) (((float) width) * this.widthPercent);
        int rectHeight = (int) (((float) height) * this.heightPercent);
        int left = ((width - panelWidth) - rectWidth) / 2;
        return new MaskOptions(new Rect(left, (height - rectHeight) / 2, left + rectWidth, (height + rectHeight) / 2));
    }

    /* access modifiers changed from: protected */
    public Class<?> getPreviewClass() {
        return LandscapeRecordPreview.class;
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3494";
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.orientationEventListener.disable();
    }

    /* access modifiers changed from: private */
    public int getRotation() {
        return ((WindowManager) getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getRotation();
    }

    /* access modifiers changed from: protected */
    public void dispatchUpdateUI(Bundle args) {
        super.dispatchUpdateUI(args);
        renderCenterTip(args, this.contentRoot);
    }
}

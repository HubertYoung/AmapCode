package com.alipay.mobile.beehive.capture.activity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.capture.constant.Constants;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.impl.CaptureServiceImpl;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.views.AutoResizeImageView;
import com.alipay.mobile.beehive.capture.views.CaptureV2MaskView.MaskOptions;

public class LandscapeCaptureForCarInsuranceActivity extends LandscapeCaptureActivity implements OnClickListener {
    private static final String TAG = "LandscapeCaptureForCarInsuranceActivity";
    private RelativeLayout mContentRoot;
    private AutoResizeImageView mFocusImage;
    private ImageView mGuideImage;
    private int mLeftControlPanelWidth;
    private ImageView mPreviewImage;
    private View mRightControlPanel;
    private ImageView mSampleImage;
    private TextView mSceneText;
    private int mTopControlPanelHeight;

    /* access modifiers changed from: protected */
    public int getLayoutResId() {
        return R.layout.activity_capture_for_car_insurance;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void initViews() {
        super.initViews();
        this.mGuideImage = (ImageView) findViewById(R.id.iv_guide);
        this.mPreviewImage = (ImageView) findViewById(R.id.iv_preview);
        this.mSampleImage = (ImageView) findViewById(R.id.iv_sample);
        this.mFocusImage = (AutoResizeImageView) findViewById(R.id.iv_focus);
        this.mFocusImage.setFitParent(true);
        this.mSceneText = (TextView) findViewById(R.id.tv_scene);
        this.mRightControlPanel = findViewById(R.id.control_panel);
        this.mPreviewImage.setOnClickListener(this);
        this.mSampleImage.setOnClickListener(this);
        this.mLeftControlPanelWidth = (int) getResources().getDimension(R.dimen.di_landscape_left_control_panel_width);
        this.mTopControlPanelHeight = (int) getResources().getDimension(R.dimen.di_landscape_top_control_panel_height);
        this.mMaskView.updateStyle(true, false, false);
        this.mContentRoot = (RelativeLayout) findViewById(R.id.rl_landscape_content);
        pendingHideLeftRightMask();
    }

    private void pendingHideLeftRightMask() {
        if (3 == maskMode) {
            findViewById(R.id.v_left_mask).setVisibility(4);
            findViewById(R.id.control_panel).setBackgroundColor(0);
            View centerContainer = findViewById(R.id.fl_center_image_container);
            LayoutParams rlp = (LayoutParams) centerContainer.getLayoutParams();
            rlp.removeRule(0);
            rlp.rightMargin = (int) getResources().getDimension(R.dimen.di_landscape_left_control_panel_width);
            centerContainer.setLayoutParams(rlp);
        }
    }

    /* access modifiers changed from: protected */
    public MaskOptions calcMaskOptions(int width, int height) {
        float f = 1.0f;
        int height2 = height - (this.mTopControlPanelHeight * 2);
        int width2 = (width - this.mLeftControlPanelWidth) - this.mRightControlPanel.getWidth();
        this.widthPercent = this.widthPercent <= 0.0f ? 1.0f : this.widthPercent;
        if (this.heightPercent > 0.0f) {
            f = this.heightPercent;
        }
        this.heightPercent = f;
        int rectWidth = (int) (((float) width2) * this.widthPercent);
        int rectHeight = (int) (((float) height2) * this.heightPercent);
        if (this.whRatio > 0.0f) {
            rectHeight = (int) (((float) rectWidth) / this.whRatio);
            if (rectHeight > height2) {
                rectHeight = height2;
                Logger.debug(TAG, "Too high ,cut it.");
            }
        }
        int top = ((height2 - rectHeight) / 2) + this.mTopControlPanelHeight;
        int left = ((width2 - rectWidth) / 2) + this.mLeftControlPanelWidth;
        return new MaskOptions(new Rect(left, top, left + rectWidth, top + rectHeight));
    }

    /* access modifiers changed from: protected */
    public void dispatchUpdateUI(Bundle args) {
        if (args.containsKey(CaptureParam.UPDATE_UI_FOCUS_IMAGE)) {
            loadImageDepends(args.getString(CaptureParam.UPDATE_UI_FOCUS_IMAGE), this.mFocusImage);
        }
        if (args.containsKey(CaptureParam.UPDATE_UI_GUIDE_IMAGE)) {
            loadImageDepends(args.getString(CaptureParam.UPDATE_UI_GUIDE_IMAGE), this.mGuideImage);
        }
        if (args.containsKey(CaptureParam.UPDATE_UI_PREVIEW_IMAGE)) {
            loadImageDepends(args.getString(CaptureParam.UPDATE_UI_PREVIEW_IMAGE), this.mPreviewImage);
        }
        if (args.containsKey(CaptureParam.UPDATE_UI_SAMPLE_IMAGE)) {
            loadImageDepends(args.getString(CaptureParam.UPDATE_UI_SAMPLE_IMAGE), this.mSampleImage);
        }
        if (args.containsKey(CaptureParam.UPDATE_UI_SCENE_TEXT)) {
            String scene = args.getString(CaptureParam.UPDATE_UI_SCENE_TEXT);
            if (!TextUtils.isEmpty(scene)) {
                this.mSceneText.setVisibility(0);
                this.mSceneText.setText(scene);
            } else {
                this.mSceneText.setVisibility(8);
            }
        }
        if (args.containsKey("centerTip")) {
            renderCenterTip(args, this.mContentRoot);
        }
    }

    public void onClick(View v) {
        if (v == this.mPreviewImage) {
            CaptureServiceImpl.notifyRecorderEvent(Constants.JS_METHOD_ON_RECORDER_PREVIEW_CLICKED, null, false);
        } else if (v == this.mSampleImage) {
            CaptureServiceImpl.notifyRecorderEvent(Constants.JS_METHOD_ON_RECORDER_SAMPLE_CLICKED, null, false);
        }
    }
}

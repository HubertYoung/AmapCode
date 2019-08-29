package com.alipay.mobile.beehive.capture.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
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

public class PortraitCaptureExtendActivity extends PortraitCaptureActivity implements OnClickListener {
    private static final String TAG = "PortraitCaptureExtendActivity";
    /* access modifiers changed from: private */
    public ImageView ivScanEffect;
    private View mBottomControlPanel;
    private AutoResizeImageView mFocusImage;
    private ImageView mGuideImage;
    private int mHintHeight;
    private ImageView mPreviewImage;
    private ImageView mSampleImage;
    private ObjectAnimator mScanAnim;
    private TextView mSceneText;
    private RelativeLayout vContainer;

    /* access modifiers changed from: protected */
    public int getLayoutResId() {
        return R.layout.activity_portrait_capture_extend;
    }

    /* access modifiers changed from: protected */
    public void initViews() {
        super.initViews();
        this.mGuideImage = (ImageView) findViewById(R.id.iv_guide);
        this.mPreviewImage = (ImageView) findViewById(R.id.iv_preview);
        this.mSampleImage = (ImageView) findViewById(R.id.iv_sample);
        this.mFocusImage = (AutoResizeImageView) findViewById(R.id.iv_focus);
        this.mFocusImage.setFitParent(true);
        this.mSceneText = (TextView) findViewById(R.id.tv_scene);
        this.mBottomControlPanel = findViewById(R.id.control_panel);
        this.ivScanEffect = (ImageView) findViewById(R.id.iv_scan_effect);
        this.vContainer = (RelativeLayout) findViewById(R.id.portrait_content_container);
        this.mPreviewImage.setOnClickListener(this);
        this.mSampleImage.setOnClickListener(this);
        this.mMaskView.updateStyle(true, false, false);
        this.mHintHeight = Math.round(getResources().getDimension(R.dimen.di_portrait_extend_hint_height));
        if (2 == maskMode) {
            this.mBottomControlPanel.setVisibility(4);
        } else {
            this.mBottomControlPanel.setVisibility(0);
        }
        pendingRemoveTopBottomMask();
    }

    private void pendingRemoveTopBottomMask() {
        if (3 == maskMode) {
            findViewById(R.id.title_panel).setBackgroundColor(0);
            findViewById(R.id.control_panel).setBackgroundColor(0);
            View centerContainer = findViewById(R.id.fl_center_image_container);
            LayoutParams rlp = (LayoutParams) centerContainer.getLayoutParams();
            rlp.removeRule(2);
            rlp.bottomMargin = (int) getResources().getDimension(R.dimen.di_top_control_height);
            centerContainer.setLayoutParams(rlp);
        }
    }

    /* access modifiers changed from: protected */
    public MaskOptions calcMaskOptions(int width, int height) {
        float f = 1.0f;
        int topPanelHeight = Math.round(getResources().getDimension(R.dimen.di_top_control_height));
        int height2 = ((height - topPanelHeight) - this.mBottomControlPanel.getHeight()) - (this.mHintHeight * 2);
        this.widthPercent = this.widthPercent <= 0.0f ? 1.0f : this.widthPercent;
        if (this.heightPercent > 0.0f) {
            f = this.heightPercent;
        }
        this.heightPercent = f;
        int rectWidth = (int) (((float) width) * this.widthPercent);
        int rectHeight = (int) (((float) height2) * this.heightPercent);
        if (this.whRatio > 0.0f) {
            rectHeight = (int) (((float) rectWidth) / this.whRatio);
            if (rectHeight > height2) {
                rectHeight = height2;
                Logger.debug(TAG, "Too high ,cut it.");
            }
        }
        int left = (width - rectWidth) / 2;
        int top = ((height2 - rectHeight) / 2) + topPanelHeight + this.mHintHeight;
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
        if (args.containsKey(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM)) {
            boolean isShow = args.getBoolean(CaptureParam.UPDATE_UI_SHOW_SCAN_ANIM);
            if (args.getInt(CaptureParam.CAPTURE_SCAN_EFFECT) == 0) {
                toggleScanAnim(isShow);
            }
        }
        if (args.containsKey("centerTip")) {
            renderCenterTip(args, this.vContainer);
        }
    }

    private void toggleScanAnim(boolean isShow) {
        cancelOldAnim();
        if (isShow) {
            this.mScanAnim = ObjectAnimator.ofFloat(this.ivScanEffect, "translationY", new float[]{(float) (-this.ivScanEffect.getMeasuredHeight()), (float) this.vContainer.getHeight()});
            this.mScanAnim.setRepeatMode(1);
            this.mScanAnim.setDuration(2000);
            this.mScanAnim.setRepeatCount(-1);
            this.mScanAnim.addListener(new AnimatorListener() {
                public final void onAnimationStart(Animator animation) {
                    PortraitCaptureExtendActivity.this.ivScanEffect.setVisibility(0);
                }

                public final void onAnimationEnd(Animator animation) {
                }

                public final void onAnimationCancel(Animator animation) {
                }

                public final void onAnimationRepeat(Animator animation) {
                }
            });
            this.mScanAnim.start();
            return;
        }
        this.ivScanEffect.setVisibility(4);
    }

    private void cancelOldAnim() {
        if (this.mScanAnim != null && this.mScanAnim.isRunning()) {
            this.mScanAnim.cancel();
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

package com.alipay.mobile.antui.dialog;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AURecordFloatTip {
    private final Activity activity;
    private final AnimationDrawable animationDrawable;
    private final View floatTip;
    private final AUImageView iconView;
    private final FrameLayout mContentView;
    private final AUTextView tipTextView;

    public AURecordFloatTip(Activity activity2) {
        this(activity2, null);
    }

    public AURecordFloatTip(Activity activity2, String tip) {
        this.activity = activity2;
        this.mContentView = (FrameLayout) activity2.findViewById(16908290);
        this.floatTip = activity2.getLayoutInflater().inflate(R.layout.msg_record_prompt, null);
        this.iconView = (AUImageView) this.floatTip.findViewById(R.id.voice_prompt_image);
        this.tipTextView = (AUTextView) this.floatTip.findViewById(R.id.voice_prompt_msg);
        this.tipTextView.setText(tip);
        this.iconView.setBackgroundResource(R.drawable.voice_frame);
        this.animationDrawable = (AnimationDrawable) this.iconView.getBackground();
    }

    public void show() {
        LayoutParams flp = new LayoutParams(DensityUtil.dip2px(this.activity, 180.0f), DensityUtil.dip2px(this.activity, 180.0f));
        flp.gravity = 17;
        this.mContentView.removeView(this.floatTip);
        this.mContentView.addView(this.floatTip, flp);
        if (this.animationDrawable != null) {
            this.animationDrawable.start();
        }
    }

    public void dismiss() {
        this.mContentView.removeView(this.floatTip);
        if (this.animationDrawable != null) {
            this.animationDrawable.stop();
        }
    }

    public AUTextView getTipTextView() {
        return this.tipTextView;
    }

    public AUImageView getIconView() {
        return this.iconView;
    }
}

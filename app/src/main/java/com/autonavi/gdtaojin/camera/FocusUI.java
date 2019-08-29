package com.autonavi.gdtaojin.camera;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.common.share.widget.ResUtils;

public class FocusUI {
    public int blue_focus_id;
    public Context mContext;
    public ImageView mFocusView;
    public Resources mResources;
    public int white_focus_id;

    public void onFocusFail() {
    }

    public FocusUI(Context context, Resources resources) {
        this.mContext = context;
        this.mResources = resources;
        int identifier = this.mResources.getIdentifier("camera_focus_view", "id", context.getPackageName());
        this.white_focus_id = this.mResources.getIdentifier("gxdcam_white_focus", ResUtils.DRAWABLE, context.getPackageName());
        this.blue_focus_id = this.mResources.getIdentifier("gxdcam_blue_focus", ResUtils.DRAWABLE, context.getPackageName());
        this.mFocusView = (ImageView) ((Activity) this.mContext).findViewById(identifier);
    }

    public void clearFocus() {
        if (this.mFocusView != null) {
            this.mFocusView.setVisibility(8);
        }
    }

    public void onFocusStarted() {
        onFocusStarted(CameraConst.widthPixels / 2, CameraConst.heightPixels / 2);
    }

    public void onFocusStarted(int i, int i2) {
        if (this.mFocusView != null) {
            int visibility = this.mFocusView.getVisibility();
            if (visibility == 8 || visibility == 4) {
                this.mFocusView.setVisibility(0);
            }
            LayoutParams layoutParams = (LayoutParams) this.mFocusView.getLayoutParams();
            layoutParams.setMargins(i - ((int) (CameraConst.density * 42.0f)), i2 - ((int) (CameraConst.density * 42.0f)), 0, 0);
            this.mFocusView.setLayoutParams(layoutParams);
            this.mFocusView.setImageResource(this.white_focus_id);
            this.mFocusView.setVisibility(0);
            ScaleOutAnimation(this.mFocusView);
        }
    }

    public void onFocusSucceeded() {
        if (this.mFocusView != null && this.mFocusView.getVisibility() == 0) {
            this.mFocusView.setImageResource(this.blue_focus_id);
        }
    }

    public void ScaleOutAnimation(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 180.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        view.startAnimation(rotateAnimation);
    }
}

package com.autonavi.minimap.route.foot.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.minimap.R;

@SuppressLint({"AppCompatCustomView"})
public class CompassBG extends FrameLayout {
    private static int ANIM_TRANS_GAP = 20;
    private static int ANIM_TRANS_POINTER_GAP = 30;
    private static int DEFAULT_ARROW_HEIGHT = 140;
    private static int DEFAULT_BG_HEIGHT = 110;
    private static int DEFAULT_WIDTH = 28;
    private int bgheight;
    private DecelerateInterpolator decelerateInterpolator;
    private AnimatorSet endAnimSet;
    private int height;
    private Context mContext;
    private ImageView pointer;
    private ImageView pointerBg;
    private AnimatorSet startAnimSet;
    private int width;

    public CompassBG(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CompassBG(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    public CompassBG(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        init();
    }

    public CompassBG(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mContext = context;
        init();
    }

    private void init() {
        this.pointer = null;
        this.pointerBg = null;
        this.decelerateInterpolator = new DecelerateInterpolator();
        float f = getResources().getDisplayMetrics().density;
        this.width = (int) (((float) DEFAULT_WIDTH) * f);
        this.bgheight = (int) (((float) DEFAULT_BG_HEIGHT) * f);
        this.height = (int) (((float) DEFAULT_ARROW_HEIGHT) * f);
        ANIM_TRANS_GAP = (int) (((float) ANIM_TRANS_GAP) * f);
        ANIM_TRANS_POINTER_GAP = (int) (((float) ANIM_TRANS_POINTER_GAP) * f);
        this.pointerBg = new ImageView(this.mContext);
        this.pointerBg.setImageResource(R.drawable.compass_direction_bg);
        LayoutParams layoutParams = new LayoutParams(this.width, this.bgheight);
        layoutParams.gravity = 1;
        this.pointerBg.setLayoutParams(layoutParams);
        addView(this.pointerBg);
        this.pointer = new ImageView(this.mContext);
        this.pointer.setImageResource(R.drawable.compass_arrow);
        LayoutParams layoutParams2 = new LayoutParams(this.width, this.height);
        layoutParams2.gravity = 1;
        this.pointer.setLayoutParams(layoutParams2);
        addView(this.pointer);
    }

    private void initStartAnimatorSet() {
        float translationY = this.pointer.getTranslationY();
        float translationY2 = this.pointerBg.getTranslationY();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.pointer, "translationY", new float[]{((float) ANIM_TRANS_GAP) + translationY, (float) ANIM_TRANS_GAP, translationY});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.pointerBg, "translationY", new float[]{((float) ANIM_TRANS_GAP) + translationY2, (float) ANIM_TRANS_GAP, translationY2});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.pointerBg, "alpha", new float[]{0.0f, 1.0f});
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.pointer, "alpha", new float[]{0.0f, 1.0f});
        this.startAnimSet = new AnimatorSet();
        this.startAnimSet.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat4, ofFloat3});
        this.startAnimSet.setDuration(330);
        this.startAnimSet.setInterpolator(this.decelerateInterpolator);
    }

    private void initEndAnimtorSet(AnimatorListenerAdapter animatorListenerAdapter) {
        float translationY = this.pointer.getTranslationY();
        float translationY2 = this.pointerBg.getTranslationY();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.pointer, "translationY", new float[]{translationY, ((float) ANIM_TRANS_GAP) + translationY, translationY + ((float) ANIM_TRANS_GAP)});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.pointerBg, "translationY", new float[]{translationY2, ((float) ANIM_TRANS_GAP) + translationY2, translationY2 + ((float) ANIM_TRANS_GAP)});
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.pointerBg, "alpha", new float[]{1.0f, 0.0f});
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.pointer, "alpha", new float[]{1.0f, 0.0f});
        ofFloat3.setDuration(330);
        ofFloat4.setDuration(330);
        ofFloat.setDuration(330);
        ofFloat2.setDuration(330);
        ofFloat4.setStartDelay(330);
        ofFloat3.setStartDelay(330);
        this.endAnimSet = new AnimatorSet();
        this.endAnimSet.playTogether(new Animator[]{ofFloat3, ofFloat4, ofFloat, ofFloat2});
        this.endAnimSet.addListener(animatorListenerAdapter);
        this.endAnimSet.setInterpolator(this.decelerateInterpolator);
    }

    public void pointerShowAnim() {
        if (this.startAnimSet == null) {
            initStartAnimatorSet();
        }
        this.startAnimSet.start();
        setVisibility(0);
    }

    public void pointerhideAnim(AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.endAnimSet == null) {
            initEndAnimtorSet(animatorListenerAdapter);
        }
        this.endAnimSet.start();
    }

    public void updateBGDirection(float f) {
        setRotation(f);
    }
}

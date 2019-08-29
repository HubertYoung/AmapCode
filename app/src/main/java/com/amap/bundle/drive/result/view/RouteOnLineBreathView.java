package com.amap.bundle.drive.result.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.minimap.R;

public class RouteOnLineBreathView extends FrameLayout {
    private static final int ANIMA_DUATION = 1400;
    private static final float END_SCALE = 1.1f;
    private static final float START_SCALE = 0.95f;
    private ImageView mBreathView;
    private ImageView mIconView;
    private ObjectAnimator mObjAnimation;

    public RouteOnLineBreathView(@NonNull Context context) {
        this(context, null);
    }

    public RouteOnLineBreathView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteOnLineBreathView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        initView();
        initAttrs(attributeSet);
        initAnimation();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_route_breath_online, this, true);
        this.mBreathView = (ImageView) findViewById(R.id.route_breath_online_bg);
        this.mIconView = (ImageView) findViewById(R.id.route_breath_online_icon);
    }

    private void initAttrs(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.RouteOnLineBreathView);
        if (obtainStyledAttributes != null) {
            int resourceId = obtainStyledAttributes.getResourceId(R.styleable.RouteOnLineBreathView_breath_src, R.drawable.route_breath_online_bg);
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RouteOnLineBreathView_breath_width, getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size));
            int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RouteOnLineBreathView_breath_height, getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size));
            int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.RouteOnLineBreathView_icon_src, R.drawable.icon_c24_selector);
            int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.RouteOnLineBreathView_icon_background, R.drawable.icon_c_bg_single);
            int dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RouteOnLineBreathView_icon_width, getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size));
            int dimensionPixelOffset4 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RouteOnLineBreathView_icon_height, getResources().getDimensionPixelOffset(R.dimen.map_container_btn_size));
            setBreathViewResource(resourceId);
            setViewLayoutParams(this.mBreathView, dimensionPixelOffset, dimensionPixelOffset2);
            setViewLayoutParams(this.mIconView, dimensionPixelOffset3, dimensionPixelOffset4);
            setIconViewBackground(resourceId3);
            setIconViewResource(resourceId2);
            obtainStyledAttributes.recycle();
        }
    }

    public void setBreathViewVisibility(int i) {
        this.mBreathView.setVisibility(i);
        if (i == 0) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void setViewLayoutParams(View view, int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    public void setBreathViewResource(int i) {
        this.mBreathView.setImageResource(i);
    }

    public void setIconViewResource(int i) {
        this.mIconView.setImageResource(i);
    }

    public void setIconViewBackground(int i) {
        this.mIconView.setBackgroundResource(i);
    }

    private void initAnimation() {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scaleX", new float[]{START_SCALE, END_SCALE, START_SCALE});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleY", new float[]{START_SCALE, END_SCALE, START_SCALE});
        this.mObjAnimation = ObjectAnimator.ofPropertyValuesHolder(this.mBreathView, new PropertyValuesHolder[]{ofFloat, ofFloat2});
        this.mObjAnimation.setDuration(1400);
        this.mObjAnimation.setRepeatCount(-1);
        this.mObjAnimation.setRepeatMode(1);
    }

    private void startAnimation() {
        if (!this.mObjAnimation.isStarted()) {
            this.mObjAnimation.start();
        }
    }

    private void stopAnimation() {
        this.mObjAnimation.cancel();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(@NonNull View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mBreathView.getVisibility() != 0 || !isShown()) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }
}

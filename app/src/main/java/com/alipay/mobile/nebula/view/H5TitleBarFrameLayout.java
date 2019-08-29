package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.alipay.mobile.nebula.R;

public class H5TitleBarFrameLayout extends FrameLayout {
    private ColorDrawable contentBgView = null;
    private boolean preventTouchEvent = true;

    public H5TitleBarFrameLayout(@NonNull Context context) {
        super(context);
        initBgView();
    }

    public H5TitleBarFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBgView();
    }

    public H5TitleBarFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBgView();
    }

    private void initBgView() {
        this.contentBgView = new ColorDrawable(getResources().getColor(R.color.h5_nav_bar));
        setBackgroundDrawable(this.contentBgView);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public ColorDrawable getContentBgView() {
        return this.contentBgView;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.preventTouchEvent;
    }

    public void setPreventTouchEvent(boolean preventTouchEvent2) {
        this.preventTouchEvent = preventTouchEvent2;
    }
}

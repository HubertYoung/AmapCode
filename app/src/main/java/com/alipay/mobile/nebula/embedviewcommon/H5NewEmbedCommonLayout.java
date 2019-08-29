package com.alipay.mobile.nebula.embedviewcommon;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class H5NewEmbedCommonLayout extends FrameLayout {
    private static final String TAG = "H5NewEmbedCommonLayout";

    public H5NewEmbedCommonLayout(Context context) {
        super(context);
    }

    public H5NewEmbedCommonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5NewEmbedCommonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        H5Log.debug(TAG, "onInterceptTouchEvent " + ev.toString());
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent event) {
        H5Log.d(TAG, "onTouchEvent " + event.toString());
        return super.onTouchEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        H5Log.debug(TAG, "onMeasure");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        H5Log.debug(TAG, "onLayout");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        H5Log.debug(TAG, "onDraw");
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        H5Log.debug(TAG, "onSizeChanged w=" + w + " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
    }

    public boolean performClick() {
        return super.performClick();
    }

    private void reorderChildrenByZIndex() {
        ArrayList viewsToSort = new ArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            viewsToSort.add(getChildAt(i));
        }
        Collections.sort(viewsToSort, new Comparator<View>() {
            public int compare(View view1, View view2) {
                Integer view1ZIndex;
                Integer view2ZIndex;
                if (view1.getClass().getName().contains("H5NativeInput") || view1.getClass().getName().contains("H5NativeTextArea")) {
                    view1ZIndex = Integer.valueOf(-9998);
                } else {
                    view1ZIndex = Integer.valueOf(Integer.parseInt((String) view1.getTag(R.id.h5_embedview_zindex)));
                    if (view1ZIndex == null) {
                        view1ZIndex = Integer.valueOf(0);
                    }
                }
                if (view2.getClass().getName().contains("H5NativeInput") || view1.getClass().getName().contains("H5NativeTextArea")) {
                    view2ZIndex = Integer.valueOf(-9998);
                } else {
                    view2ZIndex = Integer.valueOf(Integer.parseInt((String) view2.getTag(R.id.h5_embedview_zindex)));
                    if (view2ZIndex == null) {
                        view2ZIndex = Integer.valueOf(0);
                    }
                }
                return view1ZIndex.intValue() - view2ZIndex.intValue();
            }
        });
        for (int i2 = 0; i2 < viewsToSort.size(); i2++) {
            View view = (View) viewsToSort.get(i2);
            view.bringToFront();
            H5Log.d(TAG, "bringToFront " + ((String) view.getTag(R.id.h5_embedview_idfromjs)));
            if (VERSION.SDK_INT < 19) {
                requestLayout();
                invalidate();
            }
        }
    }

    public void addView(View child, LayoutParams params) {
        super.addView(child, params);
        reorderChildrenByZIndex();
    }

    public void removeView(View view) {
        super.removeView(view);
        reorderChildrenByZIndex();
    }

    public void updateViewLayout(View view, LayoutParams params) {
        super.updateViewLayout(view, params);
        reorderChildrenByZIndex();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        H5Log.d(TAG, "onDetachedFromWindow");
    }
}

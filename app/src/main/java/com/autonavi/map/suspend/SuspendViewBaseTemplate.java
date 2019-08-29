package com.autonavi.map.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class SuspendViewBaseTemplate extends ViewGroup {
    protected int mMostHeightMeasureSpec;
    protected int mMostWidthMeasureSpec;

    @SuppressFBWarnings({"SIC_INNER_SHOULD_BE_STATIC_NEEDS_THIS"})
    public class a {
        public ViewGroup a;
        public int b;
        public int c;
        public boolean d = true;

        public a(int i, int i2) {
            this.b = i;
            this.c = i2;
            this.a = SuspendViewBaseTemplate.this.getViewGroupByPosition(i);
        }
    }

    public abstract ViewGroup getViewGroupByPosition(int i);

    public SuspendViewBaseTemplate(Context context) {
        this(context, null);
    }

    public SuspendViewBaseTemplate(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SuspendViewBaseTemplate(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayoutParams();
    }

    private void initLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(2, R.id.mapBottomInteractiveView);
        layoutParams.addRule(3, R.id.mapTopInteractiveView);
        setLayoutParams(layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams, int i) {
        if (view != null && layoutParams != null) {
            ViewGroup viewGroupByPosition = getViewGroupByPosition(i);
            if (viewGroupByPosition != null) {
                viewGroupByPosition.addView(view, layoutParams);
            }
        }
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams, int i, int i2) {
        if (view != null && layoutParams != null) {
            ViewGroup viewGroupByPosition = getViewGroupByPosition(i);
            if (viewGroupByPosition != null) {
                viewGroupByPosition.addView(view, i2, layoutParams);
            }
        }
    }

    public void removeView(View view, int i) {
        if (view != null) {
            ViewGroup viewGroupByPosition = getViewGroupByPosition(i);
            if (viewGroupByPosition != null) {
                viewGroupByPosition.removeView(view);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        this.mMostWidthMeasureSpec = MeasureSpec.makeMeasureSpec((size - getPaddingLeft()) - getPaddingRight(), Integer.MIN_VALUE);
        this.mMostHeightMeasureSpec = MeasureSpec.makeMeasureSpec((size2 - getPaddingTop()) - getPaddingBottom(), Integer.MIN_VALUE);
        measureChildViews(this.mMostWidthMeasureSpec, this.mMostHeightMeasureSpec);
        setMeasuredDimension(resolveSize(size, i), resolveSize(size2, i2));
    }

    private void measureChildViews(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                childAt.measure(i, i2);
            }
        }
    }
}

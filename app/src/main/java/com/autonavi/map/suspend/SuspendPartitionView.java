package com.autonavi.map.suspend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressLint({"RtlHardcoded"})
public class SuspendPartitionView extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static Comparator<View> sIndexComparator = new Comparator<View>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            LayoutParams layoutParams = (LayoutParams) ((View) obj).getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) ((View) obj2).getLayoutParams();
            if (layoutParams.b < layoutParams2.b) {
                return -1;
            }
            return layoutParams.b > layoutParams2.b ? 1 : 0;
        }
    };
    private static Comparator<View> sPriorityComparator = new Comparator<View>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            LayoutParams layoutParams = (LayoutParams) ((View) obj).getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) ((View) obj2).getLayoutParams();
            if (layoutParams.d < layoutParams2.d) {
                return 1;
            }
            return layoutParams.d > layoutParams2.d ? -1 : 0;
        }
    };
    private List<View> mAllChildren;
    private List<View> mCenterViews;
    private int mCenterVisibility;
    private List<View> mEndViews;
    private int mEndVisibility;
    private int mGravity;
    private float mMinPriority;
    private int mNeedHeight;
    private int mNeedWidth;
    private int mOrientation;
    private List<View> mPriorityChildren;
    private int mStartVisibility;

    public static class LayoutParams extends MarginLayoutParams {
        public int a = -1;
        public float b;
        public boolean c = true;
        public float d;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        private LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        private LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public static LayoutParams a(android.view.ViewGroup.LayoutParams layoutParams) {
            LayoutParams layoutParams2;
            if (layoutParams == null) {
                return null;
            }
            if (layoutParams instanceof LayoutParams) {
                return (LayoutParams) layoutParams;
            }
            if (layoutParams instanceof MarginLayoutParams) {
                layoutParams2 = new LayoutParams((MarginLayoutParams) layoutParams);
            } else {
                layoutParams2 = new LayoutParams(layoutParams);
            }
            if (layoutParams instanceof android.widget.LinearLayout.LayoutParams) {
                layoutParams2.a = ((android.widget.LinearLayout.LayoutParams) layoutParams).gravity;
            } else if (layoutParams instanceof android.widget.FrameLayout.LayoutParams) {
                layoutParams2.a = ((android.widget.FrameLayout.LayoutParams) layoutParams).gravity;
            }
            return layoutParams2;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public SuspendPartitionView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mGravity = 51;
        this.mAllChildren = new ArrayList();
        this.mPriorityChildren = new ArrayList();
        this.mCenterViews = new ArrayList();
        this.mEndViews = new ArrayList();
        this.mStartVisibility = 0;
        this.mCenterVisibility = 0;
        this.mEndVisibility = 0;
        this.mMinPriority = -2.14748365E9f;
    }

    public SuspendPartitionView(Context context) {
        this(context, null);
    }

    public SuspendPartitionView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    public void setStartVisibility(int i) {
        if (this.mStartVisibility != i) {
            this.mStartVisibility = i;
            requestLayout();
        }
    }

    public void setCenterVisibility(int i) {
        if (this.mCenterVisibility != i) {
            this.mStartVisibility = i;
            requestLayout();
        }
    }

    public void setEndVisibility(int i) {
        if (this.mEndVisibility != i) {
            this.mStartVisibility = i;
            requestLayout();
        }
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= GravityCompat.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    private int insert(View view) {
        if (view == null) {
            return -1;
        }
        this.mAllChildren.add(view);
        this.mPriorityChildren.add(view);
        Collections.sort(this.mAllChildren, sIndexComparator);
        Collections.sort(this.mPriorityChildren, sPriorityComparator);
        return this.mAllChildren.indexOf(view);
    }

    private void delete(View view) {
        if (view != null) {
            this.mAllChildren.remove(view);
            this.mPriorityChildren.remove(view);
        }
    }

    private LayoutParams setupLayoutParams(View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            layoutParams = view.getLayoutParams();
        }
        LayoutParams a = LayoutParams.a(layoutParams);
        view.setLayoutParams(a);
        return a;
    }

    private void setupPosition(LayoutParams layoutParams, float f) {
        if (layoutParams.b == 0.0f) {
            layoutParams.b = f;
        }
    }

    private void setupPriority(LayoutParams layoutParams) {
        if (layoutParams != null && layoutParams.d == 0.0f) {
            int i = layoutParams.a & 112;
            if (i == 16) {
                layoutParams.d = 1.0f;
            } else if (i != 80) {
                layoutParams.d = 2.0f;
            } else {
                layoutParams.d = 3.0f;
            }
        }
    }

    public void addView(View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (view != null) {
            LayoutParams layoutParams2 = setupLayoutParams(view, layoutParams);
            setupPosition(layoutParams2, i < 0 ? (float) this.mAllChildren.size() : ((float) i) + 1.0f);
            setupPriority(layoutParams2);
            super.addView(view, insert(view), layoutParams2);
        }
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return LayoutParams.a(layoutParams);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void removeAllViewsInLayout() {
        super.removeAllViewsInLayout();
        this.mAllChildren.clear();
    }

    public void removeViews(int i, int i2) {
        throw new RuntimeException("not support!");
    }

    public void removeView(View view) {
        delete(view);
        super.removeView(view);
    }

    public void removeViewInLayout(View view) {
        delete(view);
        super.removeViewInLayout(view);
    }

    public void removeViewAt(int i) {
        throw new RuntimeException("not support!");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    public int getHeightForPriority(float f) {
        int size = this.mPriorityChildren.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            View view = this.mPriorityChildren.get(i2);
            if (!(view == null || view.getVisibility() == 8)) {
                if (((LayoutParams) view.getLayoutParams()).d < f) {
                    break;
                }
                i += getHeight(view);
            }
        }
        return i;
    }

    private int getWidth(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return view.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
    }

    private int getHeight(View view) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    /* access modifiers changed from: 0000 */
    public void measureHorizontal(int i, int i2) {
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                measureChildWithMargins(childAt, i, paddingLeft, i2, paddingTop);
                int max = Math.max(i5, getHeight(childAt));
                if ((((LayoutParams) childAt.getLayoutParams()).a & 7) == 1) {
                    i3 = Math.max(i3, getWidth(childAt));
                } else {
                    i4 += getWidth(childAt);
                }
                i5 = max;
            }
        }
        this.mNeedWidth = Math.max(i3, i4);
        this.mNeedHeight = i5;
        setMeasuredDimension(resolveSize(this.mNeedWidth + paddingLeft, i), resolveSize(this.mNeedHeight + paddingTop, i2));
    }

    /* access modifiers changed from: 0000 */
    public void measureVertical(int i, int i2) {
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                measureChildWithMargins(childAt, i, paddingLeft, i2, paddingTop);
                i4 += getHeight(childAt);
                i3 = Math.max(i3, getWidth(childAt));
            }
        }
        this.mNeedWidth = i3;
        this.mNeedHeight = i4;
        setMeasuredDimension(resolveSize(this.mNeedWidth + paddingLeft, i), resolveSize(this.mNeedHeight + paddingTop, i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (getOrientation() == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
        this.mCenterViews.clear();
        this.mEndViews.clear();
    }

    private void layoutHorizontal(int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int i7 = this.mGravity & 112;
        int childCount = getChildCount();
        int i8 = paddingLeft;
        for (int i9 = 0; i9 < childCount; i9++) {
            View childAt = getChildAt(i9);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i10 = layoutParams.a & 7;
                if (i10 == 1) {
                    this.mCenterViews.add(childAt);
                } else if (i10 != 5) {
                    int i11 = i8 + layoutParams.leftMargin;
                    if (i7 == 16) {
                        int measuredHeight = ((((((i6 - paddingTop) - paddingBottom) - layoutParams.topMargin) - layoutParams.bottomMargin) - childAt.getMeasuredHeight()) / 2) + paddingTop + layoutParams.topMargin;
                        childAt.layout(i11, measuredHeight, childAt.getMeasuredWidth() + i11, childAt.getMeasuredHeight() + measuredHeight);
                    } else if (i7 != 80) {
                        int i12 = layoutParams.topMargin + paddingTop;
                        childAt.layout(i11, i12, childAt.getMeasuredWidth() + i11, childAt.getMeasuredHeight() + i12);
                    } else {
                        int i13 = (i6 - paddingBottom) - layoutParams.bottomMargin;
                        childAt.layout(i11, i13 - childAt.getMeasuredHeight(), childAt.getMeasuredWidth() + i11, i13);
                    }
                    i8 = i11 + childAt.getMeasuredWidth() + layoutParams.rightMargin;
                } else {
                    this.mEndViews.add(0, childAt);
                }
            }
        }
        if (this.mCenterViews.size() > 0) {
            for (int i14 = 0; i14 < this.mCenterViews.size(); i14++) {
                View view = this.mCenterViews.get(i14);
                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                int measuredWidth = ((((((i5 - paddingLeft) - paddingRight) - layoutParams2.leftMargin) - layoutParams2.rightMargin) - view.getMeasuredWidth()) / 2) + paddingLeft + layoutParams2.leftMargin;
                if (i7 == 16) {
                    int measuredHeight2 = ((((((i6 - paddingTop) - paddingBottom) - layoutParams2.topMargin) - layoutParams2.bottomMargin) - view.getMeasuredHeight()) / 2) + paddingTop + layoutParams2.topMargin;
                    view.layout(measuredWidth, measuredHeight2, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight() + measuredHeight2);
                } else if (i7 != 80) {
                    int i15 = layoutParams2.topMargin + paddingTop;
                    view.layout(measuredWidth, i15, view.getMeasuredWidth() + measuredWidth, view.getMeasuredHeight() + i15);
                } else {
                    int i16 = (i6 - paddingBottom) - layoutParams2.bottomMargin;
                    view.layout(measuredWidth, i16 - view.getMeasuredHeight(), view.getMeasuredWidth() + measuredWidth, i16);
                }
            }
        }
        int i17 = i5 - paddingRight;
        if (this.mEndViews.size() > 0) {
            for (int i18 = 0; i18 < this.mEndViews.size(); i18++) {
                View view2 = this.mEndViews.get(i18);
                LayoutParams layoutParams3 = (LayoutParams) view2.getLayoutParams();
                int i19 = i17 - layoutParams3.rightMargin;
                if (i7 == 16) {
                    int measuredHeight3 = ((((((i6 - paddingTop) - paddingBottom) - layoutParams3.topMargin) - layoutParams3.bottomMargin) - view2.getMeasuredHeight()) / 2) + paddingTop + layoutParams3.topMargin;
                    view2.layout(i19 - view2.getMeasuredWidth(), measuredHeight3, i19, view2.getMeasuredHeight() + measuredHeight3);
                } else if (i7 != 80) {
                    int i20 = layoutParams3.topMargin + paddingTop;
                    view2.layout(i19 - view2.getMeasuredWidth(), i20, i19, view2.getMeasuredHeight() + i20);
                } else {
                    int i21 = (i6 - paddingBottom) - layoutParams3.bottomMargin;
                    view2.layout(i19 - view2.getMeasuredWidth(), i21 - view2.getMeasuredHeight(), i19, i21);
                }
                i17 = (i19 - view2.getMeasuredWidth()) - layoutParams3.leftMargin;
            }
        }
    }

    private int getCenterHeight() {
        int i = 0;
        for (int i2 = 0; i2 < this.mCenterViews.size(); i2++) {
            View view = this.mCenterViews.get(i2);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (layoutParams != null && layoutParams.c) {
                i += getHeight(view);
            }
        }
        return i;
    }

    public void setMinPriority(float f) {
        this.mMinPriority = f;
    }

    private void crash(int i) {
        int size = this.mPriorityChildren.size();
        float f = this.mMinPriority;
        int i2 = i;
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            View view = this.mPriorityChildren.get(i3);
            if (!(view.getVisibility() == 8 || (this.mOrientation == 1 && getGroupVisibility(view) == 8))) {
                int height = getHeight(view);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (height > i2) {
                    f = Math.max(layoutParams.d, f);
                    break;
                }
                i2 -= height;
            }
            i3++;
        }
        for (int i4 = 0; i4 < size; i4++) {
            LayoutParams layoutParams2 = (LayoutParams) this.mPriorityChildren.get(i4).getLayoutParams();
            layoutParams2.c = layoutParams2.d > f;
        }
    }

    private int getGroupVisibility(View view) {
        int i = ((LayoutParams) view.getLayoutParams()).a & 112;
        if (i == 16) {
            return this.mCenterVisibility;
        }
        if (i != 80) {
            return this.mStartVisibility;
        }
        return this.mEndVisibility;
    }

    private void layoutVertical(int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int i5 = i3 - i;
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        crash(paddingBottom - paddingTop);
        int i6 = this.mGravity & 7;
        int childCount = getChildCount();
        int i7 = 0;
        int i8 = paddingTop;
        int i9 = 0;
        while (i9 < childCount) {
            View childAt = getChildAt(i9);
            if (!(childAt == null || childAt.getVisibility() == 8)) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.c) {
                    childAt.layout(i7, i7, i7, i7);
                } else {
                    int i10 = layoutParams.a & 112;
                    if (i10 == 16) {
                        this.mCenterViews.add(childAt);
                    } else if (i10 != 80) {
                        if (this.mStartVisibility != 8) {
                            i8 += layoutParams.topMargin;
                        }
                        if (this.mStartVisibility != 0) {
                            childAt.layout(0, 0, 0, 0);
                        } else if (i6 == 1) {
                            int measuredWidth = ((((((i5 - paddingLeft) - paddingRight) - layoutParams.leftMargin) - layoutParams.rightMargin) - childAt.getMeasuredWidth()) / 2) + paddingLeft + layoutParams.leftMargin;
                            childAt.layout(measuredWidth, i8, childAt.getMeasuredWidth() + measuredWidth, childAt.getMeasuredHeight() + i8);
                        } else if (i6 != 5) {
                            int i11 = layoutParams.leftMargin + paddingLeft;
                            childAt.layout(i11, i8, childAt.getMeasuredWidth() + i11, childAt.getMeasuredHeight() + i8);
                        } else {
                            int i12 = (i5 - paddingRight) - layoutParams.rightMargin;
                            childAt.layout(i12 - childAt.getMeasuredWidth(), i8, i12, childAt.getMeasuredHeight() + i8);
                        }
                        if (this.mStartVisibility != 8) {
                            i8 = i8 + childAt.getMeasuredHeight() + layoutParams.bottomMargin;
                        }
                    } else {
                        this.mEndViews.add(0, childAt);
                    }
                }
            }
            i9++;
            i7 = 0;
        }
        if (this.mEndViews.size() > 0) {
            for (int i13 = 0; i13 < this.mEndViews.size(); i13++) {
                View view = this.mEndViews.get(i13);
                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                if (this.mEndVisibility != 8) {
                    paddingBottom -= layoutParams2.bottomMargin;
                }
                if (this.mEndVisibility != 0) {
                    view.layout(0, 0, 0, 0);
                } else if (i6 == 1) {
                    int measuredWidth2 = ((((((i5 - paddingLeft) - paddingRight) - layoutParams2.leftMargin) - layoutParams2.rightMargin) - view.getMeasuredWidth()) / 2) + paddingLeft + layoutParams2.leftMargin;
                    view.layout(measuredWidth2, paddingBottom - view.getMeasuredHeight(), view.getMeasuredWidth() + measuredWidth2, paddingBottom);
                } else if (i6 != 5) {
                    int i14 = layoutParams2.leftMargin + paddingLeft;
                    view.layout(i14, paddingBottom - view.getMeasuredHeight(), view.getMeasuredWidth() + i14, paddingBottom);
                } else {
                    int i15 = (i5 - paddingRight) - layoutParams2.rightMargin;
                    view.layout(i15 - view.getMeasuredWidth(), paddingBottom - view.getMeasuredHeight(), i15, paddingBottom);
                }
                if (this.mEndVisibility != 8) {
                    paddingBottom = (paddingBottom - view.getMeasuredHeight()) - layoutParams2.topMargin;
                }
            }
        }
        if (this.mCenterViews.size() > 0) {
            int centerHeight = i8 + (((paddingBottom - i8) - getCenterHeight()) / 2);
            for (int i16 = 0; i16 < this.mCenterViews.size(); i16++) {
                View view2 = this.mCenterViews.get(i16);
                LayoutParams layoutParams3 = (LayoutParams) view2.getLayoutParams();
                if (this.mCenterVisibility != 8) {
                    centerHeight += layoutParams3.topMargin;
                }
                if (this.mCenterVisibility != 0) {
                    view2.layout(0, 0, 0, 0);
                } else if (i6 == 1) {
                    int measuredWidth3 = ((((((i5 - paddingLeft) - paddingRight) - layoutParams3.leftMargin) - layoutParams3.rightMargin) - view2.getMeasuredWidth()) / 2) + paddingLeft + layoutParams3.leftMargin;
                    view2.layout(measuredWidth3, centerHeight, view2.getMeasuredWidth() + measuredWidth3, view2.getMeasuredHeight() + centerHeight);
                } else if (i6 != 5) {
                    int i17 = layoutParams3.leftMargin + paddingLeft;
                    view2.layout(i17, centerHeight, view2.getMeasuredWidth() + i17, view2.getMeasuredHeight() + centerHeight);
                } else {
                    int i18 = (i5 - paddingRight) - layoutParams3.rightMargin;
                    view2.layout(i18 - view2.getMeasuredWidth(), centerHeight, i18, view2.getMeasuredHeight() + centerHeight);
                }
                if (this.mCenterVisibility != 8) {
                    centerHeight = centerHeight + view2.getMeasuredHeight() + layoutParams3.bottomMargin;
                }
            }
        }
    }
}

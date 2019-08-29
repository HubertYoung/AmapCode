package com.autonavi.map.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;

public class SuspendViewCommonTemplate extends SuspendViewBaseTemplate {
    public static final int POSITION_BOTTOM_MIDDLE = 7;
    public static final int POSITION_LEFT_BOTTOM = 3;
    public static final int POSITION_LEFT_MIDDLE = 2;
    public static final int POSITION_LEFT_TOP = 1;
    public static final int POSITION_RIGHT_BOTTOM = 6;
    public static final int POSITION_RIGHT_MIDDLE = 5;
    public static final int POSITION_RIGHT_TOP = 4;
    private ViewGroup mBottomMiddleGroup;
    private ViewGroup mLeftBottomGroup;
    private ViewGroup mLeftMiddleGroup;
    protected ArrayList<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> mLeftPartitionList;
    private ViewGroup mLeftTopGroup;
    private ViewGroup mRightBottomGroup;
    private ViewGroup mRightMiddleGroup;
    protected ArrayList<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> mRightPartitionList;
    private ViewGroup mRightTopGroup;
    protected SparseArray<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> mTotalPartitionArray;
    private a mViewAdjust;

    public interface a {
        boolean adjustWidget(int i, ViewGroup viewGroup, int i2);
    }

    public SuspendViewCommonTemplate(Context context) {
        this(context, null);
    }

    public SuspendViewCommonTemplate(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SuspendViewCommonTemplate(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTotalPartitionArray = new SparseArray<>();
        this.mLeftPartitionList = new ArrayList<>();
        this.mRightPartitionList = new ArrayList<>();
        initPartitions(context);
        initDefaultPriority();
    }

    /* access modifiers changed from: protected */
    public void initPartitions(Context context) {
        this.mLeftTopGroup = new LinearLayout(context);
        this.mLeftMiddleGroup = new LinearLayout(context);
        this.mLeftBottomGroup = new LinearLayout(context);
        this.mRightTopGroup = new LinearLayout(context);
        this.mRightMiddleGroup = new LinearLayout(context);
        this.mRightBottomGroup = new LinearLayout(context);
        this.mBottomMiddleGroup = new LinearLayout(context);
        setOrientation(1, (LinearLayout) this.mLeftTopGroup, (LinearLayout) this.mLeftMiddleGroup, (LinearLayout) this.mLeftBottomGroup, (LinearLayout) this.mRightTopGroup, (LinearLayout) this.mRightMiddleGroup, (LinearLayout) this.mRightBottomGroup);
        setOrientation(0, (LinearLayout) this.mBottomMiddleGroup);
        ((LinearLayout) this.mRightTopGroup).setGravity(5);
        this.mRightTopGroup.setClipToPadding(false);
        this.mRightTopGroup.setClipChildren(false);
        ((LinearLayout) this.mRightMiddleGroup).setGravity(5);
        ((LinearLayout) this.mRightBottomGroup).setGravity(5);
        addViewInLayout(this.mLeftTopGroup, this.mLeftMiddleGroup, this.mLeftBottomGroup, this.mRightTopGroup, this.mRightMiddleGroup, this.mRightBottomGroup, this.mBottomMiddleGroup);
    }

    private void addViewInLayout(View... viewArr) {
        int length = viewArr.length;
        for (int i = 0; i < length; i++) {
            addViewInLayout(viewArr[i], i, generateDefaultLayoutParams(), true);
        }
    }

    private void setOrientation(int i, LinearLayout... linearLayoutArr) {
        for (LinearLayout orientation : linearLayoutArr) {
            orientation.setOrientation(i);
        }
    }

    /* access modifiers changed from: protected */
    public void initDefaultPriority() {
        setPartitionPriority(2, 1);
        setPartitionPriority(5, 1);
        setPartitionPriority(1, 3);
        setPartitionPriority(4, 3);
        setPartitionPriority(3, 4);
        setPartitionPriority(7, 4);
        setPartitionPriority(6, 4);
    }

    public void setPartitionPriority(int i, int i2) {
        com.autonavi.map.suspend.SuspendViewBaseTemplate.a aVar = new com.autonavi.map.suspend.SuspendViewBaseTemplate.a(i, i2);
        this.mTotalPartitionArray.put(i, aVar);
        switch (aVar.b) {
            case 1:
            case 2:
            case 3:
                insertPriorityList(this.mLeftPartitionList, aVar);
                return;
            case 4:
            case 5:
            case 6:
                insertPriorityList(this.mRightPartitionList, aVar);
                break;
        }
    }

    /* access modifiers changed from: protected */
    public void insertPriorityList(ArrayList<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> arrayList, com.autonavi.map.suspend.SuspendViewBaseTemplate.a aVar) {
        Iterator<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.autonavi.map.suspend.SuspendViewBaseTemplate.a next = it.next();
            if (next.b == aVar.b) {
                arrayList.remove(next);
                break;
            }
        }
        if (arrayList.size() == 0) {
            arrayList.add(aVar);
            return;
        }
        int i = 0;
        while (i < arrayList.size() && aVar.c < arrayList.get(i).c) {
            i++;
        }
        arrayList.add(i, aVar);
    }

    public ViewGroup getViewGroupByPosition(int i) {
        switch (i) {
            case 1:
                return this.mLeftTopGroup;
            case 2:
                return this.mLeftMiddleGroup;
            case 3:
                return this.mLeftBottomGroup;
            case 4:
                return this.mRightTopGroup;
            case 5:
                return this.mRightMiddleGroup;
            case 6:
                return this.mRightBottomGroup;
            case 7:
                return this.mBottomMiddleGroup;
            default:
                return this;
        }
    }

    /* access modifiers changed from: protected */
    public com.autonavi.map.suspend.SuspendViewBaseTemplate.a getPartitionByPosition(int i) {
        return this.mTotalPartitionArray.get(i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        decideEnabledByPriority();
        int paddingLeft = getPaddingLeft();
        int paddingRight = (i3 - i) - getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition = getPartitionByPosition(1);
        if (partitionByPosition != null && partitionByPosition.a != null) {
            int measuredWidth = partitionByPosition.a.getMeasuredWidth();
            int measuredHeight = partitionByPosition.a.getMeasuredHeight();
            if (partitionByPosition == null || !partitionByPosition.d || partitionByPosition.a.getVisibility() == 8) {
                partitionByPosition.a.layout(0, 0, 0, 0);
            } else {
                partitionByPosition.a.layout(paddingLeft, paddingTop, measuredWidth + paddingLeft, measuredHeight + paddingTop);
            }
            com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition2 = getPartitionByPosition(3);
            if (partitionByPosition2 != null && partitionByPosition2.a != null) {
                int measuredWidth2 = partitionByPosition2.a.getMeasuredWidth();
                int measuredHeight2 = partitionByPosition2.a.getMeasuredHeight();
                if (partitionByPosition2 == null || !partitionByPosition2.d || partitionByPosition2.a.getVisibility() == 8) {
                    partitionByPosition2.a.layout(0, 0, 0, 0);
                    i6 = paddingLeft;
                    i5 = paddingBottom;
                } else {
                    i5 = paddingBottom - measuredHeight2;
                    i6 = measuredWidth2 + paddingLeft;
                    partitionByPosition2.a.layout(paddingLeft, i5, i6, paddingBottom);
                }
                com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition3 = getPartitionByPosition(2);
                if (partitionByPosition3 != null && partitionByPosition3.a != null) {
                    int measuredWidth3 = partitionByPosition3.a.getMeasuredWidth();
                    int measuredHeight3 = partitionByPosition3.a.getMeasuredHeight();
                    if (partitionByPosition3 == null || !partitionByPosition3.d || partitionByPosition3.a.getVisibility() == 8) {
                        partitionByPosition3.a.layout(0, 0, 0, 0);
                    } else {
                        partitionByPosition3.a.layout(paddingLeft, i5 - measuredHeight3, measuredWidth3 + paddingLeft, i5);
                    }
                    com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition4 = getPartitionByPosition(7);
                    if (partitionByPosition4 != null && partitionByPosition4.a != null) {
                        int measuredWidth4 = partitionByPosition4.a.getMeasuredWidth();
                        int measuredHeight4 = partitionByPosition4.a.getMeasuredHeight();
                        if (partitionByPosition4 == null || !partitionByPosition4.d || partitionByPosition4.a.getVisibility() == 8) {
                            partitionByPosition4.a.layout(0, 0, 0, 0);
                        } else {
                            partitionByPosition4.a.layout(i6, paddingBottom - measuredHeight4, measuredWidth4 + i6, paddingBottom);
                        }
                        com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition5 = getPartitionByPosition(4);
                        if (partitionByPosition5 != null && partitionByPosition5.a != null) {
                            int measuredWidth5 = partitionByPosition5.a.getMeasuredWidth();
                            int measuredHeight5 = partitionByPosition5.a.getMeasuredHeight();
                            if (partitionByPosition5 == null || !partitionByPosition5.d || partitionByPosition5.a.getVisibility() == 8) {
                                partitionByPosition5.a.layout(0, 0, 0, 0);
                            } else {
                                int i7 = measuredHeight5 + paddingTop;
                                partitionByPosition5.a.layout(paddingRight - measuredWidth5, paddingTop, paddingRight, i7);
                                paddingTop = i7;
                            }
                            com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition6 = getPartitionByPosition(6);
                            if (partitionByPosition6 != null && partitionByPosition6.a != null) {
                                int measuredWidth6 = partitionByPosition6.a.getMeasuredWidth();
                                int measuredHeight6 = partitionByPosition6.a.getMeasuredHeight();
                                if (partitionByPosition6 == null || !partitionByPosition6.d || partitionByPosition6.a.getVisibility() == 8) {
                                    partitionByPosition6.a.layout(0, 0, 0, 0);
                                } else {
                                    int i8 = paddingBottom - measuredHeight6;
                                    partitionByPosition6.a.layout(paddingRight - measuredWidth6, i8, paddingRight, paddingBottom);
                                    paddingBottom = i8;
                                }
                                com.autonavi.map.suspend.SuspendViewBaseTemplate.a partitionByPosition7 = getPartitionByPosition(5);
                                if (partitionByPosition7 != null && partitionByPosition7.a != null) {
                                    int measuredWidth7 = partitionByPosition7.a.getMeasuredWidth();
                                    int measuredHeight7 = partitionByPosition7.a.getMeasuredHeight();
                                    if (partitionByPosition7 == null || !partitionByPosition7.d || partitionByPosition7.a.getVisibility() == 8) {
                                        partitionByPosition7.a.layout(0, 0, 0, 0);
                                        return;
                                    }
                                    int i9 = paddingTop + (((paddingBottom - paddingTop) - measuredHeight7) >> 1);
                                    partitionByPosition7.a.layout(paddingRight - measuredWidth7, i9, paddingRight, measuredHeight7 + i9);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void decideEnabledByPriority() {
        decideListEnabled(this.mLeftPartitionList);
        decideListEnabled(this.mRightPartitionList);
        getPartitionByPosition(7).d = getPartitionByPosition(3).d;
    }

    private void decideListEnabled(ArrayList<com.autonavi.map.suspend.SuspendViewBaseTemplate.a> arrayList) {
        int i = 0;
        int i2 = 0;
        while (i < arrayList.size()) {
            com.autonavi.map.suspend.SuspendViewBaseTemplate.a aVar = arrayList.get(i);
            if (!(aVar.a.getVisibility() == 8 || aVar.a.getMeasuredHeight() == 0)) {
                i2 += aVar.a.getMeasuredHeight();
                if (i2 <= getMeasuredHeight()) {
                    aVar.d = true;
                    if (this.mViewAdjust != null) {
                        int measuredHeight = i2 - getMeasuredHeight();
                        int measuredHeight2 = aVar.a.getMeasuredHeight();
                        i2 -= aVar.a.getMeasuredHeight();
                        if (this.mViewAdjust.adjustWidget(aVar.b, aVar.a, measuredHeight)) {
                            aVar.a.measure(this.mMostWidthMeasureSpec, this.mMostHeightMeasureSpec);
                            if (measuredHeight2 < aVar.a.getMeasuredHeight()) {
                                i--;
                            } else {
                                i2 += aVar.a.getMeasuredHeight();
                            }
                        } else {
                            i2 += aVar.a.getMeasuredHeight();
                        }
                    }
                } else {
                    aVar.d = false;
                    if (this.mViewAdjust != null) {
                        int measuredHeight3 = i2 - getMeasuredHeight();
                        i2 -= aVar.a.getMeasuredHeight();
                        if (this.mViewAdjust.adjustWidget(aVar.b, aVar.a, measuredHeight3)) {
                            aVar.a.measure(this.mMostWidthMeasureSpec, this.mMostHeightMeasureSpec);
                            i2 += aVar.a.getMeasuredHeight();
                            if (i2 <= getMeasuredHeight()) {
                                aVar.d = true;
                            } else {
                                i2 -= aVar.a.getMeasuredHeight();
                            }
                        }
                    } else {
                        i2 -= aVar.a.getMeasuredHeight();
                    }
                }
            }
            i++;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (((double) getAlpha()) <= 0.1d) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setViewAdjustCallback(a aVar) {
        this.mViewAdjust = aVar;
    }
}

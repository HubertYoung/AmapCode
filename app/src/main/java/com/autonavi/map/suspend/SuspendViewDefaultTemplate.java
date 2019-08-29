package com.autonavi.map.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.map.suspend.SuspendViewBaseTemplate.a;
import java.util.ArrayList;
import java.util.Iterator;

public class SuspendViewDefaultTemplate extends SuspendViewBaseTemplate {
    public static final int POSITION_BOTTOM_MIDDLE = 7;
    public static final int POSITION_LEFT_BOTTOM = 3;
    public static final int POSITION_LEFT_MIDDLE = 2;
    public static final int POSITION_LEFT_TOP = 1;
    public static final int POSITION_RIGHT_BOTTOM = 6;
    public static final int POSITION_RIGHT_MIDDLE = 5;
    public static final int POSITION_RIGHT_TOP = 4;
    public static final int POSITION_TOP = 0;
    private ViewGroup mBottomMiddleGroup;
    private ViewGroup mLeftBottomGroup;
    private ViewGroup mLeftMiddleGroup;
    protected ArrayList<a> mLeftPartitionList;
    private ViewGroup mLeftTopGroup;
    private ViewGroup mRightBottomGroup;
    private ViewGroup mRightMiddleGroup;
    protected ArrayList<a> mRightPartitionList;
    private ViewGroup mRightTopGroup;
    private ViewGroup mTopGroup;
    protected SparseArray<a> mTotalPartitionArray;

    public SuspendViewDefaultTemplate(Context context) {
        this(context, null);
    }

    public SuspendViewDefaultTemplate(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SuspendViewDefaultTemplate(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTotalPartitionArray = new SparseArray<>();
        this.mLeftPartitionList = new ArrayList<>();
        this.mRightPartitionList = new ArrayList<>();
        initPartitions(context);
        initDefaultPriority();
    }

    /* access modifiers changed from: protected */
    public void initPartitions(Context context) {
        this.mTopGroup = new RelativeLayout(context);
        this.mLeftTopGroup = new LinearLayout(context);
        this.mLeftMiddleGroup = new LinearLayout(context);
        this.mLeftBottomGroup = new LinearLayout(context);
        this.mRightTopGroup = new LinearLayout(context);
        this.mRightMiddleGroup = new LinearLayout(context);
        this.mRightBottomGroup = new LinearLayout(context);
        this.mBottomMiddleGroup = new RelativeLayout(context);
        setOrientation(1, (LinearLayout) this.mLeftTopGroup, (LinearLayout) this.mLeftMiddleGroup, (LinearLayout) this.mLeftBottomGroup, (LinearLayout) this.mRightTopGroup, (LinearLayout) this.mRightMiddleGroup, (LinearLayout) this.mRightBottomGroup);
        ((LinearLayout) this.mRightTopGroup).setGravity(5);
        this.mRightTopGroup.setClipToPadding(false);
        this.mRightTopGroup.setClipChildren(false);
        ((LinearLayout) this.mRightMiddleGroup).setGravity(5);
        ((LinearLayout) this.mRightBottomGroup).setGravity(5);
        addViewInLayout(this.mTopGroup, this.mLeftTopGroup, this.mLeftMiddleGroup, this.mLeftBottomGroup, this.mRightTopGroup, this.mRightMiddleGroup, this.mRightBottomGroup, this.mBottomMiddleGroup);
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
        setPartitionPriority(5, 1);
        setPartitionPriority(0, 2);
        setPartitionPriority(1, 3);
        setPartitionPriority(4, 3);
        setPartitionPriority(2, 4);
        setPartitionPriority(3, 5);
        setPartitionPriority(7, 5);
        setPartitionPriority(6, 5);
    }

    public void setPartitionPriority(int i, int i2) {
        a aVar = new a(i, i2);
        this.mTotalPartitionArray.put(i, aVar);
        switch (aVar.b) {
            case 0:
                insertPriorityList(this.mLeftPartitionList, aVar);
                break;
            case 1:
            case 2:
            case 3:
                insertPriorityList(this.mLeftPartitionList, aVar);
                return;
            case 4:
            case 5:
            case 6:
                break;
        }
        insertPriorityList(this.mRightPartitionList, aVar);
    }

    /* access modifiers changed from: protected */
    public void insertPriorityList(ArrayList<a> arrayList, a aVar) {
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
            case 0:
                return this.mTopGroup;
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
    public a getPartitionByPosition(int i) {
        return this.mTotalPartitionArray.get(i);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        decideEnabledByPriority();
        int paddingLeft = getPaddingLeft();
        int paddingRight = (i3 - i) - getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        a partitionByPosition = getPartitionByPosition(0);
        if (partitionByPosition != null && partitionByPosition.a != null) {
            int measuredWidth = partitionByPosition.a.getMeasuredWidth();
            int measuredHeight = partitionByPosition.a.getMeasuredHeight();
            if (partitionByPosition == null || !partitionByPosition.d || partitionByPosition.a.getVisibility() == 8) {
                partitionByPosition.a.layout(0, 0, 0, 0);
                i5 = paddingTop;
            } else {
                int i8 = (((paddingRight - paddingLeft) - measuredWidth) >> 1) + paddingLeft;
                i5 = measuredHeight + paddingTop;
                partitionByPosition.a.layout(i8, paddingTop, measuredWidth + i8, i5);
            }
            a partitionByPosition2 = getPartitionByPosition(1);
            if (partitionByPosition2 != null && partitionByPosition2.a != null) {
                int measuredWidth2 = partitionByPosition2.a.getMeasuredWidth();
                int measuredHeight2 = partitionByPosition2.a.getMeasuredHeight();
                if (partitionByPosition2 == null || !partitionByPosition2.d || partitionByPosition2.a.getVisibility() == 8) {
                    partitionByPosition2.a.layout(0, 0, 0, 0);
                } else {
                    partitionByPosition2.a.layout(paddingLeft, paddingTop, measuredWidth2 + paddingLeft, measuredHeight2 + paddingTop);
                }
                a partitionByPosition3 = getPartitionByPosition(3);
                if (partitionByPosition3 != null && partitionByPosition3.a != null) {
                    int measuredWidth3 = partitionByPosition3.a.getMeasuredWidth();
                    int measuredHeight3 = partitionByPosition3.a.getMeasuredHeight();
                    if (partitionByPosition3 == null || !partitionByPosition3.d || partitionByPosition3.a.getVisibility() == 8) {
                        partitionByPosition3.a.layout(0, 0, 0, 0);
                        i6 = paddingBottom;
                    } else {
                        i6 = paddingBottom - measuredHeight3;
                        partitionByPosition3.a.layout(paddingLeft, i6, measuredWidth3 + paddingLeft, paddingBottom);
                    }
                    a partitionByPosition4 = getPartitionByPosition(2);
                    if (partitionByPosition4 != null && partitionByPosition4.a != null) {
                        int measuredWidth4 = partitionByPosition4.a.getMeasuredWidth();
                        int measuredHeight4 = partitionByPosition4.a.getMeasuredHeight();
                        if (partitionByPosition4 == null || !partitionByPosition4.d || partitionByPosition4.a.getVisibility() == 8) {
                            partitionByPosition4.a.layout(0, 0, 0, 0);
                        } else {
                            partitionByPosition4.a.layout(paddingLeft, i6 - measuredHeight4, measuredWidth4 + paddingLeft, i6);
                        }
                        a partitionByPosition5 = getPartitionByPosition(7);
                        if (partitionByPosition5 != null && partitionByPosition5.a != null) {
                            partitionByPosition5.a.getMeasuredWidth();
                            int measuredHeight5 = partitionByPosition5.a.getMeasuredHeight();
                            if (partitionByPosition5 == null || !partitionByPosition5.d || partitionByPosition5.a.getVisibility() == 8) {
                                partitionByPosition5.a.layout(0, 0, 0, 0);
                            } else {
                                partitionByPosition5.a.layout(0, paddingBottom - measuredHeight5, paddingRight, paddingBottom);
                            }
                            a partitionByPosition6 = getPartitionByPosition(4);
                            if (partitionByPosition6 != null && partitionByPosition6.a != null) {
                                int measuredWidth5 = partitionByPosition6.a.getMeasuredWidth();
                                int measuredHeight6 = partitionByPosition6.a.getMeasuredHeight();
                                if (partitionByPosition6 == null || !partitionByPosition6.d || partitionByPosition6.a.getVisibility() == 8) {
                                    partitionByPosition6.a.layout(0, 0, 0, 0);
                                    i7 = i5;
                                } else {
                                    i7 = measuredHeight6 + i5;
                                    partitionByPosition6.a.layout(paddingRight - measuredWidth5, i5, paddingRight, i7);
                                }
                                a partitionByPosition7 = getPartitionByPosition(6);
                                if (partitionByPosition7 != null && partitionByPosition7.a != null) {
                                    int measuredWidth6 = partitionByPosition7.a.getMeasuredWidth();
                                    int measuredHeight7 = partitionByPosition7.a.getMeasuredHeight();
                                    if (partitionByPosition7 == null || !partitionByPosition7.d || partitionByPosition7.a.getVisibility() == 8) {
                                        partitionByPosition7.a.layout(0, 0, 0, 0);
                                    } else {
                                        int i9 = paddingBottom - measuredHeight7;
                                        partitionByPosition7.a.layout(paddingRight - measuredWidth6, i9, paddingRight, paddingBottom);
                                        paddingBottom = i9;
                                    }
                                    a partitionByPosition8 = getPartitionByPosition(5);
                                    if (partitionByPosition8 != null && partitionByPosition8.a != null) {
                                        int measuredWidth7 = partitionByPosition8.a.getMeasuredWidth();
                                        int measuredHeight8 = partitionByPosition8.a.getMeasuredHeight();
                                        if (partitionByPosition8 == null || !partitionByPosition8.d || partitionByPosition8.a.getVisibility() == 8) {
                                            partitionByPosition8.a.layout(0, 0, 0, 0);
                                            return;
                                        }
                                        int i10 = i7 + (((paddingBottom - i7) - measuredHeight8) >> 1);
                                        partitionByPosition8.a.layout(paddingRight - measuredWidth7, i10, paddingRight, measuredHeight8 + i10);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void decideEnabledByPriority() {
        boolean decideListEnabled = decideListEnabled(this.mLeftPartitionList, true);
        if (decideListEnabled ^ decideListEnabled(this.mRightPartitionList, decideListEnabled)) {
            decideListEnabled(this.mLeftPartitionList, false);
        }
        getPartitionByPosition(7).d = getPartitionByPosition(3).d;
    }

    private boolean decideListEnabled(ArrayList<a> arrayList, boolean z) {
        Iterator<a> it = arrayList.iterator();
        boolean z2 = z;
        int i = 0;
        while (it.hasNext()) {
            a next = it.next();
            if ((next.b != 0 || z) && next.a.getVisibility() != 8) {
                i += next.a.getMeasuredHeight();
                if (i <= getMeasuredHeight()) {
                    next.d = true;
                } else {
                    next.d = false;
                }
                if (next.b == 0) {
                    z2 = next.d;
                }
            }
        }
        return z2;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (((double) getAlpha()) <= 0.1d) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}

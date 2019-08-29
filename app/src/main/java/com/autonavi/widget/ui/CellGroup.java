package com.autonavi.widget.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;

public class CellGroup extends LinearLayout {
    public CellGroup(Context context) {
        this(context, null);
    }

    public CellGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CellGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(1);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                CellGroup.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                CellGroup.this.setChildDivide();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: private */
    public void setChildDivide() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof CellView) {
                CellView cellView = (CellView) getChildAt(i);
                if (i != 0) {
                    cellView.setTopDivideVisibility(4);
                }
                if (i != childCount - 1) {
                    cellView.setBottomDivideColor(getResources().getColor(R.color.c_3));
                }
            }
        }
    }
}

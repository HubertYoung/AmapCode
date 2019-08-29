package com.autonavi.map.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OpenLayerView extends LinearLayout implements cen {
    private List<View> mCurrentViews;
    private List<View> mExtraViews;
    private Stack<View> mRecycledViews;

    public OpenLayerView(Context context) {
        this(context, null);
    }

    public OpenLayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OpenLayerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mExtraViews = new ArrayList();
        this.mCurrentViews = new ArrayList();
        this.mRecycledViews = new Stack<>();
        init(context);
    }

    private void init(Context context) {
        setPadding(agn.a(context, 12.0f), 0, 0, 0);
        setGravity(0);
    }

    private View obtainView() {
        if (this.mRecycledViews.size() > 0) {
            return this.mRecycledViews.pop();
        }
        return new OpenLayerItemView(getContext());
    }

    private void clearExtraView() {
        while (this.mExtraViews.size() > 0) {
            View remove = this.mExtraViews.remove(0);
            if (remove.getParent() != null) {
                ((ViewGroup) remove.getParent()).removeView(remove);
            }
        }
    }

    public void setExtraViews(List<View> list) {
        clearExtraView();
        if (list != null) {
            this.mExtraViews.addAll(list);
            for (int size = this.mExtraViews.size() - 1; size >= 0; size--) {
                addView(this.mExtraViews.get(size), 0);
            }
        }
    }

    public List<View> switchToViewCount(int i) {
        if (i < 0) {
            throw new RuntimeException("error count".concat(String.valueOf(i)));
        }
        while (this.mCurrentViews.size() < i) {
            View obtainView = obtainView();
            addView(obtainView);
            this.mCurrentViews.add(obtainView);
        }
        while (this.mCurrentViews.size() > i) {
            View remove = this.mCurrentViews.remove(this.mCurrentViews.size() - 1);
            if (remove != null) {
                removeView(remove);
                this.mRecycledViews.push(remove);
            }
        }
        return this.mCurrentViews;
    }
}

package com.autonavi.map.fragmentcontainer.page.mappage.TipsView;

import android.view.View;
import android.view.ViewGroup;

@Deprecated
public class BottomTipsContainer {
    private View defaultChild;
    private View foregoroundChild;
    private ViewGroup rootViewGroup;

    public BottomTipsContainer(ViewGroup viewGroup) {
        if (viewGroup == null || viewGroup.getChildCount() != 1) {
            throw new NullPointerException("rootView can have one child only!");
        }
        this.rootViewGroup = viewGroup;
        View childAt = this.rootViewGroup.getChildAt(0);
        this.defaultChild = childAt;
        this.foregoroundChild = childAt;
    }

    public View getContainer() {
        return this.rootViewGroup;
    }

    public void setView(View view) {
        if (this.defaultChild == null) {
            throw new RuntimeException("defaultChild must be set before addView called!");
        }
        updateForeGroundView(view);
    }

    public boolean onBackKeyPressed() {
        if (this.defaultChild == null || this.defaultChild == this.foregoroundChild) {
            return false;
        }
        setView(this.defaultChild);
        return true;
    }

    private void updateForeGroundView(View view) {
        this.rootViewGroup.removeAllViews();
        this.rootViewGroup.addView(view);
        this.foregoroundChild = view;
    }
}

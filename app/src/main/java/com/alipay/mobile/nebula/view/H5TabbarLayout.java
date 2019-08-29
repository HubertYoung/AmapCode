package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.List;

public class H5TabbarLayout extends LinearLayout {
    private int selectedIndex;
    /* access modifiers changed from: private */
    public H5TabListener tabListener;
    private List<View> tabs = new ArrayList();

    public interface H5TabListener {
        void onTabItemClicked(int i, View view);
    }

    public H5TabbarLayout(Context context) {
        super(context);
        initConfig();
    }

    public H5TabbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig();
    }

    public H5TabbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    private void initConfig() {
        setOrientation(0);
    }

    public void addTab(final View view) {
        if (this.tabs != null) {
            final int position = this.tabs.size();
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (H5TabbarLayout.this.tabListener != null) {
                        H5TabbarLayout.this.tabListener.onTabItemClicked(position, view);
                    }
                }
            });
            LayoutParams layoutParams = new LayoutParams(0, -1, 1.0f);
            layoutParams.gravity = 17;
            view.setLayoutParams(layoutParams);
            view.setClickable(true);
            view.setFocusable(true);
            this.tabs.add(view);
            addView(view);
        }
    }

    public void setTabListener(H5TabListener listener) {
        this.tabListener = listener;
    }

    private void unselectAll() {
        if (this.tabs != null) {
            for (View selected : this.tabs) {
                selected.setSelected(false);
            }
        }
    }

    public void selectTab(int i) {
        if (this.tabs != null) {
            if (i >= this.tabs.size()) {
                i = 0;
            }
            unselectAll();
            this.tabs.get(i).setSelected(true);
            this.selectedIndex = i;
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public int getTabSize() {
        if (this.tabs != null) {
            return this.tabs.size();
        }
        return 0;
    }
}

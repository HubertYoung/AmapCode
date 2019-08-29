package com.autonavi.minimap.route.common.view;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import java.util.HashMap;

public class ListViewOnScrollListener implements OnScrollListener {
    private boolean isFooterShow = true;
    private View mFooter;
    private boolean mHandleScroll = false;
    private int mLastTopVisibleItem = 0;
    private HashMap<Integer, Integer> mListViewItemHeights = new HashMap<>();
    private int mMinFooterTranslation;
    private int mStartScrollY = 0;

    public ListViewOnScrollListener(View view, int i) {
        this.mFooter = view;
        this.mMinFooterTranslation = i;
        if (this.mFooter == null) {
            throw new IllegalArgumentException("footerView should not be null!");
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == 0) {
            StringBuilder sb = new StringBuilder("IDLE. y = ");
            sb.append(this.mStartScrollY);
            eao.a((String) "ListViewOnScrollListener", sb.toString());
            handleScrollIdleState(absListView);
            this.mHandleScroll = false;
        } else {
            this.mHandleScroll = true;
        }
        this.mLastTopVisibleItem = absListView.getFirstVisiblePosition();
        this.mStartScrollY = getListViewScrollY(absListView);
        StringBuilder sb2 = new StringBuilder("onScrollStateChanged y = ");
        sb2.append(this.mStartScrollY);
        eao.a((String) "ListViewOnScrollListener", sb2.toString());
    }

    public void resetStatus() {
        this.mLastTopVisibleItem = 0;
        this.mStartScrollY = 0;
        this.mHandleScroll = false;
        this.isFooterShow = true;
        this.mListViewItemHeights.clear();
    }

    private void handleScrollIdleState(AbsListView absListView) {
        if (absListView.getLastVisiblePosition() == absListView.getCount() - 1) {
            View childAt = absListView.getChildAt(absListView.getChildCount() - 1);
            if (childAt != null && absListView.getHeight() >= childAt.getBottom()) {
                showFooter();
            }
        }
    }

    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        if (this.mHandleScroll) {
            int listViewScrollY = this.mStartScrollY - getListViewScrollY(absListView);
            if (listViewScrollY > 0) {
                showFooter();
            } else if (listViewScrollY >= 0) {
                if (this.mStartScrollY == 0) {
                    showFooter();
                }
            } else if (i < this.mLastTopVisibleItem) {
                showFooter();
            } else {
                hideFooter();
            }
        }
    }

    private void showFooter() {
        if (!this.isFooterShow) {
            doAnimation(0);
            this.isFooterShow = true;
        }
    }

    private void hideFooter() {
        if (this.isFooterShow) {
            doAnimation(this.mMinFooterTranslation);
            this.isFooterShow = false;
        }
    }

    private void doAnimation(int i) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mFooter, "translationY", new float[]{this.mFooter.getTranslationY(), (float) i});
        ofFloat.setDuration(400);
        ofFloat.start();
    }

    private int getListViewScrollY(AbsListView absListView) {
        if (absListView == null || absListView.getChildCount() <= 0) {
            return 0;
        }
        View childAt = absListView.getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        int i = -childAt.getTop();
        this.mListViewItemHeights.put(Integer.valueOf(absListView.getFirstVisiblePosition()), Integer.valueOf(childAt.getHeight()));
        if (i < 0) {
            i = 0;
        }
        for (int i2 = 0; i2 < firstVisiblePosition; i2++) {
            Integer num = this.mListViewItemHeights.get(Integer.valueOf(i2));
            if (num != null) {
                i += num.intValue();
            }
        }
        return i;
    }
}

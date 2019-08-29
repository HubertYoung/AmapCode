package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.alipay.mobile.antui.R;

public class AUCornerListView extends AUListView {
    private boolean hasFoot = false;
    private boolean hasHead = false;
    private int mMultiLineDefaultBGResid = R.drawable.pop_list_corner_shape;
    private int mMultiLineFirstBGResid = R.drawable.pop_list_corner_round_top;
    private int mMultiLineLastBGResid = R.drawable.pop_list_corner_round_bottom;
    private int mSingleLineBGResid = R.drawable.pop_list_corner_round;

    public AUCornerListView(Context context) {
        super(context);
    }

    public AUCornerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AUCornerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                chooseBackground(pointToPosition((int) ev.getX(), (int) ev.getY()));
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void chooseBackground(int itemnum) {
        if (itemnum == -1) {
            return;
        }
        if (itemnum != getFirstVisiblePosition() || this.hasHead) {
            if (itemnum != getLastVisiblePosition() || this.hasFoot) {
                setSelector(this.mMultiLineDefaultBGResid);
            } else {
                setSelector(this.mMultiLineLastBGResid);
            }
        } else if (itemnum == getAdapter().getCount() - 1) {
            setSelector(this.mSingleLineBGResid);
        } else {
            setSelector(this.mMultiLineFirstBGResid);
        }
    }

    public void setSingleLineBGResid(int mSingleLineBGResid2) {
        this.mSingleLineBGResid = mSingleLineBGResid2;
    }

    public void setMultiLineFirstBGResid(int mMultiLineFirstBGResid2) {
        this.mMultiLineFirstBGResid = mMultiLineFirstBGResid2;
    }

    public void setMultiLineLastBGResid(int mMultiLineLastBGResid2) {
        this.mMultiLineLastBGResid = mMultiLineLastBGResid2;
    }

    public void setMultiLineDefaultBGResid(int mMultiLineDefaultBGResid2) {
        this.mMultiLineDefaultBGResid = mMultiLineDefaultBGResid2;
    }

    public void updateHeadFootState(boolean hasFoot2, boolean hasHead2) {
        this.hasFoot = hasFoot2;
        this.hasHead = hasHead2;
    }

    public void updateHeadState(boolean hasHead2) {
        this.hasHead = hasHead2;
    }

    public void updateFootState(boolean hasFoot2) {
        this.hasFoot = hasFoot2;
    }
}

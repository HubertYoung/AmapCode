package com.amap.bundle.drivecommon.view;

import android.widget.BaseAdapter;

public abstract class ListViewWithHeaderAdapter extends BaseAdapter {
    private a mOnNotifyViewChangeListener;

    public interface a {
        void onViewChange();
    }

    public void setOnNotifyViewChangeListener(a aVar) {
        this.mOnNotifyViewChangeListener = aVar;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (this.mOnNotifyViewChangeListener != null) {
            this.mOnNotifyViewChangeListener.onViewChange();
        }
    }
}

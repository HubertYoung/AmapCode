package com.autonavi.minimap.map.mapinterface;

import android.content.Context;
import android.widget.FrameLayout;
import com.autonavi.common.model.POI;

public class AbstractGpsTipView extends FrameLayout {
    public OnRequestCallbackListener mOnRequestCallbackListener;

    public interface OnRequestCallbackListener {
        long getFinishSequence();

        long getShowSequence();

        void onRequestCallback(POI poi);
    }

    public void adjustMargin() {
    }

    public void refreshByScreenState(boolean z) {
    }

    public void reset() {
    }

    public void setFromPageID(int i) {
    }

    public AbstractGpsTipView(Context context) {
        super(context);
    }

    public void setOnRequestCallbackListener(OnRequestCallbackListener onRequestCallbackListener) {
        this.mOnRequestCallbackListener = onRequestCallbackListener;
    }
}

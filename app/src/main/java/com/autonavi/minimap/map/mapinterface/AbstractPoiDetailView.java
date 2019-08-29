package com.autonavi.minimap.map.mapinterface;

import android.content.Context;
import android.widget.FrameLayout;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

public abstract class AbstractPoiDetailView extends FrameLayout {
    public static final int TIPSHEIGHTINLAND = agn.a(AMapPageUtil.getAppContext(), 100.0f);
    public static final int TIPSHEIGHTINPORT = agn.a(AMapPageUtil.getAppContext(), 111.0f);
    protected OnSetPoiListener mOnSetPoiListener;

    public interface Event {
        void onExecute(int i, POI poi);
    }

    public interface OnSetPoiListener {
        void onSetPoi(POI poi);
    }

    public void adjustMargin() {
    }

    public void bindEvent(int i, Event event) {
    }

    public abstract POI getPoi();

    public void refreshByScreenState(boolean z) {
    }

    public abstract void reset();

    public abstract void setMainTitle(String str);

    public abstract void setPoi(POI poi);

    public void setRootViewBackGroundRes(int i) {
    }

    public abstract void setViceTitle(String str);

    public AbstractPoiDetailView(Context context) {
        super(context);
    }

    public void setOnSetPoiListener(OnSetPoiListener onSetPoiListener) {
        this.mOnSetPoiListener = onSetPoiListener;
    }
}

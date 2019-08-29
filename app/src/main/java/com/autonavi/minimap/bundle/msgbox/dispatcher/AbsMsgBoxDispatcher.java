package com.autonavi.minimap.bundle.msgbox.dispatcher;

import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.widget.UPMarqueeView;
import com.autonavi.minimap.widget.UPMarqueeView.OnItemClickListener;
import java.util.HashMap;
import java.util.List;

public abstract class AbsMsgBoxDispatcher implements IMsgDispatcherHost {
    protected static final String ACTIVITY = "activity";
    public static final String MARQUEE_TIPS = "marquee_tips";
    protected static final String TIPS = "tips";
    private static boolean mIsFullScreen;
    private static boolean mIsIndoor;
    private static boolean mIsSmartScenic;
    private boolean mIsEnterImmersiveMap = false;
    private boolean mIsUpdateMsg = true;
    protected HashMap<String, Boolean> mMessageStatus = new HashMap<>();

    public void destroy() {
    }

    public abstract AmapMessage dismissLayerTipsView();

    public abstract AmapMessage dismissTipsView();

    public abstract AmapMessage dismissTravelTipsView();

    /* access modifiers changed from: protected */
    public abstract int getActivityVisibility();

    public abstract String getBlueTipMsg();

    public abstract UPMarqueeView getUPMarqueeView();

    public abstract boolean hasMessageShowing();

    /* access modifiers changed from: protected */
    public abstract void refreshView();

    public abstract void reportMsgClick(AmapMessage amapMessage);

    public abstract void reportMsgClose(AmapMessage amapMessage);

    public abstract void setNewMsgVisibility(int i);

    public abstract void setOnClickListener(OnClickListener onClickListener);

    public abstract void setUPMarqueeViewOnClickListener(OnItemClickListener onItemClickListener);

    public abstract boolean showActivity(AmapMessage amapMessage);

    public abstract boolean showBubble(AmapMessage amapMessage);

    public abstract boolean showMarqueeTips(List<AmapMessage> list);

    public abstract boolean showTips(AmapMessage amapMessage);

    public boolean isFullScreen() {
        return mIsFullScreen;
    }

    public void setUpdateMsgFlag(boolean z) {
        this.mIsUpdateMsg = z;
    }

    public boolean isUpdateMsgFlag() {
        return this.mIsUpdateMsg;
    }

    public void setFullScreen(boolean z) {
        if (mIsFullScreen != z) {
            mIsFullScreen = z;
            refreshView();
        }
    }

    public boolean isIndoor() {
        return mIsIndoor;
    }

    public void setIndoor(boolean z) {
        if (mIsIndoor != z) {
            mIsIndoor = z;
            refreshView();
        }
    }

    public boolean isSmartScenic() {
        return mIsSmartScenic;
    }

    public void setSmartScenic(boolean z) {
        if (mIsSmartScenic != z) {
            mIsSmartScenic = z;
            refreshView();
        }
    }

    public boolean isEnterImmersiveMap() {
        return this.mIsEnterImmersiveMap;
    }

    public void setEnterImmersiveMapFlag(boolean z) {
        this.mIsEnterImmersiveMap = z;
    }

    /* access modifiers changed from: protected */
    public void setVisibility(View view, int i) {
        if (!(view == null || view.getVisibility() == i)) {
            view.setVisibility(i);
        }
    }

    /* access modifiers changed from: protected */
    public int getTipsVisibility() {
        return (isFullScreen() || (!this.mMessageStatus.get("tips").booleanValue() && !this.mMessageStatus.get(MARQUEE_TIPS).booleanValue())) ? 8 : 0;
    }

    /* access modifiers changed from: protected */
    public int getEntranceVisibility() {
        return isFullScreen() ? 8 : 0;
    }

    /* access modifiers changed from: protected */
    public void clearClickListener(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setOnClickListener(null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void clearItemClickListener(UPMarqueeView uPMarqueeView) {
        if (uPMarqueeView != null) {
            uPMarqueeView.setOnItemClickListener(null);
        }
    }
}

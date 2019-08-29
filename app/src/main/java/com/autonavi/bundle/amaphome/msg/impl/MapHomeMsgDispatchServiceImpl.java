package com.autonavi.bundle.amaphome.msg.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.amaphome.msg.IMapHomeMsgDispatchService;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.inter.layer.ILayerStateListener;
import com.autonavi.bundle.uitemplate.mapwidget.widget.activity.OperateActivityWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.msg.MsgGroupWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.minimap.widget.UPMarqueeView;
import com.autonavi.minimap.widget.UPMarqueeView.OnItemClickListener;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MapHomeMsgDispatchServiceImpl implements IMapHomeMsgDispatchService {
    private final MainMapMsgBoxDispatcher mMsgBoxDispatcher = new MainMapMsgBoxDispatcher();

    static class MainMapMsgBoxDispatcher extends AbsMsgBoxDispatcher {
        private OperateActivityWidgetPresenter mActivityPresenter;
        private TickHandler mHandler;
        private ILayerStateListener mLayerStateListener;
        private MsgGroupWidgetPresenter mMsgBoxPresenter;
        private int mMsgInMarqueeIndex = 0;

        static class TickHandler extends Handler {
            private static final int MAX_TICK_COUNT = 7;
            private static final int MSG_MARQUEE = 2;
            private static final int MSG_TICK = 1;
            private WeakReference<Activity> mActivityWeakReference;
            private AmapMessage mAmapMessage;
            private boolean mIsBackstage = false;
            /* access modifiers changed from: private */
            public WeakReference<AbsMsgBoxDispatcher> mMainMapMsgBoxViewWeakReference;
            /* access modifiers changed from: private */
            public int mMarqueeIndex = 0;
            private List<AmapMessage> mMarqueeMessageList;
            private int mTickCount = 0;

            public TickHandler(Activity activity, AbsMsgBoxDispatcher absMsgBoxDispatcher) {
                super(Looper.getMainLooper());
                this.mActivityWeakReference = new WeakReference<>(activity);
                this.mMainMapMsgBoxViewWeakReference = new WeakReference<>(absMsgBoxDispatcher);
            }

            public void tickMessage(AmapMessage amapMessage) {
                if (amapMessage != null) {
                    removeMessages(1);
                    if (this.mAmapMessage == null || TextUtils.isEmpty(this.mAmapMessage.id) || this.mTickCount >= 7 || !this.mAmapMessage.id.equals(amapMessage.id)) {
                        this.mAmapMessage = amapMessage;
                        this.mTickCount = 0;
                        sendEmptyMessageDelayed(1, 1000);
                        return;
                    }
                    sendEmptyMessageDelayed(1, 1000);
                }
            }

            public void marqueeMessage(List<AmapMessage> list) {
                if (list != null && list.size() != 0) {
                    removeMessages(2);
                    this.mMarqueeMessageList = list;
                    this.mMarqueeIndex = 0;
                    this.mIsBackstage = false;
                    sendEmptyMessage(2);
                }
            }

            public void clear() {
                removeMessages(1);
                removeMessages(2);
                this.mAmapMessage = null;
                this.mMarqueeMessageList = null;
            }

            public void pause() {
                removeMessages(1);
                this.mIsBackstage = true;
            }

            public void resume() {
                if (this.mAmapMessage != null) {
                    sendEmptyMessageDelayed(1, 1000);
                }
                if (this.mMarqueeMessageList != null) {
                    this.mIsBackstage = false;
                    sendEmptyMessage(2);
                }
            }

            public void setBarDisplay() {
                AmapMessage amapMessage = this.mMarqueeMessageList.get(this.mMarqueeIndex);
                StringBuilder sb = new StringBuilder("---setBarDisplay-----message.isADDisplay():");
                sb.append(amapMessage.isADDisplay());
                AMapLog.d("----redesign--msgbox-->", sb.toString());
                if (!amapMessage.isADDisplay()) {
                    amapMessage.barDisplay = true;
                    MessageBoxManager.getInstance().setBarMsgDisplay(amapMessage.id);
                    StringBuilder sb2 = new StringBuilder("---setBarDisplay-message.isADMsg():");
                    sb2.append(amapMessage.isADMsg());
                    AMapLog.d("----redesign--msgbox-->", sb2.toString());
                    if (amapMessage.isADMsg()) {
                        MessageBoxManager.getInstance().reportDisplayLog(amapMessage.id, 2, 1);
                        vw vwVar = (vw) a.a.a(vw.class);
                        if (vwVar != null) {
                            vwVar.a(2, amapMessage.impression);
                        }
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("name", this.mMarqueeIndex + 1);
                        jSONObject.put("type", "常规");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2("P00001", LogConstant.BLUE_BAR_SHOW, jSONObject);
                }
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        boolean z = true;
                        this.mTickCount++;
                        if (this.mTickCount >= 7) {
                            try {
                                if (this.mActivityWeakReference.get() != null) {
                                    z = ((Activity) this.mActivityWeakReference.get()).isFinishing();
                                }
                            } catch (Throwable unused) {
                            }
                            if (!z && this.mAmapMessage != null && AMapPageUtil.isHomePage() && this.mMainMapMsgBoxViewWeakReference.get() != null) {
                                AmapMessage dismissTipsView = ((AbsMsgBoxDispatcher) this.mMainMapMsgBoxViewWeakReference.get()).dismissTipsView();
                                if (dismissTipsView != null && dismissTipsView.isToastTips()) {
                                    MessageBoxManager.getInstance().setShowOnMap(dismissTipsView);
                                }
                                this.mAmapMessage = null;
                                return;
                            }
                            return;
                        } else if (this.mTickCount < 7 && this.mTickCount >= 0) {
                            sendEmptyMessageDelayed(1, 1000);
                            return;
                        }
                        break;
                    case 2:
                        removeMessages(2);
                        if (this.mMainMapMsgBoxViewWeakReference.get() != null) {
                            UPMarqueeView uPMarqueeView = ((AbsMsgBoxDispatcher) this.mMainMapMsgBoxViewWeakReference.get()).getUPMarqueeView();
                            if (uPMarqueeView != null) {
                                this.mMarqueeIndex = uPMarqueeView.getDisplayedChild();
                                aho.a(new Runnable() {
                                    public void run() {
                                        ((AbsMsgBoxDispatcher) TickHandler.this.mMainMapMsgBoxViewWeakReference.get()).setMarqueeIndex(TickHandler.this.mMarqueeIndex);
                                    }
                                });
                                StringBuilder sb = new StringBuilder("---setBarDisplay-handle---MSG_MARQUEE--index:");
                                sb.append(this.mMarqueeIndex);
                                AMapLog.d("----redesign--msgbox-->", sb.toString());
                                if (!((AbsMsgBoxDispatcher) this.mMainMapMsgBoxViewWeakReference.get()).isFullScreen() && !this.mIsBackstage) {
                                    setBarDisplay();
                                }
                                sendEmptyMessageDelayed(2, 5000);
                                break;
                            }
                        }
                        break;
                }
            }
        }

        public AmapMessage dismissTravelTipsView() {
            return null;
        }

        public String getBlueTipMsg() {
            return null;
        }

        @Deprecated
        public void setOnClickListener(OnClickListener onClickListener) {
        }

        @Deprecated
        public void setUPMarqueeViewOnClickListener(OnItemClickListener onItemClickListener) {
        }

        public MainMapMsgBoxDispatcher() {
            this.mMessageStatus.put(WidgetType.ACTIVITY, Boolean.FALSE);
            this.mMessageStatus.put(ModulePoi.TIPS, Boolean.FALSE);
            this.mMessageStatus.put(AbsMsgBoxDispatcher.MARQUEE_TIPS, Boolean.FALSE);
        }

        public void initPresenter(MsgGroupWidgetPresenter msgGroupWidgetPresenter, OperateActivityWidgetPresenter operateActivityWidgetPresenter) {
            this.mMsgBoxPresenter = msgGroupWidgetPresenter;
            this.mMsgBoxPresenter.setMsgBoxDispatcher(this);
            this.mActivityPresenter = operateActivityWidgetPresenter;
            initLayerListener();
            initTickHandler();
        }

        private Activity getNewMainActivity() {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                return null;
            }
            return pageContext.getActivity();
        }

        private void initTickHandler() {
            this.mHandler = new TickHandler(getNewMainActivity(), this);
        }

        private boolean isNotNull(IMapWidgetPresenter iMapWidgetPresenter) {
            return (iMapWidgetPresenter == null || iMapWidgetPresenter.getWidget() == null) ? false : true;
        }

        private void initLayerListener() {
            LayerWidgetPresenter layerWidgetPresenter = (LayerWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.LAYER);
            if (layerWidgetPresenter != null) {
                this.mLayerStateListener = layerWidgetPresenter.getLayerStateListener();
            }
        }

        public void setNewMsgVisibility(int i) {
            if (isNotNull(this.mMsgBoxPresenter)) {
                ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).setNewMsgVisibility(i);
            }
        }

        public AmapMessage dismissTipsView() {
            if (((Boolean) this.mMessageStatus.get(ModulePoi.TIPS)).booleanValue() || ((Boolean) this.mMessageStatus.get(AbsMsgBoxDispatcher.MARQUEE_TIPS)).booleanValue()) {
                this.mMessageStatus.put(ModulePoi.TIPS, Boolean.FALSE);
                this.mMessageStatus.put(AbsMsgBoxDispatcher.MARQUEE_TIPS, Boolean.FALSE);
                if (isNotNull(this.mMsgBoxPresenter)) {
                    ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).setMsgBoxViewVisibility(8);
                    return (AmapMessage) ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).getTipsFrameLayoutTag();
                }
            }
            return null;
        }

        public AmapMessage dismissLayerTipsView() {
            if (this.mLayerStateListener != null) {
                return (AmapMessage) this.mLayerStateListener.dismissTips();
            }
            return null;
        }

        public boolean hasMessageShowing() {
            return ((Boolean) this.mMessageStatus.get(ModulePoi.TIPS)).booleanValue() || ((Boolean) this.mMessageStatus.get(AbsMsgBoxDispatcher.MARQUEE_TIPS)).booleanValue() || (this.mLayerStateListener != null && this.mLayerStateListener.isTipsShown());
        }

        public boolean showActivity(AmapMessage amapMessage) {
            if (!isNotNull(this.mActivityPresenter)) {
                return false;
            }
            boolean showActivity = this.mActivityPresenter.showActivity(amapMessage, this.mMessageStatus, WidgetType.ACTIVITY);
            if (showActivity) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", amapMessage.id);
                    jSONObject.put("status", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_MSGBOX_AD_ENTRANCE_DISP, jSONObject);
                setMsgDisplay(amapMessage, 3, true);
                vw vwVar = (vw) a.a.a(vw.class);
                if (vwVar != null) {
                    vwVar.a(3, amapMessage.impression);
                }
            }
            return showActivity;
        }

        public boolean showTips(AmapMessage amapMessage) {
            if (amapMessage == null || ((TextUtils.isEmpty(amapMessage.descMessage) && TextUtils.isEmpty(amapMessage.showBody)) || hasMessageShowing() || !isNotNull(this.mMsgBoxPresenter))) {
                return false;
            }
            if (AMapPageUtil.isHomePage()) {
                this.mMessageStatus.put(ModulePoi.TIPS, Boolean.TRUE);
            }
            boolean showTips = ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).showTips(amapMessage, getTipsVisibility());
            if (showTips) {
                setMsgDisplay(amapMessage, 2, false);
                vw vwVar = (vw) a.a.a(vw.class);
                if (vwVar != null) {
                    vwVar.a(2, amapMessage.impression);
                }
            }
            return showTips;
        }

        public boolean showMarqueeTips(List<AmapMessage> list) {
            if (hasMessageShowing()) {
                return false;
            }
            if (isNotNull(this.mMsgBoxPresenter)) {
                this.mMessageStatus.put(AbsMsgBoxDispatcher.MARQUEE_TIPS, Boolean.TRUE);
                ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).showMarqueeTips(list, AbsMsgBoxDispatcher.MARQUEE_TIPS, getTipsVisibility());
            }
            return true;
        }

        public boolean showBubble(AmapMessage amapMessage) {
            if (this.mLayerStateListener == null || amapMessage.location != 4) {
                return false;
            }
            return this.mLayerStateListener.showTips(amapMessage);
        }

        public UPMarqueeView getUPMarqueeView() {
            if (isNotNull(this.mMsgBoxPresenter)) {
                return ((MsgGroupMapWidget) this.mMsgBoxPresenter.getWidget()).getUPMarqueeView();
            }
            return null;
        }

        public void refreshView() {
            if (isNotNull(this.mActivityPresenter)) {
                isEnterImmersiveMap();
            }
        }

        public int getActivityVisibility() {
            return (isFullScreen() || isIndoor() || isSmartScenic() || !((Boolean) this.mMessageStatus.get(WidgetType.ACTIVITY)).booleanValue()) ? 8 : 0;
        }

        public void reportMsgClose(AmapMessage amapMessage) {
            if (amapMessage != null && amapMessage.isADMsg()) {
                MessageBoxManager.getInstance().reportDisplayLogIgnoreError(amapMessage.id, 2, 3);
            }
        }

        public void reportMsgClick(AmapMessage amapMessage) {
            if (amapMessage != null && amapMessage.isADMsg()) {
                MessageBoxManager.getInstance().reportDisplayLogIgnoreError(amapMessage.id, 2, 2);
            }
        }

        public void clearTipTimer() {
            if (this.mHandler != null) {
                this.mHandler.clear();
            }
        }

        public void stopTipTimer() {
            if (this.mHandler != null) {
                this.mHandler.pause();
            }
        }

        public void resumeTipTimer() {
            if (this.mHandler != null) {
                this.mHandler.resume();
            }
        }

        public void tickMessage(AmapMessage amapMessage) {
            if (this.mHandler != null) {
                this.mHandler.tickMessage(amapMessage);
            }
        }

        public void marqueeMessage(List<AmapMessage> list) {
            if (this.mHandler != null) {
                this.mHandler.marqueeMessage(list);
            }
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        public void setMarqueeIndex(int i) {
            AMapLog.d("----redesign--msgbox-->", "setMarqueeIndex--index:".concat(String.valueOf(i)));
            this.mMsgInMarqueeIndex = i;
        }

        public int getMarqueeIndex() {
            return this.mMsgInMarqueeIndex;
        }

        public void setMsgDisplay(AmapMessage amapMessage, int i, boolean z) {
            if (!amapMessage.isADDisplay()) {
                amapMessage.barDisplay = true;
                MessageBoxManager.getInstance().setBarMsgDisplay(amapMessage.id);
                if (amapMessage.isADMsg()) {
                    MessageBoxManager.getInstance().reportDisplayLog(amapMessage.id, i, 1);
                }
                if (!z) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("name", 1);
                        jSONObject.put("type", amapMessage.isEmergencyNews() ? "紧急" : "常规");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2("P00001", LogConstant.BLUE_BAR_SHOW, jSONObject);
                }
            }
        }
    }

    public void bindUi(MsgGroupWidgetPresenter msgGroupWidgetPresenter, OperateActivityWidgetPresenter operateActivityWidgetPresenter) {
        if (this.mMsgBoxDispatcher != null) {
            this.mMsgBoxDispatcher.initPresenter(msgGroupWidgetPresenter, operateActivityWidgetPresenter);
        }
    }

    public AbsMsgBoxDispatcher getMsgBoxDispatcher() {
        return this.mMsgBoxDispatcher;
    }
}

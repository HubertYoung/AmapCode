package com.autonavi.minimap.ajx3.widget.property;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.Property;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.ComputeUtils;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList.ScrollTopChange;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnLoadMoreListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.State;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class PullToRefreshListProperty extends BaseProperty<PullToRefreshList> {
    private static final String ATTR_INDICATOR = "indicator";
    private static final String ATTR_LEAVE_BOUND = "leavebound";
    private static final String ATTR_LIST_FORCE_SCROLL_TOP = "forceScrollTop";
    public static final String ATTR_LIST_SCROLL_TOP = "scrollTop";
    private static final String ATTR_OFFSET = "offset";
    private static final String ATTR_PRELOAD_EVENT = "preload";
    private static final String ATTR_PRETRIGGER = "pretrigger";
    private static final String ATTR_REFRESH_EVENT = "refresh";
    private static final String ATTR_REFRESH_MODE = "fresh";
    private static final String ATTR_RELATIVE_SCROLL_DISTANCE = "relativeScrollDistance";
    private static final String ATTR_SCROLL_ABLE = "scrollable";
    private static final String ATTR_SCROLL_ACCURACY = "scrollaccuracy";
    private static final String ATTR_SCROLL_BEGIN = "scrollBegin";
    private static final String ATTR_SCROLL_BOUND = "scrollbound";
    private static final String ATTR_SCROLL_EASE = "scrollease";
    private static final String ATTR_SCROLL_END = "scrollEnd";
    private static final String ATTR_SCROLL_INERTIA_END = "scrollInertiaEnd";
    public static final String ATTR_SCROLL_TOP = "_SCROLL_TOP";
    private static final String ATTR_SMOOTH_SCROLL = "smoothScroll";
    private static final String ATTR_START_LOADING = "startLoading";
    private static final String ATTR_STOP_LOADING = "stopLoading";
    private static final int MSG_SCROLL_BY_CORRECTION = 2;
    private static final int MSG_UPDATE_SCROLL_BY = 1;
    private static final int MSG_UPDATE_SCROLL_TOP = 0;
    private static final float PRECISION = 0.01f;
    private static final String TAG = "ListProperty";
    private static final int Y_DISTANCE_MAGIC = -10000;
    /* access modifiers changed from: private */
    public final HashMap<String, Object> mCurrentProperties = new HashMap<>();
    /* access modifiers changed from: private */
    public boolean mFirstSimMotionEvent = true;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (message.obj != null) {
                        PullToRefreshListProperty.this.updateScrollTop(message.obj);
                        return;
                    }
                    break;
                case 1:
                    PullToRefreshListProperty.this.scrollBy(message.obj != null ? (ScrollByData) message.obj : null);
                    return;
                case 2:
                    try {
                        ((PullToRefreshList) PullToRefreshListProperty.this.mView).scrollBy(0, message.arg1);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
            }
        }
    };
    private String mMode = Constants.ANIMATOR_NONE;
    private boolean mMotionEventRegistered = false;
    /* access modifiers changed from: private */
    public AjxList mRecyclerView;
    /* access modifiers changed from: private */
    public float mScrollAccuracy = -1.0f;
    private boolean mScrollease = false;
    /* access modifiers changed from: private */
    public MotionEvent mSimMotionEvent;

    class ScrollByData {
        int height;
        long nodeId;
        String option;
        int top;

        private ScrollByData() {
        }
    }

    public PullToRefreshListProperty(@NonNull PullToRefreshList pullToRefreshList, @NonNull AjxList ajxList, @NonNull IAjxContext iAjxContext) {
        super(pullToRefreshList, iAjxContext);
        this.mCurrentProperties.put("scrollTop", Integer.valueOf(0));
        this.mRecyclerView = ajxList;
        this.mRecyclerView.setScrollTopChange(new ScrollTopChange() {
            public void onScrollTopChange(boolean z) {
                if (PullToRefreshListProperty.this.mRecyclerView.checkViewHolder()) {
                    float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit((float) PullToRefreshListProperty.this.mRecyclerView.getAccurateScrollOffsetY());
                    if (PullToRefreshListProperty.this.mScrollAccuracy > 0.0f) {
                        Object obj = PullToRefreshListProperty.this.mCurrentProperties.get("scrollTop");
                        float f = obj instanceof Integer ? (float) ((Integer) obj).intValue() : obj instanceof Float ? ((Float) obj).floatValue() : pixelToStandardUnit;
                        PullToRefreshListProperty.this.notifyPropertyListenerWithCompensation("scrollTop", DimensionUtils.pixelToStandardUnit(pixelToStandardUnit), DimensionUtils.pixelToStandardUnit(f), PullToRefreshListProperty.this.mScrollAccuracy);
                    }
                    PullToRefreshListProperty.this.notifyPropertyListener("scrollTop", Float.valueOf(pixelToStandardUnit));
                    PullToRefreshListProperty.this.mCurrentProperties.put("scrollTop", Float.valueOf(pixelToStandardUnit));
                    PullToRefreshListProperty.this.getNode().setAttribute((String) "scrollTop", (Object) Float.valueOf(pixelToStandardUnit));
                    if (z) {
                        String valueOf = String.valueOf(pixelToStandardUnit);
                        PullToRefreshListProperty.this.mAjxContext.invokeJsEvent(new Builder().setNodeId(PullToRefreshListProperty.this.getNodeId()).addAttribute("_SCROLL_TOP", valueOf).addAttribute("scrollTop", valueOf).build());
                    }
                }
            }
        });
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateAttribute(java.lang.String r3, java.lang.Object r4) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 1
            switch(r0) {
                case -1555761739: goto L_0x00f5;
                case -1019779949: goto L_0x00ea;
                case -948122918: goto L_0x00de;
                case -866286906: goto L_0x00d2;
                case -711999985: goto L_0x00c7;
                case -671184274: goto L_0x00bc;
                case -318476791: goto L_0x00b1;
                case -27294425: goto L_0x00a7;
                case 66669991: goto L_0x009b;
                case 66788411: goto L_0x008e;
                case 97696046: goto L_0x0082;
                case 202203770: goto L_0x0075;
                case 417766094: goto L_0x0069;
                case 417780552: goto L_0x005d;
                case 848088603: goto L_0x0050;
                case 1057926094: goto L_0x0045;
                case 1085444827: goto L_0x0039;
                case 1544388765: goto L_0x002d;
                case 1640986210: goto L_0x0022;
                case 2038225372: goto L_0x0016;
                case 2068089553: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0100
        L_0x000a:
            java.lang.String r0 = "scrollbound"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 3
            goto L_0x0101
        L_0x0016:
            java.lang.String r0 = "scrollBegin"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 0
            goto L_0x0101
        L_0x0022:
            java.lang.String r0 = "_SCROLL_TOP"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 7
            goto L_0x0101
        L_0x002d:
            java.lang.String r0 = "forceScrollTop"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 8
            goto L_0x0101
        L_0x0039:
            java.lang.String r0 = "refresh"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 14
            goto L_0x0101
        L_0x0045:
            java.lang.String r0 = "relativeScrollDistance"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 5
            goto L_0x0101
        L_0x0050:
            java.lang.String r0 = "smoothScroll"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 9
            goto L_0x0101
        L_0x005d:
            java.lang.String r0 = "scrollTop"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 6
            goto L_0x0101
        L_0x0069:
            java.lang.String r0 = "scrollEnd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 1
            goto L_0x0101
        L_0x0075:
            java.lang.String r0 = "startLoading"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 12
            goto L_0x0101
        L_0x0082:
            java.lang.String r0 = "fresh"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 11
            goto L_0x0101
        L_0x008e:
            java.lang.String r0 = "scrollease"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 16
            goto L_0x0101
        L_0x009b:
            java.lang.String r0 = "scrollable"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 10
            goto L_0x0101
        L_0x00a7:
            java.lang.String r0 = "leavebound"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 4
            goto L_0x0101
        L_0x00b1:
            java.lang.String r0 = "preload"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 15
            goto L_0x0101
        L_0x00bc:
            java.lang.String r0 = "scrollInertiaEnd"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 2
            goto L_0x0101
        L_0x00c7:
            java.lang.String r0 = "indicator"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 19
            goto L_0x0101
        L_0x00d2:
            java.lang.String r0 = "scrollaccuracy"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 20
            goto L_0x0101
        L_0x00de:
            java.lang.String r0 = "stopLoading"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 13
            goto L_0x0101
        L_0x00ea:
            java.lang.String r0 = "offset"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 18
            goto L_0x0101
        L_0x00f5:
            java.lang.String r0 = "pretrigger"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0100
            r0 = 17
            goto L_0x0101
        L_0x0100:
            r0 = -1
        L_0x0101:
            switch(r0) {
                case 0: goto L_0x016a;
                case 1: goto L_0x015f;
                case 2: goto L_0x0154;
                case 3: goto L_0x0149;
                case 4: goto L_0x0149;
                case 5: goto L_0x0145;
                case 6: goto L_0x0141;
                case 7: goto L_0x0141;
                case 8: goto L_0x0138;
                case 9: goto L_0x0134;
                case 10: goto L_0x0130;
                case 11: goto L_0x012c;
                case 12: goto L_0x0128;
                case 13: goto L_0x0124;
                case 14: goto L_0x0120;
                case 15: goto L_0x011c;
                case 16: goto L_0x0118;
                case 17: goto L_0x0114;
                case 18: goto L_0x0110;
                case 19: goto L_0x010c;
                case 20: goto L_0x0108;
                default: goto L_0x0104;
            }
        L_0x0104:
            super.updateAttribute(r3, r4)
            return
        L_0x0108:
            r2.updateScrollAccuracy(r4)
            return
        L_0x010c:
            r2.updateIndicator(r4)
            return
        L_0x0110:
            r2.updateOffset(r4)
            return
        L_0x0114:
            r2.updateTrigger(r4)
            return
        L_0x0118:
            r2.updateScrollease(r4)
            return
        L_0x011c:
            r2.updatePreload(r4)
            return
        L_0x0120:
            r2.updateRefresh()
            return
        L_0x0124:
            r2.stopLoading(r4)
            return
        L_0x0128:
            r2.startLoading(r4)
            return
        L_0x012c:
            r2.updateMode(r4)
            return
        L_0x0130:
            r2.updateScrollable(r4)
            return
        L_0x0134:
            r2.updateSmoothScroll(r4)
            return
        L_0x0138:
            com.autonavi.minimap.ajx3.widget.view.list.AjxList r3 = r2.mRecyclerView
            r3.setForceFlag(r1)
            r2.updateScrollTop(r4)
            return
        L_0x0141:
            r2.updateScrollTop(r4)
            return
        L_0x0145:
            r2.updateRelativeScrollDistance(r4)
            return
        L_0x0149:
            com.autonavi.minimap.ajx3.widget.view.list.AjxList r3 = r2.mRecyclerView
            com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$5 r4 = new com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$5
            r4.<init>()
            r3.setScrollBoundListener(r4)
            return
        L_0x0154:
            com.autonavi.minimap.ajx3.widget.view.list.AjxList r3 = r2.mRecyclerView
            com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$4 r4 = new com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$4
            r4.<init>()
            r3.setScrollInertiaEnd(r4)
            return
        L_0x015f:
            com.autonavi.minimap.ajx3.widget.view.list.AjxList r3 = r2.mRecyclerView
            com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$3 r4 = new com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$3
            r4.<init>()
            r3.setScrollEndListener(r4)
            return
        L_0x016a:
            com.autonavi.minimap.ajx3.widget.view.list.AjxList r3 = r2.mRecyclerView
            com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$2 r4 = new com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty$2
            r4.<init>()
            r3.setScrollBeginListener(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty.updateAttribute(java.lang.String, java.lang.Object):void");
    }

    private void updateScrollAccuracy(Object obj) {
        try {
            this.mScrollAccuracy = Float.parseFloat(String.valueOf(obj));
        } catch (NumberFormatException unused) {
        }
    }

    private void updateBounce(Object obj) {
        if (!(obj instanceof String)) {
            this.mRecyclerView.setEdgeEffectMode(Constants.ANIMATOR_NONE);
        } else {
            this.mRecyclerView.setEdgeEffectMode((String) obj);
        }
    }

    /* access modifiers changed from: protected */
    public void updateStyle(int i, Object obj, boolean z) {
        switch (i) {
            case Property.NODE_PROPERTY_BOUNCE /*1056964687*/:
                break;
            case Property.NODE_PROPERTY_INDICATOR /*1056964688*/:
                updateIndicator(obj);
                break;
            default:
                super.updateStyle(i, obj, z);
                return;
        }
        updateBounce(obj);
    }

    private void updateTrigger(Object obj) {
        int i = obj instanceof Integer ? ((Integer) obj).intValue() : obj instanceof String ? Integer.parseInt((String) obj) : 1;
        this.mRecyclerView.setPreTrigger(i);
    }

    private void updateOffset(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (!TextUtils.isEmpty(str)) {
                int indexOf = str.indexOf(Params.UNIT_PX);
                if (indexOf != -1) {
                    str = str.substring(0, indexOf);
                }
                this.mRecyclerView.setPullOffset(Integer.parseInt(str), 0);
            }
        }
    }

    private void updateScrollease(Object obj) {
        if (obj instanceof Boolean) {
            this.mScrollease = ((Boolean) obj).booleanValue();
            return;
        }
        if (obj instanceof String) {
            this.mScrollease = StringUtils.parseBoolean((String) obj);
        }
    }

    private void updatePreload(Object obj) {
        this.mRecyclerView.trackScroll(obj != null);
    }

    private void updateIndicator(Object obj) {
        if (obj instanceof Boolean) {
            this.mRecyclerView.setVerticalScrollBarEnabled(((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof String) {
            this.mRecyclerView.setVerticalScrollBarEnabled(StringUtils.parseBoolean((String) obj));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getAttribute(java.lang.String r3) {
        /*
            r2 = this;
            int r0 = r3.hashCode()
            r1 = 417780552(0x18e6d348, float:5.9666946E-24)
            if (r0 == r1) goto L_0x0019
            r1 = 1640986210(0x61cf7662, float:4.783758E20)
            if (r0 == r1) goto L_0x000f
            goto L_0x0024
        L_0x000f:
            java.lang.String r0 = "_SCROLL_TOP"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0024
            r0 = 1
            goto L_0x0025
        L_0x0019:
            java.lang.String r0 = "scrollTop"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0024
            r0 = 0
            goto L_0x0025
        L_0x0024:
            r0 = -1
        L_0x0025:
            switch(r0) {
                case 0: goto L_0x002d;
                case 1: goto L_0x002d;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.Object r3 = super.getAttribute(r3)
            return r3
        L_0x002d:
            java.util.HashMap<java.lang.String, java.lang.Object> r3 = r2.mCurrentProperties
            java.lang.String r0 = "scrollTop"
            java.lang.Object r3 = r3.get(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty.getAttribute(java.lang.String):java.lang.Object");
    }

    private void updateRelativeScrollDistance(Object obj) {
        if (obj instanceof Float) {
            final boolean z = this.mRecyclerView.getEdgeEffectMode() == 0 || this.mRecyclerView.getEdgeEffectMode() == 2;
            if (!this.mMotionEventRegistered) {
                this.mAjxContext.getDomTree().getRootView().getHelper().setMotionEventChangeListener(new MotionEventChangeListener() {
                    public void onMotionEventChange(MotionEvent motionEvent) {
                        PullToRefreshListProperty.this.mSimMotionEvent = motionEvent;
                        if (PullToRefreshListProperty.this.mSimMotionEvent.getAction() == 1 && z && PullToRefreshListProperty.this.mRecyclerView.isLastItemVisible()) {
                            PullToRefreshListProperty.this.mFirstSimMotionEvent = true;
                            PullToRefreshListProperty.this.mRecyclerView.doTouchEvent(PullToRefreshListProperty.this.mSimMotionEvent);
                        }
                    }
                });
                this.mMotionEventRegistered = true;
            }
            int scrollY = ((PullToRefreshList) this.mView).getScrollY() - DimensionUtils.standardUnitToPixel(((Float) obj).floatValue());
            if (!z || !this.mRecyclerView.isLastItemVisible() || this.mSimMotionEvent == null) {
                ((PullToRefreshList) this.mView).scrollBy(0, scrollY);
            } else if (this.mFirstSimMotionEvent) {
                MotionEvent obtain = MotionEvent.obtain(this.mSimMotionEvent);
                obtain.setAction(0);
                this.mRecyclerView.doTouchEvent(obtain);
                this.mFirstSimMotionEvent = false;
            } else {
                this.mRecyclerView.doTouchEvent(this.mSimMotionEvent);
            }
        }
    }

    public void scrollBy(String str, int i, int i2, long j) {
        ScrollByData scrollByData = new ScrollByData();
        scrollByData.option = str;
        scrollByData.top = i;
        scrollByData.height = i2;
        scrollByData.nodeId = j;
        scrollBy(scrollByData);
    }

    /* access modifiers changed from: private */
    public void scrollBy(ScrollByData scrollByData) {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(1);
        }
        if (!((PullToRefreshList) this.mView).isUpdatingUI()) {
            scrollByInternal(scrollByData);
        } else if (this.mHandler != null) {
            Message message = new Message();
            message.what = 1;
            message.obj = scrollByData;
            this.mHandler.sendMessageDelayed(message, 100);
        }
    }

    /* access modifiers changed from: private */
    public void updateScrollTop(Object obj) {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
        }
        if (!((PullToRefreshList) this.mView).isUpdatingUI()) {
            updateScrollTopInternal(obj);
        } else if (this.mHandler != null) {
            Message message = new Message();
            message.what = 0;
            message.obj = obj;
            this.mHandler.sendMessageDelayed(message, 100);
        }
    }

    private void scrollByInternal(ScrollByData scrollByData) {
        if (this.mRecyclerView.mIsWaterFall) {
            long j = scrollByData.nodeId;
            ((PullToRefreshList) this.mView).scrollBy(0, this.mRecyclerView.getTargetScrollOffsetY(j) - this.mRecyclerView.getAccurateScrollOffsetY());
            return;
        }
        scrollByInternal(scrollByData.option, scrollByData.top, scrollByData.height);
    }

    private void scrollByInternal(String str, int i, int i2) {
        int accurateScrollOffsetY = this.mRecyclerView.getAccurateScrollOffsetY();
        int computeYScrollBy = ComputeUtils.computeYScrollBy(str, i, i2, ((PullToRefreshList) this.mView).getHeight(), accurateScrollOffsetY);
        if (computeYScrollBy <= -10000) {
            int scrollToPosition = this.mRecyclerView.getScrollToPosition(DimensionUtils.pixelToStandardUnit((float) i), true);
            if (scrollToPosition >= 0) {
                float cellTotalHeight = (float) (accurateScrollOffsetY - this.mRecyclerView.getAdapter().getCellTotalHeight(scrollToPosition));
                this.mRecyclerView.scrollToPosition(scrollToPosition);
                if (Math.abs(cellTotalHeight) > 10.0f && needCorrectY(str) && this.mHandler != null) {
                    Message message = new Message();
                    message.what = 2;
                    message.arg1 = (int) cellTotalHeight;
                    this.mHandler.sendMessage(message);
                }
                return;
            }
        }
        ((PullToRefreshList) this.mView).scrollBy(0, computeYScrollBy);
    }

    private boolean needCorrectY(String str) {
        if (str != null) {
            try {
                String string = new JSONObject(str).getString("block");
                if (Callout.CALLOUT_TEXT_ALIGN_CENTER.equals(string) || "end".equals(string)) {
                    return true;
                }
            } catch (JSONException unused) {
            }
        }
        return false;
    }

    public void onDetachedFromWindow() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
        }
    }

    public void onPageDestroy() {
        if (this.mHandler != null) {
            this.mHandler.removeMessages(0);
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
        }
        this.mHandler = null;
    }

    private void updateScrollTopInternal(Object obj) {
        if (obj != null) {
            try {
                float parseFloat = Float.parseFloat(obj.toString());
                if (Math.abs(parseFloat) >= PRECISION) {
                    int standardUnitToPixel = DimensionUtils.standardUnitToPixel(parseFloat);
                    int accurateScrollOffsetY = this.mRecyclerView.getAccurateScrollOffsetY();
                    if (this.mScrollease) {
                        this.mRecyclerView.smoothScrollBy(0, standardUnitToPixel - accurateScrollOffsetY);
                        return;
                    }
                    int i = standardUnitToPixel - accurateScrollOffsetY;
                    if (i <= -10000) {
                        if (this.mRecyclerView.mIsWaterFall) {
                            ((PullToRefreshList) this.mView).scrollBy(0, i);
                            return;
                        }
                        int scrollToPosition = this.mRecyclerView.getScrollToPosition(parseFloat, true);
                        if (scrollToPosition >= 0) {
                            float cellTotalHeight = (float) (standardUnitToPixel - this.mRecyclerView.getAdapter().getCellTotalHeight(scrollToPosition));
                            this.mRecyclerView.scrollToPosition(scrollToPosition);
                            if (Math.abs(cellTotalHeight) > 10.0f && this.mHandler != null) {
                                Message message = new Message();
                                message.what = 2;
                                message.arg1 = (int) cellTotalHeight;
                                this.mHandler.sendMessage(message);
                            }
                            return;
                        }
                    }
                    ((PullToRefreshList) this.mView).scrollBy(0, i);
                } else if (this.mScrollease) {
                    this.mRecyclerView.smoothScrollToPosition(0);
                } else {
                    this.mRecyclerView.scrollToPosition(0);
                    notifyPropertyListener("scrollTop", Integer.valueOf(0));
                }
            } catch (NumberFormatException unused) {
            }
        }
    }

    private void updateSmoothScroll(Object obj) {
        if (obj != null) {
            try {
                int parseInt = Integer.parseInt(obj.toString());
                if (parseInt == 0) {
                    this.mRecyclerView.stopScroll();
                    return;
                }
                float abs = Math.abs(DimensionUtils.pixelToDip((float) parseInt));
                if (abs > 0.0f && abs <= ((float) ViewConfiguration.getMinimumFlingVelocity())) {
                    invoke(ATTR_SCROLL_INERTIA_END, null);
                }
                this.mRecyclerView.fling(0, -parseInt);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateScrollable(Object obj) {
        if (obj != null) {
            this.mRecyclerView.scrollByInertia(!Boolean.parseBoolean((String) obj));
        }
    }

    /* access modifiers changed from: private */
    public void invoke(String str, Parcel parcel) {
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(getNodeId()).setAttribute(parcel).build());
    }

    /* access modifiers changed from: private */
    public void invokeScrollTopToJs(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(DimensionUtils.pixelToStandardUnit((float) this.mRecyclerView.getAccurateScrollOffsetY()));
        String sb2 = sb.toString();
        Parcel parcel = new Parcel();
        parcel.writeInt(4);
        parcel.writeString("_SCROLL_TOP");
        parcel.writeString(sb2);
        parcel.writeString("scrollTop");
        parcel.writeString(sb2);
        invoke(str, parcel);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateMode(java.lang.Object r6) {
        /*
            r5 = this;
            if (r6 != 0) goto L_0x0003
            return
        L_0x0003:
            java.lang.String r6 = (java.lang.String) r6
            r5.mMode = r6
            android.view.View r6 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r6 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r6
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            java.lang.String r0 = r5.mMode
            java.lang.String r0 = r0.toLowerCase()
            int r1 = r0.hashCode()
            r2 = -1383228885(0xffffffffad8d9a2b, float:-1.6098308E-11)
            r3 = 0
            r4 = -1
            if (r1 == r2) goto L_0x004f
            r2 = 115029(0x1c155, float:1.6119E-40)
            if (r1 == r2) goto L_0x0044
            r2 = 3029889(0x2e3b81, float:4.245779E-39)
            if (r1 == r2) goto L_0x003a
            r2 = 3387192(0x33af38, float:4.746467E-39)
            if (r1 == r2) goto L_0x0030
            goto L_0x0059
        L_0x0030:
            java.lang.String r1 = "none"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0059
            r0 = 0
            goto L_0x005a
        L_0x003a:
            java.lang.String r1 = "both"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0059
            r0 = 3
            goto L_0x005a
        L_0x0044:
            java.lang.String r1 = "top"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0059
            r0 = 1
            goto L_0x005a
        L_0x004f:
            java.lang.String r1 = "bottom"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0059
            r0 = 2
            goto L_0x005a
        L_0x0059:
            r0 = -1
        L_0x005a:
            switch(r0) {
                case 0: goto L_0x00b5;
                case 1: goto L_0x009f;
                case 2: goto L_0x0089;
                case 3: goto L_0x0073;
                default: goto L_0x005d;
            }
        L_0x005d:
            android.view.View r0 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r0
            com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.DISABLED
            r0.setMode(r1)
            boolean r0 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x00cb
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r0 = r6.topMargin
            if (r0 != r4) goto L_0x00cb
            r6.topMargin = r3
            goto L_0x00cb
        L_0x0073:
            android.view.View r0 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r0
            com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.BOTH
            r0.setMode(r1)
            boolean r0 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x00cb
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r0 = r6.topMargin
            if (r0 != 0) goto L_0x00cb
            r6.topMargin = r4
            return
        L_0x0089:
            android.view.View r0 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r0
            com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_END
            r0.setMode(r1)
            boolean r0 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x00cb
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r0 = r6.topMargin
            if (r0 != r4) goto L_0x00cb
            r6.topMargin = r3
            return
        L_0x009f:
            android.view.View r0 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r0
            com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.PULL_FROM_START
            r0.setMode(r1)
            boolean r0 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x00cb
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r0 = r6.topMargin
            if (r0 != 0) goto L_0x00cb
            r6.topMargin = r4
            return
        L_0x00b5:
            android.view.View r0 = r5.mView
            com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList r0 = (com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList) r0
            com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase$Mode r1 = com.autonavi.minimap.ajx3.widget.view.list.pulltorefresh.PullToRefreshBase.Mode.DISABLED
            r0.setMode(r1)
            boolean r0 = r6 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r0 == 0) goto L_0x00cb
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r0 = r6.topMargin
            if (r0 != r4) goto L_0x00cb
            r6.topMargin = r3
            return
        L_0x00cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.PullToRefreshListProperty.updateMode(java.lang.Object):void");
    }

    private void startLoading(Object obj) {
        if (obj != null && this.mMode != null) {
            String lowerCase = ((String) obj).toLowerCase();
            char c = 65535;
            int hashCode = lowerCase.hashCode();
            if (hashCode != -1383228885) {
                if (hashCode == 115029 && lowerCase.equals("top")) {
                    c = 0;
                }
            } else if (lowerCase.equals("bottom")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    if ("top".equalsIgnoreCase(this.mMode) || "both".equalsIgnoreCase(this.mMode)) {
                        this.mRecyclerView.scrollToPosition(0);
                        ((PullToRefreshList) this.mView).setCurrentState(Mode.PULL_FROM_START);
                        ((PullToRefreshList) this.mView).setState(State.MANUAL_REFRESHING, false, true);
                        return;
                    }
                case 1:
                    if ("bottom".equalsIgnoreCase(this.mMode) || "both".equalsIgnoreCase(this.mMode)) {
                        ((PullToRefreshList) this.mView).setCurrentState(Mode.PULL_FROM_END);
                        ((PullToRefreshList) this.mView).setState(State.MANUAL_REFRESHING, false, true);
                        break;
                    }
            }
        }
    }

    private void stopLoading(Object obj) {
        if (obj != null) {
            String lowerCase = ((String) obj).toLowerCase();
            char c = 65535;
            int hashCode = lowerCase.hashCode();
            if (hashCode != -1383228885) {
                if (hashCode == 115029 && lowerCase.equals("top")) {
                    c = 0;
                }
            } else if (lowerCase.equals("bottom")) {
                c = 1;
            }
            switch (c) {
                case 0:
                    if (((PullToRefreshList) this.mView).getCurrentMode() == Mode.PULL_FROM_START) {
                        ((PullToRefreshList) this.mView).setState(State.RESET, false, true);
                        return;
                    }
                    break;
                case 1:
                    if (((PullToRefreshList) this.mView).getCurrentMode() == Mode.PULL_FROM_END) {
                        ((PullToRefreshList) this.mView).setState(State.RESET, false, true);
                        break;
                    }
                    break;
            }
        }
    }

    public void updateRefresh() {
        ((PullToRefreshList) this.mView).setOnRefreshListener(new OnRefreshListener<AjxList>() {
            public void onRefresh(PullToRefreshBase<AjxList> pullToRefreshBase, boolean z) {
                if (z) {
                    PullToRefreshListProperty.this.mAjxContext.invokeJsEvent(new Builder().setEventName("refresh").setNodeId(PullToRefreshListProperty.this.getNodeId()).addAttribute("type", "top").addContent("type", "top").build());
                }
            }
        });
        ((PullToRefreshList) this.mView).setOnLoadMoreListener(new OnLoadMoreListener<AjxList>() {
            public void onLoadMore(PullToRefreshBase<AjxList> pullToRefreshBase, boolean z) {
                if (z) {
                    PullToRefreshListProperty.this.mAjxContext.invokeJsEvent(new Builder().setEventName("refresh").setNodeId(PullToRefreshListProperty.this.getNodeId()).addContent("type", "bottom").addAttribute("type", "bottom").build());
                }
            }
        });
    }
}

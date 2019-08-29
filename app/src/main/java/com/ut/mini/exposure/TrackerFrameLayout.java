package com.ut.mini.exposure;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.alibaba.analytics.utils.MapUtils;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders.UTCustomHitBuilder;
import com.ut.mini.UTPageHitHelper;
import com.ut.mini.UTPageHitHelper.PageChangeListener;
import com.ut.mini.UTTracker;
import com.ut.mini.internal.ExposureViewHandle;
import com.ut.mini.internal.IExposureViewHandleExt;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class TrackerFrameLayout extends FrameLayout implements OnGestureListener {
    private static final float CLICK_LIMIT = 20.0f;
    private static final Object HasExposrueObjectLock = new Object();
    private static final String TAG = "TrackerFrameLayout";
    public static long TIME_INTERVAL = 100;
    public static final int TRIGGER_VIEW_CHANGED = 0;
    public static final int TRIGGER_VIEW_STATUS_CHANGED = 3;
    public static final int TRIGGER_WINDOW_CHANGED = 1;
    private static final int UT_EXPORSURE_MAX_LENGTH = 25600;
    private static final String UT_SCM_TAG = "scm";
    private static final String UT_SPM_TAG = "spm";
    private static final int eventId = 2201;
    private static HashMap<String, Object> mCommonInfo = new HashMap<>();
    private static HashMap<String, Integer> mHasExposrueDataLength = new HashMap<>();
    private static Map<String, ArrayList> mHasExposrueMap = Collections.synchronizedMap(new HashMap());
    private static HashMap<String, HashSet<String>> mHasExposureSet = new HashMap<>();
    /* access modifiers changed from: private */
    public static Map<String, HashSet<String>> mHasExposureViewTagSet = Collections.synchronizedMap(new HashMap());
    private static List<String> mImmediatelyCommitBlockList = new Vector();
    private Map<String, ExposureView> currentViews = new ConcurrentHashMap();
    private long lastDispatchDrawSystemTimeMillis = 0;
    private Rect mGlobalVisibleRect = new Rect();
    private float mOriX = 0.0f;
    private float mOriY = 0.0f;
    private Runnable traceTask = new Runnable() {
        public void run() {
            TrackerFrameLayout.this.trace(0, true);
        }
    };
    private long traverseTime;

    static class ExposureEntity implements Serializable {
        public double area;
        public long duration = 0;
        public Map<String, String> exargs;
        public String scm;
        public String spm;
        public String viewid;

        public ExposureEntity(String str, String str2, Map map, long j, double d, String str3) {
            this.spm = str;
            this.scm = str2;
            this.exargs = map;
            this.duration = j;
            this.area = d;
            this.viewid = str3;
        }

        public int length() {
            int i = 0;
            if (this.spm != null) {
                i = 0 + this.spm.length() + 8;
            }
            if (this.scm != null) {
                i += this.scm.length() + 8;
            }
            if (this.exargs != null) {
                for (String next : this.exargs.keySet()) {
                    if (next != null) {
                        i += next.length();
                    }
                    String str = this.exargs.get(next);
                    if (str != null) {
                        i += str.toString().length();
                    }
                    i += 5;
                }
            }
            if (this.viewid != null) {
                i += this.viewid.length() + 11;
            }
            return i + 50;
        }
    }

    static class PageChangerMonitor implements PageChangeListener {
        PageChangerMonitor() {
        }

        public void onPageAppear(Object obj) {
            Object obj2;
            TrackerFrameLayout.mHasExposureViewTagSet.clear();
            if (obj instanceof Activity) {
                try {
                    obj2 = ((Activity) obj).findViewById(16908290);
                } catch (Exception e) {
                    ExpLogger.e(TrackerFrameLayout.TAG, e, new Object[0]);
                    obj2 = null;
                }
                if (obj2 instanceof ViewGroup) {
                    View childAt = ((ViewGroup) obj2).getChildAt(0);
                    if (childAt instanceof TrackerFrameLayout) {
                        ((TrackerFrameLayout) childAt).trace(1, true);
                        return;
                    }
                    ExpLogger.w(TrackerFrameLayout.TAG, "cannot found the trace view", childAt);
                    return;
                }
                ExpLogger.w(TrackerFrameLayout.TAG, "contentView", obj2);
            }
        }

        public void onPageDisAppear(Object obj) {
            Object obj2;
            if (obj instanceof Activity) {
                try {
                    obj2 = ((Activity) obj).findViewById(16908290);
                } catch (Exception e) {
                    ExpLogger.e(TrackerFrameLayout.TAG, e, new Object[0]);
                    obj2 = null;
                }
                if (obj2 instanceof ViewGroup) {
                    View childAt = ((ViewGroup) obj2).getChildAt(0);
                    if (childAt instanceof TrackerFrameLayout) {
                        ((TrackerFrameLayout) childAt).onPageDisAppear();
                        return;
                    }
                    ExpLogger.w(TrackerFrameLayout.TAG, "cannot found the trace view ", childAt);
                    return;
                }
                ExpLogger.w(TrackerFrameLayout.TAG, "contentView", obj2);
            }
        }
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    static {
        UTPageHitHelper.addPageChangerListener(new PageChangerMonitor());
    }

    public TrackerFrameLayout(Context context) {
        super(context);
        addCommonArgsInfo();
        ExposureConfigMgr.updateExposureConfig();
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (ExpLogger.enableLog) {
            ExpLogger.d(TAG, "action:", Integer.valueOf(motionEvent.getAction()));
        }
        switch (motionEvent.getAction()) {
            case 0:
                this.mOriX = motionEvent.getX();
                this.mOriY = motionEvent.getY();
                break;
            case 1:
                Handler threadHandle = TrackerManager.getInstance().getThreadHandle();
                if (threadHandle != null) {
                    threadHandle.removeCallbacks(this.traceTask);
                    threadHandle.postDelayed(this.traceTask, 1000);
                    break;
                }
                break;
            case 2:
                if (Math.abs(motionEvent.getX() - this.mOriX) <= 20.0f && Math.abs(motionEvent.getY() - this.mOriY) <= 20.0f) {
                    ExpLogger.d(TAG, "onInterceptTouchEvent ACTION_MOVE but not in click limit");
                    break;
                } else {
                    long currentTimeMillis = System.currentTimeMillis();
                    ExpLogger.d(TAG, " begin");
                    trace(0, false);
                    if (ExpLogger.enableLog) {
                        StringBuilder sb = new StringBuilder("end costTime=");
                        sb.append(System.currentTimeMillis() - currentTimeMillis);
                        sb.append("--\n");
                        ExpLogger.d(TAG, sb.toString());
                        break;
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (ExpLogger.enableLog) {
            ExpLogger.d(TAG, "action:", Integer.valueOf(motionEvent.getAction()));
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ExpLogger.d(TAG, "begin");
        long currentTimeMillis = System.currentTimeMillis();
        trace(0, false);
        if (ExpLogger.enableLog) {
            StringBuilder sb = new StringBuilder("end costTime=");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            sb.append("--");
            ExpLogger.d(TAG, sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        ExpLogger.d(TAG, "dispatchDraw");
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.lastDispatchDrawSystemTimeMillis > 1000) {
            this.lastDispatchDrawSystemTimeMillis = currentTimeMillis;
            addCommonArgsInfo();
        }
        super.dispatchDraw(canvas);
    }

    public void dispatchWindowFocusChanged(boolean z) {
        ExpLogger.d(TAG, "begin");
        long currentTimeMillis = System.currentTimeMillis();
        trace(1, false);
        if (ExpLogger.enableLog) {
            StringBuilder sb = new StringBuilder("end");
            sb.append(System.currentTimeMillis() - currentTimeMillis);
            sb.append("--");
            ExpLogger.d(TAG, sb.toString());
        }
        super.dispatchWindowFocusChanged(z);
    }

    /* access modifiers changed from: protected */
    public void dispatchVisibilityChanged(View view, int i) {
        if (i == 8) {
            ExpLogger.d(TAG, "begin");
            long currentTimeMillis = System.currentTimeMillis();
            trace(1, false);
            if (ExpLogger.enableLog) {
                StringBuilder sb = new StringBuilder("end costTime=");
                sb.append(System.currentTimeMillis() - currentTimeMillis);
                sb.append("--");
                ExpLogger.d(TAG, sb.toString());
            }
        } else {
            ExpLogger.d(TAG, "visibility", Integer.valueOf(i));
        }
        super.dispatchVisibilityChanged(view, i);
    }

    @TargetApi(4)
    private void addCommonArgsInfo() {
        if (getContext() != null && (getContext() instanceof Activity)) {
            View decorView = ((Activity) getContext()).getWindow().getDecorView();
            mCommonInfo.clear();
            HashMap<String, String> hashMap = TrackerManager.getInstance().commonInfoMap;
            if (hashMap != null) {
                mCommonInfo.putAll(hashMap);
            }
            HashMap hashMap2 = (HashMap) decorView.getTag(ExposureUtils.ut_exprosure_common_info_tag);
            if (hashMap2 != null && !hashMap2.isEmpty()) {
                mCommonInfo.putAll(hashMap2);
                ExpLogger.d(TAG, "addCommonArgsInfo mCommonInfo ", hashMap2);
            }
            ExpLogger.d(TAG, "addCommonArgsInfo all mCommonInfo ", hashMap2);
        }
    }

    /* access modifiers changed from: private */
    public void trace(int i, boolean z) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (z || currentTimeMillis - this.traverseTime >= TIME_INTERVAL) {
                ExpLogger.d(TAG, "扫描开始");
                this.traverseTime = currentTimeMillis;
                traverseViewTree(this);
                checkViewsStates(i);
                if (ExpLogger.enableLog) {
                    StringBuilder sb = new StringBuilder("扫描结束，耗时:");
                    sb.append(System.currentTimeMillis() - currentTimeMillis);
                    ExpLogger.d(TAG, sb.toString());
                }
                return;
            }
            if (ExpLogger.enableLog) {
                StringBuilder sb2 = new StringBuilder("triggerTime interval is too close to ");
                sb2.append(TIME_INTERVAL);
                sb2.append(RPCDataParser.TIME_MS);
                ExpLogger.d(TAG, sb2.toString());
            }
        } catch (Throwable th) {
            ExpLogger.e(TAG, th, new Object[0]);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x00be  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x016a  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x01be  */
    @android.annotation.TargetApi(4)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void traverseViewTree(android.view.View r14) {
        /*
            r13 = this;
            if (r14 != 0) goto L_0x0003
            return
        L_0x0003:
            boolean r0 = r14.isShown()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0017
            java.lang.String r14 = "TrackerFrameLayout"
            java.lang.Object[] r0 = new java.lang.Object[r2]
            java.lang.String r2 = "view invisalbe,return"
            r0[r1] = r2
            com.ut.mini.exposure.ExpLogger.d(r14, r0)
            return
        L_0x0017:
            boolean r0 = com.ut.mini.exposure.ExposureUtils.isIngoneExposureView(r14)
            r3 = 2
            if (r0 == 0) goto L_0x002c
            java.lang.String r0 = "TrackerFrameLayout"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r4 = "view ingone by user,return. view:"
            r3[r1] = r4
            r3[r2] = r14
            com.ut.mini.exposure.ExpLogger.d(r0, r3)
            return
        L_0x002c:
            boolean r0 = com.ut.mini.exposure.ExposureUtils.isExposureViewForWeex(r14)
            r4 = 0
            r5 = 4
            r6 = 3
            if (r0 == 0) goto L_0x00b6
            android.content.Context r0 = r14.getContext()
            com.ut.mini.exposure.TrackerManager r7 = com.ut.mini.exposure.TrackerManager.getInstance()
            com.ut.mini.internal.ExposureViewHandle r7 = r7.getExposureViewHandle()
            if (r7 == 0) goto L_0x00b6
            if (r0 == 0) goto L_0x0065
            boolean r8 = r0 instanceof android.app.Activity
            if (r8 == 0) goto L_0x0065
            com.ut.mini.UTPageHitHelper r8 = com.ut.mini.UTPageHitHelper.getInstance()
            java.lang.String r8 = r8.getPageUrl(r0)
            boolean r9 = android.text.TextUtils.isEmpty(r8)
            if (r9 == 0) goto L_0x0066
            java.lang.String r9 = "TrackerFrameLayout"
            java.lang.Object[] r10 = new java.lang.Object[r3]
            java.lang.String r11 = "Cannot get Current Page Url"
            r10[r1] = r11
            r10[r2] = r0
            com.ut.mini.exposure.ExpLogger.w(r9, r10)
            goto L_0x0066
        L_0x0065:
            r8 = r4
        L_0x0066:
            com.ut.mini.internal.ExposureViewTag r0 = r7.getExposureViewTag(r8, r14)
            if (r0 == 0) goto L_0x00a0
            java.lang.String r7 = r0.block
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x0081
            java.lang.String r7 = r0.viewId
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 != 0) goto L_0x0081
            java.lang.String r7 = r0.block
            java.lang.String r0 = r0.viewId
            goto L_0x00a2
        L_0x0081:
            boolean r0 = r0.notExposure
            if (r0 == 0) goto L_0x0095
            com.ut.mini.exposure.ExposureUtils.clearExposureForWeex(r14)
            java.lang.String r0 = "TrackerFrameLayout"
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.String r8 = "clear exposure tag. view"
            r7[r1] = r8
            r7[r2] = r14
            com.ut.mini.exposure.ExpLogger.w(r0, r7)
        L_0x0095:
            java.lang.String r0 = "TrackerFrameLayout"
            java.lang.Object[] r7 = new java.lang.Object[r2]
            java.lang.String r8 = "block or viewId is valid,plase check input params!"
            r7[r1] = r8
            com.ut.mini.exposure.ExpLogger.w(r0, r7)
        L_0x00a0:
            r0 = r4
            r7 = r0
        L_0x00a2:
            java.lang.String r8 = "TrackerFrameLayout"
            java.lang.Object[] r9 = new java.lang.Object[r5]
            java.lang.String r10 = "weex block"
            r9[r1] = r10
            r9[r2] = r7
            java.lang.String r10 = "viewId"
            r9[r3] = r10
            r9[r6] = r0
            com.ut.mini.exposure.ExpLogger.d(r8, r9)
            goto L_0x00b8
        L_0x00b6:
            r0 = r4
            r7 = r0
        L_0x00b8:
            boolean r8 = com.ut.mini.exposure.ExposureUtils.isExposureView(r14)
            if (r8 == 0) goto L_0x00f2
            r8 = -17001(0xffffffffffffbd97, float:NaN)
            java.lang.Object r8 = r14.getTag(r8)
            if (r8 == 0) goto L_0x00df
            boolean r9 = r8 instanceof java.util.Map
            if (r9 == 0) goto L_0x00df
            java.util.Map r8 = (java.util.Map) r8
            java.lang.String r0 = "UT_EXPROSURE_BLOCK"
            java.lang.Object r0 = r8.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r4 = "UT_EXPROSURE_VIEWID"
            java.lang.Object r4 = r8.get(r4)
            java.lang.String r4 = (java.lang.String) r4
            r7 = r0
            r0 = r4
            r4 = r8
        L_0x00df:
            java.lang.String r8 = "TrackerFrameLayout"
            java.lang.Object[] r9 = new java.lang.Object[r5]
            java.lang.String r10 = "native block"
            r9[r1] = r10
            r9[r2] = r7
            java.lang.String r10 = "viewId"
            r9[r3] = r10
            r9[r6] = r0
            com.ut.mini.exposure.ExpLogger.d(r8, r9)
        L_0x00f2:
            boolean r8 = android.text.TextUtils.isEmpty(r7)
            if (r8 != 0) goto L_0x0205
            boolean r8 = android.text.TextUtils.isEmpty(r0)
            if (r8 != 0) goto L_0x0205
            java.util.Map<java.lang.String, java.util.HashSet<java.lang.String>> r8 = mHasExposureViewTagSet
            java.lang.Object r8 = r8.get(r7)
            java.util.HashSet r8 = (java.util.HashSet) r8
            if (r8 != 0) goto L_0x010d
            java.util.HashSet r8 = new java.util.HashSet
            r8.<init>()
        L_0x010d:
            r8.add(r0)
            java.util.Map<java.lang.String, java.util.HashSet<java.lang.String>> r9 = mHasExposureViewTagSet
            r9.put(r7, r8)
            java.util.Map<java.lang.String, com.ut.mini.exposure.ExposureView> r8 = r13.currentViews
            int r9 = r14.hashCode()
            java.lang.String r9 = java.lang.String.valueOf(r9)
            java.lang.Object r8 = r8.get(r9)
            com.ut.mini.exposure.ExposureView r8 = (com.ut.mini.exposure.ExposureView) r8
            r9 = 5
            r10 = 6
            if (r8 == 0) goto L_0x016a
            java.lang.String r11 = r8.tag
            boolean r11 = r0.equalsIgnoreCase(r11)
            if (r11 == 0) goto L_0x014b
            boolean r11 = r8.isSatisfyTimeRequired()
            if (r11 != 0) goto L_0x014b
            java.lang.String r14 = "TrackerFrameLayout"
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.lang.String r5 = "this view has existed block"
            r4[r1] = r5
            r4[r2] = r7
            java.lang.String r1 = "viewId"
            r4[r3] = r1
            r4[r6] = r0
            com.ut.mini.exposure.ExpLogger.d(r14, r4)
            return
        L_0x014b:
            java.lang.String r11 = "TrackerFrameLayout"
            java.lang.Object[] r10 = new java.lang.Object[r10]
            java.lang.String r12 = "this view status has change or time > timeThreshold, block"
            r10[r1] = r12
            r10[r2] = r7
            java.lang.String r12 = " new viewId"
            r10[r3] = r12
            r10[r6] = r0
            java.lang.String r12 = "old viewId"
            r10[r5] = r12
            java.lang.String r12 = r8.tag
            r10[r9] = r12
            com.ut.mini.exposure.ExpLogger.d(r11, r10)
            r13.checkViewState(r6, r8)
            goto L_0x01a4
        L_0x016a:
            java.util.Map<java.lang.String, com.ut.mini.exposure.ExposureView> r8 = r13.currentViews
            java.util.Collection r8 = r8.values()
            java.util.Iterator r8 = r8.iterator()
        L_0x0174:
            boolean r11 = r8.hasNext()
            if (r11 == 0) goto L_0x01a4
            java.lang.Object r11 = r8.next()
            com.ut.mini.exposure.ExposureView r11 = (com.ut.mini.exposure.ExposureView) r11
            java.lang.String r12 = r11.tag
            boolean r12 = r0.equalsIgnoreCase(r12)
            if (r12 == 0) goto L_0x0174
            java.lang.String r4 = "TrackerFrameLayout"
            java.lang.Object[] r7 = new java.lang.Object[r10]
            java.lang.String r8 = "this viewId has existed current view:"
            r7[r1] = r8
            r7[r2] = r14
            java.lang.String r14 = "oldView:"
            r7[r3] = r14
            android.view.View r14 = r11.view
            r7[r6] = r14
            java.lang.String r14 = "viewId"
            r7[r5] = r14
            r7[r9] = r0
            com.ut.mini.exposure.ExpLogger.d(r4, r7)
            return
        L_0x01a4:
            boolean r8 = r13.isExposured(r7, r0)
            if (r8 == 0) goto L_0x01be
            java.lang.String r14 = "TrackerFrameLayout"
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.lang.String r5 = "this view has exposured block"
            r4[r1] = r5
            r4[r2] = r7
            java.lang.String r1 = "viewId"
            r4[r3] = r1
            r4[r6] = r0
            com.ut.mini.exposure.ExpLogger.d(r14, r4)
            return
        L_0x01be:
            double r5 = r13.viewSize(r14)
            double r8 = com.ut.mini.exposure.ExposureConfigMgr.dimThreshold
            int r8 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r8 < 0) goto L_0x01f8
            long r8 = java.lang.System.currentTimeMillis()
            com.ut.mini.exposure.ExposureView r10 = new com.ut.mini.exposure.ExposureView
            r10.<init>(r14)
            r10.beginTime = r8
            r10.tag = r0
            r10.block = r7
            r10.viewData = r4
            r10.lastCalTime = r8
            r10.area = r5
            java.util.Map<java.lang.String, com.ut.mini.exposure.ExposureView> r4 = r13.currentViews
            int r5 = r14.hashCode()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r4.put(r5, r10)
            java.lang.String r4 = "TrackerFrameLayout"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "找到元素"
            r3[r1] = r5
            r3[r2] = r0
            com.ut.mini.exposure.ExpLogger.d(r4, r3)
            goto L_0x0205
        L_0x01f8:
            java.lang.String r4 = "TrackerFrameLayout"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "找到元素,但不满足曝光条件"
            r3[r1] = r5
            r3[r2] = r0
            com.ut.mini.exposure.ExpLogger.d(r4, r3)
        L_0x0205:
            boolean r0 = r14 instanceof android.view.ViewGroup
            if (r0 == 0) goto L_0x021b
            android.view.ViewGroup r14 = (android.view.ViewGroup) r14
            int r0 = r14.getChildCount()
        L_0x020f:
            if (r1 >= r0) goto L_0x021b
            android.view.View r2 = r14.getChildAt(r1)
            r13.traverseViewTree(r2)
            int r1 = r1 + 1
            goto L_0x020f
        L_0x021b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.exposure.TrackerFrameLayout.traverseViewTree(android.view.View):void");
    }

    private void checkViewsStates(int i) {
        if (this.currentViews != null && this.currentViews.size() > 0) {
            for (Entry<String, ExposureView> value : this.currentViews.entrySet()) {
                checkViewState(i, this.currentViews.get(String.valueOf(((ExposureView) value.getValue()).view.hashCode())));
            }
        }
    }

    private void checkViewState(int i, ExposureView exposureView) {
        if (!isVisableToUser(exposureView.view)) {
            switch (exposureView.lastState) {
                case 1:
                    exposureView.lastState = 2;
                    exposureView.endTime = System.currentTimeMillis();
                    break;
            }
        } else {
            switch (exposureView.lastState) {
                case 0:
                    exposureView.lastState = 1;
                    exposureView.beginTime = System.currentTimeMillis();
                    break;
                case 1:
                    if (i == 1 || i == 3) {
                        exposureView.lastState = 2;
                        exposureView.endTime = System.currentTimeMillis();
                        break;
                    }
                case 2:
                    exposureView.lastState = 1;
                    exposureView.beginTime = System.currentTimeMillis();
                    break;
            }
        }
        if (exposureView.isSatisfyTimeRequired()) {
            addToCommit(exposureView);
            this.currentViews.remove(String.valueOf(exposureView.view.hashCode()));
            return;
        }
        if (exposureView.lastState == 2) {
            this.currentViews.remove(String.valueOf(exposureView.view.hashCode()));
            ExpLogger.d(TAG, "时间不满足，元素", exposureView.tag);
        }
    }

    private boolean isVisableToUser(View view) {
        return viewSize(view) >= ExposureConfigMgr.dimThreshold;
    }

    private double viewSize(View view) {
        int width = view.getWidth() * view.getHeight();
        if (!view.getGlobalVisibleRect(this.mGlobalVisibleRect) || width <= 0) {
            return 0.0d;
        }
        return (((double) (this.mGlobalVisibleRect.width() * this.mGlobalVisibleRect.height())) * 1.0d) / ((double) width);
    }

    private boolean isExposured(String str, String str2) {
        Set set = mHasExposureSet.get(str);
        if (set == null) {
            return false;
        }
        return set.contains(str2);
    }

    private void setExposuredTag(String str, String str2) {
        HashSet hashSet = mHasExposureSet.get(str);
        if (hashSet == null) {
            hashSet = new HashSet();
            mHasExposureSet.put(str, hashSet);
        }
        hashSet.add(str2);
    }

    /* JADX INFO: finally extract failed */
    private void addToCommit(ExposureView exposureView) {
        HashMap hashMap;
        ExposureView exposureView2 = exposureView;
        String str = exposureView2.block;
        String str2 = exposureView2.tag;
        setExposuredTag(str, str2);
        Map<String, Object> map = exposureView2.viewData;
        HashMap hashMap2 = new HashMap();
        ExposureViewHandle exposureViewHandle = TrackerManager.getInstance().getExposureViewHandle();
        if (exposureViewHandle != null) {
            String str3 = null;
            Context context = exposureView2.view.getContext();
            if (context != null) {
                str3 = UTPageHitHelper.getInstance().getPageUrl(context);
            }
            Map<String, String> exposureViewProperties = exposureViewHandle.getExposureViewProperties(str3, exposureView2.view);
            if (exposureViewProperties != null) {
                hashMap2.putAll(exposureViewProperties);
            }
        }
        if (!(map == null || map.get("UT_EXPROSURE_ARGS") == null)) {
            Map map2 = (Map) map.get("UT_EXPROSURE_ARGS");
            if (map2.size() > 0) {
                hashMap2.putAll(map2);
            }
        }
        String str4 = (String) hashMap2.remove(UT_SPM_TAG);
        String str5 = (String) hashMap2.remove("scm");
        synchronized (HasExposrueObjectLock) {
            try {
                ArrayList arrayList = mHasExposrueMap.get(str);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    mHasExposrueMap.put(str, arrayList);
                }
                HashMap hashMap3 = hashMap2;
                ExposureEntity exposureEntity = r3;
                hashMap = hashMap2;
                ExposureEntity exposureEntity2 = new ExposureEntity(str4, str5, hashMap3, System.currentTimeMillis() - exposureView2.beginTime, exposureView2.area, str2);
                arrayList.add(exposureEntity);
                Integer num = mHasExposrueDataLength.get(str);
                if (num == null) {
                    num = Integer.valueOf(0);
                }
                Integer valueOf = Integer.valueOf(num.intValue() + exposureEntity.length());
                mHasExposrueDataLength.put(str, valueOf);
                if (valueOf.intValue() > UT_EXPORSURE_MAX_LENGTH) {
                    commitToUT(str, mCommonInfo);
                } else if (mImmediatelyCommitBlockList.contains(str)) {
                    commitToUT(str, mCommonInfo);
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        ExpLogger.d(TAG, "提交元素viewId ", exposureView2.tag, "block", str, UT_SPM_TAG, str4, "scm", str5, "args", hashMap);
    }

    private static void commitToUT(String str, HashMap<String, Object> hashMap) {
        ExpLogger.d();
        ArrayList remove = mHasExposrueMap.remove(str);
        HashMap hashMap2 = new HashMap();
        if (hashMap != null && hashMap.size() > 0) {
            hashMap2.putAll(MapUtils.convertObjectMapToStringMap(hashMap));
        }
        hashMap2.put("expdata", getExpData(remove));
        UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(UTPageHitHelper.getInstance().getCurrentPageName(), 2201, str, null, null, hashMap2);
        UTAnalytics.getInstance().getDefaultTracker().send(uTOriginalCustomHitBuilder.build());
        mHasExposrueDataLength.remove(str);
    }

    private static String getExpData(ArrayList<ExposureEntity> arrayList) {
        JSONArray jSONArray = new JSONArray();
        jSONArray.addAll(arrayList);
        return jSONArray.toJSONString();
    }

    public void onPageDisAppear() {
        Handler threadHandle = TrackerManager.getInstance().getThreadHandle();
        if (threadHandle != null) {
            threadHandle.removeCallbacks(this.traceTask);
        }
        trace(1, true);
        commitExposureData();
        try {
            Object[] array = mHasExposureViewTagSet.keySet().toArray();
            if (array.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (Object obj : array) {
                    sb.append(mHasExposureViewTagSet.get(obj));
                    sb.append(",");
                }
                UTCustomHitBuilder uTCustomHitBuilder = new UTCustomHitBuilder("ut_exposure_test");
                uTCustomHitBuilder.setProperty("viewids", sb.toString().replaceAll("]", "").replaceAll("\\[", ""));
                UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
                if (defaultTracker != null) {
                    defaultTracker.send(uTCustomHitBuilder.build());
                }
            }
        } catch (Throwable th) {
            mHasExposureViewTagSet.clear();
            throw th;
        }
        mHasExposureViewTagSet.clear();
        mImmediatelyCommitBlockList.clear();
        this.currentViews.clear();
        if (!ExposureConfigMgr.notClearTagAfterDisAppear) {
            mHasExposureSet.clear();
        }
        ExposureViewHandle exposureViewHandle = TrackerManager.getInstance().getExposureViewHandle();
        if (exposureViewHandle instanceof IExposureViewHandleExt) {
            ((IExposureViewHandleExt) exposureViewHandle).onExposureDataCleared();
        }
    }

    public static void refreshExposureData() {
        mHasExposureSet.clear();
        mHasExposureViewTagSet.clear();
    }

    public static void refreshExposureData(String str) {
        ExpLogger.d(TAG, "[refreshExposureData]block", str);
        if (!TextUtils.isEmpty(str)) {
            mHasExposureSet.remove(str);
        }
    }

    public static void refreshExposureDataByViewId(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            Set set = mHasExposureSet.get(str);
            if (set != null) {
                set.remove(str2);
            }
        }
    }

    public static void commitExposureData() {
        synchronized (HasExposrueObjectLock) {
            Object[] objArr = null;
            try {
                objArr = mHasExposrueMap.keySet().toArray();
            } catch (Throwable unused) {
            }
            if (objArr != null) {
                if (objArr.length > 0) {
                    for (Object append : objArr) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(append);
                        commitToUT(sb.toString(), mCommonInfo);
                    }
                }
            }
            mHasExposrueMap.clear();
        }
    }

    public static void setCommitImmediatelyExposureBlock(String str) {
        mImmediatelyCommitBlockList.add(str);
    }
}

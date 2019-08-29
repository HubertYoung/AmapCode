package com.autonavi.bundle.uitemplate.ajx;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.container.SlideContainer;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.c;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.MapLayerMapWidget;
import com.autonavi.bundle.uitemplate.page.AbstractSlidablePage;
import com.autonavi.bundle.uitemplate.receiver.TripToolSelectReceiver;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.uc.webview.export.extension.UCCore;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("slidecontainer")
public class ModuleSlideContainer extends AbstractModule {
    public static final String MODULE_NAME = "slidecontainer";
    private static final String mode_fixed = "mode_fixed";
    private static final String mode_slide = "mode_slide";
    private static final String mode_upglide = "mode_upglide";
    private static final String state_anchored = "state_anchored";
    private static final String state_dragging = "state_dragging";
    private static final String state_hidden = "state_hidden";
    private static final String state_maxheight = "state_maxheight";
    private static final String state_minheight = "state_minheight";
    private static String tipStateChange = "";
    private boolean LogSwitch = false;
    private a mBottomNavigationBarChangeListener;
    /* access modifiers changed from: private */
    public JsFunctionCallback mContainerListenerCallback = null;
    private JsFunctionCallback mHideToolBoxTipCallback;
    /* access modifiers changed from: private */
    public JsFunctionCallback mIndividualityChangedListener = null;
    /* access modifiers changed from: private */
    public PanelState mLastStableState = null;
    com.autonavi.bundle.uitemplate.container.SlideContainer.a mPageStateListener = new com.autonavi.bundle.uitemplate.container.SlideContainer.a() {
        public final void a(boolean z) {
            ModuleSlideContainer.this.logFormat("onPageShow. appSwitch: %s", Boolean.valueOf(z));
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("app_switch", z ? 1 : 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String access$100 = ModuleSlideContainer.this.getContainerListenerString("container_show", jSONObject);
            if (ModuleSlideContainer.this.mContainerListenerCallback != null) {
                ModuleSlideContainer.this.mContainerListenerCallback.callback(access$100);
            }
        }

        public final void b(boolean z) {
            ModuleSlideContainer.this.logFormat("onPageHide. appSwitch: %s", Boolean.valueOf(z));
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("app_switch", z ? 1 : 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String access$100 = ModuleSlideContainer.this.getContainerListenerString("container_hide", jSONObject);
            if (ModuleSlideContainer.this.mContainerListenerCallback != null) {
                ModuleSlideContainer.this.mContainerListenerCallback.callback(access$100);
            }
        }
    };
    c mPanelSlideListener = new c() {
        public final void a(View view, float f) {
            ModuleSlideContainer.this.logFormat("onPanelSlide. view height: %s, offset: %s", Integer.valueOf(view.getMeasuredHeight()), Float.valueOf(f));
            ModuleSlideContainer.this.logContainerVisibleInfo("onPanelSlide");
            int containerVisibleHeight = ModuleSlideContainer.this.getContainerVisibleHeight();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("height", containerVisibleHeight);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String access$100 = ModuleSlideContainer.this.getContainerListenerString("slide", jSONObject);
            if (ModuleSlideContainer.this.mContainerListenerCallback != null) {
                ModuleSlideContainer.this.mContainerListenerCallback.callback(access$100);
            }
            if (ModuleSlideContainer.this.mRelativeAnimationAjxView != null) {
                AjxView ajxView = (AjxView) ModuleSlideContainer.this.mRelativeAnimationAjxView.get();
                if (ajxView != null) {
                    ajxView.setAttribute("Container_Height", Integer.valueOf(containerVisibleHeight), false, true, false, true);
                }
            }
        }

        public final void a(View view, PanelState panelState, PanelState panelState2) {
            ModuleSlideContainer.this.logFormat("onPanelStateChanged. view height: %s, previousState: %s, newState: %s", Integer.valueOf(view.getMeasuredHeight()), panelState, panelState2);
            ModuleSlideContainer.this.logContainerVisibleInfo("onPanelStateChanged");
            if (panelState2 != PanelState.DRAGGING) {
                PanelState access$500 = ModuleSlideContainer.this.mLastStableState;
                ModuleSlideContainer.this.mLastStableState = panelState2;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("new_state", ModuleSlideContainer.state2String(panelState2));
                    jSONObject.put("old_state", ModuleSlideContainer.state2String(access$500));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String access$100 = ModuleSlideContainer.this.getContainerListenerString("state_changed", jSONObject);
                if (ModuleSlideContainer.this.mContainerListenerCallback != null) {
                    ModuleSlideContainer.this.mContainerListenerCallback.callback(access$100);
                }
                int containerVisibleHeight = ModuleSlideContainer.this.getContainerVisibleHeight();
                if (ModuleSlideContainer.this.mRelativeAnimationAjxView != null) {
                    AjxView ajxView = (AjxView) ModuleSlideContainer.this.mRelativeAnimationAjxView.get();
                    if (ajxView != null) {
                        ajxView.setAttribute("Container_Height", Integer.valueOf(containerVisibleHeight), false, true, false, true);
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public WeakReference<AjxView> mRelativeAnimationAjxView = null;
    /* access modifiers changed from: private */
    public SlideContainer mSlideContainer;
    private b mTipStateChangeListener;

    class a implements defpackage.euj.a {
        a() {
        }

        public final void a(boolean z) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("isBottomNavigationBarShow", z);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String access$100 = ModuleSlideContainer.this.getContainerListenerString("BottomNavigationBarChange", jSONObject);
            if (ModuleSlideContainer.this.mContainerListenerCallback != null) {
                ModuleSlideContainer.this.mContainerListenerCallback.callback(access$100);
            }
        }
    }

    public interface b {
        void a(boolean z);
    }

    public void setTipStateChangeListener(b bVar) {
        this.mTipStateChangeListener = bVar;
    }

    public ModuleSlideContainer(IAjxContext iAjxContext) {
        super(iAjxContext);
        if (euj.a(AMapAppGlobal.getTopActivity())) {
            this.mBottomNavigationBarChangeListener = new a();
            euj.a((defpackage.euj.a) this.mBottomNavigationBarChangeListener);
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        detachContainer();
        detachRelativeAnimationAjxView();
        setContainerListener(null);
        if (this.mBottomNavigationBarChangeListener != null) {
            euj.b((defpackage.euj.a) this.mBottomNavigationBarChangeListener);
        }
    }

    private int getAjxPixel(int i) {
        return Math.round(DimensionUtils.pixelToStandardUnit(((float) i) * 1.0f));
    }

    /* access modifiers changed from: private */
    public void logContainerVisibleInfo(String str) {
        if (isLog() && this.mSlideContainer != null && this.mSlideContainer.getContentView() != null) {
            Rect rect = new Rect();
            this.mSlideContainer.getContentView().getLocalVisibleRect(rect);
            Rect rect2 = new Rect();
            Point point = new Point();
            this.mSlideContainer.getContentView().getGlobalVisibleRect(rect2, point);
            logFormat("%s. localRect: %s %s %s %s, globalRect: %s %s %s %s, globalOffset: %s %s", str, Integer.valueOf(rect.left), Integer.valueOf(rect.top), Integer.valueOf(rect.right), Integer.valueOf(rect.bottom), Integer.valueOf(rect2.left), Integer.valueOf(rect2.top), Integer.valueOf(rect2.right), Integer.valueOf(rect2.bottom), Integer.valueOf(point.x), Integer.valueOf(point.y));
        }
    }

    /* access modifiers changed from: private */
    public String getContainerListenerString(String str, JSONObject jSONObject) {
        String str2;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("event_name", str);
            jSONObject2.put("event_data", jSONObject);
            str2 = jSONObject2.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        logFormat("getContainerListenerString. result: %s", str2);
        return str2;
    }

    public void attachRelativeAnimationAjxView(AjxView ajxView) {
        this.mRelativeAnimationAjxView = new WeakReference<>(ajxView);
    }

    public void detachRelativeAnimationAjxView() {
        this.mRelativeAnimationAjxView = null;
    }

    public void attachContainer(SlideContainer slideContainer) {
        if (this.mSlideContainer != slideContainer) {
            if (this.mSlideContainer != null) {
                this.mSlideContainer.removePanelSlideListener(this.mPanelSlideListener);
                this.mSlideContainer.removePageStateListener(this.mPageStateListener);
            }
            if (slideContainer != null && slideContainer.getMeasuredHeight() == 0) {
                slideContainer.measure(MeasureSpec.makeMeasureSpec(slideContainer.getResources().getDisplayMetrics().widthPixels, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(slideContainer.getResources().getDisplayMetrics().heightPixels, UCCore.VERIFY_POLICY_QUICK));
            }
            this.mSlideContainer = slideContainer;
            this.mSlideContainer.addPanelSlideListener(this.mPanelSlideListener);
            this.mSlideContainer.addPageStateListener(this.mPageStateListener);
            this.mLastStableState = getStableState();
        }
    }

    public void detachContainer() {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.removePanelSlideListener(this.mPanelSlideListener);
            this.mSlideContainer.removePageStateListener(this.mPageStateListener);
        }
        this.mSlideContainer = null;
    }

    @AjxMethod("registerIndividualityChangedListener")
    public void registerIndividualityChangedListener(JsFunctionCallback jsFunctionCallback) {
        this.mIndividualityChangedListener = jsFunctionCallback;
        TripToolSelectReceiver.a(new com.autonavi.bundle.uitemplate.receiver.TripToolSelectReceiver.a() {
            public final void a() {
                if (ModuleSlideContainer.this.mIndividualityChangedListener != null) {
                    ModuleSlideContainer.this.mIndividualityChangedListener.callback(new Object[0]);
                }
            }
        });
    }

    @AjxMethod("showCloseBtn")
    public void showCloseBtn(final boolean z) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.post(new Runnable() {
                public final void run() {
                    ModuleSlideContainer.this.mSlideContainer.setCloseButtonVisible(z);
                }
            });
        }
    }

    @AjxMethod("showSlideArrow")
    public void showSlideArrow(final boolean z) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.post(new Runnable() {
                public final void run() {
                    ModuleSlideContainer.this.mSlideContainer.setArrowVisible(z);
                }
            });
        }
    }

    @AjxMethod("showBottomSheetBackground")
    public void showBottomSheetBackground(boolean z) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.setCustomAttribute("bottom_sheet_show_background", Boolean.valueOf(z));
        }
    }

    @AjxMethod("setContainerListener")
    public void setContainerListener(JsFunctionCallback jsFunctionCallback) {
        this.mContainerListenerCallback = jsFunctionCallback;
    }

    @AjxMethod(invokeMode = "sync", value = "getContainerVisibleHeight")
    public int getContainerVisibleHeight() {
        if (this.mSlideContainer == null || this.mSlideContainer.getPanelState() == PanelState.HIDDEN || this.mSlideContainer.getContentView() == null) {
            return 0;
        }
        Rect rect = new Rect();
        this.mSlideContainer.getContentView().getLocalVisibleRect(rect);
        return getAjxPixel(rect.bottom - rect.top);
    }

    @AjxMethod(invokeMode = "sync", value = "requestTouchEvent")
    public void requestTouchEvent() {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.requestDisallowInterceptTouchEvent(true);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "rejectTouchEvent")
    public void rejectTouchEvent() {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.requestDisallowInterceptTouchEvent(false);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getContainerState")
    public String getContainerState() {
        return state2String(getStableState());
    }

    private PanelState getStableState() {
        if (this.mSlideContainer == null) {
            return this.mLastStableState;
        }
        PanelState panelState = this.mSlideContainer.getPanelState();
        if (panelState != PanelState.DRAGGING) {
            return panelState;
        }
        return this.mLastStableState;
    }

    @AjxMethod(invokeMode = "sync", value = "getContainerDefaultState")
    public String getContainerDefaultState() {
        PanelState panelState;
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext instanceof AbstractSlidablePage) {
            panelState = ((AbstractSlidablePage) pageContext).t;
        } else {
            panelState = null;
        }
        if (panelState == null) {
            panelState = PanelState.ANCHORED;
        }
        return state2String(panelState);
    }

    @AjxMethod("setContainerState")
    public void setContainerState(String str, boolean z) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.setPanelState(string2State(str), z);
        }
    }

    @AjxMethod("setContainerMinHeight")
    public void setContainerMinHeight(int i) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.setMinHeight(i);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getSafePaddingTop")
    public int getSafePaddingTop() {
        int i;
        int i2 = 0;
        if (this.mSlideContainer != null) {
            View headView = this.mSlideContainer.getHeadView();
            if (headView != null) {
                if (headView.getMeasuredHeight() == 0) {
                    LayoutParams layoutParams = headView.getLayoutParams();
                    if (layoutParams != null) {
                        headView.measure(layoutParams.width, layoutParams.height);
                    }
                }
                Context context = this.mSlideContainer.getContext();
                int measuredHeight = headView.getMeasuredHeight();
                if (context == null) {
                    i = 0;
                } else {
                    i = (int) ((((float) measuredHeight) / context.getResources().getDisplayMetrics().density) + 0.5f);
                }
                i2 = 0 + i;
            }
            i2 = i2 + 20 + 8;
        }
        return i2 * 2;
    }

    @AjxMethod("setContainerSlideMode")
    public void setContainerSlideMode(String str) {
        if (this.mSlideContainer != null) {
            this.mSlideContainer.setSlideMode(string2mode(str));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getContainerHeightForState")
    public int getContainerHeightForState(String str) {
        return getContainerHeightForState(string2State(str));
    }

    @AjxMethod("setSlidableHeight")
    public void setSlidableState(float f, float f2, float f3) {
        this.mSlideContainer.setMinHeight(DimensionUtils.standardUnitToPixel(f3));
        this.mSlideContainer.setAnchorHeight(DimensionUtils.standardUnitToPixel(f2));
        int height = this.mSlideContainer.getHeight() - DimensionUtils.standardUnitToPixel(f);
        if (height < 0) {
            height = 0;
        }
        this.mSlideContainer.setTransparentHeight(height);
        this.mSlideContainer.computeSlideRange();
        this.mSlideContainer.onMeasureComplete();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int getContainerHeightForState(com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState r4) {
        /*
            r3 = this;
            com.autonavi.bundle.uitemplate.container.SlideContainer r0 = r3.mSlideContainer
            r1 = 0
            if (r0 == 0) goto L_0x0054
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.ANCHORED
            r2 = 10
            if (r4 != r0) goto L_0x001d
            com.autonavi.bundle.uitemplate.container.SlideContainer r4 = r3.mSlideContainer
            int r4 = r4.getAnchorHeight()
            com.autonavi.bundle.uitemplate.container.SlideContainer r0 = r3.mSlideContainer
            android.content.Context r0 = r0.getContext()
            int r0 = defpackage.bet.a(r0, r2)
            int r4 = r4 - r0
            goto L_0x0055
        L_0x001d:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.EXPANDED
            if (r4 != r0) goto L_0x0033
            com.autonavi.bundle.uitemplate.container.SlideContainer r4 = r3.mSlideContainer
            int r4 = r4.getExpandHeight()
            com.autonavi.bundle.uitemplate.container.SlideContainer r0 = r3.mSlideContainer
            android.content.Context r0 = r0.getContext()
            int r0 = defpackage.bet.a(r0, r2)
            int r4 = r4 - r0
            goto L_0x0055
        L_0x0033:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.COLLAPSED
            if (r4 != r0) goto L_0x0049
            com.autonavi.bundle.uitemplate.container.SlideContainer r4 = r3.mSlideContainer
            int r4 = r4.getMinHeight()
            com.autonavi.bundle.uitemplate.container.SlideContainer r0 = r3.mSlideContainer
            android.content.Context r0 = r0.getContext()
            int r0 = defpackage.bet.a(r0, r2)
            int r4 = r4 - r0
            goto L_0x0055
        L_0x0049:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.HIDDEN
            if (r4 != r0) goto L_0x0054
            com.autonavi.bundle.uitemplate.container.SlideContainer r4 = r3.mSlideContainer
            int r4 = r4.getTransparentHeight()
            goto L_0x0055
        L_0x0054:
            r4 = 0
        L_0x0055:
            if (r4 >= 0) goto L_0x0058
            r4 = 0
        L_0x0058:
            int r4 = r3.getAjxPixel(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer.getContainerHeightForState(com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState):int");
    }

    /* access modifiers changed from: private */
    public static String state2String(PanelState panelState) {
        if (panelState == PanelState.ANCHORED) {
            return state_anchored;
        }
        if (panelState == PanelState.EXPANDED) {
            return state_maxheight;
        }
        if (panelState == PanelState.COLLAPSED) {
            return state_minheight;
        }
        if (panelState == PanelState.HIDDEN) {
            return state_hidden;
        }
        return state_dragging;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState string2State(java.lang.String r2) {
        /*
            int r0 = r2.hashCode()
            r1 = -129394339(0xfffffffff849995d, float:-1.635566E34)
            if (r0 == r1) goto L_0x003a
            r1 = -409320(0xfffffffffff9c118, float:NaN)
            if (r0 == r1) goto L_0x002f
            r1 = 409794050(0x186cf602, float:3.0626487E-24)
            if (r0 == r1) goto L_0x0024
            r1 = 643084235(0x2654afcb, float:7.379052E-16)
            if (r0 == r1) goto L_0x0019
            goto L_0x0045
        L_0x0019:
            java.lang.String r0 = "state_minheight"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0045
            r2 = 2
            goto L_0x0046
        L_0x0024:
            java.lang.String r0 = "state_anchored"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0045
            r2 = 0
            goto L_0x0046
        L_0x002f:
            java.lang.String r0 = "state_hidden"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0045
            r2 = 3
            goto L_0x0046
        L_0x003a:
            java.lang.String r0 = "state_maxheight"
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0045
            r2 = 1
            goto L_0x0046
        L_0x0045:
            r2 = -1
        L_0x0046:
            switch(r2) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0052;
                case 2: goto L_0x004f;
                case 3: goto L_0x004c;
                default: goto L_0x0049;
            }
        L_0x0049:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r2 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.DRAGGING
            goto L_0x0057
        L_0x004c:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r2 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.HIDDEN
            goto L_0x0057
        L_0x004f:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r2 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.COLLAPSED
            goto L_0x0057
        L_0x0052:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r2 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.EXPANDED
            goto L_0x0057
        L_0x0055:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState r2 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState.ANCHORED
        L_0x0057:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer.string2State(java.lang.String):com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$PanelState");
    }

    private static String mode2string(SlideMode slideMode) {
        if (slideMode == SlideMode.FIXED) {
            return mode_fixed;
        }
        if (slideMode == SlideMode.UPGLIDE) {
            return mode_upglide;
        }
        if (slideMode == SlideMode.SLIDE) {
            return mode_slide;
        }
        return mode_slide;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode string2mode(java.lang.String r3) {
        /*
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$SlideMode r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode.SLIDE
            int r1 = r3.hashCode()
            r2 = 1078102862(0x40428b4e, float:3.0397525)
            if (r1 == r2) goto L_0x002a
            r2 = 1739029976(0x67a77dd8, float:1.5819132E24)
            if (r1 == r2) goto L_0x0020
            r2 = 1751110677(0x685fd415, float:4.2279998E24)
            if (r1 == r2) goto L_0x0016
            goto L_0x0034
        L_0x0016:
            java.lang.String r1 = "mode_slide"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0034
            r3 = 2
            goto L_0x0035
        L_0x0020:
            java.lang.String r1 = "mode_fixed"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0034
            r3 = 0
            goto L_0x0035
        L_0x002a:
            java.lang.String r1 = "mode_upglide"
            boolean r3 = r3.equals(r1)
            if (r3 == 0) goto L_0x0034
            r3 = 1
            goto L_0x0035
        L_0x0034:
            r3 = -1
        L_0x0035:
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x003c;
                case 2: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x0041
        L_0x0039:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$SlideMode r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode.SLIDE
            goto L_0x0041
        L_0x003c:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$SlideMode r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode.UPGLIDE
            goto L_0x0041
        L_0x003f:
            com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$SlideMode r0 = com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.SlideMode.FIXED
        L_0x0041:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer.string2mode(java.lang.String):com.autonavi.bundle.uitemplate.container.internal.SlidableLayout$SlideMode");
    }

    @AjxMethod("tipStateChange")
    public void tipStateChange(String str) {
        if (!tipStateChange.equals(str) && this.mTipStateChangeListener != null) {
            if ("1".equals(str)) {
                this.mTipStateChangeListener.a(true);
            } else if ("0".equals(str)) {
                this.mTipStateChangeListener.a(false);
            }
        }
        tipStateChange = str;
        LayerWidgetPresenter layerWidgetPresenter = (LayerWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.LAYER);
        if (layerWidgetPresenter != null && layerWidgetPresenter.getWidget() != null) {
            ((MapLayerMapWidget) layerWidgetPresenter.getWidget()).hideTip();
        }
    }

    @AjxMethod("registerHideToolBoxTip")
    public void registerHideToolBoxTip(JsFunctionCallback jsFunctionCallback) {
        this.mHideToolBoxTipCallback = jsFunctionCallback;
    }

    public void hideToolBoxTipCallback() {
        if (this.mHideToolBoxTipCallback != null) {
            this.mHideToolBoxTipCallback.callback(new Object[0]);
        }
    }

    public static String getTipStateChange() {
        return tipStateChange;
    }

    private boolean isLog() {
        if (!this.LogSwitch) {
            return false;
        }
        return bno.a;
    }

    /* access modifiers changed from: private */
    public void logFormat(String str, Object... objArr) {
        if (isLog()) {
            logFormat(str, null, objArr);
        }
    }

    private void logFormat(String str, Throwable th, Object... objArr) {
        if (isLog()) {
            try {
                log(String.format(str, objArr), th);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void log(String str) {
        if (isLog()) {
            log(str, null);
        }
    }

    private void log(String str, Throwable th) {
        if (!isLog()) {
        }
    }
}

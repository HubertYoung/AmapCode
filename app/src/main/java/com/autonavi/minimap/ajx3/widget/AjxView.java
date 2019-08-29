package com.autonavi.minimap.ajx3.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.AjxOrientationHelper;
import com.autonavi.minimap.ajx3.AjxSoftKeyboardListener;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.R;
import com.autonavi.minimap.ajx3.ViewAttributeGetter;
import com.autonavi.minimap.ajx3.analyzer.AjxAnalyzerDelegate;
import com.autonavi.minimap.ajx3.analyzer.AjxScalpelLayout;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.core.PageConfig;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.exception.IllegalEngineException;
import com.autonavi.minimap.ajx3.inspect.OnRequestOpListener;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import com.autonavi.minimap.ajx3.util.Constants;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.KeyBoardUtil;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.BodyProperty;
import com.autonavi.minimap.ajx3.widget.property.TouchHelper;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.lang.reflect.Method;
import java.util.HashSet;
import org.json.JSONObject;

public class AjxView extends AjxScalpelLayout implements OnLayoutChangeListener, ViewExtension {
    private static boolean mSendAjxViewLifeCycle = false;
    private static OnRequestOpListener onRequestOpListener;
    public boolean isTalkBackServiceEnable;
    private IAjxContext mAjxContext;
    private HashSet<AjxContextLifecycleCallback> mAjxContextLifecycleCallback;
    private AjxTransitionState mAjxTransitionState;
    private AnyTouchListener mAnyTouchListener;
    private View mCover;
    public boolean mIsFromPoiSimulate;
    private JsRunInfo mJsRunInfo;
    private BodyProperty mProperty;
    public long mRenderTime;
    protected int mScreenHeight;
    public long mStartTime;
    protected LongSparseArray<AjxView> mSubAjxViewMap;
    private TouchHelper mTouchHelper;

    public interface AjxContextLifecycleCallback {
        void onCreated(IAjxContext iAjxContext);
    }

    public interface AnyTouchListener {
        void onTouch();
    }

    public void dismissSub(long j) {
    }

    public void onAddLayer(String str, String str2, Object obj) {
    }

    public void onBack(Object obj, String str) {
    }

    public void onChildViewShow() {
    }

    /* access modifiers changed from: protected */
    public void onJsLoadEnd() {
    }

    /* access modifiers changed from: protected */
    public void onJsStartLoad(String str) {
    }

    /* access modifiers changed from: protected */
    public void onJsUiLoad() {
    }

    /* access modifiers changed from: protected */
    public void onJsUiLoadStart() {
    }

    public void onLoadingDismiss() {
    }

    public void onOpen(String str, String str2, Object obj, String str3) {
    }

    public void onRemoveLayer(String str) {
    }

    public void onReplace(String str, String str2, Object obj, String str3) {
    }

    public void orientationChange(int i) {
    }

    public void present(String str, int i, int i2, int i3, int i4) {
    }

    public AjxView(@NonNull Context context) {
        this(context, null);
    }

    public AjxView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AjxView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mScreenHeight = 0;
        this.mTouchHelper = null;
        this.mAjxContextLifecycleCallback = new HashSet<>();
        this.mIsFromPoiSimulate = false;
        this.mStartTime = -1;
        this.mRenderTime = -1;
        this.mCover = null;
        this.mSubAjxViewMap = new LongSparseArray<>();
        this.isTalkBackServiceEnable = false;
        StringBuilder sb = new StringBuilder("native AJXView create AjxViewHash=");
        sb.append(hashCode());
        sb.append(".");
        AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, sb.toString());
        this.mScreenHeight = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
        this.mAjxTransitionState = new AjxTransitionState(this);
        AjxSoftKeyboardListener.getInstance().init((Activity) context);
        adjustTalkBackService(context);
    }

    /* access modifiers changed from: protected */
    public final void load(JsRunInfo jsRunInfo) {
        if (!TextUtils.isEmpty(jsRunInfo.getUrl())) {
            if (jsRunInfo.getRunWidth() == 0.0f || jsRunInfo.getRunHeight() == 0.0f) {
                jsRunInfo.updateWidthAndHeight(getWidth(), getHeight());
            }
            this.mJsRunInfo = jsRunInfo;
            if (jsRunInfo.getRunWidth() <= 0.0f || jsRunInfo.getRunHeight() <= 0.0f) {
                addOnLayoutChangeListener(this);
            } else {
                loadJs(jsRunInfo);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean debugUrlExistCheck(String str) {
        return this.mAjxTransitionState.checkUrlExist(str);
    }

    public boolean checkUrlExist(String str) {
        return this.mAjxTransitionState.checkUrlExist(str);
    }

    public void onNodeUnitId(String str, String str2) {
        String url = this.mJsRunInfo.getUrl();
        String bundleName = AjxFileInfo.getBundleName(url);
        String baseAjxFileVersion = AjxFileInfo.getBaseAjxFileVersion(bundleName);
        String loadedDiffAjxFileVersion = AjxFileInfo.getLoadedDiffAjxFileVersion(bundleName);
        if (!TextUtils.isEmpty(loadedDiffAjxFileVersion)) {
            StringBuilder sb = new StringBuilder();
            sb.append(baseAjxFileVersion);
            sb.append("_");
            sb.append(loadedDiffAjxFileVersion);
            baseAjxFileVersion = sb.toString();
        }
        if (TextUtils.isEmpty(str) || !str.contains(MetaRecord.LOG_SEPARATOR)) {
            record(url, baseAjxFileVersion, str, str2);
            return;
        }
        ViewAttributeGetter.getInstance().savePage(this.mJsRunInfo.getUrl(), baseAjxFileVersion);
        ViewAttributeGetter.getInstance().putValue(this.mJsRunInfo.getUrl(), str, str2);
    }

    private void record(String str, String str2, String str3, String str4) {
        try {
            Class<?> cls = Class.forName("com.autonavi.minimap.evaluate.draugorp.Drawing");
            if (cls != null) {
                Method method = cls.getMethod("setAjxInfo", new Class[]{String.class});
                if (method != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("stack", str3);
                    jSONObject.put("content", str4);
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("version", str2);
                    jSONObject2.put("path", str);
                    jSONObject2.put("engineJson", jSONObject);
                    method.invoke(null, new Object[]{jSONObject2.toString()});
                }
            }
        } catch (Exception unused) {
        }
    }

    private void loadJs(JsRunInfo jsRunInfo) {
        if (jsRunInfo == null || TextUtils.isEmpty(jsRunInfo.getUrl())) {
            throw new IllegalEngineException((String) "Error: url is null.");
        }
        Ajx.getInstance().addTimestampWithoutContext("ajxBeforeOpen");
        LogManager.performaceLog("ajxBeforeOpen");
        destroy();
        this.mStartTime = System.currentTimeMillis();
        onJsStartLoad(jsRunInfo.getUrl());
        this.mAjxContext = this.mAjxTransitionState.createAjxContext(this, jsRunInfo);
        onJsLoadEnd();
        if (mSendAjxViewLifeCycle) {
            sendAjxViewLifyCycle(Constants.EVENT_READY, jsRunInfo.getData() != null ? jsRunInfo.getData().toString() : null);
        }
        this.mProperty = new BodyProperty(this, this.mAjxContext);
        StringBuilder sb = new StringBuilder("native loadJS AjxViewHash=");
        sb.append(hashCode());
        sb.append(", jsPath=");
        sb.append(jsRunInfo.getUrl());
        sb.append(".");
        AjxALCLog.info(AjxALCLog.TAG_PERFORMANCE, sb.toString());
        this.mAjxTransitionState.run(this.mAjxContext, jsRunInfo);
        dispatchAjxContextCreated(this.mAjxContext);
        ViewAttributeGetter.getInstance().setContext(this.mAjxContext);
        AjxAnalyzerDelegate.bindAjxContext(this.mAjxContext);
    }

    private void dispatchAjxContextCreated(IAjxContext iAjxContext) {
        Object[] collectAjxContextCreateListeners = collectAjxContextCreateListeners();
        if (collectAjxContextCreateListeners != null) {
            for (Object obj : collectAjxContextCreateListeners) {
                ((AjxContextLifecycleCallback) obj).onCreated(iAjxContext);
            }
        }
        sendInspect(iAjxContext);
    }

    private Object[] collectAjxContextCreateListeners() {
        if (this.mAjxContextLifecycleCallback.size() > 0) {
            return this.mAjxContextLifecycleCallback.toArray();
        }
        return null;
    }

    public void reload() {
        load(this.mJsRunInfo);
    }

    public boolean backPressed() {
        return this.mAjxTransitionState.hardwareback();
    }

    public void destroy() {
        AjxAnalyzerDelegate.bindAjxContext(null);
        clearSubAjxView();
        if (this.mAjxContext != null) {
            try {
                long currentTimeMillis = System.currentTimeMillis() - this.mStartTime;
                int i = this.mRenderTime > 0 ? 0 : 1;
                long j = this.mRenderTime;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("livetime", String.format("%.4f", new Object[]{Float.valueOf((float) (((double) currentTimeMillis) / 1000.0d))}));
                jSONObject.put("isblank", i);
                jSONObject.put("rendertime", j > 0 ? String.format("%.4f", new Object[]{Float.valueOf((float) (((double) j) / 1000.0d))}) : Long.valueOf(j));
                Ajx.getInstance().onLineLog(getUrl(), "page_destroy", "B002", "p4", jSONObject.toString());
            } catch (Exception unused) {
            }
            ViewAttributeGetter.getInstance().deleteContext(this.mAjxContext);
            this.mAjxContext.destroy();
            if (this.mTouchHelper != null) {
                this.mTouchHelper = null;
            }
            clearAnimation();
            KeyBoardUtil.hideSoftKeyboard(this);
            removeAllViews();
        }
    }

    @Deprecated
    public void onResume(boolean z, Object obj) {
        this.mAjxTransitionState.pageShow(z, obj);
        ViewAttributeGetter.getInstance().setContext(this.mAjxContext);
        adjustTalkBackService(getContext());
        if (this.mTouchHelper != null) {
            this.mTouchHelper.updateTalkBackServiceEnable(this.isTalkBackServiceEnable);
        }
        AjxAnalyzerDelegate.bindAjxContext(this.mAjxContext);
    }

    public void onNewIntent(Object obj) {
        this.mAjxTransitionState.onNewIntent(obj);
    }

    public void pageShow(boolean z, Object obj) {
        this.mAjxTransitionState.pageShow(z, obj);
        ViewAttributeGetter.getInstance().setContext(this.mAjxContext);
        adjustTalkBackService(getContext());
        if (this.mTouchHelper != null) {
            this.mTouchHelper.updateTalkBackServiceEnable(this.isTalkBackServiceEnable);
        }
        AjxAnalyzerDelegate.bindAjxContext(this.mAjxContext);
    }

    @Deprecated
    public void onPause() {
        AjxAnalyzerDelegate.bindAjxContext(null);
        this.mAjxTransitionState.pageHide(false);
        ViewAttributeGetter.getInstance().deleteContext(this.mAjxContext);
    }

    public void onPause(boolean z) {
        AjxAnalyzerDelegate.bindAjxContext(null);
        this.mAjxTransitionState.pageHide(z);
        ViewAttributeGetter.getInstance().deleteContext(this.mAjxContext);
    }

    public void pageHide(boolean z) {
        AjxAnalyzerDelegate.bindAjxContext(null);
        this.mAjxTransitionState.pageHide(z);
        ViewAttributeGetter.getInstance().deleteContext(this.mAjxContext);
    }

    public void pageBecomeActive() {
        this.mAjxTransitionState.pageBecomeActive();
    }

    public void pageResignActive() {
        this.mAjxTransitionState.pageResignActive();
    }

    public void dispatchOrientationChange(int i) {
        if (getAjxContext() != null) {
            Parcel parcel = new Parcel();
            parcel.writeInt(2);
            parcel.writeString(CaptureParam.ORIENTATION_MODE);
            parcel.writeString(String.valueOf(i));
            getAjxContext().getJsContext().invokeEvent(new Builder().setEventName("orientationchange").setNodeId(0).setAttribute(parcel).build());
        }
    }

    private void sendAjxViewLifyCycle(String str, String str2) {
        Intent intent = new Intent(Constants.ACTION_AJX_VIEW_LIFE_CYCLE);
        intent.putExtra("event", str);
        intent.putExtra("data", str2);
        if (this.mJsRunInfo.getUrl().startsWith("file")) {
            intent.putExtra("url", this.mJsRunInfo.getUrl().substring(this.mJsRunInfo.getUrl().lastIndexOf("js/") + 3));
        } else if (this.mJsRunInfo.getUrl().startsWith("path")) {
            intent.putExtra("url", this.mJsRunInfo.getUrl().substring(7));
        }
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    public void setSendLifeCycle(boolean z) {
        mSendAjxViewLifeCycle = z;
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mJsRunInfo.updateWidthAndHeight(i3 - i, i4 - i2);
        loadJs(this.mJsRunInfo);
        removeOnLayoutChangeListener(this);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AjxOrientationHelper.getInstance().onConfigurationChanged(this, configuration.orientation);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mAjxContext != null) {
            this.mAjxContext.getJsContext().resize(DimensionUtils.pixelToStandardUnit((float) i), DimensionUtils.pixelToStandardUnit((float) i2), AjxOrientationHelper.getInstance().onSizeChanged(this, i, i2, i3, i4) ? 1 : 0);
        }
    }

    public String getUrl() {
        return this.mJsRunInfo != null ? this.mJsRunInfo.getUrl() : "";
    }

    /* access modifiers changed from: 0000 */
    public String getAjxTag() {
        return this.mJsRunInfo != null ? this.mJsRunInfo.getTag() : "";
    }

    @Nullable
    public IAjxContext getAjxContext() {
        return this.mAjxContext;
    }

    public String getPageId() {
        return this.mJsRunInfo != null ? this.mJsRunInfo.getPageId() : "";
    }

    public String getPageKey() {
        JsRunInfo jsRunInfo = this.mAjxContext.getJsRunInfo();
        if (jsRunInfo != null) {
            PageConfig pageConfig = jsRunInfo.getPageConfig();
            if (!(pageConfig == null || pageConfig.getId() == null)) {
                return pageConfig.getId();
            }
        }
        return null;
    }

    public TouchHelper getHelper() {
        return this.mTouchHelper;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        AjxAnalyzerDelegate.onReceiveTouchEvent(motionEvent);
        if (!(this.mAnyTouchListener == null || motionEvent == null || motionEvent.getAction() != 0)) {
            this.mAnyTouchListener.onTouch();
        }
        if (getParent() instanceof AjxView) {
            return false;
        }
        return handleTouchEvent(motionEvent);
    }

    private boolean handleTouchEvent(MotionEvent motionEvent) {
        if (subAjxViewHandleEvent(motionEvent)) {
            return true;
        }
        if (this.mAjxContext == null || this.mAjxContext.hasDestroy()) {
            return false;
        }
        if (this.mTouchHelper == null) {
            this.mTouchHelper = new TouchHelper(this.mAjxContext, this, this.isTalkBackServiceEnable);
        }
        if (this.mTouchHelper.forbidEvents()) {
            return true;
        }
        boolean handleMotionEvent = this.mTouchHelper.handleMotionEvent(motionEvent, this);
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (this.mIsFromPoiSimulate) {
            if (motionEvent.getAction() == 1) {
                this.mIsFromPoiSimulate = false;
            }
            return true;
        } else if (handleMotionEvent || dispatchTouchEvent) {
            return true;
        } else {
            return false;
        }
    }

    private boolean subAjxViewHandleEvent(MotionEvent motionEvent) {
        if (this.mSubAjxViewMap.size() <= 0) {
            return false;
        }
        int childCount = getChildCount();
        if (childCount <= 1) {
            return false;
        }
        boolean z = false;
        for (int i = childCount - 1; i > 0; i--) {
            View childAt = getChildAt(i);
            if (childAt instanceof AjxView) {
                float[] tempPoint = TouchHelper.getTempPoint();
                tempPoint[0] = motionEvent.getX();
                tempPoint[1] = motionEvent.getY();
                if (TouchHelper.pointInView(tempPoint, childAt, getScrollX(), getScrollY())) {
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.offsetLocation((float) (getScrollX() - childAt.getLeft()), (float) (getScrollY() - childAt.getTop()));
                    z = ((AjxView) childAt).handleTouchEvent(obtain);
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public void sendJsMessage(String str) {
        if (this.mAjxContext != null) {
            this.mAjxContext.sendJsMessage(str);
        }
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mProperty != null) {
            this.mProperty.onDraw(canvas);
        }
    }

    public void draw(Canvas canvas) {
        if (this.mProperty != null) {
            this.mProperty.beforeDraw(canvas);
        }
        super.draw(canvas);
        if (this.mProperty != null) {
            this.mProperty.afterDraw(canvas);
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerAjxContextLifecycleCallback(AjxContextLifecycleCallback ajxContextLifecycleCallback) {
        this.mAjxContextLifecycleCallback.add(ajxContextLifecycleCallback);
    }

    /* access modifiers changed from: 0000 */
    public void unregisterAjxContextLifecycleCallback(AjxContextLifecycleCallback ajxContextLifecycleCallback) {
        this.mAjxContextLifecycleCallback.remove(ajxContextLifecycleCallback);
    }

    public void setSoftInputMode(int i) {
        ((Activity) getContext()).getWindow().setSoftInputMode(i);
    }

    public void setEnvironment(String str) {
        this.mJsRunInfo.setEnvironment(str);
    }

    public void addCoverView(View view) {
        LayoutParams layoutParams;
        if (this.mCover == null) {
            this.mCover = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.cover, null);
        }
        if (this.mCover.getParent() != null) {
            ((ViewGroup) this.mCover.getParent()).removeView(this.mCover);
        }
        if (view != null && view.getVisibility() != 8) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) parent;
                int width = view.getWidth();
                int height = view.getHeight();
                int left = view.getLeft();
                int top = view.getTop();
                if (parent instanceof AjxView) {
                    layoutParams = new FrameLayout.LayoutParams(width, height);
                    FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
                    layoutParams2.leftMargin = left;
                    layoutParams2.topMargin = top;
                } else if (parent instanceof AjxViewPager) {
                    layoutParams = ((AjxViewPager) parent).generateDefaultLayoutParams();
                } else {
                    layoutParams = new AbsoluteLayout.LayoutParams(width, height, left, top);
                    AbsoluteLayout.LayoutParams layoutParams3 = (AbsoluteLayout.LayoutParams) layoutParams;
                    layoutParams3.x = left;
                    layoutParams3.y = top;
                }
                layoutParams.height = height;
                layoutParams.width = width;
                viewGroup.addView(this.mCover, layoutParams);
            }
        }
    }

    public void removeCover() {
        if (this.mCover != null && this.mCover.getParent() != null) {
            ((ViewGroup) this.mCover.getParent()).removeView(this.mCover);
        }
    }

    public void beginForbidEvents(long j) {
        if (this.mTouchHelper == null) {
            this.mTouchHelper = new TouchHelper(this.mAjxContext, this, this.isTalkBackServiceEnable);
        }
        this.mTouchHelper.askForbidEvents(j);
    }

    public void stopForbidEvents(long j) {
        if (this.mTouchHelper != null) {
            this.mTouchHelper.removeForbidEvents(j);
        }
    }

    private void sendInspect(IAjxContext iAjxContext) {
        String obj;
        if (onRequestOpListener != null) {
            String url = iAjxContext.getJsRunInfo().getUrl();
            Object data = iAjxContext.getJsRunInfo().getData();
            if (data == null) {
                obj = "";
            } else {
                obj = data.toString();
            }
            String str = obj;
            StringBuilder sb = new StringBuilder();
            sb.append(System.nanoTime());
            String sb2 = sb.toString();
            String str2 = sb2;
            String str3 = url;
            onRequestOpListener.onRequestWillBeSend(str2, str3, "GET", null, "", "Document");
            onRequestOpListener.onResponseReceived(str2, str3, 200, "success", null, null, "Document", str);
            onRequestOpListener.onLoadingFinished(sb2, "Document");
        }
    }

    public static void setOnRequestOpListener(OnRequestOpListener onRequestOpListener2) {
        onRequestOpListener = onRequestOpListener2;
    }

    public void clearSubAjxView() {
        int size = this.mSubAjxViewMap.size();
        for (int i = 0; i < size; i++) {
            AjxView ajxView = (AjxView) this.mSubAjxViewMap.get(this.mSubAjxViewMap.keyAt(i));
            if (ajxView != null) {
                ajxView.destroy();
            }
        }
        this.mSubAjxViewMap.clear();
    }

    private void adjustTalkBackService(Context context) {
        System.currentTimeMillis();
        try {
            String string = Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
            if (!TextUtils.isEmpty(string) && string.contains("TalkBackService")) {
                this.isTalkBackServiceEnable = true;
            }
        } catch (Exception unused) {
        }
    }

    public void setAnyTouchListener(AnyTouchListener anyTouchListener) {
        this.mAnyTouchListener = anyTouchListener;
    }
}

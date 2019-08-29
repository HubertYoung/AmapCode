package com.autonavi.map.fragmentcontainer.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Size;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.common.Page;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IDialogManifest;
import com.autonavi.inter.IPageManifest;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractBasePage<Presenter extends IPresenter> extends bup implements Page, IPage {
    private static final int INVALID_REQUEST_CODE = -1;
    private static final int STATE_CREATED = 2;
    private static final int STATE_CREATING = 1;
    private static final int STATE_DESTROYED = 50;
    private static final int STATE_NULL = 0;
    private static final int STATE_PAUSING = 30;
    private static final int STATE_RESUMING = 20;
    private static final int STATE_STARTING = 10;
    private static final int STATE_STOPPED = 41;
    private static final int STATE_STOPPING = 40;
    private static final String TAG = "AmapPage";
    private boolean mAllowSetInputMode;
    private View mContentView;
    private Context mContext;
    private boolean mFinishInvoke;
    private AbstractBasePage mHostPage;
    private boolean mIsBaseOnCreateCalled;
    public boolean mIsJustNowCreated;
    private boolean mIsPageSwitch = true;
    private int mIsScreenOn = -1;
    private LayoutInflater mLayoutInflater;
    private PageBundle mNodeFragmentBundle;
    private boolean mOnCoverInvoke;
    private PageContainer mPageContainer;
    private dro mPageLifeCycleHooker;
    public Presenter mPresenter;
    private int mRequestCode = -1;
    private int mScreenOrientation = -1;
    private int mSoftInputMode = 32;
    private int mState = 0;
    private ViewLayerManager viewLayerManager;

    /* access modifiers changed from: protected */
    public abstract Presenter createPresenter();

    public final bid getPageContext() {
        return this;
    }

    public String getSpm() {
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean isSetSoftInput() {
        return true;
    }

    public boolean isShowMapWidgets() {
        return false;
    }

    public boolean isShowPageHeader() {
        return false;
    }

    public void attach(Context context, LayoutInflater layoutInflater, AbstractBasePage abstractBasePage, akg akg, bul bul) {
        attach(akg, bul);
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
        this.mHostPage = abstractBasePage;
        this.mPageLifeCycleHooker = drp.b();
    }

    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Activity getActivity() {
        return (Activity) this.mContext;
    }

    public final FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        FragmentActivity fragmentActivity = (activity == null || !(activity instanceof FragmentActivity)) ? null : (FragmentActivity) activity;
        if (fragmentActivity != null) {
            return fragmentActivity.getSupportFragmentManager();
        }
        return null;
    }

    public final void setAllowSetSoftMode(boolean z) {
        this.mAllowSetInputMode = z;
    }

    public final boolean isAllowSetSoftInputMode() {
        return this.mAllowSetInputMode;
    }

    public final void setSoftInputMode(int i) {
        this.mSoftInputMode = i;
        Window window = getActivity().getWindow();
        LayoutParams attributes = window.getAttributes();
        if (attributes != null && attributes.softInputMode != i && (attributes.softInputMode & -257) != i) {
            window.setSoftInputMode(this.mSoftInputMode);
        }
    }

    public final int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    /* access modifiers changed from: protected */
    public final void setContentView(@LayoutRes int i) {
        setContentView(this.mLayoutInflater.inflate(i, null, false));
    }

    /* access modifiers changed from: protected */
    public final void setContentView(View view) {
        if (isCreated()) {
            throw new IllegalStateException("Cannot setContentView() after onCreate()!!");
        }
        this.mContentView = view;
        this.mContentView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }

    public final View getContentView() {
        return this.mContentView;
    }

    @Nullable
    public final View findViewById(@IdRes int i) {
        if (this.mContentView != null) {
            return this.mContentView.findViewById(i);
        }
        throw new IllegalStateException("Must setContentView() before findViewById()!!");
    }

    public final Resources getResources() {
        checkContext();
        return this.mContext.getResources();
    }

    public final String getString(int i) {
        checkContext();
        return this.mContext.getString(i);
    }

    public final String getString(int i, Object... objArr) {
        checkContext();
        return this.mContext.getString(i, objArr);
    }

    private void checkContext() {
        if (this.mContext == null) {
            throw new IllegalStateException("Cannot access context before create or after destroy!!");
        }
    }

    public final void setRequestCode(int i) {
        this.mRequestCode = i;
    }

    public final boolean isCreated() {
        return this.mState >= 2;
    }

    public final boolean isAlive() {
        return this.mState > 0 && this.mState < 50;
    }

    public final boolean isStarted() {
        return this.mState >= 10 && this.mState < 40;
    }

    public final boolean isResumed() {
        return this.mState >= 20 && this.mState < 30;
    }

    public final void requestScreenOn(boolean z) {
        int i = z ? 1 : 2;
        if (this.mIsScreenOn != i) {
            this.mIsScreenOn = i;
            setScreenOnOrOff();
        }
    }

    public final void requestScreenOrientation(int i) {
        doRequestScreenOrientation(i);
    }

    /* access modifiers changed from: protected */
    public void doRequestScreenOrientation(int i) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        if (this.mScreenOrientation != i || activity.getRequestedOrientation() != i) {
            this.mScreenOrientation = i;
            activity.setRequestedOrientation(i);
        }
    }

    @CallSuper
    public void onCreate(Context context) {
        this.mState = 1;
        this.mPresenter = createPresenter();
        TopStackPageRecorder.record(this);
        this.mIsBaseOnCreateCalled = true;
        StringBuilder sb = new StringBuilder("onCreate~~~~~");
        sb.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb.toString());
        this.mPageLifeCycleHooker.a(new WeakReference<>(this));
        this.mIsJustNowCreated = true;
    }

    private boolean isAjx3DialogPage() {
        return getClass().getSimpleName().equals("Ajx3DialogPage") || getClass().getSuperclass().getSimpleName().equals("Ajx3DialogPage");
    }

    public final void onStart() {
        StringBuilder sb = new StringBuilder("onStart~~~~~");
        sb.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb.toString());
        if (euk.a()) {
            euk.b(getActivity());
        }
        Window window = getActivity().getWindow();
        LayoutParams attributes = window.getAttributes();
        if (!(attributes == null || attributes.softInputMode == this.mSoftInputMode || !isSetSoftInput() || (attributes.softInputMode & -257) == this.mSoftInputMode)) {
            window.setSoftInputMode(this.mSoftInputMode);
        }
        this.mState = 10;
        this.mAllowSetInputMode = true;
        this.mPageLifeCycleHooker.b(new WeakReference<>(this));
        StringBuilder sb2 = new StringBuilder("onStart~~~~~ forebackChange=");
        sb2.append(isPageSwitch());
        sb2.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb2.toString());
        dispatchStartEvent();
        if (this.mOnCoverInvoke) {
            onAppear(this);
        }
        this.mOnCoverInvoke = false;
    }

    public boolean isJustNowCreated() {
        return this.mIsJustNowCreated;
    }

    public final void onResume() {
        TopStackPageRecorder.record(this);
        this.mState = 20;
        this.mPageLifeCycleHooker.c(new WeakReference(this));
        if (but.e != null) {
            but.e.a(this);
        }
        if (but.d != null) {
            but.d.a(this);
        }
        if (!isTransparent()) {
            doRequestScreenOrientation(this.mScreenOrientation);
        }
        dispatchResumeEvent();
        if (!isTransparent()) {
            setScreenOnOrOff();
        }
        this.mIsPageSwitch = false;
        this.mPageLifeCycleHooker.d(new WeakReference(this));
        if (this.mIsJustNowCreated) {
            this.mIsJustNowCreated = false;
        }
    }

    public final void onPause() {
        StringBuilder sb = new StringBuilder("onPause~~~~~");
        sb.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb.toString());
        this.mState = 30;
        this.mPageLifeCycleHooker.e(new WeakReference(this));
        dispatchPauseEvent();
        getMvpActivityContext();
        if (!Transparent.class.isAssignableFrom(getClass())) {
            resetScreenOnState();
        }
        dro dro = this.mPageLifeCycleHooker;
        new WeakReference(this);
        dro.a();
    }

    public final void onStop() {
        this.mState = 40;
        this.mPageLifeCycleHooker.f(new WeakReference(this));
        if (!this.mFinishInvoke && drp.b().a) {
            this.mIsPageSwitch = true;
        }
        StringBuilder sb = new StringBuilder("onStop~~~~~ forebackChange=");
        sb.append(isPageSwitch());
        sb.append(", ");
        sb.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb.toString());
        dispatchStopEvent();
        if (!this.mFinishInvoke && drp.b().a) {
            this.mOnCoverInvoke = true;
            onCover(this);
        }
        this.mAllowSetInputMode = false;
        this.mState = 41;
        KeyboardUtil.hideInputMethod(getActivity());
    }

    public final void finishInvokeStateChange(boolean z) {
        this.mFinishInvoke = z;
        if (getPageContainer() != null) {
            getPageContainer().finishInvokeStateChange(z);
        }
    }

    public final void onDestroy() {
        this.mPageLifeCycleHooker.g(new WeakReference(this));
        this.mPageLifeCycleHooker.b((bid) this);
        dispatchDestroyEvent();
        if (but.a != null) {
            but.a.a(this);
        }
        this.mState = 50;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (isCreated()) {
            if (this.viewLayerManager != null) {
                this.viewLayerManager.onConfigurationChanged(configuration);
            }
            dispatchConfigurationChangedEvent(configuration);
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        if (isCreated() && this.mPresenter != null) {
            this.mPresenter.onWindowFocusChanged(z);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.viewLayerManager != null && this.viewLayerManager.onBackPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (getPageContainer() == null || getPageContainer().onBackPressed() != ON_BACK_TYPE.TYPE_IGNORE) {
            return this.mPresenter.onBackPressed();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mPresenter != null) {
            return this.mPresenter.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public final void performCreate(Context context) {
        this.mIsBaseOnCreateCalled = false;
        onCreate(context);
        if (!this.mIsBaseOnCreateCalled) {
            throw new IllegalStateException("Must call super.onCreate()!!");
        }
        dispatchPageCreatedEvent();
        this.mState = 2;
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPageCreatedEvent() {
        if (this.mPresenter != null) {
            this.mPresenter.onPageCreated();
            bul mvpActivityContext = getMvpActivityContext();
            Size size = mvpActivityContext != null ? mvpActivityContext.e == null ? new Size(0, 0) : new Size(mvpActivityContext.e.getWidth(), mvpActivityContext.e.getHeight()) : new Size(0, 0);
            this.mPresenter.onPageCreated(size.getWidth(), size.getHeight());
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchResumeEvent() {
        if (this.mPresenter != null) {
            this.mPresenter.onResume();
        }
        if (getPageContainer() != null) {
            getPageContainer().onResume();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchConfigurationChangedEvent(Configuration configuration) {
        if (this.mPresenter != null) {
            this.mPresenter.onConfigurationChanged(configuration);
        }
        if (getPageContainer() != null) {
            getPageContainer().onConfigurationChange(configuration);
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPauseEvent() {
        if (this.mPresenter != null) {
            this.mPresenter.onPause();
        }
        if (getPageContainer() != null) {
            getPageContainer().onPause();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchStartEvent() {
        if (this.mPresenter != null) {
            this.mPresenter.onStart();
        }
        if (getPageContainer() != null) {
            getPageContainer().onStart();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchStopEvent() {
        if (this.mPresenter != null) {
            this.mPresenter.onStop();
        }
        if (getPageContainer() != null) {
            getPageContainer().onStop();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchDestroyEvent() {
        if (getPageContainer() != null) {
            getPageContainer().onDestroy();
        }
        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }

    public final PageBundle getArguments() {
        return this.mNodeFragmentBundle;
    }

    public final void setArguments(PageBundle pageBundle) {
        this.mNodeFragmentBundle = pageBundle;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        if (this.mPresenter != null) {
            this.mPresenter.onNewIntent(pageBundle);
        }
    }

    public final void startPageForResult(Class<? extends bid> cls, PageBundle pageBundle, int i) {
        startPage(cls, pageBundle, i);
    }

    public final void startPageForResult(String str, PageBundle pageBundle, int i) {
        startPage(str, pageBundle, i);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        StringBuilder sb = new StringBuilder("onResult~~~~~");
        sb.append(getClass().getSimpleName());
        AMapLog.d(TAG, sb.toString());
        if (this.mPresenter != null) {
            this.mPresenter.onResult(i, resultType, pageBundle);
        }
        if (getPageContainer() != null) {
            getPageContainer().onPageResult(i, resultType, pageBundle);
        }
    }

    public final void startActivity(Intent intent) {
        getActivity().startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        Activity activity = getActivity();
        if (activity != null) {
            activity.startActivityForResult(intent, i);
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        if (this.mPresenter != null) {
            this.mPresenter.onActivityResult(i, i2, intent);
        }
    }

    public final void finish() {
        finishInvokeStateChange(true);
        super.finish();
    }

    public final void startScheme(Intent intent) {
        bul mvpActivityContext = getMvpActivityContext();
        if (mvpActivityContext.d instanceof brr) {
            ((brr) mvpActivityContext.d).b(intent);
        }
    }

    public void showViewLayer(IViewLayer iViewLayer) {
        if (this.viewLayerManager == null) {
            this.viewLayerManager = new ViewLayerManager(this.mHostPage != null ? this.mHostPage : this);
        }
        this.viewLayerManager.showViewLayer(iViewLayer);
    }

    public void dismissViewLayer(IViewLayer iViewLayer) {
        if (this.viewLayerManager != null) {
            this.viewLayerManager.dismissViewLayer(iViewLayer);
        }
    }

    public final void dismissAllViewLayers() {
        if (this.viewLayerManager != null) {
            this.viewLayerManager.dismissAllViewLayers();
        }
    }

    public final boolean isViewLayerShowing(IViewLayer iViewLayer) {
        return this.viewLayerManager != null && this.viewLayerManager.isViewLayerShowing(iViewLayer);
    }

    public final boolean hasViewLayer() {
        return this.viewLayerManager != null && this.viewLayerManager.hasViewLayer();
    }

    @Nullable
    public final PageContainer getPageContainer() {
        if (this.mContentView == null) {
            return null;
        }
        if (this.mPageContainer != null) {
            return this.mPageContainer;
        }
        this.mPageContainer = findPageContainer(this.mContentView);
        if (this.mPageContainer != null) {
            this.mPageContainer.attachHostPage(this);
        }
        return this.mPageContainer;
    }

    private PageContainer findPageContainer(View view) {
        return (PageContainer) (view != null ? view.findViewById(R.id.page_container_id) : null);
    }

    public void showDialog(Class<? extends AbstractBaseDialog> cls, PageBundle pageBundle) {
        if (cls != null) {
            try {
                Constructor<? extends AbstractBaseDialog> constructor = cls.getConstructor(new Class[]{bid.class});
                if (constructor != null) {
                    AbstractBaseDialog abstractBaseDialog = (AbstractBaseDialog) constructor.newInstance(new Object[]{this});
                    abstractBaseDialog.onCreate(pageBundle);
                    showViewLayer(abstractBaseDialog);
                }
            } catch (NoSuchMethodException e) {
                AMapLog.e(TAG, e.toString());
            } catch (InstantiationException e2) {
                AMapLog.e(TAG, e2.toString());
            } catch (IllegalAccessException e3) {
                AMapLog.e(TAG, e3.toString());
            } catch (InvocationTargetException e4) {
                AMapLog.e(TAG, e4.toString());
            }
        }
    }

    public void showDialog(String str, PageBundle pageBundle) {
        Class<?> a = ((IDialogManifest) bqn.a(IDialogManifest.class)).a();
        if (a != null) {
            showDialog(a, pageBundle);
        }
    }

    public void startAlertDialogPage(a aVar) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("builder", aVar);
        IPageManifest iPageManifest = (IPageManifest) ank.a(IPageManifest.class);
        Class<?> page = iPageManifest != null ? iPageManifest.getPage("amap.page.action.alert_dialog_page") : null;
        if (page != null && bid.class.isAssignableFrom(page)) {
            startPageForResult(page, pageBundle, -1);
        }
    }

    private void onCover(bid bid) {
        AMapLog.d(TAG, "AbstractBasePage:onCover pageContext：".concat(String.valueOf(bid)));
        final IPageStateListener a = this.mPageLifeCycleHooker.a(bid);
        if (a != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                a.onCover();
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    a.onCover();
                }
            });
        }
    }

    @Deprecated
    private void onAppear(bid bid) {
        AMapLog.d(TAG, "AbstractBasePage:onAppear pageContext：".concat(String.valueOf(bid)));
        final IPageStateListener a = this.mPageLifeCycleHooker.a(bid);
        if (a != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                a.onAppear();
                return;
            }
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    a.onAppear();
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public final boolean isPageSwitch() {
        return this.mIsPageSwitch;
    }

    private void setScreenOnOrOff() {
        StringBuilder sb = new StringBuilder("setScreenOnOrOff mIsScreenOn = ");
        sb.append(this.mIsScreenOn);
        AMapLog.d(TAG, sb.toString());
        Activity activity = getActivity();
        if (activity != null) {
            boolean z = true;
            if (this.mIsScreenOn < 0) {
                if (but.f == null || !but.f.a()) {
                    z = false;
                }
                if (z) {
                    activity.getWindow().addFlags(128);
                    return;
                }
                activity.getWindow().clearFlags(128);
            } else if (this.mIsScreenOn == 1) {
                activity.getWindow().addFlags(128);
            } else {
                activity.getWindow().clearFlags(128);
            }
        }
    }

    private void resetScreenOnState() {
        StringBuilder sb = new StringBuilder("resetScreenOnState mIsScreenOn = ");
        sb.append(this.mIsScreenOn);
        AMapLog.d(TAG, sb.toString());
        if (this.mIsScreenOn >= 0) {
            Activity activity = getActivity();
            if (activity != null) {
                if (but.f != null && but.f.a()) {
                    activity.getWindow().addFlags(128);
                    return;
                }
                activity.getWindow().clearFlags(128);
            }
        }
    }

    public List<IViewLayer> getLayerStack() {
        if (this.viewLayerManager != null) {
            return this.viewLayerManager.getLayerStack();
        }
        return null;
    }

    public void startPage(Class<?> cls, PageBundle pageBundle) {
        dro dro = this.mPageLifeCycleHooker;
        new WeakReference(this);
        dro.a(cls);
        super.startPage(cls, pageBundle);
    }

    public void startPage(String str, PageBundle pageBundle) {
        Class findClassByAction = findClassByAction(str);
        if (findClassByAction != null) {
            dro dro = this.mPageLifeCycleHooker;
            new WeakReference(this);
            dro.a(findClassByAction);
        }
        super.startPage(str, pageBundle);
    }

    public void startPage(Class<?> cls, PageBundle pageBundle, int i) {
        dro dro = this.mPageLifeCycleHooker;
        new WeakReference(this);
        dro.a(cls);
        super.startPage(cls, pageBundle, i);
    }

    public void startPage(String str, PageBundle pageBundle, int i) {
        Class findClassByAction = findClassByAction(str);
        if (findClassByAction != null) {
            dro dro = this.mPageLifeCycleHooker;
            new WeakReference(this);
            dro.a(findClassByAction);
        }
        super.startPage(str, pageBundle, i);
    }

    public void onAnimationStarted(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onAnimationStarted isEnter = ");
        sb.append(z);
        AMapLog.d(TAG, sb.toString());
    }

    public void onAnimationFinished(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onAnimationFinished isEnter = ");
        sb.append(z);
        AMapLog.d(TAG, sb.toString());
    }

    public boolean isTransparent() {
        return this instanceof Transparent;
    }

    public AbstractBasePage getHostPage() {
        AbstractBasePage abstractBasePage = this.mHostPage;
        if (abstractBasePage == null) {
            return null;
        }
        while (abstractBasePage.mHostPage != null) {
            abstractBasePage = abstractBasePage.mHostPage;
        }
        return abstractBasePage;
    }
}

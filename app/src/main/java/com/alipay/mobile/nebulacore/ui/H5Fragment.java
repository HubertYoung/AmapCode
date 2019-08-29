package com.alipay.mobile.nebulacore.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Page.H5TitleBarReadyCallback;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageFactoryImpl;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.core.H5PagePreloader;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.prerender.H5PreRenderPool;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.web.H5WebChromeClient;
import com.alipay.mobile.nebulacore.web.H5WebView;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

public class H5Fragment extends Fragment implements H5BaseFragment {
    public static final String fragmentType = "fragmentType";
    public static final String normal = "normal";
    public static final String subtab = "subtab";
    protected H5ViewHolder a;
    protected H5WebView b;
    private View c;
    /* access modifiers changed from: private */
    public WeakReference<H5Activity> d;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;
    /* access modifiers changed from: private */
    public boolean h = false;
    private boolean i = false;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        H5Log.d("H5Fragment", "onAttach " + this);
        this.d = new WeakReference<>((H5Activity) activity);
    }

    public void onDetach() {
        super.onDetach();
        H5Log.d("H5Fragment", "onDetach " + this);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        H5Log.d("H5Fragment", "onCreate " + this);
        if (H5PageLoader.sServiceStart == 0) {
            H5PageLoader.sServiceStart = System.currentTimeMillis();
        }
    }

    public Animation onCreateAnimation(int transit, boolean enter, final int nextAnim) {
        if (R.anim.h5_translate_in_right != nextAnim) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
        Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        anim.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                H5Log.d("H5Fragment", "pushwindow animation onAnimationEnd");
                if (R.anim.h5_translate_in_right == nextAnim && H5Fragment.this.h && H5Fragment.this.b != null) {
                    H5Log.d("H5Fragment", "pushwindow animation old webview onResume " + H5Fragment.this.b);
                    H5Fragment.this.h = false;
                    H5Fragment.this.b.onResume();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        return anim;
    }

    private View a() {
        H5FragmentRootView view = new H5FragmentRootView(getActivity());
        view.init(getActivity(), getArguments());
        view.setLayoutParams(new LayoutParams(-1, -1));
        view.setTag(H5Utils.FRAGMENT_ROOT_VIEW_TAG);
        view.setBackgroundColor(getResources().getColor(R.color.h5_transparent));
        return view;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        H5Log.d("H5Fragment", "onCreateView " + this);
        try {
            if (this.c == null) {
                this.c = a();
                if (((H5Activity) this.d.get()).isFinishing()) {
                    H5Log.e((String) "H5Fragment", (String) "activity is finish");
                    return null;
                }
                Bundle params = getArguments();
                H5Utils.handleTinyAppKeyEvent((String) "main", (String) "H5Fragment.onCreateView()");
                if (H5Utils.isInTinyProcess()) {
                    this.a = H5PagePreloader.getPreloadedViewHolder(params);
                }
                if (this.a == null) {
                    this.a = new H5PageFactoryImpl((Activity) this.d.get()).createPage((Activity) this.d.get(), params);
                }
                NebulaUtil.parseNoAlphaColor(params);
                this.a.setRootView((ViewGroup) this.c);
                this.a.getH5Page().setH5Fragment(this);
                this.a.getH5Page().setRootView(this.c);
                this.a.getH5Page().applyParams();
                a(params);
                this.b = this.a.getH5Page().getWebView();
                if (this.b == null) {
                    return null;
                }
                if (this.b.getView() != null && !NebulaUtil.isShowTransAnimate(params)) {
                    this.b.getView().setBackgroundColor(H5Utils.getInt(params, (String) "backgroundColor"));
                }
                if (this.b.getView() != null && NebulaUtil.transparentBackground(params)) {
                    this.b.getView().setBackgroundColor(0);
                }
                H5PageData.walletServiceStart = System.currentTimeMillis();
                this.a.refreshView();
                if (this.a.getH5Page() != null) {
                    H5TitleBarReadyCallback h5TitleBarReadyCallback = this.a.getH5Page().getTitleBarReadyCallBack();
                    if (h5TitleBarReadyCallback != null) {
                        h5TitleBarReadyCallback.onCreate();
                    }
                }
            } else {
                ViewParent parent = this.c.getParent();
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeAllViews();
                }
            }
            return this.c;
        } catch (Throwable e2) {
            H5Log.e((String) "H5Fragment", e2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        String sessionId = H5Utils.getString(getArguments(), (String) "sessionId");
        if (!TextUtils.isEmpty(sessionId)) {
            H5Session h5Session = Nebula.getService().getSession(sessionId);
            if (h5Session != null) {
                String workId = h5Session.getServiceWorkerID();
                H5Log.d("H5Fragment", "workId " + workId);
                if (TextUtils.isEmpty(workId) && !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfig("h5_detectWorkId"))) {
                    JSONObject param = new JSONObject();
                    param.put((String) "errorType", (Object) "workIdsNull");
                    param.put((String) "errorCode", (Object) Integer.valueOf(H5ErrorCode.BLANK_SCREEN_DSL_ERROR));
                    H5Page h5Page = h5Session.getTopPage();
                    if (h5Page != null) {
                        H5Log.d("H5Fragment", "send page abnormal event : " + param);
                        h5Page.sendEvent(CommonEvents.H5_PAGE_ABNORMAL, param);
                    }
                }
            }
        }
    }

    private void c() {
        if (this.i) {
            H5Log.d("H5Fragment", "hasPreRender not to postPreRenderRunnable");
            return;
        }
        this.i = true;
        if (H5Utils.getBoolean(getArguments(), (String) "isTinyApp", false)) {
            final Runnable preRunnable = new Runnable() {
                public void run() {
                    if (H5Fragment.this.getActivity() == null || H5Fragment.this.getActivity().isFinishing()) {
                        H5Log.d("H5Fragment", "isFinishing" + H5Fragment.this);
                    } else if (H5Fragment.this.d.get() == null || ((H5Activity) H5Fragment.this.d.get()).isBetweenResumePause()) {
                        H5Fragment.this.b();
                        JSONObject enablePrerenderConfig = H5Utils.parseObject(H5Environment.getConfig("h5_preRenderConfig"));
                        JSONArray appBlackList4Android = H5Utils.getJSONArray(enablePrerenderConfig, "appBlackList4Android", null);
                        JSONArray appForceWhiteList4Android = H5Utils.getJSONArray(enablePrerenderConfig, "appForceWhiteList4Android", null);
                        String appId = H5Utils.getString(H5Fragment.this.getArguments(), (String) "appId");
                        if (appBlackList4Android == null || !appBlackList4Android.contains(appId)) {
                            Uri uri = H5UrlHelper.parseUrl(H5Utils.getString(H5Fragment.this.getArguments(), (String) "url"));
                            if (uri != null) {
                                String fragmentPart = uri.getEncodedFragment();
                                H5Log.d("H5Fragment", uri + " ##h5prerender## fragmentPart " + fragmentPart);
                                if ((appForceWhiteList4Android == null || (appForceWhiteList4Android != null && !appForceWhiteList4Android.contains(appId))) && TextUtils.isEmpty(fragmentPart)) {
                                    H5Log.d("H5Fragment", "##h5prerender## disable nodsl");
                                    return;
                                }
                            }
                            if (TextUtils.equals("YES", H5Utils.getString(enablePrerenderConfig, (String) "shouldUsePreRender")) && H5PreRenderPool.getInstance().getPreFragmentCount() == 0) {
                                String urlSuffix = H5StartParamManager.getInstance().getH5PreRenderPage(appId);
                                H5Log.d("H5Fragment", "##h5prerender## get urlSuffix from appConfig.json " + urlSuffix);
                                if (TextUtils.isEmpty(urlSuffix)) {
                                    urlSuffix = H5Utils.getString(enablePrerenderConfig, (String) "prerenderPage");
                                    H5Log.d("H5Fragment", "##h5prerender## get urlSuffix from online config " + urlSuffix);
                                }
                                if (!TextUtils.isEmpty(urlSuffix)) {
                                    Bundle preStartParam = (Bundle) H5Fragment.this.getArguments().clone();
                                    if (urlSuffix.startsWith("/")) {
                                        urlSuffix = urlSuffix.substring(1);
                                    }
                                    preStartParam.putString("url", H5Utils.getString(H5Fragment.this.getArguments(), (String) H5Param.ONLINE_HOST) + urlSuffix);
                                    preStartParam.putBoolean(H5Param.LONG_ISPRERENDER, true);
                                    preStartParam.remove(H5Fragment.fragmentType);
                                    if (!H5PreRenderPool.getInstance().containsPoolKey(preStartParam, 2)) {
                                        H5Log.d("H5Fragment", "##h5prerender## add prerender in tiny first time");
                                        ((H5Activity) H5Fragment.this.getActivity()).getH5FragmentManager().addPreFragment(preStartParam, 2);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        H5Log.d("H5Fragment", "##h5prerender## disable by appBlackList4Android");
                    } else {
                        H5Log.d("H5Fragment", "##h5prerender## cannot use prerender " + H5Fragment.this);
                    }
                }
            };
            int time = 3000;
            boolean useIdle = true;
            JSONObject jsonObject = H5Utils.parseObject(H5Environment.getConfig("h5_addIdleHandler"));
            if (jsonObject != null && jsonObject.isEmpty()) {
                useIdle = H5Utils.getBoolean(jsonObject, (String) "useIdle", true);
                time = H5Utils.getInt(jsonObject, (String) "time", 3000);
            }
            if (!useIdle) {
                H5Utils.runOnMain(preRunnable, (long) time);
                return;
            }
            final int finalTime = time;
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5Log.d("H5Fragment", "runOnMain " + finalTime);
                    Looper.myQueue().addIdleHandler(new IdleHandler() {
                        public boolean queueIdle() {
                            H5Log.d("H5Fragment", "queueIdle");
                            preRunnable.run();
                            return false;
                        }
                    });
                }
            }, (long) time);
        }
    }

    public void onResume() {
        super.onResume();
        H5Log.d("H5Fragment", "onResume " + this);
        try {
            Nebula.getService().getWebDriverHelper().onWebViewCreated(this.b);
        } catch (Throwable throwable) {
            H5Log.e((String) "H5Fragment", throwable);
        }
        if (this.a != null && this.a.getH5Page() != null) {
            H5PageImpl h5Page = this.a.getH5Page();
            if (H5Utils.getBoolean(h5Page.getParams(), (String) H5Param.LONG_FULLSCREEN, false) && !this.f) {
                h5Page.sendEvent(CommonEvents.H5_PAGE_RESUME, null);
            }
        }
    }

    public void onPause() {
        super.onPause();
        H5Log.d("H5Fragment", "onPause " + this);
        this.f = false;
    }

    public void onStart() {
        super.onStart();
        H5Log.d("H5Fragment", "onStart " + this);
        this.f = true;
        if (VERSION.SDK_INT >= 11 && this.b != null) {
            try {
                this.b.onResume();
            } catch (Throwable t) {
                H5Log.e("H5Fragment", "webview onResume exception.", t);
            }
        }
        if (!this.e) {
            this.e = true;
        } else if (!(this.a == null || this.a.getH5Page() == null)) {
            this.a.getH5Page().sendEvent(CommonEvents.H5_PAGE_RESUME, null);
        }
        setTabbarVisible();
    }

    public void setTabbarVisible() {
        boolean isPrerender = H5Utils.getBoolean(getArguments(), (String) H5Param.LONG_ISPRERENDER, false);
        if (this.a != null && this.a.getH5Page() != null && !isPrerender) {
            H5Session currentSession = this.a.getH5Page().getSession();
            if (currentSession != null) {
                Stack clonePages = (Stack) currentSession.getPages().clone();
                H5Page page = this.a.getH5Page();
                if (page == null) {
                    return;
                }
                if (TextUtils.equals(H5Utils.getString(page.getParams(), (String) fragmentType, (String) "normal"), subtab)) {
                    ((H5Activity) this.d.get()).getSessionTabContainer().setVisibility(0);
                    Iterator it = clonePages.iterator();
                    while (it.hasNext()) {
                        H5Page item = (H5Page) it.next();
                        if ((item == null || !item.equals(page)) && item != null) {
                            Bundle params = item.getParams();
                            if (!TextUtils.equals(H5Utils.getString(params, (String) fragmentType, (String) "normal"), subtab) && !H5Utils.getBoolean(params, (String) H5Param.LONG_ISPRERENDER, false)) {
                                item.exitPage();
                            }
                        }
                    }
                    H5Log.debug("H5Fragment", "switchTab VISIBLE");
                    return;
                }
                if (((H5Activity) this.d.get()).isTabContainerVisible()) {
                    ((H5Activity) this.d.get()).getSessionTabContainer().setVisibility(8);
                }
                H5Log.debug("H5Fragment", "switchTab GONE");
            }
        }
    }

    public void onStop() {
        super.onStop();
        H5Log.d("H5Fragment", "onStop " + this);
        if (VERSION.SDK_INT >= 11 && this.b != null) {
            try {
                this.b.onPause();
            } catch (Throwable t) {
                H5Log.e("H5Fragment", "webview onPause exception.", t);
            }
        }
        if (this.a != null && this.a.getH5Page() != null) {
            this.a.getH5Page().sendEvent(CommonEvents.H5_PAGE_PAUSE, null);
        }
    }

    private void a(Bundle startParams) {
        if (TextUtils.isEmpty(H5Utils.getString(startParams, (String) "url"))) {
            this.a.getH5Page().sendEvent(CommonEvents.H5_AL_SESSION_ENTRY_ERROR, null);
        }
        for (String key : startParams.keySet()) {
            String eventName = null;
            JSONObject param = new JSONObject();
            if ("showFavorites".equals(key)) {
                eventName = H5Utils.getBoolean(startParams, key, false) ? "showFavorites" : CommonEvents.HIDE_FAVORITES;
            } else if (H5Param.LONG_TOOLBAR_MENU.equals(key)) {
                param = H5Utils.parseObject(H5Utils.getString(startParams, key));
                eventName = CommonEvents.SET_TOOL_MENU;
            } else if ("prefetchLocation".equals(key) && H5Utils.getBoolean(startParams, key, false)) {
                eventName = "prefetchLocation";
            }
            if (!TextUtils.isEmpty(eventName)) {
                this.a.getH5Page().sendEvent(eventName, param);
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent intent) {
        if (!(intent.getKeyCode() == 4 && intent.getRepeatCount() == 0) || this.a == null || this.a.getH5Page() == null) {
            return false;
        }
        H5WebChromeClient h5WebChromeClient = this.a.getH5Page().getWebChromeClient();
        if (h5WebChromeClient == null || h5WebChromeClient.mCustomViewCallback == null) {
            H5Log.d("H5Fragment", "onKeyDown H5_PAGE_PHYSICAL_BACK");
            this.a.getH5Page().sendEvent(CommonEvents.H5_PAGE_PHYSICAL_BACK, null);
            return true;
        }
        try {
            h5WebChromeClient.mCustomViewCallback.onCustomViewHidden();
            return true;
        } catch (Exception e2) {
            H5Log.e((String) "H5Fragment", (Throwable) e2);
            return true;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.e) {
            this.e = false;
            H5Log.d("H5Fragment", "onDestroy " + this);
            H5PageImpl h5Page = this.a.getH5Page();
            if (h5Page != null) {
                h5Page.doExitPage(false);
            }
            this.c = null;
            this.a = null;
            this.b = null;
        }
    }

    public void onDestroyView() {
        H5Log.d("H5Fragment", "onDestroyView " + this);
        super.onDestroyView();
    }

    public void onSaveInstanceState(Bundle outState) {
        H5Log.d("H5Fragment", "onSaveInstanceState " + this);
        super.onSaveInstanceState(outState);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        H5Log.d("H5Fragment", "onViewStateRestored " + this);
        super.onViewStateRestored(savedInstanceState);
    }

    public H5Page getH5Page() {
        if (this.a != null) {
            return this.a.getH5Page();
        }
        return null;
    }

    public H5ViewHolder getRootViewHolder() {
        return this.a;
    }

    public void setUseTranslateAnim(boolean use) {
        this.g = use;
    }

    public boolean isUseTranslateAnim() {
        return this.g;
    }

    public void onPageFinish() {
        H5Log.d("H5Fragment", "onPageFinish");
        c();
    }

    public void setShouldResumeWebView(boolean enable) {
        this.h = enable;
    }
}

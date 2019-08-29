package com.alipay.mobile.nebulacore.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.prerender.H5PreRenderPool;
import com.alipay.mobile.nebulacore.util.NebulaUtil;
import com.alipay.mobile.nebulacore.view.H5NavigationBar;
import com.alipay.mobile.nebulacore.web.H5ScriptLoader;
import com.alipay.mobile.nebulacore.web.H5WebView;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class H5FragmentManager {
    public static final String TAG = "H5FragmentManager";
    private FragmentManager a;
    private int b = R.id.h5_fragment;
    private Stack<Fragment> c = new Stack<>();
    /* access modifiers changed from: private */
    public H5Activity d;
    private Fragment e;

    public H5FragmentManager(H5Activity activity) {
        this.d = activity;
        this.a = activity.getSupportFragmentManager();
    }

    public int getFragmentCount() {
        if (this.c != null) {
            return this.c.size();
        }
        return 0;
    }

    public Fragment getCurrentFragment() {
        return this.e;
    }

    public void addFragment(Bundle startParam, boolean withAnim, boolean useTranslateAnim) {
        try {
            H5Log.d(TAG, "addFragment " + startParam);
            boolean isTinyApp = H5Utils.getBoolean(startParam, (String) "isTinyApp", false);
            int mode = isTinyApp ? 2 : 0;
            boolean fromRelaunch = H5Utils.getBoolean(startParam, (String) "fromRelaunch", false);
            boolean useTranslateAnim2 = useTranslateAnim & (!fromRelaunch);
            if (this.d.canUsePreRender() && !fromRelaunch) {
                H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
                if (h5PreRenderPool.getPreFragmentCount() > 0) {
                    startParam.putBoolean(H5Param.LONG_ISPRERENDER, true);
                }
                if (h5PreRenderPool.containsPoolKey(startParam, mode)) {
                    H5Fragment h5Fragment = h5PreRenderPool.getPreFragment(startParam, mode);
                    if (h5Fragment.isAdded()) {
                        H5Log.d(TAG, "##h5prerender## get prerender fragment by mode " + mode);
                        startParam.remove(H5Param.LONG_ISPRERENDER);
                        Bundle preStartParam = h5Fragment.getArguments();
                        int currentIndex = h5PreRenderPool.getCurrentIndex(h5Fragment);
                        H5Log.d(TAG, "##h5prerender## currentIndex " + currentIndex);
                        h5PreRenderPool.putPreFragmentBundle(preStartParam, Integer.valueOf(currentIndex));
                        if (isTinyApp && TextUtils.equals("YES", H5Utils.getString(H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_preRenderConfig")), (String) "shouldUsePreRender"))) {
                            preStartParam.clear();
                            preStartParam.putAll(startParam);
                            H5Page h5Page = h5Fragment.getH5Page();
                            H5ViewHolder h5ViewHolder = h5Fragment.getRootViewHolder();
                            if (!(h5ViewHolder == null || h5Page == null)) {
                                H5NavigationBar h5NavigationBar = h5ViewHolder.getH5NavBar();
                                if (h5NavigationBar != null) {
                                    h5NavigationBar.setPage(h5Page);
                                }
                                H5WebContent h5WebContent = h5ViewHolder.getH5WebContainer();
                                if (h5WebContent != null) {
                                    h5WebContent.refreshView();
                                }
                                h5ViewHolder.refreshView();
                            }
                            if (h5Page != null) {
                                H5WebView webView = (H5WebView) h5Page.getWebView();
                                webView.loadUrl("javascript:location.replace('" + startParam.getString("url") + "');");
                                H5ScriptLoader scriptLoader = ((H5PageImpl) h5Page).getScriptLoader();
                                if (scriptLoader != null) {
                                    scriptLoader.setParamsToWebPage(H5ScriptLoader.startupParams, H5Utils.toJSONObject(preStartParam).toJSONString());
                                }
                                h5Page.getPageData().onPageStarted(startParam.getString("url"));
                                h5Page.getPageData().setCreateScenario(4);
                                h5Page.getPageData().setFunctionHasCallback(0);
                                if (webView.getView() != null && !NebulaUtil.isShowTransAnimate(preStartParam)) {
                                    webView.getView().setBackgroundColor(H5Utils.getInt(preStartParam, (String) "backgroundColor"));
                                }
                            }
                        }
                        preStartParam.remove(H5Param.LONG_ISPRERENDER);
                        pushFragment(h5Fragment, true, startParam, withAnim, useTranslateAnim2);
                        return;
                    }
                }
            }
            startParam.remove(H5Param.LONG_ISPRERENDER);
            H5Fragment h5Fragment2 = null;
            if (RnService.isRnBiz(H5Utils.getString(startParam, (String) "bizType", (String) ""))) {
                if (H5Environment.getRnService() != null) {
                    h5Fragment2 = new RNFragment();
                } else {
                    RNFragment.resetToH5FragmentBundle(startParam);
                }
            }
            if (h5Fragment2 == null) {
                h5Fragment2 = new H5Fragment();
            }
            h5Fragment2.setArguments(startParam);
            pushFragment(h5Fragment2, false, startParam, withAnim, useTranslateAnim2);
        } catch (Exception e2) {
            H5Log.e(TAG, "catch exception ", e2);
        }
    }

    public synchronized void addPreFragment(Bundle startParam, int mode) {
        if (!this.d.isFinishing()) {
            H5Log.d(TAG, "addPreFragment " + startParam);
            H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
            if (!h5PreRenderPool.containsPoolKey(startParam, mode)) {
                H5Fragment h5Fragment = null;
                if (RnService.isRnBiz(H5Utils.getString(startParam, (String) "bizType", (String) ""))) {
                    if (H5Environment.getRnService() != null) {
                        h5Fragment = new RNFragment();
                    } else {
                        RNFragment.resetToH5FragmentBundle(startParam);
                    }
                }
                if (h5Fragment == null) {
                    h5Fragment = new H5Fragment();
                }
                H5Log.d(TAG, "##h5prerender## did addPreFragment");
                h5Fragment.setArguments(startParam);
                FragmentTransaction transaction = this.a.beginTransaction();
                try {
                    transaction.add(this.b, (Fragment) h5Fragment).hide(h5Fragment);
                    transaction.commitAllowingStateLoss();
                } catch (Throwable t) {
                    H5Log.e(TAG, "catch fragment exception ", t);
                }
                h5PreRenderPool.putPreFragment(startParam, h5Fragment);
            }
        }
    }

    public Fragment peekFragment() {
        if (this.c == null || this.c.isEmpty()) {
            return null;
        }
        return this.c.peek();
    }

    public boolean pushFragment(H5Fragment t, boolean isPreRender, Bundle param, boolean withAnim, boolean useTranslateAnim) {
        if (this.d.isFinishing()) {
            return false;
        }
        if (t == null || ((t.isAdded() && !isPreRender) || this.c.contains(t))) {
            return false;
        }
        if (!this.c.isEmpty()) {
            H5Fragment tabTopFragment = null;
            Map tabFragments = null;
            H5Session h5Session = Nebula.getService().getSession(H5Utils.getString(param, (String) "sessionId"));
            if ((h5Session instanceof H5SessionImpl) && ((H5SessionImpl) h5Session).getH5SessionTabManager() != null) {
                tabTopFragment = ((H5SessionImpl) h5Session).getH5SessionTabManager().getCurrentFragment();
                tabFragments = ((H5SessionImpl) h5Session).getH5SessionTabManager().getTabFragments();
            }
            Fragment topFragment = this.c.peek();
            if (tabFragments == null || !tabFragments.containsValue(topFragment)) {
                if (!withAnim || !useTranslateAnim) {
                    detachFragment(topFragment, false);
                } else {
                    if (!(topFragment == null || !(topFragment instanceof H5Fragment) || ((H5Fragment) topFragment).b == null)) {
                        H5Log.d(TAG, "pushwindow animation old webview onPause " + ((H5Fragment) topFragment).b);
                        ((H5Fragment) topFragment).setShouldResumeWebView(true);
                        ((H5Fragment) topFragment).b.onPause();
                    }
                    detachFragment(topFragment, true);
                }
            } else if (!withAnim || !useTranslateAnim) {
                detachFragment(tabTopFragment, false);
            } else {
                if (!(tabTopFragment == null || tabTopFragment.b == null)) {
                    H5Log.d(TAG, "pushwindow animation old webview onPause " + tabTopFragment.b);
                    tabTopFragment.setShouldResumeWebView(true);
                    tabTopFragment.b.onPause();
                }
                detachFragment(tabTopFragment, true);
            }
        }
        if (!this.c.contains(t)) {
            this.c.push(t);
        }
        FragmentTransaction transaction = this.a.beginTransaction();
        boolean containPreKey = this.d.canUsePreRender() && H5PreRenderPool.getInstance().containsPoolKey(t.getArguments(), 1);
        H5Log.d(TAG, "preRender:" + isPreRender + " isAdded():" + t.isAdded() + " isContainKey:" + containPreKey);
        if (!containPreKey || !isPreRender) {
            if (this.d.isFinishing()) {
                return false;
            }
            H5Log.d(TAG, " add fragment");
            if (withAnim) {
                if (useTranslateAnim) {
                    try {
                        H5Log.d(TAG, "fragment use translate anim.");
                        t.setUseTranslateAnim(true);
                        transaction.setCustomAnimations(R.anim.h5_translate_in_left, 0);
                    } catch (Throwable e2) {
                        H5Log.e(TAG, "catch fragment exception ", e2);
                    }
                } else {
                    transaction.setCustomAnimations(R.anim.h5_tabswitch_in, R.anim.h5_tabswitch_out);
                }
            }
            if (this.d.findViewById(this.b) != null) {
                transaction.add(this.b, (Fragment) t).commitAllowingStateLoss();
                this.e = t;
            }
        } else if (this.d.isFinishing()) {
            return false;
        } else {
            H5Page h5Page = t.getH5Page();
            if (h5Page != null) {
                h5Page.getPageData().setContainerVisible(System.currentTimeMillis());
            }
            if (withAnim) {
                if (useTranslateAnim) {
                    H5Log.d(TAG, "fragment use translate anim. preRender");
                    t.setUseTranslateAnim(true);
                    transaction.setCustomAnimations(R.anim.h5_translate_in_left, 0);
                } else {
                    transaction.setCustomAnimations(R.anim.h5_tabswitch_in, R.anim.h5_tabswitch_out);
                }
            }
            t.setTabbarVisible();
            transaction.show(t).commitAllowingStateLoss();
            this.e = t;
            H5PreRenderPool.getInstance().removeFragment(t.getArguments());
            if (h5Page != null) {
                H5Log.d(TAG, "injectPageReady when prerender window present");
                ((H5PageImpl) h5Page).injectPageReady();
            }
            if (H5Utils.getBoolean(t.getArguments(), (String) "isTinyApp", false)) {
                H5LogUtil.logNebulaTech(H5LogData.seedId("H5_PRERENDER_BINGO").param2().add(Build.MANUFACTURER + Build.MODEL + VERSION.SDK_INT, null));
                a(t);
            }
            H5Log.d(TAG, " preRender hit show.");
        }
        return true;
    }

    private void a(final H5Fragment fragment) {
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                if (H5FragmentManager.this.d == null || H5FragmentManager.this.d.isFinishing()) {
                    H5Log.d(H5FragmentManager.TAG, "isFinishing");
                } else if (H5FragmentManager.this.d == null || H5FragmentManager.this.d.isBetweenResumePause()) {
                    JSONObject enablePrerenderConfig = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_preRenderConfig"));
                    JSONArray appBlackList4Android = H5Utils.getJSONArray(enablePrerenderConfig, "appBlackList4Android", null);
                    JSONArray appForceWhiteList4Android = H5Utils.getJSONArray(enablePrerenderConfig, "appForceWhiteList4Android", null);
                    String appId = H5Utils.getString(fragment.getArguments(), (String) "appId");
                    if (appBlackList4Android == null || !appBlackList4Android.contains(appId)) {
                        Uri uri = H5UrlHelper.parseUrl(H5Utils.getString(fragment.getArguments(), (String) "url"));
                        if (uri != null) {
                            String fragmentPart = uri.getEncodedFragment();
                            H5Log.d(H5FragmentManager.TAG, "##h5prerender## fragmentPart " + fragmentPart);
                            if ((appForceWhiteList4Android == null || (appForceWhiteList4Android != null && !appForceWhiteList4Android.contains(appId))) && TextUtils.isEmpty(fragmentPart)) {
                                H5Log.d(H5FragmentManager.TAG, "##h5prerender## disable nodsl");
                                return;
                            }
                        }
                        if (TextUtils.equals("YES", H5Utils.getString(enablePrerenderConfig, (String) "shouldUsePreRender")) && H5PreRenderPool.getInstance().getPreFragmentCount() == 0) {
                            String urlSuffix = H5StartParamManager.getInstance().getH5PreRenderPage(appId);
                            H5Log.d(H5FragmentManager.TAG, "##h5prerender## get urlSuffix from appConfig.json " + urlSuffix);
                            if (TextUtils.isEmpty(urlSuffix)) {
                                urlSuffix = H5Utils.getString(enablePrerenderConfig, (String) "prerenderPage");
                                H5Log.d(H5FragmentManager.TAG, "##h5prerender## get urlSuffix from online config " + urlSuffix);
                            }
                            if (!TextUtils.isEmpty(urlSuffix)) {
                                Bundle preStartParam = (Bundle) fragment.getArguments().clone();
                                if (urlSuffix.startsWith("/")) {
                                    urlSuffix = urlSuffix.substring(1);
                                }
                                preStartParam.putString("url", H5Utils.getString(fragment.getArguments(), (String) H5Param.ONLINE_HOST) + urlSuffix);
                                preStartParam.putBoolean(H5Param.LONG_ISPRERENDER, true);
                                preStartParam.remove(H5Fragment.fragmentType);
                                if (!H5PreRenderPool.getInstance().containsPoolKey(preStartParam, 2)) {
                                    H5Log.d(H5FragmentManager.TAG, "##h5prerender## add prerender in tiny no first time");
                                    H5FragmentManager.this.addPreFragment(preStartParam, 2);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    H5Log.d(H5FragmentManager.TAG, "##h5prerender## disable by appBlackList4Android");
                } else {
                    H5Log.d(H5FragmentManager.TAG, "##h5prerender## cannot use prerender");
                }
            }
        }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
    }

    public boolean attachFragment(Fragment fragment, boolean withAnim, boolean useTranslateAnim) {
        if (this.d.isFinishing() || fragment == null) {
            H5Log.d(TAG, "activity is finishing");
            return false;
        } else if (fragment.isVisible()) {
            if (fragment instanceof H5Fragment) {
                ((H5Fragment) fragment).setTabbarVisible();
            }
            this.e = fragment;
            return false;
        } else {
            a((String) "attachFragment");
            FragmentTransaction transaction = this.a.beginTransaction();
            if (withAnim) {
                if (useTranslateAnim) {
                    transaction.setCustomAnimations(R.anim.h5_translate_in_right, 0);
                } else {
                    transaction.setCustomAnimations(R.anim.h5_tabswitch_in, R.anim.h5_tabswitch_out);
                }
            }
            transaction.attach(fragment).commitAllowingStateLoss();
            this.e = fragment;
            return true;
        }
    }

    public boolean detachFragment(final Fragment fragment, boolean withTranslateAnim) {
        if (this.d.isFinishing()) {
            H5Log.d(TAG, "activity is finishing");
            return false;
        } else if (fragment == null || fragment.isHidden()) {
            return false;
        } else {
            a((String) "detachFragment");
            final FragmentTransaction transaction = this.a.beginTransaction();
            if (H5Utils.getBoolean(fragment.getArguments(), (String) H5Param.LONG_DELAY_RENDER, false)) {
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        if (fragment.getActivity() == null || !fragment.getActivity().isFinishing()) {
                            transaction.detach(fragment).commitAllowingStateLoss();
                        }
                    }
                }, 600);
            } else {
                if (withTranslateAnim) {
                    try {
                        transaction.setCustomAnimations(0, R.anim.h5_translate_out_left);
                    } catch (Throwable t) {
                        H5Log.e((String) TAG, t);
                    }
                }
                transaction.detach(fragment).commitAllowingStateLoss();
            }
            return true;
        }
    }

    public boolean removeFragment(H5Fragment t, Bundle param, boolean exitTabScene) {
        if (this.d.isFinishing()) {
            H5Log.d(TAG, "activity is finishing");
            return false;
        } else if (t == null) {
            return false;
        } else {
            if (this.d.canUsePreRender() && !this.c.contains(t) && !H5PreRenderPool.getInstance().containsPoolKey(t.getArguments(), 1)) {
                return false;
            }
            H5Session h5Session = Nebula.getService().getSession(H5Utils.getString(param, (String) "sessionId"));
            H5Fragment tabTopFragment = null;
            Map tabFragments = null;
            if ((h5Session instanceof H5SessionImpl) && ((H5SessionImpl) h5Session).getH5SessionTabManager() != null) {
                int countTabFragments = ((H5SessionImpl) h5Session).getH5SessionTabManager().countTabFragments();
                tabTopFragment = ((H5SessionImpl) h5Session).getH5SessionTabManager().getCurrentFragment();
                tabFragments = ((H5SessionImpl) h5Session).getH5SessionTabManager().getTabFragments();
                if (this.c.size() == countTabFragments && !exitTabScene) {
                    return false;
                }
            }
            if (this.c.size() <= 1) {
                return false;
            }
            if (t.getActivity() != null && t.getActivity().isFinishing()) {
                return true;
            }
            boolean top = t == this.c.peek();
            if (tabFragments == null || !tabFragments.containsValue(t) || exitTabScene) {
                FragmentTransaction transaction = this.a.beginTransaction();
                if (t.isUseTranslateAnim()) {
                    if (t.b != null) {
                        H5Log.d(TAG, "pushwindow animation new webview onPause " + t.b);
                        t.b.onPause();
                    }
                    transaction.setCustomAnimations(0, R.anim.h5_translate_out_right);
                }
                transaction.remove(t).commitAllowingStateLoss();
                this.c.remove(t);
            }
            if (top && !this.c.isEmpty()) {
                Fragment topFragment = this.c.peek();
                if (tabFragments == null || !tabFragments.containsValue(topFragment)) {
                    if (t.isUseTranslateAnim()) {
                        attachFragment(topFragment, true, true);
                    } else {
                        attachFragment(topFragment, false, false);
                    }
                } else if (t.isUseTranslateAnim()) {
                    attachFragment(tabTopFragment, true, true);
                } else {
                    attachFragment(tabTopFragment, false, false);
                }
            }
            return true;
        }
    }

    public void clearPreFragment(int location, int length) {
        H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
        H5Log.d(TAG, "clearPreFragment location " + location + ", length " + length);
        List preFragmentList = h5PreRenderPool.getPreFragmentList();
        List preParamContentList = h5PreRenderPool.getPreParamContentList();
        List preUrlList = h5PreRenderPool.getPreUrlList();
        for (int i = location; i <= length; i++) {
            int size4Fragment = preFragmentList.size();
            int size4Content = preParamContentList.size();
            int size4Url = preUrlList.size();
            if (location >= 0 && location < size4Fragment) {
                detachFragment(preFragmentList.get(location), false);
                preFragmentList.remove(location);
            }
            if (location >= 0 && location < size4Content) {
                preParamContentList.remove(location);
            }
            if (location >= 0 && location < size4Url) {
                preUrlList.remove(location);
            }
        }
    }

    private void a(String funcName) {
        try {
            throw new Exception("打印调用堆栈（不是闪退）：" + toString());
        } catch (Exception e2) {
            H5Log.w(TAG, funcName + " track ", e2);
        }
    }
}

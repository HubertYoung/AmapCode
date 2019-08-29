package com.alipay.mobile.nebulacore.plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.core.H5SessionImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.prerender.H5PreRenderPool;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.nebulacore.ui.H5FragmentManager;
import com.autonavi.minimap.ajx3.util.Constants;

public class H5PreRenderPlugin extends H5SimplePlugin {
    public static final String KEY_PRE_RANDER_MAX = "H5_preRenderMax";
    private H5SessionImpl a;
    private Boolean b = Boolean.valueOf(false);

    public H5PreRenderPlugin(H5SessionImpl h5Session) {
        this.a = h5Session;
    }

    public void onRelease() {
        this.a = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.PRE_RENDER);
        filter.addAction(CommonEvents.CLEAR_RENDER);
        filter.addAction(CommonEvents.FINISH_RENDER);
        filter.addAction("showFavorites");
        filter.addAction(CommonEvents.HIDE_FAVORITES);
    }

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if ("showFavorites".equals(action)) {
            this.b = Boolean.valueOf(true);
        } else if (CommonEvents.HIDE_FAVORITES.equals(action)) {
            this.b = Boolean.valueOf(false);
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.PRE_RENDER.equals(action)) {
            a(event, true);
            a(event, bridgeContext);
            H5Log.d("H5PreRenderPlugin", "pre_render");
        } else if (CommonEvents.CLEAR_RENDER.equals(action)) {
            a(event, false);
            b(event, bridgeContext);
            H5Log.d("H5PreRenderPlugin", "clear_render");
            bridgeContext.sendSuccess();
        } else if (CommonEvents.FINISH_RENDER.equals(action)) {
            bridgeContext.sendSuccess();
        }
        return true;
    }

    private static void a(H5Event event, boolean open) {
        if (event.getActivity() instanceof H5Activity) {
            H5Log.d("H5PreRenderPlugin", "openPreRenderByPlugin:" + open);
            ((H5Activity) event.getActivity()).openPreRenderByPlugin(open);
        }
    }

    private synchronized void a(H5Event event, H5BridgeContext context) {
        H5CoreNode target = event.getTarget();
        if (!(target instanceof H5Page)) {
            H5Log.w("H5PreRenderPlugin", "invalid target!");
        } else {
            H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
            H5PageImpl h5Page = (H5PageImpl) target;
            JSONObject params = event.getParam();
            JSONObject windowParams = H5Utils.getJSONObject(params, "windowParams", null);
            String currentUrl = h5Page.getUrl();
            String kickOut = H5Utils.getString(params, (String) H5Param.KICK_OUT, (String) "first");
            if (windowParams == null || windowParams.isEmpty()) {
                JSONObject result = new JSONObject();
                if (h5PreRenderPool.getPreFragmentCount() == 0) {
                    result.put((String) "error", (Object) "2");
                    result.put((String) "message", (Object) H5Environment.getResources().getString(R.string.h5_wrongparam));
                    context.sendBridgeResult(result);
                } else if (!Constants.ANIMATOR_NONE.equals(kickOut)) {
                    a(event, kickOut);
                    result.put((String) "urls", (Object) h5PreRenderPool.getUrls());
                    result.put((String) "success", (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(result);
                }
            } else {
                String url = H5Utils.getString(windowParams, (String) "url");
                JSONObject param = H5Utils.getJSONObject(windowParams, "param", null);
                Bundle oldParams = new Bundle();
                oldParams.putAll(h5Page.getParams());
                if (oldParams.containsKey("preRpc")) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete preRpc startparam");
                    oldParams.remove("preRpc");
                }
                if (oldParams.containsKey(H5Param.LONG_NAV_SEARCH_BAR_TYPE)) {
                    oldParams.remove(H5Param.LONG_NAV_SEARCH_BAR_TYPE);
                }
                if (oldParams.containsKey("backgroundColor")) {
                    oldParams.remove("backgroundColor");
                }
                if (oldParams.containsKey(H5Param.LONG_TRANSPARENT_TITLE)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete transparentTitle startparam");
                    oldParams.remove(H5Param.LONG_TRANSPARENT_TITLE);
                }
                if (oldParams.containsKey(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete transparentTitleTextAuto startparam");
                    oldParams.remove(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO);
                }
                if (oldParams.containsKey(H5Param.LONG_TITLE_IMAGE)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete titleImage startparam");
                    oldParams.remove(H5Param.LONG_TITLE_IMAGE);
                }
                if (oldParams.containsKey(H5Param.LONG_BOUNCE_TOP_COLOR)) {
                    oldParams.remove(H5Param.LONG_BOUNCE_TOP_COLOR);
                }
                if (oldParams.containsKey(H5Fragment.fragmentType)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete fragmentType startparam");
                    oldParams.remove(H5Fragment.fragmentType);
                }
                if (oldParams.containsKey(H5Param.CREATEPAGESENCE)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete createPageSence startparam");
                    oldParams.remove(H5Param.CREATEPAGESENCE);
                }
                if (oldParams.containsKey(H5Param.PULL_REFRESH_STYLE)) {
                    H5Log.d("H5PreRenderPlugin", "in H5SessionPlugin delete pullRefreshStyle startparam");
                    oldParams.remove(H5Param.PULL_REFRESH_STYLE);
                }
                if (oldParams.containsKey("closeAllWindow")) {
                    oldParams.remove("closeAllWindow");
                }
                if (param != null && !param.isEmpty()) {
                    Bundle newParams = new Bundle();
                    H5Utils.toBundle(newParams, param);
                    Bundle newParams2 = H5ParamParser.parse(newParams, false);
                    for (String key : newParams2.keySet()) {
                        H5ParamParser.remove(oldParams, key);
                    }
                    oldParams.putAll(newParams2);
                }
                oldParams.putBoolean("showFavorites", this.b.booleanValue());
                String url2 = H5Utils.getAbsoluteUrl(currentUrl, url, this.a.getParams());
                H5Log.d("H5PreRenderPlugin", "pushWindow url " + url2);
                oldParams.putString("url", url2);
                oldParams.putString(H5Param.REFERER, currentUrl);
                oldParams.putBoolean(H5Param.LONG_ISPRERENDER, true);
                String launchParamsTag = H5Utils.getString(params, (String) H5StartParamManager.launchParamsTag);
                if (!TextUtils.isEmpty(launchParamsTag)) {
                    Bundle launcher = H5StartParamManager.getInstance().getH5StartParam(H5Utils.getString(oldParams, (String) "appId"), launchParamsTag);
                    if (launcher != null && !launcher.isEmpty()) {
                        H5Log.d("H5PreRenderPlugin", "launcher " + launcher);
                        oldParams.putAll(launcher);
                    }
                }
                H5ParamParser.parseMagicOptions(oldParams, "H5PreRenderPlugin");
                H5ParamParser.parse(oldParams, false);
                if (!(h5Page == null || h5Page.getContext() == null || h5Page.getContext().getContext() == null)) {
                    oldParams.putString(H5Param.FROM_TYPE, "pushWindow");
                    if (h5PreRenderPool.getPreFragmentCount() != 0 && !h5PreRenderPool.containsPoolKey(oldParams, 0) && h5PreRenderPool.getPreFragmentCount() >= a()) {
                        if (kickOut.equals(Constants.ANIMATOR_NONE)) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put((String) "urls", (Object) h5PreRenderPool.getUrls());
                            jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
                            context.sendBridgeResult(jsonObject);
                        } else {
                            a(event, kickOut);
                        }
                    }
                    if (event.getActivity() instanceof H5Activity) {
                        H5Log.d("H5PreRenderPlugin", "##h5prerender## add prerender by jsapi");
                        ((H5Activity) event.getActivity()).getH5FragmentManager().addPreFragment(oldParams, 0);
                    }
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put((String) "urls", (Object) h5PreRenderPool.getUrls());
                    jsonObject2.put((String) "success", (Object) Boolean.valueOf(true));
                    context.sendBridgeResult(jsonObject2);
                }
            }
        }
    }

    private static int a() {
        String preRanderMax = H5Environment.getConfig(KEY_PRE_RANDER_MAX);
        if (preRanderMax == null) {
            return 3;
        }
        try {
            if (!preRanderMax.equals("")) {
                return Integer.parseInt(preRanderMax);
            }
            return 3;
        } catch (Exception globalException) {
            H5Log.e("H5PreRenderPlugin", "exception detail.", globalException);
            return 3;
        }
    }

    private static void a(H5Event event, String kickOut) {
        H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
        if (h5PreRenderPool.getPreFragmentCount() != 0 && (event.getActivity() instanceof H5Activity)) {
            H5FragmentManager h5FragmentManager = ((H5Activity) event.getActivity()).getH5FragmentManager();
            if (kickOut.equals("last")) {
                h5FragmentManager.clearPreFragment(h5PreRenderPool.getPreFragmentCount() - 1, h5PreRenderPool.getPreFragmentCount() - 1);
            } else {
                h5FragmentManager.clearPreFragment(0, 0);
            }
        }
    }

    private static void b(H5Event event, H5BridgeContext context) {
        JSONObject rangeParam = H5Utils.getJSONObject(event.getParam(), "range", null);
        int location = H5Utils.getInt(rangeParam, (String) "location", 0);
        int length = H5Utils.getInt(rangeParam, (String) "length", 1);
        H5PreRenderPool h5PreRenderPool = H5PreRenderPool.getInstance();
        if (h5PreRenderPool.getPreFragmentCount() == 0) {
            context.sendBridgeResult("success", "false");
        } else if (location < 0 || length < 0) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) "2");
            result.put((String) "message", (Object) H5Environment.getResources().getString(R.string.h5_wrongparam));
            context.sendBridgeResult(result);
        } else if (location > length) {
            JSONObject result2 = new JSONObject();
            result2.put((String) "error", (Object) "2");
            result2.put((String) "message", (Object) H5Environment.getResources().getString(R.string.h5_wrongparam));
            context.sendBridgeResult(result2);
        } else if (location > h5PreRenderPool.getPreFragmentCount() - 1) {
            JSONObject result3 = new JSONObject();
            result3.put((String) "error", (Object) "2");
            result3.put((String) "message", (Object) H5Environment.getResources().getString(R.string.h5_wrongparam));
            context.sendBridgeResult(result3);
        } else {
            if (length > h5PreRenderPool.getPreFragmentCount() - 1) {
                length = h5PreRenderPool.getPreFragmentCount() - 1;
            }
            if (event.getActivity() instanceof H5Activity) {
                ((H5Activity) event.getActivity()).getH5FragmentManager().clearPreFragment(location, length);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "urls", (Object) h5PreRenderPool.getUrls());
            jsonObject.put((String) "success", (Object) Boolean.valueOf(true));
            context.sendBridgeResult(jsonObject);
        }
    }
}

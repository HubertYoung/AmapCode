package com.alipay.mobile.nebulacore.embedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedView;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5EmbededViewProviderImpl implements H5EmbededViewProvider {
    private Context a;
    private H5Page b;
    private Map<String, IH5EmbedView> c;
    private List<String> d;
    private final Map<String, List<Pair<H5BridgeContext, JSONObject>>> e = new ConcurrentHashMap();
    private final Set<String> f = new HashSet<String>() {
        {
            add("map");
            add("input");
        }
    };

    public H5EmbededViewProviderImpl(Context context, H5Page h5Page) {
        this.a = context;
        this.b = h5Page;
        this.c = new ConcurrentHashMap();
        this.d = new ArrayList();
    }

    public void addPendingMessage(String elementId, H5BridgeContext context, JSONObject message) {
        H5Log.d("H5EmbededViewProviderImpl", "addPendingMessage: " + elementId + ", message: " + message);
        synchronized (this.e) {
            List pendingArray = this.e.get(elementId);
            if (pendingArray == null) {
                pendingArray = new ArrayList();
                this.e.put(elementId, pendingArray);
            }
            pendingArray.add(new Pair(context, message));
        }
    }

    public View getEmbedView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl getEmbedView, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    String type = params.get("type");
                    String id = a(viewId, params);
                    if (!this.c.containsKey(id)) {
                        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl getEmbedView init");
                        IH5EmbedView embedViewWrapper = a(type);
                        if (embedViewWrapper != null) {
                            if (TextUtils.equals(type, "newembedbase")) {
                                clearBaseView();
                                this.d.add(id);
                            }
                            this.c.put(id, embedViewWrapper);
                            View view = embedViewWrapper.getView(width, height, viewId, mType, params);
                            if (!(this.b == null || this.b.getBridge() == null)) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put((String) "elementid", (Object) id);
                                this.b.getBridge().sendToWeb("nbcomponent.canrender", jsonObject, null);
                            }
                            if (!"canvas".equals(type)) {
                                return view;
                            }
                            a(id, embedViewWrapper);
                            return view;
                        }
                    } else {
                        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl getEmbedView reuse");
                        IH5EmbedView embedViewWrapper2 = this.c.get(id);
                        if (this.f.contains(type)) {
                            return embedViewWrapper2.getSpecialRestoreView(width, height, viewId, mType, params);
                        }
                        return embedViewWrapper2.getView(width, height, viewId, mType, params);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "getEmbedView catch throwable ", t);
            }
        }
        return null;
    }

    private void a(String id, IH5EmbedView embedViewWrapper) {
        synchronized (this.e) {
            List<Pair> pendingMessages = this.e.remove(id);
            H5Log.d("H5EmbededViewProviderImpl", "flushPendingMessages id: " + id + " size: " + (pendingMessages == null ? 0 : pendingMessages.size()));
            if (pendingMessages != null) {
                for (Pair pair : pendingMessages) {
                    String actionType = H5Utils.getString((JSONObject) pair.second, (String) "actionType");
                    JSONObject data = H5Utils.getJSONObject((JSONObject) pair.second, "data", new JSONObject());
                    data.put((String) "element", (Object) id);
                    embedViewWrapper.onReceivedMessage(actionType, data, (H5BridgeContext) pair.first);
                }
            }
        }
    }

    public void onEmbedViewAttachedToWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onEmbedViewAttachedToWebView, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewAttachedToWebView(width, height, viewId, mType, params);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "onEmbedViewAttachedToWebView catch throwable ", t);
            }
        }
    }

    public void onEmbedViewDetachedFromWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onEmbedViewDetachedFromWebView, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewDetachedFromWebView(width, height, viewId, mType, params);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "onEmbedViewDetachedFromWebView catch throwable ", t);
            }
        }
    }

    public void onEmbedViewDestory(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onEmbedViewDestory, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewDestory(width, height, viewId, mType, params);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "onEmbedViewDestory catch throwable ", t);
            }
        }
    }

    public void onEmbedViewParamChanged(int width, int height, String viewId, String mType, Map<String, String> params, String[] name, String[] value) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onEmbedViewParamChanged, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewParamChanged(width, height, viewId, mType, params, name, value);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "onEmbedViewParamChanged catch throwable ", t);
            }
        }
    }

    public void onEmbedViewVisibilityChanged(int width, int height, String viewId, String mType, Map<String, String> params, int reason) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onEmbedViewVisibilityChanged, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty() && this.c != null) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewVisibilityChanged(width, height, viewId, mType, params, reason);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "onEmbedViewVisibilityChanged catch throwable ", t);
            }
        }
    }

    public void onWebViewResume() {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onWebViewResume");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5EmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewResume();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5EmbededViewProviderImpl", "onWebViewResume catch throwable ", t);
        }
    }

    public void onWebViewPause() {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onWebViewPause");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5EmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewPause();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5EmbededViewProviderImpl", "onWebViewPause catch throwable ", t);
        }
    }

    public void onWebViewDestory() {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl onWebViewDestory");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5EmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewDestory();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5EmbededViewProviderImpl", "onWebViewDestory catch throwable ", t);
        }
    }

    private IH5EmbedView a(String type) {
        H5EmbedViewConfig config = H5EmbededViewConfigManager.getInstance().getConfig(type);
        if (config == null) {
            return null;
        }
        String bundleName = config.getBundleName();
        String clazzName = config.getClassName();
        Class clazz = H5Environment.getClass(bundleName, clazzName);
        if (clazz == null) {
            H5Log.w("H5EmbededViewProviderImpl", "getEmbedViewWrapper reflect clazz == null " + clazzName);
            return null;
        }
        try {
            if (!IH5EmbedView.class.isAssignableFrom(clazz)) {
                return null;
            }
            IH5EmbedView embedView = (IH5EmbedView) clazz.newInstance();
            if (embedView == null) {
                H5Log.w("H5EmbededViewProviderImpl", "getEmbedViewWrapper reflect embedView == null " + clazzName);
                return null;
            }
            embedView.onCreate(this.a, this.b);
            return embedView;
        } catch (Throwable t) {
            H5Log.e("H5EmbededViewProviderImpl", "getEmbedViewWrapper reflect catch exception " + clazzName, t);
            return null;
        }
    }

    public void releaseView() {
        H5Log.d("H5EmbededViewProviderImpl", "releaseView");
        synchronized (this.e) {
            this.e.clear();
        }
        this.c.clear();
        this.c = null;
        this.a = null;
        this.b = null;
    }

    public void clearBaseView() {
        if (this.c != null && !this.c.isEmpty() && !this.d.isEmpty()) {
            for (String id : this.d) {
                if (!TextUtils.isEmpty(id)) {
                    this.c.remove(id);
                }
            }
            this.d.clear();
        }
    }

    public void onRequestPermissionResult(int resultCode, String[] permissions, int[] grantResults) {
        if (this.c != null) {
            for (IH5EmbedView onRequestPermissionResult : this.c.values()) {
                onRequestPermissionResult.onRequestPermissionResult(resultCode, permissions, grantResults);
            }
        }
    }

    public IH5EmbedView getEmbedViewWrapperById(String id) {
        return this.c.get(id);
    }

    public Bitmap getSnapshot(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d("H5EmbededViewProviderImpl", "H5EmbededViewProviderImpl getSnapshot, viewId " + viewId + ", mType " + mType);
        if (params != null) {
            try {
                if (!params.isEmpty()) {
                    IH5EmbedView embedViewWrapper = this.c.get(a(viewId, params));
                    if (embedViewWrapper != null) {
                        return embedViewWrapper.getSnapshot(width, height, viewId, mType, params);
                    }
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbededViewProviderImpl", "getSnapshot catch throwable ", t);
            }
        }
        return null;
    }

    public void triggerPreSnapshot() {
        try {
            if (this.c != null && !this.c.isEmpty()) {
                for (IH5EmbedView triggerPreSnapshot : this.c.values()) {
                    triggerPreSnapshot.triggerPreSnapshot();
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5EmbededViewProviderImpl", "triggerPreSnapshot catch throwable ", t);
        }
    }

    private static String a(String viewId, Map<String, String> params) {
        String type = params.get("type");
        if (TextUtils.equals("TINY_COMPONENT", type) || TextUtils.equals("newembedbase", type)) {
            return viewId;
        }
        return params.get("id");
    }
}

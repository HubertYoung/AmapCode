package com.alipay.mobile.nebulacore.embedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.config.H5EmbedViewConfig;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.newembedview.IH5NewEmbedView;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5NewEmbedViewProviderImpl implements H5NewEmbedViewProvider {
    private Context a;
    private H5Page b;
    private Map<String, IH5NewEmbedView> c = new ConcurrentHashMap();

    public H5NewEmbedViewProviderImpl(Context context, H5Page h5Page) {
        this.a = context;
        this.b = h5Page;
    }

    public View getEmbedView(String id, String type) {
        try {
            if (!this.c.containsKey(id)) {
                H5Log.d("H5NewEmbedViewProviderImpl", "H5NewEmbedViewProviderImpl getEmbedView init");
                IH5NewEmbedView embedViewWrapper = a(type);
                if (embedViewWrapper != null) {
                    this.c.put(id, embedViewWrapper);
                    return embedViewWrapper.getView();
                }
            } else {
                H5Log.d("H5NewEmbedViewProviderImpl", "H5NewEmbedViewProviderImpl getEmbedView reuse");
                IH5NewEmbedView embedViewWrapper2 = this.c.get(id);
                if (embedViewWrapper2 != null) {
                    return embedViewWrapper2.getView();
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "getEmbedView catch throwable ", t);
        }
        return null;
    }

    public void onEmbedViewAttachedToWebView() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onEmbedViewAttachedToWebView");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewAttachedToWebView();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onEmbedViewAttachedToWebView catch throwable ", t);
        }
    }

    public void onEmbedViewDetachedFromWebView() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onEmbedViewDetachedFromWebView");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewDetachedFromWebView();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onEmbedViewDetachedFromWebView catch throwable ", t);
        }
    }

    public void onEmbedViewDestory() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onEmbedViewDestory");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewDestory();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onEmbedViewDestory catch throwable ", t);
        }
        this.c.clear();
        if (this.b != null) {
            this.b.setNewEmbedViewRoot(null);
        }
    }

    public void onEmbedViewParamChanged() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onEmbedViewParamChanged");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewParamChanged();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onEmbedViewParamChanged catch throwable ", t);
        }
    }

    public void onEmbedViewVisibilityChanged() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onEmbedViewVisibilityChanged");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onEmbedViewVisibilityChanged();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onEmbedViewVisibilityChanged catch throwable ", t);
        }
    }

    public void onWebViewResume() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onWebViewResume");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewResume();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onWebViewResume catch throwable ", t);
        }
    }

    public void onWebViewPause() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onWebViewPause");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewPause();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onWebViewPause catch throwable ", t);
        }
    }

    public void onWebViewDestory() {
        H5Log.d("H5NewEmbedViewProviderImpl", "onWebViewDestory");
        try {
            if (this.c != null) {
                for (String item : this.c.keySet()) {
                    IH5NewEmbedView embedViewWrapper = this.c.get(item);
                    if (embedViewWrapper != null) {
                        embedViewWrapper.onWebViewDestory();
                    }
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "onWebViewDestory catch throwable ", t);
        }
    }

    public void deleteView(String id) {
        H5Log.d("H5NewEmbedViewProviderImpl", "deleteView id" + id);
        try {
            if (this.c != null) {
                IH5NewEmbedView newEmbedView = this.c.remove(id);
                if (newEmbedView != null) {
                    newEmbedView.onEmbedViewDestory();
                }
            }
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "deleteView catch throwable ", t);
        }
    }

    public void releaseView() {
        H5Log.d("H5NewEmbedViewProviderImpl", "releaseView");
        this.c.clear();
        this.c = null;
        this.b = null;
        this.a = null;
    }

    public void clearAllView() {
        if (this.c != null && !this.c.isEmpty()) {
            this.c.clear();
        }
    }

    public IH5NewEmbedView getNewEmbedViewById(String id) {
        return this.c.get(id);
    }

    public Bitmap getSnapshot() {
        return null;
    }

    public void triggerPreSnapshot() {
    }

    private IH5NewEmbedView a(String type) {
        H5EmbedViewConfig config = H5NewEmbedViewConfigManager.getInstance().getConfig(type);
        if (config == null) {
            return null;
        }
        String bundleName = config.getBundleName();
        String clazzName = config.getClassName();
        Class clazz = H5Environment.getClass(bundleName, clazzName);
        if (clazz == null) {
            H5Log.w("H5NewEmbedViewProviderImpl", "generateNewEmbedViewWrapper reflect clazz == null " + clazzName);
            return null;
        }
        try {
            if (!IH5NewEmbedView.class.isAssignableFrom(clazz)) {
                return null;
            }
            IH5NewEmbedView embedView = (IH5NewEmbedView) clazz.newInstance();
            if (embedView == null) {
                H5Log.w("H5NewEmbedViewProviderImpl", "generateNewEmbedViewWrapper reflect embedView == null " + clazzName);
                return null;
            }
            embedView.onCreate(this.a, this.b);
            return embedView;
        } catch (Throwable t) {
            H5Log.e("H5NewEmbedViewProviderImpl", "generateNewEmbedViewWrapper reflect catch exception " + clazzName, t);
            return null;
        }
    }
}

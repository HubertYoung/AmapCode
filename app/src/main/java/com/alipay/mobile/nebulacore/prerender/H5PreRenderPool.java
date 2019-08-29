package com.alipay.mobile.nebulacore.prerender;

import android.os.Bundle;
import android.os.Handler;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class H5PreRenderPool {
    public static final int CONTAINSMODE_CONTENT = 0;
    public static final int CONTAINSMODE_CONTENT_URL = 2;
    public static final int CONTAINSMODE_POINT = 1;
    private static H5PreRenderPool a;
    private List<H5Fragment> b;
    private Map<Bundle, Integer> c;
    private List<JSONObject> d;
    private List<String> e;
    private boolean f;
    private Handler g;
    private Runnable h;

    private H5PreRenderPool() {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.b = new ArrayList();
        this.c = new HashMap();
        this.d = new ArrayList();
        this.e = new ArrayList();
    }

    public static H5PreRenderPool getInstance() {
        if (a == null) {
            synchronized (H5PreRenderPool.class) {
                try {
                    if (a == null) {
                        a = new H5PreRenderPool();
                    }
                }
            }
        }
        return a;
    }

    public int getPreFragmentCount() {
        if (this.b != null) {
            return this.b.size();
        }
        return 0;
    }

    public JSONArray getUrls() {
        JSONArray jsonArray = new JSONArray();
        if (this.e != null) {
            for (String url : this.e) {
                jsonArray.add(url);
            }
        }
        return jsonArray;
    }

    public boolean containsPoolKey(Bundle key, int mode) {
        switch (mode) {
            case 0:
                if (this.d == null || !this.d.contains(H5Utils.toJSONObject(key))) {
                    return false;
                }
                return true;
            case 1:
                if (this.c == null || !this.c.containsKey(key)) {
                    return false;
                }
                return true;
            case 2:
                String url = a(H5Utils.getString(key, (String) "url"));
                if (this.e == null || !this.e.contains(url)) {
                    return false;
                }
                return true;
            default:
                return false;
        }
    }

    private static String a(String url) {
        int anchorIndex = url.indexOf(35);
        if (anchorIndex != -1) {
            return url.substring(0, anchorIndex);
        }
        return url;
    }

    public void putPreFragment(Bundle bundle, H5Fragment h5Fragment) {
        if (this.d != null) {
            this.d.add(H5Utils.toJSONObject(bundle));
        }
        if (this.e != null) {
            this.e.add(a(H5Utils.getString(bundle, (String) "url")));
        }
        if (this.b != null) {
            this.b.add(h5Fragment);
        }
    }

    public int getCurrentIndex(H5Fragment h5Fragment) {
        if (this.b != null) {
            return this.b.indexOf(h5Fragment);
        }
        return -1;
    }

    public void putPreFragmentBundle(Bundle bundle, Integer index) {
        if (this.c != null) {
            this.c.put(bundle, index);
        }
    }

    public H5Fragment getPreFragment(Bundle key, int mode) {
        int index3;
        int index2;
        int index;
        switch (mode) {
            case 0:
                if (this.d != null) {
                    index = this.d.indexOf(H5Utils.toJSONObject(key));
                } else {
                    index = -1;
                }
                if (index != -1) {
                    return this.b.get(index);
                }
                return null;
            case 1:
                if (this.c != null) {
                    index2 = this.c.get(key).intValue();
                } else {
                    index2 = -1;
                }
                if (index2 != -1) {
                    return this.b.get(index2);
                }
                return null;
            case 2:
                String url = a(H5Utils.getString(key, (String) "url"));
                if (this.e != null) {
                    index3 = this.e.indexOf(url);
                } else {
                    index3 = -1;
                }
                if (index3 != -1) {
                    return this.b.get(index3);
                }
                return null;
            default:
                return null;
        }
    }

    public void removeFragment(Bundle key) {
        Integer indexObj = this.c.get(key);
        int index = indexObj != null ? indexObj.intValue() : -1;
        if (index >= 0) {
            this.c.remove(key);
        }
        int size = this.d.size();
        if (index >= 0 && index < size) {
            this.d.remove(index);
        }
        int size2 = this.b.size();
        if (index >= 0 && index < size2) {
            this.b.remove(index);
        }
        int size3 = this.e.size();
        if (index >= 0 && index < size3) {
            this.e.remove(index);
        }
    }

    public List<H5Fragment> getPreFragmentList() {
        return this.b;
    }

    public Map<Bundle, Integer> getPreParamPointMap() {
        return this.c;
    }

    public List<JSONObject> getPreParamContentList() {
        return this.d;
    }

    public List<String> getPreUrlList() {
        return this.e;
    }

    public boolean isIntercept() {
        return this.f;
    }

    public void setIsIntercept(boolean isIntercept) {
        this.f = isIntercept;
    }

    public Handler getPreHandler() {
        return this.g;
    }

    public void setPreHandler(Handler preHandler) {
        this.g = preHandler;
    }

    public Runnable getPreRunnable() {
        return this.h;
    }

    public void setPreRunnable(Runnable preRunnable) {
        this.h = preRunnable;
    }

    public void release() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
        this.g = null;
        this.h = null;
    }
}

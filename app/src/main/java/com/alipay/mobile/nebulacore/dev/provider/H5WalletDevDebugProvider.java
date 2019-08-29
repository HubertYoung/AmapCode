package com.alipay.mobile.nebulacore.dev.provider;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Base64;
import com.ali.auth.third.core.model.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.dev.H5BugmeLogCollector;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.util.batched.BatchedScheduler;
import com.alipay.mobile.nebulacore.dev.bugme.H5BugmeBatchedScheduler;
import com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class H5WalletDevDebugProvider implements H5DevDebugProvider {
    public static final String TAG = "H5WalletDevDebugProvider";
    @Nullable
    private Set<String> a = null;
    private H5BugmeBatchedScheduler b = new H5BugmeBatchedScheduler();
    private Map<String, LruCache<String, OneShotValue>> c = new HashMap();
    private H5BugMeManager d = null;

    private class OneShotValue {
        Object a;
        boolean b;

        public OneShotValue(Object value, boolean used) {
            this.a = value;
            this.b = used;
        }
    }

    public H5WalletDevDebugProvider(H5BugMeManager manager) {
        this.d = manager;
        this.c.put("url", new LruCache(20));
        this.c.put(Constants.UA, new LruCache(20));
        this.c.put("title", new LruCache(20));
        this.c.put("deviceModel", new LruCache(20));
        this.c.put("session", new LruCache(20));
        this.c.put("pkgInfo", new LruCache(20));
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            try {
                JSONArray array = JSON.parseArray(provider.getConfig("h5_bugmeIgnoreConsole"));
                int size = array.size();
                this.a = new HashSet();
                for (int i = 0; i < size; i++) {
                    this.a.add(array.getString(i));
                }
            } catch (Throwable t) {
                H5Log.e(TAG, "parse config error", t);
            }
        }
    }

    private void a(String viewId) {
        for (Entry<String, LruCache<String, OneShotValue>> value : this.c.entrySet()) {
            ((LruCache) value.getValue()).remove(viewId);
        }
    }

    private void a(String scope, String viewId, Object value) {
        if (value != null && viewId != null) {
            if (scope.equals("pkgInfo") || !TextUtils.isEmpty((String) value)) {
                LruCache lru = this.c.get(scope);
                if (lru != null) {
                    OneShotValue oneShotValue = (OneShotValue) lru.get(viewId);
                    if (oneShotValue == null) {
                        lru.put(viewId, new OneShotValue(value, false));
                        H5Log.d(TAG, "put OneShotValue[" + scope + "] " + value);
                    } else if (!value.equals(oneShotValue.a)) {
                        oneShotValue.a = value;
                        oneShotValue.b = false;
                        lru.put(viewId, oneShotValue);
                        H5Log.d(TAG, "put OneShotValue[" + scope + "] " + value);
                    }
                }
            }
        }
    }

    private Object a(String scope, String viewId, boolean force) {
        LruCache lru = this.c.get(scope);
        if (lru == null || viewId == null) {
            return null;
        }
        OneShotValue oneShotValue = (OneShotValue) lru.get(viewId);
        if (oneShotValue == null) {
            return null;
        }
        if (force) {
            return oneShotValue.a;
        }
        if (oneShotValue.b) {
            return null;
        }
        oneShotValue.b = true;
        return oneShotValue.a;
    }

    public BatchedScheduler<JSONObject> getScheduler() {
        return this.b;
    }

    public void pageLog(String subType, String viewId, String ua, String url, String title, JSONObject extraObject) {
        JSONObject bundle = new JSONObject();
        bundle.put((String) "type", (Object) "page");
        bundle.put((String) "subType", (Object) subType);
        bundle.put((String) "viewId", (Object) viewId);
        if (!TextUtils.isEmpty(url)) {
            bundle.put((String) "url", (Object) url);
        }
        if (!TextUtils.isEmpty(title)) {
            bundle.put((String) "title", (Object) title);
        }
        if (extraObject != null) {
            bundle.put((String) "extra", (Object) extraObject);
        }
        if (!TextUtils.isEmpty(ua)) {
            a((String) Constants.UA, viewId, (Object) ua);
        }
        a((String) "deviceModel", viewId, (Object) Build.MODEL);
        b(viewId, bundle);
        if ("destroy".equals(subType)) {
            a(viewId);
        }
    }

    public void jsApiLog(String subType, String viewId, String eventId, String request, String response) {
        if (TextUtils.isEmpty(subType) || this.a == null || !this.a.contains(subType)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "type", (Object) "jsapi");
            jsonObject.put((String) "subType", (Object) subType);
            jsonObject.put((String) "viewId", (Object) viewId);
            jsonObject.put((String) "eventId", (Object) eventId);
            jsonObject.put((String) "request", (Object) request);
            jsonObject.put((String) ModuleLongLinkService.CALLBACK_KEY_RESPONSE, (Object) response);
            b(viewId, jsonObject);
        }
    }

    public void eventLog(String subType, String viewId, String data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "type", (Object) "event");
        jsonObject.put((String) "subType", (Object) subType);
        jsonObject.put((String) "viewId", (Object) viewId);
        jsonObject.put((String) "data", (Object) data);
        b(viewId, jsonObject);
    }

    public void consoleLog(String subType, String viewId, String content, String func) {
        if (TextUtils.isEmpty(func) || this.a == null || !this.a.contains(func)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "type", (Object) "console");
            jsonObject.put((String) "subType", (Object) subType);
            jsonObject.put((String) "viewId", (Object) viewId);
            jsonObject.put((String) "content", (Object) content);
            b(viewId, jsonObject);
        }
    }

    public void netWorkLog(String subType, String viewId, JSONObject jsonObject) {
        String reqUrl = H5Utils.getString(jsonObject, (String) "reqUrl");
        String method = H5Utils.getString(jsonObject, (String) "method");
        String protocol = H5Utils.getString(jsonObject, (String) "protocol");
        int reqId = H5Utils.getInt(jsonObject, (String) "reqId");
        String statusCode = String.valueOf(H5Utils.getInt(jsonObject, (String) "statusCode"));
        boolean fromLocalPkg = H5Utils.getBoolean(jsonObject, (String) "fromLocalPkg", false);
        String trimmedResponse = H5Utils.getString(jsonObject, (String) "trimmedResponse");
        JSONObject jobj = new JSONObject();
        jobj.put((String) "type", (Object) "network");
        jobj.put((String) "subType", (Object) subType);
        jobj.put((String) "viewId", (Object) viewId);
        jobj.put((String) "reqId", (Object) Integer.valueOf(reqId));
        jobj.put((String) "reqUrl", (Object) reqUrl);
        jobj.put((String) "method", (Object) method);
        if (!TextUtils.isEmpty(statusCode) && !"0".equals(statusCode)) {
            jobj.put((String) "statusCode", (Object) statusCode);
        }
        if (!TextUtils.isEmpty(trimmedResponse)) {
            jobj.put((String) "trimmedResponse", (Object) trimmedResponse);
        }
        if (!TextUtils.isEmpty(protocol)) {
            jobj.put((String) "protocol", (Object) protocol);
        }
        jobj.put((String) "fromLocalPkg", (Object) Boolean.valueOf(fromLocalPkg));
        b(viewId, jobj);
    }

    public void screenshot(String subType, final String viewId) {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "type", (Object) "screenshot");
        jsonObject.put((String) "subType", (Object) subType);
        jsonObject.put((String) "viewId", (Object) viewId);
        H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
            public void run() {
                H5Service h5Service = H5ServiceUtils.getH5Service();
                if (!(h5Service == null || h5Service.getTopH5Page() == null)) {
                    Bitmap snapshot = H5PerformanceUtils.takeScreenShot(h5Service.getTopH5Page());
                    if (snapshot != null) {
                        ByteArrayOutputStream baos = null;
                        try {
                            ByteArrayOutputStream baos2 = new PoolingByteArrayOutputStream();
                            try {
                                snapshot.compress(CompressFormat.JPEG, 100, baos2);
                                jsonObject.put((String) "img", (Object) Base64.encodeToString(baos2.toByteArray(), 0));
                                H5IOUtils.closeQuietly(baos2);
                            } catch (Exception e) {
                                e = e;
                                baos = baos2;
                                try {
                                    H5Log.e((String) H5WalletDevDebugProvider.TAG, (Throwable) e);
                                    H5IOUtils.closeQuietly(baos);
                                    H5WalletDevDebugProvider.this.b(viewId, jsonObject);
                                } catch (Throwable th) {
                                    th = th;
                                    H5IOUtils.closeQuietly(baos);
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                baos = baos2;
                                H5IOUtils.closeQuietly(baos);
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            H5Log.e((String) H5WalletDevDebugProvider.TAG, (Throwable) e);
                            H5IOUtils.closeQuietly(baos);
                            H5WalletDevDebugProvider.this.b(viewId, jsonObject);
                        }
                    }
                }
                H5WalletDevDebugProvider.this.b(viewId, jsonObject);
            }
        });
    }

    public void welcome(String type, String subType, String viewId, String hello) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "type", (Object) type);
        jsonObject.put((String) "subType", (Object) subType);
        jsonObject.put((String) "viewId", (Object) viewId);
        jsonObject.put((String) "hello", (Object) hello);
        b(viewId, jsonObject);
    }

    public void setUserAgent(String viewId, String ua) {
        a((String) Constants.UA, viewId, (Object) ua);
    }

    public void setTitle(String viewId, String title) {
        a((String) "title", viewId, (Object) title);
    }

    public void setPageUrl(String viewId, String url) {
        a((String) "url", viewId, (Object) url);
    }

    public void setSessionId(String viewId, String sessionId) {
        a((String) "session", viewId, (Object) sessionId);
    }

    public void setPkgInfo(String viewId, JSONObject info) {
        a((String) "pkgInfo", viewId, (Object) info);
    }

    public void onRelease() {
        if (this.b != null) {
            this.b.shutdown();
            this.b = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r6, com.alibaba.fastjson.JSONObject r7) {
        /*
            r5 = this;
            r3 = 1
            r2 = 0
            if (r6 == 0) goto L_0x0017
            java.lang.String r4 = "url"
            java.lang.Object r1 = r5.a(r4, r6, r3)
            java.lang.String r1 = (java.lang.String) r1
            if (r1 == 0) goto L_0x0017
            com.alipay.mobile.nebula.dev.H5BugMeManager r4 = r5.d
            boolean r0 = r4.hasAccessToDebug(r1)
        L_0x0014:
            if (r0 != 0) goto L_0x0019
        L_0x0016:
            return r2
        L_0x0017:
            r0 = 1
            goto L_0x0014
        L_0x0019:
            java.lang.String r4 = "h5_bug_me_debug_switch"
            boolean r4 = com.alipay.mobile.nebula.dev.H5DevConfig.getBooleanConfig(r4, r2)
            if (r4 == 0) goto L_0x0023
            r2 = r3
            goto L_0x0016
        L_0x0023:
            java.lang.String r3 = "h5_trace_debug_switch"
            boolean r3 = com.alipay.mobile.nebula.dev.H5DevConfig.getBooleanConfig(r3, r2)
            if (r3 == 0) goto L_0x0016
            java.lang.String r2 = "page"
            java.lang.String r3 = "type"
            java.lang.String r3 = r7.getString(r3)
            boolean r2 = r2.equals(r3)
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.dev.provider.H5WalletDevDebugProvider.a(java.lang.String, com.alibaba.fastjson.JSONObject):boolean");
    }

    /* access modifiers changed from: private */
    public void b(String viewId, JSONObject jsonObject) {
        if (this.b != null) {
            boolean bugmeSwitchOpen = a(viewId, jsonObject);
            if (bugmeSwitchOpen || H5BugmeLogCollector.enabled()) {
                jsonObject.put((String) "id", (Object) Integer.valueOf(H5BugmeIdGenerator.nextId()));
                jsonObject.put((String) "ts", (Object) Long.valueOf(System.currentTimeMillis()));
                jsonObject.put((String) "bugmeSwitchOpen", (Object) Boolean.valueOf(bugmeSwitchOpen));
                Object oneShotVal = a((String) "url", viewId, false);
                if (oneShotVal != null) {
                    jsonObject.put((String) "url", (Object) oneShotVal.toString());
                }
                Object oneShotVal2 = a((String) "title", viewId, false);
                if (oneShotVal2 != null) {
                    jsonObject.put((String) "title", (Object) oneShotVal2.toString());
                }
                Object oneShotVal3 = a((String) Constants.UA, viewId, false);
                if (oneShotVal3 != null) {
                    jsonObject.put((String) "userAgent", (Object) oneShotVal3.toString());
                }
                Object oneShotVal4 = a((String) "deviceModel", viewId, false);
                if (oneShotVal4 != null) {
                    jsonObject.put((String) "deviceModel", (Object) oneShotVal4.toString());
                }
                Object oneShotVal5 = a((String) "session", viewId, false);
                if (oneShotVal5 != null) {
                    jsonObject.put((String) "sessionId", (Object) oneShotVal5.toString());
                }
                Object oneShotVal6 = a((String) "pkgInfo", viewId, false);
                if (oneShotVal6 != null) {
                    jsonObject.put((String) "app", (Object) oneShotVal6.toString());
                }
                this.b.post(jsonObject);
            }
        }
    }
}

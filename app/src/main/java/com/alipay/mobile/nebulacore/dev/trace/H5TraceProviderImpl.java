package com.alipay.mobile.nebulacore.dev.trace;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.io.PoolingByteArrayOutputStream;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5TraceProvider;
import com.alipay.mobile.nebula.util.H5IOUtils;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.dev.sampler.AbstractSampler.SampleCallback;
import com.alipay.mobile.nebulacore.dev.sampler.ThreadSampler;
import com.autonavi.minimap.ajx3.util.AjxDebugConfig;
import com.autonavi.widget.ui.BalloonLayout;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class H5TraceProviderImpl implements H5TraceProvider {
    private final Set<String> a = new HashSet();
    private final Queue<JSONObject> b = new ConcurrentLinkedQueue();
    /* access modifiers changed from: private */
    public final Map<String, Session> c = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public final Map<String, Session> d = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public AtomicInteger e = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public AtomicBoolean f = new AtomicBoolean(false);
    private ThreadSampler g;

    private class Session {
        String a;
        String b;
        String c;
        String d;
        String e;
        long f;
        long g;
        int h = Integer.MAX_VALUE;
        int i;
        List<Session> j = new ArrayList();
        Stack<Session> k = new Stack<>();

        Session(String name, String viewId, String paramStr) {
            this.a = name;
            this.b = viewId;
            this.c = paramStr;
        }

        public JSONObject toJSONObject() {
            JSONObject object = new JSONObject();
            object.put((String) "type", (Object) AjxDebugConfig.PERFORMANCE);
            object.put((String) "subType", (Object) "session");
            object.put((String) "name", (Object) this.a);
            object.put((String) "viewId", (Object) this.b);
            object.put((String) "params", (Object) this.c);
            object.put((String) "startTs", (Object) Long.valueOf(this.f));
            object.put((String) "endTs", (Object) Long.valueOf(this.g));
            if (this.j != null && this.j.size() > 0) {
                int size = this.j.size();
                JSONArray childrens = new JSONArray();
                for (int i2 = 0; i2 < size; i2++) {
                    childrens.add(this.j.get(i2).toJSONObject());
                }
                object.put((String) "childrens", (Object) childrens);
            }
            if (this.h == Integer.MAX_VALUE) {
                object.put((String) "minThread", (Object) Integer.valueOf(0));
            } else {
                object.put((String) "minThread", (Object) Integer.valueOf(this.h));
            }
            object.put((String) "maxThread", (Object) Integer.valueOf(this.i));
            return object;
        }

        public void onSampleThread(int threadSize) {
            if (threadSize < this.h) {
                this.h = threadSize;
            }
            if (threadSize > this.i) {
                this.i = threadSize;
            }
        }

        public void start() {
            this.f = System.currentTimeMillis();
            this.d = Thread.currentThread().getName();
        }

        public boolean end() {
            if (isEnd()) {
                return false;
            }
            if (this.k.isEmpty()) {
                this.g = System.currentTimeMillis();
                this.e = Thread.currentThread().getName();
                return true;
            }
            Session peek = this.k.peek();
            if (peek.k.isEmpty()) {
                peek.g = System.currentTimeMillis();
                this.k.pop();
                if (this.k.isEmpty()) {
                    this.j.add(peek);
                } else {
                    this.k.peek().j.add(peek);
                }
            } else {
                peek.end();
            }
            return false;
        }

        public void insert(Session session) {
            if (isEnd()) {
                return;
            }
            if (this.k.isEmpty()) {
                this.k.push(session);
            } else {
                this.k.peek().insert(session);
            }
        }

        public String getKey() {
            return this.a + this.c;
        }

        public boolean isEnd() {
            return this.g != 0;
        }
    }

    private static boolean a() {
        return H5DevConfig.getBooleanConfig(H5DevConfig.H5_TRACE_DEBUG_SWITCH, false);
    }

    private static String b(JSONObject params) {
        if (params == null || params.keySet().size() == 0) {
            return "";
        }
        return params.toJSONString();
    }

    private void b() {
        if (a()) {
            c();
            this.f.set(true);
            if (this.e.get() != 0) {
                this.e.set(this.e.get() - 1);
            }
        }
    }

    private void c() {
        if (this.g == null) {
            this.e.set(0);
            this.g = new ThreadSampler(10);
            this.g.registerCallback(new SampleCallback() {
                public boolean onSample(JSONObject result) {
                    if (!H5TraceProviderImpl.this.f.get() && H5TraceProviderImpl.this.e.incrementAndGet() == 100) {
                        return false;
                    }
                    int threadSize = result.getInteger("size").intValue();
                    for (Session onSampleThread : H5TraceProviderImpl.this.c.values()) {
                        onSampleThread.onSampleThread(threadSize);
                    }
                    for (Session onSampleThread2 : H5TraceProviderImpl.this.d.values()) {
                        onSampleThread2.onSampleThread(threadSize);
                    }
                    H5TraceProviderImpl.this.f.set(false);
                    return true;
                }
            });
        }
        this.g.start();
    }

    public void event(String name, final String viewId, JSONObject params) {
        H5Log.d("H5TraceProviderImpl", "event " + name);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put((String) "type", (Object) AjxDebugConfig.PERFORMANCE);
        jsonObject.put((String) "subType", (Object) "event");
        jsonObject.put((String) "thread", (Object) Thread.currentThread().getName());
        jsonObject.put((String) "name", (Object) name);
        jsonObject.put((String) "viewId", (Object) viewId);
        jsonObject.put((String) "params", (Object) b(params));
        b();
        if (a() && !TextUtils.isEmpty(viewId) && !this.a.contains(viewId) && ("pageLoad".equals(name) || "domReady".equals(name))) {
            this.a.add(viewId);
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5Utils.getExecutor("RPC").execute(new Runnable() {
                        public void run() {
                            H5Service service = H5ServiceUtils.getH5Service();
                            if (service != null && service.getTopH5Page() != null) {
                                Bitmap snapshot = H5PerformanceUtils.takeScreenShot(service.getTopH5Page());
                                if (snapshot != null) {
                                    ByteArrayOutputStream baos = null;
                                    try {
                                        ByteArrayOutputStream baos2 = new PoolingByteArrayOutputStream();
                                        try {
                                            snapshot.compress(CompressFormat.JPEG, 100, baos2);
                                            String encoded = Base64.encodeToString(baos2.toByteArray(), 0);
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put((String) "type", (Object) "screenshot");
                                            jsonObject.put((String) "viewId", (Object) viewId);
                                            jsonObject.put((String) "subType", (Object) "upload");
                                            jsonObject.put((String) "img", (Object) encoded);
                                            jsonObject.put((String) "ts", (Object) Long.valueOf(System.currentTimeMillis()));
                                            H5Log.d("H5TraceProviderImpl", "Send snapshot: " + jsonObject);
                                            H5TraceProviderImpl.d(jsonObject);
                                            H5IOUtils.closeQuietly(baos2);
                                        } catch (Exception e) {
                                            e = e;
                                            baos = baos2;
                                            try {
                                                H5Log.e((String) "H5TraceProviderImpl", (Throwable) e);
                                                H5IOUtils.closeQuietly(baos);
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
                                        H5Log.e((String) "H5TraceProviderImpl", (Throwable) e);
                                        H5IOUtils.closeQuietly(baos);
                                    }
                                }
                            }
                        }
                    });
                }
            }, BalloonLayout.DEFAULT_DISPLAY_DURATION);
        }
        c(jsonObject);
    }

    public void sessionBegin(String name, String viewId, JSONObject params) {
        H5Log.d("H5TraceProviderImpl", "sessionBegin " + name);
        b();
        String paramStr = b(params);
        Session session = new Session(name, viewId, paramStr);
        session.start();
        if ("true".equals(params.getString("async"))) {
            this.d.put(name + paramStr, session);
            return;
        }
        String threadName = Thread.currentThread().getName();
        Session active = this.c.get(threadName);
        if (active == null) {
            this.c.put(threadName, session);
            return;
        }
        active.insert(session);
    }

    public void sessionEnd(String name, String viewId, JSONObject params) {
        H5Log.d("H5TraceProviderImpl", "sessionEnd " + name);
        b();
        String paramStr = b(params);
        if ("true".equals(params.getString("async"))) {
            String key = name + paramStr;
            Session session = this.d.get(key);
            if (session != null) {
                session.end();
                this.d.remove(key);
                c(session.toJSONObject());
                return;
            }
            return;
        }
        String threadName = Thread.currentThread().getName();
        Session active = this.c.get(threadName);
        if (active != null && active.end()) {
            this.c.remove(threadName);
            c(active.toJSONObject());
        }
    }

    private void c(JSONObject jsonObject) {
        jsonObject.put((String) "ts", (Object) Long.valueOf(System.currentTimeMillis()));
        String viewId = jsonObject.getString("viewId");
        if (TextUtils.isEmpty(viewId)) {
            H5Log.d("H5TraceProviderImpl", "not send " + jsonObject.getString("name") + " because no viewId");
            this.b.offer(jsonObject);
            return;
        }
        if (this.b.size() > 0) {
            for (JSONObject pending : this.b) {
                pending.put((String) "viewId", (Object) viewId);
                d(pending);
            }
            this.b.clear();
        }
        d(jsonObject);
    }

    /* access modifiers changed from: private */
    public static void d(JSONObject jsonObject) {
        jsonObject.put((String) "id", (Object) Long.valueOf((long) H5BugmeIdGenerator.nextId()));
        jsonObject.put((String) "bugmeSwitchOpen", (Object) Boolean.valueOf(true));
        H5DevDebugProvider provider = (H5DevDebugProvider) H5Utils.getProvider(H5DevDebugProvider.class.getName());
        if (provider != null && provider.getScheduler() != null) {
            provider.getScheduler().post(jsonObject);
        }
    }
}

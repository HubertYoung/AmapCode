package com.alipay.mobile.nebulacore.manager;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.liteprocess.Const;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5PluginManagerImpl implements H5PluginManager {
    public static final String TAG = "H5PluginManager";
    private Set<H5Plugin> a = new HashSet();
    private Map<String, List<H5Plugin>> b = new ConcurrentHashMap();
    private H5CoreNode c;
    private String d;
    private final int e = 0;

    public H5PluginManagerImpl(H5CoreNode coreNode) {
        this.c = coreNode;
    }

    public boolean register(H5Plugin plugin) {
        return a(plugin, -1);
    }

    private synchronized boolean a(H5Plugin plugin, int location) {
        boolean z;
        List pluginList;
        if (plugin == null) {
            H5Log.w(TAG, "invalid plugin parameter!");
            z = false;
        } else if (H5PluginManagerUtil.enableFilterPlugin() && H5PluginManagerUtil.getInstance().isInPluginBlackList(H5Utils.getClassName(plugin))) {
            z = false;
        } else if (this.a.contains(plugin)) {
            H5Log.w(TAG, "plugin already registered!");
            z = false;
        } else {
            long time = System.currentTimeMillis();
            H5EventFilter filter = new H5EventFilter();
            try {
                plugin.onPrepare(filter);
                Iterator iterator = filter.actionIterator();
                if (!iterator.hasNext()) {
                    H5Log.w(TAG, "empty filter");
                    z = false;
                } else {
                    plugin.onInitialize(this.c);
                    this.a.add(plugin);
                    while (iterator.hasNext()) {
                        String action = iterator.next();
                        if (TextUtils.isEmpty(action)) {
                            H5Log.w(TAG, "event can't be empty!");
                        } else if (!H5PluginManagerUtil.getInstance().isInJsApiBlackList(action)) {
                            if (!this.b.containsKey(action)) {
                                pluginList = new ArrayList();
                                this.b.put(action, pluginList);
                            } else {
                                pluginList = this.b.get(action);
                            }
                            if (location < 0) {
                                pluginList.add(plugin);
                            } else {
                                pluginList.add(location, plugin);
                            }
                        }
                    }
                    if (Nebula.DEBUG) {
                        long elapse = System.currentTimeMillis() - time;
                        if (elapse > 0) {
                            H5Log.d(TAG, "Nebula cost time register plugin " + H5Utils.getClassName(plugin) + " elapse " + elapse);
                        }
                    }
                    z = true;
                }
            } catch (Throwable t) {
                this.d = H5Utils.getClassName(plugin);
                H5Log.e(TAG, this.d + "  onPrepare exception.", t);
                z = false;
            }
        }
        return z;
    }

    public synchronized boolean reregisterFront(H5Plugin plugin) {
        unregister(plugin);
        return a(plugin, 0);
    }

    public synchronized boolean unregister(H5Plugin plugin) {
        boolean z = false;
        synchronized (this) {
            if (plugin == null) {
                H5Log.w(TAG, "invalid plugin parameter!");
            } else if (!this.a.contains(plugin)) {
                H5Log.w(TAG, "plugin not registered!");
            } else {
                this.a.remove(plugin);
                for (String action : this.b.keySet()) {
                    List plugins = this.b.get(action);
                    Iterator i = plugins.iterator();
                    while (i.hasNext()) {
                        if (plugin.equals((H5Plugin) i.next())) {
                            i.remove();
                        }
                    }
                    if (plugins.isEmpty()) {
                        this.b.remove(action);
                    }
                }
                if (Nebula.DEBUG) {
                    this.d = H5Utils.getClassName(plugin);
                    H5Log.d(TAG, "Nebula cost time unregister plugin " + this.d);
                }
                z = true;
            }
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0057, code lost:
        if (com.alipay.mobile.nebulacore.Nebula.DEBUG == false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        r4 = java.lang.System.currentTimeMillis() - r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        if (r4 <= 0) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        com.alipay.mobile.nebula.util.H5Log.d(TAG, "Nebula cost time [" + r2 + "] intercepted by " + com.alipay.mobile.nebula.util.H5Utils.getClassName(r9) + " elapse:" + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0099, code lost:
        r13 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean interceptEvent(com.alipay.mobile.h5container.api.H5Event r21, com.alipay.mobile.h5container.api.H5BridgeContext r22) {
        /*
            r20 = this;
            monitor-enter(r20)
            if (r21 != 0) goto L_0x000f
            java.lang.String r13 = "H5PluginManager"
            java.lang.String r16 = "invalid event!"
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.e(r13, r0)     // Catch:{ all -> 0x0136 }
            r13 = 0
        L_0x000d:
            monitor-exit(r20)
            return r13
        L_0x000f:
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            java.lang.String r2 = r21.getAction()     // Catch:{ all -> 0x0136 }
            boolean r13 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0028
            java.lang.String r13 = "H5PluginManager"
            java.lang.String r16 = "invalid event name"
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.w(r13, r0)     // Catch:{ all -> 0x0136 }
            r13 = 0
            goto L_0x000d
        L_0x0028:
            r0 = r20
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.h5container.api.H5Plugin>> r13 = r0.b     // Catch:{ all -> 0x0136 }
            java.lang.Object r10 = r13.get(r2)     // Catch:{ all -> 0x0136 }
            java.util.List r10 = (java.util.List) r10     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x003a
            boolean r13 = r10.isEmpty()     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x003c
        L_0x003a:
            r13 = 0
            goto L_0x000d
        L_0x003c:
            int r13 = r10.size()     // Catch:{ all -> 0x0136 }
            int r7 = r13 + -1
        L_0x0042:
            if (r7 < 0) goto L_0x0181
            java.lang.Object r9 = r10.get(r7)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.h5container.api.H5Plugin r9 = (com.alipay.mobile.h5container.api.H5Plugin) r9     // Catch:{ all -> 0x0136 }
            r11 = 0
            r0 = r21
            r1 = r22
            boolean r11 = r9.interceptEvent(r0, r1)     // Catch:{ Throwable -> 0x009c }
        L_0x0053:
            if (r11 == 0) goto L_0x0139
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0099
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            long r4 = r16 - r14
            r16 = 0
            int r13 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r13 <= 0) goto L_0x0099
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "Nebula cost time ["
            r16.<init>(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r2)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "] intercepted by "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " elapse:"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r4)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.d(r13, r0)     // Catch:{ all -> 0x0136 }
        L_0x0099:
            r13 = 1
            goto L_0x000d
        L_0x009c:
            r12 = move-exception
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            r16.<init>()     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " interceptEvent exception."
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.e(r13, r0, r12)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5_PLUGIN_EXCEPTION"
            com.alipay.mobile.nebula.log.H5LogData r13 = com.alipay.mobile.nebula.log.H5LogData.seedId(r13)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param1()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "action"
            r0 = r16
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r0, r2)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param3()     // Catch:{ all -> 0x0136 }
            r16 = 0
            r0 = r16
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r3, r0)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param4()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = android.util.Log.getStackTraceString(r12)     // Catch:{ all -> 0x0136 }
            r17 = 0
            r0 = r16
            r1 = r17
            com.alipay.mobile.nebula.log.H5LogData r8 = r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
            r0 = r20
            com.alipay.mobile.h5container.api.H5CoreNode r13 = r0.c     // Catch:{ all -> 0x0136 }
            boolean r13 = r13 instanceof com.alipay.mobile.h5container.api.H5Page     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x012c
            r0 = r20
            com.alipay.mobile.h5container.api.H5CoreNode r6 = r0.c     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.h5container.api.H5Page r6 = (com.alipay.mobile.h5container.api.H5Page) r6     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r8.param2()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "appId"
            android.os.Bundle r17 = r6.getParams()     // Catch:{ all -> 0x0136 }
            java.lang.String r18 = "appId"
            java.lang.String r17 = com.alipay.mobile.nebula.util.H5Utils.getString(r17, r18)     // Catch:{ all -> 0x0136 }
            r0 = r16
            r1 = r17
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "tinyApp"
            android.os.Bundle r17 = r6.getParams()     // Catch:{ all -> 0x0136 }
            java.lang.String r18 = "isTinyApp"
            r19 = 0
            boolean r17 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r17, r18, r19)     // Catch:{ all -> 0x0136 }
            java.lang.Boolean r17 = java.lang.Boolean.valueOf(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            r1 = r17
            r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
        L_0x012c:
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r8)     // Catch:{ all -> 0x0136 }
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.enableThrow()     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0053
            throw r12     // Catch:{ all -> 0x0136 }
        L_0x0136:
            r13 = move-exception
            monitor-exit(r20)
            throw r13
        L_0x0139:
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x017d
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            long r4 = r16 - r14
            r16 = 0
            int r13 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r13 <= 0) goto L_0x017d
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "Nebula cost time ["
            r16.<init>(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r2)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "] intercept pass "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " elapse:"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r4)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.d(r13, r0)     // Catch:{ all -> 0x0136 }
        L_0x017d:
            int r7 = r7 + -1
            goto L_0x0042
        L_0x0181:
            r13 = 0
            goto L_0x000d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.manager.H5PluginManagerImpl.interceptEvent(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0057, code lost:
        if (com.alipay.mobile.nebulacore.Nebula.DEBUG == false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0059, code lost:
        r4 = java.lang.System.currentTimeMillis() - r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        if (r4 <= 0) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        com.alipay.mobile.nebula.util.H5Log.d(TAG, "Nebula cost time [" + r2 + "] handled by " + com.alipay.mobile.nebula.util.H5Utils.getClassName(r9) + " elapse:" + r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0099, code lost:
        r13 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean handleEvent(com.alipay.mobile.h5container.api.H5Event r21, com.alipay.mobile.h5container.api.H5BridgeContext r22) {
        /*
            r20 = this;
            monitor-enter(r20)
            if (r21 != 0) goto L_0x000f
            java.lang.String r13 = "H5PluginManager"
            java.lang.String r16 = "invalid event!"
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.e(r13, r0)     // Catch:{ all -> 0x0136 }
            r13 = 0
        L_0x000d:
            monitor-exit(r20)
            return r13
        L_0x000f:
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            java.lang.String r2 = r21.getAction()     // Catch:{ all -> 0x0136 }
            boolean r13 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0028
            java.lang.String r13 = "H5PluginManager"
            java.lang.String r16 = "invalid event name"
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.w(r13, r0)     // Catch:{ all -> 0x0136 }
            r13 = 0
            goto L_0x000d
        L_0x0028:
            r0 = r20
            java.util.Map<java.lang.String, java.util.List<com.alipay.mobile.h5container.api.H5Plugin>> r13 = r0.b     // Catch:{ all -> 0x0136 }
            java.lang.Object r10 = r13.get(r2)     // Catch:{ all -> 0x0136 }
            java.util.List r10 = (java.util.List) r10     // Catch:{ all -> 0x0136 }
            if (r10 == 0) goto L_0x003a
            boolean r13 = r10.isEmpty()     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x003c
        L_0x003a:
            r13 = 0
            goto L_0x000d
        L_0x003c:
            int r13 = r10.size()     // Catch:{ all -> 0x0136 }
            int r7 = r13 + -1
        L_0x0042:
            if (r7 < 0) goto L_0x0181
            java.lang.Object r9 = r10.get(r7)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.h5container.api.H5Plugin r9 = (com.alipay.mobile.h5container.api.H5Plugin) r9     // Catch:{ all -> 0x0136 }
            r11 = 0
            r0 = r21
            r1 = r22
            boolean r11 = r9.handleEvent(r0, r1)     // Catch:{ Throwable -> 0x009c }
        L_0x0053:
            if (r11 == 0) goto L_0x0139
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0099
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            long r4 = r16 - r14
            r16 = 0
            int r13 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r13 <= 0) goto L_0x0099
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "Nebula cost time ["
            r16.<init>(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r2)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "] handled by "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " elapse:"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r4)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.d(r13, r0)     // Catch:{ all -> 0x0136 }
        L_0x0099:
            r13 = 1
            goto L_0x000d
        L_0x009c:
            r12 = move-exception
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            r16.<init>()     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " handleEvent exception."
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.e(r13, r0, r12)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5_PLUGIN_EXCEPTION"
            com.alipay.mobile.nebula.log.H5LogData r13 = com.alipay.mobile.nebula.log.H5LogData.seedId(r13)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param1()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "action"
            r0 = r16
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r0, r2)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param3()     // Catch:{ all -> 0x0136 }
            r16 = 0
            r0 = r16
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r3, r0)     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.param4()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = android.util.Log.getStackTraceString(r12)     // Catch:{ all -> 0x0136 }
            r17 = 0
            r0 = r16
            r1 = r17
            com.alipay.mobile.nebula.log.H5LogData r8 = r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
            r0 = r20
            com.alipay.mobile.h5container.api.H5CoreNode r13 = r0.c     // Catch:{ all -> 0x0136 }
            boolean r13 = r13 instanceof com.alipay.mobile.h5container.api.H5Page     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x012c
            r0 = r20
            com.alipay.mobile.h5container.api.H5CoreNode r6 = r0.c     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.h5container.api.H5Page r6 = (com.alipay.mobile.h5container.api.H5Page) r6     // Catch:{ all -> 0x0136 }
            com.alipay.mobile.nebula.log.H5LogData r13 = r8.param2()     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "appId"
            android.os.Bundle r17 = r6.getParams()     // Catch:{ all -> 0x0136 }
            java.lang.String r18 = "appId"
            java.lang.String r17 = com.alipay.mobile.nebula.util.H5Utils.getString(r17, r18)     // Catch:{ all -> 0x0136 }
            r0 = r16
            r1 = r17
            com.alipay.mobile.nebula.log.H5LogData r13 = r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = "tinyApp"
            android.os.Bundle r17 = r6.getParams()     // Catch:{ all -> 0x0136 }
            java.lang.String r18 = "isTinyApp"
            r19 = 0
            boolean r17 = com.alipay.mobile.nebula.util.H5Utils.getBoolean(r17, r18, r19)     // Catch:{ all -> 0x0136 }
            java.lang.Boolean r17 = java.lang.Boolean.valueOf(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            r1 = r17
            r13.add(r0, r1)     // Catch:{ all -> 0x0136 }
        L_0x012c:
            com.alipay.mobile.nebula.log.H5LogUtil.logNebulaTech(r8)     // Catch:{ all -> 0x0136 }
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.enableThrow()     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x0053
            throw r12     // Catch:{ all -> 0x0136 }
        L_0x0136:
            r13 = move-exception
            monitor-exit(r20)
            throw r13
        L_0x0139:
            boolean r13 = com.alipay.mobile.nebulacore.Nebula.DEBUG     // Catch:{ all -> 0x0136 }
            if (r13 == 0) goto L_0x017d
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0136 }
            long r4 = r16 - r14
            r16 = 0
            int r13 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r13 <= 0) goto L_0x017d
            java.lang.String r3 = com.alipay.mobile.nebula.util.H5Utils.getClassName(r9)     // Catch:{ all -> 0x0136 }
            java.lang.String r13 = "H5PluginManager"
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "Nebula cost time ["
            r16.<init>(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r2)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = "] handle pass "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r3)     // Catch:{ all -> 0x0136 }
            java.lang.String r17 = " elapse:"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ all -> 0x0136 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r4)     // Catch:{ all -> 0x0136 }
            java.lang.String r16 = r16.toString()     // Catch:{ all -> 0x0136 }
            r0 = r16
            com.alipay.mobile.nebula.util.H5Log.d(r13, r0)     // Catch:{ all -> 0x0136 }
        L_0x017d:
            int r7 = r7 + -1
            goto L_0x0042
        L_0x0181:
            r13 = 0
            goto L_0x000d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.manager.H5PluginManagerImpl.handleEvent(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):boolean");
    }

    public void onInitialize(H5CoreNode coreNode) {
    }

    public void onPrepare(H5EventFilter filter) {
    }

    public synchronized void onRelease() {
        for (H5Plugin plugin : this.a) {
            long time = System.currentTimeMillis();
            try {
                plugin.onRelease();
                if (Nebula.DEBUG) {
                    long elapse = System.currentTimeMillis() - time;
                    if (elapse > 0) {
                        H5Log.d(TAG, "Nebula cost time release plugin " + H5Utils.getClassName(plugin) + " elapse:" + elapse);
                    }
                }
            } catch (Throwable t) {
                String clazzName = H5Utils.getClassName(plugin);
                H5Log.e(TAG, clazzName + " onRelease exception.", t);
                H5LogData logData = H5LogData.seedId("H5_PLUGIN_EXCEPTION").param3().add(clazzName, null).param4().add(Log.getStackTraceString(t), null);
                if (this.c instanceof H5Page) {
                    H5Page h5Page = (H5Page) this.c;
                    logData.param2().add("appId", H5Utils.getString(h5Page.getParams(), (String) "appId")).add(Const.TYPE_RN, Boolean.valueOf(H5Utils.getBoolean(h5Page.getParams(), (String) "isTinyApp", false)));
                }
                H5LogUtil.logNebulaTech(logData);
                if (Nebula.enableThrow()) {
                    throw t;
                }
            }
        }
        this.a.clear();
        this.b.clear();
    }

    public boolean register(List<H5Plugin> plugins) {
        if (plugins == null || plugins.isEmpty()) {
            return false;
        }
        boolean result = true;
        for (H5Plugin plugin : plugins) {
            result |= register(plugin);
        }
        return result;
    }

    public boolean unregister(List<H5Plugin> plugins) {
        if (plugins == null || plugins.isEmpty()) {
            return false;
        }
        boolean result = true;
        for (H5Plugin p : plugins) {
            result |= unregister(p);
        }
        return result;
    }

    public synchronized boolean canHandle(String action) {
        return !TextUtils.isEmpty(action) && this.b.containsKey(action);
    }
}

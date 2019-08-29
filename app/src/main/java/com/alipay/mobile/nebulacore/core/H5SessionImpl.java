package com.alipay.mobile.nebulacore.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.h5container.api.H5Flag;
import com.alipay.mobile.h5container.api.H5Listener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5Scenario;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.h5container.service.RnService;
import com.alipay.mobile.nebula.appcenter.H5AppDBService;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.log.linkmonitor.H5LinkMonitor;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5PreRpcProvider;
import com.alipay.mobile.nebula.startParam.H5AppStartParam;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.config.H5PluginConfigManager;
import com.alipay.mobile.nebulacore.data.H5MemData;
import com.alipay.mobile.nebulacore.plugin.H5PreRenderPlugin;
import com.alipay.mobile.nebulacore.plugin.H5ScreenPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SessionPlugin;
import com.alipay.mobile.nebulacore.plugin.H5SnapshotPlugin;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabBar;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabManager;
import com.alipay.mobile.nebulacore.tabbar.H5SessionTabObserver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class H5SessionImpl extends H5CoreTarget implements H5Session {
    public static final String TAG = "H5Session";
    private String b;
    private H5Scenario c;
    private Stack<H5Page> d;
    private boolean e;
    private H5ContentProvider f;
    private List<H5Listener> g;
    private Bundle h;
    private String i;
    private H5LinkMonitor j;
    private H5SessionTabBar k;
    private H5SessionTabManager l;
    private H5SessionTabObserver m;

    public H5SessionImpl() {
        H5Log.d(TAG, "nebulasessiontracker newSession @" + hashCode());
        this.e = false;
        this.g = new LinkedList();
        this.d = new Stack<>();
        this.a = new H5MemData();
        a();
        H5Refer.referUrl = H5Logger.getContextParam(LogContext.STORAGE_REFVIEWID);
        this.l = new H5SessionTabManager();
        this.m = new H5SessionTabObserver();
    }

    private void a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        H5ConfigService configService = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        if (!(configService == null || h5ConfigProvider == null || "yes".equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_disableConfigServiceOpt")))) {
            configService.addExternalPlugins();
        }
        H5PluginManager pm = getPluginManager();
        pm.register((H5Plugin) new H5SessionPlugin(this));
        pm.register((H5Plugin) new H5PreRenderPlugin(this));
        pm.register((H5Plugin) new H5ScreenPlugin());
        pm.register((H5Plugin) new H5SnapshotPlugin(this));
        this.k = new H5SessionTabBar(this);
        pm.register((H5Plugin) this.k);
        H5Plugin plugin = H5PluginConfigManager.getInstance().createPlugin("session", pm);
        if (plugin != null) {
            pm.register(plugin);
        }
    }

    public String getId() {
        return this.b;
    }

    public void setId(String id) {
        this.b = id;
        H5Log.d(TAG, "nebulasessiontracker newSession setId " + id + " @" + hashCode());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0117, code lost:
        if (r9.d.isEmpty() != false) goto L_0x0124;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0119, code lost:
        a(r9.d.peek());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean addPage(final com.alipay.mobile.h5container.api.H5Page r10) {
        /*
            r9 = this;
            r6 = 0
            if (r10 != 0) goto L_0x0005
            r5 = r6
        L_0x0004:
            return r5
        L_0x0005:
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r7 = r9.d
            monitor-enter(r7)
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            boolean r1 = r5.isEmpty()     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0030
            com.alipay.mobile.nebulacore.core.H5ContentProviderImpl r5 = new com.alipay.mobile.nebulacore.core.H5ContentProviderImpl     // Catch:{ all -> 0x002d }
            r5.<init>(r10)     // Catch:{ all -> 0x002d }
            r9.f = r5     // Catch:{ all -> 0x002d }
            java.util.List<com.alipay.mobile.h5container.api.H5Listener> r5 = r9.g     // Catch:{ all -> 0x002d }
            java.util.Iterator r8 = r5.iterator()     // Catch:{ all -> 0x002d }
        L_0x001d:
            boolean r5 = r8.hasNext()     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x0030
            java.lang.Object r5 = r8.next()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.h5container.api.H5Listener r5 = (com.alipay.mobile.h5container.api.H5Listener) r5     // Catch:{ all -> 0x002d }
            r5.onSessionCreated(r9)     // Catch:{ all -> 0x002d }
            goto L_0x001d
        L_0x002d:
            r5 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x002d }
            throw r5
        L_0x0030:
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            java.util.Iterator r8 = r5.iterator()     // Catch:{ all -> 0x002d }
        L_0x0036:
            boolean r5 = r8.hasNext()     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x004b
            java.lang.Object r5 = r8.next()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.h5container.api.H5Page r5 = (com.alipay.mobile.h5container.api.H5Page) r5     // Catch:{ all -> 0x002d }
            boolean r5 = r5.equals(r10)     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x0036
            monitor-exit(r7)     // Catch:{ all -> 0x002d }
            r5 = r6
            goto L_0x0004
        L_0x004b:
            r10.setParent(r9)     // Catch:{ all -> 0x002d }
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x002d }
            if (r5 != 0) goto L_0x0073
            java.lang.String r6 = "com.alipay.mobile.h5container.hidePage"
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            java.lang.Object r5 = r5.peek()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.h5container.api.H5Page r5 = (com.alipay.mobile.h5container.api.H5Page) r5     // Catch:{ all -> 0x002d }
            java.lang.String r8 = r5.getTitle()     // Catch:{ all -> 0x002d }
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            java.lang.Object r5 = r5.peek()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.h5container.api.H5Page r5 = (com.alipay.mobile.h5container.api.H5Page) r5     // Catch:{ all -> 0x002d }
            java.lang.String r5 = r5.getUrl()     // Catch:{ all -> 0x002d }
            a(r10, r6, r8, r5)     // Catch:{ all -> 0x002d }
        L_0x0073:
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d     // Catch:{ all -> 0x002d }
            r5.add(r10)     // Catch:{ all -> 0x002d }
            java.util.List<com.alipay.mobile.h5container.api.H5Listener> r5 = r9.g     // Catch:{ all -> 0x002d }
            java.util.Iterator r6 = r5.iterator()     // Catch:{ all -> 0x002d }
        L_0x007e:
            boolean r5 = r6.hasNext()     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x008e
            java.lang.Object r5 = r6.next()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.h5container.api.H5Listener r5 = (com.alipay.mobile.h5container.api.H5Listener) r5     // Catch:{ all -> 0x002d }
            r5.onPageCreated(r10)     // Catch:{ all -> 0x002d }
            goto L_0x007e
        L_0x008e:
            if (r1 == 0) goto L_0x009c
            java.util.concurrent.Executor r5 = com.alipay.mobile.nebula.wallet.H5ThreadPoolFactory.getSingleThreadExecutor()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.nebulacore.core.H5SessionImpl$1 r6 = new com.alipay.mobile.nebulacore.core.H5SessionImpl$1     // Catch:{ all -> 0x002d }
            r6.<init>(r10)     // Catch:{ all -> 0x002d }
            r5.execute(r6)     // Catch:{ all -> 0x002d }
        L_0x009c:
            com.alipay.mobile.h5container.api.H5PageData r0 = r10.getPageData()     // Catch:{ all -> 0x002d }
            com.alipay.mobile.nebula.provider.H5LogProvider r5 = com.alipay.mobile.nebulacore.Nebula.getH5LogHandler()     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x0110
            java.lang.String r5 = "refViewID"
            java.lang.String r3 = com.alipay.mobile.nebula.log.H5Logger.getContextParam(r5)     // Catch:{ all -> 0x002d }
            java.lang.String r5 = "viewID"
            java.lang.String r4 = com.alipay.mobile.nebula.log.H5Logger.getContextParam(r5)     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "appId="
            r5.<init>(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = r0.getAppId()     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^publicId="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = r0.getPublicId()     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^sourceId="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = com.alipay.mobile.nebula.appcenter.util.H5AppUtil.secAppId     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^viewId="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r4)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^refviewId="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r3)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^token="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = com.alipay.mobile.nebula.log.H5Logger.getToken()     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = "^h5Token="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r6 = com.alipay.mobile.h5container.api.H5PageLoader.h5Token     // Catch:{ all -> 0x002d }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x002d }
            java.lang.String r2 = r5.toString()     // Catch:{ all -> 0x002d }
            r10.setPerformance(r2)     // Catch:{ all -> 0x002d }
        L_0x0110:
            monitor-exit(r7)     // Catch:{ all -> 0x002d }
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0124
            java.util.Stack<com.alipay.mobile.h5container.api.H5Page> r5 = r9.d
            java.lang.Object r5 = r5.peek()
            com.alipay.mobile.h5container.api.H5Page r5 = (com.alipay.mobile.h5container.api.H5Page) r5
            r9.a(r5)
        L_0x0124:
            r5 = 1
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.core.H5SessionImpl.addPage(com.alipay.mobile.h5container.api.H5Page):boolean");
    }

    private static void a(H5Page page, String broadcastName, String title2, String url) {
        String title;
        Intent intent = new Intent();
        intent.setAction(broadcastName);
        if (TextUtils.isEmpty(title2)) {
            title = page.getTitle();
        } else {
            title = title2;
        }
        H5Log.d(TAG, "send Title:" + title);
        intent.putExtra("title", title);
        intent.putExtra("utl", url);
        LocalBroadcastManager.getInstance(page.getContext().getContext()).sendBroadcast(intent);
    }

    public boolean removePage(H5Page page) {
        if (page == null) {
            return false;
        }
        H5Page removedPage = null;
        synchronized (this.d) {
            Iterator iterator = this.d.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    break;
                }
                H5Page p = (H5Page) iterator.next();
                if (p.equals(page)) {
                    if (this.d.size() > 1) {
                        a(page, "com.alipay.mobile.h5container.hidePage", page.getTitle(), page.getUrl());
                    }
                    iterator.remove();
                    if (!this.d.isEmpty()) {
                        a(this.d.peek(), "com.alipay.mobile.h5container.showPage", this.d.peek().getTitle(), this.d.peek().getUrl());
                    }
                    removedPage = p;
                }
            }
            if (removedPage != null) {
                for (int index = (this.g == null ? 0 : this.g.size()) - 1; index >= 0; index--) {
                    this.g.get(index).onPageDestroyed(removedPage);
                }
                if (this.d.isEmpty()) {
                    a(removedPage, "com.alipay.mobile.h5container.pageClose", removedPage.getTitle(), removedPage.getUrl());
                    Nebula.getService().removeSession(getId());
                    exitSession();
                    for (int index2 = (this.g == null ? 0 : this.g.size()) - 1; index2 >= 0; index2--) {
                        this.g.get(index2).onSessionDestroyed(this);
                    }
                }
                if (this.d.isEmpty()) {
                    removedPage.sendEvent(CommonEvents.H5_AL_SESSION_TO_NATIVE, null);
                }
                removedPage.onRelease();
                removedPage.setParent(null);
            }
        }
        if (removedPage != null) {
            return true;
        }
        return false;
    }

    public boolean isExited() {
        return this.e;
    }

    public H5Page getTopPage() {
        synchronized (this.d) {
            if (this.d.isEmpty()) {
                H5Log.d(TAG, "getTopPage pages.isEmpty()");
                return null;
            }
            H5Page top = this.d.peek();
            if (!H5Utils.getBoolean(top.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
                H5Log.d(TAG, "getTopPage top");
                return top;
            }
            int size = this.d.size();
            for (int i2 = size - 2; i2 >= 0; i2--) {
                H5Page tmpPage = (H5Page) this.d.elementAt(i2);
                if (!H5Utils.getBoolean(tmpPage.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
                    H5Log.d(TAG, "getTopPage in index " + i2 + ", size " + size);
                    return tmpPage;
                }
            }
            H5Log.d(TAG, "getTopPage no normal page exists");
            return null;
        }
    }

    public Stack<H5Page> getPages() {
        return this.d;
    }

    public H5Scenario getScenario() {
        return this.c;
    }

    public void setScenario(H5Scenario scenario) {
        this.c = scenario;
    }

    public Bundle getParams() {
        return this.h;
    }

    public void setParams(Bundle bundle) {
        this.h = bundle;
    }

    public synchronized void addListener(H5Listener l2) {
        if (l2 != null) {
            if (this.g != null) {
                Iterator<H5Listener> it = this.g.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (l2.equals(it.next())) {
                            break;
                        }
                    } else {
                        this.g.add(l2);
                        break;
                    }
                }
            }
        }
    }

    public synchronized void removeListener(H5Listener l2) {
        if (l2 != null) {
            if (this.g != null) {
                this.g.remove(l2);
            }
        }
    }

    public synchronized void removeAllListener() {
        if (this.g != null && !this.g.isEmpty()) {
            this.g.clear();
        }
    }

    private void b() {
        Stack temp = new Stack();
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            temp.add((H5Page) it.next());
        }
        H5Page topPage = getTopPage();
        if (topPage != null) {
            temp.remove(topPage);
            topPage.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        }
        for (int index = temp.size() - 1; index >= 0; index--) {
            ((H5Page) temp.get(index)).sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        }
    }

    public boolean exitSession() {
        H5Log.d(TAG, "nebulasessiontracker exitSession @" + hashCode());
        if (this.e) {
            H5Log.e((String) TAG, (String) "session already exited!");
            return false;
        }
        this.e = true;
        H5Refer.referUrl = "";
        b();
        H5AppDBService h5AppDBService = H5ServiceUtils.getAppDBService();
        if (h5AppDBService != null) {
            h5AppDBService.clearPresetMemory();
        }
        if (this.f != null) {
            this.f.releaseContent();
            this.f = null;
        }
        H5Flag.setOpenAuthGrantFlag(this.b, false);
        H5AppStartParam.getInstance().remove(this.b);
        H5PreRpcProvider preRpcProvider = (H5PreRpcProvider) Nebula.getProviderManager().getProvider(H5PreRpcProvider.class.getName());
        if (preRpcProvider != null) {
            preRpcProvider.clearPreAll();
        }
        H5PageLoader.release();
        this.k = null;
        if (this.l != null) {
            this.l.clearTabFragments();
            this.l = null;
        }
        if (this.m != null) {
            this.m.clear();
            this.m = null;
        }
        return true;
    }

    public H5ContentProvider getWebProvider() {
        return this.f;
    }

    public void setServiceWorkerID(String s) {
        this.i = s;
    }

    public String getServiceWorkerID() {
        return this.i;
    }

    public void setH5LinkMonitor(H5LinkMonitor linkMonitor) {
        this.j = linkMonitor;
    }

    public H5LinkMonitor getH5LinkMonitor() {
        return this.j;
    }

    private void a(H5Page page) {
        if (page != null) {
            boolean enableFallbackUrl = false;
            if (RnService.isRnBiz(H5Utils.getString(page.getParams(), (String) "bizType", (String) ""))) {
                enableFallbackUrl = true;
            }
            if (this.f != null) {
                this.f.setEnableFallbackUrl(enableFallbackUrl);
            }
        }
    }

    public H5SessionTabBar getH5SessionTabBar() {
        return this.k;
    }

    public H5SessionTabManager getH5SessionTabManager() {
        return this.l;
    }

    public H5SessionTabObserver getH5SessionTabObserver() {
        return this.m;
    }
}

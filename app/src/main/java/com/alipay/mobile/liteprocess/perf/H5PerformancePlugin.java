package com.alipay.mobile.liteprocess.perf;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.Config;
import com.alipay.mobile.liteprocess.LiteProcessApi;
import com.alipay.mobile.monitor.api.MonitorFactory;

public class H5PerformancePlugin extends H5SimplePlugin {
    public static final String PERFORMANCE_JS_API = "onAppPerfEvent";

    public void onInitialize(H5CoreNode coreNode) {
        super.onInitialize(coreNode);
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_RENDER);
        filter.addAction("pushWindow");
        filter.addAction(CommonEvents.H5_PAGE_CLOSED);
        filter.addAction(CommonEvents.H5_PAGE_PAUSE);
        filter.addAction(CommonEvents.H5_PAGE_RESUME);
        filter.addAction(H5Param.MONITOR_PERFORMANCE);
        filter.addAction(PERFORMANCE_JS_API);
        super.onPrepare(filter);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (!LiteProcessApi.isLiteProcess()) {
            return super.interceptEvent(event, context);
        }
        try {
            if (H5Param.MONITOR_PERFORMANCE.equals(event.getAction())) {
                JSONArray data = event.getParam().getJSONArray("data");
                if (data != null) {
                    int length = data.size();
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        JSONObject element = data.getJSONObject(i);
                        if (element.containsKey("name") && element.containsKey("value") && "domReady".equals(element.getString("name"))) {
                            long value = element.getLongValue("value");
                            PerformanceLogger.track(TrackType.STARTUP_DOM_READY);
                            PerformanceLogger.track(TrackType.PAGE_SWITCH_DOM_READY);
                            LoggerFactory.getTraceLogger().info(PerformanceLogger.TAG, "domReady:" + value + ", now:" + System.currentTimeMillis());
                            break;
                        }
                        i++;
                    }
                }
            } else if (CommonEvents.H5_PAGE_RESUME.equals(event.getAction()) && Config.TINYAPP_PAGE_FLUENCY) {
                H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
                if (h5Service.getTopH5Page() != null && !TextUtils.isEmpty(h5Service.getTopH5Page().getUrl())) {
                    MonitorFactory.getMonitorContext().onTinyAppPageUrlChanged(h5Service.getTopH5Page().getUrl());
                }
            }
        } catch (Throwable th) {
        }
        return super.interceptEvent(event, context);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean handleEvent(com.alipay.mobile.h5container.api.H5Event r15, com.alipay.mobile.h5container.api.H5BridgeContext r16) {
        /*
            r14 = this;
            com.alipay.mobile.common.logging.api.ProcessInfo r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x00a4 }
            boolean r11 = r11.isMainProcess()     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0033
            com.alipay.mobile.framework.MicroApplicationContext r11 = com.alipay.mobile.liteprocess.Util.getMicroAppContext()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.framework.app.MicroApplication r11 = r11.findTopRunningApp()     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0031
            com.alipay.mobile.framework.MicroApplicationContext r11 = com.alipay.mobile.liteprocess.Util.getMicroAppContext()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.framework.app.MicroApplication r11 = r11.findTopRunningApp()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r9 = r11.getAppId()     // Catch:{ Throwable -> 0x00a4 }
        L_0x0020:
            if (r9 == 0) goto L_0x002c
            java.lang.String r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.getCurrentAppId()     // Catch:{ Throwable -> 0x00a4 }
            boolean r11 = r9.equals(r11)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 != 0) goto L_0x0033
        L_0x002c:
            boolean r11 = super.handleEvent(r15, r16)     // Catch:{ Throwable -> 0x00a4 }
        L_0x0030:
            return r11
        L_0x0031:
            r9 = 0
            goto L_0x0020
        L_0x0033:
            java.lang.String r0 = r15.getAction()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r11 = "h5PageStarted"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x005c
            com.alibaba.fastjson.JSONObject r6 = r15.getParam()     // Catch:{ Throwable -> 0x00a4 }
            r10 = 0
            if (r6 == 0) goto L_0x004c
            java.lang.String r11 = "url"
            java.lang.String r10 = r6.getString(r11)     // Catch:{ Throwable -> 0x00a4 }
        L_0x004c:
            java.lang.String r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.a     // Catch:{ Throwable -> 0x00a4 }
            if (r11 != 0) goto L_0x0055
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_PAGE_START     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
        L_0x0055:
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.a = r10     // Catch:{ Throwable -> 0x00a4 }
        L_0x0057:
            boolean r11 = super.handleEvent(r15, r16)
            goto L_0x0030
        L_0x005c:
            java.lang.String r11 = "h5PageFinished"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x00b5
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_PAGE_FINISH     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.h5container.api.H5CoreNode r11 = r15.getTarget()     // Catch:{ Throwable -> 0x0093 }
            boolean r11 = r11 instanceof com.alipay.mobile.h5container.api.H5Page     // Catch:{ Throwable -> 0x0093 }
            if (r11 == 0) goto L_0x0057
            com.alipay.mobile.h5container.api.H5CoreNode r2 = r15.getTarget()     // Catch:{ Throwable -> 0x0093 }
            com.alipay.mobile.h5container.api.H5Page r2 = (com.alipay.mobile.h5container.api.H5Page) r2     // Catch:{ Throwable -> 0x0093 }
            com.alipay.mobile.nebula.webview.APWebView r11 = r2.getWebView()     // Catch:{ Throwable -> 0x0093 }
            if (r11 == 0) goto L_0x0057
            com.alipay.mobile.nebula.webview.APWebView r11 = r2.getWebView()     // Catch:{ Throwable -> 0x0093 }
            java.lang.String r11 = r11.getVersion()     // Catch:{ Throwable -> 0x0093 }
            if (r11 == 0) goto L_0x0057
            com.alipay.mobile.nebula.webview.APWebView r11 = r2.getWebView()     // Catch:{ Throwable -> 0x0093 }
            java.lang.String r11 = r11.getVersion()     // Catch:{ Throwable -> 0x0093 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.setH5WebviewVersion(r11)     // Catch:{ Throwable -> 0x0093 }
            goto L_0x0057
        L_0x0093:
            r8 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<com.alipay.mobile.liteprocess.perf.H5PerformancePlugin> r12 = com.alipay.mobile.liteprocess.perf.H5PerformancePlugin.class
            java.lang.String r12 = r12.getSimpleName()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r13 = "get h5 webview version error!"
            r11.error(r12, r13, r8)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x00a4:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.Class<com.alipay.mobile.liteprocess.perf.H5PerformancePlugin> r12 = com.alipay.mobile.liteprocess.perf.H5PerformancePlugin.class
            java.lang.String r12 = r12.getSimpleName()
            java.lang.String r13 = "handleEvent"
            r11.error(r12, r13, r1)
            goto L_0x0057
        L_0x00b5:
            java.lang.String r11 = "h5PageRender"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x00c3
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_PAGE_RENDER     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x00c3:
            java.lang.String r11 = "h5PageClosed"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x00d6
            com.alipay.mobile.h5container.api.H5Page r11 = r15.getH5page()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.logStartup(r11)     // Catch:{ Throwable -> 0x00a4 }
            r11 = 0
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.a = r11     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x00d6:
            java.lang.String r11 = "h5PagePause"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x00e7
            com.alipay.mobile.h5container.api.H5Page r11 = r15.getH5page()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.logStartup(r11)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x00e7:
            java.lang.String r11 = "onAppPerfEvent"
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0057
            com.alibaba.fastjson.JSONObject r6 = r15.getParam()     // Catch:{ Throwable -> 0x00a4 }
            if (r6 == 0) goto L_0x0057
            java.lang.String r11 = "state"
            java.lang.String r7 = r6.getString(r11)     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r11 = "finish"
            boolean r11 = android.text.TextUtils.equals(r11, r7)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x010d
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_JS_FINISH     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
            r16.sendSuccess()     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x010d:
            java.lang.String r11 = "appLoaded"
            boolean r11 = android.text.TextUtils.equals(r11, r7)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x011f
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_APP_LOADED     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
            r16.sendSuccess()     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x011f:
            java.lang.String r11 = "renderFrameworkLoaded"
            boolean r11 = android.text.TextUtils.equals(r11, r7)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0135
            java.lang.String r11 = "loadTime"
            int r11 = r6.getIntValue(r11)     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.logRenderFrameworkLoaded(r11)     // Catch:{ Throwable -> 0x00a4 }
            r16.sendSuccess()     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x0135:
            java.lang.String r11 = "pageLoaded"
            boolean r11 = android.text.TextUtils.equals(r11, r7)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0198
            java.lang.String r11 = "loadTime"
            int r4 = r6.getIntValue(r11)     // Catch:{ Throwable -> 0x00a4 }
            if (r4 != 0) goto L_0x014a
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_PAGE_LOADED     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11)     // Catch:{ Throwable -> 0x00a4 }
        L_0x014a:
            java.lang.String r11 = "page"
            java.lang.String r5 = r6.getString(r11)     // Catch:{ Throwable -> 0x00a4 }
            if (r4 != 0) goto L_0x0196
            r11 = 1
        L_0x0153:
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.logPageSwitch(r4, r5, r11)     // Catch:{ Throwable -> 0x00a4 }
            r16.sendSuccess()     // Catch:{ Throwable -> 0x00a4 }
            boolean r11 = com.alipay.mobile.liteprocess.Config.TINYAPP_PAGE_FLUENCY     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0057
            com.alipay.mobile.framework.LauncherApplicationAgent r11 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.framework.MicroApplicationContext r11 = r11.getMicroApplicationContext()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Class<com.alipay.mobile.h5container.service.H5Service> r12 = com.alipay.mobile.h5container.service.H5Service.class
            java.lang.String r12 = r12.getName()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.Object r3 = r11.findServiceByInterface(r12)     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.h5container.service.H5Service r3 = (com.alipay.mobile.h5container.service.H5Service) r3     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.h5container.api.H5Page r11 = r3.getTopH5Page()     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0057
            com.alipay.mobile.h5container.api.H5Page r11 = r3.getTopH5Page()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r11 = r11.getUrl()     // Catch:{ Throwable -> 0x00a4 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 != 0) goto L_0x0057
            com.alipay.mobile.monitor.api.MonitorContext r11 = com.alipay.mobile.monitor.api.MonitorFactory.getMonitorContext()     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.h5container.api.H5Page r12 = r3.getTopH5Page()     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r12 = r12.getUrl()     // Catch:{ Throwable -> 0x00a4 }
            r11.onTinyAppPageUrlChanged(r12)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        L_0x0196:
            r11 = 0
            goto L_0x0153
        L_0x0198:
            java.lang.String r11 = "workerFrameworkLoaded"
            boolean r11 = android.text.TextUtils.equals(r11, r7)     // Catch:{ Throwable -> 0x00a4 }
            if (r11 == 0) goto L_0x0057
            java.lang.String r11 = "loadTime"
            int r4 = r6.getIntValue(r11)     // Catch:{ Throwable -> 0x00a4 }
            if (r4 <= 0) goto L_0x01ae
            com.alipay.mobile.liteprocess.perf.PerformanceLogger$TrackType r11 = com.alipay.mobile.liteprocess.perf.PerformanceLogger.TrackType.STARTUP_WORKER_FRAMEWORK_LOADED     // Catch:{ Throwable -> 0x00a4 }
            long r12 = (long) r4     // Catch:{ Throwable -> 0x00a4 }
            com.alipay.mobile.liteprocess.perf.PerformanceLogger.track(r11, r12)     // Catch:{ Throwable -> 0x00a4 }
        L_0x01ae:
            r16.sendSuccess()     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0057
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.liteprocess.perf.H5PerformancePlugin.handleEvent(com.alipay.mobile.h5container.api.H5Event, com.alipay.mobile.h5container.api.H5BridgeContext):boolean");
    }
}

package com.alipay.mobile.nebulaappproxy.api.receiver;

import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.nebula.appcenter.wifidownload.H5WifiDownloadList;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5NetworkUtil.Network;
import com.alipay.mobile.nebula.util.H5NetworkUtil.NetworkListener;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class H5NetChangeReceiver implements NetworkListener {
    private static long a;
    private static boolean b = true;

    public void onNetworkChanged(Network lastNet, Network nowNet) {
        if (nowNet != Network.NETWORK_WIFI) {
            H5Log.d("H5NetChangeReceiver", "nowNet is not wifi");
        } else if (H5Utils.isMainProcess()) {
            a();
        }
    }

    private void a() {
        long delayTime = 60;
        H5Log.d("H5NetChangeReceiver", "nowNet is wifi");
        Map map = H5WifiDownloadList.getWifiDownloadMap();
        if (map == null || map.isEmpty()) {
            H5Log.d("H5NetChangeReceiver", "getWifiDownloadMap is null ");
            return;
        }
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - a;
        H5Log.d("H5NetChangeReceiver", "time interval :  " + timeInterval + " , sIsLastTriggerDownloadProcessDone : " + b);
        if (timeInterval >= 10000 && b) {
            a = currentTime;
            long delaySeconds = 60;
            H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
            if (h5ConfigProvider != null) {
                delaySeconds = H5Utils.parseLong(h5ConfigProvider.getConfig("h5_appDownloadDelaySeconds"));
            }
            if (delaySeconds > 0 && delaySeconds <= 60) {
                delayTime = delaySeconds;
            }
            H5PageData.sAppDownloadDelaySeconds = delayTime;
            ScheduledThreadPoolExecutor executor = H5Utils.getScheduledExecutor();
            if (executor != null) {
                b = false;
                executor.schedule(new Runnable() {
                    public void run() {
                        H5NetChangeReceiver.this.b();
                    }
                }, delayTime, TimeUnit.SECONDS);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        com.alipay.mobile.nebula.util.H5Log.e((java.lang.String) "H5NetChangeReceiver", (java.lang.String) "net change not wifi");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b() {
        /*
            r12 = this;
            monitor-enter(r12)
            boolean r9 = com.alipay.mobile.nebula.util.H5Utils.isInWifi()     // Catch:{ Throwable -> 0x0081 }
            if (r9 != 0) goto L_0x0010
            java.lang.String r9 = "H5NetChangeReceiver"
            java.lang.String r10 = "net is not WIFI now"
            com.alipay.mobile.nebula.util.H5Log.d(r9, r10)     // Catch:{ Throwable -> 0x0081 }
        L_0x000e:
            monitor-exit(r12)
            return
        L_0x0010:
            java.lang.Class<com.alipay.mobile.base.config.ConfigService> r9 = com.alipay.mobile.base.config.ConfigService.class
            java.lang.String r9 = r9.getName()     // Catch:{ Throwable -> 0x0081 }
            java.lang.Object r2 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r9)     // Catch:{ Throwable -> 0x0081 }
            com.alipay.mobile.base.config.ConfigService r2 = (com.alipay.mobile.base.config.ConfigService) r2     // Catch:{ Throwable -> 0x0081 }
            if (r2 == 0) goto L_0x0087
            java.lang.String r9 = "H5_downloadApp_user_net_change"
            java.lang.String r7 = r2.getConfig(r9)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r9 = "no"
            boolean r9 = r9.equalsIgnoreCase(r7)     // Catch:{ Throwable -> 0x0081 }
            if (r9 != 0) goto L_0x0087
            java.util.Map r5 = com.alipay.mobile.nebula.appcenter.wifidownload.H5WifiDownloadList.getWifiDownloadMap()     // Catch:{ Throwable -> 0x0081 }
            if (r5 == 0) goto L_0x0087
            boolean r9 = r5.isEmpty()     // Catch:{ Throwable -> 0x0081 }
            if (r9 != 0) goto L_0x0087
            java.util.Set r9 = r5.keySet()     // Catch:{ Throwable -> 0x0081 }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ Throwable -> 0x0081 }
        L_0x0040:
            boolean r10 = r9.hasNext()     // Catch:{ Throwable -> 0x0081 }
            if (r10 == 0) goto L_0x0087
            java.lang.Object r4 = r9.next()     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0081 }
            java.lang.Object r8 = r5.get(r4)     // Catch:{ Throwable -> 0x0081 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x0081 }
            boolean r10 = com.alipay.mobile.nebula.util.H5Utils.isInWifi()     // Catch:{ Throwable -> 0x0081 }
            if (r10 == 0) goto L_0x008e
            java.lang.Class<com.alipay.mobile.h5container.service.H5AppCenterService> r10 = com.alipay.mobile.h5container.service.H5AppCenterService.class
            java.lang.String r10 = r10.getName()     // Catch:{ Throwable -> 0x0081 }
            java.lang.Object r0 = com.alipay.mobile.nebula.util.H5Utils.findServiceByInterface(r10)     // Catch:{ Throwable -> 0x0081 }
            com.alipay.mobile.h5container.service.H5AppCenterService r0 = (com.alipay.mobile.h5container.service.H5AppCenterService) r0     // Catch:{ Throwable -> 0x0081 }
            if (r0 == 0) goto L_0x0040
            com.alipay.mobile.nebula.appcenter.wifidownload.H5WifiDownloadList.remove(r4)     // Catch:{ Throwable -> 0x0081 }
            com.alipay.mobile.nebula.appcenter.H5AppDBService r10 = r0.getAppDBService()     // Catch:{ Throwable -> 0x0081 }
            com.alipay.mobile.nebula.appcenter.model.AppInfo r1 = r10.getAppInfo(r4, r8)     // Catch:{ Throwable -> 0x0081 }
            if (r1 == 0) goto L_0x0040
            com.alipay.mobile.nebula.appcenter.H5BaseApp r3 = r0.getH5App()     // Catch:{ Throwable -> 0x0081 }
            r3.setAppInfo(r1)     // Catch:{ Throwable -> 0x0081 }
            r10 = 0
            java.lang.String r11 = "net_change"
            r3.downloadApp(r10, r11)     // Catch:{ Throwable -> 0x0081 }
            goto L_0x0040
        L_0x0081:
            r6 = move-exception
            java.lang.String r9 = "H5NetChangeReceiver"
            com.alipay.mobile.nebula.util.H5Log.e(r9, r6)     // Catch:{ all -> 0x008b }
        L_0x0087:
            r9 = 1
            b = r9     // Catch:{ all -> 0x008b }
            goto L_0x000e
        L_0x008b:
            r9 = move-exception
            monitor-exit(r12)
            throw r9
        L_0x008e:
            java.lang.String r9 = "H5NetChangeReceiver"
            java.lang.String r10 = "net change not wifi"
            com.alipay.mobile.nebula.util.H5Log.e(r9, r10)     // Catch:{ Throwable -> 0x0081 }
            goto L_0x0087
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappproxy.api.receiver.H5NetChangeReceiver.b():void");
    }
}

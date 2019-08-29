package com.autonavi.minimap.bundle.qrscan.scanner;

import android.hardware.Camera;
import android.util.Log;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.bqcscanservice.impl.MPaasScanServiceImpl;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView.IScanViewStatusListener;
import java.util.LinkedList;
import java.util.Queue;

public class AjxScanManager {
    public static final String TAG = "AjxScanManager";
    private static AjxScanManager instance = new AjxScanManager();
    private Camera cameraBySelf;
    private IScanViewStatusListener cameraStatusListener = new IScanViewStatusListener() {
        public void onPrepare() {
            AjxScanManager.this.scanViewStatus = 0;
        }

        public void onMount() {
            AjxScanManager.this.scanViewStatus = 1;
            AjxScanManager.this.doTasks();
        }

        public void onUnmount() {
            AjxScanManager.this.scanViewStatus = -1;
        }

        public void onDestroy() {
            AjxScanManager.this.scanViewStatus = -2;
        }
    };
    private BQCScanService mBQCScanService;
    /* access modifiers changed from: private */
    public int scanViewStatus = -2;
    private Queue<Runnable> tasks = new LinkedList();
    private boolean torchStatusBySelf = false;

    public interface OnFetchTorchStatusCallback {
        void onTorchStatus(boolean z);
    }

    private AjxScanManager() {
    }

    public static AjxScanManager getInstance() {
        return instance;
    }

    /* access modifiers changed from: private */
    public void doTasks() {
        synchronized (this.tasks) {
            if (this.tasks.size() != 0) {
                while (true) {
                    Runnable poll = this.tasks.poll();
                    if (poll == null) {
                        break;
                    }
                    poll.run();
                }
            }
        }
    }

    private void addTask(Runnable runnable) {
        synchronized (this.tasks) {
            this.tasks.add(runnable);
        }
    }

    public synchronized BQCScanService getBQCScanService() {
        if (this.mBQCScanService == null) {
            this.mBQCScanService = new MPaasScanServiceImpl();
            this.mBQCScanService.serviceInit();
            this.mBQCScanService.setTraceLogger(new TraceLogger() {
                public void debug(String str, String str2) {
                }

                public void error(String str, String str2) {
                }

                public void error(String str, String str2, Throwable th) {
                }

                public void info(String str, String str2) {
                }

                public void print(String str, String str2) {
                }

                public void print(String str, Throwable th) {
                }

                public void verbose(String str, String str2) {
                }

                public void warn(String str, String str2) {
                }

                public void warn(String str, String str2, Throwable th) {
                }

                public void warn(String str, Throwable th) {
                }

                public void error(String str, Throwable th) {
                    Log.getStackTraceString(th);
                }
            });
        }
        return this.mBQCScanService;
    }

    public void setTorch(final boolean z) {
        StringBuilder sb = new StringBuilder("--AjxScanManager.setTorch :");
        sb.append(z);
        sb.append(Thread.currentThread());
        if (this.cameraBySelf == null) {
            BQCScanService bQCScanService = getBQCScanService();
            if (this.scanViewStatus == 0) {
                addTask(new Runnable() {
                    public void run() {
                        AjxScanManager.this.setTorch(z);
                    }
                });
                return;
            } else if (this.scanViewStatus == 1) {
                if (bQCScanService.isScanEnable() && bQCScanService.getCamera() != null) {
                    bQCScanService.setTorch(z);
                    return;
                }
            }
        }
        doTorchSelf(z);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:12|13|14|15|16|17|18) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x002f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void doTorchSelf(boolean r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.torchStatusBySelf     // Catch:{ all -> 0x0054 }
            if (r3 != r0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            android.hardware.Camera r0 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0011
            android.hardware.Camera r0 = android.hardware.Camera.open()     // Catch:{ all -> 0x0054 }
            r2.cameraBySelf = r0     // Catch:{ all -> 0x0054 }
        L_0x0011:
            android.hardware.Camera r0 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            android.hardware.Camera$Parameters r0 = r0.getParameters()     // Catch:{ all -> 0x0054 }
            r1 = 0
            if (r3 == 0) goto L_0x0039
            java.lang.String r3 = "torch"
            r0.setFlashMode(r3)     // Catch:{ all -> 0x0054 }
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            r3.setParameters(r0)     // Catch:{ all -> 0x0054 }
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ IOException -> 0x002f }
            android.graphics.SurfaceTexture r0 = new android.graphics.SurfaceTexture     // Catch:{ IOException -> 0x002f }
            r0.<init>(r1)     // Catch:{ IOException -> 0x002f }
            r3.setPreviewTexture(r0)     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            r3.startPreview()     // Catch:{ all -> 0x0054 }
            r3 = 1
            r2.torchStatusBySelf = r3     // Catch:{ all -> 0x0054 }
            monitor-exit(r2)
            return
        L_0x0039:
            java.lang.String r3 = "off"
            r0.setFlashMode(r3)     // Catch:{ all -> 0x0054 }
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            r3.setParameters(r0)     // Catch:{ all -> 0x0054 }
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            r3.stopPreview()     // Catch:{ all -> 0x0054 }
            android.hardware.Camera r3 = r2.cameraBySelf     // Catch:{ all -> 0x0054 }
            r3.release()     // Catch:{ all -> 0x0054 }
            r3 = 0
            r2.cameraBySelf = r3     // Catch:{ all -> 0x0054 }
            r2.torchStatusBySelf = r1     // Catch:{ all -> 0x0054 }
            monitor-exit(r2)
            return
        L_0x0054:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.qrscan.scanner.AjxScanManager.doTorchSelf(boolean):void");
    }

    public void getFlashlightState(final OnFetchTorchStatusCallback onFetchTorchStatusCallback) {
        BQCScanService bQCScanService = getBQCScanService();
        if (this.scanViewStatus == 0) {
            addTask(new Runnable() {
                public void run() {
                    AjxScanManager.this.getFlashlightState(onFetchTorchStatusCallback);
                }
            });
            return;
        }
        if (this.scanViewStatus == 1) {
            if (onFetchTorchStatusCallback != null) {
                if (!bQCScanService.isScanEnable() || bQCScanService.getCamera() == null) {
                    onFetchTorchStatusCallback.onTorchStatus(this.torchStatusBySelf);
                } else {
                    onFetchTorchStatusCallback.onTorchStatus(bQCScanService.isTorchOn());
                }
            }
        } else if (onFetchTorchStatusCallback != null) {
            onFetchTorchStatusCallback.onTorchStatus(this.torchStatusBySelf);
        }
    }

    public IScanViewStatusListener getScanViewStatusListener() {
        return this.cameraStatusListener;
    }
}

package com.autonavi.minimap.bundle.qrscan.scanner;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.mascanengine.impl.MaScanEngineServiceImpl;

class ScanHandler {
    /* access modifiers changed from: private */
    public BQCScanService bqcScanService;
    private Handler scanHandler;
    private HandlerThread scanHandlerThread = new HandlerThread("Scan-Recognized", 10);
    /* access modifiers changed from: private */
    public ScanResultCallbackProducer scanResultCallbackProducer;
    /* access modifiers changed from: private */
    public MediaPlayer shootMP;

    interface ScanResultCallbackProducer {
        EngineCallback makeScanResultCallback(ScanType scanType);
    }

    public void reset() {
    }

    ScanHandler() {
        this.scanHandlerThread.start();
        this.scanHandler = new Handler(this.scanHandlerThread.getLooper());
    }

    public void destroy() {
        this.scanHandlerThread.quit();
    }

    /* access modifiers changed from: 0000 */
    public void setBqcScanService(final BQCScanService bQCScanService) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.bqcScanService = bQCScanService;
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void registerAllEngine() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                if (ScanHandler.this.scanResultCallbackProducer != null) {
                    ScanHandler.this.bqcScanService.regScanEngine(ScanType.SCAN_MA.toBqcScanType(), new MaScanEngineServiceImpl().getEngineClazz(), ScanHandler.this.scanResultCallbackProducer.makeScanResultCallback(ScanType.SCAN_MA));
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void enableScan() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.bqcScanService.setScanEnable(true);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void setScanType(final ScanType scanType) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.bqcScanService.setScanType(scanType.toBqcScanType());
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void disableScan() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                try {
                    ScanHandler.this.bqcScanService.setScanEnable(false);
                } catch (Exception unused) {
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void removeContext() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.scanResultCallbackProducer = null;
                if (ScanHandler.this.shootMP != null) {
                    ScanHandler.this.shootMP.release();
                    ScanHandler.this.shootMP = null;
                }
            }
        });
    }

    public void setContext(final ScanResultCallbackProducer scanResultCallbackProducer2) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.scanResultCallbackProducer = scanResultCallbackProducer2;
            }
        });
    }
}

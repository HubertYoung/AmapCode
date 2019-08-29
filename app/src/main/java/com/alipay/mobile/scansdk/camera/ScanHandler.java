package com.alipay.mobile.scansdk.camera;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.android.phone.scancode.export.R;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.mascanengine.MaScanEngineService;
import com.alipay.mobile.scansdk.ui.ScanType;

public class ScanHandler {
    public static final String TAG = "ScanHandler";
    /* access modifiers changed from: private */
    public BQCScanService bqcScanService;
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public int curState = 0;
    private Handler scanHandler;
    private HandlerThread scanHandlerThread = new HandlerThread("Scan-Recognized", 10);
    /* access modifiers changed from: private */
    public ScanResultCallbackProducer scanResultCallbackProducer;
    /* access modifiers changed from: private */
    public MediaPlayer shootMP;

    public interface ScanResultCallbackProducer {
        EngineCallback makeScanResultCallback(ScanType scanType);
    }

    public ScanHandler() {
        this.scanHandlerThread.start();
        this.scanHandler = new Handler(this.scanHandlerThread.getLooper());
    }

    public void destroy() {
        this.scanHandlerThread.quit();
    }

    public void setBqcScanService(final BQCScanService bqcScanService2) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.bqcScanService = bqcScanService2;
                ScanHandler.this.curState = 1;
            }
        });
    }

    public void registerDefaultEngine() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                if (ScanHandler.this.scanResultCallbackProducer != null && 2 > ScanHandler.this.curState) {
                    ScanHandler.this.bqcScanService.regScanEngine(ScanType.SCAN_MA.toBqcScanType(), ((MaScanEngineService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MaScanEngineService.class.getName())).getEngineClazz(), ScanHandler.this.scanResultCallbackProducer.makeScanResultCallback(ScanType.SCAN_MA));
                    ScanHandler.this.curState = 2;
                }
            }
        });
    }

    public void registerAllEngine(boolean switchScanAR) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                if (ScanHandler.this.scanResultCallbackProducer != null) {
                    ScanHandler.this.bqcScanService.regScanEngine(ScanType.SCAN_MA.toBqcScanType(), ((MaScanEngineService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MaScanEngineService.class.getName())).getEngineClazz(), ScanHandler.this.scanResultCallbackProducer.makeScanResultCallback(ScanType.SCAN_MA));
                }
            }
        });
    }

    public void enableScan() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.curState = 4;
                ScanHandler.this.bqcScanService.setScanEnable(true);
            }
        });
    }

    public void setScanType(final ScanType mScanType) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.curState = 5;
                ScanHandler.this.bqcScanService.setScanType(mScanType.toBqcScanType());
            }
        });
    }

    public void disableScan() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.curState = 6;
                ScanHandler.this.bqcScanService.setScanEnable(false);
            }
        });
    }

    public void shootSound() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                if (ScanHandler.this.context != null && ((AudioManager) ScanHandler.this.context.getSystemService("audio")).getStreamVolume(5) != 0) {
                    if (ScanHandler.this.shootMP == null) {
                        ScanHandler.this.shootMP = MediaPlayer.create(ScanHandler.this.context, R.raw.beep);
                    }
                    if (ScanHandler.this.shootMP != null) {
                        ScanHandler.this.shootMP.start();
                    }
                }
            }
        });
    }

    public void removeContext() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.context = null;
                ScanHandler.this.scanResultCallbackProducer = null;
                if (ScanHandler.this.shootMP != null) {
                    ScanHandler.this.shootMP.release();
                    ScanHandler.this.shootMP = null;
                }
            }
        });
    }

    public void reset() {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.curState = 0;
            }
        });
    }

    public void setContext(final Context context2, final ScanResultCallbackProducer scanResultCallbackProducer2) {
        this.scanHandler.post(new Runnable() {
            public void run() {
                ScanHandler.this.context = context2;
                ScanHandler.this.scanResultCallbackProducer = scanResultCallbackProducer2;
            }
        });
    }
}

package com.alipay.mobile.bqcscanservice.impl;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.MaEngineType;
import com.alipay.mobile.bqcscanservice.BQCScanCallback;
import com.alipay.mobile.bqcscanservice.BQCScanEngine;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanError;
import com.alipay.mobile.bqcscanservice.BQCScanError.ErrorType;
import com.alipay.mobile.bqcscanservice.BQCScanResult;
import com.alipay.mobile.bqcscanservice.CameraHandler;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.executor.ScanRecognizedExecutor;
import com.alipay.mobile.bqcscanservice.monitor.ScanResultMonitor;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class BQCScanController implements PreviewCallback {
    private Context a;
    private BQCScanCallback b;
    private ArrayMap<String, Class<? extends BQCScanEngine>> c;
    private ArrayMap<String, EngineCallback> d;
    private BQCScanEngine e = null;
    private ScanTask f;
    private String g = null;
    /* access modifiers changed from: private */
    public Rect h = null;
    /* access modifiers changed from: private */
    public volatile boolean i = false;
    private byte[] j = null;
    private byte[] k = null;
    private int l = 1;
    private Map<String, Object> m;
    /* access modifiers changed from: private */
    public boolean n;
    private Size o;
    private int p = -1;
    private CameraHandler q;
    /* access modifiers changed from: private */
    public Camera r;
    private boolean s = true;
    /* access modifiers changed from: private */
    public volatile ScanResultMonitor t;
    private boolean u;
    /* access modifiers changed from: private */
    public TaskPool v;

    class ScanTask extends BQCScanTask<BQCScanResult> {
        private BQCScanEngine a;
        private boolean b = false;
        private boolean c;
        private long d;

        public ScanTask() {
        }

        public void setEngine(BQCScanEngine engine) {
            this.a = engine;
        }

        public ScanTask(BQCScanEngine engine) {
            this.a = engine;
        }

        public void autoDestroyEngine() {
            if (this.c) {
                this.b = true;
            } else {
                BQCScanController.a(this.a);
            }
        }

        /* access modifiers changed from: protected */
        public BQCScanResult doInBackground() {
            if (BQCScanController.this.i && this.a != null) {
                try {
                    LoggerFactory.getTraceLogger().error((String) "BQCScanController", (String) "scan engine process");
                    this.d = System.currentTimeMillis();
                    return this.a.process(this.mData, this.mCamera, BQCScanController.this.h, this.mPreviewSize, this.mPreviewFormat);
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().error((String) "BQCScanController", (String) "scan task doInBackground exception");
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(BQCScanResult result) {
            if (BQCScanController.this.i && this.a != null) {
                try {
                    BQCScanController.this.t.setCodeSize(this.a.getCodeSize());
                    boolean isSuccess = this.a.onProcessFinish(result);
                    if (BQCScanController.this.n) {
                        long duration = System.currentTimeMillis() - this.d;
                        if (duration > 0 && duration < 2000) {
                            BQCScanController.this.reportCameraFrameRecognized(isSuccess, duration);
                        }
                    }
                    if (isSuccess) {
                        BQCScanController.this.i = false;
                        BQCScanController.this.t.endScan(true);
                    }
                } catch (Exception e) {
                    LoggerFactory.getTraceLogger().error((String) "BQCScanController", (String) "scan task onPostExecute exception");
                }
            }
            if (this.b) {
                BQCScanController.a(this.a);
            }
            this.c = false;
            this.mData = null;
            this.mCamera = null;
            this.mPreviewSize = null;
            if (BQCScanController.this.v != null) {
                BQCScanController.this.v.returnTask();
            }
        }

        public void run() {
            onPreExecute();
            BQCScanResult scanResult = doInBackground();
            Logger.d("BQCScanController", "ScanResult == " + scanResult);
            onPostExecute(scanResult);
        }
    }

    public class TaskPool {
        private volatile int a = 3;
        private volatile int b = 0;
        private ScanTask[] c = new ScanTask[3];

        public TaskPool() {
            for (int i = 0; i < 3; i++) {
                this.c[i] = new ScanTask();
            }
        }

        public ScanTask getTask() {
            if (this.a == 0) {
                return null;
            }
            this.a--;
            int pos = this.b;
            this.b = (this.b + 1) % 3;
            return this.c[pos];
        }

        public void returnTask() {
            this.a++;
        }
    }

    public BQCScanController(Context ctx, Map<String, Object> params, CameraHandler cameraHandler, boolean firstSetup) {
        this.a = ctx;
        this.m = params;
        this.q = cameraHandler;
        this.t = new ScanResultMonitor();
        this.u = firstSetup;
        this.v = new TaskPool();
    }

    public void setEnableFrameRecogCallback(boolean enable) {
        this.n = enable;
    }

    public void setCameraBuffers(byte[] cameraBuffer1, byte[] cameraBuffer2) {
        this.j = cameraBuffer1;
        this.k = cameraBuffer2;
    }

    public void setEngineParams(Map<String, Object> params) {
        this.m = params;
    }

    public void regScanEngine(String type, Class<? extends BQCScanEngine> engine, EngineCallback engineCallback) {
        if (type != null && engine != null) {
            if (this.c == null) {
                this.c = new ArrayMap<>();
            }
            this.c.put(type, engine);
            if (this.d == null) {
                this.d = new ArrayMap<>();
            }
            this.d.put(type, engineCallback);
        }
    }

    public boolean checkEngineRegister(String type) {
        if (TextUtils.isEmpty(type) || this.c == null || this.c.get(type) == null) {
            return false;
        }
        return true;
    }

    public void setResultCallback(BQCScanCallback callback) {
        this.b = callback;
    }

    public void onPreviewFrame(byte[] bytes, Camera camera) {
        long beginTime = System.currentTimeMillis();
        this.r = camera;
        if (bytes != null && camera != null && this.q != null) {
            if (this.i && this.g != null) {
                this.t.startScan();
                if (this.o == null || this.p < 0) {
                    if (this.q.curCameraStateValid()) {
                        Parameters parameters = camera.getParameters();
                        this.o = parameters.getPreviewSize();
                        this.p = parameters.getPreviewFormat();
                    } else {
                        return;
                    }
                }
                if (ScanRecognizedExecutor.isEmpty() || !this.q.curCameraStateValid()) {
                    if (this.v != null) {
                        this.f = this.v.getTask();
                        if (this.f != null) {
                            this.f.setEngine(this.e);
                            this.f.setData(bytes, camera, this.o, this.p);
                            ScanRecognizedExecutor.execute(this.f);
                            this.l++;
                        }
                    }
                    if (this.q.curCameraStateValid()) {
                        this.r.addCallbackBuffer(a());
                    }
                    Logger.d("BQCScanController", "=======> duration: " + (System.currentTimeMillis() - beginTime));
                    return;
                }
                this.r.addCallbackBuffer(a());
            } else if (this.q.curCameraStateValid()) {
                this.r.addCallbackBuffer(a());
            }
        }
    }

    /* access modifiers changed from: private */
    public byte[] a() {
        if (!this.s || this.k == null) {
            return this.j;
        }
        if (this.l % 2 == 0) {
            return this.j;
        }
        return this.k;
    }

    public void reportError(BQCScanError error) {
        if (this.b != null) {
            Logger.d("BQCScanController", "The bqcCallBack is " + this.b);
            try {
                this.b.onError(error);
            } catch (Exception e2) {
                Logger.d("BQCScanController", "reportError: " + e2.getMessage());
            }
        }
    }

    public void reportParametersSet(long postcode) {
        if (this.b != null) {
            this.b.onParametersSetted(postcode);
        }
    }

    public void reportSurfaceViewAvailable() {
        if (this.b != null) {
            this.b.onSurfaceAvaliable();
        }
    }

    public void reportCameraOpened() {
        if (this.b != null) {
            this.b.onCameraOpened();
        }
    }

    public void reportCameraClosed() {
        if (this.b != null) {
            this.b.onCameraClose();
        }
    }

    public void reportPreviewFrameShow() {
        try {
            if (this.b != null) {
                this.b.onPreviewFrameShow();
            }
        } catch (NullPointerException e2) {
            Logger.e("BQCScanController", e2.getMessage(), e2);
        }
    }

    public void reportCameraReady() {
        if (this.b != null) {
            Logger.d("BQCScanController", "reportCameraReady: callback=" + this.b);
            this.b.onCameraReady();
        }
    }

    public void reportCameraFrameRecognized(boolean recog, long duration) {
        if (this.b != null) {
            this.b.onCameraFrameRecognized(recog, duration);
        }
    }

    public boolean setScanType(String type, MaEngineType subEngineType) {
        Logger.d("BQCScanController", "setScanType(): scanType: " + this.g + "setScanType: " + type + ", subEngineType: " + subEngineType);
        if (type == null || this.c == null) {
            return false;
        }
        if (type.equals(this.g)) {
            return true;
        }
        try {
            Class engineClazz = (Class) this.c.get(type);
            if (engineClazz == null) {
                return false;
            }
            boolean needRestoreScan = false;
            if (this.i) {
                this.i = false;
                needRestoreScan = true;
            }
            BQCScanError error = null;
            try {
                Logger.d("BQCScanController", "setScanType(): scanTask:" + this.f);
                if (this.f != null) {
                    this.f.autoDestroyEngine();
                } else {
                    a(this.e);
                }
                Logger.d("BQCScanController", "setScanType(): Begin to init engine class");
                this.e = (BQCScanEngine) engineClazz.newInstance();
                this.t.enabled = this.e.isQrCodeEngine();
                Logger.d("BQCScanController", "setScanType(): End to init engine class");
                if (!this.e.init(this.a, this.m)) {
                    this.e = null;
                    error = new BQCScanError(ErrorType.initEngineError, "init engine fail");
                } else if (this.d != null) {
                    this.e.setResultCallback((EngineCallback) this.d.get(type));
                }
                Logger.d("BQCScanController", "setScanType(): end to init the engine");
            } catch (Exception e2) {
                this.e = null;
                error = new BQCScanError(ErrorType.initEngineError, "init engine fail:" + e2.getMessage());
            }
            if (error != null) {
                reportError(error);
                return false;
            }
            this.g = type;
            if (needRestoreScan) {
                this.i = true;
            }
            if (this.i && this.e != null) {
                this.e.setSubScanType(subEngineType);
                this.e.setWhetherFirstSetup(this.u);
                this.e.start();
            }
            this.t.startScan();
            return true;
        } catch (Exception e3) {
            Logger.e("BQCScanController", "Set ScanType failed", e3);
            return false;
        }
    }

    private void b() {
        if (this.q != null) {
            this.q.post(new Runnable() {
                public void run() {
                    try {
                        byte[] buffer = BQCScanController.this.a();
                        if (BQCScanController.this.r != null && buffer != null) {
                            BQCScanController.this.r.addCallbackBuffer(buffer);
                        }
                    } catch (Exception e) {
                        Logger.e("BQCScanController", "Add Preview Buffer Error", e);
                    }
                }
            });
        }
    }

    public void setScanEnable(boolean enable) {
        this.i = enable;
        if (this.i && this.e != null) {
            this.e.start();
        }
        if (!enable) {
            this.t.disableScan();
        } else {
            this.t.startScan();
        }
        b();
        Logger.e("BQCScanController", "setScanEnable(" + enable + ")");
    }

    public boolean isScanEnable() {
        return this.i;
    }

    public void setScanRegion(Rect region) {
        this.h = region;
    }

    public void destroy() {
        if (this.f != null) {
            this.f.autoDestroyEngine();
        } else {
            a(this.e);
        }
        this.e = null;
        this.a = null;
        this.f = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.r = null;
        this.j = null;
        this.k = null;
    }

    /* access modifiers changed from: private */
    public static void a(BQCScanEngine engine) {
        if (engine != null) {
            try {
                engine.destroy();
            } catch (Exception e2) {
                LoggerFactory.getTraceLogger().error((String) "BQCScanController", (String) "engine destroy exception");
            }
        }
    }

    public ScanResultMonitor getScanResultMonitor() {
        return this.t;
    }

    public void resetPreviewSize() {
        this.o = null;
    }
}

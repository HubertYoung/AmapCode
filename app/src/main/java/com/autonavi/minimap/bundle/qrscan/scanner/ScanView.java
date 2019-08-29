package com.autonavi.minimap.bundle.qrscan.scanner;

import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.ConfigParam;
import com.alipay.mobile.bqcscanservice.BQCScanCallback;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanError;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.bqcscanservice.CameraHandler;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.mascanengine.IOnMaSDKDecodeInfo;
import com.alipay.mobile.mascanengine.MaScanCallback;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.bundle.qrscan.data.IScanResult;
import com.autonavi.minimap.bundle.qrscan.data.MaScanResultWrapper;
import com.autonavi.minimap.bundle.qrscan.scanner.AutoZoomOperator.ZoomOperator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class ScanView extends FrameLayout implements ZoomOperator, ScanResultCallbackProducer {
    public static final int DEFAULT_FRAME_VALUE = -1;
    public static final String TAG = "ToolsContainerLayout";
    private final int HIGH_THRESHOLD;
    private final int LOW_THRESHOLD;
    /* access modifiers changed from: private */
    public AutoZoomOperator autoZoomOperator;
    private int autoZoomState;
    private BQCScanCallback bqcCallback;
    /* access modifiers changed from: private */
    public BQCScanService bqcScanService;
    /* access modifiers changed from: private */
    public boolean bqcServiceSetup;
    /* access modifiers changed from: private */
    public CameraErrorListener cameraErrorListener;
    /* access modifiers changed from: private */
    public CameraGrayListener cameraGrayListener;
    private Map cameraParams;
    /* access modifiers changed from: private */
    public CameraHandler cameraScanHandler;
    private CompatibleConfig compatibleConfig;
    /* access modifiers changed from: private */
    public DecodeListener decodeListener;
    private boolean firstAutoStarted;
    private int frameNum;
    /* access modifiers changed from: private */
    public boolean isAlive;
    /* access modifiers changed from: private */
    public boolean mIsSupportTorch;
    private ScanType mScanType;
    private APTextureView mSurfaceView;
    /* access modifiers changed from: private */
    public TorchView mTorchView;
    /* access modifiers changed from: private */
    public int pauseOrResume;
    /* access modifiers changed from: private */
    public long postcode;
    /* access modifiers changed from: private */
    public ScanFinderView scanFinderView;
    /* access modifiers changed from: private */
    public ScanHandler scanHandler;
    private Rect scanRect;
    /* access modifiers changed from: private */
    public boolean scanSuccess;
    private HashSet<IScanViewStatusListener> scanViewStatusListeners;

    public interface CameraErrorListener {
        void onCameraError(int i);
    }

    public interface CameraGrayListener {
        void onCameraGray(int i);
    }

    public interface DecodeListener {
        void onFailure(int i);

        void onSuccess(IScanResult iScanResult);
    }

    public interface IScanViewStatusListener {
        public static final int DESTROY = -2;
        public static final int MOUNT = 1;
        public static final int PREPARE = 0;
        public static final int UNMOUNT = -1;

        void onDestroy();

        void onMount();

        void onPrepare();

        void onUnmount();
    }

    interface MaScanCallbackWithDecodeInfoSupport extends IOnMaSDKDecodeInfo, MaScanCallback {
    }

    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.LOW_THRESHOLD = 70;
        this.HIGH_THRESHOLD = 140;
        this.mScanType = ScanType.SCAN_MA;
        this.firstAutoStarted = true;
        this.pauseOrResume = 0;
        this.scanSuccess = false;
        this.postcode = -1;
        this.mIsSupportTorch = false;
        this.frameNum = 0;
        this.bqcCallback = new BQCScanCallback() {
            public void onCameraAutoFocus(boolean z) {
            }

            public void onCameraClose() {
            }

            public void onCameraFrameRecognized(boolean z, long j) {
            }

            public void onOuterEnvDetected(boolean z) {
            }

            public void onParametersSetted(long j) {
                if (ScanView.this.isAlive) {
                    ScanView.this.postcode = j;
                    ScanView.this.bqcServiceSetup = true;
                    ScanView.this.configPreviewAndRecognitionEngine();
                }
            }

            public void onSurfaceAvaliable() {
                if (!(ScanView.this.pauseOrResume == -1 || ScanView.this.bqcScanService == null)) {
                    ScanView.this.cameraScanHandler.onSurfaceViewAvailable();
                }
            }

            public void onCameraOpened() {
                if (ScanView.this.pauseOrResume != -1) {
                }
            }

            public void onPreviewFrameShow() {
                if (ScanView.this.pauseOrResume != -1 && ScanView.this.isAlive) {
                    ScanView.this.initScanRect();
                }
            }

            public void onError(BQCScanError bQCScanError) {
                if (ScanView.this.pauseOrResume != -1 && ScanView.this.isAlive) {
                    if (ScanView.this.cameraErrorListener != null) {
                        ScanView.this.cameraErrorListener.onCameraError(0);
                    }
                    new StringBuilder("Can't open Camera Please check camera permission or try to restart app: ").append(bQCScanError != null ? bQCScanError.msg : "");
                }
            }

            public void onCameraReady() {
                ScanView.this.invokeScanViewStatusMount();
            }
        };
        this.scanViewStatusListeners = new HashSet<>();
        onCreate(AMapAppGlobal.getApplication());
    }

    public void onCreate(Application application) {
        dbw a = dbw.a();
        if (a.a) {
            AMapLog.e("AlipayScanInitHelper", "initAlipayScan(), isInitied:true");
        } else {
            QuinoxlessFramework.init();
            a.a = true;
        }
        this.isAlive = true;
        this.cameraParams = new HashMap();
        this.cameraParams.put(ConfigParam.KEY_NOT_USE_DOUBLE_BUFFER, "yes");
        this.autoZoomOperator = new AutoZoomOperator(this);
        this.bqcScanService = AjxScanManager.getInstance().getBQCScanService();
        this.scanViewStatusListeners.add(AjxScanManager.getInstance().getScanViewStatusListener());
        invokeScanViewStatusPrepare();
        this.cameraScanHandler = this.bqcScanService.getCameraHandler();
        this.scanHandler = new ScanHandler();
        this.scanHandler.setBqcScanService(this.bqcScanService);
        try {
            autoStartScan();
        } catch (Exception e) {
            new StringBuilder("autoStartScan: Exception ").append(e.getMessage());
        }
        onCreateView();
    }

    private void onCreateView() {
        this.mSurfaceView = new APTextureView(getContext());
        addView(this.mSurfaceView, new LayoutParams(-1, -1));
        this.scanFinderView = new ScanFinderView(getContext());
        addView(this.scanFinderView, new LayoutParams(-1, -1));
        this.scanFinderView.attachScan();
        this.mIsSupportTorch = new CompatibleConfig().checkSupportTorch(Build.MANUFACTURER, Build.MODEL);
        if (this.mIsSupportTorch) {
            initTorchView();
        }
        configPreviewAndRecognitionEngine();
    }

    private void initTorchView() {
        this.mTorchView = new TorchView(getContext());
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        layoutParams.topMargin = (((displayMetrics.heightPixels / 5) + ((i * 3) / 5)) - agn.a(displayMetrics, 40.0f)) - agn.a(displayMetrics, 10.0f);
        layoutParams.gravity = 1;
        addView(this.mTorchView, layoutParams);
        this.mTorchView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ScanView.this.mTorchView.switchTorch();
            }
        });
        this.mTorchView.setVisibility(8);
        this.mTorchView.changeTorchState(false);
    }

    public void onResume() {
        this.pauseOrResume = 1;
        if (this.scanHandler == null) {
            this.scanHandler = new ScanHandler();
            this.scanHandler.setBqcScanService(this.bqcScanService);
        }
        restartScan();
        if (!this.firstAutoStarted && !this.scanSuccess && this.scanFinderView != null) {
            try {
                autoStartScan();
            } catch (Exception e) {
                new StringBuilder("autoStartScan: Exception ").append(e.getMessage());
            }
        }
    }

    private void autoStartScan() {
        this.cameraScanHandler.init(getContext(), this.bqcCallback);
        this.scanHandler.setContext(this);
        startPreview();
    }

    public void restartScan() {
        if (this.scanHandler != null) {
            this.scanHandler.enableScan();
            this.scanFinderView.onStartScan();
            this.scanSuccess = false;
        }
    }

    public void startPreview() {
        try {
            this.cameraScanHandler.configAndOpenCamera(this.cameraParams);
        } catch (Throwable unused) {
            this.cameraScanHandler.openCamera();
        }
        this.bqcScanService.setScanEnable(true);
    }

    public void onPause() {
        this.pauseOrResume = -1;
        this.firstAutoStarted = false;
        realStopPreview();
        if (!(this.bqcScanService == null || this.cameraScanHandler == null)) {
            this.cameraScanHandler.release(this.postcode);
        }
        if (this.scanFinderView != null) {
            this.scanFinderView.onStopScan();
        }
        this.mTorchView.setVisibility(8);
        this.mTorchView.changeTorchState(false);
    }

    private void realStopPreview() {
        this.cameraScanHandler.closeCamera();
        this.scanHandler.disableScan();
    }

    public void onDestroy() {
        this.isAlive = false;
        invokeScanViewStatusDestroy();
        if (this.scanHandler != null) {
            this.scanHandler.removeContext();
            this.scanHandler.destroy();
        }
        if (this.scanFinderView != null) {
            this.scanFinderView.detachScan();
        }
        if (this.autoZoomOperator != null) {
            this.autoZoomOperator.clearActivity();
        }
    }

    public ScanFinderView getScanFinderView() {
        return this.scanFinderView;
    }

    /* access modifiers changed from: private */
    public void configPreviewAndRecognitionEngine() {
        if (this.mSurfaceView != null && this.bqcServiceSetup) {
            this.bqcScanService.setDisplay(this.mSurfaceView);
            this.cameraScanHandler.onSurfaceViewAvailable();
            if (this.scanHandler == null) {
                this.scanHandler = new ScanHandler();
                this.scanHandler.setBqcScanService(this.bqcScanService);
            }
            this.scanHandler.registerAllEngine();
            setScanType(this.mScanType, true);
        }
    }

    public void setScanType(ScanType scanType, boolean z) {
        if ((z || this.mScanType != scanType) && this.bqcScanService != null) {
            this.scanHandler.disableScan();
            this.mScanType = scanType;
            this.scanHandler.setScanType(this.mScanType);
            this.scanHandler.enableScan();
        }
    }

    /* access modifiers changed from: private */
    public void initScanRect() {
        this.scanRect = this.scanFinderView.getScanRect(this.bqcScanService.getCamera(), this.mSurfaceView.getWidth(), this.mSurfaceView.getHeight());
        if (this.scanFinderView.getCropWidth() > 0.0f) {
            this.bqcScanService.setScanRegion(this.scanRect);
        }
    }

    public void setZoom(int i) {
        if (this.bqcScanService != null) {
            this.bqcScanService.setZoom(i);
        }
    }

    public void onPageResume() {
        invokeScanViewStatusPrepare();
    }

    public void onPageStop() {
        onPause();
        invokeScanViewStatusUnmount();
    }

    public void onPageDestroy() {
        onDestroy();
    }

    public EngineCallback makeScanResultCallback(ScanType scanType) {
        if (scanType == ScanType.SCAN_MA) {
            return new MaScanCallbackWithDecodeInfoSupport() {
                public void onResultMa(MultiMaScanResult multiMaScanResult) {
                    new StringBuilder("EngineCallback.onResultMa()").append(multiMaScanResult);
                    if (!ScanView.this.scanSuccess) {
                        ScanView.this.scanSuccess = true;
                        if (!(ScanView.this.decodeListener == null || multiMaScanResult == null || multiMaScanResult.maScanResults == null || multiMaScanResult.maScanResults.length <= 0)) {
                            ScanView.this.decodeListener.onSuccess(new MaScanResultWrapper(multiMaScanResult.maScanResults[0]));
                        }
                        if (ScanView.this.scanHandler != null) {
                            ScanView.this.scanHandler.disableScan();
                        }
                        if (ScanView.this.scanFinderView != null) {
                            ScanView.this.scanFinderView.onStopScan();
                        }
                    }
                }

                public void onGetMaProportion(float f) {
                    ScanView.this.handleMaProportion(f);
                }

                public void onGetAvgGray(int i) {
                    if (ScanView.this.cameraGrayListener != null) {
                        ScanView.this.cameraGrayListener.onCameraGray(i);
                    }
                    if (ScanView.this.mIsSupportTorch && ScanView.this.mTorchView != null) {
                        if (ScanView.this.mTorchView.isTorchOn() || i < 70) {
                            ScanView.this.mTorchView.post(new Runnable() {
                                public void run() {
                                    ScanView.this.mTorchView.setVisibility(0);
                                }
                            });
                        } else if (i > 140) {
                            ScanView.this.mTorchView.post(new Runnable() {
                                public void run() {
                                    ScanView.this.mTorchView.setVisibility(8);
                                }
                            });
                        }
                    }
                }
            };
        }
        return null;
    }

    public void setCameraErrorListener(CameraErrorListener cameraErrorListener2) {
        this.cameraErrorListener = cameraErrorListener2;
    }

    public void setCameraGrayListener(CameraGrayListener cameraGrayListener2) {
        this.cameraGrayListener = cameraGrayListener2;
    }

    public void setDecodeListener(DecodeListener decodeListener2) {
        this.decodeListener = decodeListener2;
    }

    private void invokeScanViewStatusPrepare() {
        Iterator<IScanViewStatusListener> it = this.scanViewStatusListeners.iterator();
        while (it.hasNext()) {
            IScanViewStatusListener next = it.next();
            if (next != null) {
                next.onPrepare();
            }
        }
    }

    /* access modifiers changed from: private */
    public void invokeScanViewStatusMount() {
        Iterator<IScanViewStatusListener> it = this.scanViewStatusListeners.iterator();
        while (it.hasNext()) {
            IScanViewStatusListener next = it.next();
            if (next != null) {
                next.onMount();
            }
        }
    }

    private void invokeScanViewStatusUnmount() {
        Iterator<IScanViewStatusListener> it = this.scanViewStatusListeners.iterator();
        while (it.hasNext()) {
            IScanViewStatusListener next = it.next();
            if (next != null) {
                next.onUnmount();
            }
        }
    }

    private void invokeScanViewStatusDestroy() {
        Iterator<IScanViewStatusListener> it = this.scanViewStatusListeners.iterator();
        while (it.hasNext()) {
            IScanViewStatusListener next = it.next();
            if (next != null) {
                next.onDestroy();
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleMaProportion(float f) {
        if (this.isAlive) {
            if (this.compatibleConfig == null) {
                this.compatibleConfig = new CompatibleConfig();
            }
            if (this.compatibleConfig.checkSupportAutoZoom() && this.autoZoomState <= 1) {
                double d = (double) f;
                if (d > 0.05d && d < 0.4d) {
                    int i = this.frameNum + 1;
                    this.frameNum = i;
                    if (i >= 5) {
                        this.autoZoomState = 2;
                        final int i2 = (int) (75.0f - (f * 75.0f));
                        post(new Runnable() {
                            public void run() {
                                if (ScanView.this.autoZoomOperator != null) {
                                    ScanView.this.autoZoomOperator.startAutoZoom((float) i2, 0);
                                }
                            }
                        });
                        return;
                    }
                }
                this.autoZoomState = 0;
            }
        }
    }
}

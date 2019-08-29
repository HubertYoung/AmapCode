package com.alipay.mobile.scansdk.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.alipay.android.phone.scancode.export.R;
import com.alipay.mobile.bqcscanservice.BQCScanCallback;
import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;
import com.alipay.mobile.bqcscanservice.BQCScanError;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.alipay.mobile.bqcscanservice.CameraHandler;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.mascanengine.MaScanCallback;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.alipay.mobile.scansdk.activity.BaseScanRouter;
import com.alipay.mobile.scansdk.camera.ScanHandler;
import com.alipay.mobile.scansdk.camera.ScanHandler.ScanResultCallbackProducer;
import com.alipay.mobile.scansdk.ui.APTextureView;
import com.alipay.mobile.scansdk.ui.BaseScanTopView;
import com.alipay.mobile.scansdk.ui.BaseScanTopView.TopViewCallback;
import com.alipay.mobile.scansdk.ui.ScanTopViewFactory;
import com.alipay.mobile.scansdk.ui.ScanType;
import java.util.HashMap;

public class BaseScanFragment extends Fragment implements ScanResultCallbackProducer {
    private final String TAG = "BaseScanFragment";
    private BQCScanCallback bqcCallback = new BQCScanCallback() {
        public void onParametersSetted(final long pcode) {
            if (BaseScanFragment.this.mAttachedActivity != null) {
                BaseScanFragment.this.mAttachedActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        BaseScanFragment.this.postcode = pcode;
                        BaseScanFragment.this.bqcServiceSetuped = true;
                        BaseScanFragment.this.configPreviewAndRecognitionEngine();
                    }
                });
            }
        }

        public void onSurfaceAvaliable() {
            if (BaseScanFragment.this.pauseOrResume != -1 && BaseScanFragment.this.bqcScanService != null) {
                BaseScanFragment.this.cameraScanHandler.onSurfaceViewAvailable();
            }
        }

        public void onCameraOpened() {
            if (BaseScanFragment.this.pauseOrResume == -1) {
            }
        }

        public void onPreviewFrameShow() {
            if (BaseScanFragment.this.pauseOrResume != -1 && BaseScanFragment.this.mAttachedActivity != null && !BaseScanFragment.this.mAttachedActivity.isFinishing()) {
                BaseScanFragment.this.mAttachedActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (BaseScanFragment.this.mScanTopView != null && BaseScanFragment.this.mAttachedActivity != null && !BaseScanFragment.this.mAttachedActivity.isFinishing()) {
                            BaseScanFragment.this.initScanRect();
                            BaseScanFragment.this.mScanTopView.onPreviewShow();
                        }
                    }
                });
            }
        }

        public void onError(BQCScanError bqcScanError) {
            BaseScanFragment.this.topViewOnCameraError();
        }

        public void onCameraAutoFocus(boolean success) {
        }

        public void onOuterEnvDetected(boolean shouldShow) {
        }

        public void onCameraReady() {
        }

        public void onCameraClose() {
        }

        public void onCameraFrameRecognized(boolean b, long l) {
        }
    };
    /* access modifiers changed from: private */
    public BQCScanService bqcScanService;
    /* access modifiers changed from: private */
    public boolean bqcServiceSetuped;
    /* access modifiers changed from: private */
    public CameraHandler cameraScanHandler;
    private boolean firstAutoStarted = false;
    private boolean isPermissionGranted = false;
    /* access modifiers changed from: private */
    public FragmentActivity mAttachedActivity;
    private ViewGroup mContentView;
    private BaseScanRouter mRouter;
    /* access modifiers changed from: private */
    public BaseScanTopView mScanTopView;
    private ScanType mScanType = ScanType.SCAN_MA;
    private APTextureView mSurfaceView;
    /* access modifiers changed from: private */
    public int pauseOrResume = 0;
    /* access modifiers changed from: private */
    public long postcode = -1;
    /* access modifiers changed from: private */
    public ScanHandler scanHandler;
    private Rect scanRect;
    /* access modifiers changed from: private */
    public boolean scanSuccess = false;
    private TopViewCallback topViewCallback = new TopViewCallback() {
        public boolean turnTorch() {
            boolean z = false;
            if (BaseScanFragment.this.bqcScanService == null) {
                return false;
            }
            boolean torchState = BaseScanFragment.this.bqcScanService.isTorchOn();
            BQCScanService access$100 = BaseScanFragment.this.bqcScanService;
            if (!torchState) {
                z = true;
            }
            access$100.setTorch(z);
            return BaseScanFragment.this.bqcScanService.isTorchOn();
        }

        public void startPreview() {
            if (BaseScanFragment.this.scanHandler == null) {
                BaseScanFragment.this.scanHandler = new ScanHandler();
                BaseScanFragment.this.scanHandler.setBqcScanService(BaseScanFragment.this.bqcScanService);
            }
            if (BaseScanFragment.this.bqcScanService != null && BaseScanFragment.this.bqcScanService.getCamera() == null) {
                BaseScanFragment.this.autoStartScan();
            }
        }

        public void stopPreview(boolean clearSurface) {
            BaseScanFragment.this.realStopPreview();
        }

        public void clearSurface() {
        }

        public void scanSuccess() {
            BaseScanFragment.this.scanSuccess = true;
        }

        public void turnEnvDetection(boolean on) {
        }
    };

    public void setRouter(BaseScanRouter router) {
        this.mRouter = router;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseScanFragment", "onCreate");
        this.bqcScanService = (BQCScanService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(BQCScanService.class.getName());
        this.cameraScanHandler = this.bqcScanService.getCameraHandler();
        this.scanHandler = new ScanHandler();
        this.scanHandler.setBqcScanService(this.bqcScanService);
        if (PermissionChecker.checkSelfPermission(this.mAttachedActivity, "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
            return;
        }
        this.isPermissionGranted = true;
        this.firstAutoStarted = true;
        try {
            autoStartScan();
        } catch (Exception ex) {
            LoggerFactory.getTraceLogger().error((String) "BaseScanFragment", "autoStartScan: Exception " + ex.getMessage());
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("BaseScanFragment", "onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        if (this.mContentView == null) {
            this.mContentView = (ViewGroup) inflater.inflate(R.layout.fragment_base_scan, container, false);
        }
        return this.mContentView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("BaseScanFragment", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        this.mSurfaceView = (APTextureView) this.mContentView.findViewById(R.id.surfaceView);
        configPreviewAndRecognitionEngine();
        afterSetContentView();
    }

    public void afterSetContentView() {
        RelativeLayout mTopViewContainer = (RelativeLayout) this.mContentView.findViewById(R.id.top_view_container);
        this.mScanTopView = new ScanTopViewFactory().getScanTopView(this.mAttachedActivity, getArguments());
        mTopViewContainer.removeAllViews();
        mTopViewContainer.addView(this.mScanTopView, -1, -1);
        this.mScanTopView.onInitCamera();
        this.mScanTopView.setTopViewCallback(this.topViewCallback);
        this.mScanTopView.setCodeRouter(this.mRouter);
    }

    /* access modifiers changed from: private */
    public void configPreviewAndRecognitionEngine() {
        if (this.mSurfaceView != null && this.bqcServiceSetuped) {
            this.bqcScanService.setDisplay(this.mSurfaceView);
            this.cameraScanHandler.onSurfaceViewAvailable();
            if (this.scanHandler == null) {
                this.scanHandler = new ScanHandler();
                this.scanHandler.setBqcScanService(this.bqcScanService);
            }
            this.scanHandler.registerAllEngine(false);
            setScanType(this.mScanType, true);
        }
    }

    public EngineCallback makeScanResultCallback(ScanType type) {
        if (type == ScanType.SCAN_MA) {
            return new MaScanCallback() {
                public void onResultMa(MultiMaScanResult multiMaScanResult) {
                    BaseScanFragment.this.scanSuccess = true;
                    if (BaseScanFragment.this.bqcScanService != null) {
                        BaseScanFragment.this.bqcScanService.setScanEnable(false);
                    }
                    if (BaseScanFragment.this.mScanTopView != null) {
                        BaseScanFragment.this.mScanTopView.onStopScan();
                    }
                    BaseScanFragment.this.scanHandler.shootSound();
                    if (BaseScanFragment.this.mScanTopView != null) {
                        BaseScanFragment.this.mScanTopView.onResultMa(multiMaScanResult);
                    }
                }
            };
        }
        return null;
    }

    public void onResume() {
        Log.d("BaseScanFragment", "onResume");
        super.onResume();
        this.pauseOrResume = 1;
        if (this.scanHandler == null) {
            this.scanHandler = new ScanHandler();
            this.scanHandler.setBqcScanService(this.bqcScanService);
        }
        if (!this.firstAutoStarted && !this.scanSuccess && this.isPermissionGranted) {
            try {
                autoStartScan();
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().error((String) "BaseScanFragment", "autoStartScan: Exception " + ex.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public void autoStartScan() {
        if (this.mScanTopView != null) {
            this.mScanTopView.onInitCamera();
        }
        this.cameraScanHandler.init(this.mAttachedActivity, this.bqcCallback);
        this.scanHandler.setContext(this.mAttachedActivity, this);
        startPreview();
    }

    public void onPause() {
        Log.d("BaseScanFragment", "onPause");
        super.onPause();
        this.pauseOrResume = -1;
        this.firstAutoStarted = false;
        if (this.isPermissionGranted) {
            realStopPreview();
        }
        if (this.bqcScanService != null) {
            this.cameraScanHandler.release(this.postcode);
        }
        this.scanHandler.reset();
    }

    public void startPreview() {
        initCameraParams();
        this.bqcScanService.setScanEnable(true);
    }

    /* access modifiers changed from: private */
    public void realStopPreview() {
        this.cameraScanHandler.closeCamera();
        this.scanHandler.disableScan();
    }

    /* access modifiers changed from: private */
    public void initScanRect() {
        this.mScanTopView.onStartScan();
        if (this.scanRect == null) {
            this.scanRect = this.mScanTopView.getScanRect(this.bqcScanService.getCamera(), this.mSurfaceView.getWidth(), this.mSurfaceView.getHeight());
        }
        this.bqcScanService.setScanRegion(this.scanRect);
    }

    /* access modifiers changed from: private */
    public void topViewOnCameraError() {
        if (this.mAttachedActivity != null && !this.mAttachedActivity.isFinishing()) {
            this.mScanTopView.onCameraOpenFailed();
            Toast.makeText(this.mAttachedActivity, getResources().getString(R.string.camera_open_error), 0).show();
        }
    }

    public void setScanType(ScanType scanType, boolean firstAutoStarted2) {
        if ((firstAutoStarted2 || this.mScanType != scanType) && this.bqcScanService != null) {
            this.scanHandler.disableScan();
            this.mScanType = scanType;
            this.scanHandler.setScanType(this.mScanType);
            this.scanHandler.enableScan();
        }
    }

    public ScanType getScanType() {
        return this.mScanType;
    }

    public void onAttach(Activity activity) {
        Log.d("BaseScanFragment", "onAttach");
        super.onAttach(activity);
        this.mAttachedActivity = (FragmentActivity) activity;
    }

    public void onDestroy() {
        Log.d("BaseScanFragment", "onDestroy");
        super.onDestroy();
        if (this.scanHandler != null) {
            this.scanHandler.removeContext();
            this.scanHandler.destroy();
        }
    }

    public void onDetach() {
        Log.d("BaseScanFragment", "onDetach");
        super.onDetach();
        this.mAttachedActivity = null;
    }

    private void initCameraParams() {
        this.cameraScanHandler.configAndOpenCamera(new HashMap<>());
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && permissions != null && grantResults != null) {
            int i = 0;
            while (true) {
                if (i >= permissions.length || i >= grantResults.length) {
                    break;
                }
                if (TextUtils.equals(permissions[i], "android.permission.CAMERA")) {
                    if (grantResults[i] != 0) {
                        showPermissionDenied();
                        break;
                    }
                    this.firstAutoStarted = true;
                    this.isPermissionGranted = true;
                    try {
                        autoStartScan();
                    } catch (Exception ex) {
                        LoggerFactory.getTraceLogger().error((String) "BaseScanFragment", "autoStartScan: Exception " + ex.getMessage());
                    }
                }
                i++;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /* access modifiers changed from: 0000 */
    public void showPermissionDenied() {
        if (this.mAttachedActivity != null && !this.mAttachedActivity.isFinishing()) {
            Toast.makeText(this.mAttachedActivity, getResources().getString(R.string.camera_no_permission), 0).show();
        }
    }
}

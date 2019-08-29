package com.alipay.zoloz.toyger.workspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.biometrics.ui.widget.AlgorithmInfo;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.sensor.SensorCollectors;
import com.alipay.mobile.security.bio.sensor.SensorData;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioUploadCallBack;
import com.alipay.mobile.security.bio.service.BioUploadResult;
import com.alipay.mobile.security.bio.service.BioUploadService;
import com.alipay.mobile.security.bio.task.ActionFrame;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.DeviceUtil;
import com.alipay.mobile.security.bio.utils.RotateBitmapHelper;
import com.alipay.mobile.security.bio.workspace.BioFragmentResponse;
import com.alipay.mobile.security.faceauth.circle.protocol.DeviceSetting;
import com.alipay.mobile.security.faceauth.model.DetectTimerTask;
import com.alipay.zoloz.hardware.camera.a;
import com.alipay.zoloz.hardware.camera.b;
import com.alipay.zoloz.hardware.camera.c;
import com.alipay.zoloz.hardware.camera.widget.CameraSurfaceView;
import com.alipay.zoloz.toyger.BuildConfig;
import com.alipay.zoloz.toyger.ToygerService;
import com.alipay.zoloz.toyger.algorithm.TGDepthFrame;
import com.alipay.zoloz.toyger.algorithm.TGFaceAttr;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.bean.FrameType;
import com.alipay.zoloz.toyger.bean.ToygerError;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import com.alipay.zoloz.toyger.blob.BitmapHelper;
import com.alipay.zoloz.toyger.extservice.ToygerIspService;
import com.alipay.zoloz.toyger.extservice.record.TimeRecord;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.face.ToygerFaceAttr;
import com.alipay.zoloz.toyger.face.ToygerFaceCallback;
import com.alipay.zoloz.toyger.face.ToygerFaceService;
import com.alipay.zoloz.toyger.face.ToygerFaceState;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.upload.NineShootManager;
import com.alipay.zoloz.toyger.upload.UploadContent;
import com.alipay.zoloz.toyger.upload.UploadManager;
import com.alipay.zoloz.toyger.util.EnvCheck;
import com.alipay.zoloz.toyger.util.EnvErrorType;
import com.alipay.zoloz.toyger.util.ObjectUtil;
import com.alipay.zoloz.toyger.util.PoseUtil;
import com.alipay.zoloz.toyger.util.ToygerFrameUtil;
import com.alipay.zoloz.toyger.widget.ToygerCirclePattern;
import com.alipay.zoloz.toyger.workspace.alert.AlertHelper;
import com.alipay.zoloz.toyger.workspace.alert.AlertType;
import com.alipay.zoloz.toyger.workspace.assist.WorkState;
import com.alipay.zoloz.toyger.workspace.task.ToygerBaseTask;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ToygerWorkspace implements BioUploadCallBack, b, ToygerFaceCallback {
    /* access modifiers changed from: private */
    public static int AUTH_IN_BACKGROUND_GEN_AVATAR_DELAY_TIME = 400;
    private static int AUTH_IN_BACKGROUND_LIVENESS_FAIL_DESTROY_DELAY_TIME = 700;
    private static int SENSOR_TIMER_INTERNAL = 100;
    public static final int WHAT_START_UPLOAD = 1;
    public static final int WHAT_START_UPLOAD_PAGE = 2;
    private boolean isAlgorithRunning = false;
    private boolean isCheckedFace = false;
    /* access modifiers changed from: private */
    public boolean isFirstTime;
    private boolean isPaused = false;
    private TGFrame lastFrame = null;
    /* access modifiers changed from: private */
    public AlertHelper mAlertHelper;
    private int mAlgorithAngle = 270;
    /* access modifiers changed from: private */
    public BioServiceManager mBioServiceManager;
    private BioUploadService mBioUploadService;
    private c mCameraInterface;
    /* access modifiers changed from: private */
    public ToygerFrame mCurrentToygerFrame = null;
    /* access modifiers changed from: private */
    public DetectTimerTask mDetectTimerTask;
    private DeviceSetting mDeviceSetting = null;
    private boolean mIsAuthInBackground = false;
    protected boolean mIsBeUploadPage = false;
    /* access modifiers changed from: private */
    public SensorCollectors mSensorCollectors = null;
    /* access modifiers changed from: private */
    public Vector<SensorData> mSensorData = null;
    private DetectTimerTask mSensorTimerTask;
    /* access modifiers changed from: private */
    public ToygerCallback mToygerCallback;
    /* access modifiers changed from: private */
    public ToygerCirclePattern mToygerCirclePattern;
    private ToygerFaceService mToygerFaceService = new ToygerFaceService();
    private ToygerIspService mToygerIspService;
    protected ToygerRecordService mToygerRecordService;
    private ToygerTaskManager mToygerTaskManager;
    /* access modifiers changed from: private */
    public UploadManager mUploadManager;
    /* access modifiers changed from: private */
    public WorkState mWorkState;
    /* access modifiers changed from: private */
    public Handler mWorkspaceHandler;
    private PoseUtil poseUtil;
    private a tempCameraData = null;

    public class WorkspaceHandler extends Handler {
        public WorkspaceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    ToygerWorkspace.this.mWorkState = WorkState.UPLOADING;
                    return;
                case 2:
                    ToygerWorkspace.this.mIsBeUploadPage = true;
                    ToygerWorkspace.this.stopTimerTask();
                    return;
                default:
                    return;
            }
        }
    }

    public ToygerWorkspace(BioServiceManager bioServiceManager, ToygerCallback toygerCallback, ToygerCirclePattern toygerCirclePattern) {
        BioLog.i("zolozTime", "smiletopay view end");
        Context bioApplicationContext = bioServiceManager.getBioApplicationContext();
        FaceRemoteConfig remoteConfig = toygerCallback.getRemoteConfig();
        this.poseUtil = new PoseUtil(remoteConfig, bioApplicationContext.getResources());
        this.mBioServiceManager = bioServiceManager;
        this.mToygerCallback = toygerCallback;
        Map<String, String> extProperty = this.mToygerCallback.getAppDescription().getExtProperty();
        if (extProperty == null || extProperty.isEmpty() || !extProperty.containsKey(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND)) {
            this.mIsAuthInBackground = false;
        } else {
            this.mIsAuthInBackground = Boolean.parseBoolean(extProperty.get(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND));
        }
        this.mToygerCirclePattern = toygerCirclePattern;
        if (DeviceUtil.isDebug(bioApplicationContext)) {
            if ("Cherry".equalsIgnoreCase("Cherry")) {
                this.mToygerCirclePattern.getAlgorithmInfoPattern().setVisibility(8);
            } else {
                this.mToygerCirclePattern.getAlgorithmInfoPattern().setVisibility(0);
            }
        }
        this.mToygerRecordService = (ToygerRecordService) this.mBioServiceManager.getBioService(ToygerRecordService.class);
        this.mBioUploadService = (BioUploadService) this.mBioServiceManager.getBioService(BioUploadService.class);
        this.mBioUploadService.addCallBack(this);
        this.mToygerIspService = (ToygerIspService) this.mBioServiceManager.getBioService(ToygerIspService.class);
        this.mWorkspaceHandler = new WorkspaceHandler(Looper.getMainLooper());
        this.mUploadManager = new UploadManager(this, this.mBioServiceManager, this.mToygerCallback);
        this.mToygerTaskManager = new ToygerTaskManager(this.mBioServiceManager, this.mToygerCirclePattern, this.mWorkspaceHandler, this.mToygerCallback, this.mUploadManager);
        this.mAlertHelper = new AlertHelper(bioApplicationContext, this, toygerCallback, this.mUploadManager);
        this.mAlertHelper.setAuthInBackground(false);
        HashMap hashMap = new HashMap();
        hashMap.put(ToygerService.KEY_PUBLIC_KEY, UploadManager.getPublicKey(bioApplicationContext, remoteConfig));
        if (extProperty != null && !extProperty.isEmpty() && extProperty.containsKey("meta_serializer")) {
            hashMap.put("meta_serializer", extProperty.get("meta_serializer"));
        }
        if (CameraSurfaceView.getCameraName().contains("AstraPro")) {
            hashMap.put(ToygerService.KEY_IS_MIRROR, Boolean.toString(true));
        }
        if (!this.mToygerFaceService.init(bioApplicationContext, (ToygerFaceCallback) this, remoteConfig.getAlgorithm().toJSONString(), remoteConfig.getUpload().toJSONString(), (Map<String, Object>) hashMap)) {
            this.mAlertHelper.alert(AlertType.ALERT_SYSTEM_ERROR);
            return;
        }
        this.mToygerCirclePattern.getTitleBar().setBackButtonListener(this.mAlertHelper);
        BioLog.i("zolozTime", "camera call");
        this.mCameraInterface = CameraSurfaceView.getCameraImpl(bioApplicationContext);
        this.mToygerCirclePattern.getCameraSurfaceView().setCameraCallback(this);
        this.mCameraInterface.setCallback(this);
        this.mWorkState = WorkState.FACE_CAPTURING;
        initRotation();
    }

    private void initRotation() {
        DeviceSetting[] deviceSettings = this.mToygerCallback.getRemoteConfig().getDeviceSettings();
        if (deviceSettings == null || deviceSettings.length <= 0) {
            this.mDeviceSetting = new DeviceSetting();
        } else {
            this.mDeviceSetting = deviceSettings[0];
        }
        int cameraViewRotation = this.mCameraInterface.getCameraViewRotation();
        int i = (360 - cameraViewRotation) % 360;
        if (this.mDeviceSetting.isAlgorithmAuto()) {
            this.mAlgorithAngle = i;
            BioLog.w((String) "Toyger", "onSurfaceChanged() : mAlgorithAngle=" + this.mAlgorithAngle + ", mCameraInterface.getCameraViewRotation()=" + cameraViewRotation);
            return;
        }
        this.mAlgorithAngle = this.mDeviceSetting.getAlgorithmAngle();
        BioLog.w((String) "Toyger", "onSurfaceChanged() : mAlgorithAngle=" + this.mAlgorithAngle);
    }

    private void sendActionFrame(ActionFrame<ToygerFrame> actionFrame) {
        if (this.mWorkspaceHandler != null) {
            BioLog.d(ToygerService.TAG, "ToygerWorkspace.sendActionFrame() : " + actionFrame.getObject());
            this.mWorkspaceHandler.post(new d(this, actionFrame));
        }
    }

    public void onDoAction(ActionFrame actionFrame) {
        ToygerFrame toygerFrame = (ToygerFrame) actionFrame.getObject();
        if (toygerFrame.frameType == FrameType.CAMERA && this.mUploadManager != null) {
            NineShootManager nineShootManager = this.mUploadManager.getNineShootManager();
            if (nineShootManager != null) {
                nineShootManager.shootToygerFrame(toygerFrame);
            }
        } else if ((this.mAlertHelper != null && this.mAlertHelper.isAlertRunning()) || ((this.isPaused && !this.mIsAuthInBackground) || (this.mDetectTimerTask != null && this.mDetectTimerTask.isTimeOut()))) {
            BioLog.i("ToygerWorkspace.onDoAction() return. => isPaused=" + this.isPaused + ", mIsProgressCallback=" + this.mIsAuthInBackground);
        } else if (toygerFrame.frameType != FrameType.ERROR) {
            if (this.mToygerTaskManager != null) {
                BioLog.i(ToygerService.TAG, "ToygerTaskManager.action() : " + toygerFrame.frameType);
                this.mToygerTaskManager.action(actionFrame);
                if (toygerFrame.frameType == FrameType.FRAME || toygerFrame.frameType == FrameType.STATE) {
                    this.mCurrentToygerFrame = toygerFrame;
                }
            }
            if (!this.isPaused && this.mBioServiceManager != null && DeviceUtil.isDebug(this.mBioServiceManager.getBioApplicationContext()) && toygerFrame != null && toygerFrame.frameType != FrameType.COMPLETED) {
                this.mWorkspaceHandler.post(new h(this, toygerFrame));
            }
        } else if (toygerFrame.error == ToygerError.CAMERA_ERROR) {
            this.mAlertHelper.alert(AlertType.ALERT_NO_PERMISSION_OF_CAMERA);
        } else if (toygerFrame.error == ToygerError.ALGORITHM_ERROR) {
            this.mAlertHelper.alert(AlertType.ALERT_SYSTEM_ERROR);
        } else if (toygerFrame.error == ToygerError.LIVENESS_FAIL) {
            BioLog.i("zolozTime", "liveness fail!");
            new Thread(new f(this), "uploadBehaviourLog").start();
            if (this.mIsAuthInBackground) {
                this.mWorkspaceHandler.postDelayed(new g(this), (long) AUTH_IN_BACKGROUND_LIVENESS_FAIL_DESTROY_DELAY_TIME);
            } else {
                this.mAlertHelper.alert(AlertType.ALERT_FACE_FAIL);
            }
        }
    }

    public void init() {
        checkEnv();
        startTimerTask();
        this.mToygerTaskManager.resetTask();
    }

    /* access modifiers changed from: private */
    public AlgorithmInfo faceAttrToAlgAttr(TGFaceAttr tGFaceAttr) {
        AlgorithmInfo algorithmInfo = new AlgorithmInfo();
        if (tGFaceAttr != null) {
            algorithmInfo.setBrightness(tGFaceAttr.brightness);
            algorithmInfo.setHasFace(tGFaceAttr.hasFace);
            algorithmInfo.setEyeBlink(tGFaceAttr.eyeBlink);
            algorithmInfo.setFaceRegion(tGFaceAttr.faceRegion);
            algorithmInfo.setQuality(tGFaceAttr.quality);
            algorithmInfo.setYaw(tGFaceAttr.yaw);
            algorithmInfo.setPitch(tGFaceAttr.pitch);
            algorithmInfo.setGaussian(tGFaceAttr.gaussian);
            algorithmInfo.setIntegrity(tGFaceAttr.integrity);
            algorithmInfo.setLeftEyeBlinkRatio(tGFaceAttr.leftEyeBlinkRatio);
            algorithmInfo.setRightEyeBlinkRatio(tGFaceAttr.rightEyeBlinkRatio);
            algorithmInfo.setDistance(tGFaceAttr.distance);
        }
        return algorithmInfo;
    }

    public void resume() {
        BioLog.d("ToygerWorkspace.resume()");
        if (this.mToygerCallback.haveCameraPermission()) {
            setCameraVisible(true);
            if (this.isPaused) {
                if (!this.mIsBeUploadPage) {
                    this.mAlertHelper.alert(AlertType.ALERT_INTERRUPT_RESUME);
                }
                this.isPaused = false;
            }
        }
    }

    public void pause() {
        BioLog.d("ToygerWorkspace.pause()");
        if (this.mToygerCallback.haveCameraPermission()) {
            if (!this.mIsAuthInBackground) {
                setCameraVisible(false);
            }
            if (!this.mIsBeUploadPage) {
                this.isPaused = true;
            }
            stopTimerTask();
        }
    }

    public void destroy() {
        BioLog.d("ToygerWorkspace.destroy(), mWorkState=" + this.mWorkState);
        this.mToygerFaceService.release();
        this.mToygerFaceService = null;
        if (this.mCameraInterface != null) {
            if (TextUtils.equals(com.alipay.android.phone.a.a.a.a, CameraSurfaceView.getCameraName())) {
                this.mCameraInterface.closeCamera();
            } else {
                this.mCameraInterface.stopCamera();
            }
            this.mCameraInterface = null;
        }
        stopTimerTask();
        if (this.mToygerCirclePattern != null) {
            this.mToygerCirclePattern.pause();
            this.mToygerCirclePattern.destroy();
            this.mToygerCirclePattern = null;
        }
        this.mBioServiceManager = null;
        this.mToygerCallback = null;
        if (this.mToygerTaskManager != null) {
            this.mToygerTaskManager.destroy();
            this.mToygerTaskManager = null;
        }
        this.mWorkspaceHandler = null;
        if (this.mUploadManager != null) {
            this.mUploadManager.destroy();
            this.mUploadManager = null;
        }
        this.mBioUploadService = null;
        this.mWorkspaceHandler = null;
        this.mCurrentToygerFrame = null;
        this.lastFrame = null;
        this.tempCameraData = null;
        this.poseUtil = null;
        this.mAlertHelper = null;
    }

    public void retry() {
        this.mCurrentToygerFrame = null;
        this.mToygerRecordService.retry();
        this.mToygerFaceService.reset();
        this.mWorkState = WorkState.FACE_CAPTURING;
        this.mToygerTaskManager.resetTask();
        this.mToygerCirclePattern.getCircleUploadPattern().setVisibility(8);
        this.mToygerCirclePattern.getTitleBar().setVisibility(0);
        this.mToygerCirclePattern.getTitleBar().setCloseButtonVisible(0);
        this.mIsBeUploadPage = false;
        startTimerTask();
        this.mCameraInterface.startCamera();
        this.isAlgorithRunning = false;
    }

    public void setCameraVisible(boolean z) {
        if (this.mToygerCirclePattern != null) {
            this.mToygerCirclePattern.setCameraVisible(z);
        }
    }

    public void alertCameraPermission() {
        this.mAlertHelper.alert(AlertType.ALERT_NO_PERMISSION_OF_CAMERA);
    }

    public void onSurfaceCreated() {
        this.mWorkState = WorkState.FACE_CAPTURING;
        BioLog.w((String) "Toyger", (String) "onSurfaceCreated()");
        this.mToygerRecordService.write(ToygerRecordService.IMAGE_CAPTURE_START);
    }

    public void onSurfaceChanged(double d, double d2) {
        int cameraViewRotation = this.mCameraInterface.getCameraViewRotation();
        int i = (360 - cameraViewRotation) % 360;
        this.mWorkspaceHandler.post(new i(this, d, d2));
        if (this.mDeviceSetting.isAlgorithmAuto()) {
            this.mAlgorithAngle = i;
            BioLog.w((String) "Toyger", "onSurfaceChanged() : mAlgorithAngle=" + this.mAlgorithAngle + ", mCameraInterface.getCameraViewRotation()=" + cameraViewRotation);
            return;
        }
        this.mAlgorithAngle = this.mDeviceSetting.getAlgorithmAngle();
        BioLog.w((String) "Toyger", "onSurfaceChanged() : mAlgorithAngle=" + this.mAlgorithAngle);
    }

    public void onSurfaceDestroyed() {
        BioLog.w((String) "Toyger", (String) "onSurfaceDestroyed()");
    }

    public void onError(int i) {
        BioLog.w((Throwable) new RuntimeException("ICameraCallback.onError(error=" + i + ")"));
        if (this.mToygerCallback.haveCameraPermission()) {
            switch (i) {
                case -1:
                    alertCameraPermission();
                    return;
                case 1:
                case 2:
                case 3:
                    responseWithCode(200);
                    Process.killProcess(Process.myPid());
                    return;
                default:
                    return;
            }
        }
    }

    public void onPreviewFrame(a aVar) {
        ArrayList arrayList;
        TGDepthFrame tGDepthFrame;
        int i = 0;
        this.tempCameraData = aVar;
        if (!this.mToygerIspService.isInitialized()) {
            this.mToygerIspService.init(aVar.b, aVar.c, aVar.h, aVar.i, aVar.d);
        }
        if (this.mWorkState != WorkState.FACE_CAPTURING && this.mWorkState != WorkState.FACE_CAPTURING_DARK) {
            return;
        }
        if (this.isAlgorithRunning) {
            BioLog.e((String) "Toyger", (String) "Lost Frame => isAlgorithRunning = true");
            return;
        }
        try {
            this.isAlgorithRunning = true;
            if (this.mWorkState != WorkState.FACE_CAPTURING) {
                i = 1;
            }
            BioLog.e((String) "Toyger", "mToygerFaceService.processImage(cameraData, type=" + i + ", mAlgorithAngle=" + this.mAlgorithAngle + ")");
            ByteBuffer byteBuffer = aVar.a;
            if (byteBuffer != null) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(new TGFrame(byteBuffer, aVar.b, aVar.c, this.mAlgorithAngle, aVar.d, i));
                arrayList = arrayList2;
            } else {
                arrayList = null;
            }
            ShortBuffer shortBuffer = aVar.e;
            if (shortBuffer != null) {
                tGDepthFrame = new TGDepthFrame(shortBuffer, aVar.f, aVar.g, this.mAlgorithAngle);
            } else {
                tGDepthFrame = null;
            }
            if (!this.mToygerFaceService.processImage(arrayList, tGDepthFrame)) {
                this.isAlgorithRunning = false;
                BioLog.e((String) "Toyger", (String) "Lost Frame => PreviewDataQueue.offer() = false");
            } else if (arrayList != null && arrayList.size() > 0) {
                onProcessOneImageFinish((TGFrame) arrayList.get(0));
            }
        } catch (Exception e) {
            BioLog.e((String) "Toyger", (Throwable) e);
        }
    }

    public boolean onResponse(BioUploadResult bioUploadResult) {
        BioLog.i("ToygerWorkspace.onResponse() : " + bioUploadResult);
        int i = bioUploadResult.productRetCode;
        HashMap hashMap = new HashMap();
        hashMap.put("serverReturnCode", bioUploadResult.productRetCode);
        hashMap.put("code", String.valueOf(i));
        hashMap.put("productRetCode", bioUploadResult.productRetCode);
        hashMap.put("validationRetCode", bioUploadResult.validationRetCode);
        hashMap.put("hasNext", bioUploadResult.hasNext);
        if (bioUploadResult.extParams != null && !bioUploadResult.extParams.isEmpty()) {
            hashMap.putAll(bioUploadResult.extParams);
        }
        hashMap.put("networkResult", i == 1001 ? "yes" : BQCCameraParam.VALUE_NO);
        hashMap.put("timecost", (System.currentTimeMillis() - TimeRecord.getInstance().getUploadStartTime()));
        if (hashMap.containsKey("avatar")) {
            hashMap.remove("avatar");
        }
        this.mToygerRecordService.write(ToygerRecordService.UPLOAD_END, hashMap);
        switch (i) {
            case 1001:
                BioFragmentResponse bioFragmentResponse = new BioFragmentResponse();
                bioFragmentResponse.isSucess = bioUploadResult.productRetCode == 1001;
                bioFragmentResponse.suggest = bioUploadResult.productRetCode;
                bioFragmentResponse.errorCode = 500;
                bioFragmentResponse.resultMessage = bioUploadResult.subMsg;
                bioFragmentResponse.ext.put(BioDetector.EXT_KEY_UPLOAD_RESPONSE, JSON.toJSONString(bioUploadResult));
                if (this.mToygerCallback != null) {
                    bioFragmentResponse.token = this.mToygerCallback.getAppDescription().getBistoken();
                    this.mToygerCallback.sendResponse(bioFragmentResponse);
                    this.mToygerCallback.finishActivity(true);
                    break;
                }
                break;
            case 2002:
                if (this.mAlertHelper != null) {
                    if (!this.mIsAuthInBackground) {
                        if (!this.isPaused) {
                            this.mAlertHelper.alert(AlertType.ALERT_REMOTE_COMMAND_FAIL_RETRY);
                            break;
                        }
                    } else {
                        responseWithCode(this.mAlertHelper.getAlertReturnCode(AlertType.ALERT_REMOTE_COMMAND_FAIL_RETRY));
                        break;
                    }
                }
                break;
            case 3001:
            case 3002:
                if (this.mAlertHelper != null) {
                    if (!this.mIsAuthInBackground) {
                        if (!this.isPaused) {
                            this.mAlertHelper.alert(AlertType.ALERT_REMOTE_NETWORK_ERROR);
                            break;
                        }
                    } else {
                        responseWithCode(this.mAlertHelper.getAlertReturnCode(AlertType.ALERT_REMOTE_NETWORK_ERROR));
                        break;
                    }
                }
                break;
            default:
                if (this.mAlertHelper != null) {
                    if (!this.mIsAuthInBackground) {
                        if (!this.isPaused) {
                            if (!BuildConfig.UI_ORANGE.equalsIgnoreCase("Cherry")) {
                                BioFragmentResponse bioFragmentResponse2 = new BioFragmentResponse();
                                bioFragmentResponse2.isSucess = false;
                                bioFragmentResponse2.suggest = bioUploadResult.productRetCode;
                                bioFragmentResponse2.errorCode = 208;
                                bioFragmentResponse2.resultMessage = bioUploadResult.subMsg;
                                bioFragmentResponse2.subCode = bioUploadResult.subCode;
                                bioFragmentResponse2.subMsg = bioUploadResult.subMsg;
                                if (this.mToygerCallback != null) {
                                    bioFragmentResponse2.token = this.mToygerCallback.getAppDescription().getBistoken();
                                    this.mToygerCallback.sendResponse(bioFragmentResponse2);
                                    this.mToygerCallback.finishActivity(true);
                                    break;
                                }
                            } else {
                                this.mAlertHelper.alert(AlertType.ALERT_REMOTE_COMMAND_FAIL);
                                break;
                            }
                        }
                    } else {
                        responseWithCode(this.mAlertHelper.getAlertReturnCode(AlertType.ALERT_REMOTE_COMMAND_FAIL));
                        break;
                    }
                }
                break;
        }
        return false;
    }

    public boolean onProcessOneImageFinish(TGFrame tGFrame) {
        this.isAlgorithRunning = false;
        this.lastFrame = tGFrame;
        ToygerFrame toygerFrame = new ToygerFrame();
        toygerFrame.frameType = FrameType.CAMERA;
        toygerFrame.tgFrame = tGFrame;
        sendActionFrame(new ActionFrame(toygerFrame));
        return true;
    }

    public boolean onComplete(int i, byte[] bArr, byte[] bArr2, boolean z) {
        BioLog.i("zolozTime", "liveness end!");
        stopTimerTask();
        this.mWorkState = WorkState.FACE_COMPLETED;
        BioLog.i(ToygerService.TAG, "onComplete(result=" + i + ", content=" + (bArr == null ? "***" : "null") + ", key=" + (bArr2 == null ? "***" : "null") + ", isUTF8=" + z + ")");
        Map<String, String> faceParam = ToygerFrameUtil.getFaceParam(this.mCurrentToygerFrame.tgFaceAttr);
        faceParam.put("bis_action", "live_body_end");
        faceParam.put("timecost", (System.currentTimeMillis() - TimeRecord.getInstance().getLivebodyStartTime()));
        this.mToygerRecordService.write(ToygerRecordService.LIVEBODY_END, faceParam);
        HashMap<String, String> objectToStringMap = ObjectUtil.objectToStringMap(this.mCurrentToygerFrame.tgFaceState);
        objectToStringMap.put("result", "true");
        this.mToygerRecordService.write(ToygerRecordService.IMAGE_CAPTURE_END, objectToStringMap);
        ToygerFrame toygerFrame = new ToygerFrame();
        toygerFrame.frameType = FrameType.COMPLETED;
        toygerFrame.tgFrame = this.mCurrentToygerFrame.tgFrame;
        toygerFrame.tgFaceAttr = this.mCurrentToygerFrame.tgFaceAttr;
        toygerFrame.uploadContent = new UploadContent(bArr, bArr2, z);
        sendActionFrame(new ActionFrame(toygerFrame));
        return true;
    }

    public boolean onHighQualityFrame(Bitmap bitmap, ToygerFaceAttr toygerFaceAttr) {
        this.mWorkState = WorkState.FACE_CAPTURED;
        BioLog.i(ToygerService.TAG, "mWorkState = FACE_CAPTURED");
        BioLog.i("zolozTime", "scan face end!");
        if (bitmap != null) {
            Bitmap blur = ToygerBaseTask.blur(bitmap, 3.0f);
            if (this.mWorkspaceHandler != null) {
                this.mWorkspaceHandler.post(new j(this, blur));
            }
        }
        if (this.mAlertHelper != null) {
            this.mAlertHelper.setAuthInBackground(this.mIsAuthInBackground);
        }
        this.mToygerRecordService.write(ToygerRecordService.POSE_END);
        BioFragmentResponse bioFragmentResponse = new BioFragmentResponse();
        bioFragmentResponse.token = this.mToygerCallback.getAppDescription().getBistoken();
        BioLog.w((String) "ToygerWorkspace", (String) "mToygerCallback.sendProgressResponse()");
        this.mToygerCallback.sendProgressResponse(bioFragmentResponse);
        new Thread(new k(this, bitmap, toygerFaceAttr, bioFragmentResponse), "gen_avatar").start();
        this.mToygerRecordService.write(ToygerRecordService.LIVEBODY_START, new HashMap());
        TimeRecord.getInstance().setLivebodyStartTime(System.currentTimeMillis());
        this.mToygerRecordService.write(ToygerRecordService.UPLOAD_AVARRIABLE);
        ToygerFrame toygerFrame = new ToygerFrame();
        toygerFrame.frameType = FrameType.FRAME;
        toygerFrame.tgFaceAttr = toygerFaceAttr;
        sendActionFrame(new ActionFrame(toygerFrame));
        if (this.mIsAuthInBackground) {
            stopTimerTask();
        }
        return true;
    }

    public boolean onStateUpdated(ToygerFaceState toygerFaceState, ToygerFaceAttr toygerFaceAttr, Map<String, Object> map) {
        this.mToygerIspService.adjustIsp((TGFrame) map.get(ToygerFaceService.KEY_TOYGER_FRAME), (TGDepthFrame) map.get(ToygerFaceService.KEY_TOYGER_DEPTH_FRAME), toygerFaceState, toygerFaceAttr);
        ToygerFrame toygerFrame = new ToygerFrame();
        toygerFrame.frameType = FrameType.STATE;
        toygerFrame.tgFaceState = toygerFaceState;
        toygerFrame.tgFaceAttr = toygerFaceAttr;
        sendActionFrame(new ActionFrame(toygerFrame));
        return true;
    }

    public boolean onEvent(int i, Map<String, Object> map) {
        switch (i) {
            case -3:
                ToygerFrame toygerFrame = new ToygerFrame();
                toygerFrame.frameType = FrameType.ERROR;
                toygerFrame.error = ToygerError.LIVENESS_FAIL;
                sendActionFrame(new ActionFrame(toygerFrame));
                this.mWorkState = WorkState.FAILED;
                return true;
            case -2:
                ToygerFrame toygerFrame2 = new ToygerFrame();
                toygerFrame2.frameType = FrameType.ERROR;
                toygerFrame2.error = ToygerError.ALGORITHM_ERROR;
                sendActionFrame(new ActionFrame(toygerFrame2));
                this.mWorkState = WorkState.FAILED;
                return true;
            case -1:
                ToygerFrame toygerFrame3 = new ToygerFrame();
                toygerFrame3.frameType = FrameType.DARK;
                sendActionFrame(new ActionFrame(toygerFrame3));
                this.mWorkState = WorkState.FACE_CAPTURING_DARK;
                return true;
            default:
                return false;
        }
    }

    public PointF onAlignDepthPoint(PointF pointF) {
        PointF pointF2 = new PointF(pointF.x, pointF.y);
        if (this.mCameraInterface != null) {
            int colorWidth = this.mCameraInterface.getColorWidth();
            int colorHeight = this.mCameraInterface.getColorHeight();
            int depthWidth = this.mCameraInterface.getDepthWidth();
            int depthHeight = this.mCameraInterface.getDepthHeight();
            PointF pointF3 = new PointF();
            pointF3.x = ((float) colorWidth) * pointF.x;
            pointF3.y = pointF.y * ((float) colorHeight);
            PointF colorToDepth = this.mCameraInterface.colorToDepth(pointF3);
            pointF2.x = colorToDepth.x / ((float) depthWidth);
            pointF2.y = colorToDepth.y / ((float) depthHeight);
        }
        return pointF2;
    }

    private void checkEnv() {
        EnvErrorType check = new EnvCheck().check();
        if (check != EnvErrorType.ENV_ERROR_INVALID) {
            if (check == EnvErrorType.ENV_ERROR_LOW_OS) {
                this.mAlertHelper.alert(AlertType.ALERT_ANDROID_VERSION_LOW);
            } else if (check == EnvErrorType.ENV_ERROR_NO_FRONT_CAMERA) {
                this.mAlertHelper.alert(AlertType.ALERT_NO_FRONT_CAMERA);
            } else if (check == EnvErrorType.ENV_ERROR_NO_PERMISSION_OF_CAMERA) {
                this.mAlertHelper.alert(AlertType.ALERT_NO_PERMISSION_OF_CAMERA);
            } else if (check == EnvErrorType.ENV_ERROR_UNSUPPORTED_CPU) {
                this.mAlertHelper.alert(AlertType.ALERT_UNSUPPORTED_CPU);
            }
        }
    }

    public void responseWithCode(int i) {
        BioLog.w((String) "ToygerWorkspace", "responseWithCode(error=" + i + ")");
        if (!(this.mCurrentToygerFrame == null || this.mCurrentToygerFrame.tgFaceState == null)) {
            this.mToygerRecordService.write(ToygerRecordService.IMAGE_CAPTURE_END, ObjectUtil.objectToStringMap(this.mCurrentToygerFrame.tgFaceState));
        }
        this.mToygerCallback.finishActivity(false);
        this.mToygerCallback.sendResponse(i);
    }

    public boolean ontActivityEvent(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (!this.mIsBeUploadPage) {
            this.mAlertHelper.alert(AlertType.ALERT_BACK);
        }
        return true;
    }

    public void pauseToygerFaceService() {
        this.mWorkState = WorkState.PAUSE;
        showLastFrame();
    }

    private void showLastFrame() {
        if (this.lastFrame != null && this.tempCameraData != null) {
            Bitmap bytes2Bitmap = BitmapHelper.bytes2Bitmap(this.tempCameraData.a.array(), this.lastFrame.width, this.lastFrame.height, this.lastFrame.frameMode);
            Bitmap verticalRotateBitmap = RotateBitmapHelper.getVerticalRotateBitmap(bytes2Bitmap, (float) this.lastFrame.rotation);
            bytes2Bitmap.recycle();
            if (this.mWorkspaceHandler != null) {
                this.mWorkspaceHandler.post(new l(this, verticalRotateBitmap));
            }
        }
    }

    private void recordAlgorithmAction(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        this.mToygerRecordService.write(ToygerRecordService.ALGORITHM, hashMap);
    }

    private void recordAlgorithmAction(String str, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("action", str);
        hashMap.put("inttime", String.valueOf(j));
        this.mToygerRecordService.write(ToygerRecordService.ALGORITHM, hashMap);
    }

    public void alertRecord(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("reason", str);
        this.mToygerRecordService.write(ToygerRecordService.ALERT_APPEAR, hashMap);
    }

    public void clickBackRecord() {
        this.mToygerRecordService.write(ToygerRecordService.CLICK_BACK);
    }

    public void alertClickRecord(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("reason", str2);
        hashMap.put("choose", str);
        this.mToygerRecordService.write(ToygerRecordService.ALERT_CHOOSE, hashMap);
    }

    public void showFaceStatus(ToygerFrame toygerFrame) {
        String str = "";
        this.mToygerCirclePattern.getTopTip().setVisibility(0);
        if (!toygerFrame.tgFaceState.hasFace) {
            BioLog.i("ToygerAndroid !toygerFrame.tgFaceState.hasFace");
            str = this.poseUtil.getTopText_noface();
            this.mToygerCirclePattern.getTopTip().setText(str);
        } else {
            if (!this.isCheckedFace) {
                this.isCheckedFace = true;
                this.mToygerRecordService.write(ToygerRecordService.DETECT_COND_END, ToygerFrameUtil.getFaceParam(toygerFrame));
                this.mToygerRecordService.write(ToygerRecordService.POSE_START);
            }
            if (toygerFrame.tgFaceState.distance == -1) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.distance == -1");
                str = this.poseUtil.getTopText_max_rectwidth();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (toygerFrame.tgFaceState.distance == 1) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.distance == 1");
                str = this.poseUtil.getTopText_rectwidth();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (toygerFrame.tgFaceState.goodQuality) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.goodQuality == 1");
                str = this.poseUtil.getTopText_stay();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (toygerFrame.tgFaceState.brightness == -1) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.brightness == -1");
                str = this.poseUtil.getTopText_light();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (!toygerFrame.tgFaceState.faceInCenter) {
                BioLog.i("ToygerAndroid !toygerFrame.tgFaceState.faceInCenter");
                str = this.poseUtil.getTopText_integrity();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (toygerFrame.tgFaceState.goodPitch != 0 || toygerFrame.tgFaceState.goodYaw != 0) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.goodPitch != 0 || toygerFrame.tgFaceState.goodYaw != 0");
                str = this.poseUtil.getTopText_angle();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (toygerFrame.tgFaceState.isMoving) {
                BioLog.i("ToygerAndroid toygerFrame.tgFaceState.isMoving");
                str = this.poseUtil.getTopText_blur();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else if (!toygerFrame.tgFaceState.goodQuality) {
                BioLog.i("ToygerAndroid !toygerFrame.tgFaceState.goodQuality");
                str = this.poseUtil.getTopText_quality();
                this.mToygerCirclePattern.getTopTip().setText(str);
            } else {
                this.mToygerCirclePattern.getTopTip().setVisibility(4);
                this.mToygerCirclePattern.getTopTip().setText("");
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("prompt", str);
        HashMap<String, String> objectToStringMap = ObjectUtil.objectToStringMap(toygerFrame.tgFaceAttr);
        if (objectToStringMap != null && !objectToStringMap.isEmpty()) {
            hashMap.putAll(objectToStringMap);
        }
        this.mToygerRecordService.write(ToygerRecordService.PROMPT_COPY_POINT, hashMap);
    }

    public void startTimerTask() {
        BioLog.i("ToygerAndroid startTimerTask");
        this.isFirstTime = true;
        this.mSensorCollectors = new SensorCollectors(this.mBioServiceManager.getBioApplicationContext());
        this.mSensorCollectors.startListening();
        this.mSensorData = new Vector<>();
        int i = 20;
        FaceRemoteConfig remoteConfig = this.mToygerCallback.getRemoteConfig();
        if (!(remoteConfig == null || remoteConfig.getColl() == null)) {
            i = remoteConfig.getColl().getTime();
        }
        this.mDetectTimerTask = new DetectTimerTask(i * 1000);
        this.mDetectTimerTask.setTimerTaskListener(new m(this));
        this.mSensorTimerTask = new DetectTimerTask(i * 1000, 0, SENSOR_TIMER_INTERNAL);
        this.mSensorTimerTask.setTimerTaskListener(new e(this));
        this.mDetectTimerTask.start();
        this.mSensorTimerTask.start();
    }

    /* access modifiers changed from: private */
    public void recordSlice(ToygerFrame toygerFrame) {
        this.mToygerRecordService.write(ToygerRecordService.FACE_SLICE, ToygerFrameUtil.getFaceParam(toygerFrame));
    }

    public void stopTimerTask() {
        BioLog.i("ToygerAndroid stopTimerTask");
        try {
            if (this.mDetectTimerTask != null) {
                this.mDetectTimerTask.setTimerTaskListener(null);
                this.mDetectTimerTask.stop();
                this.mDetectTimerTask = null;
            }
            if (this.mSensorTimerTask != null) {
                this.mSensorTimerTask.setTimerTaskListener(null);
                this.mSensorTimerTask.stop();
                this.mSensorTimerTask = null;
            }
            if (this.mSensorData != null) {
                this.mSensorData.clear();
                this.mSensorData = null;
            }
            if (this.mSensorCollectors != null) {
                this.mSensorCollectors.destroy();
                this.mSensorCollectors = null;
            }
        } catch (Exception e) {
        }
    }

    private Map<String, Object> jsonObjectToMap(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        if (jSONObject != null && !jSONObject.isEmpty()) {
            for (String next : jSONObject.keySet()) {
                hashMap.put(next, jSONObject.get(next));
            }
        }
        return hashMap;
    }

    public ToygerFaceService getToygerFaceService() {
        return this.mToygerFaceService;
    }
}

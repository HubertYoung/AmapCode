package com.autonavi.gdtaojin.camera;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.widget.Toast;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CameraControllerManager extends AbstractCameraControllerManager {
    private static final int DEFAULT_VALUE = -2;
    private static final int MEDIA_TYPE_IMAGE = 1;
    public static final String MSG_XDirection = "dir_x";
    public static final String MSG_YDirection = "dir_y";
    public static final String MSG_ZDirection = "dir_z";
    public static final String MY_POILOCATION_ACR = "accuracy";
    public static final String MY_POILOCATION_LAT = "lat";
    public static final String MY_POILOCATION_LNG = "lng";
    public static final String RESULT_KEY_COMPRESSED_PICTURE_HEIGHT = "压缩后的图片高度";
    public static final String RESULT_KEY_COMPRESSED_PICTURE_WIDTH = "压缩后的图片宽度";
    public static final String RESULT_KEY_PICTURE_HEIGHT = "相机输出的图片高度";
    public static final String RESULT_KEY_PICTURE_WIDTH = "相机输出的图片宽度";
    public static final String TAG = "gxd_camera";
    private static String mFilePath;
    private CameraState cameraState = CameraState.IDLE;
    private CommandEvent commandEvent = CommandEvent.FIRST_IN_FOCUS;
    private boolean isContainLocationJar = false;
    /* access modifiers changed from: private */
    public boolean isSupport = false;
    private Camera mCamera;
    private boolean mCanTakePicture = true;
    private long mCaptureTime;
    private FocusController mFocusManager;
    private FocusUI mFocusUI;
    /* access modifiers changed from: private */
    public int mOrientation;
    private OrientationEventListener mOrientationListener;
    /* access modifiers changed from: private */
    public Parameters mParameters = null;
    private PhotoModule mPhotoModule = new PhotoModule();
    private File mPictureFile;
    private boolean mPreviewing = false;
    private String mReason;
    private boolean mSupportAutoFocus;
    private boolean mSupportContinuousFocus;
    private Toast mToast;
    private int mZoomProgress;
    /* access modifiers changed from: private */
    public byte[] picData;
    private boolean picTaked = false;
    PictureCallback picturecallbck = new PictureCallback() {
        public void onPictureTaken(byte[] bArr, Camera camera) {
            CameraControllerManager.this.picData = bArr;
            CameraControllerManager.this.handlePicData();
        }
    };
    private boolean start_preview_failed = false;

    public class CameraSizeComparator implements Comparator<Size> {
        public CameraSizeComparator() {
        }

        public int compare(Size size, Size size2) {
            if (size.width == size2.width) {
                return 0;
            }
            return size.width > size2.width ? 1 : -1;
        }
    }

    enum CameraState {
        IDLE,
        AUTO_FOCUSING,
        TAKING_PICTURE
    }

    enum CommandEvent {
        IDLE,
        FIRST_IN_FOCUS,
        TOUCH_SCREEN,
        CLICK_TAKE_PIC,
        SENSOR_AUTO_FOCUS
    }

    public CameraControllerManager(Activity activity, Handler handler, SurfaceHolder surfaceHolder, Resources resources) {
        super(activity, handler, surfaceHolder);
        this.mPhotoModule.init();
        this.mFocusUI = new FocusUI(activity, resources);
        FocusController focusController = new FocusController(activity, this.mPhotoModule, this.mFocusUI, this, handler);
        this.mFocusManager = focusController;
    }

    public Camera openCameraAndSetParameters() {
        if (!checkCameraHardware(this.mContext)) {
            return null;
        }
        if (this.mCamera == null) {
            try {
                this.mCamera = this.mPhotoModule.openCamera();
                if (this.mCamera != null) {
                    try {
                        setCommenParametersFirstTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return this.mCamera;
    }

    public void restartPreview(Camera camera, SurfaceHolder surfaceHolder) {
        if (camera != null) {
            setStartPreview(camera, surfaceHolder);
        }
    }

    private boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public void stopAndReleaseCamera() {
        try {
            if (this.mCamera != null) {
                this.mCamera.setPreviewCallback(null);
                if (this.mPreviewing) {
                    stopPreview();
                }
                if (this.mPhotoModule != null) {
                    this.mPhotoModule.release();
                }
                this.mCamera = null;
            }
        } catch (Exception e) {
            if (this.mPhotoModule != null) {
                this.mPhotoModule.releaseCameraHolder();
            }
            e.printStackTrace();
        }
    }

    public void setStartPreview(Camera camera, SurfaceHolder surfaceHolder) {
        if (camera != null && surfaceHolder != null) {
            this.mPhotoModule.setPreviewDisplay(surfaceHolder);
            int previewDegree = getPreviewDegree(this.mContext);
            if (ApiChecker.AT_LEAST_11) {
                this.mPhotoModule.setDisplayOrientation(previewDegree);
            } else {
                setMyDisplayOrientation(camera, previewDegree);
            }
            startPreview();
        }
    }

    public void startPreview() {
        if (this.mPhotoModule != null) {
            try {
                if (this.mPreviewing) {
                    stopPreview();
                }
                this.mPhotoModule.onStartPreview();
                this.mPreviewing = true;
            } catch (Exception e) {
                e.printStackTrace();
                this.start_preview_failed = true;
            }
        }
    }

    public boolean isStart_preview_failed() {
        return this.start_preview_failed;
    }

    public void stopPreview() {
        if (this.mPhotoModule != null) {
            this.mPhotoModule.onStopPreview();
            this.mPreviewing = false;
        }
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) {
        this.mPhotoModule.setPreviewDisplay(surfaceHolder);
    }

    public int getPreviewDegree(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
                return 90;
            case 2:
                return 270;
            case 3:
                return 180;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: protected */
    public void setMyDisplayOrientation(Camera camera, int i) {
        try {
            Method method = camera.getClass().getMethod("setDisplayOrientation", new Class[]{Integer.TYPE});
            if (method != null) {
                method.invoke(camera, new Object[]{Integer.valueOf(i)});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSupportContinuousFocus() {
        if (!ApiChecker.AT_LEAST_14 || this.mParameters == null) {
            return false;
        }
        boolean contains = this.mParameters.getSupportedFocusModes().contains("continuous-picture");
        if (!ApiChecker.HAS_AUTO_FOCUS_MOVE_CALLBACK || !contains) {
            return false;
        }
        return true;
    }

    public boolean isSupportAutoFocus() {
        if (this.mParameters != null) {
            return this.mParameters.getSupportedFocusModes().contains("auto");
        }
        return false;
    }

    public boolean getSupportedFocusMode() {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (CameraControllerManager.this.mParameters != null) {
                    CameraControllerManager.this.isSupport = CameraControllerManager.this.mParameters.getSupportedFocusModes().contains("auto");
                }
            }
        });
        return this.isSupport;
    }

    public int getCurrPreviewSize(int i, int i2, int i3, int i4) {
        if (i > i2) {
            int i5 = i2;
            i2 = i;
            i = i5;
        }
        if (i3 <= i4) {
            int i6 = i4;
            i4 = i3;
            i3 = i6;
        }
        int i7 = i * i3;
        if (i7 < i4 * i2) {
            return i2 - (i7 / i4);
        }
        return 0;
    }

    @TargetApi(9)
    public void setCommenParametersFirstTime() {
        this.mParameters = getCurrentParameters();
        if (this.mParameters != null) {
            this.mParameters.setPictureFormat(256);
            this.mParameters.setJpegQuality(100);
            List<String> supportedFocusModes = this.mParameters.getSupportedFocusModes();
            if (supportedFocusModes != null && supportedFocusModes.size() > 0) {
                if (isSupportContinuousFocus()) {
                    this.mSupportContinuousFocus = true;
                }
                if (isSupportAutoFocus()) {
                    this.mSupportAutoFocus = true;
                }
            }
            String str = Build.MODEL;
            int i = 0;
            int i2 = !TextUtils.isEmpty(str) ? (str.contains("PLK-UL00") || str.contains("HUAWEI GRA-UL00") || str.contains("Redmi Note 2") || str.contains("Redmi Note 3") || str.contains("MI 5")) ? 1920 : 1921 : 0;
            List<Size> supportedPreviewSizes = this.mParameters.getSupportedPreviewSizes();
            Collections.sort(supportedPreviewSizes, new CameraSizeComparator());
            List<Size> supportedPictureSizes = this.mParameters.getSupportedPictureSizes();
            Collections.sort(supportedPictureSizes, new CameraSizeComparator());
            Iterator<Size> it = supportedPreviewSizes.iterator();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                int i6 = 3000;
                if (!it.hasNext()) {
                    break;
                }
                Size next = it.next();
                for (Size next2 : supportedPictureSizes) {
                    if (isDevelopTakingPicture()) {
                        if (next2.width >= i4 && next2.width < i6 && next2.height < i6 && next.width * next2.height == next2.width * next.height) {
                            i4 = next2.width;
                            i5 = next2.height;
                            i = next.width;
                            i3 = next.height;
                        }
                    } else if (next2.width >= i4 && next2.width < i2 && next2.height < i2 && next.width * next2.height == next2.width * next.height) {
                        i4 = next2.width;
                        i5 = next2.height;
                        i = next.width;
                        i3 = next.height;
                    }
                    i6 = 3000;
                }
            }
            if (i == 0 || i3 == 0) {
                for (Size next3 : supportedPreviewSizes) {
                    if (next3.width >= i && next3.width < 1281 && next3.height < 1281) {
                        int i7 = next3.width;
                        i3 = next3.height;
                        i = i7;
                    }
                }
            }
            if (i4 == 0 || i5 == 0) {
                for (Size next4 : supportedPictureSizes) {
                    if (isDevelopTakingPicture()) {
                        if (next4.width >= i4) {
                            if (next4.width < 3000 && next4.height < 3000) {
                                i4 = next4.width;
                                i5 = next4.height;
                            }
                        }
                    } else if (next4.width >= i4) {
                        if (next4.width < 1921 && next4.height < 1921) {
                            i4 = next4.width;
                            i5 = next4.height;
                        }
                    }
                }
            }
            CameraConst.picHeightPixels = i5;
            CameraConst.picWidthPixels = i4;
            try {
                this.mParameters.setPreviewSize(i, i3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.mParameters.setPictureSize(i4, i5);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            trySetParameters(this.mParameters);
        }
    }

    private boolean isDevelopTakingPicture() {
        return CameraConst.IS_DEVELOP_PICTURESIZE;
    }

    public void trySetParameters(Parameters parameters) {
        if (!this.picTaked) {
            try {
                this.mPhotoModule.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void capture() {
        if (!this.mCanTakePicture) {
            showToast(this.mReason);
            return;
        }
        hideFocusView();
        if (!this.picTaked && this.commandEvent != CommandEvent.CLICK_TAKE_PIC && this.cameraState != CameraState.TAKING_PICTURE) {
            this.commandEvent = CommandEvent.CLICK_TAKE_PIC;
            if (this.cameraState == CameraState.IDLE) {
                if (!isSupportAutoFocus() || System.currentTimeMillis() - this.mFocusManager.getFocusEndTime() <= 1000) {
                    this.cameraState = CameraState.TAKING_PICTURE;
                    takePicture();
                    return;
                }
                executeAutoFocus();
            } else if (this.cameraState != CameraState.AUTO_FOCUSING) {
            }
        }
    }

    public void returnResult(int i, int i2) {
        Intent intent = new Intent();
        if (this.mPictureFile != null) {
            intent.setData(Uri.fromFile(this.mPictureFile));
        }
        intent.putExtra(MSG_XDirection, this.mXCaptureDirection);
        intent.putExtra(MSG_YDirection, this.mYCaptureDirection);
        intent.putExtra(MSG_ZDirection, this.mZCaptureDirection);
        Size pictureSize = getPictureSize();
        if (pictureSize != null) {
            intent.putExtra(RESULT_KEY_PICTURE_WIDTH, pictureSize.width);
            intent.putExtra(RESULT_KEY_PICTURE_HEIGHT, pictureSize.height);
        }
        if (i > 0 && i2 > 0) {
            intent.putExtra(RESULT_KEY_COMPRESSED_PICTURE_WIDTH, i);
            intent.putExtra(RESULT_KEY_COMPRESSED_PICTURE_HEIGHT, i2);
        }
        this.mContext.setResult(-1, intent);
        this.mContext.finish();
    }

    public void deletePicFile() {
        if (this.mPictureFile != null) {
            this.mPictureFile.delete();
        }
    }

    public String getPicFilePath() {
        if (mFilePath != null) {
            return mFilePath;
        }
        return null;
    }

    public File getPicFile() {
        if (this.mPictureFile != null) {
            return this.mPictureFile;
        }
        return null;
    }

    public boolean isPreviewing() {
        return this.mPreviewing;
    }

    public Parameters getCurrentParameters() {
        try {
            if (this.mParameters == null) {
                return this.mPhotoModule.getParameters();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.mParameters;
    }

    @SuppressLint({"SimpleDateFormat"})
    private File getOutputMediaFile(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSDPath());
        sb.append(File.separator);
        sb.append(CameraConst.FOLDER_NAME);
        sb.append(File.separator);
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(sb2);
        if (sb2.startsWith(DiskFormatter.GB, 1) || i != 1) {
            return null;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(file2.getPath());
        sb3.append("/");
        sb3.append(String.valueOf(System.currentTimeMillis()));
        sb3.append(".jpg");
        File file3 = new File(sb3.toString());
        mFilePath = file3.toString();
        return file3;
    }

    public String getSDPath() {
        return Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory().toString() : "";
    }

    /* access modifiers changed from: private */
    public void handlePicData() {
        this.mPictureFile = getOutputMediaFile(1);
        if (this.mPictureFile == null) {
            showToast("请检查您的SD卡");
            if (this.mCamera != null) {
                this.mHandler.sendEmptyMessage(2);
                this.picTaked = false;
            }
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.mPictureFile);
            if (this.picData != null) {
                fileOutputStream.write(this.picData);
                fileOutputStream.close();
            } else {
                showToast("拍照失败，请重试");
                this.mHandler.sendEmptyMessage(2);
            }
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1));
        } catch (Exception unused) {
            this.mHandler.post(new Runnable() {
                public void run() {
                    CameraControllerManager.this.showToast("手机存储空间不足");
                }
            });
            if (this.mCamera != null) {
                this.mHandler.sendEmptyMessage(2);
            }
            setCommandEvent(CommandEvent.IDLE);
            setCameraState(CameraState.IDLE);
            this.picTaked = false;
        }
    }

    public void takePicture() {
        if (!this.picTaked) {
            this.mParameters = getCurrentParameters();
            if (this.mParameters != null) {
                this.mParameters.setRotation(this.mOrientation);
                trySetParameters(this.mParameters);
                setZoom();
                getOriontationParams();
                this.picTaked = true;
                this.mPhotoModule.onCapture(null, null, this.picturecallbck);
            }
        }
    }

    public boolean checkDerecvtionTime(long j) {
        return j - this.mDerectionTime < 500;
    }

    private void setZoom() {
        if (!this.picTaked) {
            this.mParameters = getCurrentParameters();
            if (this.mParameters != null && this.mParameters.isZoomSupported()) {
                if (this.mZoomProgress < 0) {
                    this.mZoomProgress = 0;
                }
                this.mParameters.setZoom(this.mZoomProgress);
                trySetParameters(this.mParameters);
            }
        }
    }

    public int getMaxCameraZoom() {
        int i = 0;
        if (this.picTaked) {
            return 0;
        }
        this.mParameters = getCurrentParameters();
        if (this.mParameters == null) {
            return 0;
        }
        if (this.mParameters.isZoomSupported()) {
            i = this.mParameters.getMaxZoom();
        }
        return i;
    }

    private void getOriontationParams() {
        this.mCaptureTime = System.currentTimeMillis();
        if (this.mCaptureTime - this.mDerectionTime < 500) {
            this.mXCaptureDirection = this.mXDirection;
            this.mYCaptureDirection = this.mYDirection;
            this.mZCaptureDirection = this.mZDirection;
            return;
        }
        this.mXCaptureDirection = -1.0f;
        this.mYCaptureDirection = -1.0f;
        this.mZCaptureDirection = -1.0f;
    }

    public void startOrientationEventListener() {
        this.mOrientationListener = new OrientationEventListener(this.mContext) {
            public void onOrientationChanged(int i) {
                if ((i > 325 && i < 360) || (i > 0 && i < 45)) {
                    CameraControllerManager.this.mOrientation = 90;
                } else if (i > 45 && i < 135) {
                    CameraControllerManager.this.mOrientation = 180;
                } else if (i <= 135 || i >= 225) {
                    if (i > 225 && i < 315) {
                        CameraControllerManager.this.mOrientation = 0;
                    }
                } else {
                    CameraControllerManager.this.mOrientation = 270;
                }
            }
        };
    }

    public void enableOrientationEventListener() {
        if (this.mOrientationListener != null) {
            this.mOrientationListener.enable();
        }
    }

    public void disableOrientationEventListener() {
        if (this.mOrientationListener != null) {
            this.mOrientationListener.disable();
        }
    }

    public boolean isTouchTakingPic() {
        return this.mContext.getSharedPreferences("SharedPreferences", 0).getBoolean(CameraSettingsMenu.PREF_KEY_TOUCH_TAKE, false);
    }

    public boolean isVolumeKeyTakePicture() {
        return this.mContext.getSharedPreferences("SharedPreferences", 0).getBoolean(CameraSettingsMenu.PREF_KEY_IS_VOLUMEKEY_TAKEPIC, false);
    }

    public CommandEvent getCommandEvent() {
        return this.commandEvent;
    }

    public void setCommandEvent(CommandEvent commandEvent2) {
        this.commandEvent = commandEvent2;
    }

    public CameraState getCameraState() {
        return this.cameraState;
    }

    public void setCameraState(CameraState cameraState2) {
        this.cameraState = cameraState2;
    }

    public void setPicTaked(boolean z) {
        this.picTaked = z;
    }

    public boolean getPicTaked() {
        return this.picTaked;
    }

    public void setDisplayOrientation(int i) {
        if (this.mPhotoModule != null) {
            this.mPhotoModule.setDisplayOrientation(i);
        }
    }

    public void setCameraZoom(int i) {
        this.mZoomProgress = i;
        setZoom();
    }

    public void executeAutoFocus() {
        try {
            executeAutoFocusStrategy(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAutoFocusStrategy() {
        try {
            if (this.mFocusManager != null) {
                this.mFocusManager.cancelFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMovingAutoFocusStrategy() {
        if (!(this.mCamera == null || this.mFocusManager == null)) {
            this.mFocusManager.getFocusStrategy(1);
            this.mFocusManager.operateFocus();
        }
    }

    public void executeAutoFocusStrategy(MotionEvent motionEvent) {
        try {
            if (!(this.mCamera == null || this.mFocusManager == null)) {
                this.mFocusManager.getFocusStrategy(3);
                this.mFocusManager.executeFocus(motionEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreContinuousFocus() {
        if (getCameraState() == CameraState.IDLE) {
            this.mHandler.sendEmptyMessageDelayed(3, 2000);
        }
    }

    public void hideFocusView() {
        if (this.mFocusUI != null) {
            this.mFocusUI.clearFocus();
        }
    }

    public void startAndShowFocusView(int i, int i2) {
        if (this.mFocusUI != null) {
            this.mFocusUI.onFocusStarted(i, i2);
        }
    }

    public void setCameraFlash(boolean z) {
        if (!this.picTaked) {
            this.mParameters = getCurrentParameters();
            if (this.mParameters != null && this.mParameters.getSupportedFlashModes() != null) {
                if (z) {
                    this.mParameters.setFlashMode(CameraParams.FLASH_MODE_ON);
                } else {
                    this.mParameters.setFlashMode(CameraParams.FLASH_MODE_OFF);
                }
                trySetParameters(this.mParameters);
            }
        }
    }

    public boolean getIsSupportContinuousFocus() {
        return this.mSupportContinuousFocus;
    }

    public void showToast(String str) {
        if (this.mToast == null) {
            this.mToast = Toast.makeText(this.mContext, str, 0);
        } else {
            this.mToast.setText(str);
            this.mToast.setDuration(0);
        }
        this.mToast.show();
    }

    public boolean setPicOritation(String str, int i) {
        try {
            ExifInterface exifInterface = new ExifInterface(str);
            exifInterface.setAttribute("Orientation", String.valueOf(i));
            exifInterface.saveAttributes();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setIsContainLocationJar(boolean z) {
        this.isContainLocationJar = z;
    }

    public int getOritation() {
        return this.mOrientation;
    }

    private Size getPictureSize() {
        PhotoModule photoModule = this.mPhotoModule;
        if (photoModule == null) {
            return null;
        }
        Parameters parameters = photoModule.getParameters();
        if (parameters == null) {
            return null;
        }
        return parameters.getPictureSize();
    }
}

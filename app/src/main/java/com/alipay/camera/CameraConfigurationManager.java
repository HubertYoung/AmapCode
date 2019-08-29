package com.alipay.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.camera.util.CameraConfigurationUtils;
import com.alipay.camera.util.FocusWhiteList;
import com.alipay.mobile.bqcscanservice.BQCCameraParam.ConfigParam;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.bqcscanservice.behavior.BehaviorBury;
import com.alipay.mobile.bqcscanservice.monitor.ScanExceptionHandler.TorchException;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.zoloz.toyger.bean.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public final class CameraConfigurationManager {
    private static List<Area> i = new ArrayList();
    private final Context a;
    private Point b;
    private Point c;
    private Point d;
    private int e = 90;
    private String f;
    private int g;
    private Map<String, Object> h;

    public CameraConfigurationManager(Context context, Point screenResolution, Point previewResolution, Point pictureResolution) {
        this.a = context;
        this.b = screenResolution;
        this.c = previewResolution;
        this.d = pictureResolution;
    }

    public final int getCameraDisplayOrientation() {
        return this.e;
    }

    public final Parameters initFromCameraParameters(Camera camera) {
        return initFromCameraParameters(camera, null);
    }

    public final Parameters initFromCameraParameters(Camera camera, Point previewViewSize) {
        Parameters parameters = camera.getParameters();
        Logger.d("CameraConfiguration", "The first time to get parameters");
        Display display = ((WindowManager) this.a.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        Point theScreenResolution = new Point();
        display.getSize(theScreenResolution);
        this.b = theScreenResolution;
        Logger.i("CameraConfiguration", "Screen resolution: " + this.b);
        if (previewViewSize == null || previewViewSize.x < 480 || previewViewSize.y < 800) {
            previewViewSize = this.b;
        }
        this.c = CameraConfigurationUtils.findBestPreviewSizeValue(parameters, previewViewSize);
        this.d = CameraConfigurationUtils.findBestPictureSizeValue(parameters, this.e);
        String model = Build.MODEL;
        if ((model.contains("HTC") && model.contains("One")) || model.contains("GT-N7100") || model.contains("GT-I9300")) {
            this.c = new Point(1280, 720);
        } else if (model.equals("u8800")) {
            this.c = new Point(720, Config.HQ_IMAGE_WIDTH);
        } else if (model.equals("MI PAD")) {
            this.c = new Point(2048, 1536);
        }
        Logger.i("CameraConfiguration", "Camera resolution: " + this.c);
        CameraConfigurationUtils.setBestPreviewFPS(parameters, 20, 30);
        return parameters;
    }

    public final void setDesiredCameraParameters(Camera camera, Parameters parameters, int cameraId) {
        if (parameters == null) {
            parameters = camera.getParameters();
        }
        d(parameters);
        boolean autoFocus = true;
        if (FocusWhiteList.inWhiteList(Build.BRAND, Build.MODEL)) {
            autoFocus = false;
        }
        CameraConfigurationUtils.setFocus(parameters, autoFocus);
        this.f = parameters.getFocusMode();
        try {
            a(cameraId);
            camera.setDisplayOrientation(this.e);
        } catch (Exception e2) {
            parameters.setRotation(90);
            Logger.w("CameraConfiguration", "method error" + e2.getLocalizedMessage());
        } catch (NoSuchMethodError e3) {
            parameters.setRotation(90);
            Logger.w("CameraConfiguration", "method error" + e3.getLocalizedMessage());
        }
        this.g = c(parameters);
        if (this.g >= 0) {
            parameters.setPreviewFormat(this.g);
        }
        Logger.i("CameraConfiguration", "SQY: before set Camera parameters , now ScreenSize is " + this.b + ", previewSize to set  is " + this.c);
        parameters.setPreviewSize(this.c.x, this.c.y);
        BehaviorBury.recordCameraPreviewSize(this.c.x, this.c.y);
        parameters.setPictureSize(this.d.x, this.d.y);
        parameters.setPictureFormat(b(parameters));
        if (parameters.isZoomSupported()) {
            parameters.setZoom((int) (0.1d * ((double) parameters.getMaxZoom())));
        }
        a(parameters);
        Logger.i("CameraConfiguration", "Final camera parameters: " + parameters.flatten());
        camera.setParameters(parameters);
        Size afterSize = camera.getParameters().getPreviewSize();
        if (afterSize == null) {
            return;
        }
        if (this.c.x != afterSize.width || this.c.y != afterSize.height) {
            Logger.w("CameraConfiguration", "Camera said it supported preview size " + this.c.x + 'x' + this.c.y + ", but after setting it, preview size is " + afterSize.width + 'x' + afterSize.height);
            this.c.x = afterSize.width;
            this.c.y = afterSize.height;
        }
    }

    private void a(Parameters parameters) {
        int left;
        int right;
        int top;
        int bottom;
        if (this.h != null && parameters != null) {
            if (i.isEmpty()) {
                if (this.c.x > this.c.y) {
                    top = -1000;
                    bottom = 1000;
                    left = (-(this.c.y * 1000)) / this.c.x;
                    right = (this.c.y * 1000) / this.c.x;
                } else {
                    left = -1000;
                    right = 1000;
                    top = (-(this.c.x * 1000)) / this.c.y;
                    bottom = (-(this.c.x * 1000)) / this.c.y;
                }
                if (left < -1000) {
                    left = -1000;
                }
                if (top < -1000) {
                    top = -1000;
                }
                if (right > 1000) {
                    right = 1000;
                }
                if (bottom > 1000) {
                    bottom = 1000;
                }
                i.add(new Area(new Rect(left, top, right, bottom), 1000));
            }
            if (parameters.getMaxNumFocusAreas() > 0 && TextUtils.equals((String) this.h.get(ConfigParam.KEY_ENABLE_FOCUS_AREA), "YES") && !i.isEmpty()) {
                parameters.setFocusAreas(i);
            }
        }
    }

    private static int b(Parameters parameters) {
        List list = parameters.getSupportedPictureFormats();
        if (list.contains(Integer.valueOf(256))) {
            return 256;
        }
        if (list.contains(Integer.valueOf(4))) {
            return 4;
        }
        if (list.contains(Integer.valueOf(17))) {
            return 17;
        }
        return 0;
    }

    private static int c(Parameters parameters) {
        List list = parameters.getSupportedPreviewFormats();
        if (list.contains(Integer.valueOf(17))) {
            return 17;
        }
        if (list.contains(Integer.valueOf(IjkMediaPlayer.SDL_FCC_YV12))) {
            return IjkMediaPlayer.SDL_FCC_YV12;
        }
        return -1;
    }

    public final Point getPreviewResolution() {
        return this.c;
    }

    public final Point getScreenResolution() {
        return this.b;
    }

    public final boolean getTorchState(Camera camera) {
        if (camera == null) {
            return false;
        }
        Parameters parameters = camera.getParameters();
        if (parameters == null) {
            return false;
        }
        String flashMode = parameters.getFlashMode();
        if (flashMode == null) {
            return false;
        }
        if (CameraParams.FLASH_MODE_ON.equals(flashMode) || "torch".equals(flashMode)) {
            return true;
        }
        return false;
    }

    public final void setTorch(Camera camera, boolean newSetting) {
        Parameters parameters = camera.getParameters();
        a(parameters, newSetting);
        try {
            camera.setParameters(parameters);
        } catch (Exception e2) {
            Log.d("CameraConfiguration", "Toggle Torch Error");
            throw new TorchException(newSetting, 4001, e2.getMessage());
        }
    }

    private static void d(Parameters parameters) {
        a(parameters, false);
    }

    private static void a(Parameters parameters, boolean newSetting) {
        CameraConfigurationUtils.setTorch(parameters, newSetting);
    }

    public final void setCompatibleRotation(String compatibleRotation) {
    }

    private void a(int cameraId) {
        String devName = Build.BRAND + MergeUtil.SEPARATOR_KV + Build.MODEL + MergeUtil.SEPARATOR_KV + Build.DISPLAY;
        Logger.d("CameraConfiguration", "The devName is " + devName);
        this.e = b(cameraId);
        int tmpResult = this.e;
        if (this.e == 90 || this.e == 270) {
            BehaviorBury.recordPreviewOrientationNewCal(devName, tmpResult);
            return;
        }
        BehaviorBury.recordPreviewOrientationOld(devName, tmpResult);
        if (Build.MODEL != null) {
            Logger.d("CameraConfiguration", "The device is " + Build.BRAND + ", " + Build.MODEL);
            if (!Build.MODEL.contains("M9") || !Build.BRAND.contains("Meizu")) {
                this.e = 90;
            } else {
                this.e = 180;
            }
        }
    }

    private static int b(int cameraId) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        Logger.d("CameraConfiguration", "getCameraPreviewOrientation(orientation : " + info.orientation + ")");
        if (info.facing == 1) {
            return (360 - (info.orientation % 360)) % 360;
        }
        return (info.orientation + 360) % 360;
    }

    public final String getFocusMode() {
        Logger.d("CameraConfiguration", "The focus mode is " + this.f);
        return this.f;
    }

    public final Point getPreviewSize() {
        return this.c;
    }

    public final int getPreviewFmt() {
        return this.g;
    }

    public final Point getPictureSize() {
        return this.d;
    }

    public final void setCameraAbParameters(Map<String, Object> parameters) {
        this.h = parameters;
    }
}

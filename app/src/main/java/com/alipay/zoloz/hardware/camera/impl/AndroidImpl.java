package com.alipay.zoloz.hardware.camera.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.DisplayUtil;
import com.alipay.mobile.security.faceauth.circle.protocol.DeviceSetting;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.alipay.zoloz.hardware.camera.a.a;
import com.alipay.zoloz.hardware.camera.b;
import com.alipay.zoloz.hardware.camera.c;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"InlinedApi"})
public class AndroidImpl implements c {
    private static AndroidImpl a;
    private Context b;
    private Camera c;
    private Parameters d;
    /* access modifiers changed from: private */
    public b e;
    private int f = 90;
    private int g;
    private int h;
    private DeviceSetting i = new DeviceSetting();
    private final Object j = new Object();
    /* access modifiers changed from: private */
    public int k = 0;
    /* access modifiers changed from: private */
    public int l = 0;
    /* access modifiers changed from: private */
    public int m = 0;
    /* access modifiers changed from: private */
    public int n = 0;
    private boolean o = false;
    private boolean p = false;
    private boolean q = false;
    private boolean r = false;

    private AndroidImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context can't be null");
        }
        this.b = context;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = false;
    }

    public static synchronized AndroidImpl getInstance(Context context) {
        AndroidImpl androidImpl;
        synchronized (AndroidImpl.class) {
            if (a == null) {
                a = new AndroidImpl(context);
            }
            a.b = context;
            androidImpl = a;
        }
        return androidImpl;
    }

    public void initCamera(DeviceSetting deviceSetting) {
        if (!this.o) {
            if (deviceSetting != null) {
                this.i = deviceSetting;
            }
            this.o = true;
        }
    }

    public void releaseCamera() {
        if (this.o) {
            this.o = false;
            this.b = null;
        }
    }

    public void openCamera(DeviceSetting deviceSetting) {
        if (!this.p) {
            if (deviceSetting != null) {
                this.i = deviceSetting;
            }
            this.p = true;
        }
    }

    public void closeCamera() {
        if (this.p) {
            this.p = false;
        }
    }

    public void startCamera() {
        if (!this.q) {
            this.h = Camera.getNumberOfCameras();
            int cameraID = this.i.isCameraAuto() ? this.h <= 1 ? 0 : 1 : this.i.getCameraID();
            if (a(cameraID)) {
                this.q = true;
            }
        }
    }

    public void stopCamera() {
        if (this.q) {
            this.e = null;
            stopPreview();
            if (this.c != null) {
                synchronized (this.j) {
                    try {
                        this.c.release();
                        this.c = null;
                        this.q = false;
                    } catch (Exception e2) {
                        BioLog.e(e2.toString());
                    }
                }
                return;
            }
            return;
        }
        return;
    }

    public void startPreview(SurfaceHolder surfaceHolder, float f2, int i2, int i3) {
        BioLog.d("startPreview...");
        if (!this.r && this.c != null) {
            try {
                this.c.setPreviewDisplay(surfaceHolder);
                this.c.startPreview();
                this.r = true;
            } catch (Exception e2) {
                BioLog.e(e2.toString());
                if (this.e != null) {
                    this.e.onError(-1);
                }
            }
        }
    }

    public void stopPreview() {
        if (this.r && this.c != null) {
            synchronized (this.j) {
                try {
                    this.c.setOneShotPreviewCallback(null);
                    this.c.setPreviewCallback(null);
                    this.c.stopPreview();
                } catch (Exception e2) {
                    BioLog.e(e2.toString());
                }
            }
            this.r = false;
        }
    }

    public void setCallback(b bVar) {
        this.e = bVar;
    }

    public int getCameraViewRotation() {
        return this.f;
    }

    public int getColorWidth() {
        return this.k;
    }

    public int getColorHeight() {
        return this.l;
    }

    public int getDepthWidth() {
        return 0;
    }

    public int getDepthHeight() {
        return 0;
    }

    public int getPreviewWidth() {
        return this.m;
    }

    public int getPreviewHeight() {
        return this.n;
    }

    public PointF colorToDepth(PointF pointF) {
        return null;
    }

    public PointF depthToColor(PointF pointF) {
        return null;
    }

    public void setFrameAvailableListener(OnFrameAvailableListener onFrameAvailableListener) {
    }

    private boolean a(int i2) {
        int i3;
        Size size;
        int displayAngle;
        int i4;
        BioLog.i("realStartCamera");
        try {
            this.c = Camera.open(i2);
            if (this.c == null) {
                if (this.e != null) {
                    this.e.onError(-1);
                }
                return false;
            }
            this.g = i2;
            if (this.c != null) {
                this.d = this.c.getParameters();
                if (this.d != null) {
                    a a2 = a.a();
                    List<Size> supportedPreviewSizes = this.d.getSupportedPreviewSizes();
                    float screenRate = DisplayUtil.getScreenRate(this.b);
                    if (supportedPreviewSizes == null) {
                        size = null;
                    } else {
                        Collections.sort(supportedPreviewSizes, a2.a);
                        Iterator<Size> it = supportedPreviewSizes.iterator();
                        int i5 = 0;
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Size next = it.next();
                            if (next.width >= 600 && a.a(next, screenRate)) {
                                BioLog.i("PreviewSize:w = " + next.width + "h = " + next.height);
                                break;
                            }
                            i5++;
                        }
                        if (i5 == supportedPreviewSizes.size()) {
                            i3 = supportedPreviewSizes.size() - 1;
                        } else {
                            i3 = i5;
                        }
                        size = supportedPreviewSizes.get(i3);
                    }
                    if (size != null) {
                        this.m = size.width;
                        this.n = size.height;
                        this.k = this.m;
                        this.l = this.n;
                        this.d.setPreviewSize(this.m, this.n);
                    }
                    if (this.i != null) {
                        DeviceSetting deviceSetting = this.i;
                        if (deviceSetting == null) {
                            throw new IllegalArgumentException("deviceSetting can't be null");
                        }
                        if (deviceSetting.isDisplayAuto()) {
                            int i6 = this.g;
                            CameraInfo cameraInfo = new CameraInfo();
                            Camera.getCameraInfo(i6, cameraInfo);
                            switch (((WindowManager) this.b.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getRotation()) {
                                case 0:
                                    i4 = 0;
                                    break;
                                case 1:
                                    i4 = 90;
                                    break;
                                case 2:
                                    i4 = 180;
                                    break;
                                case 3:
                                    i4 = 270;
                                    break;
                                default:
                                    i4 = 0;
                                    break;
                            }
                            if (cameraInfo.facing == 1) {
                                if (com.alipay.android.phone.a.a.a.b.booleanValue()) {
                                    cameraInfo.orientation = 270;
                                }
                                displayAngle = (360 - ((i4 + cameraInfo.orientation) % 360)) % 360;
                            } else {
                                if (com.alipay.android.phone.a.a.a.b.booleanValue()) {
                                    cameraInfo.orientation = 90;
                                }
                                displayAngle = ((cameraInfo.orientation - i4) + 360) % 360;
                            }
                        } else {
                            displayAngle = deviceSetting.getDisplayAngle();
                        }
                        this.f = displayAngle;
                        this.c.setDisplayOrientation(this.f);
                    }
                    List<String> supportedFocusModes = this.d.getSupportedFocusModes();
                    if (supportedFocusModes != null) {
                        if (supportedFocusModes.contains("continuous-video")) {
                            this.d.setFocusMode("continuous-video");
                        } else {
                            supportedFocusModes.contains("auto");
                        }
                    }
                }
                this.c.setParameters(this.d);
                if (this.e != null) {
                    this.c.setPreviewCallback(new PreviewCallback() {
                        public void onPreviewFrame(byte[] bArr, Camera camera) {
                            AndroidImpl.this.e.onPreviewFrame(new com.alipay.zoloz.hardware.camera.a(ByteBuffer.wrap(bArr), AndroidImpl.this.k, AndroidImpl.this.l, AndroidImpl.this.m, AndroidImpl.this.n));
                        }
                    });
                }
                return true;
            }
            return false;
        } catch (Exception e2) {
            if (this.e != null) {
                this.e.onError(-1);
            }
        } catch (Throwable th) {
            if (this.e != null) {
                this.e.onError(-1);
            }
        }
    }
}

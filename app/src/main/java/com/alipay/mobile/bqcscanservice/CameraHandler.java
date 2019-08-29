package com.alipay.mobile.bqcscanservice;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.Map;

public class CameraHandler {
    public static final String TAG = "CameraScanHandler";
    private HandlerThread a;
    private Handler b;
    /* access modifiers changed from: private */
    public BQCScanService c;
    /* access modifiers changed from: private */
    public volatile int d = 0;

    public CameraHandler() {
        String modelSign = Build.BRAND + "/" + Build.MODEL;
        if (modelSign != null) {
            String modelSign2 = modelSign.toLowerCase();
            if (modelSign2.startsWith("xiaomi/redmi") || modelSign2.startsWith("vivo/vivo Y67") || modelSign2.startsWith("vivo/vivo V3") || modelSign2.startsWith("OPPO/OPPO R9") || modelSign2.startsWith("OPPO/OPPO A5")) {
                this.a = new HandlerThread("Camera-Handler", 10);
            } else {
                this.a = new HandlerThread("Camera-Handler", -1);
            }
        } else {
            this.a = new HandlerThread("Camera-Handler", -1);
        }
        this.a.start();
        this.b = new Handler(this.a.getLooper());
    }

    public void destroy() {
        this.a.quit();
    }

    public void setBqcScanService(final BQCScanService bqcScanService) {
        this.b.post(new Runnable() {
            public void run() {
                CameraHandler.this.c = bqcScanService;
            }
        });
    }

    public void preOpenCamera() {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In PreOpenCamera()" + CameraHandler.this.d);
                if (CameraHandler.this.d == 0) {
                    CameraHandler.this.c.tryPreOpenCamera();
                    CameraHandler.this.d = 0;
                }
            }
        });
    }

    public void postCloseCamera() {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In postCloseCamera()" + CameraHandler.this.d);
                if (CameraHandler.this.d == 0) {
                    CameraHandler.this.c.tryPostCloseCamera();
                    CameraHandler.this.d = 0;
                }
            }
        });
    }

    public void init(final Context context, final BQCScanCallback bqcCallback) {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In init()" + CameraHandler.this.d);
                if (1 > CameraHandler.this.d) {
                    CameraHandler.this.c.setup(context, bqcCallback);
                    CameraHandler.this.d = 1;
                }
            }
        });
    }

    public void configAndOpenCamera(final Map<String, Object> parameters) {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In configAndOpenCamera()" + CameraHandler.this.d);
                if (2 > CameraHandler.this.d) {
                    if (parameters != null) {
                        for (String key : parameters.keySet()) {
                            CameraHandler.this.c.setCameraParam(key, parameters.get(key));
                        }
                    }
                    CameraHandler.this.d = 2;
                    CameraHandler.this.openCamera();
                }
            }
        });
    }

    public void openCamera() {
        Logger.d(TAG, "In openCamera()" + this.d);
        if (Looper.myLooper() != this.b.getLooper()) {
            this.b.post(new Runnable() {
                public void run() {
                    Logger.d(CameraHandler.TAG, "post In openCamera()" + CameraHandler.this.d);
                    if (3 > CameraHandler.this.d) {
                        CameraHandler.this.c.startPreview();
                        CameraHandler.this.d = 3;
                    }
                }
            });
        } else if (3 > this.d) {
            this.c.startPreview();
            this.d = 3;
        }
    }

    public void onSurfaceViewAvailable() {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In onSurfaceViewAvailable()" + CameraHandler.this.d);
                if (CameraHandler.this.d == 3) {
                    CameraHandler.this.c.onSurfaceAvailable();
                }
            }
        });
    }

    public void closeCamera() {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In closeCamera()" + CameraHandler.this.d);
                if (4 > CameraHandler.this.d) {
                    CameraHandler.this.d = 4;
                    CameraHandler.this.c.stopPreview();
                    CameraHandler.this.d = 1;
                }
            }
        });
    }

    public void post(final Runnable runnable) {
        this.b.post(new Runnable() {
            public void run() {
                if (CameraHandler.this.d == 3) {
                    Logger.d(CameraHandler.TAG, "The curCameraState is " + CameraHandler.this.d);
                    runnable.run();
                }
            }
        });
    }

    public void post(Runnable runnable, boolean removeCallbacks) {
        if (this.d == 3) {
            if (removeCallbacks) {
                this.b.removeCallbacks(runnable);
            }
            this.b.post(runnable);
        }
    }

    public void removeCallbacks(Runnable runnable) {
        if (this.b != null) {
            this.b.removeCallbacks(runnable);
        }
    }

    public void release(final long postcode) {
        this.b.post(new Runnable() {
            public void run() {
                Logger.d(CameraHandler.TAG, "In release()" + CameraHandler.this.d);
                if (5 > CameraHandler.this.d) {
                    CameraHandler.this.c.cleanup(postcode);
                    CameraHandler.this.d = 0;
                }
            }
        });
    }

    public Handler getCameraHandler() {
        return this.b;
    }

    public boolean curCameraStateValid() {
        return this.d == 3;
    }
}

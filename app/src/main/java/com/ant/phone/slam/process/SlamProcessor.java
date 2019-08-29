package com.ant.phone.slam.process;

import android.content.Context;
import android.content.pm.PackageManager;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Looper;
import com.alipay.alipaylogger.Log;
import com.alipay.streammedia.cvengine.CVNativeEngineApi;
import com.alipay.streammedia.cvengine.CVNativeException;
import com.alipay.streammedia.cvengine.slam.ORBPhyCamParams;
import com.alipay.streammedia.cvengine.slam.ORBRenderModelParams;
import com.alipay.streammedia.cvengine.slam.ORBResult;
import com.alipay.streammedia.cvengine.slam.ORBRunMode;
import com.alipay.streammedia.cvengine.slam.ORBSensorParams;
import com.alipay.streammedia.cvengine.slam.ORBSensorParams.SensorType;
import com.alipay.streammedia.cvengine.slam.ORBTrackPicParams;
import com.alipay.streammedia.cvengine.slam.ORBVirtualCamParams;
import com.ant.phone.imu.RotationTracker;
import com.ant.phone.imu.RotationTracker.IMUListener;
import com.ant.phone.imu.VerticalTracker;
import com.ant.phone.slam.SlamData;
import com.ant.phone.slam.SlamParams;
import com.ant.phone.slam.SlamParams.PreviewSize;
import com.ant.phone.slam.stat.SlamTrackStat;
import com.ant.phone.slam.stat.Statistics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SlamProcessor implements IMUListener {
    public final int a = 256;
    Context b;
    private CVNativeEngineApi c;
    private SlamParams d = null;
    private AtomicBoolean e = new AtomicBoolean(false);
    private final LinkedList<ORBSensorParams> f = new LinkedList<>();
    private List<ORBSensorParams> g = new ArrayList(20);
    private boolean h = false;
    private RotationTracker i;
    private long j = 0;
    private ORBRenderModelParams k = new ORBRenderModelParams();
    private LinkedList<a> l = new LinkedList<>();
    private a m = new a(this, 0.0d, null);
    private byte[][] n = new byte[4][];
    private long o;
    private float[] p = new float[16];
    private byte[] q = new byte[0];
    private float[] r = new float[4];
    private float[] s = new float[3];
    private float[] t = new float[16];
    private float[] u = new float[16];
    private ORBSensorParams v = null;
    private Statistics w;
    private SlamTrackStat x = new SlamTrackStat();
    private long y = 0;

    private float[] b(float[] matrix) {
        Matrix.multiplyMM(this.u, 0, matrix, 0, this.t, 0);
        a(this.u, this.r);
        float x2 = this.r[0];
        float y2 = this.r[1];
        float z = this.r[2];
        float w2 = this.r[3];
        this.s[0] = (float) Math.toDegrees((double) ((float) Math.atan2((double) (2.0f * ((w2 * x2) + (y2 * z))), (double) (1.0f - (2.0f * ((x2 * x2) + (y2 * y2)))))));
        this.s[1] = (float) Math.toDegrees((double) ((float) Math.asin((double) (2.0f * ((w2 * y2) - (z * x2))))));
        this.s[2] = (float) Math.toDegrees((double) ((float) Math.atan2((double) (2.0f * ((w2 * z) + (x2 * y2))), (double) (1.0f - (2.0f * ((y2 * y2) + (z * z)))))));
        return this.s;
    }

    private static void a(float[] in, float[] quaternion) {
        float z;
        float x2;
        float y2;
        float w2;
        if (4 > quaternion.length) {
            throw new IllegalArgumentException("Not enough space to write the result");
        }
        float[] m2 = in;
        float t2 = in[0] + m2[5] + m2[10];
        if (t2 >= 0.0f) {
            float s2 = (float) Math.sqrt((double) (1.0f + t2));
            w2 = 0.5f * s2;
            float s3 = 0.5f / s2;
            x2 = (m2[9] - m2[6]) * s3;
            y2 = (m2[2] - m2[8]) * s3;
            z = (m2[4] - m2[1]) * s3;
        } else if (m2[0] > m2[5] && m2[0] > m2[10]) {
            float s4 = (float) Math.sqrt((double) (((1.0f + m2[0]) - m2[5]) - m2[10]));
            x2 = s4 * 0.5f;
            float s5 = 0.5f / s4;
            y2 = (m2[4] + m2[1]) * s5;
            z = (m2[2] + m2[8]) * s5;
            w2 = (m2[9] - m2[6]) * s5;
        } else if (m2[5] > m2[10]) {
            float s6 = (float) Math.sqrt((double) (((1.0f + m2[5]) - m2[0]) - m2[10]));
            y2 = s6 * 0.5f;
            float s7 = 0.5f / s6;
            x2 = (m2[4] + m2[1]) * s7;
            z = (m2[9] + m2[6]) * s7;
            w2 = (m2[2] - m2[8]) * s7;
        } else {
            float s8 = (float) Math.sqrt((double) (((1.0f + m2[10]) - m2[0]) - m2[5]));
            z = s8 * 0.5f;
            float s9 = 0.5f / s8;
            x2 = (m2[2] + m2[8]) * s9;
            y2 = (m2[9] + m2[6]) * s9;
            w2 = (m2[4] - m2[1]) * s9;
        }
        quaternion[0] = x2;
        quaternion[1] = y2;
        quaternion[2] = z;
        quaternion[3] = w2;
    }

    public final void a(float[] matrix) {
        if (this.e.get() && System.currentTimeMillis() - this.j >= 500) {
            synchronized (this.q) {
                if (!this.h) {
                    System.arraycopy(matrix, 0, this.p, 0, matrix.length);
                    Matrix.invertM(this.t, 0, matrix, 0);
                    this.h = true;
                }
                float[] rotations = b(matrix);
                ORBSensorParams rotationItem = new ORBSensorParams();
                rotationItem.setTimeStamp(System.currentTimeMillis());
                rotationItem.setX(rotations[0]);
                rotationItem.setY(rotations[1]);
                rotationItem.setZ(rotations[2]);
                rotationItem.setType(SensorType.ROTATION);
                synchronized (this.f) {
                    if (this.f.size() >= 20) {
                        this.f.removeFirst();
                    }
                    this.f.add(rotationItem);
                    this.v = rotationItem;
                }
            }
        }
    }

    public SlamProcessor(Context context) {
        this.b = context;
        Matrix.setIdentityM(this.p, 0);
    }

    public final float[] a() {
        return this.p;
    }

    public final boolean b() {
        PackageManager pm = this.b.getApplicationContext().getPackageManager();
        if (!pm.hasSystemFeature("android.hardware.sensor.accelerometer")) {
            Log.i("SlamProcessor", "supportSlam.return false there is no accelerometer");
            return false;
        } else if (!pm.hasSystemFeature("android.hardware.sensor.gyroscope")) {
            Log.i("SlamProcessor", "supportSlam.return false there is no gyroscope");
            return false;
        } else if (this.d == null || this.d.cameraSupportPreviewSizeList == null) {
            Log.i("SlamProcessor", "mSlamParams or previewList is null param = " + this.d);
            return false;
        } else {
            boolean hasSuitableSize = false;
            List previewSizeList = this.d.cameraSupportPreviewSizeList;
            int i2 = 0;
            while (true) {
                if (i2 >= previewSizeList.size()) {
                    break;
                }
                PreviewSize previewSize = previewSizeList.get(i2);
                if (previewSize.width == 1280 && previewSize.height == 720) {
                    hasSuitableSize = true;
                    break;
                }
                i2++;
            }
            if (!hasSuitableSize) {
                Log.i("SlamProcessor", "has no suitable preview size");
                return false;
            }
            Log.i("SlamProcessor", "supportSlam.return true");
            return true;
        }
    }

    public final void a(SlamParams params) {
        Log.d("SlamProcessor", "setSlamParams.params=" + params);
        this.d = params;
    }

    public static boolean b(SlamParams params) {
        if (params == null || params.viewWidth <= 0 || params.viewHeight <= 0 || params.defCamDistance <= 0.0f || Float.compare(params.defCamDistance, Float.MAX_VALUE) == 0 || params.cameraFps <= 0 || params.cameraPictureSizeWidth <= 0 || params.cameraPictureSizeHeight <= 0 || params.cameraFocalLength <= 0.0f || params.cameraHorizontalViewAngle <= 0.0f || params.cameraVerticalViewAngle <= 0.0f || params.cameraSupportPreviewSizeList == null || params.cameraSupportPreviewSizeList.isEmpty()) {
            return false;
        }
        return true;
    }

    public final boolean c() {
        long st = System.currentTimeMillis();
        if (!b(this.d)) {
            Log.e("SlamProcessor", "camera param is not valid... , mSlamParams = " + this.d);
            return false;
        }
        try {
            CVNativeEngineApi.loadLibrariesOnce(null);
            IjkMediaPlayer.loadLibrariesOnce(null);
            this.c = new CVNativeEngineApi();
            ORBPhyCamParams phy = new ORBPhyCamParams();
            phy.focusLength = this.d.cameraFocalLength;
            phy.horizontalViewAngle = this.d.cameraHorizontalViewAngle;
            phy.verticalViewAngle = this.d.cameraVerticalViewAngle;
            phy.sensorWidth = this.d.cameraPictureSizeWidth;
            phy.sensorHeight = this.d.cameraPictureSizeHeight;
            phy.fps = (this.d != null ? this.d.cameraFps / 1000 : 30) | 256;
            ORBVirtualCamParams virt = new ORBVirtualCamParams();
            virt.distance = this.d.defCamDistance;
            virt.horisentalFov = phy.horizontalViewAngle;
            virt.screenWidth = this.d.viewWidth;
            virt.screenHeight = this.d.viewHeight;
            ORBTrackPicParams pic = new ORBTrackPicParams();
            pic.width = 320;
            pic.height = 240;
            this.c.Init("", phy, virt, pic, ORBRunMode.FUSE);
            if (this.i != null) {
                this.i.stopTracking();
            }
            this.i = VerticalTracker.createFromContext(this.b);
            this.i.startTracking();
            this.i.registerListener(this);
            this.e.set(true);
            this.j = System.currentTimeMillis();
        } catch (CVNativeException e2) {
            Log.e("SlamProcessor", "start CVNativeException.e=" + e2.getMessage());
            this.e.set(false);
        } catch (Throwable e3) {
            Log.e("SlamProcessor", "start Throwable.e=" + e3.getMessage());
            this.e.set(false);
        }
        Log.i("SlamProcessor", "startSlam cost time=" + (System.currentTimeMillis() - st) + "，started=" + this.e.get());
        if (this.w != null) {
            this.e.get();
        }
        return this.e.get();
    }

    public final void d() {
        Log.i("SlamProcessor", "stop slam");
        this.e.set(false);
        if (this.i != null) {
            this.i.unRegisterListener(this);
            this.i.stopTracking();
            this.i = null;
        }
        this.h = false;
        new Handler(Looper.getMainLooper()).postDelayed(new b(this.c), 1000);
        this.c = null;
    }

    private byte[] a(int length) {
        int index = (int) (this.o % 4);
        byte[] cache = this.n[index];
        if (cache != null && cache.length == length) {
            return cache;
        }
        byte[] cache2 = new byte[length];
        this.n[index] = cache2;
        return cache2;
    }

    public final SlamData a(byte[] data, int width, int height, ORBRenderModelParams orbRenderModelParams) {
        ORBResult orbResult;
        if (data == null || width != 1280 || height != 720) {
            Log.e("SlamProcessor", "parseCameraData.viewWidth or viewHeight is wrong.viewWidth=" + width + ",viewHeight=" + height);
            return null;
        } else if (data.length != ((width * height) * 3) / 2) {
            Log.e("SlamProcessor", "parseCameraData.data length not match.length=" + data.length + ",viewWidth=" + width + ",viewHeight=" + height);
            return null;
        } else if (!this.e.get()) {
            Log.e("SlamProcessor", "parseCameraData.slam not started...");
            return null;
        } else {
            this.x.a();
            this.x.d = System.currentTimeMillis();
            this.x.b = width;
            this.x.c = height;
            a renderFrame = null;
            try {
                synchronized (this.f) {
                    this.g.addAll(this.f);
                    this.f.clear();
                    if (this.g.size() <= 0 && this.v != null) {
                        this.g.add(this.v);
                    }
                }
                if (orbRenderModelParams != null) {
                    this.k.x = orbRenderModelParams.x;
                    this.k.y = orbRenderModelParams.y;
                    this.k.z = orbRenderModelParams.z;
                    this.k.clickOn = orbRenderModelParams.clickOn;
                }
                double timeStamp = (double) System.currentTimeMillis();
                byte[] newData = a(data.length);
                System.arraycopy(data, 0, newData, 0, data.length);
                this.l.addLast(new a(this, timeStamp, newData));
                orbResult = this.c.startORB(timeStamp, newData, width, height, this.g, this.k);
                this.g.clear();
                this.o++;
                int index = -1;
                if (orbResult != null) {
                    this.m.a = orbResult.timeStamp;
                    index = this.l.indexOf(this.m);
                } else {
                    Log.e("SlamProcessor", "parseCameraData orbResult is null");
                }
                if (orbResult != null && index >= 0) {
                    renderFrame = this.l.get(index);
                    this.l.remove(index);
                }
                if (this.l.size() >= 4) {
                    this.l.clear();
                    this.l.addLast(this.l.getLast());
                }
            } catch (CVNativeException e2) {
                Log.d("SlamProcessor", "parseCameraData.CVNativeException.e=" + e2.getMessage());
                orbResult = null;
            } catch (Throwable e3) {
                Log.d("SlamProcessor", "parseCameraData.Throwable.e=" + e3.getMessage());
                orbResult = null;
            }
            this.x.e = System.currentTimeMillis() - this.x.d;
            this.x.d = System.currentTimeMillis();
            this.x.f = System.currentTimeMillis() - this.x.d;
            if (orbResult != null) {
                this.x.g = orbResult.fastDectedPoints;
                this.x.h = orbResult.fastTrackPoints;
                this.x.i = orbResult.totalMapPoints;
                this.x.j = orbResult.totalKeyFrames;
            } else {
                this.x.a = 1;
            }
            if (System.currentTimeMillis() - this.y > 5000) {
                Log.i("SlamProcessor", this.x.toString());
                this.y = System.currentTimeMillis();
            }
            return new SlamData(orbResult != null ? orbResult.ORBData : null, renderFrame != null ? renderFrame.b : null);
        }
    }
}

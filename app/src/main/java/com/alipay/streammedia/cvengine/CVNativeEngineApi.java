package com.alipay.streammedia.cvengine;

import com.alipay.streammedia.cvengine.CVNativeException.NativeExceptionCode;
import com.alipay.streammedia.cvengine.slam.ORBPhyCamParams;
import com.alipay.streammedia.cvengine.slam.ORBRenderModelParams;
import com.alipay.streammedia.cvengine.slam.ORBResult;
import com.alipay.streammedia.cvengine.slam.ORBRunMode;
import com.alipay.streammedia.cvengine.slam.ORBSensorParams;
import com.alipay.streammedia.cvengine.slam.ORBTrackPicParams;
import com.alipay.streammedia.cvengine.slam.ORBVirtualCamParams;
import com.alipay.streammedia.cvengine.tracking.MultiTrackerResult;
import com.alipay.streammedia.cvengine.tracking.TargetRect;
import com.alipay.streammedia.utils.SoLoadLock;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class CVNativeEngineApi extends CVNativeEngineNativeWrapper {
    public static final int MIN_TRACKING_IMAGE_EDGE = 640;
    public static final int MIN_TRACKING_TARGET_EDGE = 50;
    private static final String TAG = "CvEngineNativeEngine";
    private static volatile boolean mIsLibLoaded = false;
    private static final IjkLibLoader sLocalLibLoader = new IjkLibLoader() {
        public final void loadLibrary(String libName) {
            System.loadLibrary(libName);
        }
    };
    private long instanceId = -1;
    private boolean isShutdown = false;

    public static void loadLibrariesOnce(IjkLibLoader libLoader) {
        synchronized (SoLoadLock.class) {
            if (!mIsLibLoaded) {
                if (libLoader == null) {
                    libLoader = sLocalLibLoader;
                }
                try {
                    libLoader.loadLibrary("ijkcvengine");
                    mIsLibLoaded = true;
                } catch (Error e) {
                    throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
                }
            }
        }
    }

    public boolean Init(String VOCPath, ORBPhyCamParams phy, ORBVirtualCamParams virt, ORBTrackPicParams pic, ORBRunMode mode) {
        if (mode == ORBRunMode.VISION) {
            throw new CVNativeException(-9);
        }
        try {
            this.instanceId = (long) initSystemWithParameters(VOCPath, phy, virt, pic, mode.getIndex());
            if (this.instanceId != -8) {
                return true;
            }
            throw new CVNativeException(-8);
        } catch (Error e) {
            throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public synchronized ORBResult startORB(double curTimeStamp, byte[] data, int width, int height, List<ORBSensorParams> semsorData, ORBRenderModelParams renderModel) {
        ORBResult ret;
        if (this.isShutdown) {
            throw new CVNativeException(NativeExceptionCode.STATE_ERROR);
        } else if (data == null) {
            throw new CVNativeException(NativeExceptionCode.PARAM_ERROR);
        } else if (semsorData.size() > 400) {
            throw new CVNativeException(NativeExceptionCode.LIST_SIZE_ERROR);
        } else {
            try {
                ret = startCurrentORB(this.instanceId, curTimeStamp, data, width, height, semsorData, renderModel);
                if (ret.retCode == -7) {
                    throw new CVNativeException(NativeExceptionCode.CVENGINE_ORB_WORK_ERROR);
                }
            } catch (Error e) {
                throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
        return ret;
    }

    public synchronized void Destory() {
        if (this.isShutdown) {
            try {
                unInitORB(this.instanceId);
                this.instanceId = -1;
                this.isShutdown = true;
            } catch (Error e) {
                throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
    }

    public void Reset() {
        try {
            reSetORB(this.instanceId);
        } catch (Error e) {
            throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public boolean trackerInit(List<TargetRect> params, int width, int height, byte[] data, int maxEdge) {
        try {
            this.instanceId = (long) initMultiTracker(params, data, width, height, maxEdge);
            if (this.instanceId != -8) {
                return true;
            }
            throw new CVNativeException(-8);
        } catch (Error e) {
            throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
        }
    }

    public synchronized MultiTrackerResult trackerStart(byte[] data, int width, int height) {
        MultiTrackerResult ret;
        if (this.isShutdown) {
            throw new CVNativeException(NativeExceptionCode.STATE_ERROR);
        } else if (data == null) {
            throw new CVNativeException(NativeExceptionCode.PARAM_ERROR);
        } else {
            try {
                ret = tracker(this.instanceId, data, width, height);
                if (ret.retCode == -7) {
                    throw new CVNativeException(-7);
                }
            } catch (Error e) {
                throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
        return ret;
    }

    public synchronized void TrackingDestory() {
        if (this.isShutdown) {
            try {
                unInitTracker(this.instanceId);
                this.instanceId = -1;
                this.isShutdown = true;
            } catch (Error e) {
                throw new CVNativeException(NativeExceptionCode.RUNTIME_ERROR, (Throwable) e);
            }
        }
    }
}

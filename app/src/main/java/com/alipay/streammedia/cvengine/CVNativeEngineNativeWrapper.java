package com.alipay.streammedia.cvengine;

import com.alipay.streammedia.cvengine.slam.ORBPhyCamParams;
import com.alipay.streammedia.cvengine.slam.ORBRenderModelParams;
import com.alipay.streammedia.cvengine.slam.ORBResult;
import com.alipay.streammedia.cvengine.slam.ORBSensorParams;
import com.alipay.streammedia.cvengine.slam.ORBTrackPicParams;
import com.alipay.streammedia.cvengine.slam.ORBVirtualCamParams;
import com.alipay.streammedia.cvengine.tracking.MultiTrackerResult;
import com.alipay.streammedia.cvengine.tracking.TargetRect;
import java.util.List;

public class CVNativeEngineNativeWrapper {
    public static native int initMultiTracker(List<TargetRect> list, byte[] bArr, int i, int i2, int i3);

    public static native int initSystemWithParameters(String str, ORBPhyCamParams oRBPhyCamParams, ORBVirtualCamParams oRBVirtualCamParams, ORBTrackPicParams oRBTrackPicParams, int i);

    public static native void reSetORB(long j);

    public static native ORBResult startCurrentORB(long j, double d, byte[] bArr, int i, int i2, List<ORBSensorParams> list, ORBRenderModelParams oRBRenderModelParams);

    public static native MultiTrackerResult tracker(long j, byte[] bArr, int i, int i2);

    public static native void unInitORB(long j);

    public static native void unInitTracker(long j);
}

package com.autonavi.jni.ae.pos;

public class LocManager {
    public static native void addCommonObserver(PosCommonObserver posCommonObserver);

    public static native void addDebugInfoObserver(DebugInfoListener debugInfoListener);

    public static native void addDriveModeObserver(DriveModeObserver driveModeObserver);

    public static native void addLocListener(LocListener locListener, int i);

    public static native void addNGMListener(LocNGMListener locNGMListener);

    public static native void addParallelRoadObserver(LocParallelRoadObserver locParallelRoadObserver);

    public static native void addParallelSwitchObserver(LocParallelSwitchObserver locParallelSwitchObserver);

    public static native String getDebugInfo(int i);

    public static native boolean getGPSHistory(LocGPSHistory locGPSHistory);

    public static native boolean getHistoryTrace(int i, LocHistoryTrace locHistoryTrace);

    public static native String getVersion();

    public static native long init();

    private static native void nativeAddMagCalibrationObserver(PosMagCalibrationObserver posMagCalibrationObserver);

    private static native void nativeAddSignInfoObserver();

    private static native void nativeRemoveMagCalibrationObserver(PosMagCalibrationObserver posMagCalibrationObserver);

    private static native void nativeSetAMapStatu(int i);

    private static native void nativeSetCloudOption(int i, int i2);

    private static native void nativeSetLogSwitch(boolean z, boolean z2, int i, int i2);

    private static native void nativeSetMagnetic(int i, int i2, float f, float f2, float f3, long j);

    private static native void nativeSetOrientation(int i, int i2, float f, float f2, float f3, long j);

    private static native void nativeSetScene(int i, int i2, int i3);

    private static native void nativeSetUserDevInfo(int i, String str);

    public static native void notifyMagInterfere();

    public static native void removeCommonObserver(PosCommonObserver posCommonObserver);

    public static native void removeDriveModeObserver(DriveModeObserver driveModeObserver);

    public static native long requestCallBackPos(int i);

    public static native void saveLocStorage();

    public static native void setAcce(int i, float f, float f2, float f3, int i2, long j);

    public static native void setCarPosByCoord(int i, int i2, double d);

    public static native void setCompass(double d, long j);

    public static native void setDRPos(LocDRPos locDRPos);

    public static native void setDebug(int i, int i2);

    public static native void setDoorIn(LocDoorIn locDoorIn);

    public static native void setDriveSigInfo(LocDriveSig locDriveSig);

    public static native void setGSVData(LocGSVData locGSVData);

    public static native void setGpsInfo(GpsInfo gpsInfo);

    public static native void setGyro(int i, float f, float f2, float f3, float f4, int i2, long j);

    public static native void setLocNemaInfo(LocNemaInfo locNemaInfo);

    public static native void setOverheadSwitch(boolean z);

    public static native void setPressure(double d, long j);

    public static native void setPulse(float f, int i, long j);

    public static native void setW4M(LocW4M locW4M);

    public static native void setW4MTR(LocW4MTR locW4MTR);

    public static native void switchParallelRoad(long j);

    public static native void uninit();

    public static void setScene(int i, int i2) {
        nativeSetScene(i, i2, -1);
    }

    public static void setScene(int i, int i2, int i3) {
        nativeSetScene(i, i2, i3);
    }

    public static void setLogSwitch(boolean z, boolean z2, int i, int i2) {
        nativeSetLogSwitch(z, z2, i, i2);
    }

    public static void setLogSwitch(boolean z, boolean z2, int i) {
        setLogSwitch(z, z2, i, 1);
    }

    public static void addMagCalibrationObserver(PosMagCalibrationObserver posMagCalibrationObserver) {
        nativeAddMagCalibrationObserver(posMagCalibrationObserver);
    }

    public static void removeMagCalibrationObserver(PosMagCalibrationObserver posMagCalibrationObserver) {
        nativeRemoveMagCalibrationObserver(posMagCalibrationObserver);
    }

    public static void setCloudOption(int i, int i2) {
        nativeSetCloudOption(i, i2);
    }

    public static void setUserDevInfo(int i, String str) {
        nativeSetUserDevInfo(i, str);
    }

    public static void setAMapStatu(int i) {
        nativeSetAMapStatu(i);
    }

    public static void setMagnetic(int i, int i2, float f, float f2, float f3, long j) {
        nativeSetMagnetic(i, i2, f, f2, f3, j);
    }

    public static void setOrientation(int i, int i2, float f, float f2, float f3, long j) {
        nativeSetOrientation(i, i2, f, f2, f3, j);
    }
}

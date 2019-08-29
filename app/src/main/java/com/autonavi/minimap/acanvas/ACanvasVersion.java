package com.autonavi.minimap.acanvas;

public class ACanvasVersion {
    private static String mMagicMirrorVersion;

    public static String getMagicMirrorVersion() {
        if (mMagicMirrorVersion == null) {
            mMagicMirrorVersion = ACanvasJNI.getMagicMirrorVersion();
        }
        return mMagicMirrorVersion;
    }
}

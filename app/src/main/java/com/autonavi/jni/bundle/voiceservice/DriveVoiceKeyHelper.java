package com.autonavi.jni.bundle.voiceservice;

import com.amap.bundle.blutils.log.DebugLog;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepClassMemberNames
@Keep
@KeepName
public class DriveVoiceKeyHelper {
    private static native void nativeDestroy();

    private static native String nativeGetDecryptedString(String str);

    private static native String nativeGetEncryptedString(String str);

    private static native String nativeGetVoiceKey();

    private static native void nativeInit();

    public static String getKey() {
        nativeInit();
        String nativeGetVoiceKey = nativeGetVoiceKey();
        nativeDestroy();
        return nativeGetVoiceKey;
    }

    public static String getEncryptedString(String str) {
        nativeInit();
        String nativeGetEncryptedString = nativeGetEncryptedString(str);
        nativeDestroy();
        return nativeGetEncryptedString;
    }

    public static String getDecryptedString(String str) {
        nativeInit();
        String nativeGetDecryptedString = nativeGetDecryptedString(str);
        nativeDestroy();
        return nativeGetDecryptedString;
    }

    static {
        try {
            System.loadLibrary("GNaviVoice");
        } catch (Throwable th) {
            DebugLog.info(th.toString());
        }
    }
}

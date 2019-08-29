package com.alipay.sdk.app;

public class EnvUtils {
    private static EnvEnum mEnv = EnvEnum.ONLINE;

    public enum EnvEnum {
        ONLINE,
        SANDBOX
    }

    public static void setEnv(EnvEnum envEnum) {
        mEnv = envEnum;
    }

    public static EnvEnum geEnv() {
        return mEnv;
    }

    public static boolean isSandBox() {
        return mEnv == EnvEnum.SANDBOX;
    }
}

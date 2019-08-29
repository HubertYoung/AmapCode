package com.autonavi.sync;

public interface ICallback {
    String getCityName(String str, String str2);

    String getFileMd5(String str, String str2);

    boolean isAuto();

    @Deprecated
    String isP20InScreen(String str, String str2);

    void logIt(int i, String str);

    void onEvent(int i, int i2, String str);

    boolean removeFile(String str, String str2);
}

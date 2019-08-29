package com.autonavi.jni.drive.offline;

public interface HttpRequestInterface {
    HttpRequestInterface addHeader(String str, String str2);

    void cancel();

    int getResponseCode();

    String getResponseHeader(String str);

    long getUserData();

    void request(String str, int i);

    void setForDownloadFile(boolean z);

    void setNeedEncrpyt(boolean z);

    void setParma(String str, String str2);

    void setSignKes(String[] strArr);

    void setTimeOut(int i);

    void setUserData(long j);
}

package com.autonavi.sync;

public interface INetwork {
    String requestByNetwork(String str, String str2, String str3, String str4);

    String requestDownloadFile(String str, String str2, String str3, String str4, String str5);

    String requestUploadFile(String str, String str2, String str3, String str4, String str5, String str6);
}

package com.taobao.accs;

import java.util.Map;

public interface IAppReceiver {
    Map<String, String> getAllServices();

    String getService(String str);

    void onBindApp(int i);

    void onBindUser(String str, int i);

    void onData(String str, String str2, byte[] bArr);

    void onSendData(String str, int i);

    void onUnbindApp(int i);

    void onUnbindUser(int i);
}

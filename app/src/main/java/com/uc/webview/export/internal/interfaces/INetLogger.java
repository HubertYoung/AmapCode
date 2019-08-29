package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface INetLogger {
    void d(String str, String str2);

    void e(String str, String str2);

    boolean getEnable();

    int getLogLevel();

    void i(String str, String str2);

    void setEnable(boolean z);

    void setLogLevel(int i);

    void w(String str, String str2);
}

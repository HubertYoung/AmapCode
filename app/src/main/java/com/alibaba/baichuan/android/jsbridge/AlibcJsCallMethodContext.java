package com.alibaba.baichuan.android.jsbridge;

import java.lang.reflect.Method;

public class AlibcJsCallMethodContext {
    public Object classinstance;
    public Method method;
    public String methodName;
    public String objectName;
    public String params;
    public String token;
    public AlibcWebview webview;
}

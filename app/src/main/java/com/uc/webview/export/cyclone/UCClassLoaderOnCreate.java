package com.uc.webview.export.cyclone;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/* compiled from: ProGuard */
public class UCClassLoaderOnCreate implements Serializable {
    private static final Class connectException = ConnectException.class;
    private static final Class exception = Exception.class;
    private static final Class iOException = IOException.class;
    private static final Class illegalMonitorStateException = IllegalMonitorStateException.class;
    private static final Class interruptedException = InterruptedException.class;
    private static final Class noClassDefFoundError = NoClassDefFoundError.class;
    private static final Class throwable = Throwable.class;
    private static final Class timeoutException = TimeoutException.class;
    private static final Class unknownHostException = UnknownHostException.class;
    private static final Class unsupportedEncodingException = UnsupportedEncodingException.class;

    public UCClassLoaderOnCreate() {
        UCLogger.print(UCLogger.createToken("v", "UCClassLoaderOnCreate"), "UCClassLoaderOnCreate create success!!!", new Throwable[0]);
    }
}

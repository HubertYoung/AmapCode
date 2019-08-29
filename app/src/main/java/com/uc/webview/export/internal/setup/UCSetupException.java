package com.uc.webview.export.internal.setup;

import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.cyclone.UCKnownException;

@Api
/* compiled from: ProGuard */
public class UCSetupException extends UCKnownException {
    public UCSetupException(int i, String str, Throwable th) {
        super(i, str, th);
    }

    public UCSetupException(int i, Throwable th) {
        super(i, th);
    }

    public UCSetupException(int i, String str) {
        super(i, str);
    }

    public UCSetupException(Throwable th) {
        super(th);
    }

    public UCSetupException(String str) {
        super(str);
    }
}

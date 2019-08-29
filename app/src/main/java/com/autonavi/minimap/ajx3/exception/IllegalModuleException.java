package com.autonavi.minimap.ajx3.exception;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class IllegalModuleException extends RuntimeException {
    public IllegalModuleException() {
    }

    public IllegalModuleException(String str) {
        super(str);
    }

    public IllegalModuleException(String str, Throwable th) {
        super(str, th);
    }

    public IllegalModuleException(Throwable th) {
        super(th == null ? null : th.toString(), th);
    }
}

package com.autonavi.minimap.ajx3.exception;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class IllegalEngineException extends RuntimeException {
    public IllegalEngineException() {
    }

    public IllegalEngineException(String str) {
        super(str);
    }

    public IllegalEngineException(String str, Throwable th) {
        super(str, th);
    }

    public IllegalEngineException(Throwable th) {
        super(th == null ? null : th.toString(), th);
    }
}

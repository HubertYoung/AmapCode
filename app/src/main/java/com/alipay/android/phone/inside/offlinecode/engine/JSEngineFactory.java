package com.alipay.android.phone.inside.offlinecode.engine;

import android.support.annotation.NonNull;

public class JSEngineFactory {
    public static IJSEngine getEngine() throws Throwable {
        return getEngine(JSEngineType.WEBKIT);
    }

    public static IJSEngine getEngine(@NonNull JSEngineType jSEngineType) throws Throwable {
        return (IJSEngine) Class.forName(jSEngineType.clazz).newInstance();
    }
}

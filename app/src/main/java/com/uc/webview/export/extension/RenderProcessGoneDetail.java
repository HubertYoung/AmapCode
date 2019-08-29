package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.interfaces.InvokeObject;

@Api
/* compiled from: ProGuard */
public abstract class RenderProcessGoneDetail implements InvokeObject {
    public abstract boolean didCrash();

    public abstract int rendererPriorityAtExit();
}

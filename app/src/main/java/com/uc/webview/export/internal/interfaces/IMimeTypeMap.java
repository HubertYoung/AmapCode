package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IMimeTypeMap {
    String getExtensionFromMimeType(String str);

    String getFileExtensionFromUrlEx(String str);

    String getMimeTypeFromExtension(String str);

    boolean hasExtension(String str);

    boolean hasMimeType(String str);
}

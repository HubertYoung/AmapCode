package com.uc.webview.export.internal.android;

import android.webkit.MimeTypeMap;
import com.uc.webview.export.internal.interfaces.IMimeTypeMap;

/* compiled from: ProGuard */
public final class f implements IMimeTypeMap {
    private MimeTypeMap a = MimeTypeMap.getSingleton();

    public final String getFileExtensionFromUrlEx(String str) {
        return MimeTypeMap.getFileExtensionFromUrl(str);
    }

    public final boolean hasMimeType(String str) {
        return this.a.hasMimeType(str);
    }

    public final String getMimeTypeFromExtension(String str) {
        return this.a.getMimeTypeFromExtension(str);
    }

    public final boolean hasExtension(String str) {
        return this.a.hasExtension(str);
    }

    public final String getExtensionFromMimeType(String str) {
        return this.a.getExtensionFromMimeType(str);
    }
}

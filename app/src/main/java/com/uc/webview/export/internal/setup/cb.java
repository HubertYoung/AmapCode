package com.uc.webview.export.internal.setup;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: ProGuard */
final class cb implements FilenameFilter {
    cb() {
    }

    public final boolean accept(File file, String str) {
        return file != null && str != null && file.getPath().startsWith("lib") && str.startsWith("lib") && str.endsWith(".so") && !str.startsWith("libkernel");
    }
}

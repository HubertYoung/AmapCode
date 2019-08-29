package com.uc.webview.export.internal.setup;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: ProGuard */
final class cf implements FilenameFilter {
    cf() {
    }

    public final boolean accept(File file, String str) {
        return str.startsWith("uc_temp_dec_");
    }
}

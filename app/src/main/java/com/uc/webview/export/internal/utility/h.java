package com.uc.webview.export.internal.utility;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: ProGuard */
final class h implements FilenameFilter {
    h() {
    }

    public final boolean accept(File file, String str) {
        return str.contains("sdk_shell");
    }
}

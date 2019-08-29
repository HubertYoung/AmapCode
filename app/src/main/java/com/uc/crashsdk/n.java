package com.uc.crashsdk;

import com.uc.crashsdk.a.h;
import java.io.File;
import java.io.FilenameFilter;

/* compiled from: ProGuard */
final class n implements FilenameFilter {
    n() {
    }

    public final boolean accept(File file, String str) {
        return h.b(str) && str.endsWith(".st");
    }
}

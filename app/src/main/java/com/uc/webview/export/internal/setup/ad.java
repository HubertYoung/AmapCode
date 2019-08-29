package com.uc.webview.export.internal.setup;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: ProGuard */
final class ad implements FilenameFilter {
    final /* synthetic */ ac a;

    ad(ac acVar) {
        this.a = acVar;
    }

    public final boolean accept(File file, String str) {
        return file != null && str != null && file.getPath().startsWith("lib") && str.equals("libkernelu4_uc_7z.so");
    }
}

package com.alipay.mobile.security.bio.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* compiled from: DeviceUtil */
final class a implements FileFilter {
    a() {
    }

    public final boolean accept(File file) {
        if (Pattern.matches("cpu[0-9]+", file.getName())) {
            return true;
        }
        return false;
    }
}

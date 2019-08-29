package com.alipay.deviceid.module.x;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

final class l implements FileFilter {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    public final boolean accept(File file) {
        return Pattern.matches("cpu[0-9]+", file.getName());
    }
}

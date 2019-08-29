package com.alipay.mobile.common.logging.appender;

import java.io.File;
import java.util.Comparator;

/* compiled from: ExternalFileAppender */
final class a implements Comparator<File> {
    a() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return a((File) obj, (File) obj2);
    }

    private static int a(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

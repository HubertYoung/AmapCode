package com.alipay.mobile.common.logging;

import java.io.File;
import java.util.Comparator;

/* compiled from: MdapLogUploadManager */
final class e implements Comparator<File> {
    e() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return a((File) obj, (File) obj2);
    }

    private static int a(File o1, File o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

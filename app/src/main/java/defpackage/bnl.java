package defpackage;

import java.io.File;

/* renamed from: bnl reason: default package */
/* compiled from: UploadFileFactory */
public final class bnl {
    public static bnj a(File file, File[] fileArr, bmt bmt) {
        if (bmt == null) {
            return null;
        }
        if (bmt.H()) {
            return new bnk(file, fileArr, bmt);
        }
        return new bni(file, fileArr, bmt);
    }
}

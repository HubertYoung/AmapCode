package defpackage;

import java.io.File;

/* renamed from: enm reason: default package */
/* compiled from: SoHotfixLibPath */
final class enm implements Comparable<enm> {
    int a = -1;
    String b = "";

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        enm enm = (enm) obj;
        if (enm == null) {
            return -1;
        }
        return this.a - enm.a;
    }

    public enm(File file) {
        this.b = file.getPath();
        if (file.isDirectory()) {
            try {
                this.a = Integer.parseInt(file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final String toString() {
        return this.b != null ? this.b : "";
    }
}

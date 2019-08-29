package defpackage;

import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.blutils.PathManager.DirType;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.util.ArrayList;

/* renamed from: dgu reason: default package */
/* compiled from: DriveFilePathHelper */
public final class dgu {
    private static volatile dgu a;
    private String b = PathManager.a().b(DirType.DRIVE_VOICE);

    private dgu() {
        new StringBuilder("DriveFilePathHelper mCurrentStoragePath:").append(this.b);
    }

    public static dgu a() {
        if (a == null) {
            synchronized (dgu.class) {
                try {
                    if (a == null) {
                        a = new dgu();
                    }
                }
            }
        }
        return a;
    }

    public static void b() {
        a = null;
    }

    public static String a(String str) {
        String b2 = PathManager.a().b(DirType.DRIVE_VOICE);
        dhb.a("OfflineDownloadUtil", "############## > dir = ".concat(String.valueOf(b2)));
        StringBuilder sb = new StringBuilder();
        sb.append(c(b2));
        StringBuilder sb2 = new StringBuilder("/");
        sb2.append(str);
        sb2.append("/");
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("############## > sb.toString() = ");
        sb3.append(sb.toString());
        dhb.a("OfflineDownloadUtil", sb3.toString());
        return sb.toString();
    }

    public final void c() {
        this.b = PathManager.a().b(DirType.DRIVE_VOICE);
    }

    public static boolean b(String str) {
        File[] listFiles = new File(a(str)).listFiles();
        return (listFiles == null || listFiles.length == 0) ? false : true;
    }

    public static String c(String str) {
        File file = new File(str, "autonavi");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, FilePathHelper.FOLDER_NAVI_RECORD);
        if (!file2.exists()) {
            file2.mkdir();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file2.toString());
        sb.append("/");
        return sb.toString();
    }

    public static void a(File file, boolean z) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                arrayList.add(file);
                while (arrayList.size() > 0) {
                    File file2 = (File) arrayList.remove(0);
                    if (file2 != null && file2.exists() && file2.isDirectory()) {
                        String[] list = file2.list();
                        if (list != null && list.length > 0) {
                            for (String str : list) {
                                if (str != null) {
                                    File file3 = new File(file2, str);
                                    if (file3.exists()) {
                                        if (file3.isDirectory()) {
                                            arrayList.add(file3);
                                        } else {
                                            a(file3);
                                        }
                                    }
                                }
                            }
                        }
                        arrayList2.add(file2);
                    }
                }
                int i = !z;
                for (int size = arrayList2.size() - 1; size >= i; size--) {
                    File file4 = (File) arrayList2.get(size);
                    if (file4 != null && file4.exists()) {
                        a(file4);
                    }
                }
                return;
            }
            a(file);
        }
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(file.getAbsolutePath());
        sb.append(System.currentTimeMillis());
        File file2 = new File(sb.toString());
        file.renameTo(file2);
        return file2.delete();
    }

    public static long b(File file) {
        long j = 0;
        if (!file.exists()) {
            return 0;
        }
        if (file.isDirectory()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(file);
            while (arrayList.size() > 0) {
                File file2 = (File) arrayList.remove(0);
                if (file2 != null && file2.exists() && file2.isDirectory()) {
                    String[] list = file2.list();
                    if (list != null && list.length > 0) {
                        for (String str : list) {
                            if (str != null) {
                                File file3 = new File(file2, str);
                                if (file3.exists()) {
                                    if (file3.isDirectory()) {
                                        arrayList.add(file3);
                                    } else {
                                        j += file3.length();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            j = 0 + file.length();
        }
        return j;
    }

    private String e() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b);
        sb.append(File.separator);
        sb.append("autonavi/data/voice");
        return sb.toString();
    }

    public final String d() {
        String e = e();
        File file = new File(e);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return e;
    }
}

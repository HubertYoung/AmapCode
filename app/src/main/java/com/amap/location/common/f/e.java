package com.amap.location.common.f;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FileUtil */
public class e {
    private static volatile String a;

    public static boolean a() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean a(File file) {
        if (!file.exists() && !file.mkdirs()) {
            return false;
        }
        a = file.getAbsolutePath();
        return true;
    }

    public static String a(Context context) {
        if (a != null) {
            return a;
        }
        if (a()) {
            try {
                File file = new File(context.getExternalFilesDir(null), "amaplocation");
                if (file.exists() || file.mkdirs()) {
                    a = file.getAbsolutePath();
                }
            } catch (Exception unused) {
                return null;
            }
        }
        return a;
    }

    public static long b(Context context) {
        return d(context.getFilesDir());
    }

    private static long d(File file) {
        try {
            StatFs statFs = new StatFs(file.getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
            }
            return ((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean b(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(file);
        while (arrayList2.size() > 0) {
            File file2 = (File) arrayList2.remove(0);
            arrayList.add(file2);
            File[] listFiles = file2.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file3 : listFiles) {
                    if (file3.isDirectory()) {
                        arrayList2.add(file3);
                    } else if (file3.exists() && !file3.delete()) {
                        return false;
                    }
                }
                continue;
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            File file4 = (File) arrayList.get(size);
            if (file4.exists() && !file4.delete()) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(String str, File file, boolean z) {
        if (file == null) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            FileWriter fileWriter2 = new FileWriter(file.getAbsolutePath(), z);
            try {
                fileWriter2.write(str);
                g.a((Closeable) fileWriter2);
                return true;
            } catch (Exception e) {
                e = e;
                fileWriter = fileWriter2;
                try {
                    e.printStackTrace();
                    g.a((Closeable) fileWriter);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    g.a((Closeable) fileWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter = fileWriter2;
                g.a((Closeable) fileWriter);
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            g.a((Closeable) fileWriter);
            return false;
        }
    }

    public static List<String> c(File file) {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        if (file != null) {
            try {
                if (file.exists()) {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            arrayList.add(readLine);
                        } catch (IOException unused) {
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            g.a((Closeable) bufferedReader);
                            throw th;
                        }
                    }
                    bufferedReader = bufferedReader2;
                }
            } catch (IOException unused2) {
            } catch (Throwable th2) {
                th = th2;
                g.a((Closeable) bufferedReader);
                throw th;
            }
        }
        g.a((Closeable) bufferedReader);
        return arrayList;
    }
}

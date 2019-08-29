package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public final class FileUtil {
    public static final int STREAM_BUFFER_SIZE = 8192;
    private static final String TAG = "FileUtil";

    static class MyFilenameFilter implements FilenameFilter {
        boolean allAccept = true;
        final Set<String> excludePrefixes;
        final Set<String> excludes;

        MyFilenameFilter(Set<String> set, Set<String> set2) {
            this.excludes = set;
            this.excludePrefixes = set2;
        }

        public boolean accept(File file, String str) {
            boolean z;
            boolean z2 = true;
            if (this.excludes == null || !this.excludes.contains(str)) {
                if (this.excludePrefixes != null && !this.excludePrefixes.isEmpty()) {
                    Iterator<String> it = this.excludePrefixes.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (str.startsWith(it.next())) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                z = true;
            } else {
                z = false;
            }
            if (!z || !this.allAccept) {
                z2 = false;
            }
            this.allAccept = z2;
            return z;
        }

        public boolean allAccept() {
            return this.allAccept;
        }
    }

    public static boolean copyFile(String str, String str2) {
        return copyFile(new File(str), new File(str2));
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00df  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFile(java.io.File r9, java.io.File r10) {
        /*
            r0 = 0
            r1 = 1
            if (r9 == 0) goto L_0x0105
            boolean r2 = r9.exists()
            if (r2 == 0) goto L_0x0105
            if (r10 != 0) goto L_0x000e
            goto L_0x0105
        L_0x000e:
            boolean r2 = r9.isDirectory()
            if (r2 == 0) goto L_0x0100
            boolean r2 = r10.exists()
            if (r2 == 0) goto L_0x004a
            boolean r2 = r10.isDirectory()
            if (r2 == 0) goto L_0x0022
            r2 = 1
            goto L_0x006b
        L_0x0022:
            boolean r2 = r10.delete()
            java.lang.String r3 = "FileUtil"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "copyFile(File["
            r4.<init>(r5)
            r4.append(r9)
            java.lang.String r5 = "], File["
            r4.append(r5)
            r4.append(r10)
            java.lang.String r5 = "]): oRoot exist, and is not a dir. try to delete it:"
            r4.append(r5)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            com.alipay.mobile.quinox.log.Log.d(r3, r2)
            goto L_0x006a
        L_0x004a:
            java.lang.String r2 = "FileUtil"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "copyFile(File["
            r3.<init>(r4)
            r3.append(r9)
            java.lang.String r4 = "], File["
            r3.append(r4)
            r3.append(r10)
            java.lang.String r4 = "]): oRoot don't not exist."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.quinox.log.Log.d(r2, r3)
        L_0x006a:
            r2 = 0
        L_0x006b:
            if (r2 == 0) goto L_0x008e
            java.lang.String r3 = "FileUtil"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "copyFile(File["
            r4.<init>(r5)
            r4.append(r9)
            java.lang.String r5 = "], File["
            r4.append(r5)
            r4.append(r10)
            java.lang.String r5 = "]): oRoot exist now."
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.quinox.log.Log.d(r3, r4)
            goto L_0x0092
        L_0x008e:
            boolean r2 = r10.mkdirs()
        L_0x0092:
            if (r2 == 0) goto L_0x00df
            java.lang.String[] r2 = r9.list()
            if (r2 == 0) goto L_0x00be
            int r3 = r2.length
            if (r3 <= 0) goto L_0x00be
            int r3 = r2.length
            r4 = 0
            r5 = 1
        L_0x00a0:
            if (r4 >= r3) goto L_0x00bc
            r6 = r2[r4]
            java.io.File r7 = new java.io.File
            r7.<init>(r9, r6)
            java.io.File r8 = new java.io.File
            r8.<init>(r10, r6)
            boolean r6 = copyFile(r7, r8)
            if (r6 == 0) goto L_0x00b8
            if (r5 == 0) goto L_0x00b8
            r5 = 1
            goto L_0x00b9
        L_0x00b8:
            r5 = 0
        L_0x00b9:
            int r4 = r4 + 1
            goto L_0x00a0
        L_0x00bc:
            r0 = r5
            goto L_0x0126
        L_0x00be:
            java.lang.String r0 = "FileUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "copyFile(File["
            r2.<init>(r3)
            r2.append(r9)
            java.lang.String r9 = "], File["
            r2.append(r9)
            r2.append(r10)
            java.lang.String r9 = "]): oRoot is an empty dir."
            r2.append(r9)
            java.lang.String r9 = r2.toString()
            com.alipay.mobile.quinox.log.Log.d(r0, r9)
            goto L_0x0125
        L_0x00df:
            java.lang.String r1 = "FileUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "copyFile(File["
            r2.<init>(r3)
            r2.append(r9)
            java.lang.String r9 = "], File["
            r2.append(r9)
            r2.append(r10)
            java.lang.String r9 = "]): oRoot finally failed to make dirs."
            r2.append(r9)
            java.lang.String r9 = r2.toString()
            com.alipay.mobile.quinox.log.Log.d(r1, r9)
            goto L_0x0126
        L_0x0100:
            boolean r0 = doCopySingleFile(r9, r10)
            goto L_0x0126
        L_0x0105:
            java.lang.String r0 = "FileUtil"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "copyFile(File["
            r2.<init>(r3)
            r2.append(r9)
            java.lang.String r9 = "], File["
            r2.append(r9)
            r2.append(r10)
            java.lang.String r9 = "]): null == iRoot || !iRoot.exists() || null == oRoot"
            r2.append(r9)
            java.lang.String r9 = r2.toString()
            com.alipay.mobile.quinox.log.Log.d(r0, r9)
        L_0x0125:
            r0 = 1
        L_0x0126:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.FileUtil.copyFile(java.io.File, java.io.File):boolean");
    }

    public static boolean copySingleFile(String str, String str2) {
        return copySingleFile(new File(str), new File(str2));
    }

    public static boolean copySingleFile(File file, File file2) {
        if (file != null && file.exists() && file2 != null) {
            return doCopySingleFile(file, file2);
        }
        StringBuilder sb = new StringBuilder("copySingleFile(File[");
        sb.append(file);
        sb.append("], File[");
        sb.append(file2);
        sb.append("]): null == iFile || !iFile.exists() || null == oFile");
        Log.d((String) "FileUtil", sb.toString());
        return true;
    }

    private static boolean doCopySingleFile(File file, File file2) {
        boolean z;
        if (file2.exists()) {
            z = !file2.delete();
        } else {
            StringBuilder sb = new StringBuilder("doCopySingleFile(File[");
            sb.append(file);
            sb.append("], File[");
            sb.append(file2);
            sb.append("]): target file don't exist, no need to delete it.");
            Log.d((String) "FileUtil", sb.toString());
            z = false;
        }
        if (z) {
            StringBuilder sb2 = new StringBuilder("doCopySingleFile(File[");
            sb2.append(file);
            sb2.append("], File[");
            sb2.append(file2);
            sb2.append("]): failed to delete exist file.");
            Log.d((String) "FileUtil", sb2.toString());
            return false;
        }
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                return StreamUtil.streamToFile(fileInputStream2, file2);
            } catch (Exception e) {
                FileInputStream fileInputStream3 = fileInputStream2;
                e = e;
                fileInputStream = fileInputStream3;
                StreamUtil.closeSafely(fileInputStream);
                StringBuilder sb3 = new StringBuilder("doCopySingleFile(File[");
                sb3.append(file);
                sb3.append("], File[");
                sb3.append(file2);
                sb3.append("]): Exception occur.");
                Log.w("FileUtil", sb3.toString(), e);
                return false;
            }
        } catch (Exception e2) {
            e = e2;
            StreamUtil.closeSafely(fileInputStream);
            StringBuilder sb32 = new StringBuilder("doCopySingleFile(File[");
            sb32.append(file);
            sb32.append("], File[");
            sb32.append(file2);
            sb32.append("]): Exception occur.");
            Log.w("FileUtil", sb32.toString(), e);
            return false;
        }
    }

    public static boolean deleteFile(String str) {
        return deleteFile(new File(str));
    }

    public static boolean deleteFile(File file) {
        boolean z;
        if (file == null || !file.exists()) {
            StringBuilder sb = new StringBuilder("deleteFile(File[");
            sb.append(file);
            sb.append("]): null == root || !root.exists()");
            Log.d((String) "FileUtil", sb.toString());
            return true;
        } else if (!file.isDirectory()) {
            return file.delete();
        } else {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                StringBuilder sb2 = new StringBuilder("deleteFile(File[");
                sb2.append(file);
                sb2.append("]): is an empty dir.");
                Log.d((String) "FileUtil", sb2.toString());
                z = true;
            } else {
                z = true;
                for (File deleteFile : listFiles) {
                    z = deleteFile(deleteFile) && z;
                }
            }
            if (!file.delete() || !z) {
                return false;
            }
            return true;
        }
    }

    public static void copyFile(FileChannel fileChannel, String str) throws IOException {
        FileChannel fileChannel2 = null;
        try {
            FileChannel channel = new FileOutputStream(str).getChannel();
            try {
                fileChannel.transferTo(0, fileChannel.size(), channel);
                StreamUtil.closeSafely(channel);
            } catch (Throwable th) {
                th = th;
                fileChannel2 = channel;
                StreamUtil.closeSafely(fileChannel2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            StreamUtil.closeSafely(fileChannel2);
            throw th;
        }
    }

    public static void copyFile(BufferedInputStream bufferedInputStream, File file) throws IOException {
        FileOutputStream fileOutputStream;
        boolean z = true;
        try {
            if (!file.exists()) {
                z = file.createNewFile();
            }
            if (z) {
                fileOutputStream = new FileOutputStream(file, false);
                try {
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int read = bufferedInputStream.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileOutputStream.flush();
                } catch (Throwable th) {
                    th = th;
                    StreamUtil.closeSafely(fileOutputStream);
                    throw th;
                }
            } else {
                fileOutputStream = null;
            }
            StreamUtil.closeSafely(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            StreamUtil.closeSafely(fileOutputStream);
            throw th;
        }
    }

    public static void deleteSingleFile(String str) {
        if (str != null) {
            deleteSingleFile(new File(str));
        }
    }

    private static void deleteSingleFile(File file) {
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    public static boolean deleteSingleFileImmediately(String str) {
        if (str != null) {
            return deleteSingleFileImmediately(new File(str));
        }
        Log.e((String) "FileUtil", (String) "deleteFileImmediately(path == null)");
        return true;
    }

    public static boolean deleteSingleFileImmediately(File file) {
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                StringBuilder sb = new StringBuilder("deleteFileImmediately(path=");
                sb.append(file);
                sb.append(")");
                Log.v((String) "FileUtil", sb.toString());
                return delete;
            }
            StringBuilder sb2 = new StringBuilder("Failed to deleteFileImmediately(path=");
            sb2.append(file);
            sb2.append(")");
            Log.w((String) "FileUtil", sb2.toString());
            return delete;
        }
        StringBuilder sb3 = new StringBuilder("deleteFileImmediately(path=");
        sb3.append(file);
        sb3.append("): not exists.");
        Log.w((String) "FileUtil", sb3.toString());
        return true;
    }

    public static boolean deleteFilesWithExcludes(File file, Set<String> set) {
        return deleteFiles(file, set, null);
    }

    public static boolean deleteFilesWithExcludePrefixes(File file, Set<String> set) {
        return deleteFiles(file, null, set);
    }

    public static boolean deleteFiles(File file) {
        return deleteFiles(file, null, null);
    }

    public static boolean deleteFiles(File file, Set<String> set, Set<String> set2) {
        boolean z;
        if (file == null || !file.exists()) {
            StringBuilder sb = new StringBuilder("deleteFile(File[");
            sb.append(file);
            sb.append("]): null == root || !root.exists()");
            Log.e((String) "FileUtil", sb.toString());
            return true;
        } else if (file.isDirectory()) {
            MyFilenameFilter myFilenameFilter = new MyFilenameFilter(set, set2);
            File[] listFiles = file.listFiles(myFilenameFilter);
            boolean allAccept = myFilenameFilter.allAccept();
            if (listFiles == null || listFiles.length <= 0) {
                z = allAccept;
            } else {
                boolean z2 = allAccept;
                for (File deleteFiles : listFiles) {
                    z2 = deleteFiles(deleteFiles, set, set2) && z2;
                }
                z = z2;
            }
            if (set != null && set.contains(file.getName())) {
                StringBuilder sb2 = new StringBuilder("Excluded to delete dir: ");
                sb2.append(file.getAbsolutePath());
                Log.v((String) "FileUtil", sb2.toString());
                return z;
            } else if (z) {
                boolean delete = file.delete();
                if (delete) {
                    StringBuilder sb3 = new StringBuilder("Delete dir: ");
                    sb3.append(file.getAbsolutePath());
                    Log.v((String) "FileUtil", sb3.toString());
                    return delete;
                }
                StringBuilder sb4 = new StringBuilder("Failed to delete dir: ");
                sb4.append(file.getAbsolutePath());
                Log.w((String) "FileUtil", sb4.toString());
                return delete;
            } else {
                StringBuilder sb5 = new StringBuilder("Ignore to delete dir (not empty): ");
                sb5.append(file.getAbsolutePath());
                Log.v((String) "FileUtil", sb5.toString());
                return z;
            }
        } else if (set == null || !set.contains(file.getName())) {
            boolean delete2 = file.delete();
            if (delete2) {
                StringBuilder sb6 = new StringBuilder("Delete file: ");
                sb6.append(file.getAbsolutePath());
                Log.v((String) "FileUtil", sb6.toString());
                return delete2;
            }
            StringBuilder sb7 = new StringBuilder("Failed to delete file: ");
            sb7.append(file.getAbsolutePath());
            Log.e((String) "FileUtil", sb7.toString());
            return delete2;
        } else {
            StringBuilder sb8 = new StringBuilder("Excluded to delete file: ");
            sb8.append(file.getAbsolutePath());
            Log.v((String) "FileUtil", sb8.toString());
            return false;
        }
    }

    public static File[] listFiles(File file) {
        return listFiles(file, (FileFilter) null);
    }

    public static File[] listFiles(File file, FileFilter fileFilter) {
        ArrayList arrayList = new ArrayList();
        doListFiles(file, fileFilter, arrayList);
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    private static void doListFiles(File file, FileFilter fileFilter, ArrayList<File> arrayList) {
        if (!file.isFile()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File doListFiles : listFiles) {
                    doListFiles(doListFiles, fileFilter, arrayList);
                }
            }
        } else if (fileFilter == null || fileFilter.accept(file)) {
            arrayList.add(file);
        }
    }

    public static File[] listFiles(File file, FilenameFilter filenameFilter) {
        ArrayList arrayList = new ArrayList();
        doListFiles(file, filenameFilter, arrayList);
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    private static void doListFiles(File file, FilenameFilter filenameFilter, ArrayList<File> arrayList) {
        if (!file.isFile()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File doListFiles : listFiles) {
                    doListFiles(doListFiles, filenameFilter, arrayList);
                }
            }
        } else if (filenameFilter == null || filenameFilter.accept(file.getParentFile(), file.getName())) {
            arrayList.add(file);
        }
    }
}

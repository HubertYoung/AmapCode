package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipFileUtil {
    private static final String TAG = "ZipFileUtil";

    private ZipFileUtil() {
    }

    public static boolean insertEntry(String str, Map<String, File> map, boolean z) throws IOException {
        if (!StringUtil.isEmpty(str)) {
            return insertEntry(new File(str), map, z);
        }
        throw new IllegalArgumentException("the parameter 'file' is empty.");
    }

    public static boolean insertEntry(File file, Map<String, File> map, boolean z) throws IOException {
        if (file != null) {
            return doInsertEntry(file, new ZipFile(file), map, z);
        }
        throw new IllegalArgumentException("file == null.");
    }

    private static boolean doInsertEntry(File file, ZipFile zipFile, Map<String, File> map, boolean z) throws IOException {
        if (zipFile == null) {
            throw new IllegalArgumentException("file == null.");
        } else if (map == null || map.size() == 0) {
            throw new IllegalArgumentException("archivePaths is empty.");
        } else {
            Set<String> keySet = map.keySet();
            HashSet hashSet = new HashSet();
            if (!z) {
                for (String next : keySet) {
                    if (zipFile.getEntry(next) != null) {
                        hashSet.add(next);
                    }
                }
                if (!hashSet.isEmpty()) {
                    StringBuilder sb = new StringBuilder("Already exist Entries: ");
                    sb.append(StringUtil.collection2String(hashSet));
                    sb.append("\r\n You may should pass 'force'=true");
                    Log.w((String) TAG, sb.toString());
                    return false;
                }
            }
            String parent = file.getParent();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getName());
            sb2.append(FilePathHelper.SUFFIX_DOT_TMP);
            File file2 = new File(parent, sb2.toString());
            if (file2.exists() && !file2.delete()) {
                throw new IOException("Failed to delete pre temp file.");
            } else if (!file2.createNewFile()) {
                StringBuilder sb3 = new StringBuilder("Failed to create temp file. ");
                sb3.append(file);
                sb3.append(" => ");
                sb3.append(file2);
                throw new IOException(sb3.toString());
            } else {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file2));
                zipOutputStream.setLevel(9);
                try {
                    ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
                    while (true) {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry != null) {
                            String name = nextEntry.getName();
                            if (!pathSecurityCheck(name)) {
                                throw new IOException("security check fail : ".concat(String.valueOf(name)));
                            } else if (!z || !keySet.contains(name)) {
                                zipOutputStream.putNextEntry(nextEntry);
                                StreamUtil.copyStreamWithoutClosing(zipInputStream, zipOutputStream);
                                zipOutputStream.closeEntry();
                            } else {
                                Log.w((String) TAG, "Ignore zip entry (force=true):".concat(String.valueOf(name)));
                            }
                        } else {
                            StreamUtil.closeSafely(zipInputStream);
                            zipOutputStream.flush();
                            for (Entry next2 : map.entrySet()) {
                                zipOutputStream.putNextEntry(new ZipEntry((String) next2.getKey()));
                                FileInputStream fileInputStream = new FileInputStream((File) next2.getValue());
                                StreamUtil.copyStreamWithoutClosing(fileInputStream, zipOutputStream);
                                StreamUtil.closeSafely(fileInputStream);
                            }
                            zipOutputStream.flush();
                            FileUtil.deleteFile(file);
                            return file2.renameTo(file);
                        }
                    }
                } finally {
                    zipFile.close();
                    StreamUtil.closeSafely(zipOutputStream);
                }
            }
        }
    }

    public static boolean deleteEntry(String str, String... strArr) throws IOException {
        if (!StringUtil.isEmpty(str)) {
            return deleteEntry(new File(str), strArr);
        }
        throw new IllegalArgumentException("the parameter 'file' is empty.");
    }

    public static boolean deleteEntry(File file, String... strArr) throws IOException {
        if (file != null) {
            return doDeleteEntry(file, new ZipFile(file), strArr);
        }
        throw new IllegalArgumentException("file == null.");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0109, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010d, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0117, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000d, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:3:0x0005, B:35:0x00b8] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized boolean doDeleteEntry(java.io.File r7, java.util.zip.ZipFile r8, java.lang.String... r9) throws java.io.IOException {
        /*
            java.lang.Class<com.alipay.mobile.quinox.utils.ZipFileUtil> r0 = com.alipay.mobile.quinox.utils.ZipFileUtil.class
            monitor-enter(r0)
            if (r8 != 0) goto L_0x0010
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x000d }
            java.lang.String r8 = "file == null."
            r7.<init>(r8)     // Catch:{ all -> 0x000d }
            throw r7     // Catch:{ all -> 0x000d }
        L_0x000d:
            r7 = move-exception
            goto L_0x0116
        L_0x0010:
            if (r9 == 0) goto L_0x010e
            int r1 = r9.length     // Catch:{ all -> 0x000d }
            if (r1 != 0) goto L_0x0017
            goto L_0x010e
        L_0x0017:
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x000d }
            java.util.List r9 = java.util.Arrays.asList(r9)     // Catch:{ all -> 0x000d }
            r1.<init>(r9)     // Catch:{ all -> 0x000d }
            java.util.Iterator r9 = r1.iterator()     // Catch:{ all -> 0x000d }
        L_0x0024:
            boolean r2 = r9.hasNext()     // Catch:{ all -> 0x000d }
            r3 = 0
            if (r2 == 0) goto L_0x0055
            java.lang.Object r2 = r9.next()     // Catch:{ all -> 0x000d }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x000d }
            java.util.zip.ZipEntry r4 = r8.getEntry(r2)     // Catch:{ all -> 0x000d }
            if (r4 == 0) goto L_0x0039
            r9 = 1
            goto L_0x0056
        L_0x0039:
            java.lang.String r3 = "ZipFileUtil"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x000d }
            java.lang.String r5 = "ZipEntry["
            r4.<init>(r5)     // Catch:{ all -> 0x000d }
            r4.append(r2)     // Catch:{ all -> 0x000d }
            java.lang.String r2 = "] is not exist. "
            r4.append(r2)     // Catch:{ all -> 0x000d }
            r4.append(r7)     // Catch:{ all -> 0x000d }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x000d }
            com.alipay.mobile.quinox.log.Log.d(r3, r2)     // Catch:{ all -> 0x000d }
            goto L_0x0024
        L_0x0055:
            r9 = 0
        L_0x0056:
            if (r9 != 0) goto L_0x005a
            monitor-exit(r0)
            return r3
        L_0x005a:
            java.io.File r9 = new java.io.File     // Catch:{ all -> 0x000d }
            java.lang.String r2 = r7.getParent()     // Catch:{ all -> 0x000d }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x000d }
            r3.<init>()     // Catch:{ all -> 0x000d }
            java.lang.String r4 = r7.getName()     // Catch:{ all -> 0x000d }
            r3.append(r4)     // Catch:{ all -> 0x000d }
            java.lang.String r4 = ".tmp"
            r3.append(r4)     // Catch:{ all -> 0x000d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x000d }
            r9.<init>(r2, r3)     // Catch:{ all -> 0x000d }
            boolean r2 = r9.exists()     // Catch:{ all -> 0x000d }
            if (r2 == 0) goto L_0x008c
            boolean r2 = r9.delete()     // Catch:{ all -> 0x000d }
            if (r2 != 0) goto L_0x008c
            java.io.IOException r7 = new java.io.IOException     // Catch:{ all -> 0x000d }
            java.lang.String r8 = "Failed to delete pre temp file."
            r7.<init>(r8)     // Catch:{ all -> 0x000d }
            throw r7     // Catch:{ all -> 0x000d }
        L_0x008c:
            boolean r2 = r9.createNewFile()     // Catch:{ all -> 0x000d }
            if (r2 != 0) goto L_0x00ae
            java.io.IOException r8 = new java.io.IOException     // Catch:{ all -> 0x000d }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x000d }
            java.lang.String r2 = "Failed to create temp file. "
            r1.<init>(r2)     // Catch:{ all -> 0x000d }
            r1.append(r7)     // Catch:{ all -> 0x000d }
            java.lang.String r7 = " => "
            r1.append(r7)     // Catch:{ all -> 0x000d }
            r1.append(r9)     // Catch:{ all -> 0x000d }
            java.lang.String r7 = r1.toString()     // Catch:{ all -> 0x000d }
            r8.<init>(r7)     // Catch:{ all -> 0x000d }
            throw r8     // Catch:{ all -> 0x000d }
        L_0x00ae:
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ all -> 0x000d }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ all -> 0x000d }
            r3.<init>(r9)     // Catch:{ all -> 0x000d }
            r2.<init>(r3)     // Catch:{ all -> 0x000d }
            java.util.Enumeration r3 = r8.entries()     // Catch:{ all -> 0x0109 }
        L_0x00bc:
            boolean r4 = r3.hasMoreElements()     // Catch:{ all -> 0x0109 }
            if (r4 == 0) goto L_0x00f7
            java.lang.Object r4 = r3.nextElement()     // Catch:{ all -> 0x0109 }
            java.util.zip.ZipEntry r4 = (java.util.zip.ZipEntry) r4     // Catch:{ all -> 0x0109 }
            java.lang.String r5 = r4.getName()     // Catch:{ all -> 0x0109 }
            boolean r6 = pathSecurityCheck(r5)     // Catch:{ all -> 0x0109 }
            if (r6 != 0) goto L_0x00e3
            java.io.IOException r7 = new java.io.IOException     // Catch:{ all -> 0x0109 }
            java.lang.String r8 = "security check fail : "
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0109 }
            java.lang.String r8 = r8.concat(r9)     // Catch:{ all -> 0x0109 }
            r7.<init>(r8)     // Catch:{ all -> 0x0109 }
            throw r7     // Catch:{ all -> 0x0109 }
        L_0x00e3:
            boolean r5 = r1.contains(r5)     // Catch:{ all -> 0x0109 }
            if (r5 != 0) goto L_0x00bc
            r2.putNextEntry(r4)     // Catch:{ all -> 0x0109 }
            java.io.InputStream r4 = r8.getInputStream(r4)     // Catch:{ all -> 0x0109 }
            com.alipay.mobile.quinox.utils.StreamUtil.copyStreamWithoutClosing(r4, r2)     // Catch:{ all -> 0x0109 }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r4)     // Catch:{ all -> 0x0109 }
            goto L_0x00bc
        L_0x00f7:
            r2.flush()     // Catch:{ all -> 0x0109 }
            r8.close()     // Catch:{ all -> 0x0109 }
            r7.delete()     // Catch:{ all -> 0x0109 }
            boolean r7 = r9.renameTo(r7)     // Catch:{ all -> 0x0109 }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)     // Catch:{ all -> 0x000d }
            monitor-exit(r0)
            return r7
        L_0x0109:
            r7 = move-exception
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)     // Catch:{ all -> 0x000d }
            throw r7     // Catch:{ all -> 0x000d }
        L_0x010e:
            java.lang.IllegalArgumentException r7 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x000d }
            java.lang.String r8 = "archivePaths is empty."
            r7.<init>(r8)     // Catch:{ all -> 0x000d }
            throw r7     // Catch:{ all -> 0x000d }
        L_0x0116:
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.utils.ZipFileUtil.doDeleteEntry(java.io.File, java.util.zip.ZipFile, java.lang.String[]):boolean");
    }

    public static boolean pathSecurityCheck(String str) {
        return !str.contains("..") && !str.contains("/") && !str.contains("\\") && !str.contains("%");
    }
}

package defpackage;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* renamed from: ahf reason: default package */
/* compiled from: ZipUtil */
public final class ahf {
    public static final String a = null;
    private static final String b = File.separator;

    /* renamed from: ahf$a */
    /* compiled from: ZipUtil */
    public interface a {
        void onFinishProgress(long j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0065 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.io.File r13, java.io.File r14, defpackage.ahf.a r15) throws java.lang.Exception {
        /*
            r0 = 0
            if (r15 == 0) goto L_0x003b
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0035 }
            r2.<init>(r13)     // Catch:{ Exception -> 0x0035 }
            java.util.zip.CheckedInputStream r3 = new java.util.zip.CheckedInputStream     // Catch:{ Exception -> 0x0035 }
            java.util.zip.CRC32 r4 = new java.util.zip.CRC32     // Catch:{ Exception -> 0x0035 }
            r4.<init>()     // Catch:{ Exception -> 0x0035 }
            r3.<init>(r2, r4)     // Catch:{ Exception -> 0x0035 }
            java.util.zip.ZipInputStream r4 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x0035 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0035 }
            r5 = r0
        L_0x0019:
            java.util.zip.ZipEntry r7 = r4.getNextEntry()     // Catch:{ Exception -> 0x0033 }
            if (r7 == 0) goto L_0x0029
            long r7 = r7.getSize()     // Catch:{ Exception -> 0x0033 }
            r9 = 0
            long r5 = r5 + r7
            r4.closeEntry()     // Catch:{ Exception -> 0x0033 }
            goto L_0x0019
        L_0x0029:
            r4.close()     // Catch:{ Exception -> 0x0033 }
            r3.close()     // Catch:{ Exception -> 0x0033 }
            r2.close()     // Catch:{ Exception -> 0x0033 }
            goto L_0x003c
        L_0x0033:
            r2 = move-exception
            goto L_0x0037
        L_0x0035:
            r2 = move-exception
            r5 = r0
        L_0x0037:
            r2.printStackTrace()
            goto L_0x003c
        L_0x003b:
            r5 = r0
        L_0x003c:
            java.io.FileInputStream r2 = new java.io.FileInputStream
            r2.<init>(r13)
            java.util.zip.CheckedInputStream r3 = new java.util.zip.CheckedInputStream
            java.util.zip.CRC32 r4 = new java.util.zip.CRC32
            r4.<init>()
            r3.<init>(r2, r4)
            java.util.zip.ZipInputStream r4 = new java.util.zip.ZipInputStream
            r4.<init>(r3)
            r7 = r13
            r8 = r14
            r9 = r4
            r10 = r5
            r12 = r15
            a(r7, r8, r9, r10, r12)
            r4.close()
            r3.close()
            r2.close()
            int r13 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r13 > 0) goto L_0x006c
            if (r15 == 0) goto L_0x006c
            r13 = 100
            r15.onFinishProgress(r13)
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahf.a(java.io.File, java.io.File, ahf$a):void");
    }

    public static void a(String str, String str2) throws Exception {
        a(new File(str), new File(str2), (a) null);
    }

    public static void a(File file, String str, a aVar) throws Exception {
        a(file, new File(str), aVar);
    }

    private static void a(File file, File file2, ZipInputStream zipInputStream, long j, a aVar) throws Exception {
        boolean z = false;
        int i = 0;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                break;
            }
            String name = nextEntry.getName();
            if (TextUtils.isEmpty(name) || name.contains("../")) {
                z = true;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(file2.getPath());
                sb.append(File.separator);
                sb.append(name);
                File file3 = new File(sb.toString());
                a(file3);
                if (nextEntry.isDirectory()) {
                    file3.mkdirs();
                } else {
                    i += a(file3, zipInputStream, (long) i, j, aVar);
                }
                zipInputStream.closeEntry();
            }
        }
        z = true;
        if (z && file != null) {
            try {
                file.delete();
            } catch (Exception unused) {
            }
        }
    }

    private static void a(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            a(parentFile);
            parentFile.mkdir();
        }
    }

    private static int a(File file, ZipInputStream zipInputStream, long j, long j2, a aVar) throws Exception {
        a aVar2 = aVar;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] bArr = new byte[1024];
        ZipInputStream zipInputStream2 = zipInputStream;
        int i = 0;
        long j3 = 0;
        while (true) {
            int read = zipInputStream2.read(bArr, 0, 1024);
            if (read == -1) {
                break;
            }
            bufferedOutputStream.write(bArr, 0, read);
            i += read;
            if (j2 > 0 && aVar2 != null) {
                j3 = ((j + ((long) i)) * 100) / j2;
                if (j3 == 100) {
                    j3 = 99;
                }
                aVar2.onFinishProgress(j3);
            }
        }
        bufferedOutputStream.close();
        if (j3 == 99) {
            aVar2.onFinishProgress(100);
        }
        return i;
    }

    public static void a(List<File> list, File file, a aVar) throws Exception {
        if (list == null || list.size() <= 0) {
            throw new IllegalArgumentException("没有需要压缩的文件");
        }
        long j = 0;
        for (File b2 : list) {
            j += b(b2);
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(file), new CRC32()));
        int i = 0;
        for (File a2 : list) {
            i = (int) (((long) i) + a(a2, zipOutputStream, (String) "", j, aVar));
            if (aVar != null) {
                aVar.onFinishProgress(((long) (i * 100)) / j);
            }
        }
        zipOutputStream.flush();
        zipOutputStream.close();
    }

    private static long b(File file) {
        long j = 0;
        if (file.isFile()) {
            return 0 + file.length();
        }
        if (!file.isDirectory()) {
            return 0;
        }
        for (File b2 : file.listFiles()) {
            j += b(b2);
        }
        return j;
    }

    private static long a(File file, ZipOutputStream zipOutputStream, String str, long j, a aVar) throws Exception {
        if (file.isDirectory()) {
            return ((long) b(file, zipOutputStream, str, j, aVar)) + 0;
        }
        return ((long) a(file, zipOutputStream, str)) + 0;
    }

    private static int b(File file, ZipOutputStream zipOutputStream, String str, long j, a aVar) throws Exception {
        ZipOutputStream zipOutputStream2;
        String str2 = str;
        File[] listFiles = file.listFiles();
        if (listFiles.length <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(file.getName());
            sb.append(b);
            ZipEntry zipEntry = new ZipEntry(sb.toString());
            zipOutputStream2 = zipOutputStream;
            zipOutputStream2.putNextEntry(zipEntry);
            zipOutputStream2.closeEntry();
        } else {
            zipOutputStream2 = zipOutputStream;
        }
        int i = 0;
        for (File a2 : listFiles) {
            long j2 = (long) i;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(file.getName());
            sb2.append(b);
            i = (int) (j2 + a(a2, zipOutputStream2, sb2.toString(), j, aVar));
        }
        return i;
    }

    private static int a(File file, ZipOutputStream zipOutputStream, String str) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(file.getName());
        zipOutputStream.putNextEntry(new ZipEntry(sb.toString()));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bArr = new byte[1024];
        int i = 0;
        while (true) {
            int read = bufferedInputStream.read(bArr, 0, 1024);
            if (read != -1) {
                zipOutputStream.write(bArr, 0, read);
                i += read;
            } else {
                bufferedInputStream.close();
                zipOutputStream.closeEntry();
                return i;
            }
        }
    }

    public static void a(InputStream inputStream, String str) throws Exception {
        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, new CRC32());
        ZipInputStream zipInputStream = new ZipInputStream(checkedInputStream);
        a((File) null, new File(str), zipInputStream, 0, (a) null);
        zipInputStream.close();
        checkedInputStream.close();
    }
}

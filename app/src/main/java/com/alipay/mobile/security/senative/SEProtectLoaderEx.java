package com.alipay.mobile.security.senative;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class SEProtectLoaderEx {
    private static final String TAG = "SEProtect";
    private static Context mContext = null;
    private static String mVertion = "";
    private TraceLogger logger = LoggerFactory.f();

    class DelFileFilter implements FileFilter {
        String condition = "";

        public DelFileFilter(String str) {
            this.condition = str;
        }

        public boolean accept(File file) {
            return file.getName().startsWith(this.condition);
        }
    }

    public SEProtectLoaderEx(Context context) {
        mContext = context;
    }

    public boolean loadSo(String str, String str2) {
        mVertion = str2;
        return loadSo(str);
    }

    private boolean loadSo(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_BK");
        String sb2 = sb.toString();
        try {
            File aPSEFile = getAPSEFile();
            if (copyAPSElib(aPSEFile.toString(), sb2, str)) {
                StringBuilder sb3 = new StringBuilder("lib");
                sb3.append(str);
                sb3.append("_");
                sb3.append(mVertion);
                sb3.append(".so");
                String sb4 = sb3.toString();
                StringBuilder sb5 = new StringBuilder();
                sb5.append(sb2);
                sb5.append(File.separator);
                sb5.append(sb4);
                String sb6 = sb5.toString();
                StringBuilder sb7 = new StringBuilder();
                sb7.append(aPSEFile.toString());
                sb7.append(File.separator);
                sb7.append(sb6);
                File file = new File(sb7.toString());
                if (file.exists()) {
                    try {
                        System.load(file.toString());
                        return true;
                    } catch (UnsatisfiedLinkError e) {
                        this.logger.b((String) TAG, e.toString());
                        return false;
                    }
                } else {
                    this.logger.b((String) TAG, String.format(Locale.ENGLISH, "error can't find %1$s lib in plugins_lib", new Object[]{str}));
                    return false;
                }
            } else {
                this.logger.b((String) TAG, String.format(Locale.ENGLISH, "error copy %1$s lib fail", new Object[]{str}));
                return false;
            }
        } catch (FileNotFoundException e2) {
            this.logger.b((String) TAG, e2.toString());
            return false;
        }
    }

    private void deleteSoFiles(String str, String str2) {
        try {
            for (File deleteFile : new File(str).listFiles(new DelFileFilter(str2))) {
                deleteFile(deleteFile);
            }
        } catch (Exception e) {
            this.logger.b((String) TAG, e.toString());
        }
    }

    private void deleteFile(File file) {
        if (!file.exists()) {
            this.logger.b((String) TAG, (String) "所删除的文件不存在！\n");
        } else if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File deleteFile : listFiles) {
                deleteFile(deleteFile);
            }
            file.delete();
        }
    }

    private File getAPSEFile() throws FileNotFoundException {
        return mContext.getFilesDir();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00f2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean copyAPSElib(java.lang.String r9, java.lang.String r10, java.lang.String r11) {
        /*
            r8 = this;
            java.lang.String r0 = android.os.Build.CPU_ABI
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "lib"
            r1.<init>(r2)
            r1.append(r11)
            java.lang.String r11 = "_"
            r1.append(r11)
            java.lang.String r11 = mVertion
            r1.append(r11)
            java.lang.String r11 = ".so"
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            java.lang.String r1 = "x86"
            boolean r1 = r1.equals(r0)
            r2 = 0
            if (r1 == 0) goto L_0x0034
            java.lang.String r0 = "lib/x86/"
            java.lang.String r1 = java.lang.String.valueOf(r11)
            java.lang.String r0 = r0.concat(r1)
            goto L_0x0059
        L_0x0034:
            java.lang.String r1 = "armeabi"
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x0047
            java.lang.String r0 = "lib/armeabi/"
            java.lang.String r1 = java.lang.String.valueOf(r11)
            java.lang.String r0 = r0.concat(r1)
            goto L_0x0059
        L_0x0047:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = r8.logger
            java.lang.String r3 = "SEProtect"
            java.lang.String r4 = "apse is not support for this mode: "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r4.concat(r0)
            r1.b(r3, r0)
            r0 = r2
        L_0x0059:
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x00b1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b1 }
            r3.<init>()     // Catch:{ Exception -> 0x00b1 }
            r3.append(r9)     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r4 = java.io.File.separator     // Catch:{ Exception -> 0x00b1 }
            r3.append(r4)     // Catch:{ Exception -> 0x00b1 }
            r3.append(r10)     // Catch:{ Exception -> 0x00b1 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00b1 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x00b1 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x00af }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00af }
            r4.<init>()     // Catch:{ Exception -> 0x00af }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x00af }
            r4.append(r5)     // Catch:{ Exception -> 0x00af }
            java.lang.String r5 = java.io.File.separator     // Catch:{ Exception -> 0x00af }
            r4.append(r5)     // Catch:{ Exception -> 0x00af }
            r4.append(r11)     // Catch:{ Exception -> 0x00af }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00af }
            r3.<init>(r4)     // Catch:{ Exception -> 0x00af }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = r8.logger     // Catch:{ Exception -> 0x00aa }
            java.lang.String r4 = "SEProtect"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00aa }
            java.lang.String r6 = "libSE:"
            r5.<init>(r6)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x00aa }
            r5.append(r6)     // Catch:{ Exception -> 0x00aa }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00aa }
            r2.b(r4, r5)     // Catch:{ Exception -> 0x00aa }
            r2 = r3
            goto L_0x00bf
        L_0x00aa:
            r2 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
            goto L_0x00b4
        L_0x00af:
            r3 = move-exception
            goto L_0x00b4
        L_0x00b1:
            r1 = move-exception
            r3 = r1
            r1 = r2
        L_0x00b4:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r4 = r8.logger
            java.lang.String r5 = "SEProtect"
            java.lang.String r3 = r3.toString()
            r4.b(r5, r3)
        L_0x00bf:
            if (r2 == 0) goto L_0x00f2
            boolean r3 = r2.exists()
            if (r3 == 0) goto L_0x00e7
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r9 = r8.logger
            java.lang.String r10 = "SEProtect"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "file "
            r11.<init>(r0)
            java.lang.String r0 = r2.toString()
            r11.append(r0)
            java.lang.String r0 = " is exist"
            r11.append(r0)
            java.lang.String r11 = r11.toString()
            r9.b(r10, r11)
            r9 = 1
            return r9
        L_0x00e7:
            r8.deleteSoFiles(r9, r10)
            r1.mkdirs()
            boolean r9 = r8.saveApseSo(r9, r0, r11, r2)
            goto L_0x00f3
        L_0x00f2:
            r9 = 0
        L_0x00f3:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.senative.SEProtectLoaderEx.copyAPSElib(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    private boolean saveApseSo(String str, String str2, String str3, File file) {
        this.logger.b((String) TAG, "apklibPath = ".concat(String.valueOf(str2)));
        InputStream resourceAsStream = SEProtectLoaderEx.class.getClassLoader().getResourceAsStream(str2);
        if (resourceAsStream != null) {
            if (str == null) {
                this.logger.b((String) TAG, (String) "apse file cann't be null...");
            }
            boolean saveFile = saveFile(resourceAsStream, file);
            try {
                resourceAsStream.close();
                return saveFile;
            } catch (IOException e) {
                this.logger.b((String) TAG, e.toString());
                return saveFile;
            }
        } else {
            TraceLogger traceLogger = this.logger;
            StringBuilder sb = new StringBuilder("error: can't find ");
            sb.append(str3);
            sb.append(" in apk");
            traceLogger.b((String) TAG, sb.toString());
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x0080 A[SYNTHETIC, Splitter:B:46:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0085 A[Catch:{ IOException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x008a A[Catch:{ IOException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x009e A[SYNTHETIC, Splitter:B:57:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00a6 A[Catch:{ IOException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00ab A[Catch:{ IOException -> 0x00a2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00be A[SYNTHETIC, Splitter:B:68:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00c6 A[Catch:{ IOException -> 0x00c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00cb A[Catch:{ IOException -> 0x00c2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:54:0x0091=Splitter:B:54:0x0091, B:43:0x0073=Splitter:B:43:0x0073} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean saveFile(java.io.InputStream r7, java.io.File r8) {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            boolean r2 = r8.exists()     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0070, all -> 0x006c }
            if (r2 == 0) goto L_0x000b
            r8.delete()     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0070, all -> 0x006c }
        L_0x000b:
            r8.createNewFile()     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0070, all -> 0x006c }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0070, all -> 0x006c }
            r2.<init>(r7)     // Catch:{ FileNotFoundException -> 0x008e, IOException -> 0x0070, all -> 0x006c }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0069, IOException -> 0x0066, all -> 0x0063 }
            r7.<init>(r8)     // Catch:{ FileNotFoundException -> 0x0069, IOException -> 0x0066, all -> 0x0063 }
            java.io.BufferedOutputStream r8 = new java.io.BufferedOutputStream     // Catch:{ FileNotFoundException -> 0x005d, IOException -> 0x0057, all -> 0x0050 }
            r8.<init>(r7)     // Catch:{ FileNotFoundException -> 0x005d, IOException -> 0x0057, all -> 0x0050 }
            r0 = 512(0x200, float:7.175E-43)
            byte[] r0 = new byte[r0]     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x0045, all -> 0x003f }
        L_0x0021:
            int r3 = r2.read(r0)     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x0045, all -> 0x003f }
            r4 = -1
            if (r3 == r4) goto L_0x002c
            r8.write(r0, r1, r3)     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x0045, all -> 0x003f }
            goto L_0x0021
        L_0x002c:
            r8.flush()     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x0045, all -> 0x003f }
            r7.flush()     // Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x0045, all -> 0x003f }
            r0 = 1
            r7.close()     // Catch:{ IOException -> 0x00a2 }
            r2.close()     // Catch:{ IOException -> 0x00a2 }
            r8.close()     // Catch:{ IOException -> 0x00a2 }
            r1 = 1
            goto L_0x00ba
        L_0x003f:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x00bc
        L_0x0045:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0073
        L_0x004a:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0091
        L_0x0050:
            r8 = move-exception
            r5 = r0
            r0 = r7
            r7 = r8
            r8 = r5
            goto L_0x00bc
        L_0x0057:
            r8 = move-exception
            r5 = r0
            r0 = r7
            r7 = r8
            r8 = r5
            goto L_0x0073
        L_0x005d:
            r8 = move-exception
            r5 = r0
            r0 = r7
            r7 = r8
            r8 = r5
            goto L_0x0091
        L_0x0063:
            r7 = move-exception
            r8 = r0
            goto L_0x00bc
        L_0x0066:
            r7 = move-exception
            r8 = r0
            goto L_0x0073
        L_0x0069:
            r7 = move-exception
            r8 = r0
            goto L_0x0091
        L_0x006c:
            r7 = move-exception
            r8 = r0
            r2 = r8
            goto L_0x00bc
        L_0x0070:
            r7 = move-exception
            r8 = r0
            r2 = r8
        L_0x0073:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = r6.logger     // Catch:{ all -> 0x00bb }
            java.lang.String r4 = "SEProtect"
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00bb }
            r3.b(r4, r7)     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x0083
            r0.close()     // Catch:{ IOException -> 0x00a2 }
        L_0x0083:
            if (r2 == 0) goto L_0x0088
            r2.close()     // Catch:{ IOException -> 0x00a2 }
        L_0x0088:
            if (r8 == 0) goto L_0x00ba
            r8.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00ba
        L_0x008e:
            r7 = move-exception
            r8 = r0
            r2 = r8
        L_0x0091:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = r6.logger     // Catch:{ all -> 0x00bb }
            java.lang.String r4 = "SEProtect"
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x00bb }
            r3.b(r4, r7)     // Catch:{ all -> 0x00bb }
            if (r0 == 0) goto L_0x00a4
            r0.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00a4
        L_0x00a2:
            r7 = move-exception
            goto L_0x00af
        L_0x00a4:
            if (r2 == 0) goto L_0x00a9
            r2.close()     // Catch:{ IOException -> 0x00a2 }
        L_0x00a9:
            if (r8 == 0) goto L_0x00ba
            r8.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00ba
        L_0x00af:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r8 = r6.logger
            java.lang.String r0 = "SEProtect"
            java.lang.String r7 = r7.toString()
            r8.b(r0, r7)
        L_0x00ba:
            return r1
        L_0x00bb:
            r7 = move-exception
        L_0x00bc:
            if (r0 == 0) goto L_0x00c4
            r0.close()     // Catch:{ IOException -> 0x00c2 }
            goto L_0x00c4
        L_0x00c2:
            r8 = move-exception
            goto L_0x00cf
        L_0x00c4:
            if (r2 == 0) goto L_0x00c9
            r2.close()     // Catch:{ IOException -> 0x00c2 }
        L_0x00c9:
            if (r8 == 0) goto L_0x00da
            r8.close()     // Catch:{ IOException -> 0x00c2 }
            goto L_0x00da
        L_0x00cf:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = r6.logger
            java.lang.String r1 = "SEProtect"
            java.lang.String r8 = r8.toString()
            r0.b(r1, r8)
        L_0x00da:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.senative.SEProtectLoaderEx.saveFile(java.io.InputStream, java.io.File):boolean");
    }
}

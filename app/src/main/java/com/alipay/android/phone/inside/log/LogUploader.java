package com.alipay.android.phone.inside.log;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.log.util.DateUtil;
import com.alipay.android.phone.inside.log.util.net.NetworkHandler;
import com.alipay.android.phone.inside.log.util.storage.EncryptFileUtils;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class LogUploader {
    private static File a;
    private String b;

    public LogUploader(Context context) {
        if (a != null) {
            LoggerFactory.f().b((String) "inside", (String) "LogUploader::initializeDir > mLogDir initialized");
        } else {
            try {
                String absolutePath = context.getFilesDir().getAbsolutePath();
                StringBuilder sb = new StringBuilder();
                sb.append(absolutePath);
                sb.append(File.separator);
                sb.append("inside_logs");
                String sb2 = sb.toString();
                LoggerFactory.f().b((String) "inside", "LogUploader::initializeDir > log dir:".concat(String.valueOf(sb2)));
                File file = new File(sb2);
                a = file;
                if (!file.exists()) {
                    a.mkdirs();
                }
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        if (a != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a.getAbsolutePath());
            sb3.append(File.separator);
            sb3.append(System.currentTimeMillis());
            sb3.append("_monitor");
            this.b = sb3.toString();
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb4 = new StringBuilder("LogUploader::initialize > mLogPath:");
        sb4.append(this.b);
        f.b((String) "inside", sb4.toString());
    }

    public final void a() {
        LoggerFactory.f().b((String) "inside", (String) "LogUploader::uploadLocalLog");
        if (a != null) {
            File[] listFiles = a.listFiles();
            Arrays.sort(listFiles, new Comparator<File>() {
                public /* synthetic */ int compare(Object obj, Object obj2) {
                    return ((File) obj2).getName().compareTo(((File) obj).getName());
                }
            });
            int i = 10;
            if (listFiles.length <= 10) {
                i = listFiles.length;
            }
            LoggerFactory.f().b((String) "inside", "LogUploader::uploadLocalLog > size:".concat(String.valueOf(i)));
            for (int i2 = 0; i2 < i; i2++) {
                File file = listFiles[i2];
                if (file != null && file.exists() && file.isFile()) {
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder("LogUploader::uploadLocalLog > index:");
                    sb.append(i2);
                    sb.append(", name:");
                    sb.append(file.getName());
                    f.b((String) "inside", sb.toString());
                    if (c(b(EncryptFileUtils.a(file)))) {
                        file.delete();
                    }
                }
            }
        }
    }

    public final boolean a(String str) {
        boolean c = c(b(str));
        if (!c) {
            EncryptFileUtils.a(this.b, str);
        }
        return c;
    }

    private static String b(String str) {
        LoggerFactory.f().b((String) "inside", "LogUploader::formatRecord > orignal:".concat(String.valueOf(str)));
        String replace = str.replace("{{SUBMIT_TIME}}", DateUtil.a());
        LoggerFactory.f().b((String) "inside", "LogUploader::formatRecord > format:".concat(String.valueOf(replace)));
        return replace;
    }

    private boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        boolean z = false;
        try {
            a(str, 0);
            z = true;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        LoggerFactory.f().b((String) "inside", "LogUploader::sendLogRequest > success:".concat(String.valueOf(z)));
        return z;
    }

    private byte[] a(String str, int i) throws Exception {
        InsideLogPack insideLogPack = new InsideLogPack();
        byte[] a2 = insideLogPack.a(str.getBytes("utf-8"));
        new NetworkHandler();
        try {
            return insideLogPack.b(NetworkHandler.a("https://mdap.alipay.com/loggw/sdkLogUpload.do", a2, null, null));
        } catch (PublicKeyUpdateException e) {
            if (i > 0) {
                throw e;
            }
            a(str, 1);
            return null;
        }
    }
}

package com.alipay.android.phone.inside.log.util.storage;

import android.content.Context;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.biz.ContextManager;
import com.alipay.android.phone.inside.log.util.sec.DesCBC;
import com.alipay.android.phone.inside.log.util.sec.EncryptUtil;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.io.File;

public class EncryptFileUtils {
    public static boolean a(String str, String str2) {
        String str3;
        Context context = ContextManager.a().getContext();
        if (str2 != null) {
            try {
                str3 = DesCBC.a(EncryptUtil.a(context), str2);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) ReportManager.LOG_PATH, (String) "LogFileEncryptEx", th);
                return false;
            }
        } else {
            str3 = null;
        }
        return FileUtils.a(str, str3);
    }

    public static String a(File file) {
        String a = FileUtils.a(file);
        Context context = ContextManager.a().getContext();
        if (a != null) {
            try {
                return DesCBC.b(EncryptUtil.a(context), a);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) ReportManager.LOG_PATH, (String) "LogFileDecryptEx", th);
            }
        }
        return null;
    }
}

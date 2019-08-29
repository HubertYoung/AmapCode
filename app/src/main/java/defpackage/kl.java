package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.io.File;

/* renamed from: kl reason: default package */
/* compiled from: ApkUtil */
public final class kl {
    public static boolean a(File file) {
        Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
        if (applicationContext == null) {
            return false;
        }
        PackageInfo packageArchiveInfo = applicationContext.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 64);
        String str = "";
        if (!(packageArchiveInfo == null || packageArchiveInfo.signatures == null || packageArchiveInfo.signatures.length <= 0)) {
            str = packageArchiveInfo.signatures[0].toCharsString();
        }
        return "c9aafe76be3a0e842b7172fa4643b33d".equals(agy.a(str));
    }

    public static boolean a(Activity activity, File file) {
        if (activity == null || file == null) {
            return false;
        }
        try {
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setAction("android.intent.action.VIEW");
            if (VERSION.SDK_INT >= 24) {
                intent.setFlags(1);
                intent.setDataAndType(FileProvider.getUriForFile(activity, FileUtil.FILE_PROVIDER, file), "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            String printStackTraceString = DebugLog.getPrintStackTraceString(e);
            if (!TextUtils.isEmpty(printStackTraceString)) {
                AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0006", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, printStackTraceString);
                cjy.a(ALCLogLevel.P6, (String) AMapLog.GROUP_COMMON, (String) "D1", (String) "P0006", (String) "E101", printStackTraceString);
                AMapLog.logNormalNative(AMapLog.GROUP_COMMON, "P0006", "E201", printStackTraceString);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }
}

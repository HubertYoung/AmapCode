package defpackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import com.amap.bundle.blutils.FileUtil;
import java.io.File;

/* renamed from: kh reason: default package */
/* compiled from: FileProviderCompatUtil */
public final class kh {
    public static void a(Context context, Intent intent, String str, File file) {
        Uri uri;
        if (VERSION.SDK_INT >= 24) {
            if (VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(context, FileUtil.FILE_PROVIDER, file);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, str);
            intent.addFlags(1);
            return;
        }
        intent.setDataAndType(Uri.fromFile(file), str);
    }
}

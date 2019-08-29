package defpackage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.LotusPoolService;
import org.jetbrains.annotations.NotNull;

/* renamed from: wr reason: default package */
/* compiled from: LotusPool */
public class wr {
    private static final String a = "wr";

    public static void a(@NotNull Context context, int i) {
        if (context == null || i <= 0 || i > 4) {
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, a, "startService", "参数错误,launchType:".concat(String.valueOf(i)));
            return;
        }
        try {
            Intent intent = new Intent(context, LotusPoolService.class);
            intent.putExtra("launch_type", i);
            context.startService(intent);
        } catch (Throwable th) {
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, a, "startService", Log.getStackTraceString(th));
        }
    }
}

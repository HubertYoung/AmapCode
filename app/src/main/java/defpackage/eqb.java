package defpackage;

import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;

/* renamed from: eqb reason: default package */
/* compiled from: DebugVcsFileUtils */
public final class eqb {
    public static final String a;
    public static final String b;
    public static boolean c = (!new File(b).exists());

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getAppSDCardFileDir());
        sb.append("/VCS/lib");
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtil.getAppSDCardFileDir());
        sb2.append("/flag.txt");
        b = sb2.toString();
    }

    public static boolean a(String str) {
        String str2 = c ? "A" : DiskFormatter.B;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("/data/data/");
        sb2.append(AMapAppGlobal.getApplication().getPackageName());
        sb2.append("/vcslibs");
        sb.append(sb2.toString());
        sb.append(str2);
        sb.append("/lib");
        sb.append(str);
        sb.append(".so");
        String sb3 = sb.toString();
        AMapLog.d(RPCDataItems.SWITCH_TAG_LOG, "loadSo ".concat(String.valueOf(sb3)));
        if (!new File(sb3).exists()) {
            return false;
        }
        System.load(sb3);
        AMapLog.d(RPCDataItems.SWITCH_TAG_LOG, "loadSo Sucess: ".concat(String.valueOf(sb3)));
        ToastHelper.showToast(sb3);
        return true;
    }
}

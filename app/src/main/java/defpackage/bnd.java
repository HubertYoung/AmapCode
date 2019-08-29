package defpackage;

import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

/* renamed from: bnd reason: default package */
/* compiled from: UploadCrashLimit */
public final class bnd {
    private bmt a;

    public bnd(bmt bmt) {
        this.a = bmt;
    }

    public final void a(List<File> list) {
        for (File next : list) {
            if (a(next)) {
                bmu.a(next);
            }
        }
    }

    private boolean a(File file) {
        int a2 = bnc.a(file);
        HashMap hashMap = new HashMap();
        hashMap.put(NewHtcHomeBadger.COUNT, String.valueOf(a2));
        hashMap.put("packageName", CrashLogUtil.getApplication().getPackageName());
        String jSONObject = new JSONObject(hashMap).toString();
        File file2 = new File(file, bnc.a());
        boolean z = false;
        try {
            String a3 = this.a.a(jSONObject, file2);
            if (TextUtils.isEmpty(a3)) {
                return false;
            }
            JSONObject jSONObject2 = new JSONObject(a3);
            if (jSONObject2.optInt("code") == 1 && "success".equals(jSONObject2.optString("message"))) {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

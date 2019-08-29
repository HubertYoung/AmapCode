package defpackage;

import android.app.Dialog;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: dxk reason: default package */
/* compiled from: BusNaviDetailDialogHolder */
public final class dxk {
    public Map<String, Dialog> a = new LinkedHashMap();

    public final void a(String str) {
        Dialog dialog = this.a.get(str);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            this.a.remove(str);
        }
    }
}

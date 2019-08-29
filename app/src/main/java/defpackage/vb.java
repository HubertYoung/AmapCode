package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: vb reason: default package */
/* compiled from: EyrieABTest */
public class vb implements vg {
    private List<vf> a = new CopyOnWriteArrayList();

    public final void a(vf vfVar) {
        this.a.add(vfVar);
    }

    public final String a() {
        StringBuilder sb = new StringBuilder();
        vf[] vfVarArr = (vf[]) this.a.toArray(new vf[0]);
        int length = vfVarArr.length;
        for (int i = 0; i < length; i++) {
            String b = vfVarArr[i].b();
            if (!TextUtils.isEmpty(b)) {
                sb.append(b);
                if (i != length - 1) {
                    sb.append(MergeUtil.SEPARATOR_KV);
                }
            }
        }
        return sb.toString();
    }
}

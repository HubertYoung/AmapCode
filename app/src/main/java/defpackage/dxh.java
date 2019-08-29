package defpackage;

import android.util.SparseBooleanArray;
import com.alipay.mobile.h5container.api.H5PageData;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;

/* renamed from: dxh reason: default package */
/* compiled from: UgcAccessManager */
public final class dxh extends dxc<dxb> {
    private SparseBooleanArray a = new SparseBooleanArray();

    /* access modifiers changed from: protected */
    public final /* synthetic */ void b(a aVar, Object obj) {
        dxb dxb = (dxb) obj;
        ebb.a();
        if (!ebb.b()) {
            if (dwu.k() != a.a().b) {
                dwu.i();
            }
            if (this.a.get(dxb.d) || dwu.g() >= 3) {
                return;
            }
        }
        bdp bdp = (bdp) a.a.a(bdp.class);
        if (bdp != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("source", 2);
            pageBundle.putString(H5PageData.KEY_UC_START, dxb.a);
            pageBundle.putString("end", dxb.b);
            pageBundle.putString("bsid", dxb.c);
            pageBundle.putInt("source", 2);
            pageBundle.putInt("title_no", 1);
            pageBundle.putInt("project_no", dxb.d);
            pageBundle.putString("mainDes", dxb.e);
            this.a.put(dxb.d, true);
            aVar.a(bdp.a().a(), pageBundle, 1);
        }
    }

    public final void a(ResultType resultType) {
        if (resultType == ResultType.CANCEL) {
            dwu.h();
            dwu.j();
        }
    }
}

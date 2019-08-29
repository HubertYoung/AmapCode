package defpackage;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.uc.webview.export.internal.setup.UCMPackageInfo;

/* renamed from: eam reason: default package */
/* compiled from: DisclaimerUtil */
public final class eam {
    ConfirmDlg a = null;
    b b;

    /* renamed from: eam$a */
    /* compiled from: DisclaimerUtil */
    class a implements OnClickListener {
        private String b;

        public a(String str) {
            this.b = str;
        }

        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.cancel) {
                if (eam.this.a != null && eam.this.a.isShowing()) {
                    eam.this.a.dismiss();
                    eam.this.a = null;
                }
            } else if (id == R.id.confirm && eam.this.b != null) {
                eam.this.b.a();
                edr.b(this.b, true);
            }
        }
    }

    /* renamed from: eam$b */
    /* compiled from: DisclaimerUtil */
    public interface b {
        void a();
    }

    private eam(b bVar) {
        this.b = bVar;
    }

    public static eam a(b bVar) {
        return new eam(bVar);
    }

    public final void a(String str, boolean z) {
        boolean z2;
        if (TextUtils.equals("agree_onfoot_declare", str) || TextUtils.equals("agree_ondest_declare", str)) {
            z2 = edr.a(str, false);
        } else {
            z2 = true;
        }
        if (!z2 || this.b == null) {
            eko.a((int) UCMPackageInfo.expectCreateDirFile2P);
            a aVar = new a(str);
            Activity activity = null;
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                activity = pageContext.getActivity();
            }
            if (activity == null) {
                activity = AMapAppGlobal.getTopActivity();
            }
            this.a = new ConfirmDlg(activity, aVar, z ? R.layout.ondest_declare : R.layout.onfoot_declare);
            this.a.show();
            return;
        }
        this.b.a();
    }
}

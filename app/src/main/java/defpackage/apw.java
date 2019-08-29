package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.minimap.R;
import com.tencent.connect.common.Constants;

/* renamed from: apw reason: default package */
/* compiled from: BackSchemePileHandler */
public final class apw {
    dlg a;
    private bid b;

    public apw(bid bid) {
        this.b = bid;
    }

    public final boolean a(PageBundle pageBundle) {
        if (pageBundle == null || !pageBundle.containsKey(Constants.KEY_ACTION) || !"actiono_back_scheme".equals(pageBundle.getString(Constants.KEY_ACTION))) {
            return false;
        }
        dlg dlg = (dlg) pageBundle.getObject("key_back_scheme_param");
        if (dlg != null && dlg.a) {
            this.a = dlg;
        }
        return true;
    }

    public final boolean a() {
        if (this.a == null || !this.a.a || b() == null) {
            return false;
        }
        Activity b2 = b();
        if (!(this.a == null || !this.a.a || b2 == null)) {
            Builder builder = new Builder(b2);
            Resources resources = b2.getResources();
            builder.setNegativeButton((CharSequence) resources.getString(R.string.back_to, new Object[]{this.a.c}), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    nodeAlertDialogPage.finish();
                    Intent a2 = apw.this.a.a();
                    if (a2 != null) {
                        try {
                            apw.this.b().startActivity(a2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    apw.this.a = null;
                }
            });
            builder.setPositiveButton((CharSequence) resources.getString(R.string.stay_at_amap), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    apw.this.a = null;
                    nodeAlertDialogPage.finish();
                }
            });
            builder.setTitle((CharSequence) resources.getString(R.string.be_sure_where_to_back));
            this.b.startAlertDialogPage(builder);
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final Activity b() {
        if (this.b == null) {
            return null;
        }
        return this.b.getActivity();
    }
}

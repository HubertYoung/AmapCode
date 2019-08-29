package defpackage;

import android.content.Intent;
import android.os.Handler;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.adapter.PoiSelectAdapter;
import com.autonavi.map.search.common.PoiSearcherDialogHelper$2;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.CustomDialog;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.util.ArrayList;

/* renamed from: bvs reason: default package */
/* compiled from: PoiSearcherCallbackImpl */
public final class bvs implements bvp {
    public final void a() {
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            a a = new a(DoNotUseTool.getContext()).a(R.string.search_offline_first_switch_dialog_text).a(R.string.search_offline_first_switch_dialog_btn, (a) new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            });
            a.c = new a() {
                public final void onClick(AlertView alertView, int i) {
                    pageContext.dismissViewLayer(alertView);
                }
            };
            ccf.a(pageContext, a.a(true).a());
        }
    }

    public final void a(String str) {
        ToastHelper.showToast(str);
    }

    public final void b(String str) {
        ToastHelper.showLongToast(str);
    }

    public final void a(int i, int i2) {
        if (DoNotUseTool.getActivity() != null) {
            new CustomDialog(DoNotUseTool.getActivity()).setDlgTitle(i).setMsg(i2).setPositiveButton(R.string.alert_button_confirm, (OnClickListener) null).show();
        }
    }

    public final ListDialog a(ArrayList<String> arrayList, String str) {
        if (DoNotUseTool.getActivity() == null) {
            return null;
        }
        ListDialog listDialog = new ListDialog(DoNotUseTool.getActivity());
        listDialog.setDlgTitle(str);
        listDialog.setAdapter(new ArrayAdapter(AMapAppGlobal.getApplication().getApplicationContext(), R.layout.list_dialog_item_1, arrayList));
        listDialog.show();
        return listDialog;
    }

    public final void a(ArrayList<POI> arrayList, String str, Handler handler, int i, int i2, InfoliteResult infoliteResult) {
        if (DoNotUseTool.getActivity() != null) {
            bwy bwy = new bwy(DoNotUseTool.getActivity(), infoliteResult, i2);
            bwy.d = new PoiSelectAdapter(bwy.a, arrayList);
            bwy.c.setDlgTitle(str);
            bwy.c.setAdapter(bwy.d);
            bwy.c.setOnItemClickListener(new PoiSearcherDialogHelper$2(bwy, i, handler));
            bwy.c.updatePullList(bwy.b.mWrapper.pagenum, bcy.m(bwy.b));
            bwy.c.show();
        }
    }

    public final void a(Intent intent) {
        DoNotUseTool.startScheme(intent);
    }

    public final void a(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(cls, pageBundle);
        }
    }

    public final void b(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            bby.a(pageContext, cls, pageBundle);
        }
    }
}

package defpackage;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;

/* renamed from: aav reason: default package */
/* compiled from: LoadingDialogFactory */
public final class aav {
    public static CompatDialog a(final AosRequest aosRequest, String str) {
        final ProgressDlg progressDlg = new ProgressDlg(AMapAppGlobal.getTopActivity(), str);
        progressDlg.setCanceledOnTouchOutside(false);
        progressDlg.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aosRequest != null && !aosRequest.isCanceled()) {
                    in.a().a(aosRequest);
                }
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });
        if (progressDlg.getLoadingView() != null) {
            progressDlg.getLoadingView().setOnCloseClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (aosRequest != null && !aosRequest.isCanceled()) {
                        in.a().a(aosRequest);
                    }
                    progressDlg.dismiss();
                }
            });
        }
        return progressDlg;
    }
}

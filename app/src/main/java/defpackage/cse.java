package defpackage;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;

/* renamed from: cse reason: default package */
/* compiled from: TrafficDeclarManager */
public final class cse {
    public static WeakReference<Dialog> a;

    /* renamed from: cse$a */
    /* compiled from: TrafficDeclarManager */
    public interface a {
        void a();

        void b();
    }

    public static void a(Context context, final a aVar) {
        final Dialog dialog = new Dialog(context, R.style.custom_declare_dlg);
        a = new WeakReference<>(dialog);
        try {
            dialog.show();
        } catch (Throwable th) {
            DebugLog.error(th);
        }
        euk.a(dialog, 0);
        View inflate = LayoutInflater.from(context).inflate(R.layout.traffic_report_declar, null, false);
        ((TitleBar) inflate.findViewById(R.id.titlebar)).setWidgetVisibility(1, 4);
        dialog.setContentView(inflate);
        dialog.findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (aVar != null) {
                    aVar.a();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aVar != null) {
                    aVar.b();
                }
            }
        });
    }
}

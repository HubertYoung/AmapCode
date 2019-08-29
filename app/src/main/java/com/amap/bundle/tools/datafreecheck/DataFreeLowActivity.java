package com.amap.bundle.tools.datafreecheck;

import android.app.Activity;
import android.os.Bundle;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"DM_EXIT"})
public class DataFreeLowActivity extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mi miVar = new mi(this);
        miVar.a(R.string.warn_data_space_low);
        miVar.a(R.string.alert_button_confirm, new a() {
            public final void a(mi miVar) {
                miVar.a.dismiss();
                System.exit(0);
            }
        });
        miVar.b(R.string.cancel, new a() {
            public final void a(mi miVar) {
                miVar.a.dismiss();
                System.exit(0);
            }
        });
        miVar.c = false;
        miVar.a();
        try {
            miVar.b();
        } catch (Throwable unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(getResources().getString(R.string.init_error));
            sb.append("\n");
            sb.append(getString(R.string.warn_data_space_low));
            ToastHelper.showLongToast(sb.toString());
        }
    }
}

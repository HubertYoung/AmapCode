package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.Router;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;

@Router({"amaphome"})
/* renamed from: ano reason: default package */
/* compiled from: AmaphomeRouter */
public class ano extends esk {
    public boolean start(ese ese) {
        String str = ese.a.getPathSegments().get(0);
        if (TextUtils.equals(str, ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW)) {
            StatusBarManager.d().a(FeatureType.TYPE_TAXI, "您打的飞机到了", new OnClickListener() {
                public final void onClick(View view) {
                    ToastHelper.showLongToast("人家用小拳拳捶你胸口");
                }
            });
            return true;
        } else if (!TextUtils.equals(str, DataflowMonitorModel.METHOD_NAME_CLOSE)) {
            return false;
        } else {
            StatusBarManager.d().a(FeatureType.TYPE_TAXI);
            return true;
        }
    }
}

package com.amap.bundle.drive.offline;

import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import com.amap.bundle.drivecommon.model.ICarRouteResult;
import com.autonavi.jni.ae.route.observer.RouteObserver;
import com.autonavi.map.widget.ProgressDlg;

public class OfflineNaviQueryMgr {
    private static final String e = "OfflineNaviQueryMgr";
    public RouteObserver a;
    public boolean b = false;
    public ProgressDlg c;
    public OnDismissListener d;
    private Context f;

    public enum EnumNaviResponseType {
        SUCCESS,
        FAIL,
        ERROR,
        FROM_TO_ERROR,
        NOENGINE,
        EXISTDATA,
        NODATA,
        NEEDREBOOT
    }

    public interface a {
        void a(EnumNaviResponseType enumNaviResponseType, ICarRouteResult iCarRouteResult, int i);
    }

    public OfflineNaviQueryMgr(Context context) {
        this.f = context;
    }

    public final void a() {
        if (this.c != null) {
            this.c.dismiss();
            this.c = null;
        }
    }
}

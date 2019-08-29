package com.autonavi.operation.bundle;

import android.app.Activity;
import android.content.Context;
import com.amap.bundle.blutils.platform.ShortCutUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.modules.ModuleConvoy;
import com.autonavi.minimap.ajx3.widget.view.timepicker.TimePickerView;
import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService;

public class OperationVApp extends esh {
    private boolean a = false;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleConvoy.class);
        Ajx.getInstance().registerView("datepicker", TimePickerView.class);
        this.a = hasPermission();
        ato ato = (ato) a.a.a(ato.class);
        if (ato != null) {
            AMapPageUtil.getAppContext();
            ato.b().a(new a() {
                private ant c;
                private IAccountService d = ((IAccountService) a.a.a(IAccountService.class));

                {
                    if (this.d != null) {
                        this.c = this.d.e();
                    }
                }

                public final boolean a() {
                    return this.d != null && this.d.a();
                }
            });
        }
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        ahm.a(new Runnable() {
            public final void run() {
                OperationVApp.a(DoNotUseTool.getActivity());
                bim.aa().z();
                OperationVApp.a();
            }
        });
        b();
        con con = (con) ank.a(con.class);
        if (con != null && !con.a()) {
            con.a((Context) AMapAppGlobal.getApplication());
            con.b(AMapAppGlobal.getApplication());
        }
        brn brn = (brn) ank.a(brn.class);
        if (bim.aa().k((String) "104") && brn != null) {
            brn.b();
        }
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            mapView.a(avv.a());
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.a) {
            b();
        }
    }

    private static void b() {
        IMsgboxService iMsgboxService = (IMsgboxService) a.a.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.reset();
        }
    }

    static /* synthetic */ void a(Activity activity) {
        String string = activity.getString(R.string.triphelper_name);
        if (ShortCutUtil.hasShortCutCompat(activity, string)) {
            ShortCutUtil.deleteShortCut(string, activity);
        }
    }

    static /* synthetic */ void a() {
        IMsgboxService iMsgboxService = (IMsgboxService) ank.a(IMsgboxService.class);
        if (iMsgboxService != null) {
            iMsgboxService.notifyOfflineMapInformed();
        }
    }
}

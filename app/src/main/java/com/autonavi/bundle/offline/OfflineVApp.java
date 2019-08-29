package com.autonavi.bundle.offline;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Process;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.offline.ajx.ModuleJsOfflineAuiService;
import com.autonavi.bundle.offline.ajx.ModuleJsOfflineAutoService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import org.json.JSONObject;

public class OfflineVApp extends esh {
    private BroadcastReceiver a;
    private boolean b = false;
    /* access modifiers changed from: private */
    public int c = -1;
    private boolean d = false;
    private a e = new a() {
        public final void a(JSONObject jSONObject) {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                Application application = AMapAppGlobal.getApplication();
                String str = "";
                ls lsVar = lt.a().c;
                if (lsVar.l != null) {
                    str = lsVar.l;
                }
                String str2 = "";
                ls lsVar2 = lt.a().c;
                if (lsVar2.p != null) {
                    str2 = lsVar2.p;
                }
                String str3 = "";
                ls lsVar3 = lt.a().c;
                if (lsVar3.q != null) {
                    str3 = lsVar3.q;
                }
                String str4 = "";
                ls lsVar4 = lt.a().c;
                if (lsVar4.o != null) {
                    str4 = lsVar4.o;
                }
                String str5 = "";
                ls lsVar5 = lt.a().c;
                if (lsVar5.r != null) {
                    str5 = lsVar5.r;
                }
                iOfflineManager.putOffLatestVerByAppInit(application, str, str2, str3, str4, str5);
            }
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleJsOfflineAuiService.class, ModuleJsOfflineAutoService.class);
        if (hasPermission()) {
            this.d = true;
            this.a = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) AMapPageUtil.getAppContext().getSystemService("connectivity")).getActiveNetworkInfo();
                    IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                    if (activeNetworkInfo != null) {
                        if (!activeNetworkInfo.isConnected() || activeNetworkInfo.getType() != 1) {
                            if (OfflineVApp.this.c == 1 && iOfflineManager != null) {
                                iOfflineManager.pauseAll();
                            }
                        } else if (iOfflineManager != null) {
                            iOfflineManager.resumeWifi();
                        }
                        OfflineVApp.this.c = activeNetworkInfo.getType();
                    }
                }
            };
        }
        lt.a().a(this.e);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            iOfflineManager.initialize();
        }
        IOfflineManager iOfflineManager2 = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager2 != null) {
            iOfflineManager2.requestGpu3dSupport(AMapPageUtil.getPageContext());
        }
        if (!this.b) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            AMapPageUtil.getAppContext().registerReceiver(this.a, intentFilter);
            this.b = true;
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.d) {
            if (this.b) {
                AMapPageUtil.getAppContext().unregisterReceiver(this.a);
            }
            this.b = false;
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                if (iOfflineManager.isDBException()) {
                    iOfflineManager.setIsDBException(false);
                    Process.killProcess(Process.myPid());
                }
                iOfflineManager.destroy();
            }
        }
        lt.a().b(this.e);
    }
}

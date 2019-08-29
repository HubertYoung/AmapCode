package com.amap.bundle.drive;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonUtils;
import com.amap.bundle.drive.ajx.module.ModuleDriveEnd;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.drive.ajx.module.ModuleRouteCar;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.ajx.module.ModuleRouteEtrip;
import com.amap.bundle.drive.ajx.module.ModuleRouteMotor;
import com.amap.bundle.drive.ajx.module.ModuleRouteTruck;
import com.amap.bundle.drive.common.speaker.SpeakerPlayManager;
import com.amap.bundle.drive.common.yuncontrol.YunConfigurationManager;
import com.amap.bundle.drive.offline.IDriveOfflineManager;
import com.amap.bundle.drive.radar.module.ModuleRouteBoard;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;

public class DriveNaviVApp extends esh {
    SpeakerPlayManager a = null;
    private BroadcastReceiver b;
    private boolean c = false;

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        bgl.a((bgd) new po());
        bgl.a((bgd) new pl());
        bgl.a((bgd) new pm());
        bgl.a((bgd) new pn());
        Ajx.getInstance().registerModule(ModuleRouteCar.class, ModuleRouteDriveResult.class, ModuleRouteMotor.class, ModuleRouteTruck.class, ModuleRouteEtrip.class, ModuleRouteBoard.class, ModuleHeadunit.class, ModuleDriveNavi.class, ModuleDriveEnd.class, ModuleCommonBusiness.class, ModuleDriveCommonBusiness.class, ModuleDriveCommonUtils.class);
        oh.a().b();
        this.b = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int i;
                Context appContext = AMapPageUtil.getAppContext();
                NetworkInfo networkInfo = null;
                ConnectivityManager connectivityManager = appContext != null ? (ConnectivityManager) appContext.getSystemService("connectivity") : null;
                if (connectivityManager != null) {
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                }
                djk djk = (djk) ank.a(djk.class);
                if (djk != null) {
                    if (networkInfo == null) {
                        i = -1;
                    } else {
                        i = networkInfo.getType();
                    }
                    djk.a(i);
                }
                IDriveOfflineManager iDriveOfflineManager = (IDriveOfflineManager) ank.a(IDriveOfflineManager.class);
                if (iDriveOfflineManager != null) {
                    iDriveOfflineManager.onConnectionChanged(networkInfo);
                }
            }
        };
        IDriveOfflineManager iDriveOfflineManager = (IDriveOfflineManager) ank.a(IDriveOfflineManager.class);
        if (iDriveOfflineManager != null) {
            iDriveOfflineManager.init();
        }
        this.a = new SpeakerPlayManager(AMapPageUtil.getAppContext());
        this.a.d();
        try {
            if (!this.c) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                Context appContext = AMapPageUtil.getAppContext();
                if (appContext != null) {
                    appContext.registerReceiver(this.b, intentFilter);
                    this.c = true;
                }
            }
        } catch (Exception unused) {
        }
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        Activity activity = DoNotUseTool.getActivity();
        boolean z = false;
        if (!new bnv().a()) {
            Intent intent = activity != null ? activity.getIntent() : null;
            if (intent != null) {
                Uri data = intent.getData();
                String action = intent.getAction();
                if (data == null && (TextUtils.isEmpty(action) || action.equalsIgnoreCase("android.intent.action.VIEW"))) {
                    z = true;
                }
            }
        }
        if (z) {
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.a(activity);
            }
        }
        YunConfigurationManager.a().b();
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        try {
            if (this.c) {
                Context appContext = AMapPageUtil.getAppContext();
                if (appContext != null) {
                    appContext.unregisterReceiver(this.b);
                }
            }
            this.c = false;
        } catch (Exception unused) {
        }
        IDriveOfflineManager iDriveOfflineManager = (IDriveOfflineManager) ank.a(IDriveOfflineManager.class);
        if (iDriveOfflineManager != null) {
            iDriveOfflineManager.destroy();
        }
        if (this.a != null) {
            this.a.c();
        }
    }
}

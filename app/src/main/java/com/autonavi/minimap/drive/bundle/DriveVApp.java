package com.autonavi.minimap.drive.bundle;

import android.content.SharedPreferences.Editor;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.iflytek.tts.TtsService.TtsManager;
import com.iflytek.tts.TtsService.TtsManagerUtil;
import java.io.File;
import java.io.InputStream;
import org.json.JSONObject;

public class DriveVApp extends esh {
    private boolean a = false;
    private a b = new a() {
        public final void a(JSONObject jSONObject) {
            ahn.b().execute(new Runnable() {
                public final void run() {
                    DriveVApp.a();
                }
            });
        }
    };
    private IPageStateListener c = new IPageStateListener() {
        public final void onAppear() {
        }

        public final void onCover() {
            ms msVar = (ms) a.a.a(ms.class);
            if (msVar != null) {
                msVar.b();
            }
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        if (hasPermission()) {
            this.a = true;
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            boolean booleanValue = mapSharePreference.getBooleanValue("traffic", false);
            if (!mapSharePreference.contains("traffic_for_drive")) {
                mapSharePreference.putBooleanValue("traffic_for_drive", booleanValue);
            }
            MapSharePreference mapSharePreference2 = new MapSharePreference(SharePreferenceName.SharedPreferences);
            mapSharePreference2.putBooleanValue("ali_auto_car_connected", false);
            mapSharePreference2.putBooleanValue("amap_auto_car_connected", false);
            ((IMainMapService) ank.a(IMainMapService.class)).a((Object) this.c);
        }
        lt.a().a(this.b);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.a();
            dfm.a(bim.aa().k((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY));
        }
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            vpVar.a();
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.init();
        }
        ahm.a(new Runnable() {
            public final void run() {
                DriveVApp.b();
                TtsManager.getInstance().InitializeTTs();
                TtsManager.getInstance().checkUpdateVoiceCommon();
            }
        });
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.a) {
            dfm dfm = (dfm) ank.a(dfm.class);
            if (dfm != null) {
                dfm.d();
            }
            ank.a(djk.class);
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            if (iVoicePackageManager != null) {
                iVoicePackageManager.destroy();
            }
            IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
            if (iAutoRemoteController != null) {
                iAutoRemoteController.release();
            }
        }
        lt.a().b(this.b);
    }

    static /* synthetic */ void a() {
        mb mbVar = lt.a().c.B;
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        if (mbVar != null) {
            edit.putString("voiceCommonUrl", mbVar.a);
            edit.putString("voiceCommonMd5", mbVar.b);
            edit.apply();
        }
    }

    static /* synthetic */ void b() {
        String xiaoyanFileFullName = TtsManagerUtil.getXiaoyanFileFullName(AMapAppGlobal.getApplication());
        StringBuilder sb = new StringBuilder();
        sb.append(dgu.a().d());
        sb.append("/biaozhunnvzhongyin.irf");
        String sb2 = sb.toString();
        File file = new File(xiaoyanFileFullName);
        File file2 = new File(sb2);
        if (file.exists()) {
            if (!file2.exists()) {
                TtsManagerUtil.copyFile(xiaoyanFileFullName, sb2);
                tw b2 = dgm.a().b((String) "nvzhongyin");
                ua uaVar = new ua();
                uaVar.b = "nvzhongyin";
                uaVar.d = file2.length();
                uaVar.e = file2.length();
                uaVar.f = "/biaozhunnvzhongyin.irf";
                new dgl(b2, uaVar).a(4);
            } else {
                dgl a2 = dgm.a().a((String) "nvzhongyin");
                if (a2 == null) {
                    tw b3 = dgm.a().b((String) "nvzhongyin");
                    ua uaVar2 = new ua();
                    uaVar2.b = "nvzhongyin";
                    uaVar2.d = file2.length();
                    uaVar2.e = file2.length();
                    uaVar2.f = "/biaozhunnvzhongyin.irf";
                    new dgl(b3, uaVar2).a(4);
                } else if (a2.g() != 4) {
                    a2.a(4);
                }
            }
            file.delete();
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(dgu.a().d());
        sb3.append("/lzl.irf");
        String sb4 = sb3.toString();
        if (!new File(sb4).exists()) {
            String defaultFileFullName = TtsManagerUtil.getDefaultFileFullName(AMapAppGlobal.getApplication());
            if (!new File(defaultFileFullName).exists()) {
                try {
                    InputStream open = AMapAppGlobal.getApplication().getAssets().open("tts/Resource_6.0_lzl.png");
                    if (open != null) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(dgu.a().d());
                        sb5.append("/temp");
                        String sb6 = sb5.toString();
                        File file3 = new File(sb6);
                        if (file3.isFile() && file3.exists()) {
                            file3.delete();
                        }
                        ahf.a(open, sb6);
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(sb6);
                        sb7.append("/lzl.irf");
                        String sb8 = sb7.toString();
                        File file4 = new File(sb8);
                        if (file4.exists()) {
                            TtsManagerUtil.copyFile(sb8, sb4);
                            file4.delete();
                            file3.delete();
                        }
                    }
                } catch (Exception unused) {
                }
            } else {
                TtsManagerUtil.copyFile(defaultFileFullName, sb4);
            }
        }
        dfx.a();
        dgl d = dfx.d();
        if (d != null && d.g() != 4) {
            d.a(4);
        }
    }
}

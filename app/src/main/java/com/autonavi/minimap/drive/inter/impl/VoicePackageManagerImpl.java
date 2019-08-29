package com.autonavi.minimap.drive.inter.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil;
import com.autonavi.minimap.drive.navi.navitts.NaviRecordUtil.a;
import com.autonavi.minimap.drive.navi.navitts.fragment.NavigationVoiceRecordFragment;
import com.autonavi.minimap.drive.navi.navitts.fragment.OfflineNaviTtsFragment;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.model.data.NaviTtsConstant;
import com.iflytek.tts.TtsService.TtsManager;
import com.iflytek.tts.TtsService.TtsManagerUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class VoicePackageManagerImpl implements IVoicePackageManager {
    /* access modifiers changed from: private */
    public static final Object b = new Object();
    public boolean a = false;
    private boolean c = false;
    /* access modifiers changed from: private */
    public dhf d = ((dhf) ank.a(dhf.class));

    public void initialization() {
        DriveOfflineSDK.e();
        DriveOfflineSDK.i();
    }

    public void destroy() {
        DriveOfflineSDK.f();
    }

    public boolean setDefaultTts(final boolean z) {
        if (this.d == null) {
            return false;
        }
        final String defaultFileFullName = TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext());
        if (new File(defaultFileFullName).exists()) {
            this.a = true;
            ahl.a(new a() {
                public final Object doBackground() throws Exception {
                    TtsManager.getInstance().setCurrentTtsFile(defaultFileFullName, "9");
                    if (z) {
                        TtsManager.getInstance().TTS_Txt(VoicePackageManagerImpl.this.d.c().getApplicationContext(), "已恢复默认语音");
                    }
                    return null;
                }
            });
            restoreDefaultTTS();
            return true;
        }
        TtsManager.getInstance().TTS_Destory();
        return false;
    }

    public void restoreDefaultTTS() {
        dfx.a().a((String) "linzhilingyuyin", (String) null);
        this.a = true;
    }

    public String getCurrentTtsFilePath() {
        dgl c2 = dfx.a().c();
        return c2 != null ? c2.a() : "";
    }

    public String getCurrentTtsName2() {
        if (this.d == null) {
            return "";
        }
        if (this.a && new File(TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext())).exists()) {
            return NaviTtsConstant.DEFAULT_VOICE_NAME;
        }
        dgl c2 = dfx.a().c();
        return c2 != null ? c2.a.l : "";
    }

    public String getPlayType(String str) {
        String str2 = "9";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        tw c2 = dgm.a().c(str);
        if (c2 != null && !TextUtils.isEmpty(c2.p)) {
            str2 = c2.p;
        }
        if (bno.a) {
            AMapLog.i("OfflineManagerImpl", "getPlayType soundType:".concat(String.valueOf(str2)));
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("getPlayType name:");
            sb.append(str);
            sb.append(" soundType:");
            sb.append(str2);
            a2.c("PlaySoundUtils", sb.toString());
        }
        return str2;
    }

    public String getCurrentTtsName() {
        if (this.d == null) {
            return "";
        }
        if (this.a && new File(TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext())).exists()) {
            return NaviTtsConstant.DEFAULT_VOICE_NAME;
        }
        dgl c2 = dfx.a().c();
        return c2 != null ? c2.a.c : "";
    }

    public void setCurrentTtsFileByName(final String str, final Callback callback) {
        if (this.d != null) {
            if (str.equals(NaviTtsConstant.DEFAULT_VOICE_NAME)) {
                final String defaultFileFullName = TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext());
                if (new File(defaultFileFullName).exists()) {
                    this.a = true;
                    ahl.a(new a() {
                        public final Object doBackground() throws Exception {
                            TtsManager.getInstance().setCurrentTtsFile(defaultFileFullName, VoicePackageManagerImpl.this.getPlayType(str));
                            VoicePackageManagerImpl.this.restoreDefaultTTS();
                            dfx.a().a((String) "linzhilingyuyin", (String) null);
                            callback.callback(null);
                            return null;
                        }
                    });
                    return;
                }
                callback.error(new FileNotFoundException(), false);
                return;
            }
            DriveOfflineSDK.e();
            List<dgl> k = DriveOfflineSDK.k();
            for (int i = 0; i < k.size(); i++) {
                dgl dgl = k.get(i);
                if (dgl.a.c.equals(str)) {
                    a(dgl, callback);
                    return;
                }
            }
            callback.error(new FileNotFoundException(), false);
        }
    }

    private void a(final dgl dgl, final Callback callback) {
        this.a = false;
        ahl.a(new a() {
            public final Object doBackground() throws Exception {
                if (TtsManager.getInstance().setCurrentTtsFile(dgl.a(), VoicePackageManagerImpl.this.getPlayType(dgl.a.c))) {
                    dfx.a().a(dgl);
                    if (callback != null) {
                        callback.callback(null);
                    }
                } else {
                    DriveOfflineSDK.e().a(dgl, (dgx) null);
                    if (callback != null) {
                        callback.error(new FileNotFoundException(), false);
                    }
                }
                return null;
            }
        });
    }

    public boolean setCurrentTtsFileByName(final String str) {
        if (this.d == null) {
            return false;
        }
        if (str.equals(NaviTtsConstant.DEFAULT_VOICE_NAME)) {
            final String defaultFileFullName = TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext());
            if (new File(defaultFileFullName).exists()) {
                this.a = true;
                ahl.a(new a() {
                    public final Object doBackground() throws Exception {
                        TtsManager.getInstance().setCurrentTtsFile(defaultFileFullName, VoicePackageManagerImpl.this.getPlayType(str));
                        VoicePackageManagerImpl.this.restoreDefaultTTS();
                        return null;
                    }
                });
                dfx.a().a((String) "linzhilingyuyin", (String) null);
                return true;
            }
        } else {
            DriveOfflineSDK.e();
            List<dgl> k = DriveOfflineSDK.k();
            for (int i = 0; i < k.size(); i++) {
                dgl dgl = k.get(i);
                if (dgl.a.c.equals(str)) {
                    a(dgl, null);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNaviTtsNewVersion() {
        try {
            if (Float.parseFloat(b()) > Float.parseFloat(dfo.e())) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String b() {
        DriveOfflineSDK.e();
        return DriveOfflineSDK.a(dhd.b(), (String) "dialectVoiceVersion");
    }

    public boolean isNaviTtsNewFeature() {
        return dfo.i();
    }

    public List<String[]> getDownloadedVoiceList() {
        ArrayList arrayList = new ArrayList();
        DriveOfflineSDK.e();
        List<dgl> k = DriveOfflineSDK.k();
        for (int i = 0; i < k.size(); i++) {
            dgl dgl = k.get(i);
            arrayList.add(new String[]{dgl.a.c, dgl.a.d, dgl.a.k, String.valueOf(dgl.a.h), dgl.a.e, dgl.a.f, dgl.a.m, dgl.a.l, dgl.a.n, String.valueOf(dgl.a.g), dgl.a.i, dgl.a.j});
        }
        return arrayList;
    }

    public List<String> getDownloadedVoiceNameList() {
        if (this.d == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (new File(TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext())).exists()) {
            arrayList.add(NaviTtsConstant.DEFAULT_VOICE_NAME);
        }
        DriveOfflineSDK.e();
        List<dgl> k = DriveOfflineSDK.k();
        for (int i = 0; i < k.size(); i++) {
            arrayList.add(k.get(i).a.c);
        }
        return arrayList;
    }

    public String getCurrentTtsImage() {
        if (this.d == null) {
            return "";
        }
        if (this.a && new File(TtsManagerUtil.getDefaultFileFullName(this.d.c().getApplicationContext())).exists()) {
            return NaviTtsConstant.DEFAULT_VOICE_NAME;
        }
        dgl c2 = dfx.a().c();
        return c2 != null ? c2.a.k : "";
    }

    public String getNaviTtsUpdateVer() {
        return b();
    }

    public String getCurrentTtsSubName() {
        dgl c2 = dfx.a().c();
        return c2 != null ? c2.a.f : "";
    }

    public boolean hasNaviTTS() {
        String d2 = dgu.a().d();
        if (d2 != null) {
            File file = new File(d2);
            if (file.isDirectory() && file.exists()) {
                String[] list = file.list(new FilenameFilter() {
                    public final boolean accept(File file, String str) {
                        return str.endsWith(FilePathHelper.SUFFIX_DOT_IRF_FOR_VOICE);
                    }
                });
                if (list != null && list.length > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<String[]> getCustomizedVoices(Context context) {
        ArrayList arrayList = new ArrayList();
        for (a next : NaviRecordUtil.getCustomVoices()) {
            arrayList.add(new String[]{next.a, next.b, next.c, String.valueOf(next.d), String.valueOf(next.e), String.valueOf(next.f), String.valueOf(next.g), next.h, String.valueOf(next.i), String.valueOf(next.j), a.a()});
        }
        return arrayList;
    }

    public void setCurrentCustomVoice(String str) {
        dfo.d(str);
    }

    public boolean getCustomVoiceState() {
        return dfo.f();
    }

    public boolean isVoiceInDownloading() {
        DriveOfflineSDK.e();
        return DriveOfflineSDK.c().size() > 0;
    }

    public void setIsUpgradeAe8TTSVersion(boolean z) {
        DriveOfflineSDK.e();
        DriveOfflineSDK.a(z);
    }

    public void setCustomVoiceState(boolean z) {
        dfo.a(z);
    }

    public String getCurrentCustomizedVoice() {
        return dfo.g();
    }

    public void deal(bid bid, Intent intent) {
        if (intent != null && bid != null && bid.isAlive()) {
            if (intent.getExtras() != null) {
                if (intent.getBooleanExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, false)) {
                    bid.startPage(OfflineNaviTtsFragment.class, new PageBundle(intent));
                    return;
                } else if (intent.getBooleanExtra(IVoicePackageManager.ENTRANCE_RECORD_CUSTOMIZED_VOICES, false)) {
                    bid.startPage(NavigationVoiceRecordFragment.class, new PageBundle(intent));
                    return;
                }
            }
            bid.finish();
        }
    }

    static /* synthetic */ void b(VoicePackageManagerImpl voicePackageManagerImpl) {
        if (voicePackageManagerImpl.d != null) {
            String defaultFileFullName = TtsManagerUtil.getDefaultFileFullName(voicePackageManagerImpl.d.c().getApplicationContext());
            if (new File(defaultFileFullName).exists()) {
                voicePackageManagerImpl.a = true;
                TtsManager.getInstance().setCurrentTtsFile(defaultFileFullName, "9");
                voicePackageManagerImpl.restoreDefaultTTS();
                TtsManager.getInstance().TTS_Txt(voicePackageManagerImpl.d.c().getApplicationContext(), "语音文件损坏，已为您切换到默认语音。");
                return;
            }
            TtsManager.getInstance().TTS_Txt(voicePackageManagerImpl.d.c().getApplicationContext(), "语音文件损坏，已为您切换到默认语音。");
        }
    }
}

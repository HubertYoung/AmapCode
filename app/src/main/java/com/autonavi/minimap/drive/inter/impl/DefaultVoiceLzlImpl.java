package com.autonavi.minimap.drive.inter.impl;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.iflytek.tts.TtsService.TtsManager;

public class DefaultVoiceLzlImpl implements afy {
    private String mLzlVoicePkgName = TtsManager.TTS_LZL_FILE_NAME;

    public void setIsDefaultLzl(boolean z) {
        dgl c = dfx.a().c();
        boolean equals = c != null ? TextUtils.equals(c.a.f, "linzhilingyuyin") : true;
        boolean o = dfo.o();
        if (z && o && equals && saveLzlFromAssetToSDCard()) {
            dfx.a();
            dgl d = dfx.d();
            if (d != null) {
                d.a(4);
                setDefaultLzlVoice(d);
            }
        }
    }

    private boolean saveLzlFromAssetToSDCard() {
        return dfp.a(this.mLzlVoicePkgName);
    }

    private void setDefaultLzlVoice(dgl dgl) {
        if (dgl != null) {
            b.a.b();
            StringBuilder sb = new StringBuilder();
            sb.append(dgu.a().d());
            sb.append("/lzl.irf");
            String sb2 = sb.toString();
            IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
            String str = "";
            if (iVoicePackageManager != null) {
                str = iVoicePackageManager.getPlayType(dgl.a.c);
            }
            TtsManager.getInstance().setCurrentTtsFile(sb2, str);
            dfx.a().a(dgl);
            dfm dfm = (dfm) ank.a(dfm.class);
            if (!(dfm == null || iVoicePackageManager == null)) {
                dfm.b(iVoicePackageManager.getPlayType(dgl.a.c));
            }
            dfo.n();
            dfo.m();
        }
    }
}

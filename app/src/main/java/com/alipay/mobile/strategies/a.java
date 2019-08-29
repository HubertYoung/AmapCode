package com.alipay.mobile.strategies;

import android.text.TextUtils;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.mascanengine.BuryRecord;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MaBlackListOperation */
public final class a {
    public int a = 0;
    private List<String> b = new ArrayList();

    public final void a(String framesDelayStr) {
        if (TextUtils.isEmpty(framesDelayStr)) {
            this.a = 0;
            return;
        }
        try {
            int frames = Integer.parseInt(framesDelayStr);
            if (frames > 0) {
                this.a = frames;
                Logger.d("MaBlackListOperation", "enable frames delay: framesDelay is " + framesDelayStr);
            }
        } catch (Exception e) {
            Logger.e("MaBlackListOperation", e.getMessage());
            this.a = 0;
        }
    }

    public final void b(String cfgStr) {
        if (!TextUtils.isEmpty(cfgStr)) {
            Logger.d("MaBlackListOperation", "addToBlackList() : " + cfgStr);
            String[] subCfgStrs = cfgStr.split(";");
            if (subCfgStrs != null && subCfgStrs.length != 0) {
                for (String subCfg : subCfgStrs) {
                    if (!TextUtils.isEmpty(subCfg.toLowerCase().trim())) {
                        this.b.add(subCfg.toLowerCase());
                    }
                }
            }
        }
    }

    private boolean c(String candidateStr) {
        boolean result = false;
        if (this.a >= 0 && !TextUtils.isEmpty(candidateStr) && this.b != null && !this.b.isEmpty()) {
            result = false;
            String lowLink = candidateStr.toLowerCase();
            for (String cfgBStr : this.b) {
                if (lowLink.startsWith(cfgBStr) || lowLink.startsWith(new StringBuilder(AjxHttpLoader.DOMAIN_HTTP).append(cfgBStr).toString()) || lowLink.startsWith(new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(cfgBStr).toString())) {
                    result = true;
                }
            }
            if (result) {
                Logger.d("MaBlackListOperation", "----> " + candidateStr + " <---- in black list");
            }
        }
        return result;
    }

    public final MultiMaScanResult a(MultiMaScanResult multiMaScanResult) {
        MaScanResult[] maScanResultArr;
        if (!(multiMaScanResult == null || multiMaScanResult.maScanResults == null || multiMaScanResult.maScanResults.length == 0)) {
            List optResults = new ArrayList();
            for (MaScanResult maScanResult : multiMaScanResult.maScanResults) {
                if (maScanResult != null && !c(maScanResult.text)) {
                    optResults.add(maScanResult);
                }
            }
            if (multiMaScanResult.maScanResults.length >= 2 && optResults.size() > 0 && optResults.size() != multiMaScanResult.maScanResults.length) {
                BuryRecord.recordTwoCodeHasBlackList(((MaScanResult) optResults.get(0)).text);
            }
            if (this.a <= 0) {
                if (optResults.isEmpty()) {
                    optResults.add(multiMaScanResult.maScanResults[0]);
                }
                multiMaScanResult.maScanResults = (MaScanResult[]) optResults.toArray(new MaScanResult[optResults.size()]);
            } else if (!optResults.isEmpty()) {
                multiMaScanResult.maScanResults = (MaScanResult[]) optResults.toArray(new MaScanResult[optResults.size()]);
            } else {
                multiMaScanResult.candidate = true;
            }
        }
        return multiMaScanResult;
    }
}

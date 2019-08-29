package com.autonavi.minimap.ajx3.upgrade;

import android.content.Context;
import com.autonavi.minimap.ajx3.image.ImageCache;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.EncodingUtils;
import com.autonavi.minimap.ajx3.util.FileUtil;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

public class Ajx3WebResourcesConfig {
    private static final String WEB_CONFIG_PATH = "/preload.json";
    /* access modifiers changed from: private */
    public boolean isRunning = false;
    private Context mContext;

    public Ajx3WebResourcesConfig(Context context) {
        this.mContext = context;
    }

    public void checkUpdate(boolean z) {
        LogHelper.d("Ajx3WebResourcesConfig::checkUpdate #isColdStart = ".concat(String.valueOf(z)));
        if (!this.isRunning) {
            this.isRunning = true;
            List<String> bundleNameList = Ajx3UpgradeManager.getInstance().getBundleNameList();
            if (bundleNameList != null && bundleNameList.size() > 0) {
                doCheckUpdate(new ArrayList(bundleNameList), true);
            }
            this.isRunning = false;
            Ajx3UpgradeManager.getInstance().setBundleListUpdateListener(new BundleNameListUpdateListener() {
                public void onBundleNameUpdated(List<String> list) {
                    if (!Ajx3WebResourcesConfig.this.isRunning) {
                        Ajx3WebResourcesConfig.this.isRunning = true;
                        if (list != null && list.size() > 0) {
                            Ajx3WebResourcesConfig.this.doCheckUpdate(list, true);
                        }
                        Ajx3WebResourcesConfig.this.isRunning = false;
                    }
                }

                public void onJsBundleNameUpdate(List<String> list) {
                    if (!Ajx3WebResourcesConfig.this.isRunning) {
                        Ajx3WebResourcesConfig.this.isRunning = true;
                        if (list != null && list.size() > 0) {
                            Ajx3WebResourcesConfig.this.doCheckUpdate(list, false);
                        }
                        Ajx3WebResourcesConfig.this.isRunning = false;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void doCheckUpdate(List<String> list, boolean z) {
        String str;
        for (String next : list) {
            StringBuilder sb = new StringBuilder("Ajx3WebResourcesConfig::doCheckUpdate #bundleNames = ");
            sb.append(next);
            sb.append(", isAjxFile = ");
            sb.append(z);
            LogHelper.d(sb.toString());
            byte[] bArr = null;
            if (z) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(next);
                sb2.append(WEB_CONFIG_PATH);
                str = sb2.toString();
                if (AjxFileInfo.isFileExists(str)) {
                    bArr = AjxFileInfo.getFileDataByPath(str);
                }
            } else {
                String str2 = a.a;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(next);
                sb3.append(WEB_CONFIG_PATH);
                str = cnz.a(str2, sb3.toString());
                if (FileUtil.checkFileInvalid(str)) {
                    bArr = FileUtil.getFileBytes(str);
                }
            }
            if (bArr == null || bArr.length <= 0) {
                LogHelper.d("Ajx3WebResourcesConfig::doCheckUpdate # file read error!!! file = ".concat(String.valueOf(str)));
            } else {
                checkWebResource(EncodingUtils.getString(bArr, (String) "UTF-8"));
            }
        }
    }

    private void checkWebResource(String str) {
        if (str != null) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                String[] strArr = new String[length];
                for (int i = 0; i < length; i++) {
                    strArr[i] = jSONArray.getString(i);
                }
                ImageCache.getInstance(this.mContext).preLoad(this.mContext, strArr);
            } catch (JSONException unused) {
            }
        }
    }
}

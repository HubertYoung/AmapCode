package com.alipay.mobile.nebulaappproxy.provider;

import android.content.Context;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.patch.BasePatcher;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5PatchProvider;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5PatchProviderImpl implements H5PatchProvider {
    public boolean patcherDir(Context context, String newFileFolder, String oldFileFolder, String patchFilePath, String oldFileMD5, String patchFileMD5) {
        if (a()) {
            return a(context, newFileFolder, oldFileFolder, patchFilePath, oldFileMD5, patchFileMD5);
        }
        return BasePatcher.patcherDir(context, newFileFolder, oldFileFolder, patchFilePath, oldFileMD5, patchFileMD5);
    }

    private synchronized boolean a(Context context, String newFileFolder, String oldFileFolder, String patchFilePath, String oldFileMD5, String patchFileMD5) {
        boolean patcherDir;
        if (!H5FileUtil.exists(newFileFolder) || !b()) {
            patcherDir = BasePatcher.patcherDir(context, newFileFolder, oldFileFolder, patchFilePath, oldFileMD5, patchFileMD5);
        } else {
            H5Log.d("H5PatchProviderImpl", "newFileFolder : " + newFileFolder + " exists, return");
            patcherDir = true;
        }
        return patcherDir;
    }

    private static boolean a() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_patcherDirSync"))) {
            return true;
        }
        return false;
    }

    private static boolean b() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithProcessCache("h5_enableCheckNewFileExists"))) {
            return true;
        }
        return false;
    }
}

package com.autonavi.minimap.offline.nativesupport;

import android.text.TextUtils;
import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.minimap.ackor.ackoroffline.IAmapCompat;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.init.OfflineDataInit;
import java.io.File;

public class AmapCompat implements IAmapCompat {
    public Parcel onDatabaseCheckAndUpgrade(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String backupSdcardPath = DataCompatHelper.getBackupSdcardPath(str);
        boolean z = false;
        if (!TextUtils.isEmpty(backupSdcardPath)) {
            File file = new File(backupSdcardPath);
            if (file.exists() && file.canRead()) {
                DataCompatHelper.copyDatabase(file, new File(str2));
                z = true;
            }
        }
        if (!z) {
            AmapCompatData compatData = new DataCompatHelper().getCompatData();
            if (compatData != null) {
                Parcel parcel = new Parcel();
                compatData.writeToParcel(parcel);
                if (OfflineDataInit.getInstance().isUpgradeAe8Version()) {
                    OfflineNativeSdk.getInstance().setAE8UpdateFlag(true);
                }
                return parcel;
            }
        }
        return null;
    }

    public boolean onDatabaseBackup(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        String backupSdcardPath = DataCompatHelper.getBackupSdcardPath(str2);
        if (!TextUtils.isEmpty(backupSdcardPath)) {
            return DataCompatHelper.copyDatabase(new File(str), new File(backupSdcardPath));
        }
        return false;
    }
}

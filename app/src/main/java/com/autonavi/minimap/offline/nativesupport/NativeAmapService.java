package com.autonavi.minimap.offline.nativesupport;

import android.text.TextUtils;
import com.autonavi.jni.ae.bl.Parcel;
import java.io.File;

class NativeAmapService {
    private NativeAmapService() {
    }

    /* access modifiers changed from: 0000 */
    public Parcel onDatabaseCheckAndUpgrade(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String backupSdcardPath = DataCompatHelper.getBackupSdcardPath(str);
        boolean z = false;
        if (!TextUtils.isEmpty(backupSdcardPath)) {
            File file = new File(backupSdcardPath);
            if (file.exists() && file.canRead()) {
                z = true;
                DataCompatHelper.copyDatabase(file, new File(str2));
            }
        }
        if (!z) {
            AmapCompatData compatData = new DataCompatHelper().getCompatData();
            if (compatData != null) {
                Parcel parcel = new Parcel();
                compatData.writeToParcel(parcel);
                return parcel;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
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

    /* access modifiers changed from: 0000 */
    public boolean requestWifiAutoUpdate() {
        return !((dfm) ank.a(dfm.class)).b();
    }
}

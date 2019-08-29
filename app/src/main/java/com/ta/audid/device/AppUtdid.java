package com.ta.audid.device;

import android.content.Context;
import android.text.TextUtils;
import com.ta.audid.Constants;
import com.ta.audid.Variables;
import com.ta.audid.collect.DeviceInfo2;
import com.ta.audid.store.SdcardDeviceModle;
import com.ta.audid.store.UtdidContentBuilder;
import com.ta.audid.store.UtdidContentSqliteStore;
import com.ta.audid.upload.UtdidUploadTask;
import com.ta.audid.utils.MutiProcessLock;
import com.ta.audid.utils.TaskExecutor;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.device.UTUtdid;
import java.util.ArrayList;

public class AppUtdid {
    private static final int V5 = 5;
    private static long mCollectDelayTime = 30000;
    private static final AppUtdid mInstance = new AppUtdid();
    /* access modifiers changed from: private */
    public String mAppUtdid = "";
    private String mUtdid = "";

    private AppUtdid() {
    }

    public static AppUtdid getInstance() {
        return mInstance;
    }

    public synchronized String getUtdid() {
        try {
            if (!TextUtils.isEmpty(this.mUtdid)) {
                return this.mUtdid;
            } else if (TextUtils.isEmpty(getUtdidFromFile())) {
                return Constants.UTDID_INVALID;
            } else {
                uploadAppUtdid();
                return this.mUtdid;
            }
        }
    }

    public String getUtdidFromFile() {
        try {
            MutiProcessLock.lockUtdidFile();
            String v5Utdid = getV5Utdid();
            if (!TextUtils.isEmpty(v5Utdid)) {
                UtdidLogger.d((String) "", "read from NewFile:".concat(String.valueOf(v5Utdid)));
                this.mUtdid = v5Utdid;
                this.mAppUtdid = v5Utdid;
                String str = this.mUtdid;
                MutiProcessLock.releaseUtdidFile();
                return str;
            }
            String valueForUpdate = UTUtdid.instance(Variables.getInstance().getContext()).getValueForUpdate();
            if (!TextUtils.isEmpty(valueForUpdate)) {
                UtdidLogger.d((String) "", "read from OldFile:".concat(String.valueOf(valueForUpdate)));
                this.mUtdid = valueForUpdate;
                this.mAppUtdid = valueForUpdate;
                String str2 = this.mUtdid;
                MutiProcessLock.releaseUtdidFile();
                return str2;
            }
            MutiProcessLock.releaseUtdidFile();
            return "";
        } catch (Throwable th) {
            MutiProcessLock.releaseUtdidFile();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x006a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getV5Utdid() {
        /*
            r10 = this;
            com.ta.audid.Variables r0 = com.ta.audid.Variables.getInstance()
            android.content.Context r0 = r0.getContext()
            if (r0 != 0) goto L_0x000d
            java.lang.String r0 = ""
            return r0
        L_0x000d:
            java.lang.Boolean r1 = com.ta.audid.utils.AppInfoUtils.isBelowMVersion()
            boolean r1 = r1.booleanValue()
            r2 = 5
            if (r1 == 0) goto L_0x0039
            java.lang.String r1 = com.ta.audid.upload.UtdidKeyFile.getUtdidFromSettings(r0)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x0039
            com.ta.audid.device.UtdidObj r3 = com.ta.audid.device.AppUtdidDecoder.decode(r1)
            boolean r4 = r3.isValid()
            if (r4 == 0) goto L_0x0039
            int r3 = r3.getVersion()
            if (r3 != r2) goto L_0x0039
            com.ta.audid.upload.UtdidKeyFile.writeAppUtdidFile(r1)
            com.ta.audid.upload.UtdidKeyFile.writeSdcardUtdidFile(r1)
            return r1
        L_0x0039:
            java.lang.String r1 = com.ta.audid.upload.UtdidKeyFile.readSdcardUtdidFile()
            java.lang.String r3 = com.ta.audid.upload.UtdidKeyFile.readAppUtdidFile()
            r4 = 0
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            r6 = 0
            if (r5 != 0) goto L_0x0063
            com.ta.audid.device.UtdidObj r4 = com.ta.audid.device.AppUtdidDecoder.decode(r1)
            if (r4 == 0) goto L_0x0063
            int r5 = r4.getVersion()
            if (r5 == r2) goto L_0x005e
            java.lang.String r1 = ""
            java.lang.String r5 = ""
            com.ta.audid.upload.UtdidKeyFile.writeSdcardUtdidFile(r5)
            goto L_0x0063
        L_0x005e:
            long r8 = r4.getTimestamp()
            goto L_0x0064
        L_0x0063:
            r8 = r6
        L_0x0064:
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 != 0) goto L_0x0089
            boolean r5 = r3.equals(r1)
            if (r5 == 0) goto L_0x0071
            goto L_0x0075
        L_0x0071:
            com.ta.audid.device.UtdidObj r4 = com.ta.audid.device.AppUtdidDecoder.decode(r3)
        L_0x0075:
            if (r4 == 0) goto L_0x0089
            int r5 = r4.getVersion()
            if (r5 == r2) goto L_0x0085
            java.lang.String r3 = ""
            java.lang.String r2 = ""
            com.ta.audid.upload.UtdidKeyFile.writeAppUtdidFile(r2)
            goto L_0x0089
        L_0x0085:
            long r6 = r4.getTimestamp()
        L_0x0089:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00ae
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x00ae
            boolean r2 = r1.equals(r3)
            if (r2 == 0) goto L_0x009c
            return r1
        L_0x009c:
            int r2 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r2 < 0) goto L_0x00a7
            com.ta.audid.upload.UtdidKeyFile.writeAppUtdidFile(r1)
            com.ta.audid.upload.UtdidKeyFile.writeUtdidToSettings(r0, r1)
            return r1
        L_0x00a7:
            com.ta.audid.upload.UtdidKeyFile.writeSdcardUtdidFile(r3)
            com.ta.audid.upload.UtdidKeyFile.writeUtdidToSettings(r0, r3)
            return r3
        L_0x00ae:
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x00c1
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 == 0) goto L_0x00c1
            com.ta.audid.upload.UtdidKeyFile.writeAppUtdidFile(r1)
            com.ta.audid.upload.UtdidKeyFile.writeUtdidToSettings(r0, r1)
            return r1
        L_0x00c1:
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x00d4
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 != 0) goto L_0x00d4
            com.ta.audid.upload.UtdidKeyFile.writeSdcardUtdidFile(r3)
            com.ta.audid.upload.UtdidKeyFile.writeUtdidToSettings(r0, r3)
            return r3
        L_0x00d4:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.device.AppUtdid.getV5Utdid():java.lang.String");
    }

    private void uploadAppUtdid() {
        UtdidLogger.d();
        if (!TextUtils.isEmpty(this.mAppUtdid)) {
            try {
                TaskExecutor.getInstance().schedule(null, new Runnable() {
                    public void run() {
                        AppUtdid.this.writeSdcardDevice();
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(UtdidContentBuilder.buildUtdidFp(AppUtdid.this.mAppUtdid));
                        UtdidContentSqliteStore.getInstance().insertStringList(arrayList);
                        new UtdidUploadTask(Variables.getInstance().getContext()).run();
                    }
                }, mCollectDelayTime);
            } catch (Throwable th) {
                UtdidLogger.d((String) "", th);
            }
        }
    }

    /* access modifiers changed from: private */
    public void writeSdcardDevice() {
        Context context = Variables.getInstance().getContext();
        if (context != null) {
            SdcardDeviceModle.writeSdcardDeviceModle(DeviceInfo2.getIMEI(context), DeviceInfo2.getIMSI(context));
        }
    }

    public synchronized void setAppUtdid(String str) {
        this.mAppUtdid = str;
    }

    public synchronized String getCurrentAppUtdid() {
        return this.mAppUtdid;
    }

    public static void setCollectDelayTime(long j) {
        if (j >= 0) {
            mCollectDelayTime = j;
        }
    }
}

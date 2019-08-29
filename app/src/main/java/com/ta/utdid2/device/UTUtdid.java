package com.ta.utdid2.device;

import android.content.Context;
import android.os.Binder;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.alibaba.analytics.core.device.Constants;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.security.mobile.module.crypto.CryptoUtil;
import com.ta.audid.utils.RC4;
import com.ta.audid.utils.UtdidLogger;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.IntUtils;
import com.ta.utdid2.android.utils.PhoneInfoUtils;
import com.ta.utdid2.android.utils.StringUtils;
import com.ta.utdid2.core.persistent.PersistentConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class UTUtdid {
    private static final Object CREATE_LOCK = new Object();
    private static final String S_GLOBAL_PERSISTENT_CONFIG_DIR;
    private static final String S_GLOBAL_PERSISTENT_CONFIG_KEY = "Alvin2";
    private static final String S_LOCAL_STORAGE_KEY = "ContextData";
    private static final String S_LOCAL_STORAGE_NAME = ".DataStorage";
    private static final String UM_SETTINGS_STORAGE = "dxCRMxhQkdGePGnp";
    private static final String UM_SETTINGS_STORAGE_NEW = "mqBRboGZkQPcAkyk";
    private static UTUtdid s_umutdid;
    private String mCBDomain = "xx_utdid_domain";
    private String mCBKey = "xx_utdid_key";
    private Context mContext = null;
    private PersistentConfiguration mPC = null;
    private Pattern mPattern = Pattern.compile("[^0-9a-zA-Z=/+]+");
    private PersistentConfiguration mTaoPC = null;
    private String mUtdid = null;
    private UTUtdidHelper mUtdidHelper = null;

    static {
        StringBuilder sb = new StringBuilder(Constants.PERSISTENT_CONFIG_DIR);
        sb.append(File.separator);
        sb.append("Global");
        S_GLOBAL_PERSISTENT_CONFIG_DIR = sb.toString();
    }

    private UTUtdid(Context context) {
        this.mContext = context;
        PersistentConfiguration persistentConfiguration = new PersistentConfiguration(context, S_GLOBAL_PERSISTENT_CONFIG_DIR, S_GLOBAL_PERSISTENT_CONFIG_KEY, false, true);
        this.mTaoPC = persistentConfiguration;
        PersistentConfiguration persistentConfiguration2 = new PersistentConfiguration(context, S_LOCAL_STORAGE_NAME, S_LOCAL_STORAGE_KEY, false, true);
        this.mPC = persistentConfiguration2;
        this.mUtdidHelper = new UTUtdidHelper();
        this.mCBKey = String.format("K_%d", new Object[]{Integer.valueOf(StringUtils.hashCode(this.mCBKey))});
        this.mCBDomain = String.format("D_%d", new Object[]{Integer.valueOf(StringUtils.hashCode(this.mCBDomain))});
    }

    private void removeIllegalKeys() {
        if (this.mTaoPC != null) {
            if (StringUtils.isEmpty(this.mTaoPC.getString("UTDID2"))) {
                String string = this.mTaoPC.getString("UTDID");
                if (!StringUtils.isEmpty(string)) {
                    saveUtdidToTaoPPC(string);
                }
            }
            boolean z = false;
            if (!StringUtils.isEmpty(this.mTaoPC.getString("DID"))) {
                this.mTaoPC.remove("DID");
                z = true;
            }
            if (!StringUtils.isEmpty(this.mTaoPC.getString("EI"))) {
                this.mTaoPC.remove("EI");
                z = true;
            }
            if (!StringUtils.isEmpty(this.mTaoPC.getString("SI"))) {
                this.mTaoPC.remove("SI");
                z = true;
            }
            if (z) {
                this.mTaoPC.commit();
            }
        }
    }

    public static UTUtdid instance(Context context) {
        if (context != null && s_umutdid == null) {
            synchronized (CREATE_LOCK) {
                try {
                    if (s_umutdid == null) {
                        UTUtdid uTUtdid = new UTUtdid(context);
                        s_umutdid = uTUtdid;
                        uTUtdid.removeIllegalKeys();
                    }
                }
            }
        }
        return s_umutdid;
    }

    private void saveUtdidToTaoPPC(String str) {
        if (isValidUtdid(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() == 24 && this.mTaoPC != null) {
                this.mTaoPC.putString("UTDID2", str);
                this.mTaoPC.commit();
            }
        }
    }

    private void saveUtdidToLocalStorage(String str) {
        if (str != null && this.mPC != null && !str.equals(this.mPC.getString(this.mCBKey))) {
            this.mPC.putString(this.mCBKey, str);
            this.mPC.commit();
        }
    }

    private void saveUtdidToNewSettings(String str) {
        if (checkSettingsPermission() && isValidUtdid(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length()) {
                String str2 = null;
                try {
                    str2 = System.getString(this.mContext.getContentResolver(), UM_SETTINGS_STORAGE_NEW);
                } catch (Exception unused) {
                }
                if (!isValidUtdid(str2)) {
                    try {
                        System.putString(this.mContext.getContentResolver(), UM_SETTINGS_STORAGE_NEW, str);
                    } catch (Exception unused2) {
                    }
                }
            }
        }
    }

    private void syncUtdidToSettings(String str) {
        String str2;
        try {
            str2 = System.getString(this.mContext.getContentResolver(), UM_SETTINGS_STORAGE);
        } catch (Exception unused) {
            str2 = null;
        }
        if (!str.equals(str2)) {
            try {
                System.putString(this.mContext.getContentResolver(), UM_SETTINGS_STORAGE, str);
            } catch (Exception unused2) {
            }
        }
    }

    private void saveUtdidToSettings(String str) {
        if (checkSettingsPermission() && str != null) {
            syncUtdidToSettings(str);
        }
    }

    private String getUtdidFromTaoPPC() {
        if (this.mTaoPC != null) {
            String string = this.mTaoPC.getString("UTDID2");
            if (!StringUtils.isEmpty(string) && this.mUtdidHelper.packUtdidStr(string) != null) {
                return string;
            }
        }
        return null;
    }

    private boolean isValidUtdid(String str) {
        if (str != null) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            return 24 == str.length() && !this.mPattern.matcher(str).find();
        }
    }

    public synchronized String getValue() {
        if (this.mUtdid != null) {
            return this.mUtdid;
        }
        return getValueForUpdate();
    }

    public synchronized String getValueForUpdate() {
        this.mUtdid = readUtdid();
        if (!TextUtils.isEmpty(this.mUtdid)) {
            return this.mUtdid;
        }
        try {
            byte[] generateUtdid = generateUtdid();
            if (generateUtdid != null) {
                this.mUtdid = Base64.encodeToString(generateUtdid, 2);
                saveUtdidToTaoPPC(this.mUtdid);
                String pack = this.mUtdidHelper.pack(generateUtdid);
                if (pack != null) {
                    saveUtdidToSettings(pack);
                    saveUtdidToLocalStorage(pack);
                }
                return this.mUtdid;
            }
        } catch (Exception e) {
            UtdidLogger.e("", e, new Object[0]);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00e6, code lost:
        return null;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x0068 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0010 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0018 A[SYNTHETIC, Splitter:B:10:0x0018] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0016 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.String readUtdid() {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.String r0 = ""
            android.content.Context r1 = r6.mContext     // Catch:{ Exception -> 0x0010 }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ Exception -> 0x0010 }
            java.lang.String r2 = "mqBRboGZkQPcAkyk"
            java.lang.String r1 = android.provider.Settings.System.getString(r1, r2)     // Catch:{ Exception -> 0x0010 }
            r0 = r1
        L_0x0010:
            boolean r1 = r6.isValidUtdid(r0)     // Catch:{ all -> 0x00e7 }
            if (r1 == 0) goto L_0x0018
            monitor-exit(r6)
            return r0
        L_0x0018:
            com.ta.utdid2.device.UTUtdidHelper2 r0 = new com.ta.utdid2.device.UTUtdidHelper2     // Catch:{ all -> 0x00e7 }
            r0.<init>()     // Catch:{ all -> 0x00e7 }
            r1 = 0
            r2 = 0
            android.content.Context r3 = r6.mContext     // Catch:{ Exception -> 0x002c }
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch:{ Exception -> 0x002c }
            java.lang.String r4 = "dxCRMxhQkdGePGnp"
            java.lang.String r3 = android.provider.Settings.System.getString(r3, r4)     // Catch:{ Exception -> 0x002c }
            goto L_0x002d
        L_0x002c:
            r3 = r2
        L_0x002d:
            boolean r4 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r3)     // Catch:{ all -> 0x00e7 }
            if (r4 != 0) goto L_0x0085
            java.lang.String r4 = r0.dePackWithBase64(r3)     // Catch:{ all -> 0x00e7 }
            boolean r5 = r6.isValidUtdid(r4)     // Catch:{ all -> 0x00e7 }
            if (r5 == 0) goto L_0x0042
            r6.saveUtdidToNewSettings(r4)     // Catch:{ all -> 0x00e7 }
            monitor-exit(r6)
            return r4
        L_0x0042:
            java.lang.String r4 = r0.dePack(r3)     // Catch:{ all -> 0x00e7 }
            boolean r5 = r6.isValidUtdid(r4)     // Catch:{ all -> 0x00e7 }
            if (r5 == 0) goto L_0x0068
            com.ta.utdid2.device.UTUtdidHelper r5 = r6.mUtdidHelper     // Catch:{ all -> 0x00e7 }
            java.lang.String r4 = r5.packUtdidStr(r4)     // Catch:{ all -> 0x00e7 }
            boolean r5 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r4)     // Catch:{ all -> 0x00e7 }
            if (r5 != 0) goto L_0x0068
            r6.saveUtdidToSettings(r4)     // Catch:{ all -> 0x00e7 }
            android.content.Context r4 = r6.mContext     // Catch:{ Exception -> 0x0068 }
            android.content.ContentResolver r4 = r4.getContentResolver()     // Catch:{ Exception -> 0x0068 }
            java.lang.String r5 = "dxCRMxhQkdGePGnp"
            java.lang.String r4 = android.provider.Settings.System.getString(r4, r5)     // Catch:{ Exception -> 0x0068 }
            r3 = r4
        L_0x0068:
            com.ta.utdid2.device.UTUtdidHelper r4 = r6.mUtdidHelper     // Catch:{ all -> 0x00e7 }
            java.lang.String r4 = r4.dePack(r3)     // Catch:{ all -> 0x00e7 }
            boolean r5 = r6.isValidUtdid(r4)     // Catch:{ all -> 0x00e7 }
            if (r5 == 0) goto L_0x0086
            r6.mUtdid = r4     // Catch:{ all -> 0x00e7 }
            r6.saveUtdidToTaoPPC(r4)     // Catch:{ all -> 0x00e7 }
            r6.saveUtdidToLocalStorage(r3)     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r6.mUtdid     // Catch:{ all -> 0x00e7 }
            r6.saveUtdidToNewSettings(r0)     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r6.mUtdid     // Catch:{ all -> 0x00e7 }
            monitor-exit(r6)
            return r0
        L_0x0085:
            r1 = 1
        L_0x0086:
            java.lang.String r3 = r6.getUtdidFromTaoPPC()     // Catch:{ all -> 0x00e7 }
            boolean r4 = r6.isValidUtdid(r3)     // Catch:{ all -> 0x00e7 }
            if (r4 == 0) goto L_0x00a5
            com.ta.utdid2.device.UTUtdidHelper r0 = r6.mUtdidHelper     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r0.packUtdidStr(r3)     // Catch:{ all -> 0x00e7 }
            if (r1 == 0) goto L_0x009b
            r6.saveUtdidToSettings(r0)     // Catch:{ all -> 0x00e7 }
        L_0x009b:
            r6.saveUtdidToNewSettings(r3)     // Catch:{ all -> 0x00e7 }
            r6.saveUtdidToLocalStorage(r0)     // Catch:{ all -> 0x00e7 }
            r6.mUtdid = r3     // Catch:{ all -> 0x00e7 }
            monitor-exit(r6)
            return r3
        L_0x00a5:
            com.ta.utdid2.core.persistent.PersistentConfiguration r3 = r6.mPC     // Catch:{ all -> 0x00e7 }
            java.lang.String r4 = r6.mCBKey     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = r3.getString(r4)     // Catch:{ all -> 0x00e7 }
            boolean r4 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r3)     // Catch:{ all -> 0x00e7 }
            if (r4 != 0) goto L_0x00e5
            java.lang.String r0 = r0.dePack(r3)     // Catch:{ all -> 0x00e7 }
            boolean r4 = r6.isValidUtdid(r0)     // Catch:{ all -> 0x00e7 }
            if (r4 != 0) goto L_0x00c3
            com.ta.utdid2.device.UTUtdidHelper r0 = r6.mUtdidHelper     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r0.dePack(r3)     // Catch:{ all -> 0x00e7 }
        L_0x00c3:
            boolean r3 = r6.isValidUtdid(r0)     // Catch:{ all -> 0x00e7 }
            if (r3 == 0) goto L_0x00e5
            com.ta.utdid2.device.UTUtdidHelper r3 = r6.mUtdidHelper     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = r3.packUtdidStr(r0)     // Catch:{ all -> 0x00e7 }
            boolean r4 = com.ta.utdid2.android.utils.StringUtils.isEmpty(r0)     // Catch:{ all -> 0x00e7 }
            if (r4 != 0) goto L_0x00e5
            r6.mUtdid = r0     // Catch:{ all -> 0x00e7 }
            if (r1 == 0) goto L_0x00dc
            r6.saveUtdidToSettings(r3)     // Catch:{ all -> 0x00e7 }
        L_0x00dc:
            java.lang.String r0 = r6.mUtdid     // Catch:{ all -> 0x00e7 }
            r6.saveUtdidToTaoPPC(r0)     // Catch:{ all -> 0x00e7 }
            java.lang.String r0 = r6.mUtdid     // Catch:{ all -> 0x00e7 }
            monitor-exit(r6)
            return r0
        L_0x00e5:
            monitor-exit(r6)
            return r2
        L_0x00e7:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.device.UTUtdid.readUtdid():java.lang.String");
    }

    private byte[] generateUtdid() throws Exception {
        String str;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int nextInt = new Random().nextInt();
        byte[] bytes = IntUtils.getBytes((int) (System.currentTimeMillis() / 1000));
        byte[] bytes2 = IntUtils.getBytes(nextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            str = PhoneInfoUtils.getImei(this.mContext);
        } catch (Exception unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(new Random().nextInt());
            str = sb.toString();
        }
        byteArrayOutputStream.write(IntUtils.getBytes(StringUtils.hashCode(str)), 0, 4);
        byteArrayOutputStream.write(IntUtils.getBytes(StringUtils.hashCode(calcHmac(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    public static String calcHmac(byte[] bArr) throws Exception {
        Mac instance = Mac.getInstance(CryptoUtil.HMAC_SHA1);
        instance.init(new SecretKeySpec(RC4.rc4(new byte[]{69, 114, 116, -33, 125, -54, -31, 86, -11, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, -78, -96, -17, -99, 64, 23, -95, -126, -82, -64, 113, 116, -16, -103, 49, -30, 9, -39, 33, -80, -68, -78, -117, 53, 30, -122, 64, -104, 74, -49, 106, 85, -38, -93}), instance.getAlgorithm()));
        return Base64.encodeToString(instance.doFinal(bArr), 2);
    }

    private boolean checkSettingsPermission() {
        return this.mContext.checkPermission("android.permission.WRITE_SETTINGS", Binder.getCallingPid(), Binder.getCallingUid()) == 0;
    }
}

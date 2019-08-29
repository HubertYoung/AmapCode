package com.alipay.mobile.security.bio.service.impl;

import android.content.Context;
import android.content.Intent;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.common.record.impl.BioRecordServiceImpl;
import com.alipay.mobile.security.bio.common.record.impl.ZimRecordServiceImpl;
import com.alipay.mobile.security.bio.config.Constant;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.module.MicroModule;
import com.alipay.mobile.security.bio.runtime.Runtime;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioAppManager;
import com.alipay.mobile.security.bio.service.BioMetaInfo;
import com.alipay.mobile.security.bio.service.BioRecordService;
import com.alipay.mobile.security.bio.service.BioService;
import com.alipay.mobile.security.bio.service.BioServiceDescription;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioStoreService;
import com.alipay.mobile.security.bio.service.BioTaskService;
import com.alipay.mobile.security.bio.service.BioUploadService;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BioServiceManagerImpl extends BioServiceManager {
    /* access modifiers changed from: private */
    public static boolean g = false;
    private Hashtable<String, BioService> b = new Hashtable<>();
    /* access modifiers changed from: private */
    public Hashtable<String, BioService> c = new Hashtable<>();
    private Hashtable<String, BioAppDescription> d = new Hashtable<>();
    private HashMap<String, LocalService> e = new HashMap<>();
    private HashMap<String, BioServiceDescription> f = new HashMap<>();

    public BioServiceManagerImpl(Context context, String str) {
        super(context, str);
        a(context);
        BioStoreServiceImpl bioStoreServiceImpl = new BioStoreServiceImpl();
        this.b.put(BioStoreService.class.getName(), bioStoreServiceImpl);
        BioTaskServiceImpl bioTaskServiceImpl = new BioTaskServiceImpl(this.mContext);
        this.b.put(BioTaskService.class.getName(), bioTaskServiceImpl);
        BioRecordServiceImpl bioRecordServiceImpl = new BioRecordServiceImpl();
        this.b.put(BioRecordService.class.getName(), bioRecordServiceImpl);
        ZimRecordServiceImpl zimRecordServiceImpl = new ZimRecordServiceImpl();
        this.b.put(ZimRecordService.class.getName(), zimRecordServiceImpl);
        BioUploadServiceImpl bioUploadServiceImpl = new BioUploadServiceImpl();
        this.b.put(BioUploadService.class.getName(), bioUploadServiceImpl);
        BioAppManager bioAppManager = new BioAppManager();
        this.b.put(BioAppManager.class.getName(), bioAppManager);
        bioStoreServiceImpl.create(this);
        bioTaskServiceImpl.create(this);
        bioRecordServiceImpl.create(this);
        zimRecordServiceImpl.create(this);
        bioUploadServiceImpl.create(this);
        bioAppManager.create(this);
        b(this.mContext);
    }

    private void a(Context context) {
        Runtime.getLocalService(context, this.e, this.f);
        for (LocalService create : this.e.values()) {
            create.create(this);
        }
    }

    private void b(Context context) {
        for (BioMetaInfo next : Runtime.getBioMetaInfoList(context, this)) {
            for (BioServiceDescription next2 : next.getExtServices()) {
                try {
                    this.c.put(next2.getInterfaceName(), (BioService) next2.getClazz().newInstance());
                } catch (InstantiationException e2) {
                    BioLog.e(next2.getInterfaceName() + e2.toString());
                } catch (IllegalAccessException e3) {
                    BioLog.e(next2.getInterfaceName() + e3.toString());
                } catch (Throwable th) {
                    BioLog.e(next2.getInterfaceName() + th.toString());
                }
            }
            for (BioAppDescription next3 : next.getApplications()) {
                if (next3 != null) {
                    String str = "bio_type_" + next3.getBioType() + "_" + next3.getBioAction();
                    if (!this.d.containsKey(str)) {
                        this.d.put(str, next3);
                    } else {
                        BioLog.d("app exist:" + this.d.get(str).toString());
                        BioLog.d("app input:" + next3.toString());
                    }
                }
            }
        }
        for (String str2 : this.c.keySet()) {
            this.c.get(str2).create(this);
        }
    }

    public <T> T getBioService(Class<T> cls) {
        return getBioService(cls.getName());
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0033 A[SYNTHETIC, Splitter:B:12:0x0033] */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002b A[SYNTHETIC, Splitter:B:9:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T getBioService(java.lang.String r5) {
        /*
            r4 = this;
            r2 = 0
            java.util.HashMap<java.lang.String, com.alipay.mobile.security.bio.service.local.LocalService> r0 = r4.e     // Catch:{ Throwable -> 0x003a }
            java.lang.Object r2 = r0.get(r5)     // Catch:{ Throwable -> 0x003a }
        L_0x0007:
            if (r2 != 0) goto L_0x004b
            java.util.HashMap<java.lang.String, com.alipay.mobile.security.bio.service.BioServiceDescription> r0 = r4.f     // Catch:{ Throwable -> 0x0043 }
            java.lang.Object r0 = r0.remove(r5)     // Catch:{ Throwable -> 0x0043 }
            com.alipay.mobile.security.bio.service.BioServiceDescription r0 = (com.alipay.mobile.security.bio.service.BioServiceDescription) r0     // Catch:{ Throwable -> 0x0043 }
            if (r0 == 0) goto L_0x004b
            java.lang.Class r1 = r0.getClazz()     // Catch:{ Throwable -> 0x0043 }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ Throwable -> 0x0043 }
            com.alipay.mobile.security.bio.service.local.LocalService r1 = (com.alipay.mobile.security.bio.service.local.LocalService) r1     // Catch:{ Throwable -> 0x0043 }
            r1.create(r4)     // Catch:{ Throwable -> 0x0043 }
            java.util.HashMap<java.lang.String, com.alipay.mobile.security.bio.service.local.LocalService> r3 = r4.e     // Catch:{ Throwable -> 0x0043 }
            java.lang.String r0 = r0.getInterfaceName()     // Catch:{ Throwable -> 0x0043 }
            r3.put(r0, r1)     // Catch:{ Throwable -> 0x0043 }
        L_0x0029:
            if (r1 != 0) goto L_0x0055
            java.util.Hashtable<java.lang.String, com.alipay.mobile.security.bio.service.BioService> r0 = r4.b     // Catch:{ Throwable -> 0x004d }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ Throwable -> 0x004d }
        L_0x0031:
            if (r0 != 0) goto L_0x0039
            java.util.Hashtable<java.lang.String, com.alipay.mobile.security.bio.service.BioService> r1 = r4.c     // Catch:{ Throwable -> 0x0057 }
            java.lang.Object r0 = r1.get(r5)     // Catch:{ Throwable -> 0x0057 }
        L_0x0039:
            return r0
        L_0x003a:
            r0 = move-exception
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)
            goto L_0x0007
        L_0x0043:
            r0 = move-exception
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)
        L_0x004b:
            r1 = r2
            goto L_0x0029
        L_0x004d:
            r0 = move-exception
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)
        L_0x0055:
            r0 = r1
            goto L_0x0031
        L_0x0057:
            r1 = move-exception
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.service.impl.BioServiceManagerImpl.getBioService(java.lang.String):java.lang.Object");
    }

    public <T extends BioService> T putBioService(String str, Class<T> cls) {
        Throwable th;
        T t;
        try {
            t = (BioService) cls.newInstance();
            try {
                t.create(this);
                this.b.put(str, t);
            } catch (Throwable th2) {
                th = th2;
                BioLog.e(th);
                return t;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            t = null;
            th = th4;
            BioLog.e(th);
            return t;
        }
        return t;
    }

    public void destroy() {
        if (this.c != null) {
            for (String str : this.c.keySet()) {
                this.c.get(str).destroy();
            }
            this.c.clear();
        }
        if (this.b != null) {
            for (String str2 : this.b.keySet()) {
                this.b.get(str2).destroy();
            }
            this.b.clear();
        }
        if (this.e != null) {
            for (String str3 : this.e.keySet()) {
                this.e.get(str3).destroy();
            }
            this.e.clear();
        }
        if (this.f != null) {
            this.f.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
    }

    public String startBioActivity(BioAppDescription bioAppDescription, MicroModule microModule) {
        boolean z;
        int i;
        boolean z2;
        if (bioAppDescription == null) {
            return "";
        }
        String str = "bio_type_" + bioAppDescription.getBioType() + "_" + bioAppDescription.getBioAction();
        BioLog.i("appID:" + str);
        if (!this.d.containsKey(str)) {
            return "";
        }
        BioAppDescription bioAppDescription2 = this.d.get(str);
        bioAppDescription.setAppName(bioAppDescription2.getAppName());
        bioAppDescription.setAppInterfaceName(bioAppDescription2.getAppInterfaceName());
        String appInterfaceName = bioAppDescription.getAppInterfaceName();
        if (StringUtil.isNullorEmpty(appInterfaceName)) {
            throw new BioIllegalArgumentException();
        }
        Intent intent = new Intent();
        intent.setClassName(this.mContext, appInterfaceName);
        Map<String, String> extProperty = bioAppDescription.getExtProperty();
        if (extProperty == null || extProperty.isEmpty() || !extProperty.containsKey(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND) || !Boolean.parseBoolean(extProperty.get(BioDetector.EXT_KEY_AUTH_IN_BACKGROUND))) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            i = 805339136;
        } else {
            i = 805306368;
        }
        intent.setFlags(i);
        intent.putExtra(Constant.BIOLOGY_INTENT_ACTION_INFO, bioAppDescription.getTag());
        if (Runtime.isRunningOnQuinox(this.mContext)) {
            try {
                z2 = Runtime.startActivity(intent);
            } catch (Throwable th) {
                BioLog.w(th);
                z2 = false;
            }
            BioLog.d("Runtime.startActivity(intent=" + intent + ") : bRet=" + z2);
        } else {
            z2 = false;
        }
        if (!z2) {
            if (this.mContext != null) {
                this.mContext.startActivity(intent);
                return str;
            }
            BioLog.e((String) "start APP context null");
        }
        return str;
    }

    public int preLoad() {
        BioLog.i("preload:" + g);
        if (g) {
            return 0;
        }
        g = true;
        new Thread(new a(this), "loadingResource").start();
        return 1;
    }
}

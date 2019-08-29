package com.alipay.mobile.security.bio.runtime;

import android.content.Context;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.mobile.security.bio.service.BioMetaInfo;
import com.alipay.mobile.security.bio.service.BioServiceDescription;
import com.alipay.mobile.security.bio.service.local.NotImplementedException;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.StringUtil;
import java.util.List;

public class ModuleDesc {
    @JSONField(name = "local_services")
    public List<LocalServiceDesc> localServiceDescs;
    public List<BioMetaInfo> mBioMetaInfoList;
    public List<BioServiceDescription> mBioServiceDescription;
    public String mBundleName;
    @JSONField(name = "meta_info")
    public List<String> metaInfoList;

    public static class LocalServiceDesc {
        @JSONField(name = "class")
        public String className;
        @JSONField(name = "interface")
        public String interfaceName;
        @JSONField(name = "essential")
        public boolean isEssential = true;
        @JSONField(name = "lazy")
        public boolean isLazy = true;

        public String toString() {
            return "LocalServiceDesc{interfaceName='" + this.interfaceName + '\'' + ", isEssential=" + this.isEssential + ", className='" + this.className + '\'' + ", isLazy=" + this.isLazy + '}';
        }
    }

    public String toString() {
        return "ModuleDesc{metaInfoList=" + StringUtil.collection2String(this.metaInfoList) + ", localServiceDescs=" + StringUtil.collection2String(this.localServiceDescs) + '}';
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00f3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alipay.mobile.security.bio.runtime.ModuleDesc create(android.content.Context r4, boolean r5, com.alipay.mobile.security.bio.runtime.FrameworkDesc.ConfigDesc r6) {
        /*
            r1 = 0
            java.lang.String r0 = r6.bundleName
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00f5
            boolean r0 = r6.dynamic
            if (r0 == 0) goto L_0x00f5
            if (r5 == 0) goto L_0x00f5
            java.lang.String r0 = r6.bundleName
            android.content.res.Resources r0 = com.alipay.mobile.security.bio.runtime.Runtime.getResourcesByBundleName(r0)
            if (r0 == 0) goto L_0x00f5
            java.lang.String r2 = r6.configFileName
            byte[] r2 = com.alipay.mobile.security.bio.utils.FileUtil.getAssetsData(r0, r2)
            if (r2 == 0) goto L_0x00f5
            int r0 = r2.length
            if (r0 <= 0) goto L_0x00f5
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0027:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x0041
            android.content.res.Resources r2 = r4.getResources()
            java.lang.String r3 = r6.configFileName
            byte[] r2 = com.alipay.mobile.security.bio.utils.FileUtil.getAssetsData(r2, r3)
            if (r2 == 0) goto L_0x0041
            int r3 = r2.length
            if (r3 <= 0) goto L_0x0041
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0041:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 != 0) goto L_0x00f3
            java.lang.Class<com.alipay.mobile.security.bio.runtime.ModuleDesc> r1 = com.alipay.mobile.security.bio.runtime.ModuleDesc.class
            java.lang.Object r0 = com.alibaba.fastjson.JSON.parseObject(r0, r1)
            com.alipay.mobile.security.bio.runtime.ModuleDesc r0 = (com.alipay.mobile.security.bio.runtime.ModuleDesc) r0
            java.lang.String r1 = r6.bundleName
            r0.mBundleName = r1
            java.util.List<java.lang.String> r1 = r0.metaInfoList
            if (r1 == 0) goto L_0x008c
            java.util.List<java.lang.String> r1 = r0.metaInfoList
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x008c
            java.util.ArrayList r1 = new java.util.ArrayList
            java.util.List<java.lang.String> r2 = r0.metaInfoList
            int r2 = r2.size()
            r1.<init>(r2)
            r0.mBioMetaInfoList = r1
            java.util.List<java.lang.String> r1 = r0.metaInfoList
            java.util.Iterator r2 = r1.iterator()
        L_0x0072:
            boolean r1 = r2.hasNext()
            if (r1 == 0) goto L_0x008c
            java.lang.Object r1 = r2.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r3 = r6.bundleName
            com.alipay.mobile.security.bio.service.BioMetaInfo r1 = a(r4, r5, r1, r3)
            if (r1 == 0) goto L_0x0072
            java.util.List<com.alipay.mobile.security.bio.service.BioMetaInfo> r3 = r0.mBioMetaInfoList
            r3.add(r1)
            goto L_0x0072
        L_0x008c:
            java.util.List<com.alipay.mobile.security.bio.runtime.ModuleDesc$LocalServiceDesc> r1 = r0.localServiceDescs
            if (r1 == 0) goto L_0x00f4
            java.util.List<com.alipay.mobile.security.bio.runtime.ModuleDesc$LocalServiceDesc> r1 = r0.localServiceDescs
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x00f4
            java.util.ArrayList r1 = new java.util.ArrayList
            java.util.List<com.alipay.mobile.security.bio.runtime.ModuleDesc$LocalServiceDesc> r2 = r0.localServiceDescs
            int r2 = r2.size()
            r1.<init>(r2)
            r0.mBioServiceDescription = r1
            java.util.List<com.alipay.mobile.security.bio.runtime.ModuleDesc$LocalServiceDesc> r1 = r0.localServiceDescs
            java.util.Iterator r2 = r1.iterator()
        L_0x00ab:
            boolean r1 = r2.hasNext()
            if (r1 == 0) goto L_0x00f4
            java.lang.Object r1 = r2.next()
            com.alipay.mobile.security.bio.runtime.ModuleDesc$LocalServiceDesc r1 = (com.alipay.mobile.security.bio.runtime.ModuleDesc.LocalServiceDesc) r1
            boolean r3 = r1.isEssential
            if (r3 == 0) goto L_0x00c0
            java.lang.String r3 = r1.className
            android.text.TextUtils.isEmpty(r3)
        L_0x00c0:
            java.lang.String r3 = r1.className
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00d0
            java.lang.String r3 = r1.interfaceName
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x00e5
        L_0x00d0:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Invalid LocalServiceDesc: "
            r2.<init>(r3)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00e5:
            java.lang.String r3 = r6.bundleName
            com.alipay.mobile.security.bio.service.BioServiceDescription r1 = a(r4, r5, r1, r3)
            if (r1 == 0) goto L_0x00ab
            java.util.List<com.alipay.mobile.security.bio.service.BioServiceDescription> r3 = r0.mBioServiceDescription
            r3.add(r1)
            goto L_0x00ab
        L_0x00f3:
            r0 = r1
        L_0x00f4:
            return r0
        L_0x00f5:
            r0 = r1
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.runtime.ModuleDesc.create(android.content.Context, boolean, com.alipay.mobile.security.bio.runtime.FrameworkDesc$ConfigDesc):com.alipay.mobile.security.bio.runtime.ModuleDesc");
    }

    private static BioMetaInfo a(Context context, boolean z, String str, String str2) {
        try {
            return (BioMetaInfo) Runtime.loadClass(context, z, str, str2).newInstance();
        } catch (Throwable th) {
            BioLog.e(Runtime.TAG, "Failed to createMetaInfo: " + str, th);
            return null;
        }
    }

    private static BioServiceDescription a(Context context, boolean z, LocalServiceDesc localServiceDesc, String str) {
        BioServiceDescription bioServiceDescription;
        Throwable th;
        try {
            Class<?> loadClass = Runtime.loadClass(context, z, localServiceDesc.className, str);
            if (loadClass == null) {
                throw new NotImplementedException();
            }
            bioServiceDescription = new BioServiceDescription();
            try {
                bioServiceDescription.setClazz(loadClass);
                bioServiceDescription.setInterfaceName(localServiceDesc.interfaceName);
                bioServiceDescription.setLazy(localServiceDesc.isLazy);
            } catch (Throwable th2) {
                th = th2;
            }
            return bioServiceDescription;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bioServiceDescription = null;
            th = th4;
            BioLog.e(Runtime.TAG, "Failed to create LocalServiceDescription: LocalServiceDesc=" + localServiceDesc + ", isOnQuinox=" + z + ", bundleName=" + str, th);
            return bioServiceDescription;
        }
    }
}

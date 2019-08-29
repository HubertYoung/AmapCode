package defpackage;

import android.support.annotation.NonNull;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent;
import com.alibaba.wireless.security.open.umid.IUMIDComponent;
import com.alibaba.wireless.security.open.umid.IUMIDInitListenerEx;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: fgv reason: default package */
/* compiled from: ProductSignImpl */
public final class fgv extends fgq {
    private SecurityGuardManager c = null;

    public final void a(@NonNull ffd ffd) {
        super.a(ffd);
        String c2 = c();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            this.c = SecurityGuardManager.getInstance(this.a.e);
            String appKeyByIndex = this.c.getStaticDataStoreComp().getAppKeyByIndex(ffd.k, b());
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(c2);
                sb.append(" [init]ISign get appKey=");
                sb.append(appKeyByIndex);
                sb.append(",authCode=");
                sb.append(b());
                TBSdkLog.b("mtopsdk.ProductSignImpl", sb.toString());
            }
            String b = b();
            final String c3 = c();
            try {
                IUMIDComponent uMIDComp = this.c.getUMIDComp();
                if (uMIDComp != null) {
                    uMIDComp.initUMID(appKeyByIndex, a(), b, new IUMIDInitListenerEx() {
                        public final void onUMIDInitFinishedEx(String str, int i) {
                            if (i == 200) {
                                fgy.a(c3, "umt", str);
                                StringBuilder sb = new StringBuilder();
                                sb.append(c3);
                                sb.append(" [initUmidToken]IUMIDComponent initUMID succeed,UMID token=");
                                sb.append(str);
                                TBSdkLog.b("mtopsdk.ProductSignImpl", sb.toString());
                                return;
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(c3);
                            sb2.append(" [initUmidToken]IUMIDComponent initUMID error,resultCode :");
                            sb2.append(i);
                            TBSdkLog.c("mtopsdk.ProductSignImpl", sb2.toString());
                        }
                    });
                }
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c3);
                sb2.append(" [initUmidToken]IUMIDComponent initUMID error");
                TBSdkLog.a((String) "mtopsdk.ProductSignImpl", sb2.toString(), (Throwable) e);
            }
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c2);
                sb3.append(" [init]ISign init SecurityGuard succeed.init time=");
                sb3.append(System.currentTimeMillis() - currentTimeMillis);
                TBSdkLog.b("mtopsdk.ProductSignImpl", sb3.toString());
            }
        } catch (SecException e2) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(c2);
            sb4.append(" [init]ISign init SecurityGuard error.errorCode=");
            sb4.append(e2.getErrorCode());
            TBSdkLog.b((String) "mtopsdk.ProductSignImpl", sb4.toString(), (Throwable) e2);
        } catch (Throwable th) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(c2);
            sb5.append(" [init]ISign init SecurityGuard error.");
            TBSdkLog.b((String) "mtopsdk.ProductSignImpl", sb5.toString(), th);
        }
    }

    public final String a(a aVar) {
        try {
            return this.c.getStaticDataStoreComp().getAppKeyByIndex(aVar.a, aVar.b);
        } catch (SecException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(c());
            sb.append(" [appKey]getAppKeyByIndex error.errorCode=");
            sb.append(e.getErrorCode());
            sb.append(",index=");
            sb.append(aVar.a);
            sb.append(",authCode=");
            sb.append(aVar.b);
            TBSdkLog.b((String) "mtopsdk.ProductSignImpl", sb.toString(), (Throwable) e);
            return null;
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(c());
            sb2.append(" [appKey]getAppKeyByIndex error.index=");
            sb2.append(aVar.a);
            sb2.append(",authCode=");
            sb2.append(aVar.b);
            TBSdkLog.b((String) "mtopsdk.ProductSignImpl", sb2.toString(), (Throwable) e2);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x01af, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x01b0, code lost:
        r2 = r1;
        r17 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x01cd, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x01ce, code lost:
        r2 = r1;
        r5 = r4;
        r1 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01af A[ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:8:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.util.HashMap<java.lang.String, java.lang.String> r21, java.lang.String r22, java.lang.String r23) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            r3 = r22
            java.lang.String r4 = r20.c()
            r5 = 0
            if (r3 != 0) goto L_0x002b
            java.lang.String r3 = "SG_ERROR_CODE"
            java.lang.String r6 = "AppKey is null"
            r2.put(r3, r6)
            java.lang.String r2 = "mtopsdk.ProductSignImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            java.lang.String r4 = " [getMtopApiSign] AppKey is null."
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r3)
            return r5
        L_0x002b:
            com.alibaba.wireless.security.open.SecurityGuardManager r6 = r1.c
            if (r6 != 0) goto L_0x004d
            java.lang.String r3 = "SG_ERROR_CODE"
            java.lang.String r6 = "SGManager is null"
            r2.put(r3, r6)
            java.lang.String r2 = "mtopsdk.ProductSignImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            java.lang.String r4 = " [getMtopApiSign]SecurityGuardManager is null,please call ISign init()"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r3)
            return r5
        L_0x004d:
            com.alibaba.wireless.security.open.SecurityGuardParamContext r6 = new com.alibaba.wireless.security.open.SecurityGuardParamContext     // Catch:{ SecException -> 0x01cd, Throwable -> 0x01af }
            r6.<init>()     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            r6.appKey = r3     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            r7 = 7
            r6.requestType = r7     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            int r7 = r21.size()     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            if (r7 > 0) goto L_0x0063
            r17 = r4
            r18 = r6
            goto L_0x0178
        L_0x0063:
            java.lang.String r7 = "data"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r8 = "reqbiz-ext"
            java.lang.Object r8 = r2.get(r8)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r9 = "x-features"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r10 = "lat"
            java.lang.Object r10 = r2.get(r10)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r11 = "api"
            java.lang.Object r11 = r2.get(r11)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r12 = "lng"
            java.lang.Object r12 = r2.get(r12)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r13 = "sid"
            java.lang.Object r13 = r2.get(r13)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r14 = "uid"
            java.lang.Object r14 = r2.get(r14)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r15 = "ttid"
            java.lang.Object r15 = r2.get(r15)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r5 = "v"
            java.lang.Object r5 = r2.get(r5)     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ SecException -> 0x01aa, Throwable -> 0x01af }
            r17 = r4
            java.lang.String r4 = "t"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ SecException -> 0x01a7, Throwable -> 0x01a4 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ SecException -> 0x01a7, Throwable -> 0x01a4 }
            java.lang.String r1 = "utdid"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r18 = r6
            java.lang.String r6 = "pv"
            java.lang.Object r6 = r2.get(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r19 = r6
            r6 = 128(0x80, float:1.794E-43)
            r2.<init>(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = defpackage.fgx.b(r7)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = "&"
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = defpackage.fgx.a(r8)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = "&"
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r9)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = "&"
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = defpackage.fgx.a(r10)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r6 = "&"
            r2.append(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r11)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = defpackage.fgx.a(r12)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = defpackage.fgx.a(r13)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = defpackage.fgx.a(r14)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = defpackage.fgx.a(r15)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r5)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r4)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r3 = "&"
            r2.append(r3)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r1 = defpackage.fgx.a(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r6 = r19
            java.lang.String r1 = defpackage.fgx.a(r6)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2.append(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r1 = 2
            r5.<init>(r1)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            java.lang.String r1 = "INPUT"
            java.lang.String r2 = r2.toString()     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r5.put(r1, r2)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
        L_0x0178:
            java.lang.String r1 = "ATLAS"
            java.lang.String r2 = "c"
            r5.put(r1, r2)     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r1 = r18
            r1.paramMap = r5     // Catch:{ SecException -> 0x019d, Throwable -> 0x0199 }
            r2 = r20
            com.alibaba.wireless.security.open.SecurityGuardManager r3 = r2.c     // Catch:{ SecException -> 0x0197, Throwable -> 0x0195 }
            com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent r3 = r3.getSecureSignatureComp()     // Catch:{ SecException -> 0x0197, Throwable -> 0x0195 }
            r4 = r23
            java.lang.String r5 = r3.signRequest(r1, r4)     // Catch:{ SecException -> 0x0197, Throwable -> 0x0195 }
            r16 = r5
            goto L_0x01fb
        L_0x0195:
            r0 = move-exception
            goto L_0x01b3
        L_0x0197:
            r0 = move-exception
            goto L_0x01a0
        L_0x0199:
            r0 = move-exception
            r2 = r20
            goto L_0x01b3
        L_0x019d:
            r0 = move-exception
            r2 = r20
        L_0x01a0:
            r1 = r0
            r5 = r17
            goto L_0x01d1
        L_0x01a4:
            r0 = move-exception
            r2 = r1
            goto L_0x01b3
        L_0x01a7:
            r0 = move-exception
            r2 = r1
            goto L_0x01a0
        L_0x01aa:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r5 = r4
            goto L_0x01d1
        L_0x01af:
            r0 = move-exception
            r2 = r1
            r17 = r4
        L_0x01b3:
            r1 = r0
            java.lang.String r3 = "mtopsdk.ProductSignImpl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = r17
            r4.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            mtopsdk.common.util.TBSdkLog.b(r3, r4, r1)
            goto L_0x01f9
        L_0x01cd:
            r0 = move-exception
            r2 = r1
            r5 = r4
            r1 = r0
        L_0x01d1:
            int r3 = r1.getErrorCode()
            java.lang.String r4 = "SG_ERROR_CODE"
            java.lang.String r6 = java.lang.String.valueOf(r3)
            r7 = r21
            r7.put(r4, r6)
            java.lang.String r4 = "mtopsdk.ProductSignImpl"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error,errorCode="
            r6.append(r5)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            mtopsdk.common.util.TBSdkLog.b(r4, r3, r1)
        L_0x01f9:
            r16 = 0
        L_0x01fb:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgv.a(java.util.HashMap, java.lang.String, java.lang.String):java.lang.String");
    }

    public final String a(String str, String str2, String str3, int i) {
        String str4;
        if (fdd.b(str2) || fdd.b(str)) {
            return null;
        }
        try {
            str4 = ((ISecurityBodyComponent) this.c.getInterface(ISecurityBodyComponent.class)).getSecurityBodyDataEx(str, str2, str3, null, i, a());
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append(c());
            sb.append(" [getSecBodyDataEx] ISecurityBodyComponent getSecurityBodyDataEx  error,flag=");
            sb.append(i);
            TBSdkLog.b((String) "mtopsdk.ProductSignImpl", sb.toString(), th);
            str4 = null;
        }
        return str4;
    }
}

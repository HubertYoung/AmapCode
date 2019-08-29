package defpackage;

import android.support.annotation.NonNull;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;

/* renamed from: fgu reason: default package */
/* compiled from: OpenSignImpl */
public final class fgu extends fgq {
    private SecurityGuardManager c = null;

    public final void a(@NonNull ffd ffd) {
        super.a(ffd);
        String c2 = c();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            this.c = SecurityGuardManager.getInstance(this.a.e);
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb = new StringBuilder();
                sb.append(c2);
                sb.append(" [init]ISign init succeed.init time=");
                sb.append(System.currentTimeMillis() - currentTimeMillis);
                TBSdkLog.b("mtopsdk.OpenSignImpl", sb.toString());
            }
        } catch (SecException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(c2);
            sb2.append(" [init]init securityguard error.errorCode=");
            sb2.append(e.getErrorCode());
            TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb2.toString(), (Throwable) e);
        } catch (Throwable th) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(c2);
            sb3.append(" [init]init securityguard error.");
            TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb3.toString(), th);
        }
    }

    public final String a(a aVar) {
        String str;
        SecException e;
        Exception e2;
        String c2 = c();
        try {
            str = this.c.getStaticDataStoreComp().getAppKeyByIndex(aVar.a, aVar.b);
            try {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(c2);
                    sb.append(" [getAppKey]ISign getAppKey.index=");
                    sb.append(aVar.a);
                    sb.append(",authCode=");
                    sb.append(aVar.b);
                    sb.append(",appKey=");
                    sb.append(str);
                    TBSdkLog.b("mtopsdk.OpenSignImpl", sb.toString());
                }
            } catch (SecException e3) {
                e = e3;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c2);
                sb2.append(" [getAppKey]ISign getAppKey error.errorCode=");
                sb2.append(e.getErrorCode());
                sb2.append(",index=");
                sb2.append(aVar.a);
                sb2.append(",authCode=");
                sb2.append(aVar.b);
                TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb2.toString(), (Throwable) e);
                return str;
            } catch (Exception e4) {
                e2 = e4;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c2);
                sb3.append(" [getAppKey]ISign getAppKey error.index=");
                sb3.append(aVar.a);
                sb3.append(",authCode=");
                sb3.append(aVar.b);
                TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb3.toString(), (Throwable) e2);
                return str;
            }
        } catch (SecException e5) {
            e = e5;
            str = null;
            StringBuilder sb22 = new StringBuilder();
            sb22.append(c2);
            sb22.append(" [getAppKey]ISign getAppKey error.errorCode=");
            sb22.append(e.getErrorCode());
            sb22.append(",index=");
            sb22.append(aVar.a);
            sb22.append(",authCode=");
            sb22.append(aVar.b);
            TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb22.toString(), (Throwable) e);
            return str;
        } catch (Exception e6) {
            e2 = e6;
            str = null;
            StringBuilder sb32 = new StringBuilder();
            sb32.append(c2);
            sb32.append(" [getAppKey]ISign getAppKey error.index=");
            sb32.append(aVar.a);
            sb32.append(",authCode=");
            sb32.append(aVar.b);
            TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb32.toString(), (Throwable) e2);
            return str;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x017b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x017c, code lost:
        r17 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0198, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0199, code lost:
        r5 = r4;
        r2 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x017b A[ExcHandler: Throwable (th java.lang.Throwable), Splitter:B:8:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.util.HashMap<java.lang.String, java.lang.String> r20, java.lang.String r21, java.lang.String r22) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r21
            java.lang.String r4 = r19.c()
            r5 = 0
            if (r3 != 0) goto L_0x002b
            java.lang.String r3 = "SG_ERROR_CODE"
            java.lang.String r6 = "AppKey is null"
            r2.put(r3, r6)
            java.lang.String r2 = "mtopsdk.OpenSignImpl"
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
            java.lang.String r2 = "mtopsdk.OpenSignImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            java.lang.String r4 = " [getMtopApiSign]SecurityGuardManager is null,please call ISign init()"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r3)
            return r5
        L_0x004d:
            com.alibaba.wireless.security.open.SecurityGuardParamContext r6 = new com.alibaba.wireless.security.open.SecurityGuardParamContext     // Catch:{ SecException -> 0x0198, Throwable -> 0x017b }
            r6.<init>()     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            r6.appKey = r3     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            r7 = 7
            r6.requestType = r7     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            int r7 = r20.size()     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            if (r7 > 0) goto L_0x0061
            r17 = r4
            goto L_0x014c
        L_0x0061:
            java.lang.String r7 = "api"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r8 = "v"
            java.lang.Object r8 = r2.get(r8)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r9 = "data"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r10 = "accessToken"
            java.lang.Object r10 = r2.get(r10)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r11 = "t"
            java.lang.Object r11 = r2.get(r11)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r12 = "utdid"
            java.lang.Object r12 = r2.get(r12)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r13 = "pv"
            java.lang.Object r13 = r2.get(r13)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r14 = "x-features"
            java.lang.Object r14 = r2.get(r14)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r15 = "ttid"
            java.lang.Object r15 = r2.get(r15)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r5 = "sid"
            java.lang.Object r5 = r2.get(r5)     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ SecException -> 0x0177, Throwable -> 0x017b }
            r17 = r4
            java.lang.String r4 = "wua"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            r1 = 64
            r2.<init>(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r7)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r8)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.b(r9)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r3)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r10)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r11)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r12)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r13)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r14)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r15)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r5)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = defpackage.fgx.a(r4)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r2.append(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r1 = 2
            r5.<init>(r1)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            java.lang.String r1 = "INPUT"
            java.lang.String r2 = r2.toString()     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r5.put(r1, r2)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
        L_0x014c:
            java.lang.String r1 = "ATLAS"
            java.lang.String r2 = "a"
            r5.put(r1, r2)     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r6.paramMap = r5     // Catch:{ SecException -> 0x0170, Throwable -> 0x016c }
            r1 = r19
            com.alibaba.wireless.security.open.SecurityGuardManager r2 = r1.c     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent r2 = r2.getSecureSignatureComp()     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            java.lang.String r3 = r19.b()     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            java.lang.String r5 = r2.signRequest(r6, r3)     // Catch:{ SecException -> 0x016a, Throwable -> 0x0168 }
            r16 = r5
            goto L_0x01c5
        L_0x0168:
            r0 = move-exception
            goto L_0x017e
        L_0x016a:
            r0 = move-exception
            goto L_0x0173
        L_0x016c:
            r0 = move-exception
            r1 = r19
            goto L_0x017e
        L_0x0170:
            r0 = move-exception
            r1 = r19
        L_0x0173:
            r2 = r0
            r5 = r17
            goto L_0x019b
        L_0x0177:
            r0 = move-exception
            r2 = r0
            r5 = r4
            goto L_0x019b
        L_0x017b:
            r0 = move-exception
            r17 = r4
        L_0x017e:
            r2 = r0
            java.lang.String r3 = "mtopsdk.OpenSignImpl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = r17
            r4.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            mtopsdk.common.util.TBSdkLog.b(r3, r4, r2)
            goto L_0x01c3
        L_0x0198:
            r0 = move-exception
            r5 = r4
            r2 = r0
        L_0x019b:
            int r3 = r2.getErrorCode()
            java.lang.String r4 = "SG_ERROR_CODE"
            java.lang.String r6 = java.lang.String.valueOf(r3)
            r7 = r20
            r7.put(r4, r6)
            java.lang.String r4 = "mtopsdk.OpenSignImpl"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error,errorCode="
            r6.append(r5)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            mtopsdk.common.util.TBSdkLog.b(r4, r3, r2)
        L_0x01c3:
            r16 = 0
        L_0x01c5:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgu.a(java.util.HashMap, java.lang.String, java.lang.String):java.lang.String");
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
            sb.append("[getSecBodyDataEx] ISecurityBodyComponent getSecurityBodyDataEx  error,flag=");
            sb.append(i);
            TBSdkLog.b((String) "mtopsdk.OpenSignImpl", sb.toString(), th);
            str4 = null;
        }
        return str4;
    }
}

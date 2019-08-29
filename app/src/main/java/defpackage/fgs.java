package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import com.alibaba.wireless.security.open.SecException;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent;
import com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent.IAVMPGenericInstance;
import com.alibaba.wireless.security.open.securitybody.ISecurityBodyComponent;
import com.alibaba.wireless.security.open.umid.IUMIDComponent;
import com.alibaba.wireless.security.open.umid.IUMIDInitListenerEx;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.security.util.SignStatistics;

/* renamed from: fgs reason: default package */
/* compiled from: InnerSignImpl */
public class fgs extends fgq {
    private SecurityGuardManager c = null;
    private volatile IAVMPGenericInstance d;

    public final void a(@NonNull ffd ffd) {
        super.a(ffd);
        final String c2 = c();
        try {
            SignStatistics.a(ffd.w);
            long currentTimeMillis = System.currentTimeMillis();
            this.c = SecurityGuardManager.getInstance(this.a.e);
            String a = a(ffd.k, b());
            String b = b();
            final String c3 = c();
            try {
                IUMIDComponent uMIDComp = this.c.getUMIDComp();
                if (uMIDComp != null) {
                    int a2 = a();
                    if (b == null) {
                        b = "";
                    }
                    uMIDComp.initUMID(a, a2, b, new IUMIDInitListenerEx() {
                        public final void onUMIDInitFinishedEx(String str, int i) {
                            if (i == 200) {
                                fgy.a(c3, "umt", str);
                                StringBuilder sb = new StringBuilder();
                                sb.append(c3);
                                sb.append(" [initUmidToken]IUMIDComponent initUMID succeed,UMID token=");
                                sb.append(str);
                                TBSdkLog.b("mtopsdk.InnerSignImpl", sb.toString());
                                return;
                            }
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(c3);
                            sb2.append(" [initUmidToken]IUMIDComponent initUMID error,resultCode :");
                            sb2.append(i);
                            TBSdkLog.c("mtopsdk.InnerSignImpl", sb2.toString());
                        }
                    });
                }
            } catch (SecException e) {
                int errorCode = e.getErrorCode();
                SignStatistics.a("InitUMID", String.valueOf(errorCode), "");
                StringBuilder sb = new StringBuilder();
                sb.append(c3);
                sb.append("[initUmidToken]IUMIDComponent initUMID error,errorCode=");
                sb.append(errorCode);
                TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb.toString(), (Throwable) e);
            } catch (Exception e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c3);
                sb2.append("[initUmidToken]IUMIDComponent initUMID error.");
                TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb2.toString(), (Throwable) e2);
            }
            final Context context = this.a.e;
            ffy.a(new Runnable() {
                public final void run() {
                    try {
                        fgs.this.a(context);
                    } catch (Throwable th) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(c2);
                        sb.append(" [init]getAVMPInstance error when async init AVMP.");
                        TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb.toString(), th);
                    }
                }
            });
            if (TBSdkLog.a(LogEnable.InfoEnable)) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c2);
                sb3.append(" [init]ISign init SecurityGuard succeed.init time=");
                sb3.append(System.currentTimeMillis() - currentTimeMillis);
                TBSdkLog.b("mtopsdk.InnerSignImpl", sb3.toString());
            }
        } catch (SecException e3) {
            int errorCode2 = e3.getErrorCode();
            SignStatistics.a("SGManager", String.valueOf(errorCode2), "");
            StringBuilder sb4 = new StringBuilder();
            sb4.append(c2);
            sb4.append(" [init]ISign init SecurityGuard error.errorCode=");
            sb4.append(errorCode2);
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb4.toString(), (Throwable) e3);
        } catch (Exception e4) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(c2);
            sb5.append(" [init]ISign init SecurityGuard error.");
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb5.toString(), (Throwable) e4);
        }
    }

    public final String a(a aVar) {
        return a(aVar.a, aVar.b);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:50:0x01e1, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01e2, code lost:
        r17 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01fe, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01ff, code lost:
        r5 = r4;
        r2 = r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01e1 A[ExcHandler: Exception (e java.lang.Exception), Splitter:B:8:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.util.HashMap<java.lang.String, java.lang.String> r22, java.lang.String r23, java.lang.String r24) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r3 = r23
            java.lang.String r4 = r21.c()
            r5 = 0
            if (r3 != 0) goto L_0x002b
            java.lang.String r3 = "SG_ERROR_CODE"
            java.lang.String r6 = "AppKey is null"
            r2.put(r3, r6)
            java.lang.String r2 = "mtopsdk.InnerSignImpl"
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
            java.lang.String r2 = "mtopsdk.InnerSignImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r4)
            java.lang.String r4 = " [getMtopApiSign]SecurityGuardManager is null,please call ISign init()"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            mtopsdk.common.util.TBSdkLog.d(r2, r3)
            return r5
        L_0x004d:
            com.alibaba.wireless.security.open.SecurityGuardParamContext r6 = new com.alibaba.wireless.security.open.SecurityGuardParamContext     // Catch:{ SecException -> 0x01fe, Exception -> 0x01e1 }
            r6.<init>()     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            r6.appKey = r3     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            r7 = 7
            r6.requestType = r7     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            int r7 = r22.size()     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            if (r7 > 0) goto L_0x0063
            r17 = r4
            r18 = r6
            goto L_0x01a6
        L_0x0063:
            java.lang.String r7 = "utdid"
            java.lang.Object r7 = r2.get(r7)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r9 = "uid"
            java.lang.Object r9 = r2.get(r9)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r10 = "reqbiz-ext"
            java.lang.Object r10 = r2.get(r10)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r11 = "data"
            java.lang.Object r11 = r2.get(r11)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r12 = "t"
            java.lang.Object r12 = r2.get(r12)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r13 = "api"
            java.lang.Object r13 = r2.get(r13)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r14 = "v"
            java.lang.Object r14 = r2.get(r14)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r15 = "sid"
            java.lang.Object r15 = r2.get(r15)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r15 = (java.lang.String) r15     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r5 = "ttid"
            java.lang.Object r5 = r2.get(r5)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r8 = "deviceId"
            java.lang.Object r8 = r2.get(r8)     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ SecException -> 0x01dd, Exception -> 0x01e1 }
            r17 = r4
            java.lang.String r4 = "lat"
            java.lang.Object r4 = r2.get(r4)     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            r18 = r6
            java.lang.String r6 = "lng"
            java.lang.Object r6 = r2.get(r6)     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            java.lang.String r1 = "extdata"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r19 = r1
            java.lang.String r1 = "x-features"
            java.lang.Object r1 = r2.get(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r20 = r1
            r1 = 64
            r2.<init>(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r7)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r9)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r10)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r3)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.b(r11)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r12)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r13)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r14)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r15)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r5)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r8)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r4)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = defpackage.fgx.a(r6)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r1 = r19
            boolean r3 = defpackage.fdd.a(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            if (r3 == 0) goto L_0x0192
            r2.append(r1)     // Catch:{ SecException -> 0x018a, Exception -> 0x0184 }
            java.lang.String r1 = "&"
            r2.append(r1)     // Catch:{ SecException -> 0x018a, Exception -> 0x0184 }
            goto L_0x0192
        L_0x0184:
            r0 = move-exception
            r2 = r0
            r1 = r21
            goto L_0x01e5
        L_0x018a:
            r0 = move-exception
            r2 = r0
            r5 = r17
            r1 = r21
            goto L_0x0201
        L_0x0192:
            r1 = r20
            r2.append(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.util.HashMap r5 = new java.util.HashMap     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r1 = 2
            r5.<init>(r1)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            java.lang.String r1 = "INPUT"
            java.lang.String r2 = r2.toString()     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
            r5.put(r1, r2)     // Catch:{ SecException -> 0x01d9, Exception -> 0x01d5 }
        L_0x01a6:
            if (r5 == 0) goto L_0x01c0
            r1 = r21
            int r2 = r21.a()     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            r3 = 2
            if (r3 != r2) goto L_0x01c2
            java.lang.String r2 = "ATLAS"
            java.lang.String r3 = "daily"
            r5.put(r2, r3)     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            goto L_0x01c2
        L_0x01b9:
            r0 = move-exception
            goto L_0x01e4
        L_0x01bb:
            r0 = move-exception
        L_0x01bc:
            r2 = r0
            r5 = r17
            goto L_0x0201
        L_0x01c0:
            r1 = r21
        L_0x01c2:
            r2 = r18
            r2.paramMap = r5     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            com.alibaba.wireless.security.open.SecurityGuardManager r3 = r1.c     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent r3 = r3.getSecureSignatureComp()     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            r4 = r24
            java.lang.String r5 = r3.signRequest(r2, r4)     // Catch:{ SecException -> 0x01bb, Exception -> 0x01b9 }
            r16 = r5
            goto L_0x0236
        L_0x01d5:
            r0 = move-exception
            r1 = r21
            goto L_0x01e4
        L_0x01d9:
            r0 = move-exception
            r1 = r21
            goto L_0x01bc
        L_0x01dd:
            r0 = move-exception
            r2 = r0
            r5 = r4
            goto L_0x0201
        L_0x01e1:
            r0 = move-exception
            r17 = r4
        L_0x01e4:
            r2 = r0
        L_0x01e5:
            java.lang.String r3 = "mtopsdk.InnerSignImpl"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = r17
            r4.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            mtopsdk.common.util.TBSdkLog.b(r3, r4, r2)
            goto L_0x0234
        L_0x01fe:
            r0 = move-exception
            r5 = r4
            r2 = r0
        L_0x0201:
            int r3 = r2.getErrorCode()
            java.lang.String r4 = "SignMtopRequest"
            java.lang.String r6 = java.lang.String.valueOf(r3)
            java.lang.String r7 = ""
            mtopsdk.security.util.SignStatistics.a(r4, r6, r7)
            java.lang.String r4 = "SG_ERROR_CODE"
            java.lang.String r6 = java.lang.String.valueOf(r3)
            r7 = r22
            r7.put(r4, r6)
            java.lang.String r4 = "mtopsdk.InnerSignImpl"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r5)
            java.lang.String r5 = " [getMtopApiSign] ISecureSignatureComponent signRequest error,errorCode="
            r6.append(r5)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            mtopsdk.common.util.TBSdkLog.b(r4, r3, r2)
        L_0x0234:
            r16 = 0
        L_0x0236:
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fgs.a(java.util.HashMap, java.lang.String, java.lang.String):java.lang.String");
    }

    public final String a(String str, String str2, String str3, int i) {
        try {
            return ((ISecurityBodyComponent) this.c.getInterface(ISecurityBodyComponent.class)).getSecurityBodyDataEx(str, str2, str3, null, i, a());
        } catch (SecException e) {
            SignStatistics.a("GetSecBody", String.valueOf(e.getErrorCode()), String.valueOf(i));
            StringBuilder sb = new StringBuilder();
            sb.append(c());
            sb.append(" [getSecBodyDataEx] ISecurityBodyComponent getSecurityBodyDataEx  error.errorCode=");
            sb.append(e.getErrorCode());
            sb.append(", flag=");
            sb.append(i);
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb.toString(), (Throwable) e);
            return null;
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(c());
            sb2.append(" [getSecBodyDataEx] ISecurityBodyComponent getSecurityBodyDataEx  error,flag=");
            sb2.append(i);
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb2.toString(), (Throwable) e2);
            return null;
        }
    }

    public final String a(String str, String str2, int i) {
        String a = a(str);
        if (!fdd.b(a)) {
            return a;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(c());
        sb.append(" [getAvmpSign] call avmpSign return null.degrade call getSecBodyDataEx ");
        TBSdkLog.d("mtopsdk.InnerSignImpl", sb.toString());
        return a("", "", str2, i);
    }

    private synchronized String a(String str) {
        String str2;
        int i;
        byte[] bArr = new byte[4];
        str2 = null;
        if (str == null) {
            str = "";
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(c());
                sb.append(" [avmpSign] input is null");
                TBSdkLog.d("mtopsdk.InnerSignImpl", sb.toString());
            } catch (Exception e) {
                i = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).getInt();
                SignStatistics.a("InvokeAVMP", String.valueOf(i), "");
            } catch (Throwable unused) {
            }
        }
        IAVMPGenericInstance a = a(this.a != null ? this.a.e : fdb.c());
        if (a == null) {
            return null;
        }
        byte[] bArr2 = (byte[]) a.invokeAVMP("sign", new byte[0].getClass(), Integer.valueOf(0), str.getBytes(), Integer.valueOf(str.getBytes().length), "", bArr, Integer.valueOf(a()));
        if (bArr2 != null) {
            str2 = new String(bArr2);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(c());
        sb2.append(" [avmpSign] call avmpInstance.invokeAVMP error.errorCode=");
        sb2.append(i);
        TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb2.toString(), (Throwable) e);
        return str2;
    }

    /* access modifiers changed from: 0000 */
    public final IAVMPGenericInstance a(@NonNull Context context) {
        if (this.d == null) {
            synchronized (fgs.class) {
                if (this.d == null) {
                    try {
                        this.d = ((IAVMPGenericComponent) SecurityGuardManager.getInstance(context).getInterface(IAVMPGenericComponent.class)).createAVMPInstance("mwua", "sgcipher");
                        if (this.d == null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(c());
                            sb.append(" [getAVMPInstance] call createAVMPInstance return null.");
                            TBSdkLog.d("mtopsdk.InnerSignImpl", sb.toString());
                        }
                    } catch (SecException e) {
                        int errorCode = e.getErrorCode();
                        SignStatistics.a("AVMPInstance", String.valueOf(errorCode), "");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(c());
                        sb2.append(" [getAVMPInstance] call createAVMPInstance error,errorCode=");
                        sb2.append(errorCode);
                        TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb2.toString(), (Throwable) e);
                    } catch (Exception e2) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(c());
                        sb3.append(" [getAVMPInstance] call createAVMPInstance error.");
                        TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb3.toString(), (Throwable) e2);
                    }
                }
            }
        }
        return this.d;
    }

    private String a(int i, String str) {
        String str2;
        SecException e;
        Throwable e2;
        String c2 = c();
        try {
            str2 = this.c.getStaticDataStoreComp().getAppKeyByIndex(i, str);
            try {
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(c2);
                    sb.append(" [getAppKeyByIndex]getAppKeyByIndex  appKey=");
                    sb.append(str2);
                    sb.append(",appKeyIndex=");
                    sb.append(i);
                    sb.append(",authCode=");
                    sb.append(str);
                    TBSdkLog.b("mtopsdk.InnerSignImpl", sb.toString());
                }
            } catch (SecException e3) {
                e = e3;
                int errorCode = e.getErrorCode();
                SignStatistics.a("GetAppKey", String.valueOf(errorCode), "");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(c2);
                sb2.append(" [getAppKeyByIndex]getAppKeyByIndex error.errorCode=");
                sb2.append(errorCode);
                sb2.append(",appKeyIndex=");
                sb2.append(i);
                sb2.append(",authCode=");
                sb2.append(str);
                TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb2.toString(), (Throwable) e);
                return str2;
            } catch (Exception e4) {
                e2 = e4;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(c2);
                sb3.append(" [getAppKeyByIndex]getAppKeyByIndex error.appKeyIndex=");
                sb3.append(i);
                sb3.append(",authCode=");
                sb3.append(str);
                TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb3.toString(), e2);
                return str2;
            }
        } catch (SecException e5) {
            SecException secException = e5;
            str2 = null;
            e = secException;
            int errorCode2 = e.getErrorCode();
            SignStatistics.a("GetAppKey", String.valueOf(errorCode2), "");
            StringBuilder sb22 = new StringBuilder();
            sb22.append(c2);
            sb22.append(" [getAppKeyByIndex]getAppKeyByIndex error.errorCode=");
            sb22.append(errorCode2);
            sb22.append(",appKeyIndex=");
            sb22.append(i);
            sb22.append(",authCode=");
            sb22.append(str);
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb22.toString(), (Throwable) e);
            return str2;
        } catch (Exception e6) {
            Throwable th = e6;
            str2 = null;
            e2 = th;
            StringBuilder sb32 = new StringBuilder();
            sb32.append(c2);
            sb32.append(" [getAppKeyByIndex]getAppKeyByIndex error.appKeyIndex=");
            sb32.append(i);
            sb32.append(",authCode=");
            sb32.append(str);
            TBSdkLog.b((String) "mtopsdk.InnerSignImpl", sb32.toString(), e2);
            return str2;
        }
        return str2;
    }
}

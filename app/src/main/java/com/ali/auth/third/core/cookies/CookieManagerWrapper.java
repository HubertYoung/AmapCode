package com.ali.auth.third.core.cookies;

public class CookieManagerWrapper {
    public static final CookieManagerWrapper INSTANCE = new CookieManagerWrapper();
    private static final String a = "CookieManagerWrapper";
    private String[] b;

    private CookieManagerWrapper() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clearCookies() {
        /*
            r11 = this;
            com.ali.auth.third.core.WebViewProxy r0 = com.ali.auth.third.core.cookies.a.a()
            r0.removeSessionCookie()
            java.lang.String[] r0 = r11.b
            if (r0 != 0) goto L_0x0032
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.getApplicationContext()
            java.lang.String r1 = "cookies"
            java.lang.String r0 = com.ali.auth.third.core.util.FileUtils.readFileData(r0, r1)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0032
            java.lang.String r1 = a
            java.lang.String r2 = "get cookie from storage:"
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r2 = r2.concat(r3)
            com.ali.auth.third.core.trace.SDKLogger.d(r1, r2)
            java.lang.String r1 = "\u0005"
            java.lang.String[] r0 = android.text.TextUtils.split(r0, r1)
            r11.b = r0
        L_0x0032:
            java.lang.String[] r0 = r11.b
            if (r0 == 0) goto L_0x010b
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String[] r1 = r11.b
            int r2 = r1.length
            r3 = 0
            r4 = 0
        L_0x0040:
            if (r4 >= r2) goto L_0x0080
            r5 = r1[r4]
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x007d
            com.ali.auth.third.core.cookies.c r5 = com.ali.auth.third.core.cookies.LoginCookieUtils.parseCookie(r5)     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r6 = "munb"
            java.lang.String r7 = r5.c     // Catch:{ Throwable -> 0x0079 }
            boolean r6 = r6.equals(r7)     // Catch:{ Throwable -> 0x0079 }
            if (r6 == 0) goto L_0x0059
            goto L_0x007d
        L_0x0059:
            java.lang.String r6 = com.ali.auth.third.core.cookies.LoginCookieUtils.getHttpDomin(r5)     // Catch:{ Throwable -> 0x0079 }
            com.ali.auth.third.core.cookies.LoginCookieUtils.expiresCookies(r5)     // Catch:{ Throwable -> 0x0079 }
            com.ali.auth.third.core.WebViewProxy r7 = com.ali.auth.third.core.cookies.a.a()     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r8 = r5.toString()     // Catch:{ Throwable -> 0x0079 }
            r7.setCookie(r6, r8)     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r6 = r5.a     // Catch:{ Throwable -> 0x0079 }
            java.lang.String r7 = ".taobao.com"
            boolean r6 = android.text.TextUtils.equals(r6, r7)     // Catch:{ Throwable -> 0x0079 }
            if (r6 == 0) goto L_0x007d
            r0.add(r5)     // Catch:{ Throwable -> 0x0079 }
            goto L_0x007d
        L_0x0079:
            r5 = move-exception
            r5.printStackTrace()
        L_0x007d:
            int r4 = r4 + 1
            goto L_0x0040
        L_0x0080:
            r1 = 0
            com.ali.auth.third.core.service.impl.CredentialManager r2 = com.ali.auth.third.core.service.impl.CredentialManager.INSTANCE     // Catch:{ Exception -> 0x00ab }
            com.ali.auth.third.core.model.InternalSession r2 = r2.getInternalSession()     // Catch:{ Exception -> 0x00ab }
            java.util.Map<java.lang.String, java.lang.Object> r2 = r2.otherInfo     // Catch:{ Exception -> 0x00ab }
            if (r2 == 0) goto L_0x00af
            com.ali.auth.third.core.service.impl.CredentialManager r2 = com.ali.auth.third.core.service.impl.CredentialManager.INSTANCE     // Catch:{ Exception -> 0x00ab }
            com.ali.auth.third.core.model.InternalSession r2 = r2.getInternalSession()     // Catch:{ Exception -> 0x00ab }
            java.util.Map<java.lang.String, java.lang.Object> r2 = r2.otherInfo     // Catch:{ Exception -> 0x00ab }
            java.lang.String r4 = "ssoDomainList"
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Exception -> 0x00ab }
            if (r2 == 0) goto L_0x00af
            boolean r4 = r2 instanceof java.util.ArrayList     // Catch:{ Exception -> 0x00ab }
            if (r4 == 0) goto L_0x00af
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch:{ Exception -> 0x00ab }
            java.lang.String[] r4 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00ab }
            java.lang.Object[] r2 = r2.toArray(r4)     // Catch:{ Exception -> 0x00ab }
            java.lang.String[] r2 = (java.lang.String[]) r2     // Catch:{ Exception -> 0x00ab }
            goto L_0x00b0
        L_0x00ab:
            r2 = move-exception
            r2.printStackTrace()
        L_0x00af:
            r2 = r1
        L_0x00b0:
            if (r2 == 0) goto L_0x00f7
            int r4 = r2.length
            if (r4 <= 0) goto L_0x00f7
            boolean r4 = r0.isEmpty()
            if (r4 != 0) goto L_0x00f7
            java.util.Iterator r0 = r0.iterator()
        L_0x00bf:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x00f7
            java.lang.Object r4 = r0.next()
            com.ali.auth.third.core.cookies.c r4 = (com.ali.auth.third.core.cookies.c) r4
            java.lang.String r5 = r4.a
            int r6 = r2.length
            r7 = 0
        L_0x00cf:
            if (r7 >= r6) goto L_0x00f4
            r8 = r2[r7]
            java.lang.String r9 = "munb"
            java.lang.String r10 = r4.c
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x00f1
            r4.a = r8
            java.lang.String r8 = com.ali.auth.third.core.cookies.LoginCookieUtils.getHttpDomin(r4)
            com.ali.auth.third.core.cookies.LoginCookieUtils.expiresCookies(r4)
            com.ali.auth.third.core.WebViewProxy r9 = com.ali.auth.third.core.cookies.a.a()
            java.lang.String r10 = r4.toString()
            r9.setCookie(r8, r10)
        L_0x00f1:
            int r7 = r7 + 1
            goto L_0x00cf
        L_0x00f4:
            r4.a = r5
            goto L_0x00bf
        L_0x00f7:
            java.lang.String r0 = a
            java.lang.String r2 = "injectCookie cookies is null"
            com.ali.auth.third.core.trace.SDKLogger.d(r0, r2)
            r11.b = r1
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.getApplicationContext()
            java.lang.String r1 = "cookies"
            java.lang.String r2 = ""
            com.ali.auth.third.core.util.FileUtils.writeFileData(r0, r1, r2)
        L_0x010b:
            com.ali.auth.third.core.WebViewProxy r0 = com.ali.auth.third.core.cookies.a.a()
            r0.removeExpiredCookie()
            com.ali.auth.third.core.WebViewProxy r0 = com.ali.auth.third.core.cookies.a.a()
            r0.flush()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.cookies.CookieManagerWrapper.clearCookies():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c7, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cc, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void injectCookie(java.lang.String[] r13, java.lang.String[] r14) {
        /*
            r12 = this;
            monitor-enter(r12)
            r12.b = r13     // Catch:{ all -> 0x00cd }
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.context     // Catch:{ all -> 0x00cd }
            if (r0 == 0) goto L_0x00cb
            if (r13 == 0) goto L_0x00c8
            java.lang.String r0 = a     // Catch:{ all -> 0x00cd }
            java.lang.String r1 = "injectCookie cookies != null"
            com.ali.auth.third.core.trace.SDKLogger.d(r0, r1)     // Catch:{ all -> 0x00cd }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00cd }
            r0.<init>()     // Catch:{ all -> 0x00cd }
            int r1 = r13.length     // Catch:{ all -> 0x00cd }
            r2 = 0
            r3 = 0
        L_0x0018:
            if (r3 >= r1) goto L_0x0059
            r4 = r13[r3]     // Catch:{ all -> 0x00cd }
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x00cd }
            if (r5 != 0) goto L_0x0056
            com.ali.auth.third.core.cookies.c r4 = com.ali.auth.third.core.cookies.LoginCookieUtils.parseCookie(r4)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r5 = com.ali.auth.third.core.cookies.LoginCookieUtils.getHttpDomin(r4)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r6 = r4.toString()     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r7 = a     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r8 = "add cookie: "
            java.lang.String r9 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r8 = r8.concat(r9)     // Catch:{ Throwable -> 0x0052 }
            com.ali.auth.third.core.trace.SDKLogger.d(r7, r8)     // Catch:{ Throwable -> 0x0052 }
            com.ali.auth.third.core.WebViewProxy r7 = com.ali.auth.third.core.cookies.a.a()     // Catch:{ Throwable -> 0x0052 }
            r7.setCookie(r5, r6)     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r5 = r4.a     // Catch:{ Throwable -> 0x0052 }
            java.lang.String r6 = ".taobao.com"
            boolean r5 = android.text.TextUtils.equals(r5, r6)     // Catch:{ Throwable -> 0x0052 }
            if (r5 == 0) goto L_0x0056
            r0.add(r4)     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x00cd }
        L_0x0056:
            int r3 = r3 + 1
            goto L_0x0018
        L_0x0059:
            if (r14 == 0) goto L_0x00ae
            int r1 = r14.length     // Catch:{ all -> 0x00cd }
            if (r1 <= 0) goto L_0x00ae
            boolean r1 = r0.isEmpty()     // Catch:{ all -> 0x00cd }
            if (r1 != 0) goto L_0x00ae
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00cd }
        L_0x0068:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x00cd }
            if (r1 == 0) goto L_0x00ae
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x00cd }
            com.ali.auth.third.core.cookies.c r1 = (com.ali.auth.third.core.cookies.c) r1     // Catch:{ all -> 0x00cd }
            java.lang.String r3 = r1.a     // Catch:{ all -> 0x00cd }
            int r4 = r14.length     // Catch:{ all -> 0x00cd }
            r5 = 0
        L_0x0078:
            if (r5 >= r4) goto L_0x00ab
            r6 = r14[r5]     // Catch:{ all -> 0x00cd }
            r1.a = r6     // Catch:{ all -> 0x00cd }
            java.lang.String r7 = com.ali.auth.third.core.cookies.LoginCookieUtils.getHttpDomin(r1)     // Catch:{ all -> 0x00cd }
            java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x00cd }
            java.lang.String r9 = a     // Catch:{ all -> 0x00cd }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cd }
            java.lang.String r11 = "add cookies to domain:"
            r10.<init>(r11)     // Catch:{ all -> 0x00cd }
            r10.append(r6)     // Catch:{ all -> 0x00cd }
            java.lang.String r6 = ", cookie = "
            r10.append(r6)     // Catch:{ all -> 0x00cd }
            r10.append(r8)     // Catch:{ all -> 0x00cd }
            java.lang.String r6 = r10.toString()     // Catch:{ all -> 0x00cd }
            com.ali.auth.third.core.trace.SDKLogger.d(r9, r6)     // Catch:{ all -> 0x00cd }
            com.ali.auth.third.core.WebViewProxy r6 = com.ali.auth.third.core.cookies.a.a()     // Catch:{ all -> 0x00cd }
            r6.setCookie(r7, r8)     // Catch:{ all -> 0x00cd }
            int r5 = r5 + 1
            goto L_0x0078
        L_0x00ab:
            r1.a = r3     // Catch:{ all -> 0x00cd }
            goto L_0x0068
        L_0x00ae:
            com.ali.auth.third.core.WebViewProxy r14 = com.ali.auth.third.core.cookies.a.a()     // Catch:{ all -> 0x00cd }
            r14.flush()     // Catch:{ all -> 0x00cd }
            java.lang.String[] r14 = r12.b     // Catch:{ all -> 0x00cd }
            if (r14 == 0) goto L_0x00c6
            android.content.Context r14 = com.ali.auth.third.core.context.KernelContext.context     // Catch:{ all -> 0x00cd }
            java.lang.String r0 = "cookies"
            java.lang.String r1 = "\u0005"
            java.lang.String r13 = android.text.TextUtils.join(r1, r13)     // Catch:{ all -> 0x00cd }
            com.ali.auth.third.core.util.FileUtils.writeFileData(r14, r0, r13)     // Catch:{ all -> 0x00cd }
        L_0x00c6:
            monitor-exit(r12)
            return
        L_0x00c8:
            r12.clearCookies()     // Catch:{ all -> 0x00cd }
        L_0x00cb:
            monitor-exit(r12)
            return
        L_0x00cd:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.cookies.CookieManagerWrapper.injectCookie(java.lang.String[], java.lang.String[]):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:1|2|(3:4|5|(1:7))|8|9|(1:11)|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x002c */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void refreshCookie() {
        /*
            r4 = this;
            monitor-enter(r4)
            java.lang.String[] r0 = r4.b     // Catch:{ all -> 0x0038 }
            if (r0 != 0) goto L_0x002c
            android.content.Context r0 = com.ali.auth.third.core.context.KernelContext.getApplicationContext()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = "cookies"
            java.lang.String r0 = com.ali.auth.third.core.util.FileUtils.readFileData(r0, r1)     // Catch:{ Throwable -> 0x002c }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x002c }
            if (r1 != 0) goto L_0x002c
            java.lang.String r1 = a     // Catch:{ Throwable -> 0x002c }
            java.lang.String r2 = "get cookie from storage:"
            java.lang.String r3 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ Throwable -> 0x002c }
            com.ali.auth.third.core.trace.SDKLogger.d(r1, r2)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r1 = "\u0005"
            java.lang.String[] r0 = android.text.TextUtils.split(r0, r1)     // Catch:{ Throwable -> 0x002c }
            r4.b = r0     // Catch:{ Throwable -> 0x002c }
        L_0x002c:
            java.lang.String[] r0 = r4.b     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0036
            java.lang.String[] r0 = r4.b     // Catch:{ all -> 0x0038 }
            r1 = 0
            r4.injectCookie(r0, r1)     // Catch:{ all -> 0x0038 }
        L_0x0036:
            monitor-exit(r4)
            return
        L_0x0038:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.auth.third.core.cookies.CookieManagerWrapper.refreshCookie():void");
    }
}

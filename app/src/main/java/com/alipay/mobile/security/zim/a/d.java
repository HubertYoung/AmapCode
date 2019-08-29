package com.alipay.mobile.security.zim.a;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.biometrics.ui.widget.LoadingProgressDialog;
import com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse;
import com.alipay.bis.common.service.facade.gw.zim.ZimValidateGwResponse;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.alipay.mobile.security.bio.api.BioParameter;
import com.alipay.mobile.security.bio.api.BioProgressCallback;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.ZimRecordService;
import com.alipay.mobile.security.bio.service.local.monitorlog.MonitorLogService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.workspace.Env;
import com.alipay.mobile.security.zim.api.ZIMCallback;
import com.alipay.mobile.security.zim.api.ZIMFacade;
import com.alipay.mobile.security.zim.api.ZIMResponse;
import com.alipay.mobile.security.zim.api.ZimProgressCallback;
import com.alipay.mobile.security.zim.gw.a;
import com.alipay.mobile.security.zim.gw.b;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ZimPlatform */
public final class d extends ZIMFacade implements BioProgressCallback, b {
    private static final String a = Boolean.TRUE.toString();
    private static final String b = Boolean.FALSE.toString();
    private Context c;
    private ZIMCallback d;
    private String e = "";
    private Map<String, String> f;
    private String g;
    private a h;
    private BioDetector i;
    /* access modifiers changed from: private */
    public LoadingProgressDialog j;
    private BioServiceManager k;
    private boolean l;
    private BioParameter m = new BioParameter();

    public d(Context context) {
        String str;
        this.c = context;
        switch (Env.getProtocolFormat(context)) {
            case 2:
                str = "com.alipay.mobile.security.zim.gw.PbGwService";
                break;
            default:
                str = "com.alipay.mobile.security.zim.gw.JsonGwService";
                break;
        }
        try {
            Constructor<?> constructor = Class.forName(str).getConstructor(new Class[]{b.class});
            constructor.setAccessible(true);
            this.h = (a) constructor.newInstance(new Object[]{this});
        } catch (Throwable th) {
            BioLog.e(th);
            ZIMResponse zIMResponse = new ZIMResponse();
            zIMResponse.code = 1001;
            a(zIMResponse);
        }
    }

    public final ZimInitGwResponse parse(String str) {
        if (!TextUtils.isEmpty(str)) {
            return this.h.convert(str);
        }
        return null;
    }

    public final void verify(String str, Map<String, String> map, ZIMCallback zIMCallback) {
        ZimInitGwResponse zimInitGwResponse = null;
        if (map != null && map.containsKey(ZIMFacade.KEY_INIT_RESP)) {
            zimInitGwResponse = parse(map.remove(ZIMFacade.KEY_INIT_RESP));
        }
        verify(str, zimInitGwResponse, map, zIMCallback);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004c, code lost:
        r6.e = r7;
        r6.f = r9;
        r6.d = r10;
        com.alipay.mobile.security.bio.utils.BioLog.d("verify(zimId=" + r7 + ", params=" + com.alipay.mobile.security.bio.utils.StringUtil.map2String(r9) + ", callback=" + r10 + ")");
        r3 = r6.c;
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x008b, code lost:
        if (com.alipay.mobile.security.bio.utils.DeviceUtil.isDebug(r6.c) == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x008d, code lost:
        if (r9 == null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0095, code lost:
        if (r9.containsKey(com.alipay.mobile.security.zim.api.ZIMFacade.KEY_ENV_NAME) == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0097, code lost:
        r0 = r9.remove(com.alipay.mobile.security.zim.api.ZIMFacade.KEY_ENV_NAME);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a3, code lost:
        if (android.text.TextUtils.isEmpty(r0) == false) goto L_0x00d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r3 = r3.getPackageManager().getApplicationInfo(r3.getPackageName(), 128);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b3, code lost:
        if (r3 == null) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b7, code lost:
        if (r3.metaData == null) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b9, code lost:
        r0 = r3.metaData.getString(com.alipay.mobile.security.zim.api.ZIMFacade.KEY_ENV_NAME);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c1, code lost:
        com.alipay.mobile.security.bio.utils.BioLog.d("ZimPlatform", "ApplicationInfo.metaData.env_name == " + r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0168, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0169, code lost:
        com.alipay.mobile.security.bio.utils.BioLog.w((java.lang.Throwable) r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void verify(java.lang.String r7, com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse r8, java.util.Map<java.lang.String, java.lang.String> r9, com.alipay.mobile.security.zim.api.ZIMCallback r10) {
        /*
            r6 = this;
            r1 = 1
            r2 = 0
            boolean r0 = android.text.TextUtils.isEmpty(r7)
            if (r0 == 0) goto L_0x0010
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "zimId cant be null"
            r0.<init>(r1)
            throw r0
        L_0x0010:
            if (r10 != 0) goto L_0x001a
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "ZIMCallback cant be null"
            r0.<init>(r1)
            throw r0
        L_0x001a:
            monitor-enter(r6)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0165 }
            java.lang.String r3 = "zim is busy : "
            r0.<init>(r3)     // Catch:{ all -> 0x0165 }
            boolean r3 = r6.l     // Catch:{ all -> 0x0165 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x0165 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0165 }
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)     // Catch:{ all -> 0x0165 }
            boolean r0 = r6.l     // Catch:{ all -> 0x0165 }
            if (r0 == 0) goto L_0x0048
            com.alipay.mobile.security.zim.api.ZIMResponse r0 = new com.alipay.mobile.security.zim.api.ZIMResponse     // Catch:{ all -> 0x0165 }
            r0.<init>()     // Catch:{ all -> 0x0165 }
            r1 = 2006(0x7d6, float:2.811E-42)
            r0.code = r1     // Catch:{ all -> 0x0165 }
            java.lang.String r1 = "busy"
            r0.reason = r1     // Catch:{ all -> 0x0165 }
            b(r0)     // Catch:{ all -> 0x0165 }
            r10.response(r0)     // Catch:{ all -> 0x0165 }
            monitor-exit(r6)     // Catch:{ all -> 0x0165 }
        L_0x0047:
            return
        L_0x0048:
            r0 = 1
            r6.l = r0     // Catch:{ all -> 0x0165 }
            monitor-exit(r6)     // Catch:{ all -> 0x0165 }
            r6.e = r7
            r6.f = r9
            r6.d = r10
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "verify(zimId="
            r0.<init>(r3)
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.String r3 = ", params="
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r3 = com.alipay.mobile.security.bio.utils.StringUtil.map2String(r9)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r3 = ", callback="
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r3 = ")"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.d(r0)
            android.content.Context r3 = r6.c
            r0 = 0
            android.content.Context r4 = r6.c
            boolean r4 = com.alipay.mobile.security.bio.utils.DeviceUtil.isDebug(r4)
            if (r4 == 0) goto L_0x009f
            if (r9 == 0) goto L_0x009f
            java.lang.String r4 = "env_name"
            boolean r4 = r9.containsKey(r4)
            if (r4 == 0) goto L_0x009f
            java.lang.String r0 = "env_name"
            java.lang.Object r0 = r9.remove(r0)
            java.lang.String r0 = (java.lang.String) r0
        L_0x009f:
            boolean r4 = android.text.TextUtils.isEmpty(r0)
            if (r4 == 0) goto L_0x00d5
            android.content.pm.PackageManager r4 = r3.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0168 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ NameNotFoundException -> 0x0168 }
            r5 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r3 = r4.getApplicationInfo(r3, r5)     // Catch:{ NameNotFoundException -> 0x0168 }
            if (r3 == 0) goto L_0x00c1
            android.os.Bundle r4 = r3.metaData     // Catch:{ NameNotFoundException -> 0x0168 }
            if (r4 == 0) goto L_0x00c1
            android.os.Bundle r3 = r3.metaData     // Catch:{ NameNotFoundException -> 0x0168 }
            java.lang.String r4 = "env_name"
            java.lang.String r0 = r3.getString(r4)     // Catch:{ NameNotFoundException -> 0x0168 }
        L_0x00c1:
            java.lang.String r3 = "ZimPlatform"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NameNotFoundException -> 0x0168 }
            java.lang.String r5 = "ApplicationInfo.metaData.env_name == "
            r4.<init>(r5)     // Catch:{ NameNotFoundException -> 0x0168 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ NameNotFoundException -> 0x0168 }
            java.lang.String r4 = r4.toString()     // Catch:{ NameNotFoundException -> 0x0168 }
            com.alipay.mobile.security.bio.utils.BioLog.d(r3, r4)     // Catch:{ NameNotFoundException -> 0x0168 }
        L_0x00d5:
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x00dd
            java.lang.String r0 = "online"
        L_0x00dd:
            java.lang.String r3 = "ZimPlatform"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "initEnv() : env="
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.security.bio.utils.BioLog.i(r3, r4)
            com.alipay.mobile.security.bio.workspace.Env r0 = com.alipay.mobile.security.bio.workspace.Env.getEnvByName(r0)
            if (r0 == 0) goto L_0x00fa
            com.alipay.mobile.security.bio.service.BioServiceManager.setEnv(r0)
        L_0x00fa:
            android.content.Context r0 = r6.c
            boolean r3 = r0 instanceof android.app.Activity
            if (r3 == 0) goto L_0x0107
            com.alipay.biometrics.ui.widget.LoadingProgressDialog r3 = new com.alipay.biometrics.ui.widget.LoadingProgressDialog
            r3.<init>(r0)
            r6.j = r3
        L_0x0107:
            com.alipay.mobile.security.bio.module.MicroModule r3 = new com.alipay.mobile.security.bio.module.MicroModule
            r3.<init>(r7)
            com.alipay.mobile.security.bio.api.BioDetector r0 = com.alipay.mobile.security.bio.api.BioDetectorBuilder.create(r0, r3)
            r6.i = r0
            com.alipay.mobile.security.bio.service.BioServiceManager r0 = com.alipay.mobile.security.bio.service.BioServiceManager.getCurrentInstance()
            r6.k = r0
            com.alipay.mobile.security.bio.service.BioServiceManager r0 = r6.k
            java.lang.Class<com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService> r3 = com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService.class
            java.lang.Object r0 = r0.getBioService(r3)
            com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService r0 = (com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService) r0
            if (r0 != 0) goto L_0x016e
            java.lang.String r0 = "BioTransfer.buildBioParameter(): null == ApSecurityService"
            com.alipay.mobile.security.bio.utils.BioLog.i(r0)
        L_0x0129:
            com.alipay.mobile.security.zim.a.a r0 = com.alipay.mobile.security.zim.a.a.a()
            if (r0 != 0) goto L_0x0135
            android.content.Context r0 = r6.c
            com.alipay.mobile.security.zim.a.a r0 = com.alipay.mobile.security.zim.a.a.a(r0)
        L_0x0135:
            r0.a(r7)
            java.lang.String r3 = com.alipay.mobile.security.zim.a.a.e
            r0.b(r3)
            java.lang.String r3 = com.alipay.mobile.security.zim.a.a.f
            r0.b(r3)
            if (r8 == 0) goto L_0x0179
        L_0x0144:
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.lang.String r4 = java.lang.Boolean.toString(r1)
            java.lang.String r5 = "mock"
            r3.put(r5, r4)
            java.lang.String r5 = com.alipay.mobile.security.zim.a.a.g
            r0.a(r5, r3)
            com.alipay.mobile.security.bio.api.BioParameter r0 = r6.m
            java.lang.String r3 = "mock"
            r0.addExtProperty(r3, r4)
            if (r1 == 0) goto L_0x017b
            r6.a(r8)
            goto L_0x0047
        L_0x0165:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0165 }
            throw r0
        L_0x0168:
            r3 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.w(r3)
            goto L_0x00d5
        L_0x016e:
            java.lang.String r3 = "BioTransfer.buildBioParameter(): ApSecurityService.init()"
            com.alipay.mobile.security.bio.utils.BioLog.i(r3)
            android.content.Context r3 = r6.c
            r0.init(r3)
            goto L_0x0129
        L_0x0179:
            r1 = r2
            goto L_0x0144
        L_0x017b:
            com.alipay.biometrics.ui.widget.LoadingProgressDialog r0 = r6.j
            if (r0 == 0) goto L_0x0193
            com.alipay.biometrics.ui.widget.LoadingProgressDialog r0 = r6.j
            boolean r0 = r0.isShowing()
            if (r0 != 0) goto L_0x0193
            android.content.Context r0 = r6.c
            android.app.Activity r0 = (android.app.Activity) r0
            com.alipay.mobile.security.zim.a.e r1 = new com.alipay.mobile.security.zim.a.e
            r1.<init>(r6)
            r0.runOnUiThread(r1)
        L_0x0193:
            com.alipay.bis.common.service.facade.gw.zim.ZimInitGwRequest r0 = new com.alipay.bis.common.service.facade.gw.zim.ZimInitGwRequest
            r0.<init>()
            r0.zimId = r7
            android.content.Context r1 = r6.c
            java.lang.String r1 = getMetaInfos(r1, r2)
            r0.metaInfo = r1
            java.lang.String r1 = "zolozTime"
            java.lang.String r2 = "smiletopay get protocol begin"
            com.alipay.mobile.security.bio.utils.BioLog.i(r1, r2)
            com.alipay.mobile.security.zim.gw.a r1 = r6.h
            r1.init(r0)
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.zim.a.d.verify(java.lang.String, com.alipay.bis.common.service.facade.gw.zim.ZimInitGwResponse, java.util.Map, com.alipay.mobile.security.zim.api.ZIMCallback):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00f8, code lost:
        r0 = r1;
        r1 = true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onResult(com.alipay.mobile.security.bio.api.BioResponse r8) {
        /*
            r7 = this;
            r6 = 209(0xd1, float:2.93E-43)
            r3 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "ZimPlatform.onResult(), response="
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r0 = r0.toString()
            com.alipay.mobile.security.bio.utils.BioLog.i(r0)
            java.util.HashMap r0 = new java.util.HashMap
            r1 = 2
            r0.<init>(r1)
            java.lang.String r1 = "result"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            boolean r4 = r8.isSuccess()
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r1, r2)
            java.lang.String r1 = "message"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = r8.getResultMessage()
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r1, r2)
            java.lang.String r1 = "retCode"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            int r4 = r8.getResult()
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r1, r2)
            java.lang.String r1 = "subCode"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = r8.subCode
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r1, r2)
            java.lang.String r1 = "subMsg"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r4 = r8.subMsg
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r0.put(r1, r2)
            com.alipay.mobile.security.zim.a.a r1 = com.alipay.mobile.security.zim.a.a.a()
            java.lang.String r2 = com.alipay.mobile.security.zim.a.a.j
            r1.a(r2, r0)
            r2 = 1
            r1 = 0
            java.lang.String r0 = r7.e
            java.lang.String r4 = "_bis"
            boolean r0 = r0.contains(r4)
            if (r0 != 0) goto L_0x0189
            java.util.Map r0 = r8.getExt()
            if (r0 == 0) goto L_0x0207
            boolean r4 = r0.isEmpty()
            if (r4 != 0) goto L_0x0207
            java.lang.String r4 = "upload_response"
            boolean r4 = r0.containsKey(r4)
            if (r4 == 0) goto L_0x0207
            java.lang.String r1 = "upload_response"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Class<com.alipay.mobile.security.bio.service.BioUploadResult> r1 = com.alipay.mobile.security.bio.service.BioUploadResult.class
            java.lang.Object r0 = com.alibaba.fastjson.JSON.parseObject(r0, r1)
            com.alipay.mobile.security.bio.service.BioUploadResult r0 = (com.alipay.mobile.security.bio.service.BioUploadResult) r0
            com.alipay.mobile.security.zim.api.ZIMResponse r1 = new com.alipay.mobile.security.zim.api.ZIMResponse
            r1.<init>()
            int r4 = r0.validationRetCode
            r1.code = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r5 = r0.validationRetCode
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r1.reason = r4
            java.lang.String r4 = r0.subCode
            r1.subCode = r4
            java.lang.String r4 = r0.subMsg
            r1.msg = r4
            java.util.Map<java.lang.String, java.lang.String> r4 = r0.extParams
            if (r4 == 0) goto L_0x00f3
            java.util.Map<java.lang.String, java.lang.String> r4 = r0.extParams
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x00f3
            java.util.Map<java.lang.String, java.lang.String> r4 = r1.extInfo
            java.util.Map<java.lang.String, java.lang.String> r5 = r0.extParams
            r4.putAll(r5)
        L_0x00f3:
            int r4 = r0.validationRetCode
            switch(r4) {
                case 1000: goto L_0x014d;
                case 3001: goto L_0x0182;
                default: goto L_0x00f8;
            }
        L_0x00f8:
            r0 = r1
            r1 = r2
        L_0x00fa:
            r3 = r1
        L_0x00fb:
            if (r3 == 0) goto L_0x014c
            if (r0 != 0) goto L_0x013b
            com.alipay.mobile.security.zim.api.ZIMResponse r0 = new com.alipay.mobile.security.zim.api.ZIMResponse
            r0.<init>()
            int r1 = r8.getResult()
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x011c
            int r1 = r8.getResult()
            r2 = 205(0xcd, float:2.87E-43)
            if (r1 == r2) goto L_0x011c
            int r1 = r8.getResult()
            r2 = 100
            if (r1 != r2) goto L_0x01cd
        L_0x011c:
            r1 = 1001(0x3e9, float:1.403E-42)
            r0.code = r1
        L_0x0120:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            int r2 = r8.getResult()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.reason = r1
            java.lang.String r1 = r8.subCode
            r0.subCode = r1
            java.lang.String r1 = r8.subMsg
            r0.msg = r1
        L_0x013b:
            b(r0)
            java.lang.String r1 = com.alibaba.fastjson.JSON.toJSONString(r8)
            java.util.Map<java.lang.String, java.lang.String> r2 = r0.extInfo
            java.lang.String r3 = "bioResponse"
            r2.put(r3, r1)
            r7.a(r0)
        L_0x014c:
            return
        L_0x014d:
            boolean r4 = r0.hasNext
            if (r4 == 0) goto L_0x00f8
            java.lang.String r4 = r0.nextProtocol
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x00f8
            com.alipay.mobile.security.bio.api.BioParameter r2 = new com.alipay.mobile.security.bio.api.BioParameter
            r2.<init>()
            java.lang.String r0 = r0.nextProtocol
            r2.setProtocol(r0)
            java.lang.String r0 = "verifyid"
            java.lang.String r4 = r7.e
            r2.addExtProperty(r0, r4)
            java.lang.String r0 = "TOKEN_ID"
            java.lang.String r4 = r7.e
            r2.addExtProperty(r0, r4)
            com.alipay.mobile.security.zim.a.a r0 = com.alipay.mobile.security.zim.a.a.a()
            java.lang.String r4 = com.alipay.mobile.security.zim.a.a.i
            r0.b(r4)
            r7.m = r2
            r7.a(r2)
            r0 = r1
            goto L_0x00fb
        L_0x0182:
            r7.retry()
            r0 = r1
            r1 = r3
            goto L_0x00fa
        L_0x0189:
            int r0 = r8.getResult()
            if (r0 == r6) goto L_0x0197
            int r0 = r8.getResult()
            r4 = 500(0x1f4, float:7.0E-43)
            if (r0 != r4) goto L_0x0203
        L_0x0197:
            com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest r2 = new com.alipay.bis.common.service.facade.gw.zim.ZimValidateJsonGwRequest
            r2.<init>()
            java.lang.String r0 = r7.e
            r2.zimId = r0
            java.lang.String r0 = ""
            r2.zimData = r0
            com.alipay.mobile.security.zim.a.a r0 = com.alipay.mobile.security.zim.a.a.a()
            java.lang.String r4 = com.alipay.mobile.security.zim.a.a.k
            r0.b(r4)
            com.alipay.biometrics.ui.widget.LoadingProgressDialog r0 = r7.j
            if (r0 == 0) goto L_0x01c5
            com.alipay.biometrics.ui.widget.LoadingProgressDialog r0 = r7.j
            boolean r0 = r0.isShowing()
            if (r0 != 0) goto L_0x01c5
            android.content.Context r0 = r7.c
            android.app.Activity r0 = (android.app.Activity) r0
            com.alipay.mobile.security.zim.a.f r4 = new com.alipay.mobile.security.zim.a.f
            r4.<init>(r7)
            r0.runOnUiThread(r4)
        L_0x01c5:
            com.alipay.mobile.security.zim.gw.a r0 = r7.h
            r0.validate(r8, r2)
            r0 = r1
            goto L_0x00fb
        L_0x01cd:
            int r1 = r8.getResult()
            r2 = 301(0x12d, float:4.22E-43)
            if (r1 == r2) goto L_0x01dd
            int r1 = r8.getResult()
            r2 = 202(0xca, float:2.83E-43)
            if (r1 != r2) goto L_0x01e3
        L_0x01dd:
            r1 = 1003(0x3eb, float:1.406E-42)
            r0.code = r1
            goto L_0x0120
        L_0x01e3:
            int r1 = r8.getResult()
            r2 = 203(0xcb, float:2.84E-43)
            if (r1 != r2) goto L_0x01f1
            r1 = 1004(0x3ec, float:1.407E-42)
            r0.code = r1
            goto L_0x0120
        L_0x01f1:
            int r1 = r8.getResult()
            if (r1 != r6) goto L_0x01fd
            r1 = 1005(0x3ed, float:1.408E-42)
            r0.code = r1
            goto L_0x0120
        L_0x01fd:
            r1 = 2006(0x7d6, float:2.811E-42)
            r0.code = r1
            goto L_0x0120
        L_0x0203:
            r0 = r1
            r3 = r2
            goto L_0x00fb
        L_0x0207:
            r0 = r1
            r1 = r2
            goto L_0x00fa
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.zim.a.d.onResult(com.alipay.mobile.security.bio.api.BioResponse):void");
    }

    public final void a(ZimInitGwResponse zimInitGwResponse) {
        boolean z;
        BioLog.i("zolozTime", "smiletopay get protocal end");
        if (this.j != null && this.j.isShowing()) {
            ((Activity) this.c).runOnUiThread(new g(this));
        }
        if (zimInitGwResponse == null) {
            HashMap hashMap = new HashMap(2);
            hashMap.put("result", b);
            hashMap.put("message", "0");
            hashMap.put("retCode", "0");
            hashMap.put("subCode", "");
            hashMap.put("subMsg", "");
            a.a().a(a.h, hashMap);
            z = true;
        } else if (zimInitGwResponse.retCode == 1001 || zimInitGwResponse.retCode == 200) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("result", b);
            hashMap2.put("retCode", zimInitGwResponse.retCode);
            hashMap2.put("message", zimInitGwResponse.message);
            hashMap2.put("subCode", zimInitGwResponse.retCodeSub);
            hashMap2.put("subMsg", zimInitGwResponse.retMessageSub);
            if (zimInitGwResponse.extParams != null && !zimInitGwResponse.extParams.isEmpty()) {
                hashMap2.putAll(zimInitGwResponse.extParams);
            }
            a.a().a(a.h, hashMap2);
            z = true;
        } else {
            HashMap hashMap3 = new HashMap();
            hashMap3.put("result", a);
            hashMap3.put("retCode", zimInitGwResponse.retCode);
            hashMap3.put("message", zimInitGwResponse.message);
            hashMap3.put("subCode", zimInitGwResponse.retCodeSub);
            hashMap3.put("subMsg", zimInitGwResponse.retMessageSub);
            if (zimInitGwResponse.extParams != null && !zimInitGwResponse.extParams.isEmpty()) {
                hashMap3.putAll(zimInitGwResponse.extParams);
            }
            a.a().a(a.h, hashMap3);
            if (zimInitGwResponse.extParams != null && !zimInitGwResponse.extParams.isEmpty()) {
                this.g = zimInitGwResponse.extParams.get(ZIMFacade.KEY_FACE_PAY_INFO);
            }
            this.m.setProtocol(zimInitGwResponse.protocol);
            if (this.f != null && this.f.containsKey(ZIMFacade.KEY_AUTO_CLOSE)) {
                this.m.setAutoClose(Boolean.parseBoolean(this.f.remove(ZIMFacade.KEY_AUTO_CLOSE)));
            }
            Map<String, String> extProperty = this.m.getExtProperty();
            extProperty.put(BioDetector.EXT_KEY_VERIFYID, this.e);
            extProperty.put("TOKEN_ID", this.e);
            if (this.f != null && !this.f.isEmpty()) {
                extProperty.putAll(this.f);
            }
            a.a().b(a.i);
            if (this.e.contains("_bis")) {
                this.m.isValidate = false;
            } else {
                this.m.isValidate = true;
            }
            a(this.m);
            z = false;
        }
        if (z) {
            ZIMResponse zIMResponse = new ZIMResponse();
            zIMResponse.code = zimInitGwResponse.retCode;
            zIMResponse.reason = zimInitGwResponse.retCode;
            zIMResponse.subCode = zimInitGwResponse.retCodeSub;
            zIMResponse.msg = zimInitGwResponse.retMessageSub;
            b(zIMResponse);
            a(zIMResponse);
        }
    }

    private void a(BioParameter bioParameter) {
        try {
            BioLog.d("ZimPlatform", "ZimPlatform.auth()");
            this.i.auth(bioParameter, this);
        } catch (Throwable th) {
            BioLog.e(th);
            ZIMResponse zIMResponse = new ZIMResponse();
            zIMResponse.code = 1001;
            zIMResponse.reason = String.valueOf(th);
            b(zIMResponse);
            a(zIMResponse);
        }
    }

    public final void retry() {
        BioLog.i("ZimPlatform", "ZIMFacade.retry()");
        command(4099);
        ((ZimRecordService) this.k.getBioService(ZimRecordService.class)).retry();
        a(this.m);
    }

    public final boolean onFaceDetected(Map<String, String> map) {
        if (this.d instanceof ZimProgressCallback) {
            if (!TextUtils.isEmpty(this.g)) {
                map.put(ZIMFacade.KEY_FACE_PAY_INFO, this.g);
            }
            ((ZimProgressCallback) this.d).onFaceDetected(map);
        }
        return true;
    }

    public final void a(ZimValidateGwResponse zimValidateGwResponse) {
        boolean z;
        if (this.j != null && this.j.isShowing()) {
            ((Activity) this.c).runOnUiThread(new h(this));
        }
        HashMap hashMap = new HashMap(2);
        if (zimValidateGwResponse != null) {
            switch (zimValidateGwResponse.validationRetCode) {
                case 100:
                case 1000:
                    hashMap.put("result", a);
                    break;
                default:
                    hashMap.put("result", b);
                    break;
            }
            hashMap.put("message", "");
            hashMap.put("retCode", zimValidateGwResponse.validationRetCode);
            hashMap.put("subCode", zimValidateGwResponse.retCodeSub);
            hashMap.put("subMsg", zimValidateGwResponse.retMessageSub);
        } else {
            hashMap.put("result", b);
            hashMap.put("message", "0");
            hashMap.put("retCode", "0");
            hashMap.put("subCode", "");
            hashMap.put("subMsg", "");
        }
        a.a().a(a.l, hashMap);
        ZIMResponse zIMResponse = new ZIMResponse();
        if (zimValidateGwResponse == null) {
            zIMResponse.code = 1001;
            z = true;
        } else {
            zIMResponse.bizData = zimValidateGwResponse.nextProtocol;
            zIMResponse.reason = zimValidateGwResponse.productRetCode;
            zIMResponse.code = zimValidateGwResponse.validationRetCode;
            if (zimValidateGwResponse.extParams != null) {
                for (String next : zimValidateGwResponse.extParams.keySet()) {
                    zIMResponse.extInfo.put(next, zimValidateGwResponse.extParams.get(next));
                }
            }
            if (zimValidateGwResponse.validationRetCode == 3001 || zimValidateGwResponse.validationRetCode == 3002) {
                z = false;
            } else {
                z = true;
            }
        }
        if (z) {
            b(zIMResponse);
            if (a(zIMResponse)) {
                command(4099);
            }
        }
    }

    private boolean a(ZIMResponse zIMResponse) {
        BioLog.w((Throwable) new RuntimeException("doCallZimCallback(): zimResponse=" + zIMResponse));
        boolean response = this.d.response(zIMResponse);
        a.a().b(a.n);
        if (this.k != null) {
            MonitorLogService monitorLogService = (MonitorLogService) this.k.getBioService(MonitorLogService.class);
            if (monitorLogService != null) {
                monitorLogService.trigUpload();
            }
        }
        if (response) {
            BioLog.e((String) "ZimPlatform", (String) "ZimPlatform.destroy()");
            this.l = false;
            a a2 = a.a();
            a2.o.clear();
            a2.d = null;
            a2.c = 0;
            a2.b = 0;
            a.a = null;
            if (this.i != null) {
                this.i.destroy();
            }
            if (this.h != null) {
                this.h.destroy();
            }
            this.c = null;
            this.k = null;
        }
        return response;
    }

    private static void b(ZIMResponse zIMResponse) {
        HashMap hashMap = new HashMap(2);
        if (zIMResponse != null) {
            switch (zIMResponse.code) {
                case 100:
                case 1000:
                    hashMap.put("result", a);
                    break;
                default:
                    hashMap.put("result", b);
                    break;
            }
            hashMap.put("message", zIMResponse.reason);
            hashMap.put("retCode", zIMResponse.code);
            hashMap.put("subCode", zIMResponse.subCode);
            hashMap.put("subMsg", zIMResponse.msg);
        } else {
            hashMap.put("result", b);
            hashMap.put("message", "0");
            hashMap.put("retCode", "0");
            hashMap.put("subCode", "");
            hashMap.put("subMsg", "");
        }
        a.a().a(a.m, hashMap);
    }

    public final void command(int i2) {
        this.i.command(i2);
    }
}

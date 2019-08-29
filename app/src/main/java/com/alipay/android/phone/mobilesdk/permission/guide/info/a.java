package com.alipay.android.phone.mobilesdk.permission.guide.info;

import android.content.Context;
import com.alipay.android.phone.mobilesdk.permission.guide.PermissionType;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.PgDataPB;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PGInfoRpcTask */
public final class a implements Runnable {
    private static boolean a = true;

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r25 = this;
            com.alipay.mobile.common.logging.api.ProcessInfo r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x010c }
            boolean r21 = r21.isMainProcess()     // Catch:{ Throwable -> 0x010c }
            if (r21 != 0) goto L_0x0016
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.String r23 = "cancel rpc task because it's not in process."
            r21.info(r22, r23)     // Catch:{ Throwable -> 0x010c }
        L_0x0015:
            return
        L_0x0016:
            com.alipay.mobile.framework.LauncherApplicationAgent r6 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x010c }
            android.app.Application r7 = r6.getApplicationContext()     // Catch:{ Throwable -> 0x010c }
            boolean r21 = a     // Catch:{ Throwable -> 0x010c }
            if (r21 != 0) goto L_0x012c
            java.util.concurrent.TimeUnit r21 = java.util.concurrent.TimeUnit.HOURS     // Catch:{ Throwable -> 0x010c }
            r22 = 6
            long r8 = r21.toMinutes(r22)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "duration="
            r23.<init>(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r8)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "minutes."
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x010c }
            r21.debug(r22, r23)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = "common_guide_config_rpc_interval"
            java.lang.String r20 = com.alipay.android.phone.mobilesdk.permission.guide.f.a(r21)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "strDuration="
            r23.<init>(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r8)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "minutes."
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x010c }
            r21.debug(r22, r23)     // Catch:{ Throwable -> 0x010c }
            boolean r21 = android.text.TextUtils.isEmpty(r20)     // Catch:{ Throwable -> 0x010c }
            if (r21 != 0) goto L_0x0081
            long r4 = java.lang.Long.parseLong(r20)     // Catch:{ Throwable -> 0x011c }
            r22 = 0
            int r21 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1))
            if (r21 <= 0) goto L_0x0081
            r8 = r4
        L_0x0081:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "final duration="
            r23.<init>(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r8)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "minutes."
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x010c }
            r21.debug(r22, r23)     // Catch:{ Throwable -> 0x010c }
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x010c }
            long r16 = com.alipay.android.phone.mobilesdk.permission.guide.a.a.b(r7)     // Catch:{ Throwable -> 0x010c }
            long r14 = r18 - r16
            java.util.concurrent.TimeUnit r21 = java.util.concurrent.TimeUnit.MINUTES     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            long r8 = r0.toMillis(r8)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.StringBuilder r23 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "now="
            r23.<init>(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            r1 = r18
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = ", lastTime="
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            r1 = r16
            java.lang.StringBuilder r23 = r0.append(r1)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = ". => (now - lastTime)="
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r14)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = "ms, duration="
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r8)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r24 = " ms."
            java.lang.StringBuilder r23 = r23.append(r24)     // Catch:{ Throwable -> 0x010c }
            java.lang.String r23 = r23.toString()     // Catch:{ Throwable -> 0x010c }
            r21.debug(r22, r23)     // Catch:{ Throwable -> 0x010c }
            int r21 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r21 > 0) goto L_0x012c
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.String r23 = "(now - lastTime) <= duration, return."
            r21.debug(r22, r23)     // Catch:{ Throwable -> 0x010c }
            goto L_0x0015
        L_0x010c:
            r10 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r22 = "Permissions_GetInfo"
            r0 = r21
            r1 = r22
            r0.error(r1, r10)
            goto L_0x0015
        L_0x011c:
            r10 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            r0 = r21
            r1 = r22
            r0.warn(r1, r10)     // Catch:{ Throwable -> 0x010c }
            goto L_0x0081
        L_0x012c:
            long r22 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x010c }
            r0 = r22
            com.alipay.android.phone.mobilesdk.permission.guide.a.a.a(r7, r0)     // Catch:{ Throwable -> 0x010c }
            com.alipay.android.phone.mobilesdk.permission.guide.a.c r11 = com.alipay.android.phone.mobilesdk.permission.guide.a.c.a()     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoReqPB r12 = new com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoReqPB     // Catch:{ Throwable -> 0x010c }
            r12.<init>()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = "10.1.38"
            r0 = r21
            r12.productVersion = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = com.alipay.android.phone.mobilesdk.permission.sdk.PermissionGuideSdk.getAppCode()     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.appCode = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = android.os.Build.MODEL     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.mobileModel = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = android.os.Build.MANUFACTURER     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.manufacturer = r0     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.DeviceProperty r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getDeviceProperty()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = r21.getRomVersion()     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.romVersion = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = android.os.Build.BRAND     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.mobileBrand = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = com.alipay.android.phone.mobilesdk.permission.utils.f.a(r7)     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.netType = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = com.alipay.android.phone.mobilesdk.permission.guide.a.a.c(r7)     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.lastTime = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = "android"
            r0 = r21
            r12.platform = r0     // Catch:{ Throwable -> 0x010c }
            java.lang.String r21 = android.os.Build.VERSION.RELEASE     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.osVersion = r0     // Catch:{ Throwable -> 0x010c }
            java.util.List r21 = a(r7)     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            r12.extraData = r0     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.String r23 = java.lang.String.valueOf(r12)     // Catch:{ Throwable -> 0x010c }
            r21.warn(r22, r23)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.framework.MicroApplicationContext r21 = r6.getMicroApplicationContext()     // Catch:{ Throwable -> 0x010c }
            java.lang.Class<com.alipay.mobile.framework.service.common.RpcService> r22 = com.alipay.mobile.framework.service.common.RpcService.class
            java.lang.String r22 = r22.getName()     // Catch:{ Throwable -> 0x010c }
            java.lang.Object r21 = r21.findServiceByInterface(r22)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.framework.service.common.RpcService r21 = (com.alipay.mobile.framework.service.common.RpcService) r21     // Catch:{ Throwable -> 0x010c }
            java.lang.Class<com.alipay.mobileappcommon.biz.rpc.pginfo.PGInfoFacade> r22 = com.alipay.mobileappcommon.biz.rpc.pginfo.PGInfoFacade.class
            java.lang.Object r21 = r21.getPBRpcProxy(r22)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobileappcommon.biz.rpc.pginfo.PGInfoFacade r21 = (com.alipay.mobileappcommon.biz.rpc.pginfo.PGInfoFacade) r21     // Catch:{ Throwable -> 0x010c }
            r0 = r21
            com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoRespPB r13 = r0.getPGInfo(r12)     // Catch:{ Throwable -> 0x010c }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r21 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x010c }
            java.lang.String r22 = "Permissions_GetInfo"
            java.lang.String r23 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x010c }
            r21.warn(r22, r23)     // Catch:{ Throwable -> 0x010c }
            if (r13 == 0) goto L_0x0015
            r21 = 0
            a = r21     // Catch:{ Throwable -> 0x010c }
            java.lang.Boolean r0 = r13.success     // Catch:{ Throwable -> 0x010c }
            r21 = r0
            boolean r21 = r21.booleanValue()     // Catch:{ Throwable -> 0x010c }
            if (r21 == 0) goto L_0x0015
            r11.a(r7, r13)     // Catch:{ Throwable -> 0x010c }
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.guide.info.a.run():void");
    }

    private static List<PgDataPB> a(Context context) {
        List list = new ArrayList();
        PgDataPB shortcutPB = new PgDataPB();
        shortcutPB.key = String.valueOf(PermissionType.SHORTCUT);
        shortcutPB.value = b.f().getRpcName();
        list.add(shortcutPB);
        PgDataPB selfStartingPB = new PgDataPB();
        selfStartingPB.key = String.valueOf(PermissionType.SELFSTARTING);
        selfStartingPB.value = b.e().getRpcName();
        list.add(selfStartingPB);
        PgDataPB shinfoPB = new PgDataPB();
        shinfoPB.key = String.valueOf(PermissionType.SHINFO);
        shinfoPB.value = b.d(context).getRpcName();
        list.add(shinfoPB);
        PgDataPB microPhonePB = new PgDataPB();
        microPhonePB.key = String.valueOf(PermissionType.MICROPHONE);
        microPhonePB.value = b.c(context).getRpcName();
        list.add(microPhonePB);
        PgDataPB cameraPB = new PgDataPB();
        cameraPB.key = String.valueOf(PermissionType.CAMERA);
        cameraPB.value = b.b(context).getRpcName();
        list.add(cameraPB);
        PgDataPB addressPB = new PgDataPB();
        addressPB.key = String.valueOf(PermissionType.ADDRESSBOOK);
        addressPB.value = b.a(context).getRpcName();
        list.add(addressPB);
        PgDataPB notificationPB = new PgDataPB();
        notificationPB.key = String.valueOf(PermissionType.NOTIFICATION);
        notificationPB.value = b.d().getRpcName();
        list.add(notificationPB);
        int permissionFromLBSBundle = b.h();
        LoggerFactory.getTraceLogger().info("Permissions_GetInfo", "permissionFromLBSBundle: " + permissionFromLBSBundle);
        if (permissionFromLBSBundle != -1) {
            PgDataPB lbsPB = new PgDataPB();
            lbsPB.key = String.valueOf(PermissionType.LBS);
            lbsPB.value = String.valueOf(permissionFromLBSBundle);
            list.add(lbsPB);
            PgDataPB lbsServicePB = new PgDataPB();
            lbsServicePB.key = String.valueOf(PermissionType.LBSSERVICE);
            lbsServicePB.value = String.valueOf(permissionFromLBSBundle);
            list.add(lbsServicePB);
        } else {
            PgDataPB lbsPB2 = new PgDataPB();
            lbsPB2.key = String.valueOf(PermissionType.LBS);
            lbsPB2.value = b.b().getRpcName();
            list.add(lbsPB2);
            PgDataPB lbsServicePB2 = new PgDataPB();
            lbsServicePB2.key = String.valueOf(PermissionType.LBSSERVICE);
            lbsServicePB2.value = b.c().getRpcName();
            list.add(lbsServicePB2);
        }
        PgDataPB backgrounderPB = new PgDataPB();
        backgrounderPB.key = String.valueOf(PermissionType.BACKGROUNDER);
        backgrounderPB.value = b.g().getRpcName();
        list.add(backgrounderPB);
        return list;
    }
}

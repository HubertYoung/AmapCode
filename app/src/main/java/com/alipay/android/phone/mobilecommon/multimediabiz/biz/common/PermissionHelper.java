package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build.VERSION;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.OnPermissionResultHandler;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.PermissionResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
    public static final int PERMISSION_ACQUIRE_AUDIO = 1;
    public static final int PERMISSION_ACQUIRE_CAMERA = 2;
    public static final int PERMISSION_REQUEST_CODE_AUDIO_REOPEN = 4;
    public static final int PERMISSION_REQUEST_CODE_RECORD_AUDIO = 1;
    public static final int PERMISSION_REQUEST_CODE_VIDEO_AUDIO = 2;
    private static boolean a = false;

    public static void acquirePermissions(final int... permissions) {
        if (a()) {
            new Thread(new Runnable() {
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r8 = this;
                        r2 = 0
                        int[] r3 = r3
                        int r4 = r3.length
                        r1 = r2
                    L_0x0005:
                        if (r1 >= r4) goto L_0x0035
                        r0 = r3[r1]
                        switch(r0) {
                            case 1: goto L_0x000f;
                            case 2: goto L_0x0031;
                            default: goto L_0x000c;
                        }
                    L_0x000c:
                        int r1 = r1 + 1
                        goto L_0x0005
                    L_0x000f:
                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper.acquireAudioPermission()     // Catch:{ Exception -> 0x0013 }
                        goto L_0x000c
                    L_0x0013:
                        r5 = move-exception
                        java.lang.String r5 = "PermissionHelper"
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder
                        java.lang.String r7 = "take it easy, acquire permission for "
                        r6.<init>(r7)
                        java.lang.StringBuilder r6 = r6.append(r0)
                        java.lang.String r7 = " error"
                        java.lang.StringBuilder r6 = r6.append(r7)
                        java.lang.String r6 = r6.toString()
                        java.lang.Object[] r7 = new java.lang.Object[r2]
                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r5, r6, r7)
                        goto L_0x000c
                    L_0x0031:
                        com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper.acquireCameraPermission()     // Catch:{ Exception -> 0x0013 }
                        goto L_0x000c
                    L_0x0035:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper.AnonymousClass1.run():void");
                }
            }, "permission-acquire-thread").start();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0074 A[SYNTHETIC, Splitter:B:22:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0087 A[SYNTHETIC, Splitter:B:29:0x0087] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void acquireAudioPermission() {
        /*
            r11 = 16000(0x3e80, float:2.2421E-41)
            r10 = 16
            r4 = 2
            r9 = 1
            r8 = 0
            java.lang.String r1 = "PermissionHelper"
            java.lang.String r2 = "acquireAudioPermission enter"
            java.lang.Object[] r3 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r1, r2, r3)
            java.lang.String r1 = "android.permission.RECORD_AUDIO"
            boolean r1 = hasPermission(r1)
            if (r1 != 0) goto L_0x0019
        L_0x0018:
            return
        L_0x0019:
            int r1 = android.os.Build.VERSION.SDK_INT
            r2 = 23
            if (r1 >= r2) goto L_0x0018
            java.lang.String r1 = "PermissionHelper"
            java.lang.String r2 = "acquireAudioPermission hasRecordAudio permission"
            java.lang.Object[] r3 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r1, r2, r3)
            int r5 = android.media.AudioRecord.getMinBufferSize(r11, r10, r4)
            r6 = 0
            android.media.AudioRecord r0 = new android.media.AudioRecord     // Catch:{ Exception -> 0x0059, all -> 0x0083 }
            r1 = 1
            r2 = 16000(0x3e80, float:2.2421E-41)
            r3 = 16
            r4 = 2
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0059, all -> 0x0083 }
            int r1 = r0.getState()     // Catch:{ Exception -> 0x0098 }
            if (r1 != r9) goto L_0x0041
            r0.startRecording()     // Catch:{ Exception -> 0x0098 }
        L_0x0041:
            r0.release()     // Catch:{ Exception -> 0x004e }
        L_0x0044:
            java.lang.String r1 = "PermissionHelper"
            java.lang.String r2 = "acquireAudioPermission finish"
            java.lang.Object[] r3 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.D(r1, r2, r3)
            goto L_0x0018
        L_0x004e:
            r7 = move-exception
            java.lang.String r1 = "PermissionHelper"
            java.lang.String r2 = "acquireAudioPermission exp "
            java.lang.Object[] r3 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r1, r7, r2, r3)
            goto L_0x0044
        L_0x0059:
            r7 = move-exception
            r0 = r6
        L_0x005b:
            java.lang.String r1 = "PermissionHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = "take it easy, acquireAudioPermission error, "
            r2.<init>(r3)     // Catch:{ all -> 0x0096 }
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0096 }
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0096 }
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.W(r1, r2, r3)     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0044
            r0.release()     // Catch:{ Exception -> 0x0078 }
            goto L_0x0044
        L_0x0078:
            r7 = move-exception
            java.lang.String r1 = "PermissionHelper"
            java.lang.String r2 = "acquireAudioPermission exp "
            java.lang.Object[] r3 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r1, r7, r2, r3)
            goto L_0x0044
        L_0x0083:
            r1 = move-exception
            r0 = r6
        L_0x0085:
            if (r0 == 0) goto L_0x008a
            r0.release()     // Catch:{ Exception -> 0x008b }
        L_0x008a:
            throw r1
        L_0x008b:
            r7 = move-exception
            java.lang.String r2 = "PermissionHelper"
            java.lang.String r3 = "acquireAudioPermission exp "
            java.lang.Object[] r4 = new java.lang.Object[r8]
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger.E(r2, r7, r3, r4)
            goto L_0x008a
        L_0x0096:
            r1 = move-exception
            goto L_0x0085
        L_0x0098:
            r7 = move-exception
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.PermissionHelper.acquireAudioPermission():void");
    }

    @TargetApi(9)
    public static void acquireCameraPermission() {
        Logger.P("PermissionHelper", "acquireCameraPermission enter", new Object[0]);
        if (hasPermission("android.permission.CAMERA")) {
            Logger.P("PermissionHelper", "acquireCameraPermission hasCamera permission", new Object[0]);
            int cameraCount = Camera.getNumberOfCameras();
            Logger.P("PermissionHelper", "acquireCameraPermission cameraCount: " + cameraCount, new Object[0]);
            if (cameraCount > 0) {
                try {
                    Camera camera = Camera.open();
                    if (camera != null) {
                        camera.release();
                    }
                } catch (Exception e) {
                    Logger.E("PermissionHelper", "take it easy, acquireCameraPermission error, " + e, new Object[0]);
                }
            }
            Logger.P("PermissionHelper", "acquireCameraPermission finish", new Object[0]);
            return;
        }
        return;
    }

    public static boolean hasPermission(String permission) {
        Context context = AppUtils.getApplicationContext();
        if (context == null) {
            return false;
        }
        try {
            if (VERSION.SDK_INT < 23) {
                return context.getPackageManager().checkPermission(permission, context.getPackageName()) == 0;
            }
            Logger.P("PermissionHelper", "> 23, hasPermission permission: " + permission + ", enter", new Object[0]);
            int ret = ContextCompat.checkSelfPermission(context, permission);
            Logger.P("PermissionHelper", "> 23, hasPermission permission: " + permission + ", ret: " + ret, new Object[0]);
            return ret == 0;
        } catch (Throwable e) {
            Logger.E((String) "PermissionHelper", e, (String) "take it easy, os rejected this operation", new Object[0]);
            return false;
        }
    }

    public static boolean checkVideoPermission(int mode) {
        return hasPermission("android.permission.CAMERA") && (mode != 0 || hasPermission("android.permission.RECORD_AUDIO"));
    }

    public static void requireVideoPermission(Object activityOrFragment, int mode, int reqCode) {
        List requirePermissions = new ArrayList();
        String[] strArr = {"android.permission.CAMERA", "android.permission.RECORD_AUDIO"};
        for (int i = 0; i < 2; i++) {
            String permission = strArr[i];
            if (!hasPermission(permission) && (mode != 1 || !permission.equals("android.permission.RECORD_AUDIO"))) {
                requirePermissions.add(permission);
            }
        }
        if (!requirePermissions.isEmpty()) {
            String[] permissions = new String[requirePermissions.size()];
            requirePermissions.toArray(permissions);
            if (activityOrFragment instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) activityOrFragment, permissions, reqCode);
            } else if (activityOrFragment instanceof Fragment) {
                FragmentCompat.requestPermissions((Fragment) activityOrFragment, permissions, reqCode);
            }
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object activityOrFragment, OnPermissionResultHandler handler) {
        boolean hasShowSystemDialog;
        boolean z;
        if (handler != null && requestCode == 1) {
            boolean should = a(activityOrFragment, "android.permission.RECORD_AUDIO");
            if (a || should) {
                hasShowSystemDialog = true;
            } else {
                hasShowSystemDialog = false;
            }
            boolean granted = true;
            for (int g : grantResults) {
                if (g == 0) {
                    z = true;
                } else {
                    z = false;
                }
                granted &= z;
            }
            PermissionResult result = new PermissionResult();
            result.granted = granted;
            result.showedSystemDialog = hasShowSystemDialog;
            result.shouldRequestPermissionRationale = should;
            result.requirePermissions = permissions;
            result.grantedResults = grantResults;
            handler.onRequestPermission(result);
        }
    }

    private static boolean a(Object context, String permission) {
        if (context instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
        }
        if (context instanceof Fragment) {
            return FragmentCompat.shouldShowRequestPermissionRationale((Fragment) context, permission);
        }
        if (context instanceof android.support.v4.app.Fragment) {
            return ((android.support.v4.app.Fragment) context).shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    public static void requireRecordAudioPermission(Object activityOrFragment) {
        String[] permissions = {"android.permission.RECORD_AUDIO"};
        a = a(activityOrFragment, "android.permission.RECORD_AUDIO");
        if (activityOrFragment instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) activityOrFragment).requestPermissions(permissions, 1);
        } else if (activityOrFragment instanceof Fragment) {
            FragmentCompat.requestPermissions((Fragment) activityOrFragment, permissions, 1);
        } else if (activityOrFragment instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) activityOrFragment, permissions, 1);
        } else {
            throw new IllegalArgumentException("activityOrFragment is not activity, android.app.Fragment, android.support.v4.app.Fragment !!!!!");
        }
    }

    private static boolean a() {
        if (VERSION.SDK_INT >= 23 || ConfigManager.getInstance().getCommonConfigItem().enablePreAcquirePermissions != 1) {
            return false;
        }
        return true;
    }
}

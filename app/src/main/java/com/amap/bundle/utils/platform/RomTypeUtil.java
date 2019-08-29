package com.amap.bundle.utils.platform;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class RomTypeUtil {
    private static final ROM a = b();

    public enum ROM {
        MIUI,
        Flyme,
        EMUI,
        ColorOS,
        FuntouchOS,
        SmartisanOS,
        EUI,
        Sense,
        AmigoOS,
        _360OS,
        NubiaUI,
        H2OS,
        YunOS,
        YuLong,
        SamSung,
        Sony,
        Lenovo,
        LG,
        Google,
        Other;
        
        int a;
        String b;

        public final int getBaseVersion() {
            return this.a;
        }

        public final String getVersion() {
            return this.b;
        }

        public final Intent getPermissionSettingIntent(Context context) {
            Intent intent = new Intent();
            intent.setFlags(268435456);
            String packageName = context.getPackageName();
            switch (this) {
                case EMUI:
                    intent.putExtra("packageName", packageName);
                    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                    return intent;
                case Flyme:
                    intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.putExtra("packageName", packageName);
                    return intent;
                case MIUI:
                    String a2 = RomTypeUtil.a();
                    if ("V6".equals(a2) || "V7".equals(a2)) {
                        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", packageName);
                        return intent;
                    } else if (!"V8".equals(a2) && !"V9".equals(a2)) {
                        return RomTypeUtil.a(packageName);
                    } else {
                        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", packageName);
                        return intent;
                    }
                case Sony:
                    intent.putExtra("packageName", packageName);
                    intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
                    return intent;
                case ColorOS:
                    intent.putExtra("packageName", packageName);
                    intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
                    return intent;
                case EUI:
                    intent.putExtra("packageName", packageName);
                    intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
                    return intent;
                case LG:
                    intent.setAction("android.intent.action.MAIN");
                    intent.putExtra("packageName", packageName);
                    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
                    return intent;
                case SamSung:
                case SmartisanOS:
                    return RomTypeUtil.a(packageName);
                default:
                    intent.setAction("android.settings.SETTINGS");
                    return intent;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:275:0x0420, code lost:
        r4 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:0x0421, code lost:
        switch(r4) {
            case 0: goto L_0x047d;
            case 1: goto L_0x0472;
            case 2: goto L_0x0467;
            case 3: goto L_0x045c;
            case 4: goto L_0x0451;
            case 5: goto L_0x0446;
            case 6: goto L_0x043b;
            case 7: goto L_0x0430;
            case 8: goto L_0x0425;
            default: goto L_0x0424;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:278:0x0425, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.AmigoOS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:280:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x042b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:282:0x042c, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Lenovo;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:288:0x0436, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:289:0x0437, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sense;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:294:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x0441, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:296:0x0442, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:299:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.YuLong;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:301:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x044c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x044d, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:306:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sony;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:308:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:309:0x0457, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:310:0x0458, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:313:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.SamSung;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:315:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:316:0x0462, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:317:0x0463, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:320:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.FuntouchOS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:322:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:323:0x046d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:324:0x046e, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:327:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.ColorOS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:329:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:330:0x0478, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:331:0x0479, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:334:?, code lost:
        r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.MIUI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:336:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:337:0x0483, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x0484, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x03b6 A[Catch:{ IOException -> 0x0496 }] */
    /* JADX WARNING: Removed duplicated region for block: B:354:0x04a3 A[SYNTHETIC, Splitter:B:354:0x04a3] */
    /* JADX WARNING: Removed duplicated region for block: B:359:0x04aa A[SYNTHETIC, Splitter:B:359:0x04aa] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:142:0x0213=Splitter:B:142:0x0213, B:187:0x02d3=Splitter:B:187:0x02d3, B:157:0x0257=Splitter:B:157:0x0257, B:202:0x0317=Splitter:B:202:0x0317, B:217:0x0359=Splitter:B:217:0x0359, B:122:0x01bb=Splitter:B:122:0x01bb, B:172:0x028f=Splitter:B:172:0x028f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.amap.bundle.utils.platform.RomTypeUtil.ROM b() {
        /*
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r0 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Other
            r1 = 0
            java.util.Properties r2 = new java.util.Properties     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            r2.<init>()     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            java.io.File r4 = new java.io.File     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            java.io.File r5 = android.os.Environment.getRootDirectory()     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            java.lang.String r6 = "build.prop"
            r4.<init>(r5, r6)     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x049b, all -> 0x0498 }
            r2.load(r3)     // Catch:{ IOException -> 0x0496 }
            java.lang.String r1 = "ro.miui.ui.version.name"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            r4 = 0
            r5 = 1
            if (r1 != 0) goto L_0x0359
            java.lang.String r1 = "ro.miui.ui.version.code"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0031
            goto L_0x0359
        L_0x0031:
            java.lang.String r1 = "ro.build.version.emui"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0317
            java.lang.String r1 = "ro.build.hw_emui_api_level"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0317
            java.lang.String r1 = "ro.confg.hw_systemversion"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x004e
            goto L_0x0317
        L_0x004e:
            java.lang.String r1 = "ro.meizu.setupwizard.flyme"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x02d3
            java.lang.String r1 = "ro.flyme.published"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0062
            goto L_0x02d3
        L_0x0062:
            java.lang.String r1 = "ro.oppo.theme.version"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x028f
            java.lang.String r1 = "ro.oppo.version"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x028f
            java.lang.String r1 = "ro.rom.different.version"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x007f
            goto L_0x028f
        L_0x007f:
            java.lang.String r1 = "ro.vivo.os.name"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0257
            java.lang.String r1 = "ro.vivo.os.version"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0257
            java.lang.String r1 = "ro.vivo.os.build.display.id"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x009c
            goto L_0x0257
        L_0x009c:
            java.lang.String r1 = "ro.letv.release.version"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0213
            java.lang.String r1 = "ro.product.letv_name"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x0213
            java.lang.String r1 = "ro.product.letv_model"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x00b9
            goto L_0x0213
        L_0x00b9:
            java.lang.String r1 = "ro.gn.gnromvernumber"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01cf
            java.lang.String r1 = "ro.gn.amigo.systemui.support"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x00cd
            goto L_0x01cf
        L_0x00cd:
            java.lang.String r1 = "ro.sony.irremote.protocol_type"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01cb
            java.lang.String r1 = "ro.sony.fota.encrypteddata"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x00e1
            goto L_0x01cb
        L_0x00e1:
            java.lang.String r1 = "ro.yulong.version.release"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01c7
            java.lang.String r1 = "ro.yulong.version.tag"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x00f5
            goto L_0x01c7
        L_0x00f5:
            java.lang.String r1 = "htc.build.stage"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01c3
            java.lang.String r1 = "ro.htc.bluetooth.sap"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0108
            goto L_0x01c3
        L_0x0108:
            java.lang.String r1 = "ro.lge.swversion"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01bf
            java.lang.String r1 = "ro.lge.swversion_short"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01bf
            java.lang.String r1 = "ro.lge.factoryversion"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0125
            goto L_0x01bf
        L_0x0125:
            java.lang.String r1 = "ro.lenovo.device"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01bb
            java.lang.String r1 = "ro.lenovo.platform"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 != 0) goto L_0x01bb
            java.lang.String r1 = "ro.lenovo.adb"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0142
            goto L_0x01bb
        L_0x0142:
            java.lang.String r1 = "ro.build.display.id"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x017e
            java.lang.String r1 = "ro.build.display.id"
            java.lang.String r1 = r2.getProperty(r1)     // Catch:{ IOException -> 0x0496 }
            boolean r6 = android.text.TextUtils.isEmpty(r1)     // Catch:{ IOException -> 0x0496 }
            if (r6 != 0) goto L_0x03ad
            java.lang.String r6 = "Flyme"
            boolean r6 = r1.contains(r6)     // Catch:{ IOException -> 0x0496 }
            if (r6 == 0) goto L_0x016b
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Flyme     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0166 }
            goto L_0x016a
        L_0x0166:
            r0 = move-exception
            r0.printStackTrace()
        L_0x016a:
            return r1
        L_0x016b:
            java.lang.String r6 = "amigo"
            boolean r1 = r1.contains(r6)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x03ad
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.AmigoOS     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0179 }
            goto L_0x017d
        L_0x0179:
            r0 = move-exception
            r0.printStackTrace()
        L_0x017d:
            return r1
        L_0x017e:
            java.lang.String r1 = "ro.build.version.base_os"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x03ad
            java.lang.String r1 = "ro.build.version.base_os"
            java.lang.String r1 = r2.getProperty(r1)     // Catch:{ IOException -> 0x0496 }
            boolean r6 = android.text.TextUtils.isEmpty(r1)     // Catch:{ IOException -> 0x0496 }
            if (r6 != 0) goto L_0x03ad
            java.lang.String r6 = "OPPO"
            boolean r6 = r1.contains(r6)     // Catch:{ IOException -> 0x0496 }
            if (r6 == 0) goto L_0x01a7
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.ColorOS     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x01a2 }
            goto L_0x01a6
        L_0x01a2:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01a6:
            return r1
        L_0x01a7:
            java.lang.String r6 = "samsung"
            boolean r1 = r1.contains(r6)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x03ad
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.SamSung     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x01b6 }
            goto L_0x01ba
        L_0x01b6:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01ba:
            return r1
        L_0x01bb:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Lenovo     // Catch:{ IOException -> 0x0496 }
            goto L_0x03ac
        L_0x01bf:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.LG     // Catch:{ IOException -> 0x0496 }
            goto L_0x03ac
        L_0x01c3:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sense     // Catch:{ IOException -> 0x0496 }
            goto L_0x03ac
        L_0x01c7:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.YuLong     // Catch:{ IOException -> 0x0496 }
            goto L_0x03ac
        L_0x01cb:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sony     // Catch:{ IOException -> 0x0496 }
            goto L_0x03ac
        L_0x01cf:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.AmigoOS     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.build.display.id"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.build.display.id"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            java.lang.String r6 = "amigo([\\d.]+)[a-zA-Z]*"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ IOException -> 0x0491 }
            java.util.regex.Matcher r6 = r6.matcher(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 != 0) goto L_0x03ac
            boolean r0 = r6.find()     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = r6.group(r5)     // Catch:{ Exception -> 0x020d }
            r1.b = r0     // Catch:{ Exception -> 0x020d }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x020d }
            r0 = r0[r4]     // Catch:{ Exception -> 0x020d }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x020d }
            r1.a = r0     // Catch:{ Exception -> 0x020d }
            goto L_0x03ac
        L_0x020d:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x0213:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.EUI     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.letv.release.version"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.letv.release.version"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            java.lang.String r6 = "([\\d.]+)[^\\d]*"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ IOException -> 0x0491 }
            java.util.regex.Matcher r6 = r6.matcher(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 != 0) goto L_0x03ac
            boolean r0 = r6.find()     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = r6.group(r5)     // Catch:{ Exception -> 0x0251 }
            r1.b = r0     // Catch:{ Exception -> 0x0251 }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x0251 }
            r0 = r0[r4]     // Catch:{ Exception -> 0x0251 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0251 }
            r1.a = r0     // Catch:{ Exception -> 0x0251 }
            goto L_0x03ac
        L_0x0251:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x0257:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.FuntouchOS     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.vivo.os.version"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.vivo.os.version"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r6 != 0) goto L_0x03ac
            java.lang.String r6 = "[\\d.]+"
            boolean r6 = r0.matches(r6)     // Catch:{ IOException -> 0x0491 }
            if (r6 == 0) goto L_0x03ac
            r1.b = r0     // Catch:{ Exception -> 0x0289 }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x0289 }
            r0 = r0[r4]     // Catch:{ Exception -> 0x0289 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0289 }
            r1.a = r0     // Catch:{ Exception -> 0x0289 }
            goto L_0x03ac
        L_0x0289:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x028f:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.ColorOS     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.rom.different.version"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.rom.different.version"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            java.lang.String r6 = "ColorOS([\\d.]+)"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ IOException -> 0x0491 }
            java.util.regex.Matcher r6 = r6.matcher(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 != 0) goto L_0x03ac
            boolean r0 = r6.find()     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = r6.group(r5)     // Catch:{ Exception -> 0x02cd }
            r1.b = r0     // Catch:{ Exception -> 0x02cd }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x02cd }
            r0 = r0[r4]     // Catch:{ Exception -> 0x02cd }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x02cd }
            r1.a = r0     // Catch:{ Exception -> 0x02cd }
            goto L_0x03ac
        L_0x02cd:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x02d3:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Flyme     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.build.display.id"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.build.display.id"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            java.lang.String r6 = "Flyme[^\\d]*([\\d.]+)[^\\d]*"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ IOException -> 0x0491 }
            java.util.regex.Matcher r6 = r6.matcher(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 != 0) goto L_0x03ac
            boolean r0 = r6.find()     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = r6.group(r5)     // Catch:{ Exception -> 0x0311 }
            r1.b = r0     // Catch:{ Exception -> 0x0311 }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x0311 }
            r0 = r0[r4]     // Catch:{ Exception -> 0x0311 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0311 }
            r1.a = r0     // Catch:{ Exception -> 0x0311 }
            goto L_0x03ac
        L_0x0311:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x0317:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.EMUI     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.build.version.emui"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.build.version.emui"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            java.lang.String r6 = "EmotionUI_([\\d.]+)"
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ IOException -> 0x0491 }
            java.util.regex.Matcher r6 = r6.matcher(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 != 0) goto L_0x03ac
            boolean r0 = r6.find()     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = r6.group(r5)     // Catch:{ Exception -> 0x0354 }
            r1.b = r0     // Catch:{ Exception -> 0x0354 }
            java.lang.String r6 = "\\."
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x0354 }
            r0 = r0[r4]     // Catch:{ Exception -> 0x0354 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0354 }
            r1.a = r0     // Catch:{ Exception -> 0x0354 }
            goto L_0x03ac
        L_0x0354:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
            goto L_0x03ac
        L_0x0359:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.MIUI     // Catch:{ IOException -> 0x0496 }
            java.lang.String r0 = "ro.miui.ui.version.name"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x038c
            java.lang.String r0 = "ro.miui.ui.version.name"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r6 != 0) goto L_0x038c
            java.lang.String r6 = "[Vv]\\d+"
            boolean r6 = r0.matches(r6)     // Catch:{ IOException -> 0x0491 }
            if (r6 == 0) goto L_0x038c
            java.lang.String r6 = "[Vv]"
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Exception -> 0x0388 }
            r0 = r0[r5]     // Catch:{ Exception -> 0x0388 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0388 }
            r1.a = r0     // Catch:{ Exception -> 0x0388 }
            goto L_0x038c
        L_0x0388:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x0491 }
        L_0x038c:
            java.lang.String r0 = "ro.build.version.incremental"
            boolean r0 = r2.containsKey(r0)     // Catch:{ IOException -> 0x0491 }
            if (r0 == 0) goto L_0x03ac
            java.lang.String r0 = "ro.build.version.incremental"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x0491 }
            boolean r6 = android.text.TextUtils.isEmpty(r0)     // Catch:{ IOException -> 0x0491 }
            if (r6 != 0) goto L_0x03ac
            java.lang.String r6 = "[\\d.]+"
            boolean r6 = r0.matches(r6)     // Catch:{ IOException -> 0x0491 }
            if (r6 == 0) goto L_0x03ac
            r1.b = r0     // Catch:{ IOException -> 0x0491 }
        L_0x03ac:
            r0 = r1
        L_0x03ad:
            java.lang.String r1 = "ro.com.google.clientidbase"
            boolean r1 = r2.containsKey(r1)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0488
            java.lang.String r1 = "ro.com.google.clientidbase"
            java.lang.String r1 = r2.getProperty(r1)     // Catch:{ IOException -> 0x0496 }
            r2 = -1
            int r6 = r1.hashCode()     // Catch:{ IOException -> 0x0496 }
            switch(r6) {
                case -1297558593: goto L_0x0415;
                case -1158135215: goto L_0x040b;
                case -1037975490: goto L_0x0401;
                case -1037773494: goto L_0x03f7;
                case -811278887: goto L_0x03ee;
                case -652932276: goto L_0x03e4;
                case -380192433: goto L_0x03da;
                case -64814069: goto L_0x03d0;
                case 259783324: goto L_0x03c6;
                default: goto L_0x03c5;
            }     // Catch:{ IOException -> 0x0496 }
        L_0x03c5:
            goto L_0x0420
        L_0x03c6:
            java.lang.String r4 = "android-samsung"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 3
            goto L_0x0421
        L_0x03d0:
            java.lang.String r4 = "android-sonyericsson"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 4
            goto L_0x0421
        L_0x03da:
            java.lang.String r4 = "android-htc-rev"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 6
            goto L_0x0421
        L_0x03e4:
            java.lang.String r4 = "android-coolpad"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 5
            goto L_0x0421
        L_0x03ee:
            java.lang.String r5 = "android-xiaomi"
            boolean r1 = r1.equals(r5)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            goto L_0x0421
        L_0x03f7:
            java.lang.String r4 = "android-vivo"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 2
            goto L_0x0421
        L_0x0401:
            java.lang.String r4 = "android-oppo"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 1
            goto L_0x0421
        L_0x040b:
            java.lang.String r4 = "android-lenovo"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 7
            goto L_0x0421
        L_0x0415:
            java.lang.String r4 = "android-gionee"
            boolean r1 = r1.equals(r4)     // Catch:{ IOException -> 0x0496 }
            if (r1 == 0) goto L_0x0420
            r4 = 8
            goto L_0x0421
        L_0x0420:
            r4 = -1
        L_0x0421:
            switch(r4) {
                case 0: goto L_0x047d;
                case 1: goto L_0x0472;
                case 2: goto L_0x0467;
                case 3: goto L_0x045c;
                case 4: goto L_0x0451;
                case 5: goto L_0x0446;
                case 6: goto L_0x043b;
                case 7: goto L_0x0430;
                case 8: goto L_0x0425;
                default: goto L_0x0424;
            }     // Catch:{ IOException -> 0x0496 }
        L_0x0424:
            goto L_0x0488
        L_0x0425:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.AmigoOS     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x042b }
            goto L_0x042f
        L_0x042b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x042f:
            return r1
        L_0x0430:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Lenovo     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0436 }
            goto L_0x043a
        L_0x0436:
            r0 = move-exception
            r0.printStackTrace()
        L_0x043a:
            return r1
        L_0x043b:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sense     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0441 }
            goto L_0x0445
        L_0x0441:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0445:
            return r1
        L_0x0446:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.YuLong     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x044c }
            goto L_0x0450
        L_0x044c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0450:
            return r1
        L_0x0451:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.Sony     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0457 }
            goto L_0x045b
        L_0x0457:
            r0 = move-exception
            r0.printStackTrace()
        L_0x045b:
            return r1
        L_0x045c:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.SamSung     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0462 }
            goto L_0x0466
        L_0x0462:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0466:
            return r1
        L_0x0467:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.FuntouchOS     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x046d }
            goto L_0x0471
        L_0x046d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0471:
            return r1
        L_0x0472:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.ColorOS     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0478 }
            goto L_0x047c
        L_0x0478:
            r0 = move-exception
            r0.printStackTrace()
        L_0x047c:
            return r1
        L_0x047d:
            com.amap.bundle.utils.platform.RomTypeUtil$ROM r1 = com.amap.bundle.utils.platform.RomTypeUtil.ROM.MIUI     // Catch:{ IOException -> 0x0496 }
            r3.close()     // Catch:{ IOException -> 0x0483 }
            goto L_0x0487
        L_0x0483:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0487:
            return r1
        L_0x0488:
            r3.close()     // Catch:{ IOException -> 0x048c }
            goto L_0x04a6
        L_0x048c:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x04a6
        L_0x0491:
            r0 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x049e
        L_0x0496:
            r1 = move-exception
            goto L_0x049e
        L_0x0498:
            r0 = move-exception
            r3 = r1
            goto L_0x04a8
        L_0x049b:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L_0x049e:
            r1.printStackTrace()     // Catch:{ all -> 0x04a7 }
            if (r3 == 0) goto L_0x04a6
            r3.close()     // Catch:{ IOException -> 0x048c }
        L_0x04a6:
            return r0
        L_0x04a7:
            r0 = move-exception
        L_0x04a8:
            if (r3 == 0) goto L_0x04b2
            r3.close()     // Catch:{ IOException -> 0x04ae }
            goto L_0x04b2
        L_0x04ae:
            r1 = move-exception
            r1.printStackTrace()
        L_0x04b2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.utils.platform.RomTypeUtil.b():com.amap.bundle.utils.platform.RomTypeUtil$ROM");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041 A[SYNTHETIC, Splitter:B:17:0x0041] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004e A[SYNTHETIC, Splitter:B:25:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a() {
        /*
            java.lang.String r0 = "ro.miui.ui.version.name"
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.lang.String r3 = "getprop "
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.lang.String r0 = r3.concat(r0)     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.lang.Process r0 = r2.exec(r0)     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            r0 = 1024(0x400, float:1.435E-42)
            r2.<init>(r3, r0)     // Catch:{ IOException -> 0x003a, all -> 0x0038 }
            java.lang.String r0 = r2.readLine()     // Catch:{ IOException -> 0x0036 }
            r2.close()     // Catch:{ IOException -> 0x0036 }
            r2.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0035
        L_0x0031:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0035:
            return r0
        L_0x0036:
            r0 = move-exception
            goto L_0x003c
        L_0x0038:
            r0 = move-exception
            goto L_0x004c
        L_0x003a:
            r0 = move-exception
            r2 = r1
        L_0x003c:
            r0.printStackTrace()     // Catch:{ all -> 0x004a }
            if (r2 == 0) goto L_0x0049
            r2.close()     // Catch:{ IOException -> 0x0045 }
            goto L_0x0049
        L_0x0045:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0049:
            return r1
        L_0x004a:
            r0 = move-exception
            r1 = r2
        L_0x004c:
            if (r1 == 0) goto L_0x0056
            r1.close()     // Catch:{ IOException -> 0x0052 }
            goto L_0x0056
        L_0x0052:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0056:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.utils.platform.RomTypeUtil.a():java.lang.String");
    }

    public static Intent a(String str) {
        return new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.parse("package:".concat(String.valueOf(str)))).addFlags(268435456);
    }
}

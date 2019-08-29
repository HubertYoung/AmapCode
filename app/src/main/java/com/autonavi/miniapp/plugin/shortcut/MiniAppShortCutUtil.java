package com.autonavi.miniapp.plugin.shortcut;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutInfo.Builder;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickNegativeListener;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MiniAppShortCutUtil {
    private static final String TAG = "MiniAppShortCutUtil";

    MiniAppShortCutUtil() {
    }

    static void handleHaveShortCut(H5Event h5Event, String str, String str2, H5BridgeContext h5BridgeContext) {
        h5BridgeContext.sendBridgeResult("result", Boolean.valueOf(haveShortCut(h5Event.getActivity(), str, str2)));
    }

    static boolean haveShortCut(Context context, String str, String str2) {
        boolean z = true;
        boolean z2 = false;
        if (VERSION.SDK_INT >= 26) {
            ShortcutManager shortcutManager = (ShortcutManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(ShortcutManager.class);
            if (shortcutManager == null) {
                return false;
            }
            List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
            if (pinnedShortcuts == null || pinnedShortcuts.isEmpty()) {
                return false;
            }
            for (ShortcutInfo id : pinnedShortcuts) {
                if (TextUtils.equals(str, id.getId())) {
                    z2 = true;
                }
            }
            return z2;
        }
        try {
            Cursor query = context.getContentResolver().query(getUriFromLauncher(context), new String[]{"title", "intent"}, "title=?  and intent=?", new String[]{str2, getActionIntent(str).toUri(0)}, null);
            if (query == null || query.getCount() <= 0) {
                z = false;
            }
            if (query != null && !query.isClosed()) {
                query.close();
            }
            return z;
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
            return false;
        }
    }

    private static Uri getUriFromLauncher(Context context) {
        StringBuilder sb = new StringBuilder();
        String authorityFromPermissionDefault = LauncherUtil.getAuthorityFromPermissionDefault(context);
        if (authorityFromPermissionDefault == null || authorityFromPermissionDefault.trim().equals("")) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(LauncherUtil.getCurrentLauncherPackageName(context));
            sb2.append(".permission.READ_SETTINGS");
            authorityFromPermissionDefault = LauncherUtil.getAuthorityFromPermission(context, sb2.toString());
        }
        sb.append("content://");
        if (TextUtils.isEmpty(authorityFromPermissionDefault)) {
            int i = VERSION.SDK_INT;
            if (i < 8) {
                sb.append("com.android.launcher.settings");
            } else if (i < 19) {
                sb.append("com.android.launcher2.settings");
            } else {
                sb.append("com.android.launcher3.settings");
            }
        } else {
            sb.append(authorityFromPermissionDefault);
        }
        sb.append("/favorites?notify=true");
        return Uri.parse(sb.toString());
    }

    static void handleIsSupportShortcut(H5BridgeContext h5BridgeContext) {
        h5BridgeContext.sendBridgeResult("result", Boolean.valueOf(isSupportShortcut()));
    }

    static boolean isSupportShortcut() {
        if (VERSION.SDK_INT >= 26) {
            ShortcutManager shortcutManager = (ShortcutManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(ShortcutManager.class);
            if (shortcutManager != null && !shortcutManager.isRequestPinShortcutSupported()) {
                return false;
            }
        }
        return true;
    }

    static void handleSetShortCut(H5Event h5Event, final String str, final String str2, final Bitmap bitmap, final H5BridgeContext h5BridgeContext) {
        boolean z = H5Utils.getBoolean(h5Event.getParam(), (String) "needTipToasts", false);
        if (!isSupportShortcut()) {
            if (z) {
                ToastHelper.showToast("该手机设备不支持");
            }
            h5BridgeContext.sendError(5, (String) "当前设备不支持添加桌面快捷方式");
        } else if (haveShortCut(h5Event.getActivity(), str, str2)) {
            if (z) {
                ToastHelper.showToast("该应用桌面快捷方式已创建");
            }
            h5BridgeContext.sendError(6, (String) "该应用桌面快捷方式已创建");
        } else {
            if (H5Utils.getBoolean(h5Event.getParam(), (String) "needConfirmDialog", true)) {
                Activity activity = h5Event.getActivity();
                if (activity == null) {
                    h5BridgeContext.sendError(4, (String) "当前栈顶没有页面，请重新打开页面再次添加");
                    return;
                }
                AUNoticeDialog aUNoticeDialog = new AUNoticeDialog(activity, null, "将该应用添加至手机桌面快捷方式?", "立即添加", "取消");
                aUNoticeDialog.setPositiveListener(new OnClickPositiveListener() {
                    public final void onClick() {
                        Intent access$000 = MiniAppShortCutUtil.getActionIntent(str);
                        boolean z = false;
                        if (VERSION.SDK_INT >= 26) {
                            ShortcutManager shortcutManager = (ShortcutManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(ShortcutManager.class);
                            ShortcutInfo build = new Builder(LauncherApplicationAgent.getInstance().getApplicationContext(), str).setIntent(access$000).setIcon(Icon.createWithBitmap(bitmap)).setShortLabel(str2).setLongLabel(str2).build();
                            if (shortcutManager != null) {
                                Iterator<ShortcutInfo> it = shortcutManager.getPinnedShortcuts().iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (it.next().getId().equals(str)) {
                                            z = true;
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                            if (z) {
                                List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
                                if (pinnedShortcuts != null && !pinnedShortcuts.isEmpty()) {
                                    for (ShortcutInfo id : pinnedShortcuts) {
                                        if (TextUtils.equals(str, id.getId())) {
                                            ArrayList arrayList = new ArrayList();
                                            arrayList.add(str);
                                            shortcutManager.enableShortcuts(arrayList);
                                        }
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList();
                                arrayList2.add(build);
                                shortcutManager.updateShortcuts(arrayList2);
                            } else {
                                access$000.setPackage(LauncherApplicationAgent.getInstance().getApplicationContext().getPackageName());
                                if (shortcutManager != null) {
                                    shortcutManager.requestPinShortcut(build, null);
                                }
                            }
                        } else {
                            Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                            intent.putExtra("duplicate", false);
                            intent.putExtra("android.intent.extra.shortcut.NAME", str2);
                            intent.putExtra("android.intent.extra.shortcut.ICON", bitmap);
                            intent.putExtra("android.intent.extra.shortcut.INTENT", access$000);
                            LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(intent);
                        }
                        h5BridgeContext.sendSuccess();
                    }
                });
                aUNoticeDialog.setNegativeListener(new OnClickNegativeListener() {
                    public final void onClick() {
                        h5BridgeContext.sendError(7, (String) "用户取消添加");
                    }
                });
                try {
                    aUNoticeDialog.setCanceledOnTouchOutside(false);
                    aUNoticeDialog.setCancelable(false);
                    aUNoticeDialog.show();
                } catch (Exception e) {
                    AMapLog.warning("infoservice.miniapp", TAG, "DialogHelper.alert(): exception=".concat(String.valueOf(e)));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static Intent getActionIntent(String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("amapuri://applets/platformapi/startapp?appId=".concat(String.valueOf(str))));
        intent.setComponent(new ComponentName(AMapAppGlobal.getApplication().getPackageName(), "com.autonavi.map.activity.SplashActivity"));
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(603979776);
        return intent;
    }

    static void handleRemoveShortCut(String str, String str2, H5BridgeContext h5BridgeContext) {
        Intent actionIntent = getActionIntent(str);
        if (VERSION.SDK_INT >= 26) {
            ShortcutManager shortcutManager = (ShortcutManager) LauncherApplicationAgent.getInstance().getApplicationContext().getSystemService(ShortcutManager.class);
            if (shortcutManager != null) {
                List<ShortcutInfo> pinnedShortcuts = shortcutManager.getPinnedShortcuts();
                if (pinnedShortcuts != null && !pinnedShortcuts.isEmpty()) {
                    for (ShortcutInfo id : pinnedShortcuts) {
                        if (TextUtils.equals(str, id.getId())) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(str);
                            shortcutManager.disableShortcuts(arrayList);
                        }
                    }
                }
            }
        } else {
            Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            intent.putExtra("android.intent.extra.shortcut.NAME", str2);
            intent.putExtra("duplicate", false);
            intent.putExtra("android.intent.extra.shortcut.INTENT", actionIntent);
            LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(intent);
        }
        h5BridgeContext.sendSuccess();
    }
}

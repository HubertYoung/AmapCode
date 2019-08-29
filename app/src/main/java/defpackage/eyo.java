package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import java.util.List;

/* renamed from: eyo reason: default package */
/* compiled from: OnChangePushStatusReceiveTask */
public final class eyo extends fbe {
    public eyo(fbh fbh) {
        super(fbh);
    }

    public static boolean a(Context context) {
        Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 576);
        if (queryIntentServices == null || queryIntentServices.size() <= 0) {
            fat.a((String) "OnChangePushStatusTask", (String) "enableService error: can not find push service.");
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, queryIntentServices.get(0).serviceInfo.name);
        if (packageManager.getComponentEnabledSetting(componentName) != 1) {
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
            fat.d("OnChangePushStatusTask", "enableService push service.");
            return true;
        }
        fat.d("OnChangePushStatusTask", "push service has enabled");
        return false;
    }

    public static boolean b(Context context) {
        Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 576);
        if (queryIntentServices == null || queryIntentServices.size() <= 0) {
            fat.a((String) "OnChangePushStatusTask", (String) "disableService error: can not find push service.");
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, queryIntentServices.get(0).serviceInfo.name);
        if (packageManager.getComponentEnabledSetting(componentName) != 2) {
            packageManager.setComponentEnabledSetting(componentName, 2, 1);
            fat.d("OnChangePushStatusTask", "disableService push service.");
            return true;
        }
        fat.d("OnChangePushStatusTask", "push service has disabled");
        return false;
    }

    private static List<ResolveInfo> c(Context context) {
        List<ResolveInfo> list;
        Intent intent = new Intent("com.vivo.pushservice.action.RECEIVE");
        intent.setPackage(context.getPackageName());
        try {
            list = context.getPackageManager().queryBroadcastReceivers(intent, 576);
        } catch (Exception unused) {
            list = null;
        }
        if (list == null || list.size() <= 0) {
            Intent intent2 = new Intent("com.vivo.pushclient.action.RECEIVE");
            intent2.setPackage(context.getPackageName());
            try {
                return context.getPackageManager().queryBroadcastReceivers(intent2, 576);
            } catch (Exception unused2) {
            }
        }
        return list;
    }

    public final void a(fbh fbh) {
        if (!this.b.getPackageName().equals(faw.b(this.b))) {
            exk exk = (exk) fbh;
            int i = exk.a;
            int i2 = exk.b;
            StringBuilder sb = new StringBuilder("OnChangePushStatusTask serviceStatus is ");
            sb.append(i);
            sb.append(" ; receiverStatus is ");
            sb.append(i2);
            fat.d("OnChangePushStatusTask", sb.toString());
            if (i == 2) {
                b(this.b);
            } else if (i == 1) {
                a(this.b);
            } else if (i == 0) {
                Context context = this.b;
                Intent intent = new Intent("com.vivo.pushservice.action.PUSH_SERVICE");
                intent.setPackage(context.getPackageName());
                List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 576);
                if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                    fat.a((String) "OnChangePushStatusTask", (String) "defaultService error: can not find push service.");
                } else {
                    PackageManager packageManager = context.getPackageManager();
                    ComponentName componentName = new ComponentName(context, queryIntentServices.get(0).serviceInfo.name);
                    if (packageManager.getComponentEnabledSetting(componentName) != 0) {
                        packageManager.setComponentEnabledSetting(componentName, 0, 1);
                        fat.d("OnChangePushStatusTask", "defaultService push service.");
                    } else {
                        fat.d("OnChangePushStatusTask", "push service has defaulted");
                    }
                }
            }
            if (i2 == 2) {
                Context context2 = this.b;
                List<ResolveInfo> c = c(context2);
                if (c == null || c.size() <= 0) {
                    fat.a((String) "OnChangePushStatusTask", (String) "disableReceiver error: can not find push service.");
                } else {
                    String str = c.get(0).activityInfo.name;
                    if (TextUtils.isEmpty(str)) {
                        fat.d("OnChangePushStatusTask", "disableReceiver error: className is null. ");
                    } else {
                        PackageManager packageManager2 = context2.getPackageManager();
                        ComponentName componentName2 = new ComponentName(context2, str);
                        if (packageManager2.getComponentEnabledSetting(componentName2) != 2) {
                            packageManager2.setComponentEnabledSetting(componentName2, 2, 1);
                            fat.d("OnChangePushStatusTask", "push service disableReceiver ");
                        } else {
                            fat.d("OnChangePushStatusTask", "push service has disableReceiver ");
                        }
                    }
                }
                ezz.a().c = null;
            } else if (i2 == 1) {
                Context context3 = this.b;
                List<ResolveInfo> c2 = c(context3);
                if (c2 == null || c2.size() <= 0) {
                    fat.a((String) "OnChangePushStatusTask", (String) "enableReceiver error: can not find push service.");
                    return;
                }
                String str2 = c2.get(0).activityInfo.name;
                if (TextUtils.isEmpty(str2)) {
                    fat.d("OnChangePushStatusTask", "enableReceiver error: className is null. ");
                    return;
                }
                PackageManager packageManager3 = context3.getPackageManager();
                ComponentName componentName3 = new ComponentName(context3, str2);
                if (packageManager3.getComponentEnabledSetting(componentName3) != 1) {
                    packageManager3.setComponentEnabledSetting(componentName3, 1, 1);
                    fat.d("OnChangePushStatusTask", "push service enableReceiver ");
                    return;
                }
                fat.d("OnChangePushStatusTask", "push service has enableReceiver ");
            } else {
                if (i2 == 0) {
                    Context context4 = this.b;
                    List<ResolveInfo> c3 = c(context4);
                    if (c3 == null || c3.size() <= 0) {
                        fat.a((String) "OnChangePushStatusTask", (String) "defaultReceiver error: can not find push service.");
                        return;
                    }
                    String str3 = c3.get(0).activityInfo.name;
                    if (TextUtils.isEmpty(str3)) {
                        fat.d("OnChangePushStatusTask", "defaultReceiver error: className is null. ");
                        return;
                    }
                    PackageManager packageManager4 = context4.getPackageManager();
                    ComponentName componentName4 = new ComponentName(context4, str3);
                    if (packageManager4.getComponentEnabledSetting(componentName4) != 0) {
                        packageManager4.setComponentEnabledSetting(componentName4, 0, 1);
                        fat.d("OnChangePushStatusTask", "push service defaultReceiver ");
                        return;
                    }
                    fat.d("OnChangePushStatusTask", "push service has defaulted");
                }
            }
        }
    }
}

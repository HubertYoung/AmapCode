package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;

/* renamed from: eyi reason: default package */
/* compiled from: SendCommandTask */
public final class eyi extends fbe {
    public eyi(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        if (this.b == null) {
            StringBuilder sb = new StringBuilder("SendCommandTask ");
            sb.append(fbh);
            sb.append(" ; mContext is Null");
            fat.d("SendCommandTask", sb.toString());
        } else if (fbh == null) {
            fat.d("SendCommandTask", "SendCommandTask pushCommand is Null");
        } else {
            ezt a = faw.a(this.b);
            int i = fbh.f;
            if (i != 0) {
                if (i == 2009) {
                    fat.a(ezj.a(this.b).e());
                    if (fat.a()) {
                        ezv.a().d.a();
                        fag fag = new fag();
                        fag.a(this.b, (String) "com.vivo.push_preferences.hybridapptoken_v1");
                        fag.a();
                        fag fag2 = new fag();
                        fag2.a(this.b, (String) "com.vivo.push_preferences.appconfig_v1");
                        fag2.a();
                        if (!ezv.a().g) {
                            ezj.a(this.b).a.d();
                        }
                    }
                } else if (i != 2011) {
                    switch (i) {
                        case 2002:
                        case 2003:
                        case 2004:
                        case 2005:
                            if (a == null || a.e) {
                                ezv.a().a(((exd) fbh).c, 1005);
                                break;
                            } else {
                                exd exd = (exd) fbh;
                                int a2 = fav.a(exd);
                                if (a2 != 0) {
                                    ezv.a().a(exd.c, a2);
                                    return;
                                }
                            }
                            break;
                    }
                } else {
                    ezj.a(this.b);
                    fat.a(ezj.a(((exw) fbh).a));
                }
            } else if (ezv.a().g) {
                Context context = this.b;
                Intent intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setClassName(context.getPackageName(), "com.vivo.push.sdk.service.CommandService");
                List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
                if (queryIntentServices == null || queryIntentServices.size() <= 0) {
                    fat.d("ModuleUtil", "disableDeprecatedService is null");
                } else {
                    PackageManager packageManager = context.getPackageManager();
                    ComponentName componentName = new ComponentName(context, queryIntentServices.get(0).serviceInfo.name);
                    if (packageManager.getComponentEnabledSetting(componentName) != 2) {
                        packageManager.setComponentEnabledSetting(componentName, 2, 1);
                    }
                }
                Context context2 = this.b;
                Intent intent2 = new Intent();
                intent2.setPackage(context2.getPackageName());
                intent2.setClassName(context2.getPackageName(), "com.vivo.push.sdk.service.LinkProxyActivity");
                List<ResolveInfo> queryIntentActivities = context2.getPackageManager().queryIntentActivities(intent2, 128);
                if (queryIntentActivities == null || queryIntentActivities.size() <= 0) {
                    fat.d("ModuleUtil", "disableDeprecatedActivity is null");
                } else {
                    PackageManager packageManager2 = context2.getPackageManager();
                    ComponentName componentName2 = new ComponentName(context2, queryIntentActivities.get(0).activityInfo.name);
                    if (packageManager2.getComponentEnabledSetting(componentName2) != 2) {
                        packageManager2.setComponentEnabledSetting(componentName2, 2, 1);
                    }
                }
            }
            if (a == null) {
                StringBuilder sb2 = new StringBuilder("SendCommandTask ");
                sb2.append(fbh);
                sb2.append(" ; pushPkgInfo is Null");
                fat.d("SendCommandTask", sb2.toString());
                return;
            }
            String str = a.a;
            if (a.e) {
                ezv.a().a(((exd) fbh).c, 1004);
                fbh = new exf();
                StringBuilder sb3 = new StringBuilder("SendCommandTask ");
                sb3.append(fbh);
                sb3.append(" ; pkgName is InBlackList ");
                fat.d("SendCommandTask", sb3.toString());
            }
            ewy.a(this.b, str, fbh);
        }
    }
}

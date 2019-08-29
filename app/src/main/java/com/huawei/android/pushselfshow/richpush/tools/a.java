package com.huawei.android.pushselfshow.richpush.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.utils.d;
import java.io.File;

public class a {
    public Resources a;
    public Activity b;
    private com.huawei.android.pushselfshow.b.a c = null;

    public a(Activity activity) {
        this.b = activity;
        this.a = activity.getResources();
    }

    public void a() {
        try {
            c.a("PushSelfShowLog", "creat shortcut");
            Intent intent = new Intent();
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            Bitmap decodeResource = BitmapFactory.decodeResource(this.b.getResources(), d.g(this.b, "hwpush_main_icon"));
            intent.putExtra("android.intent.extra.shortcut.NAME", this.b.getResources().getString(d.a(this.b, "hwpush_msg_collect")));
            intent.putExtra("android.intent.extra.shortcut.ICON", decodeResource);
            intent.putExtra("duplicate", false);
            Intent intent2 = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent2.putExtra("type", "favorite");
            intent2.addFlags(1476395008);
            String str = "com.huawei.android.pushagent";
            if (!com.huawei.android.pushselfshow.utils.a.b((Context) this.b, str)) {
                str = this.b.getPackageName();
            }
            intent2.setPackage(str);
            intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
            this.b.sendBroadcast(intent);
        } catch (Exception e) {
            c.d("PushSelfShowLog", "creat shortcut error", e);
        }
    }

    public void a(com.huawei.android.pushselfshow.b.a aVar) {
        this.c = aVar;
    }

    public void b() {
        try {
            if (this.c == null || this.c.D == null) {
                Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a((Context) this.b, (String) "内容保存失败", (String) "Save Failed"), 0).show();
                return;
            }
            StringBuilder sb = new StringBuilder("the rpl is ");
            sb.append(this.c.D);
            c.e("PushSelfShowLog", sb.toString());
            String substring = this.c.D.startsWith("file://") ? this.c.D.substring(7) : this.c.D;
            c.e("PushSelfShowLog", "filePath is ".concat(String.valueOf(substring)));
            if ("text/html_local".equals(this.c.F)) {
                File parentFile = new File(substring).getParentFile();
                if (parentFile != null && parentFile.isDirectory() && this.c.D.contains("richpush")) {
                    String absolutePath = parentFile.getAbsolutePath();
                    String replace = absolutePath.replace("richpush", "shotcut");
                    c.b((String) "PushSelfShowLog", (String) "srcDir is %s ,destDir is %s", absolutePath, replace);
                    if (com.huawei.android.pushselfshow.utils.a.a(absolutePath, replace)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(replace);
                        sb2.append(File.separator);
                        sb2.append("index.html");
                        this.c.D = Uri.fromFile(new File(sb2.toString())).toString();
                    } else {
                        Toast.makeText(this.b, com.huawei.android.pushselfshow.utils.a.a((Context) this.b, (String) "内容保存失败", (String) "Save Failed"), 0).show();
                        return;
                    }
                }
            }
            c.a("PushSelfShowLog", "insert data into db");
            a();
            boolean a2 = com.huawei.android.pushselfshow.utils.a.c.a(this.b, this.c.r, this.c);
            c.e("PushSelfShowLog", "insert result is ".concat(String.valueOf(a2)));
            if (a2) {
                com.huawei.android.pushselfshow.utils.a.a((Context) this.b, (String) "14", this.c);
            } else {
                c.d("PushSelfShowLog", "save icon fail");
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", "SaveBtnClickListener error ", e);
        }
    }
}

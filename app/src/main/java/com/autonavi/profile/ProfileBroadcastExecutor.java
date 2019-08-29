package com.autonavi.profile;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ProfileBroadcastExecutor {
    public static void info(Context context, String str, String str2) {
    }

    public static void init(Context context, String str, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder("path [");
            sb.append(str);
            sb.append("] not exist!");
            enx.b(sb.toString());
            return;
        }
        int length = file.list().length;
        if (length == 0) {
            StringBuilder sb2 = new StringBuilder("no file found in [");
            sb2.append(str);
            sb2.append("].");
            enx.b(sb2.toString());
        } else if (env.a()) {
            enx.b("copy is running, skip!");
        } else {
            new env(context, str, length).start();
        }
    }

    public static void reset(final Context context, String str, String str2) {
        Executors.newCachedThreadPool().execute(new Runnable() {
            public final void run() {
                ProfileBroadcastExecutor.deleteReplaceSo();
                enx.a(context);
            }
        });
    }

    public static void deleteReplaceSo() {
        String c = enx.c();
        File file = new File(c);
        if (file.exists()) {
            file.deleteOnExit();
            for (String append : file.list()) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);
                sb.append("/");
                sb.append(append);
                String sb2 = sb.toString();
                new File(sb2).delete();
                enx.a("delete file: ".concat(String.valueOf(sb2)));
            }
        }
        File file2 = new File(akp.a);
        if (file2.exists()) {
            file2.delete();
        } else {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File file3 = new File(enx.a());
        if (file3.exists()) {
            file3.delete();
        }
    }
}

package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.c;
import com.xiaomi.channel.commonutils.android.j;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.channel.commonutils.string.a;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

public class bg {
    public static final Object a = new Object();

    public static void a(Context context, f fVar) {
        if (!j.e() || Constants.HYBRID_PACKAGE_NAME.equals(fVar.p())) {
            StringBuilder sb = new StringBuilder("TinyData TinyDataStorage.cacheTinyData cache data to file begin item:");
            sb.append(fVar.m());
            sb.append("  ts:");
            sb.append(System.currentTimeMillis());
            b.a(sb.toString());
            h.a(context).a((Runnable) new bh(context, fVar));
        }
    }

    public static byte[] a(Context context) {
        String b = bc.a(context).b("mipush", "td_key", "");
        if (TextUtils.isEmpty(b)) {
            b = d.a(20);
            bc.a(context).a((String) "mipush", (String) "td_key", b);
        }
        return a(b);
    }

    private static byte[] a(String str) {
        byte[] copyOf = Arrays.copyOf(a.a(str), 16);
        copyOf[0] = 68;
        copyOf[15] = 84;
        return copyOf;
    }

    /* access modifiers changed from: private */
    public static void c(Context context, f fVar) {
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        try {
            byte[] b = c.b(a(context), au.a(fVar));
            if (b != null) {
                if (b.length > 0) {
                    if (b.length > 10240) {
                        StringBuilder sb = new StringBuilder("TinyData write to cache file failed case too much data content item:");
                        sb.append(fVar.m());
                        b.a(sb.toString());
                        com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                        com.xiaomi.channel.commonutils.file.a.a((OutputStream) null);
                        return;
                    }
                    BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(new FileOutputStream(new File(context.getFilesDir(), "tiny_data.data"), true));
                    try {
                        bufferedOutputStream3.write(com.xiaomi.channel.commonutils.misc.b.a(b.length));
                        bufferedOutputStream3.write(b);
                        bufferedOutputStream3.flush();
                        com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                        com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream3);
                        return;
                    } catch (IOException e) {
                        Throwable th = e;
                        bufferedOutputStream2 = bufferedOutputStream3;
                        e = th;
                        b.a((String) "TinyData write to cache file failed cause io exception", e);
                        com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                        com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
                    } catch (Exception e2) {
                        Throwable th2 = e2;
                        bufferedOutputStream = bufferedOutputStream3;
                        e = th2;
                        try {
                            b.a((String) "TinyData write to cache file  failed", e);
                            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                            com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
                        } catch (Throwable th3) {
                            th = th3;
                            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                            com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
                            throw th;
                        }
                    } catch (Throwable th4) {
                        Throwable th5 = th4;
                        bufferedOutputStream = bufferedOutputStream3;
                        th = th5;
                        com.xiaomi.channel.commonutils.file.a.a((Writer) null);
                        com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
                        throw th;
                    }
                }
            }
            StringBuilder sb2 = new StringBuilder("TinyData write to cache file failed case encryption fail item:");
            sb2.append(fVar.m());
            b.a(sb2.toString());
            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
            com.xiaomi.channel.commonutils.file.a.a((OutputStream) null);
        } catch (IOException e3) {
            e = e3;
            bufferedOutputStream2 = null;
            b.a((String) "TinyData write to cache file failed cause io exception", e);
            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
            com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
        } catch (Exception e4) {
            e = e4;
            bufferedOutputStream = null;
            b.a((String) "TinyData write to cache file  failed", e);
            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
            com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
        } catch (Throwable th6) {
            th = th6;
            bufferedOutputStream = null;
            com.xiaomi.channel.commonutils.file.a.a((Writer) null);
            com.xiaomi.channel.commonutils.file.a.a((OutputStream) bufferedOutputStream);
            throw th;
        }
    }
}

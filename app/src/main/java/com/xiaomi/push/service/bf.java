package com.xiaomi.push.service;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.file.a;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.e;
import com.xiaomi.xmpush.thrift.f;
import com.xiaomi.xmpush.thrift.r;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class bf {
    private static String a;
    private static AtomicLong b = new AtomicLong(0);

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(d.a(5));
        sb.append("-");
        a = sb.toString();
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(b.incrementAndGet());
        return sb.toString();
    }

    public static ArrayList<ai> a(List<f> list, String str, String str2, int i) {
        String str3;
        if (list == null) {
            str3 = "requests can not be null in TinyDataHelper.transToThriftObj().";
        } else if (list.size() == 0) {
            str3 = "requests.length is 0 in TinyDataHelper.transToThriftObj().";
        } else {
            ArrayList<ai> arrayList = new ArrayList<>();
            e eVar = new e();
            int i2 = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                f fVar = list.get(i3);
                if (fVar != null) {
                    int length = au.a(fVar).length;
                    if (length > i) {
                        StringBuilder sb = new StringBuilder("TinyData is too big, ignore upload request item:");
                        sb.append(fVar.m());
                        b.d(sb.toString());
                    } else {
                        if (i2 + length > i) {
                            ai aiVar = new ai(a(), false);
                            aiVar.d(str);
                            aiVar.b(str2);
                            aiVar.c(r.UploadTinyData.W);
                            aiVar.a(a.a(au.a(eVar)));
                            arrayList.add(aiVar);
                            eVar = new e();
                            i2 = 0;
                        }
                        eVar.a(fVar);
                        i2 += length;
                    }
                }
            }
            if (eVar.a() != 0) {
                ai aiVar2 = new ai(a(), false);
                aiVar2.d(str);
                aiVar2.b(str2);
                aiVar2.c(r.UploadTinyData.W);
                aiVar2.a(a.a(au.a(eVar)));
                arrayList.add(aiVar2);
            }
            return arrayList;
        }
        b.d(str3);
        return null;
    }

    public static void a(Context context, String str, String str2, long j, String str3) {
        f fVar = new f();
        fVar.d(str);
        fVar.c(str2);
        fVar.a(j);
        fVar.b(str3);
        fVar.a((String) "push_sdk_channel");
        fVar.g(context.getPackageName());
        fVar.e(context.getPackageName());
        fVar.c(true);
        fVar.b(System.currentTimeMillis());
        fVar.f(a());
        bg.a(context, fVar);
    }

    public static boolean a(f fVar, boolean z) {
        String sb;
        if (fVar == null) {
            sb = "item is null, verfiy ClientUploadDataItem failed.";
        } else if (!z && TextUtils.isEmpty(fVar.a)) {
            sb = "item.channel is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(fVar.g)) {
            sb = "item.category is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (TextUtils.isEmpty(fVar.c)) {
            sb = "item.name is null or empty, verfiy ClientUploadDataItem failed.";
        } else if (!d.d(fVar.g)) {
            sb = "item.category can only contain ascii char, verfiy ClientUploadDataItem failed.";
        } else if (!d.d(fVar.c)) {
            sb = "item.name can only contain ascii char, verfiy ClientUploadDataItem failed.";
        } else if (fVar.b == null || fVar.b.length() <= 10240) {
            return false;
        } else {
            StringBuilder sb2 = new StringBuilder("item.data is too large(");
            sb2.append(fVar.b.length());
            sb2.append("), max size for data is 10240 , verfiy ClientUploadDataItem failed.");
            sb = sb2.toString();
        }
        b.a(sb);
        return true;
    }
}

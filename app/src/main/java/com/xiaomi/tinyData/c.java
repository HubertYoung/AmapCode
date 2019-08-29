package com.xiaomi.tinyData;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.bf;
import com.xiaomi.xmpush.thrift.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class c {
    private static HashMap<String, ArrayList<f>> a(Context context, List<f> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        HashMap<String, ArrayList<f>> hashMap = new HashMap<>();
        for (f next : list) {
            a(context, next);
            ArrayList arrayList = hashMap.get(next.k());
            if (arrayList == null) {
                arrayList = new ArrayList();
                hashMap.put(next.k(), arrayList);
            }
            arrayList.add(next);
        }
        return hashMap;
    }

    private static void a(Context context, e eVar, HashMap<String, ArrayList<f>> hashMap) {
        for (Entry next : hashMap.entrySet()) {
            try {
                ArrayList arrayList = (ArrayList) next.getValue();
                if (arrayList != null) {
                    if (arrayList.size() != 0) {
                        StringBuilder sb = new StringBuilder("TinyData is uploaded immediately for ");
                        sb.append(((f) arrayList.get(0)).m());
                        b.c(sb.toString());
                        eVar.a(arrayList, ((f) arrayList.get(0)).p(), (String) next.getKey());
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public static void a(Context context, e eVar, List<f> list) {
        HashMap<String, ArrayList<f>> a = a(context, list);
        if (a != null && a.size() != 0) {
            a(context, eVar, a);
        }
    }

    private static void a(Context context, f fVar) {
        if (fVar.f) {
            fVar.a((String) "push_sdk_channel");
        }
        if (TextUtils.isEmpty(fVar.m())) {
            fVar.f(bf.a());
        }
        fVar.b(System.currentTimeMillis());
        if (TextUtils.isEmpty(fVar.p())) {
            fVar.e(context.getPackageName());
        }
        if (TextUtils.isEmpty(fVar.k())) {
            fVar.e(fVar.p());
        }
    }
}

package com.xiaomi.push.service;

import android.util.Pair;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.xmpush.thrift.ag;
import com.xiaomi.xmpush.thrift.ah;
import com.xiaomi.xmpush.thrift.h;
import com.xiaomi.xmpush.thrift.i;
import com.xiaomi.xmpush.thrift.q;
import com.xiaomi.xmpush.thrift.s;
import java.util.ArrayList;
import java.util.List;

public class ao {
    public static int a(an anVar, h hVar) {
        String a = a(hVar);
        int i = 0;
        switch (ap.a[hVar.ordinal()]) {
            case 1:
                i = 1;
                break;
        }
        return anVar.a.getInt(a, i);
    }

    private static String a(h hVar) {
        StringBuilder sb = new StringBuilder("oc_version_");
        sb.append(hVar.a());
        return sb.toString();
    }

    private static List<Pair<Integer, Object>> a(List<s> list, boolean z) {
        Object obj;
        if (c.a(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (s next : list) {
            int a = next.a();
            i a2 = i.a(next.c());
            if (a2 != null) {
                if (!z || !next.c) {
                    switch (ap.b[a2.ordinal()]) {
                        case 1:
                            obj = new Pair(Integer.valueOf(a), Integer.valueOf(next.f()));
                            break;
                        case 2:
                            obj = new Pair(Integer.valueOf(a), Long.valueOf(next.h()));
                            break;
                        case 3:
                            obj = new Pair(Integer.valueOf(a), next.j());
                            break;
                        case 4:
                            obj = new Pair(Integer.valueOf(a), Boolean.valueOf(next.l()));
                            break;
                        default:
                            obj = null;
                            break;
                    }
                    arrayList.add(obj);
                } else {
                    arrayList.add(new Pair(Integer.valueOf(a), null));
                }
            }
        }
        return arrayList;
    }

    public static void a(an anVar, ag agVar) {
        anVar.b(a(agVar.a(), true));
    }

    public static void a(an anVar, ah ahVar) {
        for (q next : ahVar.a()) {
            if (next.a() > a(anVar, next.d())) {
                a(anVar, next.d(), next.a());
                anVar.a(a(next.b, false));
            }
        }
    }

    public static void a(an anVar, h hVar, int i) {
        anVar.a.edit().putInt(a(hVar), i).commit();
    }
}

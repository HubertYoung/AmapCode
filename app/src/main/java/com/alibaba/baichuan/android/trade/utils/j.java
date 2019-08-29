package com.alibaba.baichuan.android.trade.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class j {

    public static class a implements Serializable {
        public String a;
        public Boolean b;
        public Boolean c;
        public String[] d;
        public String[] e;
    }

    private static void a(String str, String str2, Set set, Map map) {
        Set<String> set2 = (Set) map.get(str2);
        if (set2 != null && !set2.contains(str)) {
            for (String str3 : set2) {
                if (set.add(str3)) {
                    a(str, str3, set, map);
                }
            }
        }
    }

    public static void a(a[] aVarArr) {
        String[] strArr;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        int i = 0;
        for (a aVar : aVarArr) {
            if (aVar.d != null) {
                for (String str : aVar.d) {
                    Set set = (Set) hashMap.get(str);
                    if (set == null) {
                        set = new HashSet();
                        hashMap.put(str, set);
                    }
                    set.add(aVar.a);
                }
            }
            Collection collection = (Set) hashMap.get(aVar.a);
            if (collection == null) {
                collection = new HashSet();
                hashMap.put(aVar.a, collection);
            }
            if (aVar.e != null) {
                Collections.addAll(collection, aVar.e);
            }
            hashMap2.put(aVar.a, aVar);
        }
        HashSet hashSet = new HashSet();
        for (a aVar2 : aVarArr) {
            a(aVar2.a, aVar2.a, hashSet, hashMap);
            ((Set) hashMap.get(aVar2.a)).addAll(hashSet);
            hashSet.clear();
        }
        HashSet hashSet2 = new HashSet();
        HashSet<String> hashSet3 = new HashSet<>();
        for (a aVar3 : aVarArr) {
            String str2 = aVar3.a;
            if (aVar3.b != null && aVar3.b.booleanValue()) {
                hashSet2.add(str2);
                hashSet2.addAll((Set) hashMap.get(str2));
            } else if (aVar3.c != null && aVar3.c.booleanValue()) {
                hashSet3.add(str2);
                for (Entry entry : hashMap.entrySet()) {
                    if (((Set) entry.getValue()).contains(str2)) {
                        hashSet3.add(entry.getKey());
                    }
                }
            }
        }
        for (Entry entry2 : hashMap.entrySet()) {
            if (!hashSet2.contains(entry2.getKey())) {
                ((Set) entry2.getValue()).addAll(hashSet2);
            }
        }
        HashSet hashSet4 = new HashSet(hashMap.keySet());
        hashSet4.removeAll(hashSet3);
        for (String str3 : hashSet3) {
            ((Set) hashMap.get(str3)).addAll(hashSet4);
        }
        HashSet hashSet5 = new HashSet();
        HashSet hashSet6 = new HashSet();
        ArrayList<String> arrayList = new ArrayList<>(aVarArr.length);
        while (hashMap.size() > 0) {
            Iterator it = hashMap.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry3 = (Entry) it.next();
                ((Set) entry3.getValue()).removeAll(hashSet6);
                ((Set) entry3.getValue()).retainAll(hashMap.keySet());
                if (((Set) entry3.getValue()).size() == 0) {
                    hashSet5.add(entry3.getKey());
                    arrayList.add(entry3.getKey());
                    it.remove();
                }
            }
            if (hashSet5.size() == 0) {
                throw new IllegalStateException("Circular found for ".concat(String.valueOf(hashMap)));
            }
            hashSet6.clear();
            hashSet6.addAll(hashSet5);
            hashSet5.clear();
        }
        for (String str4 : arrayList) {
            a aVar4 = (a) hashMap2.get(str4);
            if (aVar4 != null) {
                aVarArr[i] = aVar4;
                i++;
            }
        }
    }
}

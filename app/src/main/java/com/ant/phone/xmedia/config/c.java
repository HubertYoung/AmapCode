package com.ant.phone.xmedia.config;

import java.util.Comparator;
import java.util.Map.Entry;

/* compiled from: DeviceConfigUtils */
final class c implements Comparator<Entry<String, String>> {
    c() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return a((Entry) obj, (Entry) obj2);
    }

    private static int a(Entry<String, String> firstMapEntry, Entry<String, String> secondMapEntry) {
        return firstMapEntry.getKey().compareTo(secondMapEntry.getKey());
    }
}

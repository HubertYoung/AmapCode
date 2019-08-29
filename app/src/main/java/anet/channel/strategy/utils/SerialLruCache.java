package anet.channel.strategy.utils;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class SerialLruCache<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = -4331642331292721006L;
    private int cacheSize;

    /* access modifiers changed from: protected */
    public boolean entryRemoved(Entry<K, V> entry) {
        return true;
    }

    public SerialLruCache(int i) {
        super(i + 1, 1.0f, true);
        this.cacheSize = i;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Entry<K, V> entry) {
        if (size() > this.cacheSize) {
            return entryRemoved(entry);
        }
        return false;
    }
}

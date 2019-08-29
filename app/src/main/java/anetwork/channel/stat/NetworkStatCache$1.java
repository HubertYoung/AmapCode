package anetwork.channel.stat;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class NetworkStatCache$1 extends LinkedHashMap<String, String> {
    final /* synthetic */ eg a;

    public NetworkStatCache$1(eg egVar) {
        this.a = egVar;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Entry<String, String> entry) {
        return size() > 100;
    }
}

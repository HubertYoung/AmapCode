package anetwork.channel.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Cache {

    public static class Entry implements Serializable {
        public byte[] data;
        public String etag;
        public long lastModified;
        public Map<String, List<String>> responseHeaders = Collections.EMPTY_MAP;
        public long serverDate;
        public long ttl;

        public boolean isFresh() {
            return System.currentTimeMillis() <= this.ttl;
        }
    }

    Entry a(String str);

    void a(String str, Entry entry);

    void b(String str);
}

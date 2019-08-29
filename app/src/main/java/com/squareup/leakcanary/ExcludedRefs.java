package com.squareup.leakcanary;

import com.alipay.mobile.framework.util.xml.MetaInfoXmlParser;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class ExcludedRefs implements Serializable {
    public final Map<String, Set<String>> excludeFieldMap;
    public final Map<String, Set<String>> excludeStaticFieldMap;
    public final Set<String> excludedThreads;

    public static final class Builder {
        private final Map<String, Set<String>> excludeFieldMap = new LinkedHashMap();
        private final Map<String, Set<String>> excludeStaticFieldMap = new LinkedHashMap();
        private final Set<String> excludedThreads = new LinkedHashSet();

        public final Builder instanceField(String str, String str2) {
            Preconditions.checkNotNull(str, "className");
            Preconditions.checkNotNull(str2, "fieldName");
            Set set = this.excludeFieldMap.get(str);
            if (set == null) {
                set = new LinkedHashSet();
                this.excludeFieldMap.put(str, set);
            }
            set.add(str2);
            return this;
        }

        public final Builder staticField(String str, String str2) {
            Preconditions.checkNotNull(str, "className");
            Preconditions.checkNotNull(str2, "fieldName");
            Set set = this.excludeStaticFieldMap.get(str);
            if (set == null) {
                set = new LinkedHashSet();
                this.excludeStaticFieldMap.put(str, set);
            }
            set.add(str2);
            return this;
        }

        public final Builder thread(String str) {
            Preconditions.checkNotNull(str, MetaInfoXmlParser.KEY_VALVE_THREAD_NAME);
            this.excludedThreads.add(str);
            return this;
        }

        public final ExcludedRefs build() {
            return new ExcludedRefs(this.excludeFieldMap, this.excludeStaticFieldMap, this.excludedThreads);
        }
    }

    private ExcludedRefs(Map<String, Set<String>> map, Map<String, Set<String>> map2, Set<String> set) {
        this.excludeFieldMap = Collections.unmodifiableMap(new LinkedHashMap(map));
        this.excludeStaticFieldMap = Collections.unmodifiableMap(new LinkedHashMap(map2));
        this.excludedThreads = Collections.unmodifiableSet(new LinkedHashSet(set));
    }
}

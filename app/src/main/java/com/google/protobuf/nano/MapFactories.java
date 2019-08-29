package com.google.protobuf.nano;

import java.util.HashMap;
import java.util.Map;

public final class MapFactories {
    private static volatile MapFactory mapFactory = new DefaultMapFactory();

    static class DefaultMapFactory implements MapFactory {
        private DefaultMapFactory() {
        }

        public <K, V> Map<K, V> forMap(Map<K, V> map) {
            return map == null ? new HashMap() : map;
        }
    }

    public interface MapFactory {
        <K, V> Map<K, V> forMap(Map<K, V> map);
    }

    static void setMapFactory(MapFactory mapFactory2) {
        mapFactory = mapFactory2;
    }

    public static MapFactory getMapFactory() {
        return mapFactory;
    }

    private MapFactories() {
    }
}

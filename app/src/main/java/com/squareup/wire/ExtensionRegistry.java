package com.squareup.wire;

import java.util.LinkedHashMap;
import java.util.Map;

final class ExtensionRegistry {
    private final Map<Class<? extends ExtendableMessage>, Map<String, Extension<?, ?>>> extensionsByName = new LinkedHashMap();
    private final Map<Class<? extends ExtendableMessage>, Map<Integer, Extension<?, ?>>> extensionsByTag = new LinkedHashMap();

    ExtensionRegistry() {
    }

    public final <T extends ExtendableMessage<?>, E> void add(Extension<T, E> extension) {
        Class extendedType = extension.getExtendedType();
        Map map = this.extensionsByTag.get(extendedType);
        Map map2 = this.extensionsByName.get(extendedType);
        if (map == null) {
            map = new LinkedHashMap();
            map2 = new LinkedHashMap();
            this.extensionsByTag.put(extendedType, map);
            this.extensionsByName.put(extendedType, map2);
        }
        map.put(Integer.valueOf(extension.getTag()), extension);
        map2.put(extension.getName(), extension);
    }

    public final <T extends ExtendableMessage<?>, E> Extension<T, E> getExtension(Class<T> cls, int i) {
        Map map = this.extensionsByTag.get(cls);
        if (map == null) {
            return null;
        }
        return (Extension) map.get(Integer.valueOf(i));
    }

    public final <T extends ExtendableMessage<?>, E> Extension<T, E> getExtension(Class<T> cls, String str) {
        Map map = this.extensionsByName.get(cls);
        if (map == null) {
            return null;
        }
        return (Extension) map.get(str);
    }
}

package com.autonavi.minimap.ajx3.loader.picasso;

public interface Cache<T> {
    void clear();

    void clearKeyUri(String str);

    T get(String str);

    int maxSize();

    void set(String str, T t);

    int size();
}

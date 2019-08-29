package com.j256.ormlite.table;

import java.lang.reflect.Constructor;

public interface ObjectFactory<T> {
    T createObject(Constructor<T> constructor, Class<T> cls);
}

package org.nanohttpd.util;

import java.lang.Throwable;

public interface IFactoryThrowing<T, E extends Throwable> {
    T create();
}

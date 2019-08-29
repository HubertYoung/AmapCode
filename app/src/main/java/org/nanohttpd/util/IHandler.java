package org.nanohttpd.util;

public interface IHandler<I, O> {
    O handle(I i);
}

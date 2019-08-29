package com.alipay.multimedia.common.config;

public interface IConfig {
    Object clone();

    String toString();

    Object update(Object obj);
}

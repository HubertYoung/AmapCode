package com.alipay.multimedia.common.config;

import com.alipay.multimedia.common.config.IConfig;

public interface IParser<T extends IConfig> {
    T parse(String str);
}

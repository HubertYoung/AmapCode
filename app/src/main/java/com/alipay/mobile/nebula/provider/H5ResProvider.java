package com.alipay.mobile.nebula.provider;

import java.io.InputStream;

public interface H5ResProvider {
    boolean contains(String str);

    InputStream getResource(String str);
}

package com.alipay.mobile.common.logging.api.abtest;

import java.util.Map;

public interface AbtestInfoGetterV2 extends AbtestInfoGetter {
    Map<String, String> getExtInfoForSpmID(String str);
}

package com.alipay.mobile.common.share;

import java.util.HashMap;

public interface ShareTokenListener {
    boolean isFilter(int i, ShareContent shareContent, String str, HashMap<String, Object> hashMap);
}

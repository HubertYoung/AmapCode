package com.alibaba.sdk.trade.container.auth;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class AlibcContainerAuth {
    public static void registHint(String str, List<Integer> list) {
        if (!TextUtils.isEmpty(str) && list != null && list.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (Integer intValue : list) {
                arrayList.add(AlibcContainerHint.getHintId(intValue.intValue()));
            }
            AlibcContainerHintManager.registHintList(str, arrayList);
        }
    }
}

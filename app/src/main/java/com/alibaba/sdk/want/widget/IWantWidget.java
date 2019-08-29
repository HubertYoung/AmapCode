package com.alibaba.sdk.want.widget;

import com.alibaba.sdk.trade.component.cart.AlibcTkParams;
import java.util.HashMap;

public interface IWantWidget {
    boolean addData();

    void destroy();

    String getType();

    void removeData();

    void removeDataEnd();

    void updateData(WantBaseData wantBaseData, HashMap<String, String> hashMap, AlibcTkParams alibcTkParams);
}

package com.alibaba.baichuan.android.trade.adapter.applink;

import android.content.Context;
import java.util.Map;

public interface IAlibcApplink {
    boolean jumpDetail(Context context, Map map);

    boolean jumpShop(Context context, Map map);

    boolean jumpTBURI(Context context, Map map);

    void setOpenParam(Map map);
}

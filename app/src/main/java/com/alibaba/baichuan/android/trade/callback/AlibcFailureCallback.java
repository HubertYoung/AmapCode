package com.alibaba.baichuan.android.trade.callback;

import java.io.Serializable;

public interface AlibcFailureCallback extends Serializable {
    void onFailure(int i, String str);
}

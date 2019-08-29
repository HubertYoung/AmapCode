package com.alibaba.baichuan.android.trade.callback;

import com.alibaba.baichuan.android.trade.model.TradeResult;

public interface AlibcTradeCallback extends AlibcFailureCallback {
    void onTradeSuccess(TradeResult tradeResult);
}

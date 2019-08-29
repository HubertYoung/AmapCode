package com.alibaba.baichuan.android.trade.adapter.alipay;

import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;

public interface IAlibcAlipay {
    boolean openAlipay(AlibcTradeCallback alibcTradeCallback, WebView webView, String str);
}

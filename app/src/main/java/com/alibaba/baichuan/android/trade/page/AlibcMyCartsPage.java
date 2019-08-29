package com.alibaba.baichuan.android.trade.page;

import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;

public class AlibcMyCartsPage extends AlibcBasePage {
    public String genOpenUrl() {
        return AlibcContext.SHOW_CART_URL;
    }

    public String getApi() {
        return UserTrackerConstants.E_SHOWCART;
    }

    public String getPerformancePageType() {
        return AlibcConstants.MY_CART;
    }

    public String getUsabilityPageType() {
        return UserTrackerConstants.U_MYCARTS_PAGE;
    }

    public boolean isBackWhenLoginFail() {
        return true;
    }

    public boolean needTaoke(AlibcTaokeParams alibcTaokeParams) {
        return false;
    }
}

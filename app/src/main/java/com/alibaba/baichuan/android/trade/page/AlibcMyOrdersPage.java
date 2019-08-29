package com.alibaba.baichuan.android.trade.page;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;

public class AlibcMyOrdersPage extends AlibcBasePage {
    public static final String[] ORDER_TYPE = {"all", "waitPay", "waitSend", "waitConfirm", "waitRate"};
    public static final String TAB_CODE = "?tabCode=%s";
    int b;
    boolean c;

    public AlibcMyOrdersPage(int i, boolean z) {
        this.b = i;
        this.c = z;
    }

    public boolean checkParams() {
        return this.b >= 0 && this.b <= 4;
    }

    public String genOpenUrl() {
        String str;
        Object[] objArr;
        if (this.a != null && !TextUtils.isEmpty(this.a)) {
            return this.a;
        }
        String str2 = AlibcContext.MY_ORDERS_URL;
        int indexOf = str2.indexOf(63);
        if (indexOf > 0) {
            str2 = str2.substring(0, indexOf);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(TAB_CODE);
        String sb2 = sb.toString();
        if (this.b < 0 || this.b >= 5) {
            str = sb2.substring(0, sb2.indexOf("?"));
        } else {
            if (!this.c) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append("&condition={\"extra\":{\"attributes\":{\"ttid\":\"%s\"}}}");
                sb2 = sb3.toString();
                objArr = new Object[]{ORDER_TYPE[this.b], AlibcContext.getAppKey()};
            } else {
                objArr = new Object[]{ORDER_TYPE[this.b]};
            }
            str = String.format(sb2, objArr);
        }
        this.a = str;
        return this.a;
    }

    public String getApi() {
        return UserTrackerConstants.E_SHOWORDER;
    }

    public String getPerformancePageType() {
        return AlibcConstants.MY_ORDER;
    }

    public String getUsabilityPageType() {
        return UserTrackerConstants.U_ORDER_PAGE;
    }

    public boolean isBackWhenLoginFail() {
        return true;
    }

    public boolean needTaoke(AlibcTaokeParams alibcTaokeParams) {
        return false;
    }
}

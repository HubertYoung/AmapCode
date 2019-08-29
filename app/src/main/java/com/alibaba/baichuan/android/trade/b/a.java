package com.alibaba.baichuan.android.trade.b;

import com.alibaba.baichuan.android.trade.adapter.ut.performance.PagePerformancePoint;
import com.alibaba.baichuan.android.trade.adapter.ut.performance.Point4ShowH5;
import com.alibaba.baichuan.android.trade.adapter.ut.performance.Point4ShowNative;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class a implements Serializable {
    private static final String f = "a";
    public Set a = new HashSet();
    public PagePerformancePoint b;
    public boolean c;
    public String d;
    public AlibcTradeCallback e;

    public a(AlibcBasePage alibcBasePage, AlibcShowParams alibcShowParams) {
        a(alibcBasePage, alibcShowParams);
        this.d = alibcShowParams.getTitle();
    }

    private void a(AlibcBasePage alibcBasePage, AlibcShowParams alibcShowParams) {
        this.c = alibcBasePage == null ? false : alibcBasePage.isNativeOpen(alibcShowParams);
        a();
        c(alibcBasePage == null ? UserTrackerConstants.U_OTHER_PAGE : alibcBasePage.getPerformancePageType());
    }

    public void a() {
        this.a.add(UserTrackerConstants.PM_GO_TAOBAO_TIME);
        this.b = this.c ? new Point4ShowNative() : new Point4ShowH5();
    }

    public void a(AlibcBasePage alibcBasePage) {
        if (alibcBasePage == null || this.b == null) {
            com.alibaba.baichuan.android.trade.utils.a.a(f, "changePoint", "basePage/pagePerformancePoint is null");
            AlibcLogger.e(f, "changePoint:basePage/pagePerformancePoint is null");
        }
        if (this.b instanceof Point4ShowNative) {
            Point4ShowH5 point4ShowH5 = new Point4ShowH5();
            point4ShowH5.analysisTime = ((Point4ShowNative) this.b).analysisTime;
            point4ShowH5.setPageType(alibcBasePage.getPerformancePageType());
            this.b = point4ShowH5;
            this.c = false;
            return;
        }
        com.alibaba.baichuan.android.trade.utils.a.a(f, "changePoint", "pagePerformancePoint is not Point4ShowNative");
        AlibcLogger.e(f, "changePoint:pagePerformancePoint is not Point4ShowNative");
    }

    public void a(String str) {
        if (this.b == null) {
            com.alibaba.baichuan.android.trade.utils.a.a(f, "timeBegin", "pagePerformancePoint is null");
            AlibcLogger.e(f, "timeBegin:pagePerformancePoint is null");
            return;
        }
        this.b.timeBegin(str);
    }

    public void b(String str) {
        if (this.b == null) {
            com.alibaba.baichuan.android.trade.utils.a.a(f, "timeEnd", "pagePerformancePoint is null");
            AlibcLogger.e(f, "timeEnd:pagePerformancePoint is null");
            return;
        }
        this.b.timeEnd(str);
    }

    public void c(String str) {
        if (this.b == null) {
            com.alibaba.baichuan.android.trade.utils.a.a(f, "setPageType", "pagePerformancePoint is null");
            AlibcLogger.e(f, "setPageType:pagePerformancePoint is null");
            return;
        }
        this.b.setPageType(str);
    }

    public void d(String str) {
        if (this.b == null) {
            com.alibaba.baichuan.android.trade.utils.a.a(f, "setTaokeType", "pagePerformancePoint is null");
            AlibcLogger.e(f, "setTaokeType:pagePerformancePoint is null");
            return;
        }
        this.b.setTaokeType(str);
    }
}

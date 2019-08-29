package com.autonavi.mine.page.toolsbox.presenter;

import com.autonavi.common.Callback;
import java.util.Calendar;

public class ToolsBoxPresenter$2 implements Callback<ddt> {
    final /* synthetic */ cgu a;

    public void error(Throwable th, boolean z) {
    }

    public ToolsBoxPresenter$2(cgu cgu) {
        this.a = cgu;
    }

    public void callback(ddt ddt) {
        if (ddt.a.size() > 0) {
            cgu.a(this.a, Calendar.getInstance().getTimeInMillis(), ddt.a, ddt.g);
            this.a.c();
        }
    }
}

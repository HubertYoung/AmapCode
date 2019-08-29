package com.alipay.android.phone.inside.log.perf;

import com.alipay.android.phone.inside.log.LogCollect;
import com.alipay.android.phone.inside.log.api.perf.PerfLogger;
import com.alipay.android.phone.inside.log.field.PerfField;

public class PerfLoggerImpl implements PerfLogger {
    public final void a(String str, String str2, Long l) {
        LogCollect.a().a(new PerfField(str, str2, l));
    }

    public final void a(String str, String str2, Long l, String... strArr) {
        LogCollect.a().a(new PerfField(str, str2, l, strArr));
    }
}

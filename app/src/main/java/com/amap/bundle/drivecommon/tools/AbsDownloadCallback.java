package com.amap.bundle.drivecommon.tools;

import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Callback.d;

public abstract class AbsDownloadCallback<ResultType> implements Callback<ResultType>, a, d {
    public abstract void callback(ResultType resulttype);

    public abstract void cancel();

    public abstract void error(Throwable th, boolean z);

    public String getSavePath() {
        return null;
    }

    public boolean isCancelled() {
        return true;
    }

    public abstract void onCancelled();

    public abstract void onLoading(long j, long j2);

    public abstract void onStart();
}

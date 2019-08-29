package com.taobao.accs.utl;

import android.app.Application;
import android.content.Context;

public interface UT {
    public static final int EVENT_ID = 66001;

    void commitEvent(int i, String str, Object obj);

    void commitEvent(int i, String str, Object obj, Object obj2);

    void commitEvent(int i, String str, Object obj, Object obj2, Object obj3);

    void commitEvent(int i, String str, Object obj, Object obj2, Object obj3, String... strArr);

    String getUtdId(Context context);

    void onCaughException(Throwable th);

    void start(Application application, String str, String str2, String str3);

    void stop(Context context);
}

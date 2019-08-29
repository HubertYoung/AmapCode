package com.autonavi.minimap.basemap.errorback.inter;

import android.app.Activity;
import android.content.Context;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IBusErrorReportRemind extends bie {
    void dismissDialog();

    void handlePageOnResume(Activity activity, int i);

    void handleResume(Context context);

    boolean isOn(Context context);

    void setContentAndState(Context context, String str);

    void tryToRecord(String str, Context context);
}

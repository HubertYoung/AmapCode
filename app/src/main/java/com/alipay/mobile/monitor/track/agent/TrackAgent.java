package com.alipay.mobile.monitor.track.agent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import java.util.Map;

public interface TrackAgent {
    void onActivityBackPressed(Activity activity);

    void onActivityCreate(Activity activity);

    void onActivityDestroy(Activity activity);

    void onActivityFinish(Activity activity);

    void onActivityPause(Activity activity);

    void onActivityResume(Activity activity);

    void onActivityWindowFocusChanged(Activity activity, boolean z);

    void onFragmentBackPressed();

    void onFragmentDestory(Fragment fragment);

    void onFragmentFinish();

    void onFragmentHiddenChanged(Fragment fragment, boolean z);

    void onFragmentPause(Fragment fragment);

    void onFragmentResume(Fragment fragment);

    void onTouchEvent(MotionEvent motionEvent);

    void onViewClick(View view, boolean z, String str, Map<String, String> map);
}

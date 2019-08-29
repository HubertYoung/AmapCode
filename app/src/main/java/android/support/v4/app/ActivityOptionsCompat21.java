package android.support.v4.app;

import android.app.Activity;
import android.app.ActivityOptions;
import android.util.Pair;
import android.view.View;

class ActivityOptionsCompat21 {
    final ActivityOptions a;

    public static ActivityOptionsCompat21 a(Activity activity, View view, String str) {
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity, view, str));
    }

    public static ActivityOptionsCompat21 a(Activity activity, View[] viewArr, String[] strArr) {
        Pair[] pairArr;
        if (viewArr != null) {
            pairArr = new Pair[viewArr.length];
            for (int i = 0; i < pairArr.length; i++) {
                pairArr[i] = Pair.create(viewArr[i], strArr[i]);
            }
        } else {
            pairArr = null;
        }
        return new ActivityOptionsCompat21(ActivityOptions.makeSceneTransitionAnimation(activity, pairArr));
    }

    private ActivityOptionsCompat21(ActivityOptions activityOptions) {
        this.a = activityOptions;
    }
}

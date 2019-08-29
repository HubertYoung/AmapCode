package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;

public class ActivityOptionsCompat {

    static class ActivityOptionsImpl21 extends ActivityOptionsCompat {
        private final ActivityOptionsCompat21 a;

        ActivityOptionsImpl21(ActivityOptionsCompat21 activityOptionsCompat21) {
            this.a = activityOptionsCompat21;
        }

        public Bundle toBundle() {
            return this.a.a.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof ActivityOptionsImpl21) {
                this.a.a.update(((ActivityOptionsImpl21) activityOptionsCompat).a.a);
            }
        }
    }

    static class ActivityOptionsImplJB extends ActivityOptionsCompat {
        private final ActivityOptionsCompatJB a;

        ActivityOptionsImplJB(ActivityOptionsCompatJB activityOptionsCompatJB) {
            this.a = activityOptionsCompatJB;
        }

        public Bundle toBundle() {
            return this.a.a.toBundle();
        }

        public void update(ActivityOptionsCompat activityOptionsCompat) {
            if (activityOptionsCompat instanceof ActivityOptionsImplJB) {
                this.a.a.update(((ActivityOptionsImplJB) activityOptionsCompat).a.a);
            }
        }
    }

    public Bundle toBundle() {
        return null;
    }

    public void update(ActivityOptionsCompat activityOptionsCompat) {
    }

    public static ActivityOptionsCompat makeCustomAnimation(Context context, int i, int i2) {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.a(context, i, i2));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeScaleUpAnimation(View view, int i, int i2, int i3, int i4) {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.a(view, i, i2, i3, i4));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(View view, Bitmap bitmap, int i, int i2) {
        if (VERSION.SDK_INT >= 16) {
            return new ActivityOptionsImplJB(ActivityOptionsCompatJB.a(view, bitmap, i, i2));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, View view, String str) {
        if (VERSION.SDK_INT >= 21) {
            return new ActivityOptionsImpl21(ActivityOptionsCompat21.a(activity, view, str));
        }
        return new ActivityOptionsCompat();
    }

    public static ActivityOptionsCompat makeSceneTransitionAnimation(Activity activity, Pair<View, String>... pairArr) {
        String[] strArr;
        if (VERSION.SDK_INT < 21) {
            return new ActivityOptionsCompat();
        }
        View[] viewArr = null;
        if (pairArr != null) {
            viewArr = new View[pairArr.length];
            strArr = new String[pairArr.length];
            for (int i = 0; i < pairArr.length; i++) {
                viewArr[i] = (View) pairArr[i].first;
                strArr[i] = (String) pairArr[i].second;
            }
        } else {
            strArr = null;
        }
        return new ActivityOptionsImpl21(ActivityOptionsCompat21.a(activity, viewArr, strArr));
    }

    protected ActivityOptionsCompat() {
    }
}

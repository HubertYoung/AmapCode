package android.support.v4.app;

import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

class ActivityOptionsCompatJB {
    final ActivityOptions a;

    public static ActivityOptionsCompatJB a(Context context, int i, int i2) {
        return new ActivityOptionsCompatJB(ActivityOptions.makeCustomAnimation(context, i, i2));
    }

    public static ActivityOptionsCompatJB a(View view, int i, int i2, int i3, int i4) {
        return new ActivityOptionsCompatJB(ActivityOptions.makeScaleUpAnimation(view, i, i2, i3, i4));
    }

    public static ActivityOptionsCompatJB a(View view, Bitmap bitmap, int i, int i2) {
        return new ActivityOptionsCompatJB(ActivityOptions.makeThumbnailScaleUpAnimation(view, bitmap, i, i2));
    }

    private ActivityOptionsCompatJB(ActivityOptions activityOptions) {
        this.a = activityOptions;
    }
}

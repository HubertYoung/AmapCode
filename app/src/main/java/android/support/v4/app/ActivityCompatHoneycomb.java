package android.support.v4.app;

import android.app.Activity;

class ActivityCompatHoneycomb {
    ActivityCompatHoneycomb() {
    }

    static void a(Activity activity) {
        activity.invalidateOptionsMenu();
    }
}

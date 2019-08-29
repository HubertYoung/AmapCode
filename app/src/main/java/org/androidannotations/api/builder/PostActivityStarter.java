package org.androidannotations.api.builder;

import android.app.Activity;
import android.content.Context;

public final class PostActivityStarter {
    private Context a;

    public PostActivityStarter(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: 0000 */
    public final void withAnimation(int enterAnim, int exitAnim) {
        if (this.a instanceof Activity) {
            ((Activity) this.a).overridePendingTransition(enterAnim, exitAnim);
        }
    }
}

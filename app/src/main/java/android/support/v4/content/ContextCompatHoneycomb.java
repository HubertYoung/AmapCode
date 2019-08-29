package android.support.v4.content;

import android.content.Context;
import android.content.Intent;
import java.io.File;

class ContextCompatHoneycomb {
    ContextCompatHoneycomb() {
    }

    static void a(Context context, Intent[] intentArr) {
        context.startActivities(intentArr);
    }

    public static File a(Context context) {
        return context.getObbDir();
    }
}

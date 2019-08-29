package android.support.v4.content;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build.VERSION;

public class IntentCompat {
    public static final String ACTION_EXTERNAL_APPLICATIONS_AVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE";
    public static final String ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE = "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE";
    public static final String EXTRA_CHANGED_PACKAGE_LIST = "android.intent.extra.changed_package_list";
    public static final String EXTRA_CHANGED_UID_LIST = "android.intent.extra.changed_uid_list";
    public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
    public static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;
    public static final int FLAG_ACTIVITY_TASK_ON_HOME = 16384;
    private static final IntentCompatImpl IMPL;

    interface IntentCompatImpl {
        Intent a(ComponentName componentName);

        Intent a(String str, String str2);

        Intent b(ComponentName componentName);
    }

    static class IntentCompatImplBase implements IntentCompatImpl {
        IntentCompatImplBase() {
        }

        public Intent a(ComponentName componentName) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(componentName);
            intent.addCategory("android.intent.category.LAUNCHER");
            return intent;
        }

        public Intent a(String str, String str2) {
            Intent intent = new Intent(str);
            intent.addCategory(str2);
            return intent;
        }

        public Intent b(ComponentName componentName) {
            Intent a = a(componentName);
            a.addFlags(268468224);
            return a;
        }
    }

    static class IntentCompatImplHC extends IntentCompatImplBase {
        IntentCompatImplHC() {
        }

        public final Intent a(ComponentName componentName) {
            return IntentCompatHoneycomb.a(componentName);
        }

        public final Intent b(ComponentName componentName) {
            return IntentCompatHoneycomb.b(componentName);
        }
    }

    static class IntentCompatImplIcsMr1 extends IntentCompatImplHC {
        IntentCompatImplIcsMr1() {
        }

        public final Intent a(String str, String str2) {
            return IntentCompatIcsMr1.a(str, str2);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 15) {
            IMPL = new IntentCompatImplIcsMr1();
        } else if (i >= 11) {
            IMPL = new IntentCompatImplHC();
        } else {
            IMPL = new IntentCompatImplBase();
        }
    }

    private IntentCompat() {
    }

    public static Intent makeMainActivity(ComponentName componentName) {
        return IMPL.a(componentName);
    }

    public static Intent makeMainSelectorActivity(String str, String str2) {
        return IMPL.a(str, str2);
    }

    public static Intent makeRestartActivityTask(ComponentName componentName) {
        return IMPL.b(componentName);
    }
}

package android.support.v4.content;

import android.content.Intent;

class IntentCompatIcsMr1 {
    IntentCompatIcsMr1() {
    }

    public static Intent a(String str, String str2) {
        return Intent.makeMainSelectorActivity(str, str2);
    }
}

package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

class EditorCompatGingerbread {
    EditorCompatGingerbread() {
    }

    public static void a(@NonNull Editor editor) {
        try {
            editor.apply();
        } catch (AbstractMethodError unused) {
            editor.commit();
        }
    }
}

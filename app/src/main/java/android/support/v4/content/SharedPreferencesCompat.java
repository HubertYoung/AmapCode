package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public class SharedPreferencesCompat {

    public static class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper;

        static class EditorHelperApi9Impl implements Helper {
            private EditorHelperApi9Impl() {
            }

            /* synthetic */ EditorHelperApi9Impl(byte b) {
                this();
            }

            public final void a(@NonNull Editor editor) {
                EditorCompatGingerbread.a(editor);
            }
        }

        static class EditorHelperBaseImpl implements Helper {
            private EditorHelperBaseImpl() {
            }

            /* synthetic */ EditorHelperBaseImpl(byte b) {
                this();
            }

            public final void a(@NonNull Editor editor) {
                editor.commit();
            }
        }

        interface Helper {
            void a(@NonNull Editor editor);
        }

        private EditorCompat() {
            if (VERSION.SDK_INT >= 9) {
                this.mHelper = new EditorHelperApi9Impl(0);
            } else {
                this.mHelper = new EditorHelperBaseImpl(0);
            }
        }

        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        public void apply(@NonNull Editor editor) {
            this.mHelper.a(editor);
        }
    }
}

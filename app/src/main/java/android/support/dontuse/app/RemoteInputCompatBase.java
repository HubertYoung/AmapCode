package android.support.dontuse.app;

import android.os.Bundle;
import java.util.Set;

@Deprecated
public class RemoteInputCompatBase {

    public static abstract class RemoteInput {

        public interface Factory {
            RemoteInput a(String str, CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, Bundle bundle, Set<String> set);

            RemoteInput[] a(int i);
        }

        public abstract String a();

        public abstract CharSequence b();

        public abstract CharSequence[] c();

        public abstract Set<String> d();

        public abstract boolean e();

        public abstract Bundle f();
    }
}

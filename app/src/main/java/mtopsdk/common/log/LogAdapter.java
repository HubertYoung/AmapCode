package mtopsdk.common.log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface LogAdapter {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }

    String a();

    void a(int i, String str, String str2, Throwable th);

    void a(String str, String str2);
}

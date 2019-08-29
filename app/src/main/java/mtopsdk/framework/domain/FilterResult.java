package mtopsdk.framework.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface FilterResult {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }
}

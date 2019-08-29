package mtopsdk.mtop.cache;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface CacheHitType {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }
}

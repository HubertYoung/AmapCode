package mtopsdk.mtop.network;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface NetParam {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }
}

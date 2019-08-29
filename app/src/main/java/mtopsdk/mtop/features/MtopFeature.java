package mtopsdk.mtop.features;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MtopFeature {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }
}

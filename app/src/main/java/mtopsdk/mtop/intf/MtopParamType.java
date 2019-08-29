package mtopsdk.mtop.intf;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MtopParamType {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Definition {
    }
}

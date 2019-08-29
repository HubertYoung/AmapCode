package com.autonavi.minimap.search.model;

import android.support.annotation.Keep;
import com.autonavi.map.search.model.BaseResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import proguard.annotation.KeepClassMemberNames;

@Keep
@SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class ETAResponse extends BaseResponse {
    private static final long serialVersionUID = -108506608210437916L;
    public ETA auto;
    public ETA bus;
    public ETA foot;
    public ETA train;

    @Keep
    @KeepClassMemberNames
    public static class ETA implements Serializable {
        private static final long serialVersionUID = -3360169798428580522L;
        public int code;
        public int distance;
        public String message;
        public int status;
        public int texi_price;
        public int travel_time;
        public int type;
    }
}

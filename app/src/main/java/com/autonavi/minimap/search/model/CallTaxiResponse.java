package com.autonavi.minimap.search.model;

import android.support.annotation.Keep;
import com.autonavi.map.search.model.BaseResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import proguard.annotation.KeepClassMemberNames;

@Keep
public class CallTaxiResponse extends BaseResponse {
    private static final long serialVersionUID = -108506608210437916L;
    public CallTaxiData data;

    @Keep
    @KeepClassMemberNames
    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    public static class CallTaxiData implements Serializable {
        public int eta_distance;
        public int eta_time;
        public CallTaxiDataPrice simple;
    }

    @Keep
    @KeepClassMemberNames
    @SuppressFBWarnings({"UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
    public static class CallTaxiDataPrice implements Serializable {
        public int max_price;
        public int min_price;
    }
}

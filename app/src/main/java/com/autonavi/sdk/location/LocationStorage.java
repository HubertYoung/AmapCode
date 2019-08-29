package com.autonavi.sdk.location;

import com.autonavi.common.KeyValueStorage;
import com.autonavi.common.KeyValueStorage.DefaultValue;

public interface LocationStorage extends KeyValueStorage<LocationStorage> {
    @DefaultValue(500.0d)
    float getAccuracy();

    @DefaultValue(0.0d)
    String getAltitude();

    @DefaultValue(jsonValue = "39.988334")
    String getLatitude();

    @DefaultValue(jsonValue = "116.475022")
    String getLongitude();

    boolean isFistLocateCompleted();

    void setAccuracy(float f);

    void setAltitude(String str);

    void setFistLocateCompleted(boolean z);

    void setLatitude(String str);

    void setLongitude(String str);
}

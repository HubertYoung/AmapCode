package com.autonavi.bundle.entity.infolite.external;

import java.io.Serializable;

public class ResponseHeaderModule implements Serializable {
    public int errorCode;
    public String errorMessage;
    public boolean isOnLine = true;
    public int offlineResponseType = -1;
    public boolean result = false;
    public long timeStamp;
    public String version = "";
}

package com.autonavi.map.search.model;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 8649082753971036528L;
    public int code;
    public String message;
    public boolean result;
    public String timestamp;
    public String version;
}

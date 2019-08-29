package com.amap.bundle.datamodel.poi;

import java.io.Serializable;

public class CpData implements Serializable, Cloneable {
    public static final String DATA_SOURCE_AUTONAVI = "autonavi";
    private static final long serialVersionUID = 2727948247256311361L;
    private String cpid = "";
    private String source = "";

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getCpid() {
        return this.cpid;
    }

    public void setCpid(String str) {
        this.cpid = str;
    }

    public CpData clone() {
        try {
            return (CpData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

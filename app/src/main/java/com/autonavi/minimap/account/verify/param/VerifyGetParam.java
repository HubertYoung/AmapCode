package com.autonavi.minimap.account.verify.param;

import java.io.Serializable;

public class VerifyGetParam implements Serializable {
    public String code_type = null;
    public int mode = 1;
    public int skip_new = 0;
    public String target_type = null;
    public String target_value = null;
}

package com.autonavi.minimap.account.login.param;

import java.io.File;
import java.io.Serializable;

public class ProfileUpdateParam implements Serializable {
    public String adcode = null;
    public File avatar = new File("/fake");
    public String avatarfield = null;
    public String birthday = null;
    public String gender = null;
    public String nickname = null;
    public String signature = null;
}

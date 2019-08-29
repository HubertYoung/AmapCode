package com.ali.user.mobile.login.sso.info;

import java.io.Serializable;

public class SsoLoginInfo implements Serializable {
    public static final String TYPE_ALIPAY = "com.alipay";
    private static final long serialVersionUID = 4505380815810667124L;
    public String headImg;
    public Boolean isDirectLogin;
    public String loginId;
    public String loginToken;
    public String userId;
}

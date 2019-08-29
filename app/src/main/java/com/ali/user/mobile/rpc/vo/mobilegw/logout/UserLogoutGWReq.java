package com.ali.user.mobile.rpc.vo.mobilegw.logout;

import com.ali.user.mobile.rpc.vo.mobilegw.ToString;
import java.util.Map;

public class UserLogoutGWReq extends ToString {
    private static final long serialVersionUID = -2751589506345980714L;
    public String clientId;
    public Map<String, String> externParams;
    public String logonId;
    public String logoutType;
    public String mspClientKey;
    public String mspImei;
    public String mspImsi;
    public String mspTid;
    public String productId;
    public String productVersion;
    public String walletClientKey;
    public String walletTid;
}

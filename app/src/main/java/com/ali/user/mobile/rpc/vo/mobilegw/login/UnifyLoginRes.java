package com.ali.user.mobile.rpc.vo.mobilegw.login;

import com.ali.user.mobile.rpc.vo.mobilegw.ToString;
import java.io.Serializable;
import java.util.Map;

public class UnifyLoginRes extends ToString implements Serializable {
    public String alipayLoginId;
    public String checkCodeId;
    public String checkCodeUrl;
    public String code;
    public String data;
    public Map<String, String> extMap;
    public String h5Url;
    public String headImg;
    public long hid;
    public String msg;
    public String scene;
    public String signData;
    public String ssoToken;
    public boolean success;
    public String taobaoNick;
    public long taobaoUserId;
    public String tbLoginId;
    public String token;
    public String userId;
}

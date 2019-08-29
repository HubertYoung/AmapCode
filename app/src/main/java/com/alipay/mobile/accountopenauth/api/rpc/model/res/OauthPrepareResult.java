package com.alipay.mobile.accountopenauth.api.rpc.model.res;

import java.util.Date;
import java.util.List;

public class OauthPrepareResult {
    public boolean agreementCheckEnabled = false;
    public List<AuthAgreementModel> agreements;
    public String appId;
    public String appLogoLink;
    public String appName;
    public Date authEnd;
    public Date authStart;
    public List<String> authText;
    public List<BuAuthCotentModel> buAuthCotentModels;
    public String contextToken;
    public String customerForm = "";
    public String errorDestUrl;
    public String isMobile;
    public Boolean isvAgent;
    public String isvAppName;
    public String message;
    public List<IdentifyMobile> mobiles;
    public String oauthScene;
    public int oauthTpCode = 0;
    public String pid;
    public String qrCodeId;
    public String resultCode;
    public boolean skipAllowed = false;
    public String state;
    public boolean success = false;
    public String userAvatar;
    public List<UserInputData> userInputDatas;
    public String userLogonId;
}

package com.ali.auth.third.core.service;

import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.Session;

public interface CredentialService {
    Session getSession();

    boolean isSessionValid();

    ResultCode logout();

    void refreshWhenLogin(LoginReturnData loginReturnData);
}

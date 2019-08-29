package com.ali.user.mobile.service;

import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import com.ali.user.mobile.rpc.vo.mobilegw.register.RegMixRes;

public interface UserRegisterService {
    GwCommonRes a(String str, String str2, String str3, String str4, String str5, boolean z);

    RegMixRes a();
}

package com.ali.user.mobile.accountbiz.extservice;

public interface RSAService {
    String RSAEncrypt(String str, boolean z);

    String getRsaKey();

    String getRsaTS();

    long getSafeRSATS();
}

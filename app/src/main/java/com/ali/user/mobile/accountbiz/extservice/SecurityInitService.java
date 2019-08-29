package com.ali.user.mobile.accountbiz.extservice;

import android.content.Intent;
import java.util.List;

public interface SecurityInitService {
    void securityInit(Intent intent);

    void updateWalletLoginAuth(List<String> list);
}

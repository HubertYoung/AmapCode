package com.alipay.mobile.nebula.provider;

import android.os.Bundle;

public interface H5LoginProvider {
    boolean auth();

    boolean auth(Bundle bundle);

    String getExtern_token();

    String getLoginId();

    String getLoginToken();

    String getNick();

    String getUserAvatar();

    String getUserId();

    boolean isLogin();
}

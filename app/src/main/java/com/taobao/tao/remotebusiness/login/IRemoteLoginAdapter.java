package com.taobao.tao.remotebusiness.login;

import android.os.Bundle;

public interface IRemoteLoginAdapter extends IRemoteLogin {
    void setSessionInvalid(Bundle bundle);
}

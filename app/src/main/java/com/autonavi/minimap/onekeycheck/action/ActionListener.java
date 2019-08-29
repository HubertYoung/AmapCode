package com.autonavi.minimap.onekeycheck.action;

import com.autonavi.minimap.onekeycheck.exception.OneKeyCheckException;
import com.autonavi.minimap.onekeycheck.module.ResultData;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public interface ActionListener {
    void onException(BaseAction baseAction, OneKeyCheckException oneKeyCheckException);

    void onResponse(BaseAction baseAction, ResultData resultData);
}

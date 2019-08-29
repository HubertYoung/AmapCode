package com.ali.user.mobile.register.router;

import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.register.model.State;

public interface IRouterHandler {
    void afterDialog();

    BaseActivity getActivity();

    boolean handleStateChange(State state);
}

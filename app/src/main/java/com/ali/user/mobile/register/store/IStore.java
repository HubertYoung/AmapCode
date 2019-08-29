package com.ali.user.mobile.register.store;

import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;

public interface IStore {
    State a(SimpleRequest simpleRequest, State state, BaseActivity baseActivity);
}

package com.ali.auth.third.core.callback;

import com.ali.auth.third.core.model.Session;

public interface LoginCallback extends FailureCallback {
    void onSuccess(Session session);
}

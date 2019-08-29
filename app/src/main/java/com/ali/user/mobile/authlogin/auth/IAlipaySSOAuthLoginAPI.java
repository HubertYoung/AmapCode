package com.ali.user.mobile.authlogin.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.authlogin.exception.ParamNullException;
import com.ali.user.mobile.authlogin.exception.PreAuthLoginException;

public interface IAlipaySSOAuthLoginAPI {
    Bundle a();

    void a(Activity activity, IAlipaySSOPreHandler iAlipaySSOPreHandler) throws ParamNullException, PreAuthLoginException;

    void a(Intent intent, IAlipaySSOEventHandler iAlipaySSOEventHandler) throws ParamNullException;

    boolean a(Intent intent);
}

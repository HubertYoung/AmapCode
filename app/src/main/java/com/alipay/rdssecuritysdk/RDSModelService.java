package com.alipay.rdssecuritysdk;

import android.content.Context;
import com.alipay.mobile.security.senative.APSE;
import java.util.Map;

public abstract class RDSModelService {
    public abstract String getRdsRequestMessage(Context context, APSE apse);

    public abstract void onControlClick(String str, String str2);

    public abstract void onFocusChange(String str, String str2, boolean z);

    public abstract void onKeyDown(String str, String str2, String str3);

    public abstract void onPage(String str, String str2);

    public abstract void onPageEnd();

    public abstract void onTouchScreen(String str, String str2, double d, double d2);

    public abstract void reInit(Context context, Map<String, String> map, boolean z);

    public abstract void updateUser(String str);
}

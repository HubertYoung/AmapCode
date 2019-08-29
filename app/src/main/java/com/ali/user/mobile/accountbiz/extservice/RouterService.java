package com.ali.user.mobile.accountbiz.extservice;

import android.content.Context;
import android.net.Uri;

public interface RouterService {
    boolean callback(Context context, Uri uri);
}

package com.alipay.mobile.nebulacore.factory;

import android.app.Activity;
import android.os.Bundle;
import com.alipay.mobile.nebulacore.ui.H5ViewHolder;

public interface H5PageFactory {
    H5ViewHolder createPage(Activity activity, Bundle bundle);
}

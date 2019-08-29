package com.alipay.mobile.security.bio.workspace;

import android.os.Bundle;

public interface BioFragmentCallBack {
    void backward(Bundle bundle);

    void finish(Bundle bundle);

    void forward(Bundle bundle, BioFragment bioFragment);
}

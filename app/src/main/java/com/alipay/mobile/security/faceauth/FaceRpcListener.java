package com.alipay.mobile.security.faceauth;

import com.alipay.mobile.security.bio.workspace.BioFragmentResponse;

public interface FaceRpcListener {
    void onResponse(BioFragmentResponse bioFragmentResponse);
}

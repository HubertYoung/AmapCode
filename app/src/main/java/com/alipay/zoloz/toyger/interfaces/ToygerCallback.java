package com.alipay.zoloz.toyger.interfaces;

import android.content.DialogInterface.OnClickListener;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.workspace.BioFragmentResponse;
import com.alipay.mobile.security.faceauth.UserVerifyInfo;
import com.alipay.zoloz.toyger.util.DialogTypeIndex;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;

public interface ToygerCallback {
    void alert(DialogTypeIndex dialogTypeIndex, String str, String str2, String str3, String str4, OnClickListener onClickListener, String str5, OnClickListener onClickListener2, boolean z, boolean z2);

    void finishActivity(boolean z);

    DialogTypeIndex getAlertTag();

    BioAppDescription getAppDescription();

    FaceRemoteConfig getRemoteConfig();

    int getRetryTime();

    UserVerifyInfo getUserVerifyInfo();

    void gotoSettings();

    boolean haveCameraPermission();

    void sendAvatarResponse(BioFragmentResponse bioFragmentResponse);

    void sendProgressResponse(BioFragmentResponse bioFragmentResponse);

    void sendResponse(int i);

    void sendResponse(BioFragmentResponse bioFragmentResponse);

    void setCameraPermission(boolean z);

    void setRetryTime(int i);
}

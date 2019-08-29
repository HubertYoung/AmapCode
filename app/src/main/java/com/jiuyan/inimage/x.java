package com.jiuyan.inimage;

import android.graphics.Bitmap;
import android.support.v4.view.InputDeviceCompat;
import android.util.Log;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.nbnet.api.NBNetStatus;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.jiuyan.inimage.callback.IFaceDelegate;
import com.jiuyan.inimage.callback.IFaceDelegate.IDetectCallback;
import com.standardar.common.Util;
import java.lang.reflect.Array;

/* compiled from: MockLauncherActivityAgent */
class x implements IFaceDelegate {
    final /* synthetic */ w a;

    x(w wVar) {
        this.a = wVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void detectFace(Bitmap bitmap, IDetectCallback iDetectCallback) {
        int[] iArr = {AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 347, 336, 358, Util.TOF_TAG, 358, 534, 353, 255, 393, 328, 406, 426, 406, 497, 396, 379, 503, 338, 520, NBNetStatus.SC_HTTP_RANGE_NOT_SATISFIABLE, 521, 315, 570, 435, 568, 379, 563, 377, SecExceptionCode.SEC_ERROR_SIGNATURE_INCORRECT_DATA_FILE_DATA, 374, 670, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 515, 522, InputDeviceCompat.SOURCE_DPAD};
        int[][] iArr2 = (int[][]) Array.newInstance(Integer.TYPE, new int[]{1, iArr.length});
        iArr2[0] = iArr;
        iDetectCallback.onDetectResult(iArr2);
    }
}

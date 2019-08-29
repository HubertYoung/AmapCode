package tv.danmaku.ijk.media.widget;

import android.hardware.Camera;
import android.os.Looper;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APTakePictureOption;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.FaceDetectionListener;
import com.alipay.android.phone.mobilecommon.multimedia.widget.SightCameraView.TakePictureListener;

public interface FalconLooksViewInterface {
    void onCameraSwitch(Camera camera, int i);

    void setFaceDetectionListener(FaceDetectionListener faceDetectionListener);

    void setFilter(int i);

    void setMaterial(String str);

    void takePicture(TakePictureListener takePictureListener, Looper looper);

    void takePicture(TakePictureListener takePictureListener, Looper looper, APTakePictureOption aPTakePictureOption);
}

package tv.danmaku.ijk.media.encode;

import android.hardware.Camera;
import android.hardware.Camera.Size;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraHelper {
    private static final String TAG = "CameraHelper";

    private static class CameraSizeComparator implements Comparator<Size> {
        private CameraSizeComparator() {
        }

        public int compare(Size lhs, Size rhs) {
            if (lhs.width == rhs.width) {
                return 0;
            }
            if (lhs.width > rhs.width) {
                return 1;
            }
            return -1;
        }
    }

    public static Size getSuggestPictureSize(Camera camera, int minHeight) {
        return getPropPictureSize(camera.getParameters().getSupportedPictureSizes(), minHeight);
    }

    private static Size getPropPictureSize(List<Size> list, int minHeight) {
        Collections.sort(list, new CameraSizeComparator());
        int suitablePosition = -1;
        float diffRatio = -1.0f;
        for (int i = 0; i < list.size(); i++) {
            float width = (float) list.get(i).height;
            float height = (float) list.get(i).width;
            if (height >= ((float) minHeight)) {
                if (diffRatio == -1.0f) {
                    diffRatio = height / width;
                    suitablePosition = i;
                    Logger.D(TAG, height + "---" + width + "---" + diffRatio, new Object[0]);
                } else if (Math.abs((height / width) - 1.7777778f) < Math.abs(diffRatio - 1.7777778f)) {
                    diffRatio = height / width;
                    suitablePosition = i;
                    Logger.D(TAG, height + "---" + width + "---" + diffRatio, new Object[0]);
                }
                if (Math.abs(diffRatio - 1.7777778f) <= 0.02f) {
                    break;
                }
            }
        }
        if (suitablePosition == -1) {
            suitablePosition = list.size() - 1;
        }
        Logger.D(TAG, "found the final camera height is : " + list.get(suitablePosition).width + "  width is : " + list.get(suitablePosition).height, new Object[0]);
        return list.get(suitablePosition);
    }
}

package com.alipay.zoloz.toyger.blob;

import android.graphics.Bitmap;
import com.alipay.zoloz.toyger.ToygerBiometricInfo;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.algorithm.ToygerBlobConfig;
import java.util.List;
import java.util.Map;

public abstract class BlobManager<Info extends ToygerBiometricInfo> {
    protected static final String BLOB_ELEM_TYPE_DOC = "doc";
    protected static final String BLOB_ELEM_TYPE_FACE = "face";
    public static final String BLOB_VERSION = "1.0";
    protected static final int META_ALGRESULT_BAT = 3;
    protected static final int META_ALGRESULT_DRAGONFLY = 2;
    protected static final int META_ALGRESULT_VERIFY = 1;
    public static final int META_SERIALIZER_JSON = 1;
    public static final int META_SERIALIZER_PB = 2;
    protected static final String META_TYPE_DOC = "zdoc";
    protected static final String META_TYPE_FACE = "zface";
    public static final String SUB_TYPE_DARK = "Dark";
    public static final String SUB_TYPE_DEPTH = "Depth";
    public static final String SUB_TYPE_DOC_IMAGE = "docimage";
    public static final String SUB_TYPE_PANO = "Pano";
    public static final String SUB_TYPE_SURVEILLANCE = "Surveillance";
    public static final String SUB_TYPE_VERSION = "1.0";
    protected static final String TAG = "BlobManager";
    public ToygerBlobConfig config;
    public CryptoManager crypto;

    public abstract byte[] generateBlob(List<Info> list, Map<String, Object> map);

    public abstract byte[] getKey();

    public abstract boolean isUTF8();

    public byte[] processFrame(TGFrame tGFrame) {
        return processFrame(tGFrame, this.config.getDesiredWidth().intValue(), (int) (this.config.getCompressRate() * 100.0f));
    }

    public byte[] processFrame(TGFrame tGFrame, int i, int i2) {
        if (tGFrame == null || tGFrame.data == null) {
            return null;
        }
        int frameMode = getFrameMode(tGFrame);
        if (frameMode >= 0) {
            Bitmap bytes2Bitmap = BitmapHelper.bytes2Bitmap(tGFrame.data, tGFrame.width, tGFrame.height, frameMode);
            if (bytes2Bitmap != null) {
                Bitmap rotateBitmap = BitmapHelper.rotateBitmap(bytes2Bitmap, tGFrame.rotation);
                if (rotateBitmap != null) {
                    if (rotateBitmap.getWidth() <= i || i <= 0) {
                        i = rotateBitmap.getWidth();
                    }
                    if (i != tGFrame.width) {
                        rotateBitmap = BitmapHelper.resize(rotateBitmap, i);
                    }
                    if (rotateBitmap != null) {
                        byte[] bitmapToByteArray = BitmapHelper.bitmapToByteArray(rotateBitmap, i2);
                        if (bitmapToByteArray != null) {
                            byte[] encrypt = this.crypto.encrypt(bitmapToByteArray);
                            if (encrypt != null) {
                                return encrypt;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private int getFrameMode(TGFrame tGFrame) {
        switch (tGFrame.frameMode) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return -1;
        }
    }
}

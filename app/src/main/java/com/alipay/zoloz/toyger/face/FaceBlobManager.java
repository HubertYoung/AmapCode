package com.alipay.zoloz.toyger.face;

import android.graphics.Rect;
import android.graphics.RectF;
import com.alipay.zoloz.toyger.algorithm.TGFrame;
import com.alipay.zoloz.toyger.blob.BlobManager;
import com.alipay.zoloz.toyger.blob.CryptoManager;
import java.util.List;
import java.util.Map;

public abstract class FaceBlobManager extends BlobManager<ToygerFaceInfo> {
    protected static final int MONITOR_COMPRESS_RATE = 15;
    protected static final int MONITOR_IMAGE_WIDTH = 160;

    public abstract void addMonitorImage(TGFrame tGFrame);

    public abstract byte[] generateBlob(List<ToygerFaceInfo> list, Map<String, Object> map);

    public abstract byte[] getMonitorBlob();

    public abstract boolean isUTF8();

    public FaceBlobManager() {
    }

    public FaceBlobManager(ToygerFaceBlobConfig toygerFaceBlobConfig) {
        this.config = toygerFaceBlobConfig;
        this.crypto = new CryptoManager(toygerFaceBlobConfig.pubkey);
    }

    public byte[] getKey() {
        return this.crypto.getAESCypher();
    }

    public static Rect convertFaceRegion(RectF rectF, int i, int i2, int i3, boolean z) {
        Rect rect = new Rect();
        rect.left = (int) (rectF.left * ((float) i));
        rect.right = (int) (rectF.right * ((float) i));
        rect.top = (int) (rectF.top * ((float) i2));
        rect.bottom = (int) (rectF.bottom * ((float) i2));
        return rect;
    }

    /* access modifiers changed from: protected */
    public String getBlobElemType(ToygerFaceInfo toygerFaceInfo) {
        switch (toygerFaceInfo.frame.frameType) {
            case 0:
                return BlobManager.SUB_TYPE_PANO;
            case 1:
                return BlobManager.SUB_TYPE_DARK;
            case 2:
                return BlobManager.SUB_TYPE_DEPTH;
            default:
                return "";
        }
    }
}

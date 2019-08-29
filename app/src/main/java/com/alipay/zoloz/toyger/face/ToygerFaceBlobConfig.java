package com.alipay.zoloz.toyger.face;

import com.alibaba.fastjson.JSON;
import com.alipay.zoloz.toyger.algorithm.ToygerBlobConfig;
import java.util.ArrayList;
import java.util.List;

public class ToygerFaceBlobConfig extends ToygerBlobConfig {
    public List<String> collection;
    public int desiredWidth;
    public float upload_compress_rate;

    public ToygerFaceBlobConfig() {
    }

    public ToygerFaceBlobConfig(float f, int i, String str) {
        this.upload_compress_rate = f;
        this.desiredWidth = i;
        this.pubkey = str;
    }

    public float getCompressRate() {
        return this.upload_compress_rate;
    }

    public Integer getDesiredWidth() {
        return Integer.valueOf(this.desiredWidth);
    }

    public static ToygerFaceBlobConfig from(String str, String str2) {
        ToygerFaceBlobConfig toygerFaceBlobConfig = (ToygerFaceBlobConfig) JSON.parseObject(str, ToygerFaceBlobConfig.class);
        if (toygerFaceBlobConfig.desiredWidth <= 0) {
            toygerFaceBlobConfig.desiredWidth = 1280;
        }
        if (toygerFaceBlobConfig.collection == null) {
            toygerFaceBlobConfig.collection = new ArrayList();
        }
        toygerFaceBlobConfig.pubkey = str2;
        return toygerFaceBlobConfig;
    }

    public int getMinWidth(int i) {
        return this.desiredWidth > i ? i : this.desiredWidth;
    }

    public String toString() {
        return "ToygerFaceBlobConfig{upload_compress_rate=" + this.upload_compress_rate + ", desiredWidth=" + this.desiredWidth + ", collection=" + this.collection + '}';
    }
}

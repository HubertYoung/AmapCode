package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Interface;
import java.util.HashMap;
import java.util.Map;

@Interface
/* compiled from: ProGuard */
public class EmbedViewConfig {
    public int mEmbedViewID;
    public int mHeight;
    public boolean mIsCurrentPage;
    public Map mObjectParam = new HashMap();
    public String mType;
    public int mWidth;

    public EmbedViewConfig(int i, int i2, int i3, String str, String[] strArr, String[] strArr2, boolean z) {
        this.mType = str;
        this.mHeight = i;
        this.mWidth = i2;
        this.mEmbedViewID = i3;
        for (int i4 = 0; i4 < strArr.length; i4++) {
            if (i4 < strArr2.length) {
                this.mObjectParam.put(strArr[i4], strArr2[i4]);
            }
        }
        this.mIsCurrentPage = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("height=");
        sb.append(this.mHeight);
        sb.append(", width=");
        sb.append(this.mWidth);
        sb.append(", id=");
        sb.append(this.mEmbedViewID);
        sb.append(", type = ");
        sb.append(this.mType);
        sb.append(", mIsCurrentPage = ");
        sb.append(this.mIsCurrentPage);
        return sb.toString();
    }
}

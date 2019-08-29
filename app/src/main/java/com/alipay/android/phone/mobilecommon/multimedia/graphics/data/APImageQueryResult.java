package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;

public class APImageQueryResult<T extends APImageQuery> {
    public int height;
    public String path;
    public T query;
    public boolean success;
    public int width;

    public APImageQueryResult() {
    }

    public APImageQueryResult(T query2) {
        this.query = query2;
    }

    public String toString() {
        return "APImageQueryResult{success=" + this.success + ", query=" + this.query + ", path='" + this.path + '\'' + ", width=" + this.width + ", height=" + this.height + '}';
    }
}

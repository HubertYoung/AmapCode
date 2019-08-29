package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import android.text.TextUtils;

public class APGifQuery extends APImageQuery {
    public APGifQuery(String path) {
        super(path);
        this.width = Integer.valueOf(Integer.MAX_VALUE);
        this.height = Integer.valueOf(Integer.MAX_VALUE);
    }

    public String getQueryKey() {
        if (TextUtils.isEmpty(this.queryKey)) {
            String pluginKey = this.plugin != null ? this.plugin.getPluginKey() : "";
            if (!TextUtils.isEmpty(pluginKey)) {
                this.queryKey = this.path + "##" + this.width + "##" + this.height + "##" + this.cutScaleType;
            } else {
                this.queryKey = this.path + "##" + this.width + "##" + this.height + "##" + this.cutScaleType + "##" + pluginKey;
            }
        }
        return this.queryKey;
    }
}

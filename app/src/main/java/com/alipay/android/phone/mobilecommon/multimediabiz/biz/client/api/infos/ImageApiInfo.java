package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress.ServerType;

public class ImageApiInfo extends BaseApiInfo {
    public static ImageApiInfo ADD_THUMBNAILS_WARTERMARK = new ImageApiInfo(ServerType.DOWNLOAD, "rest/2.7/image", HttpMethod.GET);
    public static ImageApiInfo DOWNLOAD_THUMBNAILS = new ImageApiInfo(ServerType.DOWNLOAD, "rest/1.0/image", HttpMethod.GET);
    public static ImageApiInfo DOWNLOAD_THUMBNAILS_WARTERMARK = new ImageApiInfo(ServerType.DOWNLOAD, "rest/2.2/image", HttpMethod.GET);

    private ImageApiInfo(ServerType serverType, String apiPath, HttpMethod httpMethod) {
        super(serverType, apiPath, httpMethod);
    }
}

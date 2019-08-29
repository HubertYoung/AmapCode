package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress.ServerType;

public class ChunkApiInfo extends BaseApiInfo {
    public static ChunkApiInfo DOWNLOAD_CHUNKS = new ChunkApiInfo(ServerType.DOWNLOAD, "rest/1.0/chunk", HttpMethod.GET);
    public static ChunkApiInfo GET_CHUNKS_META = new ChunkApiInfo(ServerType.API, "rest/1.0/chunk/meta", HttpMethod.GET);
    public static ChunkApiInfo GET_FILE_CHUNKS_INFO = new ChunkApiInfo(ServerType.API, "rest/1.0/chunkinfo", HttpMethod.GET);

    private ChunkApiInfo(ServerType serverType, String apiPath, HttpMethod httpMethod) {
        super(serverType, apiPath, httpMethod);
    }
}

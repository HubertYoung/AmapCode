package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ServerAddress.ServerType;

public class FileApiInfo extends BaseApiInfo {
    public static FileApiInfo DELETE_BATCH = new FileApiInfo(ServerType.API, "rest/1.0/file", HttpMethod.DELETE);
    public static FileApiInfo DOWNLOAD_BATCH = new FileApiInfo(ServerType.DOWNLOAD, "rest/1.0/file", HttpMethod.GET);
    public static FileApiInfo GET_FILES_META = new FileApiInfo(ServerType.API, "rest/1.0/file/meta", HttpMethod.GET);
    public static FileApiInfo GET_META_INFO = new FileApiInfo(ServerType.API, "rest/1.0/file/meta", HttpMethod.GET);
    public static FileApiInfo SET_EXT = new FileApiInfo(ServerType.API, "rest/1.0/file/ext", HttpMethod.POST);
    public static FileApiInfo UPLOAD_CHECK_RAPID_PARALLEL_RANGE = new FileApiInfo(ServerType.UPLOAD, "rest/r2.6/file/head", HttpMethod.GET);
    public static FileApiInfo UPLOAD_CHECK_RAPID_RANGE = new FileApiInfo(ServerType.UPLOAD, "rest/r2.5/file/head", HttpMethod.GET);
    public static FileApiInfo UPLOAD_CHUNK_COMMIT = new FileApiInfo(ServerType.UPLOAD, "rest/1.0/file/transaction", HttpMethod.POST);
    public static FileApiInfo UPLOAD_CHUNK_OPEN = new FileApiInfo(ServerType.UPLOAD, "rest/1.0/file/transaction", HttpMethod.GET);
    public static BaseApiInfo UPLOAD_CHUNK_PROCESS = new FileApiInfo(ServerType.UPLOAD, "rest/1.1/file/chunk", HttpMethod.POST);
    public static FileApiInfo UPLOAD_CHUNK_PROCESS_RAPID = new FileApiInfo(ServerType.UPLOAD, "rest/1.1/file/chunk/head", HttpMethod.GET);
    public static FileApiInfo UPLOAD_DIRECT = new FileApiInfo(ServerType.UPLOAD, "rest/1.0/file", HttpMethod.POST);
    public static FileApiInfo UPLOAD_DIRECT_RAPID = new FileApiInfo(ServerType.UPLOAD, "rest/1.1/file/head", HttpMethod.GET);
    public static FileApiInfo UPLOAD_FILE_PARALLEL_RANGE = new FileApiInfo(ServerType.UPLOAD, "rest/r2.6/file", HttpMethod.POST);
    public static FileApiInfo UPLOAD_FILE_RANGE = new FileApiInfo(ServerType.UPLOAD, "rest/r2.5/file", HttpMethod.POST);
    public static FileApiInfo UPLOAD_OFFLINE = new FileApiInfo(ServerType.UPLOAD, "rest/1.3/file", HttpMethod.POST);

    private FileApiInfo(ServerType serverType, String apiPath, HttpMethod httpMethod) {
        super(serverType, apiPath, httpMethod);
    }
}

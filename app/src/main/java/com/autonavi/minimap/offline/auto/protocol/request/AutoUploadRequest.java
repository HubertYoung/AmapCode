package com.autonavi.minimap.offline.auto.protocol.request;

import android.text.TextUtils;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.protocol.http.HttpClientManager;
import com.autonavi.link.protocol.http.HttpProgresser;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity.DataBean.CityBean;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataFile;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataItem;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataItemRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.AutoBaseResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.UploadProgressInfo;
import com.autonavi.minimap.offline.auto.protocol.request.AutoCityInfoRequest.CityInfoResponse;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase;
import com.autonavi.minimap.offline.dataaccess.UseCase.RequestValues;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.minimap.offline.utils.log.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoUploadRequest extends UseCase<AutoUploadParam, UploadProgressInfo, Integer> {
    private static final String TAG = "AutoUploadRequest";
    private Logger logger = Logger.getLogger(TAG);
    private String mUrl;
    private HttpClientManager manager;

    public static final class AutoUploadParam implements RequestValues {
        CityBean city;
        CityInfoResponse response;

        public AutoUploadParam(CityBean cityBean, CityInfoResponse cityInfoResponse) {
            this.city = cityBean;
            this.response = cityInfoResponse;
        }
    }

    public AutoUploadRequest() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        DiscoverInfo wifiDiscoverInfo = iAutoRemoteController != null ? iAutoRemoteController.getWifiDiscoverInfo() : null;
        if (wifiDiscoverInfo != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(wifiDiscoverInfo.IP);
            sb.append(":");
            sb.append(wifiDiscoverInfo.httpPort);
            this.mUrl = sb.toString();
        }
        this.manager = new HttpClientManager();
    }

    public void cancel() {
        if (this.manager != null) {
            this.manager.cancel();
            this.manager = null;
        }
    }

    public boolean isCancel() {
        return this.manager == null;
    }

    /* access modifiers changed from: protected */
    public void executeUseCase(AutoUploadParam autoUploadParam) {
        try {
            if (TextUtils.isEmpty(this.mUrl)) {
                getUseCaseCallback().onError(Integer.valueOf(201));
                return;
            }
            ATUploadCityDataItemRequest buildUploadDataInfo = AutoUtils.buildUploadDataInfo(autoUploadParam.city, autoUploadParam.response);
            if (buildUploadDataInfo == null) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            String jsonUtil = JsonUtil.toString(buildUploadDataInfo);
            HashMap hashMap = new HashMap();
            hashMap.put("content", jsonUtil);
            this.logger.e("content:".concat(String.valueOf(jsonUtil)));
            ATUploadCityDataItem uploadCity = buildUploadDataInfo.getUploadCity();
            if (uploadCity == null) {
                getUseCaseCallback().onError(Integer.valueOf(0));
                return;
            }
            List<ATUploadCityDataFile> uploadFiles = uploadCity.getUploadFiles();
            if (uploadFiles != null) {
                if (uploadFiles.size() > 0) {
                    final double uploadTotalSize = getUploadTotalSize(uploadFiles);
                    Map<String[], Long> buildUploadFilesMap = buildUploadFilesMap(uploadFiles);
                    if (buildUploadFilesMap.size() <= 0) {
                        getUseCaseCallback().onError(Integer.valueOf(0));
                        return;
                    }
                    final UploadProgressInfo uploadProgressInfo = new UploadProgressInfo();
                    byte[] postFiles = this.manager.postFiles(this.mUrl, "/dataservice/uploadcity", hashMap, buildUploadFilesMap, new HttpProgresser() {
                        int a = 0;

                        public final void onProgress(long j, long j2, float f) {
                            double d2 = (double) j2;
                            int i = (int) ((d2 / uploadTotalSize) * 100.0d);
                            if (i - this.a > 0) {
                                uploadProgressInfo.alreadySendSize = d2;
                                uploadProgressInfo.totalSize = uploadTotalSize;
                                uploadProgressInfo.singleCitysendStatus = -1;
                                AutoUploadRequest.this.getUseCaseCallback().onSuccess(uploadProgressInfo);
                            }
                            this.a = i;
                        }
                    });
                    if (postFiles == null || postFiles.length <= 0) {
                        this.logger.e("error!");
                        getUseCaseCallback().onError(Integer.valueOf(0));
                        return;
                    }
                    AutoBaseResponse autoBaseResponse = (AutoBaseResponse) JsonUtil.fromString(new String(postFiles), AutoBaseResponse.class);
                    UploadProgressInfo uploadProgressInfo2 = new UploadProgressInfo();
                    uploadProgressInfo2.singleCitysendStatus = autoBaseResponse.code;
                    uploadProgressInfo2.alreadySendSize = uploadProgressInfo.alreadySendSize;
                    if (autoBaseResponse.code == 1) {
                        getUseCaseCallback().onSuccess(uploadProgressInfo2);
                    } else {
                        getUseCaseCallback().onError(Integer.valueOf(autoBaseResponse.code));
                    }
                    Logger logger2 = this.logger;
                    StringBuilder sb = new StringBuilder("uploadcitySuccess:");
                    sb.append(JsonUtil.toString(autoBaseResponse));
                    logger2.e(sb.toString());
                    return;
                }
            }
            getUseCaseCallback().onError(Integer.valueOf(0));
        } catch (Exception e) {
            Logger logger3 = this.logger;
            StringBuilder sb2 = new StringBuilder("error! Exception");
            sb2.append(e.getMessage());
            logger3.e(sb2.toString());
            getUseCaseCallback().onError(Integer.valueOf(1000));
        }
    }

    private Map<String[], Long> buildUploadFilesMap(List<ATUploadCityDataFile> list) {
        HashMap hashMap = new HashMap();
        if (list == null) {
            return null;
        }
        for (ATUploadCityDataFile next : list) {
            String fileType = next.getFileType();
            long offset = next.getOffset();
            String fileName = next.getFileName();
            String str = "";
            if (fileType.equals("map")) {
                str = FilePathHelper.getInstance().getMapFilePath(fileName, false);
            } else if (fileType.equals("poi")) {
                str = FilePathHelper.getInstance().getPoiFilePath(fileName, false);
            } else if (fileType.equals(AutoConstants.AUTO_FILE_ROUTE)) {
                str = FilePathHelper.getInstance().getRouteFilePath(fileName, false);
            } else if (fileType.equals(AutoConstants.AUTO_FILE_CROSS)) {
                str = FilePathHelper.getInstance().getCrossFilePath(fileName, false);
            } else if (fileType.equals(AutoConstants.AUTO_FILE_3DCROSS)) {
                str = FilePathHelper.getInstance().get3dCrossFilePath(fileName, false);
            }
            this.logger.e("filePath:".concat(String.valueOf(str)));
            hashMap.put(new String[]{fileType, str}, Long.valueOf(offset));
        }
        return hashMap;
    }

    private double getUploadTotalSize(List<ATUploadCityDataFile> list) {
        if (list == null) {
            return 0.0d;
        }
        long j = 0;
        for (ATUploadCityDataFile length : list) {
            j = (long) (((double) j) + length.getLength());
        }
        return (double) j;
    }
}

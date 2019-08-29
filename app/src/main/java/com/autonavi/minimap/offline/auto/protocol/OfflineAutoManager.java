package com.autonavi.minimap.offline.auto.protocol;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.tripgroup.api.AlinkConnectionListener;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.link.LinkSDK;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.offline.auto.model.ATJsAosInfo;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo;
import com.autonavi.minimap.offline.auto.model.ATJsApkInfo.ATJsApkItem;
import com.autonavi.minimap.offline.auto.model.ATJsApkInitInfo;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity.DataBean.CityBean;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkMemo;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackage;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATApkPackageResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATCityDataItemRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATDeleteCityDataItemRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATServerStateRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATServerStateResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataItem;
import com.autonavi.minimap.offline.auto.model.protocolModel.ATUploadCityDataListRequest;
import com.autonavi.minimap.offline.auto.model.protocolModel.AutoBaseResponse;
import com.autonavi.minimap.offline.auto.model.protocolModel.UploadProgressInfo;
import com.autonavi.minimap.offline.auto.protocol.ApkUpdateHandler.UpdateCallback;
import com.autonavi.minimap.offline.auto.protocol.request.AutoCityInfoRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoCityInfoRequest.CityInfoParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoCityInfoRequest.CityInfoResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDataListRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDelCitysRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDelCitysRequest.AutoDelCityParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDelCitysRequest.AutoDelCityResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest.AutoDownloadLogParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest.AutoDownloadResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoPrepareSendApkRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoPrepareSendApkRequest.AutoPrepareSendApkParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoPrepareSendApkRequest.AutoPrepareSendApkResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoStorageRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoStorageRequest.AutoStorageParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoSyncDownloadTaskRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoSyncDownloadTaskRequest.AutoSyncDownloadTaskParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoUploadApkRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoUploadApkRequest.AutoUploadApkParam;
import com.autonavi.minimap.offline.auto.protocol.request.AutoUploadApkRequest.AutoUploadApkResponse;
import com.autonavi.minimap.offline.auto.protocol.request.AutoUploadRequest;
import com.autonavi.minimap.offline.auto.protocol.request.AutoUploadRequest.AutoUploadParam;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils;
import com.autonavi.minimap.offline.dataaccess.UseCase.UseCaseCallback;
import com.autonavi.minimap.offline.dataaccess.UseCaseHandler;
import com.autonavi.minimap.offline.koala.Koala;
import com.autonavi.minimap.offline.koala.KoalaConfig.Builder;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloader;
import com.autonavi.minimap.offline.koala.intf.IKoalaDownloaderFactory;
import com.autonavi.minimap.offline.service.IAutoOfflineJsCallback;
import com.autonavi.minimap.offline.utils.log.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class OfflineAutoManager {
    private static final String KOALA_KEY = "auto_apk_update";
    public static final String TAG = "OfflineAutoManager";
    /* access modifiers changed from: private */
    public static Logger logger = Logger.getLogger(TAG);
    private static volatile Koala mKoala;
    private int count = 0;
    volatile boolean isCitySending = false;
    private AlinkConnectionListener mAutoLinkListener;
    /* access modifiers changed from: private */
    public String mCurrentSendCityPinyin = "";
    /* access modifiers changed from: private */
    public Queue mSendCitiesQueue = new LinkedList();
    private List<ATUploadCityDataItem> mSyncTaskCityList = new ArrayList();
    private AutoUploadApkRequest mUploadApkRequest = new AutoUploadApkRequest();
    /* access modifiers changed from: private */
    public AutoUploadRequest mUploadOfflineMapRequest = new AutoUploadRequest();
    /* access modifiers changed from: private */
    public UploadProgressInfo mUploadProgressInfo = new UploadProgressInfo();

    public void registerAutoLinkListener(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        final AutoBaseResponse autoBaseResponse = new AutoBaseResponse();
        this.mAutoLinkListener = new AlinkConnectionListener() {
            public void onConnected() {
                autoBaseResponse.setCode(300);
                iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(autoBaseResponse));
                OfflineAutoManager.this.toJson(autoBaseResponse);
                OfflineAutoManager.this.downloadAutoLog();
            }

            public void onDisconnected() {
                autoBaseResponse.setCode(301);
                iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(autoBaseResponse));
                OfflineAutoManager.this.toJson(autoBaseResponse);
            }
        };
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.addAlinkWifiConnectionListener(this.mAutoLinkListener);
        }
    }

    public void requestCityListInfo(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        UseCaseHandler.getInstance().execute(new AutoDataListRequest(), new UseCaseCallback<AutoJsCity, Integer>() {
            public final void onCancel() {
            }

            public final /* synthetic */ void onError(Object obj) {
                AutoJsCity autoJsCity = new AutoJsCity();
                autoJsCity.setCode(((Integer) obj).toString());
                String access$000 = OfflineAutoManager.this.toJson(autoJsCity);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("requestCityListInfo onError ".concat(String.valueOf(access$000)));
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                String access$000 = OfflineAutoManager.this.toJson((AutoJsCity) obj);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("requestCityListInfo ".concat(String.valueOf(access$000)));
            }
        });
    }

    public void getAutoCarState(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        ATServerStateRequest aTServerStateRequest = new ATServerStateRequest();
        aTServerStateRequest.setType(3);
        UseCaseHandler.getInstance().execute(new AutoStorageRequest(), new AutoStorageParam(aTServerStateRequest), new UseCaseCallback<ATServerStateResponse, Integer>() {
            public final void onCancel() {
            }

            public final /* synthetic */ void onError(Object obj) {
                Integer num = (Integer) obj;
                new StringBuilder("onError:").append(num);
                ATServerStateResponse aTServerStateResponse = new ATServerStateResponse();
                aTServerStateResponse.setCode(num.toString());
                String access$000 = OfflineAutoManager.this.toJson(aTServerStateResponse);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("getAutoCarState onError ".concat(String.valueOf(access$000)));
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                String access$000 = OfflineAutoManager.this.toJson((ATServerStateResponse) obj);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("getAutoCarState onSuccess ".concat(String.valueOf(access$000)));
            }
        });
    }

    public synchronized void startSendAllCities(String str, final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        logger.e("startSendAllCities ".concat(String.valueOf(str)));
        if (!this.isCitySending) {
            this.isCitySending = true;
            addAllSendCityToQueue(str);
            if (this.mSyncTaskCityList.size() <= 0) {
                AutoBaseResponse autoBaseResponse = new AutoBaseResponse();
                autoBaseResponse.setCode(0);
                iAutoOfflineJsCallback.call(toJson(autoBaseResponse));
                return;
            }
            ATUploadCityDataListRequest aTUploadCityDataListRequest = new ATUploadCityDataListRequest();
            aTUploadCityDataListRequest.setUploadCities(this.mSyncTaskCityList);
            UseCaseHandler.getInstance().execute(new AutoSyncDownloadTaskRequest(), new AutoSyncDownloadTaskParam(aTUploadCityDataListRequest), new UseCaseCallback<AutoBaseResponse, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    OfflineAutoManager.logger.e("startSendAllCities AutoSyncDownloadTaskRequest onError");
                    AutoBaseResponse autoBaseResponse = new AutoBaseResponse();
                    autoBaseResponse.setCode(((Integer) obj).intValue());
                    iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(autoBaseResponse));
                    OfflineAutoManager.this.isCitySending = false;
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    AutoBaseResponse autoBaseResponse = (AutoBaseResponse) obj;
                    OfflineAutoManager.logger.e("startSendAllCities AutoSyncDownloadTaskRequest onSuccess");
                    if (autoBaseResponse.getCode() == 1 && autoBaseResponse.getAllowToDownloadState() == 1) {
                        OfflineAutoManager.this.prepareSendCity(iAutoOfflineJsCallback);
                    } else {
                        iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(autoBaseResponse));
                    }
                    OfflineAutoManager.this.isCitySending = false;
                }
            });
        }
    }

    public void screenKeepScreenLit(boolean z) {
        if (DoNotUseTool.getActivity() != null) {
            if (z) {
                DoNotUseTool.getActivity().getWindow().addFlags(128);
            } else {
                DoNotUseTool.getActivity().getWindow().clearFlags(128);
            }
        }
    }

    public void stopSendCity(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        logger.e("stopSendCity");
        if (this.mUploadOfflineMapRequest != null) {
            this.mUploadOfflineMapRequest.cancel();
        }
        this.isCitySending = false;
        if (TextUtils.isEmpty(this.mCurrentSendCityPinyin)) {
            Logger logger2 = logger;
            StringBuilder sb = new StringBuilder("stopSendCity mCurrentSendCityPinyin");
            sb.append(this.mCurrentSendCityPinyin);
            logger2.e(sb.toString());
            AutoDelCityResponse autoDelCityResponse = new AutoDelCityResponse();
            autoDelCityResponse.setCode(201);
            iAutoOfflineJsCallback.call(toJson(autoDelCityResponse));
            return;
        }
        if (this.mCurrentSendCityPinyin.equals("quanguo")) {
            this.mCurrentSendCityPinyin = "jichugongnengbao";
        }
        ATDeleteCityDataItemRequest aTDeleteCityDataItemRequest = new ATDeleteCityDataItemRequest();
        aTDeleteCityDataItemRequest.setPinyinList(this.mCurrentSendCityPinyin);
        UseCaseHandler.getInstance().execute(new AutoDelCitysRequest(), new AutoDelCityParam(aTDeleteCityDataItemRequest), new UseCaseCallback<AutoDelCityResponse, Integer>() {
            public final void onCancel() {
            }

            public final /* synthetic */ void onError(Object obj) {
                AutoDelCityResponse autoDelCityResponse = new AutoDelCityResponse();
                autoDelCityResponse.setCode(((Integer) obj).intValue());
                String access$000 = OfflineAutoManager.this.toJson(autoDelCityResponse);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("stopSendCity onError".concat(String.valueOf(access$000)));
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                String access$000 = OfflineAutoManager.this.toJson((AutoDelCityResponse) obj);
                iAutoOfflineJsCallback.call(access$000);
                OfflineAutoManager.logger.e("stopSendCity onSuccess".concat(String.valueOf(access$000)));
            }
        });
    }

    public void stopSendCities(String str, final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        logger.e("stopSendCities");
        if (this.mUploadOfflineMapRequest != null) {
            this.mUploadOfflineMapRequest.cancel();
        }
        this.isCitySending = false;
        if (TextUtils.isEmpty(str)) {
            logger.e("stopSendCities pinyinStr".concat(String.valueOf(str)));
            return;
        }
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                String obj = jSONArray.get(i).toString();
                if (obj.equals("quanguo")) {
                    obj = "jichugongnengbao";
                }
                sb.append(obj);
                if (i < length - 1) {
                    sb.append(",");
                }
            }
            ATDeleteCityDataItemRequest aTDeleteCityDataItemRequest = new ATDeleteCityDataItemRequest();
            aTDeleteCityDataItemRequest.setPinyinList(sb.toString());
            UseCaseHandler.getInstance().execute(new AutoDelCitysRequest(), new AutoDelCityParam(aTDeleteCityDataItemRequest), new UseCaseCallback<AutoDelCityResponse, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    AutoDelCityResponse autoDelCityResponse = new AutoDelCityResponse();
                    autoDelCityResponse.setCode(((Integer) obj).intValue());
                    String access$000 = OfflineAutoManager.this.toJson(autoDelCityResponse);
                    iAutoOfflineJsCallback.call(access$000);
                    OfflineAutoManager.logger.e("stopSendCities onError".concat(String.valueOf(access$000)));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    String access$000 = OfflineAutoManager.this.toJson((AutoDelCityResponse) obj);
                    iAutoOfflineJsCallback.call(access$000);
                    OfflineAutoManager.logger.e("stopSendCities onSuccess".concat(String.valueOf(access$000)));
                }
            });
        } catch (Exception unused) {
        }
    }

    public void deleteCities(ArrayList<String> arrayList, final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        if (arrayList != null && arrayList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                StringBuilder sb = new StringBuilder();
                sb.append(it.next());
                sb.append(",");
                stringBuffer.append(sb.toString());
            }
            if (!TextUtils.isEmpty(stringBuffer)) {
                String stringBuffer2 = stringBuffer.toString();
                String substring = stringBuffer2.substring(0, stringBuffer2.length() - 1);
                if (!TextUtils.isEmpty(substring)) {
                    ATDeleteCityDataItemRequest aTDeleteCityDataItemRequest = new ATDeleteCityDataItemRequest();
                    aTDeleteCityDataItemRequest.setPinyinList(substring);
                    UseCaseHandler.getInstance().execute(new AutoDelCitysRequest(), new AutoDelCityParam(aTDeleteCityDataItemRequest), new UseCaseCallback<AutoDelCityResponse, Integer>() {
                        public final void onCancel() {
                        }

                        public final /* synthetic */ void onError(Object obj) {
                            AutoDelCityResponse autoDelCityResponse = new AutoDelCityResponse();
                            autoDelCityResponse.setCode(((Integer) obj).intValue());
                            iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(autoDelCityResponse));
                            new StringBuilder("onError:").append(OfflineAutoManager.this.toJson(autoDelCityResponse));
                        }

                        public final /* synthetic */ void onSuccess(Object obj) {
                            iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson((AutoDelCityResponse) obj));
                        }
                    });
                }
            }
        }
    }

    private static Koala getKoala() {
        if (mKoala == null) {
            Application application = AMapAppGlobal.getApplication();
            mKoala = Koala.create(KOALA_KEY, new Builder((Context) application).setPersistence(ApkDownloadPersistence.get()).setDownloaderFactory(new IKoalaDownloaderFactory() {
                public final IKoalaDownloader create() {
                    return new ApkDownloader();
                }
            }).setAllowSameUrlOnTask(false).setDownloadExecutor(Executors.newSingleThreadExecutor()).setDownloadLocalRootPath(getApkDownloadRootPath(application)).build());
        }
        return mKoala;
    }

    private static String getApkDownloadRootPath(Context context) {
        return new File(FileUtil.getInnerSDCardPath(context), "autonavi/apk/").getAbsolutePath();
    }

    /* access modifiers changed from: private */
    public ATJsApkInitInfo createInitInfo(ATApkPackageResponse aTApkPackageResponse, ATApkPackage aTApkPackage) {
        ATJsApkInitInfo aTJsApkInitInfo = new ATJsApkInitInfo();
        ApkDownloadPersistence.get().forceCheck();
        ATJsApkInfo apkInfo = ApkDownloadPersistence.get().getApkInfo();
        apkInfo.isConnected = false;
        if (isAlinkWifiConnected()) {
            IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
            if (iAutoRemoteController != null) {
                DiscoverInfo wifiDiscoverInfo = iAutoRemoteController.getWifiDiscoverInfo();
                if (wifiDiscoverInfo != null) {
                    apkInfo.display_ver = wifiDiscoverInfo.appVersion;
                }
            }
            apkInfo.isConnected = true;
        }
        if (aTApkPackageResponse != null) {
            apkInfo.version = aTApkPackageResponse.getAppver();
            apkInfo.build = aTApkPackageResponse.getBuild();
            apkInfo.div = aTApkPackageResponse.getDiv();
        } else {
            apkInfo.version = "";
            apkInfo.build = "";
            apkInfo.div = "";
        }
        ApkDownloadPersistence.get().save();
        aTJsApkInitInfo.auto = apkInfo;
        if (!(aTApkPackage == null || aTApkPackage.getMemo() == null || aTApkPackage.getMemo().size() <= 0)) {
            ATApkMemo aTApkMemo = aTApkPackage.getMemo().get(0);
            aTJsApkInitInfo.aos = new ATJsAosInfo();
            aTJsApkInitInfo.aos.size = safeParseLong(aTApkMemo.getSize());
            aTJsApkInitInfo.aos.div = aTApkMemo.getDiv();
            aTJsApkInitInfo.aos.display_ver = aTApkMemo.getApp_name();
            aTJsApkInitInfo.aos.build = aTApkMemo.getBuild();
            aTJsApkInitInfo.aos.url = aTApkMemo.getPara2();
            aTJsApkInitInfo.aos.version = aTApkMemo.getAppver();
            aTJsApkInitInfo.aos.sendParams = toJson(aTApkPackage);
        }
        return aTJsApkInitInfo;
    }

    public void initApkInfo(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        ApkUpdateHandler.get().checkUpdate(new UpdateCallback() {
            public final void onUpdate(ATApkPackageResponse aTApkPackageResponse, ATApkPackage aTApkPackage) {
                iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson(OfflineAutoManager.this.createInitInfo(aTApkPackageResponse, aTApkPackage)));
            }
        });
    }

    public synchronized void prepareSendCity(final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        Logger logger2 = logger;
        StringBuilder sb = new StringBuilder("prepareSendCity mSendCitiesQueue.size():");
        sb.append(this.mSendCitiesQueue.size());
        sb.append(",isCancel:");
        sb.append(this.mUploadOfflineMapRequest.isCancel());
        logger2.e(sb.toString());
        if (this.mSendCitiesQueue.size() <= 0 || this.mUploadOfflineMapRequest.isCancel()) {
            iAutoOfflineJsCallback.call(sendFinish());
            this.mCurrentSendCityPinyin = "";
            logger.e("全部城市发送完毕！");
            return;
        }
        final CityBean cityBean = (CityBean) this.mSendCitiesQueue.poll();
        this.count++;
        Logger logger3 = logger;
        StringBuilder sb2 = new StringBuilder("取第 ");
        sb2.append(this.count);
        sb2.append("个城市：");
        sb2.append(cityBean.getCityname());
        logger3.e(sb2.toString());
        this.mCurrentSendCityPinyin = cityBean.getPinyin();
        startSendSigleCity(cityBean, new UseCaseCallback<UploadProgressInfo, Integer>() {
            int a = 0;

            public final void onCancel() {
            }

            public final /* synthetic */ void onError(Object obj) {
                Logger access$200 = OfflineAutoManager.logger;
                StringBuilder sb = new StringBuilder();
                sb.append(cityBean.getCityname());
                sb.append("城市失败！onError");
                access$200.e(sb.toString());
                OfflineAutoManager.this.mUploadProgressInfo.setFailedCityNumIncrease();
                OfflineAutoManager.this.mUploadProgressInfo.addFailCityList(cityBean.getCityname());
                if (((Integer) obj).intValue() == 1000) {
                    while (OfflineAutoManager.this.mSendCitiesQueue.size() > 0) {
                        OfflineAutoManager.this.mUploadProgressInfo.setFailedCityNumIncrease();
                        OfflineAutoManager.this.mUploadProgressInfo.addFailCityList(((CityBean) OfflineAutoManager.this.mSendCitiesQueue.poll()).getCityname());
                    }
                    if (OfflineAutoManager.this.mSendCitiesQueue.size() == 0) {
                        iAutoOfflineJsCallback.call(OfflineAutoManager.this.sendFinish());
                        OfflineAutoManager.this.mCurrentSendCityPinyin = "";
                        OfflineAutoManager.logger.e("全部城市发送完毕！sdk异常！");
                    }
                } else {
                    OfflineAutoManager.this.prepareSendCity(iAutoOfflineJsCallback);
                }
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                UploadProgressInfo uploadProgressInfo = (UploadProgressInfo) obj;
                try {
                    if (uploadProgressInfo.singleCitysendStatus == 1) {
                        Logger access$200 = OfflineAutoManager.logger;
                        StringBuilder sb = new StringBuilder();
                        sb.append(cityBean.getCityname());
                        sb.append(" 发送成功！");
                        access$200.e(sb.toString());
                    }
                    OfflineAutoManager.this.isCitySending = false;
                    OfflineAutoManager.this.mCurrentSendCityPinyin = cityBean.getPinyin();
                    UploadProgressInfo access$500 = OfflineAutoManager.this.buildUploadResponse(uploadProgressInfo);
                    if (access$500.progress - this.a > 0) {
                        String access$000 = OfflineAutoManager.this.toJson(access$500);
                        iAutoOfflineJsCallback.call(access$000);
                        OfflineAutoManager.logger.e(access$000);
                    }
                    this.a = access$500.progress;
                    if (uploadProgressInfo.singleCitysendStatus == 1) {
                        OfflineAutoManager.this.mCurrentSendCityPinyin = "";
                        OfflineAutoManager.this.mUploadProgressInfo.addSuccCityList(cityBean.getCityname());
                        OfflineAutoManager.this.prepareSendCity(iAutoOfflineJsCallback);
                    }
                } catch (Exception e) {
                    OfflineAutoManager.this.mUploadProgressInfo.setFailedCityNumIncrease();
                    Logger access$2002 = OfflineAutoManager.logger;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(cityBean.getCityname());
                    sb2.append("城市失败！");
                    access$2002.e(sb2.toString());
                    OfflineAutoManager.this.mUploadProgressInfo.addFailCityList(cityBean.getCityname());
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public String sendFinish() {
        UploadProgressInfo uploadProgressInfo = new UploadProgressInfo();
        uploadProgressInfo.alreadySentCityNum = this.mUploadProgressInfo.getAlreadySentCityNum();
        uploadProgressInfo.totalCityNum = this.mUploadProgressInfo.getTotalCityNum();
        List<String> failCityList = this.mUploadProgressInfo.getFailCityList();
        List<String> succCityList = this.mUploadProgressInfo.getSuccCityList();
        if (failCityList.size() > 0) {
            uploadProgressInfo.failedCityList = toJson(failCityList);
            if (this.mUploadProgressInfo.getTotalCityNum() == this.mUploadProgressInfo.getFaileCityNum()) {
                uploadProgressInfo.sendState = 4;
            } else {
                uploadProgressInfo.sendState = 3;
            }
        } else {
            uploadProgressInfo.sendState = 2;
        }
        if (succCityList.size() > 0) {
            uploadProgressInfo.successCityList = toJson(succCityList);
        }
        uploadProgressInfo.code = 1;
        return toJson(uploadProgressInfo);
    }

    /* access modifiers changed from: private */
    public UploadProgressInfo buildUploadResponse(UploadProgressInfo uploadProgressInfo) {
        if (uploadProgressInfo.singleCitysendStatus == 1) {
            this.mUploadProgressInfo.setAlreadySentCityNum(this.mUploadProgressInfo.getAlreadySentCityNum() + 1);
            uploadProgressInfo.progress = this.mUploadProgressInfo.getProgress(uploadProgressInfo.alreadySendSize);
            this.mUploadProgressInfo.setAlreadySendSize(this.mUploadProgressInfo.getAlreadySendSize() + uploadProgressInfo.alreadySendSize);
        } else {
            uploadProgressInfo.progress = this.mUploadProgressInfo.getProgress(uploadProgressInfo.alreadySendSize);
        }
        uploadProgressInfo.sendState = 1;
        uploadProgressInfo.alreadySentCityNum = this.mUploadProgressInfo.getAlreadySentCityNum();
        uploadProgressInfo.totalCityNum = this.mUploadProgressInfo.getTotalCityNum();
        return uploadProgressInfo;
    }

    private ATJsApkItem parseApkSendParams(ATApkPackage aTApkPackage) {
        if (aTApkPackage == null) {
            return null;
        }
        List<ATApkMemo> memo = aTApkPackage.getMemo();
        if (memo == null || memo.size() == 0) {
            return null;
        }
        ATApkMemo aTApkMemo = memo.get(0);
        for (ATJsApkItem next : ApkDownloadPersistence.get().getApkInfo().items) {
            if (TextUtils.equals(aTApkMemo.getAppver(), next.version) && TextUtils.equals(aTApkMemo.getBuild(), next.build) && TextUtils.equals(aTApkMemo.getDiv(), next.div)) {
                return next;
            }
        }
        return null;
    }

    private boolean isApkItemFileExists(ATJsApkItem aTJsApkItem) {
        return aTJsApkItem != null && !TextUtils.isEmpty(aTJsApkItem.local_file) && new File(aTJsApkItem.local_file).exists();
    }

    public void prepareSendApk(String str, final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        ATApkPackage aTApkPackage = (ATApkPackage) fromJson(str, ATApkPackage.class);
        if (!isApkItemFileExists(parseApkSendParams(aTApkPackage))) {
            iAutoOfflineJsCallback.call(createErrorJson(AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST));
        } else {
            UseCaseHandler.getInstance().execute(new AutoPrepareSendApkRequest(), new AutoPrepareSendApkParam(aTApkPackage), new UseCaseCallback<AutoPrepareSendApkResponse, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    iAutoOfflineJsCallback.call(OfflineAutoManager.this.createErrorJson(((Integer) obj).intValue()));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    iAutoOfflineJsCallback.call(OfflineAutoManager.this.createErrorJson(((AutoPrepareSendApkResponse) obj).rsp.getCode()));
                }
            });
        }
    }

    public void startSendApk(String str, final IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        ATApkPackage aTApkPackage = (ATApkPackage) fromJson(str, ATApkPackage.class);
        ATJsApkItem parseApkSendParams = parseApkSendParams(aTApkPackage);
        if (!isApkItemFileExists(parseApkSendParams)) {
            iAutoOfflineJsCallback.call(createErrorJson(4));
        } else {
            UseCaseHandler.getInstance().execute(this.mUploadApkRequest, new AutoUploadApkParam(parseApkSendParams.local_file, aTApkPackage), new UseCaseCallback<AutoUploadApkResponse, Integer>() {
                public final void onCancel() {
                }

                public final /* synthetic */ void onError(Object obj) {
                    iAutoOfflineJsCallback.call(OfflineAutoManager.this.createErrorJson(((Integer) obj).intValue()));
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    iAutoOfflineJsCallback.call(OfflineAutoManager.this.toJson((AutoUploadApkResponse) obj));
                }
            });
        }
    }

    public void stopSendApk() {
        if (this.mUploadApkRequest != null) {
            this.mUploadApkRequest.cancel();
        }
    }

    private void startSendSigleCity(final CityBean cityBean, final UseCaseCallback<UploadProgressInfo, Integer> useCaseCallback) {
        String pinyin = cityBean.getPinyin();
        if (TextUtils.isEmpty(pinyin)) {
            useCaseCallback.onError(Integer.valueOf(0));
            return;
        }
        if (pinyin.equalsIgnoreCase("quanguo")) {
            pinyin = "jichugongnengbao";
        }
        ATCityDataItemRequest aTCityDataItemRequest = new ATCityDataItemRequest();
        aTCityDataItemRequest.setPinyin(pinyin);
        UseCaseHandler.getInstance().execute(new AutoCityInfoRequest(), new CityInfoParam(aTCityDataItemRequest), new UseCaseCallback<CityInfoResponse, Integer>() {
            public final void onCancel() {
            }

            public final /* bridge */ /* synthetic */ void onError(Object obj) {
                useCaseCallback.onError((Integer) obj);
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                CityInfoResponse cityInfoResponse = (CityInfoResponse) obj;
                OfflineAutoManager.logger.e("startSendAllCities AutoCityInfoRequest onSuccess ");
                try {
                    UseCaseHandler.getInstance().execute(OfflineAutoManager.this.mUploadOfflineMapRequest, new AutoUploadParam(cityBean, cityInfoResponse), useCaseCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                    useCaseCallback.onError(Integer.valueOf(0));
                }
            }
        });
    }

    private void addAllSendCityToQueue(String str) {
        if (!TextUtils.isEmpty(str)) {
            List<CityBean> buildJsSendCities = AutoJsonUtils.buildJsSendCities(str);
            if (buildJsSendCities != null && buildJsSendCities.size() > 0) {
                reSetAllCityData();
                this.mSendCitiesQueue.clear();
                double d = 0.0d;
                double d2 = 0.0d;
                for (CityBean next : buildJsSendCities) {
                    d += AutoJsonUtils.calculateCityFileSize(next);
                    d2 += next.getAlreadyDownloadSize();
                    this.mSyncTaskCityList.add(AutoUtils.buildSyncTaskCity(next));
                    this.mSendCitiesQueue.offer(next);
                }
                this.mUploadProgressInfo.setAlreadySendSize(d2);
                this.mUploadProgressInfo.setTotalSize(d);
                this.mUploadProgressInfo.setTotalCityNum(this.mSendCitiesQueue.size());
                Logger logger2 = logger;
                StringBuilder sb = new StringBuilder("待发送城市数:");
                sb.append(this.mUploadProgressInfo.getTotalCityNum());
                sb.append(",alreadSendSize:");
                sb.append(d2);
                logger2.e(sb.toString());
            }
        }
    }

    private void reSetAllCityData() {
        this.mUploadProgressInfo = new UploadProgressInfo();
        this.mUploadOfflineMapRequest = new AutoUploadRequest();
        this.mSyncTaskCityList = new ArrayList();
        this.count = 0;
    }

    public void bindDownloadApk(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        ApkDownloadListener.get().setKoala(getKoala());
        if (!getKoala().isBind(ApkDownloadListener.get())) {
            getKoala().bind(ApkDownloadListener.get());
        }
        ApkDownloadListener.get().setCallback(iAutoOfflineJsCallback);
    }

    public void unbindDownloadApk() {
        getKoala().forcePersistence();
        ApkDownloadListener.get().setCallback(null);
    }

    public void startDownloadApk(String str, String str2, String str3, String str4, String str5, long j, String str6) {
        String str7 = str;
        ApkDownloadPersistence.get().addExtra(str7, str4, str2, str3, str5, j, str6);
        getKoala().start(str7);
    }

    public void pauseDownloadApk(int i) {
        getKoala().pause(i);
    }

    public void resumeDownloadApk(int i) {
        getKoala().resume(i);
    }

    public void stopDownloadApk(int i) {
        getKoala().stop(i);
    }

    public void removeApk(int i) {
        getKoala().remove(i);
    }

    public boolean autoConnectStatus() {
        return LinkSDK.getInstance().getWifiInstance().getIsConnect();
    }

    /* access modifiers changed from: private */
    public void downloadAutoLog() {
        UseCaseHandler.getInstance().execute(new AutoDownloadLogRequest(), new AutoDownloadLogParam(), new UseCaseCallback<AutoDownloadResponse, Integer>() {
            public final void onCancel() {
            }

            public final /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            }

            public final /* synthetic */ void onError(Object obj) {
                new StringBuilder("str:").append((Integer) obj);
            }
        });
    }

    private <T> T fromJson(String str, Class<T> cls) {
        try {
            return JsonUtil.fromString(str, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: private */
    public String createErrorJson(int i) {
        StringBuilder sb = new StringBuilder("{\"code\":");
        sb.append(i);
        sb.append(h.d);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public <T> String toJson(T t) {
        try {
            return JsonUtil.toString(t);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private long safeParseLong(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Long.parseLong(str);
        } catch (Throwable unused) {
            return 0;
        }
    }

    public void unRegisterAutoListener() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.removeAlinkWifiConnectionListener(this.mAutoLinkListener);
        }
    }

    private boolean isAlinkWifiConnected() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            return iAutoRemoteController.IsWifiConnected();
        }
        return false;
    }
}

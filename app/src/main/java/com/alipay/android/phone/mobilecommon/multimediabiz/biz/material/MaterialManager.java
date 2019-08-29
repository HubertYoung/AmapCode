package com.alipay.android.phone.mobilecommon.multimediabiz.biz.material;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;
import com.alipay.android.phone.mobilecommon.multimedia.file.APFileDownCallback;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.material.APBizMaterialPackage;
import com.alipay.android.phone.mobilecommon.multimedia.material.APDownloadStatus;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFalconAbility;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialDownloadRequest;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APBizMaterialPackageQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCancelListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnCompleteListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnDownloadTaskAddListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnErrorListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APOnProgressListener;
import com.alipay.android.phone.mobilecommon.multimedia.material.callback.APPackageQueryCallback;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APBizMaterialPackageQueryError;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APDownloadTaskAdd;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APPackageQueryComplete;
import com.alipay.android.phone.mobilecommon.multimedia.material.response.APPackageQueryError;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.VideoUtils;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class MaterialManager {
    private static MaterialManager a;
    private static final Logger b = Logger.getLogger((String) "MaterialManager");
    private Context c = AppUtils.getApplicationContext();
    private MultimediaFileService d = AppUtils.getFileService();
    private MaterialDownloadListenerHandler e = new MaterialDownloadListenerHandler(this);
    private final Map<APMaterialDownloadRequest, APMultimediaTaskModel> f = new ConcurrentHashMap();
    private Map<String, APDownloadStatus> g = new ConcurrentHashMap();
    private MaterialResourcesManager h = MaterialResourcesManager.get();
    private Map<String, APMaterialInfo> i = new ConcurrentHashMap();
    private APBizMaterialPackage j;
    /* access modifiers changed from: private */
    public final AtomicBoolean k = new AtomicBoolean(false);

    private MaterialManager() {
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                synchronized (MaterialManager.this.k) {
                    MaterialManager.this.a();
                    MaterialManager.this.k.set(true);
                }
            }
        });
    }

    public static MaterialManager get() {
        if (a == null) {
            synchronized (MaterialManager.class) {
                if (a == null) {
                    a = new MaterialManager();
                }
            }
        }
        return a;
    }

    public void downloadMaterial(APMaterialDownloadRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request must be not null!!");
        }
        synchronized (this.f) {
            if (this.f.containsKey(request)) {
                APMultimediaTaskModel model = this.f.get(request);
                a(model.getTaskId(), request);
                a(model, getMaterialInfo(request.getId()), request);
            } else {
                a(request);
            }
        }
    }

    private void a(APMaterialDownloadRequest request) {
        b(request);
    }

    private void b(APMaterialDownloadRequest request) {
        APMaterialInfo info = new APMaterialInfo();
        info.materialId = request.getId();
        a(a(info.materialId, info, request), info, request);
    }

    private APMultimediaTaskModel a(String cloudId, APMaterialInfo info, APMaterialDownloadRequest request) {
        APDownloadStatus status = new APDownloadStatus();
        APFileDownCallback callback = new FileDownloadCallbackProxy(this, request, info, this.e, status);
        this.g.put(request.getId(), status);
        APFileReq fileReq = new APFileReq();
        fileReq.setCloudId(cloudId);
        fileReq.setSavePath(MaterialResourcesManager.createTempSavePath(this.c, request.getId()));
        fileReq.setIsNeedCache(true);
        fileReq.businessId = MaterialResourcesManager.BUSINESS_ID;
        APMultimediaTaskModel taskModel = this.d.downLoad(fileReq, callback, (String) MaterialResourcesManager.BUSINESS_ID);
        a(taskModel.getTaskId(), request);
        return taskModel;
    }

    private static void a(APMultimediaTaskModel model, APMaterialInfo info, APMaterialDownloadRequest request) {
        a(new APDownloadTaskAdd(model, info), request);
    }

    private static void a(APDownloadTaskAdd add, APMaterialDownloadRequest request) {
        b.d("notifyAddDownloadTask add: " + add + ", request: " + request, new Object[0]);
        APOnDownloadTaskAddListener listener = request != null ? request.getTaskAddListener() : null;
        if (listener != null) {
            listener.onAddSuccess(add);
        }
    }

    private void a(String taskId, APMaterialDownloadRequest request) {
        if (request.getProgressListener() != null) {
            registerDownloadProgressListener(taskId, request.getProgressListener());
        }
        if (request.getCompleteListener() != null) {
            registerDownloadCompleteListener(taskId, request.getCompleteListener());
        }
        if (request.getCancelListener() != null) {
            registerDownloadCancelListener(taskId, request.getCancelListener());
        }
        if (request.getErrorListener() != null) {
            registerDownloadErrorListener(taskId, request.getErrorListener());
        }
    }

    public void cancelDownloadMaterial(String id) {
        b.e("cancelDownloadMaterial id: " + id, new Object[0]);
        for (APMaterialDownloadRequest request : this.f.keySet()) {
            if (id.equals(request.getId())) {
                this.d.cancelLoad(this.f.get(request).getTaskId());
            }
        }
    }

    public void registerDownloadProgressListener(String taskId, APOnProgressListener listener) {
        this.e.registerProgressListener(taskId, listener);
    }

    public void unregisterDownloadProgressListener(String taskId, APOnProgressListener listener) {
        this.e.unregisterProgressListener(taskId, listener);
    }

    public void registerDownloadCompleteListener(String taskId, APOnCompleteListener listener) {
        this.e.registerCompleteListener(taskId, listener);
    }

    public void unregisterDownloadCompleteListener(String taskId, APOnCompleteListener listener) {
        this.e.unregisterCompleteListener(taskId, listener);
    }

    public void registerDownloadErrorListener(String taskId, APOnErrorListener listener) {
        this.e.registerErrorListener(taskId, listener);
    }

    public void unregisterDownloadErrorListener(String taskId, APOnErrorListener listener) {
        this.e.unregisterErrorListener(taskId, listener);
    }

    public void registerDownloadCancelListener(String taskId, APOnCancelListener listener) {
        this.e.registerCancelListener(taskId, listener);
    }

    public void unregisterDownloadCancelListener(String taskId, APOnCancelListener listener) {
        this.e.unregisterCancelListener(taskId, listener);
    }

    public APDownloadStatus getMaterialStatus(String id) {
        APMaterialInfo info = getMaterialInfo(id);
        APDownloadStatus status = this.g.get(id);
        if (status == null && info != null) {
            APMultimediaTaskModel taskModel = this.d.getLoadTaskStatusByCloudId(info.materialId);
            status = new APDownloadStatus();
            status.fromFileStatus(taskModel);
            this.g.put(id, status);
        }
        a(info, status);
        return status;
    }

    private static void a(APMaterialInfo info, APDownloadStatus status) {
        switch (status.getStatus()) {
            case 4:
            case 5:
                if (TextUtils.isEmpty(info.materialPath)) {
                    status.setStatus(3);
                    return;
                }
                return;
            case 7:
                if (!TextUtils.isEmpty(info.materialPath)) {
                    status.setStatus(4);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public APPackageInfo getPackageInfo(String id, APPackageQueryCallback callback) {
        APPackageInfo pkg = MaterialResourcesManager.get().getPackageInfo(a(id) ? id : b(id));
        b.d("getPackageInfo id: " + id + ", callback: " + callback + ", mPackageInfo: " + pkg, new Object[0]);
        if (pkg == null) {
            APPackageQueryError error = new APPackageQueryError();
            error.code = 1000;
            error.id = id;
            error.msg = "package does not exists";
            callback.onQueryError(error);
        } else if (callback != null) {
            callback.onQueryComplete(new APPackageQueryComplete(pkg));
        }
        return pkg;
    }

    /* access modifiers changed from: protected */
    public void removeDownloadTask(APMaterialDownloadRequest request) {
        if (request != null) {
            this.f.remove(request);
        }
    }

    private static boolean a(String id) {
        return id.length() == 32;
    }

    public boolean saveMaterialResource(APMaterialInfo materialInfo, APMaterialDownloadRequest downloadRequest, String savePath) {
        return this.h.saveMaterialResource(materialInfo, savePath);
    }

    private static String b(String businessId) {
        return businessId.substring(3);
    }

    public APMaterialInfo getMaterialInfo(String id) {
        String path = this.h.getMaterialPath(id);
        APMaterialInfo info = c(id);
        if (info == null) {
            info = new APMaterialInfo();
            info.materialId = id;
        }
        info.materialPath = path;
        return info;
    }

    public List<APFilterInfo> getSupportedFilters() {
        return this.h.getSupportedFilters();
    }

    public APBizMaterialPackage getBizMaterialPackage(String businessId, APBizMaterialPackageQueryCallback cb) {
        if (cb != null) {
            APBizMaterialPackageQueryError error = new APBizMaterialPackageQueryError();
            error.code = 1000;
            error.id = businessId;
            error.msg = "BusinessId: " + businessId + " does not found!";
            cb.onQueryError(error);
        }
        return null;
    }

    public APBizMaterialPackage getPresetBizMaterialPackage(String businessId) {
        long start = System.currentTimeMillis();
        if (this.j == null && !this.k.get()) {
            synchronized (this.k) {
                a();
            }
        }
        b.d("getPresetBizMaterialPackage businessId: " + businessId + ", cost: " + (System.currentTimeMillis() - start), new Object[0]);
        return this.j;
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.j == null) {
            long start = System.currentTimeMillis();
            this.j = (APBizMaterialPackage) JSON.parseObject((String) Defaults.DEFAULT_BIZ_MATERIAL_PACKAGE_JSON, APBizMaterialPackage.class);
            a(this.j);
            a(this.j.mPackageInfos);
            b.d("initPresetRes cost: " + (System.currentTimeMillis() - start), new Object[0]);
        }
    }

    private static void a(APBizMaterialPackage pkg) {
        if (pkg != null) {
            for (APPackageInfo info : pkg.mPackageInfos) {
                info.iconId = "file:///[asset]/material/icons/" + info.iconId;
                info.selectedIconId = "file:///[asset]/material/icons/" + info.selectedIconId;
            }
            b.d("fillAssetsPresetResources result: " + pkg, new Object[0]);
        }
    }

    private void a(List<APPackageInfo> packageInfos) {
        if (packageInfos != null && !packageInfos.isEmpty()) {
            for (APPackageInfo aPPackageInfo : packageInfos) {
                List infos = aPPackageInfo.mMaterialInfos;
                if (infos != null && !infos.isEmpty()) {
                    for (APMaterialInfo info : infos) {
                        this.i.put(info.materialId, info);
                    }
                }
            }
        }
    }

    private APMaterialInfo c(String materialId) {
        if (!TextUtils.isEmpty(materialId)) {
            return this.i.get(materialId);
        }
        return null;
    }

    public APFalconAbility getAbility() {
        boolean z;
        boolean cloudSwitch;
        APFalconAbility ability = new APFalconAbility();
        if (VERSION.SDK_INT < 18 || !ConfigManager.getInstance().getFalconConfig().isFalconSwitchOn() || !VideoUtils.supportGles30(this.c)) {
            z = false;
        } else {
            z = true;
        }
        ability.deviceSupport = z;
        if (!ability.deviceSupport || !ConfigManager.getInstance().getFilterConfSwitch(ability.falconSwitch)) {
            cloudSwitch = false;
        } else {
            cloudSwitch = true;
        }
        ability.falconSwitch = FalconFactory.INS.isSupportWaterMark(cloudSwitch);
        return ability;
    }
}

package com.alipay.zoloz.toyger.upload;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavCommon;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavLog;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisBehavToken;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientInfo;
import com.alipay.mobile.security.bio.runtime.Runtime;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.BioTaskService;
import com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService;
import com.alipay.mobile.security.bio.task.SubTask;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.FileUtil;
import com.alipay.mobile.security.faceauth.InvokeType;
import com.alipay.mobile.security.faceauth.UserVerifyInfo;
import com.alipay.zoloz.toyger.ToygerService;
import com.alipay.zoloz.toyger.bean.ToygerFrame;
import com.alipay.zoloz.toyger.extservice.record.TimeRecord;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;
import com.alipay.zoloz.toyger.interfaces.ToygerCallback;
import com.alipay.zoloz.toyger.workspace.FaceRemoteConfig;
import com.alipay.zoloz.toyger.workspace.ToygerWorkspace;
import com.alipay.zoloz.toyger.workspace.task.ToygerBaseTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UploadManager {
    public static final String TAG = "UploadManager";
    private BioAppDescription mBioAppDescription;
    private BioServiceManager mBioServiceManager;
    private BioTaskService mBioTaskService = ((BioTaskService) this.mBioServiceManager.getBioService(BioTaskService.class));
    private Context mContext;
    private NineShootManager mNineShootManager;
    private ToygerCallback mToygerCallback;
    ToygerWorkspace mToygerWorkspace;
    private UploadChannel mUploadChannel;
    private String publicKey;

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0044, code lost:
        r0 = java.lang.Integer.parseInt(r1.get("meta_serializer"));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public UploadManager(com.alipay.zoloz.toyger.workspace.ToygerWorkspace r5, com.alipay.mobile.security.bio.service.BioServiceManager r6, com.alipay.zoloz.toyger.interfaces.ToygerCallback r7) {
        /*
            r4 = this;
            r4.<init>()
            r4.mToygerWorkspace = r5
            r4.mBioServiceManager = r6
            com.alipay.mobile.security.bio.service.BioServiceManager r0 = r4.mBioServiceManager
            java.lang.Class<com.alipay.mobile.security.bio.service.BioTaskService> r1 = com.alipay.mobile.security.bio.service.BioTaskService.class
            java.lang.Object r0 = r0.getBioService(r1)
            com.alipay.mobile.security.bio.service.BioTaskService r0 = (com.alipay.mobile.security.bio.service.BioTaskService) r0
            r4.mBioTaskService = r0
            android.content.Context r0 = r6.getBioApplicationContext()
            r4.mContext = r0
            com.alipay.mobile.security.bio.service.BioAppDescription r0 = r7.getAppDescription()
            r4.mBioAppDescription = r0
            r4.mToygerCallback = r7
            android.content.Context r0 = r4.mContext
            com.alipay.zoloz.toyger.interfaces.ToygerCallback r1 = r4.mToygerCallback
            com.alipay.zoloz.toyger.workspace.FaceRemoteConfig r1 = r1.getRemoteConfig()
            java.lang.String r0 = getPublicKey(r0, r1)
            r4.publicKey = r0
            com.alipay.zoloz.toyger.interfaces.ToygerCallback r0 = r4.mToygerCallback
            com.alipay.mobile.security.bio.service.BioAppDescription r0 = r0.getAppDescription()
            java.util.Map r1 = r0.getExtProperty()
            r0 = 2
            if (r1 == 0) goto L_0x0050
            java.lang.String r2 = "meta_serializer"
            boolean r2 = r1.containsKey(r2)
            if (r2 == 0) goto L_0x0050
            java.lang.String r0 = "meta_serializer"
            java.lang.Object r0 = r1.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            int r0 = java.lang.Integer.parseInt(r0)
        L_0x0050:
            switch(r0) {
                case 2: goto L_0x0087;
                default: goto L_0x0053;
            }
        L_0x0053:
            java.lang.String r0 = "com.alipay.zoloz.toyger.upload.UploadChannelByJson"
        L_0x0055:
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x008a }
            r1 = 1
            java.lang.Class[] r1 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x008a }
            r2 = 0
            java.lang.Class<com.alipay.mobile.security.bio.service.BioServiceManager> r3 = com.alipay.mobile.security.bio.service.BioServiceManager.class
            r1[r2] = r3     // Catch:{ Throwable -> 0x008a }
            java.lang.reflect.Constructor r0 = r0.getConstructor(r1)     // Catch:{ Throwable -> 0x008a }
            r1 = 1
            r0.setAccessible(r1)     // Catch:{ Throwable -> 0x008a }
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x008a }
            r2 = 0
            r1[r2] = r6     // Catch:{ Throwable -> 0x008a }
            java.lang.Object r0 = r0.newInstance(r1)     // Catch:{ Throwable -> 0x008a }
            com.alipay.zoloz.toyger.upload.UploadChannel r0 = (com.alipay.zoloz.toyger.upload.UploadChannel) r0     // Catch:{ Throwable -> 0x008a }
            r4.mUploadChannel = r0     // Catch:{ Throwable -> 0x008a }
        L_0x0077:
            com.alipay.zoloz.toyger.upload.NineShootManager r0 = new com.alipay.zoloz.toyger.upload.NineShootManager
            com.alipay.zoloz.toyger.face.ToygerFaceService r1 = r5.getToygerFaceService()
            com.alipay.zoloz.toyger.workspace.FaceRemoteConfig r2 = r7.getRemoteConfig()
            r0.<init>(r1, r2)
            r4.mNineShootManager = r0
            return
        L_0x0087:
            java.lang.String r0 = "com.alipay.zoloz.toyger.upload.UploadChannelByPb"
            goto L_0x0055
        L_0x008a:
            r0 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)
            goto L_0x0077
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.zoloz.toyger.upload.UploadManager.<init>(com.alipay.zoloz.toyger.workspace.ToygerWorkspace, com.alipay.mobile.security.bio.service.BioServiceManager, com.alipay.zoloz.toyger.interfaces.ToygerCallback):void");
    }

    public void uploadFaceInfo(ToygerFrame toygerFrame) {
        if (this.mUploadChannel != null) {
            UploadContent uploadContent = toygerFrame.uploadContent;
            BisBehavLog bisBehavLogData = getBisBehavLogData(this.mToygerCallback.getUserVerifyInfo(), InvokeType.NORMAL);
            String bistoken = this.mToygerCallback.getAppDescription().getBistoken();
            TimeRecord.getInstance().setUploadStartTime(System.currentTimeMillis());
            HashMap hashMap = new HashMap();
            hashMap.put("faceQuality", toygerFrame.tgFaceAttr.quality);
            ((ToygerRecordService) this.mBioServiceManager.getBioService(ToygerRecordService.class)).write(ToygerRecordService.UPLOAD_START, hashMap);
            this.mUploadChannel.uploadFaceInfo(uploadContent, bisBehavLogData, bistoken, this.publicKey);
        }
        uploadNineShoot();
    }

    public void uploadNineShoot() {
        if (this.mUploadChannel != null && this.mNineShootManager != null && this.mNineShootManager.isNeedUpload()) {
            this.mUploadChannel.uploadNineShoot(getNineShoot(), getBisBehavLogData(this.mToygerCallback.getUserVerifyInfo(), InvokeType.MONITOR), this.mBioAppDescription.getBistoken(), this.publicKey);
        }
    }

    public void uploadBehaviourLog(InvokeType invokeType) {
        if (this.mUploadChannel != null) {
            this.mUploadChannel.uploadBehaviourLog(getBisBehavLogData(this.mToygerCallback.getUserVerifyInfo(), invokeType), this.mBioAppDescription.getBistoken(), this.publicKey);
        }
    }

    public static String getPublicKey(Context context, FaceRemoteConfig faceRemoteConfig) {
        byte[] assetsData;
        try {
            BioLog.i("PublicKey:" + faceRemoteConfig.getEnv());
            if (faceRemoteConfig.getEnv() == 0) {
                assetsData = FileUtil.getAssetsData(context, (String) "bid-log-key-public.key");
            } else {
                assetsData = FileUtil.getAssetsData(context, (String) "bid-log-key-public_t.key");
            }
            return new String(assetsData);
        } catch (IllegalStateException e) {
            BioLog.e(e.toString());
            r0 = "";
            return "";
        } catch (IllegalArgumentException e2) {
            BioLog.e(e2.toString());
            r0 = "";
            return "";
        }
    }

    private BisBehavLog getBisBehavLogData(UserVerifyInfo userVerifyInfo, InvokeType invokeType) {
        BisBehavLog bisBehavLog = new BisBehavLog();
        BisClientInfo bisClientInfo = new BisClientInfo();
        bisClientInfo.setClientVer(Runtime.getFrameworkVersion(this.mContext));
        bisClientInfo.setModel(Build.MODEL);
        bisClientInfo.setOs("android");
        bisClientInfo.setOsVer(VERSION.RELEASE);
        BisBehavToken bisBehavToken = new BisBehavToken();
        bisBehavToken.setApdid(userVerifyInfo.apdid);
        bisBehavToken.setAppid(userVerifyInfo.appid);
        ApSecurityService apSecurityService = (ApSecurityService) this.mBioServiceManager.getBioService(ApSecurityService.class);
        if (apSecurityService != null) {
            bisBehavToken.setApdidToken(apSecurityService.getApDidToken());
        }
        bisBehavToken.setBehid(userVerifyInfo.behid);
        if (this.mBioAppDescription != null) {
            bisBehavToken.setToken(this.mBioAppDescription.getBistoken());
            if (this.mBioAppDescription.getBioAction() == 991 || this.mBioAppDescription.getBioAction() == 992) {
                bisBehavToken.setSampleMode(302);
            } else if (this.mBioAppDescription.getBioAction() == 992) {
                bisBehavToken.setSampleMode(303);
            } else {
                bisBehavToken.setSampleMode(this.mBioAppDescription.getBioAction());
            }
            bisBehavToken.setType(this.mBioAppDescription.getBioType());
            bisBehavToken.setBizid(this.mBioAppDescription.getBistoken());
        }
        bisBehavToken.setUid(userVerifyInfo.userid);
        bisBehavToken.setVtoken(userVerifyInfo.vtoken);
        bisBehavToken.setVerifyid(userVerifyInfo.verifyid);
        BisBehavCommon bisBehavCommon = new BisBehavCommon();
        bisBehavCommon.setInvtp(invokeType.toString());
        bisBehavCommon.setTm("");
        bisBehavCommon.setRetry(this.mToygerCallback.getRetryTime());
        ArrayList arrayList = new ArrayList();
        Iterator<SubTask> it = this.mBioTaskService.getTasks().iterator();
        while (it.hasNext()) {
            ToygerBaseTask toygerBaseTask = (ToygerBaseTask) it.next();
            if (toygerBaseTask != null && toygerBaseTask.isHasBeHaviorInfo()) {
                arrayList.add(toygerBaseTask.getBisBehavTask());
            }
        }
        bisBehavLog.setBehavCommon(bisBehavCommon);
        bisBehavLog.setBehavTask(arrayList);
        bisBehavLog.setBehavToken(bisBehavToken);
        bisBehavLog.setClientInfo(bisClientInfo);
        BioLog.i("bisBehavLog:" + JSON.toJSONString(bisBehavLog));
        return bisBehavLog;
    }

    public UploadContent getNineShoot() {
        if (this.mNineShootManager == null) {
            return null;
        }
        Map<String, Object> generateMonitorBlob = this.mToygerWorkspace.getToygerFaceService().generateMonitorBlob();
        UploadContent uploadContent = new UploadContent((byte[]) generateMonitorBlob.get("content"), (byte[]) generateMonitorBlob.get("key"), ((Boolean) generateMonitorBlob.get(ToygerService.KEY_RES_9_IS_UTF8)).booleanValue());
        BioLog.i("getNineShoot DONE");
        return uploadContent;
    }

    public NineShootManager getNineShootManager() {
        return this.mNineShootManager;
    }

    public void destroy() {
        if (this.mNineShootManager != null) {
            this.mNineShootManager.destroy();
        }
    }
}

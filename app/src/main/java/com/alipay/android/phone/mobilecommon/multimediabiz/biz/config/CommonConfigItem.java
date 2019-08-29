package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config;

import android.os.Build.VERSION;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.Cache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.CameraStarupConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DiskConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DjangoConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.GrayscaleConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.HttpClientConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ImageProcessorConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LiveConfigItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LocalIdConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LogConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.Net;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ProgConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.ProgressiveConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.SecurityConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.TaskConfig;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.VideoConfigItem;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.VideoEditorConf;

public class CommonConfigItem {
    public static final int OP_TYPE_IMAGE_ID = 2;
    public static final int OP_TYPE_THUMB = 1;
    @JSONField(name = "allowDlSpace")
    public int allowDownloadSpace = 1;
    @JSONField(name = "bt")
    public int beautyType = 0;
    @JSONField(name = "cache")
    public Cache cache = new Cache();
    @JSONField(name = "cbbf")
    public int cacheBytesByFile = 1;
    @JSONField(name = "copt")
    public CameraStarupConf cameraStarupConf = new CameraStarupConf();
    @JSONField(name = "cafts")
    public int checkAftsId = 0;
    @JSONField(name = "cas")
    public int checkAudioSyncMd5 = 1;
    @JSONField(name = "ciif")
    public int cleanInvalidImageFile = 1;
    @JSONField(name = "ddot")
    public int[] decodeDirectionOpTypes = {1, 2};
    @JSONField(name = "df")
    public DiskConf diskConf = new DiskConf();
    @JSONField(name = "dj")
    public DjangoConf djangoConf = new DjangoConf();
    @JSONField(name = "bbcc")
    public int enableBitmapBytesCountCheck = 1;
    @JSONField(name = "ecfe")
    public int enableCutForEncrypt = 1;
    @JSONField(name = "hevc")
    public int enableHevc = 1;
    @JSONField(name = "preAcquirePermissions")
    public int enablePreAcquirePermissions = 1;
    @JSONField(name = "rraf")
    public int enableRecordingRequestAudioFocus = 1;
    @JSONField(name = "fusl")
    public int fileUpSizeLimit = 20;
    @JSONField(name = "fscm")
    public int fixSmartCropMode = 1;
    @JSONField(name = "fwDispatch")
    public int frameworkDispatchThreadPoolSwitch = 0;
    @JSONField(name = "gray")
    public GrayscaleConfig grayConf = new GrayscaleConfig();
    @JSONField(name = "hc")
    public HttpClientConf httpClientConf = new HttpClientConf();
    @JSONField(name = "ipc")
    public ImageProcessorConf imageProcessorConf = new ImageProcessorConf();
    @JSONField(name = "live")
    public LiveConfigItem liveConf = new LiveConfigItem();
    @JSONField(name = "lqds")
    public int loacalIdQueryDbSwitch = 1;
    @JSONField(name = "dl")
    public int loadDiskLog = 0;
    @JSONField(name = "ldl")
    public int loadLocalDiskLog = 1;
    @JSONField(name = "locic")
    public LocalIdConfig localIdConfig = null;
    @JSONField(name = "lsc")
    public int localSmartCrop = 1;
    @JSONField(name = "log")
    public LogConf logConf = new LogConf();
    @JSONField(name = "md5s")
    public int md5CheckSwitch = 1;
    @JSONField(name = "net")
    public Net net = new Net();
    @JSONField(name = "niss")
    public int newImageSizeSwitch = 1;
    @JSONField(name = "prog")
    public ProgConfig progConf = new ProgConfig();
    @JSONField(name = "pgs")
    public ProgressiveConfig progressiveConfig = new ProgressiveConfig();
    @JSONField(name = "rc")
    public int regionCrop = 1;
    @JSONField(name = "rdcv")
    public int resDpiChangeVer = 18;
    @JSONField(name = "svt")
    public int saveVideoThumb = 0;
    @JSONField(name = "secc")
    public SecurityConf securityConf = new SecurityConf();
    @JSONField(name = "sc")
    public int smartCrop = 1;
    @JSONField(name = "tpnc")
    public int takePictureUseNativeCompress = 0;
    @JSONField(name = "task")
    public TaskConfig taskConf = new TaskConfig();
    @JSONField(name = "tncs")
    public int taskNetCheckSwitch = 1;
    @JSONField(name = "tis")
    public int thumbMediaIdSwitch = getDefaultMediaIdVal();
    @JSONField(name = "ts")
    public int thumbnailSwitch = 1;
    @JSONField(name = "uas")
    public int useAudioSync = 1;
    @JSONField(name = "udcc")
    public int useDoubleCheckCache = 1;
    @JSONField(name = "unrc")
    public int useNewReuseCheck = 1;
    @JSONField(name = "video")
    public VideoConfigItem videoConf = new VideoConfigItem();
    @JSONField(name = "vec")
    public VideoEditorConf videoEditorConf = new VideoEditorConf();
    @JSONField(name = "vusl")
    public int videoUpSizeLimit = 20;

    public boolean useThumbnaiId() {
        return 1 == this.thumbMediaIdSwitch;
    }

    public boolean inDecodeDirectionOpTypes(int type) {
        for (int i : this.decodeDirectionOpTypes) {
            if (type == i) {
                return true;
            }
        }
        return false;
    }

    public boolean useThumbnai() {
        return 1 == this.thumbnailSwitch;
    }

    public boolean saveVideoThumb() {
        return 1 == this.saveVideoThumb;
    }

    public int getDefaultMediaIdVal() {
        return VERSION.SDK_INT < 21 ? 0 : 1;
    }

    public String toString() {
        return "CommonConfigItem{cache=" + this.cache + ", net=" + this.net + ", hc=" + this.httpClientConf + ", diskConf=" + this.diskConf + ", djangoConf=" + this.djangoConf + ", logConf=" + this.logConf + ", liveConf=" + this.liveConf + ", videoConf=" + this.videoConf + ", progConf=" + this.progConf + ", taskConf=" + this.taskConf + ", grayConf=" + this.grayConf + ", progressiveConfig=" + this.progressiveConfig + ", frameworkDispatchThreadPoolSwitch=" + this.frameworkDispatchThreadPoolSwitch + ", allowDownloadSpace=" + this.allowDownloadSpace + ", enablePreAcquirePermissions=" + this.enablePreAcquirePermissions + ", useNewReuseCheck=" + this.useNewReuseCheck + ", beautyType=" + this.beautyType + ", regionCrop=" + this.regionCrop + ", smartCrop=" + this.smartCrop + ", localSmartCrop=" + this.localSmartCrop + ", checkAudioSyncMd5=" + this.checkAudioSyncMd5 + ", useAudioSync=" + this.useAudioSync + ", useDoubleCheckCache=" + this.useDoubleCheckCache + ", enableRecordingRequestAudioFocus=" + this.enableRecordingRequestAudioFocus + ", fileUpSizeLimit=" + this.fileUpSizeLimit + ", videoUpSizeLimit=" + this.videoUpSizeLimit + ", loadDiskLog=" + this.loadDiskLog + ", loadLocalDiskLog=" + this.loadLocalDiskLog + ", fixSmartCropMode=" + this.fixSmartCropMode + ", cleanInvalidImageFile=" + this.cleanInvalidImageFile + ", md5CheckSwitch=" + this.md5CheckSwitch + ", localIdConfig=" + this.localIdConfig + ", taskNetCheckSwitch=" + this.taskNetCheckSwitch + ", takePictureUseNativeCompress=" + this.takePictureUseNativeCompress + ", videoEditorConf=" + this.videoEditorConf + ", securityConf=" + this.securityConf + ", resDpiChangeVer=" + this.resDpiChangeVer + ", imageProcessorConf=" + this.imageProcessorConf + ", cameraStarupConf=" + this.cameraStarupConf + ", checkAftsId=" + this.checkAftsId + ", enableHevc=" + this.enableHevc + ", enableBitmapBytesCountCheck=" + this.enableBitmapBytesCountCheck + ", enableCutForEncrypt=" + this.enableCutForEncrypt + ", newImageSizeSwitch=" + this.newImageSizeSwitch + ", loacalIdQueryDbSwitch=" + this.loacalIdQueryDbSwitch + ", pgs=" + this.progressiveConfig + ", fwDispatch=" + this.frameworkDispatchThreadPoolSwitch + ", allowDlSpace=" + this.allowDownloadSpace + ", preAcquirePermissions=" + this.enablePreAcquirePermissions + ", unrc=" + this.useNewReuseCheck + ", bt=" + this.beautyType + ", rc=" + this.regionCrop + ", sc=" + this.smartCrop + ", lsc=" + this.localSmartCrop + ", cas=" + this.checkAudioSyncMd5 + ", uas=" + this.useAudioSync + ", udcc=" + this.useDoubleCheckCache + ", rraf=" + this.enableRecordingRequestAudioFocus + ", fusl=" + this.fileUpSizeLimit + ", vusl=" + this.videoUpSizeLimit + ", dl=" + this.loadDiskLog + ", ldl=" + this.loadLocalDiskLog + ", fscm=" + this.fixSmartCropMode + ", ciif=" + this.cleanInvalidImageFile + ", md5s=" + this.md5CheckSwitch + ", locic=" + this.localIdConfig + ", tncs=" + this.taskNetCheckSwitch + ", tpnc=" + this.takePictureUseNativeCompress + ", vec=" + this.videoEditorConf + ", secc=" + this.securityConf + ", rdcv=" + this.resDpiChangeVer + ", ipc=" + this.imageProcessorConf + ", copt=" + this.cameraStarupConf + ", cafts=" + this.checkAftsId + ", hevc=" + this.enableHevc + ", bbcc=" + this.enableBitmapBytesCountCheck + ", ecfe=" + this.enableCutForEncrypt + ", niss=" + this.newImageSizeSwitch + ", lqds=" + this.loacalIdQueryDbSwitch + ", cbbf=" + this.cacheBytesByFile + ", ts=" + this.thumbnailSwitch + '}';
    }
}

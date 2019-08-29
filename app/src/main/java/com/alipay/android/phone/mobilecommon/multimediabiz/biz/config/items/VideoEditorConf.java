package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.DeviceWrapper;
import com.alipay.streammedia.video.editor.NativeVideoEditor;

public class VideoEditorConf {
    @JSONField(name = "dcmcdl")
    public String disableCompressMediaCodecDeviceList = null;
    @JSONField(name = "dmcdl")
    public String disableMediaCodecDeviceList = "";
    @JSONField(name = "eac")
    public int enableAudioCopy = 1;
    @JSONField(name = "ecmc")
    public int enableCompressMediaCodec = 1;
    @JSONField(name = "emc")
    public int enableMediaCodec = 1;
    @JSONField(name = "sf")
    public int skipFrame = 0;
    @JSONField(name = "vcs")
    public int videoCompressStrategy = 1;

    public boolean isEnableMediaCodec() {
        if (this.enableMediaCodec != 1 || DeviceWrapper.isMatchDevice(this.disableMediaCodecDeviceList) || !NativeVideoEditor.isSupportMeidaCodec(getMediaCodecBlackList())) {
            return false;
        }
        return true;
    }

    public boolean isEnableCompressMediaCodec() {
        if (this.enableCompressMediaCodec != 1 || (this.disableCompressMediaCodecDeviceList == null ? DeviceWrapper.isMatchDevice(this.disableMediaCodecDeviceList) : DeviceWrapper.isMatchDevice(this.disableCompressMediaCodecDeviceList)) || !NativeVideoEditor.isSupportMeidaCodec(getMediaCodecBlackList())) {
            return false;
        }
        return true;
    }

    private String getMediaCodecBlackList() {
        return ConfigManager.getInstance().getStringValue(ConfigConstants.MULTIMEDIA_DISABLE_MEDIA_CODEC_LIST, "");
    }

    public String toString() {
        return "VideoEditorConf{skipFrame=" + this.skipFrame + ", enableCompressMediaCodec=" + this.enableCompressMediaCodec + ", disableCompressMediaCodecDeviceList='" + this.disableCompressMediaCodecDeviceList + '\'' + ", enableMediaCodec=" + this.enableMediaCodec + ", disableMediaCodecDeviceList='" + this.disableMediaCodecDeviceList + '\'' + ", videoCompressStrategy=" + this.videoCompressStrategy + ", enableAudioCopy=" + this.enableAudioCopy + '}';
    }
}

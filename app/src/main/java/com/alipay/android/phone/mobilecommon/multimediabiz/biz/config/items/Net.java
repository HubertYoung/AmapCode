package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioOptions;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.Arrays;

public class Net {
    @JSONField(name = "aahs")
    public int allApiHttpsSwitch = 0;
    @JSONField(name = "adhs")
    public int allDownHttpsSwitch = 0;
    @JSONField(name = "ahs")
    public int allHttpsSwitch = 0;
    @JSONField(name = "auhs")
    public int allUpHttpsSwitch = 0;
    @JSONField(name = "apihttpshost")
    public String apiHttpsHost = "api-mayi.django.t.taobao.com";
    @JSONField(name = "blackips")
    public String[] blackIps;
    @JSONField(name = "ccs")
    public int checkContentSwitch = 1;
    @JSONField(name = "ctkey")
    public String contentTypeKey = PoiLayoutTemplate.HTML;
    @JSONField(name = "djghttps")
    public int djgMgrHttps = 0;
    @JSONField(name = "dlhttpshost")
    public String dlHttpsHost = "oalipay-dl-django.alicdn.com";
    @JSONField(name = "dlscodeswitch")
    public int dlserviceCodeSwitch = 1;
    @JSONField(name = "dfdt")
    public int dsFileDownloadTimeOut = 300000;
    @JSONField(name = "didt")
    public int dsImageDownloadTimeOut = AudioOptions.MAX_DURATION;
    @JSONField(name = "expswitch")
    public int expswitch = 1;
    @JSONField(name = "httpsswitch")
    public int httpsSwitch = 1;
    @JSONField(name = "iptimeout")
    public int ipTimeout = 300000;
    @JSONField(name = "nfdt")
    public int nbnetFileDownloadTimeOut = 120000;
    @JSONField(name = "nidt")
    public int nbnetImageDownloadTimeOut = 120000;
    @JSONField(name = "ndm")
    public int newDomain = 1;
    @JSONField(name = "newhttpswitch")
    public int newHttpsSwitch = 1;
    @JSONField(name = "ping")
    public int ping = 0;
    @JSONField(name = "pdhttpswitch")
    public int predownHttpsSwitch = 1;
    @JSONField(name = "uphttpshost")
    public String upHttpsHost = "up-mayi.django.t.taobao.com";

    public String toString() {
        return "Net{, blackIps=" + Arrays.toString(this.blackIps) + ", ipTimeout=" + this.ipTimeout + ", ping=" + this.ping + ", dlHttpsHost=" + this.dlHttpsHost + ", apiHttpsHost=" + this.apiHttpsHost + ", upHttpsHost=" + this.upHttpsHost + ", httpsSwitch=" + this.httpsSwitch + ", expswitch=" + this.expswitch + ", contentTypeKey=" + this.contentTypeKey + ", djgMgrHttps=" + this.djgMgrHttps + ", dlserviceCodeSwitch=" + this.dlserviceCodeSwitch + ", predownHttpsSwitch=" + this.predownHttpsSwitch + ", allHttpsSwitch=" + this.allHttpsSwitch + ", newDomain=" + this.newDomain + ", checkContentSwitch=" + this.checkContentSwitch + ", allDownHttpsSwitch=" + this.allDownHttpsSwitch + ", allUpHttpsSwitch=" + this.allUpHttpsSwitch + ", allApiHttpsSwitch=" + this.allApiHttpsSwitch + ", nbnetImageDownloadTimeOut=" + this.nbnetImageDownloadTimeOut + ", nbnetFileDownloadTimeOut=" + this.nbnetFileDownloadTimeOut + ", dsImageDownloadTimeOut=" + this.dsImageDownloadTimeOut + ", dsFileDownloadTimeOut=" + this.dsFileDownloadTimeOut + '}';
    }
}

package com.alipay.multimedia.adjuster.config;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;

public class CdnConfigItem extends BaseConfig {
    @JSONField(name = "ach")
    public String aftsCdnHost = "https://mdn.alipayobjects.com";
    @JSONField(name = "acp")
    public String[] aftsCdnPrefixs = {"mdn.alipayobjects.com", "gw.alipayobjects.com/mdn"};
    @JSONField(name = "amh")
    public String aftsMasterHost = "https://mdgw.alipay.com";
    @JSONField(name = "chl")
    public int[] cdnHeightListOf10000Width = ConfigConst.CDN_HEIGHT_OF_10000_WIDTH;
    @JSONField(name = "cisl")
    public int[] cdnImageSizeList = ConfigConst.CDN_IMAGE_SIZE;
    @JSONField(name = "cwl")
    public int[] cdnWidthListOf10000Height = ConfigConst.CDN_WIDTH_OF_10000_HEIGHT;
    @JSONField(name = "cxzisl")
    public int[] cdnXZImageSizeList = ConfigConst.CDN_XZ_IMAGE_SIZE;
    @JSONField(name = "hab")
    public String[] highAvailabilityBizs = {"NebulaMD"};
    @JSONField(name = "hah")
    public HostItem[] highAvailabilityHost = genDefaultHAHostItems();
    @JSONField(name = "qv")
    public int mQuality = 80;
    @JSONField(name = "sc")
    public int mScreenScale = 1;
    @JSONField(name = "sv")
    public int mSharpValue = 0;
    @JSONField(name = "webp")
    public int mSupportWebp = genDefaultWebp();
    @JSONField(name = "odebl")
    public String[] ossCdnDomainExactBlackList = null;
    @JSONField(name = "odfbl")
    public String[] ossCdnDomainFuzzyBlackList = null;
    @JSONField(name = "odl")
    public String[] ossCdnDomainList = ConfigConst.OSS_DOMAIN_WHITE_LIST;
    @JSONField(name = "tdebl")
    public String[] tfsCdnDomainExactBlackList = null;
    @JSONField(name = "tdfbl")
    public String[] tfsCdnDomainFuzzyBlackList = null;
    @JSONField(name = "tdl")
    public String[] tfsCdnDomainList = ConfigConst.TFS_DOMAIN_WHITE_LIST;
    @JSONField(name = "tcisr")
    public String tfsCdnParseImageSizeRegex = ConfigConst.TFS_CDN_PARSE_IMAGE_SIZE_REGEX;
    @JSONField(name = "uopisr")
    public int useOldCdnParseImageSizeRegex = 0;

    private int genDefaultWebp() {
        return VERSION.SDK_INT >= 28 ? 0 : 1;
    }

    public boolean useOldCdnParseImageSizeRegex() {
        return this.useOldCdnParseImageSizeRegex == 1;
    }

    public boolean isSupportWebp() {
        return this.mSupportWebp == 1;
    }

    private HostItem[] genDefaultHAHostItems() {
        return new HostItem[]{new HostItem("tfs.alipayobjects.com", 100), new HostItem("zos.alipayobjects.com", 100), new HostItem("gw.alipayobjects.com/tfs/", 100), new HostItem("gw.alipayobjects.com/zos/", 100)};
    }

    public HostItem checkHighAvailability(String url, String bizType) {
        String[] strArr;
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(bizType) || this.highAvailabilityBizs == null || this.highAvailabilityBizs.length <= 0 || this.highAvailabilityHost == null || this.highAvailabilityHost.length <= 0) {
            return null;
        }
        HostItem[] hostItemArr = this.highAvailabilityHost;
        int length = hostItemArr.length;
        for (int i = 0; i < length; i++) {
            HostItem item = hostItemArr[i];
            if (url.contains(item.host)) {
                for (String biz : this.highAvailabilityBizs) {
                    if ("all".equalsIgnoreCase(biz) || bizType.startsWith(biz)) {
                        return item;
                    }
                }
                continue;
            }
        }
        return null;
    }
}

package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.File;

public class AftsLinkHelper {
    public static String genDlImageAftsUrl(String fileId, String zoom, String bizType, APImageMarkRequest mk) {
        if (!PathUtils.isDjangoPath(fileId)) {
            Logger.D("AftsLinkHelper", "genDlImageAftsUrl fileid=" + fileId + "; is not id", new Object[0]);
            return fileId;
        }
        String zoom1 = zoom;
        String zoom2 = "";
        if (!TextUtils.isEmpty(zoom) && zoom.contains("&")) {
            zoom1 = zoom.substring(0, zoom.indexOf("&"));
            zoom2 = zoom.substring(zoom.indexOf("&"), zoom.length());
        }
        if (mk != null) {
            Object[] objArr = new Object[14];
            objArr[0] = a();
            objArr[1] = b();
            objArr[2] = "/afts/imgmk";
            objArr[3] = fileId;
            objArr[4] = TextUtils.isEmpty(zoom1) ? "" : File.separator + zoom1;
            if (TextUtils.isEmpty(bizType)) {
                bizType = "mdn-biz";
            }
            objArr[5] = bizType;
            objArr[6] = zoom2;
            objArr[7] = mk.getPosition();
            objArr[8] = mk.getTransparency();
            objArr[9] = mk.getPaddingX();
            objArr[10] = mk.getPaddingY();
            objArr[11] = mk.getPercent();
            objArr[12] = "";
            objArr[13] = mk.getMarkId();
            String aftsUrl = String.format("%s%s%s/%s%s?bz=%s%s&mk=i,%s,%s,%s,%s,%s,%s,%s", objArr);
            Logger.D("AftsLinkHelper", "genDlImageAftsUrl mk aftsUrl=" + aftsUrl, new Object[0]);
            return aftsUrl;
        }
        Object[] objArr2 = new Object[7];
        objArr2[0] = a();
        objArr2[1] = b();
        objArr2[2] = "/afts/img";
        objArr2[3] = fileId;
        objArr2[4] = TextUtils.isEmpty(zoom1) ? "" : File.separator + zoom1;
        if (TextUtils.isEmpty(bizType)) {
            bizType = "mdn-biz";
        }
        objArr2[5] = bizType;
        objArr2[6] = zoom2;
        String aftsUrl2 = String.format("%s%s%s/%s%s?bz=%s%s", objArr2);
        Logger.P("AftsLinkHelper", "genDlImageAftsUrl aftsUrl=" + aftsUrl2, new Object[0]);
        return aftsUrl2;
    }

    private static String a() {
        return ConfigManager.getInstance().getAftsLinkConf().imageDlHttpSwitch == 1 ? AjxHttpLoader.DOMAIN_HTTP : AjxHttpLoader.DOMAIN_HTTPS;
    }

    public static String genFileDlAftsUrl(String fileId, String bizType) {
        if (!PathUtils.isDjangoPath(fileId)) {
            Logger.D("AftsLinkHelper", "genFileDlAftsUrl fileid=" + fileId + "; is not id", new Object[0]);
            return fileId;
        }
        Object[] objArr = new Object[5];
        objArr[0] = ConfigManager.getInstance().getAftsLinkConf().fileDlHttpSwitch == 1 ? AjxHttpLoader.DOMAIN_HTTP : AjxHttpLoader.DOMAIN_HTTPS;
        objArr[1] = b();
        objArr[2] = "/afts/file";
        objArr[3] = fileId;
        if (TextUtils.isEmpty(bizType)) {
            bizType = "mdn-biz";
        }
        objArr[4] = bizType;
        String aftsUrl = String.format("%s%s%s/%s?bz=%s", objArr);
        Logger.P("AftsLinkHelper", "genFileDlAftsUrl aftsUrl=" + aftsUrl, new Object[0]);
        return aftsUrl;
    }

    private static String b() {
        if (EnvSwitcher.isOnlineEnv()) {
            return ConfigManager.getInstance().getAftsLinkConf().onlineDomain;
        }
        return "mdn.alipayobjects.com";
    }
}

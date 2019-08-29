package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageMarkRequest;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req.ThumbnailMarkDownReq;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.pb.MMDPImageMarkParam;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class MarkUtil {
    public static boolean isValidMarkOption(DisplayImageOptions options) {
        if (options != null) {
            return isValidMarkRequest(options.getImageMarkRequest());
        }
        throw new RuntimeException("isValidMarkRequest " + "options cannot be null");
    }

    public static boolean isValidMarkRequest(APImageMarkRequest req) {
        boolean ret = false;
        String err = "";
        if (req == null) {
            err = "APImageMarkRequest cannot be null";
        } else if (TextUtils.isEmpty(req.getMarkId())) {
            err = "mark id cannot be null";
        } else if (req.getPosition() == null || req.getPosition().intValue() <= 0 || req.getPosition().intValue() > 9) {
            err = "position must between 1 and 9";
        } else if (req.getTransparency() == null || req.getTransparency().intValue() <= 0 || req.getPosition().intValue() > 100) {
            err = "transparency must between 1 and 100";
        } else if (req.getMarkWidth() != null && req.getMarkWidth().intValue() < 0) {
            err = "mark width must big or equal to 0";
        } else if (req.getMarkHeight() != null && req.getMarkHeight().intValue() < 0) {
            err = "mark height must big or equal to 0";
        } else if (req.getPaddingX() == null) {
            err = "mark padding x must be set value";
        } else if (req.getPaddingY() == null) {
            err = "mark padding y must be set value";
        } else if (req.getPercent() != null && (req.getPercent().intValue() <= 0 || req.getPercent().intValue() > 100)) {
            err = "mark percent must be null or (0,100]";
        } else if (req.getPercent() == null && (req.getMarkWidth() == null || req.getMarkHeight() == null)) {
            err = "mark must have percent or width&height";
        } else {
            ret = true;
        }
        Logger.P("MarkUtil", "isValidMarkRequest " + ret + Token.SEPARATOR + err, new Object[0]);
        if (ret) {
            return ret;
        }
        throw new RuntimeException("isValidMarkRequest " + err);
    }

    public static void fillMarkParams(ThumbnailMarkDownReq downReq, APImageMarkRequest markRequest) {
        downReq.setMarkId(markRequest.getMarkId());
        downReq.setMarkHeight(markRequest.getMarkHeight().intValue());
        downReq.setMarkWidth(markRequest.getMarkWidth().intValue());
        downReq.setPosition(markRequest.getPosition().intValue());
        downReq.setTransparency(markRequest.getTransparency().intValue());
        downReq.setPaddingX(markRequest.getPaddingX());
        downReq.setPaddingY(markRequest.getPaddingY());
        downReq.setPercent(markRequest.getPercent());
    }

    public static void fillMarkParams(MMDPImageMarkParam downReq, APImageMarkRequest markRequest) {
        downReq.fileid = markRequest.getMarkId();
        downReq.height = markRequest.getMarkHeight();
        downReq.width = markRequest.getMarkWidth();
        downReq.position = markRequest.getPosition();
        downReq.transparency = markRequest.getTransparency();
        downReq.x = markRequest.getPaddingX();
        downReq.y = markRequest.getPaddingY();
        downReq.pwh = markRequest.getPercent();
    }
}

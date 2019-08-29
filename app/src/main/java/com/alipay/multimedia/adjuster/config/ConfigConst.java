package com.alipay.multimedia.adjuster.config;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.antui.clickspan.BaseClickableSpan;
import com.alipay.zoloz.toyger.bean.Config;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import tv.danmaku.ijk.media.encode.VideoRecordParameters;

public class ConfigConst {
    public static final int[] CDN_HEIGHT_OF_10000_WIDTH = {170, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 340, 500};
    public static final int[] CDN_IMAGE_SIZE = {16, 20, 24, 30, 32, 36, 40, 48, 50, 60, 64, 70, 72, 80, 88, 90, 100, 110, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 125, 128, 130, 145, 160, 170, 180, 190, 200, 210, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 230, 234, 240, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 270, 290, 300, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 315, 320, 336, BaseClickableSpan.CLICK_ENABLE_TIME, 360, 400, 430, 460, 468, Config.HQ_IMAGE_WIDTH, 490, VideoRecordParameters.HD_HEIGHT_16_9, 560, 570, 580, 600, 640, 670, 720, 728, 760, 960, 970};
    public static final int[] CDN_WIDTH_OF_10000_HEIGHT = {110, 150, 170, AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST, 240, 290, 450, 570, 580, 620, 790};
    public static final int[] CDN_XZ_IMAGE_SIZE = {72, 88, 90, 100, 110, MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_, 145, 160, 170, 180, 200, 230, 240, 270, 290, SecExceptionCode.SEC_ERROR_STA_INVALID_ENCRYPTED_DATA, 320, 360, 430, 460, 580, 640};
    public static final String CONFIG_KEY_CDN = "APM_ALI_CDN";
    public static final String[] OSS_DOMAIN_WHITE_LIST = {"/zos.alipayobjects.com", "/os.alipayobjects.com", "/gw.alipayobjects.com/os/", "/gw.alipayobjects.com/zos/"};
    public static final String OSS_ZOOM_REGEX = "@(?:(?:_?(\\d+)w[_\\.])|(?:_?(\\d+)w$)|(?:_?(\\d+)h)|(?:_?(\\d+)[Qq])|(?:_?[^_.]+))+";
    public static final String TFS_CDN_PARSE_IMAGE_SIZE_REGEX = "(\\S*)(_(\\d+)[xX](\\d+)?(?:[Qq](\\d{2})|s(\\d{2,3})|xc|xz|g|co0|c[xy]\\d+i\\d){0,}(?:$|\\.jpeg|\\.jpg|_\\.webp|\\?))";
    public static final String[] TFS_DOMAIN_WHITE_LIST = {"/t.alipayobjects.com", "/tfs.alipayobjects.com", "/img.alicdn.com", "/gw.alipayobjects.com/tfs", "/gw.alicdn.com", "/img03.taobaocdn.com"};
    public static final String TFS_ZOOM_REGEX = "_(?:(?:\\.webp)|((?:(?:(\\d+)x(\\d+)(?:xz)?)|(?:q\\d{2})|(?:s\\d{3})){1,3}(?:\\.jpg)?(_\\.webp)?))";
    public static final String TFS_ZOOM_WH_REGEX = "_(\\d+)x(\\d+).*";
}

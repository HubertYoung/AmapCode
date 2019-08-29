package com.alipay.mobile.beehive.photo.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.alipay.android.phone.falcon.falconimg.layout.PhotoDetail;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.iconfont.util.IconUtils;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.app.ui.ActivityResponsable;
import com.alipay.mobile.framework.service.MicroService;
import java.util.List;

public class CommonUtils {
    private static final String FILE_PREFIX = "file://";

    public static PhotoInfo covertMediaInfo2PhotoInfo(MediaInfo mediaInfo) {
        PhotoInfo ret = new PhotoInfo(mediaInfo.path);
        ret.setPhotoHeight(mediaInfo.heightPx);
        ret.setPhotoWidth(mediaInfo.widthPx);
        ret.oriPhotoSize = new Size(mediaInfo.widthPx, mediaInfo.heightPx);
        ret.setMediaType(mediaInfo.mediaType == 0 ? 0 : 1);
        ret.setVideoDuration(mediaInfo.durationMs);
        return ret;
    }

    public static PhotoDetail covertPhotoInfo2PhotoDetail(PhotoInfo src) {
        PhotoDetail ret = new PhotoDetail();
        ret.height = src.getPhotoHeight();
        ret.width = src.getPhotoWidth();
        return ret;
    }

    public static <T extends MicroService> T serviceHolder(Class<T> clazz, T holder) {
        if (holder == null) {
            return MicroServiceUtil.getMicroService(clazz);
        }
        return holder;
    }

    public static void alipayToast(Context context, int strId, int duration) {
        if (context instanceof ActivityResponsable) {
            ((ActivityResponsable) context).toast(context.getString(strId), duration);
        }
    }

    public static String removeFilePrefix(String url) {
        String ret = url;
        if (TextUtils.isEmpty(url) || !url.startsWith("file://")) {
            return ret;
        }
        return url.replace("file://", "");
    }

    public static void GifDebugger(String msg) {
        Log.d("ZIV_GIF_DEBUGGER", msg);
    }

    public static void alipaySingleAlert(Context context, String msg, String positive) {
        if (context instanceof ActivityResponsable) {
            ((ActivityResponsable) context).alert("", msg, positive, null, "", null);
        }
    }

    public static void setTitleBarBackDrawable(ImageView backBtn) {
        if (backBtn != null) {
            backBtn.setImageDrawable(IconUtils.getTitleIcon_White(backBtn.getContext(), R.string.iconfont_back));
        }
    }

    public static boolean isIndexValidInList(List list, int index) {
        if (index < 0 || list == null || list.isEmpty()) {
            return false;
        }
        return list.size() > index;
    }
}

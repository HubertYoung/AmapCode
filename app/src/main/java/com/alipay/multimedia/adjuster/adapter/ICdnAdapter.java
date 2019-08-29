package com.alipay.multimedia.adjuster.adapter;

import com.alipay.multimedia.adjuster.data.APMImageInfo.CutType;
import com.alipay.multimedia.adjuster.data.APMImageInfo.Format;
import com.alipay.multimedia.adjuster.data.UrlInfo;
import com.alipay.multimedia.adjuster.data.UrlInfo.Size;
import java.util.Map;

public interface ICdnAdapter {
    String adapterCdnZoomResult(String str, Format format, CutType cutType, int i, int i2, int i3, int i4, Map map);

    Size adjustImageSize(String str, Size size, Size size2, CutType cutType);

    boolean canExecAdapterForUrl(String str);

    UrlInfo getBaseUrlAndImageSize(String str);

    boolean hasNotSupportCdnRule(String str, Size size, CutType cutType);
}

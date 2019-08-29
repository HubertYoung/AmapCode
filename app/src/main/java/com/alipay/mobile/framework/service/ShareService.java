package com.alipay.mobile.framework.service;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import com.alipay.mobile.common.share.AuthWeiboListener;
import com.alipay.mobile.common.share.ContactShareListener;
import com.alipay.mobile.common.share.GetWeiboUserPicListener;
import com.alipay.mobile.common.share.GetWeixinUserPicListener;
import com.alipay.mobile.common.share.GroupShareListener;
import com.alipay.mobile.common.share.ShareContent;
import com.alipay.mobile.common.share.ShareException;
import com.alipay.mobile.common.share.ShareTokenListener;
import com.alipay.mobile.common.share.TencentFilterListener;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class ShareService extends ExternalService {
    public static final int WEIBO_IMG_HD = 3;
    public static final int WEIBO_IMG_LOW = 1;
    public static final int WEIBO_IMG_MID = 2;

    public interface ShareActionListener {
        void onComplete(int i);

        void onException(int i, ShareException shareException);
    }

    public abstract void AuthWeibo(AuthWeiboListener authWeiboListener);

    public abstract void AuthWeixin();

    public abstract AuthWeiboListener getAuthWeiboListener();

    public abstract ContactShareListener getContactShareListener();

    public abstract GroupShareListener getGroupShareListener();

    public abstract ShareActionListener getShareActionListener();

    public abstract ShareTokenListener getShareTokenListener();

    public abstract TencentFilterListener getTencentFilterListener();

    public abstract void getWeiboUserPic(String str, String str2, int i, GetWeiboUserPicListener getWeiboUserPicListener);

    public abstract void getWeixinUserPic(String str);

    public abstract GetWeixinUserPicListener getWeixinUserPicListener();

    public abstract void initAlipayContact(String str);

    public abstract void initDingDing(String str);

    public abstract void initLaiwang(String str, String str2);

    public abstract void initQQ(String str);

    public abstract void initQZone(String str);

    public abstract void initWeiBo(String str, String str2, String str3);

    public abstract void initWeixin(String str, String str2);

    public abstract boolean isChannelClientInstalled(int i);

    public abstract boolean isDingDingInstalled(Context context);

    public abstract boolean isDingDingSupported(Context context);

    public abstract void setAppName(String str);

    public abstract void setContactShareListener(ContactShareListener contactShareListener);

    public abstract void setGroupShareListener(GroupShareListener groupShareListener);

    public abstract void setShareActionListener(ShareActionListener shareActionListener);

    public abstract void setShareTokenListener(ShareTokenListener shareTokenListener);

    public abstract void setTencentFilterListener(TencentFilterListener tencentFilterListener);

    public abstract void setWeixinUserPicListener(GetWeixinUserPicListener getWeixinUserPicListener);

    public abstract boolean shareMMFriendsByIntent(Activity activity, Uri uri, String str);

    public abstract boolean shareMMTimelineByIntent(Activity activity, Uri uri, String str);

    public abstract void silentShare(ShareContent shareContent, int i, String str);

    public abstract void unRegisterAuthWeiboListener();
}

package com.sina.weibo.sdk.share;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.WbAppInfo;
import com.sina.weibo.sdk.utils.FileUtils;
import com.sina.weibo.sdk.utils.ImageUtils;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class CopyResourceTask extends AsyncTask<WeiboMultiMessage, Object, TransResourceResult> {
    private TransResourceCallback mCallback;
    private WeakReference<Context> mReference;

    public CopyResourceTask(Context context, TransResourceCallback transResourceCallback) {
        this.mReference = new WeakReference<>(context);
        this.mCallback = transResourceCallback;
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
        super.onPreExecute();
    }

    /* access modifiers changed from: protected */
    public TransResourceResult doInBackground(WeiboMultiMessage... weiboMultiMessageArr) {
        Context context = (Context) this.mReference.get();
        if (context == null) {
            return null;
        }
        WeiboMultiMessage weiboMultiMessage = weiboMultiMessageArr[0];
        TransResourceResult transResourceResult = new TransResourceResult();
        try {
            if (WbSdk.isWbInstall(context)) {
                WbAppInfo queryWbInfoInternal = WeiboAppManager.queryWbInfoInternal(context);
                if (queryWbInfoInternal == null || queryWbInfoInternal.getSupportVersion() < 10772) {
                    weiboMultiMessage.multiImageObject = null;
                    weiboMultiMessage.videoSourceObject = null;
                } else {
                    if (!(weiboMultiMessage.imageObject == null || weiboMultiMessage.multiImageObject == null)) {
                        weiboMultiMessage.imageObject = null;
                    }
                    if (!(weiboMultiMessage.videoSourceObject == null || (weiboMultiMessage.multiImageObject == null && weiboMultiMessage.imageObject == null))) {
                        weiboMultiMessage.multiImageObject = null;
                        weiboMultiMessage.imageObject = null;
                    }
                }
                if (weiboMultiMessage.multiImageObject != null) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<Uri> it = weiboMultiMessage.multiImageObject.getImageList().iterator();
                    while (it.hasNext()) {
                        Uri next = it.next();
                        if (next != null && FileUtils.isImageFile(context, next)) {
                            String copyFileToWeiboTem = ShareUtils.copyFileToWeiboTem(context, next, 1);
                            if (!TextUtils.isEmpty(copyFileToWeiboTem)) {
                                arrayList.add(Uri.fromFile(new File(copyFileToWeiboTem)));
                            }
                        }
                    }
                    weiboMultiMessage.multiImageObject.setImageList(arrayList);
                }
                if (weiboMultiMessage.videoSourceObject != null) {
                    Uri uri = weiboMultiMessage.videoSourceObject.videoPath;
                    if (uri != null && FileUtils.isVideoFile(context, uri)) {
                        String copyFileToWeiboTem2 = ShareUtils.copyFileToWeiboTem(context, uri, 0);
                        weiboMultiMessage.videoSourceObject.videoPath = Uri.fromFile(new File(copyFileToWeiboTem2));
                        weiboMultiMessage.videoSourceObject.during = ImageUtils.getVideoDuring(copyFileToWeiboTem2);
                    }
                }
            }
            transResourceResult.message = weiboMultiMessage;
            transResourceResult.transDone = true;
        } catch (Exception unused) {
            transResourceResult.transDone = false;
        }
        return transResourceResult;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(TransResourceResult transResourceResult) {
        super.onPostExecute(transResourceResult);
        if (this.mCallback != null) {
            this.mCallback.onTransFinish(transResourceResult);
        }
    }
}

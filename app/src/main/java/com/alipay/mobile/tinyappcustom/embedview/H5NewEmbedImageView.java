package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceResponse;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.APImageDownLoadCallback;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.DisplayImageOptions.Builder;
import com.alipay.android.phone.mobilesdk.storage.utils.FileUtils;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.newembedview.H5NewBaseEmbedView;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.mpaas.nebula.NebulaBiz;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class H5NewEmbedImageView extends H5NewBaseEmbedView {
    private final String a = "H5NewEmbedImageView";
    /* access modifiers changed from: private */
    public final Set<File> b = new HashSet();
    /* access modifiers changed from: private */
    public AUImageView c;
    /* access modifiers changed from: private */
    public String d;
    private String e;
    /* access modifiers changed from: private */
    public MultimediaImageService f = ((MultimediaImageService) NebulaBiz.findServiceByInterface(MultimediaImageService.class.getName()));

    private void a() {
        if (this.c == null) {
            this.c = new AUImageView((Context) this.mContext.get());
            this.c.setScaleType(ScaleType.FIT_XY);
            this.c.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (H5NewEmbedImageView.this.mH5Page.get() != null && !TextUtils.isEmpty(H5NewEmbedImageView.this.d)) {
                        H5Bridge h5Bridge = ((H5Page) H5NewEmbedImageView.this.mH5Page.get()).getBridge();
                        if (h5Bridge != null) {
                            JSONObject event = new JSONObject();
                            JSONObject data = new JSONObject();
                            data.put((String) "element", (Object) H5NewEmbedImageView.this.d);
                            event.put((String) "data", (Object) data);
                            h5Bridge.sendToWeb("nbcomponent.image.bindtap", event, null);
                        }
                    }
                }
            });
        }
    }

    public View getView() {
        a();
        H5Log.d("H5NewEmbedImageView", "getView " + this.c.hashCode());
        return this.c;
    }

    public void onEmbedViewAttachedToWebView() {
        H5Log.d("H5NewEmbedImageView", "onEmbedViewAttachedToWebView");
    }

    public void onEmbedViewDetachedFromWebView() {
        H5Log.d("H5NewEmbedImageView", "onEmbedViewDetachedFromWebView");
    }

    public void onEmbedViewDestory() {
        H5Log.d("H5NewEmbedImageView", "onEmbedViewDestory");
        if (this.b.size() > 0) {
            for (File file : this.b) {
                H5Log.d("H5NewEmbedImageView", "delete gif cache file: " + this.b);
                H5FileUtil.delete(file);
            }
        }
    }

    public void onEmbedViewParamChanged() {
        H5Log.d("H5NewEmbedImageView", "onEmbedViewParamChanged");
    }

    public void onEmbedViewVisibilityChanged() {
        H5Log.d("H5NewEmbedImageView", "onEmbedViewVisibilityChanged");
    }

    public void onWebViewResume() {
        H5Log.d("H5NewEmbedImageView", "onWebViewResume");
    }

    public void onWebViewPause() {
        H5Log.d("H5NewEmbedImageView", "onWebViewPause");
    }

    public void onWebViewDestory() {
        H5Log.d("H5NewEmbedImageView", "onWebViewDestory");
    }

    public void onReceivedMessage(String s, JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedImageView", "onReceivedMessage actionType " + s + ", data " + jsonObject.toJSONString());
        }
    }

    public void onReceivedRender(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            if (this.mH5Page.get() != null) {
                this.e = H5Utils.getString(((H5Page) this.mH5Page.get()).getParams(), (String) "appId");
            }
            H5Log.d("H5NewEmbedImageView", "onReceivedRender data " + jsonObject.toJSONString() + Token.SEPARATOR + this.c.hashCode());
            this.c.setBackgroundDrawable(H5EmbedViewUtil.generateBackgroundDrawable(this.c.getContext(), jsonObject, 0));
            String image = jsonObject.getString("src");
            this.d = jsonObject.getString("element");
            if (!TextUtils.isEmpty(image) && this.c != null) {
                String image2 = H5UrlHelper.purifyUrl(image);
                if (!image2.startsWith("http") && !image2.startsWith("./") && !image2.startsWith("/")) {
                    Bitmap bitmap = H5ImageUtil.base64ToBitmap(image2);
                    if (bitmap != null) {
                        this.c.setImageBitmap(bitmap);
                        a(null, this.d);
                        return;
                    }
                    a("something wrong", this.d);
                } else if (!image2.endsWith("gif") || this.f == null) {
                    getComponentResourceDataWithUrl(image2, new ResponseListen() {
                        public void onGetResponse(WebResourceResponse webResourceResponse) {
                            H5Log.d("H5NewEmbedImageView", "onGetResponse");
                            if (webResourceResponse != null) {
                                H5NewEmbedImageView.this.c.setImageBitmap(BitmapFactory.decodeStream(webResourceResponse.getData()));
                                H5NewEmbedImageView.this.a(null, H5NewEmbedImageView.this.d);
                                return;
                            }
                            H5NewEmbedImageView.this.a("something wrong", H5NewEmbedImageView.this.d);
                        }
                    }, (H5Page) this.mH5Page.get());
                } else {
                    H5Log.d("H5NewEmbedImageView", "gif use image service");
                    a(image2);
                }
            }
        }
    }

    private void a(String image) {
        final APImageDownLoadCallback imageCallback = new APImageDownLoadCallback() {
            public void onSucc(APImageDownloadRsp apImageDownloadRsp) {
                H5Log.d("H5NewEmbedImageView", "on success");
                H5NewEmbedImageView.this.a(null, H5NewEmbedImageView.this.d);
            }

            public void onProcess(String s, int i) {
            }

            public void onError(APImageDownloadRsp apImageDownloadRsp, Exception e) {
                H5Log.e("H5NewEmbedImageView", "load gif error", e);
                H5NewEmbedImageView.this.a("something wrong", H5NewEmbedImageView.this.d);
            }
        };
        if (image.startsWith("http")) {
            H5Log.d("H5NewEmbedImageView", "load gif image with http");
            String str = image;
            this.f.loadImage(str, (ImageView) this.c, new Builder().detectedGif(true).build(), imageCallback, (String) NebulaBiz.MULTIMEDIA_IMAGE_BIZ);
            a(null, this.d);
            return;
        }
        final File gifFile = new File(((Context) this.mContext.get()).getCacheDir(), this.e + image.replace(File.separator, "_"));
        if (gifFile.exists()) {
            this.f.loadImage(gifFile.getAbsolutePath(), (ImageView) this.c, new Builder().detectedGif(true).build(), imageCallback, (String) NebulaBiz.MULTIMEDIA_IMAGE_BIZ);
            a(null, this.d);
            return;
        }
        getComponentResourceDataWithUrl(image, new ResponseListen() {
            public void onGetResponse(WebResourceResponse webResourceResponse) {
                H5Log.d("H5NewEmbedImageView", "load gif image with nebula pkg");
                if (webResourceResponse != null) {
                    try {
                        FileUtils.copyFile(new BufferedInputStream(webResourceResponse.getData()), gifFile);
                        H5NewEmbedImageView.this.b.add(gifFile);
                        H5NewEmbedImageView.this.f.loadImage(gifFile.getAbsolutePath(), (ImageView) H5NewEmbedImageView.this.c, new Builder().detectedGif(true).build(), imageCallback, (String) NebulaBiz.MULTIMEDIA_IMAGE_BIZ);
                        H5NewEmbedImageView.this.a(null, H5NewEmbedImageView.this.d);
                    } catch (Throwable t) {
                        H5NewEmbedImageView.this.a("something wrong: " + t, H5NewEmbedImageView.this.d);
                    }
                } else {
                    H5NewEmbedImageView.this.a("something wrong", H5NewEmbedImageView.this.d);
                }
            }
        }, (H5Page) this.mH5Page.get());
    }

    public void onReceivedData(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedImageView", "onReceivedData data " + jsonObject.toJSONString());
        }
    }

    public Bitmap getSnapshot() {
        return null;
    }

    public void triggerPreSnapshot() {
    }

    /* access modifiers changed from: private */
    public void a(final String errorMsg, final String element) {
        this.c.post(new Runnable() {
            public void run() {
                if (H5NewEmbedImageView.this.mH5Page.get() != null && H5NewEmbedImageView.this.c != null) {
                    H5Bridge h5Bridge = ((H5Page) H5NewEmbedImageView.this.mH5Page.get()).getBridge();
                    if (h5Bridge != null) {
                        JSONObject event = new JSONObject();
                        JSONObject data = new JSONObject();
                        JSONObject detail = new JSONObject();
                        if (TextUtils.isEmpty(errorMsg)) {
                            detail.put((String) "height", (Object) H5NewEmbedImageView.this.c.getHeight() + Params.UNIT_PX);
                            detail.put((String) "width", (Object) H5NewEmbedImageView.this.c.getWidth() + Params.UNIT_PX);
                        } else {
                            detail.put((String) "errMsg", (Object) errorMsg);
                        }
                        data.put((String) "detail", (Object) detail);
                        data.put((String) "element", (Object) element);
                        event.put((String) "data", (Object) data);
                        h5Bridge.sendToWeb(TextUtils.isEmpty(errorMsg) ? "bindload" : "binderror", event, null);
                    }
                }
            }
        });
    }
}

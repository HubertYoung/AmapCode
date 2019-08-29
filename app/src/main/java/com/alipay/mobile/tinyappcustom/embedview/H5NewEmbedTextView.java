package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.newembedview.H5NewBaseEmbedView;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;

public class H5NewEmbedTextView extends H5NewBaseEmbedView {
    private TextView a;
    /* access modifiers changed from: private */
    public String b;

    private void a() {
        if (this.a == null) {
            this.a = new TextView((Context) this.mContext.get());
            this.a.setSingleLine(true);
            this.a.setEllipsize(TruncateAt.END);
            this.a.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (H5NewEmbedTextView.this.mH5Page.get() != null && !TextUtils.isEmpty(H5NewEmbedTextView.this.b)) {
                        H5Bridge h5Bridge = ((H5Page) H5NewEmbedTextView.this.mH5Page.get()).getBridge();
                        if (h5Bridge != null) {
                            JSONObject event = new JSONObject();
                            JSONObject data = new JSONObject();
                            data.put((String) "element", (Object) H5NewEmbedTextView.this.b);
                            event.put((String) "data", (Object) data);
                            h5Bridge.sendToWeb("nbcomponent.text.bindtap", event, null);
                        }
                    }
                }
            });
        }
    }

    public View getView() {
        a();
        H5Log.d("H5NewEmbedTextView", "getView " + this.a.hashCode());
        return this.a;
    }

    public void onEmbedViewAttachedToWebView() {
        H5Log.d("H5NewEmbedTextView", "onEmbedViewAttachedToWebView");
    }

    public void onEmbedViewDetachedFromWebView() {
        H5Log.d("H5NewEmbedTextView", "onEmbedViewDetachedFromWebView");
    }

    public void onEmbedViewDestory() {
        H5Log.d("H5NewEmbedTextView", "onEmbedViewDestory");
    }

    public void onEmbedViewParamChanged() {
        H5Log.d("H5NewEmbedTextView", "onEmbedViewParamChanged");
    }

    public void onEmbedViewVisibilityChanged() {
        H5Log.d("H5NewEmbedTextView", "onEmbedViewVisibilityChanged");
    }

    public void onWebViewResume() {
        H5Log.d("H5NewEmbedTextView", "onWebViewResume");
    }

    public void onWebViewPause() {
        H5Log.d("H5NewEmbedTextView", "onWebViewPause");
    }

    public void onWebViewDestory() {
        H5Log.d("H5NewEmbedTextView", "onWebViewDestory");
    }

    public void onReceivedMessage(String s, JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedTextView", "onReceivedMessage actionType " + s + ", data " + jsonObject.toJSONString());
        }
    }

    public void onReceivedRender(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedTextView", "onReceivedRender data " + jsonObject.toJSONString() + Token.SEPARATOR + this.a.hashCode());
            this.b = jsonObject.getString("element");
            String text = jsonObject.getString("text");
            String textColor = jsonObject.getString("color");
            String textAlign = jsonObject.getString("textAlign");
            String fontSize = jsonObject.getString("fontSize");
            String fontWeight = jsonObject.getString("fontWeight");
            this.a.setText(text);
            this.a.setBackgroundDrawable(H5EmbedViewUtil.generateBackgroundDrawable(this.a.getContext(), jsonObject, 0));
            if (!TextUtils.isEmpty(textColor)) {
                this.a.setTextColor(H5EmbedViewUtil.a(textColor));
            }
            if (TextUtils.equals(Callout.CALLOUT_TEXT_ALIGN_CENTER, textAlign)) {
                this.a.setGravity(17);
            } else if (TextUtils.equals("right", textAlign)) {
                this.a.setGravity(21);
            } else {
                this.a.setGravity(19);
            }
            if (!TextUtils.isEmpty(fontSize)) {
                this.a.setTextSize(1, Float.valueOf(fontSize).floatValue() - 0.5f);
            }
            if (TextUtils.equals(fontWeight, "bold")) {
                this.a.setTypeface(Typeface.defaultFromStyle(1));
            }
        }
    }

    public void onReceivedData(JSONObject jsonObject, H5BridgeContext h5BridgeContext) {
        if (jsonObject != null && !jsonObject.isEmpty()) {
            H5Log.d("H5NewEmbedTextView", "onReceivedData data " + jsonObject.toJSONString());
        }
    }

    public Bitmap getSnapshot() {
        return null;
    }

    public void triggerPreSnapshot() {
    }
}

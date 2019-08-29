package com.sina.weibo.sdk.network.impl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.sina.weibo.sdk.net.NetStateManager;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.RequestCancelable;
import com.sina.weibo.sdk.network.base.RequestResult;
import com.sina.weibo.sdk.network.base.WbResponse;
import com.sina.weibo.sdk.network.exception.InterceptException;
import com.sina.weibo.sdk.network.exception.SdkException;
import com.sina.weibo.sdk.network.intercept.GlobalInterceptHelper;
import com.sina.weibo.sdk.network.target.Target;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

public class FixRequestTask<T, R> implements RequestCancelable, Runnable {
    private RequestCallback callback = new RequestCallback<>();
    private IRequestParam param;
    Class<T> tClass;
    /* access modifiers changed from: private */
    public Target<R> target;

    class RequestCallback implements Callback {
        private RequestCallback() {
        }

        public boolean handleMessage(Message message) {
            RequestResult requestResult = (RequestResult) message.obj;
            if (FixRequestTask.this.target != null) {
                FixRequestTask.this.target.onRequestDone();
                if (requestResult.getE() != null) {
                    FixRequestTask.this.target.onFailure(requestResult.getE());
                    FixRequestTask.this.target.onError();
                } else {
                    FixRequestTask.this.target.onRequestSuccess(requestResult.getResponse());
                }
            }
            return false;
        }
    }

    public boolean isCancelRequest() {
        return false;
    }

    public FixRequestTask(IRequestParam iRequestParam, Target<R> target2) {
        this.param = iRequestParam;
        this.target = target2;
    }

    public void run() {
        RequestResult requestResult = new RequestResult();
        if (!NetStateManager.isNetworkConnected(this.param.getContext())) {
            requestResult.setE(new SdkException((String) "网络连接错误，请检查网络状态"));
        }
        if (this.param.needIntercept()) {
            try {
                Bundle bundle = new Bundle();
                HashMap<String, IRequestIntercept> globalIntercept = GlobalInterceptHelper.init().getGlobalIntercept();
                for (String str : globalIntercept.keySet()) {
                    IRequestIntercept iRequestIntercept = globalIntercept.get(str);
                    if (iRequestIntercept != null && iRequestIntercept.needIntercept(this.param, bundle)) {
                        iRequestIntercept.doIntercept(this.param, bundle);
                    }
                }
                Iterator<IRequestIntercept> it = this.param.getIntercept().iterator();
                while (it.hasNext()) {
                    IRequestIntercept next = it.next();
                    if (next.needIntercept(this.param, bundle)) {
                        next.doIntercept(this.param, bundle);
                    }
                }
                this.param.getGetBundle().putAll(bundle);
                this.param.getPostBundle().putAll(bundle);
            } catch (InterceptException e) {
                requestResult.setE(e);
                Handler handler = new Handler(Looper.getMainLooper(), this.callback);
                Message message = new Message();
                message.obj = requestResult;
                handler.sendMessage(message);
                return;
            }
        }
        try {
            WbResponse request = RequestEngine.request(this.param);
            Object transResponse = this.target.transResponse(request);
            this.target.onRequestSuccessBg(transResponse);
            requestResult.setResponse(transResponse);
            try {
                InputStream byteStream = request.body().byteStream();
                if (byteStream != null) {
                    byteStream.close();
                }
            } catch (Exception unused) {
            }
        } catch (Exception e2) {
            requestResult.setE(e2);
        }
        Handler handler2 = new Handler(Looper.getMainLooper(), this.callback);
        Message message2 = new Message();
        message2.obj = requestResult;
        handler2.sendMessage(message2);
    }

    public void cancelRequest() {
        run();
    }
}

package com.sina.weibo.sdk.network.impl;

import android.os.Bundle;
import com.sina.weibo.sdk.net.NetStateManager;
import com.sina.weibo.sdk.network.IRequestIntercept;
import com.sina.weibo.sdk.network.IRequestParam;
import com.sina.weibo.sdk.network.IRequestService;
import com.sina.weibo.sdk.network.RequestCancelable;
import com.sina.weibo.sdk.network.base.RequestResult;
import com.sina.weibo.sdk.network.exception.InterceptException;
import com.sina.weibo.sdk.network.exception.RequestException;
import com.sina.weibo.sdk.network.exception.SdkException;
import com.sina.weibo.sdk.network.intercept.GlobalInterceptHelper;
import com.sina.weibo.sdk.network.target.Target;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestService implements IRequestService {
    private static IRequestService iRequestService;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    @Deprecated
    public <T> T request(IRequestParam iRequestParam, Class<T> cls) throws RequestException {
        return null;
    }

    private RequestService() {
        GlobalInterceptHelper.init();
    }

    public static IRequestService getInstance() {
        if (iRequestService == null) {
            iRequestService = new RequestService();
        }
        return iRequestService;
    }

    public RequestResult request(IRequestParam iRequestParam) throws RequestException {
        RequestResult requestResult = new RequestResult();
        if (!NetStateManager.isNetworkConnected(iRequestParam.getContext())) {
            requestResult.setE(new SdkException((String) "网络连接错误，请检查网络状态"));
        }
        if (iRequestParam.needIntercept()) {
            try {
                Bundle bundle = new Bundle();
                HashMap<String, IRequestIntercept> globalIntercept = GlobalInterceptHelper.init().getGlobalIntercept();
                for (String str : globalIntercept.keySet()) {
                    IRequestIntercept iRequestIntercept = globalIntercept.get(str);
                    if (iRequestIntercept != null && iRequestIntercept.needIntercept(iRequestParam, bundle)) {
                        iRequestIntercept.doIntercept(iRequestParam, bundle);
                    }
                }
                Iterator<IRequestIntercept> it = iRequestParam.getIntercept().iterator();
                while (it.hasNext()) {
                    IRequestIntercept next = it.next();
                    if (next.needIntercept(iRequestParam, bundle)) {
                        next.doIntercept(iRequestParam, bundle);
                    }
                }
                iRequestParam.getPostBundle().putAll(bundle);
                iRequestParam.getGetBundle().putAll(bundle);
            } catch (InterceptException e) {
                requestResult.setE(e);
                return requestResult;
            }
        }
        try {
            requestResult.setResponse(String.valueOf(RequestEngine.request(iRequestParam).body().string()));
        } catch (Exception e2) {
            requestResult.setE(e2);
        }
        return requestResult;
    }

    public <T> RequestCancelable asyncRequest(IRequestParam iRequestParam, Target<T> target) {
        this.fixedThreadPool.execute(new FixRequestTask(iRequestParam, target));
        return null;
    }
}

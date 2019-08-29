package com.jiuyan.inimage.http.impl;

import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.common.transport.h5.H5HttpUrlRequest;
import com.alipay.mobile.common.transport.h5.H5HttpUrlResponse;
import com.alipay.mobile.common.transport.h5.H5NetworkManager;
import com.alipay.mobile.common.transport.http.HttpForm;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.jiuyan.inimage.http.interfaces.IHttpAction;
import com.jiuyan.inimage.http.interfaces.OnRequestCompleteListener;
import com.jiuyan.inimage.util.n;
import com.jiuyan.inimage.util.q;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpCore implements IHttpAction {
    private static final String TAG = "HttpCore";
    private H5HttpUrlRequest h5HttpUrlRequest = null;
    private String mApi;
    private String mHost;
    private int mMethod;
    private StringBuilder mUrl;
    private List<NameValuePair> nameValuePairs;

    class InRequestCallback implements TransportCallback {
        private OnRequestCompleteListener mListener;

        public InRequestCallback(OnRequestCompleteListener onRequestCompleteListener) {
            this.mListener = onRequestCompleteListener;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public void onCancelled(Request request) {
        }

        public void onPreExecute(Request request) {
        }

        public void onPostExecute(Request request, Response response) {
        }

        public void onProgressUpdate(Request request, double d) {
        }

        public void onFailed(Request request, int i, String str) {
            if (this.mListener != null) {
                this.mListener.onFailed(i);
            }
        }
    }

    public HttpCore(int i, String str, String str2) {
        this.mMethod = i;
        this.mHost = str;
        this.mApi = str2;
        this.mUrl = new StringBuilder();
        this.mUrl.append(this.mHost).append(this.mApi);
        switch (this.mMethod) {
            case 1:
                this.nameValuePairs = new ArrayList();
                break;
        }
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void putParam(String str, String str2) {
        switch (this.mMethod) {
            case 0:
                buildUrl(str, str2);
                return;
            case 1:
                this.nameValuePairs.add(new BasicNameValuePair(str, str2));
                return;
            default:
                return;
        }
    }

    public void buildUrl(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str2)) {
                String encode = URLEncoder.encode(str2, "utf-8");
                if (this.mUrl != null) {
                    String sb = this.mUrl.toString();
                    if (TextUtils.isEmpty(sb) || !sb.contains("?")) {
                        this.mUrl.append("?").append(str).append("=").append(encode);
                    } else {
                        this.mUrl.append("&").append(str).append("=").append(encode);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void execute(final OnRequestCompleteListener onRequestCompleteListener) {
        ThreadPoolExecutor acquireExecutor = ((TaskScheduleService) n.a(TaskScheduleService.class)).acquireExecutor(ScheduleType.RPC);
        if (acquireExecutor != null) {
            acquireExecutor.execute(new Runnable() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public void run() {
                    HttpCore.this.doExecute(onRequestCompleteListener);
                }
            });
            return;
        }
        onRequestCompleteListener.onFailed(0);
        LoggerFactory.getTraceLogger().warn((String) "httpcore", (String) "获取线程池失败");
    }

    /* access modifiers changed from: private */
    public void doExecute(OnRequestCompleteListener onRequestCompleteListener) {
        switch (this.mMethod) {
            case 0:
                this.h5HttpUrlRequest = new H5HttpUrlRequest(this.mUrl.toString());
                this.h5HttpUrlRequest.setRequestMethod("GET");
                break;
            case 1:
                try {
                    this.h5HttpUrlRequest = new H5HttpUrlRequest(this.mUrl.toString(), new HttpForm(this.nameValuePairs, "UTF-8"), (ArrayList<Header>) null, (HashMap<String, String>) null);
                } catch (UnsupportedEncodingException e) {
                    q.a(TAG, e.toString());
                }
                this.h5HttpUrlRequest.setRequestMethod("POST");
                break;
        }
        this.h5HttpUrlRequest.setTransportCallback(new InRequestCallback(onRequestCompleteListener));
        try {
            H5HttpUrlResponse h5HttpUrlResponse = (H5HttpUrlResponse) ((Response) new H5NetworkManager(TransportEnvUtil.getContext()).enqueue(this.h5HttpUrlRequest).get());
            h5HttpUrlResponse.getHeader().toMultimap();
            InputStream inputStream = h5HttpUrlResponse.getInputStream();
            if (onRequestCompleteListener != null) {
                byte[] bytesByInputStream = getBytesByInputStream(inputStream);
                if (bytesByInputStream == null) {
                    onRequestCompleteListener.onFailed(-1);
                    q.a("onPostExecute", "result == null");
                    return;
                }
                q.a("onPostExecute", "json: " + new String(bytesByInputStream));
                onRequestCompleteListener.onSuccess(bytesByInputStream);
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            if (onRequestCompleteListener != null) {
                onRequestCompleteListener.onFailed(-1);
            }
        } catch (ExecutionException e3) {
            e3.printStackTrace();
            if (onRequestCompleteListener != null) {
                onRequestCompleteListener.onFailed(-1);
            }
        }
    }

    private byte[] getBytesByInputStream(InputStream inputStream) {
        byte[] bArr = null;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        byte[] bArr2 = new byte[8192];
        while (true) {
            try {
                int read = bufferedInputStream.read(bArr2);
                if (read <= 0) {
                    break;
                }
                bufferedOutputStream.write(bArr2, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
                try {
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                try {
                    bufferedInputStream.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } finally {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                try {
                    bufferedInputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
        }
        bufferedOutputStream.flush();
        bArr = byteArrayOutputStream.toByteArray();
        try {
        } catch (IOException e6) {
            e6.printStackTrace();
        }
        try {
            bufferedInputStream.close();
        } catch (IOException e7) {
            e7.printStackTrace();
        }
        return bArr;
    }
}

package com.alipay.android.phone.mobilecommon.rpc;

import android.content.Context;
import com.alipay.mobile.common.rpc.RpcFactory;
import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.service.local.rpc.BioRPCService;
import com.alipay.mobile.security.bio.utils.BioLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class AlipayRpcService extends BioRPCService {
    public static final String APP_KEY_DEBUG = "98F6BCD082047";
    public static final String APP_KEY_ONLINE = "rpc-sdk-online";
    private static String a = "http://116.62.88.165/mgw.htm";
    private static String b = "https://cn-hangzhou-mgs-gw.cloud.alipay.com/mgw.htm";
    public static String envType;
    /* access modifiers changed from: private */
    public String c = b;
    protected RpcFactory mRpcFactory = new RpcFactory(new a(this));

    public AlipayRpcService() {
        this.mRpcFactory.addRpcInterceptor(OperationType.class, new c());
    }

    public void onCreate(BioServiceManager bioServiceManager) {
        super.onCreate(bioServiceManager);
        if (this.mContext != null) {
            this.mRpcFactory.setContext(this.mContext);
        }
    }

    public void setContext(Context context) {
        super.setContext(context);
        if (this.mContext != null) {
            this.mRpcFactory.setContext(this.mContext);
        }
    }

    public void addAntCloudHeaders(ArrayList<Header> arrayList) {
        if (arrayList != null) {
            if ("staging".equalsIgnoreCase(envType)) {
                arrayList.add(new BasicHeader("WorkspaceId", "staging"));
            } else {
                arrayList.add(new BasicHeader("WorkspaceId", "prod"));
            }
        }
    }

    public void addRequestHeaders(Object obj, Map<String, String> map) {
        this.mRpcFactory.getRpcInvokeContext(obj).setRequestHeaders(map);
    }

    public <T> T getRpcProxy(Class<T> cls) {
        HashMap hashMap = new HashMap();
        T rpcProxy = this.mRpcFactory.getRpcProxy(cls);
        this.mRpcFactory.getRpcInvokeContext(rpcProxy).setRequestHeaders(hashMap);
        this.mRpcFactory.getRpcInvokeContext(rpcProxy).setAppId("C321516081430");
        return rpcProxy;
    }

    public void setRemoteUrl(String str) {
        BioLog.w((String) "AlipayRpcService", "setRemoteUrl(" + str + ")");
        this.c = str;
        BioLog.w((String) "AlipayRpcService", "setRemoteUrl() : mRemoteUrl=" + this.c);
    }
}

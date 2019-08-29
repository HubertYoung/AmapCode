package com.alipay.mobile.security.bio.workspace;

import android.content.Context;
import android.util.Base64;
import com.alibaba.fastjson.JSON;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig;
import com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent;
import com.alipay.mobile.security.bio.api.BioParameter;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.service.BioAppDescription;
import com.alipay.mobile.security.bio.service.BioServiceManager;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.InputStreamUtils;
import com.alipay.mobile.security.bio.utils.RSASign;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

public class JsonToBioApp extends a {
    public JsonToBioApp(Context context, BioTransfer bioTransfer) {
        super(context, bioTransfer);
    }

    public BioAppDescription toBioApp(BioParameter bioParameter) {
        if (bioParameter == null) {
            throw new BioIllegalArgumentException();
        }
        String protocol = bioParameter.getProtocol();
        BioAppDescription bioAppDescription = new BioAppDescription();
        bioAppDescription.setRemoteURL(bioParameter.getRemoteURL());
        bioAppDescription.setHeadImageURL(bioParameter.getHeadImageUrl());
        try {
            BisClientConfig bisClientConfig = (BisClientConfig) JSON.parseObject(protocol, BisClientConfig.class);
            Map<String, String> extProperty = bioParameter.getExtProperty();
            boolean z = false;
            if (extProperty != null && extProperty.containsKey("mock")) {
                z = Boolean.parseBoolean(extProperty.remove("mock"));
            }
            if (z) {
                Env env = BioServiceManager.getEnv();
                String str = env.name;
                if ("online".equals(str) || Env.NAME_PRE.equals(str) || Env.NAME_ONLINE_ANT_CLOUD.equals(str)) {
                    InputStream a = a();
                    boolean doCheck = RSASign.doCheck(bisClientConfig.getContent().getBytes(), Base64.decode(bisClientConfig.getSign(), 8), a);
                    InputStreamUtils.close(a);
                    if (!doCheck) {
                        throw new RuntimeException("protocol check sign failed : env=" + env);
                    }
                }
            }
            BisClientConfigContent bisClientConfigContent = (BisClientConfigContent) JSON.parseObject(bisClientConfig.getContent(), BisClientConfigContent.class);
            String androidcfg = bisClientConfigContent.getAndroidcfg();
            bioAppDescription.setBioType(bisClientConfigContent.getType());
            bioAppDescription.setBioAction(bisClientConfigContent.getSampleMode());
            bioAppDescription.setCfg(androidcfg);
            bioAppDescription.setBistoken(bisClientConfigContent.getToken());
            bioAppDescription.setAutoClose(bioParameter.isAutoClose());
            bioAppDescription.setBundle(bioParameter.getBundle());
            if (extProperty != null && !extProperty.isEmpty()) {
                for (Entry next : extProperty.entrySet()) {
                    bioAppDescription.addExtProperty((String) next.getKey(), (String) next.getValue());
                }
            }
        } catch (Throwable th) {
            BioLog.e(th);
        }
        return bioAppDescription;
    }
}

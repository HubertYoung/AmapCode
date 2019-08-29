package com.autonavi.minimap.ajx3.modules;

import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.server.aos.serverkey;
import java.nio.charset.Charset;

@AjxModule(isInUiThread = false, value = "utils")
public class ModuleCommonUtils extends AbstractModule {
    public static final String MODULE_NAME = "utils";

    public ModuleCommonUtils(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "encrypt")
    public String encrypt(String str, String str2) {
        if (str2 == null) {
            return encrypt(str);
        }
        return serverkey.amapEncodeV2(str2, str);
    }

    @AjxMethod(invokeMode = "sync", value = "decrypt")
    public String decrypt(String str, String str2) {
        if (str2 == null) {
            return decrypt(str);
        }
        return serverkey.amapDecodeV2(str2, str);
    }

    private String encrypt(String str) {
        if (str == null) {
            return null;
        }
        return serverkey.amapEncodeV2(str);
    }

    private String decrypt(String str) {
        if (str == null) {
            return null;
        }
        return serverkey.amapDecodeV2(str);
    }

    @AjxMethod(invokeMode = "sync", value = "base64Encode")
    public String base64Encode(String str) {
        return str != null ? agv.a(str.getBytes()) : str;
    }

    @AjxMethod(invokeMode = "sync", value = "base64Decode")
    public String base64Decode(String str) {
        return str != null ? new String(agv.a(str), Charset.forName("utf-8")) : str;
    }

    @AjxMethod(invokeMode = "sync", value = "md5")
    public String md5(String str) {
        if (str == null) {
            return null;
        }
        return agy.a(str);
    }
}

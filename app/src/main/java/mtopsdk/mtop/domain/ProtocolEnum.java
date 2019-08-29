package mtopsdk.mtop.domain;

import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;

public enum ProtocolEnum {
    HTTP(AjxHttpLoader.DOMAIN_HTTP),
    HTTPSECURE(AjxHttpLoader.DOMAIN_HTTPS);
    
    private String protocol;

    public final String getProtocol() {
        return this.protocol;
    }

    private ProtocolEnum(String str) {
        this.protocol = str;
    }
}

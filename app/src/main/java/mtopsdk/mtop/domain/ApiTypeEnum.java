package mtopsdk.mtop.domain;

public enum ApiTypeEnum {
    ISV_OPEN_API("isv_open_api");
    
    private String apiType;

    public final String getApiType() {
        return this.apiType;
    }

    private ApiTypeEnum(String str) {
        this.apiType = str;
    }
}

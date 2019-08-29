package mtopsdk.mtop.domain;

public enum JsonTypeEnum {
    JSON("json"),
    ORIGINALJSON("originaljson");
    
    private String jsonType;

    public final String getJsonType() {
        return this.jsonType;
    }

    private JsonTypeEnum(String str) {
        this.jsonType = str;
    }
}

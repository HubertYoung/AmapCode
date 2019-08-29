package mtopsdk.mtop.domain;

import java.io.Serializable;
import java.util.Map;

public class MtopRequest implements ffb, Serializable {
    private static final long serialVersionUID = -439476282014493612L;
    private String apiName;
    private String data = bny.c;
    public Map<String, String> dataParams;
    private boolean needEcode;
    private boolean needSession;
    private String version;

    public String getApiName() {
        return this.apiName;
    }

    public void setApiName(String str) {
        this.apiName = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public boolean isNeedEcode() {
        return this.needEcode;
    }

    public void setNeedEcode(boolean z) {
        this.needEcode = z;
    }

    public boolean isNeedSession() {
        return this.needSession;
    }

    public void setNeedSession(boolean z) {
        this.needSession = z;
    }

    public boolean isLegalRequest() {
        return fdd.a(this.apiName) && fdd.a(this.version) && fdd.a(this.data);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("MtopRequest [apiName=");
        sb.append(this.apiName);
        sb.append(", version=");
        sb.append(this.version);
        sb.append(", data=");
        sb.append(this.data);
        sb.append(", needEcode=");
        sb.append(this.needEcode);
        sb.append(", needSession=");
        sb.append(this.needSession);
        sb.append("]");
        return sb.toString();
    }

    public String getKey() {
        if (fdd.b(this.apiName) || fdd.b(this.version)) {
            return null;
        }
        return fdd.b(this.apiName, this.version);
    }
}

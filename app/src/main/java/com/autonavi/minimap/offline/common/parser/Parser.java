package com.autonavi.minimap.offline.common.parser;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Parser<T> {
    public static final int ERRORCODE0 = 0;
    public static final int ERRORCODE1 = 1;
    public static final int ERRORCODE3 = 3;
    public static final int ERRORCODE4 = 4;
    public static final int ERRORCODE5 = 5;
    public static final int ERRORCODE_NETWORK = -1;
    private int errorCode = -1;
    private JSONObject mDataObject = null;
    private boolean result = false;

    public abstract T paseAll(byte[] bArr);

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean z) {
        this.result = z;
    }

    public JSONObject getmDataObject() {
        return this.mDataObject;
    }

    /* access modifiers changed from: protected */
    public void parseHeader(byte[] bArr) {
        if (bArr == null) {
            this.errorCode = -1;
            return;
        }
        try {
            this.mDataObject = new JSONObject(new String(bArr, "UTF-8"));
            this.result = this.mDataObject.getBoolean("result");
            this.errorCode = this.mDataObject.getInt("code");
        } catch (UnsupportedEncodingException unused) {
            this.errorCode = -1;
        } catch (JSONException unused2) {
            this.errorCode = -1;
        } catch (Exception unused3) {
            this.errorCode = -1;
        }
    }
}

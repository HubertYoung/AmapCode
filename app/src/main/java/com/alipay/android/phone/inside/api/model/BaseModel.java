package com.alipay.android.phone.inside.api.model;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.api.utils.IBundleModel;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.io.Serializable;
import java.lang.reflect.Field;
import org.json.JSONObject;

public abstract class BaseModel<T extends ResultCode> implements IBundleModel, Serializable {
    private String appKey;
    private JSONObject extParams;
    private String havanaId;
    private boolean isPrisonBreak = false;
    private boolean isThirdPartyApp = false;
    private boolean isTrojan = false;
    private String sid;

    public abstract IBizOperation<T> getOperaion();

    public void addParam(String str, String str2) {
        try {
            if (this.extParams == null) {
                this.extParams = new JSONObject();
            }
            this.extParams.put(str, str2);
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
    }

    public void addParam(String str, boolean z) {
        addParam(str, String.valueOf(z));
    }

    public void addParam(String str, int i) {
        addParam(str, String.valueOf(i));
    }

    public void addParam(String str, float f) {
        addParam(str, String.valueOf(f));
    }

    public String getExtParams() {
        if (this.extParams == null) {
            return "";
        }
        return this.extParams.toString();
    }

    public boolean isThirdPartyApp() {
        return this.isThirdPartyApp;
    }

    public void setThirdPartyApp(boolean z) {
        this.isThirdPartyApp = z;
    }

    public Bundle toBundle() {
        try {
            Bundle bundle = new Bundle();
            bundle.putString("action", getOperaion().getAction().getActionName());
            Class cls = getClass();
            do {
                Field[] declaredFields = cls.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    Object obj = field.get(this);
                    String cls2 = field.getType().toString();
                    TraceLogger f = LoggerFactory.f();
                    StringBuilder sb = new StringBuilder();
                    sb.append(cls2);
                    sb.append(":");
                    sb.append(field.getName());
                    sb.append(":");
                    sb.append(obj);
                    f.a((String) "BaseModel", sb.toString());
                    if (obj != null) {
                        bundle.putString(field.getName(), String.valueOf(obj));
                    }
                }
                cls = cls.getSuperclass();
            } while (cls != Object.class);
            return bundle;
        } catch (Throwable th) {
            LoggerFactory.f().e(BaseModel.class.getName(), th.getMessage());
            return null;
        }
    }

    public boolean isTrojan() {
        return this.isTrojan;
    }

    public void setTrojan(boolean z) {
        this.isTrojan = z;
    }

    public boolean isPrisonBreak() {
        return this.isPrisonBreak;
    }

    public void setPrisonBreak(boolean z) {
        this.isPrisonBreak = z;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public String getHavanaId() {
        return this.havanaId;
    }

    public void setHavanaId(String str) {
        this.havanaId = str;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String str) {
        this.sid = str;
    }
}

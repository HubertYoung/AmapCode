package com.oppo.oms.sdk.entity;

import org.json.JSONObject;

public class Result<E, T> {
    private T data;
    private E error;
    private boolean success;

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public E getError() {
        return this.error;
    }

    public void setData(T t) {
        this.data = t;
    }

    public void setError(E e) {
        this.error = e;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public String toString() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("success", this.success);
            if (this.error != null) {
                jSONObject.put("error", new JSONObject(this.error.toString()));
            }
            if (this.data != null) {
                jSONObject.put("data", new JSONObject(this.data.toString()));
            }
            return jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return super.toString();
        }
    }
}

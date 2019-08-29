package com.alipay.mobile.common.transport.http;

import java.io.OutputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public class HttpForm extends UrlEncodedFormEntity {
    public HttpForm(List<? extends NameValuePair> parameters, String encoding) {
        super(parameters, encoding);
    }

    public HttpForm(List<? extends NameValuePair> parameters) {
        super(parameters);
    }

    public void writeTo(OutputStream outstream) {
        HttpForm.super.writeTo(outstream);
    }
}

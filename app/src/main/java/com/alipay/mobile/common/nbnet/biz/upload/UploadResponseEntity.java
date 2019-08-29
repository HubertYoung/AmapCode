package com.alipay.mobile.common.nbnet.biz.upload;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetServerLimitFlowException;
import java.util.Collections;
import java.util.Map;

public class UploadResponseEntity {
    public String a;
    public String b;
    public UploadActionSession c;
    public NBNetException d;
    public boolean e = false;
    public int f = 0;
    public Map<String, String> g = Collections.EMPTY_MAP;

    public final void a(NBNetException nbNetException) {
        this.d = nbNetException;
        if (this.d != null && (this.d instanceof NBNetServerLimitFlowException)) {
            this.f = ((NBNetServerLimitFlowException) this.d).getSleep();
        }
    }
}

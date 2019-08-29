package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.FileBody;
import java.io.File;
import java.io.OutputStream;

public class ProgressFileBody extends FileBody {
    private TransferredListener a;
    private ProgressOutputStream b;
    private String c;

    public ProgressFileBody(File file, TransferredListener transferedChangedListener) {
        super(file);
        this.a = transferedChangedListener;
    }

    public ProgressFileBody(File file, String aliasName, TransferredListener listener) {
        this(file, listener);
        this.c = aliasName;
    }

    public void writeTo(OutputStream out) {
        this.b = new ProgressOutputStream(out, this.a);
        super.writeTo(this.b);
    }

    public String getFilename() {
        return TextUtils.isEmpty(this.c) ? super.getFilename() : this.c;
    }
}

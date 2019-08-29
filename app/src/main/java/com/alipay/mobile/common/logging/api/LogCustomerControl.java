package com.alipay.mobile.common.logging.api;

import com.alipay.mobile.common.logging.api.customer.LogUploadInfo;
import com.alipay.mobile.common.logging.api.customer.LogWriteInfo;
import com.alipay.mobile.common.logging.api.customer.UploadResultInfo;

public interface LogCustomerControl {
    UploadResultInfo isLogUpload(LogUploadInfo logUploadInfo);

    boolean isLogWrite(LogWriteInfo logWriteInfo);
}

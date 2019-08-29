package com.amap.bundle.dumpcrash.installerror;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import java.io.File;

public class ErrorSoUploader$2 implements AosResponseCallbackOnUi<AosStringResponse> {
    final /* synthetic */ File a;
    final /* synthetic */ a b;
    final /* synthetic */ um c;

    public ErrorSoUploader$2(um umVar, File file, a aVar) {
        this.c = umVar;
        this.a = file;
        this.b = aVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosStringResponse aosStringResponse = (AosStringResponse) aosResponse;
        if (!(aosStringResponse == null || aosStringResponse.getResult() == null || !((String) aosStringResponse.getResult()).contains("Successful"))) {
            ahd.a(this.a);
        }
        if (this.b != null) {
            this.b.a();
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.b != null) {
            this.b.a();
        }
    }
}

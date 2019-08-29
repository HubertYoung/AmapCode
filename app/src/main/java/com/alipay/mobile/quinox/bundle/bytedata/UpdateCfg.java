package com.alipay.mobile.quinox.bundle.bytedata;

import com.alipay.mobile.quinox.bundle.IBundle;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.security.Adler32Verifier;
import com.alipay.mobile.quinox.security.Md5Verifier;
import com.alipay.mobile.quinox.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Deprecated
public class UpdateCfg {
    public static final String TAG = "UpdateCfg";

    public static void main(String[] strArr) throws IOException {
        String str;
        long j;
        StringBuilder sb = new StringBuilder("bundle-core.jar version=1.1.2.55, args=");
        sb.append(StringUtil.array2String(strArr));
        Log.w((String) TAG, sb.toString());
        ByteDataBundleOperator byteDataBundleOperator = new ByteDataBundleOperator(new File(strArr[0]));
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        byteDataBundleOperator.readBundlesFromCfg(arrayList, hashMap);
        File file = new File(strArr[1]);
        int i = 4;
        for (String str2 : hashMap.keySet()) {
            IBundle iBundle = (IBundle) hashMap.get(str2);
            File file2 = new File(file, iBundle.getLocation());
            int version = iBundle.getVERSION();
            switch (version) {
                case 3:
                    iBundle.setSize(file2.length());
                    break;
                case 4:
                    if (file2.exists()) {
                        j = Adler32Verifier.genFileAdler32Sum(file2);
                        str = Md5Verifier.genFileMd5sum(file2);
                    } else {
                        j = -1;
                        str = null;
                    }
                    StringBuilder sb2 = new StringBuilder("[adler32sum : ");
                    sb2.append(iBundle.getAdler32Sum());
                    sb2.append(" => ");
                    sb2.append(j);
                    sb2.append("], [md5 : ");
                    sb2.append(iBundle.getMD5());
                    sb2.append(" => ");
                    sb2.append(str);
                    sb2.append("] : ");
                    sb2.append(file2);
                    Log.w((String) TAG, sb2.toString());
                    iBundle.setAdler32Sum(j);
                    iBundle.setMD5(str);
                    break;
            }
            if (version >= 5) {
                i = 5;
            }
        }
        ArrayList arrayList2 = new ArrayList(hashMap.size());
        arrayList2.addAll(hashMap.values());
        Log.w((String) TAG, "bundleVER=".concat(String.valueOf(i)));
        if (i >= 5) {
            byteDataBundleOperator.writeBundlesToCfg2(arrayList, arrayList2);
        } else {
            byteDataBundleOperator.writeBundlesToCfg(arrayList, arrayList2);
        }
    }
}

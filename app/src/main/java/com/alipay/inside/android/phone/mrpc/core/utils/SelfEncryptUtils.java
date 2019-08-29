package com.alipay.inside.android.phone.mrpc.core.utils;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.security.InsideRpcPack;
import com.alipay.inside.android.phone.mrpc.core.monitor.DataContainer;
import com.alipay.inside.android.phone.mrpc.core.monitor.DataItemsUtil;
import com.alipay.inside.android.phone.mrpc.core.monitor.RPCDataItems;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;

public class SelfEncryptUtils {
    private static final String TAG = "SelfEncryptUtils";
    private static boolean usingSelfEncrypt = true;

    public static AbstractHttpEntity getEncryptedEntity(byte[] bArr, InsideRpcPack insideRpcPack, DataContainer dataContainer) throws Exception {
        LoggerFactory.f().a((String) TAG, (String) "encrypted...");
        try {
            DataItemsUtil.putDataItem2Container(dataContainer, "REQ_RAW_SIZE", String.valueOf(bArr.length));
            long currentTimeMillis = System.currentTimeMillis();
            if (!usingSelfEncrypt) {
                return new ByteArrayEntity(bArr);
            }
            byte[] a = insideRpcPack.a(bArr);
            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(a);
            byteArrayEntity.setContentEncoding("alipayinside");
            long currentTimeMillis2 = System.currentTimeMillis();
            DataItemsUtil.putDataItem2Container(dataContainer, "REQ_SIZE", String.valueOf(a.length));
            DataItemsUtil.putDataItem2Container(dataContainer, RPCDataItems.ENCODE_TIME, String.valueOf(currentTimeMillis2 - currentTimeMillis));
            return byteArrayEntity;
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getEncryptedEntity ex:");
            sb.append(e.toString());
            f.d(TAG, sb.toString());
            throw e;
        }
    }

    public static byte[] getDecryptedContent(byte[] bArr, InsideRpcPack insideRpcPack, DataContainer dataContainer) throws Exception {
        LoggerFactory.f().a((String) TAG, (String) "decrypted...");
        try {
            DataItemsUtil.putDataItem2Container(dataContainer, "RES_SIZE", String.valueOf(bArr.length));
            long currentTimeMillis = System.currentTimeMillis();
            if (!usingSelfEncrypt) {
                return bArr;
            }
            byte[] b = insideRpcPack.b(bArr);
            DataItemsUtil.putDataItem2Container(dataContainer, RPCDataItems.DECODE_TIME, String.valueOf(System.currentTimeMillis() - currentTimeMillis));
            DataItemsUtil.putDataItem2Container(dataContainer, RPCDataItems.RES_RAW_SIZE, String.valueOf(b.length));
            return b;
        } catch (Exception e) {
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("getDecryptedContent ex:");
            sb.append(e.toString());
            f.d(TAG, sb.toString());
            throw e;
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }
}

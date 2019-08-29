package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.SocketException;

public class TlvUtil {
    public static final int LEN_LENGTH = 2;
    public static final int TAG_LENGTH = 2;

    public static void sendNormalTlv(OutputStream output, int tag, byte[] data) {
        output.write(ByteUtil.shortToBytes(tag));
        output.write(ByteUtil.shortToBytes(data.length));
        output.write(data);
    }

    public static void sendNormalTl(OutputStream output, int tag, int length) {
        output.write(ByteUtil.shortToBytes(tag));
        output.write(ByteUtil.shortToBytes(length));
    }

    public static int calculateLength(int tlvs, int dataLength) {
        return (tlvs * 4) + dataLength;
    }

    public static void readTlBytes(InputStream input, byte[] temp2bytes) {
        readDataFully(input, 2, temp2bytes, 0);
    }

    public static void readDataFully(InputStream input, int readNum, byte[] buffer, int offset) {
        if (readNum <= 0) {
            throw new IllegalArgumentException("1000 数据读取异常");
        }
        while (readNum > 0) {
            try {
                int bytesRead = input.read(buffer, offset, readNum);
                InnerLogUtil.log4AtlsTest("DATA-RES-DETAIL：" + bytesRead);
                if (bytesRead != readNum) {
                    if (bytesRead < 0) {
                        throw new SocketException("socket exception. bytesRead == -1");
                    }
                    offset += bytesRead;
                    readNum -= bytesRead;
                } else {
                    return;
                }
            } catch (Throwable ex) {
                if (ex instanceof IOException) {
                    throw ((IOException) ex);
                }
                InterruptedIOException ioException = new InterruptedIOException();
                ioException.initCause(ex);
                throw ioException;
            }
        }
    }
}

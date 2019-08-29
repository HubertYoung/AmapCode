package com.alipay.android.phone.bluetoothsdk.scan.message.protocol;

import java.security.InvalidParameterException;

public class MessageFragment {
    public static final int DATA_LENGTH = 15;
    public static final int MAX_FRAGMENTS_NUM = 15;
    public static final int REDUNDANT_BYTES_LEN = 5;
    private byte[] data;
    private byte[] sha;

    public MessageFragment(byte[] sha2) {
        this.sha = sha2;
    }

    public void compose(int totalNum, int index, byte[] dataBytes) {
        if (totalNum > 15) {
            throw new InvalidParameterException("The number of origin data split cannot be greater than 15");
        } else if (index + 1 > totalNum) {
            throw new InvalidParameterException(String.format("The index(%d) cannot be greater than the totalNum(%d)", new Object[]{Integer.valueOf(index), Integer.valueOf(totalNum)}));
        } else if (dataBytes == null) {
            throw new InvalidParameterException("The data bytes cannot be null");
        } else if (dataBytes.length == 0) {
            throw new InvalidParameterException("The length of the data cannot be 0");
        } else {
            int residualBytesLen = dataBytes.length - (index * 15);
            if (residualBytesLen > 15) {
                residualBytesLen = 15;
            }
            this.data = new byte[(residualBytesLen + 5)];
            System.arraycopy(this.sha, 0, this.data, 0, 4);
            this.data[4] = (byte) (totalNum & 255);
            this.data[3] = (byte) ((this.data[3] & 240) | (index & 15));
            System.arraycopy(dataBytes, index * 15, this.data, 5, residualBytesLen);
        }
    }

    public void initFromOutData(byte[] data2) {
        this.data = data2;
    }

    public Long getSha1Key() {
        if (this.data == null || this.data.length < 6) {
            return null;
        }
        return Long.valueOf((long) (this.data[0] * this.data[1] * this.data[2] * (this.data[3] & 240)));
    }

    public int getIndex() {
        if (this.data == null || this.data.length < 6) {
            return -1;
        }
        return this.data[3] & 15;
    }

    public int getTotalNum() {
        if (this.data == null || this.data.length < 6) {
            return -1;
        }
        return this.data[4] & 255;
    }

    /* access modifiers changed from: 0000 */
    public byte[] getOriginData() {
        if (this.data == null || this.data.length < 6) {
            return null;
        }
        byte[] originData = new byte[(this.data.length - 5)];
        System.arraycopy(this.data, 5, originData, 0, this.data.length - 5);
        return originData;
    }

    public byte[] getData() {
        return this.data;
    }
}

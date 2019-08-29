package com.alipay.mobile.quinox.utils.bytedata;

import com.alipay.mobile.quinox.utils.Pool;

class ByteArrayPools {

    static class ByteArray127Pool extends ByteArrayPool {
        ByteArray127Pool() {
            super();
        }

        /* access modifiers changed from: protected */
        public byte[] newObject() {
            return new byte[127];
        }
    }

    static class ByteArray2Pool extends ByteArrayPool {
        ByteArray2Pool() {
            super();
        }

        /* access modifiers changed from: protected */
        public byte[] newObject() {
            return new byte[2];
        }
    }

    static class ByteArray4Pool extends ByteArrayPool {
        ByteArray4Pool() {
            super();
        }

        /* access modifiers changed from: protected */
        public byte[] newObject() {
            return new byte[4];
        }
    }

    static class ByteArray8Pool extends ByteArrayPool {
        ByteArray8Pool() {
            super();
        }

        /* access modifiers changed from: protected */
        public byte[] newObject() {
            return new byte[8];
        }
    }

    static abstract class ByteArrayPool extends Pool<byte[]> {
        private ByteArrayPool() {
            super(1, 8);
        }

        public synchronized byte[] obtain() {
            return (byte[]) super.obtain();
        }

        public synchronized void free(byte[] bArr) {
            super.free(bArr);
        }
    }

    ByteArrayPools() {
    }
}

package com.alipay.mobile.nebula.io;

import android.support.v4.util.LruCache;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ByteArrayPool {
    private static final boolean DEBUG = H5Utils.isDebuggable(H5Utils.getContext());
    private static final String TAG = "ByteArrayPool";
    private final BytePool mBuffersFastCache = new BytePool(12);
    /* access modifiers changed from: private */
    public int mCurrentSize = 0;
    private boolean mEnabled = DEBUG;
    private final AtomicBoolean mIsTriming = new AtomicBoolean(false);
    private final int mSizeLimit;
    /* access modifiers changed from: private */
    public final Object mSizeLock = new Object();
    private int mTotallyReusedSize = 0;

    private class BytePool extends LruCache<Integer, LinkedList<byte[]>> {
        private BytePool(int maxSize) {
            super(maxSize);
        }

        /* access modifiers changed from: protected */
        public void entryRemoved(boolean evicted, Integer key, LinkedList<byte[]> oldValue, LinkedList<byte[]> newValue) {
            synchronized (ByteArrayPool.this.mSizeLock) {
                if (evicted || newValue != null) {
                    ByteArrayPool.this.mCurrentSize = ByteArrayPool.this.mCurrentSize - (key.intValue() * oldValue.size());
                }
            }
        }
    }

    private class ConcurrentLinkedList<T> extends LinkedList<T> {
        private ConcurrentLinkedList() {
        }

        public T poll() {
            T poll;
            synchronized (this) {
                poll = super.poll();
            }
            return poll;
        }

        public boolean offer(T o) {
            boolean offer;
            synchronized (this) {
                offer = super.offer(o);
            }
            return offer;
        }
    }

    public ByteArrayPool(int sizeLimit) {
        this.mSizeLimit = sizeLimit;
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null) {
            this.mEnabled = "YES".equalsIgnoreCase(provider.getConfigWithProcessCache("h5_enableByteArrayPool"));
        }
    }

    public byte[] getBuf(int len) {
        if (!this.mEnabled) {
            return new byte[len];
        }
        long start = System.currentTimeMillis();
        LinkedList bufsMatchSize = (LinkedList) this.mBuffersFastCache.get(Integer.valueOf(len));
        if (bufsMatchSize != null) {
            byte[] buf = (byte[]) bufsMatchSize.poll();
            if (buf != null) {
                synchronized (this.mSizeLock) {
                    try {
                        this.mCurrentSize -= buf.length;
                    }
                }
                if (!DEBUG) {
                    return buf;
                }
                this.mTotallyReusedSize += buf.length;
                long elapsed = System.currentTimeMillis() - start;
                if (elapsed <= 0) {
                    return buf;
                }
                H5Log.d(TAG, "(debug only) reuse pool: " + len + "(" + this.mTotallyReusedSize + ") use time: " + elapsed);
                return buf;
            }
        }
        if (DEBUG) {
            long elapsed2 = System.currentTimeMillis() - start;
            if (elapsed2 > 0) {
                H5Log.d(TAG, "(debug only) new byte, use time: " + elapsed2);
            }
        }
        return new byte[len];
    }

    public void returnBuf(byte[] buf) {
        if (this.mEnabled && buf != null && buf.length <= this.mSizeLimit) {
            long start = System.currentTimeMillis();
            synchronized (this.mSizeLock) {
                this.mCurrentSize += buf.length;
            }
            LinkedList bufsMatchSize = (LinkedList) this.mBuffersFastCache.get(Integer.valueOf(buf.length));
            if (bufsMatchSize == null) {
                LinkedList bufsMatchSize2 = new ConcurrentLinkedList();
                bufsMatchSize2.offer(buf);
                this.mBuffersFastCache.put(Integer.valueOf(buf.length), bufsMatchSize2);
            } else {
                bufsMatchSize.offer(buf);
            }
            trim();
            if (DEBUG) {
                long elapsed = System.currentTimeMillis() - start;
                if (elapsed > 0) {
                    H5Log.d(TAG, "(debug only) return use time: " + elapsed);
                }
            }
        }
    }

    private void trim() {
        if (!this.mIsTriming.get() && this.mCurrentSize > this.mSizeLimit) {
            this.mIsTriming.set(true);
            this.mBuffersFastCache.trimToSize(this.mBuffersFastCache.size() / 2);
            this.mIsTriming.set(false);
        }
    }
}

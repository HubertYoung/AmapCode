package com.alipay.multimedia.apxmmusic;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheRecord;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APCacheReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.mobile.aspect.AliAspectCenter;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.common.utils.MD5Util;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.service.ext.security.AuthService;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.common.logging.MusicDownloadBehavior;
import com.alipay.multimedia.network.NetDownloader;
import com.alipay.multimedia.utils.AESUtils;
import com.alipay.multimedia.utils.MusicUtils;
import com.googlecode.androidannotations.api.BackgroundExecutor;
import com.taobao.wireless.security.sdk.SecurityGuardManager;
import com.taobao.wireless.security.sdk.staticdatastore.IStaticDataStoreComponent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.BitSet;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.Signature;
import org.aspectj.runtime.internal.AroundClosure;
import org.aspectj.runtime.internal.Conversions;
import org.aspectj.runtime.reflect.Factory;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class MusicFile extends InputStream {
    private static final int PIECE_SIZE = 65536;
    private static final int READ_SPEED = 655360;
    private static final String TAG = "MusicFile";
    private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_1 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_2 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_3 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_4 = null;
    private static final /* synthetic */ StaticPart ajc$tjp_5 = null;
    private static byte[] sCacheLock = new byte[0];
    private int mAlreadyReadBytes = 0;
    /* access modifiers changed from: private */
    public MusicDownloadBehavior mBehavior;
    /* access modifiers changed from: private */
    public int mBitCount = 0;
    /* access modifiers changed from: private */
    public BitSet mBitSet;
    private byte[] mBitSetLock = new byte[0];
    /* access modifiers changed from: private */
    public long mBitmapUpdateTimestamp = 0;
    /* access modifiers changed from: private */
    public File mCacheFile;
    /* access modifiers changed from: private */
    public long mContentLength = -1;
    private String mContentType = "audio/mpeg";
    private long mCurrentOffset = 0;
    /* access modifiers changed from: private */
    public int mCurrentReadIndex = 0;
    /* access modifiers changed from: private */
    public byte[] mDownloadBuffer = new byte[65536];
    /* access modifiers changed from: private */
    public byte[] mDownloadLock = new byte[0];
    /* access modifiers changed from: private */
    public RandomAccessFile mDownloadRandomFile;
    /* access modifiers changed from: private */
    public NetDownloader mDownloader = null;
    /* access modifiers changed from: private */
    public String mFileId;
    /* access modifiers changed from: private */
    public boolean mFinished = false;
    /* access modifiers changed from: private */
    public byte[] mInitLock = new byte[0];
    private String mKey;
    /* access modifiers changed from: private */
    public long mRangeStart = 0;
    private byte[] mReadBuffer = new byte[65536];
    /* access modifiers changed from: private */
    public byte[] mReadLock = new byte[0];
    private RandomAccessFile mReadRandomFile;
    private byte[] mReadSpeedLock = new byte[0];
    private long mReadStartTime = 0;
    /* access modifiers changed from: private */
    public String mRedirectUrl;
    private long mRefreshSessionTime = 0;
    /* access modifiers changed from: private */
    public int mStatus = -1;
    /* access modifiers changed from: private */
    public String mUrl;

    public class AjcClosure1 extends AroundClosure {
        public AjcClosure1(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    public class AjcClosure11 extends AroundClosure {
        public AjcClosure11(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    public class AjcClosure3 extends AroundClosure {
        public AjcClosure3(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    public class AjcClosure5 extends AroundClosure {
        public AjcClosure5(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    public class AjcClosure7 extends AroundClosure {
        public AjcClosure7(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    public class AjcClosure9 extends AroundClosure {
        public AjcClosure9(Object[] objArr) {
            super(objArr);
        }

        public Object run(Object[] objArr) {
            Object[] objArr2 = this.state;
            return Conversions.booleanObject(((File) objArr2[1]).delete());
        }
    }

    private class DataProcessor implements Runnable {
        private DataProcessor() {
        }

        /* JADX WARNING: CFG modification limit reached, blocks count: 400 */
        /* JADX WARNING: Code restructure failed: missing block: B:192:0x04af, code lost:
            if (r16 == 65536) goto L_0x0582;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:194:0x04c2, code lost:
            if ((((long) r16) + r6) == com.alipay.multimedia.apxmmusic.MusicFile.access$300(r30.this$0)) goto L_0x0582;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:195:0x04c4, code lost:
            com.alipay.multimedia.common.logging.MLog.e(com.alipay.multimedia.apxmmusic.MusicFile.TAG, "get music data error.len=" + r16);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:229:?, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$702(r30.this$0, com.alipay.multimedia.apxmmusic.MusicFile.access$700(r30.this$0) + ((long) (r16 - ((int) (com.alipay.multimedia.apxmmusic.MusicFile.access$700(r30.this$0) - r6)))));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:230:0x05b5, code lost:
            if (com.alipay.multimedia.apxmmusic.MusicFile.access$600(r30.this$0) != false) goto L_0x04de;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:234:0x05bf, code lost:
            if (com.alipay.multimedia.apxmmusic.MusicFile.access$1700(r30.this$0) != null) goto L_0x05db;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:235:0x05c1, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$1702(r30.this$0, new java.io.RandomAccessFile(com.alipay.multimedia.apxmmusic.MusicFile.access$1800(r30.this$0), "rw"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:236:0x05db, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$1700(r30.this$0).seek(r6);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:237:0x05ee, code lost:
            if (com.alipay.multimedia.apxmmusic.MusicFile.access$600(r30.this$0) == false) goto L_0x01cb;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:238:0x05f2, code lost:
            r14 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:241:?, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$202(r30.this$0, 500);
            com.alipay.multimedia.common.logging.MLog.e(com.alipay.multimedia.apxmmusic.MusicFile.TAG, "write file exp=" + r14.getMessage());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:257:0x06a9, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$702(r30.this$0, com.alipay.multimedia.apxmmusic.MusicFile.access$700(r30.this$0) + tv.danmaku.ijk.media.player.IjkMediaMeta.AV_CH_TOP_BACK_CENTER);
            com.alipay.multimedia.common.logging.MLog.i(com.alipay.multimedia.apxmmusic.MusicFile.TAG, "download music data.index=" + r15 + " cached. totalPieces=" + com.alipay.multimedia.apxmmusic.MusicFile.access$400(r30.this$0) + r30);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
            com.alipay.multimedia.apxmmusic.MusicFile.access$1700(r30.this$0).write(com.alipay.multimedia.apxmmusic.MusicFile.access$1600(r30.this$0), 0, r16);
            com.alipay.multimedia.apxmmusic.MusicFile.access$1900(r30.this$0, r15);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r30 = this;
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x0076 }
                r4.init()     // Catch:{ Throwable -> 0x0076 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                long r24 = r4.mContentLength
                r26 = 0
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 <= 0) goto L_0x003e
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                long r24 = r24.mContentLength
                r0 = r24
                double r0 = (double) r0
                r24 = r0
                r26 = 4607182418800017408(0x3ff0000000000000, double:1.0)
                double r24 = r24 * r26
                r26 = 4679240012837945344(0x40f0000000000000, double:65536.0)
                double r24 = r24 / r26
                double r24 = java.lang.Math.ceil(r24)
                r0 = r24
                int r0 = (int) r0
                r24 = r0
                r0 = r24
                r4.mBitCount = r0
            L_0x003e:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mInitLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0073 }
                byte[] r4 = r4.mInitLock     // Catch:{ all -> 0x0073 }
                r4.notifyAll()     // Catch:{ all -> 0x0073 }
                monitor-exit(r24)     // Catch:{ all -> 0x0073 }
            L_0x0053:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                boolean r4 = r4.mFinished     // Catch:{ Throwable -> 0x035d }
                if (r4 == 0) goto L_0x0147
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mReadLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0144 }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x0144 }
                r4.notifyAll()     // Catch:{ all -> 0x0144 }
                monitor-exit(r24)     // Catch:{ all -> 0x0144 }
            L_0x0072:
                return
            L_0x0073:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0073 }
                throw r4
            L_0x0076:
                r14 = move-exception
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ed }
                java.lang.String r25 = "init error.e="
                r24.<init>(r25)     // Catch:{ all -> 0x00ed }
                java.lang.String r25 = r14.getMessage()     // Catch:{ all -> 0x00ed }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x00ed }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x00ed }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ all -> 0x00ed }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x00ed }
                r24 = 500(0x1f4, float:7.0E-43)
                r0 = r24
                r4.mStatus = r0     // Catch:{ all -> 0x00ed }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                long r24 = r4.mContentLength
                r26 = 0
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 <= 0) goto L_0x00d3
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                long r24 = r24.mContentLength
                r0 = r24
                double r0 = (double) r0
                r24 = r0
                r26 = 4607182418800017408(0x3ff0000000000000, double:1.0)
                double r24 = r24 * r26
                r26 = 4679240012837945344(0x40f0000000000000, double:65536.0)
                double r24 = r24 / r26
                double r24 = java.lang.Math.ceil(r24)
                r0 = r24
                int r0 = (int) r0
                r24 = r0
                r0 = r24
                r4.mBitCount = r0
            L_0x00d3:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mInitLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x00ea }
                byte[] r4 = r4.mInitLock     // Catch:{ all -> 0x00ea }
                r4.notifyAll()     // Catch:{ all -> 0x00ea }
                monitor-exit(r24)     // Catch:{ all -> 0x00ea }
                goto L_0x0053
            L_0x00ea:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x00ea }
                throw r4
            L_0x00ed:
                r4 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                long r24 = r24.mContentLength
                r26 = 0
                int r24 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r24 <= 0) goto L_0x0127
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r25 = r0
                long r26 = r25.mContentLength
                r0 = r26
                double r0 = (double) r0
                r26 = r0
                r28 = 4607182418800017408(0x3ff0000000000000, double:1.0)
                double r26 = r26 * r28
                r28 = 4679240012837945344(0x40f0000000000000, double:65536.0)
                double r26 = r26 / r28
                double r26 = java.lang.Math.ceil(r26)
                r0 = r26
                int r0 = (int) r0
                r25 = r0
                r24.mBitCount = r25
            L_0x0127:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                byte[] r24 = r24.mInitLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0141 }
                r25 = r0
                byte[] r25 = r25.mInitLock     // Catch:{ all -> 0x0141 }
                r25.notifyAll()     // Catch:{ all -> 0x0141 }
                monitor-exit(r24)     // Catch:{ all -> 0x0141 }
                throw r4
            L_0x0141:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0141 }
                throw r4
            L_0x0144:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0144 }
                throw r4
            L_0x0147:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                int r4 = r4.mStatus     // Catch:{ Throwable -> 0x035d }
                r24 = 200(0xc8, float:2.8E-43)
                r0 = r24
                if (r4 == r0) goto L_0x0163
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                int r4 = r4.mStatus     // Catch:{ Throwable -> 0x035d }
                r24 = 206(0xce, float:2.89E-43)
                r0 = r24
                if (r4 != r0) goto L_0x0171
            L_0x0163:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                long r24 = r4.mContentLength     // Catch:{ Throwable -> 0x035d }
                r26 = 0
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 > 0) goto L_0x0244
            L_0x0171:
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x035d }
                java.lang.String r25 = "state wrong.mStatus="
                r24.<init>(r25)     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r25 = r0
                int r25 = r25.mStatus     // Catch:{ Throwable -> 0x035d }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r25 = ", mContentLength="
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r25 = r0
                long r26 = r25.mContentLength     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                r1 = r26
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                r1 = r30
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r24 = r24.toString()     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mReadLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x01c8 }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x01c8 }
                r4.notifyAll()     // Catch:{ all -> 0x01c8 }
                monitor-exit(r24)     // Catch:{ all -> 0x01c8 }
                goto L_0x0072
            L_0x01c8:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x01c8 }
                throw r4
            L_0x01cb:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                java.io.RandomAccessFile r4 = r4.mDownloadRandomFile     // Catch:{ Throwable -> 0x05f2 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                r24 = r0
                byte[] r24 = r24.mDownloadBuffer     // Catch:{ Throwable -> 0x05f2 }
                r25 = 0
                r0 = r24
                r1 = r25
                r2 = r16
                r4.write(r0, r1, r2)     // Catch:{ Throwable -> 0x05f2 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                r4.setCache(r15)     // Catch:{ Throwable -> 0x05f2 }
            L_0x01ef:
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = "download music data.index="
                r24.<init>(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                java.lang.StringBuilder r24 = r0.append(r15)     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = ",downloadTime="
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                java.lang.StringBuilder r24 = r0.append(r12)     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = ", totalPieces="
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r25 = r0
                int r25 = r25.mBitCount     // Catch:{ all -> 0x02b9 }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                r1 = r30
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x02b9 }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x02b9 }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.i(r4, r0)     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                byte[] r24 = r4.mReadLock     // Catch:{ all -> 0x02b9 }
                monitor-enter(r24)     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x06a6 }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x06a6 }
                r4.notifyAll()     // Catch:{ all -> 0x06a6 }
                monitor-exit(r24)     // Catch:{ all -> 0x06a6 }
            L_0x0244:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                long r24 = r4.mRangeStart     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                long r26 = r4.mContentLength     // Catch:{ all -> 0x02b9 }
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 >= 0) goto L_0x04de
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.mFinished     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x04de
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                long r24 = r4.mRangeStart     // Catch:{ all -> 0x02b9 }
                r26 = 65536(0x10000, double:3.2379E-319)
                long r24 = r24 / r26
                r0 = r24
                int r15 = (int) r0     // Catch:{ all -> 0x02b9 }
            L_0x0272:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                int r4 = r4.mCurrentReadIndex     // Catch:{ all -> 0x02b9 }
                if (r15 <= r4) goto L_0x03c0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                int r4 = r4.mCurrentReadIndex     // Catch:{ all -> 0x02b9 }
                int r4 = r15 - r4
                r24 = 10
                r0 = r24
                if (r4 <= r0) goto L_0x03c0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.mFinished     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x03c0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.errHappen()     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x03c0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                byte[] r24 = r4.mDownloadLock     // Catch:{ all -> 0x02b9 }
                monitor-enter(r24)     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ InterruptedException -> 0x03a3 }
                byte[] r4 = r4.mDownloadLock     // Catch:{ InterruptedException -> 0x03a3 }
                r4.wait()     // Catch:{ InterruptedException -> 0x03a3 }
            L_0x02b4:
                monitor-exit(r24)     // Catch:{ all -> 0x02b6 }
                goto L_0x0272
            L_0x02b6:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x02b6 }
                throw r4     // Catch:{ all -> 0x02b9 }
            L_0x02b9:
                r4 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                byte[] r24 = r24.mReadLock     // Catch:{ Throwable -> 0x035d }
                monitor-enter(r24)     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0732 }
                r25 = r0
                byte[] r25 = r25.mReadLock     // Catch:{ all -> 0x0732 }
                r25.notifyAll()     // Catch:{ all -> 0x0732 }
                monitor-exit(r24)     // Catch:{ all -> 0x0732 }
                long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                long r24 = r24.mBitmapUpdateTimestamp     // Catch:{ Throwable -> 0x035d }
                int r24 = (r10 > r24 ? 1 : (r10 == r24 ? 0 : -1))
                if (r24 < 0) goto L_0x02f7
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                long r24 = r24.mBitmapUpdateTimestamp     // Catch:{ Throwable -> 0x035d }
                long r24 = r10 - r24
                r26 = 1000(0x3e8, double:4.94E-321)
                int r24 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r24 <= 0) goto L_0x0338
            L_0x02f7:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                long r26 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                r1 = r26
                r0.mBitmapUpdateTimestamp = r1     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                r24.updateBitmapFile()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                java.util.BitSet r24 = r24.mBitSet     // Catch:{ Throwable -> 0x035d }
                int r24 = r24.cardinality()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r25 = r0
                int r25 = r25.mBitCount     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                r1 = r25
                if (r0 != r1) goto L_0x0338
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                r24.saveToCache()     // Catch:{ Throwable -> 0x035d }
            L_0x0338:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                java.io.RandomAccessFile r24 = r24.mDownloadRandomFile     // Catch:{ Throwable -> 0x035d }
                if (r24 == 0) goto L_0x035c
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x0735 }
                r24 = r0
                java.io.RandomAccessFile r24 = r24.mDownloadRandomFile     // Catch:{ Throwable -> 0x0735 }
                r24.close()     // Catch:{ Throwable -> 0x0735 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x0735 }
                r24 = r0
                r25 = 0
                r24.mDownloadRandomFile = r25     // Catch:{ Throwable -> 0x0735 }
            L_0x035c:
                throw r4     // Catch:{ Throwable -> 0x035d }
            L_0x035d:
                r14 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x06fa }
                r24 = 500(0x1f4, float:7.0E-43)
                r0 = r24
                r4.mStatus = r0     // Catch:{ all -> 0x06fa }
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x06fa }
                java.lang.String r25 = "exp happen exp="
                r24.<init>(r25)     // Catch:{ all -> 0x06fa }
                java.lang.String r25 = r14.getMessage()     // Catch:{ all -> 0x06fa }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x06fa }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x06fa }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ all -> 0x06fa }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mReadLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0750 }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x0750 }
                r4.notifyAll()     // Catch:{ all -> 0x0750 }
                monitor-exit(r24)     // Catch:{ all -> 0x0750 }
            L_0x0398:
                java.lang.String r4 = "MusicFile"
                java.lang.String r24 = "download thread end...."
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.i(r4, r0)
                goto L_0x0072
            L_0x03a3:
                r14 = move-exception
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b6 }
                java.lang.String r26 = "download lock exp="
                r25.<init>(r26)     // Catch:{ all -> 0x02b6 }
                java.lang.String r26 = r14.getMessage()     // Catch:{ all -> 0x02b6 }
                java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ all -> 0x02b6 }
                java.lang.String r25 = r25.toString()     // Catch:{ all -> 0x02b6 }
                r0 = r25
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ all -> 0x02b6 }
                goto L_0x02b4
            L_0x03c0:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.mFinished     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x04de
                r4 = 65536(0x10000, float:9.18355E-41)
                int r4 = r4 * r15
                long r6 = (long) r4     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.isCached(r15)     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x06a9
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                java.lang.String r5 = r4.mUrl     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                java.lang.String r4 = r4.mRedirectUrl     // Catch:{ all -> 0x02b9 }
                boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x03f6
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                java.lang.String r5 = r4.mRedirectUrl     // Catch:{ all -> 0x02b9 }
            L_0x03f6:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                com.alipay.multimedia.network.NetDownloader r4 = r4.mDownloader     // Catch:{ all -> 0x02b9 }
                if (r4 != 0) goto L_0x0422
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                com.alipay.multimedia.network.NetDownloader r24 = new com.alipay.multimedia.network.NetDownloader     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r25 = r0
                java.lang.String r25 = r25.mUrl     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r26 = r0
                java.lang.String r26 = r26.mFileId     // Catch:{ all -> 0x02b9 }
                r24.<init>(r25, r26)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                r4.mDownloader = r0     // Catch:{ all -> 0x02b9 }
            L_0x0422:
                r24 = 65536(0x10000, double:3.2379E-319)
                long r24 = r24 + r6
                r26 = 1
                long r8 = r24 - r26
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                long r24 = r4.mContentLength     // Catch:{ all -> 0x02b9 }
                r26 = 1
                long r24 = r24 - r26
                int r4 = (r8 > r24 ? 1 : (r8 == r24 ? 0 : -1))
                if (r4 <= 0) goto L_0x0447
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                long r24 = r4.mContentLength     // Catch:{ all -> 0x02b9 }
                r26 = 1
                long r8 = r24 - r26
            L_0x0447:
                long r20 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x02b9 }
                r12 = 0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                com.alipay.multimedia.network.NetDownloader r4 = r4.mDownloader     // Catch:{ all -> 0x02b9 }
                org.apache.http.HttpResponse r18 = r4.download(r5, r6, r8)     // Catch:{ all -> 0x02b9 }
                r22 = 500(0x1f4, float:7.0E-43)
                if (r18 == 0) goto L_0x046b
                org.apache.http.StatusLine r4 = r18.getStatusLine()     // Catch:{ all -> 0x02b9 }
                if (r4 == 0) goto L_0x046b
                org.apache.http.StatusLine r4 = r18.getStatusLine()     // Catch:{ all -> 0x02b9 }
                int r22 = r4.getStatusCode()     // Catch:{ all -> 0x02b9 }
            L_0x046b:
                r4 = 200(0xc8, float:2.8E-43)
                r0 = r22
                if (r0 == r4) goto L_0x0477
                r4 = 206(0xce, float:2.89E-43)
                r0 = r22
                if (r0 != r4) goto L_0x0642
            L_0x0477:
                r16 = 0
                org.apache.http.HttpEntity r4 = r18.getEntity()     // Catch:{ IOException -> 0x061a }
                java.io.InputStream r23 = r4.getContent()     // Catch:{ IOException -> 0x061a }
            L_0x0481:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                byte[] r4 = r4.mDownloadBuffer     // Catch:{ IOException -> 0x061a }
                r24 = 65536(0x10000, float:9.18355E-41)
                int r24 = r24 - r16
                r0 = r23
                r1 = r16
                r2 = r24
                int r19 = r0.read(r4, r1, r2)     // Catch:{ IOException -> 0x061a }
                r4 = -1
                r0 = r19
                if (r0 == r4) goto L_0x04a5
                r4 = 65536(0x10000, float:9.18355E-41)
                r0 = r16
                if (r0 >= r4) goto L_0x04a5
                int r16 = r16 + r19
                goto L_0x0481
            L_0x04a5:
                long r24 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x061a }
                long r12 = r24 - r20
                r4 = 65536(0x10000, float:9.18355E-41)
                r0 = r16
                if (r0 == r4) goto L_0x0582
                r0 = r16
                long r0 = (long) r0     // Catch:{ IOException -> 0x061a }
                r24 = r0
                long r24 = r24 + r6
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                long r26 = r4.mContentLength     // Catch:{ IOException -> 0x061a }
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 == 0) goto L_0x0582
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x061a }
                java.lang.String r25 = "get music data error.len="
                r24.<init>(r25)     // Catch:{ IOException -> 0x061a }
                r0 = r24
                r1 = r16
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ IOException -> 0x061a }
                java.lang.String r24 = r24.toString()     // Catch:{ IOException -> 0x061a }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ IOException -> 0x061a }
            L_0x04de:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                byte[] r24 = r4.mReadLock     // Catch:{ Throwable -> 0x035d }
                monitor-enter(r24)     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x06f7 }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x06f7 }
                r4.notifyAll()     // Catch:{ all -> 0x06f7 }
                monitor-exit(r24)     // Catch:{ all -> 0x06f7 }
                long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                long r24 = r4.mBitmapUpdateTimestamp     // Catch:{ Throwable -> 0x035d }
                int r4 = (r10 > r24 ? 1 : (r10 == r24 ? 0 : -1))
                if (r4 < 0) goto L_0x0513
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                long r24 = r4.mBitmapUpdateTimestamp     // Catch:{ Throwable -> 0x035d }
                long r24 = r10 - r24
                r26 = 1000(0x3e8, double:4.94E-321)
                int r4 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1))
                if (r4 <= 0) goto L_0x0548
            L_0x0513:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                long r24 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                r4.mBitmapUpdateTimestamp = r0     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r4.updateBitmapFile()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                java.util.BitSet r4 = r4.mBitSet     // Catch:{ Throwable -> 0x035d }
                int r4 = r4.cardinality()     // Catch:{ Throwable -> 0x035d }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r24 = r0
                int r24 = r24.mBitCount     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                if (r4 != r0) goto L_0x0548
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                r4.saveToCache()     // Catch:{ Throwable -> 0x035d }
            L_0x0548:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x035d }
                java.io.RandomAccessFile r4 = r4.mDownloadRandomFile     // Catch:{ Throwable -> 0x035d }
                if (r4 == 0) goto L_0x0568
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x0715 }
                java.io.RandomAccessFile r4 = r4.mDownloadRandomFile     // Catch:{ Throwable -> 0x0715 }
                r4.close()     // Catch:{ Throwable -> 0x0715 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x0715 }
                r24 = 0
                r0 = r24
                r4.mDownloadRandomFile = r0     // Catch:{ Throwable -> 0x0715 }
            L_0x0568:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this
                byte[] r24 = r4.mReadLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x057f }
                byte[] r4 = r4.mReadLock     // Catch:{ all -> 0x057f }
                r4.notifyAll()     // Catch:{ all -> 0x057f }
                monitor-exit(r24)     // Catch:{ all -> 0x057f }
                goto L_0x0398
            L_0x057f:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x057f }
                throw r4
            L_0x0582:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                long r24 = r4.mRangeStart     // Catch:{ IOException -> 0x061a }
                long r24 = r24 - r6
                r0 = r24
                int r0 = (int) r0     // Catch:{ IOException -> 0x061a }
                r17 = r0
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                r24 = r0
                long r24 = r24.mRangeStart     // Catch:{ IOException -> 0x061a }
                int r26 = r16 - r17
                r0 = r26
                long r0 = (long) r0     // Catch:{ IOException -> 0x061a }
                r26 = r0
                long r24 = r24 + r26
                r0 = r24
                r4.mRangeStart = r0     // Catch:{ IOException -> 0x061a }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                boolean r4 = r4.mFinished     // Catch:{ IOException -> 0x061a }
                if (r4 != 0) goto L_0x04de
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                java.io.RandomAccessFile r4 = r4.mDownloadRandomFile     // Catch:{ Throwable -> 0x05f2 }
                if (r4 != 0) goto L_0x05db
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                java.io.RandomAccessFile r24 = new java.io.RandomAccessFile     // Catch:{ Throwable -> 0x05f2 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                r25 = r0
                java.io.File r25 = r25.mCacheFile     // Catch:{ Throwable -> 0x05f2 }
                java.lang.String r26 = "rw"
                r24.<init>(r25, r26)     // Catch:{ Throwable -> 0x05f2 }
                r0 = r24
                r4.mDownloadRandomFile = r0     // Catch:{ Throwable -> 0x05f2 }
            L_0x05db:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                java.io.RandomAccessFile r4 = r4.mDownloadRandomFile     // Catch:{ Throwable -> 0x05f2 }
                r4.seek(r6)     // Catch:{ Throwable -> 0x05f2 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ Throwable -> 0x05f2 }
                boolean r4 = r4.mFinished     // Catch:{ Throwable -> 0x05f2 }
                if (r4 == 0) goto L_0x01cb
                goto L_0x04de
            L_0x05f2:
                r14 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ IOException -> 0x061a }
                r24 = 500(0x1f4, float:7.0E-43)
                r0 = r24
                r4.mStatus = r0     // Catch:{ IOException -> 0x061a }
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x061a }
                java.lang.String r25 = "write file exp="
                r24.<init>(r25)     // Catch:{ IOException -> 0x061a }
                java.lang.String r25 = r14.getMessage()     // Catch:{ IOException -> 0x061a }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ IOException -> 0x061a }
                java.lang.String r24 = r24.toString()     // Catch:{ IOException -> 0x061a }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ IOException -> 0x061a }
                goto L_0x04de
            L_0x061a:
                r14 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r24 = 500(0x1f4, float:7.0E-43)
                r0 = r24
                r4.mStatus = r0     // Catch:{ all -> 0x02b9 }
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = "download io exp="
                r24.<init>(r25)     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = r14.getMessage()     // Catch:{ all -> 0x02b9 }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x02b9 }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ all -> 0x02b9 }
                goto L_0x04de
            L_0x0642:
                r4 = 401(0x191, float:5.62E-43)
                r0 = r22
                if (r0 != r4) goto L_0x065b
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                boolean r4 = r4.canRefreshSession()     // Catch:{ all -> 0x02b9 }
                if (r4 == 0) goto L_0x065b
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r4.refreshSession()     // Catch:{ all -> 0x02b9 }
                goto L_0x01ef
            L_0x065b:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                com.alipay.multimedia.common.logging.MusicDownloadBehavior r4 = r4.mBehavior     // Catch:{ all -> 0x02b9 }
                r24 = 8
                r0 = r24
                r4.result = r0     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                com.alipay.multimedia.common.logging.MusicDownloadBehavior r4 = r4.mBehavior     // Catch:{ all -> 0x02b9 }
                r0 = r22
                r4.status = r0     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r0 = r22
                r4.mStatus = r0     // Catch:{ all -> 0x02b9 }
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = "piece "
                r24.<init>(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                java.lang.StringBuilder r24 = r0.append(r15)     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = " status="
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                r1 = r22
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x02b9 }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x02b9 }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ all -> 0x02b9 }
                goto L_0x04de
            L_0x06a6:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x06a6 }
                throw r4     // Catch:{ all -> 0x02b9 }
            L_0x06a9:
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r4 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r24 = r0
                long r24 = r24.mRangeStart     // Catch:{ all -> 0x02b9 }
                r26 = 65536(0x10000, double:3.2379E-319)
                long r24 = r24 + r26
                r0 = r24
                r4.mRangeStart = r0     // Catch:{ all -> 0x02b9 }
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = "download music data.index="
                r24.<init>(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                java.lang.StringBuilder r24 = r0.append(r15)     // Catch:{ all -> 0x02b9 }
                java.lang.String r25 = " cached. totalPieces="
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x02b9 }
                r25 = r0
                int r25 = r25.mBitCount     // Catch:{ all -> 0x02b9 }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ all -> 0x02b9 }
                r0 = r24
                r1 = r30
                java.lang.StringBuilder r24 = r0.append(r1)     // Catch:{ all -> 0x02b9 }
                java.lang.String r24 = r24.toString()     // Catch:{ all -> 0x02b9 }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.i(r4, r0)     // Catch:{ all -> 0x02b9 }
                goto L_0x0244
            L_0x06f7:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x06f7 }
                throw r4     // Catch:{ Throwable -> 0x035d }
            L_0x06fa:
                r4 = move-exception
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this
                r24 = r0
                byte[] r24 = r24.mReadLock
                monitor-enter(r24)
                r0 = r30
                com.alipay.multimedia.apxmmusic.MusicFile r0 = com.alipay.multimedia.apxmmusic.MusicFile.this     // Catch:{ all -> 0x0753 }
                r25 = r0
                byte[] r25 = r25.mReadLock     // Catch:{ all -> 0x0753 }
                r25.notifyAll()     // Catch:{ all -> 0x0753 }
                monitor-exit(r24)     // Catch:{ all -> 0x0753 }
                throw r4
            L_0x0715:
                r14 = move-exception
                java.lang.String r4 = "MusicFile"
                java.lang.StringBuilder r24 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x035d }
                java.lang.String r25 = "close download file fail.e="
                r24.<init>(r25)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r25 = r14.getMessage()     // Catch:{ Throwable -> 0x035d }
                java.lang.StringBuilder r24 = r24.append(r25)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r24 = r24.toString()     // Catch:{ Throwable -> 0x035d }
                r0 = r24
                com.alipay.multimedia.common.logging.MLog.e(r4, r0)     // Catch:{ Throwable -> 0x035d }
                goto L_0x0568
            L_0x0732:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0732 }
                throw r4     // Catch:{ Throwable -> 0x035d }
            L_0x0735:
                r14 = move-exception
                java.lang.String r24 = "MusicFile"
                java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x035d }
                java.lang.String r26 = "close download file fail.e="
                r25.<init>(r26)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r26 = r14.getMessage()     // Catch:{ Throwable -> 0x035d }
                java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ Throwable -> 0x035d }
                java.lang.String r25 = r25.toString()     // Catch:{ Throwable -> 0x035d }
                com.alipay.multimedia.common.logging.MLog.e(r24, r25)     // Catch:{ Throwable -> 0x035d }
                goto L_0x035c
            L_0x0750:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0750 }
                throw r4
            L_0x0753:
                r4 = move-exception
                monitor-exit(r24)     // Catch:{ all -> 0x0753 }
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.apxmmusic.MusicFile.DataProcessor.run():void");
        }
    }

    private static /* synthetic */ void ajc$preClinit() {
        Factory factory = new Factory("MusicFile.java", MusicFile.class);
        ajc$tjp_0 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 184);
        ajc$tjp_1 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 206);
        ajc$tjp_2 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 209);
        ajc$tjp_3 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 234);
        ajc$tjp_4 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 237);
        ajc$tjp_5 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), (int) SecExceptionCode.SEC_ERROR_PKG_VALID_UNKNOWN_ERR);
    }

    static {
        ajc$preClinit();
    }

    public MusicFile(String url, String fileId, long rangeStart, MusicDownloadBehavior behavior) {
        this.mUrl = url;
        this.mFileId = fileId;
        this.mRangeStart = rangeStart;
        this.mCurrentOffset = this.mRangeStart;
        this.mBehavior = behavior;
        new Thread(new DataProcessor(), "MusicDownload").start();
    }

    public static Context getApplicationContext() {
        return getMicroApplicationContext().getApplicationContext();
    }

    public static MicroApplicationContext getMicroApplicationContext() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    /* access modifiers changed from: private */
    public String getCachePath() {
        String cacheKey = this.mFileId;
        if (MusicUtils.isEncrptedMusic(this.mFileId)) {
            AuthService authService = (AuthService) getMicroApplicationContext().findServiceByInterface(AuthService.class.getName());
            String uid = null;
            if (!(authService == null || authService.getUserInfo() == null)) {
                uid = authService.getUserInfo().getUserId();
            }
            if (!TextUtils.isEmpty(uid)) {
                cacheKey = this.mFileId + "_" + uid;
            }
        }
        if (TextUtils.isEmpty(cacheKey)) {
            cacheKey = MD5Util.getMD5String(this.mUrl);
        }
        return getCacheDir() + File.separator + cacheKey + "_65536";
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0212 A[SYNTHETIC, Splitter:B:64:0x0212] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0217 A[Catch:{ Throwable -> 0x021b }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x024e A[SYNTHETIC, Splitter:B:74:0x024e] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0253 A[Catch:{ Throwable -> 0x0257 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void tryInitFromCache() {
        /*
            r28 = this;
            r0 = r28
            java.lang.String r11 = r0.mFileId
            r0 = r28
            java.lang.String r0 = r0.mFileId
            r21 = r0
            boolean r21 = com.alipay.multimedia.utils.MusicUtils.isEncrptedMusic(r21)
            if (r21 == 0) goto L_0x0059
            com.alipay.mobile.framework.MicroApplicationContext r21 = getMicroApplicationContext()
            java.lang.Class<com.alipay.mobile.framework.service.ext.security.AuthService> r22 = com.alipay.mobile.framework.service.ext.security.AuthService.class
            java.lang.String r22 = r22.getName()
            java.lang.Object r4 = r21.findServiceByInterface(r22)
            com.alipay.mobile.framework.service.ext.security.AuthService r4 = (com.alipay.mobile.framework.service.ext.security.AuthService) r4
            r20 = 0
            if (r4 == 0) goto L_0x0032
            com.alipay.mobile.framework.service.ext.security.bean.UserInfo r21 = r4.getUserInfo()
            if (r21 == 0) goto L_0x0032
            com.alipay.mobile.framework.service.ext.security.bean.UserInfo r21 = r4.getUserInfo()
            java.lang.String r20 = r21.getUserId()
        L_0x0032:
            boolean r21 = android.text.TextUtils.isEmpty(r20)
            if (r21 != 0) goto L_0x0059
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            r0 = r28
            java.lang.String r0 = r0.mFileId
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r22 = "_"
            java.lang.StringBuilder r21 = r21.append(r22)
            r0 = r21
            r1 = r20
            java.lang.StringBuilder r21 = r0.append(r1)
            java.lang.String r11 = r21.toString()
        L_0x0059:
            boolean r21 = android.text.TextUtils.isEmpty(r11)
            if (r21 == 0) goto L_0x0069
            r0 = r28
            java.lang.String r0 = r0.mUrl
            r21 = r0
            java.lang.String r11 = com.alipay.mobile.common.utils.MD5Util.getMD5String(r21)
        L_0x0069:
            java.lang.String r8 = r28.getCacheDir()
            java.io.File r21 = new java.io.File
            r0 = r21
            r0.<init>(r8)
            java.io.File[] r17 = r21.listFiles()
            if (r17 == 0) goto L_0x00e2
            byte[] r22 = sCacheLock
            monitor-enter(r22)
            r0 = r17
            int r0 = r0.length     // Catch:{ all -> 0x01d2 }
            r23 = r0
            r21 = 0
        L_0x0084:
            r0 = r21
            r1 = r23
            if (r0 >= r1) goto L_0x00e1
            r13 = r17[r21]     // Catch:{ all -> 0x01d2 }
            java.lang.String r16 = r13.getName()     // Catch:{ all -> 0x01d2 }
            boolean r24 = android.text.TextUtils.isEmpty(r16)     // Catch:{ all -> 0x01d2 }
            if (r24 != 0) goto L_0x00de
            r0 = r16
            boolean r24 = r0.contains(r11)     // Catch:{ all -> 0x01d2 }
            if (r24 != 0) goto L_0x00de
            org.aspectj.lang.JoinPoint$StaticPart r24 = ajc$tjp_0     // Catch:{ all -> 0x01d2 }
            r0 = r24
            r1 = r28
            org.aspectj.lang.JoinPoint r24 = org.aspectj.runtime.reflect.Factory.makeJP(r0, r1, r13)     // Catch:{ all -> 0x01d2 }
            com.alipay.mobile.aspect.AliAspectCenter r25 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()     // Catch:{ all -> 0x01d2 }
            r26 = 3
            r0 = r26
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x01d2 }
            r26 = r0
            r27 = 0
            r26[r27] = r28     // Catch:{ all -> 0x01d2 }
            r27 = 1
            r26[r27] = r13     // Catch:{ all -> 0x01d2 }
            r27 = 2
            r26[r27] = r24     // Catch:{ all -> 0x01d2 }
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure1 r24 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure1     // Catch:{ all -> 0x01d2 }
            r0 = r24
            r1 = r26
            r0.<init>(r1)     // Catch:{ all -> 0x01d2 }
            r26 = 4112(0x1010, float:5.762E-42)
            r0 = r24
            r1 = r26
            org.aspectj.lang.ProceedingJoinPoint r24 = r0.linkClosureAndJoinPoint(r1)     // Catch:{ all -> 0x01d2 }
            r0 = r25
            r1 = r24
            java.lang.Object r24 = r0.doAspect(r1)     // Catch:{ all -> 0x01d2 }
            org.aspectj.runtime.internal.Conversions.booleanValue(r24)     // Catch:{ all -> 0x01d2 }
        L_0x00de:
            int r21 = r21 + 1
            goto L_0x0084
        L_0x00e1:
            monitor-exit(r22)     // Catch:{ all -> 0x01d2 }
        L_0x00e2:
            java.lang.String r10 = r28.getCachePath()
            java.lang.StringBuilder r21 = new java.lang.StringBuilder
            r21.<init>()
            r0 = r21
            java.lang.StringBuilder r21 = r0.append(r10)
            java.lang.String r22 = ".bitmap"
            java.lang.StringBuilder r21 = r21.append(r22)
            java.lang.String r7 = r21.toString()
            java.io.File r9 = new java.io.File
            r9.<init>(r10)
            java.io.File r6 = new java.io.File
            r6.<init>(r7)
            boolean r21 = r9.exists()
            if (r21 == 0) goto L_0x026f
            boolean r21 = r6.exists()
            if (r21 == 0) goto L_0x026f
            r14 = 0
            r18 = 0
            java.io.FileInputStream r15 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0307 }
            r15.<init>(r6)     // Catch:{ Throwable -> 0x0307 }
            java.io.ObjectInputStream r19 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x030a, all -> 0x02fd }
            r0 = r19
            r0.<init>(r15)     // Catch:{ Throwable -> 0x030a, all -> 0x02fd }
            java.lang.Object r5 = r19.readObject()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            java.util.BitSet r5 = (java.util.BitSet) r5     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            if (r5 == 0) goto L_0x013f
            int r21 = r5.length()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            int r21 = r21 + -1
            r22 = 65536(0x10000, float:9.18355E-41)
            int r21 = r21 * r22
            r0 = r21
            long r0 = (long) r0     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r22 = r0
            long r24 = r9.length()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            int r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
            if (r21 <= 0) goto L_0x01d5
        L_0x013f:
            boolean r21 = r9.exists()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            if (r21 == 0) goto L_0x0185
            org.aspectj.lang.JoinPoint$StaticPart r21 = ajc$tjp_1     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r21
            r1 = r28
            org.aspectj.lang.JoinPoint r21 = org.aspectj.runtime.reflect.Factory.makeJP(r0, r1, r9)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            com.alipay.mobile.aspect.AliAspectCenter r22 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = 3
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = r0
            r24 = 0
            r23[r24] = r28     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r24 = 1
            r23[r24] = r9     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r24 = 2
            r23[r24] = r21     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure3 r21 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure3     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r21
            r1 = r23
            r0.<init>(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = 4112(0x1010, float:5.762E-42)
            r0 = r21
            r1 = r23
            org.aspectj.lang.ProceedingJoinPoint r21 = r0.linkClosureAndJoinPoint(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r22
            r1 = r21
            java.lang.Object r21 = r0.doAspect(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            org.aspectj.runtime.internal.Conversions.booleanValue(r21)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
        L_0x0185:
            boolean r21 = r6.exists()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            if (r21 == 0) goto L_0x01cb
            org.aspectj.lang.JoinPoint$StaticPart r21 = ajc$tjp_2     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r21
            r1 = r28
            org.aspectj.lang.JoinPoint r21 = org.aspectj.runtime.reflect.Factory.makeJP(r0, r1, r6)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            com.alipay.mobile.aspect.AliAspectCenter r22 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = 3
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = r0
            r24 = 0
            r23[r24] = r28     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r24 = 1
            r23[r24] = r6     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r24 = 2
            r23[r24] = r21     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure5 r21 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure5     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r21
            r1 = r23
            r0.<init>(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r23 = 4112(0x1010, float:5.762E-42)
            r0 = r21
            r1 = r23
            org.aspectj.lang.ProceedingJoinPoint r21 = r0.linkClosureAndJoinPoint(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r22
            r1 = r21
            java.lang.Object r21 = r0.doAspect(r1)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            org.aspectj.runtime.internal.Conversions.booleanValue(r21)     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
        L_0x01cb:
            r15.close()     // Catch:{ Throwable -> 0x0233 }
            r19.close()     // Catch:{ Throwable -> 0x0233 }
        L_0x01d1:
            return
        L_0x01d2:
            r21 = move-exception
            monitor-exit(r22)     // Catch:{ all -> 0x01d2 }
            throw r21
        L_0x01d5:
            r0 = r28
            r0.mBitSet = r5     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r28
            r0.mCacheFile = r9     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r28
            java.io.File r0 = r0.mCacheFile     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r21 = r0
            long r22 = r21.length()     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r0 = r22
            r2 = r28
            r2.mContentLength = r0     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            r21 = 200(0xc8, float:2.8E-43)
            r0 = r21
            r1 = r28
            r1.mStatus = r0     // Catch:{ Throwable -> 0x01f6, all -> 0x0301 }
            goto L_0x01cb
        L_0x01f6:
            r12 = move-exception
            r18 = r19
            r14 = r15
        L_0x01fa:
            java.lang.String r21 = "MusicFile"
            java.lang.StringBuilder r22 = new java.lang.StringBuilder     // Catch:{ all -> 0x024b }
            java.lang.String r23 = "init from cache exp="
            r22.<init>(r23)     // Catch:{ all -> 0x024b }
            r0 = r22
            java.lang.StringBuilder r22 = r0.append(r12)     // Catch:{ all -> 0x024b }
            java.lang.String r22 = r22.toString()     // Catch:{ all -> 0x024b }
            com.alipay.multimedia.common.logging.MLog.e(r21, r22)     // Catch:{ all -> 0x024b }
            if (r14 == 0) goto L_0x0215
            r14.close()     // Catch:{ Throwable -> 0x021b }
        L_0x0215:
            if (r18 == 0) goto L_0x01d1
            r18.close()     // Catch:{ Throwable -> 0x021b }
            goto L_0x01d1
        L_0x021b:
            r12 = move-exception
            java.lang.String r21 = "MusicFile"
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            java.lang.String r23 = "close inputstream exp="
            r22.<init>(r23)
            r0 = r22
            java.lang.StringBuilder r22 = r0.append(r12)
            java.lang.String r22 = r22.toString()
            com.alipay.multimedia.common.logging.MLog.e(r21, r22)
            goto L_0x01d1
        L_0x0233:
            r12 = move-exception
            java.lang.String r21 = "MusicFile"
            java.lang.StringBuilder r22 = new java.lang.StringBuilder
            java.lang.String r23 = "close inputstream exp="
            r22.<init>(r23)
            r0 = r22
            java.lang.StringBuilder r22 = r0.append(r12)
            java.lang.String r22 = r22.toString()
            com.alipay.multimedia.common.logging.MLog.e(r21, r22)
            goto L_0x01d1
        L_0x024b:
            r21 = move-exception
        L_0x024c:
            if (r14 == 0) goto L_0x0251
            r14.close()     // Catch:{ Throwable -> 0x0257 }
        L_0x0251:
            if (r18 == 0) goto L_0x0256
            r18.close()     // Catch:{ Throwable -> 0x0257 }
        L_0x0256:
            throw r21
        L_0x0257:
            r12 = move-exception
            java.lang.String r22 = "MusicFile"
            java.lang.StringBuilder r23 = new java.lang.StringBuilder
            java.lang.String r24 = "close inputstream exp="
            r23.<init>(r24)
            r0 = r23
            java.lang.StringBuilder r23 = r0.append(r12)
            java.lang.String r23 = r23.toString()
            com.alipay.multimedia.common.logging.MLog.e(r22, r23)
            goto L_0x0256
        L_0x026f:
            boolean r21 = r9.exists()
            if (r21 == 0) goto L_0x02b5
            org.aspectj.lang.JoinPoint$StaticPart r21 = ajc$tjp_3
            r0 = r21
            r1 = r28
            org.aspectj.lang.JoinPoint r21 = org.aspectj.runtime.reflect.Factory.makeJP(r0, r1, r9)
            com.alipay.mobile.aspect.AliAspectCenter r22 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()
            r23 = 3
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r23 = r0
            r24 = 0
            r23[r24] = r28
            r24 = 1
            r23[r24] = r9
            r24 = 2
            r23[r24] = r21
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure7 r21 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure7
            r0 = r21
            r1 = r23
            r0.<init>(r1)
            r23 = 4112(0x1010, float:5.762E-42)
            r0 = r21
            r1 = r23
            org.aspectj.lang.ProceedingJoinPoint r21 = r0.linkClosureAndJoinPoint(r1)
            r0 = r22
            r1 = r21
            java.lang.Object r21 = r0.doAspect(r1)
            org.aspectj.runtime.internal.Conversions.booleanValue(r21)
        L_0x02b5:
            boolean r21 = r6.exists()
            if (r21 == 0) goto L_0x01d1
            org.aspectj.lang.JoinPoint$StaticPart r21 = ajc$tjp_4
            r0 = r21
            r1 = r28
            org.aspectj.lang.JoinPoint r21 = org.aspectj.runtime.reflect.Factory.makeJP(r0, r1, r6)
            com.alipay.mobile.aspect.AliAspectCenter r22 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()
            r23 = 3
            r0 = r23
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r23 = r0
            r24 = 0
            r23[r24] = r28
            r24 = 1
            r23[r24] = r6
            r24 = 2
            r23[r24] = r21
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure9 r21 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure9
            r0 = r21
            r1 = r23
            r0.<init>(r1)
            r23 = 4112(0x1010, float:5.762E-42)
            r0 = r21
            r1 = r23
            org.aspectj.lang.ProceedingJoinPoint r21 = r0.linkClosureAndJoinPoint(r1)
            r0 = r22
            r1 = r21
            java.lang.Object r21 = r0.doAspect(r1)
            org.aspectj.runtime.internal.Conversions.booleanValue(r21)
            goto L_0x01d1
        L_0x02fd:
            r21 = move-exception
            r14 = r15
            goto L_0x024c
        L_0x0301:
            r21 = move-exception
            r18 = r19
            r14 = r15
            goto L_0x024c
        L_0x0307:
            r12 = move-exception
            goto L_0x01fa
        L_0x030a:
            r12 = move-exception
            r14 = r15
            goto L_0x01fa
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.apxmmusic.MusicFile.tryInitFromCache():void");
    }

    private String getCacheDir() {
        String cacheDirPath = getApplicationContext().getCacheDir().getPath() + File.separator + "mmmusic";
        File cacheDir = new File(cacheDirPath);
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
        return cacheDirPath;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0012  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void createFixedLengthFile(java.io.File r3, long r4) {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ all -> 0x000f }
            java.lang.String r2 = "rw"
            r1.<init>(r3, r2)     // Catch:{ all -> 0x000f }
            r1.setLength(r4)     // Catch:{ all -> 0x0016 }
            r1.close()
            return
        L_0x000f:
            r2 = move-exception
        L_0x0010:
            if (r0 == 0) goto L_0x0015
            r0.close()
        L_0x0015:
            throw r2
        L_0x0016:
            r2 = move-exception
            r0 = r1
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.apxmmusic.MusicFile.createFixedLengthFile(java.io.File, long):void");
    }

    private boolean verify(String sign, String source) {
        String encryptKey = null;
        SecurityGuardManager sgMgr = SecurityGuardManager.getInstance(getApplicationContext());
        if (sgMgr != null) {
            IStaticDataStoreComponent sdsComp = sgMgr.getStaticDataStoreComp();
            if (sdsComp != null) {
                encryptKey = sdsComp.getExtraData("mdaeskey");
            }
        }
        if (!TextUtils.isEmpty(encryptKey)) {
            String decryptStr = AESUtils.decryptStr(encryptKey, sign);
            if (decryptStr != null && decryptStr.equals(source)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void init() {
        MultimediaFileService service = (MultimediaFileService) getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName());
        APFileQueryResult result = null;
        if (TextUtils.isEmpty(this.mFileId)) {
            result = service.queryCacheFile(this.mUrl);
        } else if (MusicUtils.isEncrptedMusic(this.mFileId)) {
            AuthService authService = (AuthService) getMicroApplicationContext().findServiceByInterface(AuthService.class.getName());
            String uid = null;
            if (!(authService == null || authService.getUserInfo() == null)) {
                uid = authService.getUserInfo().getUserId();
            }
            if (!TextUtils.isEmpty(uid)) {
                String key = this.mFileId + "_" + uid;
                result = service.queryCacheFile(key);
                if (result != null) {
                    APCacheRecord record = service.queryCacheRecord(key);
                    if (record != null) {
                        String extra = record.extra;
                        if (!TextUtils.isEmpty(extra)) {
                            String sign = null;
                            try {
                                sign = JSON.parseObject(extra).getString("sign");
                            } catch (Throwable th) {
                                MLog.e(TAG, "parse json failed.extra=" + extra);
                            }
                            if (TextUtils.isEmpty(sign) || !verify(sign, key)) {
                                result = null;
                                MLog.e(TAG, "local verify failed.sign=" + sign);
                            }
                        } else {
                            result = null;
                        }
                    } else {
                        result = null;
                    }
                }
            }
        } else {
            result = service.queryCacheFile(this.mFileId);
        }
        MLog.i(TAG, "query file cache result=" + result + this);
        if (result == null || !result.success || TextUtils.isEmpty(result.path)) {
            try {
                tryInitFromCache();
            } catch (Exception e) {
                MLog.e(TAG, "tryInitFromCache.exp=" + e);
            }
            if (this.mCacheFile == null) {
                MLog.i(TAG, "temp cache also not exist.");
                if (this.mDownloader == null) {
                    this.mDownloader = new NetDownloader(this.mUrl, this.mFileId);
                }
                getFileInfoFromNet();
                if (this.mStatus == 401 && canRefreshSession()) {
                    refreshSession();
                    getFileInfoFromNet();
                    return;
                }
                return;
            }
            MLog.i(TAG, "init from temp cache.");
            return;
        }
        this.mCacheFile = new File(result.path);
        this.mContentLength = this.mCacheFile.length();
        this.mStatus = 200;
        int count = (int) Math.ceil((((double) this.mContentLength) * 1.0d) / 65536.0d);
        this.mBitSet = new BitSet(count);
        this.mBitSet.set(0, count);
    }

    /* access modifiers changed from: private */
    public boolean canRefreshSession() {
        if (System.currentTimeMillis() - this.mRefreshSessionTime > 300000) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void refreshSession() {
        String domain = ReadSettingServerUrl.getInstance().getGWFURL(getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        MLog.i(TAG, "before refreshSession.fileId=" + this.mFileId + ",cookie=" + cookieManager.getCookie(domain));
        ((AuthService) getMicroApplicationContext().findServiceByInterface(AuthService.class.getName())).rpcAuth();
        this.mRefreshSessionTime = System.currentTimeMillis();
        MLog.i(TAG, "after refreshSession.fileId=" + this.mFileId + ",cookie=" + cookieManager.getCookie(domain));
    }

    public int read() {
        return 0;
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        byte[] data;
        if (this.mReadStartTime == 0) {
            this.mReadStartTime = System.currentTimeMillis();
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime >= this.mReadStartTime) {
            if ((((double) this.mAlreadyReadBytes) * 1.0d) / ((double) (currentTime - this.mReadStartTime)) > 655360.0d) {
                synchronized (this.mReadSpeedLock) {
                    try {
                        this.mReadSpeedLock.wait(1000);
                    } catch (InterruptedException e) {
                        r0 = TAG;
                        MLog.e(TAG, "read speed lock exp=" + e.getMessage());
                    }
                }
            }
        } else {
            this.mReadStartTime = currentTime;
        }
        int index = (int) (this.mCurrentOffset / IjkMediaMeta.AV_CH_TOP_BACK_CENTER);
        if (index >= this.mBitCount) {
            MLog.i(TAG, "read music data.index=" + index + ",return -1,totalPieces=" + this.mBitCount + this);
            finish();
            return -1;
        }
        this.mCurrentReadIndex = index;
        synchronized (this.mDownloadLock) {
            this.mDownloadLock.notifyAll();
        }
        while (!isCached(index)) {
            if (this.mFinished || errHappen()) {
                MLog.i(TAG, "task is end.");
                finish();
                throw new IOException("task is end..");
            }
            synchronized (this.mReadLock) {
                try {
                    this.mReadLock.wait();
                } catch (InterruptedException e2) {
                    r0 = TAG;
                    MLog.e(TAG, "InterruptedException.e=" + e2.getMessage());
                }
            }
        }
        int len = 0;
        long pieceOffset = (long) (65536 * index);
        int bufOffset = (int) (this.mCurrentOffset - pieceOffset);
        try {
            if (this.mReadRandomFile == null) {
                this.mReadRandomFile = new RandomAccessFile(this.mCacheFile, "rw");
            }
            this.mReadRandomFile.seek(pieceOffset);
            while (true) {
                int ret = this.mReadRandomFile.read(this.mReadBuffer, len, 65536 - len);
                if (ret != -1 && len < 65536) {
                    len += ret;
                }
            }
            if (len == 65536 || ((long) len) + pieceOffset == this.mContentLength) {
                int dataLen = 0;
                if (MusicUtils.isEncrptedMusic(this.mFileId)) {
                    this.mBehavior.encrypt = 1;
                    data = decrypt(this.mReadBuffer, len);
                    if (data != null) {
                        dataLen = data.length;
                    }
                } else {
                    data = this.mReadBuffer;
                    dataLen = len;
                }
                if (data == null) {
                    MLog.e(TAG, "read music data.index=" + index + " data is null decrypt fail");
                    this.mBehavior.result = 9;
                    finish();
                    throw new IOException("decrypt fail.");
                } else if (bufOffset >= dataLen) {
                    MLog.i(TAG, "read music data from file.index=" + index + ",return -1,bufOffset=" + bufOffset + ",data.length=" + data.length + this);
                    finish();
                    return -1;
                } else {
                    int min = Math.min(dataLen - bufOffset, byteCount);
                    System.arraycopy(data, bufOffset, buffer, byteOffset, min);
                    this.mCurrentOffset += (long) min;
                    MLog.i(TAG, "read music data from file.index=" + index + this);
                    return min;
                }
            } else {
                MLog.e(TAG, "read music data error.len=" + len);
                finish();
                throw new IOException("data len is wrong.");
            }
        } catch (FileNotFoundException e3) {
            MLog.e(TAG, "file io FileNotFoundException.exp=" + e3.getMessage());
            finish();
            throw new IOException("FileNotFoundException.");
        } catch (IOException e4) {
            MLog.e(TAG, "file io IOException.exp=" + e4.getMessage());
            finish();
            throw new IOException("IOException.");
        }
    }

    /* access modifiers changed from: private */
    public void saveToCache() {
        MultimediaFileService service = (MultimediaFileService) getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName());
        APCacheReq req = new APCacheReq();
        if (MusicUtils.isEncrptedMusic(this.mFileId)) {
            AuthService authService = (AuthService) getMicroApplicationContext().findServiceByInterface(AuthService.class.getName());
            String uid = null;
            if (!(authService == null || authService.getUserInfo() == null)) {
                uid = authService.getUserInfo().getUserId();
            }
            if (TextUtils.isEmpty(uid)) {
                uid = "";
            }
            String key = this.mFileId + "_" + uid;
            req.cloudId = key;
            String encryptKey = null;
            SecurityGuardManager sgMgr = SecurityGuardManager.getInstance(getApplicationContext());
            if (sgMgr != null) {
                IStaticDataStoreComponent sdsComp = sgMgr.getStaticDataStoreComp();
                if (sdsComp != null) {
                    encryptKey = sdsComp.getExtraData("mdaeskey");
                }
            }
            if (!TextUtils.isEmpty(encryptKey)) {
                String sign = AESUtils.encryptStr(encryptKey, key);
                JSONObject jsonObject = new JSONObject();
                if (!TextUtils.isEmpty(sign)) {
                    jsonObject.put((String) "sign", (Object) sign);
                }
                req.extra = jsonObject.toJSONString();
            }
        } else {
            req.cloudId = this.mFileId;
        }
        req.srcPath = this.mCacheFile.getPath();
        req.businessId = "mmmusic";
        synchronized (sCacheLock) {
            MLog.i(TAG, "saveToCache.fileId=" + req.cloudId + ",path=" + req.srcPath);
            service.saveToCache(req);
        }
    }

    private byte[] decrypt(byte[] src, int len) {
        if (TextUtils.isEmpty(this.mKey)) {
            SecurityGuardManager sgMgr = SecurityGuardManager.getInstance(getApplicationContext());
            if (sgMgr == null) {
                return null;
            }
            IStaticDataStoreComponent sdsComp = sgMgr.getStaticDataStoreComp();
            if (sdsComp == null) {
                return null;
            }
            String extraData = sdsComp.getExtraData("mmmusic");
            if (extraData == null) {
                return null;
            }
            this.mKey = extraData;
        }
        byte[] buf = src;
        if (src.length != len) {
            buf = new byte[len];
            System.arraycopy(src, 0, buf, 0, len);
        }
        return MusicUtils.decryptData(buf, this.mKey, true);
    }

    /* access modifiers changed from: private */
    public boolean isCached(int index) {
        boolean z;
        synchronized (this.mBitSetLock) {
            if (this.mBitSet.get(index)) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void setCache(int index) {
        synchronized (this.mBitSetLock) {
            this.mBitSet.set(index);
        }
    }

    private void getFileInfoFromNet() {
        long start = System.currentTimeMillis();
        HttpResponse response = this.mDownloader.getHead();
        int status = 500;
        if (!(response == null || response.getStatusLine() == null)) {
            status = response.getStatusLine().getStatusCode();
        }
        long contentLength = -1;
        String contentType = null;
        if (status == 301 || status == 302) {
            Header[] headers = response.getAllHeaders();
            MLog.i(TAG, "getFileInfoFromNet redirect.headers=" + headers);
            if (headers != null) {
                int length = headers.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Header header = headers[i];
                    if ("Location".equalsIgnoreCase(header.getName())) {
                        this.mRedirectUrl = header.getValue();
                        break;
                    }
                    i++;
                }
            }
            if (!TextUtils.isEmpty(this.mRedirectUrl)) {
                response = this.mDownloader.getHead(this.mRedirectUrl);
                if (!(response == null || response.getStatusLine() == null)) {
                    status = response.getStatusLine().getStatusCode();
                }
            }
        }
        if (status == 200 || status == 206) {
            Header[] headers2 = response.getAllHeaders();
            if (headers2 != null) {
                int length2 = headers2.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    Header header2 = headers2[i2];
                    MLog.i(TAG, "getFileInfoFromNet.headers.key=" + header2.getName() + ",value=" + header2.getValue());
                    if ("Content-Range".equalsIgnoreCase(header2.getName())) {
                        String value = header2.getValue();
                        if (!TextUtils.isEmpty(value)) {
                            String[] array = value.split("/");
                            if (array.length > 1) {
                                try {
                                    contentLength = Long.parseLong(array[1]);
                                } catch (Throwable e) {
                                    MLog.e(TAG, "parse contentLength error.e=" + e + ",value=" + value);
                                }
                            }
                        }
                    } else if ("Content-Type".equalsIgnoreCase(header2.getName())) {
                        contentType = header2.getValue();
                    }
                }
            }
        }
        MLog.i(TAG, "getFileInfoFromNet.status=" + status + ",contentLength=" + contentLength + ",contentType=" + contentType + ",costTime=" + (System.currentTimeMillis() - start) + this);
        synchronized (this.mInitLock) {
            this.mStatus = status;
            this.mContentLength = contentLength;
            this.mContentType = contentType;
            if (this.mContentLength > 0) {
                BitSet bitSet = new BitSet((int) Math.ceil((((double) this.mContentLength) * 1.0d) / 65536.0d));
                this.mBitSet = bitSet;
                File file = new File(getCachePath());
                this.mCacheFile = file;
                try {
                    createFixedLengthFile(this.mCacheFile, this.mContentLength);
                } catch (IOException e2) {
                    this.mStatus = 500;
                    this.mBehavior.result = 7;
                    MLog.e(TAG, "create cache file exp=" + e2);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean errHappen() {
        if (this.mStatus == 200 || this.mStatus == 206) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x009b A[SYNTHETIC, Splitter:B:23:0x009b] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a0 A[Catch:{ Throwable -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bd A[SYNTHETIC, Splitter:B:31:0x00bd] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c2 A[Catch:{ Throwable -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateBitmapFile() {
        /*
            r12 = this;
            java.lang.String r2 = r12.getCachePath()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r2)
            java.lang.String r9 = ".bitmap"
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r1 = r8.toString()
            java.io.File r0 = new java.io.File
            r0.<init>(r1)
            boolean r8 = r0.exists()
            if (r8 == 0) goto L_0x004a
            org.aspectj.lang.JoinPoint$StaticPart r8 = ajc$tjp_5
            org.aspectj.lang.JoinPoint r8 = org.aspectj.runtime.reflect.Factory.makeJP(r8, r12, r0)
            com.alipay.mobile.aspect.AliAspectCenter r9 = com.alipay.mobile.aspect.AliAspectCenter.aspectOf()
            r10 = 3
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r11 = 0
            r10[r11] = r12
            r11 = 1
            r10[r11] = r0
            r11 = 2
            r10[r11] = r8
            com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure11 r8 = new com.alipay.multimedia.apxmmusic.MusicFile$AjcClosure11
            r8.<init>(r10)
            r10 = 4112(0x1010, float:5.762E-42)
            org.aspectj.lang.ProceedingJoinPoint r8 = r8.linkClosureAndJoinPoint(r10)
            java.lang.Object r8 = r9.doAspect(r8)
            org.aspectj.runtime.internal.Conversions.booleanValue(r8)
        L_0x004a:
            r0.createNewFile()     // Catch:{ IOException -> 0x0065 }
            r4 = 0
            r6 = 0
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0084 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x0084 }
            java.io.ObjectOutputStream r7 = new java.io.ObjectOutputStream     // Catch:{ Throwable -> 0x00e3, all -> 0x00dc }
            r7.<init>(r5)     // Catch:{ Throwable -> 0x00e3, all -> 0x00dc }
            java.util.BitSet r8 = r12.mBitSet     // Catch:{ Throwable -> 0x00e6, all -> 0x00df }
            r7.writeObject(r8)     // Catch:{ Throwable -> 0x00e6, all -> 0x00df }
            r5.close()     // Catch:{ Throwable -> 0x006e }
            r7.close()     // Catch:{ Throwable -> 0x006e }
        L_0x0064:
            return
        L_0x0065:
            r8 = move-exception
            java.lang.String r8 = "MusicFile"
            java.lang.String r9 = "create bitmap file failed."
            com.alipay.multimedia.common.logging.MLog.e(r8, r9)
            goto L_0x0064
        L_0x006e:
            r3 = move-exception
            java.lang.String r8 = "MusicFile"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "close inputstream exp="
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r3)
            java.lang.String r9 = r9.toString()
            com.alipay.multimedia.common.logging.MLog.e(r8, r9)
            goto L_0x0064
        L_0x0084:
            r3 = move-exception
        L_0x0085:
            java.lang.String r8 = "MusicFile"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ba }
            java.lang.String r10 = "write bitset exp="
            r9.<init>(r10)     // Catch:{ all -> 0x00ba }
            java.lang.StringBuilder r9 = r9.append(r3)     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00ba }
            com.alipay.multimedia.common.logging.MLog.e(r8, r9)     // Catch:{ all -> 0x00ba }
            if (r4 == 0) goto L_0x009e
            r4.close()     // Catch:{ Throwable -> 0x00a4 }
        L_0x009e:
            if (r6 == 0) goto L_0x0064
            r6.close()     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x0064
        L_0x00a4:
            r3 = move-exception
            java.lang.String r8 = "MusicFile"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "close inputstream exp="
            r9.<init>(r10)
            java.lang.StringBuilder r9 = r9.append(r3)
            java.lang.String r9 = r9.toString()
            com.alipay.multimedia.common.logging.MLog.e(r8, r9)
            goto L_0x0064
        L_0x00ba:
            r8 = move-exception
        L_0x00bb:
            if (r4 == 0) goto L_0x00c0
            r4.close()     // Catch:{ Throwable -> 0x00c6 }
        L_0x00c0:
            if (r6 == 0) goto L_0x00c5
            r6.close()     // Catch:{ Throwable -> 0x00c6 }
        L_0x00c5:
            throw r8
        L_0x00c6:
            r3 = move-exception
            java.lang.String r9 = "MusicFile"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "close inputstream exp="
            r10.<init>(r11)
            java.lang.StringBuilder r10 = r10.append(r3)
            java.lang.String r10 = r10.toString()
            com.alipay.multimedia.common.logging.MLog.e(r9, r10)
            goto L_0x00c5
        L_0x00dc:
            r8 = move-exception
            r4 = r5
            goto L_0x00bb
        L_0x00df:
            r8 = move-exception
            r6 = r7
            r4 = r5
            goto L_0x00bb
        L_0x00e3:
            r3 = move-exception
            r4 = r5
            goto L_0x0085
        L_0x00e6:
            r3 = move-exception
            r6 = r7
            r4 = r5
            goto L_0x0085
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.apxmmusic.MusicFile.updateBitmapFile():void");
    }

    public void finish() {
        MLog.i(TAG, "finish...." + this);
        if (this.mFinished) {
            MLog.i(TAG, "already finished...");
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                MLog.i(TAG, "close read random file..");
                if (this.mReadRandomFile != null) {
                    try {
                        this.mReadRandomFile.close();
                        this.mReadRandomFile = null;
                    } catch (IOException e) {
                        MLog.i(TAG, "close read random file fail.e=" + e.getMessage());
                    }
                }
            }
        } else {
            this.mFinished = true;
            synchronized (this.mInitLock) {
                this.mInitLock.notifyAll();
            }
            synchronized (this.mReadSpeedLock) {
                this.mReadSpeedLock.notifyAll();
            }
            synchronized (this.mReadLock) {
                this.mReadLock.notifyAll();
            }
            synchronized (this.mDownloadLock) {
                this.mDownloadLock.notifyAll();
            }
            if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                MLog.i(TAG, "close read random file..");
                if (this.mReadRandomFile != null) {
                    try {
                        this.mReadRandomFile.close();
                        this.mReadRandomFile = null;
                    } catch (IOException e2) {
                        MLog.i(TAG, "close read random file fail.e=" + e2.getMessage());
                    }
                }
            }
            this.mBehavior.submit();
        }
    }

    public long getContentLength() {
        synchronized (this.mInitLock) {
            if (this.mContentLength <= 0 && this.mStatus <= 0) {
                try {
                    this.mInitLock.wait();
                } catch (InterruptedException e) {
                    MLog.e(TAG, "getContentLength.exp=" + e);
                }
            }
        }
        return this.mContentLength;
    }

    public String getContentType() {
        synchronized (this.mInitLock) {
            if (TextUtils.isEmpty(this.mContentType) && this.mStatus <= 0) {
                try {
                    this.mInitLock.wait();
                } catch (InterruptedException e) {
                    MLog.e(TAG, "getContentType.exp=" + e);
                }
            }
        }
        return this.mContentType;
    }

    public int getStatus() {
        synchronized (this.mInitLock) {
            if (this.mStatus <= 0) {
                try {
                    this.mInitLock.wait();
                } catch (InterruptedException e) {
                    MLog.e(TAG, "getStatus.exp=" + e);
                }
            }
        }
        return this.mStatus;
    }

    public int getErrorCode() {
        return this.mStatus;
    }

    public void onError() {
        BackgroundExecutor.execute((Runnable) new Runnable() {
            private static final /* synthetic */ StaticPart ajc$tjp_0 = null;
            private static final /* synthetic */ StaticPart ajc$tjp_1 = null;

            /* renamed from: com.alipay.multimedia.apxmmusic.MusicFile$1$AjcClosure1 */
            public class AjcClosure1 extends AroundClosure {
                public AjcClosure1(Object[] objArr) {
                    super(objArr);
                }

                public Object run(Object[] objArr) {
                    Object[] objArr2 = this.state;
                    return Conversions.booleanObject(((File) objArr2[1]).delete());
                }
            }

            /* renamed from: com.alipay.multimedia.apxmmusic.MusicFile$1$AjcClosure3 */
            public class AjcClosure3 extends AroundClosure {
                public AjcClosure3(Object[] objArr) {
                    super(objArr);
                }

                public Object run(Object[] objArr) {
                    Object[] objArr2 = this.state;
                    return Conversions.booleanObject(((File) objArr2[1]).delete());
                }
            }

            static {
                ajc$preClinit();
            }

            private static /* synthetic */ void ajc$preClinit() {
                Factory factory = new Factory("MusicFile.java", AnonymousClass1.class);
                ajc$tjp_0 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 1033);
                ajc$tjp_1 = factory.makeSJP((String) JoinPoint.METHOD_CALL, (Signature) factory.makeMethodSig((String) "1", (String) "delete", (String) "java.io.File", (String) "", (String) "", (String) "", (String) "boolean"), 1036);
            }

            public void run() {
                if (MusicFile.this.mStatus == 200) {
                    try {
                        MLog.i(MusicFile.TAG, "onError delete cache file" + this);
                        String cacheFilePath = MusicFile.this.getCachePath();
                        String bitmapFilePath = cacheFilePath + ".bitmap";
                        File cacheFile = new File(cacheFilePath);
                        File bitmapFile = new File(bitmapFilePath);
                        if (cacheFile.exists()) {
                            JoinPoint makeJP = Factory.makeJP(ajc$tjp_0, this, cacheFile);
                            Conversions.booleanValue(AliAspectCenter.aspectOf().doAspect(new AjcClosure1(new Object[]{this, cacheFile, makeJP}).linkClosureAndJoinPoint(4112)));
                        }
                        if (bitmapFile.exists()) {
                            JoinPoint makeJP2 = Factory.makeJP(ajc$tjp_1, this, bitmapFile);
                            Conversions.booleanValue(AliAspectCenter.aspectOf().doAspect(new AjcClosure3(new Object[]{this, bitmapFile, makeJP2}).linkClosureAndJoinPoint(4112)));
                        }
                    } catch (Throwable e) {
                        MLog.e(MusicFile.TAG, "onError.e=" + e);
                    }
                } else {
                    MLog.i(MusicFile.TAG, "onError but code is not 200, so donot delete temp cache.");
                }
            }
        });
    }
}

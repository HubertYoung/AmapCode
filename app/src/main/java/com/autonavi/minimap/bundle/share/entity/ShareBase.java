package com.autonavi.minimap.bundle.share.entity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.server.ShortURLResponser;

public abstract class ShareBase {
    protected boolean mCancleTask = false;
    private dcz mProgressDialog;

    class MyShortUrlListener implements Callback<ShortURLResponser> {
        private MyShortUrlListener() {
        }

        /* synthetic */ MyShortUrlListener(ShareBase shareBase, byte b) {
            this();
        }

        public void error(Throwable th, boolean z) {
            String str;
            StringBuffer stringBuffer = new StringBuffer("MyShortUrlListener error:");
            if (th == null) {
                str = null;
            } else {
                str = th.getMessage();
            }
            stringBuffer.append(str);
            ddl.a("ShareBase", stringBuffer.toString());
            ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.share_request_shorturl_fail));
            ddi.a().b();
            ShareBase.this.dismissProgressDialog();
        }

        public void callback(ShortURLResponser shortURLResponser) {
            StringBuffer stringBuffer = new StringBuffer("MyShortUrlListener callback cancelTask");
            stringBuffer.append(ShareBase.this.mCancleTask);
            stringBuffer.append("  responser.mErrorMsg:");
            stringBuffer.append(shortURLResponser == null ? null : shortURLResponser.mErrorMsg);
            ddl.a("ShareBase", stringBuffer.toString());
            ShareBase.this.dismissProgressDialog();
            if (ShareBase.this.mCancleTask) {
                ddi.a().b();
                ShareBase.this.mCancleTask = false;
            } else if (shortURLResponser.mErrorMsg != null) {
                ShareBase.this.onFinishResult(null);
            } else {
                ShareBase.this.onFinishResult(shortURLResponser.value_url);
            }
        }
    }

    public abstract int getShareType();

    public abstract void onFinishResult(String str);

    public abstract void startShare();

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c A[SYNTHETIC, Splitter:B:25:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0076 A[SYNTHETIC, Splitter:B:31:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0086 A[SYNTHETIC, Splitter:B:39:0x0086] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:28:0x0071=Splitter:B:28:0x0071, B:22:0x0067=Splitter:B:22:0x0067} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String compressBitmapToTempFile(android.graphics.Bitmap r6, boolean r7) {
        /*
            r5 = this;
            if (r6 == 0) goto L_0x008f
            boolean r0 = r6.isRecycled()
            if (r0 == 0) goto L_0x000a
            goto L_0x008f
        L_0x000a:
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG
            r2 = 90
            r6.compress(r1, r2, r0)
            byte[] r0 = r0.toByteArray()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.lang.String r2 = com.amap.bundle.blutils.FileUtil.getTmpFilePath(r2)
            r1.append(r2)
            java.lang.String r2 = "/temp_share.jpg"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            java.io.File r4 = r3.getParentFile()     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            boolean r4 = r4.exists()     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            if (r4 != 0) goto L_0x004a
            java.io.File r4 = r3.getParentFile()     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            r4.mkdirs()     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
        L_0x004a:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            r4.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0070, IOException -> 0x0066 }
            r2 = 0
            int r3 = r0.length     // Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005e, all -> 0x005b }
            r4.write(r0, r2, r3)     // Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005e, all -> 0x005b }
            r4.flush()     // Catch:{ FileNotFoundException -> 0x0061, IOException -> 0x005e, all -> 0x005b }
            r4.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x007e
        L_0x005b:
            r6 = move-exception
            r2 = r4
            goto L_0x0084
        L_0x005e:
            r0 = move-exception
            r2 = r4
            goto L_0x0067
        L_0x0061:
            r0 = move-exception
            r2 = r4
            goto L_0x0071
        L_0x0064:
            r6 = move-exception
            goto L_0x0084
        L_0x0066:
            r0 = move-exception
        L_0x0067:
            r0.printStackTrace()     // Catch:{ all -> 0x0064 }
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x007e
        L_0x0070:
            r0 = move-exception
        L_0x0071:
            r0.printStackTrace()     // Catch:{ all -> 0x0064 }
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ IOException -> 0x007a }
            goto L_0x007e
        L_0x007a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x007e:
            if (r7 == 0) goto L_0x0083
            r6.recycle()
        L_0x0083:
            return r1
        L_0x0084:
            if (r2 == 0) goto L_0x008e
            r2.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x008e
        L_0x008a:
            r7 = move-exception
            r7.printStackTrace()
        L_0x008e:
            throw r6
        L_0x008f:
            java.lang.String r6 = ""
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.bundle.share.entity.ShareBase.compressBitmapToTempFile(android.graphics.Bitmap, boolean):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void requestShortUrl(String str, String str2) {
        ddl.a("ShareBase", "requestShortUrl:".concat(String.valueOf(str)));
        showProgressDialog(AMapPageUtil.getAppContext().getString(R.string.share_base_request_content));
        bnw.a("1", str, str2, new MyShortUrlListener(this, 0));
    }

    /* access modifiers changed from: protected */
    public void showProgressDialog(String str) {
        try {
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new dcz(AMapAppGlobal.getTopActivity(), str, "");
                this.mProgressDialog.setCancelable(true);
                this.mProgressDialog.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        ShareBase.this.mCancleTask = true;
                    }
                });
            }
            this.mCancleTask = false;
            this.mProgressDialog.a(str);
            if (!this.mProgressDialog.isShowing()) {
                this.mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void dismissProgressDialog() {
        if (this.mProgressDialog != null) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    public static String getAjxStorageItem(String str) {
        return AMapAppGlobal.getApplication().getSharedPreferences("AJX", 0).getString(str, "");
    }

    /* access modifiers changed from: protected */
    public void notifyShareResult(int i) {
        ddi.a().a(getShareType(), i);
        ddi.a().b();
    }
}

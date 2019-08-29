package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    static final class PrintHelperKitkatImpl implements PrintHelperVersionImpl {
        private final PrintHelperKitkat a;

        PrintHelperKitkatImpl(Context context) {
            this.a = new PrintHelperKitkat(context);
        }

        public final void a(int i) {
            this.a.c = i;
        }

        public final int a() {
            return this.a.c;
        }

        public final void b(int i) {
            this.a.d = i;
        }

        public final int b() {
            return this.a.d;
        }

        public final void c(int i) {
            this.a.e = i;
        }

        public final int c() {
            return this.a.e;
        }

        public final void a(String str, Bitmap bitmap, final OnPrintFinishCallback onPrintFinishCallback) {
            android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback r6 = onPrintFinishCallback != null ? new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                public void onFinish() {
                    onPrintFinishCallback.onFinish();
                }
            } : null;
            PrintHelperKitkat printHelperKitkat = this.a;
            if (bitmap != null) {
                int i = printHelperKitkat.c;
                PrintManager printManager = (PrintManager) printHelperKitkat.a.getSystemService("print");
                MediaSize mediaSize = MediaSize.UNKNOWN_PORTRAIT;
                if (bitmap.getWidth() > bitmap.getHeight()) {
                    mediaSize = MediaSize.UNKNOWN_LANDSCAPE;
                }
                PrintAttributes build = new Builder().setMediaSize(mediaSize).setColorMode(printHelperKitkat.d).build();
                AnonymousClass1 r1 = new PrintDocumentAdapter(str, bitmap, i, r6) {
                    final /* synthetic */ String a;
                    final /* synthetic */ Bitmap b;
                    final /* synthetic */ int c;
                    final /* synthetic */ OnPrintFinishCallback d;
                    private PrintAttributes f;

                    {
                        this.a = r2;
                        this.b = r3;
                        this.c = r4;
                        this.d = r5;
                    }

                    public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                        this.f = printAttributes2;
                        layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.a).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
                    }

                    /* JADX WARNING: Can't wrap try/catch for region: R(10:1|2|3|4|5|6|7|(2:9|10)|11|(2:14|15)(1:16)) */
                    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0059 */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void onWrite(android.print.PageRange[] r7, android.os.ParcelFileDescriptor r8, android.os.CancellationSignal r9, android.print.PrintDocumentAdapter.WriteResultCallback r10) {
                        /*
                            r6 = this;
                            android.print.pdf.PrintedPdfDocument r7 = new android.print.pdf.PrintedPdfDocument
                            android.support.v4.print.PrintHelperKitkat r9 = android.support.v4.print.PrintHelperKitkat.this
                            android.content.Context r9 = r9.a
                            android.print.PrintAttributes r0 = r6.f
                            r7.<init>(r9, r0)
                            android.graphics.Bitmap r9 = r6.b
                            android.print.PrintAttributes r0 = r6.f
                            int r0 = r0.getColorMode()
                            android.graphics.Bitmap r9 = android.support.v4.print.PrintHelperKitkat.a(r9, r0)
                            r0 = 1
                            android.graphics.pdf.PdfDocument$Page r1 = r7.startPage(r0)     // Catch:{ all -> 0x006d }
                            android.graphics.RectF r2 = new android.graphics.RectF     // Catch:{ all -> 0x006d }
                            android.graphics.pdf.PdfDocument$PageInfo r3 = r1.getInfo()     // Catch:{ all -> 0x006d }
                            android.graphics.Rect r3 = r3.getContentRect()     // Catch:{ all -> 0x006d }
                            r2.<init>(r3)     // Catch:{ all -> 0x006d }
                            int r3 = r9.getWidth()     // Catch:{ all -> 0x006d }
                            int r4 = r9.getHeight()     // Catch:{ all -> 0x006d }
                            int r5 = r6.c     // Catch:{ all -> 0x006d }
                            android.graphics.Matrix r2 = android.support.v4.print.PrintHelperKitkat.a(r3, r4, r2, r5)     // Catch:{ all -> 0x006d }
                            android.graphics.Canvas r3 = r1.getCanvas()     // Catch:{ all -> 0x006d }
                            r4 = 0
                            r3.drawBitmap(r9, r2, r4)     // Catch:{ all -> 0x006d }
                            r7.finishPage(r1)     // Catch:{ all -> 0x006d }
                            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0059 }
                            java.io.FileDescriptor r2 = r8.getFileDescriptor()     // Catch:{ IOException -> 0x0059 }
                            r1.<init>(r2)     // Catch:{ IOException -> 0x0059 }
                            r7.writeTo(r1)     // Catch:{ IOException -> 0x0059 }
                            android.print.PageRange[] r0 = new android.print.PageRange[r0]     // Catch:{ IOException -> 0x0059 }
                            r1 = 0
                            android.print.PageRange r2 = android.print.PageRange.ALL_PAGES     // Catch:{ IOException -> 0x0059 }
                            r0[r1] = r2     // Catch:{ IOException -> 0x0059 }
                            r10.onWriteFinished(r0)     // Catch:{ IOException -> 0x0059 }
                            goto L_0x005c
                        L_0x0059:
                            r10.onWriteFailed(r4)     // Catch:{ all -> 0x006d }
                        L_0x005c:
                            r7.close()
                            if (r8 == 0) goto L_0x0064
                            r8.close()     // Catch:{ IOException -> 0x0064 }
                        L_0x0064:
                            android.graphics.Bitmap r7 = r6.b
                            if (r9 == r7) goto L_0x006c
                            r9.recycle()
                            return
                        L_0x006c:
                            return
                        L_0x006d:
                            r10 = move-exception
                            r7.close()
                            if (r8 == 0) goto L_0x0076
                            r8.close()     // Catch:{ IOException -> 0x0076 }
                        L_0x0076:
                            android.graphics.Bitmap r7 = r6.b
                            if (r9 == r7) goto L_0x007d
                            r9.recycle()
                        L_0x007d:
                            throw r10
                        */
                        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.print.PrintHelperKitkat.AnonymousClass1.onWrite(android.print.PageRange[], android.os.ParcelFileDescriptor, android.os.CancellationSignal, android.print.PrintDocumentAdapter$WriteResultCallback):void");
                    }

                    public void onFinish() {
                        if (this.d != null) {
                            this.d.onFinish();
                        }
                    }
                };
                printManager.print(str, r1, build);
            }
        }

        public final void a(String str, Uri uri, final OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
            AnonymousClass2 r0 = onPrintFinishCallback != null ? new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                public void onFinish() {
                    onPrintFinishCallback.onFinish();
                }
            } : null;
            PrintHelperKitkat printHelperKitkat = this.a;
            AnonymousClass2 r1 = new PrintDocumentAdapter(str, uri, r0, printHelperKitkat.c) {
                AsyncTask<Uri, Boolean, Bitmap> a;
                Bitmap b = null;
                final /* synthetic */ String c;
                final /* synthetic */ Uri d;
                final /* synthetic */ OnPrintFinishCallback e;
                final /* synthetic */ int f;
                private PrintAttributes h;

                {
                    this.c = r2;
                    this.d = r3;
                    this.e = r4;
                    this.f = r5;
                }

                public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, LayoutResultCallback layoutResultCallback, Bundle bundle) {
                    this.h = printAttributes2;
                    if (cancellationSignal.isCanceled()) {
                        layoutResultCallback.onLayoutCancelled();
                    } else if (this.b != null) {
                        layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.c).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
                    } else {
                        final CancellationSignal cancellationSignal2 = cancellationSignal;
                        final PrintAttributes printAttributes3 = printAttributes2;
                        final PrintAttributes printAttributes4 = printAttributes;
                        final LayoutResultCallback layoutResultCallback2 = layoutResultCallback;
                        AnonymousClass1 r0 = new AsyncTask<Uri, Boolean, Bitmap>() {
                            /* access modifiers changed from: protected */
                            public /* synthetic */ Object doInBackground(Object[] objArr) {
                                return a();
                            }

                            /* access modifiers changed from: protected */
                            public /* synthetic */ void onPostExecute(Object obj) {
                                Bitmap bitmap = (Bitmap) obj;
                                super.onPostExecute(bitmap);
                                AnonymousClass2.this.b = bitmap;
                                if (bitmap != null) {
                                    layoutResultCallback2.onLayoutFinished(new PrintDocumentInfo.Builder(AnonymousClass2.this.c).setContentType(1).setPageCount(1).build(), true ^ printAttributes3.equals(printAttributes4));
                                } else {
                                    layoutResultCallback2.onLayoutFailed(null);
                                }
                                AnonymousClass2.this.a = null;
                            }

                            /* access modifiers changed from: protected */
                            public void onPreExecute() {
                                cancellationSignal2.setOnCancelListener(new OnCancelListener() {
                                    public void onCancel() {
                                        AnonymousClass2.this.a();
                                        AnonymousClass1.this.cancel(false);
                                    }
                                });
                            }

                            private Bitmap a() {
                                try {
                                    return PrintHelperKitkat.this.a(AnonymousClass2.this.d);
                                } catch (FileNotFoundException unused) {
                                    return null;
                                }
                            }

                            /* access modifiers changed from: protected */
                            public /* synthetic */ void onCancelled(Object obj) {
                                layoutResultCallback2.onLayoutCancelled();
                                AnonymousClass2.this.a = null;
                            }
                        };
                        this.a = r0.execute(new Uri[0]);
                    }
                }

                /* access modifiers changed from: private */
                public void a() {
                    synchronized (PrintHelperKitkat.this.f) {
                        if (PrintHelperKitkat.this.b != null) {
                            PrintHelperKitkat.this.b.requestCancelDecode();
                            PrintHelperKitkat.this.b = null;
                        }
                    }
                }

                public void onFinish() {
                    super.onFinish();
                    a();
                    if (this.a != null) {
                        this.a.cancel(true);
                    }
                    if (this.e != null) {
                        this.e.onFinish();
                    }
                    if (this.b != null) {
                        this.b.recycle();
                        this.b = null;
                    }
                }

                /* JADX WARNING: Can't wrap try/catch for region: R(10:1|2|3|4|5|6|7|(2:9|10)|11|(2:14|15)(1:16)) */
                /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x005d */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void onWrite(android.print.PageRange[] r7, android.os.ParcelFileDescriptor r8, android.os.CancellationSignal r9, android.print.PrintDocumentAdapter.WriteResultCallback r10) {
                    /*
                        r6 = this;
                        android.print.pdf.PrintedPdfDocument r7 = new android.print.pdf.PrintedPdfDocument
                        android.support.v4.print.PrintHelperKitkat r9 = android.support.v4.print.PrintHelperKitkat.this
                        android.content.Context r9 = r9.a
                        android.print.PrintAttributes r0 = r6.h
                        r7.<init>(r9, r0)
                        android.graphics.Bitmap r9 = r6.b
                        android.print.PrintAttributes r0 = r6.h
                        int r0 = r0.getColorMode()
                        android.graphics.Bitmap r9 = android.support.v4.print.PrintHelperKitkat.a(r9, r0)
                        r0 = 1
                        android.graphics.pdf.PdfDocument$Page r1 = r7.startPage(r0)     // Catch:{ all -> 0x0071 }
                        android.graphics.RectF r2 = new android.graphics.RectF     // Catch:{ all -> 0x0071 }
                        android.graphics.pdf.PdfDocument$PageInfo r3 = r1.getInfo()     // Catch:{ all -> 0x0071 }
                        android.graphics.Rect r3 = r3.getContentRect()     // Catch:{ all -> 0x0071 }
                        r2.<init>(r3)     // Catch:{ all -> 0x0071 }
                        android.graphics.Bitmap r3 = r6.b     // Catch:{ all -> 0x0071 }
                        int r3 = r3.getWidth()     // Catch:{ all -> 0x0071 }
                        android.graphics.Bitmap r4 = r6.b     // Catch:{ all -> 0x0071 }
                        int r4 = r4.getHeight()     // Catch:{ all -> 0x0071 }
                        int r5 = r6.f     // Catch:{ all -> 0x0071 }
                        android.graphics.Matrix r2 = android.support.v4.print.PrintHelperKitkat.a(r3, r4, r2, r5)     // Catch:{ all -> 0x0071 }
                        android.graphics.Canvas r3 = r1.getCanvas()     // Catch:{ all -> 0x0071 }
                        r4 = 0
                        r3.drawBitmap(r9, r2, r4)     // Catch:{ all -> 0x0071 }
                        r7.finishPage(r1)     // Catch:{ all -> 0x0071 }
                        java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x005d }
                        java.io.FileDescriptor r2 = r8.getFileDescriptor()     // Catch:{ IOException -> 0x005d }
                        r1.<init>(r2)     // Catch:{ IOException -> 0x005d }
                        r7.writeTo(r1)     // Catch:{ IOException -> 0x005d }
                        android.print.PageRange[] r0 = new android.print.PageRange[r0]     // Catch:{ IOException -> 0x005d }
                        r1 = 0
                        android.print.PageRange r2 = android.print.PageRange.ALL_PAGES     // Catch:{ IOException -> 0x005d }
                        r0[r1] = r2     // Catch:{ IOException -> 0x005d }
                        r10.onWriteFinished(r0)     // Catch:{ IOException -> 0x005d }
                        goto L_0x0060
                    L_0x005d:
                        r10.onWriteFailed(r4)     // Catch:{ all -> 0x0071 }
                    L_0x0060:
                        r7.close()
                        if (r8 == 0) goto L_0x0068
                        r8.close()     // Catch:{ IOException -> 0x0068 }
                    L_0x0068:
                        android.graphics.Bitmap r7 = r6.b
                        if (r9 == r7) goto L_0x0070
                        r9.recycle()
                        return
                    L_0x0070:
                        return
                    L_0x0071:
                        r10 = move-exception
                        r7.close()
                        if (r8 == 0) goto L_0x007a
                        r8.close()     // Catch:{ IOException -> 0x007a }
                    L_0x007a:
                        android.graphics.Bitmap r7 = r6.b
                        if (r9 == r7) goto L_0x0081
                        r9.recycle()
                    L_0x0081:
                        throw r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: android.support.v4.print.PrintHelperKitkat.AnonymousClass2.onWrite(android.print.PageRange[], android.os.ParcelFileDescriptor, android.os.CancellationSignal, android.print.PrintDocumentAdapter$WriteResultCallback):void");
                }
            };
            PrintManager printManager = (PrintManager) printHelperKitkat.a.getSystemService("print");
            Builder builder = new Builder();
            builder.setColorMode(printHelperKitkat.d);
            if (printHelperKitkat.e == 1) {
                builder.setMediaSize(MediaSize.UNKNOWN_LANDSCAPE);
            } else if (printHelperKitkat.e == 2) {
                builder.setMediaSize(MediaSize.UNKNOWN_PORTRAIT);
            }
            printManager.print(str, r1, builder.build());
        }
    }

    static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
        int a;
        int b;
        int c;

        public final void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        }

        public final void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) {
        }

        private PrintHelperStubImpl() {
            this.a = 2;
            this.b = 2;
            this.c = 1;
        }

        /* synthetic */ PrintHelperStubImpl(byte b2) {
            this();
        }

        public final void a(int i) {
            this.a = i;
        }

        public final int b() {
            return this.b;
        }

        public final void b(int i) {
            this.b = i;
        }

        public final void c(int i) {
            this.c = i;
        }

        public final int c() {
            return this.c;
        }

        public final int a() {
            return this.a;
        }
    }

    interface PrintHelperVersionImpl {
        int a();

        void a(int i);

        void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        int b();

        void b(int i);

        int c();

        void c(int i);
    }

    public static boolean systemSupportsPrint() {
        return VERSION.SDK_INT >= 19;
    }

    public PrintHelper(Context context) {
        if (systemSupportsPrint()) {
            this.mImpl = new PrintHelperKitkatImpl(context);
        } else {
            this.mImpl = new PrintHelperStubImpl(0);
        }
    }

    public final void setScaleMode(int i) {
        this.mImpl.a(i);
    }

    public final int getScaleMode() {
        return this.mImpl.a();
    }

    public final void setColorMode(int i) {
        this.mImpl.b(i);
    }

    public final int getColorMode() {
        return this.mImpl.b();
    }

    public final void setOrientation(int i) {
        this.mImpl.c(i);
    }

    public final int getOrientation() {
        return this.mImpl.c();
    }

    public final void printBitmap(String str, Bitmap bitmap) {
        this.mImpl.a(str, bitmap, (OnPrintFinishCallback) null);
    }

    public final void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        this.mImpl.a(str, bitmap, onPrintFinishCallback);
    }

    public final void printBitmap(String str, Uri uri) throws FileNotFoundException {
        this.mImpl.a(str, uri, (OnPrintFinishCallback) null);
    }

    public final void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        this.mImpl.a(str, uri, onPrintFinishCallback);
    }
}

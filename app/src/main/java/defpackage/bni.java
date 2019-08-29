package defpackage;

import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import java.io.File;
import java.net.HttpURLConnection;

/* renamed from: bni reason: default package */
/* compiled from: AmapUploadFile */
public final class bni extends bnj {
    protected bni(File file, File[] fileArr, bmt bmt) {
        super(file, fileArr, bmt);
    }

    public final void a() {
        if (b()) {
            String n = this.c.n();
            if (!TextUtils.isEmpty(n)) {
                new bmz();
                bmz.b(n, this.a, new bmy() {
                    public final void a(HttpURLConnection httpURLConnection) throws Throwable {
                        int responseCode = httpURLConnection.getResponseCode();
                        String headerField = httpURLConnection.getHeaderField("uploadstatus");
                        if (responseCode >= 300 || !headerField.equals("successful")) {
                            String headerField2 = httpURLConnection.getHeaderField("errormessage");
                            File[] fileArr = bni.this.b;
                            StringBuilder sb = new StringBuilder(" uploadFailed. result:");
                            sb.append(responseCode);
                            sb.append(" errorMessage:");
                            sb.append(headerField2);
                            sb.append(" resultStatus:");
                            sb.append(headerField);
                            CrashLogUtil.appendUploadFlag(fileArr, sb.toString());
                            return;
                        }
                        bni.a(bni.this.b);
                        bni.this.d = true;
                    }

                    public final void a(Throwable th) {
                        th.printStackTrace();
                        File[] fileArr = bni.this.b;
                        StringBuilder sb = new StringBuilder(" uploadFailed. exception:");
                        sb.append(th.toString());
                        CrashLogUtil.appendUploadFlag(fileArr, sb.toString());
                    }
                });
                if (this.a != null) {
                    try {
                        this.a.delete();
                    } catch (Throwable unused) {
                    }
                }
            }
        }
    }
}

package defpackage;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.wireless.security.SecExceptionCode;
import com.amap.bundle.aosservice.request.AosMultipartRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import com.amap.bundle.lotuspool.internal.model.http.FeedbackResultsResponse;
import com.amap.bundle.lotuspool.internal.model.http.UploadFileEntity;
import java.io.File;
import java.io.FileFilter;

/* renamed from: xb reason: default package */
/* compiled from: UploadFileExecutor */
public class xb implements wv {
    private static final String a = "xb";
    private int b;
    private StringBuilder c = new StringBuilder();
    private int d = 1;

    public final CommandResult a(String str, int i, Command command) {
        Command command2 = command;
        try {
            String e = command2.e("base_dir");
            final String e2 = command2.e("file_regex");
            final long d2 = command2.d("max_size");
            this.b = command2.c("network");
            String a2 = xi.a(e);
            if (bno.a) {
                AMapLog.d(a, "uploadFile command base dir=".concat(String.valueOf(a2)), true);
            }
            if (a2 == null) {
                if (bno.a) {
                    AMapLog.e(a, "dir is null", true);
                }
                this.c.append("\ndir is null");
                long j = command2.b;
                long j2 = command2.d;
                int i2 = command2.e;
                CommandResult commandResult = new CommandResult(str, j, j2, i2, command2.i, i, 101, this.c.toString());
                return commandResult;
            }
            File file = new File(a2);
            if (!file.exists() || !file.isDirectory()) {
                StringBuilder sb = this.c;
                sb.append("dir err=");
                sb.append(file.exists());
                sb.append(":");
                sb.append(file.getAbsolutePath());
                sb.append(";");
                long j3 = command2.b;
                long j4 = command2.d;
                int i3 = command2.e;
                CommandResult commandResult2 = new CommandResult(str, j3, j4, i3, command2.i, i, 101, this.c.toString());
                return commandResult2;
            }
            File[] listFiles = file.listFiles(new FileFilter() {
                public final boolean accept(File file) {
                    if (file == null) {
                        return false;
                    }
                    String name = file.getName();
                    if (TextUtils.isEmpty(name) || !name.matches(e2) || file.length() <= 0 || file.length() > d2) {
                        return false;
                    }
                    return true;
                }
            });
            if (listFiles == null || listFiles.length == 0) {
                if (bno.a) {
                    AMapLog.e(a, "filer files is null", true);
                }
                this.c.append("matched file array empty;");
                long j5 = command2.b;
                long j6 = command2.d;
                int i4 = command2.e;
                CommandResult commandResult3 = new CommandResult(str, j5, j6, i4, command2.i, i, 101, this.c.toString());
                return commandResult3;
            }
            if (!a(listFiles, str, command2.d, command2.i, command2.f)) {
                return null;
            }
            long j7 = command2.b;
            long j8 = command2.d;
            int i5 = command2.e;
            long j9 = command2.i;
            CommandResult commandResult4 = new CommandResult(str, j7, j8, i5, j9, i, this.d, this.c.toString());
            return commandResult4;
        } catch (Exception e3) {
            Exception exc = e3;
            if (bno.a) {
                String str2 = a;
                StringBuilder sb2 = new StringBuilder("get param error:");
                sb2.append(Log.getStackTraceString(exc));
                AMapLog.e(str2, sb2.toString(), true);
            }
            StringBuilder sb3 = this.c;
            sb3.append(exc.toString());
            sb3.append(";");
            long j10 = command2.b;
            long j11 = command2.d;
            int i6 = command2.e;
            CommandResult commandResult5 = new CommandResult(str, j10, j11, i6, command2.i, i, 101, this.c.toString());
            return commandResult5;
        }
    }

    public final boolean a(Command command) {
        return command.a("base_dir") && command.a("file_regex");
    }

    private boolean a(File[] fileArr, String str, long j, long j2, int i) {
        File[] fileArr2 = fileArr;
        int length = fileArr2.length;
        boolean z = false;
        int i2 = 0;
        while (i2 < length) {
            File file = fileArr2[i2];
            if (file == null || !file.exists() || !file.isFile()) {
                String str2 = str;
                long j3 = j;
                long j4 = j2;
                int i3 = i;
            } else if (!xi.b(this.b)) {
                if (bno.a) {
                    AMapLog.d(a, "network type is error", true);
                }
                return z;
            } else {
                String a2 = xi.a(file);
                UploadFileEntity uploadFileEntity = new UploadFileEntity();
                uploadFileEntity.command_id = j2;
                uploadFileEntity.dispatch_id = str;
                uploadFileEntity.dispatch_time = j;
                uploadFileEntity.md5 = a2;
                uploadFileEntity.sequence = i;
                if (bno.a) {
                    AMapLog.d(a, "upload file md5 = ".concat(String.valueOf(a2)), true);
                }
                try {
                    AosMultipartRequest c2 = aax.c(uploadFileEntity);
                    c2.a((String) "file", file);
                    c2.setPriority(125);
                    yq.a();
                    FeedbackResultsResponse feedbackResultsResponse = (FeedbackResultsResponse) yq.a((AosRequest) c2, FeedbackResultsResponse.class);
                    if (feedbackResultsResponse != null) {
                        if (((Integer) feedbackResultsResponse.getResult()).intValue() == 1) {
                            this.c.append("1,");
                        }
                    }
                    if (bno.a) {
                        String str3 = a;
                        StringBuilder sb = new StringBuilder("upLoad File error file name:");
                        sb.append(file.getAbsolutePath());
                        AMapLog.e(str3, sb.toString(), true);
                    }
                    this.d = SecExceptionCode.SEC_ERROR_SECURITYBODY_INVALID_PARAM;
                    this.c.append("0,");
                } catch (Exception e) {
                    Exception exc = e;
                    if (bno.a) {
                        String str4 = a;
                        StringBuilder sb2 = new StringBuilder("upLoad file exception:");
                        sb2.append(Log.getStackTraceString(exc));
                        AMapLog.e(str4, sb2.toString(), true);
                    }
                    this.c.append("0,");
                    this.d = SecExceptionCode.SEC_ERROR_SECURITYBODY_INVALID_PARAM;
                }
            }
            i2++;
            z = false;
        }
        this.c.append(";");
        return true;
    }
}

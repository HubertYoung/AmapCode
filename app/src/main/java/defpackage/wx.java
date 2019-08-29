package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: wx reason: default package */
/* compiled from: ExecuteFileExecutor */
public final class wx implements a, bjf, wv {
    private final String a;
    private final String b;
    private int c;
    private StringBuilder d = new StringBuilder();
    private String e;
    private long f;
    private Context g;

    public final boolean a(Command command) {
        return command != null;
    }

    public final void onFinishProgress(long j) {
    }

    public wx(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getFilesDir());
        sb.append("/dex/run/ready");
        this.a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(FileUtil.getFilesDir());
        sb2.append("/dex/download");
        this.b = sb2.toString();
        this.g = context;
    }

    public final CommandResult a(String str, int i, Command command) {
        String str2 = str;
        Command command2 = command;
        try {
            String e2 = command2.e("file_url");
            this.c = command2.c("network");
            if (TextUtils.isEmpty(e2)) {
                this.d.append("---url empty;");
                CommandResult commandResult = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 101, this.d.toString());
                return commandResult;
            } else if (this.g.getSharedPreferences("exefile_execute_state", 0).getBoolean(str2, false)) {
                CommandResult commandResult2 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1, this.d.toString());
                return commandResult2;
            } else {
                File file = new File(this.b);
                if (file.exists() || file.mkdirs()) {
                    File file2 = new File(file, "dex.apk");
                    if (!file2.exists() || ahd.a(file2)) {
                        this.e = file2.getAbsolutePath();
                        bjg bjg = new bjg(file2.getAbsolutePath());
                        bjg.setUrl(e2);
                        bjg.b = true;
                        bjh.a().a(bjg, (bjf) this);
                        synchronized (this) {
                            try {
                                wait();
                            } catch (InterruptedException e3) {
                                StringBuilder sb = this.d;
                                sb.append("---");
                                sb.append(e3.toString());
                                sb.append(";");
                                CommandResult commandResult3 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1201, this.d.toString());
                                return commandResult3;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (0 == this.f) {
                            this.d.append(" file length_0;");
                            ahd.a(file2);
                            CommandResult commandResult4 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1201, this.d.toString());
                            return commandResult4;
                        } else if (!file2.exists()) {
                            this.d.append(" file not exist;");
                            CommandResult commandResult5 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1201, this.d.toString());
                            return commandResult5;
                        } else if (file2.length() != this.f) {
                            StringBuilder sb2 = this.d;
                            sb2.append(" file err_");
                            sb2.append(this.f);
                            sb2.append("_");
                            sb2.append(file2.length());
                            sb2.append(";");
                            ahd.a(file2);
                            CommandResult commandResult6 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1201, this.d.toString());
                            return commandResult6;
                        } else if (!kl.a(file2)) {
                            ahd.a(file2);
                            this.d.append("---apk file err;");
                            CommandResult commandResult7 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1204, this.d.toString());
                            return commandResult7;
                        } else {
                            File file3 = new File(this.a);
                            if (file3.exists() || file3.mkdirs()) {
                                File file4 = new File(file3, "dex.apk");
                                if (!file2.renameTo(file4)) {
                                    ahd.a(file2);
                                    ahd.a(file4);
                                    this.d.append("---rename dex.apk faile;");
                                    CommandResult commandResult8 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1204, this.d.toString());
                                    return commandResult8;
                                }
                                Editor edit = this.g.getSharedPreferences("exefile_execute_state", 0).edit();
                                edit.putBoolean(str2, true);
                                edit.apply();
                                CommandResult commandResult9 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1, this.d.toString());
                                return commandResult9;
                            }
                            ahd.a(file2);
                            this.d.append("---ready space mkdir error;");
                            CommandResult commandResult10 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1203, this.d.toString());
                            return commandResult10;
                        }
                    } else {
                        this.d.append("---apkfile del err;");
                        CommandResult commandResult11 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1204, this.d.toString());
                        return commandResult11;
                    }
                } else {
                    this.d.append("---mkdir dowoLoad err;");
                    CommandResult commandResult12 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 1203, this.d.toString());
                    return commandResult12;
                }
            }
        } catch (Exception e4) {
            StringBuilder sb3 = this.d;
            sb3.append("---");
            sb3.append(e4.toString());
            sb3.append(";");
            CommandResult commandResult13 = new CommandResult(str2, command2.b, command2.d, command2.e, command2.i, i, 101, this.d.toString());
            return commandResult13;
        }
    }

    public final void onStart(long j, Map<String, List<String>> map, int i) {
        StringBuilder sb = this.d;
        sb.append(" start_");
        sb.append(j);
        sb.append(";");
    }

    public final void onProgressUpdate(long j, long j2) {
        if (!xi.b(this.c)) {
            this.d.append(" cancel down;");
            bjh.a().a(this.e);
        }
    }

    public final void onFinish(bpk bpk) {
        StringBuilder sb = this.d;
        sb.append(" finish_");
        sb.append(bpk.getContentLength());
        sb.append(";");
        this.f = bpk.getContentLength();
        synchronized (this) {
            notifyAll();
        }
    }

    public final void onError(int i, int i2) {
        StringBuilder sb = this.d;
        sb.append(" err_");
        sb.append(i);
        sb.append("_");
        sb.append(i2);
        sb.append(";");
        synchronized (this) {
            notifyAll();
        }
    }
}

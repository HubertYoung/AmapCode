package defpackage;

import android.text.TextUtils;
import com.alibaba.wireless.security.SecExceptionCode;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import java.io.File;
import java.io.FilenameFilter;

/* renamed from: ww reason: default package */
/* compiled from: DeleteFileExecutor */
public final class ww implements wv {
    public final boolean a(Command command) {
        return true;
    }

    public final CommandResult a(String str, int i, Command command) {
        Command command2 = command;
        StringBuilder sb = new StringBuilder();
        try {
            String e = command2.e("base_dir");
            final String e2 = command2.e("file_regex");
            if (TextUtils.isEmpty(e) || TextUtils.isEmpty(e2)) {
                sb.append("\ndir or file regex is empty");
                CommandResult commandResult = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, sb.toString());
                return commandResult;
            }
            String a = xi.a(e);
            if (a == null) {
                sb.append("\n dir is null ");
                CommandResult commandResult2 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, sb.toString());
                return commandResult2;
            }
            File file = new File(a);
            if (!file.exists() || !file.isDirectory()) {
                sb.append("\ndir err=");
                sb.append(file.exists());
                sb.append(":");
                sb.append(file.isDirectory());
                CommandResult commandResult3 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, sb.toString());
                return commandResult3;
            }
            File[] listFiles = file.listFiles(new FilenameFilter() {
                public final boolean accept(File file, String str) {
                    return !TextUtils.isEmpty(str) && str.matches(e2);
                }
            });
            if (listFiles == null || listFiles.length == 0) {
                sb.append("\nmatched file array empty");
                CommandResult commandResult4 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_PARAM, sb.toString());
                return commandResult4;
            }
            sb.append(" result=");
            for (File file2 : listFiles) {
                sb.append("  ");
                sb.append(file2.getName());
                if (!file2.delete()) {
                    sb.append(",0");
                } else {
                    sb.append(",1");
                }
            }
            CommandResult commandResult5 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 1, sb.toString());
            return commandResult5;
        } catch (Exception unused) {
            sb.append(" param empty");
            CommandResult commandResult6 = new CommandResult(str, command2.b, command2.d, command2.e, command2.i, i, 101, sb.toString());
            return commandResult6;
        }
    }
}

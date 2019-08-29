package defpackage;

import android.support.annotation.Nullable;
import com.amap.bundle.lotuspool.internal.model.bean.Command;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;

/* renamed from: wv reason: default package */
/* compiled from: ICommandExecutor */
public interface wv {
    @Nullable
    CommandResult a(String str, int i, Command command);

    boolean a(Command command);
}

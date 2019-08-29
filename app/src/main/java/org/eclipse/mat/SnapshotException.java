package org.eclipse.mat;

import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.util.MessageUtil;

public class SnapshotException extends Exception {
    private static final long serialVersionUID = 1;

    public SnapshotException() {
    }

    public SnapshotException(String str, Throwable th) {
        super(str, th);
    }

    public SnapshotException(String str) {
        super(str);
    }

    public SnapshotException(Throwable th) {
        super(th);
    }

    public SnapshotException(Messages messages) {
        super(MessageUtil.format(messages, new Object[0]));
    }

    public static final SnapshotException rethrow(Throwable th) {
        if (th instanceof RuntimeException) {
            RuntimeException runtimeException = (RuntimeException) th;
            if (runtimeException.getCause() instanceof SnapshotException) {
                return (SnapshotException) runtimeException.getCause();
            }
            throw runtimeException;
        } else if (th instanceof SnapshotException) {
            return (SnapshotException) th;
        } else {
            return new SnapshotException(th);
        }
    }
}

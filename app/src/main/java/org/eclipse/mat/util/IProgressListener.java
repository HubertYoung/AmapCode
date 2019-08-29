package org.eclipse.mat.util;

import org.eclipse.mat.hprof.Messages;

public interface IProgressListener {
    public static final int UNKNOWN_TOTAL_WORK = -1;

    public static class OperationCanceledException extends RuntimeException {
        private static final long serialVersionUID = 1;
    }

    public enum Severity {
        INFO,
        WARNING,
        ERROR
    }

    void beginTask(String str, int i);

    void beginTask(Messages messages, int i);

    void done();

    boolean isCanceled();

    void sendUserMessage(Severity severity, String str, Throwable th);

    void setCanceled(boolean z);

    void subTask(String str);

    void worked(int i);
}

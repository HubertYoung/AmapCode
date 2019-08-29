package org.eclipse.mat.util;

import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.util.IProgressListener.Severity;

public class SilentProgressListener implements IProgressListener {
    IProgressListener delegate;

    public void beginTask(String str, int i) {
    }

    public void done() {
    }

    public void subTask(String str) {
    }

    public void worked(int i) {
    }

    public SilentProgressListener(IProgressListener iProgressListener) {
        this.delegate = iProgressListener;
    }

    public final void beginTask(Messages messages, int i) {
        beginTask(messages.pattern, i);
    }

    public boolean isCanceled() {
        return this.delegate.isCanceled();
    }

    public void sendUserMessage(Severity severity, String str, Throwable th) {
        this.delegate.sendUserMessage(severity, str, th);
    }

    public void setCanceled(boolean z) {
        this.delegate.setCanceled(z);
    }
}

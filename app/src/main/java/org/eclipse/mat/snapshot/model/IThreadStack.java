package org.eclipse.mat.snapshot.model;

public interface IThreadStack {
    IStackFrame[] getStackFrames();

    int getThreadId();
}

package org.eclipse.mat.util;

import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.util.IProgressListener.Severity;

public class SimpleMonitor {
    int currentMonitor;
    IProgressListener delegate;
    int[] percentages;
    String task;

    public class Listener implements IProgressListener {
        long counter;
        boolean isSmaller;
        int majorUnits;
        int unitsReported;
        long workDone;
        long workPerUnit;

        public Listener(int i) {
            this.majorUnits = i;
        }

        public final void beginTask(Messages messages, int i) {
            beginTask(messages.pattern, i);
        }

        public void beginTask(String str, int i) {
            if (str != null) {
                SimpleMonitor.this.delegate.subTask(str);
            }
            if (i != 0) {
                this.isSmaller = i < this.majorUnits;
                this.workPerUnit = this.isSmaller ? (long) (this.majorUnits / i) : (long) (i / this.majorUnits);
                this.unitsReported = 0;
            }
        }

        public void subTask(String str) {
            SimpleMonitor.this.delegate.subTask(str);
        }

        public void done() {
            if (this.majorUnits - this.unitsReported > 0) {
                SimpleMonitor.this.delegate.worked(this.majorUnits - this.unitsReported);
            }
        }

        public boolean isCanceled() {
            return SimpleMonitor.this.delegate.isCanceled();
        }

        public boolean isProbablyCanceled() {
            long j = this.counter;
            this.counter = 1 + j;
            if (j % 5000 == 0) {
                return isCanceled();
            }
            return false;
        }

        public void totalWorkDone(long j) {
            if (this.workDone != j && this.workPerUnit != 0) {
                this.workDone = j;
                int i = ((int) (this.isSmaller ? j * this.workPerUnit : j / this.workPerUnit)) - this.unitsReported;
                if (i > 0) {
                    SimpleMonitor.this.delegate.worked(i);
                    this.unitsReported += i;
                }
            }
        }

        public void worked(int i) {
            totalWorkDone(this.workDone + ((long) i));
        }

        public void setCanceled(boolean z) {
            SimpleMonitor.this.delegate.setCanceled(z);
        }

        public void sendUserMessage(Severity severity, String str, Throwable th) {
            SimpleMonitor.this.delegate.sendUserMessage(severity, str, th);
        }

        public long getWorkDone() {
            return this.workDone;
        }
    }

    public SimpleMonitor(String str, IProgressListener iProgressListener, int[] iArr) {
        this.task = str;
        this.delegate = iProgressListener;
        this.percentages = iArr;
    }

    public IProgressListener nextMonitor() {
        if (this.currentMonitor == 0) {
            int i = 0;
            for (int i2 : this.percentages) {
                i += i2;
            }
            this.delegate.beginTask(this.task, i);
        }
        int[] iArr = this.percentages;
        int i3 = this.currentMonitor;
        this.currentMonitor = i3 + 1;
        return new Listener(iArr[i3]);
    }
}

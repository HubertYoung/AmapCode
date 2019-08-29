package de.greenrobot.event;

final class PendingPostQueue {
    private PendingPost head;
    private PendingPost tail;

    PendingPostQueue() {
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void enqueue(PendingPost pendingPost) {
        if (pendingPost == null) {
            try {
                throw new NullPointerException("null cannot be enqueued");
            } catch (Throwable th) {
                throw th;
            }
        } else {
            if (this.tail != null) {
                this.tail.next = pendingPost;
                this.tail = pendingPost;
            } else if (this.head == null) {
                this.tail = pendingPost;
                this.head = pendingPost;
            } else {
                throw new IllegalStateException("Head present, but no tail");
            }
            notifyAll();
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized PendingPost poll() {
        PendingPost pendingPost;
        try {
            pendingPost = this.head;
            if (this.head != null) {
                this.head = this.head.next;
                if (this.head == null) {
                    this.tail = null;
                }
            }
        }
        return pendingPost;
    }

    /* access modifiers changed from: 0000 */
    public final synchronized PendingPost poll(int i) throws InterruptedException {
        if (this.head == null) {
            wait((long) i);
        }
        return poll();
    }
}

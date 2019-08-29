package com.alipay.mobile.common.transportext.biz.spdy;

public class Failure {
    private final Throwable exception;
    private final Request request;

    public class Builder {
        /* access modifiers changed from: private */
        public Throwable exception;
        /* access modifiers changed from: private */
        public Request request;

        public Builder request(Request request2) {
            this.request = request2;
            return this;
        }

        public Builder exception(Throwable exception2) {
            this.exception = exception2;
            return this;
        }

        public Failure build() {
            return new Failure(this);
        }
    }

    private Failure(Builder builder) {
        this.request = builder.request;
        this.exception = builder.exception;
    }

    public Request request() {
        return this.request;
    }

    public Throwable exception() {
        return this.exception;
    }
}

package com.alipay.mobile.common.transportext.biz.spdy;

import com.alipay.mobile.common.transportext.biz.spdy.Response.Body;
import com.alipay.mobile.common.transportext.biz.spdy.Response.Receiver;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class Dispatcher {
    private final Map<Object, List<Job>> enqueuedJobs = new LinkedHashMap();
    private final ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 8, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());

    class RealResponseBody extends Body {
        private final HttpURLConnection connection;
        private final InputStream in;

        RealResponseBody(HttpURLConnection connection2, InputStream in2) {
            this.connection = connection2;
            this.in = in2;
        }

        public String contentType() {
            return this.connection.getHeaderField("Content-Type");
        }

        public long contentLength() {
            return (long) this.connection.getContentLength();
        }

        public InputStream byteStream() {
            return this.in;
        }
    }

    Dispatcher() {
    }

    public final synchronized void enqueue(HttpURLConnection connection, Request request, Receiver responseReceiver) {
        Job job = new Job(this, connection, request, responseReceiver);
        List jobsForTag = this.enqueuedJobs.get(request.tag());
        if (jobsForTag == null) {
            jobsForTag = new ArrayList(2);
            this.enqueuedJobs.put(request.tag(), jobsForTag);
        }
        jobsForTag.add(job);
        this.executorService.execute(job);
    }

    public final synchronized void cancel(Object tag) {
        List<Job> jobs = this.enqueuedJobs.remove(tag);
        if (jobs != null) {
            for (Job job : jobs) {
                this.executorService.remove(job);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final synchronized void finished(Job job) {
        List jobs = this.enqueuedJobs.get(job.request.tag());
        if (jobs != null) {
            jobs.remove(job);
        }
    }
}

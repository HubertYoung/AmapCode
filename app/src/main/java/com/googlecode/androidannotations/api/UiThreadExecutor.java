package com.googlecode.androidannotations.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import java.util.HashMap;
import java.util.Map;

public final class UiThreadExecutor {
    private static final Handler a = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message msg) {
            Runnable callback = msg.getCallback();
            if (callback != null) {
                callback.run();
                UiThreadExecutor.a((Token) msg.obj);
                return;
            }
            super.handleMessage(msg);
        }
    };
    private static final Map<String, Token> b = new HashMap();

    final class Token {
        final String id;
        int runnablesCount;

        private Token(String id2) {
            this.runnablesCount = 0;
            this.id = id2;
        }
    }

    private UiThreadExecutor() {
    }

    public static void runTask(String id, Runnable task, long delay) {
        if ("".equals(id)) {
            a.postDelayed(task, delay);
            return;
        }
        a.postAtTime(task, a(id), SystemClock.uptimeMillis() + delay);
    }

    private static Token a(String id) {
        Token token;
        synchronized (b) {
            token = b.get(id);
            if (token == null) {
                token = new Token(id);
                b.put(id, token);
            }
            token.runnablesCount++;
        }
        return token;
    }

    /* access modifiers changed from: private */
    public static void a(Token token) {
        synchronized (b) {
            int i = token.runnablesCount - 1;
            token.runnablesCount = i;
            if (i == 0) {
                String id = token.id;
                Token old = b.remove(id);
                if (old != token) {
                    b.put(id, old);
                }
            }
        }
    }

    public static void cancelAll(String id) {
        Token token;
        synchronized (b) {
            token = b.remove(id);
        }
        if (token != null) {
            a.removeCallbacksAndMessages(token);
        }
    }
}

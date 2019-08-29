package com.autonavi.link.adapter.client.listener;

import java.util.ArrayList;

/* compiled from: ListenerDispatcher */
public class a {
    private static volatile a a;
    private ArrayList<OnALinkConnectedListener> b = new ArrayList<>();

    private a() {
    }

    public static a a() {
        if (a == null) {
            synchronized (a.class) {
                try {
                    a = new a();
                }
            }
        }
        return a;
    }

    public void b() {
        if (this.b != null && !this.b.isEmpty()) {
            this.b.clear();
        }
    }

    public ArrayList<OnALinkConnectedListener> c() {
        return this.b;
    }

    public boolean a(OnALinkConnectedListener onALinkConnectedListener) {
        if (this.b.contains(onALinkConnectedListener)) {
            return false;
        }
        return this.b.add(onALinkConnectedListener);
    }

    public boolean b(OnALinkConnectedListener onALinkConnectedListener) {
        if (this.b.contains(onALinkConnectedListener)) {
            return this.b.remove(onALinkConnectedListener);
        }
        return false;
    }
}

package com.autonavi.bundle.amaphome.widget.guideview;

import android.view.View;
import java.util.ArrayList;
import java.util.List;

public final class GuideBuilder {
    public Configuration a = new Configuration();
    public boolean b;
    public a c;
    private List<asf> d = new ArrayList();
    private b e;

    public enum SlideState {
        UP,
        DOWN
    }

    public interface a {
    }

    public interface b {
        void a();

        void b();
    }

    public final GuideBuilder a() {
        if (this.b) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.a.h = 150;
        return this;
    }

    public final GuideBuilder a(View view) {
        if (this.b) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.a.a = view;
        return this;
    }

    public final GuideBuilder a(int i) {
        if (this.b) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.a.k = 0;
        }
        this.a.k = i;
        return this;
    }

    public final GuideBuilder a(asf asf) {
        if (this.b) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.d.add(asf);
        return this;
    }

    public final GuideBuilder a(b bVar) {
        if (this.b) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.e = bVar;
        return this;
    }

    public final GuideBuilder b(int i) {
        if (this.b) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.a.c = 0;
        }
        this.a.c = i;
        return this;
    }

    public final GuideBuilder c(int i) {
        if (this.b) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.a.e = 0;
        }
        this.a.e = i;
        return this;
    }

    public final asg b() {
        asg asg = new asg();
        asg.c = (asf[]) this.d.toArray(new asf[this.d.size()]);
        asg.a = this.a;
        asg.d = this.e;
        asg.e = this.c;
        this.d = null;
        this.a = null;
        this.e = null;
        this.b = true;
        return asg;
    }
}

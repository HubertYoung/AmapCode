package defpackage;

/* renamed from: cda reason: default package */
/* compiled from: ISuspendEventController */
public interface cda extends defpackage.ami.a, amn, bie {

    /* renamed from: cda$a */
    /* compiled from: ISuspendEventController */
    public interface a {
        void updateStateWhenCompassPaint();
    }

    /* renamed from: cda$b */
    /* compiled from: ISuspendEventController */
    public interface b {
        boolean onIndoorBuildingActive(ami ami);
    }

    /* renamed from: cda$c */
    /* compiled from: ISuspendEventController */
    public interface c {
        void c();
    }

    /* renamed from: cda$d */
    /* compiled from: ISuspendEventController */
    public interface d {
        void a();
    }

    /* renamed from: cda$e */
    /* compiled from: ISuspendEventController */
    public interface e {
        void onResetViewState();
    }

    /* renamed from: cda$f */
    /* compiled from: ISuspendEventController */
    public interface f {
        void changeLogoStatus(boolean z);

        void refreshScaleLineView();

        void refreshScaleLogo();

        void setScaleColor(int i, int i2);

        void setScaleLineColor(int i, int i2);
    }

    /* renamed from: cda$g */
    /* compiled from: ISuspendEventController */
    public interface g {
        void updateZoomButtonState(bty bty);

        void updateZoomViewVisibility();
    }

    void a();

    void a(ccz ccz);

    @Deprecated
    void a(a aVar);

    void a(b bVar);

    @Deprecated
    void a(c cVar);

    @Deprecated
    void a(d dVar);

    @Deprecated
    void a(e eVar);

    @Deprecated
    void a(f fVar);

    @Deprecated
    void a(g gVar);

    @Deprecated
    void a(cdf cdf);

    void a(boolean z);

    ccz b();

    void b(boolean z);

    void c();

    void c(boolean z);

    void d();

    void d(boolean z);

    void e();

    void e(boolean z);

    void f();

    void fadeCompassWidget(int i);

    void g();

    void h();

    void i();

    void indoorBuildingActivity(int i, ami ami);

    void j();

    void k();

    boolean l();

    void m();

    void paintCompass(int i);

    void refreshScaleLineView(int i);

    void setFrontViewVisibility(int i, boolean z);

    void setScaleColor(int i, int i2, int i3);
}

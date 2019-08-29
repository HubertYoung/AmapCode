package defpackage;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.autonavi.map.core.MapCustomizeManager;

/* renamed from: dae reason: default package */
/* compiled from: GPSBtnControllerImpl */
public class dae implements cec {
    OnTouchListener a = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (dae.this.h == 1) {
                return dae.this.b(view, motionEvent);
            }
            if (dae.this.h == 2) {
                return dae.this.c(view, motionEvent);
            }
            return dae.this.a(view, motionEvent);
        }
    };
    private cdc b;
    private agl<a> c = new agl<>();
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public int e = 0;
    private int f = 1;
    private boolean g = false;
    /* access modifiers changed from: private */
    public int h = 0;
    private cdw i = new cdw();

    public final void a(cdc cdc) {
        this.b = cdc;
        cdd.n().a((e) this);
    }

    public final void a(ced ced) {
        if (ced != null) {
            ced.setOnTouchListener(this.a);
            cdw cdw = this.i;
            if (ced != null) {
                ced ced2 = (ced) cdw.a.b();
                if (!(ced2 == null || ced2 == ced)) {
                    ced.setState(ced2.getState());
                    ced.setVisibility(ced2.getVisibility());
                }
            }
            cdw.a.a(ced);
        }
    }

    public final void b(ced ced) {
        if (ced != null) {
            ced.setOnTouchListener(null);
            this.i.a.b(ced);
        }
    }

    public final boolean a() {
        return this.d == 2 || this.d == 5;
    }

    public final boolean b() {
        return this.d == 6 || this.d == 7;
    }

    public final boolean c() {
        return this.g;
    }

    public final void a(boolean z) {
        this.g = z;
    }

    public final int h() {
        return this.e;
    }

    public final void d() {
        a(this.f);
    }

    public final void a(int i2) {
        if (this.d != i2) {
            this.e = this.d;
            if (i2 != 0 && this.g) {
                this.f = i2;
            }
            this.d = i2;
            this.i.a(this.d);
            if (this.c != null) {
                this.c.a((a<T>) new a<a>() {
                    public final /* synthetic */ void onNotify(Object obj) {
                        dae.this.e;
                        dae.this.d;
                    }
                });
            }
        }
    }

    public final int e() {
        return this.d;
    }

    public final void b(int i2) {
        this.h = i2;
    }

    public final int i() {
        return this.h;
    }

    private void a(final View view) {
        if (this.c != null) {
            this.c.a((a<T>) new a<a>() {
                public final /* synthetic */ void onNotify(Object obj) {
                    ((a) obj).a();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public boolean a(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (!this.g) {
                a(0);
            } else if (this.d == 5) {
                a(6);
            } else if (this.d == 4) {
                a(2);
            } else if (this.d == 7) {
                a(2);
            }
            a(view);
        } else if (motionEvent.getAction() == 0) {
            if (this.d == 0) {
                a(3);
            } else if (this.d == 2) {
                a(5);
            } else if (this.d == 1) {
                a(4);
            } else if (this.d == 6) {
                a(7);
            }
        } else if (motionEvent.getAction() == 3) {
            if (!this.g) {
                a(0);
            } else if (this.d == 5) {
                a(2);
            } else if (this.d == 4) {
                a(1);
            } else if (this.d == 7) {
                a(6);
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean b(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (!this.g) {
                a(0);
            } else if (this.d == 4) {
                a(2);
            } else if (this.d == 5) {
                a(2);
            }
            a(view);
        } else if (motionEvent.getAction() == 0) {
            if (this.d == 0) {
                a(3);
            } else if (this.d == 2) {
                a(5);
            } else if (this.d == 1) {
                a(4);
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean c(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.d == 3) {
                a(0);
            } else if (this.d == 5) {
                a(2);
            } else if (this.d == 4) {
                a(1);
            }
            a(view);
        } else if (motionEvent.getAction() == 0) {
            if (this.d == 0) {
                a(3);
            } else if (this.d == 2) {
                a(5);
            } else if (this.d == 1) {
                a(4);
            }
        }
        return false;
    }

    public final void a(a aVar) {
        this.c.a(aVar);
    }

    public void onResetViewState() {
        if (!cdd.n().l()) {
            MapCustomizeManager e2 = this.b.e();
            if (e2 != null) {
                int i2 = e2.isViewEnable(4) ? 0 : 8;
                cdw cdw = this.i;
                cdw.a.a((a<T>) new a<ced>(i2) {
                    final /* synthetic */ int a;

                    {
                        this.a = r2;
                    }

                    public final /* synthetic */ void onNotify(Object obj) {
                        ced ced = (ced) obj;
                        if (this.a != ced.getVisibility()) {
                            ced.setVisibility(this.a);
                        }
                    }
                });
            }
        }
    }

    public final void g() {
        View view = this.i.a() != null ? this.i.a().getView() : null;
        if (view != null && this.h == 0) {
            if (this.g) {
                a(7);
                a(2);
            } else {
                a(0);
            }
            a(view);
        }
    }

    public final boolean f() {
        if (!this.g) {
            return false;
        }
        if (a() || b()) {
            return true;
        }
        return false;
    }
}

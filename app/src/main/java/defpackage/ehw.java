package defpackage;

/* renamed from: ehw reason: default package */
/* compiled from: BaseNotificationWidgetPolicy */
public abstract class ehw {
    /* access modifiers changed from: protected */
    public abstract boolean a(String str);

    /* access modifiers changed from: protected */
    public boolean b() {
        try {
            StringBuilder sb = new StringBuilder("FootNaviStateTunnel.getFootNaviState()=");
            sb.append(egc.a());
            eao.d("wbsww", sb.toString());
            return egc.a();
        } catch (Throwable th) {
            eao.e(ehw.class.getName(), "Happen error during get status of drive navigation");
            th.printStackTrace();
            return false;
        }
    }
}

package defpackage;

/* renamed from: bgx reason: default package */
/* compiled from: IH5TemplateService */
public interface bgx extends esc {

    /* renamed from: bgx$a */
    /* compiled from: IH5TemplateService */
    public interface a {
        void a(String str);
    }

    String getTemplateFilePath(String str);

    @Deprecated
    String getUrl(String str);

    void getUrl(String str, a aVar);

    void update();
}

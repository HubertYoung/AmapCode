package defpackage;

import org.json.JSONObject;

/* renamed from: bgm reason: default package */
/* compiled from: IVUIPage */
public interface bgm {
    void finishSelf();

    bgo getPresenter();

    JSONObject getScenesData();

    long getScenesID();

    boolean isInnerPage();

    boolean needKeepSessionAlive();
}

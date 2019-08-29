package defpackage;

import java.util.HashMap;
import java.util.Map;

/* renamed from: ezq reason: default package */
/* compiled from: UPSNotificationMessage */
public class ezq {
    public int f;
    public String g;
    public String h;
    public String i;
    public int j;
    public String k;
    public String l;
    public String m;
    public String n;
    public int o;
    public boolean p;
    public long q;
    public Map<String, String> r = new HashMap();

    public String toString() {
        StringBuilder sb = new StringBuilder("UPSNotificationMessage{mTargetType=");
        sb.append(this.f);
        sb.append(", mTragetContent='");
        sb.append(this.g);
        sb.append('\'');
        sb.append(", mTitle='");
        sb.append(this.h);
        sb.append('\'');
        sb.append(", mContent='");
        sb.append(this.i);
        sb.append('\'');
        sb.append(", mNotifyType=");
        sb.append(this.j);
        sb.append(", mPurePicUrl='");
        sb.append(this.k);
        sb.append('\'');
        sb.append(", mIconUrl='");
        sb.append(this.l);
        sb.append('\'');
        sb.append(", mCoverUrl='");
        sb.append(this.m);
        sb.append('\'');
        sb.append(", mSkipContent='");
        sb.append(this.n);
        sb.append('\'');
        sb.append(", mSkipType=");
        sb.append(this.o);
        sb.append(", mShowTime=");
        sb.append(this.p);
        sb.append(", mMsgId=");
        sb.append(this.q);
        sb.append(", mParams=");
        sb.append(this.r);
        sb.append('}');
        return sb.toString();
    }
}

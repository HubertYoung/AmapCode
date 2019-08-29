package defpackage;

import android.graphics.Bitmap;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
/* renamed from: dcq reason: default package */
/* compiled from: ShareInfo */
public final class dcq {
    public String caller;
    public String content;
    public String fromSource = null;
    public int hideLinkCopyBtn = 0;
    public int hideMoreBtn = 0;
    public Bitmap imgBitmap;
    public String imgUrl;
    public int isWord = 0;
    public int loadDirectly = 0;
    public boolean needToShortUrl;
    public String picOrWord = "0";
    public dcs sharePassphraseInfo;
    public int shareType;
    public String title;
    public String url;
    public int useCustomUrl = 0;

    public dcq(int i, String str, String str2, String str3, boolean z, int i2, String str4, String str5, int i3, int i4, int i5) {
        this.shareType = i;
        this.title = str;
        this.url = str3;
        this.content = str2;
        this.needToShortUrl = z;
        this.imgUrl = str4;
        this.caller = str5;
        this.loadDirectly = i3;
        this.hideLinkCopyBtn = i4;
        this.hideMoreBtn = i5;
    }

    public final void setSharePassphraseInfo(dcs dcs) {
        this.sharePassphraseInfo = dcs;
        if (dcs != null) {
            this.isWord = this.isWord;
        }
    }

    public dcq() {
    }
}

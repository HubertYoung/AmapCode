package com.alipay.zoloz.toyger.blob;

public class Content {
    public Blob blob;
    public Meta meta;

    public Content() {
    }

    public Content(Meta meta2, Blob blob2) {
        this.meta = meta2;
        this.blob = blob2;
    }
}

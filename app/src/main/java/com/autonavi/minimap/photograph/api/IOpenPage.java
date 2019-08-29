package com.autonavi.minimap.photograph.api;

public interface IOpenPage {

    public enum PhotoSelectOptions {
        DEFALUT,
        TAKE_PHOTO_BY_CAMERA,
        SELECT_PHOTO_FROM_GALLARY
    }

    public static class a {
        public String a = null;
        public Boolean b = null;
        public Integer c = null;
        public PhotoSelectOptions d = PhotoSelectOptions.DEFALUT;
    }

    void a(bid bid, a aVar);

    void a(bid bid, String str, PhotoSelectOptions photoSelectOptions);
}

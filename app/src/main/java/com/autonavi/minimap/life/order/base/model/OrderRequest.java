package com.autonavi.minimap.life.order.base.model;

import java.io.Serializable;

public class OrderRequest implements Serializable {
    public static final String INTENT_KEY = "OrderRequest";
    private static final long serialVersionUID = 2311927548491546159L;
    public String code = "";
    public String oid = "";
    public String orderjson = "";
    public String phone = "";
    public String src_type = "";
    public String type = "";
}

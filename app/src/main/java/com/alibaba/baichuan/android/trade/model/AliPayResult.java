package com.alibaba.baichuan.android.trade.model;

import java.io.Serializable;
import java.util.List;

public class AliPayResult implements Serializable {
    public List payFailedOrders;
    public List paySuccessOrders;
}

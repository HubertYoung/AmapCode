package com.alipay.mobile.common.feedback;

import com.alipay.android.hackbyte.ClassVerifier;

public interface IFeedbackBizDataProvider {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    FeedbackBizData getFeedbackBizData();
}

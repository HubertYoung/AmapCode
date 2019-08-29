package com.alipay.android.phone.inside.sdk;

public class InsideProviderService {
    private static IInteractionProvider mInteractionProvider;

    public interface IInteractionProvider {
        String getPkgName();
    }

    public static void setInteractionProvider(IInteractionProvider iInteractionProvider) {
        mInteractionProvider = iInteractionProvider;
    }

    public static IInteractionProvider getInteractionProvider() {
        return mInteractionProvider;
    }
}

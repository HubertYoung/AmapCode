package com.sina.weibo.sdk.share;

import com.sina.weibo.sdk.api.StoryObject;

public interface TransResourceCallback {
    void onTransFinish(StoryObject storyObject);

    void onTransFinish(TransResourceResult transResourceResult);
}

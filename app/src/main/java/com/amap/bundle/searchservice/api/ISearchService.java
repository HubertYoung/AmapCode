package com.amap.bundle.searchservice.api;

import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ISearchService extends esc {

    @Retention(RetentionPolicy.SOURCE)
    public @interface SearchMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuggMode {
    }

    adx a(SuggestionParam suggestionParam, int i, aeb<aux> aeb);

    adx a(InfoliteParam infoliteParam, int i, aeb<InfoliteResult> aeb);

    adx b(InfoliteParam infoliteParam, int i, aeb<aud> aeb);
}

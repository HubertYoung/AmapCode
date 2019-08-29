package com.amap.bundle.searchservice.service.search;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.amap.bundle.searchservice.service.search.parser.SuggestionParser;

public class SearchSuggServiceImpl$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ aeb a;
    final /* synthetic */ SuggestionParam b;
    final /* synthetic */ int c;
    final /* synthetic */ adx d;
    final /* synthetic */ aek e;

    public SearchSuggServiceImpl$1(aek aek, aeb aeb, SuggestionParam suggestionParam, int i, adx adx) {
        this.e = aek;
        this.a = aeb;
        this.b = suggestionParam;
        this.c = i;
        this.d = adx;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        final aux parser = SuggestionParser.parser((byte[]) ((AosByteResponse) aosResponse).getResult());
        aho.a(new Runnable() {
            public final void run() {
                SearchSuggServiceImpl$1.this.a.callback(parser);
            }
        });
    }

    public void onFailure(AosRequest aosRequest, final AosResponseException aosResponseException) {
        this.e.a(this.b, this.c, this.a, this.d);
        aho.a(new Runnable() {
            public final void run() {
                SearchSuggServiceImpl$1.this.a.error(aosResponseException.errorCode);
            }
        });
    }
}

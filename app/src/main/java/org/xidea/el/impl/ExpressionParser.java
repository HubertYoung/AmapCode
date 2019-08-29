package org.xidea.el.impl;

import org.xidea.el.json.JSONTokenizer;

public class ExpressionParser extends JSONTokenizer {
    private static final TokenImpl e = new TokenImpl(Boolean.TRUE);
    private static final TokenImpl f = new TokenImpl(Boolean.FALSE);
    private static final TokenImpl g = new TokenImpl(null);
}

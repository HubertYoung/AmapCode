package com.alipay.mobile.beehive.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class Money implements Serializable, Comparable {
    public static final String DEFAULT_CURRENCY_CODE = "CNY";
    public static final int DEFAULT_ROUNDING_MODE = 6;
    private static final int[] centFactors = {1, 10, 100, 1000};
    private static final long serialVersionUID = 6009335074727417445L;
    private long cent;
    private Currency currency;

    public Money() {
        this(0.0d);
    }

    public Money(long yuan, int cent2) {
        this(yuan, cent2, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(long yuan, int cent2, Currency currency2) {
        this.currency = currency2;
        this.cent = (((long) getCentFactor()) * yuan) + ((long) (cent2 % getCentFactor()));
    }

    public Money(String amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(String amount, Currency currency2) {
        this(new BigDecimal(amount), currency2);
    }

    public Money(String amount, Currency currency2, int roundingMode) {
        this(new BigDecimal(amount), currency2, roundingMode);
    }

    public Money(double amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(double amount, Currency currency2) {
        this.currency = currency2;
        this.cent = Math.round(((double) getCentFactor()) * amount);
    }

    public Money(BigDecimal amount) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(BigDecimal amount, int roundingMode) {
        this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
    }

    public Money(BigDecimal amount, Currency currency2) {
        this(amount, currency2, 6);
    }

    public Money(BigDecimal amount, Currency currency2, int roundingMode) {
        this.currency = currency2;
        this.cent = rounding(amount.movePointRight(currency2.getDefaultFractionDigits()), roundingMode);
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(this.cent, this.currency.getDefaultFractionDigits());
    }

    public void setAmount(BigDecimal amount) {
        if (amount != null) {
            this.cent = rounding(amount.movePointRight(2), 6);
        }
    }

    public long getCent() {
        return this.cent;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public int getCentFactor() {
        return centFactors[this.currency.getDefaultFractionDigits()];
    }

    public boolean equals(Object other) {
        return (other instanceof Money) && equals((Money) other);
    }

    public boolean equals(Money other) {
        return this.currency.equals(other.currency) && this.cent == other.cent;
    }

    public int hashCode() {
        return (int) (this.cent ^ (this.cent >>> 32));
    }

    public int compareTo(Object other) {
        return compareTo((Money) other);
    }

    public int compareTo(Money other) {
        assertSameCurrencyAs(other);
        if (this.cent < other.cent) {
            return -1;
        }
        if (this.cent == other.cent) {
            return 0;
        }
        return 1;
    }

    public boolean greaterThan(Money other) {
        return compareTo(other) > 0;
    }

    public Money add(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(this.cent + other.cent);
    }

    public Money addTo(Money other) {
        assertSameCurrencyAs(other);
        this.cent += other.cent;
        return this;
    }

    public Money subtract(Money other) {
        assertSameCurrencyAs(other);
        return newMoneyWithSameCurrency(this.cent - other.cent);
    }

    public Money subtractFrom(Money other) {
        assertSameCurrencyAs(other);
        this.cent -= other.cent;
        return this;
    }

    public Money multiply(long val) {
        return newMoneyWithSameCurrency(this.cent * val);
    }

    public Money multiplyBy(long val) {
        this.cent *= val;
        return this;
    }

    public Money multiply(double val) {
        return newMoneyWithSameCurrency(Math.round(((double) this.cent) * val));
    }

    public Money multiplyBy(double val) {
        this.cent = Math.round(((double) this.cent) * val);
        return this;
    }

    public Money multiply(BigDecimal val) {
        return multiply(val, 6);
    }

    public Money multiplyBy(BigDecimal val) {
        return multiplyBy(val, 6);
    }

    public Money multiply(BigDecimal val, int roundingMode) {
        return newMoneyWithSameCurrency(rounding(BigDecimal.valueOf(this.cent).multiply(val), roundingMode));
    }

    public Money multiplyBy(BigDecimal val, int roundingMode) {
        this.cent = rounding(BigDecimal.valueOf(this.cent).multiply(val), roundingMode);
        return this;
    }

    public Money divide(double val) {
        return newMoneyWithSameCurrency(Math.round(((double) this.cent) / val));
    }

    public Money divideBy(double val) {
        this.cent = Math.round(((double) this.cent) / val);
        return this;
    }

    public Money divide(BigDecimal val) {
        return divide(val, 6);
    }

    public Money divide(BigDecimal val, int roundingMode) {
        return newMoneyWithSameCurrency(BigDecimal.valueOf(this.cent).divide(val, roundingMode).longValue());
    }

    public Money divideBy(BigDecimal val) {
        return divideBy(val, 6);
    }

    public Money divideBy(BigDecimal val, int roundingMode) {
        this.cent = BigDecimal.valueOf(this.cent).divide(val, roundingMode).longValue();
        return this;
    }

    public Money[] allocate(int targets) {
        Money[] results = new Money[targets];
        Money lowResult = newMoneyWithSameCurrency(this.cent / ((long) targets));
        Money highResult = newMoneyWithSameCurrency(lowResult.cent + 1);
        int remainder = ((int) this.cent) % targets;
        for (int i = 0; i < remainder; i++) {
            results[i] = highResult;
        }
        for (int i2 = remainder; i2 < targets; i2++) {
            results[i2] = lowResult;
        }
        return results;
    }

    public Money[] allocate(long[] ratios) {
        Money[] results = new Money[ratios.length];
        long total = 0;
        for (long j : ratios) {
            total += j;
        }
        long remainder = this.cent;
        for (int i = 0; i < results.length; i++) {
            results[i] = newMoneyWithSameCurrency((this.cent * ratios[i]) / total);
            remainder -= results[i].cent;
        }
        for (int i2 = 0; ((long) i2) < remainder; i2++) {
            Money money = results[i2];
            money.cent++;
        }
        return results;
    }

    public String toString() {
        return getAmount().toString();
    }

    /* access modifiers changed from: protected */
    public void assertSameCurrencyAs(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Money math currency mismatch.");
        }
    }

    /* access modifiers changed from: protected */
    public long rounding(BigDecimal val, int roundingMode) {
        return val.setScale(0, roundingMode).longValue();
    }

    /* access modifiers changed from: protected */
    public Money newMoneyWithSameCurrency(long cent2) {
        Money money = new Money(0.0d, this.currency);
        money.cent = cent2;
        return money;
    }

    public String dump() {
        String lineSeparator = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer();
        sb.append("cent = ").append(this.cent).append(lineSeparator);
        sb.append("currency = ").append(this.currency);
        return sb.toString();
    }

    public void setCent(long l) {
        this.cent = l;
    }
}

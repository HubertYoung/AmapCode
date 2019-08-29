package com.google.zxing.client.result;

public final class AddressBookParsedResult extends ParsedResult {
    private final String[] addressTypes;
    private final String[] addresses;
    private final String birthday;
    private final String[] emailTypes;
    private final String[] emails;
    private final String[] geo;
    private final String instantMessenger;
    private final String[] names;
    private final String[] nicknames;
    private final String note;

    /* renamed from: org reason: collision with root package name */
    private final String f12org;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String pronunciation;
    private final String title;
    private final String[] urls;

    public AddressBookParsedResult(String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, String[] strArr5, String[] strArr6, String[] strArr7) {
        this(strArr, null, null, strArr2, strArr3, strArr4, strArr5, null, null, strArr6, strArr7, null, null, null, null, null);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public AddressBookParsedResult(String[] strArr, String[] strArr2, String str, String[] strArr3, String[] strArr4, String[] strArr5, String[] strArr6, String str2, String str3, String[] strArr7, String[] strArr8, String str4, String str5, String str6, String[] strArr9, String[] strArr10) {
        super(ParsedResultType.ADDRESSBOOK);
        this.names = strArr;
        this.nicknames = strArr2;
        this.pronunciation = str;
        this.phoneNumbers = strArr3;
        this.phoneTypes = strArr4;
        this.emails = strArr5;
        this.emailTypes = strArr6;
        this.instantMessenger = str2;
        this.note = str3;
        this.addresses = strArr7;
        this.addressTypes = strArr8;
        this.f12org = str4;
        this.birthday = str5;
        this.title = str6;
        this.urls = strArr9;
        this.geo = strArr10;
    }

    public final String[] getNames() {
        return this.names;
    }

    public final String[] getNicknames() {
        return this.nicknames;
    }

    public final String getPronunciation() {
        return this.pronunciation;
    }

    public final String[] getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public final String[] getPhoneTypes() {
        return this.phoneTypes;
    }

    public final String[] getEmails() {
        return this.emails;
    }

    public final String[] getEmailTypes() {
        return this.emailTypes;
    }

    public final String getInstantMessenger() {
        return this.instantMessenger;
    }

    public final String getNote() {
        return this.note;
    }

    public final String[] getAddresses() {
        return this.addresses;
    }

    public final String[] getAddressTypes() {
        return this.addressTypes;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getOrg() {
        return this.f12org;
    }

    public final String[] getURLs() {
        return this.urls;
    }

    public final String getBirthday() {
        return this.birthday;
    }

    public final String[] getGeo() {
        return this.geo;
    }

    public final String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        maybeAppend(this.names, sb);
        maybeAppend(this.nicknames, sb);
        maybeAppend(this.pronunciation, sb);
        maybeAppend(this.title, sb);
        maybeAppend(this.f12org, sb);
        maybeAppend(this.addresses, sb);
        maybeAppend(this.phoneNumbers, sb);
        maybeAppend(this.emails, sb);
        maybeAppend(this.instantMessenger, sb);
        maybeAppend(this.urls, sb);
        maybeAppend(this.birthday, sb);
        maybeAppend(this.geo, sb);
        maybeAppend(this.note, sb);
        return sb.toString();
    }
}

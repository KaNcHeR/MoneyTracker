package com.agrotrading.kancher.moneytracker.utils;


public class ConstantManager {

    public static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_ERROR = "Error";
    public static final String STATUS_LOGIN_BUSY_ALREADY = "Login busy already";
    public static final String STATUS_WRONG_PASSWORD = "Wrong password";
    public static final String STATUS_WRONG_LOGIN = "Wrong login";
    public static final String STATUS_WRONG_TOKEN = "Wrong token";
    public static final String STATUS_UNAUTHORIZED = "unauthorized";
    public static final String REGISTER_FLAG = "1";

    public static final String TOKEN_KEY = "token_key";
    public static final String GOOGLE_TOKEN_KEY = "google_token_key";

    private final static String G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USER_INFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USER_INFO_SCOPE + " " + EMAIL_SCOPE;

    public final static String GOOGLE_ACCOUNT_TYPE = "com.google";
    public final static String DEFAULT_GOOGLE_TOKEN = "2";
    public final static int GET_GOOGLE_TOKEN_REQUEST_CODE = 10;

    public final static int INDEX_DRAWER_HEADER_ACCOUNT_DATA = 0;

    public static final String FILTER_ID = "filter_id";
    public static final int FILTER_DELAY = 700;

}

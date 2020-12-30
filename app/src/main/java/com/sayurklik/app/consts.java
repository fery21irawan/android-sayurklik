package com.sayurklik.app;

public class consts {

    //private static final String BASE_URL = "http://api.sayurklik.com/api/v1/";
    private static final String BASE_URL = "http://192.168.8.102/_2020/jobs/sayurklik/api/v1/";
    public static final String IMAGE_URL   = "http://192.168.8.102/_2020/jobs/sayurklik/contents/uploads/";
    public static final String AVATAR_URL   = "http://192.168.8.102/_2020/jobs/sayurklik/contents/uploads/avatars/";
    public static final String BANNER_URL  = "http://192.168.8.102/_2020/jobs/sayurklik/contents/uploads/banners/";
    /*private static final String BASE_URL = "http://api.sayurklik.com/api/v1/";
    public static final String IMAGE_URL = "http://api.sayurklik.com/contents/uploads/";
    public static final String BANNER_URL = "http://api.sayurklik.com/contents/uploads/banners/";*/
    public static final String PRODUCT = BASE_URL + "product";
    public static final String INVOICE = "http://192.168.8.102/_2020/jobs/sayurklik/invoice/";


    public static final String ID               = "id";
    public static final String PRODUCT_NAME     = "product_name";
    public static final String PRODUCT_UNIT     = "product_unit";
    public static final String PRODUCT_DESC     = "product_description";
    public static final String PRODUCT_PRICE    = "product_price";
    public static final String PRODUCT_STOCK    = "product_stock";
    public static final String PRODUCT_THUMBS   = "product_thumbs";
    public static final String PRODUCT_IMAGE    = "product_image";
    public static final String PRODUCT_CATEGORY = "product_category";
    public static final String PRODUCT_TAGS     = "product_tags";
    public static final String UPLOAD_BY        = "upload_by";
    public static final String UPLOAD_DATE      = "upload_date";

    /*for category*/
    public static final String CATEGORY         = BASE_URL + "category";
    public static final String CATEGORY_NAME    = "category_name";
    /*for cart*/
    public static final String CART             = BASE_URL + "cart";
    public static final String CART_ID          = "cart_id";
    public static final String ORDER_ID         = "order_id";
    public static final String PRODUCT_ID       = "product_id";
    public static final String TOTAL            = "total";
    public static final String USER_ID          = "user_id";

    /*for image carousel*/
    public static final String SLIDER           = BASE_URL + "slider";
    public static final String SLIDER_TITLE     = "slider_title";
    public static final String SLIDER_IMAGE     = "slider_image";
    public static final String SLIDER_LINK      = "slider_link";

    /*for feeds*/
    public static final String FEED             = BASE_URL + "feed";

    /*for sharedpreference*/
    public static final String USERLOGIN = "userlogin";
    public static final boolean ISLOGIN    = false;
    /*for data parcelable*/
    public static final String PARCELABLE = "parcelable";
    /*for json response*/
    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";

    /*for user login*/
    public static final String AUTH = BASE_URL + "auth";
    public static final String FULLNAME = "fullname";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String WA = "whatsapp";
    public static final String PROFIL_IMAGE = "profil_image";
    public static final String USER_LEVEL = "user_level";

    /*for order*/
    public static final String ORDER = BASE_URL + "order";
    public static final String NAME = "name";
    public static final String ORDER_DATE = "order_date";
    public static final String BILLING_ID = "billing_id";


    public static final String BILLING = BASE_URL + "billing";
    public static final String BILLING_NAME = "billing_name";
    public static final String STATUS_NAME = "status_name";


    /*payment*/
    public static final String PAYMENT = BASE_URL + "payment";
    /*Tagihan*/
    public static final String TAGIHAN = BASE_URL + "tagihan";

    /*Confirm*/
    public static final String CONFIRM = BASE_URL + "confirm";
}

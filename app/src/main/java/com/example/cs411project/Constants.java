package com.example.cs411project;

public class Constants {
    private static final String ROOT_URL = "http://192.168.1.14/foodApp/v2/";
    private static final String ROOT_URL_V1 = "http://192.168.1.14/foodApp/v1/";
    public static final String URL_INSERT_RESTAURANT = ROOT_URL+"create_eatery.php";
    public static final String URL_GET_RESTAURANT = ROOT_URL_V1+"getEatery.php";
    public static final String URL_KEYWORD_SEARCH = "http://192.168.1.14/GoogleApiReader/keyword_search.php";
    public static final String URL_AUTOMATION = ROOT_URL_V1+"automationV2.php";
    public static final String URL_GETVALUES_2 = ROOT_URL+"get_values_2.php";
    //public static final String URL_ADD_USER = ROOT_URL+"add_user.php";
    public static final String URL_ADD_USER = ROOT_URL_V1+"add_userV2.php";
    //public static final String URL_ADD_REVIEW = ROOT_URL+"add_review.php";
    public static final String URL_ADD_REVIEW = ROOT_URL_V1+"add_reviewV2.php";
    //public static final String URL_SEARCH_REVIEW = ROOT_URL+"search_review_byname.php";
    public static final String URL_SEARCH_REVIEW = ROOT_URL_V1+"search_review_bynameV2.php";
}
package com.gq.jetpackdemo.databinding;

public class BookRatingUtil {
    public static String getRating(int rating) {
        switch (rating) {
            case 0:
                return "零星";
            case 1:
                return "一星";
            case 2:
                return "二星";
            case 3:
                return "三星";
            case 4:
                return "四星";
            case 5:
                return "五星";
        }
        return "";
    }
}

package com.kankan.op.util;

public final class IPadMovieUtil {

    public static final String PREF_DETAIL_URL = "http://data.pad.kankan.com/ipad/vip_detail/";

    public static String movieDetailURL(long movieId) {
        StringBuilder buf = new StringBuilder(PREF_DETAIL_URL);
        buf.append(movieId / 1000).append("/").append(movieId).append(".xml");
        return buf.toString();
    }
}

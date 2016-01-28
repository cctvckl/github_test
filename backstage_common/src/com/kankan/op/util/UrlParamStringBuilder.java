package com.kankan.op.util;

import com.xunlei.util.StringHelper.KeyValueStringBuilder;

/**
 * @author ZengDong
 * @since 2011-4-12 下午06:50:44
 */
public class UrlParamStringBuilder implements KeyValueStringBuilder {

    public static final KeyValueStringBuilder INSTANCE = new UrlParamStringBuilder();

    private UrlParamStringBuilder() {
    }

    @Override
    public void append(StringBuilder sb, Object key, Object value) {
        sb.append(key).append("=").append(value).append("&");
    }
}

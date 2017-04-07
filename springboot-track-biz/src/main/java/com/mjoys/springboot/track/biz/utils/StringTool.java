package com.mjoys.springboot.track.biz.utils;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class StringTool.
 */
public class StringTool {

    private StringTool st;

    public StringTool getStringTool() {
        if (st == null) {
            st = new StringTool();
        }
        return st;
    }

    /**
     * 把一个2,44,51,34,123,123,4（都是数字）形式的字符串解析成一个List<Long>.
     *
     * @param arrayStr the array str
     * @return the long list by str
     */
    public static List<Long> getLongListByStr(String arrayStr) {
        if (StringUtils.isBlank(arrayStr)) {
            return Collections.emptyList();
        }
        String[] array = arrayStr.split(",");
        List<Long> list = new ArrayList<>();
        for (String s : array) {
            list.add(Long.valueOf(s));
        }
        return list;
    }

    /**
     * list转成String.<br>
     * 分隔符为","
     * 
     * @param list the list
     * @return the string by list
     */
    public static String getStringByList(List<String> list) {
        if (!CollectionUtils.isEmpty(list)) {
            StringBuilder builder = new StringBuilder();
            for (String str : list) {
                builder.append(str);
                builder.append(",");
            }

            return builder.substring(0, builder.length() - 1);
        }
        return StringUtils.EMPTY;
    }

    /**
     * string 转成List<String>.<br>
     * 分隔符为","
     * 
     * @param arrayStr the array str
     * @return the string list by str
     */
    public static List<String> getStringListByStr(String arrayStr) {

        if (!StringUtils.isBlank(arrayStr)) {
            List<String> list = new ArrayList<>();
            String[] array = arrayStr.split(",");
            for (String s : array) {
                list.add(s);
            }
            return list;
        } else {
            return Collections.emptyList();
        }

    }
}

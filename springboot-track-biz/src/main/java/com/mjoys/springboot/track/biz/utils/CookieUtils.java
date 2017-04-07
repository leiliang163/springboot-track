package com.mjoys.springboot.track.biz.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: CookieUtil <br/>
 * date: 2016年11月30日 下午3:49:41 <br/>
 *
 * @author ZFZ
 * @version
 * @since JDK 1.6
 */
public class CookieUtils {

    private CookieUtils cu;

    public CookieUtils getCookieUtil() {
        if (cu == null) {
            cu = new CookieUtils();
        }
        return cu;
    }

    /**
     * createCookie:(创建cookies). <br/>
     *
     * @author ZFZ
     * @param name
     * @param value
     * @param wildcard *.duiba.com.cn 级别，还是完整域名级别
     * @return
     * @since JDK 1.6
     */
    public static Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);//NOSONAR
        cookie.setPath("/");

        return cookie;
    }

    /**
     * setWildcardCookie:(设置通配符级别的cookie). <br/>
     *
     * @author ZFZ
     * @param name
     * @param value
     * @since JDK 1.6
     */
    public static void setWildcardCookie(String name, String value) {
        setCookie(name, value);
    }

    /**
     * setCookie:(设置cookie). <br/>
     *
     * @author ZFZ
     * @param name
     * @param value
     * @since JDK 1.6
     */
    public static void setCookie(String name, String value) {
        Cookie cookie = createCookie(name, value);
        RequestLocalUtils.get().getResponse().addCookie(cookie);
    }

    /**
     * 如果是线上环境，对应到.duiba.com.cn；如果是测试环境或者预发环境，通过配置项读取
     * 
     * @param name
     * @param value
     * @return
     */
    public static Cookie createCrossCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);//NOSONAR
        cookie.setPath("/");
        return cookie;
    }

    /**
     * setCrossCookie:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param name
     * @param value
     * @since JDK 1.6
     */
    public static void setCrossCookie(String name, String value) {
        Cookie cookie = createCrossCookie(name, value);
        RequestLocalUtils.get().getResponse().addCookie(cookie);
    }

    /**
     * 删除通配符级别的cookie
     * 
     * @param name
     */
    public static void deleteWildcardCookie(String name) {
        Cookie cookie = createCookie(name, null);
        cookie.setMaxAge(0);
        RequestLocalUtils.get().getResponse().addCookie(cookie);
    }

    /**
     * deleteCookie:(清除cookie). <br/>
     *
     * @author ZFZ
     * @param name
     * @since JDK 1.6
     */
    public static void deleteCookie(String name) {
        Cookie cookie = createCookie(name, null);
        cookie.setMaxAge(0);
        RequestLocalUtils.get().getResponse().addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     * 
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     * 
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 设置cookie
     * 
     * @param response
     * @param name cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);//NOSONAR
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

}

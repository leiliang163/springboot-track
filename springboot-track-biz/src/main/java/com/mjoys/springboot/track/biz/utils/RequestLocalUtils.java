package com.mjoys.springboot.track.biz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mjoys.springboot.track.common.constants.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * The Class RequestLocal.
 */
public class RequestLocalUtils {

    /** The logger. */
    private Logger                           logger             = LoggerFactory.getLogger(RequestLocalUtils.class);

    /** 一天的毫秒数. */
    private static final int                 DAY_OF_MILLISECOND = 86400000;

    /** The local. */
    private static ThreadLocal<RequestLocalUtils> local              = new ThreadLocal<>();

    /** 用户ID. */
    private Long                             cid;

    /** cookie中的账户内容. */
    private JSONObject                       accountInfo;

    /** The request. */
    private HttpServletRequest               request;

    /** The response. */
    private HttpServletResponse              response;

    /**
     * The Constructor.
     */
    private RequestLocalUtils() {
    }

    /**
     * Gets the.
     *
     * @return the request local
     */
    public static RequestLocalUtils get() {
        RequestLocalUtils rl = local.get();
        if (rl == null) {
            rl = new RequestLocalUtils();
            local.set(rl);
        }
        return rl;
    }

    /**
     * Clear.
     */
    public static void clear() {
        local.set(null);
    }

    /**
     * 根据key获取cookie中对应的值.<br>
     * 如果cookie为空或者不存在改key则返回null<br>
     *
     * @param key 键
     * @return cookie中对应的值
     */
    private String getCookie(String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        return value;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 获取cookie中关于账号信息部分的数据.
     *
     * @return the cookie value
     */
    public JSONObject getAccountInfo() {
        if (accountInfo == null) {
            try {
                // 获取账号信息
                String accound = getCookie(CommonConstants.COOKIE_KEY.ACCOUNT_KEY);
                if (accound != null) {
                    String content = SecureUtils.decryptConsumerCookie(accound);
                    if (StringUtils.isNotBlank(content)) {
                        JSONObject json = JSON.parseObject(content);
                        if (new Date().getTime() - json.getLong(CommonConstants.COOKIE_KEY.LOGIN_TIME_KEY) < DAY_OF_MILLISECOND) {
                            accountInfo = json;
                        } else {
                            // 24小时过期, 删除cookie
                            CookieUtils.deleteCookie(CommonConstants.COOKIE_KEY.ACCOUNT_KEY);
                        }
                    }
                }
            } catch (Exception e) {
                CookieUtils.deleteCookie(CommonConstants.COOKIE_KEY.ACCOUNT_KEY);
                logger.error("parse cookie happen error", e);
            }
        }
        return accountInfo;
    }

    /**
     * 得到当前登录用户ID.
     *
     * @return the user id
     */
    public Long getCid() {
        if (cid == null && getAccountInfo() != null) {
            cid = accountInfo.getLong(CommonConstants.COOKIE_KEY.ACCOUNT_ID_KEY);
        }
        return cid;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

}

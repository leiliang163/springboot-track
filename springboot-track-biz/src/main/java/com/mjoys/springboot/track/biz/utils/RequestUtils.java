package com.mjoys.springboot.track.biz.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 如果是Grails进入的请求，某些测试可以使用下面的方法获取: There is a new forwardURI property using request.forwardURI and you can obtain other
 * props using standard servlet request attributes: public static final String FORWARD_REQUEST_URI_ATTRIBUTE =
 * "javax.servlet.forward.request_uri"; public static final String FORWARD_CONTEXT_PATH_ATTRIBUTE =
 * "javax.servlet.forward.context_path"; public static final String FORWARD_SERVLET_PATH_ATTRIBUTE =
 * "javax.servlet.forward.servlet_path"; public static final String FORWARD_PATH_INFO_ATTRIBUTE =
 * "javax.servlet.forward.path_info"; public static final String FORWARD_QUERY_STRING_ATTRIBUTE =
 * "javax.servlet.forward.query_string";
 * 
 * @author xuhengfei
 */
public class RequestUtils {

    /** The local host ip. */
    private static String                          localHostIp;

    private static ThreadLocal<HttpServletRequest> request    = new ThreadLocal<>();

    private static String                          unknownStr = "unknown";

    private RequestUtils rqt;

    /**
     * getRequestTool:(构造函数). <br/>
     *
     * @return
     * @since JDK 1.6
     */
    public RequestUtils getRequestTool() {
        if (rqt == null) {
            rqt = new RequestUtils();
        }
        return rqt;
    }

    /**
     * Gets the local host ip.
     *
     * @return the local host ip
     */
    public static String getLocalHostIp() {
        return localHostIp;
    }

    /**
     * Sets the local host ip.
     *
     * @param localHostIp the local host ip
     */
    public static void setLocalHostIp(String localHostIp) {
        RequestUtils.localHostIp = localHostIp;
    }

    /**
     * Gets the http servlet request.
     *
     * @return the http servlet request
     */
    public static HttpServletRequest getHttpServletRequest() {
        return request.get();
    }

    /**
     * Sets the request in thread local.
     *
     * @param req the request in thread local
     */
    public static void setRequestInThreadLocal(HttpServletRequest req) {
        request.set(req);
    }

    /**
     * Gets the request ip in thread local.
     *
     * @return the request ip in thread local
     */
    public static String getRequestIpInThreadLocal() {
        HttpServletRequest req = request.get();
        if (req != null) {
            return getIpAddr(req);
        }
        return null;
    }

    /**
     * Gets the request ua in thread local.
     *
     * @return the request ua in thread local
     */
    public static String getRequestUAInThreadLocal() {
        HttpServletRequest req = request.get();
        if (req != null) {
            return req.getHeader("User-Agent");
        }
        return null;
    }

    /**
     * getNewRequestUrl:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static String getNewRequestUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return url;
        } else {
            return url + "?" + queryString;
        }
    }

    /**
     * getRequestUrl:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static String getRequestUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();

        if (queryString == null) {
            queryString = "";
        } else {
            queryString = "?" + queryString;
        }
        if ("post".equalsIgnoreCase(request.getMethod())) {
            queryString = "?" + queryString;
            Map<String, String[]> map = request.getParameterMap();
            for (String key : map.keySet()) {
                if (map.get(key).length > 0) {
                    queryString += key + "=" + map.get(key)[0] + "&";
                }
            }
        }
        String path = (String) request.getAttribute("javax.servlet.forward.request_uri");
        if (path != null) {
            return "http://" + request.getServerName() + ":" + request.getServerPort() + path + queryString;
        } else {
            return request.getRequestURL().toString();
        }
    }

    /**
     * getIpAddr:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.trim().length() > 0) {
            String[] ips = ip.trim().split(",");
            int size = ips.length;
            if (size > 0) {
                ip = ips[size - 1].trim();
            }
        }
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Cdn-Src-Ip");
        }
        if (ip == null || ip.length() == 0 || unknownStr.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.startsWith("0:0:0:0")) {
            ip = localHostIp;
        }
        return ip;
    }

    /**
     * 获取操作系统类型： Android,iPhone,iPad,Mac,Windows,Linux
     * 
     * @param request
     * @return
     */
    public static String getOS(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        if (ua == null) {
            return "Unkonwn";
        }
        ua = ua.toLowerCase();
        if (ua != null) {
            if (ua.contains("ipad")) {
                return "iPad";
            } else if (ua.contains("iphone")) {
                return "iPhone";
            } else if (ua.contains("android")) {
                return "Android";
            } else if (ua.contains("linux")) {
                return "Linux";
            } else if (ua.contains("windows")) {
                return "Windows";
            } else if (ua.contains("macintosh")) {
                return "Mac";
            }
        }
        return "Unknown";
    }

    /**
     * isLocalRequest:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static boolean isLocalRequest(HttpServletRequest request) {
        String ip = getIpAddr(request);
        if (ip.startsWith(localHostIp) || ip.startsWith("0:0:0:0")) {
            return true;
        }
        return false;
    }

    /**
     * isHttpsRequest:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static boolean isHttpsRequest(HttpServletRequest request) {
        if ("true".equals(request.getHeader("Use-Https"))) {
            return true;
        }
        return false;
    }

    /**
     * getCookie:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @param name
     * @return
     * @since JDK 1.6
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (name.equals(c.getName())) {
                    String value = c.getValue();
                    if (value != null && value.length() > 0) {
                        return value;
                    }
                }
            }
        }
        return null;
    }

    /**
     * getServerPath:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param request
     * @return
     * @since JDK 1.6
     */
    public static String getServerPath(HttpServletRequest request) {
        String port = String.valueOf(request.getServerPort());
        if ("80".equals(port)) {
            port = "";
        } else {
            port = ":" + port;
        }
        return request.getScheme() + "://" + request.getServerName() + port;
    }
}

package com.mjoys.springboot.track.biz.utils;



/**
 * 
 * ClassName: SecureTool <br/>
 * date: 2016年12月8日 下午6:00:10 <br/>
 *
 * @author ZFZ
 * @version 
 * @since JDK 1.6
 */
public class SecureUtils {

    private static String loginKey;
    private static String adminKey;
    private static String consumerKey;
    private static String appSecretKey;

    private SecureUtils st;

    public SecureUtils getSecureTool() {
        if (st == null) {
            st = new SecureUtils();
        }
        return st;
    }

    /**
     * 
     * decryptDeveloperCookie:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param data
     * @return
     * @since JDK 1.6
     */
    public static String decryptDeveloperCookie(String data) {
        return BlowfishUtils.decryptBlowfish(data, loginKey);
    }

    /**
     * 
     * decryptAdminCookie:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param data
     * @return
     * @since JDK 1.6
     */
    public static String decryptAdminCookie(String data) {
        return BlowfishUtils.decryptBlowfish(data, adminKey);
    }

    /**
     * 
     * decryptConsumerCookie:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param data
     * @return
     * @since JDK 1.6
     */
    public static String decryptConsumerCookie(String data) {
        return BlowfishUtils.decryptBlowfish(data, consumerKey);
    }

    /**
     * 
     * encryptAppSecret:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param data
     * @return
     * @since JDK 1.6
     */
    public static String encryptAppSecret(String data) {
        return BlowfishUtils.encryptBlowfish(data, appSecretKey);
    }

    /**
     * 
     * decryptAppSecretCode:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param data
     * @return
     * @since JDK 1.6
     */
    public static String decryptAppSecretCode(String data) {
        return BlowfishUtils.decryptBlowfish(data, appSecretKey);
    }

    /**
     * 
     * setLoginKey:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param loginKey
     * @since JDK 1.6
     */
    public static void setLoginKey(String loginKey) {
        SecureUtils.loginKey = loginKey;
    }

    /**
     * 
     * setAdminKey:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param adminKey
     * @since JDK 1.6
     */
    public static void setAdminKey(String adminKey) {
        SecureUtils.adminKey = adminKey;
    }

    /**
     * 
     * setConsumerKey:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param consumerKey
     * @since JDK 1.6
     */
    public static void setConsumerKey(String consumerKey) {
        SecureUtils.consumerKey = consumerKey;
    }

    /**
     * 
     * setAppSecretKey:(这里用一句话描述这个方法的作用). <br/>
     *
     * @author ZFZ
     * @param appSecretKey
     * @since JDK 1.6
     */
    public static void setAppSecretKey(String appSecretKey) {
        SecureUtils.appSecretKey = appSecretKey;
    }

}

/**
 * 文件名： CommonConstants.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午10:47:38
 */
package com.mjoys.springboot.track.common.constants;

/**
 * 通用常量 <功能详细描述>.
 *
 * @author: leiliang
 * @version:
 */
public class CommonConstants {

    /**
     * The Interface CONTENT_TYPE.
     */
    public interface CONTENT_TYPE {

        /** The Constant TEXT_TYPE. */
        public static final String TEXT_TYPE  = "text/plain";

        /** The Constant JSON_TYPE. */
        public static final String JSON_TYPE  = "application/json";

        /** The Constant XML_TYPE. */
        public static final String XML_TYPE   = "text/xml";

        /** The Constant HTML_TYPE. */
        public static final String HTML_TYPE  = "text/html";

        /** The Constant JS_TYPE. */
        public static final String JS_TYPE    = "text/javascript";

        /** The Constant EXCEL_TYPE. */
        public static final String EXCEL_TYPE = "application/vnd.ms-excel";
    }

    /**
     * Cookie.
     */
    public interface COOKIE_KEY {

        /** 账号的key. accountInfo */
        public static final String ACCOUNT_KEY       = "dataxyz123";

        /** 账号ID的key. cid */
        public static final String ACCOUNT_ID_KEY    = "accountId";

        /** 账号登录成功时间的key. */
        public static final String LOGIN_TIME_KEY    = "loginTime";

        /** 用户类型的key. ctype */
        public static final String ACCOUNT_TYPE_KEY  = "accountType";

        /** 广告主ID(当登录的为业务员时，广告主为该业务员绑定的广告主)的key. advertiserId */
        public static final String ADVERTISER_ID_KEY = "advertiserId";

        /** 当前登录用户角色ID的key. rid */
        public static final String ROLE_ID_KEY       = "roleId";

        /** 当前登录用户所属的代理商ID的key. agentId */
        public static final String AGENT_ID_KEY      = "agentId";

        /** 当前登录切换广告主状态标识的key. isSwitch */
        public static final String IS_SWITCH_KEY     = "isSwitch";

        /** 当前登录用户所属的代理商名称的key. */
        public static final String AGENT_NAME_KEY    = "agentName";

        /** 当前登录用户CSRF TOKEN KEY. */
        public static final String CSRF_TOKEN_KEY    = "token";
    }
}

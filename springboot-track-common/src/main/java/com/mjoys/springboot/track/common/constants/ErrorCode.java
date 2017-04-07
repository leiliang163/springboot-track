/**
 * 文件名： ErrorCode.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午10:15:33
 */
package com.mjoys.springboot.track.common.constants;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 此类描述的是： 推啊core工程内部自定义错误码规范<br>
 * 错误码组成：AABCCC
 * <ul>
 * AA：模块编号
 * <li>00：公用模块</li>
 * <li>01：用户模块</li>
 * <li>02：媒体模块</li>
 * <li>03：广告位模块</li>
 * <li>04：数据模块</li>
 * </ul>
 * <ul>
 * BB：错误类型
 * <li>01 - 媒体级错误（参数错误）</li>
 * <li>02 - 业务级错误（service自身错误）</li>
 * <li>03 - 依赖级错误（service调用第三方服务错误）</li>
 * <li>04 - 交互级业务提醒（正常业务逻辑，非错误，需告知用户，如库存不足）</li>
 * </ul>
 * <ul>
 * CCC:具体错误码 举例
 * <li>通用的成功状态码：000</li>
 * <li>通用的未知错误码：999</li>
 * </ul>
 * <ul>
 * 业务前缀
 * <li>tuia-core：TC_</li>
 * </ul>
 *
 * @规范： http://cf.dui88.com/pages/viewpage.action?pageId=3544570
 * @汇总文档：http://cf.dui88.com/pages/viewpage.action?pageId=3560274
 */
public enum ErrorCode {

    // 公共模块00
    /** 成功. */
    E0000000("0", "成功"),

    E9999999("9999999", "未知错误"),

    /** 数据库错误. */
    E0002001("0002001", "数据库错误"),

    /** 参数错误. */
    E0001002("0001002", "参数错误"),

    /** IO异常 */
    E0002003("0002003", "IO异常"),

    /** Base58加密失败 */
    E0002004("0002004", "Base58加密失败"),

    /** 发送短信验证码失败. */
    E0002005("0002005", "发送短信验证码失败"),

   ;

    /** 错误码. */
    private String errorCode;//NOSONAR

    /** 中文描述. */
    private String desc;

    /**
     * The Constructor.
     *
     * @param errorCode the error code
     * @param desc the desc
     */
    private ErrorCode(String errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    /**
     * Gets the error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the desc.
     *
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

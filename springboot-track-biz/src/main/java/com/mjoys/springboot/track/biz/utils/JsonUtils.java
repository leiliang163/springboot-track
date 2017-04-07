/**
 * 文件名： JsonUtils.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午10:48:33
 */
package com.mjoys.springboot.track.biz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.exception.InnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * json相关 <功能详细描述>
 * 
 * @author: leiliang
 * @version:
 */
public class JsonUtils {

    /** The logger. */
    private static Logger       logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper mapper = null;

    private JsonUtils           ju;

    public JsonUtils getJsonUtils() {
        if (ju == null) {
            ju = new JsonUtils();
        }
        return ju;
    }

    static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    /**
     * 对象转JSON
     * 
     * @param response
     * @param obj
     */
    public static void objToJSON(HttpServletResponse response, final Object obj) {
        try {
            setResponse(response);
            String jsonStr = getObjectMapper().writeValueAsString(obj);
            PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.close();
        } catch (IOException e) {
            logger.error("JsonUtils.objToJSON happen IOException", e);
        }
    }

    /**
     * 对象转json
     * 
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("JsonUtils.objectToString is error! the obj = [{}], the e = [{}]", obj, e);
            throw new InnerException(ErrorCode.E0001002.getErrorCode(), "对象转json异常");
        }
    }

    /**
     * json转对象
     * 
     * @param clazz
     * @param json
     * @return
     */
    public static <T> T jsonToObject(Class<T> clazz, String json){
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (Exception e) {
            logger.error("JsonUtils.jsonToObject is error! the clazz=[{}],the json=[{}], the e = [{}]", clazz, json, e);
            throw new InnerException(ErrorCode.E0001002.getErrorCode(), "json转对象异常");
        }
    }

    /**
     * 设置编码格式
     * 
     * @param response
     */
    public static void setResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
    }
}

/**   
 * 文件名：  AjaxUtils.java
 * 此类描述的是：  
 * 作者: leiliang   
 * 创建时间: 2016年3月23日 上午10:46:40  
 */   
package com.mjoys.springboot.track.biz.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjoys.springboot.track.common.constants.CommonConstants;
import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.constants.TimeFormat;
import com.mjoys.springboot.track.common.exception.InnerException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


/**
 * <一句话功能描述>
 * <功能详细描述>.
 *
 * @author: leiliang
 * @version:
 */
public class AjaxUtils {
	//-- header 常量定义 --//
		/** The Constant HEADER_ENCODING. */
	private static final String HEADER_ENCODING = "encoding";
		
		/** The Constant HEADER_NOCACHE. */
		private static final String HEADER_NOCACHE = "no-cache";
		
		/** The Constant DEFAULT_ENCODING. */
		private static final String DEFAULT_ENCODING = "UTF-8";
		
		/** The Constant DEFAULT_NOCACHE. */
		private static final boolean DEFAULT_NOCACHE = true;
		
		/** The Constant EMPTY_STRING. */
		private static final String EMPTY_STRING = "";

		/** The Constant mapper. */
		public static final ObjectMapper mapper = new ObjectMapper();

		static{
			mapper.setDateFormat(new SimpleDateFormat(TimeFormat.YYYY_MM_DD_HH_MM_SS));
		}

    /**
     * The Constructor.
     */
    private AjaxUtils() {

    }
		
		/**
		 * 直接输出内容的简便函数.
		 * render("text/plain", "hello", "encoding:GBK");
		 * render("text/plain", "hello", "no-cache:false");
		 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
		 *
		 * @param response the response
		 * @param contentType the content type
		 * @param content the content
		 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
		 */
		public static void render(HttpServletResponse response,final String contentType, final String content, final String... headers) {
			initResponseHeader(response,contentType, headers);
			PrintWriter write =null;
			try {
				write = response.getWriter();
				write.write(content);
				write.flush();
			} catch (IOException e) {
				throw new InnerException(ErrorCode.E0002003, e);
			}finally{
				if(write!=null){
					write.close();
				}
			}
		}

		/**
		 * 直接输出文本.
		 *
		 * @param response the response
		 * @param text the text
		 * @param headers the headers
		 */
		public static void renderText(HttpServletResponse response,final String text, final String... headers) {
			render(response, CommonConstants.CONTENT_TYPE.TEXT_TYPE, text, headers);
		}
		

		/**
		 * 直接输出HTML.
		 *
		 * @param response the response
		 * @param html the html
		 * @param headers the headers
		 */
		public static void renderHtml(HttpServletResponse response,final String html, final String... headers) {
			render(response,CommonConstants.CONTENT_TYPE.HTML_TYPE, html, headers);
		}

		/**
		 * 直接输出XML.
		 *
		 * @param response the response
		 * @param xml the xml
		 * @param headers the headers
		 */
		public static void renderXml(HttpServletResponse response,final String xml, final String... headers) {
			render(response,CommonConstants.CONTENT_TYPE.XML_TYPE, xml, headers);
		}

		/**
		 * 直接输出JSON.
		 *
		 * @param response the response
		 * @param jsonString json字符串.
		 * @param headers the headers
		 */
		public static void renderJson(HttpServletResponse response,final String jsonString, final String... headers) {
			render(response,CommonConstants.CONTENT_TYPE.JSON_TYPE, jsonString, headers);
		}

		/**
		 * 直接输出JSON,使用Jackson转换Java对象.
		 *
		 * @param response the response
		 * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
		 * @param headers the headers
		 */
		public static void renderJson(HttpServletResponse response,final Object data, final String... headers) {
			initResponseHeader(response,CommonConstants.CONTENT_TYPE.JSON_TYPE, headers);
			try {
				mapper.writeValue(response.getWriter(), data);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}

		/**
		 * 直接输出支持跨域Mashup的JSONP.
		 *
		 * @param response the response
		 * @param callbackName callback函数名.
		 * @param object Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
		 * @param headers the headers
		 */
		public static void renderJsonp(HttpServletResponse response,final String callbackName, final Object object, final String... headers) {
			String jsonString = null;
			try {
				jsonString = mapper.writeValueAsString(object);
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}

			String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

			/** 
			 * 渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 
			 * 如callback197("{html:'Hello World!!!'}");
			**/
			render(response,CommonConstants.CONTENT_TYPE.JS_TYPE, result, headers);
		}
		

		/**
		 * 分析并设置contentType与headers.
		 *
		 * @param response the response
		 * @param contentType the content type
		 * @param headers the headers
		 */
		private static void initResponseHeader(HttpServletResponse response, final String contentType, final String... headers) {
			//分析headers参数
			String encoding = DEFAULT_ENCODING;
			boolean noCache = DEFAULT_NOCACHE;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else {
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
				}
			}
			
			//设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				setDisableCacheHeader(response);
			}
		}
		
		/**
		 * 设置禁止客户端缓存的Header.
		 *
		 * @param response the disable cache header
		 */
		public static void setDisableCacheHeader(HttpServletResponse response) {
			//Http 1.0 header
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			//Http 1.1 header
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}
		
		/**
		 * Render jsonpor json.
		 *
		 * @param response the response
		 * @param callbackName the callback name
		 * @param object the object
		 * @param headers the headers
		 */
		public static void renderJsonporJson(HttpServletResponse response,final String callbackName, final Object object, final String... headers){
			if (callbackName!=null&&!callbackName.equals(EMPTY_STRING))
			{
				renderJsonp(response, callbackName, object, headers);
			}else {
				renderJson(response, object, headers);
			}
		}
		
		/**
		 * Render jsonpor json2.
		 *
		 * @param response the response
		 * @param callbackName the callback name
		 * @param data the data
		 * @param headers the headers
		 */
		public static void renderJsonporJson2(HttpServletResponse response,final String callbackName, final String data, final String... headers){
			if (callbackName!=null&&!callbackName.equals(EMPTY_STRING))
			{

				String result=callbackName+"("+data+")";
				renderText(response, result, headers);
			}else {
				renderJson(response, data, headers);
			}
		}
}
